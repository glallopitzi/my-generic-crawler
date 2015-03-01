package org.appnest.databuilder;

import org.apache.log4j.Logger;
import org.appnest.databuilder.appdata.RecipeCooker;
import org.springframework.beans.factory.annotation.Autowired;

public class DataAnalyzer {

	private Logger logger = Logger.getLogger(DataAnalyzer.class);
	
	@Autowired
	private RecipeCooker recipeCooker;
	
	public void analyzeData(){
		logger.debug("analyzeData");
		
		recipeCooker.restoreRecipesFromFilesystem();
//		recipeCooker.printAllRecipes();
		recipeCooker.tryToGuessRecipesInfo();
		
	}
	
}
