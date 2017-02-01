package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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