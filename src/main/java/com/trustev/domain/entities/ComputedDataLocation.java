package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedDataLocation
{
	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("IsIPCountryDomestic")
	private boolean isIPCountryDomestic;

	@JsonProperty("BIN")
	private ComputedDataBIN bin;

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public boolean isIPCountryDomestic() {
		return isIPCountryDomestic;
	}

	public void setIPCountryDomestic(boolean isIPCountryDomestic) {
		this.isIPCountryDomestic = isIPCountryDomestic;
	}

	public ComputedDataBIN getBin() {
		return bin;
	}

	public void setBin(ComputedDataBIN bin) {
		this.bin = bin;
	}
}