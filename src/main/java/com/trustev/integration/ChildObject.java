package com.trustev.integration;

abstract class ChildObject<T> extends BaseObject<T>{
	
	public abstract void SaveForCase(String caseId) throws TrustevApiException;
}
