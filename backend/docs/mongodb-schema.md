# Example MongoDB Schema

## users
```json
{
  "_id": "65f0c7...",
  "fullName": "Harshit Sharma",
  "email": "harshit@example.com",
  "password": "$2a$10$...",
  "role": "USER",
  "created_at": "2026-03-14T09:00:00Z",
  "updated_at": "2026-03-14T09:00:00Z"
}
```

## products
```json
{
  "_id": "65f0d1...",
  "name": "Auraluxe Headphones",
  "description": "Immersive wireless headphones with adaptive noise control.",
  "price": 14999,
  "category": "ELECTRONICS",
  "inventory": 24,
  "imageUrl": "https://images.unsplash.com/photo-1505740420928-5e560c06d30e"
}
```

## carts
```json
{
  "_id": "65f0d8...",
  "userId": "65f0c7...",
  "items": [
    {
      "productId": "65f0d1...",
      "productName": "Auraluxe Headphones",
      "unitPrice": 14999,
      "quantity": 1,
      "imageUrl": "https://images.unsplash.com/photo-1505740420928-5e560c06d30e"
    }
  ],
  "totalPrice": 14999
}
```

## orders
```json
{
  "_id": "65f0e3...",
  "userId": "65f0c7...",
  "userEmail": "harshit@example.com",
  "items": [
    {
      "productId": "65f0d1...",
      "productName": "Auraluxe Headphones",
      "unitPrice": 14999,
      "quantity": 1,
      "subtotal": 14999
    }
  ],
  "totalPrice": 14999,
  "status": "CONFIRMED",
  "shippingAddress": "221B Baker Street",
  "paymentMethod": "UPI"
}
```
