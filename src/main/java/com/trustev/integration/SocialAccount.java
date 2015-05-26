package com.trustev.integration;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * Social media account for a customer
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SocialAccount extends ChildObject<SocialAccount> {
	
	private Integer socialId;
	private SocialNetworkType type;
	private String shortTermAccessToken;
	private String longTermAccessToken;
	private Date shortTermAccessTokenExpiry;
	private Date longTermAccessTokenExpiry;
	private String secret;
	private Date timestamp;
	
		
	/**
	 * 
	 * @return This is the Social Network Id, i.e. Your Facebook Account Id
	 */
	public Integer getSocialId() {
		return socialId;
	}
	
	/**
	 * 
	 * @param socialId This is the Social Network Id, i.e. Your Facebook Account Id
	 */
	@JsonProperty("SocialId")
	public void setSocialId(Integer socialId) {
		this.socialId = socialId;
	}
	
	/**
	 * 
	 * @return This is your Trustev Social Network Type
	 */
	public SocialNetworkType getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type This is your Trustev Social Network Type
	 */
	@JsonProperty("Type")
	public void setType(SocialNetworkType type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return This is the Short Term Access Token which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	public String getShortTermAccessToken() {
		return shortTermAccessToken;
	}
	
	/**
	 * 
	 * @param shortTermAccessToken This is the Short Term Access Token which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonProperty("ShortTermAccessToken")
	public void setShortTermAccessToken(String shortTermAccessToken) {
		this.shortTermAccessToken = shortTermAccessToken;
	}
	
	/**
	 * 
	 * @return This is the Long Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	public String getLongTermAccessToken() {
		return longTermAccessToken;
	}
	
	/**
	 * 
	 * @param longTermAccessToken This is the Long Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonProperty("LongTermAccessToken")
	public void setLongTermAccessToken(String longTermAccessToken) {
		this.longTermAccessToken = longTermAccessToken;
	}
	
	/**
	 * 
	 * @return This is the Short Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonIgnore()
	public Date getShortTermAccessTokenExpiry() {
		return shortTermAccessTokenExpiry;
	}
	
	@JsonProperty("ShortTermAccessTokenExpiry")
	String getShortTermAccessTokenExpiryString() {
		return FormatTimeStamp(shortTermAccessTokenExpiry);
	}
	
	/**
	 * 
	 * @param shortTermAccessTokenExpiry This is the Short Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonProperty("ShortTermAccessTokenExpiry")
	public void setShortTermAccessTokenExpiry(Date shortTermAccessTokenExpiry) {
		this.shortTermAccessTokenExpiry = shortTermAccessTokenExpiry;
	}
	
	/**
	 * 
	 * @return This is the Long Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonIgnore()
	public Date getLongTermAccessTokenExpiry() {
		return longTermAccessTokenExpiry;
	}
	
	@JsonProperty("LongTermAccessTokenExpiry")
	String getLongTermAccessTokenExpiryString() {
		return FormatTimeStamp(longTermAccessTokenExpiry);
	}
	
	/**
	 * 
	 * @param longTermAccessTokenExpiry This is the Long Term Token Expiry datetime which is available from the Social Access Token you received from the relevant Social Network's API
	 */
	@JsonProperty("LongTermAccessTokenExpiry")
	public void setLongTermAccessTokenExpiry(Date longTermAccessTokenExpiry) {
		this.longTermAccessTokenExpiry = longTermAccessTokenExpiry;
	}
	
	/**
	 * 
	 * @return This is the Secret which is attached to the Social Network's Developer's Account. This would have previously been needed to access the relevant Social Network's API
	 */
	public String getSecret() {
		return secret;
	}
	
	/**
	 * 
	 * @param secret This is the Secret which is attached to the Social Network's Developer's Account. This would have previously been needed to access the relevant Social Network's API
	 */
	@JsonProperty("Secret")
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	/**
	 * 
	 * @return Current Timestamp
	 */
	@JsonIgnore()
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonProperty("Timestamp")
	String getTimestampString() {
		return FormatTimeStamp(timestamp);
	}
	
	/**
	 * 
	 * @param timestamp Current Timestamp
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Adds the SocialAccount to the customer on a previously saved Case. 
	 * 
	 * @param caseId The case id of the case to save the social account against
	 * @throws TrustevApiException
	 */
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		if (this.getId() == null) {
			String path = "case/{caseId}/customer/socialaccount".replace("{caseId}",caseId);
			this.id = callApiMethodFor(path, "POST").id;
		}
		else {
			String path = "case/{caseId}/customer/socialaccount/{id}".replace("{caseId}",caseId).replace("{id}", this.id);
			this.id = callApiMethodFor(path, "PUT").id;
		}
	}
	
	/**
	 * Finds a single SocialAccount that is attached to a previously saved Case
	 * 
	 * @param caseId The case id of the case that the SocialAccount is attached to
	 * @param id The id of the SocialAccount to be retrieved
	 * @return A SocialAccount object
	 * @throws TrustevApiException
	 */
	public static SocialAccount Find(String caseId, String id) throws TrustevApiException {
		String path = "case/{caseId}/customer/socialaccount/{id}".replace("{caseId}",caseId).replace("{id}",id);
		return (SocialAccount) callApiMethodFor(path, null, SocialAccount.class, "GET");	
	}
	
	/**
	 * Finds all of the SocialAccounts attached to a previously saved Case
	 * 
	 * @param caseId The case id of the previously saved Trustev Case
	 * @return A collection of SocialAccount objects
	 * @throws TrustevApiException
	 */
	public static Collection<SocialAccount> FindAll(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/customer/socialaccount".replace("{caseId}",caseId);
		GenericType<Collection<SocialAccount>> type = new GenericType<Collection<SocialAccount>>(){};
		ClientResponse response = callApiMethod(path, null, "GET");
		return response.getEntity(type);
	}
}
