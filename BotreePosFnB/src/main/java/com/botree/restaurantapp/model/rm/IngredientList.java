package com.botree.restaurantapp.model.rm;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class IngredientList implements Serializable {

	@Expose
	private List<Ingredient> ingredients = null;
	
	private static final long serialVersionUID = 1L;

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	

}