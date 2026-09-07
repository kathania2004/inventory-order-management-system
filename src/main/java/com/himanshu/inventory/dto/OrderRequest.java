package com.himanshu.inventory.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record OrderRequest(
        @NotNull @Positive Long customerId,
        @NotEmpty List<@Valid OrderItemRequest> items
) {}
