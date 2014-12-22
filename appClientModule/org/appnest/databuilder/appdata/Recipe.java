package org.appnest.databuilder.appdata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="RECIPE")
public class Recipe extends BaseObject {

	@Column(name="NAME")
	private String name;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="DOMAIN")
	private String domain;
	
	@OneToMany
	@JoinColumn(name="ID")
	private List<RecipeIngredient> ingredients;
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setIngredients(List<RecipeIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<RecipeIngredient> getIngredients() {
		return ingredients;
	}
}
