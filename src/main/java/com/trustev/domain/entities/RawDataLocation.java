package com.trustev.domain.entities;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class RawDataLocation
{
	@JsonProperty("Coordinates")
	private RawDataCoordinates coordinates;
	
	@JsonProperty("Addresses")
	private Map<String, String> addresses;

	public RawDataCoordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(RawDataCoordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Map<String, String> getAddresses() {
		return addresses;
	}

	public void setAddresses(Map<String, String> addresses) {
		this.addresses = addresses;
	}
}