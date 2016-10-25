![alt text](https://app.trustev.com/assets/img/apple-icon-144.png)

#Trustev Java Libary
- If you are not familiar with Trustev, start with our [Developer Portal](http://www.trustev.com/developers).
- Check out our [API Documentation](http://www.trustev.com/developers#apioverview).
- If you would like to get some Test API Keys to begin Integrating, please contact our Integration Tema: integrate@trustev.com

##Requirements
- Java 1.6 +

##Installation
####Maven Users
- For Maven, it is available in the Central Repository, therefore just add the following dependency to your pom.xml file.
```xml
  <dependency>
	<groupId>com.trustev</groupId>
  	<artifactId>trustev-java</artifactId>
  	<version>2.0.5</version>
  </dependency>
```

####Others
- You could also download our solution, build it and simply include the jar files as you need them.
- Our library can also be used as an example to inspire your own Integration to the Trustev Platform.

## Usage
   The Trustev API has been designed to allow users complete control over what information they are sending to us, while still ensuring that the Trustev Integration can be done in a couple of simple steps.

###SSL Certification Import
The Trustev API https://app.trustev.com uses a wildcard certificate.  Languages such as Java and PHP are more strict with SSL certifications and do not by default validate wildcard certificates.
To get around this you will need to manually import the certficate as a trusted certificate into the JVM.  To do this download the certificate from Firefox or another browser.

- In Firefox, navigate to https://app.trustev.com/Help
- Click on the padlock in the address bar
- Click on "More Information" button
- Click on "Security" Tab
- Click on "View Certificate" button
- Click on "Details" tab
- Click on "Export" button
- In the Save as dialog, save the file under the name C:\Trustev.crt using type "X.509 Certificate (PEM)"
- Open a command line window in the folder where the Trustev.crt file was saved
- Run the following command "keytool -import -alias trustev -file Trustev.cer -keystore Trustev.jks"
- Follow the prompts, you will be prompted to enter a password.  For the purposes of this example assume the password is changeme
- Update the startup JVM args of you java environment to use the keystore created.  The following JVM arguments should be passed -Djavax.net.ssl.trustStore=C:\Trustev.jks -Djavax.net.ssl.trustStorePassword=changeme

### Simple Trustev Integration
This is a simple version of the Trustev Integration and it involves 4 simple steps.

```java

// 1. Set-Up the Trustev Api Client with your user credentials
ApiClient.SetUp(userName, password, secret, baseUrl);


// 2. Create your case and POST this Case to the Trustev API.
// You will need two bits of information for this setup
// 		SessionId : This is the SessionId that you have received from the Trustev JavaScript (Trustev.js)
//					and transferred server-side.
// 		CaseNumber : This is a number that you use to uniquely identify this Case - we recommend using your internal Order Number for the Case Number. 
					It must be unique per Case request.

Case kase = new Case(sessionId, caseNumber);

// Now add any further information you have. The more you give us, the more accurate 
// our decisions.
Customer customer = new Customer();
customer.setFirstName("John");
customer.setLastName("Doe");
kase.setCustomer(customer);


// Post this Case to the Trustev Api
Case returnCase = ApiClient.postCase(kase);


// 3. You can now get your Decision from Trustev based on the Case you have given us
Decision decision = ApiClient.getDecision(returnCase.getId());


// 4. Now it's up to you what to do with our Decision, and then updating the Case Status with what the order outcome was.
CaseStatus caseStatus = new CaseStatus();
caseStatus.setComment("Order Completed Successfully");
caseStatus.setStatus(CaseStatusType.Completed);
       
CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);

```

#### Optional Integration Steps
We also provide detailed API endpoints for updating specific parts of your Case. These steps can be used where use cases require. See below for some examples.

##### Example : Adding a Customer

```java

// 1. Set-Up the Trustev Api Client with your user credentials
ApiClient.SetUp(userName, password, secret, baseUrl);


// 2. Create your Case.
// You will need two bits of information for this step
// 		SessionId : This is the SessionId that you have received from the Trustev JavaScript (Trustev.js)
//					and transferred server-side.
// 		CaseNumber : This is a number that you use to uniquely identify this Case - we recommend using your internal Order Number for the Case Number. 
					It must be unique per Case request.

Case kase = new Case(sessionId, caseNumber);

// 3. Post this Case to the Trustev Api
Case returnCase = ApiClient.postCase(kase);


// 4. You may now want to add a Customer to the Case you have already added.
//    First let's create the customer.
Customer customer = new Customer();
customer.setFirstName("John");
customer.setLastName("Doe");

//  Now we can go ahead and add the Customer to the Case we added earlier.
Customer returnCustomer = ApiClient.postCustomer(returnCase.getId(), customer);


// 5. You can now continue as normal and get the Decision of this Case including
//    the new Customer you have added
Decision decision = ApiClient.getDecision(returnCase.getId());


// 6. Now it's up to you what to do with our Decision, and then updating the Case Status with what the order outcome was.
CaseStatus caseStatus = new CaseStatus();
caseStatus.setComment("Order Completed Successfully");
caseStatus.setStatus(CaseStatusType.Completed);
       
CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);

```

##### Example : Updating a Transaction

```java

// 1. Set-Up the Trustev Api Client with your user credentials
ApiClient.SetUp(userName, password, secret, baseUrl);


// 2. Create your Case.
// You will need two bits of information for this setp
// 		SessionId : This is the SessionId that you have received from the Trustev JavaScript (Trustev.js)
//					and transferred server-side.
// 		CaseNumber : This is a number that you use to uniquely identify this Case - we recommend using your internal Order Number for the Case Number. 
					It must be unique per Case request.

Case kase = new Case(sessionId, caseNumber);
Transaction transaction = new Transaction();
transaction.setCurrency("USD");
transaction.setTotalTransactionValue(10);
kase.setTransaction(transaction);

// 3. Post this Case to the Trustev Api
Case returnCase = ApiClient.postCase(kase);


// 4. Now, say the value of this Transaction changes,
//	  We provide the functionality to update the Transaction you have already added.
//	  Just rebuild the Transaction again with the new information
Transaction transaction = new Transaction();
transaction.setCurrency("USD");
transaction.setTotalTransactionValue(2000);

//  Now we can go ahead and add the Transaction to the Case we created earlier.
Transaction returnTransaction = ApiClient.updateTransaction(returnCase.getId(), transaction);


// 5. You can now continue as normal and get the Decision of this Case including
//    the updated Transaction you have added.
Decision decision = ApiClient.getDecision(returnCase.getId());


// Now it's up to you what to do with our Decision, and then updating the Case Status with what the order outcome was.
CaseStatus caseStatus = new CaseStatus();
caseStatus.setComment("Order Completed Successfully");
caseStatus.setStatus(CaseStatusType.Completed);
       
CaseStatus returnStatus = ApiClient.postCaseStatus(responseCase.getId(), caseStatus);

```
We provide similar functions i.e. Post (POST), Update (PUT) and Get (GET) for every Sub Entity of the Case Object.