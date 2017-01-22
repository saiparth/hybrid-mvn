package Machines;
import org.openqa.selenium.WebDriver;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class lab {
static String path="F:\\HybridFrameWork\\src\\Machines\\config.properties";
	public static void main(String[] args) throws FindFailed, InterruptedException {
		//DesiredCapabilities ds = DesiredCapabilities.firefox();
		//ds.setCapability("marionette", true);
		//ds.setJavascriptEnabled(true);*/
		WebDriver wd=null;
		//System.setProperty("webdriver.gecko.driver", "F:\\libs\\geckodriver.exe");
	//	wd=new FirefoxDriver();
		//wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//wd.get("http://localhost/login.do");
		//System.out.println(threadstest.runner(wd,"username"));
		/*System.out.println("completed");
		System.setProperty("webdriver.chrome.driver","F:\\libs\\chromedriver.exe");
		 wd=new ChromeDriver();
		wd.manage().window().maximize();
		System.out.println("outside thread");
		
		
		ExtentReports extent;
		extent = new ExtentReports("C:\\Users\\partha\\Desktop\\A", true);
		// creates a toggle for the given test, adds all log even       
		 ExtentTest test = extent.startTest("My First Test");
		 wd.get("https://www.google.co.in");
		// log(LogStatus, details)  
		      test.log(LogStatus.INFO,"starting");
		// report with snapshot    
		    String img = test.addScreenCapture("C:\\Users\\partha\\Desktop\\a.png");
		    		test.log(LogStatus.INFO, img, "Image example: " );       // end test  
		    		extent.endTest(test);   
		    		 extent.flush();
		    		 wd.quit();*/Screen screen = new Screen();

		    		// Create object of Pattern class and specify the images path

		    		
		    		Pattern image1 = new Pattern("F:\\sikulirepo\\notepadclick.PNG");

		    		Pattern image2 = new Pattern("F:\\sikulirepo\\typehere.PNG");

		    		//Pattern image3 = new Pattern("F:\\sikulirepo\\sendkeys.PNG");

		    	//	WebDriver driver=new FirefoxDriver();
		    		//System.setProperty("webdriver.chrome.driver","F:\\libs\\chromedriver.exe");
		   		// wd=new ChromeDriver();
		    	//	wd.manage().window().maximize();

		    	//	wd.get("http://localhost/login.do");
		    		Settings.BundlePath="‪F:\\sikulirepo\\excel.PNG";
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
		    		}}

		    		// using screen object we can call type  method which will accept image path and content which //we have to type and will perform action.

		    		// This  will type on username field

		    		//screen.type(image, "mukeshotwani@gmail.com");}}

		    		//This will type of password field

		    		//screen.type(image2, "password1");

		    		// This will click on login button

		    		//screen.click(image3);}}


