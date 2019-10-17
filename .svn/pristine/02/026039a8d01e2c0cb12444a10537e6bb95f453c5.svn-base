package com.htmitech.proxy.dao;

import android.content.ContentValues;
import android.content.Context;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.entity.OAConText;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.entity.Dics;

/**
 * Created by Administrator on 2018/5/25.
 */

public class MobileSearchDao {
    private SQLiteDatabase db;
    private Context context;
    public MobileSearchDao(Context context){
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
    }

    public void saveSearch(Dics dics, String appId, String fieldName){
        String portalId = BookInit.getInstance().getPortalId();
        String user_id = OAConText.getInstance(context).UserID;
        long timers = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put("time", user_id);
        values.put("portal_id", portalId);
        values.put("app_id", appId);
        values.put("user_id", user_id);
        values.put("field_name", fieldName);
        values.put("dics_id", dics.id);
        values.put("dics_name", dics.name);
        values.put("dics_value", dics.value);

        db.replace("mobile_search", null, values);


//        db.execSQL("insert into mobile_search(ID,portal_id,app_id,user_id,field_name,dics_id,dics_name,dics_value) values (?,?,?,?,?,?,?,?)", new Object[] {timers,portalId,appId,user_id,fieldName ,dics.id,dics.name, dics.value});
    }

    public List<Dics> getListSearch(String appId, String fieldName){
        String portalId = BookInit.getInstance().getPortalId();
        String user_id = OAConText.getInstance(context).UserID;
        String sql = "select * from mobile_search where portal_id = '"+portalId+"'" + " and user_id = '"+user_id+"'"+" and app_id = '"+appId+"'"+" and field_name = '"+fieldName+"'   ORDER BY time DESC";
        List<Dics> dicses = new ArrayList<Dics>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    Dics mDics = new Dics();
                    String dics_id = cursor.getString(cursor.getColumnIndex("dics_id"));
                    String dics_name = cursor.getString(cursor.getColumnIndex("dics_name"));
                    String dics_value = cursor.getString(cursor.getColumnIndex("dics_value"));
                    mDics.id = dics_id;
                    mDics.name = dics_name;
                    mDics.value = dics_value;
                    dicses.add(mDics);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }
        return dicses;
    }

    public void deleteSearch(String appId,String fieldName,String id){
        String portalId = BookInit.getInstance().getPortalId();
        String user_id = OAConText.getInstance(context).UserID;
        String sql = "DELETE FROM mobile_search WHERE portal_id = '"+portalId+"' and user_id = '"+user_id+"' and field_name = '"+fieldName +"' and app_id = '"+appId + "' and dics_id = '"+ id+"'" ;
        db.execSQL(sql);
    }

    public void deleteAllSearch(){
        db.execSQL("DELETE FROM mobile_search");
    }

}
