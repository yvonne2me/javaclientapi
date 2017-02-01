package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawDataCoordinates
{
	@JsonProperty("IP")
	private String ip;

	@JsonProperty("DeviceCellular")
	private String deviceCellular;

	@JsonProperty("DeviceBrowser;")
	private String deviceBrowser;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceCellular() {
		return deviceCellular;
	}

	public void setDeviceCellular(String deviceCellular) {
		this.deviceCellular = deviceCellular;
	}

	public String getDeviceBrowser() {
		return deviceBrowser;
	}

	public void setDeviceBrowser(String deviceBrowser) {
		this.deviceBrowser = deviceBrowser;
	}
}