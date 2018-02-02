package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;



import org.json.JSONObject;
import org.json.JSONArray;


public class NumericRangeSorter {
	
	public NumericRangeSorter()
	{
		
	}
	//this method will return a string representation of a sorted array
	public JSONObject getSortedNumericString(Integer upperBound, Map<String, List<Integer>> sortingMap)
	{
		//First order the map so that the entries with the longest lists will evaluate first
		//this will allow those elements to be filtered out so there will be no repetition of value.
		Map<String, List<Integer>> sorted = sortingMap.entrySet().stream()
		        .sorted(comparingInt(e->e.getValue().size()))
		        .collect(toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a,b) -> {throw new AssertionError();},
		                LinkedHashMap::new
		        )); 
		Map<String, List<Integer>> sortedMap = getSortedMap(upperBound, sorted);
		return convertMapToString(sortedMap);
	}
	
	public Map<String, List<Integer>> getSortedMap(Integer upperBound, Map<String, List<Integer>> sortingMap)
	{	
		Map<String, List<Integer>> sortedMap = new HashMap<>();
		
		for (Map.Entry<String, List<Integer>> entry : sortingMap.entrySet())
		{
			Stream<Integer> rangeStream = IntStream.range(1, upperBound).boxed();

			List<Predicate<Integer>> allPredicates = new ArrayList<Predicate<Integer>>();
			for (Integer value : entry.getValue() )
			{
				allPredicates.add((n -> n % value ==0));

			}
			Predicate<Integer> compositePredicate =
				    allPredicates.stream()
				                 .reduce(w -> true, Predicate::and);
	        Stream<Integer> sortedRangeStream = rangeStream.filter(compositePredicate);
	        sortedMap.put(entry.getKey(), sortedRangeStream.collect(Collectors.toList()));

		}
		
		
		return sortedMap;
	}
	
	public JSONObject convertMapToString(Map<String, List<Integer>> mapToConvert)
	{
		
	      JSONObject obj = new JSONObject();

	      for (Map.Entry<String, List<Integer>> entry : mapToConvert.entrySet())
	      {
	    	  obj.put(entry.getKey(), entry.getValue());
	      }
 
	      return obj;
	}

}
