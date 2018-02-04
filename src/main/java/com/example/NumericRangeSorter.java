package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;



import org.json.JSONObject;
import org.json.JSONArray;


public class NumericRangeSorter {
	
	public Map<String, List<Integer>> getFizzBuzzSort(Integer upperBound)
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
    	return getSortedNumericMap(upperBound, sortingMap);

	}
	//this method will return a string representation of a sorted array
	public Map<String, List<Integer>> getSortedNumericMap(Integer upperBound, Map<String, List<Integer>> sortingMap)
	{
		//Comparator to order the map by most items in the list.  This creates a priority ordering so that groups with multiple conditions
		//will contain the integer values and the groups with single conditions will be handled last.
		Comparator<Map.Entry<String, List<Integer>>> entryComparator = ((e1, e2) -> { int retVal = (e1.getValue().size() == e2.getValue().size()) ? 0 : ((e1.getValue().size() > e2.getValue().size()) ? 1 : -1); return retVal;});

		Set<Map.Entry<String, List<Integer>>> entrySet = sortingMap.entrySet();
		Map<String, List<Integer>> sorted = entrySet.stream()
				.sorted(entryComparator.reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) -> {throw new AssertionError();}, LinkedHashMap::new));
		
		Map<String, List<Integer>> sortedMap = getSortedMap(upperBound, sorted);
		return sortedMap;
	}
	
	public Map<String, List<Integer>> getSortedMap(Integer upperBound, Map<String, List<Integer>> sortingMap)
	{	
		Map<String, List<Integer>> sortedMap = new HashMap<>();
		List<Integer> processedValues = new ArrayList<>();
		for (Map.Entry<String, List<Integer>> entry : sortingMap.entrySet())
		{
			Stream<Integer> rangeStream;

			rangeStream = IntStream.rangeClosed(1, upperBound).boxed();

			List<Predicate<Integer>> allPredicates = new ArrayList<Predicate<Integer>>();
			//This is a bit of a hack, should be able to do this with the stream itself
			allPredicates.add(n -> ! processedValues.contains(n));
			for (Integer value : entry.getValue() )
			{
				allPredicates.add((n -> n % value ==0));

			}
			Predicate<Integer> compositePredicate =
				    allPredicates.stream()
				                 .reduce(w -> true, Predicate::and);
	        Stream<Integer> sortedRangeStream = rangeStream.filter(compositePredicate);
	        List<Integer> groupedIntegerList = sortedRangeStream.collect(Collectors.toList());
	        processedValues.addAll(groupedIntegerList);
	        sortedMap.put(entry.getKey(), groupedIntegerList);

		}
		
		
		return sortedMap;
	}

}
