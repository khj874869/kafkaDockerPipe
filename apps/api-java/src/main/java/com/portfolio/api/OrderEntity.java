package com.portfolio.api;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="orders")
public class OrderEntity {
  @Id @GeneratedValue private UUID id;
  private String customer; private Integer amount; private String status; private Instant createdAt;
  @PrePersist public void pre(){ if(createdAt==null) createdAt=Instant.now(); if(status==null) status="NEW"; }
  public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
  public String getCustomer(){return customer;} public void setCustomer(String c){customer=c;}
  public Integer getAmount(){return amount;} public void setAmount(Integer a){amount=a;}
  public String getStatus(){return status;} public void setStatus(String s){status=s;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant t){createdAt=t;}
}
