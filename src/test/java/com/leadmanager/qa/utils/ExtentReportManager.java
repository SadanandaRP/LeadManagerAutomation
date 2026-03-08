package com.leadmanager.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {
    private static ExtentReports extent;
    private static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Lead Manager Automation Report");
        sparkReporter.config().setReportName("UI & API Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA/SaaS");
        extent.setSystemInfo("Tester", "QA Lead Candidate");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getMethod().getDescription());
        test.log(Status.PASS, "Test Case Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getMethod().getDescription());
        test.log(Status.FAIL, "Test Case Failed: " + result.getName());
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
