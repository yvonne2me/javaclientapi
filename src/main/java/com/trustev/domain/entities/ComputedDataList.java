package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class ComputedDataList
{
	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("WasBinHit")
	private boolean wasBinHit;

	@JsonProperty("WasEmailDomainHit")
	private boolean wasEmailDomainHit;

	@JsonProperty("WasFullEmailAddressHit")
	private boolean wasFullEmailAddressHit;

	@JsonProperty("WasPostCodeHit")
	private boolean wasPostCodeHit;

	@JsonProperty("WasCustomerIdHit")
	private boolean wasCustomerIdHit;

	@JsonProperty("WasAccountNumberHit")
	private boolean wasAccountNumberHit;

	@JsonProperty("WasIPHit")
	private boolean wasIPHit;

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public boolean isWasBinHit() {
		return wasBinHit;
	}

	public void setWasBinHit(boolean wasBinHit) {
		this.wasBinHit = wasBinHit;
	}

	public boolean isWasEmailDomainHit() {
		return wasEmailDomainHit;
	}

	public void setWasEmailDomainHit(boolean wasEmailDomainHit) {
		this.wasEmailDomainHit = wasEmailDomainHit;
	}

	public boolean isWasFullEmailAddressHit() {
		return wasFullEmailAddressHit;
	}

	public void setWasFullEmailAddressHit(boolean wasFullEmailAddressHit) {
		this.wasFullEmailAddressHit = wasFullEmailAddressHit;
	}

	public boolean isWasPostCodeHit() {
		return wasPostCodeHit;
	}

	public void setWasPostCodeHit(boolean wasPostCodeHit) {
		this.wasPostCodeHit = wasPostCodeHit;
	}

	public boolean isWasCustomerIdHit() {
		return wasCustomerIdHit;
	}

	public void setWasCustomerIdHit(boolean wasCustomerIdHit) {
		this.wasCustomerIdHit = wasCustomerIdHit;
	}

	public boolean isWasAccountNumberHit() {
		return wasAccountNumberHit;
	}

	public void setWasAccountNumberHit(boolean wasAccountNumberHit) {
		this.wasAccountNumberHit = wasAccountNumberHit;
	}

	public boolean isWasIPHit() {
		return wasIPHit;
	}

	public void setWasIPHit(boolean wasIPHit) {
		this.wasIPHit = wasIPHit;
	}
}
