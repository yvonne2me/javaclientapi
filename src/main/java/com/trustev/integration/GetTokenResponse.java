package com.trustev.integration;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

class GetTokenResponse {
	public String getApiToken() {
		return this.apiToken;
	}
	
	@JsonProperty("APIToken")
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	
	public Date getExpireAt() {
		return expireAt;
	}
	
	@JsonProperty("ExpireAt")
	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}
	public String getCredentialType() {
		return credentialType;
	}
	
	@JsonProperty("CredentialType")
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	
	private String apiToken;
	private Date expireAt;
	private String credentialType;
}
