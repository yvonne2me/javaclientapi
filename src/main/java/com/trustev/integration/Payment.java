package com.trustev.integration;

import java.util.Collection;

//import org.apache.commons.lang3.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * A Payment within a Trustev Case
 * 
 * @author jack.mcauliffe
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Payment extends ChildObject<Payment> {
	private PaymentType paymentType;
	private String binNumber;
	
		
	/**
	 * 
	 * @return The type of Payment method used
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	/**
	 * 
	 * @param paymentType The type of Payment method used
	 */
	@JsonProperty("PaymentType")
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	
	/**
	 * 
	 * @return The BIN Number - the first 6 digits of a Debit/Credit Card Number.
	 */
	public String getBinNumber() {
		return this.binNumber;
	}
	
	/**
	 * 
	 * @param bINNumber The BIN Number - the first 6 digits of a Debit/Credit Card Number.
	 */
	@JsonProperty("BINNumber")
	public void setBinNumber(String bINNumber) {
		this.binNumber = bINNumber;
	}
	
	@Override
	public void SaveForCase(String caseId) throws TrustevApiException {
		if (this.getId() == null) {
			String path = "case/{caseId}/payment".replace("{caseId}",caseId);
			this.id = callApiMethodFor(path, "POST").id;
		}
		else {
			String path = "case/{caseId}/payment/{id}".replace("{caseId}",caseId).replace("{id}",this.getId());
			callApiMethodFor(path, "PUT");
		}
	}

	/**
	 * Finds a single Payment that is attached to a previously saved Case
	 * 
	 * @param caseId The case id of the case that the Payment is attached to
	 * @param id The Payment id of the Payment to be retrieved
	 * @return A Payment object
	 * @throws TrustevApiException
	 */
	public static Payment Find(String caseId, String id) throws TrustevApiException {
		String path = "case/{caseId}/payment/{id}".replace("{caseId}",caseId).replace("{id}",id);
		return (Payment) callApiMethodFor(path, null, Payment.class, "GET");	
	}
	
	/**
	 * Finds all of the Payments associated with a previously saved case
	 * 
	 * @param caseId The case id of the case that the Payment is attached to
	 * @return A collection of all the Payments on the Case
	 * @throws TrustevApiException
	 */
	public static Collection<Payment> FindAll(String caseId) throws TrustevApiException {
		String path = "case/{caseId}/payment".replace("{caseId}",caseId);
		GenericType<Collection<Payment>> type = new GenericType<Collection<Payment>>(){};
		ClientResponse response = callApiMethod(path, null, "GET");
		return response.getEntity(type);
	}
	
	/*@Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Payment))
            return false;
        if (obj == this)
            return true;
        Payment rhs = (Payment) obj;
		return EqualsBuilder.reflectionEquals(this, rhs, false);
    }*/
}
