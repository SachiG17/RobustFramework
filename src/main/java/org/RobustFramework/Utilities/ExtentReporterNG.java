package org.RobustFramework.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {


    public static ExtentReports  getReportObject(){
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        //ExtentSparkReporter expects path where report need to be placed
        //responsible to create a report and configuration
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Automation Test Results");

        //main class responsible for executing reporting execution
        ExtentReports extent = new ExtentReports();
        //ExtentSparkReporter is helper class for extentreports
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Sachin Shetty");
        return extent;
    }
}
