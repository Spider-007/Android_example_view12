package com.htmitech.ztcustom.zt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by htrf-pc on 2017/3/15.
 */
public abstract class BaseDao {
    public SQLiteDatabase db;

    public BaseDao(Context context) {
        db = DBManager.getInstance(context);
    }


}
