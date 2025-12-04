# Data Models & API Contracts: FitLife Finance

## 1. API Standards
*   **Base URL:** `https://api.fitlife.finance/v1/`
*   **Format:** JSON
*   **Auth:** Bearer Token (JWT) in Header.
*   **Dates:** ISO 8601 UTC (`2025-10-26T14:30:00Z`).

## 2. Endpoints

### 2.1 Authentication
**POST /auth/login**
*   **Request:**
    ```json
    {
      "email": "john.doe@email.com",
      "password": "securePassword123!",
      "device_id": "android_pixel_7_uuid"
    }
    ```
*   **Response (200 OK):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1...",
      "refresh_token": "drt_345...",
      "user": {
        "id": "u_12345",
        "name": "John Doe",
        "email": "john.doe@email.com",
        "tier": "PREMIUM"
      }
    }
    ```

### 2.2 Transactions
**GET /transactions**
*   **Params:** `page`, `limit`, `from_date`, `to_date`, `type` (INCOME|EXPENSE), `search_term`.
*   **Response:**
    ```json
    {
      "data": [
        {
          "id": "tx_998",
          "amount": 85.50,
          "currency": "USD",
          "type": "EXPENSE",
          "category": {
            "id": "cat_food",
            "name": "Grocery Shopping",
            "icon_url": "assets/icons/cart.png"
          },
          "merchant": "Whole Foods",
          "timestamp": "2025-12-01T14:30:00Z"
        }
      ],
      "pagination": { "next_cursor": "cursor_xyz" }
    }
    ```

**POST /transactions**
*   Used by the "Add Expense" screen.
*   **Request:**
    ```json
    {
      "amount": 12.50,
      "merchant": "Starbucks",
      "category_id": "cat_coffee",
      "account_id": "acc_chase_123",
      "date": "2025-12-01",
      "notes": "Morning coffee",
      "is_recurring": false
    }
    ```

### 2.3 Bank Accounts
**GET /accounts**
*   **Response:**
    ```json
    {
      "total_balance": 48762.50,
      "accounts": [
        {
          "id": "acc_01",
          "institution": "Chase",
          "name": "Checking",
          "mask": "4521",
          "balance": 12450.00,
          "status": "ACTIVE",
          "type": "DEBIT"
        },
        {
          "id": "acc_02",
          "institution": "Wells Fargo",
          "name": "Credit Card",
          "mask": "2291",
          "balance": -2340.00,
          "status": "DUE_SOON",
          "due_date": "2025-12-05",
          "type": "CREDIT"
        }
      ]
    }
    ```

### 2.4 Budgets
**GET /budgets/current**
*   **Response:**
    ```json
    {
      "month": "2025-12",
      "total_budget": 5000.00,
      "total_spent": 3150.00,
      "categories": [
        {
          "id": "cat_housing",
          "limit": 1500.00,
          "spent": 1500.00,
          "color_hex": "#007BFF"
        },
        {
          "id": "cat_entertain",
          "limit": 300.00,
          "spent": 380.00,
          "color_hex": "#DC3545",
          "is_over_budget": true
        }
      ]
    }
    ```

## 3. Error Handling
Standard HTTP codes mapping:
*   `400`: Validation Error (e.g., Invalid Email).
*   `401`: Token Expired -> Refresh Flow.
*   `403`: Forbidden (Trying to access another user's data).
*   `500`: Internal Server Error -> Show "Something went wrong" generic UI.