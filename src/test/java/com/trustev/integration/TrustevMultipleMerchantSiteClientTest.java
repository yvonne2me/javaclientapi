package com.trustev.integration;

import com.trustev.domain.entities.*;
import com.trustev.domain.exceptions.TrustevApiException;
import com.trustev.web.ApiClient;
import org.junit.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TrustevMultipleMerchantSiteClientTest {
    // Set your test credentials and baseURL to run the integration tests
    // you can set these up on the config.properties file or run them as
    // command line parameters when using maven.
    // Note: these tests will be ignored if a second set of credentials is not provided, or if the first and second sets are the same
    // Example : mvn clean test -DuserName=test-user -Dpassword=testpasswd -Dsecret=testSecret -Durl=US -DuserName2=test-user2 -Dpassword2=testpasswd2 -Dsecret2=testSecret2 -Durl2=US

    public static String userName;
    public static String password;
    public static String secret;
    public static BaseUrl baseUrl;
    public static String alternateUrl;

    public static String userName2;
    public static String password2;
    public static String secret2;
    public static BaseUrl baseUrl2;
    public static String alternateUrl2;

    public static MerchantSite merchantSite1;
    public static MerchantSite merchantSite2;

    public static boolean isFirstOrSecondMerchantSiteNull;
    public static boolean isFirstAndSecondMerchantSitesSame;

    //<editor-fold desc="Merchant Site Credential Setup">
    @BeforeClass
    public static void SetUpClass() throws Exception {
        isFirstOrSecondMerchantSiteNull =false;
        isFirstAndSecondMerchantSitesSame=false;

        // try to load parameters from the command line
        userName = System.getProperty("userName");
        password = System.getProperty("password");
        secret = System.getProperty("secret");
        alternateUrl = System.getProperty("altUrl");

        userName2 = System.getProperty("userName2");
        password2 = System.getProperty("password2");
        secret2 = System.getProperty("secret2");
        alternateUrl2 = System.getProperty("altUrl2");

        String baseUrlString;
        String baseUrlString2;
        if (alternateUrl == null) {
            baseUrlString = System.getProperty("url");
            baseUrlString2 = System.getProperty("url2");

            if (baseUrlString != null && baseUrlString2 != null) {
                baseUrl = BaseUrl.valueOf(baseUrlString);
                baseUrl2 = BaseUrl.valueOf(baseUrlString2);
            }
        }

        // no command line parameters were provided so we'll try to read them from the properties file
        if ((userName == null && password == null && secret == null && baseUrl == null && alternateUrl == null) &&
                (userName2 == null && password2 == null && secret2 == null && baseUrl2 == null && alternateUrl2 == null)) {
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

                userName2 = prop.getProperty("userName2");
                password2 = prop.getProperty("password2");
                secret2 = prop.getProperty("secret2");
                alternateUrl2 = prop.getProperty("altUrl2");

                baseUrlString = prop.getProperty("url");
                baseUrlString2 = prop.getProperty("url2");

                if ((baseUrlString != null && !baseUrlString.equals("") && (baseUrlString.equals("US") || baseUrlString.equals("EU"))) &&
                        (baseUrlString2 != null && !baseUrlString2.equals("") && (baseUrlString2.equals("US") || baseUrlString2.equals("EU")))) {
                    baseUrl = BaseUrl.valueOf(baseUrlString);
                    baseUrl2 = BaseUrl.valueOf(baseUrlString2);
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

        //Test if the First set of credentials is null ... just in case
        if (userName == null || password == null || secret == null || (baseUrl == null && alternateUrl == null)) {
            throw new Exception("Inexisting or invalid credentials provided.");
        }

        //Test if the second set of credentials is null
        if (userName2 == null || password2 == null || secret2 == null || (baseUrl2 == null && alternateUrl2 == null)){
            isFirstOrSecondMerchantSiteNull=true;
        } else if (userName2.equals("") || password2.equals("") || secret2.equals("")){
            isFirstOrSecondMerchantSiteNull = true;
        } else if (baseUrl2!=null && alternateUrl2!=null && (baseUrl2.equals("") && alternateUrl2.equals(""))) {
            isFirstOrSecondMerchantSiteNull = true;
        }

        //Test if the first and second set of credentials are the same, based on username
        if (userName2!=null && userName!=null && userName2.equals(userName)){
            isFirstAndSecondMerchantSitesSame=true;
        }

        ApiClient.removeAllMerchantSites();

        if (alternateUrl != null && alternateUrl != "") {
            ApiClient.SetUp(userName, password, secret, alternateUrl);
            ApiClient.SetUp(userName2, password2, secret2, alternateUrl2);

            merchantSite1 = new MerchantSite(userName, password, secret, alternateUrl);
            merchantSite2 = new MerchantSite(userName2, password2, secret2, alternateUrl2);
        } else if ((baseUrl != null) && (baseUrl2 != null)) {
            ApiClient.SetUp(userName, password, secret, baseUrl);
            ApiClient.SetUp(userName2, password2, secret2, baseUrl2);

            merchantSite1 = new MerchantSite(userName, password, secret, baseUrl);
            merchantSite2 = new MerchantSite(userName2, password2, secret2, baseUrl2);
        }
    }

    @Before
    public void testSetup() {
        //If the second set of credentials is null, then this entire class of tests will be ignored
        Assume.assumeFalse(isFirstOrSecondMerchantSiteNull);

        //If the first and second set of credentials are the same, then the entire class of tests will be ignored
        Assume.assumeFalse(isFirstAndSecondMerchantSitesSame);
    }
    //</editor-fold>

    //<editor-fold desc="First Merchant Site Credentials">
    @Test
    public void testAddCase() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        assertNotNull(responseCase.getId());
        assertNotNull(responseCase.getCustomer());
        assertNotNull(responseCase.getCustomer().getId());
        assertEquals("John", responseCase.getCustomer().getFirstName());
        assertEquals("Doe", responseCase.getCustomer().getLastName());
    }


    /******************************Case Tests**************************************/

    @Test
    public void testUpdateCase() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        customer.setFirstName("Joe");
        responseCase.setCustomer(customer);
        Case updateCase = ApiClient.updateCase(responseCase, responseCase.getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Case getCase = ApiClient.getCase(responseCase.getId(), merchantSite1.getUserName());
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
            ApiClient.getCase(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite1.getUserName());
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

            ApiClient.updateCase(kase, kase.getId(), merchantSite1.getUserName());
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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite1.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Fail, decision.getResult());
    }

    @Test
    public void testGetInexistentDecision() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite1.getUserName());
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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        DetailedDecision decision = ApiClient.getDetailedDecision(responseCase.getId(), merchantSite1.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
    }


    @Test
    public void testGetInexistentDetailedDecision() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDetailedDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite1.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }


    /***************************End Detailed Decision Tests******************************/


    /*****************************Customer Object Tests**********************************/
    @Test
    public void testPostCustomer() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Customer returnCustomer = ApiClient.postCustomer(responseCase.getId(), customer, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        customer.setFirstName("Joe");

        Customer responseCustomer = ApiClient.updateCustomer(responseCase.getId(), customer, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Customer responseCustomer = ApiClient.getCustomer(responseCase.getId(), merchantSite1.getUserName());

        assertNotNull(responseCustomer.getId());
        assertEquals("John", responseCustomer.getFirstName());
        assertEquals("Doe", responseCustomer.getLastName());
    }


    @Test
    public void testGetInexistentCustomer() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getCustomer(responseCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        Transaction responseTransaction = ApiClient.postTransaction(responseCase.getId(), transaction, merchantSite1.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testUpdateTransaction() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        transaction.setTotalTransactionValue(20.99);
        Transaction responseTransaction = ApiClient.updateTransaction(responseCase.getId(), transaction, merchantSite1.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(20.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testGetTransaction() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Transaction responseTransaction = ApiClient.getTransaction(responseCase.getId(), merchantSite1.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }


    @Test
    public void testGetInexistentTransaction() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getTransaction(responseCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.ReportedFraud);

        CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus, merchantSite1.getUserName());

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.ReportedFraud, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatus() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        //Case should have a default status of Placed
        CaseStatus returnStatus = ApiClient.getCaseStatus(responseCase.getId(), responseCase.getStatuses().iterator().next().getId(), merchantSite1.getUserName());

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.Placed, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatuses() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.Completed);

        ApiClient.postCaseStatus(responseCase.getId(), caseStatus, merchantSite1.getUserName());

        //Case should have a default status of Placed
        Collection<CaseStatus> returnStatuses = ApiClient.getCaseStatuses(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnStatuses.size() > 1);
    }

    @Test
    public void testGetInexistingCaseStatuses() throws TrustevApiException {
        int responseCode = 200;
        try {
            ApiClient.getCaseStatuses(UUID.randomUUID() + "|" + UUID.randomUUID(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Address address = new Address();
        address.setCity("Cork");

        Address returnAddress = ApiClient.postCustomerAddress(responseCase.getId(), address, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Address newAddress = new Address();
        newAddress.setCity("Dublin");
        Collection<Address> newAddresses = new LinkedList<Address>();
        newAddresses.add(newAddress);
        customer.setAddresses(newAddresses);

        Address returnAddress = ApiClient.updateCustomerAddress(responseCase.getId(), newAddress, responseCase.getCustomer().getAddresses().iterator().next().getId(), merchantSite1.getUserName());

        Address getAddress = ApiClient.getCustomerAddress(responseCase.getId(), returnAddress.getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Address returnAddress = ApiClient.getCustomerAddress(responseCase.getId(), responseCase.getCustomer().getAddresses().iterator().next().getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Collection<Address> returnAddresses = ApiClient.getCustomerAddresses(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnAddresses.size() > 1);
    }

    @Test
    public void testGetInexistingCustomerAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getCustomerAddresses(returningCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Email email = new Email();
        email.setEmailAddress("johndow@gmail.com");

        Email returnEmail = ApiClient.postEmail(responseCase.getId(), email, merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        email.setEmailAddress("joedow@gmail.com");

        Email returnEmail = ApiClient.updateEmail(responseCase.getId(), email, responseCase.getCustomer().getEmail().iterator().next().getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Email returnEmail = ApiClient.getEmail(responseCase.getId(), responseCase.getCustomer().getEmail().iterator().next().getId(), merchantSite1.getUserName());

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

        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Collection<Email> returnEmails = ApiClient.getEmails(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnEmails.size() > 1);
    }


    @Test
    public void testGetInexistingEmailAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getEmails(returningCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Payment payment = new Payment();
        payment.setBinNumber("123456");
        Payment returnPayment = ApiClient.postPayment(responseCase.getId(), payment, merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        payment.setBinNumber("654321");
        Payment returnPayment = ApiClient.updatePayment(responseCase.getId(), payment, responseCase.getPayments().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Payment returnPayment = ApiClient.getPayment(responseCase.getId(), responseCase.getPayments().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Collection<Payment> returnPayments = ApiClient.getPayments(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnPayments.size() > 1);
    }

    @Test
    public void testGetInexistingPayments() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getPayments(returningCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Address returnAddress = ApiClient.postTransactionAddress(responseCase.getId(), address, merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        address.setCity("Dublin");
        Address returnAddress = ApiClient.updateTransactionAddress(responseCase.getId(), address, responseCase.getTransaction().getAddresses().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Address returnAddress = ApiClient.getTransactionAddress(responseCase.getId(), responseCase.getTransaction().getAddresses().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Collection<Address> returnAddresses = ApiClient.getTransactionAddresses(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnAddresses.size() == 1);
    }

    @Test
    public void testGetInexistingTransactionAddresses() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getTransactionAddresses(returningCase.getId(), merchantSite1.getUserName());
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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        TransactionItem returnTransactionItem = ApiClient.postTransactionItem(responseCase.getId(), transactionItem, merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        transactionItem.setItemValue(100.99);
        TransactionItem returnTransactionItem = ApiClient.updateTransactionItem(responseCase.getId(), transactionItem, responseCase.getTransaction().getItems().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        TransactionItem returnTransactionItem = ApiClient.getTransactionItem(responseCase.getId(), responseCase.getTransaction().getItems().iterator().next().getId(), merchantSite1.getUserName());

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
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Collection<TransactionItem> returnTransactionItems = ApiClient.getTransactionItems(responseCase.getId(), merchantSite1.getUserName());

        assertTrue(returnTransactionItems.size() == 2);
    }

    @Test
    public void testGetInexistingTransactionItems() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getTransactionItems(returningCase.getId(), merchantSite1.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of TransactionItem Object Tests*****************************/
    //</editor-fold>

    //<editor-fold desc="Second Merchant Site Credentials">
    //region Second Merchant Site Credentials
    @Test
    public void testAddCaseSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        assertNotNull(responseCase.getId());
        assertNotNull(responseCase.getCustomer());
        assertNotNull(responseCase.getCustomer().getId());
        assertEquals("John", responseCase.getCustomer().getFirstName());
        assertEquals("Doe", responseCase.getCustomer().getLastName());
    }


    /******************************Case Tests**************************************/

    @Test
    public void testUpdateCaseSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        customer.setFirstName("Joe");
        responseCase.setCustomer(customer);
        Case updateCase = ApiClient.updateCase(responseCase, responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(updateCase.getId());
        assertNotNull(updateCase.getCustomer());
        assertNotNull(updateCase.getCustomer().getId());
        assertEquals("Joe", updateCase.getCustomer().getFirstName());
        assertEquals("Doe", updateCase.getCustomer().getLastName());
    }


    @Test
    public void testGetCaseSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Case getCase = ApiClient.getCase(responseCase.getId(), merchantSite2.getUserName());
        assertNotNull(getCase.getId());
        assertNotNull(getCase.getCustomer());
        assertNotNull(getCase.getCustomer().getId());
        assertEquals("John", getCase.getCustomer().getFirstName());
        assertEquals("Doe", getCase.getCustomer().getLastName());
    }


    @Test
    public void testGetCaseIncorrectCaseIdSecondMerchantSite() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getCase(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }


    @Test
    public void testUpdateNonExistingCaseSecondMerchantSite() throws TrustevApiException {

        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Doe");
            kase.setCustomer(customer);

            ApiClient.updateCase(kase, kase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }


    @Test
    public void testDefaultCaseTypePostSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.Default);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        assertEquals(CaseType.Default, responseCase.getCaseType());
    }


    @Test
    public void testAccountCreationCaseTypePostSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAccountNumber(UUID.randomUUID().toString());
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.AccountCreation);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        assertEquals(CaseType.AccountCreation, responseCase.getCaseType());
    }


    @Test
    public void testApplicationCaseTypePostSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        kase.setCaseType(CaseType.Application);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        assertEquals(CaseType.Application, responseCase.getCaseType());
    }

    /******************************End of Case Tests**************************************/


    /******************************Decision Tests*****************************************/


    @Test
    public void testGetDecisionSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
    }


    @Test
    public void testGetDecisionPassSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "pass");
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Pass, decision.getResult());
    }


    @Test
    public void testGetDecisionFlagSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "flag");
        Customer customer = new Customer();
        customer.setFirstName("JohnPass");
        customer.setLastName("DoePass");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Flag, decision.getResult());
    }

    @Test
    public void testGetDecisionFailSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString() + "fail");
        Customer customer = new Customer();
        customer.setFirstName("JohnFail");
        customer.setLastName("DoeFail");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Decision decision = ApiClient.getDecision(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
        assertEquals(DecisionResult.Fail, decision.getResult());
    }

    @Test
    public void testGetInexistentDecisionSecondMerchantSite() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*******************************End Decision Tests******************************/


    /*****************************Detailed Decision Tests***************************/
    @Test
    public void testGetDetailedDecisionSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        DetailedDecision decision = ApiClient.getDetailedDecision(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(decision.getId());
        assertNotNull(decision.getResult());
    }


    @Test
    public void testGetInexistentDetailedDecisionSecondMerchantSite() throws TrustevApiException {

        int responseCode = 200;
        try {
            ApiClient.getDetailedDecision(UUID.randomUUID().toString() + "|" + UUID.randomUUID().toString(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }


    /***************************End Detailed Decision Tests******************************/


    /*****************************Customer Object Tests**********************************/
    @Test
    public void testPostCustomerSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Customer returnCustomer = ApiClient.postCustomer(responseCase.getId(), customer, merchantSite2.getUserName());

        assertNotNull(returnCustomer.getId());
        assertEquals("John", returnCustomer.getFirstName());
        assertEquals("Doe", returnCustomer.getLastName());
    }


    @Test
    public void testUpdateCustomerSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        customer.setFirstName("Joe");

        Customer responseCustomer = ApiClient.updateCustomer(responseCase.getId(), customer, merchantSite2.getUserName());

        assertNotNull(responseCustomer.getId());
        assertEquals("Joe", responseCustomer.getFirstName());
        assertEquals("Doe", responseCustomer.getLastName());
    }

    @Test
    public void testGetCustomerSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Customer responseCustomer = ApiClient.getCustomer(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(responseCustomer.getId());
        assertEquals("John", responseCustomer.getFirstName());
        assertEquals("Doe", responseCustomer.getLastName());
    }


    @Test
    public void testGetInexistentCustomerSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getCustomer(responseCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Customer Object Tests****************************/

    /*****************************Transaction Object Tests********************************/
    @Test
    public void testPostTransactionSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        Transaction responseTransaction = ApiClient.postTransaction(responseCase.getId(), transaction, merchantSite2.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testUpdateTransactionSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        transaction.setTotalTransactionValue(20.99);
        Transaction responseTransaction = ApiClient.updateTransaction(responseCase.getId(), transaction, merchantSite2.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(20.99, responseTransaction.getTotalTransactionValue(), 0);
    }

    @Test
    public void testGetTransactionSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Transaction responseTransaction = ApiClient.getTransaction(responseCase.getId(), merchantSite2.getUserName());

        assertNotNull(responseTransaction.getId());
        assertEquals(10.99, responseTransaction.getTotalTransactionValue(), 0);
    }


    @Test
    public void testGetInexistentTransactionSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getTransaction(responseCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Transaction Object Tests*************************/

    /*****************************Status Object Tests*************************************/
    @Test
    public void testPostCaseStatusSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.ReportedFraud);

        CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus, merchantSite2.getUserName());

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.ReportedFraud, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatusSecondMerchantSite() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        //Case should have a default status of Placed
        CaseStatus returnStatus = ApiClient.getCaseStatus(responseCase.getId(), responseCase.getStatuses().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnStatus.getId());
        assertEquals(CaseStatusType.Placed, returnStatus.getStatus());
    }

    @Test
    public void testGetCaseStatusesSecondMerchantSite() throws TrustevApiException {


        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.Completed);

        ApiClient.postCaseStatus(responseCase.getId(), caseStatus, merchantSite2.getUserName());

        //Case should have a default status of Placed
        Collection<CaseStatus> returnStatuses = ApiClient.getCaseStatuses(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnStatuses.size() > 1);
    }

    @Test
    public void testGetInexistingCaseStatusesSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            ApiClient.getCaseStatuses(UUID.randomUUID() + "|" + UUID.randomUUID(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }

    /*****************************End of Transaction Object Tests*************************/

    /*****************************Customer Address Object Tests***************************/
    @Test
    public void testPostCustomerAddressSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Address address = new Address();
        address.setCity("Cork");

        Address returnAddress = ApiClient.postCustomerAddress(responseCase.getId(), address, merchantSite2.getUserName());

        assertNotNull(returnAddress.getId());
    }

    @Test
    public void testUpdateCustomerAddressSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Address newAddress = new Address();
        newAddress.setCity("Dublin");
        Collection<Address> newAddresses = new LinkedList<Address>();
        newAddresses.add(newAddress);
        customer.setAddresses(newAddresses);

        Address returnAddress = ApiClient.updateCustomerAddress(responseCase.getId(), newAddress, responseCase.getCustomer().getAddresses().iterator().next().getId(), merchantSite2.getUserName());

        Address getAddress = ApiClient.getCustomerAddress(responseCase.getId(), returnAddress.getId(), merchantSite2.getUserName());

        assertNotNull(getAddress.getId());
        assertEquals("Dublin", getAddress.getCity());
    }

    @Test
    public void testGetCustomerAddressSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Address returnAddress = ApiClient.getCustomerAddress(responseCase.getId(), responseCase.getCustomer().getAddresses().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnAddress.getId());
        assertEquals("Cork", returnAddress.getCity());
    }

    @Test
    public void testGetCustomerAddressesSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Collection<Address> returnAddresses = ApiClient.getCustomerAddresses(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnAddresses.size() > 1);
    }

    @Test
    public void testGetInexistingCustomerAddressesSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getCustomerAddresses(returningCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of CustomerAddress Object Tests*************************/

    /*****************************Customer EmailAddress Object Tests**************************/
    @Test
    public void testPostEmailSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Email email = new Email();
        email.setEmailAddress("johndow@gmail.com");

        Email returnEmail = ApiClient.postEmail(responseCase.getId(), email, merchantSite2.getUserName());

        assertNotNull(returnEmail.getId());
    }

    @Test
    public void testUpdateEmailSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        email.setEmailAddress("joedow@gmail.com");

        Email returnEmail = ApiClient.updateEmail(responseCase.getId(), email, responseCase.getCustomer().getEmail().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnEmail.getId());
        assertEquals("joedow@gmail.com", returnEmail.getEmailAddress());
    }

    @Test
    public void testGetEmailSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Email returnEmail = ApiClient.getEmail(responseCase.getId(), responseCase.getCustomer().getEmail().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnEmail.getId());
        assertEquals("johndow@gmail.com", returnEmail.getEmailAddress());
    }

    @Test
    public void testGetEmailsSecondMerchantSite() throws TrustevApiException {

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

        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Collection<Email> returnEmails = ApiClient.getEmails(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnEmails.size() > 1);
    }


    @Test
    public void testGetInexistingEmailAddressesSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getEmails(returningCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of Customer EmailAddress Object Tests********************/

    /*****************************Payment Object Tests*****************************************/
    @Test
    public void testPostPaymentSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Payment payment = new Payment();
        payment.setBinNumber("123456");
        Payment returnPayment = ApiClient.postPayment(responseCase.getId(), payment, merchantSite2.getUserName());

        assertNotNull(returnPayment.getId());
    }

    @Test
    public void testUpdatePaymentSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Payment> payments = new LinkedList<Payment>();
        Payment payment = new Payment();
        payment.setBinNumber("123456");
        payments.add(payment);
        kase.setPayments(payments);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        payment.setBinNumber("654321");
        Payment returnPayment = ApiClient.updatePayment(responseCase.getId(), payment, responseCase.getPayments().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnPayment.getId());
        assertEquals("654321", returnPayment.getBinNumber());
    }

    @Test
    public void testGetPaymentSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Payment> payments = new LinkedList<Payment>();
        Payment payment = new Payment();
        payment.setBinNumber("123456");
        payments.add(payment);
        kase.setPayments(payments);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Payment returnPayment = ApiClient.getPayment(responseCase.getId(), responseCase.getPayments().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnPayment.getId());
    }

    @Test
    public void testGetPaymentsSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Payment> payments = new LinkedList<Payment>();
        Payment payment = new Payment();
        payment.setBinNumber("123456");
        Payment payment2 = new Payment();
        payment2.setBinNumber("654321");
        payments.add(payment);
        payments.add(payment2);
        kase.setPayments(payments);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Collection<Payment> returnPayments = ApiClient.getPayments(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnPayments.size() > 1);
    }

    @Test
    public void testGetInexistingPaymentsSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getPayments(returningCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of Payment Object Tests*********************************/

    /*****************************TransactionAddress Object Tests*****************************/
    @Test
    public void testPostTransactionAddressSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Address> addresses = new LinkedList<Address>();
        Transaction transaction = new Transaction();
        Address address = new Address();
        address.setCity("Cork");
        addresses.add(address);
        transaction.setAddresses(addresses);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Address returnAddress = ApiClient.postTransactionAddress(responseCase.getId(), address, merchantSite2.getUserName());

        assertNotNull(returnAddress.getId());
        assertEquals("Cork", returnAddress.getCity());
    }

    @Test
    public void testUpdateTransactionAddressSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Address> addresses = new LinkedList<Address>();
        Transaction transaction = new Transaction();
        Address address = new Address();
        address.setCity("Cork");
        addresses.add(address);
        transaction.setAddresses(addresses);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        address.setCity("Dublin");
        Address returnAddress = ApiClient.updateTransactionAddress(responseCase.getId(), address, responseCase.getTransaction().getAddresses().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnAddress.getId());
        assertEquals("Dublin", returnAddress.getCity());
    }

    @Test
    public void testGetTransactionAddressSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Address> addresses = new LinkedList<Address>();
        Transaction transaction = new Transaction();
        Address address = new Address();
        address.setCity("Cork");
        addresses.add(address);
        transaction.setAddresses(addresses);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Address returnAddress = ApiClient.getTransactionAddress(responseCase.getId(), responseCase.getTransaction().getAddresses().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnAddress.getId());
    }

    @Test
    public void testGetTransactionAddressesesSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<Address> addresses = new LinkedList<Address>();
        Transaction transaction = new Transaction();
        Address address = new Address();
        address.setCity("Cork");
        addresses.add(address);
        transaction.setAddresses(addresses);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Collection<Address> returnAddresses = ApiClient.getTransactionAddresses(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnAddresses.size() == 1);
    }

    @Test
    public void testGetInexistingTransactionAddressesSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getTransactionAddresses(returningCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(400, responseCode);
    }

    /*****************************End of TransactionAddress Object Tests**************************/

    /*****************************TransactionItem Object Tests************************************/

    @Test
    public void testPostTransactionItemSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
        Transaction transaction = new Transaction();
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setItemValue(10.99);
        transactionItems.add(transactionItem);
        transaction.setItems(transactionItems);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        TransactionItem returnTransactionItem = ApiClient.postTransactionItem(responseCase.getId(), transactionItem, merchantSite2.getUserName());

        assertNotNull(returnTransactionItem.getId());
    }

    @Test
    public void testUpdateTransactionItemSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
        Transaction transaction = new Transaction();
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setItemValue(10.99);
        transactionItems.add(transactionItem);
        transaction.setItems(transactionItems);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        transactionItem.setItemValue(100.99);
        TransactionItem returnTransactionItem = ApiClient.updateTransactionItem(responseCase.getId(), transactionItem, responseCase.getTransaction().getItems().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnTransactionItem.getId());
    }

    @Test
    public void testGetTransactionItemSecondMerchantSite() throws TrustevApiException {

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Collection<TransactionItem> transactionItems = new LinkedList<TransactionItem>();
        Transaction transaction = new Transaction();
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setItemValue(10.99);
        transactionItems.add(transactionItem);
        transaction.setItems(transactionItems);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        TransactionItem returnTransactionItem = ApiClient.getTransactionItem(responseCase.getId(), responseCase.getTransaction().getItems().iterator().next().getId(), merchantSite2.getUserName());

        assertNotNull(returnTransactionItem.getId());
    }

    @Test
    public void testGetTransactionItemsSecondMerchantSite() throws TrustevApiException {

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
        Case responseCase = ApiClient.postCase(kase, merchantSite2.getUserName());

        Collection<TransactionItem> returnTransactionItems = ApiClient.getTransactionItems(responseCase.getId(), merchantSite2.getUserName());

        assertTrue(returnTransactionItems.size() == 2);
    }

    @Test
    public void testGetInexistingTransactionItemsSecondMerchantSite() throws TrustevApiException {
        int responseCode = 200;
        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case returningCase = ApiClient.postCase(kase, merchantSite2.getUserName());

            ApiClient.getTransactionItems(returningCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(404, responseCode);
    }
    /*****************************End of TransactionItem Object Tests*****************************/
    //endregion
    //</editor-fold>

    //<editor-fold desc="First and Second Merchant Site Credential conflicts">

    /*****************************Transaction Object Tests********************************/
    @Test
    public void testPostTransactionConflict() throws TrustevApiException {

        int responseCode = 200;

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        try {
            Transaction responseTransaction = ApiClient.postTransaction(responseCase.getId(), transaction, merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }

        assertEquals(401, responseCode);
    }

    @Test
    public void testUpdateTransactionConflict() throws TrustevApiException {

        int responseCode = 200;

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        transaction.setTotalTransactionValue(20.99);

        try {
            Transaction responseTransaction = ApiClient.updateTransaction(responseCase.getId(), transaction, merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }

        assertEquals(401, responseCode);
    }

    @Test
    public void testGetTransactionConflict() throws TrustevApiException {
        int responseCode = 200;

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Transaction transaction = new Transaction();
        transaction.setTotalTransactionValue(10.99);
        kase.setTransaction(transaction);
        Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

        try {
            Transaction responseTransaction = ApiClient.getTransaction(responseCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }

        assertEquals(401, responseCode);
    }


    @Test
    public void testGetInexistentTransactionConflict() throws TrustevApiException {

        int responseCode = 200;

        try {
            Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
            Case responseCase = ApiClient.postCase(kase, merchantSite1.getUserName());

            ApiClient.getTransaction(responseCase.getId(), merchantSite2.getUserName());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }
        assertEquals(401, responseCode);
    }

    /*****************************End of Transaction Object Tests*************************/
    //</editor-fold>

    //<editor-fold desc="Single Registered Merchant Site Post - Second Merchant Site added between Posts Conflicts">
    @Test
    public void testPostCaseStatus_AddMerchantSiteBeforePost_500() throws TrustevApiException {
        int responseCode = 200;
        ApiClient.removeMerchantSite(merchantSite2.getUserName());

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Case responseCase = ApiClient.postCase(kase);

        ApiClient.SetUp(merchantSite2.getUserName(), merchantSite2.getPassword(), merchantSite2.getSecret(), merchantSite2.getBaseUrlString());

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setComment("Testing Status!!");
        caseStatus.setStatus(CaseStatusType.ReportedFraud);

        try {
            CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }

        assertEquals(400, responseCode);
    }

    /******************************Decision Tests*****************************************/


    @Test
    public void getDecision_secondMerchantSiteAddedMid_500() throws TrustevApiException {
        int responseCode = 200;

        ApiClient.removeMerchantSite(merchantSite2.getUserName());

        Case kase = new Case(UUID.randomUUID(), UUID.randomUUID().toString());
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        kase.setCustomer(customer);
        Case responseCase = ApiClient.postCase(kase);

        ApiClient.SetUp(merchantSite2.getUserName(), merchantSite2.getPassword(), merchantSite2.getSecret(), merchantSite2.getBaseUrlString());

        try {
            Decision decision = ApiClient.getDecision(responseCase.getId());
        } catch (TrustevApiException ex) {
            responseCode = ex.responseCode;
        }

        assertEquals(400, responseCode);
    }

    /*******************************End Decision Tests******************************/
    //</editor-fold>
}
