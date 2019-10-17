package com.htmitech.emportal.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.htmitech.emportal.entity.AuthorInfo;

public class AuthorInfoDAOImpl implements AuthorInfoDAO{
	
	private DBHelper mHelper = null;
	
	private static final String SQL_INSERT = "replace into author_info(user_id,user_name,thrid_third_user_id) values(?,?,?)";
	
	private static final String SQL_SELECT = "select * from author_info";
	
	
	public AuthorInfoDAOImpl(Context context) {
		mHelper = new DBHelper(context);
	}
	
	/***
	 * 插入所有用户
	 */
	@Override
	public boolean insertAllAuthor(AuthorInfo[] authorArr) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		for (int i = 0; i < authorArr.length; i++) {
			db.execSQL(SQL_INSERT, new Object[]{authorArr[i].getUserID(), authorArr[i].getUserName(), 
					authorArr[i].getThridThirdUserId()});
		}
		db.close();
		return false;
	}
	
	 /*
	@Override
	public boolean insertAllAuthor(AuthorInfo[] authorArr) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		for (int i = 0; i < authorArr.length; i++) {
			ContentValues[] values = new ContentValues[];
		}
		db.close();
		return false;
	}*/
	
	
	/***
	 * 查询所有的用户,为OA工作流选择办理人准备数据
	 */
	@Override
	public AuthorInfo[] getAuthorForOaSelect() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		ArrayList<AuthorInfo> authorInfoList = new ArrayList<AuthorInfo>();
		Cursor cursor = db.rawQuery(SQL_SELECT, null);
		while (cursor.moveToNext()) {
			AuthorInfo authorInfo = new AuthorInfo();
			authorInfo.setUserID("U_" + cursor.getString(cursor.getColumnIndex("thrid_third_user_id")));// thread_id,url,start,end,finished
			authorInfo.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
			//authorInfo.setThridThirdUserId(cursor.getString(cursor.getColumnIndex("thrid_third_user_id")));
			authorInfoList.add(authorInfo);
		}
		cursor.close();
		db.close();
		return authorInfoList.toArray(new AuthorInfo[authorInfoList.size()]);
	}	

	/***
	 * 查询所有的用户
	 */
	@Override
	public AuthorInfo[] getAllAuthor() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		ArrayList<AuthorInfo> authorInfoList = new ArrayList<AuthorInfo>();
		Cursor cursor = db.rawQuery(SQL_SELECT, null);
		while (cursor.moveToNext()) {
			AuthorInfo authorInfo = new AuthorInfo();
			authorInfo.setUserID(cursor.getString(cursor.getColumnIndex("user_id")));// thread_id,url,start,end,finished
			authorInfo.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
			authorInfo.setThridThirdUserId(cursor.getString(cursor.getColumnIndex("thrid_third_user_id")));
			authorInfoList.add(authorInfo);
		}
		cursor.close();
		db.close();
		return authorInfoList.toArray(new AuthorInfo[authorInfoList.size()]);
	}

}
