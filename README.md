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
      <artifactId>trustev-integration</artifactId>
      <version>2.0.0</version>
    </dependency>
```

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
		
		// save the case
		myCase.Save()
		
		// get the decision on the case
		Decision decision = myCase.MakeDecision();
		if (decision.getResult() == DecisionResult.Pass) {
			// decision result was a pass, process the order
		}
		else {
			// decision result was not a pass, order is fraud!
		}
```
