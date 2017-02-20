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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestExecutor extends MultiGetelement {
	static String parent = null;
	static Logger log=Logger.getLogger(TestExecutor.class);
	public static String actionPerformer(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, 
											int columnToRefer,ExtentTest reportName)throws Exception {
		//it will provide null pointer exception if input is blank
		PropertyConfigurator.configure("F:\\.metadata\\repo\\stable2\\log4j.properties");
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		//log.info("Starting testing");
		reportName.log(LogStatus.INFO,"Starting action performer");
		statusWriter(i, sheetName, status, path, 6);
		//read ActionType

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
			try 
				{
					rowToRefer = counter(i, sheetName, 3, path) - 1;
					reportName.log(LogStatus.INFO,"Given row to refer in object repository - "+rowToRefer);
				} 
			catch (Exception e) 
				{
					status = "FAIL " + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
					String sspath=helpingfunctions.takeScreenShot(wd, scPath);
					reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath),status);
				}
			WebElement ele = null;
			try {
				ele = MultiGetelement.GetElement(wd,
						ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString(),
						ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
				reportName.log(LogStatus.INFO,"objectrepository row number= "+rowToRefer);
				
				switch (performType(i, sheetName, path).toLowerCase()) 
							{
							case "sendkeys":
								ele.sendKeys(value(i, sheetName, path));
								reportName.log(LogStatus.PASS,"Performing Sendkeys action");
								break;
							case "click":
								ele.click();
								reportName.log(LogStatus.PASS,"Performing Click action");
								break;
							case "clear":
								ele.clear();
								reportName.log(LogStatus.PASS,"Performing Clear action");
								break;
							case "verifyelement":
								int size = GetElements(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
										, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 10).size();
									if (size > 0) 
									{
									log.info("element is present");
									reportName.log(LogStatus.PASS,"element is present");
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
													String sspath=helpingfunctions.takeScreenShot(wd, scPath);
													reportName.log(LogStatus.FAIL,status);
													reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													}
												break;
							
							case " ":
												new Actions(wd).moveToElement(ele).build().perform();
												reportName.log(LogStatus.PASS,"Performing mouse hover action");
												break;
												
							case "doubleclick":
												new Actions(wd).doubleClick(ele);
												reportName.log(LogStatus.PASS,"Performing double click action");
												break;
							}
			} catch (Exception e) {
				status = "FAIL " + e.getMessage();
				statusWriter(i, sheetName, status, path, 6);
				String sspath=helpingfunctions.takeScreenShot(wd, scPath);
				reportName.log(LogStatus.FAIL,status);
				reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
			}

		}
		/*//use this to check 404,may be mouse hover over large elements
		if (input.contains("find elements")) 
											{
												long rowToRefer = 0;
												try 
													{
														rowToRefer = counter(i, sheetName, 3, path) - 1;
														reportName.log(LogStatus.INFO,"Given row to refer in object repository - "+rowToRefer);
													} 
												catch (Exception e) 
													{
														status = "FAIL " + e.getMessage();
														statusWriter(i, sheetName, status, path, 6);
														String sspath=helpingfunctions.takeScreenShot(wd, scPath);
														reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath),status);
													}
														List<WebElement>elements=null;
														try {
																elements = MultiGetelement.GetElements(wd,
																		ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString(),
																		ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
																reportName.log(LogStatus.INFO,"objectrepository row number= "+rowToRefer);
																				}
															} 
														catch (Exception e) 
														{
															status = "FAIL " + e.getMessage();
															statusWriter(i, sheetName, status, path, 6);
															String sspath=helpingfunctions.takeScreenShot(wd, scPath);
															reportName.log(LogStatus.FAIL,status);
															reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
														}
											}*/
											 
			else 
			{
			switch (input) {
			case "geturl":
							try 
							{
								wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
								wd.get(locator(i, sheetName, 3, path));
								System.out.println(wd.getCurrentUrl());
							} 
							catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
			case "checkalllinks":
							try {
							List<WebElement> Linkelements=wd.findElements(By.tagName("a"));
									for (WebElement webElement : Linkelements) 
										{
											String ur=webElement.getAttribute("href");
											helpingfunctions.linkValidator(ur);
										}
						
								reportName.log(LogStatus.PASS,"checking all links");
								} 
							catch (Exception e) {
								status = "FAIL " + e.getMessage();
							}
							break;
							
			case "checkallimages":
							try {
								List<WebElement> Imgelements=wd.findElements(By.tagName("a"));
										for (WebElement webElement : Imgelements) 
												{
													String ur=webElement.getAttribute("src");
													helpingfunctions.linkValidator(ur);
												}
							
									reportName.log(LogStatus.PASS,"checking all images");
								} 
							catch (Exception e)
								{
									status = "FAIL " + e.getMessage();
								}
								break;

			case "gettitle":
							try
								{
									String title = wd.getTitle();
									//System.out.println(title);
									statusWriter(i, sheetName, title, path, 5);
								} 
							catch (Exception e) 
								{
									status = "FAIL" + e.getMessage();
									String sspath=helpingfunctions.takeScreenShot(wd, scPath);
									reportName.log(LogStatus.FAIL,status);
									reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
								break;
				
			case "switchtowindow":
							try 
								{
									long windowIndex=counter(i, sheetName, 3, path);
									helpingfunctions.windowSwitcher(wd, windowIndex);
								} 
							catch (Exception e) 
								{
									status = "FAIL " + e.getMessage();
									String sspath=helpingfunctions.takeScreenShot(wd, scPath);
									reportName.log(LogStatus.FAIL,status);
									reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
								break;
				
			case "close":
							try 
								{
									wd.close();
								} 
							catch (Exception e)
								{
									status = "FAIL" + e.getMessage();
									String sspath=helpingfunctions.takeScreenShot(wd, scPath);
									reportName.log(LogStatus.FAIL,status);
									reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
								break;
								
			case "storethiswinhandle":
										try 
										{
											Thread.sleep(1000);					
											 parent=wd.getWindowHandle();
											 System.out.println(parent);
										} 
										catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											statusWriter(i, sheetName, status, path, 6);
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
										break;
										
			case "switchtoparentwindow":
										try 
										{	
											System.out.println("attempting to switch to parent");
											System.out.println(parent);
											 wd.switchTo().window(parent);
										} 
										catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											statusWriter(i, sheetName, status, path, 6);
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
										break;
				
				// switch to boot strap modal dialog
			case "switchtoactiveelement":
											try 
											{	
												 wd.switchTo().activeElement();
											} 
											catch (Exception e)
											{
												status = "FAIL" + e.getMessage();
												statusWriter(i, sheetName, status, path, 6);
												String sspath=helpingfunctions.takeScreenShot(wd, scPath);
												reportName.log(LogStatus.FAIL,status);
												reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
											}
											break;
			case "switchtodefaultcontent":
											try 
											{	
												 wd.switchTo().defaultContent();
											} 
											catch (Exception e) 
											{
												status = "FAIL" + e.getMessage();
												statusWriter(i, sheetName, status, path, 6);
												String sspath=helpingfunctions.takeScreenShot(wd, scPath);
												reportName.log(LogStatus.FAIL,status);
												reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
											}
											break;
			case "switchtoframe":
								try 
								{	
									long rowToRefer = counter(i, sheetName, 3, path)-1;
									WebElement wwq=MultiGetelement.GetElement
									(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
									, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
									 wd.switchTo().frame(wwq);
								} 
								catch (Exception e)
								{
									status = "FAIL" + e.getMessage();
									statusWriter(i, sheetName, status, path, 6);
									String sspath=helpingfunctions.takeScreenShot(wd, scPath);
									reportName.log(LogStatus.FAIL,status);
									reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
								}
								break;
			case "alert":
							try {
								Alert al=wd.switchTo().alert();
										if (performType(i, sheetName, path).toLowerCase()=="accept")
										{
											al.accept();
										} 
										else 
										{
											al.dismiss();
										}
							} 
							catch (Exception e) 
							{
								status = "FAIL" + e.getMessage();
								statusWriter(i, sheetName, status, path, 6);
								String sspath=helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
			case "asserttitle":
							String actual_title=wd.getTitle().toLowerCase();
							String breaker="0";
									if (performType(i, sheetName, path).toLowerCase().contains(actual_title)) 
										{
											TestExecutor.statusWriter(i, sheetName,actual_title , path, 5);
										}
									else 
										{
										status = "FAIL,actual title is " + wd.getTitle();
										statusWriter(i, sheetName, status, path, 6);
										String sspath=helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
											return breaker;
										}//throw new Exception("landed in wrong page");
							break;
			case "drag drop by element":
										try 
											{
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
											}
										catch (Exception e) 
															{
																status = "FAIL " + e.getMessage();
																statusWriter(i, sheetName, status, path, 7);
																String sspath=helpingfunctions.takeScreenShot(wd, scPath);
																reportName.log(LogStatus.FAIL,status);
																reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));			
															}
												break;
			case "drag drop by coordinates":
											try 
													{
														long SourceRowToRefer = counter(i, sheetName, 3, path)-1;
														int xOffset = (int) (counter(i, sheetName, 4, path));
														int yOffset = (int)counter(i, sheetName, 5, path);
														WebElement drag = MultiGetelement.GetElement
																(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
																, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
														
														Actions ac=new Actions(wd);
														ac.dragAndDropBy(drag, xOffset, yOffset).build().perform();
													} 
											catch (Exception e) 
																{
																	status = "FAIL " + e.getMessage();
																	statusWriter(i, sheetName, status, path, 7);
																	String sspath=helpingfunctions.takeScreenShot(wd, scPath);
																	reportName.log(LogStatus.FAIL,status);
																	reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));				
																}
											break;
			case "sleep":
						long sleeptime = 1000;
						try
							{
								 sleeptime = counter(i, sheetName, 3, path);
							} 
						catch (Exception e)
							{
								System.out.println("problem in rendering sleeptime");
							}
						 
						try 
							{
								Thread.sleep(sleeptime);
								System.out.println("sleeping for "+sleeptime);
							} 
						catch (InterruptedException e1)
							{
								e1.printStackTrace();
								status = "FAIL " + e1.getMessage();
								statusWriter(i, sheetName, status, path, 6);
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
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));	
										}
								
								status=	nestedLevelExecutor( i, path, wd, scPath, repoPath,  newsheetname, NumOfIteration, reportName);
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
			String repoPath, String sheethameToBExecuted, long iteratorCount,ExtentTest	reportName) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		String status = null;
		List<String> statuslist = new ArrayList<>();
	//	System.out.println("sheet executor is  called");
		// get the sheet name which will be executed
		log.info("executing sheet executor method");
		String sheetWiseResult=null;
		//To execute suite of sheets number of times=iterator count
		for (int c = 1; c <= iteratorCount; c++)
		{
			//To execute sheets
			for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
				{
				//System.out.println(j);
				boolean checkIfCondition = false,breaker = false,breaker2 = false;
				//check whether "if" is present in 1st column or not,without this null pointer... will thrown
				reportName.log(LogStatus.INFO,"Checking IF is present or not in first column");
				try 
				{
					checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toString().toLowerCase().contains("if");
					reportName.log(LogStatus.INFO,"IF is present,Will check next column is pass or not");
				} 
				catch (Exception e) 
				{
					log.error("checkIfCondition in method sheetExecutor"+e);
					reportName.log(LogStatus.INFO,"If not present in first column,Moving to action type column");
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
										if(actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,1,reportName).toLowerCase().contains("pass"))
											reportName.log(LogStatus.INFO,"Inside IF block,action is pass");
										{
											for (int k = j+1; k <ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
												{
												//System.out.println("value k="+k);
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
													if(val==null||val.contains("Blank value in"))
													{
														breaker2=true;
													}
															if(breaker2==true)
																{
																	break;
																}
																	else
																		{
																			status = actionPerformer(k, wd, sheethameToBExecuted, path,
																					scPath, repoPath,1,	reportName);
																			reportName.log(LogStatus.INFO,"Inside IF block actions results - "+status);
																			statuslist.add(status);
																			if (status=="0"){
																				break;
																			}
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
											reportName.log(LogStatus.INFO,"Blank value found in action type ,skipping this row");
											continue;
										}
									else
										{
											status = actionPerformer(j, wd, sheethameToBExecuted, path, scPath, repoPath,2,reportName);
											statuslist.add(status);
											if(status=="0")//if assert title fail break execution
															{
																System.out.println("Assert title failed");
																break;
															}
											
										}
						}
				}
			System.out.println(status.toString());
		}
		 		if (statuslist.toString().contains("0")||statuslist.toString().contains("FAIL"))
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
			String repoPath, String sheethameToBExecuted, long iteratorCount,ExtentTest reportName) throws Exception {
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
										if(actionPerformer2(j, wd, sheethameToBExecuted, path, scPath, repoPath,1, reportName  ).toLowerCase().contains("pass"))
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
														breaker2=true;
													}
															if(breaker2==true)
																{
																	break;
																}
																	else
																		{
																			status = actionPerformer2(k, wd, sheethameToBExecuted, path, scPath, repoPath,1, reportName);
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
									status = actionPerformer2(j, wd, sheethameToBExecuted, path, scPath, repoPath,2, reportName);
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
	public static String actionPerformer2(int i, WebDriver wd, String sheetName, String path,String scPath, String repoPath, int columnToRefer,ExtentTest reportName)
			throws Exception {
		//it will provide null pointer exception if input is blank
				PropertyConfigurator.configure("F:\\.metadata\\repo\\stable2\\log4j.properties");
				String status = "PASS",input = "";
				String repoSheetname="objectRepository";
				//log.info("Starting testing");
				reportName.log(LogStatus.INFO,"Starting action performer");
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
					try 
						{
							rowToRefer = counter(i, sheetName, 3, path) - 1;
							reportName.log(LogStatus.INFO,"Given row to refer in object repository - "+rowToRefer);
						} 
					catch (Exception e) 
						{
							status = "FAIL " + e.getMessage();
							statusWriter(i, sheetName, status, path, 6);
							String sspath=helpingfunctions.takeScreenShot(wd, scPath);
							reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath),status);
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
						reportName.log(LogStatus.INFO,"objectrepository row number= "+rowToRefer);
						switch (performType(i, sheetName, path).toLowerCase()) 
						{
						case "sendkeys":
							ele.sendKeys(value(i, sheetName, path));
							reportName.log(LogStatus.PASS,"Performing Sendkeys action");
							break;
						case "click":
							ele.click();
							reportName.log(LogStatus.PASS,"Performing Click action");
							break;
						case "clear":
							ele.clear();
							reportName.log(LogStatus.PASS,"Performing Clear action");
							break;
						case "verifyelement":
							int size = GetElements(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
									, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 10).size();
								if (size > 0) 
								{
								log.info("element is present");
								reportName.log(LogStatus.PASS,"element is present");
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
												String sspath=helpingfunctions.takeScreenShot(wd, scPath);
												reportName.log(LogStatus.FAIL,status);
												reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
												}
											break;
						}
					} catch (Exception e) {
						status = "FAIL " + e.getMessage();
						statusWriter(i, sheetName, status, path, 6);
						String sspath=helpingfunctions.takeScreenShot(wd, scPath);
						reportName.log(LogStatus.FAIL,status);
						reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
					}

				} else {
					switch (input) {
					case "geturl":
									try 
									{
										wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
										wd.get(locator(i, sheetName, 3, path));
										System.out.println(wd.getCurrentUrl());
									} 
									catch (Exception e)
									{
										status = "FAIL " + e.getMessage();
										String sspath=helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
									}
									break;
									
					case "checkalllinks"://404 for links
									try {
									List<WebElement> Linkelements=wd.findElements(By.tagName("a"));
											for (WebElement webElement : Linkelements) 
												{
													String ur=webElement.getAttribute("href");
													helpingfunctions.linkValidator(ur);
												}
								
										reportName.log(LogStatus.PASS,"Checking all links for 404 error");
										} 
									catch (Exception e) {
										status = "FAIL " + e.getMessage();
									}
									break;
									
					case "checkallimages"://404 error for imagepath
									try {
										List<WebElement> Imgelements=wd.findElements(By.tagName("a"));
												for (WebElement webElement : Imgelements) 
														{
															String ur=webElement.getAttribute("src");
															helpingfunctions.linkValidator(ur);
														}
									
											reportName.log(LogStatus.PASS,"Checking all image links for 404 error");
										} 
									catch (Exception e)
										{
											status = "FAIL " + e.getMessage();
										}
										break;

					case "gettitle":
									try
										{
											String title = wd.getTitle();
											//System.out.println(title);
											statusWriter(i, sheetName, title, path, 5);
										} 
									catch (Exception e) 
										{
											status = "FAIL" + e.getMessage();
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
										break;
						
					case "switchtowindow":
									try 
										{
											long windowIndex=counter(i, sheetName, 3, path);
											helpingfunctions.windowSwitcher(wd, windowIndex);
										} 
									catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
										break;
						
					case "close":
									try 
										{
											wd.close();
										} 
									catch (Exception e)
										{
											status = "FAIL" + e.getMessage();
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
										break;
										
					case "storethiswinhandle":
												try 
												{
													Thread.sleep(1000);					
													 parent=wd.getWindowHandle();
													 System.out.println(parent);
												} 
												catch (Exception e) 
												{
													status = "FAIL " + e.getMessage();
													statusWriter(i, sheetName, status, path, 6);
													String sspath=helpingfunctions.takeScreenShot(wd, scPath);
													reportName.log(LogStatus.FAIL,status);
													reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
												}
												break;
												
					case "switchtoparentwindow":
												try 
												{	
													System.out.println("attempting to switch to parent");
													System.out.println(parent);
													 wd.switchTo().window(parent);
												} 
												catch (Exception e) 
												{
													status = "FAIL " + e.getMessage();
													statusWriter(i, sheetName, status, path, 6);
													String sspath=helpingfunctions.takeScreenShot(wd, scPath);
													reportName.log(LogStatus.FAIL,status);
													reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
												}
												break;
						
						// switch to boot strap modal dialog
					case "switchtoactiveelement":
													try 
													{	
														 wd.switchTo().activeElement();
													} 
													catch (Exception e)
													{
														status = "FAIL" + e.getMessage();
														statusWriter(i, sheetName, status, path, 6);
														String sspath=helpingfunctions.takeScreenShot(wd, scPath);
														reportName.log(LogStatus.FAIL,status);
														reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													}
													break;
					case "switchtodefaultcontent":
													try 
													{	
														 wd.switchTo().defaultContent();
													} 
													catch (Exception e) 
													{
														status = "FAIL" + e.getMessage();
														statusWriter(i, sheetName, status, path, 6);
														String sspath=helpingfunctions.takeScreenShot(wd, scPath);
														reportName.log(LogStatus.FAIL,status);
														reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													}
													break;
					case "switchtoframe":
										try 
										{	
											long rowToRefer = counter(i, sheetName, 3, path)-1;
											WebElement wwq=MultiGetelement.GetElement
											(wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
											, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
											 wd.switchTo().frame(wwq);
										} 
										catch (Exception e)
										{
											status = "FAIL" + e.getMessage();
											statusWriter(i, sheetName, status, path, 6);
											String sspath=helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
										break;
					case "alert":
									try {
										Alert al=wd.switchTo().alert();
												if (performType(i, sheetName, path).toLowerCase()=="accept")
												{
													al.accept();
												} 
												else 
												{
													al.dismiss();
												}
									} 
									catch (Exception e) 
									{
										status = "FAIL" + e.getMessage();
										statusWriter(i, sheetName, status, path, 6);
										String sspath=helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
									}
									break;
					case "asserttitle":
									String actual_title=wd.getTitle().toLowerCase();
									String breaker="0";
											if (performType(i, sheetName, path).toLowerCase().contains(actual_title)) 
												{
													TestExecutor.statusWriter(i, sheetName,actual_title , path, 5);
												}
											else 
												{
												status = "FAIL,Title is  "+wd.getTitle();
												statusWriter(i, sheetName, status, path, 6);
												String sspath=helpingfunctions.takeScreenShot(wd, scPath);
												reportName.log(LogStatus.FAIL,status);
												reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													return breaker;
												}//throw new Exception("landed in wrong page");
									break;
					case "drag drop by element":
												try 
													{
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
													}
												catch (Exception e) 
																	{
																		status = "FAIL " + e.getMessage();
																		statusWriter(i, sheetName, status, path, 7);
																		String sspath=helpingfunctions.takeScreenShot(wd, scPath);
																		reportName.log(LogStatus.FAIL,status);
																		reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));			
																	}
														break;
					case "drag drop by coordinates":
													try 
															{
																long SourceRowToRefer = counter(i, sheetName, 3, path)-1;
																int xOffset = (int) (counter(i, sheetName, 4, path));
																int yOffset = (int)counter(i, sheetName, 5, path);
																WebElement drag = MultiGetelement.GetElement
																		(wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
																		, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
																
																Actions ac=new Actions(wd);
																ac.dragAndDropBy(drag, xOffset, yOffset).build().perform();
															} 
													catch (Exception e) 
																		{
																			status = "FAIL " + e.getMessage();
																			statusWriter(i, sheetName, status, path, 7);
																			String sspath=helpingfunctions.takeScreenShot(wd, scPath);
																			reportName.log(LogStatus.FAIL,status);
																			reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));				
																		}
													break;
					case "sleep":
								long sleeptime = 1000;
								try
									{
										 sleeptime = counter(i, sheetName, 3, path);
									} 
								catch (Exception e)
									{
										System.out.println("problem in rendering sleeptime");
									}
								 
								try 
									{
										Thread.sleep(sleeptime);
										System.out.println("sleeping for "+sleeptime);
									} 
								catch (InterruptedException e1)
									{
										e1.printStackTrace();
										status = "FAIL " + e1.getMessage();
										statusWriter(i, sheetName, status, path, 6);
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
													String sspath=helpingfunctions.takeScreenShot(wd, scPath);
													reportName.log(LogStatus.FAIL,status);
													reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));	
												}
										
										status=	nestedLevelExecutor( i, path, wd, scPath, repoPath,  newsheetname, NumOfIteration, reportName);
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
}
