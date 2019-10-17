package com.htmitech.emportal.ui.daiban.data.shortoucs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

public class ShortOucsEntity {

	private Data data;

	private Status status;

	public void parseJson(String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);
		JSONObject dataJsonObject = jsonObject.optJSONObject("data");
		JSONObject statusJsonObject = jsonObject.optJSONObject("status");
		data = new Data();
		data.parseJson(dataJsonObject);
		status = new Status();
		status.parseJson(statusJsonObject);
	}

	/**
	 * 
	 * @return The data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 *            The data
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * 
	 * @return The status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(data).append(status).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ShortOucsEntity) == false) {
			return false;
		}
		ShortOucsEntity rhs = ((ShortOucsEntity) other);
		return new EqualsBuilder().append(data, rhs.data)
				.append(status, rhs.status).isEquals();
	}

}
