package com.htmitech.ztcustom.zt.db;

import android.content.Context;
import android.database.Cursor;


import com.htmitech.ztcustom.zt.domain.FileManagerEntity;
import com.htmitech.ztcustom.zt.util.DBUnitl;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class QualityObjectionDao extends BaseDao {

    public QualityObjectionDao(Context context) {
        super(context);
    }

    /**
     * 将图片文件插入到库表中
     */
    public void insertIntoFile(String file_id, String file_name, String file_path, String task_id, String upload, String userId) {
        String sql = "INSERT INTO M_FILES (FILE_SUBMIT_ID,FILE_TYPE,FILE_NAME,FILE_PATH,TASK_ID,CREATE_TIME,UPLOAD_STATUS,UPLOAD_TIME,EXTRA) VALUES " +
                "('" + file_id + "','jpg','" + file_name + "','" + file_path + "','" + task_id + "',NULL,'" + upload + "',(datetime('now','localtime')) ,'" + userId + "');";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新图片状态
     */

    public void updateFileState(int FileID) {
        String sql = "UPDATE M_FILES SET UPLOAD_STATUS='2' WHERE FILE_ID = " + FileID + ";";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 未上报
     *
     * @return
     */
    public List<FileManagerEntity> getNeedUploadFiles() {
        String sql = "SELECT * FROM M_FILES WHERE UPLOAD_STATUS='0' OR UPLOAD_STATUS='1';";
        List<FileManagerEntity> fileManagerEntities = null;
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(sql, null);
            fileManagerEntities = DBUnitl.getObjectList(FileManagerEntity.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
        }
        return fileManagerEntities;
    }

    /**
     * 已上报
     *
     * @return
     */
    public List<FileManagerEntity> getUploadFinishedFiles() {
        String sql = "SELECT * FROM M_FILES WHERE UPLOAD_STATUS='2';";
        List<FileManagerEntity> fileManagerEntities = null;
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(sql, null);
            fileManagerEntities = DBUnitl.getObjectList(FileManagerEntity.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
        }
        return fileManagerEntities;
    }

}
