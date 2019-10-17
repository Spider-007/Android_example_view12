package com.htmitech.emportal.ui.daiban.data.shortoucs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

public class Data {

	private String id = "";
	private String title = "";
	private String iconUrl = "";

	public void parseJson(JSONObject json) throws Exception {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(title).append(title)
				.append(iconUrl).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Data) == false) {
			return false;
		}
		Data rhs = ((Data) other);
		return new EqualsBuilder().append(id, rhs.id).append(title, rhs.title)
				.append(iconUrl, rhs.iconUrl).isEquals();
	}

}
