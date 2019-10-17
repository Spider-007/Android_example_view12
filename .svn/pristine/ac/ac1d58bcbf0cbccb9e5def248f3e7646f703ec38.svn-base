package com.htmitech.htcommonformplugin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.htmitech.photoselector.model.FormExtensionFiles;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import htmitech.com.componentlibrary.db.DBCipherManager;

/**
 * Created by htrf-pc on 2016/12/2.
 */
public class FormExtensionFilesDao {
    private SQLiteDatabase db;
    private Context context;

    public FormExtensionFilesDao(Context context) {
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
    }

    /**
     * 获取所有文件
     *
     * @return
     */
    public ArrayList<FormExtensionFiles> getExtensionFiles(String Field_id) {
        String sql = "select * from form_extension_files where field_id = '"+Field_id+"'";
        ArrayList<FormExtensionFiles> mFormExtensionFilesList = new ArrayList<FormExtensionFiles>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    FormExtensionFiles mFormExtensionFiles = new FormExtensionFiles();
                    mFormExtensionFiles.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                    mFormExtensionFiles.data_id = cursor.getString(cursor.getColumnIndex("data_id"));
                    mFormExtensionFiles.form_id = cursor.getString(cursor.getColumnIndex("form_id"));
                    mFormExtensionFiles.user_id = cursor.getString(cursor.getColumnIndex("user_id"));
                    mFormExtensionFiles.app_id = cursor.getString(cursor.getColumnIndex("app_id"));
                    mFormExtensionFiles.subsystemid = cursor.getString(cursor.getColumnIndex("subsystemid"));
                    mFormExtensionFiles.other_id = cursor.getString(cursor.getColumnIndex("other_id"));
                    mFormExtensionFiles.field_id = cursor.getString(cursor.getColumnIndex("field_id"));
                    mFormExtensionFiles.ext_field_type = cursor.getString(cursor.getColumnIndex("ext_field_type"));
                    mFormExtensionFiles.file_id = cursor.getString(cursor.getColumnIndex("file_id"));
                    mFormExtensionFiles.file_path = cursor.getString(cursor.getColumnIndex("file_path"));
                    mFormExtensionFiles.status_flag = cursor.getInt(cursor.getColumnIndex("status_flag"));
                    mFormExtensionFilesList.add(mFormExtensionFiles);

                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return mFormExtensionFilesList;
    }

