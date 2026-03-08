package com.leadmanager.qa.ui;

import com.leadmanager.qa.pages.DashboardPage;
import com.leadmanager.qa.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LeadUiTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private final String APP_URL = "https://v0-lead-manager-app.vercel.app";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Initialize Page Objects
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    /**
     * TEST CASE: TC-UI-02
     * Scenario: Invalid Login (Negative)
     */
    @Test(priority = 1, description = "TC-UI-02: Verify error message with invalid credentials")
    public void testLoginNegative() {
        System.out.println("Executing TC-UI-02: Negative Login Test");
        driver.get(APP_URL);
        
        loginPage.login("wrong@user.com", "WrongPass123");
        
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "Invalid credentials", "TC-UI-02 Failed: Error message mismatch.");
        
        // Clear fields for the next test
        driver.navigate().refresh();
    }

    /**
     * TEST CASE: TC-UI-01
     * Scenario: Successful Login (Positive)
     */
    @Test(priority = 2, description = "TC-UI-01: Verify successful login with valid admin credentials")
    public void testLoginPositive() {
        System.out.println("Executing TC-UI-01: Positive Login Test");
        driver.get(APP_URL);

        loginPage.login("admin@company.com", "Admin@123");

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "TC-UI-01 Failed: Dashboard not reached.");
    }

    /**
     * TEST CASE: TC-UI-04
     * Scenario: Mandatory Field Validation (Edge Case)
     */
    @Test(priority = 3, dependsOnMethods = "testLoginPositive", 
          description = "TC-UI-04: Verify mandatory field validation for Lead Name")
    public void testMandatoryFieldValidation() {
        System.out.println("Executing TC-UI-04: Mandatory Field Validation");
        
        dashboardPage.clickAddLead();
        dashboardPage.clickSave(); // Submit without data

        String error = dashboardPage.getNameValidationMessage();
        Assert.assertEquals(error, "Name is required", "TC-UI-04 Failed: Validation message missing.");
        
        dashboardPage.clickCancel(); // Close modal to clean up UI
    }

    /**
     * TEST CASE: TC-UI-03 & TC-UI-05
     * Scenario: Create New Lead & Verify in List (Lifecycle)
     */
    @Test(priority = 4, dependsOnMethods = "testLoginPositive", 
          description = "TC-UI-03/05: Verify creating a lead and its appearance in the list")
    public void testCreateAndVerifyLead() {
        System.out.println("Executing TC-UI-03/05: Create and List Lead Test");
        String uniqueName = "QA Lead " + System.currentTimeMillis();

        // TC-UI-03: Create the Lead
        dashboardPage.createLead(uniqueName, "qa@test.com");

        // TC-UI-05: Verify in List
        boolean isFound = dashboardPage.isLeadPresent(uniqueName);
        Assert.assertTrue(isFound, "TC-UI-05 Failed: Created lead not visible in the table.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
