package com.trustev.domain.entities;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;

public enum PhoneDeliveryType {
    Sms(0),
    Voice(1);
    private final int deliverytype;
    private PhoneDeliveryType(final int deliverytype) {
        this.deliverytype = deliverytype;
    }

    @JsonValue
    public int toInt()
    {
        return this.deliverytype;
    }
}
