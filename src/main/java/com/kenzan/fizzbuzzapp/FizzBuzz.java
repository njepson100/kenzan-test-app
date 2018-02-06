package com.kenzan.fizzbuzzapp;

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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("fizzbuzz")
public class FizzBuzz {

	
	protected static final String UTF8_CHARSET = "charset=UTF-8";
	
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws XPathExpressionException 
     * @throws NumberFormatException 
     */
    @GET
    @Path("/{upperBound}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFizzBuzz(@PathParam("upperBound") String upperBound) throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, XPathExpressionException, ParserConfigurationException, SAXException {
    	ResponseBuilder response = Response.ok();
    	ObjectMapper mapper = new ObjectMapper();
    	if(! upperBound.chars().allMatch( Character::isDigit ))
    	{
    		response.status(Status.BAD_REQUEST).entity(mapper.writeValueAsString("Upper bound must be an integer value"));
    	}
    	else
    	{
        	SortMediator sortMediator = new SortMediator();
        	
        	response.entity(mapper.writeValueAsString(sortMediator.fizzBuzzSort(Integer.parseInt(upperBound)))).status(Status.OK);
    	}

    	return response.build();
    }
}
