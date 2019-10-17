package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import htmitech.com.componentlibrary.unit.Utils;

public class ServiceBookActivity extends BaseFragmentActivity {

    public static String serviceBookSdcardPath = "mnt/sdcard/fanxin";
    public static final String SERVICEBOOK_FILENAME = "servicebook"; // 这个是文件名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_book);
        //查看
        File file = new File(openServiceBook(this));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), getFileType("pdf"));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Utils.toast(this, "系统无法打开文件！请下载相关辅助软件！", Toast.LENGTH_SHORT);

        } finally {
            finish();
        }
    }

    /***/
    public String getFileType(String type) {
        String aimTypeString = "";
        if (type.equals("html") || type.equals("htm")) { // 打开HTML文件
            aimTypeString = "text/html";
        } else if (type.equals("png") || type.equals("jpg")) {// 打开图片文件
            aimTypeString = "image/*";
        } else if (type.equals("pdf") || type.equals("pdfx")) {// 打开PDF文件的intent
            aimTypeString = "application/pdf";
        } else if (type.equals("txt")) {// 打开文本文件的intent
            aimTypeString = "text/plain";
        } else if (type.equals("doc") || type.equals("docx")) {// 打开文打开Word文件
            aimTypeString = "application/msword";
        } else if (type.equals("xls") || type.equals("xlsx")) {// 打开Excel文件
            aimTypeString = "application/vnd.ms-excel";
        } else if (type.equals("ppt")) {// 打开PPT文件
            aimTypeString = "application/vnd.ms-powerpoint";
        } else {
        }
        return aimTypeString;
    }

    public String openServiceBook(Context context) {
        try {
            String SeviceBookFilename = serviceBookSdcardPath + "/" + SERVICEBOOK_FILENAME + ".pdf";
            Log.d("DBManager", SeviceBookFilename);
            File dir = new File(serviceBookSdcardPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (new File(SeviceBookFilename).exists()) {
                deleteFile(SeviceBookFilename);
            }
            if (!(new File(SeviceBookFilename)).exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.fwzn);
                FileOutputStream fos = new FileOutputStream(SeviceBookFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            return SeviceBookFilename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
