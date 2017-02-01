package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedDataCustomer
{
	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("IsReturningToPlatform")
	private boolean isReturningToPlatform;

	@JsonProperty("HasGoodHistory")
	private boolean hasGoodHistory;

	@JsonProperty("HasBadHistory")
	private boolean hasBadHistory;

	@JsonProperty("HasSuspiciousHistory")
	private boolean hasSuspiciousHistory;

	@JsonProperty("IsNameAddressCombinationValid")
	private boolean isNameAddressCombinationValid;

	@JsonProperty("Email")
	private ComputedDataEmail email;

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public boolean isReturningToPlatform() {
		return isReturningToPlatform;
	}

	public void setReturningToPlatform(boolean isReturningToPlatform) {
		this.isReturningToPlatform = isReturningToPlatform;
	}

	public boolean isHasGoodHistory() {
		return hasGoodHistory;
	}

	public void setHasGoodHistory(boolean hasGoodHistory) {
		this.hasGoodHistory = hasGoodHistory;
	}

	public boolean isHasBadHistory() {
		return hasBadHistory;
	}

	public void setHasBadHistory(boolean hasBadHistory) {
		this.hasBadHistory = hasBadHistory;
	}

	public boolean isHasSuspiciousHistory() {
		return hasSuspiciousHistory;
	}

	public void setHasSuspiciousHistory(boolean hasSuspiciousHistory) {
		this.hasSuspiciousHistory = hasSuspiciousHistory;
	}

	public boolean isNameAddressCombinationValid() {
		return isNameAddressCombinationValid;
	}

	public void setNameAddressCombinationValid(boolean isNameAddressCombinationValid) {
		this.isNameAddressCombinationValid = isNameAddressCombinationValid;
	}

	public ComputedDataEmail getEmail() {
		return email;
	}

	public void setEmail(ComputedDataEmail email) {
		this.email = email;
	}
}