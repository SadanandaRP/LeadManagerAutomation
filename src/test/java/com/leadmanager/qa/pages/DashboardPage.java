package com.leadmanager.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Locators
    private By addLeadButton = By.id("add-lead-btn");
    private By nameField = By.name("name");
    private By emailField = By.name("email");
    private By saveButton = By.id("save-btn");
    private By cancelButton = By.id("cancel-btn");
    private By leadsTable = By.className("leads-table");
    private By nameError = By.id("name-error");

    // 2. Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 3. Actions
    public void clickAddLead() {
        wait.until(ExpectedConditions.elementToBeClickable(addLeadButton)).click();
    }

    public void fillLeadForm(String name, String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public String getNameValidationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).getText();
    }

    /**
     * Verifies if a lead exists in the table
     */
    public boolean isLeadPresent(String leadName) {
        // Wait for modal to disappear first to ensure table is refreshed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal")));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(leadsTable, leadName));
    }

    public void createLead(String name, String email) {
        clickAddLead();
        fillLeadForm(name, email);
        clickSave();
    }
}
