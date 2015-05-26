package com.trustev.integration;

import java.math.BigDecimal;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * A TransactionItem on a previously saved Transaction
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TransactionItem extends ChildObject<TransactionItem> {
	private String name;
	private int quantity;
	private BigDecimal itemValue;
	
		
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
	public BigDecimal getItemValue() {
		return itemValue;
	}
	
	/**
	 * 
	 * @param itemValue  The Value of the Item being purchased.
	 */
	@JsonProperty("ItemValue")
	public void setItemValue(BigDecimal itemValue) {
		this.itemValue = itemValue;
	}
	
	/**
	 * Adds the TransactionItem to the Transaction on a previously saved Case. 
	 * 
	 * @param caseId The case id of the case to save against
	 * @throws TrustevApiException
	 */
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		if (this.getId() == null) {
			String path = "case/{caseId}/transaction/item".replace("{caseId}",caseId);
			this.id = callApiMethodFor(path, "POST").id;
		}
		else {
			String path = "case/{caseId}/transaction/item/{id}".replace("{caseId}",caseId).replace("{id}", this.id);
			this.id = callApiMethodFor(path, "PUT").id;
		}
	}
	
	/**
	 * Finds a single Transaction that is attached to a previously saved Case
	 * 
	 * @param caseId The case id of the case that the TransactionItem is attached to
	 * @param id The id of the TransactionItem to be retrieved
	 * @return A TransactionItem
	 * @throws TrustevApiException
	 */
	public static TransactionItem Find(String caseId, String id) throws TrustevApiException {
		String path = "case/{caseId}/transaction/item/{id}".replace("{caseId}",caseId).replace("{id}",id);
		return (TransactionItem) callApiMethodFor(path, null, TransactionItem.class, "GET");	
	}
	
	/**
	 * Finds all of the transactions associated with a previously saved case
	 * 
	 * @param caseId The case id of the case that the TransactionItem is attached to
	 * @return A collection of all the TransactionItems on the Case
	 * @throws TrustevApiException
	 */
	public static Collection<TransactionItem> FindAll(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/transaction/item".replace("{caseId}",caseId);
		GenericType<Collection<TransactionItem>> type = new GenericType<Collection<TransactionItem>>(){};
		ClientResponse response = callApiMethod(path, null, "GET");
		return response.getEntity(type);
	}
}
