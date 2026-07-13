package com.portfolio.api;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="orders")
public class OrderEntity {
  @Id @GeneratedValue private UUID id;
  @Column(nullable=false, length=120) private String customer;
  @Column(nullable=false) private Integer amount;
  @Column(nullable=false, length=30) private String status;
  @Column(nullable=false, updatable=false) private Instant createdAt;
  @PrePersist public void pre(){ if(createdAt==null) createdAt=Instant.now(); if(status==null) status="NEW"; }
  public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
  public String getCustomer(){return customer;} public void setCustomer(String c){customer=c;}
  public Integer getAmount(){return amount;} public void setAmount(Integer a){amount=a;}
  public String getStatus(){return status;} public void setStatus(String s){status=s;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant t){createdAt=t;}
}
