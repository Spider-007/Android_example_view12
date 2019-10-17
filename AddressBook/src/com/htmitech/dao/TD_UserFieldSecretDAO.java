package com.htmitech.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import com.htmitech.domain.TD_User;
import com.htmitech.domain.TD_UserFieldSecret;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 定义某个保密字段哪些人能看 DAO
 * 
 * @author Tony
 * 
 */
public class TD_UserFieldSecretDAO {
	private static final String TABLE_NAME = "TD_UserFieldSecret";
	private SQLiteDatabase db;
	private static final String USERID = "UserId";
	private static final String FIELDNAME = "FieldName";
	private Context context;
	private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
	public TD_UserFieldSecretDAO(Context context) {
//		db = DBManager.getInstance(context);
		db = DBCipherManager.getInstance(context).db;
		this.context = context;
	}

	public TD_UserFieldSecret getUserFieldSecret(String userIds) {
		if (db.isOpen()) {
			Cursor cursor = db.query(TABLE_NAME, null, USERID + "=?",
					new String[] { userIds }, null, null, null);
			TD_UserFieldSecret mTD_UserFieldSecret = new TD_UserFieldSecret();
			if (cursor.moveToNext()) {
				String userId = cursor.getString(cursor.getColumnIndex(USERID));
				String fieldName = cursor.getString(cursor
						.getColumnIndex(FIELDNAME));
				TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
				TD_User mTD_User = mTD_UserDAO.getTD_User(fieldName,false);
				mTD_UserFieldSecret.setUserId(userId);
				mTD_UserFieldSecret.setFieldName(fieldName);
				mTD_UserFieldSecret.setmTD_User(mTD_User);
				cursor.close();
				return mTD_UserFieldSecret;
			}

		}
		return null;
	}
	public void insert(String[] culumn,ArrayList<String[]> culumnValueArray){
		for (String c : culumn) {
			if (!ExtensionField.checkColumnExist(db, c, TABLE_NAME)) {
				String sql = "ALTER TABLE " + TABLE_NAME + "  ADD COLUMN " + c
						+ " VARCHAR(200)";
				db.execSQL(sql);
			}
		}
		if(culumnValueArray.size() > 0){
			String[] lenth = culumnValueArray.get(0);
			String isDelete = lenth[lenth.length - 1];
			if(isDelete.equals("1")){
				db.execSQL("DELETE FROM " + TABLE_NAME);
			}
		}
		for(String[] culumnValue :culumnValueArray){
			ContentValues values = new ContentValues();
			for(int i = 0 ; i < culumn.length ; i++){
				values.put(culumn[i], culumnValue[i]);
			}
			db.replace(TABLE_NAME, null, values);
		}
	}
}
