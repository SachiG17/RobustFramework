package org.RobustFramework.Listeners;

import org.RobustFramework.DriverFactory.DriverFactory;
import org.RobustFramework.Utilities.ExtentReporterNG;
import org.RobustFramework.Utilities.ScreenshotUtils;

import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.IOException;

public class Listeners implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }
    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().fail(result.getThrowable());

        WebDriver driver = null;

        try {
            driver = DriverFactory.getDriver();
        } catch (Exception e) {
            extentTest.get().log(Status.WARNING, "Driver not initialized, skipping screenshot");
            return;   // 🚨 IMPORTANT: stop here
        }

        try {
            String filePath = ScreenshotUtils.capture(
                    driver,
                    result.getMethod().getMethodName()
            );
            extentTest.get().addScreenCaptureFromPath(filePath);
        } catch (IOException e) {
            extentTest.get().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        }
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}