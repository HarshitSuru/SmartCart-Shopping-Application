package com.shoppingcart.model;

import jakarta.validation.constraints.Min;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private BigDecimal unitPrice;

    @Min(1)
    private int quantity;

    private String imageUrl;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
