package com.shoppingcart.controller;

import com.shoppingcart.dto.ApiResponse;
import com.shoppingcart.dto.ProductDtos;
import com.shoppingcart.model.Category;
import com.shoppingcart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowed-origin}")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductDtos.ProductResponse>>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Category category
    ) {
        return ResponseEntity.ok(new ApiResponse<>("Products fetched successfully", productService.getProducts(search, category)));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductDtos.ProductResponse>> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(new ApiResponse<>("Product fetched successfully", productService.getProductById(id)));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<ApiResponse<ProductDtos.ProductResponse>> createProduct(@Valid @RequestBody ProductDtos.ProductRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Product created successfully", productService.createProduct(request)));
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ApiResponse<ProductDtos.ProductResponse>> updateProduct(@PathVariable("id") String id,
                                                                                  @Valid @RequestBody ProductDtos.ProductRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Product updated successfully", productService.updateProduct(id, request)));
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>("Product deleted successfully", null));
    }
}
