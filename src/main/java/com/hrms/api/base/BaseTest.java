package com.hrms.api.base;

import com.hrms.api.constants.APIConstants;
import com.hrms.api.utils.ConfigReader;
import com.hrms.api.utils.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeSuite;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void initialize() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
    }

    protected RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    protected RequestSpecification authorizedSpec(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    protected String generateAuthToken(String username, String password) {
        Map<String, Object> authPayload = PayloadManager.createAuthPayload(username, password);

        return given()
                .spec(defaultSpec())
                .body(authPayload)
                .when()
                .post(APIConstants.AUTH_LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}
