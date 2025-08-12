import React, { useEffect, useState } from "react";
import axios from "axios";
const API = import.meta.env.VITE_API_BASE || "http://localhost:8080";
export default function App(){
  const [orders,setOrders]=useState([]); const [customer,setCustomer]=useState("alice"); const [amount,setAmount]=useState(10);
  const load=async()=>{ const {data}=await axios.get(`${API}/api/orders`); setOrders(data); };
  const submit=async()=>{ await axios.post(`${API}/api/orders`, {customer, amount:Number(amount)}); await load(); };
  useEffect(()=>{ load(); },[]);
  return (<div style={{padding:12}}>
    <h1>Order Platform</h1>
    <input value={customer} onChange={e=>setCustomer(e.target.value)}/> 
    <input type="number" value={amount} onChange={e=>setAmount(e.target.value)}/>
    <button onClick={submit}>Create</button>
    <ul>{orders.map(o=> <li key={o.id}>{o.customer} - {o.amount}</li>)}</ul>
  </div>);
}
