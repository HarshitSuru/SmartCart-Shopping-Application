# Shopping Cart API Documentation

Base URL: `http://localhost:8080/api`

## Auth

### POST `/auth/register`
```json
{
  "fullName": "Harshit Sharma",
  "email": "harshit@example.com",
  "password": "Password123"
}
```

### POST `/auth/login`
```json
{
  "email": "harshit@example.com",
  "password": "Password123"
}
```

## Products

### GET `/products`
Optional query parameters:
- `search`
- `category`

### GET `/products/{id}`

### POST `/admin/products`
Admin only.

### PUT `/admin/products/{id}`
Admin only.

### DELETE `/admin/products/{id}`
Admin only.

## Cart

### GET `/cart`

### POST `/cart/add`
```json
{
  "productId": "PRODUCT_ID",
  "quantity": 2
}
```

### PUT `/cart/update`
```json
{
  "productId": "PRODUCT_ID",
  "quantity": 3
}
```

### DELETE `/cart/remove?productId=PRODUCT_ID`

## Checkout

### POST `/checkout`
```json
{
  "shippingAddress": "221B Baker Street, London",
  "paymentMethod": "UPI"
}
```

## Orders

### GET `/orders`

### GET `/orders/{orderId}`

## Auth Header

`Authorization: Bearer <jwt-token>`
