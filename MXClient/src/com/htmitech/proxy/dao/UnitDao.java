package com.htmitech.proxy.dao;

import android.content.ContentValues;
import android.content.Context;


import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 初始化所有应用中心中的数据库
 */
public class UnitDao {
    private SQLiteDatabase db;
    private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
    public UnitDao(Context context) {
//        db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
    }

    /**
     * 初始化所有数据库中的数据
     * @param tabName
     * @param culumn
     * @param culumnValueArray
     */
    public void initApplicationCenterDao(String tabName,String[] culumn, ArrayList<String[]> culumnValueArray,String isDelete){
        insert(tabName, culumn, culumnValueArray, isDelete);
    }

    public void execSQL(String sql){
        db.execSQL(sql);
    }

    private void insert(String tabName,String[] culumn, ArrayList<String[]> culumnValueArray,String isDelete){

        if(culumnValueArray.size() > 0){
            if(isDelete != null && isDelete.equals("1")){
                db.execSQL("DELETE FROM " + tabName);
            }
        }
        for (String[] culumnValue : culumnValueArray) {
            ContentValues values = new ContentValues();
            for (int i = 0; i < culumn.length; i++) {
                values.put(culumn[i], culumnValue[i]);
            }
            db.replace(tabName, null, values);
        }

    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * 检查某表列是否存在
     * @param tableName 表名
     * @param columnName 列名
     * @return
     */
    public boolean checkColumnExist(String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0"
                    , null );
            result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
        }catch (Exception e){
//            Log.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }

    /**
     * 判断字段是否存在
     *
     * 如果字段不存在的话 那么就给一个字段进行站位处理，方式出错
     * @param TABLE_NAME
     * @param culumnList
     */
    public void addColumn(String TABLE_NAME,String[] culumnList){
        for (String c : culumnList) {
            if (!ExtensionField.checkColumnExist(db, c, TABLE_NAME)) {
                String sql = "ALTER TABLE " + TABLE_NAME + "  ADD COLUMN " + c
                        + " VARCHAR(200)";
                db.execSQL(sql);
            }
        }
    }
}
