package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class ComputedDataBIN
{
	@JsonProperty("DoesMatchCustomerBillingAddressCountry")
	private boolean doesMatchCustomerBillingAddressCountry;

	@JsonProperty("DoesMatchCustomerDeliveryAddressCountry")
	private boolean doesMatchCustomerDeliveryAddressCountry;

	@JsonProperty("DoesMatchTransactionBillingAddressCountry")
	private boolean doesMatchTransactionBillingAddressCountry;

	@JsonProperty("DoesMatchTransactionDeliveryAddressCountry")
	private boolean doesMatchTransactionDeliveryAddressCountry;

	@JsonProperty("DoesMatchIPCountry")
	private boolean doesMatchIPCountry;

	@JsonProperty("IsCountryDomestic")
	private boolean isCountryDomestic;

	public boolean isDoesMatchCustomerBillingAddressCountry() {
		return doesMatchCustomerBillingAddressCountry;
	}

	public void setDoesMatchCustomerBillingAddressCountry(boolean doesMatchCustomerBillingAddressCountry) {
		this.doesMatchCustomerBillingAddressCountry = doesMatchCustomerBillingAddressCountry;
	}

	public boolean isDoesMatchCustomerDeliveryAddressCountry() {
		return doesMatchCustomerDeliveryAddressCountry;
	}

	public void setDoesMatchCustomerDeliveryAddressCountry(boolean doesMatchCustomerDeliveryAddressCountry) {
		this.doesMatchCustomerDeliveryAddressCountry = doesMatchCustomerDeliveryAddressCountry;
	}

	public boolean isDoesMatchTransactionBillingAddressCountry() {
		return doesMatchTransactionBillingAddressCountry;
	}

	public void setDoesMatchTransactionBillingAddressCountry(boolean doesMatchTransactionBillingAddressCountry) {
		this.doesMatchTransactionBillingAddressCountry = doesMatchTransactionBillingAddressCountry;
	}

	public boolean isDoesMatchTransactionDeliveryAddressCountry() {
		return doesMatchTransactionDeliveryAddressCountry;
	}

	public void setDoesMatchTransactionDeliveryAddressCountry(boolean doesMatchTransactionDeliveryAddressCountry) {
		this.doesMatchTransactionDeliveryAddressCountry = doesMatchTransactionDeliveryAddressCountry;
	}

	public boolean isDoesMatchIPCountry() {
		return doesMatchIPCountry;
	}

	public void setDoesMatchIPCountry(boolean doesMatchIPCountry) {
		this.doesMatchIPCountry = doesMatchIPCountry;
	}

	public boolean isCountryDomestic() {
		return isCountryDomestic;
	}

	public void setCountryDomestic(boolean isCountryDomestic) {
		this.isCountryDomestic = isCountryDomestic;
	}
}