//listener class called for report generation 

package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

// Listener class to report test case status
public class Listeners implements ITestListener {

	// Called when a test case starts
	public void onTestStart(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Test case STARTED");
	}

	// Called when a test case passes
	public void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Test Case PASSED");
	}

	// Called when a test case fails
	public void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Test Case FAILED");
	}

	// Called when a test case is skipped
	public void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Test Case SKIPPED");
	}

	// Called before the starting of the TestNG suite
	public void onStart(ITestContext context) {
		System.out.println(context.getName() + " Test Case Started");
	}

	// Called after the ending of the TestNG suite
	public void onFinish(ITestContext context) {
		System.out.println(context.getName() + " Test Case Finished");
	}
}
