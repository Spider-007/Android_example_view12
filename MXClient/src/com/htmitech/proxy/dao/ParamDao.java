package com.htmitech.proxy.dao;

import android.content.Context;

import com.htmitech.proxy.doman.CmParamGroupcorp;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import htmitech.com.componentlibrary.db.DBCipherManager;

/**
 * 参数的一些sql语句
 */
public class ParamDao {

    private SQLiteDatabase db;
    private Context context;
    public ParamDao(Context context) {
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
    }

    public HashMap<String,String> getParamHashMap(){
        ArrayList<CmParamGroupcorp> cmParamGroupcorpArrayList = new ArrayList<CmParamGroupcorp>();
        HashMap<String,String> stringStringHashMap = new HashMap<String, String>();
        if(db.isOpen()){
            String sql = "select * from cm_param_groupcorp where status_flag > 0";
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    String param_name = cursor.getString(cursor.getColumnIndex("param_name"));
                    String param_value = cursor.getString(cursor.getColumnIndex("param_value"));
                    stringStringHashMap.put(param_name,param_value);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cursor.close();
            }
        }
        return stringStringHashMap;
    }

}
