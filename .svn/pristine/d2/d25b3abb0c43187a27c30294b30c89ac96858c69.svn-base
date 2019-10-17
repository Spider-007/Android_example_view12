package com.htmitech.proxy.util;

import android.util.Log;

import com.htmitech.emportal.common.CommonSettings;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by htrf-pc on 2017/5/26.
 */
public class FileSizeUtil {
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String[] getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String[] FormetFileSize(long fileS) {
        try {

            DecimalFormat df = new DecimalFormat("#.00");
            String fileSizeString = "";
            String wrongSize = "0";

            if (fileS == 0) {
                return new String[]{wrongSize, "B"};
            }
            if (fileS < 1024) {
                fileSizeString = df.format((double) fileS);
                return new String[]{fileSizeString, "B"};
            } else if (fileS < 1048576) {
                fileSizeString = df.format((double) fileS / 1024);
                return new String[]{fileSizeString, "KB"};
            } else if (fileS < 1073741824) {
                fileSizeString = df.format((double) fileS / 1048576);
                return new String[]{fileSizeString, "MB"};
            } else {
                fileSizeString = df.format((double) fileS / 1073741824);
                return new String[]{fileSizeString, "GB"};
            }
        } catch (Exception e) {

        }
        return new String[]{"0","B"};
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        if(fileS == 0){
            return 0;
        }
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = (double) fileS;
                break;
            case SIZETYPE_KB:
                fileSizeLong = (double) fileS / 1024;
                break;
            case SIZETYPE_MB:
                fileSizeLong = (double) fileS / 1048576;
                break;
            case SIZETYPE_GB:
                fileSizeLong = (double) fileS / 1073741824;
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    public static String getTempPhotoFileName(String user_id,String portal_id,String app_id) {
        // 创建一个以当前时间为名称的文件
        File file = new File(CommonSettings.DEFAULT_CACHE_FOLDER
                + File.separator + user_id + File.separator + portal_id + File.separator +app_id+ "/photo");
        if (!file.exists()) {
            file.mkdirs();
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return CommonSettings.DEFAULT_CACHE_FOLDER
                + File.separator + user_id + File.separator + portal_id + File.separator +app_id + "/photo/" + dateFormat.format(date) + ".jpg";
    }
}