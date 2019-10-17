package htmitech.com.componentlibrary.entity;


public class DocResultInfo {
	private DocInfoDes Result;
	public int code;
	public String message;
	public String debugMsg;

	public DocInfoDes getResult() {
		if(Result == null){
			Result = new DocInfoDes();
		}
		return Result;
	}

	public void setResult(DocInfoDes result) {
		Result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMsg() {
		return debugMsg;
	}

	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}
}
