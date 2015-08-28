package com.trustev.integration;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.junit.Test;

import com.trustev.domain.entities.*;
import com.trustev.domain.exceptions.TrustevApiException;
import com.trustev.web.ApiClient;
import junit.framework.TestCase;

public class TrustevClientTest extends TestCase {
	
	public String userName = "TrustevUserName";
	public String password = "TrustevPassword";
	public String secret = "TrustevSecret";
	
	@Test
	public void testAddCase() throws TrustevApiException {
		
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		
		assertNotNull(responseCase.getId());
		assertNotNull(responseCase.getCustomer());
		assertNotNull(responseCase.getCustomer().getId());
		assertEquals("John", responseCase.getCustomer().getFirstName());
		assertEquals("Doe", responseCase.getCustomer().getLastName());
	}
	
	@Test
	public void testGetCase() throws TrustevApiException {
		
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Case getCase = ApiClient.getCase(responseCase.getId());
		
		assertNotNull(getCase.getId());
		assertNotNull(getCase.getCustomer());
		assertNotNull(getCase.getCustomer().getId());
		assertEquals("John", getCase.getCustomer().getFirstName());
		assertEquals("Doe", getCase.getCustomer().getLastName());
	}
	
	@Test
	public void testGetCaseIncorrectCaseId() throws TrustevApiException {
		
		int responseCode = 200;
		try
		{
			//Initialize Client
			ApiClient.SetUp(userName, password, secret);
			
			Case getCase = ApiClient.getCase(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString());
		}
		catch(TrustevApiException ex)
		{
			responseCode = ex.responseCode;
		}
		assertEquals(404, responseCode);
	}
	
	@Test
	public void testUpdateCase() throws TrustevApiException {
		
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		customer.setFirstName("Joe");
		responseCase.setCustomer(customer);
		
		
		Case updateCase = ApiClient.updateCase(responseCase, responseCase.getId());
		
		assertNotNull(updateCase.getId());
		assertNotNull(updateCase.getCustomer());
		assertNotNull(updateCase.getCustomer().getId());
		assertEquals("Joe", updateCase.getCustomer().getFirstName());
		assertEquals("Doe", updateCase.getCustomer().getLastName());
	}
	
	@Test
	public void testGetDecision() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Decision decision = ApiClient.getDecision(responseCase.getId());
		
