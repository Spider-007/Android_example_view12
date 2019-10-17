package com.htmitech.zhiwen;


import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;


@TargetApi(Build.VERSION_CODES.M)
public class MyAuthCallback extends FingerprintManager.AuthenticationCallback {
	
	
	private Handler handler = null;
	
	public MyAuthCallback(Handler handler) {
        super();

        this.handler = handler;
    }
	

	@Override
	public void onAuthenticationError(int errorCode, CharSequence errString) {
		// TODO Auto-generated method stub
		super.onAuthenticationError(errorCode, errString);
		 if (handler != null) {
	            handler.obtainMessage(ZWUtils.MSG_AUTH_ERROR, errorCode, 0).sendToTarget();
	        }
	}
	
	
	@Override
	public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
		// TODO Auto-generated method stub
		super.onAuthenticationHelp(helpCode, helpString);
		 if (handler != null) {
	            handler.obtainMessage(ZWUtils.MSG_AUTH_HELP, helpCode, 0).sendToTarget();
	        }
	}
	
	
	
	@Override
	public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
		// TODO Auto-generated method stub
		super.onAuthenticationSucceeded(result);
		 if (handler != null) {
	            handler.obtainMessage(ZWUtils.MSG_AUTH_SUCCESS).sendToTarget();
	        }
	}
	
	
	
	@Override
	public void onAuthenticationFailed() {
		// TODO Auto-generated method stub
		super.onAuthenticationFailed();
		 if (handler != null) {
	            handler.obtainMessage(ZWUtils.MSG_AUTH_FAILED).sendToTarget();
	        }
	}
	
	
	
	
	
	
	
	
}
