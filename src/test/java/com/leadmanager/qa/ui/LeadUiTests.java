package com.leadmanager.qa.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class LeadUiTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * TEST CASE: TC-UI-01
     * Scenario: Successful Login (Positive)
     */
    @Test(priority = 1, description = "TC-UI-01: Verify successful login with valid admin credentials")
    public void testLoginPositive() {
        System.out.println("Executing TC-UI-01: Positive Login Test");
        driver.get("https://v0-lead-manager-app.vercel.app");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("admin@company.com");
        driver.findElement(By.name("password")).sendKeys("Admin@123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed: Dashboard not reached.");
    }

    /**
     * TEST CASE: TC-UI-03 & TC-UI-05
     * Scenario: Create New Lead & Verify in List
     */
    @Test(priority = 2, dependsOnMethods = "testLoginPositive", 
          description = "TC-UI-03/05: Verify creating a lead and its appearance in the list")
    public void testCreateAndVerifyLead() {
        System.out.println("Executing TC-UI-03/05: Create and List Lead Test");
        String uniqueName = "QA Lead " + System.currentTimeMillis();

        // Step 1: Trigger Add Lead Modal (TC-UI-03)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-lead-btn"))).click();

        // Step 2: Fill mandatory fields
        driver.findElement(By.name("name")).sendKeys(uniqueName);
        driver.findElement(By.name("email")).sendKeys("qa@test.com");
        driver.findElement(By.id("save-btn")).click();

        // Step 3: Verify in list (TC-UI-05)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal")));
        boolean isLeadVisible = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("leads-table"), uniqueName));

        Assert.assertTrue(isLeadVisible, "TC-UI-05 Failed: Created lead is not visible in the table.");
    }

    /**
     * TEST CASE: TC-UI-04
     * Scenario: Field Validation (Edge Case)
     */
    @Test(priority = 3, description = "TC-UI-04: Verify mandatory field validation for Lead Name")
    public void testMandatoryFieldValidation() {
        System.out.println("Executing TC-UI-04: Mandatory Field Validation");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-lead-btn"))).click();
        // Leaving Name empty and clicking save
        driver.findElement(By.id("save-btn")).click();

        String errorText = driver.findElement(By.id("name-error")).getText();
        Assert.assertEquals(errorText, "Name is required", "TC-UI-04 Failed: Validation message missing.");
        
        // Close modal for cleanup
        driver.findElement(By.id("cancel-btn")).click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
