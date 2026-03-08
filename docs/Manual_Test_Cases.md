Manual Test Cases: Lead Manager Application

Scope: Login → Create Lead → Dashboard List Verification
Environment: https://v0-lead-manager-app.vercel.app

1. Positive Scenarios (Happy Path)

TC-01: Successful Lead Creation Flow

    Preconditions: User has valid Admin credentials.
    Steps:
        Navigate to the Login page.
        Enter email admin@company.com and password Admin@123.
        Click "Login".
        Click on "Create Lead" button.
        Fill in all fields: Name ("Test Lead"), Email ("test@example.com"), Priority ("High"), Status ("New").
        Click "Submit".
        Navigate to the Dashboard/Leads List.
    Expected Result: User is redirected to the dashboard, and "Test Lead" appears at the top of the list with all details matching the input.

2. Negative Scenarios (Validation & Error Handling)

TC-02: Login with Invalid Credentials

    Steps:
        Enter an unregistered email or incorrect password.
        Click "Login".
    Expected Result: System displays an error message (e.g., "Invalid credentials") and remains on the login page.

TC-03: Create Lead with Missing Mandatory Fields

    Steps:
        On the "Create Lead" form, leave the "Name" field empty.
        Fill in other fields and click "Submit".
    Expected Result: Form validation triggers; a field-level error message appears stating "Name is required," and the lead is not created.

TC-04: API Authorization - Access Without Token

    Steps:
        Send a GET request to /api/leads without the Authorization header.
    Expected Result: System returns a 401 Unauthorized status code.

3. Edge & Validation Cases

TC-05: Lead List Pagination Verification

    Preconditions: System contains more than 10 leads (default page size).
    Steps:
        Log in and navigate to the Leads Dashboard.
        Scroll to the bottom and click the "Next" page button.
    Expected Result: The list updates to show the next set of leads correctly.

TC-06: Email Format Validation

    Steps:
        On the "Create Lead" form, enter an invalid email format (e.g., "testuser@invalid").
        Click "Submit".
    Expected Result: System prevents submission and displays an "Invalid Email Format" error.

4. Role-Based Access Control (RBAC)

TC-07: Viewer Role Restricted Actions

    Preconditions: User is logged in with the "Viewer" role.
    Steps:
        Attempt to click the "Delete" or "Edit" buttons on any lead.
    Expected Result: Buttons should be disabled in the UI, or clicking them should trigger a 403 Forbidden error/notification.
