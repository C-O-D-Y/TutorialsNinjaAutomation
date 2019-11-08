package com.atmecs.ToolsninjaAutomation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.atmecs.ToolsninjaAutomation.constants.NullCellValueException;

/*
 * In this Class i'm reading excel file using maven
 */
public class ExcelFileOperation {
	static FileInputStream fis = null;
	static XSSFWorkbook workbook = null;
	static XSSFSheet worksheet = null;

	public ExcelFileOperation(String filepath, int sheetIndex) {
		try {
			fis = new FileInputStream(new File(filepath));
		} catch (FileNotFoundException e) {
			System.out.println("Sorry! File not Found.");
			e.printStackTrace();
		}
		// Class used to read excel file and read the data
		try {
			workbook = new XSSFWorkbook(fis);

		} catch (IOException e) {
			System.out.println("File path not found");
			e.printStackTrace();
		}
		worksheet = workbook.getSheetAt(sheetIndex);
	}

	/*
	 * In this method i'm reading excel file using dependency of apache-poi
	 */

	public Iterator<Row> getRowsData() throws IOException {

		// iterating through rows and getting row number
		Iterator<Row> rows = worksheet.iterator();
		return rows;
	}

	// getting the no of rows in the sheet
	public int getNoOfRows() {
		int rowIndex = worksheet.getLastRowNum();

		return rowIndex + 1;
	}

	// getting no of columns in the sheet
	public int getNoOfColumns() {
		Iterator<Row> rowIterator = worksheet.rowIterator();
		int columnIndex = 0;
		/**
		 * Escape the header row *
		 */
		if (rowIterator.hasNext()) {
			Row headerRow = rowIterator.next();
			// get the number of cells in the header row
			columnIndex = headerRow.getPhysicalNumberOfCells();
		}
		return columnIndex;
	}

	/**
	 * Method returns the cell data on the basis of sheet index, row and column
	 * numbers
	 * 
	 * @param sheetIndex
	 * @param colNum
	 * @param rowNUm
	 * @return cell data
	 * @throws NullCellValueException
	 */
	public String getCellData(int sheetIndex, int colNum, int rowNum) throws NullCellValueException {
		try {
			if (rowNum <= 0)
				throw new NullCellValueException("row no. is less than zero ");
			;

			if (sheetIndex < 0)
				throw new NullCellValueException("Sheet index not found");
			;

			worksheet = workbook.getSheetAt(sheetIndex);
			XSSFRow row = worksheet.getRow(rowNum - 1);
			if (row == null)
				throw new NullCellValueException("Cell value is null");
			if (colNum <= 0)
				throw new NullCellValueException("column no. is less than zero ");
			XSSFCell cell = row.getCell(colNum);

			return cell.toString();
		} catch (Exception e) {

			System.out.println("Cell data not found");
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}
}
