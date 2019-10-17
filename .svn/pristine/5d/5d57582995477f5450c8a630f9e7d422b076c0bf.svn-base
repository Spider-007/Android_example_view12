package com.htmitech.emportal.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;

import htmitech.com.componentlibrary.db.DBCipherManager;

/**
 * Created by yanxin on 2017-3-7.
 */
public class CacheDeleteUtils {
    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
//        deleteFilesByDirectory(new File("/data/data/"
//                + "com.minxing.client" + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
//        deleteFilesByDirectory(new File("/data/data/"
//                + " com.minxing.client" + "/shared_prefs"));

        Log.e("Baoming",context.getPackageName());
//        SharedPreferences sp = context.getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
//         sp.getString(PreferenceUtils.PREFERENCE_LAST_TIME, "1999-01-01 00:00:00");
    }

    /** * 按名字清除本应用数据库 * * @param context * @param dbName */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /** * 清除本应用所有的数据 * * @param context * @param filepath */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
        DBCipherManager.getInstance(context).db = null;

    }

    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /** * 删除方法 删除文件和目录 */
    public static void clearFiles(String workspaceRootPath){
           File file = new File(workspaceRootPath);
        File[] files=file.listFiles();
        if(files.length>0){
            for(File f:files){
                if(f.isDirectory()){//如果是目录
                    clearFiles(f.toString()); //递归

//                    f.delete();     //删除该文件夹
                }
                else{

                    f.delete();
                }
            }
        }
        else{
            System.out.println("***该目录中无任何文件***");
        }
    }
//     public static void deleteFile(File file){
//         File[] files=file.listFiles();
//         if(files.length>0){
//             for(File f:files){
//                 if(f.isDirectory()){//如果是目录
//                     delAllFiles(f, singal); //递归
//                     System.out.println(singal+"目录 ["+f.getName()+"]已删除");
//                     f.delete();     //删除该文件夹
//                 }
//                 else{
//                     System.out.println(singal+" 文件《"+f.getName()+"》已删除");
//                     f.delete();
//                 }//else
//             }//for
//         }//if
//         else{
//             System.out.println("***该目录中无任何文件***");
//         }
//     }
}
