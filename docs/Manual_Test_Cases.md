Project Test Plan: Lead Manager SaaS Application

1. Introduction
This document defines the strategy for validating the Lead Manager application. The focus is on ensuring secure authentication, role-specific data access, and the end-to-end lifecycle of Lead management (Create → View → Delete).

2. Test Environment

    Application URL: https://v0-lead-manager-app.vercel.app
    API Base URL: https://v0-lead-manager-app.vercel.app
    Tools: Java 11, Selenium 4.30, Rest-Assured 5.5, TestNG, Maven.

3. Role-Based Access Control (RBAC) Matrix
The application supports three tiers of access which must be enforced at both the UI and API layers:
Role	Permissions	Validation Goal
Admin	Full CRUD + Export	Verify absolute authority and data visibility.
Manager	Create, View, Edit	Verify "Delete" functionality is restricted/hidden.
Viewer	View Only	Verify 403 Forbidden for POST/PUT/DELETE actions.

4. Manual Test Cases (Traceability Matrix)
Section A: Authentication (UI & API)
ID	Scenario	Input Type	Expected Result
TC-SEC-01	Multi-Role Login	Admin/Manager/Viewer	Success; JWT Token stored in session.
TC-NEG-01	Invalid Password	Valid Email + wrongpass	UI Error: "Invalid credentials".
TC-NEG-02	Invalid Email	unknown@test.com	UI Error: "User not found".
TC-NEG-03	Format Validation	notanemail	UI Error: "Invalid email format".
TC-NEG-04	Security Check	No Token (API)	API Response: 401 Unauthorized.
Section B: Lead Management (Functional)
ID	Scenario	User Role	Expected Result
TC-FUNC-01	Create Lead (UI)	Admin/Manager	Modal closes; Lead appears in Dashboard table.
TC-FUNC-02	Mandatory Fields	Admin	"Name" validation triggers if field is empty.
TC-FUNC-03	View Leads (API)	Viewer	API Response: 200 OK with JSON Lead list.
TC-FUNC-04	Restricted Action	Viewer	API Response: 403 Forbidden on POST /leads.

5. Automation Strategy

    UI Layer: Page Object Model (POM) used to decouple locators from test logic. WebDriverWait implemented to handle asynchronous React components.
    API Layer: Rest-Assured used for functional validation. Static Bearer tokens used to simulate specific user sessions.
    Data-Driven: TestNG @DataProvider used for Negative Login scenarios to minimize code duplication.
    Reporting: ExtentReportManager listener provides a visual HTML dashboard with pass/fail pie charts.

6. Exit Criteria
7. 
    100% of Critical/High priority test cases passed.
    Zero 500-level (Internal Server) errors encountered during API execution.
    Automation suite runs successfully in a "clean-room" environment via mvn clean test.
