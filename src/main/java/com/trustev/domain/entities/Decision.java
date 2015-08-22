package com.trustev.domain.entities;

import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;

public class Decision {
	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("Comment")
	private String comment;
	
	@JsonProperty("Confidence")
	private int confidence;
	
	@JsonProperty("Result")
	private DecisionResult result;
	
	@JsonProperty("Score")
	private int score;
	
	@JsonProperty("SessionId")
	private UUID sessionId;
	
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
