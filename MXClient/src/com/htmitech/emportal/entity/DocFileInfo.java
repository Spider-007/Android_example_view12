package com.htmitech.emportal.entity;

public class DocFileInfo {

	private String Type;

	private int ByteLength;

	private String FielName;

	private String ModifiedTime;

	private String DownloadURL;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getByteLength() {
		return ByteLength;
	}

	public void setByteLength(int byteLength) {
		ByteLength = byteLength;
	}

	public String getFielName() {
		return FielName;
	}

	public void setFielName(String fielName) {
		FielName = fielName;
	}

	public String getModifiedTime() {
		return ModifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		ModifiedTime = modifiedTime;
	}

	public String getDownloadURL() {
		return DownloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		DownloadURL = downloadURL;
	}

}
