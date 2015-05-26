package com.trustev.integration;


import java.util.Collection;
import java.util.Date;
import java.util.UUID;





import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TrustevClientTest extends TestCase {

	@Test
	public void testGetToken() throws TrustevApiException {
		BaseObject.GetApiTokenId();
		Assert.assertNotNull(BaseObject.apiTokenExpires);
		Assert.assertNotNull(BaseObject.apiTokenId);
		Assert.assertTrue(BaseObject.apiTokenExpires.after(new Date()));
	}
	
	@Test
	public void testGetTokenBadPassword() {
		String goodPassword = null;
		try {
			new GetTokenRequest();
			goodPassword = GetTokenRequest.passWord;
			// fake a bad password
			GetTokenRequest.passWord = "BadPassword";
			// clear the API Token so that it forces a new create of the Api Token with the bad password
			BaseObject.apiTokenExpires = null;
			BaseObject.apiTokenId = null;
			BaseObject.GetApiTokenId();
			Assert.fail("Exeption was not thrown");
		} catch (TrustevApiException e) {
			Assert.assertEquals("Your Password and |UserNameHash do not match our records. The problem is either related to an incorrect password, secret or due to incorrect hashing", e.getMessage());
		} finally {
			// need to replace the good password so subsequent tests in the test run don't fail
			GetTokenRequest.passWord = goodPassword;
		}
	}
	
	@Test
	public void testAddCase() throws TrustevApiException {
		// generate random case info
		PodamFactory factory = new PodamFactoryImpl();
		Case myCase = factory.manufacturePojo(Case.class);
		//myCase.setSessionId(UUID.fromString("f9b21183-a88e-4454-992a-febe98658384"));
		Assert.assertNotNull(myCase.getCaseNumber());
		Assert.assertNull(myCase.getId());
		myCase.Save();
		Assert.assertNotNull(myCase.getId());
	}
	
	
	@Test
	public void testGetDecision() throws TrustevApiException {
		String caseNumber = "GetDecision" + (new Date()).toString();
		UUID sessionId = UUID.fromString("f9b21183-a88e-4454-992a-febe98658384");
		Case myCase = new Case(sessionId);
		
		// set initial values
		myCase.setCaseNumber(caseNumber);
		myCase.setTimestamp(new Date());
		Customer customer = new Customer();
		customer.setFirstName("Gene");
		customer.setLastName("Geniune");
		myCase.setCustomer(customer);
		Assert.assertNull(myCase.getId());
		Assert.assertNotNull(myCase.getCaseNumber());
		Assert.assertNull("Case has not yet been saved so id should be emtpy",myCase.getId());
		
		// get the decision on the case
		Decision decision = myCase.MakeDecision();
		Assert.assertNotNull(decision);
		Assert.assertNotNull(decision.getResult());
	}
	
	@Test
	public void testUpdateCase() throws TrustevApiException {
		String caseNumberBeforeSave = "CaseNumberBeforeSave" + (new Date()).toString();
		UUID sessionId = UUID.fromString("f9b21183-a88e-4454-992a-febe98658384");
		Case myCase = new Case(sessionId);
		
		// set initial values
		myCase.setCaseNumber(caseNumberBeforeSave);
		myCase.setTimestamp(new Date());
		Customer customer = new Customer();
		customer.setFirstName("Freddy");
		customer.setLastName("Fraud");
		myCase.setCustomer(customer );
		Assert.assertNull(myCase.getId());
		Assert.assertNotNull(myCase.getCaseNumber());
		Assert.assertNull("Case has not yet been saved so id should be emtpy",myCase.getId());
		myCase.Save();
		Assert.assertNotNull(myCase.getId());
		String id = myCase.getId();
		myCase = null;	// delete the case completely
		
		// retrieve the case and assert on values
		Case retrievedCase = Case.Find(id);
		Assert.assertEquals(id,retrievedCase.getId());
		Assert.assertEquals(sessionId,retrievedCase.getSessionId());
		Assert.assertEquals(caseNumberBeforeSave, retrievedCase.getCaseNumber());
		Assert.assertEquals("Freddy", retrievedCase.getCustomer().getFirstName());
		Assert.assertEquals("Fraud", retrievedCase.getCustomer().getLastName());
		
		// update values in the case and save
		String caseNumberAfterSave = "CaseNumberAfterSave"  + (new Date()).toString();;
		retrievedCase.setCaseNumber(caseNumberAfterSave);
		retrievedCase.getCustomer().setFirstName("Frank");
		retrievedCase.Save();
		retrievedCase = null;
		
		// retrieve the case again and assert values where changed
		Case retrievedCaseAfterSave = Case.Find(id);
		Assert.assertEquals(id,retrievedCaseAfterSave.getId());
		Assert.assertEquals(sessionId,retrievedCaseAfterSave.getSessionId());
		Assert.assertEquals(caseNumberAfterSave, retrievedCaseAfterSave.getCaseNumber());
		Assert.assertEquals("Frank", retrievedCaseAfterSave.getCustomer().getFirstName());
		Assert.assertEquals("Fraud", retrievedCaseAfterSave.getCustomer().getLastName());
		
		// get the decision on the case
		/*Decision decision = retrievedCaseAfterSave.MakeDecision();
		Assert.assertNotNull(decision);
		Assert.assertNotNull(decision.getResult());*/
	}
	
	@Test
	public void testCustomerEndpoint() throws TrustevApiException {
		UUID sessionId = UUID.fromString("f9b21183-a88e-4454-992a-febe98658384");
		Case myCase = new Case(sessionId);
		
		String caseNumberBeforeSave = "CustomerEndpointTest" + (new Date()).toString();
		// set initial values
		myCase.setCaseNumber(caseNumberBeforeSave);
		myCase.setTimestamp(new Date());
		Assert.assertNull(myCase.getId());
		myCase.Save();
		Assert.assertNotNull(myCase.getId());
		Assert.assertNull(myCase.getCustomer());
		String caseId = myCase.getId();
		myCase = null;
		
		// set the customer using the granular save
		Customer myCustomer = new Customer();
		myCustomer.setFirstName("Terry");
		myCustomer.setLastName("TestCustomer");
		Date dob = new Date(1990,1,1);
		myCustomer.setDateOfBirth(dob);
		myCustomer.SaveForCase(caseId);
		myCustomer = null;
		
		// retrieve the case and assert on values
		myCase = Case.Find(caseId);
		Assert.assertNotNull(myCase);
		Assert.assertNotNull(myCase.getCustomer());
		Assert.assertEquals("Terry", myCase.getCustomer().getFirstName());
		Assert.assertEquals("TestCustomer", myCase.getCustomer().getLastName());
		Assert.assertEquals(dob,myCase.getCustomer().getDateOfBirth());
		myCase = null;
		
		// test the customer finder
		myCustomer = Customer.Find(caseId);
		Assert.assertNotNull(myCustomer);
		Assert.assertEquals("Terry", myCustomer.getFirstName());
		Assert.assertEquals("TestCustomer", myCustomer.getLastName());
		Assert.assertEquals(dob,myCustomer.getDateOfBirth());
	}
	
	@Test
	public void testPaymentEndpoint() throws TrustevApiException {
		String caseNumberBeforeSave = "TestPaymentEndpoint" + (new Date()).toString();
		UUID sessionId = UUID.fromString("f9b21183-a88e-4454-992a-febe98658384");
		Case myCase = new Case(sessionId);
		
		// set initial values
		myCase.setCaseNumber(caseNumberBeforeSave);
		myCase.setTimestamp(new Date());
		Assert.assertNull(myCase.getId());
		myCase.Save();
		Assert.assertNotNull(myCase.getId());
		Assert.assertNull(myCase.getPayments());
		String caseId = myCase.getId();
		myCase = null;
		
		// add first payment
		Payment myCreditPayment = new Payment();
		myCreditPayment.setBinNumber("123456");
		myCreditPayment.setPaymentType(PaymentType.CreditCard);
		myCreditPayment.SaveForCase(caseId);
		String creditPaymentId = myCreditPayment.getId();
		
		// add second payment
		Payment myDebitPayment = new Payment();
		myDebitPayment.setBinNumber("654321");
		myDebitPayment.setPaymentType(PaymentType.DebitCard);
		myDebitPayment.SaveForCase(caseId);
		String debitPaymentId = myDebitPayment.getId();
		
		// make sure that a second save updates the payment rather than adding a third
		myDebitPayment.setBinNumber("654322");
		myDebitPayment.SaveForCase(caseId);
		
		// retrieve the case and assert on values
		myCase = Case.Find(caseId);
		Assert.assertNotNull(myCase);
		Assert.assertNotNull(myCase.getPayments());
		Assert.assertEquals(2,myCase.getPayments().size());
		Assert.assertTrue(myCase.getPayments().contains(myCreditPayment));
		Assert.assertTrue(myCase.getPayments().contains(myDebitPayment));
		myCase = null;
		
		// test the payment finder
		Payment retrievedCreditPayment = Payment.Find(caseId, creditPaymentId);
		Assert.assertNotNull(retrievedCreditPayment);
		Assert.assertEquals("123456", retrievedCreditPayment.getBinNumber());
		Assert.assertEquals(PaymentType.CreditCard, retrievedCreditPayment.getPaymentType());
		Payment retrievedDebitPayment = Payment.Find(caseId, debitPaymentId);
		Assert.assertNotNull(retrievedDebitPayment);
		Assert.assertEquals("654322", retrievedDebitPayment.getBinNumber());
		Assert.assertEquals(PaymentType.DebitCard, retrievedDebitPayment.getPaymentType());
		
		// test the payment findall
		Collection<Payment> payments = Payment.FindAll(caseId);
		Assert.assertNotNull(payments);
		Assert.assertEquals(2,payments.size());
		Assert.assertTrue(payments.contains(myCreditPayment));
		Assert.assertTrue(payments.contains(myDebitPayment));
	}

}
