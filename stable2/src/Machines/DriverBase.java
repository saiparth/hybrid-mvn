package Machines;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DriverBase extends TestExecutor {
	static String propertiesFilepath= "F:\\stable\\config.properties";;
	File file=new File(propertiesFilepath);
	@BeforeTest
	public void fileExistChecker() throws Exception{
		if (!file.exists()) {
			throw new Exception("Base file not found");
		}
	}
	
	static double iteratorCount = 1;
	static Logger log=Logger.getLogger(DriverBase.class);
	@Test
	public  void mainRunner() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		String path = ExcelUtils.propertyReader(propertiesFilepath, "path");
		log.info("excution sheet path ="+path);
		String scPath = ExcelUtils.propertyReader(propertiesFilepath, "scPath");
		log.info("excution sc reen shot path ="+scPath);
		String repoPath = ExcelUtils.propertyReader(propertiesFilepath, "repoPath");
		log.info("excution object repository path ="+repoPath);
		WebDriver driver = null ;

		// to execute scenario's
		String executorSheetName = "suite";

		// loop through sheet suite
		for (int i = 1; i <= ExcelUtils.getRowCount(executorSheetName, path); i++) {
			// check which sheet should be executed
			if (TestExecutor.SpecialActionType(i, executorSheetName, path).equalsIgnoreCase("YES")) 
			{
				String status = "PASS";
				// check which browser to be used
				switch (TestExecutor.ActionType(i, executorSheetName, path).toLowerCase()) {
				case "firefox":
					log.info("Starting execution in Firefox");
					System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
					driver = new FirefoxDriver();
					break;
				case "chrome":
					log.info("Starting execution in Chrome");
					System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
					driver = new ChromeDriver();
					break;
				case "internet explorer":
				System.setProperty("webdriver.ie.driver", "F:\\libs\\IEDriverServer.exe");
					log.info("Starting execution in IE");
					DesiredCapabilities dis=DesiredCapabilities.internetExplorer();
					dis.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					driver = new InternetExplorerDriver(dis);
					break;
				case "phantom js":
					log.info("Starting execution in Phantom JS");
					DesiredCapabilities cap = DesiredCapabilities.phantomjs();
					cap.setJavascriptEnabled(true);
					//System.setProperty("webdriver.chrome.driver", "F:\\eclipse new\\eclipse\\phantomjs.exe");
					driver = new PhantomJSDriver();
					break;
				default:
					System.out.println("unsupported browser"+TestExecutor.ActionType(i, executorSheetName, path).toLowerCase());
					break;
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
				String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
				//to check how many number of time sheet should execute
				try 
				{
					iteratorCount = (double) ExcelUtils.reader(executorSheetName, i, 4, path);
					System.out.println(iteratorCount);
				} 
				catch (Exception e) 
				{
					log.error("no iteratorCount given,Sheet will be executed 1 time");
				}
				long iteratorCountLong = (long) iteratorCount;
				
						status = sheetExecutor(i, path, driver, scPath, repoPath, sheethameToBExecuted,
								iteratorCountLong);
					System.out.println("status "+status);
						driver.quit();			
					if (status.toString().contains("FAIL"))
					{
						TestExecutor.statusWriter(i, executorSheetName, "FAIL", path, 3);
					} else {
						TestExecutor.statusWriter(i, executorSheetName, "PASS", path, 3);
					}
				
			}else {
				TestExecutor.statusWriter(i, executorSheetName, "SKIPPED", path, 3);
			}
		}
		
	}
}
