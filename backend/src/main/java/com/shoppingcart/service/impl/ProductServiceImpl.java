package com.shoppingcart.service.impl;

import com.shoppingcart.dto.ProductDtos;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;
import com.shoppingcart.utils.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDtos.ProductResponse> getProducts(String search, Category category) {
        List<Product> products;
        if (search != null && !search.isBlank()) {
            products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
        } else if (category != null) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }
        return products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDtos.ProductResponse getProductById(String productId) {
        return productMapper.toDto(findEntityById(productId));
    }

    @Override
    public ProductDtos.ProductResponse createProduct(ProductDtos.ProductRequest request) {
        Product product = new Product();
        mapRequest(product, request);
        Product savedProduct = productRepository.save(product);
        LOGGER.info("Created product {}", savedProduct.getName());
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDtos.ProductResponse updateProduct(String productId, ProductDtos.ProductRequest request) {
        Product product = findEntityById(productId);
        mapRequest(product, request);
        Product updatedProduct = productRepository.save(product);
        LOGGER.info("Updated product {}", updatedProduct.getId());
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = findEntityById(productId);
        productRepository.delete(product);
        LOGGER.info("Deleted product {}", productId);
    }

    private Product findEntityById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private void mapRequest(Product product, ProductDtos.ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(request.category());
        product.setInventory(request.inventory());
        product.setImageUrl(request.imageUrl());
    }
}
