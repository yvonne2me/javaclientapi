package com.trustev.domain.entities;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

public class RawDataCustomer
{
	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("Emails")
	private Collection<RawDataEmail> emails;

	@JsonProperty("SocialSecurityNumber")
	private String socialSecurityNumber;

	@JsonProperty("PhoneNumber")
	private String phoneNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<RawDataEmail> getEmails() {
		return emails;
	}

	public void setEmails(Collection<RawDataEmail> emails) {
		this.emails = emails;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}