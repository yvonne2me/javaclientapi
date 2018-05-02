package com.trustev.domain.entities;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.deser.StdDeserializer;

@JsonDeserialize(using = CaseType.CaseTypeDeserializer.class)
public enum CaseType {

	Default(0), AccountCreation(2), Application(3), ADR(4);
	
	private int numVal;
	
	CaseType(int numVal)
	{
		this.numVal = numVal;
	}
	
	public int getNumVal()
	{
		return this.numVal;
	}

	public static class CaseTypeDeserializer extends StdDeserializer<CaseType> {
		public CaseTypeDeserializer() {
			super(CaseType.class);
		}

		@Override
		public CaseType deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException{
			final JsonNode jsonNode = jp.readValueAsTree();

			CaseType type;
			int valueNum = jsonNode.asInt();

			switch(valueNum)
			{
			case 0:
				type = CaseType.Default;
				break;
			case 2:
				type = CaseType.AccountCreation;
				break;
			case 3:
				type = CaseType.Application;
				break;
			case 4:
				type = CaseType.ADR;
				break;
			default:
				return null;
			}

			return type;
		}
	}
}
