package Machines;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;





public class lab {
	@Test
	void t(){
		System.out.println("hai");
	}
static String path="F:\\HybridFrameWork\\src\\Machines\\config.properties";
@Test
	public static void main() throws FindFailed, InterruptedException {
		//DesiredCapabilities ds = DesiredCapabilities.firefox();
		//ds.setCapability("marionette", true);
		//ds.setJavascriptEnabled(true);*/
		WebDriver wd=null;
		//System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
	//	wd=new FirefoxDriver();
		//wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//wd.get("http://localhost/login.do");
		//System.out.println(threadstest.runner(wd,"username"));
		System.out.println("completed");
		System.setProperty("webdriver.chrome.driver","F:\\libs\\chromedriver.exe");
		 wd=new ChromeDriver();
		//wd.manage().window().maximize();
		System.out.println("outside thread");
		
		
		//String p="F:\.metadata\repo\stable2\test-output\my.html";
		//File f=new File("F:\\libs\\extentreports-java-v2.41.1\\extent-config.xml");
		//System.out.println(f.exists());
		ExtentReports extent=new ExtentReports(System.getProperty("user.dir")+"/test-output/my.html");
	//	extent.loadConfig(f);
		// creates a toggle for the given test, adds all log even       
		 ExtentTest test =  extent.startTest("My First Test");
		 wd.get("http://localhost/login.do");
		// log(LogStatus, details)  
		      test.log(LogStatus.INFO,"starting");
		// report with snapshot    
		    //  wd=new PhantomJSDriver();
		    //  wd.get("http://localhost/login.do");
		   // String img = test.addScreenCapture("c:\\Users\\partha\\Pictures\\Divine6.jpg");
		    	//	test.log(LogStatus.INFO, test.addScreencast("F:\\phone\\whatsap vids\\VID-20160513-WA0000.mp4"), "Image example: " );       // end test  
		    		extent.endTest(test);  
		    		ExtentTest t2 = extent.startTest("My second Test");
		    		t2.log(LogStatus.INFO, test.addScreencast("F:\\phone\\whatsap vids\\VID-20160513-WA0000.mp4"), "Image example: " );
		    		ExtentTest t3 = extent.startTest("My First Test");
		    		t3.log(LogStatus.FAIL, "C:\\Users\\partha\\Pictures\\Divine6.jpg", "updateImage example: " ); 
		    		t2.log(LogStatus.PASS, "C:\\Users\\partha\\Pictures\\Divine6.jpg","Step details");
		    		t2.log(LogStatus.ERROR, "C:\\Users\\partha\\Pictures\\Divine6.jpg","error");
		    		t2.log(LogStatus.FAIL, "C:\\Users\\partha\\Pictures\\Divine6.jpg","FAIL");
		    		t2.log(LogStatus.FATAL,"C:\\Users\\partha\\Pictures\\Divine6.jpg", "FATAL");
		    		t2.log(LogStatus.INFO, "C:\\Users\\partha\\Pictures\\Divine6.jpg","INFO");
		    		t3.log(LogStatus.SKIP, "C:\\Users\\partha\\Pictures\\Divine6.jpg","SKIP");
		    		t3.log(LogStatus.UNKNOWN,"C:\\Users\\partha\\Pictures\\Divine6.jpg", "UNKNOWN");
		    		t3.log(LogStatus.WARNING,"C:\\Users\\partha\\Pictures\\Divine6.jpg", "WARNING");
		    		extent.endTest(t2); 
		    		extent.endTest(t3); 
		    		 extent.flush();
		    		 /*Screen screen = new Screen();

		    		// Create object of Pattern class and specify the images path

		    		
		    		Pattern image1 = new Pattern("F:\\sikulirepo\\notepadclick.PNG");

		    		Pattern image2 = new Pattern("F:\\sikulirepo\\typehere.PNG");
*/
		    		//Pattern image3 = new Pattern("F:\\sikulirepo\\sendkeys.PNG");

		    	//	WebDriver driver=new FirefoxDriver();
		    		//System.setProperty("webdriver.chrome.driver","F:\\libs\\chromedriver.exe");
		   		// wd=new ChromeDriver();
		    	//	wd.manage().window().maximize();

		    	//	wd.get("http://localhost/login.do");
		    		/*Settings.BundlePath="‪F:\\sikulirepo\\excel.PNG";
		    		//Pattern image = new Pattern("F:\\sikulirepo\\excel.PNG");
		    		System.out.println(Settings.BundlePath);		
//screen.wait(image, 10);

		    		// using screen object we can call click method which will accept image path and will perform //action

		    		// This will click on gmail image on google home page

		    		screen.click(image1);
		    		//screen.doubleClick(image1);
		    		//screen.rightClick(image2);
		    		screen.type(image2,"hai");
		    		//screen.dragDrop("");
*/		    		}}

		    		// using screen object we can call type  method which will accept image path and content which //we have to type and will perform action.

		    		// This  will type on username field

		    		//screen.type(image, "mukeshotwani@gmail.com");}}

		    		//This will type of password field

		    		//screen.type(image2, "password1");

		    		// This will click on login button

		    		//screen.click(image3);}}


