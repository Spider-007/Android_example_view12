package com.htmitech.htworkflowformpluginnew.entity;

import android.text.TextUtils;

public class DocFileInfo {

	private String fileType;

	private int ByteLength;

	private String FielName;

	private String ModifiedTime;

	private String DownloadURL;

	private String FilePath;

	public String fileCrc32;

	public String downloadPdfUrl;

	public String previewPdfUrl;


	public String url;

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public String getFileCrc32() {
		if(TextUtils.isEmpty(fileCrc32)){
			fileCrc32 = "";
		}
		return fileCrc32;
	}

	public void setFileCrc32(String fileCrc32) {
		this.fileCrc32 = fileCrc32;
	}

	public String getDownloadPdfUrl() {
		return downloadPdfUrl;
	}

	public void setDownloadPdfUrl(String downloadPdfUrl) {
		this.downloadPdfUrl = downloadPdfUrl;
	}

	public String getPreviewPdfUrl() {
		return previewPdfUrl;
	}

	public void setPreviewPdfUrl(String previewPdfUrl) {
		this.previewPdfUrl = previewPdfUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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


	/**
	 * 根据规则进行获取URL路径
	 * @param type
	 * @return
	 */
	public String getUrl(int type) {
		switch (type) {
			case 1:
				if (!TextUtils.isEmpty(getDownloadURL())) {
					url = getDownloadURL();
				} else if (!TextUtils.isEmpty(getDownloadPdfUrl())) {
					url = getDownloadPdfUrl();
				} else {
					url = getPreviewPdfUrl();
				}
				break;
			case 2:

				if (!TextUtils.isEmpty(getDownloadPdfUrl())) {
					url = getDownloadPdfUrl();
				}else if (!TextUtils.isEmpty(getDownloadURL())) {
					url = getDownloadURL();
				} else {
					url = getPreviewPdfUrl();
				}

				break;
			default:
				if (!TextUtils.isEmpty(getPreviewPdfUrl())) {
					url = getPreviewPdfUrl();
				}else if (!TextUtils.isEmpty(getDownloadPdfUrl())) {
					url = getDownloadPdfUrl();
				} else {
					url = getDownloadURL();
				}

				break;
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
