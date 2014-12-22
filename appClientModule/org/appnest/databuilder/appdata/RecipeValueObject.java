package org.appnest.databuilder.appdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class RecipeValueObject implements Serializable{

	private static final long serialVersionUID = 7911367659181417961L;
	
	private String name;
	private List<String> ingredients;
	private List<RecipeIngredient> recipeIngredients;
	private List<String> insctructions;
	private List<RecipeInstruction> recipeInstructions;
	
	private String duration;
	private List<String> tags;
	private List<String> categories;
	private String url;
	private String domain;
	private String makingTime;
	private String cookingTime;
	private String forHowManyPeople;
	
	
	public RecipeValueObject() {
		super();
		ingredients = new ArrayList<String>();
		insctructions = new ArrayList<String>();
		tags = new ArrayList<String>();
		categories = new ArrayList<String>();
	}


	@SuppressWarnings("unchecked")
	public String toString(){
		String result = "";
		
		result += "URL: " + url + "\n";
		result += "Name: " + name + "\n";
		if (CollectionUtils.isNotEmpty(ingredients)) result += "Ingredients: "+ StringUtils.join(ingredients) + "\n";
		if (CollectionUtils.isNotEmpty(insctructions)) result += "Instructions: "+ StringUtils.join(insctructions) + "\n";
		if (CollectionUtils.isNotEmpty(tags)) result += "Tags: "+ StringUtils.join(tags) + "\n";
		if (CollectionUtils.isNotEmpty(categories)) result += "Categories: "+ StringUtils.join(categories) + "\n";
		result += "Making time: " + makingTime + "\n";
		result += "Cooking time: " + cookingTime + "\n";
		result += "For how many people: " + forHowManyPeople + "\n";
		result += "=================================\n";
		
		return result;
	}
	

	
	public void addIngredient(String ingredient){
		ingredients.add(ingredient);
		// TODO try to guess the quantity
	}
	
	public void addInstruction(String instruction){
		insctructions.add(instruction);
		// TODO here we have to separate the instrucion
	}
	
	public void addTag(String tag){
		tags.add(tag);
		// TODO try to guess the tags
	}
	
	public void addCategory(String category){
		categories.add(category);
		// TODO try to guess the categories
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public List<String> getInsctructions() {
		return insctructions;
	}
	public void setInsctructions(List<String> insctructions) {
		this.insctructions = insctructions;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<String> getCategories() {
		return categories;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setMakingTime(String makingTime) {
		this.makingTime = makingTime;
	}

	public String getMakingTime() {
		return makingTime;
	}

	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getCookingTime() {
		return cookingTime;
	}


	public void setForHowManyPeople(String forHowManyPeople) {
		this.forHowManyPeople = forHowManyPeople;
	}


	public String getForHowManyPeople() {
		return forHowManyPeople;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getDomain() {
		return domain;
	}


	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}


	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}
	
}
