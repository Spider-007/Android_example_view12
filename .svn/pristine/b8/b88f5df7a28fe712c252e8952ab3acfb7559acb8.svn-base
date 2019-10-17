package com.htmitech.emportal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.htmitech.emportal.db.AuthorInfoDAOImpl;
import com.htmitech.emportal.entity.AuthorResultInfo;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class DownAuthorService extends Service {
	
	public static final int STOP = 0;
	//public static String DOWNPATH = "http://114.112.89.94:8081/swj/api/GetMobileData/SyncUsers?LastSyncTime=" + PreferenceUtils.getLastTime(); ;
	public static String DOWNPATH = null;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String[] dateTime = PreferenceUtils.getLastTime().split(" ");
		DOWNPATH = PreferenceUtils.getOaLoginUrl() + "/" + PreferenceUtils.getApiUrl() + "/api/GetMobileData/SyncUsers?LastSyncTime=" + dateTime[0] + "%20" +dateTime[1]
				+ "&userloginName=" + intent.getStringExtra("LoginName");
		
		new InitThread().start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
				case STOP:
					stopSelf();
				break;	
			}
		}
	};
	
	class InitThread extends Thread {
		
		private String readStream(InputStream is) {
			InputStreamReader isr;
			String result = "";
			try {
				String line = "";
				isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		public void run() {
			HttpURLConnection conn = null;
			try {
				URL url = new URL(DOWNPATH);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");
				int code = 0;
				if ((code = conn.getResponseCode()) == HttpStatus.SC_OK) {
					//解析json
					 String jsonString = readStream(conn.getInputStream());
					 AuthorResultInfo authorRes = new AuthorResultInfo();
					 authorRes.parseJson(jsonString);
					 //存入数据库
					 AuthorInfoDAOImpl authorDao = new AuthorInfoDAOImpl(DownAuthorService.this);
					 authorDao.insertAllAuthor(authorRes.getResult().getSyncUserList());
					 PreferenceUtils.saveLastTime(authorRes.getResult().getLastSysncTime());
					 //mHandler.obtainMessage(STOP).sendToTarget();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 finally {
				stopSelf();
				conn.disconnect();
			}
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}





















