ROLE: You are an API Testing Specialist with expertise in REST Assured framework and Java test automation.

TASK: Generate comprehensive test cases for the Booking API CRUD operations based on the provided API documentation and existing test structure.

COVERAGE REQUIRED:
- ✅ Happy path (valid requests) - full CRUD functionality
- ✅ Invalid inputs (validation errors, missing fields, wrong data types)
- ✅ Authentication/Authorization (token creation, invalid tokens, missing auth)
- ✅ Error handling (404, 500, timeout scenarios)
- ✅ Boundary conditions (max/min length, special characters, negative numbers)
- ✅ Negative test cases for each endpoint

CONSTRAINTS:
- Use ONLY the API documentation from: https://documenter.getpostman.com/view/3967924/RW1dExDv
- Include exact status codes and error messages as documented
- Do NOT assume undocumented behavior or endpoints
- Follow the existing test pattern from testCreateBooking.java (BaseTest extension, Allure annotations, assertion structure)

OUTPUT FORMAT:

## 1. Test Case Summary Table
| Test ID | Endpoint | Method | Request Body | Expected Status | Expected Response |

## 2. REST Assured Implementation Snippets
For each critical test case, provide:
- Java method with @Test annotation
- Allure tags (@Owner, @TmsLink, @Issue, @Description)
- Request specification setup
- Assertions using the existing assertActions utility

## 3. Test Data Variations
- Valid payload examples
- Invalid payload examples (missing fields, wrong types, boundary values)
- Auth token variations (valid, expired, invalid, missing)

## 4. CRUD Operations Coverage Map
- CREATE (POST /booking) - 5+ test cases
- READ (GET /booking/{id}) - 5+ test cases  
- UPDATE (PUT /booking/{id}) - 5+ test cases
- DELETE (DELETE /booking/{id}) - 5+ test cases
- PARTIAL UPDATE (PATCH /booking/{id}) - 3+ test cases
- AUTH (POST /auth) - 4+ test cases

REQUIREMENTS FROM YOUR CODEBASE:
- Extend BaseTest class
- Use payloadManager.createPayloadBookingAsString() pattern
- Use APIConstants for endpoints
- Use assertActions.verifyStringKey() for assertions
- Follow the existing naming convention: testVerify[Operation][Status][Number]

SPECIFIC SCENARIOS TO INCLUDE:

### For testCreateToken.java (currently empty):
- TC_TOKEN_01: Valid credentials → 200 OK with token
- TC_TOKEN_02: Invalid username → 200 OK with "reason": "Bad credentials"
- TC_TOKEN_03: Invalid password → 200 OK with error message
- TC_TOKEN_04: Empty credentials → 400 Bad Request
- TC_TOKEN_05: Missing auth header → 401 Unauthorized

### For testCreateBooking.java (extend existing):
- TC_BOOK_01: Create booking with all valid fields (existing test)
- TC_BOOK_02: Create booking with missing required field (firstname) → 400/500
- TC_BOOK_03: Create booking with invalid data type (number in firstname) → 400
- TC_BOOK_04: Create booking with empty request body → 400
- TC_BOOK_05: Create booking with special characters in name → 200 (verify escaping)

### For READ operations (new file needed):
- TC_GET_01: Get existing booking by ID → 200
- TC_GET_02: Get non-existent booking ID → 404
- TC_GET_03: Get booking with invalid ID format → 400
- TC_GET_04: Get booking with negative ID → 404

### For UPDATE operations:
- TC_UPDATE_01: Update booking with valid auth token → 200
- TC_UPDATE_02: Update booking without auth token → 403
- TC_UPDATE_03: Update non-existent booking → 404
- TC_UPDATE_04: Update with invalid token → 403

### For DELETE operations:
- TC_DELETE_01: Delete booking with valid auth → 201
- TC_DELETE_02: Delete already deleted booking → 404
- TC_DELETE_03: Delete without auth → 403
- TC_DELETE_04: Delete with invalid ID → 404

DELIVERABLES:
1. Complete test case table (40+ test cases)
2. Ready-to-use REST Assured code for each major scenario
3. TestNG XML configuration for grouping (smoke, regression, negative)
4. DataProvider methods for parameterized testing
5. JSON schema validation snippets where applicable

PRIORITY MARKING:
- 🔴 P0: Critical (Happy path, auth failure)
- 🟠 P1: High (Common validation errors)  
- 🟡 P2: Medium (Edge cases, boundary)
- 🟢 P3: Low (Rare scenarios)