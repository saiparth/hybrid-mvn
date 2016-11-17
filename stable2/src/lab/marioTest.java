package lab;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class marioTest {
	public static void main(String[]args) throws InterruptedException{
		WebDriver wd;
	//System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver","F:\\libs\\chromedriver.exe");
		 wd=new ChromeDriver();
	wd.manage().window().maximize();
	wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	wd.get("file:///C:/Users/partha/Desktop/open%20in%20new%20window.htm");
	wd = (ChromeDriver) wd.switchTo().frame(2);
	wd.findElement(By.xpath("html/body/button")).click();
	String parent=wd.getWindowHandle();
	Set<String>handles=wd.getWindowHandles();
	Iterator<String>it=handles.iterator();
	System.out.println(it.hasNext());
	while (it.hasNext()) {
		String child=it.next();
		if (!parent.equalsIgnoreCase(child)) {
			System.out.println("new window");
			wd.switchTo().window(child);
			//Thread.sleep(2000);
			wd.navigate().refresh();
			//Thread.sleep(2000);
			wd.close();
			//System.out.println(wd.getTitle());
		}
	}wd.switchTo().window(parent);
	wd.navigate().refresh();
	wd.quit();
}
}