package com.leadmanager.qa.api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LeadApiTests {

    private final String BASE_URL = "https://v0-lead-manager-app.vercel.app";
    
    // Static Tokens from Requirements
    private final String ADMIN_TOKEN = "Bearer_ADMIN_TEST_TOKEN_12345";
    private final String MANAGER_TOKEN = "Bearer_MANAGER_TEST_TOKEN_12345";
    private final String VIEWER_TOKEN = "Bearer_VIEWER_TEST_TOKEN_12345";

    /**
     * TEST CASE: TC-API-01
     * Scenario: Admin Lead Creation (Full Access)
     */
    @Test(priority = 1, description = "TC-API-01: Verify Admin can create a lead")
    public void testAdminCreateLead() {
        String payload = "{\"name\":\"Admin API Lead\", \"email\":\"admin_api@test.com\", \"priority\":\"High\"}";

        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post(BASE_URL + "/leads")
        .then()
            .statusCode(201)
            .body("name", equalTo("Admin API Lead"));
    }

    /**
     * TEST CASE: TC-API-02
     * Scenario: Manager Access Validation
     */
    @Test(priority = 2, description = "TC-API-02: Verify Manager can view leads but has restricted delete (if applicable)")
    public void testManagerViewLeads() {
        given()
            .header("Authorization", "Bearer " + MANAGER_TOKEN)
        .when()
            .get(BASE_URL + "/leads")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    /**
     * TEST CASE: TC-API-03
     * Scenario: Viewer RBAC Restriction (Negative)
     */
    @Test(priority = 3, description = "TC-API-03: Verify Viewer is forbidden from creating leads")
    public void testViewerCreateRestriction() {
        String payload = "{\"name\":\"Unauthorized Lead\", \"email\":\"viewer@test.com\"}";

        given()
            .header("Authorization", "Bearer " + VIEWER_TOKEN)
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post(BASE_URL + "/leads")
        .then()
            .statusCode(403); // Forbidden for Read-Only user
    }

    /**
     * TEST CASE: TC-API-04
     * Scenario: Unauthorized Access (Security)
     */
    @Test(priority = 4, description = "TC-API-04: Verify 401 Unauthorized when no token is provided")
    public void testUnauthorizedAccess() {
        given()
        .when()
            .get(BASE_URL + "/leads")
        .then()
            .statusCode(401);
    }

    /**
     * TEST CASE: TC-API-05
     * Scenario: Invalid Data Validation (Negative)
     */
    @Test(priority = 5, description = "TC-API-05: Verify 400 Bad Request for invalid email format")
    public void testInvalidDataValidation() {
        String invalidPayload = "{\"name\":\"Test\", \"email\":\"not-an-email\"}";

        given()
            .header("Authorization", "Bearer " + ADMIN_TOKEN)
            .contentType(ContentType.JSON)
            .body(invalidPayload)
        .when()
            .post(BASE_URL + "/leads")
        .then()
            .statusCode(400); 
    }
}
