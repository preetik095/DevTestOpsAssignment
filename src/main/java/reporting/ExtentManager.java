//extent report generation class

package reporting;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {

	 static ExtentReports extentReports;
	 static ExtentSparkReporter extentSparkReporter;
	 static ExtentTest extentTest;
	 static String reportPath;
	 static File file;
	 public static final Logger log = LogManager.getLogger(ExtentManager.class);
	 
	
	 
	//configuration for extent report
	public static void createReport(String title) {
		
		if(extentReports == null) {
			
			extentReports = new ExtentReports();
			
			try {
				
				reportPath = System.getProperty("user.dir")+"/ExtentReports/"+"ExtentReport.html";
				file = new File(reportPath);	
				
				// Ensure the parent directories exist
	            file.getParentFile().mkdirs();
	            
	            try {
	            	
	            	// Create the file if it does not exist
	            	if(!file.exists()) {                    
		            	  file.createNewFile();
		            }   
	            	
	            } catch(Exception e) {
	            	e.printStackTrace();
	            }
	            
					
				extentSparkReporter = new ExtentSparkReporter(reportPath);
				extentSparkReporter.config().setReportName("API Automation Test Report");
				extentSparkReporter.config().setTheme(Theme.STANDARD);
				extentSparkReporter.config().setDocumentTitle("API Automation Test Report");
				extentSparkReporter.config().setEncoding("uft-8");
				extentSparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
					
				extentReports.setSystemInfo("Environment", "Testing");
				extentReports.setSystemInfo("Author", "QA Team");
					
				extentReports.attachReporter(extentSparkReporter);	
				
			} catch (Exception e) {
				log.debug("Exception occured");
				e.printStackTrace();
			}
		}
			extentTest = extentReports.createTest(title);
		}
	

	   
		
		
		//report test result
	    public static void getResult(ITestResult result) throws Exception {
	    
	    	if (result.getStatus() == ITestResult.FAILURE) {
	    		
	    		//MarkupHelper is used to display the output in different colors
	    		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case FAILED", ExtentColor.RED));
	    		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case FAILED", ExtentColor.RED));					
			}
			else if(result.getStatus() == ITestResult.SKIP){
				
				extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case SKIPPED", ExtentColor.ORANGE));
			}
			else if(result.getStatus() == ITestResult.SUCCESS)
			{
				extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
			}
		
		}

		
		//generate extent report
		public static void generateReport() {
			extentReports.flush();
			log.debug("Extent report generated");
		}

}
