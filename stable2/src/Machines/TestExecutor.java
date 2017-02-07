package Machines;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TestExecutor extends MultiGetelement {
	static String parent = null;
	//static double iteratorCount=1;
	static Logger log=Logger.getLogger(TestExecutor.class);
	//PropertyConfigurator.configure("log4j.properties");
	public static String actionPerformer(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, int columnToRefer)
			throws Exception {
		//it will provide null pointer exception if input is blank
		PropertyConfigurator.configure("log4j.properties");
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		log.info("Starting testing");
		statusWriter(i, sheetName, status, path, 6);
		try {
			 input = ExcelUtils.reader(sheetName, i, columnToRefer, path).toString().toLowerCase();
			 log.info("input value = "+input);
			} 
		catch (Exception e) 
			{
			log.error("inside action performer,unable to get input");
			}

		if (input.contains("find element")) 
		{
			long rowToRefer = 0;
			try {
				rowToRefer = counter(i, sheetName, 3, path) - 1;
			} catch (Exception e) {
				status = "FAIL " + e.getMessage();
				statusWriter(i, sheetName, status, path, 6);
				helpingfunctions.takeScreenShot(wd, scPath);
				log.error("Problem with action on web elements" + e);
			}
			WebElement ele = null;
			try {
				// String Objectlocator = ExcelUtils.reader(repoSheetname, (int)
				// rowToRefer, 2, repoPath).toString();
				// String identificatioType = ExcelUtils.reader(repoSheetname,
				// (int) rowToRefer, 1, repoPath).toString();
				ele = MultiGetelement.GetElement(wd,
						ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString(),
						ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
			
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
						log.info("element is present");
						} 
						else
						{
						log.info("element is not present");
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
						log.error("Element not displayed"+e);
					}
					break;
				}
			} catch (Exception e) {
				status = "FAIL " + e.getMessage();
				statusWriter(i, sheetName, status, path, 6);
				helpingfunctions.takeScreenShot(wd, scPath);
				log.error("Problem with action on web elements"+e);
			}

		} else {
			switch (input) {
			case "geturl":
				try {
					wd.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
					wd.get(locator(i, sheetName, 3, path));
					System.out.println(wd.getCurrentUrl());
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;

			case "gettitle":
				try {
					String title = wd.getTitle();
					//System.out.println(title);
					statusWriter(i, sheetName, title, path, 6);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "switchtowindow":
				try {
					long windowIndex=counter(i, sheetName, 3, path);
					helpingfunctions.windowSwitcher(wd, windowIndex);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "close":
				try {
					wd.close();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "storethiswinhandle":
				try {
					Thread.sleep(1000);					
					 parent=wd.getWindowHandle();
					 System.out.println(parent);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "switchtoparentwindow":
				try {	
					System.out.println("attempting to switch to parent");
					System.out.println(parent);
					 wd.switchTo().window(parent);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
				// switch to boot strap modal dialog
			case "switchtoactiveelement":
				try {	
					 wd.switchTo().activeElement();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
			case "switchtoframe":
				try {	
					long rowToRefer = counter(i, sheetName, 3, path)-1;
					WebElement wwq=MultiGetelement.GetElement
					(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
					, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
					 wd.switchTo().frame(wwq);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);
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
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
			case "asserttitle":
					String actual_title=wd.getTitle().toLowerCase();
					if (performType(i, sheetName, path).toLowerCase().contains(actual_title)) {
					}
					else throw new Exception("landed in wrong page");
					break;
			case "drag drop by element":
				try {
				long SourceRowToRefer = counter(i, sheetName, 4, path)-1;
				long dropRowToRefer = counter(i, sheetName, 5, path)-1;
				WebElement drag = MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
				
				WebElement drop=MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 1, repoPath).toString(), 5);
				Actions ac=new Actions(wd);
				ac.dragAndDrop(drag, drop).build().perform();
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 7);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);				}
				break;
			case "drag drop by coordinates":
				try {
				long SourceRowToRefer = counter(i, sheetName, 3, path)-1;
				int xOffset = (int) (counter(i, sheetName, 4, path));
				int yOffset = (int)counter(i, sheetName, 5, path);
				WebElement drag = MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
				
				Actions ac=new Actions(wd);
				ac.dragAndDropBy(drag, xOffset, yOffset).build().perform();
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 7);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);				}
				break;
			case "sleep":
				long sleeptime = 1000;
				try {
					 sleeptime = counter(i, sheetName, 3, path);
				} catch (Exception e) {
					System.out.println("problem in rendering sleeptime");log.error(status);
				}
				 
				try {
					Thread.sleep(sleeptime);
					System.out.println("sleeping for "+sleeptime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					status = "FAIL " + e1.getMessage();
					statusWriter(i, sheetName, status, path, 6);log.error(status);
				}
				break;
				//call dupe method to execute sheet within sheet
			case "executesheet":
				System.out.println("execute sheet is called in switch");
				String newsheetname = null;
				long NumOfIteration = 1;
				try 
				{
					NumOfIteration = counter(i, sheetName, 3, path);
				}
				catch (Exception e) 
				{
					log.info("executeSheetwithinsheet - no count mentioned");
				}
				
					try
						{
							newsheetname=ExcelUtils.reader(sheetName, i, 5, path).toString();
							System.out.println(newsheetname);
						} 
					catch (Exception e) 
						{
							status = "FAIL -" + e.getMessage();
							statusWriter(i, sheetName, status, path, 6);
							helpingfunctions.takeScreenShot(wd, scPath);
							log.error(status);
						}
				
				status=	nestedLevelExecutor( i, path, wd, scPath, repoPath,  newsheetname, NumOfIteration);
				//statusWriter(i, newsheetname, status, path, 7);
				System.out.println(status+" executesheet");
				
				if (status=="FAIL") 
				{
					statusWriter(i, sheetName, status, path, 7);
				}
				//else statusWriter(i, sheetName, "PASS", path, 6);
				break;
			default:
				log.error("unable to find by  method");
				System.err.println();
				break;
			}
		}

		return status;

	}
	//executorSheetName,rowNum,executer sheet path,
	public static String sheetExecutor( int i, String path, WebDriver wd, String scPath, 
			String repoPath, String sheethameToBExecuted, long iteratorCount) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		String status = null;
		List<String> statuslist = new ArrayList<>();
	//	System.out.println("sheet executor is  called");
		// get the sheet name which will be executed
		//String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
		//long f=(long)iteratorCount;
		log.info("executing sheet executor method");
		String sheetWiseResult=null;
		//To execute suite of sheets number of times=iterator count
		for (int c = 1; c <= iteratorCount; c++)
		{
			//To execute sheets
			for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
				{
				System.out.println(j);
				boolean checkIfCondition = false,breaker = false,breaker2 = false;
				//check whether "if" is present in 1st column or not,without this null pointer... will thrown
				try 
				{
					checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toString().toLowerCase().contains("if");
					System.out.println(checkIfCondition);
				} 
				catch (Exception e) 
				{
					log.error("checkIfCondition in method sheetExecutor"+e);
					System.out.println("if not there "+e);
				}
						
				if(checkIfCondition==true)
						{
							//check whether ActionType is blank
							try 
								{
									breaker=ExcelUtils.reader(sheethameToBExecuted, j, 1, path).toString().contains("Blank value in");
								} 
							catch (Exception e) 
								{
									log.error("breaker in method sheetExecutor"+e);
								}
							//If blank value is there then break loop of special scenario
								if(breaker==true)
									{
										break;
									}
								else
									{
										//if result is fail the execute special scenario
										if(actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,1).toLowerCase().contains("pass"))
										{
											for (int k = j+1; k <ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
												{
												System.out.println("value k="+k);
												String val = null;
													try 
														{
															 val = ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString();
																System.out.println("breaker2 " +k+"" +ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString());
														} 	
													catch (Exception e) 
														{
															log.error("empty cells in method sheetExecutor "+e);
														}
												
													//If blank value is there then break loop of special scenario
													if(val==null||val.contains("Blank value in")){
														breaker=true;
													}
															if(breaker2==true)
																{
																	break;
																}
																	else
																		{
																			status = actionPerformer(k, wd, sheethameToBExecuted, path, scPath, repoPath,1);
																			System.out.println("after brek2"+status);
																			statuslist.add(status);
																		}
												}//for loop end
										}
									}
							}
						//else continue regular 
						else
						{
							boolean actionBreaker=false;
							try 
							{
								 actionBreaker=ExcelUtils.reader(sheethameToBExecuted, j, 2, path).toString().toLowerCase().contains("blank value in");
								//System.out.println(breaker2);
							} 
						catch (Exception e) 
							{
								log.error("emty cells in method sheetExecutor "+e);
							}
							if (actionBreaker==true)
								{
									continue;
								}
							else
								{
									status = actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,2);
									statuslist.add(status);
								}
						}
				}
			System.out.println(status.toString());
		}
 		if (statuslist.toString().contains("FAIL"))
 		{
			 sheetWiseResult = "FAIL";	
		}
 		else
 		{
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
	
	public static String nestedLevelExecutor( int i, String path, WebDriver wd, String scPath, 
			String repoPath, String sheethameToBExecuted, long iteratorCount) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		String status = null;
		List<String> statuslist = new ArrayList<>();
		System.out.println("sheet executor is  called");
		// get the sheet name which will be executed
		//String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
		//long f=(long)iteratorCount;
		log.info("executing sheet executor method");
		String sheetWiseResult=null;
		//To execute suite of sheets number of times=iterator count
		for (int c = 1; c <= iteratorCount; c++)
		{
			//To execute sheets
			for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
				{
				System.out.println(j);
				boolean checkIfCondition = false,breaker = false,breaker2 = false;
				//check whether "if" is present in 1st column or not,without this null pointer... will thrown
				try 
				{
					checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toString().toLowerCase().contains("if");
					System.out.println(checkIfCondition);
				} 
				catch (Exception e) 
				{
					log.error("checkIfCondition in method sheetExecutor"+e);
					System.out.println("if not there "+e);
				}
						
				if(checkIfCondition==true)
						{
							//check whether ActionType is blank
							try 
								{
									breaker=ExcelUtils.reader(sheethameToBExecuted, j, 1, path).toString().contains("Blank value in");
								} 
							catch (Exception e) 
								{
									log.error("breaker in method sheetExecutor"+e);
								}
							//If blank value is there then break loop of special scenario
								if(breaker==true)
									{
										break;
									}
								else
									{
										//if result is fail the execute special scenario
										if(actionPerformer2(j, wd, sheethameToBExecuted, path, scPath, repoPath,1).toLowerCase().contains("pass"))
										{
											for (int k = j+1; k <ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
												{
												System.out.println("value k="+k);
												String val = null;
													try 
														{
															 val = ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString();
																System.out.println("breaker2 " +k+"" +ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString());
														} 	
													catch (Exception e) 
														{
															log.error("empty cells in method sheetExecutor "+e);
														}
												
													//If blank value is there then break loop of special scenario
													if(val==null||val.contains("Blank value in")){
														breaker=true;
													}
															if(breaker2==true)
																{
																	break;
																}
																	else
																		{
																			status = actionPerformer2(k, wd, sheethameToBExecuted, path, scPath, repoPath,1);
																			System.out.println("after brek2"+status);
																			statuslist.add(status);
																		}
												}//for loop end
										}
									}
							}
						//else continue regular 
						else
						{
							System.out.println("execu in else "+status);
							boolean actionBreaker=false;
							try 
							{
								 actionBreaker=ExcelUtils.reader(sheethameToBExecuted, j, 2, path).toString().toLowerCase().contains("blank value in");
								//System.out.println(breaker2);
							} 
						catch (Exception e) 
							{
								log.error("empty cells in method sheetExecutor "+e);
							}
							if (actionBreaker==true)
								{
									continue;
								}
							else
								{
									status = actionPerformer2(j, wd, sheethameToBExecuted, path, scPath, repoPath,2);
									statuslist.add(status);
								}
						}
				}
			System.out.println(status.toString());
		}
 		if (statuslist.toString().contains("FAIL"))
 		{
			 sheetWiseResult = "FAIL";	
		}
 		else
 		{
			 sheetWiseResult = "PASS";
		}
		
		return sheetWiseResult;
	
		}
	public static String actionPerformer2(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, int columnToRefer)
			throws Exception {
		//it will provide null pointer exception if input is blank
		PropertyConfigurator.configure("log4j.properties");
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		log.info("Starting testing");
		statusWriter(i, sheetName, status, path, 6);
		try {
			 input = ExcelUtils.reader(sheetName, i, columnToRefer, path).toString().toLowerCase();
			 log.info("input value = "+input);
			} 
		catch (Exception e) 
			{
			log.error("inside actionperformer,unable to get input");
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
						log.info("element is present");
						} 
						else
						{
						log.info("element is not present");
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
						log.error("Element not displayed"+e);
					}
					break;
				}
			} catch (Exception e) {
				status = "FAIL " + e.getMessage();
				statusWriter(i, sheetName, status, path, 6);
				helpingfunctions.takeScreenShot(wd, scPath);
				log.error("Problem with action on web elements"+e);
			}

		} else {
			switch (input) {
			case "geturl":
				try {
					wd.get(locator(i, sheetName, 3, path));
					System.out.println(wd.getCurrentUrl());
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;

			case "gettitle":
				try {
					String title = wd.getTitle();
					System.out.println(title);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "switchtowindow":
				try {
					long windowIndex=counter(i, sheetName, 3, path);
					helpingfunctions.windowSwitcher(wd, windowIndex);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "close":
				try {
					wd.close();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "storethiswinhandle":
				try {
					Thread.sleep(1000);					
					 parent=wd.getWindowHandle();
					 System.out.println(parent);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
			case "switchtoparentwindow":
				try {	
					System.out.println("attempting to switch to parent");
					System.out.println(parent);
					 wd.switchTo().window(parent);
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
				
				// switch to boot strap modal dialog
			case "switchtoactiveelement":
				try {	
					 wd.switchTo().activeElement();
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
			case "switchtoframe":
				try {	
					long rowToRefer = counter(i, sheetName, 3, path)-1;
					WebElement wwq=MultiGetelement.GetElement
					(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
					, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
					 wd.switchTo().frame(wwq);
				} catch (Exception e) {
					status = "FAIL" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);
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
					helpingfunctions.takeScreenShot(wd, scPath);log.error(status);
				}
				break;
			case "asserttitle":
					String actual_title=wd.getTitle().toLowerCase();
					if (performType(i, sheetName, path).toLowerCase().contains(actual_title)) {
					}
					else throw new Exception("landed in wrong page");
					break;
			case "drag drop by element":
				try {
				long SourceRowToRefer = counter(i, sheetName, 4, path)-1;
				long dropRowToRefer = counter(i, sheetName, 5, path)-1;
				WebElement drag = MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
				
				WebElement drop=MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 1, repoPath).toString(), 5);
				Actions ac=new Actions(wd);
				ac.dragAndDrop(drag, drop).build().perform();
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 7);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);				}
				break;
			case "drag drop by coordinates":
				try {
				long SourceRowToRefer = counter(i, sheetName, 3, path)-1;
				int xOffset = (int) (counter(i, sheetName, 4, path));
				int yOffset = (int)counter(i, sheetName, 5, path);
				WebElement drag = MultiGetelement.GetElement
						(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
						, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
				
				Actions ac=new Actions(wd);
				ac.dragAndDropBy(drag, xOffset, yOffset).build().perform();
				} catch (Exception e) {
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 7);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);				}
				break;
			case "sleep":
				long sleeptime = 1000;
				try {
					 sleeptime = counter(i, sheetName, 3, path);
				} catch (Exception e) {
					System.out.println("problem in rendering sleeptime");log.error(status);
				}
				 
				try {
					Thread.sleep(sleeptime);
					System.out.println("sleeping for "+sleeptime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					status = "FAIL " + e1.getMessage();
					statusWriter(i, sheetName, status, path, 6);log.error(status);
				}
				break;
				
			case "executesheet":
				String newsheetname = null;
				long NumOfIteration = 1;
				try {
					NumOfIteration = counter(i, sheetName, 3, path);
				} catch (Exception e) {
					log.info("executeSheetwithinsheet - no count mentioned");
				}
				try
				{
					 newsheetname=ExcelUtils.reader(sheetName, i, 5, path).toString().toLowerCase();
					
				} 
				catch (Exception e) {
					status = "FAIL -" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					helpingfunctions.takeScreenShot(wd, scPath);
					log.error(status);
				}
				status=	nestedLevelExecutor( i, path, wd, scPath, repoPath,  newsheetname, NumOfIteration);
				//statusWriter(i, newsheetname, status, path, 7);
				System.out.println(status+"executesheet");
				if (status=="FAIL") {
					statusWriter(i, sheetName, status, path, 7);
				}
				else
				{statusWriter(i, sheetName, "PASS", path, 8);}
				//else statusWriter(i, sheetName, "PASS", path, 6);
				break;
			default:
				log.error("unable to find by id method");
				break;
			}
		}

		return status;

	}
}
