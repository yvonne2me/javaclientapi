package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedDataAccount
{
	@JsonProperty("CustomerHas1ExistingAccount")
	private boolean customerHas1ExistingAccount;

	
	@JsonProperty("CustomerHas2ExistingAccounts")
	private boolean customerHas2ExistingAccounts;

	
	@JsonProperty("CustomerHas3ExistingAccounts")
	private boolean customerHas3ExistingAccounts;

	
	@JsonProperty("CustomerHas4ExistingAccounts")
	private boolean customerHas4ExistingAccounts;

	
	@JsonProperty("CustomerHasMoreThan5ExistingAccounts")
	private boolean customerHasMoreThan5ExistingAccounts;


	public boolean isCustomerHas1ExistingAccount() {
		return customerHas1ExistingAccount;
	}


	public void setCustomerHas1ExistingAccount(boolean customerHas1ExistingAccount) {
		this.customerHas1ExistingAccount = customerHas1ExistingAccount;
	}


	public boolean isCustomerHas2ExistingAccounts() {
		return customerHas2ExistingAccounts;
	}


	public void setCustomerHas2ExistingAccounts(boolean customerHas2ExistingAccounts) {
		this.customerHas2ExistingAccounts = customerHas2ExistingAccounts;
	}


	public boolean isCustomerHas3ExistingAccounts() {
		return customerHas3ExistingAccounts;
	}


	public void setCustomerHas3ExistingAccounts(boolean customerHas3ExistingAccounts) {
		this.customerHas3ExistingAccounts = customerHas3ExistingAccounts;
	}


	public boolean isCustomerHas4ExistingAccounts() {
		return customerHas4ExistingAccounts;
	}


	public void setCustomerHas4ExistingAccounts(boolean customerHas4ExistingAccounts) {
		this.customerHas4ExistingAccounts = customerHas4ExistingAccounts;
	}


	public boolean isCustomerHasMoreThan5ExistingAccounts() {
		return customerHasMoreThan5ExistingAccounts;
	}


	public void setCustomerHasMoreThan5ExistingAccounts(boolean customerHasMoreThan5ExistingAccounts) {
		this.customerHasMoreThan5ExistingAccounts = customerHasMoreThan5ExistingAccounts;
	}
}