package hybridFramework.webdriver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestExecutor extends MultiGetelement {
	static String parent = null;
	static int elementLoadTimeLimit=60;
	static Logger log=Logger.getLogger(TestExecutor.class);
	
	public static String actionPerformer(int i,//execute sheet checker
										WebDriver wd, 
										String sheetName, 
										String path,
										String scPath, 
										String repoPath, 
										int columnToRefer,
										ExtentTest reportName)
										throws Exception 
	{
		//it will provide null pointer exception if input is blank
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
		String status = "PASS",input = "";
		String repoSheetname="objectRepository";
		// log.info("Starting action performer");
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

		  if (input.contains("executesheet"))
				{
					//System.out.println("execute sheet is called in switch");
					String newsheetname = "";
					long NumOfIterations = 1;
					
						try
							{
								newsheetname=ExcelUtils.reader(sheetName, i, 5, path).toString();
								System.out.println(newsheetname);
							} 
						catch (Exception e) 
							{
								status = "FAIL -" + e.getMessage();
								statusWriter(i, sheetName, status, path, 6);
							}
						try 
							{
							NumOfIterations = counter(i, sheetName, 3, path);
								System.out.println("i "+i+""+ newsheetname+"col"+ "iteration"+ NumOfIterations);
							}
						catch (Exception e) 
							{
								log.info("executeSheetwithinsheet - no count mentioned");
							}
									status=	sheetExecutor(i,
															path, 
															wd, 
															scPath, 
															repoPath, 
															newsheetname, 
															NumOfIterations,
															reportName);
									System.out.println(status+" executesheet");
			
									if (status=="FAIL") 
										{
											statusWriter(i, sheetName, status, path, 7);
										}								
				}//end of if (input.contains("executesheet"))
			else
				{
					status=webActionPerformer(input, 
												wd, 
												i, 
												sheetName, 
												path, 
												reportName, 
												scPath, 
												elementLoadTimeLimit, 
												repoSheetname, 
												repoPath,log);
				}
		return status;

	}
	public static String actionPerformerDupe(int i,//execute sheet checker
			WebDriver wd, 
			String sheetName, 
			String path,
			String scPath, 
			String repoPath, 
			int columnToRefer,
			ExtentTest reportName)
			throws Exception 
				{
				//it will provide null pointer exception if input is blank
				PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
				String status = "PASS",input = "";
				String repoSheetname="objectRepository";
				// log.info("Starting action performer");
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
				
				if (input.contains("executesheet"))
				{
				//System.out.println("execute sheet is called in switch");
				String newsheetname = "";
				long NumOfIterations = 1;
				
				try
				{
					newsheetname=ExcelUtils.reader(sheetName, i, 5, path).toString();
					System.out.println(newsheetname);
				} 
				catch (Exception e) 
				{
					status = "FAIL -" + e.getMessage();
					statusWriter(i, sheetName, status, path, 6);
				}
				try 
				{
				NumOfIterations = counter(i, sheetName, 3, path);
					System.out.println("i "+i+""+ newsheetname+"col"+ "iteration"+ NumOfIterations);
				}
				catch (Exception e) 
				{
					log.info("executeSheetwithinsheet - no count mentioned");
				}
						status=	sheetExecutor(i,
												path, 
												wd, 
												scPath, 
												repoPath, 
												newsheetname, 
												NumOfIterations,
												reportName);
						System.out.println(status+" executesheet");
				
						if (status=="FAIL") 
							{
								statusWriter(i, sheetName, status, path, 7);
							}								
				}//end of if (input.contains("executesheet"))
				else
				{
				status=webActionPerformer(input, 
									wd, 
									i, 
									sheetName, 
									path, 
									reportName, 
									scPath, 
									elementLoadTimeLimit, 
									repoSheetname, 
									repoPath, log);
				}
				return status;

}
	
	//executorSheetName,rowNum,executer sheet path,
	public static String sheetExecutor( int i, 
											String path, 
											WebDriver wd, 
											String scPath, 
											String repoPath, 
											String sheethameToBExecuted, 
											long iteratorCount,
											ExtentTest	reportName) throws IOException, Exception {
					PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
					String status = null;
					List<String> statuslist = new ArrayList<>();
					//System.out.println("sheet executor is  called");
					// get the sheet name which will be executed
					log.info("executing sheet executor method");
					String sheetWiseResult=null; 
					//To execute suite of sheets number of times=iterator count
					for (int c = 1; c <= iteratorCount; c++)
					{
					//log.info("the value of c= "+c);
					//To execute sheets
					for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
					{
					log.info("the value of j= "+j);
					boolean checkIfCondition = false,breaker = false;
					//check whether "if" is present in 1st column or not,without this null pointer... will thrown
					log.info("Checking IF is present or not in first column");
					try 
					{
					checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toString().toLowerCase().contains("if");
					log.info("IF is present,Will check next column is pass or not");
					} 
					catch (Exception e) 
					{
					log.error("checkIfCondition in method sheetExecutor"+e);
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
						log.error("IF was present but,ActionType was passed blank");
					}
					//If blank value is there then break loop of special scenario,to set limit to for loop this condition is put
					if(breaker==true)
						{
							log.info("end of IF loop");
							break;
						}
					else
						{
							//if result is pass the execute special scenario,1st row result=pass then execute from below row
							if(actionPerformerDupe(j, 
												wd, 
												sheethameToBExecuted, 
												path, 
												scPath, 
												repoPath,
												1,
												reportName).toLowerCase().contains("pass"))
								log.info("Inside IF block,action is pass");
							{
								//to execute inside if steps
								for (int k = j+1; k <=ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
									{
									//log.info("value k="+k);
									String val = "Blank value in";
										try 
											{
												 val = ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString();
											} 	
										catch (Exception e) 
											{
												log.error("empty actiontype method sheetExecutor");
											}
									
										//If blank value is there then break loop of special scenario
										if(val.contains("Blank value in"))
										{
											break;
										}
										else
													{
																status = actionPerformerDupe(k, 
																						wd, 
																						sheethameToBExecuted, 
																						path,
																						scPath, 
																						repoPath,
																						1,	
																						reportName);
																log.info("Inside IF block actions results - "+status);
																statuslist.add(status);
																if (status=="0")
																{// to terminate if assert fails
																	reportName.log(LogStatus.FAIL, "Assertion failed");
																	break;
																}
															}
									}//for loop end
							}
						}
					}
					//else continue regular 
					else
					{//if regular action type is blank then skilp that row,because when if condtion is executed those rows should be ignored now
					boolean actionBreaker=false;
					try 
					{
						 actionBreaker=ExcelUtils.reader(sheethameToBExecuted, j, 2, path).toString().
								 toLowerCase().contains("blank value in");
						//System.out.println(breaker2);
					} 
					catch (Exception e) 
					{
						log.error("empty cells in method sheetExecutor ");
					}
						if (actionBreaker==true)
							{
								log.info("Blank value found in action type ,skipping this row");
								continue;
							}
						else
							{
								status = actionPerformer(j, wd, 
														sheethameToBExecuted, 
														path, 
														scPath, 
														repoPath,
														2,
														reportName);
								statuslist.add(status);
								if(status=="0")//if assert title fail break execution
												{
													reportName.log(LogStatus.FAIL, "Assertion failed");
													break;
												}
								
							}
					}
					}
					//System.out.println(status.toString());
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
	
	 public static String frmDriverLoopCreater(int i, 
												String path, 
												WebDriver wd, 
												String scPath, 
												String repoPath, 
												String sheethameToBExecuted, 
												long iteratorCount,
												ExtentTest	reportName) throws IOException, Exception {
		 	PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
			String status = null;
			List<String> statuslist = new ArrayList<>();
			//System.out.println("sheet executor is  called");
			// get the sheet name which will be executed
			log.info("executing sheet executor method");
			String sheetWiseResult=null; 
			//To execute suite of sheets number of times=iterator count
			for (int c = 1; c <= iteratorCount; c++)
			{
				//log.info("the value of c= "+c);
				//To execute sheets
				for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
					{
					log.info("the value of j= "+j);
					boolean checkIfCondition = false,breaker = false;
					//check whether "if" is present in 1st column or not,without this null pointer... will thrown
					log.info("Checking IF is present or not in first column");
					try 
					{
						checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toString().toLowerCase().contains("if");
						log.info("IF is present,Will check next column is pass or not");
					} 
					catch (Exception e) 
					{
						log.error("checkIfCondition in method sheetExecutor"+e);
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
										log.error("IF was present but,ActionType was passed blank");
									}
								//If blank value is there then break loop of special scenario,to set limit to for loop this condition is put
									if(breaker==true)
										{
											log.info("end of IF loop");
											break;
										}
									else
										{
											//if result is pass the execute special scenario,1st row result=pass then execute from below row
											if(actionPerformer(j, 
																wd, 
																sheethameToBExecuted, 
																path, 
																scPath, 
																repoPath,
																1,
																reportName).toLowerCase().contains("pass"))
												log.info("Inside IF block,action is pass");
											{
												//to execute inside if steps
												for (int k = j+1; k <=ExcelUtils.getRowCount(sheethameToBExecuted, path); k++)
													{
													//log.info("value k="+k);
													String val = "Blank value in";
														try 
															{
																 val = ExcelUtils.reader(sheethameToBExecuted, k, 1, path).toString();
															} 	
														catch (Exception e) 
															{
																log.error("empty actiontype method sheetExecutor");
															}
													
														//If blank value is there then break loop of special scenario
														if(val.contains("Blank value in"))
														{
															break;
														}
														else
																	{
																				status = actionPerformer(k, 
																										wd, 
																										sheethameToBExecuted, 
																										path,
																										scPath, 
																										repoPath,
																										1,	
																										reportName);
																				log.info("Inside IF block actions results - "+status);
																				statuslist.add(status);
																				if (status=="0")
																				{// to terminate if assert fails
																					reportName.log(LogStatus.FAIL, "Assertion failed");
																					break;
																				}
																			}
													}//for loop end
											}
										}
								}
							//else continue regular 
							else
							{//if regular action type is blank then skilp that row,because when if condtion is executed those rows should be ignored now
								boolean actionBreaker=false;
								try 
									{
										 actionBreaker=ExcelUtils.reader(sheethameToBExecuted, j, 2, path).toString().
												 toLowerCase().contains("blank value in");
										//System.out.println(breaker2);
									} 
								catch (Exception e) 
									{
										log.error("empty cells in method sheetExecutor ");
									}
										if (actionBreaker==true)
											{
												log.info("Blank value found in action type ,skipping this row");
												continue;
											}
										else
											{
												status = actionPerformer(j, wd, 
																		sheethameToBExecuted, 
																		path, 
																		scPath, 
																		repoPath,
																		2,
																		reportName);
												statuslist.add(status);
												if(status=="0")//if assert title fail break execution
																{
																	reportName.log(LogStatus.FAIL, "Assertion failed");
																	break;
																}
												
											}
							}
					}
				//System.out.println(status.toString());
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
	
	 


}
