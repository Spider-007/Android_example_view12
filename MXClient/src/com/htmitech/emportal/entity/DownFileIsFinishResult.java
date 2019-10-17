package com.htmitech.emportal.entity;


public class DownFileIsFinishResult {

	private boolean IsFinished;
	private DocFileInfo DocFileInfoResult;
	private String ErroMsg;

	public boolean isIsFinished() {
		return IsFinished;
	}

	public void setIsFinished(boolean isFinished) {
		IsFinished = isFinished;
	}

	public DocFileInfo getDocFileInfoResult() {
		return DocFileInfoResult;
	}

	public void setDocFileInfoResult(DocFileInfo docFileInfoResult) {
		DocFileInfoResult = docFileInfoResult;
	}

	public String getErroMsg() {
		return ErroMsg;
	}

	public void setErroMsg(String erroMsg) {
		ErroMsg = erroMsg;
	}

}
