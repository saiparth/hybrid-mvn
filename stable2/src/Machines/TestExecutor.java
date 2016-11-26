package Machines;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import hybrid.MultiGetelement;

public class TestExecutor extends MultiGetelement {
	static String parent = null;
	public static String actionPerformer(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, int columnToRefer)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		
		statusWriter(i, sheetName, status, path, 6);
		try {
			 input = ExcelUtils.reader(sheetName, i, columnToRefer, path).toString().toLowerCase();
			
		} catch (Exception e) {
			System.out.println("inside actionperformer,unable to get input");
		}
			
			if (input.contains("find element"))
			{			
				try
				{
				long rowToRefer = counter(i, sheetName, 3, path)-1; 
				//String Objectlocator = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString();
				//String identificatioType = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString();
				WebElement ele = MultiGetelement.GetElement(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
				
				switch (performType(i, sheetName, path).toLowerCase()) 
				{
				
				case "sendkeys":
					ele.sendKeys(value(i, sheetName, path));
					break;
				case "click":
					ele.click();
					break;
				case "clear":
					ele.clear();
					break;
				case "verifyelement":
					int size = GetElements(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
							, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString()).size();
						if (size > 0) 
						{
						System.out.println("element is present");
						} 
						else
						{
						System.out.println("element is not present");
					}
					break;
				case "isdisplayed":
					try 
					{
						ele.isDisplayed();
					} 
					catch (Exception e) 
					{
						helpingfunctions.takeScreenShot(wd, scPath);
					}
					break;
				}
			} catch (Exception e) {
				status = "FAIL " + e.getMessage();
				statusWriter(i, sheetName, status, path, 6);
				helpingfunctions.takeScreenShot(wd, scPath);
			}

		} else {
			switch (input) {
			case "geturl":
				try {
					wd.get(locator(i, sheetName, 3, path));
					System.out.println(wd.getCurrentUrl());
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;

			case "gettitle":
				try {
					String title = wd.getTitle();
					System.out.println(title);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			case "switchtowindow":
				try {
					//int windowIndex=0;
					long windowIndex=counter(i, sheetName, 3, path);
					//counter(int i, String sheetName, int colName, String path)
					helpingfunctions.windowSwitcher(wd, windowIndex);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			case "close":
				try {
					wd.close();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			case "storethiswinhandle":
				try {					
					 parent=wd.getWindowHandle();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			case "switchtoparentwindow":
				try {	
					System.out.println("attempting to switch to parent");
					System.out.println(parent);
					 wd.switchTo().window(parent);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			case "alert":
				try {
					Alert al=wd.switchTo().alert();
					if (performType(i, sheetName, path).toLowerCase()=="accept") {
						al.accept();
					} else {
						al.dismiss();
					}
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
				}
				break;
			default:
				System.out.println("unable to find by id method");
				break;
			}
		}

		return status;

	}

	public static String SpecialFunctions(int i, String sheetName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, 0, path).toString();
		return value;
	}

	public static String SpecialActionType(int i, String sheetName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, 1, path).toString();
		return value;
	}

	public static String ActionType(int i, String sheetName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, 2, path).toString();
		return value;
	}

	public static String locator(int i, String sheetName, int colNum, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, colNum, path).toString();
		return value;
	}

	public static String performType(int i, String sheetName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, 4, path).toString();
		return value;
	}

	public static String value(int i, String sheetName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String value = ExcelUtils.reader(sheetName, i, 5, path).toString();
		return value;
	}

	public static void statusWriter(int i, String sheetName, String status, String path, int colName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ExcelUtils.writer(sheetName, i, colName, status, path);
	}

	public static void SpecialCaseStatusWriter(int i, String sheetName, String status, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ExcelUtils.writer(sheetName, i, 7, status, path);
	}

	public static long counter(int i, String sheetName, int colName, String path)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		double value = (double) ExcelUtils.reader(sheetName, i, colName, path);
		return (long) value;
	}

}
