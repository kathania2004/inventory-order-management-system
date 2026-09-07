package com.himanshu.inventory.dto;

public record InventoryResponse(
        Long productId,
        String productName,
        Integer quantity,
        Integer reorderLevel,
        boolean lowStock
) {}
