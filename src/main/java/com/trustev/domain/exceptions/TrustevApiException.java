package com.trustev.integration;

public class TrustevApiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5303648162049104799L;

	public TrustevApiException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TrustevApiException(String message) {
		super(message);
	}
}
