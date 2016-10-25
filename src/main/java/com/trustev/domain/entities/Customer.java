package com.trustev.domain.entities;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * The Customer object includes the information on a Customer such as Address, Names, Email, and Phone Number information.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends BaseObject{
	
		
	/**
	 * @return The First Name of the Customer.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName The First Name of the Customer.
	 */
	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return The Last Name of the Customer.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName The Last Name of the Customer.
	 */
	@JsonProperty("LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return A collection of Emails. Please see Emails object for further parameter information.
	 */
	public Collection<Email> getEmail() {
		return email;
	}
	
	/**
	 * @param email A collection of Emails. Please see Emails object for further parameter information.
	 */
	@JsonProperty("Emails")
	public void setEmail(Collection<Email> email) {
		this.email = email;
	}
	
	/**
	 * @return The Phone Number for the Customer.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * @param phoneNumber The Phone Number for the Customer.
	 */
	@JsonProperty("PhoneNumber")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
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
	 * @param dateOfBirth The Date of Birth of the Customer.
	 */
	@JsonProperty("DateOfBirth")
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
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
	 * 
	 * @return The Account number of the Customer.
	 */
	public String getSocialSecurityNumber() {
		return this.socialSecurityNumber;
	}
	
	/**
	 * 
	 * @param socialSecurityNumber The Social Security Number of the customer
	 */
	@JsonProperty("SocialSecurityNumber")
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	
	private String firstName;
	private String lastName;
	private Collection<Email> email;
	private String phoneNumber;
	private Date dateOfBirth;
	private Collection<Address> addresses;
	private String accountNumber;
	private String socialSecurityNumber;
	
}
