package org.appnest.databuilder.appdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="INGREDIENT")
public class Ingredient extends BaseObject{
	
	@Column(name="INGREDIENT")
	private String ingredient;

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getIngredient() {
		return ingredient;
	}
}
