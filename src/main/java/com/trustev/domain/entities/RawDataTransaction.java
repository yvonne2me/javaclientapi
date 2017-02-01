package com.trustev.domain.entities;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawDataTransaction
{
	@JsonProperty("Emails")
	private Collection<RawDataEmail> emails;

	public Collection<RawDataEmail> getEmails() {
		return emails;
	}

	public void setEmails(Collection<RawDataEmail> emails) {
		this.emails = emails;
	}
}