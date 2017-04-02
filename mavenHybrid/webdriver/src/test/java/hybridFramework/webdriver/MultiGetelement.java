/**
 * 
 */
package hybridFramework.webdriver;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


/**
 * @author partha
 *
 */
public class MultiGetelement  {
	static String parent = null;
	public static WebElement GetElement( int timelimit, WebDriver wd, String value, String type, long poll) throws InterruptedException {
		List<WebElement> ele = null;
		boolean status=false;
		for (int i = 0; i <= timelimit; i++)
		{
			try 
				{
					  ele = GetElements(wd, value, type);
				} 
			catch (Exception e)
			{
			}
			if (ele.size()>0) 
					{
						status=true;
						break;
					}
			System.out.println(i);
			 Thread.sleep(poll);
		}
			if (status==true)
				{
					return ele.get(0);
				} 
			else 
				{
					return null;
				}
		
	}

	public static  List<WebElement> GetElements(WebDriver wd, String value, String type) {
		List<WebElement> elements = null;
		switch (type.toLowerCase()) {
		case "find by id":
			elements=wd.findElements(By.id(value));
			break;
		case "find by classname":
			elements = wd.findElements(By.className(value));
			break;
		case "find by css":
			elements = wd.findElements(By.cssSelector(value));
			break;
		case "find by linktext":
			elements = wd.findElements(By.linkText(value));
			break;
		case "find by name":
			elements = wd.findElements(By.name(value));
			break;
		case "find by partial linktext":
			elements = wd.findElements(By.partialLinkText(value));
			break;
		case "find by xpath":
			elements = wd.findElements(By.xpath(value));
			break;
		case "find by tagname":
			elements=wd.findElements(By.tagName(value));
			break;
		default:
			System.out.println(
					"unable to find using given value"+value+"-"+type);
			break;
		}
		return elements;
	}
	public static String ElementActions(	int i, 
										String sheetName, 
										String path, 
										ExtentTest reportName, 
										WebDriver wd, 
										String scPath, 
										int elementLoadTimeLimit, 
										String repoSheetname, 
										String repoPath										
										) throws EncryptedDocumentException, InvalidFormatException, IOException {
	long rowToRefer = 0;
	String status = null;
	try 
		{
		//TestExecutor ts=new TestExecutor();
			rowToRefer = TestExecutor.counter( i, sheetName, 3, path) - 1;
			reportName.log(LogStatus.INFO,"Given row to refer in object repository - "+rowToRefer);
		} 
	catch (Exception e) 
		{
			status = "FAIL " + e.getMessage();
			TestExecutor.statusWriter(i, sheetName, status, path, 6);
			String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
			reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath),status);
		}
	WebElement ele = null;
	try {
		ele = MultiGetelement.GetElement(elementLoadTimeLimit,wd,
										ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString(),
										ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
										reportName.log(LogStatus.INFO,"objectrepository row number= "+rowToRefer);
		
		switch (TestExecutor.performType(i, sheetName, path).toLowerCase()) 
					{
					case "sendkeys":
						ele.sendKeys(TestExecutor.value(i, sheetName, path));
						reportName.log(LogStatus.PASS,"Performing Sendkeys action with "+TestExecutor.value(i, sheetName, path));
						break;
					case "click":
						ele.click();
						reportName.log(LogStatus.PASS,"Performing Click action");
						break;
					case "clear":
						ele.clear();
						reportName.log(LogStatus.PASS,"Performing Clear action");
						break;
					 
					case "isdisplayed":
										try 
											{
												ele.isDisplayed();
											} 
										catch (Exception e) 
											{
												String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
												reportName.log(LogStatus.FAIL,status);
												reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
											}
										break;
					
					case "mousehover":
										new Actions(wd).moveToElement(ele).build().perform();
										reportName.log(LogStatus.PASS,"Performing mouse hover action");
										break;
										
					case "doubleclick":
										new Actions(wd).doubleClick(ele);
										reportName.log(LogStatus.PASS,"Performing double click action");
										break;
					}
	} 
	catch (Exception e) 
						{
							status = "FAIL " + e.getMessage();
							TestExecutor.statusWriter(i, sheetName, status, path, 6);
							String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
							reportName.log(LogStatus.FAIL,status);
							reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
						}
	return status;
	}
	
	public static String webActionPerformer(	String input, 
									WebDriver wd, 
									int i, 
									String sheetName, 
									String path, 
									ExtentTest reportName,
									String scPath, 
									int elementLoadTimeLimit, 
									String repoSheetname, 
									String repoPath) throws EncryptedDocumentException, InvalidFormatException, IOException {
	String status="PASS";
	if (input.contains("find element")) 
	{
		ElementActions(i, sheetName, path, reportName, wd, scPath,elementLoadTimeLimit , repoSheetname, repoPath);
	}
	else
	switch (input) 
	{
					case "geturl":
									try 
									{
										wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
										wd.get(TestExecutor.locator(i, sheetName, 3, path));
										System.out.println(wd.getCurrentUrl());
									} 
									catch (Exception e)
									{
										status = "FAIL " + e.getMessage();
										String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
									}
									break;
					case "checkalllinks":
									try {
										String PageTitle=wd.getTitle();
										reportName.log(LogStatus.INFO, "Checking all links in page - "+PageTitle);
										List<WebElement> Linkelements=wd.findElements(By.tagName("a"));
											for (WebElement link : Linkelements) 
												{
													String url=link.getAttribute("href");
													Helpingfunctions.linkValidator(url,reportName);
												}
										reportName.log(LogStatus.PASS,"checking all links");
										} 
									catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
									break;
									
					case "checkallimages":
									try {
										String PageTitle=wd.getTitle();
										reportName.log(LogStatus.INFO, "Checking all images in page - "+PageTitle);
										List<WebElement> Imgelements=wd.findElements(By.tagName("img"));
												for (WebElement img : Imgelements) 
														{
															String image=img.getAttribute("src");
															Helpingfunctions.linkValidator(image,reportName);
														}
											reportName.log(LogStatus.PASS,"checking all images");
										} 
									catch (Exception e)
										{
											status = "FAIL " + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
										break;
					
					case "gettitle":
									try
										{
											String title = wd.getTitle();
											//System.out.println(title);
											TestExecutor.statusWriter(i, sheetName, title, path, 5);
										} 
									catch (Exception e) 
										{
											status = "FAIL" + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));								}
										break;
						
					case "switchtowindow":
									try 
										{
											long windowIndex=TestExecutor.counter(i, sheetName, 3, path);
											Helpingfunctions.windowSwitcher(wd, windowIndex);
										} 
									catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
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
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
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
													TestExecutor.statusWriter(i, sheetName, status, path, 6);
													String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
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
													TestExecutor.statusWriter(i, sheetName, status, path, 6);
													String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
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
														TestExecutor.statusWriter(i, sheetName, status, path, 6);
														String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
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
														TestExecutor.statusWriter(i, sheetName, status, path, 6);
														String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
														reportName.log(LogStatus.FAIL,status);
														reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													}
													break;
					case "switchtoframe":
													try 
														{	
															long rowToRefer = TestExecutor.counter(i, sheetName, 3, path)-1;
															WebElement wwq=MultiGetelement.GetElement
															(elementLoadTimeLimit,wd, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString()
															, ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5);
															 wd.switchTo().frame(wwq);
														} 
													catch (Exception e)
														{
															status = "FAIL" + e.getMessage();
															TestExecutor.statusWriter(i, sheetName, status, path, 6);
															String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
															reportName.log(LogStatus.FAIL,status);
															reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
														}
													break;
					case "debug":
													try 
														{	
														JOptionPane.showMessageDialog(null, "after clicking ok test will proceed");
														} 
													catch (Exception e)
														{
															status = "FAIL" + e.getMessage();
															TestExecutor.statusWriter(i, sheetName, status, path, 6);
															String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
															reportName.log(LogStatus.FAIL,status);
															reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
														}
													break;
					case "alert":
													try {
														Alert al=wd.switchTo().alert();
																if (TestExecutor.performType(i, sheetName, path).toLowerCase()=="accept")
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
														TestExecutor.statusWriter(i, sheetName, status, path, 6);
														String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
														reportName.log(LogStatus.FAIL,status);
														reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
													}
													break;
					case "asserttitle":
													String actual_title=wd.getTitle().toLowerCase();
													String breaker="0";
															if (TestExecutor.performType(i, sheetName, path).toLowerCase().contains(actual_title)) 
																{
																	TestExecutor.statusWriter(i, sheetName,actual_title , path, 5);
																}
															else 
																{
																	status = "FAIL,actual title is " + wd.getTitle();
																	TestExecutor.statusWriter(i, sheetName, status, path, 6);
																	String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
																	reportName.log(LogStatus.FAIL,status);
																	reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
																	return breaker;
																}//breaker will send value 0,that will break loop
													break;
					case "drag drop by element":
												try 
													{
														long SourceRowToRefer = TestExecutor.counter(i, sheetName, 4, path)-1;
														long dropRowToRefer = TestExecutor.counter(i, sheetName, 5, path)-1;
														WebElement drag = MultiGetelement.GetElement
																(elementLoadTimeLimit,wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
																, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
														
														WebElement drop=MultiGetelement.GetElement
																(elementLoadTimeLimit,wd, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 2, repoPath).toString()
																, ExcelUtils.reader(repoSheetname, (int) dropRowToRefer, 1, repoPath).toString(), 5);
														Actions ac=new Actions(wd);
														ac.dragAndDrop(drag, drop).build().perform();
													}
												catch (Exception e) 
																	{
																		status = "FAIL " + e.getMessage();
																		TestExecutor.statusWriter(i, sheetName, status, path, 7);
																		String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
																		reportName.log(LogStatus.FAIL,status);
																		reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));			
																	}
														break;
					case "drag drop by coordinates":
													try 
															{
																long SourceRowToRefer = TestExecutor.counter(i, sheetName, 3, path)-1;
																int xOffset = (int) (TestExecutor.counter(i, sheetName, 4, path));
																int yOffset = (int)TestExecutor.counter(i, sheetName, 5, path);
																WebElement drag = MultiGetelement.GetElement
																		(elementLoadTimeLimit,wd, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 2, repoPath).toString()
																		, ExcelUtils.reader(repoSheetname, (int) SourceRowToRefer, 1, repoPath).toString(), 5);
																
																try 
																{
																	Actions ac=new Actions(wd);
																	ac.dragAndDropBy(drag, xOffset, yOffset).build().perform();
																} 
														catch (Exception e) 
																{
																	status = "FAIL " + e.getMessage();
																	TestExecutor.statusWriter(i, sheetName, status, path, 7);
																	String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
																	reportName.log(LogStatus.FAIL,status);
																	reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));				
																}
															} 
													catch (Exception e) 
															{
																status = "FAIL " + e.getMessage();
																TestExecutor.statusWriter(i, sheetName, status, path, 6);
																String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
																reportName.log(LogStatus.FAIL,status);
																reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));				
															}
													
													break;
					case "sleep":
													long sleeptime = 1000;
													try
														{
															 sleeptime = TestExecutor.counter(i, sheetName, 3, path);
														} 
													catch (Exception e)
														{
															System.out.println("problem in rendering sleeptime,Used default as 1 second");
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
															TestExecutor.statusWriter(i, sheetName, status, path, 6);
														}
													break;
	
											}
	return status;
	} 
}




