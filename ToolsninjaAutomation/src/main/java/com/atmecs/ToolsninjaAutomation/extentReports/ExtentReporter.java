package com.atmecs.ToolsninjaAutomation.extentReports;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.atmecs.ToolsninjaAutomation.constants.FilePath;
import com.atmecs.ToolsninjaAutomation.testBase.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This the Extent report class is for generate the report for the project in
 * web application manner and give pie chart of project application with Test
 * failures with the screenshot.
 * 
 * @author saurabh chauhan
 */
public class ExtentReporter extends TestBase implements IReporter {
	public ExtentReports extentObject;
	public ExtentTest extentlogger;

	// public WebDriver driver;

	/**
	 * This startReport is method is used to load the configuration files and create
	 * the Extent.html file for Extent report.
	 */
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputdirectory) {
		extentObject = new ExtentReports(FilePath.EXTENT_REPORT_FILE, true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				try {
					buildTestNodes(context.getPassedTests(), LogStatus.PASS);
					buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
					buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		extentObject.flush();
		extentObject.close();
	}

	public void buildTestNodes(IResultMap tests, LogStatus status) throws Exception {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				extentlogger = extentObject.startTest(result.getMethod().getMethodName());

				extentlogger.setStartedTime(getTime(result.getStartMillis()));
				extentlogger.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					extentlogger.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase() + "ed";
				if (result.getThrowable() != null)
					message = result.getThrowable().getClass() + ": " + result.getThrowable().getMessage();
				extentlogger.log(status, message);
				for (String testMessage : Reporter.getOutput(result)) {
					extentlogger.log(LogStatus.INFO, testMessage);
				}
				if (status.toString().equalsIgnoreCase("FAIL")) {

					String screenshotPath = getScreenshot(driver, result.getName());
					extentlogger.log(LogStatus.FAIL, extentlogger.addScreenCapture(screenshotPath));
				}
				extentObject.endTest(extentlogger);
			}
		}
	}

	public Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * This getScreenshot method takes below parameters:
	 * 
	 * @param driver
	 * @param testname and return the screenshot image destination path as a String
	 *                 .
	 * @return
	 * @throws Exception
	 */
	public String getScreenshot(WebDriver driver, String testname) throws Exception {
//			Object currentClass = result.getInstance();
//			ITestContext context=result.getTestContext();
		if (driver == null) {
			System.out.println("DRIVER IS NULL");
		}

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = FilePath.FAILED_SCREENSHOT_FILE + testname + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
}
