package lab;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.python.modules.thread.thread;

public class switchtowindow {
@Test
public  void ru() throws InterruptedException{
	System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
	WebDriver wd=new ChromeDriver();
	wd.get("file:///C:/Users/partha/Desktop/as.html");
	String parent=wd.getWindowHandle();
	wd.findElement(By.partialLinkText("Print")).click();
	Set<String>iter=wd.getWindowHandles();
	Iterator<String>i1=iter.iterator();
while (i1.hasNext()) {
	System.out.println("new window");
	String child=i1.next();
	if (!parent.equalsIgnoreCase(child)) {
		System.out.println("new window");
		wd.switchTo().window(child);
		Thread.sleep(4);
		System.out.println(wd.getTitle());
		wd.close();
	}
	//wd.quit();
	wd.switchTo().window(parent);
	System.out.println(wd.getTitle());
	//wd.quit();
	
}
}
}
