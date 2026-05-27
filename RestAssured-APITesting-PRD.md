PRODUCT REQUIREMENT DOCUMENT (PRD)

Project Name:
REST Assured API Automation Framework – Employee Management System

Project Type:
Enterprise API Automation Framework

Domain:
HRMS / Employee Management

Prepared By:
Product Manager & Business Analyst

Version:
2.0

------------------------------------------------------------
1. PROJECT OVERVIEW
------------------------------------------------------------

This project aims to develop a REST Assured API Automation Framework for validating Employee Management System APIs.

The framework should automate:
- Employee creation
- Employee update
- Employee retrieval
- Employee deletion
- Authentication validation
- Response validation
- Database validation

------------------------------------------------------------
2. BASE URL DETAILS
------------------------------------------------------------

Environment URLs:

DEV:
https://dev-api.hrmsapp.com/api

QA:
https://qa-api.hrmsapp.com/api

UAT:
https://uat-api.hrmsapp.com/api

PROD:
https://api.hrmsapp.com/api

------------------------------------------------------------
3. AUTHENTICATION API DETAILS
------------------------------------------------------------

Token Generation API

Endpoint:
POST /auth/login

Full URL:
https://qa-api.hrmsapp.com/api/auth/login

Request Headers:
Content-Type: application/json

Sample Request Payload:
{
   "username": "qa_admin",
   "password": "Admin@123"
}

Sample Success Response:
{
   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
   "tokenType": "Bearer",
   "expiresIn": 3600
}

Token Usage:

Authorization: Bearer <token>

------------------------------------------------------------
4. SAMPLE API DETAILS
------------------------------------------------------------

4.1 CREATE EMPLOYEE API

Method:
POST

Endpoint:
POST /employees

Full URL:
https://qa-api.hrmsapp.com/api/employees

Headers:
Authorization: Bearer Token
Content-Type: application/json

Request Payload:
{
   "employeeId": 101,
   "firstName": "Rahul",
   "lastName": "Sharma",
   "email": "rahul.sharma@test.com",
   "mobile": "9876543210",
   "department": "QA",
   "designation": "Automation Tester",
   "salary": 65000
}

Expected Response:
{
   "message": "Employee Created Successfully",
   "status": "SUCCESS",
   "employeeId": 101
}

Expected Status Code:
201

------------------------------------------------------------
4.2 GET EMPLOYEE API
------------------------------------------------------------

Method:
GET

Endpoint:
GET /employees/{employeeId}

Sample URL:
https://qa-api.hrmsapp.com/api/employees/101

Expected Status Code:
200

------------------------------------------------------------
4.3 UPDATE EMPLOYEE API
------------------------------------------------------------

Method:
PUT

Endpoint:
PUT /employees/{employeeId}

Sample URL:
https://qa-api.hrmsapp.com/api/employees/101

Sample Payload:
{
   "designation": "Senior Automation Tester",
   "salary": 85000
}

Expected Status Code:
200

------------------------------------------------------------
4.4 DELETE EMPLOYEE API
------------------------------------------------------------

Method:
DELETE

Endpoint:
DELETE /employees/{employeeId}

Sample URL:
https://qa-api.hrmsapp.com/api/employees/101

Expected Status Code:
200

------------------------------------------------------------
5. TEST DATA
------------------------------------------------------------

POSITIVE TEST DATA

| EmployeeId | FirstName | LastName | Department | Salary |
|------------|------------|-----------|-------------|---------|
| 101        | Rahul      | Sharma    | QA          | 65000   |
| 102        | Priya      | Reddy     | DEV         | 80000   |
| 103        | Arjun      | Kumar     | HR          | 55000   |

NEGATIVE TEST DATA

| Scenario | Input |
|----------|-------|
| Empty first name | "" |
| Invalid email | testgmail.com |
| Invalid mobile | 123 |
| Negative salary | -1000 |
| Duplicate employee ID | 101 |

------------------------------------------------------------
6. FUNCTIONAL REQUIREMENTS
------------------------------------------------------------

1. Framework should support:
   - GET
   - POST
   - PUT
   - PATCH
   - DELETE

2. Framework should validate:
   - Status code
   - Response body
   - Response schema
   - Response time
   - Headers

3. Framework should support:
   - Token authentication
   - OAuth 2.0
   - API key authentication

4. Framework should generate:
   - Extent Reports
   - Allure Reports

------------------------------------------------------------
7. DATABASE VALIDATION
------------------------------------------------------------

Database:
MySQL

Sample Query:
SELECT * FROM employees WHERE employee_id = 101;

Validation:
- API response data should match DB records

------------------------------------------------------------
8. TEST SCENARIOS
------------------------------------------------------------

Positive Scenarios:
1. Create employee successfully
2. Fetch employee details
3. Update employee information
4. Delete employee successfully

Negative Scenarios:
1. Create employee without token
2. Create employee with invalid payload
3. Access invalid endpoint
4. Submit duplicate employee ID
5. Validate unauthorized response

------------------------------------------------------------
9. NON-FUNCTIONAL REQUIREMENTS
------------------------------------------------------------

1. Parallel execution support
2. Cross-environment execution
3. Reusable components
4. Secure credential handling
5. CI/CD compatibility

------------------------------------------------------------
10. TECH STACK
------------------------------------------------------------

Language:
Java

Automation Tool:
REST Assured

Framework:
TestNG

Build Tool:
Maven

Reporting:
Extent Reports

Version Control:
GitHub

CI/CD:
Jenkins

Logging:
Log4j

------------------------------------------------------------
11. FOLDER STRUCTURE
------------------------------------------------------------

src/test/java
   |
   |-- api
   |-- utils
   |-- payloads
   |-- testcases
   |-- reports
   |-- config

------------------------------------------------------------
12. ACCEPTANCE CRITERIA
------------------------------------------------------------

1. APIs should execute successfully
2. Token generation should work
3. Reports should generate automatically
4. Framework should support parallel execution
5. Logs should capture request and response details

------------------------------------------------------------
13. FUTURE ENHANCEMENTS
------------------------------------------------------------

1. Docker support
2. Kubernetes integration
3. AI-based API validation
4. API contract testing
5. Performance testing integration

------------------------------------------------------------
END OF DOCUMENT
------------------------------------------------------------