package com.trustev.domain.entities;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Detailed decision Raw data object
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RawData{

	@JsonProperty("CaseType")
	private CaseType caseType;

	@JsonProperty("DeviceTag")
	private String deviceTag;

	@JsonProperty("TrustevCustomerId")
	private String trustevCustomerId;

	@JsonProperty("Browser")
	private String browser;

	@JsonProperty("OS")
	private String os;

	@JsonProperty("NavigatorOscpu")
	private String navigatorOsCpu;

	@JsonProperty("NavigatorLanguage")
	private String navigatorLanguage;

	@JsonProperty("NavigatorPlugins")
	private String navigatorPlugins;

	@JsonProperty("ScreenWidth")
	private String screenWidth;

	@JsonProperty("ScreenHeight")
	private String screenHeight;

	@JsonProperty("ScreenColorDepth")
	private String screenColorDepth;

	@JsonProperty("IPAddresses")
	private Collection<RawDataIPAddress> ipAddresses;

	@JsonProperty("BINInformation")
	private RawDataBINInformation binInformation;

	@JsonProperty("Customer")
	private RawDataCustomer customer;

	@JsonProperty("Transaction")
	private RawDataTransaction transaction;

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public String getDeviceTag() {
		return deviceTag;
	}

	public void setDeviceTag(String deviceTag) {
		this.deviceTag = deviceTag;
	}

	public String getTrustevCustomerId() {
		return trustevCustomerId;
	}

	public void setTrustevCustomerId(String trustevCustomerId) {
		this.trustevCustomerId = trustevCustomerId;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getNavigatorOscpu() {
		return navigatorOsCpu;
	}

	public void setNavigatorOscpu(String navigatorOsCpu) {
		this.navigatorOsCpu = navigatorOsCpu;
	}

	public String getNavigatorLanguage() {
		return navigatorLanguage;
	}

	public void setNavigatorLanguage(String navigatorLanguage) {
		this.navigatorLanguage = navigatorLanguage;
	}

	public String getNavigatorPlugins() {
		return navigatorPlugins;
	}

	public void setNavigatorPlugins(String navigatorPlugins) {
		this.navigatorPlugins = navigatorPlugins;
	}

	public String getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(String screenWidth) {
		this.screenWidth = screenWidth;
	}

	public String getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(String screenHeight) {
		this.screenHeight = screenHeight;
	}

	public String getScreenColorDepth() {
		return screenColorDepth;
	}

	public void setScreenColorDepth(String screenColorDepth) {
		this.screenColorDepth = screenColorDepth;
	}

	public Collection<RawDataIPAddress> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(Collection<RawDataIPAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public RawDataBINInformation getBinInformation() {
		return binInformation;
	}

	public void setBinInformation(RawDataBINInformation binInformation) {
		this.binInformation = binInformation;
	}

	public RawDataCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(RawDataCustomer customer) {
		this.customer = customer;
	}

	public RawDataTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(RawDataTransaction transaction) {
		this.transaction = transaction;
	}
}
