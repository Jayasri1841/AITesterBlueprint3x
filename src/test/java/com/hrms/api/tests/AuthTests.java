package com.hrms.api.tests;

import com.hrms.api.base.BaseTest;
import com.hrms.api.constants.APIConstants;
import com.hrms.api.utils.PayloadManager;
import com.hrms.api.utils.ResponseValidator;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthTests extends BaseTest {

    @Test(groups = {"smoke", "auth"}, description = "TC_TOKEN_01: Valid credentials should return a bearer token")
    public void testVerifyValidToken() {
        String token = generateAuthToken("qa_admin", "Admin@123");
        Assert.assertNotNull(token, "Expected auth token to be generated");
        Assert.assertFalse(token.isEmpty(), "Expected non-empty token");
    }

    @Test(dataProvider = "invalidCredentials", groups = {"negative", "auth"}, description = "TC_TOKEN_02 / TC_TOKEN_03: Invalid login credentials should return a handled error response")
    public void testVerifyInvalidCredentials(String username, String password) {
        ValidatableResponse response = given()
                .spec(defaultSpec())
                .body(PayloadManager.createAuthPayload(username, password))
                .when()
                .post(APIConstants.AUTH_LOGIN)
                .then();

        ResponseValidator.verifyStatusCode(response, 200);
        String reason = response.extract().path("reason");
        Assert.assertNotNull(reason, "Expected error reason for invalid credentials");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"invalid_user", "Admin@123"},
                {"qa_admin", "WrongPassword"}
        };
    }

    @Test(groups = {"negative", "auth"}, description = "TC_TOKEN_04: Empty credentials should return a bad request response")
    public void testVerifyEmptyCredentials() {
        ValidatableResponse response = given()
                .spec(defaultSpec())
                .body(PayloadManager.createAuthPayload("", ""))
                .when()
                .post(APIConstants.AUTH_LOGIN)
                .then();

        ResponseValidator.verifyStatusCode(response, 400);
    }

    @Test(groups = {"negative", "auth"}, description = "TC_TOKEN_05: Missing authorization header should be rejected when accessing a protected endpoint")
    public void testVerifyMissingAuthHeader() {
        ValidatableResponse response = given()
                .spec(defaultSpec())
                .body(PayloadManager.createEmployeePayload(501, "Test", "User", "test.user@test.com", "9999999999", "QA", "Tester", 60000))
                .when()
                .post(APIConstants.EMPLOYEES)
                .then();

        ResponseValidator.verifyStatusCode(response, 401);
    }
}
