package com.atmecs.ToolsninjaAutomation.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.atmecs.ToolsninjaAutomation.constants.Locators;
import com.atmecs.ToolsninjaAutomation.constants.ValidatingData;
import com.atmecs.ToolsninjaAutomation.helpers.WebUtility;

//in this class, validation of different functionality is validated of the homepage of tutorialsninja.com of product functionality

public class HomePage {

	WebDriver driver;
	Locators loc = new Locators();
	ValidatingData data;
	WebUtility WebUtility;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		WebUtility = new WebUtility(driver);
		data = new ValidatingData();
	}

	/**
	 * In this method i'm validating the functionality of redirecting the page is
	 * correct.
	 * 
	 * @param driver
	 */
	public void isRedirectionCorrect() {

		String title = WebUtility.getTitle();
		Assert.assertEquals(title, data.getValidatingData("homepage_Title"));
		System.out.println("Redirection is on the correct page");
	}

	/**
	 * In this method i'm validating description is present or not
	 * 
	 * @param product name
	 */

	public void isDescriptionCorrect(String description) {
		System.out.println(description);
		String text = WebUtility.getText(Locators.getLocators("loc.text.description"));
		System.out.println(text);
		Assert.assertTrue(text.contains(data.getValidatingData(description)), "Description is not present");

	}

	/**
	 * In this method i'm validating product is available or not
	 * 
	 * @param product name
	 */

	public void isProductAvailable(String productName) {

		String text = WebUtility.getText(Locators.getLocators("loc.text.productNameAfterSearch"));
		System.out.println(text);
		Assert.assertEquals(text, productName, "product is not present");

	}

	/**
	 * In this method i'm error message is available or not
	 * 
	 * @param message
	 */
	public void isMessageDisplayed(String message) {

		String text = WebUtility.getText(Locators.getLocators("loc.text.message"));
		Assert.assertEquals(text, message, "message is not displayed");

	}

	/**
	 * In this method i'm validating price is correct or not
	 * 
	 * @param message
	 */
	public void isPriceCorrect(String price, String exTax) {
		WebUtility.clickElement("css;//a[text()='iPhone']");
		String pricing = WebUtility.getText(Locators.getLocators("loc.text.productPrice"));
		System.out.println(pricing + "and" + price);
		Assert.assertEquals(pricing.replace("$", ""), "" + price, "price is not correct");
		String taxes = WebUtility.getText(Locators.getLocators("loc.text.exTax"));
		System.out.println(taxes);
		Assert.assertEquals(taxes.split(":")[1].replace("$", ""), "" + exTax, "price is not correct");

	}

	/**
	 * In this method i'm validating grandprice is correct or not
	 * 
	 * @param grandtotal
	 */
	public void isGrandTotalCorrect(String grandtotal) {
		String pricing = WebUtility.getText(Locators.getLocators("loc.text.grandtotal"));
		Assert.assertEquals(pricing.replace("$", ""), "" + grandtotal, "grandtotal is not correct");
	}
}
