package com.htmitech.ztcustom.zt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ztcustom.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 *  获取SQLiteDatabase
 * @author Tony
 *
 */
public class DBManager {
	private static SQLiteDatabase database;
	public static final String DATABASE_FILENAME = "rdms"; // 这个是DB文件名字
//	public static final String DATABASE_PATH = "/data"
//			+ Environment.getDataDirectory().getAbsolutePath() + "/"
//			+ PACKAGE_NAME; // 获取存储位置地址
	public static String databaseSdcardPath = "mnt/sdcard/fanxin";
	public static SQLiteDatabase openDatabase(Context context) {
		try {
//			String databaseFilename = databaseSdcardPath + "/" + DATABASE_FILENAME+ BookInit.getInstance().getCrrentUserId()+".db";
//			databaseSdcardPath = context.getDatabasePath("htmitech").getPath();
			String databaseFilename = databaseSdcardPath + "/" + DATABASE_FILENAME+".db";
			Log.d("DBManager", databaseFilename);
			File dir = new File(databaseSdcardPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(R.raw.rdms);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			database = SQLiteDatabase.openOrCreateDatabase(databaseFilename,
					null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static SQLiteDatabase getInstance(Context context) {
		if (database == null) {
			database = openDatabase(context);
		}else{
			if(isDBexists(context)){
				return database;
			}else{
				database = openDatabase(context);
			}
		}
		return database;
	}

	public static void repelct(Context context){
		database = null;
	}

	public static boolean isDBexists(Context context){
//		databaseSdcardPath = context.getDatabasePath("htmitech").getPath();
		String databaseFilename = databaseSdcardPath + "/" + DATABASE_FILENAME+".db";
		Log.d("DBManager", databaseFilename);
		File dir = new File(databaseSdcardPath);
		if (dir.exists()) {
			return true;
		}
		return false;
	}
}
