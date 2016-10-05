package com.trustev.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.trustev.domain.entities.*;
import com.trustev.domain.exceptions.TrustevApiException;

	/*
	* The ApiClient has all the methods required to communicate with the Trustev Platform.
	*/
public class ApiClient {

	private static String userName;
	private static String password;
	private static String secret;
	private static String baseUrl;
	private static String apiToken;
	private static Date expiryDate;
	
	static{
		userName = "";
		password = "";
		secret = "";
		baseUrl = "https://app.trustev.com/api/v2.0";
	}
	
	/**
	 * Initialize the Trustev class by passing in your UserName, Secret and Password.
	 * If you do not have this information, please contact our Integration Team - integrate@trustev.com
	 * @param userName Your Trustev Username
	 * @param password Your Trustev Password
	 * @param secret Your Trustev Secret
	 * @throws TrustevApiException
	 */
	public static void SetUp(String userName, String password, String secret)
	{
		ApiClient.userName = userName;
		ApiClient.password = password;
		ApiClient.secret = secret;
	}
	
	/**
	 * Post your Case to the TrustevClient Api
	 * @param kase Your Case which you want to POST
	 * @return The Case, along with the Case Id that the TrustevClient API have assigned it.
	 * @throws TrustevApiException
	 */
	public static Case postCase(Case kase) throws TrustevApiException{
		String url = "/Case";
		Case response = (Case)PerformHttpCall(url, HttpMethod.POST, Case.class, kase, true);
		return response;
	}

	/**
	 * Update your Case with the case Id, provided with the new Case object
	 * @param kase Your Case which you want to PUT and update the existing Case with.
	 * @param caseId The Case Id of the Case you want to update. The TrustevClient API will have assigned this Id and returned it in the response Case from the PostCase Method
	 * @return 
	 * @throws TrustevApiException
	 */
	public static Case updateCase(Case kase, String caseId) throws TrustevApiException{
		String url = "/Case/" + caseId;
		Case response = (Case)PerformHttpCall(url, HttpMethod.PUT, Case.class, kase, true);
		return response;
	}
	
	/**
	 * Get the Case with the Id caseId
	 * @param caseId The Case Id of the Case you want to get. The TrustevClient API will have assigned this Id and returned it in the response Case from the PostCase Method
	 * @return
	 * @throws TrustevApiException
	 */
	public static Case getCase(String caseId) throws TrustevApiException{
		String url = "/Case/" + caseId;
		Case response = (Case)PerformHttpCall(url, HttpMethod.GET, Case.class, null, true);
		return response;
	}
	
	
	/**
	 * Get a Decision on a Case with Id caseId.
	 * @param caseId The Id of a Case which you have already posted to the TrustevClient API. 
	 * @return
	 * @throws TrustevApiException 
	 */
	public static Decision getDecision(String caseId) throws TrustevApiException
	{
		String url = "/Decision/" + caseId;
		Decision response = (Decision)PerformHttpCall(url, HttpMethod.GET, Decision.class, null, true);
		return response;
	}
	
