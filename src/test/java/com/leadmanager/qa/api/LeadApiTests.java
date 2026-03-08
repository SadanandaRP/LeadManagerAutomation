package com.leadmanager.qa.api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LeadApiTests {

    private String authToken;
    private final String BASE_URL = "https://v0-lead-manager-app.vercel.app";

    /**
     * TEST CASE: TC-API-01
     * Scenario: Generate Auth Token (Positive)
     * Logic: Replaces the 'Login Page' UI action with a direct API call.
     */
    @BeforeClass(description = "TC-API-01: Authenticate and retrieve Bearer Token")
    public void setupAuth() {
        System.out.println("Executing TC-API-01: API Authentication");
        
        String loginPayload = "{\"email\": \"admin@company.com\", \"password\": \"Admin@123\"}";

        authToken = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .post(BASE_URL + "/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        Assert.assertNotNull(authToken, "API Auth Failed: Token is null");
    }

    /**
     * TEST CASE: TC-API-02
     * Scenario: Create Lead (Authorized)
     */
    @Test(priority = 1, description = "TC-API-02: Verify Lead creation with valid token")
    public void testCreateLead() {
        System.out.println("Executing TC-API-02: POST /api/leads");
        
        String leadPayload = "{" +
                "\"name\": \"API Automation Lead\"," +
                "\"email\": \"api_test@example.com\"," +
                "\"priority\": \"High\"" +
                "}";

        given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .body(leadPayload)
        .when()
                .post(BASE_URL + "/leads")
        .then()
                .statusCode(201)
                .body("name", equalTo("API Automation Lead"))
                .body("email", equalTo("api_test@example.com"));
    }

    /**
     * TEST CASE: TC-API-04
     * Scenario: Get Leads (Retrieve List)
     */
    @Test(priority = 2, description = "TC-API-04: Verify retrieval of lead list")
    public void testGetAllLeads() {
        System.out.println("Executing TC-API-04: GET /api/leads");
        
        given()
                .header("Authorization", "Bearer " + authToken)
        .when()
                .get(BASE_URL + "/leads")
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("name", hasItem("API Automation Lead"));
    }

    /**
     * TEST CASE: TC-API-03
     * Scenario: Create Lead (Unauthorized - Negative)
     */
    @Test(priority = 3, description = "TC-API-03: Verify 401 Unauthorized when token is missing")
    public void testUnauthorizedAccess() {
        System.out.println("Executing TC-API-03: Negative Security Test");
        
        given()
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(BASE_URL + "/leads")
        .then()
                .statusCode(401);
    }
}
