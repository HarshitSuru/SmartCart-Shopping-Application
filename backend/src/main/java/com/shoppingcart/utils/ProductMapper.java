package com.shoppingcart.utils;

import com.shoppingcart.dto.ProductDtos;
import com.shoppingcart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements GenericMapper<Product, ProductDtos.ProductResponse> {

    @Override
    public ProductDtos.ProductResponse toDto(Product product) {
        return new ProductDtos.ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getInventory(),
                product.getImageUrl()
        );
    }
}
