package com.shoppingcart.dto;

import com.shoppingcart.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public final class ProductDtos {

    private ProductDtos() {
    }

    public record ProductRequest(
            @NotBlank String name,
            @NotBlank String description,
            @NotNull @DecimalMin("0.0") BigDecimal price,
            @NotNull Category category,
            @Min(0) int inventory,
            String imageUrl
    ) {
    }

    public record ProductResponse(
            String id,
            String name,
            String description,
            BigDecimal price,
            Category category,
            int inventory,
            String imageUrl
    ) {
    }
}
