package com.leadmanager.qa.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LeadApiTests {

    private String authToken;
    private final String BASE_URL = "https://v0-lead-manager-app.vercel.app";

    @BeforeClass
    public void setupAuth() {
        // 1. Authenticate and extract JWT Token
        String loginPayload = "{" +
                "\"email\": \"admin@company.com\"," +
                "\"password\": \"Admin@123\"" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .post(BASE_URL + "/login");

        Assert.assertEquals(response.getStatusCode(), 200, "Login failed!");
        authToken = response.jsonPath().getString("token");
    }

    @Test(priority = 1)
    public void testCreateLead() {
        // 2. Create a new lead using the token
        String leadPayload = "{" +
                "\"name\": \"Automation API Lead\"," +
                "\"email\": \"api_test@example.com\"," +
                "\"priority\": \"High\"," +
                "\"status\": \"New\"" +
                "}";

        given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .body(leadPayload)
        .when()
                .post(BASE_URL + "/leads")
        .then()
                .statusCode(201)
                .body("name", equalTo("Automation API Lead"))
                .body("email", equalTo("api_test@example.com"));
    }

    @Test(priority = 2)
    public void testGetAllLeads() {
        // 3. Retrieve all leads and verify the list is not empty
        given()
                .header("Authorization", "Bearer " + authToken)
        .when()
                .get(BASE_URL + "/leads")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }

    @Test(priority = 3)
    public void testUnauthorizedAccess() {
        // 4. Negative Test: Attempt to fetch leads without a token
        given()
        .when()
                .get(BASE_URL + "/leads")
        .then()
                .statusCode(401);
    }
}
