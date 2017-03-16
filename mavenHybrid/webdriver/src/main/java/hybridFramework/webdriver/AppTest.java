package hybridFramework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Hello world!
 *
 */
public class AppTest 
{
	 private WebDriver driver;

	    @BeforeClass
	    public static void setupClass() {
	    }

	    @Test
	    public void setupTest() {
	        driver = new ChromeDriver();
	        driver.get("www.google.com");
	    }

}
