package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawDataBINInformation
{
	@JsonProperty("BIN")
	private String bin;

	@JsonProperty("Brand")
	private String brand;

	@JsonProperty("CountryISO3166_Code2")
	private String countryISO3166_Code2;

	@JsonProperty("CardType")
	private String cardType;

	@JsonProperty("Bank")
	private String bank;

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCountryISO3166_Code2() {
		return countryISO3166_Code2;
	}

	public void setCountryISO3166_Code2(String countryISO3166_Code2) {
		this.countryISO3166_Code2 = countryISO3166_Code2;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
}