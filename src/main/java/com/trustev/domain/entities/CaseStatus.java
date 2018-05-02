package com.trustev.domain.entities;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The CaseStatus is used to let Trustev know the current status of any Case. When the status of a Case changes, please update the status of the Case.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseStatus extends BaseObject {
	
	private CaseStatusType status;
	private String comment;
	private Date timestamp;
	private String id;
	private boolean isUpdated;

	/**
	 * @return Id of the Status
	 */
	public CaseStatusType getStatus() {
		return status;
	}

    /**
     * @param status The Status Type of the Trustev Case
     */
    @JsonProperty("Status")
    public void setStatus(CaseStatusType status) {
        this.status = status;
    }

    /**
	 * @param id Id of the Status
	 */
	@JsonProperty("Id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Id of the Status
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return Comment on the Status
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @param comment Comment on the Status
	 */
	@JsonProperty("Comment")
	public void setComment(String comment) {
		this.comment = comment;
	}

    /**
     * @return Update status on the Status
     */
    public boolean getIsUpdated() {
        return isUpdated;
    }

    /**
     * @param isUpdated Update status on the Status
     */
    @JsonProperty("IsUpdated")
    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

	/**
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
	 * @param timestamp Current Timestamp
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
