package com.shoppingcart.config;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepository;

@Configuration
public class DataSeeder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner seedDefaultData(ProductRepository productRepository) {
        return args -> {
            List<Product> catalog = List.of(
                    product("Auraluxe Headphones", "Wireless headphones with warm sound, soft ear cushions, and the kind of battery life you stop thinking about.", new BigDecimal("14999"), Category.ELECTRONICS, 24, "https://images.pexels.com/photos/3394650/pexels-photo-3394650.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Terra Travel Pack", "A clean everyday backpack with room for a laptop, charger, notebook, and a quick overnight change of clothes.", new BigDecimal("3499"), Category.FASHION, 40, "https://images.pexels.com/photos/2905238/pexels-photo-2905238.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Brew Ritual Set", "Stoneware cups and a simple pour-over setup that make a kitchen shelf look a little better.", new BigDecimal("2199"), Category.HOME, 18, "https://images.pexels.com/photos/606545/pexels-photo-606545.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Pulse Fitness Watch", "A lightweight fitness watch for steps, heart rate, sleep, and quick glance notifications.", new BigDecimal("8999"), Category.SPORTS, 31, "https://images.pexels.com/photos/437037/pexels-photo-437037.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Northline Running Shoes", "Daily running shoes with a soft midsole and enough grip for roads, tracks, and quick errands after.", new BigDecimal("4299"), Category.SPORTS, 27, "https://images.pexels.com/photos/2529148/pexels-photo-2529148.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Canvas Desk Lamp", "A simple metal desk lamp with warm light for late work sessions and calm evening reading.", new BigDecimal("1899"), Category.HOME, 22, "https://images.pexels.com/photos/1112598/pexels-photo-1112598.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Weekender Polo", "An easy cotton polo that works for casual office days, short trips, and everyday wear.", new BigDecimal("1299"), Category.FASHION, 46, "https://images.pexels.com/photos/9558773/pexels-photo-9558773.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Quiet Keys Keyboard", "Low-profile wireless keyboard with a compact layout and a neat desk-friendly finish.", new BigDecimal("5499"), Category.ELECTRONICS, 19, "https://images.pexels.com/photos/841228/pexels-photo-841228.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Market Fresh Basket", "A mixed essentials basket with pantry basics that covers the week without overcomplicating the list.", new BigDecimal("1599"), Category.GROCERY, 35, "https://images.pexels.com/photos/264537/pexels-photo-264537.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Everyday Moisture Cream", "Light daily face cream with a soft finish that sits well under sunscreen or makeup.", new BigDecimal("799"), Category.BEAUTY, 52, "https://images.pexels.com/photos/7796458/pexels-photo-7796458.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Paperbound Notes Set", "A neat set of ruled notebooks for classes, meetings, lists, and the notes you want to keep.", new BigDecimal("699"), Category.BOOKS, 60, "https://images.pexels.com/photos/159711/books-bookstore-book-reading-159711.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Kitchen Prep Board", "Solid wood chopping board with enough surface for vegetables, fruit, bread, and quick dinner prep.", new BigDecimal("999"), Category.HOME, 29, "https://images.pexels.com/photos/616404/pexels-photo-616404.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Commuter Bottle", "Insulated steel bottle that keeps water cold through the day and fits into most backpack side pockets.", new BigDecimal("1099"), Category.SPORTS, 44, "https://images.pexels.com/photos/4000094/pexels-photo-4000094.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Soft Knit Throw", "A soft throw blanket that works on the sofa, the reading chair, or the foot of the bed.", new BigDecimal("1499"), Category.HOME, 26, "https://images.pexels.com/photos/6585759/pexels-photo-6585759.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Daily Grind Coffee", "Medium roast coffee beans with a smooth finish that work well for both filter and French press.", new BigDecimal("599"), Category.GROCERY, 70, "https://images.pexels.com/photos/894695/pexels-photo-894695.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Studio Mouse", "Simple wireless mouse with quiet clicks and an easy shape for long desk sessions.", new BigDecimal("1899"), Category.ELECTRONICS, 33, "https://images.pexels.com/photos/2115257/pexels-photo-2115257.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Weekend Cap", "Relaxed cotton cap for sunny walks, travel days, and daily wear.", new BigDecimal("699"), Category.FASHION, 48, "https://images.pexels.com/photos/1124465/pexels-photo-1124465.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Calm Clay Mask", "A gentle clay face mask that leaves skin feeling clean without drying it out too much.", new BigDecimal("499"), Category.BEAUTY, 38, "https://images.pexels.com/photos/3738346/pexels-photo-3738346.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Trail Duffel", "A roomy duffel bag for gym clothes, quick weekend trips, and everyday carry.", new BigDecimal("2799"), Category.FASHION, 21, "https://images.pexels.com/photos/1152077/pexels-photo-1152077.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Reading Lamp Mini", "Compact bedside lamp with a warm glow and a simple switch at the base.", new BigDecimal("1199"), Category.HOME, 36, "https://images.pexels.com/photos/112811/pexels-photo-112811.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Pocket Journal", "Small notebook that slips into a bag easily for lists, plans, and quick notes.", new BigDecimal("299"), Category.BOOKS, 84, "https://images.pexels.com/photos/904616/pexels-photo-904616.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Garden Fresh Fruit Box", "A mixed fruit box with everyday staples for the kitchen counter and lunch breaks.", new BigDecimal("899"), Category.GROCERY, 41, "https://images.pexels.com/photos/1132047/pexels-photo-1132047.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Motion Yoga Mat", "Cushioned yoga mat with enough grip for home workouts, stretching, and daily mobility work.", new BigDecimal("1599"), Category.SPORTS, 28, "https://images.pexels.com/photos/3823039/pexels-photo-3823039.jpeg?auto=compress&cs=tinysrgb&w=1200"),
                    product("Desk Shelf Speaker", "Compact Bluetooth speaker with clear vocals and enough volume for a room-sized playlist.", new BigDecimal("3299"), Category.ELECTRONICS, 23, "https://images.pexels.com/photos/63703/pexels-photo-63703.jpeg?auto=compress&cs=tinysrgb&w=1200")
            );

            for (Product sample : catalog) {
                Product existing = productRepository.findByName(sample.getName()).orElseGet(Product::new);
                existing.setName(sample.getName());
                existing.setDescription(sample.getDescription());
                existing.setPrice(sample.getPrice());
                existing.setCategory(sample.getCategory());
                existing.setInventory(sample.getInventory());
                existing.setImageUrl(sample.getImageUrl());
                productRepository.save(existing);
            }
            LOGGER.info("Seeded starter product catalog");
        };
    }

    private Product product(String name, String description, BigDecimal price, Category category, int inventory, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setInventory(inventory);
        product.setImageUrl(imageUrl);
        return product;
    }
}
