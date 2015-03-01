package org.appnest.databuilder;

import org.apache.log4j.Logger;
import org.appnest.databuilder.appdata.RecipeCooker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DataAnalyzer {

	private Logger logger = Logger.getLogger(DataAnalyzer.class);
	
	private RecipeCooker recipeCooker;
	
	@Inject
	public DataAnalyzer(RecipeCooker recipeCooker) {
		this.recipeCooker = recipeCooker;
	}
	
	public void analyzeData(){
		recipeCooker.restoreRecipesFromFilesystem();
//		recipeCooker.printAllRecipes();
		recipeCooker.tryToGuessRecipesInfo();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new DataBuilderModule());
		DataAnalyzer dataAnalyzer = injector.getInstance(DataAnalyzer.class);
		dataAnalyzer.analyzeData();
	}

}
