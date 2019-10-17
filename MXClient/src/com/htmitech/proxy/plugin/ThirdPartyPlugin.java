package com.htmitech.proxy.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;

import java.util.List;
import java.util.Map;

/**
 * Tony
 */
public class ThirdPartyPlugin implements ApplicationObserver {

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        /**
         * 比较特殊 直接安装之后直接进入 并没有 将当前版本插入进去，所以第一次的时候将手动添加进入
         */

        AppliationCenterDao centerDao = new AppliationCenterDao(context);
        if(mAppInfo.getmAppVersion() != null){
            centerDao.updateCurrentVersion(mAppInfo.getmAppVersion().getVersion_number(),mAppInfo.getApp_id());

            doStartApplicationWithPackageName(context,mAppInfo,parameters);
        }

    }
    /**
     * 启动第三方 app
     * @param mcontext
     */
    public void doStartApplicationWithPackageName (Context mcontext, AppInfo mAppInfo, Map<String, Object> parameters) {
//        packagename = "com.htmitech.emportalzt";
        // 通过包名获取此 APP 详细信息，包括 Activities、 services 、versioncode 、 name等等
        PackageInfo packageinfo = null;
        try {
            if(mAppInfo.getmAppVersion().getPackage_name() != null){
                packageinfo = mcontext.getPackageManager().getPackageInfo(mAppInfo.getmAppVersion().getPackage_name(), 0 );
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace() ;
        }
        boolean openApk = false;
       if(packageinfo != null){
           PackageManager pckMan = mcontext.getPackageManager();
           List<PackageInfo> packageInfo = pckMan.getInstalledPackages(0);
           for(int i = 0; i < packageInfo.size();i++){
               if(packageInfo.get(i).packageName.equals(packageinfo.packageName)){
                   int versionCode = packageInfo.get(i).versionCode;//安装的版本号
                   long currentVersionCode = mAppInfo.getmAppVersion().getVersion_number();//appinfo中的版本
                   if(currentVersionCode > versionCode){
                       openApk = true;
                   }
               }
           }
       }
        if (packageinfo == null || openApk) {

//            Intent intent = new Intent("android.intent.action.VIEW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            intent.setDataAndType(Uri.fromFile(new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + "/" + mAppInfo.getmAppVersion().getLocalName())), "application/vnd.android.package-archive");
//            mcontext.startActivity(intent);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER +"/" + mAppInfo.getApp_id() + "/" + mAppInfo.getmAppVersion().getLocalName()),
                    "application/vnd.android.package-archive");
            mcontext.startActivity(intent);
            return;
        }

        // 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
        Intent resolveIntent = new Intent(Intent. ACTION_MAIN, null) ;
        resolveIntent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK ) ;
        resolveIntent.addCategory(Intent. CATEGORY_LAUNCHER );
        resolveIntent.setPackage(packageinfo. packageName );

        // 通过 getPackageManager()的 queryIntentActivities 方法遍历
        List<ResolveInfo> resolveinfoList = mcontext.getPackageManager()
                .queryIntentActivities(resolveIntent , 0) ;

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null ) {
            // packagename = 参数 packname
            String packageName = resolveinfo.activityInfo. packageName;
            // 这个就是我们要找的该 APP 的LAUNCHER 的 Activity[组织形式： packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name ;
//            String className = mAppInfo.getmAppVersion().getPackage_main();//服务端配置了哪个入口就从哪里进入
            // LAUNCHER Intent
            Intent intent = new Intent(Intent. ACTION_MAIN) ;
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
            intent.addCategory(Intent. CATEGORY_LAUNCHER );

            // 设置 ComponentName参数 1:packagename 参数2:MainActivity 路径
//            String classNameActivity = "";
//            if(className.contains(".")){
//                classNameActivity = packageName + className;
//            }else{
//                classNameActivity = className;
//            }
//            String classNameActivity = packageName + className;
            ComponentName cn = new ComponentName(packageName , className) ;

            intent.setComponent(cn) ;
            /**
             * 传递参数
             */
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue().toString());
            }
            try{
                mcontext.startActivity(intent) ;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
