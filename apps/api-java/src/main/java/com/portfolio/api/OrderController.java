package com.portfolio.api;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/orders") @CrossOrigin(origins="*")
public class OrderController {
  @Autowired private OrderRepository repo;
  @Autowired(required=false) private KafkaTemplate<String,Object> kafka;
  @GetMapping public List<OrderEntity> list(){ return repo.findAll(); }
  @PostMapping public OrderEntity create(@RequestBody Map<String,Object> body){
    OrderEntity e = new OrderEntity();
    e.setCustomer((String)body.getOrDefault("customer","guest"));
    e.setAmount((Integer)body.getOrDefault("amount",0));
    e = repo.save(e);
    if(kafka!=null) kafka.send("orders", Map.of("id", e.getId().toString(), "amount", e.getAmount(), "customer", e.getCustomer()));
    return e;
  }
}
