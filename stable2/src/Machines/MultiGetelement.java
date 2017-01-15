/**
 * 
 */
package Machines;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author partha
 *
 */
public class MultiGetelement  {

	public static WebElement GetElement(WebDriver wd, String value, String type, long timout) {
		WebElement ele = null;
		wd.manage().timeouts().implicitlyWait(timout, TimeUnit.SECONDS);
		//Wait w=new FluentWait(wd).withTimeout(timout, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
		switch (type.toLowerCase()) {
		case "find by id":
			ele = wd.findElement(By.id(value));
			break;
		case "find by classname":
			ele = wd.findElement(By.className(value));
			break;
		case "find by css":
			ele = wd.findElement(By.cssSelector(value));
			break;
		case "find by linktext":
			ele = wd.findElement(By.linkText(value));
			break;
		case "find by name":
			ele = wd.findElement(By.name(value));
			break;
		case "find by partial linktext":
			ele = wd.findElement(By.partialLinkText(value));
			break;
		case "find by xpath":
			ele = wd.findElement(By.xpath(value));
			break;
		case "find by tagname":
			ele=wd.findElement(By.tagName(value));
			break;
		default:
			System.out.println(
					"unable to find using given value"+value+"-"+type);
			break;
		}

		return ele;
	}

	public static List<WebElement> GetElements(WebDriver wd, String value, String type) {
		List<WebElement> elements = null;
		switch (type.toLowerCase()) {
		case "find by id":
			elements = wd.findElements(By.id(value));
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

