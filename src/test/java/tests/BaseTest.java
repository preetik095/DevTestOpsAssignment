package tests;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


import api.BookingAPI;
import api.PingAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import reporting.ExtentManager;
import utility.ConfigManager;

public class BaseTest {
	
	private static final String BASE_URL = ConfigManager.getInstance().getString("baseURL");
	private static final Logger log = LogManager.getLogger(BaseTest.class);
	BookingAPI bookingAPI; 
	PingAPI pingAPI = new PingAPI();
	static String testMethodName;
	
	@BeforeSuite(alwaysRun=true)
	public void setup() {
		RestAssured.baseURI = BASE_URL;	
		log.debug("Initializing restful booker API");
		
		bookingAPI = new BookingAPI();	
	} 
	
	@BeforeTest
    public void testHealthCheck() {
		try {
			Response response = pingAPI.apiHealthCheck();
			
			assertEquals(response.statusCode(), 201);
	  	  	log.debug("API healthcheck status code is successfully validated");
	      		
	  	  	assertEquals(response.statusLine(), "HTTP/1.1 201 Created");
	  	  	log.debug("API healthcheck status code is successfully validated");
	      		
	  	  	assertEquals(response.contentType(), "text/plain; charset=utf-8");
	  	  	log.debug("API healthcheck status code is successfully validated");
			
		} catch (Exception e) {
			
			log.error("Exception occured");
			e.printStackTrace();
		}	
    }

       
    @BeforeMethod(alwaysRun=true)
    public void getMethodName(Method method) {
     
    	log.info("Generating testmethod name");
    	testMethodName = method.getName();
    	
    } 
    
    
    
    @AfterMethod
	public void getResult(ITestResult result) throws Exception{
		ExtentManager.getResult(result);
	} 
    
    
    @AfterTest
    public void endReport() {
    	ExtentManager.generateReport();
    } 
    
    
}
