package hybridFramework.webdriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DriverBase extends TestExecutor {
	static String propertiesFilepath= System.getProperty("user.dir")+"/config.properties";
	
	File file=new File(propertiesFilepath);
	
	@BeforeTest
	public void fileExistChecker() throws Exception
		{
			if (!file.exists()) 
				{
					System.out.println(file);
					throw new Exception("Base file not found");
				}
			//Flush old status
			String path = System.getProperty("user.dir")+"/excelLib.xlsx";
			String executorSheetName = "suite";
			for (int i = 1; i <= ExcelUtils.getRowCount(executorSheetName, path); i++) 
				
			{
				TestExecutor.statusWriter(i, executorSheetName, "",path, 3);
				System.out.println("flushing");
				if (TestExecutor.SpecialActionType(i, executorSheetName, path).equalsIgnoreCase("YES")) 
					{
					String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
					for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
								{
									TestExecutor.statusWriter(i, sheethameToBExecuted, "",path, 6);
									//System.out.println("flushing");
								}
					}
				}
			}
		
	static double iteratorCount = 1;
	static Logger log=Logger.getLogger(DriverBase.class);
	
	@Test
	public  static void mainRunner()  
	{
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");
		String path = System.getProperty("user.dir")+"/excelLib.xlsx";
		log.info("excution sheet path ="+path);
		String scPath = ExcelUtils.propertyReader(propertiesFilepath, "scPath");
		log.info("excution sc reen shot path ="+scPath);
		String repoPath = System.getProperty("user.dir")+"/objectrepository.xlsx";
		log.info("excution object repository path ="+repoPath);
	
		//extent path, it will allow only 3 or 4 column,
		//o decide in beginning what u wants 
		ExtentReports	ex=new ExtentReports(System.getProperty("user.dir")+"/test-output/executionReport.html");
		ExtentTest test = null;
		// to execute scenario's
		String executorSheetName = "suite";
		test =  ex.startTest("Browser intialization details");
		// loop through sheet suite
		try {
			for (int i = 1; i <= ExcelUtils.getRowCount(executorSheetName, path); i++) {
				// check which sheet should be executed
				WebDriver driver = null ;
				try {
					//run status
					if (TestExecutor.SpecialActionType(i, executorSheetName, path).equalsIgnoreCase("YES")) 
					{
						String status = "PASS";  
						test.log(LogStatus.INFO, "Checking browser details");
						// check which browser to be used
							
						try {
									switch (TestExecutor.ActionType(i, executorSheetName, path).toLowerCase())
									{
										case "firefox":
														test.log(LogStatus.INFO, "Starting test in Firefox");
														System.setProperty("webdriver.gecko.driver", "D:\\libs\\geckodriver.exe");
														driver = new FirefoxDriver();
														break;
										case "chrome":
														test.log(LogStatus.INFO, "Starting test in Chrome");
														System.setProperty("webdriver.chrome.driver", "D:\\libs\\chromedriver.exe");
														driver = new ChromeDriver();
														break;
										case "internet explorer":
														System.setProperty("webdriver.ie.driver", "D:\\libs\\IEDriverServer.exe");
														test.log(LogStatus.INFO, "Starting test in IE");
														DesiredCapabilities dis=DesiredCapabilities.internetExplorer();
														dis.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
														driver = new InternetExplorerDriver(dis);
														break;
										case "phantom js":
														test.log(LogStatus.INFO, "Starting test in Phantom JS");
														//System.setProperty("webdriver.phantomjs.driver", "D:\\libs\\phantomjs.exe");
														DesiredCapabilities cap = DesiredCapabilities.phantomjs();
														cap.setJavascriptEnabled(true);
														driver = new PhantomJSDriver();
														break;
										default:
														test.log(LogStatus.INFO, "No browser was provided,Starting in chrome");
														System.setProperty("webdriver.chrome.driver", "D:\\libs\\chromedriver.exe");
														driver = new ChromeDriver();
														break;
									}
							} 
						catch (EncryptedDocumentException e1)
							{
									e1.printStackTrace();
									status="FAIL"+e1;
							} 
						catch (InvalidFormatException e1) 
							{
									e1.printStackTrace();
									status="FAIL"+e1;
							} 
						catch (IOException e1)
							{
									e1.printStackTrace();
									status="FAIL"+e1;
							}
															driver.manage().window().maximize();
															driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
															driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
															//get which sheet should be executed
															String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
															test.log(LogStatus.INFO, "Starting test using sheet - "+sheethameToBExecuted);
															//to check how many number of time sheet should execute
															try 
																{
																	iteratorCount = (double) ExcelUtils.reader(executorSheetName, i, 4, path);
																} 
															catch (Exception e) 
																{
																	log.info("no iteratorCount given,Sheet will be executed 1 time");
																	test.log(LogStatus.INFO,"No count present,Sheet will be executed 1 time");
																}
																		long iteratorCountLong = (long) iteratorCount;
																		ExtentTest	reportName =  ex.startTest(TestExecutor.ActionType(i, 
																								  executorSheetName, path)+" "+sheethameToBExecuted);
																		
																		status = frmDriverLoopCreater( i, 
																								path, 
																								driver, 
																								scPath, 
																								repoPath, 
																								sheethameToBExecuted,
																								iteratorCountLong,
																								reportName);
																		System.out.println(status);
																		driver.quit();	
											
																		test.log(LogStatus.INFO,"Browser closed succesfully");
																		if (status.toString().contains("FAIL"))
																				{
																					TestExecutor.statusWriter(i, executorSheetName, status.toString(), path, 3);
																					reportName.log(LogStatus.FAIL,sheethameToBExecuted , "Failed");
																				}
																		else 
																				{
																					TestExecutor.statusWriter(i, executorSheetName, "PASS", path, 3);
																					reportName.log(LogStatus.PASS,sheethameToBExecuted, status);
																				}
							ex.endTest(reportName);
					}
					else
						{
							TestExecutor.statusWriter(i, executorSheetName, "SKIPPED", path, 3);
							test.log(LogStatus.SKIP,executorSheetName+" Skipped");
						}
				} 
				catch (EncryptedDocumentException e) 
				{
					try 
						{
							TestExecutor.statusWriter(i, executorSheetName, "SKIPPED", path, 3);
						} 
					catch (EncryptedDocumentException e1)
						{
							e1.printStackTrace();
						} 
					catch (InvalidFormatException e1) 
						{
							e1.printStackTrace();
						}
				} 
				catch (InvalidFormatException e) 
					{
						e.printStackTrace();
					} 
				catch (IOException e) 
					{
						e.printStackTrace();
					} 
				catch (Exception e) 
					{
						e.printStackTrace();
					}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		ex.endTest(test);
		ex.flush();
		
	}
}
