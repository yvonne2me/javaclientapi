package com.trustev.domain.exceptions;

public class TrustevApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2200672193613015236L;
	
	public int responseCode;

	public TrustevApiException(int httpCode, String message) {
		super(message);
		this.responseCode = httpCode;
	}
}
