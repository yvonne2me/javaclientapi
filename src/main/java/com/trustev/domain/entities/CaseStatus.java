package com.trustev.integration;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Status within a case
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CaseStatus extends ChildObject<CaseStatus> {
	
	private CaseStatusType status;
	private String comment;
	private Date timestamp;
	
		
	/**
	 * 
	 * @return The Status Type of the Trustev Case
	 */
	public CaseStatusType getStatus() {
		return status;
	}
	
	/**
	 * 
	 * @param status The Status Type of the Trustev Case
	 */
	@JsonProperty("Status")
	public void setStatus(CaseStatusType status) {
		this.status = status;
	}
	
	/**
	 * 
	 * @return Comment on the Status
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * 
	 * @param comment Comment on the Status
	 */
	@JsonProperty("Comment")
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 
	 * @return Current Timestamp
	 */
	@JsonIgnore()
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonProperty("Timestamp")
	String getTimestampString() {
		return FormatTimeStamp(timestamp);
	}
	
	/**
	 * 
	 * @param timestamp Current Timestamp
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Adds the CaseStatus to a previously saved Case. 
	 * 
	 * @param caseId The case id of the case to save the email against
	 * @throws TrustevApiException
	 */
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/status".replace("{caseId}",caseId);
		this.id = callApiMethodFor(path, "POST").id;	
	}

	/**
	 * Finds a CaseStatus for a previously saved Case
	 * 
	 * @param caseId The case id of the previously saved case
	 * @param id The id of the CaseStatus to be retrieved
	 * @return A CaseStatus object
	 * @throws TrustevApiException
	 */
	public static CaseStatus Find(String caseId, String id) throws TrustevApiException {
		String path = "case/{caseId}/status/{id}".replace("{caseId}",caseId).replace("{id}",id);
		return (CaseStatus) callApiMethodFor(path, null, CaseStatus.class, "GET");	
	}
}
