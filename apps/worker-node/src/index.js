import express from "express";
import { Kafka } from "kafkajs";
import client from "prom-client";
const KAFKA_BROKER = process.env.KAFKA_BROKER || "kafka:9092";
const KAFKA_TOPIC = process.env.KAFKA_TOPIC || "orders";
const METRICS_PORT = process.env.METRICS_PORT || 9100;
const registry = new client.Registry(); client.collectDefaultMetrics({ register: registry });
const processed = new client.Counter({ name: "order_worker_processed_total", help: "Total processed order events" });
const failed = new client.Counter({ name: "order_worker_failed_total", help: "Invalid or failed order events" });
registry.registerMetric(processed);
registry.registerMetric(failed);
const app = express();
app.get("/metrics", async (_req, res)=>{ res.set("Content-Type", registry.contentType); res.end(await registry.metrics()); });
app.get("/", (_req,res)=>res.send("worker ok"));
async function run(){
  const kafka = new Kafka({ clientId: "order-worker", brokers: [KAFKA_BROKER] });
  const consumer = kafka.consumer({ groupId: "order-workers" });
  await consumer.connect(); await consumer.subscribe({ topic: KAFKA_TOPIC, fromBeginning: true });
  await consumer.run({ eachMessage: async ({ message }) => {
    try {
      const event = JSON.parse(message.value?.toString() || "{}");
      if (!event.id || !event.customer || !Number.isInteger(event.amount) || event.amount <= 0) {
        throw new Error("invalid order event");
      }
      processed.inc();
      console.log(JSON.stringify({ event: "order.processed", orderId: event.id, amount: event.amount }));
    } catch (error) {
      failed.inc();
      console.error(JSON.stringify({ event: "order.rejected", error: String(error) }));
      throw error;
    }
  } });
  app.listen(METRICS_PORT, ()=>console.log("metrics on", METRICS_PORT));
}
run().catch(e=>{ console.error(e); process.exit(1); });
