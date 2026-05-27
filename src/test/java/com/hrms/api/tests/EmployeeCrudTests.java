package com.hrms.api.tests;

import com.hrms.api.base.BaseTest;
import com.hrms.api.constants.APIConstants;
import com.hrms.api.utils.PayloadManager;
import com.hrms.api.utils.ResponseValidator;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EmployeeCrudTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "TC_BOOK_01: Create employee with valid fields")
    public void testVerifyCreateEmployeeValid() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(101, "Rahul", "Sharma", "rahul.sharma@test.com", "9876543210", "QA", "Automation Tester", 65000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 201);
        ResponseValidator.verifyStringKey(response, "message", "Employee Created Successfully");
        ResponseValidator.verifyJsonSchema(response, "schema/employee-schema.json");
    }

    @Test(groups = {"negative", "regression"}, description = "TC_BOOK_02: Create employee missing required firstname should fail")
    public void testVerifyCreateEmployeeMissingFirstName() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(102, "", "Reddy", "priya.reddy@test.com", "8888888888", "DEV", "Developer", 80000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 400);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_BOOK_03: Create employee with invalid email should fail")
    public void testVerifyCreateEmployeeInvalidEmail() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(103, "Arjun", "Kumar", "invalid-email", "7777777777", "HR", "HR Executive", 55000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 400);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_BOOK_04: Create employee with empty payload should fail")
    public void testVerifyCreateEmployeeEmptyBody() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .body("")
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 400);
    }

    @Test(groups = {"regression"}, description = "TC_BOOK_05: Create employee with special characters in name should succeed")
    public void testVerifyCreateEmployeeWithSpecialCharacters() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(104, "Rahul@123", "Sharma!", "rahul.special@test.com", "9999999999", "QA", "Automation@Tester", 65000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 201);
        ResponseValidator.verifyStringKey(response, "message", "Employee Created Successfully");
    }

    @Test(groups = {"smoke", "regression"}, description = "TC_GET_01: Get existing employee by ID")
    public void testVerifyGetExistingEmployee() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse createResponse = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(105, "Priya", "Reddy", "priya.reddy@test.com", "7771112222", "DEV", "Developer", 80000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        int employeeId = createResponse.extract().path("employeeId");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", employeeId)
                .when()
                .get(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 200);
        ResponseValidator.verifyNonNullKey(response, "employeeId");
    }

    @Test(groups = {"negative", "regression"}, description = "TC_GET_02: Get non-existent employee ID should return 404")
    public void testVerifyGetNonExistentEmployee() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", 99999)
                .when()
                .get(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 404);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_GET_03: Get employee with invalid ID format should return 400")
    public void testVerifyGetInvalidIdFormat() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", "abc")
                .when()
                .get(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 400);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_GET_04: Get employee with negative ID should return 404")
    public void testVerifyGetNegativeId() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", -1)
                .when()
                .get(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 404);
    }

    @Test(groups = {"regression"}, description = "TC_UPDATE_01: Update employee with valid auth token should succeed")
    public void testVerifyUpdateEmployeeWithValidAuth() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse createResponse = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(106, "Arjun", "Kumar", "arjun.kumar@test.com", "6667778888", "HR", "HR Executive", 55000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        int employeeId = createResponse.extract().path("employeeId");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", employeeId)
                .body(PayloadManager.createEmployeePayload(employeeId, "Arjun", "Kumar", "arjun.kumar@test.com", "6667778888", "HR", "Senior HR Executive", 65000))
                .when()
                .put(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 200);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_UPDATE_02: Update employee without auth token should be forbidden")
    public void testVerifyUpdateEmployeeWithoutAuth() {
        ValidatableResponse response = given()
                .spec(defaultSpec())
                .pathParam("employeeId", 101)
                .body(PayloadManager.createEmployeePayload(101, "Rahul", "Sharma", "rahul.sharma@test.com", "9876543210", "QA", "Senior Automation Tester", 85000))
                .when()
                .put(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 403);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_UPDATE_03: Update non-existent employee should return 404")
    public void testVerifyUpdateNonExistentEmployee() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", 99999)
                .body(PayloadManager.createEmployeePayload(99999, "Non", "Exist", "non.exist@test.com", "5555555555", "QA", "Tester", 50000))
                .when()
                .put(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 404);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_UPDATE_04: Update employee with invalid token should be rejected")
    public void testVerifyUpdateEmployeeWithInvalidToken() {
        ValidatableResponse response = given()
                .spec(authorizedSpec("invalid.token.value"))
                .pathParam("employeeId", 101)
                .body(PayloadManager.createEmployeePayload(101, "Rahul", "Sharma", "rahul.sharma@test.com", "9876543210", "QA", "Senior Automation Tester", 85000))
                .when()
                .put(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 403);
    }

    @Test(groups = {"smoke", "regression"}, description = "TC_DELETE_01: Delete employee with valid auth token should succeed")
    public void testVerifyDeleteEmployeeWithValidAuth() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse createResponse = given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(107, "Rita", "Patel", "rita.patel@test.com", "1112223333", "QA", "Automation Tester", 62000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        int employeeId = createResponse.extract().path("employeeId");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", employeeId)
                .when()
                .delete(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 200);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_DELETE_02: Delete already deleted employee should return 404")
    public void testVerifyDeleteAlreadyDeletedEmployee() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        int employeeId = 108;
        given()
                .spec(authorizedSpec(token))
                .body(PayloadManager.createEmployeePayload(employeeId, "Sana", "Khan", "sana.khan@test.com", "3334445555", "HR", "HR Analyst", 58000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then()
                .statusCode(201);

        given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", employeeId)
                .when()
                .delete(APIConstants.EMPLOYEE_BY_ID)
                .then()
                .statusCode(200);

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", employeeId)
                .when()
                .delete(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 404);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_DELETE_03: Delete employee without auth token should be forbidden")
    public void testVerifyDeleteEmployeeWithoutAuth() {
        ValidatableResponse response = given()
                .spec(defaultSpec())
                .pathParam("employeeId", 101)
                .when()
                .delete(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 403);
    }

    @Test(groups = {"negative", "regression"}, description = "TC_DELETE_04: Delete employee with invalid ID should return 404")
    public void testVerifyDeleteEmployeeWithInvalidId() {
        String token = generateAuthToken("qa_admin", "Admin@123");

        ValidatableResponse response = given()
                .spec(authorizedSpec(token))
                .pathParam("employeeId", 123456789)
                .when()
                .delete(APIConstants.EMPLOYEE_BY_ID)
                .then();

        ResponseValidator.verifyStatusCode(response, 404);
    }

    @DataProvider(name = "employeeUpdateData")
    public Object[][] employeeUpdateData() {
        return new Object[][]{
                {106, "Senior Automation Tester", 90000},
                {107, "Lead Automation Tester", 95000}
        };
    }
}
