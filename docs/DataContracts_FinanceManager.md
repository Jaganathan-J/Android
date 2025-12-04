# Data Models & API Contracts

## 1. Base URL
`https://api.financemanager.app/v1`

## 2. Authentication
### POST /auth/login
**Request:**
```json
{
  "email": "john.doe@email.com",
  "password": "securePassword123"
}
```
**Response (200 OK):**
```json
{
  "access_token": "jwt_token_string",
  "refresh_token": "refresh_token_string",
  "expires_in": 3600,
  "user": {
    "id": "user_123",
    "name": "John Doe",
    "avatar_url": "https://..."
  }
}
```

## 3. Accounts
### GET /accounts
**Response:**
```json
[
  {
    "id": "acc_chase_01",
    "institution_name": "Chase",
    "account_type": "CHECKING",
    "last_four": "4521",
    "balance": 12450.00,
    "is_primary": true,
    "status": "ACTIVE" // or "OVERDUE"
  },
  {
    "id": "acc_wf_cc",
    "institution_name": "Wells Fargo Credit Card",
    "account_type": "CREDIT_CARD",
    "last_four": "2291",
    "balance": -2340.00,
    "next_payment_due": "2024-12-05"
  }
]
```

## 4. Transactions
### GET /transactions
**Query Params:** `?startDate=2024-12-01&endDate=2024-12-31&type=EXPENSE`
**Response:**
```json
[
  {
    "id": "tx_098",
    "title": "Grocery Shopping",
    "merchant": "Whole Foods",
    "amount": -85.50,
    "currency": "USD",
    "timestamp": "2024-12-01T14:30:00Z",
    "category_id": "cat_food",
    "type": "EXPENSE" // enum: INCOME, EXPENSE, TRANSFER
  }
]
```

### POST /transactions (Add Expense)
**Request:**
```json
{
  "amount": 15.50,
  "category_id": "cat_dining",
  "source_account_id": "acc_chase_01",
  "note": "Lunch with team",
  "is_recurring": false,
  "timestamp": "2024-12-06T12:00:00Z"
}
```

## 5. Budget
### GET /budgets/monthly
**Query Params:** `?month=12&year=2024`
**Response:**
```json
{
  "total_budget": 5000.00,
  "total_spent": 3150.00,
  "categories": [
    {
      "category_name": "Housing",
      "limit": 1500.00,
      "spent": 1500.00,
      "status": "AT_LIMIT"
    },
    {
      "category_name": "Entertainment",
      "limit": 300.00,
      "spent": 380.00,
      "status": "OVER_BUDGET"
    }
  ]
}
```