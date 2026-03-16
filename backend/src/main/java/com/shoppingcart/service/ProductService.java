package com.shoppingcart.service;

import com.shoppingcart.dto.ProductDtos;
import com.shoppingcart.model.Category;

import java.util.List;

public interface ProductService {

    List<ProductDtos.ProductResponse> getProducts(String search, Category category);

    ProductDtos.ProductResponse getProductById(String productId);

    ProductDtos.ProductResponse createProduct(ProductDtos.ProductRequest request);

    ProductDtos.ProductResponse updateProduct(String productId, ProductDtos.ProductRequest request);

    void deleteProduct(String productId);
}
