package com.atmecs.ToolsninjaAutomation.testScripts;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.ToolsninjaAutomation.constants.Locators;
import com.atmecs.ToolsninjaAutomation.constants.ValidatingData;
import com.atmecs.ToolsninjaAutomation.dataProvider.TestDataProvider;
import com.atmecs.ToolsninjaAutomation.logReports.LogReport;
import com.atmecs.ToolsninjaAutomation.pages.HomePage;
import com.atmecs.ToolsninjaAutomation.testBase.TestBase;
import com.atmecs.ToolsninjaAutomation.testflow.HomePageFlow;

/*
*Class validates the functionality of homepage functionality
*/
public class HomePagetestscripts extends TestBase {
	LogReport log = new LogReport();
	int i = 1;
	Locators locators = new Locators();
	HomePage home;
	ValidatingData data;
	HomePageFlow HomePageFlow;

	@BeforeClass
	public void getUrl() {
		String url = baseClass.getProperty("URL");
		driver.get(url);

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
	}

	/*
	 * Test validates the homepage redirection of the toolsninja.com
	 */
	@Test(priority = 1)
	public void homePageRedirection() {
		home = new HomePage(driver);
		data = new ValidatingData();
		HomePageFlow = new HomePageFlow(driver);
		LogReport.getlogger();
		// log = extent.startTest("HomepageRedirection");
		log.info("Starting Redirection validation");
		home.isRedirectionCorrect();
		log.info("Redirection is on the correct page");
		log.info("Starting the homepage testing");
	}

	// method validate the product availability
	@Test(priority = 2, dataProvider = "products", dataProviderClass = TestDataProvider.class)
	public void searchProductAndCheckAvailability(String productName, String price, String Quantity, String exTax)
			throws InterruptedException {
		HomePageFlow.searchProduct(productName);
		home.isProductAvailable(productName);
		HomePageFlow.AddProduct();
		log.info("product name is correct");
		if (productName.equalsIgnoreCase("iphone")) {
			home.isDescriptionCorrect("descriptioniphone");

		}

		if (productName.equalsIgnoreCase("macbook air")) {
			home.isDescriptionCorrect("descriptionmac");
		}

		home.isPriceCorrect(price, exTax);
		log.info("product price and taxes is correct");
		log.info("Validation done");

	}

	@Test(priority = 3, dataProvider = "products", dataProviderClass = TestDataProvider.class)
	public void validateGrandTotal(String productName, String price, String quantity, String exTax) {

		HomePageFlow.manipulateQuantity(quantity, i);

		i++;

	}

	@Test(priority = 4)
	public void checkGrandTotal() {
		home.isGrandTotalCorrect(data.getValidatingData("grandtotal1"));
	}

	@Test(priority = 5)
	public void validateGrandTotalAfterRemovingItem() {

		HomePageFlow.removeItem();
		home.isGrandTotalCorrect(data.getValidatingData("grandTotal2"));

	}

	@Test(priority = 6, dataProvider = "wrongProducts", dataProviderClass = TestDataProvider.class)
	public void validateerrormessage(String productName, String message) throws InterruptedException {

		HomePageFlow.searchProduct(productName);
		home.isMessageDisplayed(message);

	}
}
