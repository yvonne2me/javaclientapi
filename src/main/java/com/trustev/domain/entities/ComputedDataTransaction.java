package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class ComputedDataTransaction
{
	@JsonProperty("Email")
	private ComputedDataEmail email;

	public ComputedDataEmail getEmail() {
		return email;
	}

	public void setEmail(ComputedDataEmail email) {
		this.email = email;
	}
}