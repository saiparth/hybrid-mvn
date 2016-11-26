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
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.sun.jna.platform.win32.Wdm;

public class switchtowindow {
	static String parent=null;
static String url="file:///C:/Users/partha/Desktop/as.html";

public  void ru(WebDriver wd) throws InterruptedException{
	/*System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");//chromedriver.exe");
	WebDriver wd=new FirefoxDriver();//new ChromeDriver();
*/	wd.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	wd.get(url);
	 parent=wd.getWindowHandle();
	 System.out.println(parent);
	Set<String> iter=wd.getWindowHandles();
	for (String string : iter) {
		System.out.println(iter.size());
		System.out.println(string);
	}
	 WebElement link=wd.findElement(By.partialLinkText("Print"));
	link.click();
	Iterator<String>i1=iter.iterator();
	
	while (i1.hasNext()) {
	System.out.println("new window");
	String child=i1.next();
		 wd.switchTo().window(child);
		Thread.sleep(4000);
		System.out.println(wd.getTitle());
			wd.close();
			Thread.sleep(4000);}
	wd.quit();
	}

public void title_test() throws InterruptedException{
	WebDriver wd;
	/*wd=new PhantomJSDriver();
	ru(wd);
	wd.quit();*/
	System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
	wd= new ChromeDriver();
	ru(wd);
	/*System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
	wd=new FirefoxDriver();
	ru(wd);
	wd.quit();*/
}
@Test
public void nested() throws InterruptedException{
	System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
	WebDriver wd;
	//wd=new PhantomJSDriver();
	wd=new ChromeDriver();
	wd.get(url);
	parent=wd.getWindowHandle(); System.out.println(parent);
	System.out.println(wd.getTitle());
	WebElement link=wd.findElement(By.xpath("html/body/a"));
	link.click();
	usingNum(wd, 1);
	wd.findElement(By.partialLinkText("Print")).click();
	System.out.println(wd.getTitle());
	//Thread.sleep(4000);
	wd.close();
	//Thread.sleep(2000);
	wd.switchTo().window(parent);
	wd.get("http://localhost/login.do;jsessionid=67dipob3uadbm");
	System.out.println(wd.getTitle());
	wd.quit();
	
}
//@Test
public void foreach(){
	String a[]={"b","a","c","d"};
	for (String string : a) {
		//System.out.println(string);
		System.out.println(a[0]);
	}
}

public void usingNum(WebDriver wd, long windownum){
	Set<String> handles=wd.getWindowHandles();
	Iterator<String>iterator=handles.iterator();
	long count=0;
	while(iterator.hasNext()){
		String handl=iterator.next().toString();
		wd.switchTo().window(handl);
		if(count==windownum)
			{System.out.println("breaked switch to window");break;}
		count++;
	}
}
}
	//wd.quit()
	
	//wd.quit();
	

	/*for (String string : wd.getWindowHandles()) {
		if (wd.switchTo().window(string).getTitle().equals("parent")) {
			System.out.println("inside for if");
			break;
		}
		else {
			System.out.println("inside for else");
			wd.switchTo().window(parent);
		}
	}*/


