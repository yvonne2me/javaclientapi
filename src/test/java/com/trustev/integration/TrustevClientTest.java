package com.trustev.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.UUID;

import com.trustev.domain.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.trustev.domain.exceptions.TrustevApiException;
import com.trustev.web.ApiClient;

public class TrustevClientTest {

    // Set your test credentials and baseURL to run the integration tests
    // you can set these up on the config.properties file or run them as
    // command line parameters when using maven.
    // Example : mvn clean test -DuserName=test-user -Dpassword=testpasswd -Dsecret=testSecret -Durl=US

    public static String userName;
    public static String password;
    public static String secret;
    public static BaseUrl baseUrl;
    public static String alternateUrl;

    @BeforeClass
    public static void SetUpClass() throws Exception {

        // try to load parameters from the command line
        userName = System.getProperty("userName");
        password = System.getProperty("password");
        secret = System.getProperty("secret");
        alternateUrl = System.getProperty("altUrl");

        String baseUrlString;
        if (alternateUrl == null) {
            baseUrlString = System.getProperty("url");

            if (baseUrlString != null) {
                baseUrl = BaseUrl.valueOf(baseUrlString);
            }
        }

        // no command line parameters were provided so we'll try to read them from the properties file
        if (userName == null && password == null && secret == null && baseUrl == null && alternateUrl == null) {
            InputStream inputStream = null;
            try {
                Properties prop = new Properties();
                String propFileName = "config.properties";

                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                inputStream = classloader.getResourceAsStream(propFileName);

                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }

                // get the property values
                userName = prop.getProperty("userName");
                password = prop.getProperty("password");
                secret = prop.getProperty("secret");
                alternateUrl = prop.getProperty("altUrl");
                baseUrlString = prop.getProperty("url");

                if (baseUrlString != null && !baseUrlString.equals("") && (baseUrlString.equals("US") || baseUrlString.equals("EU"))) {
                    baseUrl = BaseUrl.valueOf(baseUrlString);
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e);
                throw e;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        if (userName == null || password == null || secret == null || (baseUrl == null && alternateUrl == null)) {
            throw new Exception("Inexisting or invalid credentials provided.");
        }

        ApiClient.removeAllMerchantSites();

        if (alternateUrl != null && alternateUrl != "") {
            ApiClient.SetUp(userName, password, secret, alternateUrl);
        } else if (baseUrl != null) {
            ApiClient.SetUp(userName, password, secret, baseUrl);
        }
    }

    @Test
    public void testAddCase() throws TrustevApiException {

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
    public void testAddFulfilmentToCase() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Fulfilment fulfilment= new Fulfilment();
        fulfilment.setFulfilmentGeoLocation(FulfilmentGeoLocation.National);
        fulfilment.setFulfilmentMethod(FulfilmentMethod.Courier);
        fulfilment.setTimeToFulfilment(TimeToFulfilment.SameDay);
        kase.setFulfilment(fulfilment);

        Case responseCase = ApiClient.postCase(kase);

        assertNotNull(responseCase.getId());
        assertNotNull(responseCase.getCustomer());
        assertNotNull(responseCase.getCustomer().getId());
        assertEquals("John", responseCase.getCustomer().getFirstName());
        assertEquals("Doe", responseCase.getCustomer().getLastName());
        assertEquals(FulfilmentGeoLocation.National, responseCase.getFulfilment().getFulfilmentGeoLocation());
        assertEquals(FulfilmentMethod.Courier, responseCase.getFulfilment().getFulfilmentMethod());
        assertEquals(TimeToFulfilment.SameDay, responseCase.getFulfilment().getTimeToFulfilment());
    }


    /******************************Case Tests**************************************/

    @Test
    public void testUpdateCase() throws TrustevApiException {

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
    public void testGetCase() throws TrustevApiException {

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
        try {
            ApiClient.getCase(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }


    @Test
    public void testUpdateNonExistingCase() throws TrustevApiException {

        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Doe");
            kase.setCustomer(customer);

            ApiClient.updateCase(kase, kase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }


    @Test
    public void testDefaultCaseTypePost() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.Default);

        Case responseCase = ApiClient.postCase(kase);

        assertEquals(CaseType.Default, responseCase.getCaseType());
    }


    @Test
    public void testAccountCreationCaseTypePost() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAccountNumber(UUID.randomUUID().toString());
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.AccountCreation);

        Case responseCase = ApiClient.postCase(kase);

        assertEquals(CaseType.AccountCreation, responseCase.getCaseType());
    }


    @Test
    public void testApplicationCaseTypePost() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.Application);

        Case responseCase = ApiClient.postCase(kase);

