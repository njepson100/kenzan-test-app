package com.example;

import java.util.List;

public class SorterGrouper {

	private String groupIdentifier;
	private List<Integer> numericGrouperValue;
	
	public SorterGrouper(String identifier, List<Integer> numericValueList)
	{
		groupIdentifier = identifier;
		numericGrouperValue.addAll(numericValueList);
	}
	
	public String getIdentifier()
	{
		return groupIdentifier;
	}
	
	public List<Integer> getNumericValue()
	{
		return numericGrouperValue;
	}
	
	
}
