package com.trustev.integration;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * Represents a Customer.  Each case can have one associated customer
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Customer extends ChildObject<Customer>{
	
		
	/**
	 * 
	 * @return The First Name of the Customer.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * 
	 * @param firstName The First Name of the Customer.
	 */
	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return The Last Name of the Customer.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * 
	 * @param lastName The Last Name of the Customer.
	 */
	@JsonProperty("LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return A collection of Emails. Please see Emails object for further parameter information.
	 */
	public Collection<Email> getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param email A collection of Emails. Please see Emails object for further parameter information.
	 */
	@JsonProperty("Emails")
	public void setEmail(Collection<Email> email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @return The Phone Number for the Customer.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * 
	 * @param phoneNumber The Phone Number for the Customer.
	 */
	@JsonProperty("PhoneNumber")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * 
	 * @return The Date of Birth of the Customer.
	 */
	@JsonIgnore()
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	
	@JsonProperty("DateOfBirth")
	String getDateOfBirthString() {
		return FormatTimeStamp(dateOfBirth);
	}
	
	/**
	 * 
	 * @param dateOfBirth The Date of Birth of the Customer.
	 */
	@JsonProperty("DateOfBirth")
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * 
	 * @return Addresses Object  Contains standard/delivery/billing information. Please see Address Object for further parameter information.
	 */
	public Collection<Address> getAddresses() {
		return addresses;
	}
	
	/**
	 * 
	 * @param addresses Addresses Object  Contains standard/delivery/billing information. Please see Address Object for further parameter information.
	 */
	@JsonProperty("Addresses")
	public void setAddresses(Collection<Address> addresses) {
		this.addresses = addresses;
	}
	
	/**
	 * 
	 * @return Social Account Object  Contains Short Term and Long Term Access Tokens, along with Social Account Ids and Types. See Trustev Integration Documentation, http://developers.trustev.com/v2 for more information.
	 */
	public Collection<SocialAccount> getSocialAccounts() {
		return socialAccounts;
	}
	
	/**
	 * 
	 * @param socialAccounts Social Account Object  Contains Short Term and Long Term Access Tokens, along with Social Account Ids and Types. See Trustev Integration Documentation, http://developers.trustev.com/v2 for more information.
	 */
	@JsonProperty("SocialAccounts")
	public void setSocialAccounts(Collection<SocialAccount> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
	
	/**
	 * 
	 * @return The Account number of the Customer.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	
	/**
	 * 
	 * @param accountNumber The Account number of the Customer.
	 */
	@JsonProperty("AccountNumber")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	/**
	 * Adds the customer to a previously saved Case. 
	 * 
	 * @param caseId The case id of the case to save the customer against
	 * @throws TrustevApiException
	 */
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		if (this.getId() == null) {
			String path = "case/{caseId}/customer".replace("{caseId}",caseId);
			this.id = callApiMethodFor(path, "POST").id;
		}
		else {
			String path = "case/{caseId}/customer".replace("{caseId}",caseId);
			callApiMethodFor(path, "PUT");	
		}
	}

	/**
	 * Finds a Customer that is attached to a previously saved Case
	 * 
	 * @param caseId The case id of the case that the customer is attached to
	 * @return The customer object associated with the provided case
	 * @throws TrustevApiException
	 */
	public static Customer Find(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/customer".replace("{caseId}",caseId);
		return (Customer) callApiMethodFor(path, null, Customer.class, "GET");	
	}
	
	private String firstName;
	private String lastName;
	private Collection<Email> email;
	private String phoneNumber;
	private Date dateOfBirth;
	private Collection<Address> addresses;
	private Collection<SocialAccount> socialAccounts;
	private String accountNumber;
	
}
