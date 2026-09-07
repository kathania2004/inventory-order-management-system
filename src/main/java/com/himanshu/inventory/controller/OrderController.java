package com.himanshu.inventory.controller;

import com.himanshu.inventory.dto.*;
import com.himanshu.inventory.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.placeOrder(request));
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getByCustomer(@PathVariable Long customerId) {
        return service.getByCustomer(customerId);
    }
}
