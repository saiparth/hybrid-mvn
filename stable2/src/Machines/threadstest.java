package Machines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;

import com.google.common.base.Stopwatch;

public class threadstest extends lab {
	
	public static WebElement runner(WebDriver wd, String locator,int timeout) 
	{
		List<Integer> liststatus = new ArrayList<>();
		List<WebElement> elementholder = new ArrayList<>();
		WebElement val = null;
		new Thread() {
			public void run() {
				List<WebElement> elems = wd.findElements(By.name(locator));
				int size = elems.size();
				if (size > 0) {
					liststatus.add(2);
					if (liststatus.size() <= 1) {
						WebElement ele=(elems.get(0));
						elementholder.set(0, ele);
						System.out.println("thread 1 added");
						System.out.println(liststatus.size());
					} 
				}
			}
		}.start();
		
		new Thread() {
			public void run() {
				List<WebElement> elems = wd.findElements(By.id(locator));
				int size = elems.size();
				if (size > 0) {
					liststatus.add(1);
					if (liststatus.size() <= 1) {
						elementholder.set(0, elems.get(0));
						System.out.println("thread 2 added");
						System.out.println(liststatus.size());
					}
				}
			}
		}.start();
		for (int i = 0; i <=timeout; i++) {
			try {
				//val=elementholder.get(1);
				System.out.println("gf");
			} catch (Exception e) {
System.out.println(e);			}
			 
		}
		
		return val;
		
	}

	public static class tester {
		public static List<WebElement> GetElement(WebDriver wd, String value, String type, long timout) {
			List<WebElement> ele = null;
			String status = null;
			switch (type.toLowerCase()) {
			case "id":
				ele = wd.findElements(By.id(value));
				System.out.println(ele.size());
				break;
			case "classname":
				ele = wd.findElements(By.className(value));
				System.out.println(ele.size());
				break;
			case "cssselector":
				ele = wd.findElements(By.cssSelector(value));
				System.out.println(ele.size());
				break;
			case "linktext":
				ele = wd.findElements(By.linkText(value));
				System.out.println(ele.size());
				break;
			case "name":
				ele = wd.findElements(By.name(value));
				System.out.println(ele.size());
				break;
			case "partiallinktext":
				ele = wd.findElements(By.partialLinkText(value));
				System.out.println(ele.size());
				break;
			case "xpath":
				ele = wd.findElements(By.xpath(value));
				System.out.println(ele.size());
				break;
			default:
				System.out.println(
						"unable to find using given value,Use:- id,classname,cssselector,linktext,name,partiallinktext,xpath");
				break;
			}
			// return status;
			
			return ele;
		}

	}
}
