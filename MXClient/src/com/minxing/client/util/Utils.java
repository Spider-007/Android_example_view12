package com.minxing.client.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.minxing.client.upgrade.AppUpgradeInfo;
import com.minxing.client.upgrade.SmartUpgradeHelper;

import org.apache.http.conn.util.InetAddressUtils;
import org.greenrobot.eventbus.EventBus;
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

import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

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

    public static void showSystemDialog(Context context, String title, String tip, final DialogInterface.OnClickListener okListener,
                                        final DialogInterface.OnClickListener cancelListener, boolean isCancelable) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(tip);

        builder.setPositiveButton(R.string.ok, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okListener.onClick(dialog, which);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancelListener != null) {
                    cancelListener.onClick(dialog, which);
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(isCancelable);
        dialog.show();
    }

    public static void showUpgradeDialog(final Context context, final AppUpgradeInfo upgradeInfo) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle(String.format(context.getString(R.string.app_upgrade), upgradeInfo.getVersion()));
        String message = buildUpgradeMessage(context, upgradeInfo);
        if (message != null && !"".equals(message)) {
            builder.setMessage(message);
        }

        builder.setPositiveButton(R.string.ok, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                BookInit.getInstance().setBoradCast(false);
                PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
                BookInit.getInstance().setOrgUser(null);
                BookInit.getInstance().bitmapClean();

                SmartUpgradeHelper updates = new SmartUpgradeHelper(context, upgradeInfo);
                updates.startUpdate(context);
                dialog.dismiss();
            }
        });
        if (!upgradeInfo.isMandatoryUpgrade()) {
            builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setCancelable(!upgradeInfo.isMandatoryUpgrade());
        dialog.show();
    }

    public static String buildUpgradeMessage(Context context, AppUpgradeInfo upgradeInfo) {
        StringBuffer sb = new StringBuffer();

        if (upgradeInfo.getDescription() != null && !"".equals(upgradeInfo.getDescription())) {
            sb.append(upgradeInfo.getDescription()).append("\r\n\r\n");
        }
        if (upgradeInfo.getUpdateTime() != null && !"".equals(upgradeInfo.getUpdateTime())) {
            sb.append(context.getString(R.string.upgrade_info_time));
            sb.append(upgradeInfo.getUpdateTime()).append("\r\n");
        }

        if (upgradeInfo.getSize() > 0) {
            sb.append(context.getString(R.string.upgrade_info_size));
            if (upgradeInfo.isSmartUpgrade()) {
                sb.append(getFileSize(upgradeInfo.getSmart_size()));
            } else {
                sb.append(getFileSize(upgradeInfo.getSize()));
            }
        }
        return sb.toString();
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
        intent.setAction(android.content.Intent.ACTION_VIEW);
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
    public static long getAvailMemory() {
        ActivityManager am = (ActivityManager) HtmitechApplication.getApplication().getSystemService(HtmitechApplication.getApplication().ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem / 1024 / 1024;
    }

    /*
    * 获取经纬度
    * */
    private static double latitude = 0.0;
    private static double longitude = 0.0;

    public static Location getPosition(Activity context) {
        LocationManager locationManager = (LocationManager) HtmitechApplication.getApplication().getSystemService(Context.LOCATION_SERVICE);
        //api23以上需要动态授权 定位
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //2.如果没有授权，那么申请授权
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 321);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            return location;
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
                return location;
            }
        }
        return null;
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
    public static String getScreenResolution() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) (HtmitechApplication.getApplication().getSystemService(Context.WINDOW_SERVICE));
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
    * 换肤功能
    * */
    public static void changeSkin(final Context context, String strOutFileName, String fileName, final ConfigStyleUtil.FinishPortalSwitch mFinishPortalSwitch) {
//		SkinManager.getInstance().restoreDefaultTheme();
        try {
            copyFileToSD(context, strOutFileName, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File skin = new File(strOutFileName);
        if (skin == null || !skin.exists()) {
            Toast.makeText(context, "请检查皮肤文件是否存在",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        SkinManager.getInstance().load(skin.getAbsolutePath(), new ILoaderListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess() {
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "portalSwitch";
                EventBus.getDefault().post(mClassEvent);  //发送桌面重启
                if (mFinishPortalSwitch != null)
                    mFinishPortalSwitch.finishPortalSwitchActivity();
            }

            @Override
            public void onFailed() {
                Toast.makeText(context, "皮肤资源不存在！",
                        Toast.LENGTH_SHORT).show();
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "portalSwitch";
                EventBus.getDefault().post(mClassEvent);  //发送桌面重启
                if (mFinishPortalSwitch != null)
                    mFinishPortalSwitch.finishPortalSwitchActivity();
            }
        });
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
    public static boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) HtmitechApplication.instance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

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

}
