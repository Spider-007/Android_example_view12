package htmitech.com.componentlibrary.db;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * Created by htrf-pc on 2018/1/11.
 */
public class DBCipherManager {

    private static final String TAG = "DatabaseManager";
    // 静态引用
    private volatile static DBCipherManager mInstance;
    // DatabaseHelper
    public HtmitechDatabaseHelper dbHelper;
    public SQLiteDatabase db;
    private DBCipherManager(Context context) {
        dbHelper = new HtmitechDatabaseHelper(context.getApplicationContext());
        db = dbHelper.getWritableDatabase(HtmitechDatabaseHelper.SECRET_KEY);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBCipherManager getInstance(Context context) {
        DBCipherManager inst = mInstance;
        if (inst == null) {
            synchronized (DBCipherManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new DBCipherManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public void openDB(){
        db = dbHelper.getWritableDatabase(HtmitechDatabaseHelper.SECRET_KEY);
    }


    public static boolean isDBexists(Context context){
//		databaseSdcardPath = Environment.getDataDirectory().getAbsolutePath()+"/data/"+context.getPackageName()+"/databases";
        String databaseFilename = "/data/data/"
                + context.getPackageName() + "/databases" + "/" + "htmitech.db";
        Log.d("DBManager",databaseFilename);
        File dir = new File(databaseFilename);
        if (!dir.exists()) {
            return true;
        }
        return false;
    }

}
