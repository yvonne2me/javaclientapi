package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DigitalAuthenticationResult extends BaseObject {
    public DigitalAuthenticationResult(){
        timestamp= new Date();
    }
    @JsonProperty("Id")
    private String id;

    private Date timestamp;

    @JsonProperty("OTP")
    private OTPResult otp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


    public OTPResult getOtp() {
        return otp;
    }

    public void setOtp(OTPResult otp) {
        this.otp = otp;
    }
}
