package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NumericRangeSorterTest {

	private NumericRangeSorter rangeSorter;
	@Before
	public void setup()
	{
		rangeSorter = new NumericRangeSorter();
	}
	@Test
	public void testGetSortedNumericString()
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
    	
    	rangeSorter.getSortedNumericString(100, sortingMap);
	}
	
	public void testGetSortedArray()
	{
		
	}
	
	public void testConvertMapToString()
	{
		
	}
}
