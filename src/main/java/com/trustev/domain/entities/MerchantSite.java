package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantSite extends BaseObject {

    private String userName;
    private String password;
    private String secret;
    private String baseUrlString;
    private BaseUrl baseUrl;
    private String apiToken;
    private Date expiryDate;

    public MerchantSite() {
    }

    public MerchantSite(String userName, String password, String secret, String baseUrlString) {
        this.userName = userName;
        this.password = password;
        this.secret = secret;
        this.baseUrlString = baseUrlString;
    }

    public MerchantSite(String userName, String password, String secret, BaseUrl baseUrl) {
        this.userName = userName;
        this.password = password;
        this.secret = secret;
        this.baseUrl = baseUrl;
    }

    /**
     *
     * @return The Merchante Site's Username
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    @JsonProperty("Username")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("Password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("Secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBaseUrlString() {
        return baseUrlString;
    }

    @JsonProperty("BaseUrlString")
    public void setBaseUrlString(String baseUrlString) {
        this.baseUrlString = baseUrlString;
    }

    public BaseUrl getBaseUrl() {
        return baseUrl;
    }

    @JsonProperty("BaseUrl")
    public void setBaseUrl(BaseUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecret() {
        return secret;
    }

    public String getApiToken() {
        return apiToken;
    }

    @JsonProperty("ApiToken")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    @JsonProperty("ExpiryDate")
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
