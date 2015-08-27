![alt text](https://app.trustev.com/assets/img/apple-icon-144.png)
#Trustev Java Libary
- If you are not familiar with Trustev, start with our [Developer Portal](http://www.trustev.com/developers).
- Check out our [API Documentation](http://www.trustev.com/developers#apioverview).
- If you would like to get some test keys to begin integrating please contact integrate@trustev.com

##Installation
####Maven Users
- For Maven, it is available in the Central Repository therefore just add the following dependency to your pom.xml file
```xml
  <dependency>
	<groupId>com.trustev</groupId>
  	<artifactId>trustev-java</artifactId>
  	<version>2.0.1</version>
  </dependency>
```

####Others
- You could also download our solution, build it and simply include the jar files as you need them.
- Our library can also be used as an example to inspire your own integration to the Trustev Platform.

## Usage
   The Trustev API has been designed to allow users complete control over what information they are sending to us while still ensuring that integration can be done a couple of simple steps

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