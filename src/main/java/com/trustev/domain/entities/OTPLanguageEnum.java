package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonValue;

public enum OTPLanguageEnum {
    /// <summary>
    /// English
    /// </summary>
    EN(0),

    /// <summary>
    /// Spanish
    /// </summary>
    ES(1);
    private final int language;
    private OTPLanguageEnum(final int language) {
        this.language = language;
    }

    @JsonValue
    public int toInt()
    {
        return this.language;
    }
}
