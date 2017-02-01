package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawDataIPAddress
{
	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("ClientIp")
	private String clientIp;
	
	@JsonProperty("Continent")
	private String continent;
	
	@JsonProperty("Country")
	private String country;
	
	@JsonProperty("CountryCode")
	private String countryCode;
	
	@JsonProperty("State")
	private String state;
	
	@JsonProperty("City")
	private String city;
	
	@JsonProperty("ConnectionType")
	private String connectionType;
	
	@JsonProperty("LineSpeed")
	private String lineSpeed;
	
	@JsonProperty("RoutingType")
	private String routingType;
	
	@JsonProperty("SLD")
	private String sld;
	
	@JsonProperty("TLD")
	private String tld;
	
	@JsonProperty("HostingFacility")
	private String hostingFacility;
	
	@JsonProperty("ProxyType")
	private String proxyType;
	
	@JsonProperty("AnonymizerStatus")
	private String anonymizerStatus;
}
