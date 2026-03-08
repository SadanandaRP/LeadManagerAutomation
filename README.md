# LeadManagerAutomation
E2E UI & API Automation Framework for Lead Management. 
Tech Stack: Java, Selenium, Rest-Assured, TestNG, Maven.

Lead Manager QA Automation Project

Project Overview
    This project contains the automated test suite for the Lead Manager SaaS Application. It includes end-to-end (E2E) 
    UI testing using Selenium and API validation using Rest-Assured to ensure the integrity of the Lead management lifecycle.

Tools & Frameworks Used
    Language: Java 11
    UI Automation: Selenium WebDriver (v4.18.1)
    API Automation: Rest-Assured (v5.4.0)
    Test Runner: TestNG
    Build Tool: Maven
    Driver Management: WebDriverManager

Setup Instructions
    Clone the Repository:
        bash
        git clone <your-repo-link>
        cd lead-manager-automation

Install Dependencies:
    Ensure you have Maven installed. 
    Run:
        bash
        mvn install

Browser Setup:
    The project is configured for Chrome. Ensure you have Chrome browser installed.

How to Execute Tests

You can execute tests via the command line or your IDE:

1. Run all tests (UI & API)
   bash
   mvn clean test

2. Run specific test groups
   If you have tagged your tests (e.g., in testng.xml), you can run them specifically:
   bash

   mvn test -Dgroups=UI
   mvn test -Dgroups=API

Scenarios Covered

UI Automation
    Login → Create Lead → List Lead: Validates that a user can authenticate, successfully create a new lead via the UI form, and verify its presence in the dashboard.

API Testing
    Authentication: Validates JWT token generation via /api/login.
    Lead Creation: Validates POST /api/leads with valid payload and Bearer token.
    Lead Retrieval: Validates GET /api/leads ensures the list is fetchable and authenticated.

Project Deliverables
    Manual Test Cases: Located in /docs/Manual_Test_Cases.md
    Automation Code: Located in src/test/java/.
    Configuration: pom.xml for dependencies and testng.xml for suite execution.
