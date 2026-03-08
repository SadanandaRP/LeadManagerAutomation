Detailed Test Plan: Lead Manager SaaS

1. Project Overview

       This test plan outlines the strategy for validating the Lead Manager application. The focus is on ensuring a seamless UI/UX
       experience and secure REST API endpoints, specifically enforcing Role-Based Access Control (RBAC) across Admin, Manager, and Viewer tiers.

3. Test Objectives

       Verify successful authentication for all authorized user roles.
       Validate robust error handling for negative login scenarios.
       Ensure the integrity of the Lead Lifecycle (Create → List → Secure Access).
       Confirm that permission restrictions (RBAC) are strictly enforced at the API level.

4. Scope of Testing

       UI Testing (Selenium)

       Authentication: Multi-role login and error message validation.
       Functional: Lead creation through modals and table data persistence.
       Validations: Client-side mandatory field checks.

       API Testing (RestAssured)

       Security: JWT Token generation and unauthorized access prevention (401/403).
       Functional: CRUD operations on /api/leads and JSON schema validation.

4. User Role & Permission Matrix
   
        Role      Email                 Password       Permissions
        Admin     admin@company.com     Admin@123      Full Access (Create, View, Edit, Delete, Export)
        Manager   qa@company.com        password123    Limited (Create, View, Edit, Export - No Delete)
        Viewer    tester@company.com    Test@456       Read-only (No Create/Edit/Delete/Export)
 
5. Manual Test Cases (Traceability Matrix)

       A. UI Authentication & Negative Scenarios

        ID          Scenario                   Input Data                        Expected Result
        TC-UI-01    Valid Multi-Role Login     Admin / Manager / Viewer          Successful redirect to Dashboard.
        TC-UI-02    Wrong Password             admin@company.com / wrongpass     Error: "Invalid credentials".
        TC-UI-03    Unregistered Email         unknown@test.com / pass123        Error: "User not found".
        TC-UI-04    Invalid Email Format       notanemail / pass123              Error: "Invalid email format".
        TC-UI-05    Empty Fields               (empty) / (empty)                 Error: "Fields cannot be empty".

       B. Lead Management (Functional UI)

        ID           Scenario                    User Role          Expected Result
        TC-UI-06     Create New Lead             Admin / Manager    Lead saved; modal closes; name appears in table.
        TC-UI-07     Mandatory Field Validation  Admin              Error: "Name is required" triggers on empty save.
        TC-UI-08     Read-Only UI Restriction    Viewer             "Add Lead" and "Delete" buttons are hidden/disabled.

       C. REST API Endpoints (Security & Data)

        ID           Scenario                  Method            Expected Result
        TC-API-01    Token Generation          POST /login       Status 200 OK + JWT Token.
        TC-API-02    Authorized Lead Creation  POST /leads       Status 201 Created (Admin/Manager).
        TC-API-03    Unauthorized Creation     POST /leads       Status 403 Forbidden (Viewer).
        TC-API-04    Missing Token Access      GET /leads        Status 401 Unauthorized.
        TC-API-05    Data Retrieval            GET /leads        Status 200 OK + JSON Array of Leads.

6. Automation Strategy

    Design Pattern: Page Object Model (POM) to isolate UI locators from test logic.
    Framework: TestNG using @DataProvider for efficient Negative Login testing.
    Wait Strategy: Explicit Waits used to handle asynchronous React components.
    Reporting: ExtentReports providing a visual HTML dashboard with pass/fail metrics.

7. Execution Instructions

    Navigate to root: cd LeadManagerAutomation
    Run: mvn clean test
    Report: Open test-output/ExtentReport.html in any browser.
