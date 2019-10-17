package com.htmitech.commonx.util;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class CodeDetectUtil {
	
	private static final boolean DEVELOPER_MODE = false;
	
	@SuppressLint("NewApi")
	public static void  strictMode() {
		
		if (DEVELOPER_MODE) {
	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	                 .detectDiskReads()
	                 .detectDiskWrites()
	                 .detectNetwork()   
	                 .penaltyLog()
	                 .penaltyFlashScreen()
	                 .build());
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	         			.detectAll()
	         			.penaltyLog()
	                 .build());
	     }
		
		
	}
	
}
