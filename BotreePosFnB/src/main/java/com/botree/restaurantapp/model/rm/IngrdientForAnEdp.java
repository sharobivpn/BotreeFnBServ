package com.botree.restaurantapp.model.rm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class IngrdientForAnEdp implements Serializable {
	@Expose
	private int invTypeItemId;

	@Expose
	private Ingredient ingredient;

	@Expose
	private double totalQuantity;

	@Expose
	private double reqQuantity;

	private static final long serialVersionUID = 1L;

	public int getInvTypeItemId() {
		return invTypeItemId;
	}

	public void setInvTypeItemId(int invTypeItemId) {
		this.invTypeItemId = invTypeItemId;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getReqQuantity() {
		return reqQuantity;
	}

	public void setReqQuantity(double reqQuantity) {
		this.reqQuantity = reqQuantity;
	}

}