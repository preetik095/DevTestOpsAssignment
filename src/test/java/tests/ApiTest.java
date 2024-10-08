package tests;

//import static org.testng.Assert.assertEquals;

//import java.io.File;
//import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

//import com.fasterxml.jackson.databind.ObjectMapper;

import api.BookingAPI;
import io.restassured.response.Response;
//import pojo.BookingRequestPayload;
//import pojo.BookingResponsePayload;
import reporting.ExtentManager;

public class ApiTest extends BaseTest {
	
	
	private static final Logger log = LogManager.getLogger(ApiTest.class);
    
	@Test (priority=1, description = "this request to get list of all booking IDs" )
	public void testGetAllBookingIDs_GET() {
		
		ExtentManager.createReport(testMethodName);
		
		Response bookingIDs =  BookingAPI.getAllBookingIds();
		
        Assert.assertNotNull(bookingIDs, "BookingID list is not Null");
        log.debug("BookingID list is not Null");
        
        Assert.assertEquals(bookingIDs.getStatusCode(), HttpStatus.SC_OK, "OK");
        log.debug("Status code is OK");
        
        Assert.assertEquals(bookingIDs.getStatusLine(), "HTTP/1.1 200 OK");
        log.debug("Status code is OK");
	} 
	
	
	@Test (priority=2, description = "this request to get list of all booking IDs with parameters firstname & last name" )
	public void testGetBookingIDsByName_GET() {
		
		ExtentManager.createReport(testMethodName);
		
		Response bookingIDs =  BookingAPI.getBookingIdsByName();
		
        Assert.assertNotNull(bookingIDs, "BookingID list is not Null");
        log.debug("BookingID list is not Null");
        
        Assert.assertEquals(bookingIDs.getStatusCode(), HttpStatus.SC_OK, "OK");
        log.debug("Status code is OK");
        
        Assert.assertEquals(bookingIDs.getStatusLine(), "HTTP/1.1 200 OK");
        log.debug("Status code is OK");
	} 
	
	
	@Test (priority=3, description = "this request to get list of all booking IDs with parameters checkin & checkout" )
	public void testGetBookingIDsByDate_GET() {
		
		ExtentManager.createReport(testMethodName);
		
		Response bookingIDs =  BookingAPI.getBookingIdsByDate();
		
        Assert.assertNotNull(bookingIDs, "BookingID list is not Null");
        log.debug("BookingID list is not Null");
        
        Assert.assertEquals(bookingIDs.getStatusCode(), HttpStatus.SC_OK, "OK");
        log.debug("Status code is OK");
        
        Assert.assertEquals(bookingIDs.getStatusLine(), "HTTP/1.1 200 OK");
        log.debug("Status code is OK");
	}
	
}
