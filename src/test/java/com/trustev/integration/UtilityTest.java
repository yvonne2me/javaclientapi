package com.trustev.integration;

import junit.framework.Assert;
import junit.framework.TestCase;

public class UtilityTest extends TestCase {
	
	
	public void testConfig() {
		try {
			new GetTokenRequest();
			Assert.assertNotNull(GetTokenRequest.userName);
			Assert.assertNotNull(GetTokenRequest.passWord);
			Assert.assertNotNull(GetTokenRequest.sharedSecret);
		} catch (TrustevApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testHashing() throws TrustevApiException {
		GetTokenRequest request = new GetTokenRequest();
		Assert.assertNotNull(request.getUserName());
		Assert.assertNotNull(request.getTimeStamp());
		Assert.assertNotNull(request.getPasswordHash());
		Assert.assertNotNull(request.getUserNameHash());
		System.out.println("username=" + request.getUserName());
		System.out.println("timestamp=" + request.getTimeStamp());
		System.out.println("passwordhash=" + request.getPasswordHash());
		System.out.println("usernamehash=" + request.getUserNameHash());
	}
}
