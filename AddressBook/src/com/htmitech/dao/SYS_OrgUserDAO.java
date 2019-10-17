package com.htmitech.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_OrgUser;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.ObtainCurrentCount;
import com.htmitech.myApplication.BaseApplication;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 人员部门关系DAO
 * @author Tony
 *
 */
public class SYS_OrgUserDAO {
	private static final String TABLE_NAME = "org_user_org";
	private static final String ID = "ID";
	private static final String USERID = "user_id"; //用户ID
	private static final String GROUPCORPID = "group_corp_id";// 集团单位ID
	private static final String DEPARTMENTCODE = "org_id";//组织ID
	private static final String CORPID = "corp_id";//企业编码
	private static final String ISMANAGER = "is_manager";//是否部门负责人
	private static final String ORGTITLE = "org_title";//职务名称
	private static final String USERTITLENAME = "user_titlename";//用户职务尊称，例如：李总、张局长、王主任等
	private static final String OFFICEPHONE = "office_phone";//办公电话
	private static final String FAX = "fax";//传真
//
//	private static final String CREATEDBY = "CreatedBy";
//	private static final String CREATEDDATE = "CreatedDate";
//	private static final String DISORDER = "DisOrder";
	private SQLiteDatabase db;
	private BaseApplication myApp;
	public Context context;
	private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
	public SYS_OrgUserDAO(Context context) {
		this.context = context;
//		db = DBManager.getInstance(context);

		db = DBCipherManager.getInstance(context).db;
	}
	public SYS_OrgUserDAO(SQLiteDatabase db){
		this.db = db;
	}
	//根据人员ID获取部门ID
	public SYS_OrgUser getSYSOrgUser(SYS_User mSYS_User){
		if (db.isOpen()) {
			Cursor cursor = null;
			try {
				cursor = db.query(TABLE_NAME, null, USERID + "=?",
						new String[]{mSYS_User.getUserId()}, null, null, null);
				if (cursor.moveToFirst()) {
					SYS_OrgUser mSYS_OrgUser = new SYS_OrgUser();
					String userId = cursor.getString(cursor.getColumnIndex(USERID));
					String departmentCode = cursor.getString(cursor.getColumnIndex(DEPARTMENTCODE));
//					int disOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
					mSYS_OrgUser.setUserId(userId);
					mSYS_OrgUser.setDepartmentCode(departmentCode);
//					mSYS_OrgUser.setDisOrder(disOrder);
					cursor.close();
					return mSYS_OrgUser;
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				if(cursor != null){
					cursor.close();
				}
			}
		}
		return null;
	}


	//根据人员ID获取部门ID
	public List<SYS_User> findPartmentIdOrgUser(String DepartmentCode,SYS_Department mSYS_Department) throws ParseException {
		if (db.isOpen()) {
			List<SYS_User> mArrayList = new ArrayList<SYS_User>();
			Cursor cursor = null;
				try {
//					cursor = db.query(TABLE_NAME, null, DEPARTMENTCODE + "=? order by "+ DISORDER +" ASC",
//							new String[] { DepartmentCode }, null, null, null);
					cursor = db.query(TABLE_NAME, null, DEPARTMENTCODE + "=?",
							new String[] { DepartmentCode }, null, null, null);
					while (cursor.moveToNext()) {
						SYS_OrgUser mSYS_OrgUser = new SYS_OrgUser();
						String userId = cursor.getString(cursor.getColumnIndex(USERID));
						String departmentCode = cursor.getString(cursor.getColumnIndex(DEPARTMENTCODE));
//						int disOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
						mSYS_OrgUser.setUserId(userId);
						mSYS_OrgUser.setDepartmentCode(departmentCode);
//						mSYS_OrgUser.setDisOrder(disOrder);
						SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(context);
						SYS_User mSYS_User = mSYS_UserDAO.findUserIdSYS_User(userId);
						if(mSYS_User != null) {
							mSYS_User.setmSYS_Department(mSYS_Department);
							mArrayList.add(mSYS_User);
						}
					}
					cursor.close();
					return mArrayList;
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					if(cursor != null){
						cursor.close();
					}
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
