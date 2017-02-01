package com.trustev.domain.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputedDataEmail
{
	@JsonProperty("IsDisposable")
	private boolean isDisposable;

	@JsonProperty("IsDomainNotAllowed")
	private boolean isDomainNotAllowed;

	@JsonProperty("IsUserNameNotAllowed")
	private boolean isUserNameNotAllowed;

	@JsonProperty("ContainsDomainIssue")
	private boolean containsDomainIssue;

	@JsonProperty("ContainsMailboxIssue")
	private boolean containsMailboxIssue;

	@JsonProperty("ContainsSyntaxIssue")
	private boolean containsSyntaxIssue;

	public boolean isDisposable() {
		return isDisposable;
	}

	public void setDisposable(boolean isDisposable) {
		this.isDisposable = isDisposable;
	}

	public boolean isDomainNotAllowed() {
		return isDomainNotAllowed;
	}

	public void setDomainNotAllowed(boolean isDomainNotAllowed) {
		this.isDomainNotAllowed = isDomainNotAllowed;
	}

	public boolean isUserNameNotAllowed() {
		return isUserNameNotAllowed;
	}

	public void setUserNameNotAllowed(boolean isUserNameNotAllowed) {
		this.isUserNameNotAllowed = isUserNameNotAllowed;
	}

	public boolean isContainsDomainIssue() {
		return containsDomainIssue;
	}

	public void setContainsDomainIssue(boolean containsDomainIssue) {
		this.containsDomainIssue = containsDomainIssue;
	}

	public boolean isContainsMailboxIssue() {
		return containsMailboxIssue;
	}

	public void setContainsMailboxIssue(boolean containsMailboxIssue) {
		this.containsMailboxIssue = containsMailboxIssue;
	}

	public boolean isContainsSyntaxIssue() {
		return containsSyntaxIssue;
	}

	public void setContainsSyntaxIssue(boolean containsSyntaxIssue) {
		this.containsSyntaxIssue = containsSyntaxIssue;
	}
}