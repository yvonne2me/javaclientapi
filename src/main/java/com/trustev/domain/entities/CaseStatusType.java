package com.trustev.domain.entities;

public enum CaseStatusType {
	Completed(0), RejectedFraud(1), RejectedAuthFailure(2), RejectedSuspicious(3), Cancelled(4), ChargebackFraud(5), ChargebackOther(6), Refunded(7), Placed(8), OnHoldReview(9), ReportedFraud(12);
	
	private int numVal;
	
	CaseStatusType(int numVal){
		this.numVal = numVal;
	}
	
	public int getNumVal() {
        return numVal;
    }
}
