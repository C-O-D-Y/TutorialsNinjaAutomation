package com.atmecs.ToolsninjaAutomation.dataProvider;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.atmecs.ToolsninjaAutomation.constants.FilePath;
import com.atmecs.ToolsninjaAutomation.utils.ProvideExcelData;

/**
 * In this class, data is given from the dataprovider
 */
public class TestDataProvider {

	/**
	 * In this method, getting the data of the headers into object array and
	 * returning to the calling method
	 */

	@DataProvider(name = "products")
	public Object[][] getHeader() {
		ProvideExcelData provideData = new ProvideExcelData(FilePath.TESTDATA_FILE, 0);
		Object[][] getData = provideData.provideExcelData();
		return getData;
	}

	/**
	 * In this method, getting the data inside the header(SERVICES) into object
	 * array and returning to the calling method
	 */
	@DataProvider(name = "wrongProducts")
	public Object[][] getOption1() {
		ProvideExcelData provideData = new ProvideExcelData(FilePath.TESTDATA_FILE, 1);
		Object[][] getData = provideData.provideExcelData();
		return getData;
	}
}