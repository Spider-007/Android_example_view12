package com.htmitech.proxy.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 静默安装
 *
 *   <!– 以下是静默安装apk所需要到权限 –>
 <uses-permission android:name=“android.permission.INSTALL_PACKAGES” />
 <uses-permission android:name=“android.permission.DELETE_PACKAGES” />
 <uses-permission android:name=“android.permission.CLEAR_APP_CACHE” />
 <uses-permission android:name=“android.permission.CLEAR_APP_USER_DATA” />
 <uses-permission android:name=“android.permission.READ_PHONE_STATE” />
 */
public class APKInstallUtil {

    /**
     * 软件静默安装
     * @param apkAbsolutePath apk文件所在路径
     * @return 安装结果:获取到的result值<br>
     *
     * 如果安装成功的话是“
     * pkg: /data/local/tmp/Calculator.apk  /nSuccess”，<br>
     * 如果是失败的话，则没有结尾的“Success”。
     */
//    public static String silentInstall(String apkAbsolutePath) {
//
//        String[] args = { "pm", "install", "-r", apkAbsolutePath };
//        String result = "";
//        ProcessBuilder processBuilder = new ProcessBuilder(args);
//        Process process = null;
//        InputStream errIs = null;
//        InputStream inIs = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int read = -1;
//            process = processBuilder.start();
//            errIs = process.getErrorStream();
//            while ((read = errIs.read()) != -1) {
//                baos.write(read);
//            }
//            baos.write("/n".getBytes());
//            inIs = process.getInputStream();
//            while ((read = inIs.read()) != -1) {
//                baos.write(read);
//            }
//            byte[] data = baos.toByteArray();
//            result = new String(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (errIs != null) {
//                    errIs.close();
//                }
//                if (inIs != null) {
//                    inIs.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (process != null) {
//                process.destroy();
//            }
//        }
//        return result;
//    }


    public static void silentInstall(String apkAbsolutePath){
        Process process = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 请求root
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            // 调用安装
            System.out.println(apkAbsolutePath);
            out.write(("pm install -r " + apkAbsolutePath + "").getBytes());
                    in = process.getInputStream();
            int len = 0;
            int readLen = 0;
            byte[] bs = new byte[256];
            //读出所有的输出数据
            while (-1 != (readLen = in.read(bs))) {
                len = len + readLen;
                //如果读的数据大于缓存区。则停止
                if (len > bs.length) {
                    len -= readLen;
                    break;
                }
            }
            String state = new String(bs, 0, len);
            if (state.startsWith("Success")) {
                // 安装成功后的操作
            }
            else {
                //静默安装失败，使用手动安装
                System.out.println("安装失败");
//                installByUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
