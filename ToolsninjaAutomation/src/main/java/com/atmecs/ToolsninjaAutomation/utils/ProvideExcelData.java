package com.atmecs.ToolsninjaAutomation.utils;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Class provides the Excel cell data to the calling method
 */
public class ProvideExcelData {

	Object[][] object;
	ExcelFileOperation provider;

	// Constructor provide loading of the file
	// parameters filepath
	public ProvideExcelData(String filepath, int sheetIndex) {
		provider = new ExcelFileOperation(filepath, sheetIndex);
	}

	/**
	 * Method returns the object array containing the cell data
	 */
	public Object[][] provideExcelData() {

		Iterator<Row> rows = null;
		try {
			rows = provider.getRowsData();

			int noOfRows = provider.getNoOfRows();
			System.out.println(noOfRows);
			int noOfColumns = provider.getNoOfColumns();
			System.out.println(noOfColumns);
			object = new Object[noOfRows][noOfColumns];

		} catch (IOException e) {
			System.out.println("Exception");
		}
		int i = 0;
		rows.next();
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> iterator = row.cellIterator();
			int j = 0;
			while (iterator.hasNext()) {
				Cell cell = iterator.next();
				if (!cell.toString().equals(" "))

				{
					object[i][j] = cell.toString();
					// System.out.println(cell.toString());
				}
				j++;
			}
			i++;
		}
		return object;
	}
}
