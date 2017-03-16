package lab;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class marioTest {
	@Test
	public  void mainh() throws InterruptedException {
		WebDriver wd;
		// System.setProperty("webdriver.gecko.driver",
		// "F:\\libs\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "D:\\libs\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.manage().window().maximize();
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wd.get("file:///E:/webs/hero/the-internet.herokuapp.com/drag_and_drop.html");
		WebElement el=	wd.findElement(By.id("column-a"));
		JavascriptExecutor js=(JavascriptExecutor)wd;
		js.executeScript("<html><script src='C:\\Users\\partha\\Downloads\\sweetalert-master\\sweetalert-master\\dist\\sweetalert.min.js'></script><link rel='stylesheet' href='C:\\Users\\partha\\Downloads\\sweetalert-master\\sweetalert-master\\dist\\sweetalert.css' /><body>  <script>  sweetAlert('Hello world!');    </script></body></html>",el);
		//js.executeScript("sweetAlert('Hello world!');", el);
		wd.get("file:///C:/Users/partha/Downloads/sweetalert-master/sweetalert-master/MysweetAlert.html");
		
		Thread.sleep(5000);
		wd.quit();
	}
}