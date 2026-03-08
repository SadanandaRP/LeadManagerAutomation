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

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://v0-lead-manager-app.vercel.app");
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    /**
     * SCENARIO: TC-UI-02 (Negative Login Scenarios)
     * Covers: Wrong Password, Unregistered Email, Invalid Format, Empty Fields
     */
    @DataProvider(name = "negativeLoginData")
    public Object[][] getNegativeData() {
        return new Object[][] {
            {"admin@company.com", "wrongpass", "Invalid credentials"},  // Wrong Password
            {"unknown@test.com", "password123", "User not found"},      // Unregistered Email
            {"notanemail", "password123", "Invalid email format"},     // Invalid Format
            {"", "", "Fields cannot be empty"}                         // Empty Fields
        };
    }

    @Test(dataProvider = "negativeLoginData", priority = 1, description = "TC-UI-02: Negative Login Validation")
    public void testNegativeLoginScenarios(String email, String pass, String expectedError) {
        loginPage.login(email, pass);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }

    @Test(priority = 2, description = "TC-UI-01: Positive Login - ADMIN Role (Full Access)")
    public void testAdminLogin() {
        loginPage.login("admin@company.com", "Admin@123");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Admin login failed!");
    }

    @Test(priority = 3, description = "TC-UI-03/05: Create & List Lead - MANAGER Role (Limited Access)")
    public void testManagerCreateLead() {
        loginPage.login("qa@company.com", "password123");
        String name = "Manager Lead " + System.currentTimeMillis();
        dashboardPage.createLead(name, "manager@test.com");
        Assert.assertTrue(dashboardPage.verifyLeadInTable(name), "Lead not visible in table!");
    }

    @Test(priority = 4, description = "TC-UI-08: RBAC Restriction - VIEWER Role (Read-Only)")
    public void testViewerRestriction() {
        loginPage.login("tester@company.com", "Test@456");
        // Verify 'Add Lead' button is hidden for Viewers (No Create Access)
        Assert.assertFalse(dashboardPage.isAddLeadButtonVisible(), "Security Violation: Viewer should not see Add Lead button");
    }

    @Test(priority = 5, description = "TC-UI-07: Form Mandatory Field Validation")
    public void testFormValidation() {
        loginPage.login("admin@company.com", "Admin@123");
        dashboardPage.clickAddLead();
        dashboardPage.clickSave(); // Empty submit
        Assert.assertEquals(dashboardPage.getNameErrorMessage(), "Name is required");
    }

    @AfterMethod
    public void tearDown() { if (driver != null) driver.quit(); }
}
