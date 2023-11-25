package FrameworkDesign.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportNG {
	
	public static ExtentReports getReportObject() {
		
		String path = System.getProperty("user.dir") + "\\Reports\\Index.html";
		ExtentSparkReporter report = new ExtentSparkReporter(path);
		report.config().setReportName("Web Automation Report");
		report.config().setDocumentTitle("Test Result");

//		class object of ExtentReports

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Tester Name", "Ajith");
		return extent;

	}

}
