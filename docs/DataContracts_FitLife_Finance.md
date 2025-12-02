# Data Models & API Contracts
## Application: FitLife Finance

### 1. Local Data Models (Room Database)

Since this starts as a local-first application, the Schema Definition is critical.

#### Table: `transactions`
| Column | Type | Constraints | Description |
|---|---|---|---|
| id | Long | Primary Key, AutoGen | Unique ID |
| amount | Double | Not Null | Monetary value |
| type | String | Check('EXPENSE', 'INCOME') | Discriminator |
| category_id | Long | Foreign Key | Link to Category |
| date | Long | Not Null | Epoch Millis |
| notes | String | Nullable | User notes |
| created_at | Long | Not Null | Audit |

#### Table: `categories`
| Column | Type | Constraints | Description |
|---|---|---|---|
| id | Long | Primary Key | |
| name | String | Not Null | Display Name (e.g., "Shopping") |
| icon_res | String | Not Null | Resource ID reference |
| color_hex | String | Not Null | Hex color for charts |

### 2. JSON API Contracts (REST Future-Proofing)
Although MVP is local, the domain models must map to these contracts for future cloud sync.

#### 2.1 GET /v1/transactions
**Response:**
```json
{
  "data": [
    {
      "id": "uuid-1234",
      "amount": 120.50,
      "currency": "USD",
      "type": "EXPENSE",
      "category": {
        "id": 5,
        "name": "Housing",
        "icon": "ic_house"
      },
      "date": "2025-10-26T14:30:00Z",
      "notes": "Rent payment"
    }
  ],
  "meta": {
    "page": 1,
    "total": 50
  }
}
```

#### 2.2 POST /v1/transactions
**Request:**
```json
{
  "amount": 45.00,
  "type": "EXPENSE",
  "category_id": 2,
  "date": "2025-10-26T00:00:00Z",
  "notes": "Grocery run"
}
```
**Response:** `201 Created`

### 3. Error Handling Models
Standardized error wrapper for domain layer communication.
```kotlin
sealed class DataError : Error {
    object Network : DataError()
    object DiskFull : DataError()
    data class Unknown(val exception: Throwable) : DataError()
}
```