package com.htmitech_updown.updownloadmanagement.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.RequestSuccessFileInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.RequestSuccessResultBean;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.utils.DBUnitl;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.db.SQLConstant;

/**
 */
public class DbUtil {

    public static Context context;

    public static void setContext(Context contexts) {
        context = contexts.getApplicationContext();
    }

    public static SQLiteDatabase getDataBase(Context context) {
        return new DbOpenHelper(context.getApplicationContext()).getWritableDatabase();
    }

    /**
     * 添加
     */
    public static void addUpDownState(ChunkInfo mChunkInfo) {
        ContentValues contentValues = alarmContentValues(mChunkInfo);
        SQLiteDatabase db = getDataBase(context);
        try {
            db.replace("T_UPLOADTASKTABLE", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    /**
     * 所有文件上传完成后
     */
    public static void addUpDownSuccessState(ChunkInfo mChunkInfo, RequestSuccessFileInfo fileInfo) {
        ContentValues contentValues = alarmContentValues(mChunkInfo, fileInfo);
        SQLiteDatabase db = getDataBase(context);
        try {
            db.replace("T_UPLOADTASKTABLE", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    /**
     * 上传成功用的ContentValue
     *
     * @param mChunkInfo
     * @return
     */
    private static ContentValues alarmContentValues(ChunkInfo mChunkInfo, RequestSuccessFileInfo fileInfo) {
        ContentValues values = new ContentValues();
        values.put("TASK_ID", mChunkInfo.getMd5());
//        values.put("key", mChunkInfo.getMd5());
        values.put("taskNo", mChunkInfo.getMd5());
        values.put("filePath", mChunkInfo.getFilePath());
        values.put("fileLength", mChunkInfo.getFileSize());
        values.put("batchNumber", mChunkInfo.getBatchNumber());
        values.put("fileName", mChunkInfo.getFileName());
        values.put("len", mChunkInfo.getFileLength());
        values.put("chunk", mChunkInfo.getChunk());
        values.put("chunks", mChunkInfo.getChunks());
        values.put("taskName", mChunkInfo.getTaskName());
        values.put("createTime", mChunkInfo.getCreateTime());
        values.put("formId", mChunkInfo.getFormId());
        values.put("uniqueId", mChunkInfo.getUniqueId());
        values.put("submitBean", mChunkInfo.getSubmitBean());
        values.put("isChunk", mChunkInfo.isChunk() ? "1" : "0");
        values.put("mediaDuration", mChunkInfo.getMediaDuration());

        values.put("status", fileInfo.statusFlag);//上传成功
        values.put("uploadTime", fileInfo.updateTime);
        values.put("fileId", fileInfo.fileId);
        values.put("groupCorpId", fileInfo.groupCorpId);
        values.put("filePurpose", fileInfo.filePurpose);
        values.put("fileClassCode", fileInfo.fileClassCode);
        values.put("fileCode", fileInfo.fileCode);
        return values;
    }


    //上传失败或者暂停或者还没上传
    private static ContentValues alarmContentValues(ChunkInfo mChunkInfo) {
        ContentValues values = new ContentValues();
        values.put("TASK_ID", mChunkInfo.getMd5());
//        values.put("key", mChunkInfo.getMd5());
        values.put("taskNo", mChunkInfo.getMd5());
        values.put("filePath", mChunkInfo.getFilePath());
        values.put("fileLength", mChunkInfo.getFileSize());
        values.put("batchNumber", mChunkInfo.getBatchNumber());
        values.put("fileName", mChunkInfo.getFileName());
        values.put("len", mChunkInfo.getFileLength());
        values.put("status", 0);
        values.put("chunk", mChunkInfo.getChunk());
        values.put("chunks", mChunkInfo.getChunks());
        values.put("taskName", mChunkInfo.getTaskName());
        values.put("createTime", mChunkInfo.getCreateTime());
        values.put("formId", mChunkInfo.getFormId());
        values.put("uniqueId", mChunkInfo.getUniqueId());
        values.put("submitBean", mChunkInfo.getSubmitBean());
        values.put("isChunk", mChunkInfo.isChunk() ? "1" : "0");
        values.put("mediaDuration", mChunkInfo.getMediaDuration());
        return values;
    }


    /**
     * 获取未上传，暂停，和上传失败的数据集合
     *
     * @return
     */
    public static List<UploadFileInfoBean> getUploadFileInfoSCLBList() {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE status=0 ORDER BY createTime DESC;";
        Cursor mCursor = null;
        List<UploadFileInfoBean> uploadFileInfoBeanList = new ArrayList<>();
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBeanList = DBUnitl.getObjectList(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBeanList;
    }

    /**
     * 获取暂停或者上传失败的文件信息
     *
     * @return
     */
    public static UploadFileInfoBean getUploadFileInfoSCLB(String TASK_ID) {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE TASK_ID = '" + TASK_ID + "';";
        Cursor mCursor = null;
        UploadFileInfoBean uploadFileInfoBean = null;
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBean = DBUnitl.getObject(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBean;
    }

    /**
     * 获取已上传的文件信息
     *
     * @return
     */
    public static List<UploadFileInfoBean> getUploadFileInfoFinishList() {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE status=1 ORDER BY uploadTime DESC;";
        Cursor mCursor = null;
        List<UploadFileInfoBean> uploadFileInfoBeanList = new ArrayList<>();
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBeanList = DBUnitl.getObjectList(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBeanList;
    }


    /**
     * 获取当前流程所有上报的文件信息
     *
     * @return
     */
    public static List<UploadFileInfoBean> getUploadFileInfoFlowIdList(String formId) {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE formId='" + formId + "';";
        Cursor mCursor = null;
        List<UploadFileInfoBean> uploadFileInfoBeanList = new ArrayList<>();
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBeanList = DBUnitl.getObjectList(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBeanList;
    }

    /**
     * 获取当前流程所有上报的文件信息
     *
     * @return
     */
    public static List<UploadFileInfoBean> getUploadFileInfoUniqueIdList(String uniqueId) {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE uniqueId='" + uniqueId + "';";
        Cursor mCursor = null;
        List<UploadFileInfoBean> uploadFileInfoBeanList = new ArrayList<>();
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBeanList = DBUnitl.getObjectList(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBeanList;
    }


    /**
     * 删除已上传文件功能，改变数据库数据的状态值，假删除，修改标志 state=-1 已经被删除
     *
     * @param task_id
     */
    public static void delectFile(String task_id) {
        SQLiteDatabase db = getDataBase(context);
        String sql = "UPDATE T_UPLOADTASKTABLE SET status='-1' WHERE TASK_ID='" + task_id + "' AND status='1';";
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 搜索已上传的文件信息
     *
     * @return
     */
    public static List<UploadFileInfoBean> getUploadFileInfoFinishSearchList(String quary) {
        SQLiteDatabase db = getDataBase(context);
        String sql = "SELECT * FROM T_UPLOADTASKTABLE WHERE status=1 AND fileName LIKE '%" + quary + "%';";
        Cursor mCursor = null;
        List<UploadFileInfoBean> uploadFileInfoBeanList = new ArrayList<>();
        try {
            mCursor = db.rawQuery(sql, null);
            uploadFileInfoBeanList = DBUnitl.getObjectList(UploadFileInfoBean.class, mCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCursor && !mCursor.isClosed()) {
                mCursor.close();
            }
            db.close();
        }
        return uploadFileInfoBeanList;
    }
}

