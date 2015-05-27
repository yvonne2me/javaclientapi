package com.trustev.integration;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Case object is the top level object in the trustev API.  Decisions are made by instantiating a Case object, populating the values and calling the MakeDecision method
 * 
 * 
 * <pre>
 * <code>
 * String caseNumber = "MyCase1234" // this case number is chosen by merchant and must be unique
 * UUID sessionId = UUID.fromString("7e740ea2-7e8b-43d8-a3b1-4073ca336e28");  // session id returned from js file
 * Case myCase = new Case(sessionId);
 * myCase.setCaseNumber(caseNumber);
 * myCase.setTimestamp(new Date());
 * Customer customer = new Customer();
 * customer.setFirstName("Gene");
 * customer.setLastName("Geniune");
 * myCase.setCustomer(customer);
 * Decision decision = myCase.MakeDecision();
 * if (decision.getResult() == DecisionResult.Pass) {
 *   // process order
 * }
 * else {
 *   // order is fraud
 * }
 * </code>
 * </pre>
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Case extends BaseObject<Case> {
	
	
	/**
	 * 
	 * @param sessionId SessionId is required when adding a Trustev Case. SessionId is available through Trustev.js as a publicly accessible variable - TrustevV2.SessionId
	 * @throws TrustevApiException If the sessionId passed in is null
	 */
	@JsonCreator
	public Case(@JsonProperty("SessionId")UUID sessionId, @JsonProperty("CaseNumber") String caseNumber) throws TrustevApiException {
		if (sessionId == null) {
			throw new TrustevApiException("Session ID cannot be null");
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
	 * @return The CaseNumber is chosen by the Merchant to uniquely identify the Trustev Case. It can be an alphanumeric string of your liking. Please see our Testing Guide to find out how to use the CaseNumber to get expected Trustev Decisions during Integration.
	 */
	public String getCaseNumber() {
		return caseNumber;
	}
	
	/**
	 * @return The transaction object
	 */
	public Transaction getTransaction() {
		return transaction;
	}
	
	/**
	 * @param transaction The CaseNumber is chosen by the Merchant to uniquely identify the Trustev Case. It can be an alphanumeric string of your liking. Please see our Testing Guide to find out how to use the CaseNumber to get expected Trustev Decisions during Integration.
	 */
	@JsonProperty("Transaction")
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	/**
	 * @return Customer Object - includes details like First/Last name of Customer, address details, phone numbers, email addresses. Social details may also be included here where available. Please see Customer object for further parameter information.
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * 
	 * @param customer Customer Object - includes details like First/Last name of Customer, address details, phone numbers, email addresses. Social details may also be included here where available. Please see Customer object for further parameter information.
	 */
	@JsonProperty("Customer")
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * @return A Status includes the Order Status and a Comment section. Trustev require that a Status is attached to a Trustev Case so that we can learn from the decision that you make on a Trustev Case. Please see Why add Statuses? for more information.
	 */
	public Collection<CaseStatus> getStatuses() {
		return statuses;
	}
	
	/**
	 * @param statuses A Status includes the Order Status and a Comment section. Trustev require that a Status is attached to a Trustev Case so that we can learn from the decision that you make on a Trustev Case. Please see Why add Statuses? for more information.
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
	 * Makes an overall decision on the case.  If the case has not yet been saved then it is implicitly saved.
	 * 
	 * @return A Decision object representing the overall decision
	 * @throws TrustevApiException
	 */
	@JsonIgnore()
	public Decision MakeDecision() throws TrustevApiException {
		// if it hasn't been saved then save before getting decision
		if (this.getId() == null) {
			Save();
		}
		String path = String.format("Decision/%1$s", this.getId());
		return (Decision) callApiMethodFor(path, null, Decision.class, "GET");	
	}
	
	private UUID sessionId;
	
	private String caseNumber;
	
	private Transaction transaction;
	
	private Customer customer;
	
	private Collection<CaseStatus> statuses;
	
	private Collection<Payment> payments;
	
	private Date timestamp;

	/**
	 * Saves the case.  If the case has not previously been saved then it creates it as a new case, if the case has been saved
	 * and therefore has an id then the previous case is updated with new values
	 * @throws TrustevApiException
	 */
	public void Save() throws TrustevApiException {
		if (this.getId() == null) {
			// id is null so its an add
			this.id = callApiMethodFor("Case", "POST").id;	
		}
		else {
			// id is not null so its a save
			String path = "Case/{id}".replace("{id}", this.getId());
			callApiMethodFor(path, "PUT");	
		}
		
	}

	/**
	 * Retrieves a previously saved case.
	 * 
	 * @param id The case id of the case to retrieve
	 * @return A Case object representing the previously saved case
	 * @throws TrustevApiException
	 */
	public static Case Find(String id) throws TrustevApiException {
		String path = "Case/{id}".replace("{id}", id);
		return (Case) callApiMethodFor(path, null, Case.class, "GET");	
	}
}
