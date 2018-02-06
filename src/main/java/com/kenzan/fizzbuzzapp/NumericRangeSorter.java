package com.kenzan.fizzbuzzapp;

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
	
	private NumericSortSpec sortSpec;
	
	
	//The sorter takes in a spec which determines how the values will be grouped and what the names of the groups are along with the upper bound of the numeric range.
	public NumericRangeSorter(NumericSortSpec numericSortSpec)
	{
		sortSpec = numericSortSpec;
	}
	
	//Return a map of string and list of integers where the String is the grouping name and the list of integers
	//are the integers grouped by that string
	public Map<String, List<Integer>> getSortedNumericValueMap(Integer upperBound)
	{	
		Map<String, List<Integer>> sortTypeValueMap = sortSpec.getSortTypeValueMap();
		if(sortSpec.duplicateEntries())
		{
			sortTypeValueMap = orderMapBySortPriority(sortTypeValueMap);
			
		}
		Map<String, List<Integer>> sortedMap = new HashMap<>();
		List<Integer> processedValues = new ArrayList<>();
		for (Map.Entry<String, List<Integer>> entry : sortTypeValueMap.entrySet())
		{
			Stream<Integer> rangeStream;

			rangeStream = IntStream.rangeClosed(1, upperBound).boxed();

			List<Predicate<Integer>> allPredicates = new ArrayList<Predicate<Integer>>();
			//This is a bit of a hack, should be able to do this with the stream itself
			if (! sortSpec.duplicateEntries())
			{
				allPredicates.add(n -> ! processedValues.contains(n));
			}
			for (Integer value : entry.getValue() )
			{
				if (sortSpec.getSortOperator().equals("/"))
				{
					allPredicates.add((n -> n % value ==0));

				}
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
	
	//This method enforces that multiple params for a map entry value will be executed before single params.
	//This allows for priority sorting where mulitples of 3 and 5 priotize higher than grouping where multiple of 3.
	public LinkedHashMap<String, List<Integer>> orderMapBySortPriority(Map<String, List<Integer>> sortTypeValueMap)
	{
		
		LinkedHashMap<String, List<Integer>> orderedHashMap = new LinkedHashMap<>();
		Comparator<Map.Entry<String, List<Integer>>> entryComparator = ((e1, e2) -> { int retVal = (e1.getValue().size() == e2.getValue().size()) ? 0 : ((e1.getValue().size() > e2.getValue().size()) ? 1 : -1); return retVal;});

		Set<Map.Entry<String, List<Integer>>> entrySet = sortTypeValueMap.entrySet();
		orderedHashMap = entrySet.stream()
				.sorted(entryComparator.reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) -> {throw new AssertionError();}, LinkedHashMap::new));
		
		return orderedHashMap;
	}
	

}
