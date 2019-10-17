package com.htmitech.dao;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 用户常用联系人DAO
 * 
 * @author htrf-pc
 * 
 */
public class T_UserRelationshipDAO {
	public static final String TABLE_NAME = "V_contact";
	public static final String TABLENAME = "org_user_contact";
	public static final String COLUMN_USERID = "myuser_id"; //用户ID
	public static final String COLUMN_CUSERID = "user_id";//联系人的用户ID
	public static final String STATUS_FLAG = "status_flag";//状态标识，1-有效，0-无效


	private SQLiteDatabase db;
	private Context context;
	private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
	public T_UserRelationshipDAO(Context context) {
//		db = DBManager.getInstance(context);
		db = DBCipherManager.getInstance(context).db;
		this.context = context;
	}

	/***
	 * 获取联系人所有信息
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<T_UserRelationship> getContactList() throws ParseException {
		List<T_UserRelationship> userRelationMap = new ArrayList<T_UserRelationship>();
		if (db.isOpen()) {

			Cursor cursor = null;
			try{
				cursor = db.rawQuery(
						"select * from " + TABLE_NAME + " where "+COLUMN_USERID+" = '"+ BookInit.getInstance().getCrrentUserId()+"' and user_type > 0 AND status_flag > 0 and ouc_status_flag > 0", null);
				SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(context,null);
				T_UserRelationship mT_UserRelationship = null;
				while (cursor.moveToNext()) {
					mT_UserRelationship = new T_UserRelationship();
					String userId = cursor.getString(cursor
							.getColumnIndex(COLUMN_USERID));
					String cUserId = cursor.getString(cursor
							.getColumnIndex(COLUMN_CUSERID));
					SYS_User mSYS_User = mSYS_UserDAO.findUserIdSYS_User(cUserId);
					mT_UserRelationship.setCUserId(cUserId);
					mT_UserRelationship.setUserId(userId);
					if(mSYS_User == null){
						continue;
					}
					String headerName = "";
					if (mSYS_User != null && !TextUtils.isEmpty(mSYS_User.getFullName())) {
						headerName = mSYS_User.getFullName();
					} else {
//					headerName = mSYS_User.getUsername();
					}
					if (cUserId.equals(Constant.NEW_FRIENDS_USERNAME)
							|| cUserId.equals(Constant.GROUP_USERNAME)) {
						mT_UserRelationship.setHeader("");
					} else if (!headerName.equals("") && Character.isDigit(headerName.charAt(0))) {
						mT_UserRelationship.setHeader("#");
					} else {
						mT_UserRelationship.setHeader(HanziToPinyin.getInstance()
								.get(headerName.substring(0, 1)).get(0).target
								.substring(0, 1).toUpperCase());
						char header = mT_UserRelationship.getHeader().toLowerCase()
								.charAt(0);
						if (header < 'a' || header > 'z') {
							mT_UserRelationship.setHeader("#");
						}
					}
					mT_UserRelationship.setmSYS_User(mSYS_User);
					if(!userRelationMap.contains(mT_UserRelationship))
						userRelationMap.add(mT_UserRelationship);
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				if(cursor != null){
					cursor.close();
				}
			}
		}
		return userRelationMap;
	}
	public void insert(String[] culumn,ArrayList<String[]> culumnValueArray){
		for (String c : culumn) {
			if (!ExtensionField.checkColumnExist(db, c, TABLENAME)) {
				String sql = "ALTER TABLE " + TABLE_NAME + "  ADD COLUMN " + c
						+ " VARCHAR(200)";
				db.execSQL(sql);
			}
		}

		if(culumnValueArray.size() > 0){
			String[] lenth = culumnValueArray.get(0);
			String isDelete = lenth[lenth.length - 1];
			if(isDelete != null && isDelete.equals("1")){
				db.execSQL("DELETE FROM " + TABLENAME);
			}
		}
		for(String[] culumnValue :culumnValueArray){
			ContentValues values = new ContentValues();
			for(int i = 0 ; i < culumn.length ; i++){
				values.put(culumn[i], culumnValue[i]);
			}
			db.replace(TABLENAME, null, values);
		}
	}
	public void deleteUser(String userId){
		if (db.isOpen()) {
			db.delete("org_user_contact", "contact_id=? and user_id=?", new String[]{userId,BookInit.getInstance().getCrrentUserId()});
		}
	}
}
