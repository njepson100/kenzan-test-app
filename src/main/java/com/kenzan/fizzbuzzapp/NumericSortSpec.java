package com.kenzan.fizzbuzzapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumericSortSpec {

	protected String sortSpecName;
	protected String sortOperator;
	protected Map<String, List<Integer>> sortTypeValueMap = new HashMap<>();
	protected boolean duplicateSortedValues;
	
	public NumericSortSpec(Builder builder)
	{
		sortSpecName = builder.sortSpecName;
		sortOperator = builder.sortOperator;
		sortTypeValueMap.putAll(builder.sortValueTypeMap);
		duplicateSortedValues = builder.duplicateSortedValues;
	}
	
	public String getSortOperator()
	{
		return sortOperator;
	}
	public boolean duplicateEntries()
	{
		return duplicateSortedValues;
	}
	
	public Map<String, List<Integer>> getSortTypeValueMap()
	{
		return sortTypeValueMap;
	}
	
	public static class Builder {
		private String sortSpecName;
		private String sortOperator;
		private Map<String, List<Integer>> sortValueTypeMap = new HashMap<>();
		private boolean duplicateSortedValues;
		
		public Builder (String sortSpecName, String sortOperator, boolean duplicateSortedValues, Map<String, List<Integer>> sortValueTypeMap)
		{
			this.sortSpecName = sortSpecName;
			this.sortOperator = sortOperator;
			this.duplicateSortedValues = duplicateSortedValues;
			this.sortValueTypeMap.putAll(sortValueTypeMap);
		}
		
		public NumericSortSpec build()
		{
			return new NumericSortSpec(this); 
		}
		
	}
	
}
