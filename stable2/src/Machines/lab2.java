package Machines;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class lab2 extends ExcelUtils {
	static int e = 2;

	/*
	 * public static void main(String[] args) { int h = 1; boolean cond = false;
	 * String path = "C:\\Users\\partha\\Desktop\\excelLib.xlsx"; int rowcount =
	 * 0; try { rowcount = ExcelUtils.getRowCount("login",
	 * "C:\\Users\\partha\\Desktop\\excelLib.xlsx"); } catch (IOException e) {
	 * e.printStackTrace(); } for (int i = h + 1; i <= rowcount; i++) { if
	 * ((ExcelUtils.reader("login", i, 1, path).toString()).contains("z")) {
	 * System.out.println("null"+cond); break; }
	 * System.out.println(ExcelUtils.reader("login", i, 1, path)); } } }
	 */
	//test branch
	@Test
	public void g() throws InterruptedException{
			
		System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver.exe");
		WebDriver wd ;
		wd= new ChromeDriver();
		//wd=PhantomJSDriver();
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wd.get("http://localhost/login.do");
		wd.findElement(By.id("username")).sendKeys("admin");
		wd.findElement(By.name("pwd")).sendKeys("manager");
		wd.findElement(By.id("loginButton")).click();
		wd.findElement(By.xpath(".//*[@id='topnav']/tbody/tr[1]/td[5]/a/img")).click();
		//wd.findElement(By.)
		//WebElement d=wd.findElement(By.name("recordsPerPage"));
		Select s=new Select(wd.findElement(By.name("recordsPerPage")));
		s.selectByIndex(1);
		Thread.sleep(2000);
		s.selectByValue("40");
		Thread.sleep(2000);
		s.selectByVisibleText("100");
		wd.quit();
		}
	
	public String t() {
		int i=10,b=3;
		String val=null;
		for (int j = 0; j < 3; j++) {
			val=ta();
		}
		System.out.println(val);
		return val;
		
	}
	public String ta() {
		Scanner sc=new Scanner(System.in);
		String f=sc.next();
		return f;
	}
}
