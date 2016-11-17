package Machines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class threads extends ExcelUtils{
	//FileInputStream fis=new FileInputStream(new File(""));

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		String path = "C:\\Users\\partha\\Desktop\\excelLib.xlsx";
		String sheetName="login";
for (int i = 1; i <= ExcelUtils.getRowCount(sheetName, path); i++) {
	String val=ExcelUtils.reader(sheetName, i, 5, path).toString();
	test(val);
}
	
	}//System.setProperty("webdriver.gecko.driver","F:\\libs\\geckodriver.exe");
	public static void test(String val){
		//final String val="username";
		new Thread() {
			public void run() {
				WebDriver wd = new PhantomJSDriver();
				test(wd);
				try {
					//System.out.println("id");
					boolean status=	wd.findElement(By.id(val)).isDisplayed();
					System.out.println("findby id "+status+" "+val);
				} catch (Exception e) {
					System.out.println("finding by id faild");
				}
			}
		}.start();
		new Thread() {
			public void run() {
				WebDriver wd = new PhantomJSDriver();
				test(wd);//"//input[@id='keepLoggedInCheckBox']"
				try {
					int status=	wd.findElements(By.name(val)).size();
					if (status>0) {
						System.out.println(status+"name found"+val);	
					} else {
						System.out.println(status+"name not found"+val);
					}
				} catch (Exception e) {
					System.out.println("finding by name failed");
				}
			}
		}.start();
	}
	static Object test(WebDriver wd) {
		wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		wd.get("http://localhost/login.do");
		wd.manage().window().maximize();
		return wd;
	}
}
