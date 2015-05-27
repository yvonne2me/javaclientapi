package com.trustev.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

class GetTokenRequest {
	static String userName;
	static String passWord;
	static String sharedSecret;
	private String timeStamp;
	private String passwordHash;
	private String userNameHash;
	
	// logger
	private final static Logger LOGGER = Logger.getLogger(GetTokenRequest.class.getName()); 
	
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
			LOGGER.info(String.format("Trustev Config: username=%1$s password=%2$s sharedsecret=%3$s", userName, passWord, sharedSecret));
		} catch (IOException e) {
			throw new TrustevApiConfigurationException(e.getMessage(), e);
		}
	}
	
	private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
	
	protected static String CreateHash(String value, String timestamp, String sharedsecret) {
		try {
			LOGGER.finest(String.format("Hashing %1$s timestamp %2$s sharedsecret %3$s", value, timestamp, sharedsecret));
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String part1prehash = String.format("%1$s.%2$s", timestamp, value);
			LOGGER.finest(String.format("Part1 prehash %1$s", part1prehash));
			//String part1hash = new String(Hex.encode(md.digest(part1prehash.getBytes("UTF-8"))));
			md.update(part1prehash.getBytes());
			String part1hash = bytesToHex(md.digest());
			LOGGER.finest(String.format("Part1 hash %1$s", part1hash));
			String part2prehash = String.format("%1$s.%2$s", part1hash, sharedsecret);
			//String hashValue = new String(Hex.encode(md.digest(part2prehash.getBytes("UTF-8"))));
			md.update(part2prehash.getBytes());
			String hashValue = bytesToHex(md.digest());
			LOGGER.finest(String.format("Part2 hash %1$s", hashValue));
			return hashValue;
		} catch (NoSuchAlgorithmException e) {
			throw new TrustevApiConfigurationException(e.getMessage(), e);
		} /*catch (UnsupportedEncodingException f) {
			throw new TrustevApiConfigurationException(f.getMessage(), f);
		}*/
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
