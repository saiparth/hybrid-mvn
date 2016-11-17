package Machines;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
public class helpingfunctions {
		@Test
		public static void takeScreenShot(WebDriver wd, String destinationPath) throws IOException {
			TakesScreenshot ts = (TakesScreenshot) wd;
			File file = ts.getScreenshotAs(OutputType.FILE);
			String date=today();
			String Todate=todayDate();
			String today=date+".png";
			File d = new File(destinationPath+"\\"+Todate+"\\"+today);
			FileUtils.copyFile(file, d);
		}public static String today() {
			DateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
			Calendar cal = Calendar.getInstance();
			String date = df.format(cal.getTime());
			return date;
		}

		public static String todayDate() {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			String date = df.format(cal.getTime());
			return date;
		}
	
}