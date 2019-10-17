//package com.htmitech_updown.updownloadmanagement.receive;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.util.Log;
//
//public class WifiReceiver extends BroadcastReceiver {
//    public static final String NET_CHANGE = "net_change";
//    //标记当前网络状态，0为无可用网络状态，1表示有。
//    public static final String NET_TYPE = "net_type";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        try {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            //移动数据
//            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            //wifi网络
//            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//                Log.e("TAG", "upload网络已断开");
//                return;
//            }
//
//            if (!wifiNetInfo.isConnected()) {
//                Log.e("TAG", "uploadWIFI已经断开");
//                return;
//            }
//
//            if (wifiNetInfo.isConnected()) {
//                Log.e("TAG", "uploadWIFI已经连接");
//                return;
//            }
//
//            if (mobNetInfo.isConnected()) {
////            手机没有处于wifi网络而是处于移动网络
//                Log.e("TAG", "upload 4G 已经连接");
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
