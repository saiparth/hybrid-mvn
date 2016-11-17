package Machines;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.sun.jna.platform.FileUtils;

public class ExcelUtils {

	public static int getRowCount(String sheetName, String path) throws IOException {
		FileInputStream fis = null;
		int lastrownum = 1;
		try {
			fis = new FileInputStream(new File(path));
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			lastrownum = sheet.getLastRowNum();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return lastrownum;
	}

	public static String propertyReader(String path, String input) {
		String vla = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
			Properties p = new Properties();
			p.load(fis);
			vla = p.getProperty(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vla;
	}
		

	public static Object reader(String sheetName, int rowNum, int colNum, String path)
			 {
		FileInputStream fis = null;
		Object data = null;
		try {
			fis = new FileInputStream(new File(path));
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);
			if (sh != null) {
				Row row = sh.getRow(rowNum);
				if (row != null) {
					Cell col = row.getCell(colNum);
					if (col != null) {
						switch (col.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							data = col.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							data = col.getNumericCellValue();
							break;
						case Cell.CELL_TYPE_BLANK:
							System.out.println("Blank cell row "+row+" col "+col+" sheet "+sheetName);
							break;
						}
					} else {
						System.out.println("Blank value in column " + colNum + " sheet " + sheetName);
					}
				} else {
					System.out.println("Blank value in row " + rowNum + " sheet " + sheetName);
				}
			} else {
				System.out.println("Blank value in sheet name " + sheetName);
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static void writer(String sheetName, int rowNum, int colNum, String status, String path)  {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);
			if (sh != null) {

				Row row = sh.getRow(rowNum);
				if (row != null) {
					Cell cel = row.createCell(colNum);
					cel.setCellValue(status);
					fos = new FileOutputStream(path);
					wb.write(fos);
				} else {
					System.out.println("blank row,I created new row" + rowNum);
				}
			} else {
				System.out.println("problem with sheetname");
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (Exception e2) {
				System.out.println("error in closing file object");			}
		}
	}

}
