package com.qa.tutorialsninja.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// in order to read data from excel sheet we need to use some excel utility but java doesn't provide any
// excel operation. so we have to use a third party library called Apache POI API and we add the dependencies in project POM.xml file
// with the help of Apache POI API we can use excel operations as below 
public class ExcelUtil {
	
	private static Workbook wBook;
	private static Sheet sheet;
	public static String TEST_DATA_SHEET_PATH = "./src/main/java/com/qa/tutorialsninja/testdata/TutorialNinjaRD.xlsx";
	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH); // just create the file object in the memory by copying from the actual file on the disk
			// but this file is not an excel file yet. so :
			wBook = WorkbookFactory.create(ip); // this way we define that the created file is a Workbook (excel file)
			sheet = wBook.getSheet(sheetName); // and here we read the sheet name and create the sheet object
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
}