        assertEquals(CaseType.Application, responseCase.getCaseType());
    }

    /******************************End of Case Tests**************************************/


    /******************************Decision Tests*****************************************/


    @Test
    public void testGetDecision() throws TrustevApiException {

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
    public void testGetDecisionPass() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "pass");
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        Decision decision = ApiClient.getDecision(responseCase.getId());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Pass, decision.getResult());
    }


    @Test
    public void testGetDecisionFlag() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "flag");
        Customer customer = new Customer();
        customer.setFirstName("JohnPass");
        customer.setLastName("DoePass");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        Decision decision = ApiClient.getDecision(responseCase.getId());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Flag, decision.getResult());
    }

    @Test
    public void testGetDecisionFail() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "fail");
        Customer customer = new Customer();
        customer.setFirstName("JohnFail");
        customer.setLastName("DoeFail");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        Decision decision = ApiClient.getDecision(responseCase.getId());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Fail, decision.getResult());
    }

    @Test
    public void testGetInexistentDecision() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*******************************End Decision Tests******************************/


    /*****************************Detailed Decision Tests***************************/
    @Test
    public void testGetDetailedDecision() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        DetailedDecision decision = ApiClient.getDetailedDecision(responseCase.getId());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
    }


    @Test
    public void testGetInexistentDetailedDecision() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDetailedDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }


    /***************************End Detailed Decision Tests******************************/

    /*****************************OTP Tests***************************/
    @Test
    public void testSentOtp() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        //change this to a correct number
        customer.setPhoneNumber("353878767543");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        DetailedDecision decision = ApiClient.getDetailedDecision(responseCase.getId());
        assertEquals(decision.getAuthentication().getOtp().getStatus(), OTPStatus.Offered);
        DigitalAuthenticationResult auth = new DigitalAuthenticationResult();
        OTPResult otp = new OTPResult(responseCase.getId());
        otp.setDeliveryType(PhoneDeliveryType.Sms);
        otp.setLanguage(OTPLanguageEnum.EN);

        auth.setOtp(otp);
        DigitalAuthenticationResult check=ApiClient.postOtp(responseCase.getId(), auth);
        assertEquals(check.getOtp().getStatus(),OTPStatus.InProgress);

    }


    @Test
    public void testVerifyOtp() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        //change this to a correct number
        customer.setPhoneNumber("353878767543");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase);

        DetailedDecision decision = ApiClient.getDetailedDecision(responseCase.getId());
        assertEquals(decision.getAuthentication().getOtp().getStatus(), OTPStatus.Offered);
        DigitalAuthenticationResult auth = new DigitalAuthenticationResult();
        OTPResult otp = new OTPResult(responseCase.getId());
        otp.setDeliveryType(PhoneDeliveryType.Sms);
        otp.setLanguage(OTPLanguageEnum.EN);

        auth.setOtp(otp);
        DigitalAuthenticationResult check=ApiClient.postOtp(responseCase.getId(), auth);
        assertEquals(check.getOtp().getStatus(),OTPStatus.InProgress);

        DigitalAuthenticationResult verifyPassword = new DigitalAuthenticationResult();
        OTPResult otpPassword = new OTPResult(responseCase.getId());
        otpPassword.setDeliveryType(PhoneDeliveryType.Sms);
        otpPassword.setLanguage(OTPLanguageEnum.EN);
        otpPassword.setStatus(OTPStatus.InProgress);
        //make sure that you edit this with the code received from the sms
        otpPassword.setPasscode("12345");

        verifyPassword.setOtp(otpPassword);
        DigitalAuthenticationResult verify=ApiClient.putOtp(responseCase.getId(), verifyPassword);
        assertEquals(verify.getOtp().getMessage(),"OTP Offered And Failed");
    }


    /***************************End OTP Tests******************************/


    /*****************************Customer Object Tests**********************************/
    @Test
    public void testPostCustomer() throws TrustevApiException {

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
    public void testGetInexistentCustomer() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase);

            ApiClient.getCustomer(responseCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Customer Object Tests****************************/

    /*****************************Transaction Object Tests********************************/
    @Test
    public void testPostTransaction() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        Transaction responseTransaction = ApiClient.postTransaction(responseCase.getId(), transaction);

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testUpdateTransaction() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase);

        transaction.setTotalTransactionValue(20.99);
        Transaction responseTransaction = ApiClient.updateTransaction(responseCase.getId(), transaction);

        assertNotNull(responseTransaction.getId());
        assertEquals(20.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testGetTransaction() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase);

        Transaction responseTransaction = ApiClient.getTransaction(responseCase.getId());

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }


    @Test
    public void testGetInexistentTransaction() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase);

            ApiClient.getTransaction(responseCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Transaction Object Tests*************************/

    /*****************************Status Object Tests*************************************/
    @Test
    public void testPostCaseStatus() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.ReportedFraud);

        CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.ReportedFraud, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatus() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        //Case should have a default status of Placed
        CaseStatus returnStatus = ApiClient.getCaseStatus(responseCase.getId(), responseCase.getStatuses().iterator().next().getId());

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.Placed, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatuses() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.Completed);

        ApiClient.postCaseStatus(responseCase.getId(), caseStatus);

        //Case should have a default status of Placed
        Collection<CaseStatus> returnStatuses = ApiClient.getCaseStatuses(responseCase.getId());

        assertTrue(returnStatuses.size() > 1);
    }

    @Test
    public void testGetInexistingCaseStatuses() throws TrustevApiException {
        int responseCode = 200;
        try {
            ApiClient.getCaseStatuses(UUID.randomUUID() + "|" + UUID.randomUUID());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Transaction Object Tests*************************/

    /*****************************Customer Address Object Tests***************************/
    @Test
    public void testPostCustomerAddress() throws TrustevApiException {

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

        Address newAddress = new Address();
        newAddress.setCity("Dublin");
        Collection<Address> newAddresses = new LinkedList<Address>();
        newAddresses.add(newAddress);
        customer.setAddresses(newAddresses);

        Address returnAddress = ApiClient.updateCustomerAddress(responseCase.getId(), newAddress, responseCase.getCustomer().getAddresses().iterator().next().getId());

        Address getAddress = ApiClient.getCustomerAddress(responseCase.getId(), returnAddress.getId());

        assertNotNull(getAddress.getId());
        assertEquals("Dublin", getAddress.getCity());
    }

    @Test
    public void testGetCustomerAddress() throws TrustevApiException {

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
        assertEquals("Cork", returnAddress.getCity());
    }

    @Test
    public void testGetCustomerAddresses() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Address address = new Address();
        address.setCity("Cork");
        Address address1 = new Address();
        address1.setCity("Dublin");
        Collection<Address> addresses = new LinkedList<Address>();
        addresses.add(address);
        addresses.add(address1);
        customer.setAddresses(addresses);

        Case responseCase = ApiClient.postCase(kase);

        Collection<Address> returnAddresses = ApiClient.getCustomerAddresses(responseCase.getId());

        assertTrue(returnAddresses.size() > 1);
    }

    @Test
    public void testGetInexistingCustomerAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase);

            ApiClient.getCustomerAddresses(returningCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of CustomerAddress Object Tests*************************/

    /*****************************Customer EmailAddress Object Tests**************************/
    @Test
    public void testPostEmail() throws TrustevApiException {

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
    public void testGetInexistingEmailAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase);

            ApiClient.getEmails(returningCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of Customer EmailAddress Object Tests********************/

    /*****************************Payment Object Tests*****************************************/
    @Test
    public void testPostPayment() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        Payment payment = new Payment();
        payment.setBinNumber("123456");
        Payment returnPayment = ApiClient.postPayment(responseCase.getId(), payment);

        assertNotNull(returnPayment.getId());
    }

    @Test
    public void testUpdatePayment() throws TrustevApiException {

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
        assertEquals("654321", returnPayment.getBinNumber());
    }

    @Test
    public void testGetPayment() throws TrustevApiException {

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
    public void testGetInexistingPayments() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase);

            ApiClient.getPayments(returningCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of Payment Object Tests*********************************/

    /*****************************TransactionAddress Object Tests*****************************/
    @Test
    public void testPostTransactionAddress() throws TrustevApiException {

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
        assertEquals("Cork", returnAddress.getCity());
    }

    @Test
    public void testUpdateTransactionAddress() throws TrustevApiException {

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
        assertEquals("Dublin", returnAddress.getCity());
    }

    @Test
    public void testGetTransactionAddress() throws TrustevApiException {

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
    public void testGetInexistingTransactionAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase);

            ApiClient.getTransactionAddresses(returningCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of TransactionAddress Object Tests**************************/

    /*****************************TransactionItem Object Tests************************************/

    @Test
    public void testPostTransactionItem() throws TrustevApiException {

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

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
        Transaction transaction = new Transaction();
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setItemValue(10.99);
        transactionItems.add(transactionItem);

        TransactionItem transactionItem2 = new TransactionItem();
        transactionItem.setItemValue(20.99);
        transactionItems.add(transactionItem2);

        transaction.setItems(transactionItems);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase);

        Collection<TransactionItem> returnTransactionItems = ApiClient.getTransactionItems(responseCase.getId());

        assertTrue(returnTransactionItems.size() == 2);
    }

    @Test
    public void testGetInexistingTransactionItems() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase);

            ApiClient.getTransactionItems(returningCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }
    /*****************************End of TransactionItem Object Tests*****************************/
}
