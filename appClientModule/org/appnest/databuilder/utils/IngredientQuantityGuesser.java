package org.appnest.databuilder.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.appnest.databuilder.appdata.RecipeValueObject;

public class IngredientQuantityGuesser {
	
	private static Logger logger = Logger.getLogger(IngredientQuantityGuesser.class);
	
	private final static Pattern QUANTITY_FILTERS = Pattern.compile("[0-9]+");

	private static List<String> quantityFlag;
	
	public static void doMagic(String rawIngredient, RecipeValueObject recipeValueObject){
		initQuantityFlag();
		
		Matcher m = QUANTITY_FILTERS.matcher(rawIngredient);
		
		boolean found = false;
		while (m.find()) {
			logger.info(m.group());
			
			found = true;
		}
		if (!found) {
			logger.info("No match found.%n");
		}
	}

	private static void initQuantityFlag() {
		quantityFlag = new ArrayList<String>();
		quantityFlag.add("gr");
		quantityFlag.add("kg");
		quantityFlag.add("lt");
		quantityFlag.add("ml");
		quantityFlag.add("cl");
		
	}
	
	
}
