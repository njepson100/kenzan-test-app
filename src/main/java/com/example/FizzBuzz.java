package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("fizzbuzz")
public class FizzBuzz {

	
	protected static final String UTF8_CHARSET = "charset=UTF-8";
	
	private NumericRangeSorter numericRangeSorter;
	
	public FizzBuzz(NumericRangeSorter sorter)
	{
		numericRangeSorter = sorter;
	}
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @GET
    @Path("/{upperBound}")
    @Produces(MediaType.APPLICATION_JSON + ";" + UTF8_CHARSET)
    public Response getFizzBuzz(@PathParam("upperBound") String upperBound) throws JsonGenerationException, JsonMappingException, IOException {
    	ResponseBuilder response = Response.ok();
    	ObjectMapper mapper = new ObjectMapper();
    	if(! upperBound.chars().allMatch( Character::isDigit ))
    	{
    		response.status(Status.BAD_REQUEST).entity(mapper.writeValueAsString("Upper bound must be a numeric value"));
    	}
    	else
    	{        	
        	response.entity(mapper.writeValueAsString(numericRangeSorter.getFizzBuzzSort(Integer.parseInt(upperBound)))).status(Status.OK);
    	}

    	return response.build();
    }
}
