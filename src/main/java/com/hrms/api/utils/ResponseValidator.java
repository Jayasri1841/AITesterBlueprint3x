package com.hrms.api.utils;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class ResponseValidator {

    private ResponseValidator() {
        // Utility class
    }

    public static void verifyStatusCode(ValidatableResponse response, int expectedStatus) {
        response.statusCode(expectedStatus);
    }

    public static void verifyStringKey(ValidatableResponse response, String key, String expectedValue) {
        response.assertThat().body(key, org.hamcrest.Matchers.equalTo(expectedValue));
    }

    public static void verifyJsonSchema(ValidatableResponse response, String schemaPath) {
        response.assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
    }

    public static void verifyNonNullKey(ValidatableResponse response, String key) {
        Object value = response.extract().path(key);
        Assert.assertNotNull(value, "Expected key to be present: " + key);
    }
}
