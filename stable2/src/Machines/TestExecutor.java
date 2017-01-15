package Machines;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestExecutor extends MultiGetelement {
	static String parent = null;
	//static double iteratorCount=1;
	public static String actionPerformer(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, int columnToRefer)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		//it will provide null pointer exception if input is blank
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		
		statusWriter(i, sheetName, status, path, 6);
		try {
			 input = ExcelUtils.reader(sheetName, i, columnToRefer, path).toString().toLowerCase();
			} 
		catch (Exception e) 
			{
			System.out.println("inside actionperformer,unable to get input");
			}
			
			if (input.contains("find element"))
			{			
				try
				{
				long rowToRefer = counter(i, sheetName, 3, path)-1; 
				//String Objectlocator = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString();
				//String identificatioType = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString();
				WebElement ele = MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
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
					long windowIndex=counter(i, sheetName, 3, path);
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
				
				// switch to boot strap modal dialog
			case "switchtoactiveelement":
				try {	
					 wd.switchTo().activeElement();
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
			case "sleep":
				long sleeptime = 1000;
				try {
					 sleeptime = counter(i, sheetName, 3, path);
				} catch (Exception e) {
					System.out.println("problem in rendering sleeptime");
				}
				 
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					status = "FAIL " + e1.getMessage();
					statusWriter(i, sheetName, status, path, 6);
				}
				break;
				
			case "executesheet":
				long NumOfIteration = 1;
				try {
					NumOfIteration = counter(i, sheetName, 3, path);
				} catch (Exception e) {
					System.out.println("executeSheetwithinsheet - no count mentioned");
				}
				try
				{
					//String sheetExecutor( int i, String path, WebDriver wd, String scPath, 
							//String repoPath, String status, String sheethameToBExecuted, long iteratorCount)
					String newsheetname=ExcelUtils.reader(sheetName, i, 5, path).toString().toLowerCase();
					sheetExecutor( i, path, wd, scPath, repoPath, status, newsheetname, NumOfIteration);
					statusWriter(i, sheetName, status, path, 7);
				} 
				catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 7);
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
	//executorSheetName,rowNum,executer sheet path,
	public static String sheetExecutor( int i, String path, WebDriver wd, String scPath, 
			String repoPath, String status, String sheethameToBExecuted, long iteratorCount) throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<String> statuslist = new ArrayList<>();
		// get the sheet name which will be executed
		//String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
		//long f=(long)iteratorCount;
		String sheetWiseResult;
		//To execute suite of sheets number of times=iterator count
		for (int c = 1; c <= iteratorCount; c++)
		{
			//To execute sheets
			for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
				{
				boolean checkIfCondition = true,breaker = true,breaker2 = true;
				//check whether "if" is present in 1st column or not,without this null pointer... will thrown
				try 
				{
					checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toLowerCase().contains("if");
				//	System.out.println(checkIfCondition);
				} 
				catch (Exception e) 
				{
					//System.out.println("if is blank");
				}
						if(checkIfCondition==true)
						{
							//check whether value is blank
							try 
							{
								breaker=ExcelUtils.reader(sheethameToBExecuted, j, 2, path).toString().contains("Blank value in");
							} 
							catch (Exception e) 
							{
								//System.out.println("breaker found in action type");
							}
							//If blank value is there then break loop of special scenario
								if(breaker==true)
								{
									continue;
								}
							//if result is fail the execute speacial scenario
								if(actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,2).toLowerCase().contains("pass"))
						{
							for (int k = j+1; k <=ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
							{
								try 
								{
									breaker2=ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString().contains("Blank value in");
								} 
								catch (Exception e) 
								{
									//System.out.println("breaker found in special action type");
								}
								//If blank value is there then break loop of special scenario
									if(breaker2==true)
									{
									//	System.out.println("breaker2");
										break;
									}
								status = actionPerformer(k, wd, sheethameToBExecuted, path, scPath, repoPath,1);
										}
									}
								}
						//else continue regular 
						else
						{
							
							status = actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,2);
						}
				}
			statuslist.add(status);
		}
 		if (statuslist.contains("FAIL")) {
			 sheetWiseResult = "FAIL";	
		} else {
			 sheetWiseResult = "PASS";
		}
		
		
		return sheetWiseResult;
	
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
