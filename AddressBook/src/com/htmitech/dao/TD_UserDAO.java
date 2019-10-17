package com.htmitech.dao;

import java.util.ArrayList;
import java.util.Map;

import com.htmitech.api.BookInit;
import com.htmitech.domain.TD_User;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 人员属性定义DAO
 *
 * @author htrf-pc
 */

public class TD_UserDAO {
    private static final String TABLE_NAME = "org_userfield";
    private static final String FIELDNAME = "field_name";//字段名称
    private static final String DISLABEL = "display_name";//显示名称
    private static final String DISORDER = "display_order";//显示顺序号
    private static final String ISACTIVE = "display_level";//1' COMMENT '是否显示,0：不显示，1：仅服务器显示，2-全部显示
    private static final String ENABLEDEDIT = "can_edit"; //'能否编辑，0-不能编辑，1-可编辑'
    private static final String SECRETFLAG = "safe_level"; //安全级别，0：不保护，1-指定用户组,2：只允许上级部门访问，3-允许上级部门及本部门访问，4-允许本部门访问，5-允许本部门及下级部门访问\r\n
    private static final String USERGROUPPROTECT = "usergroup_protect"; //'是否通过启用用户组保护，0：不启用，1-启用',
    private static final String PROTECTMODE = "protect_mode"; //安全模式,''0''-表示完全隐藏，后续再定义特殊表达式来规定哪几位需要用什么特殊符号来代替，\r\n            例如：（1）前4位用"*"代替；（2）第5-10位用"#"代替；（3）最后3位用"*"代替；\r\n
//    private static final String ACTION = "Action";
    private SQLiteDatabase db;
    private Context context;
    private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
    public TD_UserDAO(Context context) {
//        db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
        this.context=context;
    }


    // 根据字段名称获取这个字段的含义
    public TD_User getTD_User(String FieldNames,boolean isDb) {
//        SQLiteDatabase db = DBManager.getInstance(context);
        if(isDb)
//            db = mHtmitechDatabaseHelper.getReadableDatabase(mHtmitechDatabaseHelper.SECRET_KEY);
        if (db.isOpen()) {
            Cursor cursor = null;
            String sql = "";
            if(!TextUtils.isEmpty(BookInit.getInstance().getCorp_id())){
                sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"' AND corp_id="+ BookInit.getInstance().getCorp_id();
            }else{
                if(!TextUtils.isEmpty(BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id() + "")){
                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"' AND corp_id="+ BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id();
                }else{
                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"'";
                }
            }
            try {
                cursor = db.rawQuery(sql,null);

//                if (cursor == null || cursor.getCount() <= 0) {
//                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"' AND corp_id="+ BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id();
//                    cursor = db.rawQuery(sql,null);
//                }
//
//                if (cursor == null || cursor.getCount() <= 0) {
//                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"'";
//                    cursor = db.rawQuery(sql,null);
//                }
                if (!cursor.moveToFirst()) {
                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"' AND corp_id="+ BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id();
                    cursor = db.rawQuery(sql,null);
                }

                if (!cursor.moveToFirst()) {
                    sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIELDNAME+"='"+FieldNames+"'";
                    cursor = db.rawQuery(sql,null);
                }
                if (cursor == null ) {
                    return null;
                }
                if (cursor.moveToFirst()) {
                    TD_User mTD_User = getTDUser(cursor);
//                    mTD_User.setAction(action);
                    cursor.close();
                    return mTD_User;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }


        }
        return null;
    }

    public TD_User getTDUser(Cursor cursor){
        TD_User mTD_User = new TD_User();
        String FieldName = cursor.getString(cursor
                .getColumnIndex(FIELDNAME));
        String DisLabel = cursor.getString(cursor
                .getColumnIndex(DISLABEL));
        int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
        short IsActive = cursor.getShort(cursor
                .getColumnIndex(ISACTIVE));
        boolean EnabledEdit = cursor.getShort(cursor
                .getColumnIndex(ENABLEDEDIT)) == 0 ? false : true;
        short SecretFlag = cursor.getShort(cursor
                .getColumnIndex(SECRETFLAG));
//                    short action = cursor.getShort(cursor.getColumnIndex(ACTION));
        short userGourpProtect = cursor.getShort(cursor.getColumnIndex(USERGROUPPROTECT));
        String protect_mode = cursor.getString(cursor.getColumnIndex(PROTECTMODE));
        mTD_User.setUsergroup_protect(userGourpProtect);
        mTD_User.setProtect_mode(protect_mode);
        mTD_User.setFieldName(FieldName);
        mTD_User.setDisLabel(DisLabel);
        mTD_User.setDisOrder(DisOrder);
        mTD_User.setIsActive(IsActive);
        mTD_User.setEnabledEdit(EnabledEdit);
        mTD_User.setSecretFlag(SecretFlag);
        return mTD_User;
    }

    public void insert(String[] culumn, ArrayList<String[]> culumnValueArray) {


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
        for (String[] culumnValue : culumnValueArray) {
            ContentValues values = new ContentValues();
            for (int i = 0; i < culumn.length; i++) {
                values.put(culumn[i], culumnValue[i]);
            }
            db.replace(TABLE_NAME, null, values);
        }
    }


}
