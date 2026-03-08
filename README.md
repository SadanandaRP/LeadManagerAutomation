 Lead Manager E2E Automation Framework 

Project Overview
A professional-grade Test Automation Framework designed to validate the Lead Manager SaaS Application. This project demonstrates advanced QA engineering practices, including UI/API integration, Role-Based Access Control (RBAC) validation, and Data-Driven Testing.

Tech Stack

    Language: Java 11
    UI Engine: Selenium WebDriver (v4.18.1)
    API Engine: Rest-Assured (v5.4.0)
    Test Runner: TestNG (with Data-Driven @DataProvider)
    Design Pattern: Page Object Model (POM)
    Reporting: ExtentReports (Interactive HTML Dashboard)
    Build Tool: Maven 

User Role & Coverage Matrix

The suite validates three distinct user tiers, ensuring that permissions are strictly enforced at both the UI and API levels:

    Role	     Access Level	     UI/API Scenarios Validated
    Admin	    Full Access	      Login, Create, View, Edit, Export, Delete
    Manager	  Limited	          Login, Create, View, Edit, Export (No Delete)
    Viewer	   Read-Only	        Login, View Only (403 Forbidden on Create/Delete)

Automated Scenarios

UI Tests (LeadUiTests.java)

    TC-UI-01: Positive Login flow for Admin, Manager, and Viewer roles.
    TC-UI-02: Data-driven Negative Login (Wrong Password, Unregistered Email, Invalid Format, Empty Fields).
    TC-UI-03/05: Full Lead Creation lifecycle and verification in the Leads Table.
    TC-UI-04: Mandatory Field Validation (ensuring error messages trigger on empty forms).

API Tests (LeadApiTests.java)

    TC-API-01: JWT Token generation and payload validation.
    TC-API-02/04: Endpoint authorization for POST and GET operations.
    TC-API-03: Security validation for 401 Unauthorized (missing tokens) and 403 Forbidden (role-based violations).

Quick Start

    Clone the Repository:
        bash
        git clone https://github.com
        cd LeadManagerAutomation

    Install Dependencies:
        bash
        mvn install

    Execute All Tests:
        bash
        mvn clean test

     
Viewing Test Reports

After execution, a professional HTML dashboard is generated:

    Report Path: test-output/ExtentReport.html
    Key Features: Visual pie charts, execution timing, and detailed failure stack traces mapped to Manual Test Case IDs.

QA Lead Perspective

    This framework is CI/CD ready. By utilizing WebDriverManager and parameterized Maven properties, it is built to be integrated directly into GitHub Actions or Jenkins with zero manual setup.
