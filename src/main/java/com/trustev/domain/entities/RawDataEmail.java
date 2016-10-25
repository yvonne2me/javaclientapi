package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class RawDataEmail
{
	@JsonProperty("EmailAddress")
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}