package org.appnest.databuilder.cooker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.appnest.databuilder.utils.DataBuilderConfigFileReader;
import org.appnest.databuilder.utils.DataBuilderConstants;


public class DataParserRuleManager {
	
	private static Logger logger = Logger.getLogger(DataParserRuleManager.class);
	
	private Map<String,String> parserRulesMap;
	
	public DataParserRuleManager(){
		List<String> parserRulesString = new ArrayList<String>();
		try {
			parserRulesString = DataBuilderConfigFileReader.readListFromFile(DataBuilderConstants.PARSER_RULES_FILE_PATH);
		} catch (IOException e) {
			logger.error("some error during parser rules parsing", e);
		}
		parserRulesMap = new HashMap<String, String>();
		for (String parserRuleString : parserRulesString) {
			String[] splited = StringUtils.split(parserRuleString, "|");
			if (splited.length == 3) {
				parserRulesMap.put(splited[0]+"|"+splited[1], splited[2]);
			}
		}
	}
	
	
	public String getGenericDataSelector(String domain, String dataKey){
		String selector = parserRulesMap.get(domain+"|"+dataKey); 
		if (selector != null) return selector;
		logger.warn("missing rule for: " + domain+"|"+dataKey);
		return "null-"+dataKey+"-selector" ;
	}
	
	
	
	
	public void setParserRulesMap(Map<String,String> parserRulesMap) {
		this.parserRulesMap = parserRulesMap;
	}

	public Map<String,String> getParserRulesMap() {
		return parserRulesMap;
	}
}
