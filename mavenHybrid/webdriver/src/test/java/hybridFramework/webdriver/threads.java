package hybridFramework.webdriver;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class threads {
	private static final int MYTHREADS = 30;
 
 
	public static ExtentReports	ex=new ExtentReports(System.getProperty("user.dir")+"/test-output/linklist.html");
	
	public static void test(List<WebElement>hostList, String value) throws Exception {
		ExtentTest test =  ex.startTest("Link validator,innerHTML-"+value);
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		System.out.println(hostList.size());
		 
		for (WebElement sd:hostList) 
		{
			String url="";
			try
			{
				url = sd.getAttribute(value);;
			}  
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(url);
			if ((!(url==null))&&url.contains("http"))
				{
				Runnable worker = new MyRunnable(url,test);
				executor.execute(worker);
				}
			else{
				test.log(LogStatus.SKIP, "Invalid URL since it does'nt contains HTTP "+url);
				continue;
				}
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) 
		{
		}
		System.out.println("Finished all threads");
		ex.endTest(test);
		ex.flush();
	}

	public static class MyRunnable implements Runnable 	{
		private final String url;
		private final ExtentTest test;
				MyRunnable(String url,ExtentTest test) 
				{
					this.url = url;
					this.test=test;
				}

		@Override
		public void run() 
		{
			int code = 200;
						try 
						{
							URL siteURL = new URL(url);
							HttpURLConnection con = (HttpURLConnection) siteURL.openConnection();
							con.setRequestMethod("GET");
							con.setConnectTimeout(5000);
							con.connect();
							code = con.getResponseCode();
							if (code == 200) 
										{
											this.test.log(LogStatus.PASS, "The passed URL is - "+url+" "+con.getResponseMessage());
										} 
											
							else
											{
												this.test.log(LogStatus.FAIL, "The passed URL is - "+url+" "+con.getResponseMessage());
											}
						} 
						catch (MalformedURLException e) 
						{
							e.printStackTrace();
							this.test.log(LogStatus.FAIL, "The passed URL is - "+url+" "+e);
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
							this.test.log(LogStatus.FAIL, "The passed URL is - "+url+" "+e);
						}
			
			}}}	
