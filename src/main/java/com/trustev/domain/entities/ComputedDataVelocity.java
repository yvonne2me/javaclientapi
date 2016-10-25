package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class ComputedDataVelocity
{
	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("DecisionsWithin1Hour")
	private int decisionsWithin1Hour;

	@JsonProperty("DecisionsWithin24Hours")
	private int decisionsWithin24Hours;

	@JsonProperty("DecisionsWithin7Days")
	private int decisionsWithin7Days;

	@JsonProperty("DecisionsWithin30Days")
	private int decisionsWithin30Days;

	@JsonProperty("IsShortTermVelocityHigh")
	private boolean isShortTermVelocityHigh;

	@JsonProperty("IsShortTermVelocityMedium")
	private boolean isShortTermVelocityMedium;

	@JsonProperty("IsLongTermVelocityHigh")
	private boolean isLongTermVelocityHigh;

	@JsonProperty("IsLongTermVelocityMedium")
	private boolean isLongTermVelocityMedium;

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public int getDecisionsWithin1Hour() {
		return decisionsWithin1Hour;
	}

	public void setDecisionsWithin1Hour(int decisionsWithin1Hour) {
		this.decisionsWithin1Hour = decisionsWithin1Hour;
	}

	public int getDecisionsWithin24Hours() {
		return decisionsWithin24Hours;
	}

	public void setDecisionsWithin24Hours(int decisionsWithin24Hours) {
		this.decisionsWithin24Hours = decisionsWithin24Hours;
	}

	public int getDecisionsWithin7Days() {
		return decisionsWithin7Days;
	}

	public void setDecisionsWithin7Days(int decisionsWithin7Days) {
		this.decisionsWithin7Days = decisionsWithin7Days;
	}

	public int getDecisionsWithin30Days() {
		return decisionsWithin30Days;
	}

	public void setDecisionsWithin30Days(int decisionsWithin30Days) {
		this.decisionsWithin30Days = decisionsWithin30Days;
	}

	public boolean isIsShortTermVelocityHigh() {
		return isShortTermVelocityHigh;
	}

	public void setIsShortTermVelocityHigh(boolean isShortTermVelocityHigh) {
		this.isShortTermVelocityHigh = isShortTermVelocityHigh;
	}

	public boolean isIsShortTermVelocityMedium() {
		return isShortTermVelocityMedium;
	}

	public void setIsShortTermVelocityMedium(boolean isShortTermVelocityMedium) {
		this.isShortTermVelocityMedium = isShortTermVelocityMedium;
	}

	public boolean isIsLongTermVelocityHigh() {
		return isLongTermVelocityHigh;
	}

	public void setIsLongTermVelocityHigh(boolean isLongTermVelocityHigh) {
		this.isLongTermVelocityHigh = isLongTermVelocityHigh;
	}

	public boolean isIsLongTermVelocityMedium() {
		return this.isLongTermVelocityMedium;
	}

	public void setIsLongTermVelocityMedium(boolean isLongTermVelocityMedium) {
		this.isLongTermVelocityMedium = isLongTermVelocityMedium;
	}
}
