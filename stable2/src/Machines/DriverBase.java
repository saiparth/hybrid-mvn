package Machines;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverBase extends TestExecutor {
	static String propertiesFilepath = "F:\\stable\\config.properties";
	static double iteratorCount = 1;

	public static void main(String[] args) throws IOException, IllegalStateException, InvalidFormatException {
		String path = ExcelUtils.propertyReader(propertiesFilepath, "path");
		String scPath = ExcelUtils.propertyReader(propertiesFilepath, "scPath");
		String repoPath = ExcelUtils.propertyReader(propertiesFilepath, "repoPath");
		WebDriver wd = null;

		// to execute scenario's
		String executorSheetName = "suite";

		// loop through sheet suite
		for (int i = 1; i <= ExcelUtils.getRowCount(executorSheetName, path); i++) {
			// check which sheet should be executed
			if (TestExecutor.SpecialActionType(i, executorSheetName, path).contains("YES")) {
				String status = "PASS";
				// check which browser to be used
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
				wd.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
				String sheethameToBExecuted = TestExecutor.SpecialFunctions(i, executorSheetName, path);
				try 
				{
					iteratorCount = (double) ExcelUtils.reader(executorSheetName, i, 4, path);
					System.out.println(iteratorCount);
				} 
				catch (Exception e) 
				{
					System.out.println("no iteratorCount given,Sheet will be executed 1 time");
				}
				long iteratorCountLong = (long) iteratorCount;

				status = sheetExecutor(i, path, wd, scPath, repoPath, status, sheethameToBExecuted,
						iteratorCountLong);
				
				if (status == "PASS")
				{
					TestExecutor.statusWriter(i, executorSheetName, "PASS", path, 3);
				} 
				
				else 
				{
					TestExecutor.statusWriter(i, executorSheetName, "FAIL", path, 3);
				}
				
			} else {
				TestExecutor.statusWriter(i, executorSheetName, "SKIPPED", path, 3);
			}

		}
		wd.quit();
	}
}
