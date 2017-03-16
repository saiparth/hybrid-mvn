/**
 * 
 */
package Machines;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
//com.google.common.base.Function
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

	public static <T> WebElement GetElement(WebDriver wd, String value, String type, long timout) {
		WebElement ele = null;
		Wait<WebDriver> w=new FluentWait<WebDriver>(wd).
				withTimeout(timout, TimeUnit.SECONDS).
				pollingEvery(100, TimeUnit.MILLISECONDS).
				ignoring(NoSuchElementException.class,ElementNotVisibleException.class);
		switch (type.toLowerCase()) {
		case "find by id":
			ele = (WebElement) w.until( (Function<? super WebDriver, T>) ExpectedConditions.elementToBeClickable(wd.findElement(By.id(value))));
			break;
		case "find by classname":
			ele =  (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.className(value))));
			break;
		case "find by css":
			ele =  (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.cssSelector(value))));
			break;
		case "find by linktext":
			ele =  (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.linkText(value))));
			break;
		case "find by name":
			ele = (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.name(value))));
			break;
		case "find by partial linktext":
			ele =  (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.partialLinkText(value))));
			break;
		case "find by xpath":
			ele =(WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.xpath(value))));
			break;
		case "find by tagname":
			ele= (WebElement) w.until( (Function<? super WebDriver, T>)ExpectedConditions.elementToBeClickable(wd.findElement(By.tagName(value))));
			break;
		default:
			System.out.println(
					"unable to find using given value"+value+"-"+type);
			break;
		}

		return ele;
	}

	public static <T> List<WebElement> GetElements(WebDriver wd, String value, String type, long timout) {
		List<WebElement> elements = null;
		Wait<WebDriver> w=new FluentWait<WebDriver>(wd).
				withTimeout(timout, TimeUnit.SECONDS).
				pollingEvery(100, TimeUnit.MILLISECONDS).
				ignoring(NoSuchElementException.class,ElementNotVisibleException.class);
		switch (type.toLowerCase()) {
		case "find by id":
			elements= (List<WebElement>) w.until( (Function<? super WebDriver, T>)ExpectedConditions.visibilityOfAllElements(elements = wd.findElements(By.id(value))));
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

