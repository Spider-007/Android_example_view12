package htmitech.com.componentlibrary.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by htrf-pc on 2018/1/11.
 */
public class HtmitechDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "htmitech.db";
    private static final int DATABASE_VERSION = 1;

    //读写数据库用到的password
    public static final String SECRET_KEY="htmitech_password";

    public HtmitechDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 第一次使用数据库时自动建表
        db.execSQL(SQLConstant.APC_USERDEFINE_PORTAL);
        db.execSQL(SQLConstant.APP_INFO);
        db.execSQL(SQLConstant.APP_VERSION);
        db.execSQL(SQLConstant.APP_VERSION_CONFIG);
        db.execSQL(SQLConstant.APPCLIENT_VERSION);
        db.execSQL(SQLConstant.EMP_CORP_PORTAL);
        db.execSQL(SQLConstant.EMP_PORTAL);
        db.execSQL(SQLConstant.FORM_EXTENSION_FILES);
        db.execSQL(SQLConstant.FS_PICTRUE);
        db.execSQL(SQLConstant.MOBILE_APP_INFO);
        db.execSQL(SQLConstant.ORG_ORG_TREE);
        db.execSQL(SQLConstant.ORG_USER);
        db.execSQL(SQLConstant.ORG_USER_CONTACT);
        db.execSQL(SQLConstant.ORG_USER_ORG);
        db.execSQL(SQLConstant.ORG_USERFIELD);
        db.execSQL(SQLConstant.PORTAL_APP);
        db.execSQL(SQLConstant.PORTAL_LEFT_MENU);
        db.execSQL(SQLConstant.PORTAL_RIGHTTOP_MENU);
        db.execSQL(SQLConstant.PORTAL_TAB);
        db.execSQL(SQLConstant.PORTAL_TAB_MENU);
        db.execSQL(SQLConstant.SHORTCUTKEYS);
        db.execSQL(SQLConstant.USER_APC_PORTAL_APP);
        db.execSQL(SQLConstant.APC_APP_APPGROUP);
        db.execSQL(SQLConstant.V_CONTACT_VIEW);
        db.execSQL(SQLConstant.V_ORG_USER_ORG_VIEW);
        db.execSQL(SQLConstant.V_USER_VIEW);
        db.execSQL(SQLConstant.COM_PARAM_GROUPCORP);
        db.execSQL(SQLConstant.VD_TAB_DEFINE);
        db.execSQL(SQLConstant.MOBILE_SEARCH);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
