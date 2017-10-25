package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonValue;

public enum FulfilmentGeoLocation {
    /// <summary>
    /// Undefined
    /// </summary>
    Undefined(0),
    /// <summary>
    /// National Location
    /// </summary>
    National(1),
    /// <summary>
    /// International Location
    /// </summary>
    International(2);
    private final int geoLocation;
    private FulfilmentGeoLocation(final int geoLocation) {
        this.geoLocation = geoLocation;
    }

    @JsonValue
    public int toInt()
    {
        return this.geoLocation;
    }
}
