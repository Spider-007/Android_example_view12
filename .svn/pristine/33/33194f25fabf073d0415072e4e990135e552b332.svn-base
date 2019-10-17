package com.htmitech.commonx.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件读写帮助类.
 * 
 * @author liuweining
 *
 * @date Jan 14, 2015
 *
 */
public class FileHelper {
    private static final String TAG = "FileHelper";

    private FileHelper() {

    }

    /**
     * 以UTF-8格式读取文件内容，返回字符串.
     * 
     * @param file
     * @return
     */
    public static String readFileContent(File file) {
        String result = "";

        if (file == null) {
            LogUtil.w(TAG, "readText, file is null, return");
            return result;
        }

        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            int fileSize = (int) file.length();
            byte[] buffer = new byte[fileSize];

            inputStream.read(buffer, 0, buffer.length);

            result = new String(buffer);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } catch (IOException e2) {
            LogUtil.e(TAG, e2.getMessage(), e2);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            }
        }

        return result;
    }

    /**
     * 以UTF-8格式读取文件内容，并返回字符串.
     * 
     * @param filePath
     * @return
     */
    public static String readFileContent(String filePath) {
        return readFileContent(new File(filePath));
    }

    /**
     * 将String写入文件.
     * 
     * @param content 写入内容
     * @param filePath 文件路径
     * @param isOverride 是否覆盖原文件.
     * @return
     */
    public static boolean writeContentToFile(String content, String filePath, boolean isOverride) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(filePath)) {
            LogUtil.w(TAG, "writeContentToFile content or filePath is null, return false");
            return false;
        }
        File file = new File(filePath);
        BufferedOutputStream bos = null;
        try {
            // 如果文件上级目录不存在，先创建目录
            if (!IOUtils.isFileExist(file)) {
                if (file.getParentFile() == null) {
                    LogUtil.w("write, file.getParentFile() == null, return false");
                    return false;
                } else {
                    file.getParentFile().mkdirs();
                }
            }

            // 如果需要覆盖文件，且文件已存在，删除原文件
            if (isOverride && IOUtils.isFileExist(file)) {
                IOUtils.delFile(file);
            }

            bos = new BufferedOutputStream(new FileOutputStream(file, true));
            bos.write(content.getBytes());
            bos.flush();
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 解压文件.
     * 
     * warning: 解压完毕会删除原zip包!
     * 
     * @param sourceFile 待解压文件
     * @param targetPath 解压路径
     * @return
     */
    public static boolean unzipFile(File sourceFile, String targetPath) {
        if (sourceFile == null || !sourceFile.exists()) {
            return false;
        }
        File pathFile = new File(targetPath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        boolean success = false;

        ZipFile zf = null;
        byte buffer[] = new byte[1024 * 256];
        try {
            zf = new ZipFile(sourceFile);
            Enumeration<?> entries = zf.entries();
            if (entries != null) {
                while (entries.hasMoreElements()) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    InputStream in = zf.getInputStream(entry);
                    String str = targetPath + File.separator + entry.getName();
                    if (entry.isDirectory()) {
                        File targetFile = new File(str);
                        if (!targetFile.exists()) {
                            targetFile.mkdirs();
                        }
                        continue;
                    }
                    File targetFile = new File(str);
                    if (!targetFile.exists()) {
                        targetFile.createNewFile();
                    } else {
                        targetFile.delete();
                        targetFile.createNewFile();
                    }
                    OutputStream out = new FileOutputStream(targetFile);
                    int realLength;
                    while ((realLength = in.read(buffer)) > 0) {
                        out.write(buffer, 0, realLength);
                    }

                    out.flush();
                    in.close();
                    out.close();
                    success = true;
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            success = false;
        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        sourceFile.delete();

        return success;
    }

    /**
     * 从assets里读取内容.
     * 
     * @param path
     * @return
     */
    public static String readAssetsFile(Context context, String path) {
        String result = "";
        InputStream is = null;

        if (null == context) {
            LogUtil.w(TAG, "readAssetsFile, context is null, return");
            return result;
        }

        try {
            AssetManager manager = context.getAssets();
            is = manager.open(path);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);

            result = new String(buffer);
            return result;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return result;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                }
            }
        }
    }
}
