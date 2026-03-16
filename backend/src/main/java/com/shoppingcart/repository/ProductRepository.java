package com.shoppingcart.repository;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    java.util.Optional<Product> findByName(String name);
}
