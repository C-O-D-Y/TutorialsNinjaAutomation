package com.atmecs.ToolsninjaAutomation.testflow;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.WebDriver;

import com.atmecs.ToolsninjaAutomation.constants.Locators;
import com.atmecs.ToolsninjaAutomation.helpers.WebUtility;

/**
 * class used to maintain the flow of the homepage of the tutorialsninja.com
 */
public class HomePageFlow {

	WebDriver driver;
	Locators locaters = new Locators();
	WebUtility WebUtility;

	public HomePageFlow(WebDriver driver) {
		this.driver = driver;
		WebUtility = new WebUtility(driver);
	}

	/**
	 * Method used to search the products to maintain the flow
	 */
	public void searchProduct(String productName) throws InterruptedException {
		if (productName == null) {
		}
		BasicConfigurator.configure();
		WebUtility.clearField(Locators.getLocators("loc.text.searchButton"));
		WebUtility.clickAndSendText(Locators.getLocators("loc.text.searchButton"), productName);
		WebUtility.clickElement(Locators.getLocators("loc.btn.searchButton"));
		WebUtility.explicitWait(Locators.getLocators("loc.text.productNameAfterSearch"));
	}

	/**
	 * Method used to add the products to maintain the flow
	 * 
	 * @throws InterruptedException
	 */
	public void AddProduct() throws InterruptedException {
		Thread.sleep(1000);
		WebUtility.scrollBySpacebar();
		WebUtility.clickElement(Locators.getLocators("loc.btn.addToCart"));
	}

	/**
	 * Method used to manipulate the products quantity to maintain the flow
	 */
	public void manipulateQuantity(String quantity, int index) {
		WebUtility.clickElement(Locators.getLocators("loc.btn.goToCart"));
		WebUtility.explicitWait(Locators.getLocators("loc.text.productquantity"));
		WebUtility.clickAndSendText(Locators.getLocators("loc.text.productquantity").replace("dummytext", index + ""),
				quantity);
		WebUtility.clickElement(Locators.getLocators("loc.btn.update"));
		WebUtility.explicitWait(Locators.getLocators("loc.text.productquantity"));
	}

	/**
	 * Method used to remove the products to maintain the flow
	 */
	public void removeItem() {
		WebUtility.clickElement(Locators.getLocators("loc.btn.remove"));
		WebUtility.explicitWait(Locators.getLocators("loc.text.productquantity"));
	}
}
