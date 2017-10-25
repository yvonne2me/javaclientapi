package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonValue;

public enum FulfilmentMethod {
    /// <summary>
    /// Undefined
    /// </summary>
    Undefined(0),
    /// <summary>
    /// Virtual
    /// </summary>
    Virtual(1),
    /// <summary>
    /// In Person
    /// </summary>
    InPerson(2),
    /// <summary>
    /// Post
    /// </summary>
    Post(3),
    /// <summary>
    /// Courier
    /// </summary>
    Courier(4);
    private final int method;
    private FulfilmentMethod(final int method) {
        this.method = method;
    }

    @JsonValue
    public int toInt()
    {
        return this.method;
    }
}
