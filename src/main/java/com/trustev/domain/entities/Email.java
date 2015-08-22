package com.trustev.integration;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * Email Address for a customer
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Email extends ChildObject<Email> {
	private String emailAddress;
	private boolean isDefault;
	
		
	/**
	 * 
	 * @return Email Address of the customer
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	
	/**
	 * 
	 * @param emailAddress Email Address of the customer
	 */
	@JsonProperty("EmailAddress")
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * 
	 * @return Is this is the default email for the customer?
	 */
	public boolean isDefault() {
		return isDefault;
	}
	
	/**
	 * 
	 * @param isDefault Is this is the default email for the customer?
	 */
	@JsonProperty("IsDefault")
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * Adds the email to the customer on a previously saved Case. 
	 * 
	 * @param caseId The case id of the case to save the email against
	 * @throws TrustevApiException
	 */
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		if (this.getId() == null) {
			String path = "case/{caseId}/customer/email".replace("{caseId}",caseId);
			this.id = callApiMethodFor(path, "POST").id;
		}
		else {
			String path = "case/{caseId}/customer/email/{id}".replace("{caseId}",caseId).replace("{id}", this.id);
			this.id = callApiMethodFor(path, "PUT").id;
		}
	}
	
	/**
	 * Finds a single Email address that is attached to a previously saved Case
	 * 
	 * @param caseId The case id of the case that the Email is attached to
	 * @param id The email id of the email address to be retrieved
	 * @return An Email object
	 * @throws TrustevApiException
	 */
	public static Email Find(String caseId, String id) throws TrustevApiException {
		String path = "case/{caseId}/customer/email/{id}".replace("{caseId}",caseId).replace("{id}",id);
		return (Email) callApiMethodFor(path, null, Email.class, "GET");	
	}
	
	/**
	 * Finds all of the email addresses associated with a previously saved case
	 * 
	 * @param caseId The case id of the case that the Email is attached to
	 * @return A collection of all the Email Addresses on the Case
	 * @throws TrustevApiException
	 */
	public static Collection<Email> FindAll(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/customer/email".replace("{caseId}",caseId);
		GenericType<Collection<Email>> type = new GenericType<Collection<Email>>(){};
		ClientResponse response = callApiMethod(path, null, "GET");
		return response.getEntity(type);
	}
}
