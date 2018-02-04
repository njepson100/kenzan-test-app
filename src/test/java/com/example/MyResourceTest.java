package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

	private static String fizz = "Fizz";
	private static String buzz = "Buzz";
	private static String fizzBuzz = "FizzBuzz";
    
    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see if Bad Request is returned when the upperBound pathParam is not a valid number
     */
    @Test
    public void getFizzBuzzInvalidParamTest() throws JsonGenerationException, JsonMappingException, IOException
    {	
    	NumericRangeSorter testNumericRangeSorter = new NumericRangeSorter();
    	FizzBuzz fizzBuzz = new FizzBuzz();
    	
    	Response actualResponse = fizzBuzz.getFizzBuzz("11.0");
    	assertEquals(Status.BAD_REQUEST.getStatusCode(), actualResponse.getStatus());
    }
    
}
