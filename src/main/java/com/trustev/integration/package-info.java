/**
 * Provides the classes necessary to communicate with the Trustev API
 * <p>
 * To communicate use the library, two things are required.  Firstly a file called trustev.properties should be included in classpath.  The properties
 * file will contain the API keys in the following format
 * 
 * <pre>
 * username=yourusername
 * password=yourpasswordhash
 * sharedsecret=yoursharedsecrethash
 * </pre>
 * 
 * Once this properties file in included the Trustev Api can be access through the Case object by instantiating a Case object, populating the values and calling the MakeDecision method
 *
 * <pre>
 * <code>
 * String caseNumber = "MyCase1234" // this case number is chosen by merchant and must be unique
 * UUID sessionId = UUID.fromString("7e740ea2-7e8b-43d8-a3b1-4073ca336e28");  // session id returned from js file
 * Case myCase = new Case(sessionId);
 * myCase.setCaseNumber(caseNumber);
 * myCase.setTimestamp(new Date());
 * Customer customer = new Customer();
 * customer.setFirstName("Gene");
 * customer.setLastName("Geniune");
 * myCase.setCustomer(customer);
 * Decision decision = myCase.MakeDecision();
 * if (decision.getResult() == DecisionResult.Pass) {
 *   // process order
 * }
 * else {
 *   // order is fraud
 * }
 * </code>
 * </pre>
 * 
 */
package com.trustev.integration;