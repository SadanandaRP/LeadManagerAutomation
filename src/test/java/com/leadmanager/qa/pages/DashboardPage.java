package com.leadmanager.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By addLeadButton = By.id("add-lead-btn");
    private By nameField = By.name("name");
    private By emailField = By.name("email");
    private By priorityDropdown = By.name("priority");
    private By saveButton = By.id("save-btn");
    private By cancelButton = By.id("cancel-btn");
    private By leadsTable = By.className("leads-table");
    private By nameValidationError = By.id("name-error");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddLead() {
        wait.until(ExpectedConditions.elementToBeClickable(addLeadButton)).click();
    }

    public void fillLeadDetails(String name, String email, String priority) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        // Select priority logic can be added here if it's a standard select
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    /**
     * RBAC Check: Verifies if the 'Add Lead' button is visible for the role
     */
    public boolean isAddLeadButtonVisible() {
        try {
            return driver.findElement(addLeadButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getNameErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameValidationError)).getText();
    }

    /**
     * Verifies lead presence in the list after creation
     */
    public boolean verifyLeadInTable(String leadName) {
        // Wait for modal to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal")));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(leadsTable, leadName));
    }

    public void createLead(String name, String email) {
        clickAddLead();
        fillLeadDetails(name, email, "High");
        clickSave();
    }
}