		assertNotNull(decision.getId());
		assertNotNull(decision.getResult());
	}
	
	@Test
	public void testPostCustomer() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		
		Case responseCase = ApiClient.postCase(kase);
		
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		
		Customer returnCustomer = ApiClient.postCustomer(responseCase.getId(), customer);
		
		assertNotNull(returnCustomer.getId());
		assertEquals("John", returnCustomer.getFirstName());
		assertEquals("Doe", returnCustomer.getLastName());
	}
	
	@Test
	public void testUpdateCustomer() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		
		customer.setFirstName("Joe");
		
		Customer responseCustomer = ApiClient.updateCustomer(responseCase.getId(), customer);
		
		assertNotNull(responseCustomer.getId());
		assertEquals("Joe", responseCustomer.getFirstName());
		assertEquals("Doe", responseCustomer.getLastName());
	}
	
	@Test
	public void testGetCustomer() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Customer responseCustomer = ApiClient.getCustomer(responseCase.getId());
		
		assertNotNull(responseCustomer.getId());
		assertEquals("John", responseCustomer.getFirstName());
		assertEquals("Doe", responseCustomer.getLastName());
	}
	
	@Test
	public void testPostTransaction() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Case responseCase = ApiClient.postCase(kase);
		
		Transaction transaction = new Transaction();
		transaction.setTotalTransactionValue(10.99);
		Transaction responseTransaction = ApiClient.postTransaction(responseCase.getId(), transaction);
		
		assertNotNull(responseTransaction.getId());
		assertEquals(10.99, responseTransaction.getTotalTransactionValue());
	}
	
	@Test
	public void testUpdateTransaction() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Transaction transaction = new Transaction();
		transaction.setTotalTransactionValue(10.99);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		transaction.setTotalTransactionValue(100.99);
		Transaction responseTransaction = ApiClient.updateTransaction(responseCase.getId(), transaction);
		
		assertNotNull(responseTransaction.getId());
		assertEquals(100.99, responseTransaction.getTotalTransactionValue());
	}
	
	@Test
	public void testGetTransaction() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Transaction transaction = new Transaction();
		transaction.setTotalTransactionValue(10.99);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		Transaction responseTransaction = ApiClient.getTransaction(responseCase.getId());
		
		assertNotNull(responseTransaction.getId());
		assertEquals(10.99, responseTransaction.getTotalTransactionValue());
	}
	
	@Test
	public void testPostCaseStatus() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Case responseCase = ApiClient.postCase(kase);
		
		CaseStatus caseStatus = new CaseStatus();
		caseStatus.setComment("Testing Status!!");
		caseStatus.setStatus(CaseStatusType.Completed);
		
		CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);
		
		assertNotNull(returnStatus.getId());
		assertEquals(CaseStatusType.Completed, returnStatus.getStatus());
	}
	
	@Test
	public void testGetCaseStatus() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Case responseCase = ApiClient.postCase(kase);
		
		//Case should have a default status of Placed
		CaseStatus returnStatus = ApiClient.getCaseStatus(responseCase.getId(), responseCase.getStatuses().iterator().next().getId());
		
		assertNotNull(returnStatus.getId());
		assertEquals(CaseStatusType.Placed, returnStatus.getStatus());
	}
	
	@Test
	public void testGetCaseStatuses() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Case responseCase = ApiClient.postCase(kase);
		
		CaseStatus caseStatus = new CaseStatus();
		caseStatus.setComment("Testing Status!!");
		caseStatus.setStatus(CaseStatusType.Completed);
		
		CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);
		
		//Case should have a default status of Placed
		Collection<CaseStatus> returnStatuses = ApiClient.getCaseStatuses(responseCase.getId());

		assertTrue(returnStatuses.size() > 1);
	}
	
	@Test
	public void testPostCustomerAddress() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		Address address = new Address();
		address.setCity("Cork");
		
		Address returnAddress = ApiClient.postCustomerAddress(responseCase.getId(), address);
		
		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testUpdateCustomerAddress() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Address address = new Address();
		address.setCity("Cork");
		Collection<Address> addresses = new LinkedList<Address>();
		addresses.add(address);
		customer.setAddresses(addresses);
		
		Case responseCase = ApiClient.postCase(kase);
		
		
		
		Address returnAddress = ApiClient.updateCustomerAddress(responseCase.getId(), address, responseCase.getCustomer().getAddresses().iterator().next().getId());
		
		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testGetCustomerAddress() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Address address = new Address();
		address.setCity("Cork");
		Collection<Address> addresses = new LinkedList<Address>();
		addresses.add(address);
		customer.setAddresses(addresses);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Address returnAddress = ApiClient.getCustomerAddress(responseCase.getId(), responseCase.getCustomer().getAddresses().iterator().next().getId());
		
		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testGetCustomerAddresses() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Address address = new Address();
		address.setCity("Cork");
		Address address1 = new Address();
		address1.setCity("Cork");
		Collection<Address> addresses = new LinkedList<Address>();
		addresses.add(address);
		addresses.add(address1);
		customer.setAddresses(addresses);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<Address> returnAddresses = ApiClient.getCustomerAddresses(responseCase.getId());

		assertTrue(returnAddresses.size() > 1);
	}
	
	@Test
	public void testPostEmail() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		Email email = new Email();
		email.setEmailAddress("johndow@gmail.com");
		
		Email returnEmail = ApiClient.postEmail(responseCase.getId(), email);

		assertNotNull(returnEmail.getId());
	}
	
	@Test
	public void testUpdateEmail() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Collection<Email> emails = new LinkedList<Email>();
		Email email = new Email();
		email.setEmailAddress("johndow@gmail.com");
		emails.add(email);
		customer.setEmail(emails);
		
		Case responseCase = ApiClient.postCase(kase);
		
		email.setEmailAddress("joedow@gmail.com");
		
		Email returnEmail = ApiClient.updateEmail(responseCase.getId(), email, responseCase.getCustomer().getEmail().iterator().next().getId());

		assertNotNull(returnEmail.getId());
		assertEquals("joedow@gmail.com", returnEmail.getEmailAddress());
	}
	
	@Test
	public void testGetEmail() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Collection<Email> emails = new LinkedList<Email>();
		Email email = new Email();
		email.setEmailAddress("johndow@gmail.com");
		emails.add(email);
		customer.setEmail(emails);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Email returnEmail = ApiClient.getEmail(responseCase.getId(), responseCase.getCustomer().getEmail().iterator().next().getId());

		assertNotNull(returnEmail.getId());
		assertEquals("johndow@gmail.com", returnEmail.getEmailAddress());
	}
	
	@Test
	public void testGetEmails() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		kase.setCustomer(customer);
		Collection<Email> emails = new LinkedList<Email>();
		Email email = new Email();
		email.setEmailAddress("johndow@gmail.com");
		
		Email email2 = new Email();
		email2.setEmailAddress("janedow@gmail.com");
		emails.add(email);
		emails.add(email2);
		customer.setEmail(emails);
		
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<Email> returnEmails = ApiClient.getEmails(responseCase.getId());

		assertTrue(returnEmails.size() > 1);
	}
	
	@Test
	public void testPostPayment() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Case responseCase = ApiClient.postCase(kase);
		
		Payment payment = new Payment();
		payment.setBinNumber("123456");
		Payment returnPayment = ApiClient.postPayment(responseCase.getId(), payment);

		assertNotNull(returnPayment.getId());
	}
	
	@Test
	public void testUpdatePayment() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Payment> payments = new LinkedList<Payment>();
		Payment payment = new Payment();
		payment.setBinNumber("123456");
		payments.add(payment);
		kase.setPayments(payments);
		Case responseCase = ApiClient.postCase(kase);
		
		payment.setBinNumber("654321");
		Payment returnPayment = ApiClient.updatePayment(responseCase.getId(), payment, responseCase.getPayments().iterator().next().getId());

		assertNotNull(returnPayment.getId());
	}
	
	@Test
	public void testGetPayment() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Payment> payments = new LinkedList<Payment>();
		Payment payment = new Payment();
		payment.setBinNumber("123456");
		payments.add(payment);
		kase.setPayments(payments);
		Case responseCase = ApiClient.postCase(kase);
		
		Payment returnPayment = ApiClient.getPayment(responseCase.getId(), responseCase.getPayments().iterator().next().getId());

		assertNotNull(returnPayment.getId());
	}
	
	@Test
	public void testGetPayments() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Payment> payments = new LinkedList<Payment>();
		Payment payment = new Payment();
		payment.setBinNumber("123456");
		Payment payment2 = new Payment();
		payment2.setBinNumber("654321");
		payments.add(payment);
		payments.add(payment2);
		kase.setPayments(payments);
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<Payment> returnPayments = ApiClient.getPayments(responseCase.getId());

		assertTrue(returnPayments.size() > 1);
	}
	
	@Test
	public void testPostSocialAccount() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Customer customer = new Customer();
		customer.setFirstName("Joe");
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		SocialAccount socialAccount = new SocialAccount();
		socialAccount.setSocialId(123454);
		SocialAccount returnSocialAccount = ApiClient.postSocialAccount(responseCase.getId(), socialAccount);

		assertNotNull(returnSocialAccount.getId());
	}
	
	@Test
	public void testUpdateSocialAccount() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<SocialAccount> socialAccounts = new LinkedList<SocialAccount>();
		Customer customer = new Customer();
		SocialAccount socialAccount = new SocialAccount();
		socialAccount.setSocialId(123456);
		socialAccounts.add(socialAccount);
		customer.setSocialAccounts(socialAccounts);
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		socialAccount.setSocialId(65432);
		SocialAccount returnSocialAccount = ApiClient.updateSocialAccount(responseCase.getId(), socialAccount, responseCase.getCustomer().getSocialAccounts().iterator().next().getId());

		assertNotNull(returnSocialAccount.getId());
	}
	
	@Test
	public void testGetSocialAccount() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<SocialAccount> socialAccounts = new LinkedList<SocialAccount>();
		Customer customer = new Customer();
		SocialAccount socialAccount = new SocialAccount();
		socialAccount.setSocialId(123456);
		socialAccounts.add(socialAccount);
		customer.setSocialAccounts(socialAccounts);
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		SocialAccount returnSocialAccount = ApiClient.getSocialAccount(responseCase.getId(), responseCase.getCustomer().getSocialAccounts().iterator().next().getId());

		assertNotNull(returnSocialAccount.getId());
	}
	
	@Test
	public void testGetSocialAccounts() throws TrustevApiException {
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<SocialAccount> socialAccounts = new LinkedList<SocialAccount>();
		Customer customer = new Customer();
		SocialAccount socialAccount = new SocialAccount();
		socialAccount.setSocialId(123456);
		socialAccounts.add(socialAccount);
		customer.setSocialAccounts(socialAccounts);
		kase.setCustomer(customer);
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<SocialAccount> returnSocialAccounts = ApiClient.getSocialAccounts(responseCase.getId());

		assertEquals(1, returnSocialAccounts.size());
	}
	
	@Test
	public void testPostTransactionAddress() throws TrustevApiException {
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Address> addresses = new LinkedList<Address>();
		Transaction transaction = new Transaction();
		Address address = new Address();
		address.setCity("Cork");
		addresses.add(address);
		transaction.setAddresses(addresses);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		Address returnAddress = ApiClient.postTransactionAddress(responseCase.getId(), address);

		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testUpdateTransactionAddress() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Address> addresses = new LinkedList<Address>();
		Transaction transaction = new Transaction();
		Address address = new Address();
		address.setCity("Cork");
		addresses.add(address);
		transaction.setAddresses(addresses);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		address.setCity("Dublin");
		Address returnAddress = ApiClient.updateTransactionAddress(responseCase.getId(), address, responseCase.getTransaction().getAddresses().iterator().next().getId());

		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testGetTransactionAddress() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Address> addresses = new LinkedList<Address>();
		Transaction transaction = new Transaction();
		Address address = new Address();
		address.setCity("Cork");
		addresses.add(address);
		transaction.setAddresses(addresses);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		Address returnAddress = ApiClient.getTransactionAddress(responseCase.getId(), responseCase.getTransaction().getAddresses().iterator().next().getId());

		assertNotNull(returnAddress.getId());
	}
	
	@Test
	public void testGetTransactionAddresseses() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<Address> addresses = new LinkedList<Address>();
		Transaction transaction = new Transaction();
		Address address = new Address();
		address.setCity("Cork");
		addresses.add(address);
		transaction.setAddresses(addresses);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<Address> returnAddresses = ApiClient.getTransactionAddresses(responseCase.getId());

		assertTrue(returnAddresses.size() == 1);
	}
	
	@Test
	public void testPostTransactionItem() throws TrustevApiException {
		ApiClient.SetUp(userName, password, secret);
		
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
		Transaction transaction = new Transaction();
		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setItemValue(10.99);
		transactionItems.add(transactionItem);
		transaction.setItems(transactionItems);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		TransactionItem returnTransactionItem = ApiClient.postTransactionItem(responseCase.getId(), transactionItem);

		assertNotNull(returnTransactionItem.getId());
	}
	
	@Test
	public void testUpdateTransactionItem() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
		Transaction transaction = new Transaction();
		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setItemValue(10.99);
		transactionItems.add(transactionItem);
		transaction.setItems(transactionItems);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		transactionItem.setItemValue(100.99);
		TransactionItem returnTransactionItem = ApiClient.updateTransactionItem(responseCase.getId(), transactionItem, responseCase.getTransaction().getItems().iterator().next().getId());

		assertNotNull(returnTransactionItem.getId());
	}
	
	@Test
	public void testGetTransactionItem() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
		Transaction transaction = new Transaction();
		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setItemValue(10.99);
		transactionItems.add(transactionItem);
		transaction.setItems(transactionItems);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		TransactionItem returnTransactionItem = ApiClient.getTransactionItem(responseCase.getId(), responseCase.getTransaction().getItems().iterator().next().getId());

		assertNotNull(returnTransactionItem.getId());
	}
	
	@Test
	public void testGetTransactionItems() throws TrustevApiException {
		//Initialize Client
		ApiClient.SetUp(userName, password, secret);
				
		Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
		Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
		Transaction transaction = new Transaction();
		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setItemValue(10.99);
		transactionItems.add(transactionItem);
		transaction.setItems(transactionItems);
		kase.setTransaction(transaction);
		Case responseCase = ApiClient.postCase(kase);
		
		Collection<TransactionItem> returnTransactionItems = ApiClient.getTransactionItems(responseCase.getId());

		assertTrue(returnTransactionItems.size() == 1);
	}
}
