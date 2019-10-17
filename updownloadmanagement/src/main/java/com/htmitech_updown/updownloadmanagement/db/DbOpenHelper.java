package com.htmitech_updown.updownloadmanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String UPLOADFILETABLE = "CREATE TABLE IF NOT EXISTS T_UPLOADFILETABLE(batchId TEXT PRIMARY KEY NOT NULL, uploadUserId TEXT, date  TEXT, clientIp  TEXT,fileName TEXT,filePath TEXT, fileLength TEXT, skipSize TEXT,uploadThreadNum  TEXT, threadLimitNum TEXT, status TEXT,ip  TEXT,port TEXT,serversID TEXT,uploadFileType TEXT, byteSize TEXT, isMergeFile TEXT, downAppId TEXT,downKey TEXT, fileAccessRight TEXT,percent TEXT,moduleTppe TEXT)";
    private static final String UPLOADTASKTABLE = "CREATE TABLE IF NOT EXISTS T_UPLOADTASKTABLE(TASK_ID TEXT PRIMARY KEY NOT NULL,  taskNo TEXT, totolNum   TEXT, len  TEXT, startPos TEXT,endPos TEXT,uploadTime TEXT,wasteTime TEXT,status TEXT, resultErrMsg  TEXT, batchId TEXT, uploadUserId TEXT, clientIp TEXT, fileName TEXT, filePath TEXT, fileLength TEXT, skipSize TEXT, uploadThreadNum  TEXT,threadLimitNum TEXT,isHolded  TEXT, ip  TEXT,  port TEXT, serversID TEXT,byteSize TEXT, isMergeFile TEXT,chunk INTEGER,chunks INTEGER,batchNumber TEXT,taskName TEXT,createTime TEXT,formId TEXT,fileId TEXT,groupCorpId TEXT,filePurpose TEXT,fileClassCode TEXT,fileCode TEXT,submitBean TEXT,uniqueId TEXT,isChunk TEXT,mediaDuration INTEGER)";

    public DbOpenHelper(Context context) {
        super(new CustomPathDatabaseContext(context, getDirPath()), "updownload.db", null, 4);
    }


    /**
     * 获取db文件在sd卡的路径
     *
     * @return
     */
    private static String getDirPath() {
        //TODO 这里返回存放db的文件夹的绝对路径
        return Environment.getExternalStorageDirectory().getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UPLOADFILETABLE);
        db.execSQL(UPLOADTASKTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS T_UPLOADTASKTABLE");
        onCreate(db);
    }
}
