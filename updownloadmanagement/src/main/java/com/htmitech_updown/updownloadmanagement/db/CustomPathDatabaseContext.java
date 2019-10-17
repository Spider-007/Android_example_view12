package com.htmitech_updown.updownloadmanagement.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by Administrator on 2018/7/6.
 */
public class CustomPathDatabaseContext extends ContextWrapper {

    private String mDirPath;

    public CustomPathDatabaseContext(Context base, String dirPath) {
        super(base);
        this.mDirPath = dirPath;
    }

    @Override
    public File getDatabasePath(String name) {
        File result = new File(mDirPath + File.separator + name);
        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }
        return result;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getAbsolutePath(), factory, errorHandler);
    }

}
