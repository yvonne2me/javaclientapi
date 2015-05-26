package com.trustev.integration;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;

abstract class BaseObject<T> {
	static String apiTokenId;
	static Date apiTokenExpires;

	@JsonProperty("Id")
	String id = null;

	/**
	 * 
	 * @return This is the Object Id. The Id is returned once a Trustev Case has
	 *         been created. It is required when getting a Trustev Decision on a
	 *         Trustev Case, when updating a Case Status, and anytime you wish
	 *         to update Trustev Case information.
	 */
	public String getId() {
		return this.id;
	}

	static String FormatTimeStamp(Date d) {
		return (d == null) ? null : new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(d);
	}

	// method needs to be synchronized so that if one caller is in the process of requesting a new ApiToken
	// the others wait and use the same token.  Rather than having 100 concurrent callers all requesting a new ApiToek
	static synchronized String GetApiTokenId() throws TrustevApiException {
		if (apiTokenExpires != null && apiTokenExpires.after(new Date())) {
			return apiTokenId;
		}
		else {
			apiTokenId = null;
			apiTokenExpires = null;
			ClientResponse rawResponse = callApiMethod("Token", new GetTokenRequest(),"POST");
			GetTokenResponse response = rawResponse
					.getEntity(GetTokenResponse.class);
			apiTokenId = response.getApiToken();
			apiTokenExpires = response.getExpireAt();
			return apiTokenId;
		}
	}

	static ClientResponse callApiMethod(String urlPath, Object requestObject,
			String httpMethod) throws TrustevApiException {
		
		DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
		defaultClientConfig.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(defaultClientConfig);

		WebResource resource = client.resource(
				"https://app.trustev.com/api/v2.0/").path(urlPath);
		Builder resourceBuilder = resource.getRequestBuilder();
		// add the api Token to the header, unless the path starts with Token meaning that we are requesting a token
		if (!urlPath.startsWith("Token")) {
			resourceBuilder = resourceBuilder.header("X-Authorization", String
					.format("%1$s %2$s", GetTokenRequest.userName, GetApiTokenId()));
		}
		resourceBuilder = resourceBuilder.accept(MediaType.APPLICATION_JSON);
		resourceBuilder = resourceBuilder.type(MediaType.APPLICATION_JSON);
		ClientResponse response;
		response = resourceBuilder
				.method(httpMethod, ClientResponse.class, requestObject);

		if (response.getStatus() == 200) {
			return response;
		} else {
			ErrorResponse error = response.getEntity(ErrorResponse.class);
			throw new TrustevApiException(error.Message);
		}
	}

	static Object callApiMethodFor(String urlPath, Object requestObject, Class c,
			String httpMethod) throws TrustevApiException {
		ClientResponse rawResponse = callApiMethod(urlPath, requestObject, httpMethod);
		return rawResponse.getEntity(c);
	}

	T callApiMethodFor(String urlPath, String httpMethod) throws TrustevApiException {
		ClientResponse rawResponse = callApiMethod(urlPath, this, httpMethod);
		return (T) rawResponse.getEntity(getClass());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseObject))
			return false;
		if (obj == this)
			return true;
		BaseObject rhs = (BaseObject) obj;
		if (this.getId() == rhs.getId())
			return true;
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
}
