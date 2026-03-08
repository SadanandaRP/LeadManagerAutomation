Test Plan: Lead Manager SaaS Application

1. Introduction
     The purpose of this document is to define the testing strategy for the Lead Manager application.
   The goal is to verify the core user flow: Login → Create Lead → List Lead across both UI and API layers. 

3. Scope of Testing

    Functional Testing: Validating user authentication and lead management features.
    UI/UX Testing: Ensuring the dashboard and forms behave as expected.
    API Testing: Validating REST endpoints, authorization, and data integrity. 

4. Environment & Tools

    UI URL: https://v0-lead-manager-app.vercel.app
    API Base URL: https://v0-lead-manager-app.vercel.app
    Tools: Java, Selenium, Rest-Assured, TestNG, Maven.

Manual Test Cases (UI & API)

Part 1: UI Test Cases (Login → Create → List) 

Test ID: TC-UI-01	
Scenario: Successful Login (Positive)	
Precondition: User has valid credentials.	
Test Steps: 1. Navigate to UI URL.
            2. Enter admin@company.com.
            3. Enter Admin@123.
            4. Click Login.	
Expected Result: User is redirected to the Dashboard; "List of Leads" is visible.

Test ID: TC-UI-02	
Scenario: Invalid Login (Negative)	
Precondition: App is on Login page.	
Test Steps: 1. Enter wrong@email.com.
            2. Enter WrongPass.
            3. Click Login.
Expected Result: Error message "Invalid credentials" appears; user remains on Login page.

Test ID: TC-UI-03	
Scenario: Create New Lead (Positive)	
Precondition: User is logged in.	
Test Steps: 1. Click "Create Lead" button.
            2. Fill all fields (Name, Email, Priority).
            3. Click Save.
Expected Result: Success message appears; lead is saved successfully.

Test ID: TC-UI-04	
Scenario: Field Validation (Edge Case)	
Precondition: User is on "Create Lead" page.	
Test Steps: 1. Leave "Name" field empty.
            2. Fill other fields.
            3. Click Save.	
Expected Result: Form prevents submission; "Name is required" validation appears.

Test ID: TC-UI-05	
Scenario: Verify List Lead	
Precondition: Lead was created in TC-UI-03.	
Test Steps: 1. Navigate to Dashboard/List page.
            2. Search or scroll for the created lead.
Expected Result: The newly created lead name and details are correctly displayed in the list.


Part 2: API Test Cases

Test ID: TC-API-01	
Scenario: Generate Auth Token	
Method: POST	
Endpoint: /api/login	
Expected Result: Status 200 OK; JSON response contains a valid token.


Test ID: TC-API-02	
Scenario: Create Lead (Authorized)	
Method: POST	
Endpoint: /api/leads	
Expected Result: Status 201 Created; Lead object is returned in response.

Test ID: TC-API-03	
Scenario: Create Lead (Unauthorized)	
Method: POST	
Endpoint: /api/leads	
Expected Result: Status 401 Unauthorized if Bearer token is missing or invalid.

Test ID: TC-API-04	
Scenario: Get Leads (Pagination)	
Method: GET	
Endpoint: /api/leads	
Expected Result: Status 200 OK; Returns a list of leads with pagination metadata.

Test ID: TC-API-05	
Scenario: Invalid Input Handling	
Method: POST	
Endpoint: /api/leads	
Expected Result: Status 400 Bad Request when sending invalid email format in body.


4. Assumptions 
    The application uses JWT-based authentication for all protected routes.
    The "Dashboard" acts as the default "List Lead" view.
    Test data (admin credentials) provided in the assignment are active.
