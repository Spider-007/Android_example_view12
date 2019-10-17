package com.htmitech.commonx.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 字节流转换的工具类.
 * 
 * @author liuweining
 * 
 */
public class StreamExtUtil {
    private static final String DEFUALT_ENCODING = "UTF-8";
    private static final int DEFUALT_PICTURE_QUALITY = 100; // 默认图片压缩质量
    private static final int BUFFER_SIZE = 1024; // 默认缓存为1k

    private StreamExtUtil() {

    }

    /**
     * 将文件读取为String,默认以utf-8编码格式读取,读取整个文件,大文件慎用。
     * 
     * @param fileInputStream 要读取的文件流
     * @return String
     */
    public static String stream2String(FileInputStream fileInputStream) {
        return stream2String(fileInputStream, DEFUALT_ENCODING);
    }

    /**
     * 将文件读取为String,可以指定源文件的编码格式,读取整个文件,文件大于500K慎用。
     * 
     * @param fileInputStream 要读取的文件流
     * @param encoding 字符编码
     * @return String
     */
    public static String stream2String(FileInputStream fileInputStream, String encoding) {

        if (null == fileInputStream) {
            LogUtil.w("stream2String fileInputStream is invalidate, return empty string");
            return "";
        }

        String result = "";
        StringBuffer sb = new StringBuffer();
        int readLength = 0;

        byte[] buffer = new byte[BUFFER_SIZE];
        byte[] byteArray = null;

        try {
            while ((readLength = fileInputStream.read(buffer)) != -1) {
                byteArray = new byte[readLength];
                System.arraycopy(buffer, 0, byteArray, 0, readLength);
                sb.append(new String(byteArray, encoding));
            }

            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            LogUtil.w("stream2String, UnsupportedEncodingException encoding:" + encoding);
        } catch (IOException e) {
            LogUtil.w("stream2String IOException");
        } finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (IOException e2) {
                LogUtil.w("stream2String, close IOException");
            }
        }

        return result;

    }

    /**
     * 将图片保存至指定位置。客户端应先检查指定路径是否可写，有充足空间。
     * 
     * @param bitmap 要保存的图片
     * @param dirPath 保存图片的绝对路径
     * @param picName 图片的名称
     * @param override 是否覆盖
     * @return boolean
     */
    public static boolean writeBitmapToFile(Bitmap bitmap, String dirPath, String picName, boolean override) {
        if (null == bitmap || TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(picName)) {
            LogUtil.w("writeBitmapToFile, bitmap or filePath is null, return false");
            return false;
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File img = new File(dirPath, picName);
        if (override && img.exists()) {
            img.delete();
        }

        BufferedOutputStream bos = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(img));
            bitmap.compress(Bitmap.CompressFormat.JPEG, DEFUALT_PICTURE_QUALITY, bos);
            bos.flush();
            return true;
        } catch (Exception e) {
            LogUtil.w("writeBitmapToFile, error");
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (Exception e2) {
                LogUtil.w("writeBitmapToFile, close bos error");
            }
        }

        return false;
    }
}
