package org.appnest.databuilder.appdata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="RECIPE_INGREDIENT")
public class RecipeIngredient extends BaseObject{
	
	@OneToOne 
	public Ingredient ingredient;
	
	@OneToMany
    @JoinColumn(name="ID")
	public List<Quantity> quantities;
	
	public RecipeIngredient(){
		super();
	}
	
	public List<Quantity> getQuantities() {
		return quantities;
	}
	public void setQuantities(List<Quantity> quantities) {
		this.quantities = quantities;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

}
