package com.trustev.domain.entities;

import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Decision {
	
	/*
	* This is the Id generated by the Trustev Platform
	*/
	@JsonProperty("Id")
	private String id;
	
	/*
	* The Trustev Comment that Trustev has added to this Decision.
	*/
	@JsonProperty("Comment")
	private String comment;
	
	/*
	* A Trustev Confidence Score represents our confidence on a Trustev Decision based on the level of details supplied within a Trustev Case. 
	* The Trustev Confidence Score is out of 100. 100 being all information was supplied and validated and 0 indicating that no information was contained within the Trustev Case.
	*/
	@JsonProperty("Confidence")
	private int confidence;
	
	/*
	* The Result of the Trustev Decision.
	*/
	@JsonProperty("Result")
	private DecisionResult result;
	
	/*
	* This is the score that Trustev assigned the Case. This can be ignored.
	*/
	@JsonProperty("Score")
	private int score;
	
	/*
	* The SessionId of the Case that this Decision was based on
	*/
	@JsonProperty("SessionId")
	private UUID sessionId;
	
	/*
	* The Date Time that this Decision was generated at
	*/
	@JsonProperty("Timestamp")
	private Date timeStamp;
	
	@JsonProperty("Type")
	int type;
	
	@JsonProperty("Version")
	String version;
	
	public String getId() {
		return id;
	}
	public String getComment() {
		return comment;
	}
	public int getConfidence() {
		return confidence;
	}
	public DecisionResult getResult() {
		return result;
	}
	public int getScore() {
		return score;
	}
	public UUID getSessionId() {
		return sessionId;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public int getType() {
		return type;
	}
	public String getVersion() {
		return version;
	}
}
