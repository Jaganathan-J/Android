# Data Models & API Contracts - FitLife Finance

## 1. Overview
The app assumes a RESTful JSON API. All monetary values are handled as `BigDecimals` mapped to strings or minor units (cents) to avoid floating-point errors.

## 2. Entities (Domain)

### 2.1. Money
```kotlin
data class Money(
    val amount: BigDecimal,
    val currencyCode: String = "USD"
)
```

### 2.2. Transaction
```kotlin
data class Transaction(
    val id: String,
    val merchantName: String,
    val category: CategoryType,
    val amount: Money,
    val timestamp: Instant
)
```

## 3. API Contracts (JSON)

### 3.1. GET /v1/dashboard/summary
**Response:**
```json
{
  "total_balance": {
    "amount": 5234.50,
    "currency": "USD"
  },
  "monthly_spending": {
    "amount": 1234.56,
    "currency": "USD"
  },
  "cards": [
    {
      "id": "card_123",
      "network": "VISA",
      "last_4": "4000"
    }
  ]
}
```

### 3.2. GET /v1/transactions
**Query Params:** `page=1`, `limit=20`
**Response:**
```json
{
  "data": [
    {
      "id": "tx_999",
      "merchant": "Starbucks",
      "category": "FOOD",
      "amount": -5.50,
      "date": "2025-10-26T09:41:00Z"
    }
  ],
  "next_cursor": "tx_998"
}
```

### 3.3. POST /v1/expenses
**Request:**
```json
{
  "amount": 45.99,
  "currency": "USD",
  "category": "SHOPPING",
  "description": "Amazon Purchase",
  "date": "2025-10-26T12:00:00Z"
}
```
**Response:** `201 Created`

### 3.4. GET /v1/budget
**Response:**
```json
{
  "total_budget": 5000.00,
  "categories": [
    {
      "name": "Food",
      "spent": 250.00,
      "limit": 500.00
    },
    {
      "name": "Transport",
      "spent": 100.00,
      "limit": 300.00
    }
  ]
}
```

## 4. Local Database (Room)

### 4.1. Entity: TransactionEntity
```kotlin
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val merchant: String,
    val amount: String, // Stored as string to preserve precision
    val timestamp: Long
)
```