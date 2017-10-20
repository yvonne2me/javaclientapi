package com.trustev.domain.entities;



import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTPResult extends BaseObject {

    @JsonProperty("Id")
    private String id;

    private Date timestamp;

    @JsonProperty("Status")
    private OTPStatus status;

    @JsonProperty("AuthURL")
    private String authURL;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Passcode")
    private String passcode;

    @JsonProperty("DeliveryType")
    private PhoneDeliveryType deliveryType;

    @JsonProperty("Language")
    private OTPLanguageEnum language;
    public OTPResult(String id){
       this.id=id;
       this.timestamp= new Date();
    }
    public OTPResult(){}
    /**
     * @return Current Timestamp.
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
     * @param timestamp Current Timestamp.
     */
    @JsonProperty("Timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OTPStatus getStatus() {
        return status;
    }

    public void setStatus(OTPStatus status) {
        this.status = status;
    }

    public String getAuthURL() {
        return authURL;
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PhoneDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(PhoneDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public OTPLanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(OTPLanguageEnum language) {
        this.language = language;
    }
}
