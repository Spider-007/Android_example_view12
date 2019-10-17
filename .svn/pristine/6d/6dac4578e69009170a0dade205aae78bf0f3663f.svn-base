package htmitech.com.componentlibrary.unit;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.http.conn.util.InetAddressUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import htmitech.com.componentlibrary.MediaPlayerActivity;

public class Utils {

    public static boolean sdcardAvailable() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void toast(Context context, String message, int duration) {
        if (isApplicationForeground(context)) {
            Toast.makeText(context, message, duration).show();
        }
    }

    public static void toast(Context context, int messageID, int duration) {
        if (isApplicationForeground(context)) {
            Toast.makeText(context, messageID, duration).show();
        }
    }

    public static boolean checkConnectState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null) {
            return activeInfo.isConnected();
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static boolean isApplicationForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager kgm = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        boolean isForeground = false;
        boolean isScreenActive = true;
        if (kgm.inKeyguardRestrictedInputMode()) {
            isScreenActive = false;
        }

        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                isForeground = true;
            }
        }
        return isForeground && isScreenActive;
    }

    public static boolean checkMobileNumber(String mobile) {
        ///^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i
        //^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$
        //^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        String check = "^((\\+{0,1}86){0,1})((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mobile);
        return matcher.matches();
    }


    /**
     * 计算文件的大小（G,M,KB）
     */
    public static String getFileSize(double size) {
        double dis = size / 1024 / 1024;
        if (dis > 1024) {
            return new java.text.DecimalFormat("#0.00").format(size / 1024 / 1024 / 1024) + "G";
        }
        if (dis > 1) {
            return new java.text.DecimalFormat("#0.00").format(dis) + "M";
        }
        return new java.text.DecimalFormat("#0.00").format(size / 1024) + "KB";
    }

    public static String iso8601ToCustomerDate(String iso8601, String format) {
        if (iso8601 == null || iso8601.trim().length() == 0) {
            return "";
        }

        DateTime dateTime = null;
        try {
            dateTime = new DateTime(iso8601);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dateTime == null) {
            return "";
        }

        if (format == null || "".equals(format)) {
            return String.valueOf(dateTime.getMillis());
        } else {
            return dateTime.toString(format);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            mActivityManager.clearApplicationUserData();
        }
    }

    /*
    * 获取剩余内存
    * */
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(context.getApplicationContext().ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem / 1024 / 1024;
    }
   /*
    * 获取手机ip地址
    * */
    public static String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {

                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;

    }

    /*
    * 获取手机总运行内存
    * */
//获取总内存大小
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static long getTotalMemorySize(Context context) {
        long size = 0;
        //获取ActivityManager管理，要获取【运行相关】的信息，与运行相关的信息有关
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();//outInfo对象里面包含了内存相关的信息
        activityManager.getMemoryInfo(outInfo);//把内存相关的信息传递到outInfo里面C++思想

        size = outInfo.totalMem;

        //通过读取配置文件方式获取总内大小。文件目录：/proc/meminfo

        return size / 1024 / 1024;
    }

    /*
    * 获取屏幕分辨率
    * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getScreenResolution(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) (context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        wm.getDefaultDisplay().getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return height + "*" + width;
    }

    /*
    * 获取联网方式
    * */
    public static String GetNetworkType(Context context) {
        String strNetworkType = "";
        ConnectivityManager manager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
      /*  NetworkInfo networkInfo = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE).getActiveNetworkInfo();*/
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    public static boolean isBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /*
    *    换肤拷贝皮肤文件到sd卡
    * */
    public static void copyFileToSD(Context context, String strOutFileName, String fileName) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = context.getResources().getAssets().open(fileName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    //判断当前是否联网
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static int getViewHeight(View view) //获得某组件的高度
    {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 临时使用 调用系统播放器
     *
     * @param paths
     */
    public static void playVideo(Context context,String paths) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
////        String path = Environment.getExternalStorageDirectory().getPath()+ "/1.mp4";//该路径可以自定义
//        File file = new File(paths);
//        Uri uri = Uri.fromFile(file);
//        intent.setDataAndType(uri, "video/*");
//        context.startActivity(intent);

        Intent intent1 = new Intent();
        intent1.setClassName(context,"com.htmitech.video.activity.VideoDetailActivity");
        intent1.putExtra("videoPath", paths);
        intent1.putExtra("videoName", paths.substring(paths.lastIndexOf("/")+ 1));
        context.startActivity(intent1);
    }

    /**
     * 播放指定名称的歌曲
     * @param audioPath 指定默认播放的音乐
     */
    public static void playAudio(Context context,String audioPath){
        Intent mIntent = new Intent(context,MediaPlayerActivity.class);
        mIntent.putExtra("url",audioPath);
//        mIntent.setAction(android.content.Intent.ACTION_VIEW);
//        Uri uri = Uri.parse("file:///"+audioPath);
//        mIntent.setDataAndType(uri , "audio/mp3");
        context.startActivity(mIntent);


    }
}
