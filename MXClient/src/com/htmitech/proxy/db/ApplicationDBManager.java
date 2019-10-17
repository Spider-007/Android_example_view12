package com.htmitech.proxy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.htmitech.addressbook.R;
import com.htmitech.app.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *  获取SQLiteDatabase 应用中心的db
 * @author Tony
 *
 */
public class ApplicationDBManager {
	private static SQLiteDatabase database;
	public static final String DATABASE_FILENAME = "HTMI_DB"; // 这个是DB文件名字
	public static String databaseSdcardPath;
	public static SQLiteDatabase openDatabase(Context context) {
		try {
			databaseSdcardPath = context.getDatabasePath("htmitech").getPath();
			String databaseFilename = databaseSdcardPath + "/" + DATABASE_FILENAME+".db";
			Log.d("DBManager",databaseFilename);
			File dir = new File(databaseSdcardPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(
						R.raw.htmitech);
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
		}
		return database;
	}

	public static void repelct(Context context){
		database = null;
	}


}
