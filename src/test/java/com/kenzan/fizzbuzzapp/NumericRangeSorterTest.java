package com.kenzan.fizzbuzzapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.kenzan.fizzbuzzapp.NumericRangeSorter;
import com.kenzan.fizzbuzzapp.NumericSortSpec;

import static org.junit.Assert.assertEquals;


public class NumericRangeSorterTest {

	private NumericRangeSorter rangeSorter;
	private static String fizz = "Fizz";
	private static String buzz = "Buzz";
	private static String fizzBuzz = "FizzBuzz";
	@Before
	public void setup()
	{
    	Map<String, List<Integer>> sortingMap = new HashMap<>();
    	List<Integer> fizzList = new ArrayList<>();
    	fizzList.add(3);
    	sortingMap.put("Fizz", fizzList );
    	
    	List<Integer> buzzList = new ArrayList<>();
    	buzzList.add(5);
    	sortingMap.put("Buzz", buzzList );
    	
    	List<Integer> fizzBuzzList = new ArrayList<>();
    	fizzBuzzList.add(3);
    	fizzBuzzList.add(5);
    	sortingMap.put("FizzBuzz", fizzBuzzList );
    	
		NumericSortSpec.Builder numericSortSpecBuilder = new NumericSortSpec.Builder("FizzBuzz", "/", false, sortingMap);
		NumericSortSpec numericRangeSortSpec = numericSortSpecBuilder.build();

		rangeSorter = new NumericRangeSorter(numericRangeSortSpec);
	}
	
	//Unit test for sorting a given numeric range using FizzBuzzSortSpec
	@Test
	public void testGetFizzBuzzSortedNumericString()
	{
		Map<String, List<Integer>> expectedSortedMap = new HashMap<String, List<Integer>>();

		List<Integer> expectedFizzBuzzList = Arrays.asList(15,30,45,60,75,90);
		expectedSortedMap.put(fizzBuzz, expectedFizzBuzzList);
		
		List<Integer> expectedFizzList = Arrays.asList(3,6,9,12,18,21,24,27,33,36,39,42,48,51,54,57,63,66,69,72,78,81,84,87,93,96,99);
		expectedSortedMap.put(fizz, expectedFizzList);
		
		List<Integer> expectedBuzzList = Arrays.asList(5,10,20,25,35,40,50,55,65,70,80,85,95,100);
		expectedSortedMap.put(buzz, expectedBuzzList);
		

    	Map<String, List<Integer>> actualMap = rangeSorter.getSortedNumericValueMap(100);
    	assertEquals(expectedSortedMap, actualMap);	
    	
	}
	
	@Test
	public void testOrderMapBySortPriority()
	{
    	Map<String, List<Integer>> sortingMap = new HashMap<>();
    	List<Integer> fizzList = new ArrayList<>();
    	fizzList.add(3);
    	sortingMap.put("Fizz", fizzList );
    	
    	List<Integer> buzzList = new ArrayList<>();
    	buzzList.add(5);
    	sortingMap.put("Buzz", buzzList );
    	
    	List<Integer> fizzBuzzList = new ArrayList<>();
    	fizzBuzzList.add(3);
    	fizzBuzzList.add(5);
    	sortingMap.put("FizzBuzz", fizzBuzzList );
    	
    	rangeSorter.orderMapBySortPriority(sortingMap);

    	
    	assertEquals(sortingMap.size(), 3);
    	Entry<String, List<Integer>> firstEntry = sortingMap.entrySet().stream()
        .findFirst()
        .get();
    	assertEquals(firstEntry.getKey(), "FizzBuzz");
    	assert(firstEntry.getValue().get(0).equals(3));
    	assert(firstEntry.getValue().get(1).equals(5));
    	
	}
	
}
