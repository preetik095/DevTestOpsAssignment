package api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utility.Endpoints;

public class PingAPI {
	
	private static final Logger log = LogManager.getLogger(PingAPI.class);
	
	//healthcheck of API 
	public Response apiHealthCheck() {
			
			log.debug("API healtcheck");
			Response response = RestAssured.given().log().all()
					.contentType("application/json")
					.when()
					.get(Endpoints.HEALTHCHECK)
					.then()
    				.log().all()
    				.assertThat().statusCode(201)
    				.statusLine("HTTP/1.1 201 Created")
    				.contentType("text/plain; charset=utf-8")
    				.extract().response()
    				.andReturn();
			
			return response;
		}

}
