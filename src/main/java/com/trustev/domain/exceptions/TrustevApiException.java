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

	public static class MultipleMerchantSitesException extends TrustevApiException {

        public MultipleMerchantSitesException() {
            super(500, "More than one Merchant Site has been setup for this ApiClient, but no username was provided for the action. Try adding a merchant site username as a method parameter.");
        }

    }

	public static class MerchantSiteNotSetupException extends TrustevApiException {

		public MerchantSiteNotSetupException(String userName) {
			super(500, "A MerchantSite for the given username was not setup correctly. Be sure that ApiClient.SetUp was called for " + userName);
		}

	}
}
