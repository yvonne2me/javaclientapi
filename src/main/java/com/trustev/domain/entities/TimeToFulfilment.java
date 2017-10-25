package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonValue;

public enum TimeToFulfilment {
    /// <summary>
    /// Undefined
    /// </summary>
    Undefined(0),
    /// <summary>
    /// Immediate
    /// </summary>
    Immediate(1),
    /// <summary>
    /// Same Day
    /// </summary>
    SameDay(2),
    /// <summary>
    ///  Next Day
    /// </summary>
    NextDay(3),
    /// <summary>
    /// Up To 3 Days,
    /// </summary>
    UpTo3Days(4),
    /// <summary>
    /// Up To 5 Days
    /// </summary>
    UpTo5Days(5);

    private final int timeTo;
    private TimeToFulfilment(final int timeTo) {
        this.timeTo = timeTo;
    }

    @JsonValue
    public int toInt()
    {
        return this.timeTo;
    }

    }
