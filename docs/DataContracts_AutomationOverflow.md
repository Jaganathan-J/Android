### Data Contracts

#### API Endpoints
1. **Authentication:** POST /auth/login, POST /auth/signup.
2. **Workflow:** POST /workflows, GET /workflows, PUT /workflows/{id}.
3. **Execution History:** GET /executions, GET /executions/{id}.
4. **Profile:** GET /users/me, PUT /users/me.

#### Request/Response Models
**Login Request:**
```json
{
  "email": "string",
  "password": "string"
}
```

**Workflow Creation Request:**
```json
{
  "name": "string",
  "trigger": "string",
  "action": "string"
}
```

**Response Models include status codes (200, 401, 500) and error messages.

#### Error Handling
- Custom exceptions for network errors.
- Global error handler in ViewModel layer.
- User-friendly error messages displayed.