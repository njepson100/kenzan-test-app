package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	NumericRangeSorter sorter = new NumericRangeSorter();
    	Map<String, List<Integer>> sortingMap = new HashMap<>();
    	List<Integer> fizzList = new ArrayList<>();
    	fizzList.add(3);
    	sortingMap.put("Fizz", fizzList );
    	
    	List<Integer> buzzList = new ArrayList<>();
    	buzzList.add(3);
    	sortingMap.put("Buzz", buzzList );
    	
    	List<Integer> fizzBuzzList = new ArrayList<>();
    	fizzBuzzList.add(3);
    	sortingMap.put("FizzBuzz", fizzBuzzList );
    	
    	sorter.getSortedNumericString(100, sortingMap);
        return "Got it!";
    }
}
