package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * A TransactionItem on a previously saved Transaction
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TransactionItem extends BaseObject {
	private String name;
	private int quantity;
	private double itemValue;
	
		
	/**
	 * 
	 * @return  The Name of the Item being purchased.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name  The Name of the Item being purchased.
	 */
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return  The Quantity of the Item being purchased.
	 */
	public int getQuantity() {
		return quantity;
	}
	
	
	/**
	 * 
	 * @param quantity  The Quantity of the Item being purchased.
	 */
	@JsonProperty("Quantity")
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 * @return  The Value of the Item being purchased.
	 */
	public double getItemValue() {
		return itemValue;
	}
	
	/**
	 * 
	 * @param itemValue  The Value of the Item being purchased.
	 */
	@JsonProperty("ItemValue")
	public void setItemValue(double itemValue) {
		this.itemValue = itemValue;
	}
}
