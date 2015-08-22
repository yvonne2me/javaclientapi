package com.trustev.domain.entities;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Email Address for a customer
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Email extends BaseObject {
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
}
