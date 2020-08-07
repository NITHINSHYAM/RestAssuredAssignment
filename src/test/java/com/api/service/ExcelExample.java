package com.api.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExcelExample {

	static String readDataFromExcel(String desiredTestCaseName, String columnName) {
		try {
			String filename = "src/main/java/data1/Lop_sampleData.xlsx";
			String key = "";
			String value = "";
			int maxColumnCount = 7; // This should come from property file

			FileInputStream fio = new FileInputStream(filename);
			XSSFWorkbook workbook = new XSSFWorkbook(fio);//open the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);//get the sheet at index 0
			int lastRow = sheet.getLastRowNum();

			System.out.println("The last row number is : " + lastRow); // Here we get the total number of rows in the excel

			ArrayList<ArrayList<HashMap<String, String>>> completeTestData = new ArrayList<ArrayList<HashMap<String, String>>>(); // has testdata for the test cases

			//Looping over entire row
			for (int i = 1; i <= lastRow; i++) {  // Since first row is the header row, start from second row, hence i = 1

				Row headerRow = sheet.getRow(0);  // 0th row is header row
				Row dataRow = sheet.getRow(i);

				ArrayList<HashMap<String, String>> eachTestCaseTestData = new ArrayList<HashMap<String, String>>(); // This arraylist will have all the test data per test cases
				completeTestData.add(eachTestCaseTestData);

				for (int j = 0; j < maxColumnCount; j++) {
					Cell headerCell = headerRow.getCell(j);
					Cell dataCell = dataRow.getCell(j);

					key = headerCell.getStringCellValue().trim();
					boolean exception = false;
					String errorMessage = null;
					try {
						value = dataCell.getStringCellValue().trim();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						errorMessage = e.getMessage();
						exception = true;
					}

					if (exception == true) {
						if (errorMessage.compareTo("Cannot get a STRING value from a NUMERIC cell") == 0) {
							value = Double.toString(dataCell.getNumericCellValue());
						}
					}

					HashMap<String, String> map = new HashMap<String, String>(); // field level

					System.out.println(key + " : " + value);
					map.put(key, value);
					eachTestCaseTestData.add(map);
				}
			}

			System.out.println("Total number of test cases are : " + completeTestData.size());

			for (int i = 0; i < completeTestData.size(); i++) {
				ArrayList<HashMap<String, String>> eachTestCaseTestData = completeTestData.get(i); // take 1 row at a time
				System.out.println("The count of test data per test case is : " + eachTestCaseTestData.size());

				for (int j = 0; j < eachTestCaseTestData.size(); j++) {
					HashMap<String, String> testCaseNameCell = eachTestCaseTestData.get(0);
					System.out.println("The test case name is : " + testCaseNameCell.get(("TestCaseName")));
					System.out.println("The desired test case is : " + desiredTestCaseName);
					if (testCaseNameCell.get("TestCaseName").compareTo(desiredTestCaseName) == 0) { // if the test case name matches, get the cell value of the desired column
						HashMap<String, String> testDataCell = eachTestCaseTestData.get(j);
						if (testDataCell.containsKey(columnName)) { // return the column value for matching test case name and columnname
							return testDataCell.get(columnName);
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String args[]) {
		for (int i = 0; i < 99; i++)
		{

			System.out.println("The response for the desired test case column is : " + readDataFromExcel("Initiate the cardAuth_authorize API with valid fields and verify the Expected Result1", "CardAuthorizationResponseCode"));

		}
	}
}
