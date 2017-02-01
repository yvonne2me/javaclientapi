package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedDataPhone
{
	@JsonProperty("IsPhoneRisky")
	private boolean isPhoneRisky;

	public boolean isPhoneRisky() {
		return isPhoneRisky;
	}

	public void setPhoneRisky(boolean isPhoneRisky) {
		this.isPhoneRisky = isPhoneRisky;
	}
}