package com.api.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilityClass {
	

	static Map<String, String> dataMap = new HashMap<String, String>();
	
	

	public static String excelreading(String excelFilePath,String sheetName, String testCaseName)
			throws IOException, FileNotFoundException {
		// Load the excel
		String filename = "";
		String key = "";
		String value = "";

		FileInputStream fio = new FileInputStream(excelFilePath);

		XSSFWorkbook workbook = new XSSFWorkbook(fio);// open the workbook

		XSSFSheet sheet = workbook.getSheet(sheetName);// get the sheet at index 0
		int lastRow = sheet.getLastRowNum();
		
		// Looping over entire row
		for (int i = 1; i <= lastRow; i++) {

			Row row = sheet.getRow(i);

			// 1st Cell as Value
			Cell valueCell = row.getCell(1);

			// 0th Cell as Key
			Cell keyCell = row.getCell(0);

			if (valueCell == null && keyCell == null) {
				System.out.println("Cell is Empty in Column:");

			} else {
				key = keyCell.getStringCellValue().trim();

				value = valueCell.getStringCellValue().trim();
				
			}

			dataMap.put(key, value);

		}
		String payload1 = "";
		// get the key mapping for
		// Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result
		if (dataMap.containsKey(testCaseName)) {
			// key is the filename
			String textfilePath = dataMap.get(testCaseName);

			// open the file
			String filePath = "src/main/java/data2/" + textfilePath;

			FileInputStream fin = new FileInputStream(filePath);
			BufferedInputStream bin = new BufferedInputStream(fin);
			int i1;

			while ((i1 = bin.read()) != -1) {
				// read the payload
				payload1 = payload1 + (char) i1;

			}

		}
		return payload1;
	}
}
