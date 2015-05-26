package com.trustev.integration;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
class ErrorResponse {
	public String Message;
}
