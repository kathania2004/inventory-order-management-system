package com.himanshu.inventory.service;

import com.himanshu.inventory.dto.InventoryResponse;
import com.himanshu.inventory.dto.InventoryUpdateRequest;
import com.himanshu.inventory.entity.Inventory;
import com.himanshu.inventory.exception.ResourceNotFoundException;
import com.himanshu.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public InventoryResponse getByProductId(Long productId) {
        return toResponse(repository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId)));
    }

    @Transactional
    public InventoryResponse update(Long productId, InventoryUpdateRequest request) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));

        inventory.setQuantity(request.quantity());
        inventory.setReorderLevel(request.reorderLevel());
        return toResponse(repository.save(inventory));
    }

    private InventoryResponse toResponse(Inventory i) {
        return new InventoryResponse(
                i.getProduct().getId(),
                i.getProduct().getName(),
                i.getQuantity(),
                i.getReorderLevel(),
                i.getQuantity() <= i.getReorderLevel()
        );
    }
}
