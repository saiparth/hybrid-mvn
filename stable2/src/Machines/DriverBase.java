package Machines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class DriverBase extends TestExecutor {
	static String propertiesFilepath = "F:\\stable\\config.properties";
	static double iteratorCount=1;
	public static void main(String[] args) throws IOException, IllegalStateException, InvalidFormatException {
		//String sheetName = ExcelUtils.propertyReader(propertiesFilepath, "sheetname");
		String path = ExcelUtils.propertyReader(propertiesFilepath, "path");
		String scPath = ExcelUtils.propertyReader(propertiesFilepath, "scPath");
		String repoPath = ExcelUtils.propertyReader(propertiesFilepath, "repoPath");
		WebDriver wd = null;
		
		// to execute scenario's
		String executorSheetName = "suite";
		
		//loop through sheet suite 
		for (int i = 1; i <= ExcelUtils.getRowCount(executorSheetName, path); i++) {
			// check which sheet should be executed
			if (TestExecutor.SpecialActionType(i, executorSheetName, path).contains("YES")) 
			{
				
				String status = "PASS";
				//check which browser to be used
				switch (TestExecutor.ActionType(i, executorSheetName, path).toLowerCase()) {
				case "firefox":
					System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
					wd = new FirefoxDriver();
					break;
				case "chrome":
					System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
					wd = new ChromeDriver();
					break;
				case "internet explorer":
					wd = new InternetExplorerDriver();
					break;
				case "phantom js":
					DesiredCapabilities dis = DesiredCapabilities.phantomjs();
					dis.setJavascriptEnabled(true);
					wd = new PhantomJSDriver();
				default:
					System.out.println("unsupported browser");
					break;
				}
			
				wd.manage().window().maximize();
				wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				//to write total scenario status,put all action steps into this list
				List<String> statuslist = new ArrayList<>();
				// get the sheet name which will be executed
				String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
				try
				{
					iteratorCount=(double)ExcelUtils.reader(executorSheetName,i,4,path);
					System.out.println(iteratorCount);
				}
				catch (Exception e) 
				{
					System.out.println("no iteratorCount given,Sheet will be executed 1 time");
				}
				
				long f=(long)iteratorCount;
				
				//To execute suite of sheets number of times=iterator count
				for (int c = 1; c <= f; c++)
				{
					//To execute sheets
					for (int j = 1; j <= ExcelUtils.getRowCount(sheethameToBExecuted, path); j++) 
						{
						boolean checkIfCondition = true,breaker = true,breaker2 = true;
						//check whether "if" is present in 1st column or not,without this null pointer... will thrown
						try 
						{
							checkIfCondition=TestExecutor.SpecialFunctions(j, sheethameToBExecuted, path).toLowerCase().contains("if");
							System.out.println(checkIfCondition);
						} 
						catch (Exception e) 
						{
							System.out.println("if is blank");
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
										System.out.println("breaker found in action type");
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
											System.out.println("breaker found in special action type");
										}
										//If blank value is there then break loop of special scenario
											if(breaker2==true)
											{
												System.out.println("breaker2");
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
				
				if (statuslist.toString().contains("FAIL"))
					{
					TestExecutor.statusWriter(i, executorSheetName, "FAIL", path, 3);
					}
				
				else 
					{
					TestExecutor.statusWriter(i, executorSheetName, "PASS", path, 3);
					}
			
				}
			else 
			{
				TestExecutor.statusWriter(i, executorSheetName, "SKIPPED", path, 3);
			}

	
	}
		wd.quit();	}
}
