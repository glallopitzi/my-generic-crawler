package org.appnest.databuilder.appdata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.appnest.databuilder.DataBuilderConnectionManager;
import org.appnest.databuilder.cooker.DataCooker;
import org.appnest.databuilder.cooker.DataParserRuleManager;
import org.appnest.databuilder.utils.DataBuilderConstants;
import org.appnest.databuilder.utils.DataBuilderCrawledObjectHandler;
import org.appnest.databuilder.utils.IngredientQuantityGuesser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.inject.Inject;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class RecipeCooker implements DataCooker{
	
	private Logger logger = Logger.getLogger(RecipeCooker.class);
	
	private int cookedData;
	private List<RecipeValueObject> recipeValueObjects;
	
	private DataParserRuleManager dataParserRuleManager;
	private DataBuilderConnectionManager connectionManager;
	
	private String recipeParsingRuleKey 					= "recipe";
	private String recipeTitleParsingRuleKey 				= "title";
	private String recipeIngredientsParsingRuleKey 			= "ingredients";
	private String recipeInstructionsParsingRuleKey 		= "instructions";
	private String recipeDurationParsingRuleKey 			= "duration";
	private String recipeMakingTimeParsingRuleKey 			= "making-time";
	private String recipeCookingTimeParsingRuleKey 			= "cooking-time";
	private String recipeForHowManyPeopleParsingRuleKey 	= "for-how-many-people";
	
	
	@Inject
	public RecipeCooker(DataParserRuleManager dataParserRuleManager, DataBuilderConnectionManager dataBuilderConnectionManager) {
		super();
		
		this.cookedData = 0;
		this.recipeValueObjects = new ArrayList<RecipeValueObject>();
		this.dataParserRuleManager = dataParserRuleManager;
		this.connectionManager = dataBuilderConnectionManager;
		
		logger.info("RecipeCooker initiated");
	}


	@Override
	public void cookPage(Page page){
		// try to extract recipe from page
		RecipeValueObject recipeValueObject = parseHtml(page);
		
		if (recipeValueObject != null){
			saveHtmlPage(recipeValueObject);
			saveRecipeObj(recipeValueObject);
			// TODO here save the object over db
			saveRecipeData(recipeValueObject);
		}
	}
	
	
	/**
	 * TODO try to persist object over db
	 * 
	 * @param recipeValueObject
	 */
	private void saveRecipeData(RecipeValueObject recipeValueObject) {
		Recipe recipeToSave = new Recipe();
		recipeToSave.setName(recipeValueObject.getName());
		recipeToSave.setUrl(recipeValueObject.getUrl());
		recipeToSave.setDomain(recipeValueObject.getDomain());		
		Session session = connectionManager.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(recipeToSave);
		tx.commit();
		session.close();
	}


	private void saveRecipeObj(RecipeValueObject recipeValueObject) {
		String objFilename = DataBuilderConstants.CRAWLED_OBJS_FOLDER 
				+ recipeValueObject.getDomain() + " - " + recipeValueObject.getName() + ".OBJ";
		
		DataBuilderCrawledObjectHandler.writeObjOnFileSystem(recipeValueObject, objFilename);
		logger.info("Object saved on: " + objFilename);
	}


	private void saveHtmlPage(RecipeValueObject recipeValueObject) {
		String htmlFilename = DataBuilderConstants.CRAWLED_HTML_PAGES_FOLDER
				+ recipeValueObject.getDomain() + " - " + recipeValueObject.getName() + ".RECIPE";
		
		File file = new File(htmlFilename);
		try {
			FileUtils.write(file, recipeValueObject.toString());
		} catch (Exception e) {
			logger.error("error in saving html page", e);
		}
		
		logger.info("Page saved on: " + htmlFilename);
	}




	@Override
	public void cookData() {
		logger.info("cookData()");
		
		logger.info("restore data from filesystem");
		restoreRecipesFromFilesystem();
		
		tryToGuessRecipesInfo();
		
	}
	
	
	
	
	private RecipeValueObject parseHtml(Page page){
		this.cookedData++;
		
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		WebURL weburl = page.getWebURL();
		
		String html = htmlParseData.getHtml();
		String url = weburl.getURL();
		String domain = weburl.getDomain();
		String subdomain = weburl.getSubDomain();
		if (StringUtils.isNotBlank(subdomain)) domain = subdomain + "." + domain;

		RecipeValueObject recipeValueObject = null;
		if(checkIfPossibleRecipe(html, domain)){
			Document doc = Jsoup.parse(html);
			
			logger.info("I've found a recipe!");
			recipeValueObject = new RecipeValueObject();
			recipeValueObject.setUrl(url);
			recipeValueObject.setDomain(domain);
			
			Element recipeTitle = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeTitleParsingRuleKey)).first();
			Elements recipeIngredients = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeIngredientsParsingRuleKey));
			Elements recipeInstructions = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeInstructionsParsingRuleKey));
			Element recipeDuration = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeDurationParsingRuleKey)).first();
			Element recipeMakingTime = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeMakingTimeParsingRuleKey)).first();
			Element recipeCookingTime = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeCookingTimeParsingRuleKey)).first();
			Element recipeForHowManyPeople = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeForHowManyPeopleParsingRuleKey)).first();
			
			// RecipeValueObject title is mandatory
			if (recipeTitle != null) recipeValueObject.setName(recipeTitle.text());
			else return null;
			
			if (CollectionUtils.isNotEmpty(recipeIngredients)){
				for (Element element : recipeIngredients) {
					recipeValueObject.addIngredient(element.text());
				}
			}
			if (CollectionUtils.isNotEmpty(recipeInstructions)){
				for (Element element : recipeInstructions) {
					recipeValueObject.addInstruction(element.text());
				}
			}
			if (recipeDuration != null) recipeValueObject.setDuration(recipeDuration.text());
			if (recipeMakingTime != null) recipeValueObject.setMakingTime(recipeMakingTime.text());
			if (recipeCookingTime != null) recipeValueObject.setCookingTime(recipeCookingTime.text());
			if (recipeForHowManyPeople != null) recipeValueObject.setForHowManyPeople(recipeForHowManyPeople.text());
			
			this.recipeValueObjects.add(recipeValueObject);
		}
		
		return recipeValueObject;
	}
	
	
	public boolean checkIfPossibleRecipe(String html, String domain){
		boolean result = false;
		
		// check first for recipe container
		Document doc = Jsoup.parse(html);
		Elements recipesInPage = doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeParsingRuleKey));
		result = recipesInPage.size() > 0;
		
		// check here for title and ingredients
		if(!result){
			result = (doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeTitleParsingRuleKey)).size() > 0 
					|| doc.select(dataParserRuleManager.getGenericDataSelector(domain, recipeIngredientsParsingRuleKey)).size() > 0);
		}
		
		return result;
	}
	
	
	
	
	
	public void printAllRecipes(){

		for (RecipeValueObject recipeValueObject : recipeValueObjects) {
			logger.info(recipeValueObject.toString());
		}
		
		logger.info("Total parsed pages: " + cookedData);
		logger.info("Total recipeValueObjects found: " + recipeValueObjects.size());
	}
	
	
	
	
	
	public void restoreRecipesFromFilesystem(){
		File recipesObjFolder = new File(DataBuilderConstants.CRAWLED_OBJS_FOLDER);
		Collection<File> recipesObj = FileUtils.listFiles(recipesObjFolder, TrueFileFilter.INSTANCE,null);
		for (File file : recipesObj) {
			RecipeValueObject recipeValueObject = DataBuilderCrawledObjectHandler.readObjFromFileSystem(file.getAbsolutePath());
			if(recipeValueObject != null)recipeValueObjects.add(recipeValueObject);
		}
		logger.info("restored " + recipeValueObjects.size() + " recipeValueObjects from filesystem");
	}
	
	
	
	
	public void tryToGuessRecipesInfo(){
		for (RecipeValueObject recipeValueObject : recipeValueObjects) {
			List<String> ingredients = recipeValueObject.getIngredients();
			List<RecipeIngredient> recipeIngredients = new ArrayList<RecipeIngredient>();
			
			int index = 0;
			for (String ingredient : ingredients) {
				// TODO foreach collected ingredients, try to guess quantity
				RecipeIngredient recipeIngredient = new RecipeIngredient();
//				recipeIngredient.setIngredient(new Ingredient().setTag(ingredient));
				logger.info("Ingredient: " + ingredient);
				String quantity = tryToGuessRecipeIngredientQuantity(ingredient, recipeValueObject);
				logger.info("Quantity: " + quantity);
				
				
				index++;
			}

			
		}
	}
	
	
	

	
	
	private String tryToGuessRecipeIngredientQuantity(String string, RecipeValueObject recipeValueObject) {
		
		IngredientQuantityGuesser.doMagic(string, recipeValueObject);
		return null;
	}




	
	
	
	// GETTERS AND SETTERS

	public int getParsedData() {
		return cookedData;
	}

	public void setParsedData(int parsedData) {
		this.cookedData = parsedData;
	}

	public List<RecipeValueObject> getRecipes() {
		return recipeValueObjects;
	}

	public void setRecipes(List<RecipeValueObject> recipeValueObjects) {
		this.recipeValueObjects = recipeValueObjects;
	}
	
	public DataParserRuleManager getDataParserRuleManager() {
		return dataParserRuleManager;
	}

	public void setDataParserRuleManager(DataParserRuleManager dataParserRuleManager) {
		this.dataParserRuleManager = dataParserRuleManager;
	}
	
}
