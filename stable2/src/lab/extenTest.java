package lab;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class extenTest {
	public String filePath;
	public Boolean replaceExisting = true;

	public void name() {
		ExtentReports extent;

		// To append to a report you had previously created, simply
		// mark replaceExisting = false and new tests will be appended to the
		// same report.
		// ExtentReports extent = new ExtentReports(file-path, false);

		DisplayOrder displayOrder;
		NetworkMode networkMode;
		// ex( filePath , replaceExisting, displayOrder, networkMode);
		// To create an offline report, useNetworkMode.OFFLINE in your
		// initialization:
		extent = new ExtentReports("file-path", NetworkMode.OFFLINE);
	}

	public void startTestExample() {
		// new instance
		ExtentReports extent = new ExtentReports(filePath, replaceExisting);

		// starting test
		ExtentTest test = extent.startTest("Test Name", "Sample description");

		// step log
		test.log(LogStatus.PASS, "Step details");
		test.log(LogStatus.ERROR, "error");
		test.log(LogStatus.FAIL, "FAIL");
		test.log(LogStatus.FATAL, "FATAL");
		test.log(LogStatus.INFO, "INFO");
		test.log(LogStatus.SKIP, "SKIP");
		test.log(LogStatus.UNKNOWN, "UNKNOWN");
		test.log(LogStatus.WARNING, "WARNING");

		// ending test
		extent.endTest(test);

		// writing everything to document
		extent.flush();
	}

	public void stepLogs() {
		// There are 2 ways logs can be created: one that creates 3 columns and
		// other that creates 4.
		// Always use only 1 type of log for the test otherwise the table will
		// become malformed.
		// creates 3 columns in table: TimeStamp, Status, Details
		//test.log(LogStatus.ERROR, "details");
		//log(LogStatus logStatus, Throwable t);
		// creates 4 columns in table: TimeStamp, Status, StepName, Details
		//log(LogStatus.ERROR, stepName, details);
		//log(LogStatus logStatus, String stepName, Throwable t);
	}
	//LogStatus status = test.getRunStatus();
	//You can know the current status of the test during execution by calling the getRunStatus() method.
	//You can assign categories to tests using assignCategory(String... params) method:
/*	test.assignCategory("Regression");
	test.assignCategory("Regression", "ExtentAPI");
	test.assignCategory("Regression", "ExtentAPI", "category-3", "cagegory-4", ..);
	
	Or simply assign them when you start your test:


		ExtentTest test = extent
		    .startTest("Categories")
		    .assignCategory("Regression", "ExtentAPI");*/
	
	/*
		Simply insert any custom HTML in the logs by using an HTML tag:
		extent.log(LogStatus.INFO, "HTML", "Usage: BOLD TEXT");*/
	public void screnncapture(){
	//	To add a screen-shot, simply call addScreenCapture. This method returns the HTML with  tag which can be used anywhere in the log details.
		ExtentTest test = null;
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture("screenshot-path"));
		//Relative paths starting with . and / are supported. If you using an absolute path, 
		//file:/// will be automatically be appended for the image to load.

		//To insert a Base64 screenshot, simply use the string with addBase64ScreenShot method:
		test.log(LogStatus.INFO, "Snapshot below: " + test.addBase64ScreenShot("base64String"));
		
		//To add a screencast/recording of your test run, use the addScreencast method anywhere in the log details:
			test.log(LogStatus.INFO, "Screencast below: " + test.addScreencast("screencast-path"));
			//Relative paths starting with . and / are supported. If you using an absolute path, file:/// will be automatically be appended for the screencst to load.
	}
}