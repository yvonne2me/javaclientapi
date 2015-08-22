package com.trustev.domain.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

abstract class BaseObject {

	@JsonProperty("Id")
	String id = null;
	/**
	 * 
	 * @return This is the Object Id. The Id is returned once a Trustev Case has
	 *         been created. It is required when getting a Trustev Decision on a
	 *         Trustev Case, when updating a Case Status, and anytime you wish
	 *         to update Trustev Case information.
	 */
	public String getId() {
		return this.id;
	}

	static String FormatTimeStamp(Date d) {
		return (d == null) ? null : new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(d);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseObject))
			return false;
		if (obj == this)
			return true;
		BaseObject rhs = (BaseObject) obj;
		if (this.getId() == null)
			return false;
		return this.getId().equals(rhs.getId());
	}
}