	/**
	 * Post your Customer to an existing Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param customer Your Customer which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Customer postCustomer(String caseId, Customer customer) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer".replace("{id}", caseId);
		Customer response = (Customer)PerformHttpCall(url, HttpMethod.POST, Customer.class, customer, true);
		return response;
	}
	
	/**
	 * Update the Customer on a Case which already contains a Customer
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param customer Your Customer which you want to Put and update the existing Customer with
	 * @return
	 * @throws TrustevApiException
	 */
	public static Customer updateCustomer(String caseId, Customer customer) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer".replace("{id}", caseId);
		Customer response = (Customer)PerformHttpCall(url, HttpMethod.PUT, Customer.class, customer, true);
		return response;
	}
	
	/**
	 * Get the Customer attached to the Case
	 * @param caseId The case Id of the the Case with the Customer you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Customer getCustomer(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer".replace("{id}", caseId);
		Customer response = (Customer)PerformHttpCall(url, HttpMethod.GET, Customer.class, null, true);
		return response;
	}
	
	/**
	 * Post your Transaction to an existing Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param transaction Your Transaction which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Transaction postTransaction(String caseId, Transaction transaction) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction".replace("{id}", caseId);
		Transaction response = (Transaction)PerformHttpCall(url, HttpMethod.POST, Transaction.class, transaction, true);
		return response;
	}
	
	/**
	 * Update the Transaction on a Case which already contains a Transaction
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param transaction Your Transaction which you want to Put and update the existing Transaction with
	 * @return
	 * @throws TrustevApiException
	 */
	public static Transaction updateTransaction(String caseId, Transaction transaction) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction".replace("{id}", caseId);
		Transaction response = (Transaction)PerformHttpCall(url, HttpMethod.PUT, Transaction.class, transaction, true);
		return response;
	}
	
	/**
	 * Get the Transaction attached to the Case
	 * @param caseId The Case Id of the the Case with the Transaction you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Transaction getTransaction(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction".replace("{id}", caseId);
		Transaction response = (Transaction)PerformHttpCall(url, HttpMethod.GET, Transaction.class, null, true);
		return response;
	}
	
	/**
	 * Post your CaseStatus to an existing Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param caseStatus Your CaseStatus which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static CaseStatus postCaseStatus(String caseId, CaseStatus caseStatus) throws TrustevApiException
	{
		String url = "/Case/{id}/Status".replace("{id}", caseId);
		CaseStatus response = (CaseStatus)PerformHttpCall(url, HttpMethod.POST, CaseStatus.class, caseStatus, true);
		return response;
	}
	
	/**
	 * Get a specific status from a Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param caseStatusId The Id of the CaseStatus you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static CaseStatus getCaseStatus(String caseId, String caseStatusId) throws TrustevApiException
	{
		String url = "/Case/{id}/Status/{id2}".replace("{id}", caseId).replace("{id2}", caseStatusId);
		CaseStatus response = (CaseStatus)PerformHttpCall(url, HttpMethod.GET, CaseStatus.class, null, true);
		return response;
	}
	
	/**
	 * Get all the Statuses from a Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<CaseStatus> getCaseStatuses(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Status".replace("{id}", caseId);
		GenericType<Collection<CaseStatus>> type = new GenericType<Collection<CaseStatus>>(){};
		Collection<CaseStatus> response = (Collection<CaseStatus>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your CustomerAddress to an existing Customer on an existing Case
	 * @param caseId The Case Id of a Case with the Customer  which you have already posted
	 * @param customerAddress Your CustomerAddress which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address postCustomerAddress(String caseId, Address customerAddress) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Address".replace("{id}", caseId);
		Address response = (Address)PerformHttpCall(url, HttpMethod.POST, Address.class, customerAddress, true);
		return response;
	}
	
	/**
	 * Update a specific CustomerAddress on a Case which already contains a CustomerAddress
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param customerAddress The CustomerAddress you want to update the existing CustomerAddress to
	 * @param customerAddressId The id of the CustomerAddress you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address updateCustomerAddress(String caseId, Address customerAddress, String customerAddressId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Address/{id2}".replace("{id}", caseId).replace("{id2}", customerAddressId);;
		Address response = (Address)PerformHttpCall(url, HttpMethod.PUT, Address.class, customerAddress, true);
		return response;
	}
	
	/**
	 * Get a specific CustomerAddress from a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @param customerAddressId The Id of the CustomerAddress you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address getCustomerAddress(String caseId, String customerAddressId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Address/{id2}".replace("{id}", caseId).replace("{id2}", customerAddressId);;
		Address response = (Address)PerformHttpCall(url, HttpMethod.GET, Address.class, null, true);
		return response;
	}
	
	/**
	 * Get all the Addresses from a Customer on a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<Address> getCustomerAddresses(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Address".replace("{id}", caseId);
		GenericType<Collection<Address>> type = new GenericType<Collection<Address>>(){};
		Collection<Address> response = (Collection<Address>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your Email to an existing Customer on an existing Case
	 * @param caseId The Case Id of a Case with the Customer  which you have already posted
	 * @param email Your Email which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Email postEmail(String caseId, Email email) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Email".replace("{id}", caseId);
		Email response = (Email)PerformHttpCall(url, HttpMethod.POST, Email.class, email, true);
		return response;
	}
	
	/**
	 * Update a specific Email on a Case which already contains a Email
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param email The Email you want to update the existing Email to
	 * @param emailId The id of the Email you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static Email updateEmail(String caseId, Email email, String emailId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Email/{id2}".replace("{id}", caseId).replace("{id2}", emailId);;
		Email response = (Email)PerformHttpCall(url, HttpMethod.PUT, Email.class, email, true);
		return response;
	}
	
	/**
	 * Get a specific Email from a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @param emailId The Id of the Email you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Email getEmail(String caseId, String emailId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Email/{id2}".replace("{id}", caseId).replace("{id2}", emailId);;
		Email response = (Email)PerformHttpCall(url, HttpMethod.GET, Email.class, null, true);
		return response;
	}
	
	/**
	 * Get all the Emails from a Case
	 * @param caseId The Case Id of a Case with the Customer  which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<Email> getEmails(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/Email".replace("{id}", caseId);
		GenericType<Collection<Email>> type = new GenericType<Collection<Email>>(){};
		Collection<Email> response = (Collection<Email>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your Payment to an existing Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param payment Your Payment which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Payment postPayment(String caseId, Payment payment) throws TrustevApiException
	{
		String url = "/Case/{id}/Payment".replace("{id}", caseId);
		Payment response = (Payment)PerformHttpCall(url, HttpMethod.POST, Payment.class, payment, true);
		return response;
	}
	
	/**
	 * Update a specific Payment on a Case which already contains a Payment
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param payment The Payment you want to update the existing Payment to
	 * @param paymentId The id of the Payment you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static Payment updatePayment(String caseId, Payment payment, String paymentId) throws TrustevApiException
	{
		String url = "/Case/{id}/Payment/{id2}".replace("{id}", caseId).replace("{id2}", paymentId);;
		Payment response = (Payment)PerformHttpCall(url, HttpMethod.PUT, Payment.class, payment, true);
		return response;
	}
	
	/**
	 * Get a specific Payment from a Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param paymentId The Id of the Payment you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Payment getPayment(String caseId, String paymentId) throws TrustevApiException
	{
		String url = "/Case/{id}/Payment/{id2}".replace("{id}", caseId).replace("{id2}", paymentId);;
		Payment response = (Payment)PerformHttpCall(url, HttpMethod.GET, Payment.class, null, true);
		return response;
	}
	
	/**
	 * Get all the Payments from a Case
	 * @param caseId The Case Id of a Case which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<Payment> getPayments(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Payment".replace("{id}", caseId);
		GenericType<Collection<Payment>> type = new GenericType<Collection<Payment>>(){};
		Collection<Payment> response = (Collection<Payment>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your SocialAccount to an existing Customer on an existing Case
	 * @param caseId The Case Id of a Case with the Customer  which you have already posted
	 * @param socialAccount Your SocialAccount which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static SocialAccount postSocialAccount(String caseId, SocialAccount socialAccount) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/SocialAccount".replace("{id}", caseId);
		SocialAccount response = (SocialAccount)PerformHttpCall(url, HttpMethod.POST, SocialAccount.class, socialAccount, true);
		return response;
	}
	
	/**
	 * Update a specific SocialAccount on a Case which already contains a SocialAccount
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param socialAccount The SocialAccount you want to update the existing SocialAccount to
	 * @param socialAccountId The id of the SocialAccount you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static SocialAccount updateSocialAccount(String caseId, SocialAccount socialAccount, String socialAccountId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/SocialAccount/{id2}".replace("{id}", caseId).replace("{id2}", socialAccountId);
		SocialAccount response = (SocialAccount)PerformHttpCall(url, HttpMethod.PUT, SocialAccount.class, socialAccount, true);
		return response;
	}
	
	/**
	 * Get a specific SocialAccount from a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @param socialAccountId The Id of the SocialAccount you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static SocialAccount getSocialAccount(String caseId, String socialAccountId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/SocialAccount/{id2}".replace("{id}", caseId).replace("{id2}", socialAccountId);
		SocialAccount response = (SocialAccount)PerformHttpCall(url, HttpMethod.GET, SocialAccount.class, null, true);
		return response;
	}
	
	/**
	 * Get all the SocialAccounts from a Customer on a Case
	 * @param caseId The Case Id of a Case with the Customer  which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<SocialAccount> getSocialAccounts(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Customer/SocialAccount".replace("{id}", caseId);
		GenericType<Collection<SocialAccount>> type = new GenericType<Collection<SocialAccount>>(){};
		Collection<SocialAccount> response = (Collection<SocialAccount>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your TransactionAddress to an existing Transaction on an existing Case
	 * @param caseId The Case Id of a Case with the Transaction which you have already posted
	 * @param transactionAddress Your TransactionAddress which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address postTransactionAddress(String caseId, Address transactionAddress) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Address".replace("{id}", caseId);
		Address response = (Address)PerformHttpCall(url, HttpMethod.POST, Address.class, transactionAddress, true);
		return response;
	}
	
	/**
	 * Update a specific TransactionAddress on a Case which already contains a TransactionAddress
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param transactionAddress The TransactionAddress you want to update the existing TransactionAddress to
	 * @param transactionAddressId The id of the TransactionAddress you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address updateTransactionAddress(String caseId, Address transactionAddress, String transactionAddressId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Address/{id2}".replace("{id}", caseId).replace("{id2}", transactionAddressId);;
		Address response = (Address)PerformHttpCall(url, HttpMethod.PUT, Address.class, transactionAddress, true);
		return response;
	}
	
	/**
	 * Get a specific TransactionAddress from a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @param transactionAddressId The Id of the TransactionAddress you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static Address getTransactionAddress(String caseId, String transactionAddressId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Address/{id2}".replace("{id}", caseId).replace("{id2}", transactionAddressId);;
		Address response = (Address)PerformHttpCall(url, HttpMethod.GET, Address.class, null, true);
		return response;
	}
	
	/**
	 * Get all the Addresses from a Transaction on a Case
	 * @param caseId The Case Id of a Case with the Transaction which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<Address> getTransactionAddresses(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Address".replace("{id}", caseId);
		GenericType<Collection<Address>> type = new GenericType<Collection<Address>>(){};
		Collection<Address> response = (Collection<Address>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * Post your TransactionItem to an existing Transaction on an existing Case
	 * @param caseId The Case Id of a Case with the Transaction which you have already posted
	 * @param transactionItem Your TransactionItem which you want to post
	 * @return
	 * @throws TrustevApiException
	 */
	public static TransactionItem postTransactionItem(String caseId, TransactionItem transactionItem) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Item".replace("{id}", caseId);
		TransactionItem response = (TransactionItem)PerformHttpCall(url, HttpMethod.POST, TransactionItem.class, transactionItem, true);
		return response;
	}
	
	/**
	 * Update a specific TransactionItem on a Case which already contains a TransactionItem
	 * @param caseId The Case Id of a Case which you have already posted
	 * @param transactionItem The TransactionAddress you want to update the existing TransactionItem to
	 * @param transactionItemId The id of the TransactionItem you want to update
	 * @return
	 * @throws TrustevApiException
	 */
	public static TransactionItem updateTransactionItem(String caseId, TransactionItem transactionItem, String transactionItemId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Item/{id2}".replace("{id}", caseId).replace("{id2}", transactionItemId);
		TransactionItem response = (TransactionItem)PerformHttpCall(url, HttpMethod.PUT, TransactionItem.class, transactionItem, true);
		return response;
	}
	
	/**
	 * Get a specific TransactionItem from a Case
	 * @param caseId The Case Id of a Case with the Customer which you have already posted
	 * @param transactionItemId The Id of the TransactionItem you want to get
	 * @return
	 * @throws TrustevApiException
	 */
	public static TransactionItem getTransactionItem(String caseId, String transactionItemId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Item/{id2}".replace("{id}", caseId).replace("{id2}", transactionItemId);
		TransactionItem response = (TransactionItem)PerformHttpCall(url, HttpMethod.GET, TransactionItem.class, null, true);
		return response;
	}
	
	/**
	 * Get all the TransactionItems from a Transaction on a Case
	 * @param caseId The Case Id of a Case with the Transaction which you have already posted
	 * @return
	 * @throws TrustevApiException
	 */
	public static Collection<TransactionItem> getTransactionItems(String caseId) throws TrustevApiException
	{
		String url = "/Case/{id}/Transaction/Item".replace("{id}", caseId);
		GenericType<Collection<TransactionItem>> type = new GenericType<Collection<TransactionItem>>(){};
		Collection<TransactionItem> response = (Collection<TransactionItem>)PerformHttpCall(url, HttpMethod.GET, type.getRawClass(), null, true);
		return response;
	}
	
	/**
	 * This method performs the Http Request to the TrustevClient API
	 * @param urlPath The HttpMethod Uri
	 * @param method The Http Method
	 * @param responseType The type of the response object
	 * @param entity The relevant entity
	 * @param isAuthenticationNeeded Does this API call require the X-Authorization header
	 */
	private static Object PerformHttpCall(String uriPath, String httpMethod, Class responseType, Object entity, Boolean isAuthenticationNeeded)
			throws TrustevApiException
	{
		Object responseObject = null;
		
		DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
		defaultClientConfig.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(defaultClientConfig);
		
		WebResource resource = client.resource(ApiClient.baseUrl).path(uriPath);
		Builder resourceBuilder = resource.getRequestBuilder();
		
		if(isAuthenticationNeeded)
		{
			resourceBuilder = resourceBuilder.header("X-Authorization", String
					.format("%1$s %2$s", ApiClient.userName, getApiToken()));
		}
		
		resourceBuilder = resourceBuilder.accept(MediaType.APPLICATION_JSON);
		resourceBuilder = resourceBuilder.type(MediaType.APPLICATION_JSON);
		ClientResponse response;
		response = resourceBuilder
				.method(httpMethod, ClientResponse.class, entity);

		if (response.getStatus() == 200) {
			responseObject = response.getEntity(responseType);
		} else {
			ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
			
			throw new TrustevApiException(response.getStatus(), errorResponse.Message);
		}
		
		return responseObject;
	}
	
	private static String getApiToken() throws TrustevApiException
	{
		if(apiToken == null || expiryDate == null || expiryDate.before(new Date()))
		{
			SetToken();
		}
		
		return apiToken;
	}
	
	private static void SetToken() throws TrustevApiException
	{
		String url = "/Token";
		
		TokenResponse response = (TokenResponse)PerformHttpCall(url, HttpMethod.POST, TokenResponse.class, new TokenRequest(), false);
	
		ApiClient.apiToken = response.apiToken;
		ApiClient.expiryDate = response.expireAt;
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	static class ErrorResponse
	{
		public String Message;
	}
	
	static class TokenRequest
	{
		private static String userName;
		private static String passwordHash;
		private static String userNameHash;
		private static String timestamp;
		
		public TokenRequest(){
			this.timestamp = FormatTimeStamp(new Date());
		}
		
		public String getUserName()
		{
			if(userName == null)
			{
				userName = ApiClient.userName;
			}
			
			return userName;
		}
		
		public String getPasswordHash()
		{	
			return CreateHash(ApiClient.password, TokenRequest.timestamp, ApiClient.secret);
		}
		
		public String getTimeStamp() {
			return timestamp;
		}
		
		public String getUserNameHash() {
			
			return CreateHash(ApiClient.userName, TokenRequest.timestamp, ApiClient.secret);
		}
		
		private static String CreateHash(String value, String timestamp, String sharedsecret) {
			
			String hashValue = "";
			
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				String part1prehash = String.format("%1$s.%2$s", timestamp, value);
				md.update(part1prehash.getBytes());
				String part1hash = bytesToHex(md.digest());
				String part2prehash = String.format("%1$s.%2$s", part1hash, sharedsecret);
				//String hashValue = new String(Hex.encode(md.digest(part2prehash.getBytes("UTF-8"))));
				md.update(part2prehash.getBytes());
				hashValue = bytesToHex(md.digest());
				return hashValue;
			} catch (NoSuchAlgorithmException e) {
			}
			
			return hashValue;
		}
		
		private static String bytesToHex(byte[] bytes) {
	        StringBuffer result = new StringBuffer();
	        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
	        return result.toString();
	    }
		
		private String FormatTimeStamp(Date d) {
			if (d == null) {
				return null;
			} 
			DateFormat m_ISO8601Local = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			m_ISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			return m_ISO8601Local.format(d);
		}
	}
	
	static class TokenResponse{
		public String getApiToken() {
			return this.apiToken;
		}
		
		@JsonProperty("APIToken")
		public void setApiToken(String apiToken) {
			this.apiToken = apiToken;
		}
		
		public Date getExpireAt() {
			return expireAt;
		}
		
		@JsonProperty("ExpireAt")
		public void setExpireAt(String expireAt) throws ParseException {
			String expireTimeStamp = expireAt.substring(0, expireAt.lastIndexOf(".")+ 1 ) + "000Z";
			DateFormat m_ISO8601Local = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			m_ISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
			this.expireAt = m_ISO8601Local.parse(expireTimeStamp);
		}
		
		public String getCredentialType() {
			return credentialType;
		}
		
		@JsonProperty("CredentialType")
		public void setCredentialType(String credentialType) {
			this.credentialType = credentialType;
		}
		
		private String apiToken;
		private Date expireAt;
		private String credentialType;
	}
	
}
