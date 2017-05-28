/**
 * 
 */
package hybridFramework.webdriver;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JOptionPane;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.sikuli.api.robot.Env;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.KeyModifier;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.basics.Settings;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author partha
 *
 */
public class MultiGetelement  {
	static String parent = null;
	public static WebElement GetElement( int timelimit, 
										WebDriver wd, 
										final String value, 
										final String type, 
										long poll) throws Exception 
	{
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
		Wait<WebDriver>wait=new FluentWait<WebDriver>(wd)
				.withTimeout(timelimit, TimeUnit.SECONDS)
				.pollingEvery(poll, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class );
			
		WebElement element=wait.until(new Function<WebDriver, WebElement>()
			{
				public WebElement apply(WebDriver wd) 
				{
					System.out.println("polling");
					return GetElementSwitch(wd, value, type);
				}
				
			}
		);
			return element;
				
			 
	}
	public static void waitForLoad(WebDriver driver) {
		 Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		    wait.until(new Function<WebDriver, Boolean>() {
		        public Boolean apply(WebDriver driver) {
		               return (((JavascriptExecutor) driver).executeScript("return document.readyState"))
		                .equals("complete");
		        }
		    });
	}
	public static  WebElement GetElementSwitch(WebDriver wd, String value, String type) {
		WebElement elements = null;
		switch (type.toLowerCase()) {
		case "find by id":
			elements=wd.findElement(By.id(value));
			break;
		case "find by classname":
			elements = wd.findElement(By.className(value));
			break;
		case "find by css":
			elements = wd.findElement(By.cssSelector(value));
			break;
		case "find by linktext":
			elements = wd.findElement(By.linkText(value));
			break;
		case "find by name":
			elements = wd.findElement(By.name(value));
			break;
		case "find by partial linktext":
			elements = wd.findElement(By.partialLinkText(value));
			break;
		case "find by xpath":
			elements = wd.findElement(By.xpath(value));
			break;
		case "find by tagname":
			elements=wd.findElement(By.tagName(value));
			break;
		default:
			System.out.println(
					"unable to find using given value"+value+"-"+type);
			break;
		}
		return elements;
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
										String repoPath, Logger log										
										) throws EncryptedDocumentException, InvalidFormatException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
		long rowToRefer = 0;
	String status = null;
	try 
		{
		//TestExecutor ts=new TestExecutor();
			rowToRefer = TestExecutor.counter( i, sheetName, 3, path) - 1;
			 log.info("Given row to refer in object repository - "+rowToRefer);
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
		String findby = null;
		try
			{
				findby = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString();
			} 
		catch (Exception e1) 
			{
				e1.printStackTrace();
				reportName.log(LogStatus.FATAL,"findby value not found");
			}
		String findvalue = null;
		try 
			{
				findvalue = ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString();
			} 
		catch (Exception e1) 
			{
				e1.printStackTrace();
				reportName.log(LogStatus.FATAL,"element locator value not found");
			}
		String findvalue2=null;
		//if object value contains @@ then read the next column value and search that value in property file
		//used split string here ...divide string into 2 arrays
		if (findvalue.contains("@@")) {
			try 
				{
					findvalue2=ExcelUtils.reader(repoSheetname, (int) rowToRefer, 3, repoPath).toString();
				} 
			catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			String [] addDynamicId=findvalue.split("@@");
			String a1=addDynamicId[0];
			String a2=addDynamicId[1];//variableStore.properties
			
			String concatPropertyValue = "";
			try 
				{
					concatPropertyValue = ExcelUtils.propertyReader(System.getProperty("user.dir")+"/variableStore.properties", findvalue2);
				} 
			catch (Exception e) 
				{
					status = "FAIL " + e.getMessage();
					reportName.log(LogStatus.FAIL,"There is no element in property file for mentioned name in excel sheet");
				}
			if (ExcelUtils.propertyReader(System.getProperty("user.dir")+
					"/variableStore.properties", findvalue2).contains("getcurrentdate")) 
				{
					concatPropertyValue=Helpingfunctions.currentDate();
				}
			findvalue=a1+concatPropertyValue+a2;
		reportName.log(LogStatus.INFO,"splitting string,a1 value="+a1+" concatProperty Value= "+concatPropertyValue+" a2 value= "+a2);
			
		}
		System.out.println(findvalue);
		ele = MultiGetelement.GetElement(elementLoadTimeLimit,wd,findvalue,findby, 250);
			log.info("objectrepository row number= "+rowToRefer);
		
			String	time=Helpingfunctions.timeForName();
			String sendkeysvalue=time;
			String storeval=time;
					try {
							sendkeysvalue=TestExecutor.value(i, sheetName, path).toLowerCase();
						} 
					catch (Exception e) 
						{
						//if sendkeys value is blank then add time ,this is to avoid writing status to click
							
							if (TestExecutor.performType(i, sheetName, path).toLowerCase().contains("sendkeys"))
							{
							reportName.log(LogStatus.WARNING,status+" sendkeys cell is blank,we sent current time value for this");
							TestExecutor.statusWriter(i, sheetName, "WARNING : sendkeys cell is blank,we sent current time value for this", path, 6);
							}
						} 
									
			switch (TestExecutor.performType(i, sheetName, path).toLowerCase()) 
					{
			
					case "sendkeys":
						ele.sendKeys(sendkeysvalue);
						reportName.log(LogStatus.PASS,"Performing Sendkeys action with "+sendkeysvalue);
						break;
					
					case "addtextwithtime":
						ele.sendKeys(sendkeysvalue+"_"+time);
						reportName.log(LogStatus.PASS,"Performing Sendkeys appendname action with "+sendkeysvalue+"_"+time);
						break;
						
					case "addtextwithtimestore":// j th col
						try 
						{
							 storeval=ExcelUtils.reader(sheetName, i, 9, path).toString();
						} 
						catch (Exception e) 
						{
							reportName.log(LogStatus.WARNING,status+" store value(column 9) cell is blank,we sent current time value for this");
							TestExecutor.statusWriter(i, sheetName, "WARNING : store value(column 9) cell is blank,we sent current time value for this", path, 6);
						}
						ele.sendKeys(sendkeysvalue+"_"+time);
						ExcelUtils.propertyWriter(storeval, sendkeysvalue+"_"+time);
						reportName.log(LogStatus.PASS,"Performing Sendkeys appendname action with "+sendkeysvalue+"_"+time);
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
					default:
										JOptionPane.showMessageDialog( null, "Invalid keyword  defined", null, JOptionPane.ERROR_MESSAGE);
										break;
					}
	} 
	catch (Exception e) 
						{
							status = "FAIL " + e.getMessage();
							System.out.println(status);
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
									String repoPath,Logger log) throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
		String status="PASS";
	if (input.contains("find element")) 
	{
		ElementActions(i, sheetName, path, reportName, wd, scPath,elementLoadTimeLimit , repoSheetname, repoPath, log);
	}
	else
	switch (input) 
	{
					case "geturl":
									try 
									{
										wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
										wd.get(TestExecutor.locator(i, sheetName, 3, path));
										reportName.log(LogStatus.PASS,"Performing Get URL"+wd.getCurrentUrl());
										//System.out.println(wd.getCurrentUrl());
									} 
									catch (Exception e)
									{
										status = "FAIL " + e.getMessage();
										String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
									}
									break;
					case "stdclick":
									String imgpath = null;
									long sikuli_rowToRefer=0;
									try 
										{
											sikuli_rowToRefer = TestExecutor.counter( i, sheetName, 3, path) - 1;
											 imgpath=ExcelUtils.reader(repoSheetname, (int) sikuli_rowToRefer, 2, repoPath).toString();
											 log.info("row to refer - "+sikuli_rowToRefer+"imagpath="+imgpath);
										} 
									catch (Exception e) 
										{
											status = "FAIL " + e.getMessage();
											TestExecutor.statusWriter(i, sheetName, status, path, 6);
											reportName.log(LogStatus.FAIL,"Problem in finding sikuli image path");
										}
									
									try 
										{
											 Screen sc=new Screen();
											 Settings.BundlePath=DriverBaseTest.bundlepath;
											 Pattern pat=new Pattern(imgpath);
											 sc.wait(pat);
											 sc.click(pat);
											 reportName.log(LogStatus.PASS,"Performing std click");
											//System.out.println(wd.getCurrentUrl());
										} 
									catch (Exception e)
										{
											status = "FAIL " + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
									break;
									
					case "stddoubleclick":
								String imgpath1 = null;
								long sikuli_rowToRefer1=0;
								try 
									{
										sikuli_rowToRefer1 = TestExecutor.counter( i, sheetName, 3, path) - 1;
										 imgpath1=ExcelUtils.reader(repoSheetname, (int) sikuli_rowToRefer1, 2, repoPath).toString();
										 log.info("row to refer - "+sikuli_rowToRefer1+"imagpath="+imgpath1);
									} 
								catch (Exception e) 
									{
										status = "FAIL " + e.getMessage();
										TestExecutor.statusWriter(i, sheetName, status, path, 6);
										reportName.log(LogStatus.FAIL,"Problem in finding sikuli image path");
									}
								
								try 
									{
										 Screen sc=new Screen();
										 Settings.BundlePath=DriverBaseTest.bundlepath;
										 Pattern pat=new Pattern(imgpath1);
										 sc.wait(pat);
										 sc. doubleClick(pat);
										 reportName.log(LogStatus.PASS,"Performing double click");
										//System.out.println(wd.getCurrentUrl());
									} 
								catch (Exception e)
									{
										status = "FAIL " + e.getMessage();
										String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status);
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
									}
								break;
					case "stdrightclick":
						String imgpath4 = null;
						long sikuli_rowToRefer4=0;
						try 
							{
								sikuli_rowToRefer4 = TestExecutor.counter( i, sheetName, 3, path) - 1;
								 imgpath4=ExcelUtils.reader(repoSheetname, (int) sikuli_rowToRefer4, 2, repoPath).toString();
								 log.info("row to refer - "+sikuli_rowToRefer4+" imagpath="+imgpath4);
							} 
						catch (Exception e) 
							{
								status = "FAIL " + e.getMessage();
								TestExecutor.statusWriter(i, sheetName, status, path, 6);
								reportName.log(LogStatus.FAIL,"Problem in finding sikuli image path");
							}
						
						try 
							{
								 Screen sc=new Screen();
								 Settings.BundlePath=DriverBaseTest.bundlepath;
								 Pattern pat=new Pattern(imgpath4);
								 Thread.sleep(250);
								 sc.rightClick(pat);
								 reportName.log(LogStatus.PASS,"Performing std rightclink");
								//System.out.println(wd.getCurrentUrl());
							} 
						catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
						break;
					case "stdtype":
									String imgpath3 = null,typeval=null;;
									long sikuli_rowToRefer3=0;
									try 
										{
											typeval=TestExecutor.value(i, sheetName, path).toLowerCase();
											sikuli_rowToRefer3 = TestExecutor.counter( i, sheetName, 3, path) - 1;
											 imgpath3=ExcelUtils.reader(repoSheetname, (int) sikuli_rowToRefer3, 2, repoPath).toString();
											 log.info("row to refer - "+sikuli_rowToRefer3+"imagpath="+imgpath3);
										} 
									catch (Exception e)
										{
											status = "FAIL " + e.getMessage();
											TestExecutor.statusWriter(i, sheetName, status, path, 6);
											reportName.log(LogStatus.FAIL,"Problem in finding sikuli image path");
										}
									
									try 
										{
											 Screen sc=new Screen();
											 Settings.BundlePath=DriverBaseTest.bundlepath;
											 Pattern pat=new Pattern(imgpath3);
											 sc.wait(pat);
											 Keyboard k=new DesktopKeyboard();
											 k.type(typeval);
											 reportName.log(LogStatus.PASS,"Performing stand alone type action");
											//System.out.println(wd.getCurrentUrl());
										} 
									catch (Exception e)
										{
											status = "FAIL " + e.getMessage();
											String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
											reportName.log(LogStatus.FAIL,status);
											reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										}
										break;
					case "clickenter":
						try 
							{
									Robot r=new Robot();
									r.keyPress(KeyEvent.VK_ENTER);
									r.keyRelease(KeyEvent.VK_ENTER);
									reportName.log(LogStatus.PASS,"Performing enter action");
								//System.out.println(wd.getCurrentUrl());
							} 
						catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
							
					case "selectall":
						try 
							{
								 Keyboard k=new DesktopKeyboard();
								 k.keyDown(Key.CTRL);
								 k.type("a");
								 k.keyUp(Key.CTRL);
								 reportName.log(LogStatus.PASS,"Performing select all action");
								//System.out.println(wd.getCurrentUrl());
							} 
						catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
					case "copy":
						try 
							{
								 Keyboard k=new DesktopKeyboard();
								 k.copy();
								 reportName.log(LogStatus.PASS,"Performing TEXT COPY action");
								//System.out.println(wd.getCurrentUrl());
							} 
						catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
					case "paste":
						try 
							{
								 Keyboard k=new DesktopKeyboard();
								 k.keyDown(Key.CTRL);
								 k.type("v");
								 k.keyUp(Key.CTRL);
								 reportName.log(LogStatus.PASS,"Performing PASTE action");
								//System.out.println(wd.getCurrentUrl());
							} 
						catch (Exception e)
							{
								status = "FAIL " + e.getMessage();
								String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
								reportName.log(LogStatus.FAIL,status);
								reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
							}
							break;
					case "robotupload":// D
						String filepath = null;
						long _rowToRefr=0;
						try 
							{
							_rowToRefr = TestExecutor.counter( i, sheetName, 3, path) - 1;
							filepath=ExcelUtils.reader(repoSheetname, (int) _rowToRefr, 2, repoPath).toString();
								 log.info("row to refer - "+_rowToRefr+"imagpath="+filepath);
							} 
						catch (Exception e) 
							{
								status = "FAIL " + e.getMessage();
								TestExecutor.statusWriter(i, sheetName, status, path, 6);
								reportName.log(LogStatus.FAIL,"Problem in finding sikuli image path");
							}
						
						try 
							{
							Thread.sleep(2000);
							Robot r=new Robot();
							r.keyPress(KeyEvent.VK_CONTROL);
							r.keyPress(KeyEvent.VK_V);
							r.keyRelease(KeyEvent.VK_V);
							r.keyRelease(KeyEvent.VK_CONTROL);
							r.keyPress(KeyEvent.VK_ENTER);
							r.keyRelease(KeyEvent.VK_ENTER);
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
										reportName.log(LogStatus.INFO, "Details on /webdriver/test-output/linklist.html  Checking all links in page - "+PageTitle);
										List<WebElement> hostList=wd.findElements(By.tagName("a"));
										reportName.log(LogStatus.INFO, "Number of links in this page - "+hostList.size());
										String value="href";
										threads.test(hostList, value); 
										 
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
										reportName.log(LogStatus.INFO, "Details on /webdriver/test-output/linklist.html Checking all Images in page - "+PageTitle);
										List<WebElement> hostList=wd.findElements(By.tagName("img"));
										reportName.log(LogStatus.INFO, "Number of links in this page - "+hostList.size());
										String value="src";
										threads.test(hostList, value); 
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
											reportName.log(LogStatus.INFO, "Page title  - "+title);
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
											reportName.log(LogStatus.INFO, "Switching to windowindex "+windowIndex);
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
											reportName.log(LogStatus.INFO,"Closing window" );
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
													reportName.log(LogStatus.INFO,"storing current window handle as parent" );
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
													reportName.log(LogStatus.INFO,"Switching to parent window" );
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
														 reportName.log(LogStatus.INFO,"switching to active element" );
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
														 reportName.log(LogStatus.INFO,"switch to default content" );
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
															 reportName.log(LogStatus.INFO,"switch to frame with row num"+rowToRefer );
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
														org.openqa.selenium.Alert al=wd.switchTo().alert();
																if (TestExecutor.performType(i, sheetName, path).toLowerCase()=="dismiss")
																	{
																	al.dismiss();
																	} 
																else 
																	{
																		al.accept();
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
													waitForLoad(wd);
													String actual_title=wd.getTitle();
													String breaker="0";
															if (TestExecutor.locator(i, sheetName, 3, path).equalsIgnoreCase(actual_title)) 
																{
																	TestExecutor.statusWriter(i, sheetName,actual_title , path, 5);
																	reportName.log(LogStatus.INFO,"assert title "+actual_title);
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
					case "assertelement":
						
						long rowToRefer = 0;
												try 
														{
															rowToRefer = TestExecutor.counter( i, sheetName, 3, path) - 1;
															log.info("Given row to refer in object repository - "+rowToRefer);
														} 
												catch (Exception e) 
												{
															status = "FAIL " + e.getMessage();
															TestExecutor.statusWriter(i, sheetName, status, path, 6);
															reportName.log(LogStatus.FAIL,"row to refer is blank");
												}
									String breaker2="0";
									
									try 
												{
										reportName.log(LogStatus.PASS,"Performing Element Assertion");
													MultiGetelement.GetElement(elementLoadTimeLimit,wd,
														ExcelUtils.reader(repoSheetname, (int) rowToRefer, 2, repoPath).toString(),
														ExcelUtils.reader(repoSheetname, (int) rowToRefer, 1, repoPath).toString(), 5).isDisplayed();
													
												} 
									catch (Exception e) 
									{
										status = "FAIL "+e;
										TestExecutor.statusWriter(i, sheetName, status, path, 6);
										String sspath=Helpingfunctions.takeScreenShot(wd, scPath);
										reportName.log(LogStatus.FAIL,status+"Assert element failed");
										reportName.log(LogStatus.FAIL,reportName.addScreenCapture(sspath));
										return breaker2;
									}//breaker will send value 0,that will break loop
						break;
					case "drag drop by element":
												try 
													{
														long SourceRowToRefer = TestExecutor.counter(i, sheetName, 4, path)-1;
														long dropRowToRefer = TestExecutor.counter(i, sheetName, 5, path)-1;
														reportName.log(LogStatus.INFO,"Performing drag drop by element "+SourceRowToRefer+" "+dropRowToRefer);
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
																reportName.log(LogStatus.INFO,"Performing drag drop by coordinates "+SourceRowToRefer+" "+xOffset+" "+yOffset);
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
															reportName.log(LogStatus.INFO,"sleeping for "+sleeptime+"milliseconds");
														} 
													catch (InterruptedException e1)
														{
															e1.printStackTrace();
															status = "FAIL " + e1.getMessage();
															TestExecutor.statusWriter(i, sheetName, status, path, 6);
														}
													break;
	default :
		JOptionPane.showMessageDialog( null, "Invalid keyword  defined", null, JOptionPane.ERROR_MESSAGE);
		break;									
	}
	return status;
	} 
}




