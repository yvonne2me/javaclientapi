package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonValue;

public enum OTPStatus {
    /// <summary>
    /// Case is Eligible and OTP Offered
    /// </summary>
    Offered(0),

    /// <summary>
    /// OTP Offered And Passed
    /// </summary>
    Pass(1),

    /// <summary>
    /// OTP Offered And Failed
    /// </summary>
    Fail(2),

    /// <summary>
    /// Case is Ineligible and OTP was not Offered
    /// </summary>
    Ineligible(3),

    /// <summary>
    /// OTP Offered and code sent
    /// </summary>
    InProgress(4),

    /// <summary>
    /// Hit max retries
    /// </summary>
    MaxRetryHit(5),

    /// <summary>
    /// Final state of Abandoned
    /// </summary>
    Abandoned(6) ,

    /// <summary>
    /// OTP is not Configured
    /// </summary>
    NotConfigured(7);
    private final int status;
    private OTPStatus(final int status) {
        this.status = status;
    }

    @JsonValue
    public int toInt()
    {
        return this.status;
    }


}
