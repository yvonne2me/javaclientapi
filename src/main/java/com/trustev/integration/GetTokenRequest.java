package com.trustev.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import org.bouncycastle.util.encoders.Hex;

class GetTokenRequest {
	static String userName;
	static String passWord;
	static String sharedSecret;
	private String timeStamp;
	private String passwordHash;
	private String userNameHash;
	
	static {
		try {
			Properties prop = new Properties();
			String propFileName = "trustev.properties";
			InputStream inputStream = GetTokenRequest.class.getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}	
			userName = prop.getProperty("username");
			passWord = prop.getProperty("password");
			sharedSecret = prop.getProperty("sharedsecret");
			if (userName == null) {
				throw new TrustevApiConfigurationException("property file '" + propFileName + "' did not contain value for username");
			}
			if (passWord == null) {
				throw new TrustevApiConfigurationException("property file '" + propFileName + "' did not contain value for password");
			}
			if (sharedSecret == null) {
				throw new TrustevApiConfigurationException("property file '" + propFileName + "' did not contain value for sharedSecret");
			}
		} catch (IOException e) {
			throw new TrustevApiConfigurationException(e.getMessage(), e);
		}
	}
	
	protected static String CreateHash(String value, String timestamp, String sharedsecret) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String part1prehash = String.format("%1$s.%2$s", timestamp, value);
			String part1hash = new String(Hex.encode(digest.digest(part1prehash.getBytes("UTF-8"))));
			String part2prehash = String.format("%1$s.%2$s", part1hash, sharedsecret);
			return new String(Hex.encode(digest.digest(part2prehash.getBytes("UTF-8"))));
		} catch (NoSuchAlgorithmException e) {
			throw new TrustevApiConfigurationException(e.getMessage(), e);
		} catch (UnsupportedEncodingException f) {
			throw new TrustevApiConfigurationException(f.getMessage(), f);
		}
	}
	
	public GetTokenRequest() throws TrustevApiException {
		this.timeStamp = BaseObject.FormatTimeStamp(new Date());
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPasswordHash() {
		if (passwordHash == null) {
			passwordHash = CreateHash(passWord, this.timeStamp, sharedSecret);
		}
		return passwordHash;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public String getUserNameHash() {
		if (userNameHash == null) {
			userNameHash = CreateHash(userName, this.timeStamp, sharedSecret);
		}
		return userNameHash;
	}
}
