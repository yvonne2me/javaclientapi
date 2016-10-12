package com.trustev.domain.entities;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.trustev.domain.exceptions.TrustevApiException;

/**
 * The Case Object is the what Trustev bases its Decision on. It is a container for all the information that can be provided.
 * The more information that you provide us with, the more accurate our Decision, so please populate as much as possible.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Case extends BaseObject{
	
	
	/**
	 * @param sessionId SessionId is required when adding a Trustev Case. SessionId is available through Trustev.js as a publicly accessible variable - TrustevV2.SessionId
	 * @param caseNumber The CaseNumber of the case being created
	 * @throws TrustevApiException If the sessionId passed in is null
	 */
	@JsonCreator
	public Case(@JsonProperty("SessionId")UUID sessionId, @JsonProperty("CaseNumber") String caseNumber) throws TrustevApiException {
		if (sessionId == null) {
			throw new TrustevApiException(400, "Session ID cannot be null");
		}
		this.sessionId = sessionId;
		this.caseNumber = caseNumber;
	}
	
	/**
	 * 
	 * @return SessionId is required when adding a Trustev Case. SessionId is available through Trustev.js as a publicly accessible variable - TrustevV2.SessionId
	 */
	public UUID getSessionId() {
		return sessionId;
	}
	
	/**
	* @return The CaseNumber is chosen by the Merchant to uniquely identify the Trustev Case. It can be an alphanumeric string of your liking, but it must be unique.
	* We would always recommend that Merchants set the Case Number as the internal Order Number so it is easy to reference in later reporting. 
    * Please see our Testing Guide to find out how to use the CaseNumber to get expected Trustev Decisions during Integration.
	*/
	public String getCaseNumber() {
		return caseNumber;
	}
	
	/**
	 * @return Transaction Object - includes details such as Transaction Amount, Currency, Items and Transaction delivery/billing address.
	 */
	public Transaction getTransaction() {
		return transaction;
	}
	
	/**
	 * @param transaction Transaction Object - includes details such as Transaction Amount, Currency, Items and Transaction delivery/billing address.
	 */
	@JsonProperty("Transaction")
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	/**
	 * @return Customer Object - includes details like First/Last name of Customer, address details, phone numbers, email addresses. 
     * Social details may also be included here where available. 
     * Please see Customer object for further parameter information.
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * 
	 * @param customer Customer Object - includes details like First/Last name of Customer, address details, phone numbers, email addresses. 
     * Social details may also be included here where available. 
     * Please see Customer object for further parameter information.
	 */
	@JsonProperty("Customer")
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * @return A Status includes the Order Status and a Comment section. Trustev require that a Status is attached to a Trustev Case so that we can learn from the decision that you make on a Trustev Case. 
	 */
	public Collection<CaseStatus> getStatuses() {
		return statuses;
	}
	
	/**
	 * @param statuses A Status includes the Order Status and a Comment section. Trustev require that a Status is attached to a Trustev Case so that we can learn from the decision that you make on a Trustev Case. 
	 */
	@JsonProperty("Statuses")
	public void setStatuses(Collection<CaseStatus> statuses) {
		this.statuses = statuses;
	}
	
	/**
	 * @return Payments includes forwarding the Payment Type (Credit/Debit Card, PayPal), and the BIN/IIN Number of the relevant card should it be available.
	 */
	public Collection<Payment> getPayments() {
		return payments;
	}
	
	/**
	 * @param payments Payments includes forwarding the Payment Type (Credit/Debit Card, PayPal), and the BIN/IIN Number of the relevant card should it be available.
	 */
	@JsonProperty("Payments")
	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}
	
	/**
	 * @return Current Timestamp.
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
	 * @param timestamp Current Timestamp.
	 */
	@JsonProperty("Timestamp")
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	/**
	 * @return The CaseType of this Case
	 */
	@JsonProperty("CaseType")
	public CaseType getCaseType() {
		return this.caseType;
	}
	
	/**
	 * @param caseType to set
	 */
	@JsonProperty("CaseType")
	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}
	
	
	private UUID sessionId;
	
	private String caseNumber;
	
	private Transaction transaction;
	
	private Customer customer;
	
	private Collection<CaseStatus> statuses;
	
	private Collection<Payment> payments;
	
	private Date timestamp;
	
	private CaseType caseType;
}
