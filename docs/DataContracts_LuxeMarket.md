# Data Models & API Contracts

## 1. Base API Structure
All responses are wrapped:
```json
{
  "data": { ... },
  "meta": {
    "page": 1,
    "has_next": true
  }
}
```

## 2. Endpoints

### Products
- `GET /v1/feed`
  - Headers: `Authorization: Bearer <token>`
  - Query: `page=1`, `sort=newest`
- `GET /v1/products/{id}`

**Product Model (JSON)**
```json
{
  "id": "prod_8821",
  "title": "Vintage Chanel Flap Bag",
  "price": {
    "amount": 450000,
    "currency": "USD",
    "display": "$4,500.00"
  },
  "images": [
    { "url": "https://cdn.luxe.com/img1.jpg", "aspect_ratio": 0.8 }
  ],
  "seller": {
    "id": "user_99",
    "rating": 4.9
  },
  "condition": "EXCELLENT"
}
```

### Cart
- `POST /v1/cart/items`
  - Body: `{ "product_id": "...", "qty": 1 }`
- `GET /v1/cart`

## 3. Error Handling
Standard HTTP codes observed. 401 triggers Force Logout. 503 triggers Maintenance Mode screen.