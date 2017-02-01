package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedData
{
	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("Velocity")
	private ComputedDataVelocity velocity;

	@JsonProperty("BlackList")
	private ComputedDataList blackList;

	@JsonProperty("GreyList")
	private ComputedDataList greyList;

	@JsonProperty("WhiteList")
	private ComputedDataList whiteList;

	@JsonProperty("Customer")
	private ComputedDataCustomer customer;

	@JsonProperty("Transaction")
	private ComputedDataTransaction transaction;

	@JsonProperty("Location")
	private ComputedDataLocation location;

	@JsonProperty("Phone")
	private ComputedDataPhone phone;

	@JsonProperty("Account")
	private ComputedDataAccount account;


	public CaseType getCaseType() {
		return caseType;
	}


	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}


	public ComputedDataVelocity getVelocity() {
		return velocity;
	}


	public void setVelocity(ComputedDataVelocity velocity) {
		this.velocity = velocity;
	}


	public ComputedDataList getBlackList() {
		return blackList;
	}


	public void setBlackList(ComputedDataList blackList) {
		this.blackList = blackList;
	}


	public ComputedDataList getGreyList() {
		return greyList;
	}


	public void setGreyList(ComputedDataList greyList) {
		this.greyList = greyList;
	}


	public ComputedDataList getWhiteList() {
		return whiteList;
	}


	public void setWhiteList(ComputedDataList whiteList) {
		this.whiteList = whiteList;
	}


	public ComputedDataCustomer getCustomer() {
		return customer;
	}


	public void setCustomer(ComputedDataCustomer customer) {
		this.customer = customer;
	}


	public ComputedDataTransaction getTransaction() {
		return transaction;
	}


	public void setTransaction(ComputedDataTransaction transaction) {
		this.transaction = transaction;
	}


	public ComputedDataLocation getLocation() {
		return location;
	}


	public void setLocation(ComputedDataLocation location) {
		this.location = location;
	}


	public ComputedDataPhone getPhone() {
		return phone;
	}


	public void setPhone(ComputedDataPhone phone) {
		this.phone = phone;
	}


	public ComputedDataAccount getAccount() {
		return account;
	}


	public void setAccount(ComputedDataAccount account) {
		this.account = account;
	}
}
