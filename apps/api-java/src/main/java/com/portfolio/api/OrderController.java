package com.portfolio.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository repository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderController(OrderRepository repository, KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<OrderEntity> list() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEntity create(@Valid @RequestBody CreateOrderRequest request) {
        OrderEntity order = new OrderEntity();
        order.setCustomer(request.customer().trim());
        order.setAmount(request.amount());
        OrderEntity saved = repository.save(order);
        kafkaTemplate.send(
                "orders",
                saved.getId().toString(),
                new OrderCreatedEvent(saved.getId(), saved.getCustomer(), saved.getAmount(), saved.getCreatedAt())
        );
        return saved;
    }

    public record CreateOrderRequest(
            @NotBlank @Size(max = 120) String customer,
            @NotNull @Positive Integer amount
    ) {
    }

    public record OrderCreatedEvent(UUID id, String customer, Integer amount, Instant createdAt) {
    }
}
