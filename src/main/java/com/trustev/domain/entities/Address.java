package com.trustev.domain.entities;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Represents an Address for either a Customer or Transaction
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address extends BaseObject {
	
		
	/**
	 * @return The First Name for the Standard/Billing/Delivery Address.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName The First Name for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return The Last Name for the Standard/Billing/Delivery Address.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * 
	 * @param lastName The Last Name for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return Address Line 1 for the Standard/Billing/Delivery Address.
	 */
	public String getAddress1() {
		return address1;
	}
	
	/**
	 * @param address1 Address Line 1 for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("Address1")
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	/**
	 * @return Address Line 2 for the Standard/Billing/Delivery Address.
	 */
	public String getAddress2() {
		return address2;
	}
	
	/**
	 * @param address2 Address Line 2 for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("Address2")
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	/**
	 * @return Address Line 3 for the Standard/Billing/Delivery Address.
	 */
	public String getAddress3() {
		return address3;
	}
	
	/**
	 * @param address3 Address Line 3 for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("Address3")
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	/**
	 * @return City for the Standard/Billing/Delivery Address.
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city City for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("City")
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return State for the Standard/Billing/Delivery Address.
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param state State for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("State")
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return The Postal Code for the Standard/Billing/Delivery Address.
	 */
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * @param postalCode The Postal Code for the Standard/Billing/Delivery Address.
	 */
	@JsonProperty("PostalCode")
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * @return The Address Type - Standard (0), Billing (1), or Delivery (2)
	 */
	public AddressType getType() {
		return type;
	}
	
	/**
	 * @param type The Address Type - Standard (0), Billing (1), or Delivery (2)
	 */
	@JsonProperty("Type")
	public void setType(AddressType type) {
		this.type = type;
	}
	
	/**
	 * @return  These are the 2 letter country codes published by ISO. Details can be found at http://www.nationsonline.org/oneworld/countrycodes.htm
	 */
	public String getCountryCode() {
		return countryCode;
	}
	
	/**
	 * @param countryCode These are the 2 letter country codes published by ISO. Details can be found at http://www.nationsonline.org/oneworld/countrycodes.htm
	 */
	@JsonProperty("CountryCode")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	/**
	 * @return  Current Timestamp.
	 */
	@JsonIgnore()
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonProperty("Timestamp")
	String getTimestampString() {
		return FormatTimeStamp(timestamp);
	}
	
	/**
	 * @param timestamp  Current Timestamp.
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return  Is this the default address?
	 */
	public boolean getIsDefault() {
		return isDefault;
	}
	
	/**
	 * @param isDefault Is this the default address?
	 */
	@JsonProperty("IsDefault")
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	private String firstName;
	
	private String lastName;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private String city;
	
	private String state;
	
	private String postalCode;
	
	private AddressType type;
	
	private String countryCode;
	
	private Date timestamp;
	
	private boolean isDefault;
	
}
