package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

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