    /**
     * 保存单个文件
     */
    public int saveExtensionFiles(FormExtensionFiles mFormExtensionFiles) {
        int id = -1;
        String file_id = null;
        String sql = "SELECT last_insert_rowid() AS id FROM form_extension_files";
        String sq2 = "SELECT file_id FROM form_extension_files WHERE file_id = '"+mFormExtensionFiles.file_id+"' LIMIT 1;";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sq2, null);
                while (cursor.moveToNext()) {
                    file_id = cursor.getString(cursor.getColumnIndex("file_id"));
                }
                if(file_id ==null){
                    ContentValues values = new ContentValues();
                    values.put("data_id", mFormExtensionFiles.data_id);
                    values.put("form_id", mFormExtensionFiles.form_id);
                    values.put("user_id", mFormExtensionFiles.user_id);
                    values.put("app_id", mFormExtensionFiles.app_id);
                    values.put("subsystemid", mFormExtensionFiles.subsystemid);
                    values.put("other_id", mFormExtensionFiles.other_id);
                    values.put("field_id", mFormExtensionFiles.field_id);
                    values.put("ext_field_type", mFormExtensionFiles.ext_field_type);
                    values.put("file_id", mFormExtensionFiles.file_id);
                    values.put("file_path", mFormExtensionFiles.file_path);
                    values.put("status_flag", mFormExtensionFiles.status_flag);
                    db.replace("form_extension_files", null, values);
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(cursor.getColumnIndex("id"));
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * 保存文件所有文件
     */
    public void saveExtensionFiles(ArrayList<FormExtensionFiles> mFormExtensionFilesList) {
        String file_id = null;
        if (db.isOpen()) {
            try {
                Cursor cursor = null;
                for (FormExtensionFiles mFormExtensionFiles : mFormExtensionFilesList) {
                    String sql = "SELECT file_id FROM form_extension_files WHERE file_id = '"+mFormExtensionFiles.file_id+"' LIMIT 1;";
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        file_id = cursor.getString(cursor.getColumnIndex("file_id"));
                    }
                    if(file_id !=null){
                        continue;
                    }
                    ContentValues values = new ContentValues();
                    values.put("data_id", mFormExtensionFiles.data_id);
                    values.put("form_id", mFormExtensionFiles.form_id);
                    values.put("user_id", mFormExtensionFiles.user_id);
                    values.put("app_id", mFormExtensionFiles.app_id);
                    values.put("subsystemid", mFormExtensionFiles.subsystemid);
                    values.put("other_id", mFormExtensionFiles.other_id);
                    values.put("field_id", mFormExtensionFiles.field_id);
                    values.put("ext_field_type", mFormExtensionFiles.ext_field_type);
                    values.put("file_id", mFormExtensionFiles.file_id);
                    values.put("file_path", mFormExtensionFiles.file_path);
                    values.put("status_flag", mFormExtensionFiles.status_flag);
                    db.replace("form_extension_files", null, values);
                }


                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否全部上传完成
     */
    public boolean isAllFilesUpFinished() {

        boolean exist = false;
        String sql = "SELECT count(*) AS count FROM form_extension_files WHERE status_flag <> 1";
        int count = -1;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    count = cursor.getInt(cursor.getColumnIndex("count"));
                }
                if (count == 0) {
                    exist = true;
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }


        return exist;
    }

    /**
     * 更新文件状态
     * status_flag = 1
     *
     * @return
     */
    public void updateExtensionFiles(FormExtensionFiles mFormExtensionFiles) {
        String sql = "UPDATE form_extension_files SET status_flag = 1 ,file_id = '"+mFormExtensionFiles.getFile_id()+"' WHERE ID = "+mFormExtensionFiles.getID()+"";

        if (db.isOpen()) {
            try {
                db.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除文件
     * status_flag = 1
     *
     * @return
     */
    public void deleteExtensionFiles() {
        String sql = "DELETE FROM form_extension_files ";

        if (db.isOpen()) {
            try {
                db.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //***********************************************************************************************************************

    /**
     * 获取所有文件
     *
     * @return
     */
    public ArrayList<htmitech.com.componentlibrary.entity.FormExtensionFiles> getExtensionFilesJava(String Field_id) {
        String sql = "select * from form_extension_files where field_id = '"+Field_id+"'";
        ArrayList<htmitech.com.componentlibrary.entity.FormExtensionFiles> mFormExtensionFilesList = new ArrayList<htmitech.com.componentlibrary.entity.FormExtensionFiles>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    htmitech.com.componentlibrary.entity.FormExtensionFiles mFormExtensionFiles = new htmitech.com.componentlibrary.entity.FormExtensionFiles();
                    mFormExtensionFiles.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                    mFormExtensionFiles.dataId = cursor.getString(cursor.getColumnIndex("data_id"));
                    mFormExtensionFiles.formId = cursor.getString(cursor.getColumnIndex("form_id"));
//                    mFormExtensionFiles.userId = cursor.getString(cursor.getColumnIndex("user_id"));
//                    mFormExtensionFiles.app_id = cursor.getString(cursor.getColumnIndex("app_id"));
//                    mFormExtensionFiles.subsystemid = cursor.getString(cursor.getColumnIndex("subsystemid"));
//                    mFormExtensionFiles.other_id = cursor.getString(cursor.getColumnIndex("other_id"));
                    mFormExtensionFiles.fieldId = cursor.getString(cursor.getColumnIndex("field_id"));
//                    mFormExtensionFiles.ext_field_type = cursor.getString(cursor.getColumnIndex("ext_field_type"));
                    mFormExtensionFiles.fileId = cursor.getString(cursor.getColumnIndex("file_id"));
                    mFormExtensionFiles.file_path = cursor.getString(cursor.getColumnIndex("file_path"));
                    mFormExtensionFiles.status_flag = cursor.getInt(cursor.getColumnIndex("status_flag"));
                    mFormExtensionFilesList.add(mFormExtensionFiles);

                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return mFormExtensionFilesList;
    }


    /**
     * 保存单个文件
     */
    public int saveExtensionFilesJava(htmitech.com.componentlibrary.entity.FormExtensionFiles mFormExtensionFiles) {
        int id = -1;
        String file_id = null;
        String sql = "SELECT last_insert_rowid() AS id FROM form_extension_files";
        String sq2 = "SELECT file_id FROM form_extension_files WHERE file_id = '"+mFormExtensionFiles.fileId+"' LIMIT 1;";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sq2, null);
                while (cursor.moveToNext()) {
                    file_id = cursor.getString(cursor.getColumnIndex("file_id"));
                }
                if(file_id ==null){
                    ContentValues values = new ContentValues();
                    values.put("data_id", mFormExtensionFiles.dataId);
                    values.put("form_id", mFormExtensionFiles.formId);
//                    values.put("user_id", mFormExtensionFiles.useId);
//                    values.put("app_id", mFormExtensionFiles.app_id);
//                    values.put("subsystemid", mFormExtensionFiles.subsystemid);
//                    values.put("other_id", mFormExtensionFiles.other_id);
                    values.put("field_id", mFormExtensionFiles.fieldId);
//                    values.put("ext_field_type", mFormExtensionFiles.ext_field_type);
                    values.put("file_id", mFormExtensionFiles.fileId);
                    values.put("file_path", mFormExtensionFiles.file_path);
                    values.put("status_flag", mFormExtensionFiles.status_flag);
                    db.replace("form_extension_files", null, values);
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(cursor.getColumnIndex("id"));
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * 更新文件状态
     * status_flag = 1
     *
     * @return
     */
    public void updateExtensionFilesJava(htmitech.com.componentlibrary.entity.FormExtensionFiles mFormExtensionFiles) {
        String sql = "UPDATE form_extension_files SET status_flag = 1 ,file_id = '"+mFormExtensionFiles.getFileId()+"' WHERE ID = "+mFormExtensionFiles.getID()+"";

        if (db.isOpen()) {
            try {
                db.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
