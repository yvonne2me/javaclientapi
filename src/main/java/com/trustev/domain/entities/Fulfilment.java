package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class Fulfilment {
    /**
     * @return The TimeToFulfilment of this Case
     */
    @JsonProperty("TimeToFulfilment")
    public TimeToFulfilment getTimeToFulfilment() {
        return this.timeToFulfilment;
    }

    /**
     * @param timeToFulfilment to set
     */
    @JsonProperty("TimeToFulfilment")
    public void setTimeToFulfilment(TimeToFulfilment timeToFulfilment) {
        this.timeToFulfilment = timeToFulfilment;
    }
    private TimeToFulfilment timeToFulfilment;

    /**
     * @return The FulfilmentMethod of this Case
     */
    @JsonProperty("Method")
    public FulfilmentMethod getFulfilmentMethod() {
        return this.fulfilmentMethod;
    }

    /**
     * @param fulfilmentMethod to set
     */
    @JsonProperty("Method")
    public void setFulfilmentMethod(FulfilmentMethod fulfilmentMethod) {
        this.fulfilmentMethod = fulfilmentMethod;
    }
    private FulfilmentMethod fulfilmentMethod;

    /**
     * @return The FulfilmentGeoLocation of this Case
     */
    @JsonProperty("GeoLocation")
    public FulfilmentGeoLocation getFulfilmentGeoLocation() {
        return this.fulfilmentGeoLocation;
    }

    /**
     * @param fulfilmentGeoLocation to set
     */
    @JsonProperty("GeoLocation")
    public void setFulfilmentGeoLocation(FulfilmentGeoLocation fulfilmentGeoLocation) {
        this.fulfilmentGeoLocation = fulfilmentGeoLocation;
    }
    private FulfilmentGeoLocation fulfilmentGeoLocation;

}
