package hybridFramework.webdriver;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class Helpingfunctions {
		//@Test
		public static String takeScreenShot(WebDriver wd, String destinationPath) throws IOException 
		{
			TakesScreenshot ts = (TakesScreenshot) wd;
			File file = ts.getScreenshotAs(OutputType.FILE);
			String date=today();
			String Todate=todayDate();
			String today=date+".png";
			File d = new File(destinationPath+"\\"+Todate+"\\"+today);
			FileUtils.copyFile(file, d);
			return destinationPath+"\\"+Todate+"\\"+today;
		}
		public static String timeForName() 
		{
			DateFormat df = new SimpleDateFormat("MMMddhhmmss");
			Calendar cal = Calendar.getInstance();
			String date = df.format(cal.getTime());
			return date;
		}
		public static String today() 
		{
			DateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
			Calendar cal = Calendar.getInstance();
			String date = df.format(cal.getTime());
			return date;
		}

		public static String todayDate() 
		{
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			String date = df.format(cal.getTime());
			return date;
		}
		public static void windowSwitcher(WebDriver wd,long windowIndex)
		{
			Set<String>iterator=wd.getWindowHandles();
			Iterator<String>winIterator=iterator.iterator();
			long count=0;
			while (winIterator.hasNext()) {
				String s=winIterator.next().toString();
				wd.switchTo().window(s);
				if (count==windowIndex) {
					break;
				}
				count++;
			}
		}
		public static void linkValidator(String inputUrl,ExtentTest reportName){
			try {
				if (inputUrl.contains("http")) 
					{
						//reportName.log(LogStatus.INFO,"The passed URL is - "+inputUrl);
						URL url=new URL(inputUrl);
						HttpURLConnection con=(HttpURLConnection) url.openConnection();
						con.setConnectTimeout(5000);
						con.connect();
							if (con.getResponseCode()==200) 
							{
								reportName.log(LogStatus.PASS, "The passed URL is - "+inputUrl+" "+con.getResponseMessage());
								//System.out.println(inputUrl +"\t"+con.getResponseMessage());
							} 
								if(con.getResponseCode()==HttpURLConnection.HTTP_BAD_REQUEST)
								{
									reportName.log(LogStatus.FAIL, "The passed URL is - "+inputUrl+" "+HttpURLConnection.HTTP_BAD_REQUEST);
									//System.out.println(inputUrl+"\t"+con.getResponseMessage()+"\t"+HttpURLConnection.HTTP_BAD_REQUEST);
								}
								if(con.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)
								{
									reportName.log(LogStatus.FAIL, "The passed URL is - "+inputUrl+" "+HttpURLConnection.HTTP_NOT_FOUND);
									//System.out.println(inputUrl+"\t"+con.getResponseMessage()+"\t"+HttpURLConnection.HTTP_NOT_FOUND);
								}
					}
				else
				{
					reportName.log(LogStatus.SKIP, "Invalid URL since it does'nt contains HTTP "+inputUrl);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
}