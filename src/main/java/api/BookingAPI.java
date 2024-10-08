package api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utility.ConfigManager;
import utility.Endpoints;

public class BookingAPI {
		
	private static String firstname = ConfigManager.getInstance().getString("firstname");
	private static String lastname = ConfigManager.getInstance().getString("lastname");
	private static String checkin = ConfigManager.getInstance().getString("checkin");
	private static String checkout = ConfigManager.getInstance().getString("checkout");
	private static final Logger log = LogManager.getLogger(BookingAPI.class);
	
	
	//get list of all booking IDs - GET request
	public static Response getAllBookingIds() {
			
		log.debug("Calling getAllBooking function");
		Response response = RestAssured.given().log().all()
				.contentType("application/json")
				.when()
				.get(Endpoints.GET_BOOKING_IDS)
				.then()
				.assertThat()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.contentType("application/json; charset=utf-8")
				.extract().response()
				.andReturn();
					
				response.prettyPeek();			
		return response;
	}
		
		
	//get list of all booking IDs by name parameter - GET request
	public static Response getBookingIdsByName() {
					
		log.debug("Calling getAllBooking function");
		Response response = RestAssured.given().log().all()
				.contentType("application/json")
				.queryParam(firstname, lastname)
				.when()
				.get(Endpoints.GET_BOOKING_IDS)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType("application/json; charset=utf-8")
				.statusLine("HTTP/1.1 200 OK")
				.extract().response()
				.andReturn();
							
				response.prettyPeek();			
		return response;
	}	
	
	
	//get list of all booking IDs by name parameter - GET request
	public static Response getBookingIdsByDate() {
						
	log.debug("Calling getAllBooking function");
	Response response = RestAssured.given().log().all()
			.contentType("application/json")
			.queryParam(checkin, checkout)
			.when()
			.get(Endpoints.GET_BOOKING_IDS)
			.then()
			.assertThat()
			.statusCode(200)
			.contentType("application/json; charset=utf-8")
			.extract().response()
			.andReturn();
								
			response.prettyPeek();			
			return response;
	}
	
}
