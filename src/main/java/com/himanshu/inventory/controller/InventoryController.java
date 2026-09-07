package com.himanshu.inventory.controller;

import com.himanshu.inventory.dto.*;
import com.himanshu.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<InventoryResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{productId}")
    public InventoryResponse getByProductId(@PathVariable Long productId) {
        return service.getByProductId(productId);
    }

    @PutMapping("/{productId}")
    public InventoryResponse update(@PathVariable Long productId,
                                    @Valid @RequestBody InventoryUpdateRequest request) {
        return service.update(productId, request);
    }
}
