package com.trustev.domain.entities;

//import org.apache.commons.lang3.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Payments includes forwarding the Payment Type (Credit/Debit Card, PayPal…), and the BIN/IIN Number of the relevant card should it be available.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Payment extends BaseObject {
	private PaymentType paymentType;
	private String binNumber;
	
		
	/**
	 * @return The type of Payment method used
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	/**
	 * @param paymentType The type of Payment method used
	 */
	@JsonProperty("PaymentType")
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	
	/**
	 * @return The BIN Number - the first 6 digits of a Debit/Credit Card Number.
	 */
	public String getBinNumber() {
		return this.binNumber;
	}
	
	/**
	 * @param bINNumber The BIN Number - the first 6 digits of a Debit/Credit Card Number.
	 */
	@JsonProperty("BINNumber")
	public void setBinNumber(String bINNumber) {
		this.binNumber = bINNumber;
	}
}
