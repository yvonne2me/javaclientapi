package com.trustev.domain.entities;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.deser.StdDeserializer;

@JsonDeserialize(using = CaseStatusType.CaseStatusTypeDeserializer.class)
public enum CaseStatusType {
	Completed(0), RejectedFraud(1), RejectedAuthFailure(2), RejectedSuspicious(3), Cancelled(4), ChargebackFraud(5), ChargebackOther(6), Refunded(7), Placed(8), OnHoldReview(9), ReportedFraud(12);
	
	private int numVal;
	
	CaseStatusType(int numVal){
		this.numVal = numVal;
	}
	
	public int getNumVal() {
        return numVal;
    }
	
	public static class CaseStatusTypeDeserializer extends StdDeserializer<CaseStatusType> {
		public CaseStatusTypeDeserializer() {
			super(CaseStatusType.class);
		}

		@Override
		public CaseStatusType deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException{
			final JsonNode jsonNode = jp.readValueAsTree();

			CaseStatusType type;
			int valueNum = jsonNode.asInt();

			switch(valueNum)
			{
			case 0:
				type = CaseStatusType.Completed;
				break;
			case 2:
				type = CaseStatusType.RejectedFraud;
				break;
			case 3:
				type = CaseStatusType.RejectedAuthFailure;
				break;
			case 4:
				type = CaseStatusType.Cancelled;
				break;
			case 5:
				type = CaseStatusType.ChargebackFraud;
				break;
			case 6:
				type = CaseStatusType.ChargebackOther;
				break;
			case 7:
				type = CaseStatusType.Refunded;
				break;
			case 8:
				type = CaseStatusType.Placed;
				break;
			case 9:
				type = CaseStatusType.OnHoldReview;
				break;
			case 12:
				type = CaseStatusType.ReportedFraud;
				break;
			default:
				return null;
			}

			return type;
		}

	}
}
