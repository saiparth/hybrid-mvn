/**
 * 
 */
package hybridFramework.webdriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


/**
 * @author partha
 *
 */
public class MultiGetelement  {

	public static WebElement GetElement( int timelimit, WebDriver wd, String value, String type, long poll) throws InterruptedException {
		List<WebElement> ele = null;
		boolean status=false;
		for (int i = 0; i <= timelimit; i++)
		{
			try 
				{
					  ele = GetElements(wd, value, type);
				} 
			catch (Exception e)
			{
			}
			if (ele.size()>0) 
					{
						status=true;
						break;
					}
			System.out.println(i);
			 Thread.sleep(poll);
		}
			if (status==true)
				{
					return ele.get(0);
				} 
			else 
				{
					return null;
				}
		
	}

	public static  List<WebElement> GetElements(WebDriver wd, String value, String type) {
		List<WebElement> elements = null;
		switch (type.toLowerCase()) {
		case "find by id":
			elements=wd.findElements(By.id(value));
			break;
		case "find by classname":
			elements = wd.findElements(By.className(value));
			break;
		case "find by css":
			elements = wd.findElements(By.cssSelector(value));
			break;
		case "find by linktext":
			elements = wd.findElements(By.linkText(value));
			break;
		case "find by name":
			elements = wd.findElements(By.name(value));
			break;
		case "find by partial linktext":
			elements = wd.findElements(By.partialLinkText(value));
			break;
		case "find by xpath":
			elements = wd.findElements(By.xpath(value));
			break;
		case "find by tagname":
			elements=wd.findElements(By.tagName(value));
			break;
		default:
			System.out.println(
					"unable to find using given value"+value+"-"+type);
			break;
		}
		return elements;
	}
}

