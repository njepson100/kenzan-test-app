package com.kenzan.fizzbuzzapp;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SortMediator {
	
	private final String SORT_SPEC_CONFIG_FILENAME = "SortSpecConfig.xml";
	private static HashMap<String, NumericSortSpec> sortSpecMap = new HashMap<>();
	private static Map<String, Node> nodeMap = new HashMap<>();
	public Map<String, List<Integer>> fizzBuzzSort(Integer upperBound) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{
		loadNumericSortSpecConfig();
    	NumericSortSpec sortSpec = sortSpecMap.get("FizzBuzz");
		NumericRangeSorter sorter = new NumericRangeSorter(sortSpec);
		return sorter.getSortedNumericValueMap(upperBound);		
	}
	
	public void loadNumericSortSpecConfig() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
	{

		if (sortSpecMap.isEmpty())
		{
			nodeMap = loadConfig();
			for (Map.Entry<String, Node> entry : nodeMap.entrySet())
			{
				loadSortSpec((Element)entry.getValue());
			}

		}

		
	}
	
	public Map<String, Node> loadConfig() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(SORT_SPEC_CONFIG_FILENAME);
		Map<String, Node> nodeMap = new HashMap<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		
		String expression = "//SortConfigs/SortConfig";
		XPathExpression expr = xPath.compile(expression);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < ((NodeList)result).getLength() ; i++)
		{
			Element nodeElement = (Element)(((NodeList)result).item(i));
			nodeMap.put(nodeElement.getAttribute("ID"), nodeElement);
		}
		return nodeMap;
				
	}
	
	public static void loadSortSpec(Element nodeElement)
	{
		String sortSpecID = nodeElement.getAttribute("ID");
		if (! sortSpecMap.containsKey(sortSpecID) )
		{
			//NumericSortSpec.Builder numericSortSpecBuilder = null;
			NodeList sortParamsNodeList = nodeElement.getElementsByTagName("SortParam");
			Map<String, List<Integer>> sortParamMap = new HashMap<>();

			for (int i = 0 ; i < sortParamsNodeList.getLength() ; i++)
			{
				String paramName;
				List<Integer> paramValueList = new ArrayList<>();
				Element sortParamElement = (Element) sortParamsNodeList.item(i);
				paramName = sortParamElement.getAttribute("Name");
				Element sortParamValueElements = (Element) sortParamElement.getElementsByTagName("ParamValues").item(0);
				NodeList sortParamValueElementList = sortParamValueElements.getElementsByTagName("ParamValue");
				for (int j = 0 ; j < sortParamValueElementList.getLength() ; j++)
				{
					Element sortParamValueElement = (Element) sortParamValueElementList.item(j);
					paramValueList.add(Integer.parseInt(sortParamValueElement.getAttribute("Value")));
				}

				sortParamMap.put(paramName, paramValueList);
		
			}
			Element sortParamOperatorElement = (Element) nodeElement.getElementsByTagName("ParamOperator").item(0);
			String sortParamOperator = sortParamOperatorElement.getAttribute("Value");

			NumericSortSpec.Builder numericSortSpecBuilder = new NumericSortSpec.Builder(sortSpecID, sortParamOperator, false, sortParamMap);
			NumericSortSpec sortSpec = numericSortSpecBuilder.build();
			sortSpecMap.put(sortSpecID, sortSpec);

		}
	}
}
