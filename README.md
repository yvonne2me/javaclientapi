<img src="Assets/Images/TrustevLogo.png"></img>
#Trustev-Java
Java wrapper for Trustev API

##Getting Started
- If you are not familiar with Trustev, start with our [Developer Portal](http://developers.trustev.com/).
- Check out our [API Documentation](http://developers.trustev.com/#apioverview).

##Installation
- For Maven, it is available in the Central Repository therefore just add the following dependency to your pom.xml file
```xml
  <dependency>
	<groupId>com.trustev</groupId>
  	<artifactId>trustev-java</artifactId>
  	<version>2.0.0</version>
  </dependency>
```


##SSL Certification Import
The Trustev API https://app.trustev.com uses a wildcard certificate.  Languages such as Java and PHP are more strict with SSL certifications and do not by default validate wildcard certificates.
To get around this you will need to manually import the certficate as a trusted certificate into the JVM.  To do this download the certificate from Firefox or another browser.

In Firefox, navigate to https://app.trustev.com/Help
Click on the padlock in the address bar
Click on "More Information" button
Click on "Security" Tab
Click on "View Certificate" button
Click on "Details" tab
Click on "Export" button
In the Save as dialog, save the file under the name C:\Trustev.crt using type "X.509 Certificate (PEM)"
Open a command line window in the folder where the Trustev.crt file was saved
Run the following command "keytool -import -alias trustev -file Trustev.cer -keystore Trustev.jks"
Follow the prompts, you will be prompted to enter a password.  For the purposes of this example assume the password is changeme
Update the startup JVM args of you java environment to use the keystore created.  The following JVM arguments should be passed -Djavax.net.ssl.trustStore=C:\Trustev.jks -Djavax.net.ssl.trustStorePassword=changeme


##Usage

#### Supply API Keys
The first step is to get an API Tokens, refer to http://developers.trustev.com/ for information on how to do that.
Once you have API Keys you will need to create a java properties file on your class path called trustev.properties the file
should contain the following keys
```java

		username=test-http://app.testintegration.com
		password=4c80addf828f49d48704082bf4f0279f
		sharedsecret=1f9e91af029a48fabcfd9e2828f31b80
```

Where username, password and sharedsecret are the values in the dashboard.  Each request will also require a SessionId which is obtained by including the trustev.js file on your webpage and
passed to the backend Servlet or Controller.

#### Creating a Case and Making a Decision
Once you have supplied the trustev.properties file you can make a decision by instantiating a Case object, setting the relevant values and calling MakeDecision

```java
		String caseNumber = "MyCase12345";		// merchant specific Case number, 
		UUID sessionId = UUID.fromString("f9b21183-a88e-4454-992a-febe98658384");  // SessionId created by trustev.js inclusion
		
		// instantiate a Case object
		Case myCase = new Case(sessionId);
		
		// set initial values
		myCase.setCaseNumber(caseNumber);
		myCase.setTimestamp(new Date());
		Customer customer = new Customer();
		customer.setFirstName("Gene");
		customer.setLastName("Geniune");
		myCase.setCustomer(customer);
		// set other values as needed, the Case object reflects exactly the objects in http://app.trustev.com/Help
		
		// get the decision on the case
		Decision decision = myCase.MakeDecision();
		if (decision.getResult() == DecisionResult.Pass) {
			// decision result was a pass, process the order
		}
		else {
			// decision result was not a pass, order is fraud!
		}
```
