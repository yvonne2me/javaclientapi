package com.trustev.domain.exceptions;

import com.trustev.web.ApiClient;

public class TrustevInvalidBaseUrlException extends Exception {
    public TrustevInvalidBaseUrlException() {
        super("Invalid base url. Check that you have provided a valid api endpoint for your request");
    }
}

