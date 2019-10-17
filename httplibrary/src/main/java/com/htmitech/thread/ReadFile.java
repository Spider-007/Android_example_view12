package com.htmitech.thread;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.htmitech.dao.UnitDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.myEnum.TableTetweenEnum;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * 读取文件以及插入数据库
 *
 * @author Tony
 */
public class ReadFile {
    public long SynchronizationeventStamp; // 同步時間戳
    public static final String SEG = "ú";
    public static final int BIG = 200;
    private ArrayList<String[]> tempTableValue = new ArrayList<String[]>();
    private Context context;
    private static final String SUFFIX = "!";
    private UnitDao unitDao;
    private int index = 0;

    public ReadFile(Context context) {
        this.context = context;
        index = 0;
        unitDao = new UnitDao(context);
        unitDao.getDb().beginTransaction();
    }

    public void readTxtFile(File file) {
        try {
            String encoding = "utf-8";
//			File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String SynchronizationeventStamp = bufferedReader
                        .readLine();// 读取时间戳
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
//				format.format(new Data(SynchronizationeventStamp));
                String year = SynchronizationeventStamp.substring(0, 4);
                String month = SynchronizationeventStamp.substring(4, 6);
                String date = SynchronizationeventStamp.substring(6, 8);
                String hours = SynchronizationeventStamp.substring(8, 10);
                String minutes = SynchronizationeventStamp.substring(10, 12);
                String seconds = SynchronizationeventStamp.substring(12, 14);
                SynchronizationeventStamp = year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
                PreferenceUtils.saveLastTime(SynchronizationeventStamp); //保存时间戳
                int position = 2;
                String[] tableList = null;
                do {
                    String tableTag = bufferedReader.readLine(); // 读取表结构以及内容的表示
                    if (!TextUtils.isEmpty(tableTag)) {
                        tableList = tableTag.split(SEG);
                        Log.d("ReadFiled", "tableList == " + tableList);
                    } else {
                        break;
                    }
                    int currentCulumnCount = 0;
                    try {
                        currentCulumnCount = Integer.parseInt(tableList[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (currentCulumnCount == 0) {
                        continue;
                    }

                    index++;
                    tempTableValue.clear();

                    String currentCulumnTag = tableList[0];


                    String isDelete = "";
                    String[] columnNameList = null;
                    if (!currentCulumnTag.equals("e-sql")) {
                        isDelete = tableList[2];
                        String columnName = bufferedReader.readLine(); // 读取字段名称
                        position++;
                        columnNameList = columnName.split(SEG); //对应字段的数组
                        String tabName = TableTetweenEnum.getTabName(currentCulumnTag);
                        if (!tabName.equals(""))
                            try{
                                unitDao.addColumn(tabName,columnNameList);
                            }catch (Exception e){

                            }

                    }

                    int startNumber = position;
                    int index = (startNumber + currentCulumnCount) - position; //表示当前下标和表内容下标是否一致
                    while (index > 0) {
                        position++;
                        index = (startNumber + currentCulumnCount) - position;
                        String currentCulumnValue = bufferedReader.readLine();// 读取字段对应的内容
//                        if (currentCulumnTag.equals("ddl-sql")) {
//                            alterTable(currentCulumnValue);
//                        }
                        if (currentCulumnTag.equals("e-sql")) {
                            unitDao.execSQL(currentCulumnValue);
                        } else {

                            currentCulumnValue += SUFFIX;
                            String[] currentCulumnValueList = currentCulumnValue
                                    .split(SEG);
                            removeSuffix(currentCulumnValueList);
                            tempTableValue.add(currentCulumnValueList);
                            if (tempTableValue.size() >= BIG || index == 0) {
                                try {
                                    insertTable(columnNameList, tempTableValue, currentCulumnTag, isDelete);
                                    tempTableValue.clear();
                                } catch (Exception e) {
                                    break;
                                }
                            }
                        }

                    }

                } while (tableList != null);
                read.close();
                file.delete();//读取完成 将文件删除/
                unitDao.getDb().setTransactionSuccessful();
            } else {
                Log.d("File", "找不到指定的文件");
            }
        } catch (Exception e) {
            Log.d("File", "读取文件内容出错");
            e.printStackTrace();
        } finally {
            try{
                unitDao.getDb().endTransaction();
            }catch (Exception e){

            }

        }
    }

    //去除后缀
    public void removeSuffix(String[] s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i].contains("!")) {
                s[i] = s[i].replace("!", "");
            }
        }
    }

    /**
     * 插入语句
     *
     * @param culumnList
     * @param tempList
     * @param tag
     */
    public void insertTable(String[] culumnList, ArrayList<String[]> tempList,
                            String tag, String isDelete) {

        TableTetweenEnum nTableTetweenEnum = TableTetweenEnum.getName(tag);
        if(nTableTetweenEnum == null){
            return;
        }
        switch (nTableTetweenEnum) {
//		case SYS_DEPARTMENT:
//			mSYS_DepartmentDAO.insert(culumnList, tempList,isDelete);
//			break;
            default:
                String tabName = TableTetweenEnum.getTabName(tag);
                if (!tabName.equals("")) {
                    unitDao.initApplicationCenterDao(tabName, culumnList, tempList, isDelete);
                }
                break;
        }
        ComponentInit.getInstance().getSuccess().setProgressbar("正在同步数据...");
    }

    /**
     * 独立执行服务端变化的字段语句
     * @param currentCulumn
     */
    public void alterTable(String currentCulumn) {
        try{
            String[] culumnSlit = currentCulumn.split(",");
            String alterTableStr = culumnSlit[culumnSlit.length - 1];
            String tableName = culumnSlit[0];
            String culunmName = culumnSlit[1];
            String type = culumnSlit[3];
            if(type.equals("add")){
                if(!unitDao.checkColumnExist(tableName,culunmName)){
                    unitDao.execSQL(alterTableStr);
                }
            }else{
                unitDao.execSQL(alterTableStr);
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }



    }
}
