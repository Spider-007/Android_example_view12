package com.htmitech.proxy.ApplicationCenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationCenterProxyImp;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationEnum;
import com.htmitech.proxy.util.FileSizeUtil;

import java.io.File;
import java.util.List;

/**
 * 私有处理
 */
public class PrivateDealApplication implements ApplicationCenterProxyImp {

    private AppConcreteSubject mConcreteSubject;


    private Context context;

    public PrivateDealApplication(Context context) {
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        mConcreteSubject = new AppConcreteSubject(context);
        try {
            for (ApplicationEnum a : ApplicationEnum.values()) {
                mConcreteSubject.addApplicationObserver(a.appId, (ApplicationObserver) a.c.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 返回true表示拦截 需要进行安装以及升级操作
     *
     * @param mAppInfo
     * @return
     * @throws NotApplicationException
     */
    @Override
    public int applicationCenterProxy(AppInfo mAppInfo) throws NotApplicationException {
        int isUpdate = intercept(mAppInfo);
        switch (isUpdate) {
            case 3://正常进入
                mConcreteSubject.notifyApplicationObserver(mAppInfo);
                break;
        }
        //表示点击了暂不升级
        if(isUpdate == 4){
            new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("该应用无可用版本！").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }else if(isUpdate == 5){
            new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("您没有该应用的权限！").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }else if (isUpdate != 4 && mAppInfo != null&& mAppInfo.isUpdate()) {//混合插件暂不升级点击后new标签消失问题处理去掉 &&mAppInfo.isUpdate()判断
            String file_location = mAppInfo.getmAppVersion().getFile_location();
            mAppInfo.getmAppVersion().setLocalName(getFileName(file_location));
            if(isUpdate != 3){
                mConcreteSubject.notifyApplicationObserver(mAppInfo);
            }

            /**
             * 将当前版本插入到数据库中
             */
            AppliationCenterDao centerDao = new AppliationCenterDao(context);
            if(!mAppInfo.isUpdate()){//混合插件暂不升级点击后new标签消失问题处理
                centerDao.updateCurrentVersion(mAppInfo.getmAppVersion().getVersion_number(), mAppInfo.getApp_id());
                mAppInfo.getmAppVersion().setLocalName(getFileName(file_location));
            }

            isUpdate = 3;
        }
        return isUpdate;
    }


    /**
     * 此处进行拦截 拦截强制升级
     * <p/>
     * 还缺少对类型的判断是否需要下载
     * <p/>
     * <p/>
     * 缺少 有升级并不是强制升级 ------
     * 有升级并且是强制升级
     * 返回值类型过于单一需要调整
     * <p/>
     * 返回类型修改为int类型  1 位强制升级 2位可暂不升级 弹出框要弹出来 3表示正常进入
     *
     * 新增返回4 表示无最新版本
     *
     * 包含拦截停用应用 和未授权应用
     *
     *
     * return 5
     * 表示返回 应用信息的版本不能为空，如果为空则表示应用有可能未有授权或则取消使用
     */
    public int intercept(AppInfo mAppInfo) {
        boolean isDown = false;

        if(mAppInfo == null){///应用信息的版本不能为空，如果为空则表示应用有可能未有授权或则取消使用
            return 5;
        }
        /**
         * 过滤掉分类应用
         */
        if(mAppInfo.getApp_type() != 7 && mAppInfo.getApp_type() != 0 && mAppInfo.getmAppVersion() == null) //表示无最新版本
        {
            /**
             * 如果没有最新版本了 给一个默认最小版本放入进去
             */
            AppliationCenterDao centerDao = new AppliationCenterDao(context);
            centerDao.updateCurrentVersion(1, mAppInfo.getApp_id());
            return 4;
        }

        if (mAppInfo.getmAppVersion() == null) {
            return 3;
        }
        String file_location = mAppInfo.getmAppVersion().getFile_location();
        if (mAppInfo.getmAppVersion().getFile_location() != null && !mAppInfo.getmAppVersion().getFile_location().equals("")) {

            isDown = interceptAPK(mAppInfo);
        }

        if ((mAppInfo.getApk_flag() == 1 || mAppInfo.getApk_flag() == 2) && isDown) { //强制升级 则进入分发的时候是
            if (mAppInfo.getmAppVersion().getMustupdated() == 1) {
                return 1;
            } else {
                return 2;
            }
        } else {
            /**
             * 将当前版本插入到数据库中
             */
            AppliationCenterDao centerDao = new AppliationCenterDao(context);
            centerDao.updateCurrentVersion(mAppInfo.getmAppVersion().getVersion_number(), mAppInfo.getApp_id());
            mAppInfo.getmAppVersion().setLocalName(getFileName(file_location));
            return 3;
        }

    }

    /**
     * 拦截apk 卸载和删除本地应用中的
     *
     *
     * @param mAppInfo
     * @return
     *
     * TODO 修改拦截方式 修改为 拦截 3 4  h5插件 和第三方插件
     */
    public boolean interceptAPK(AppInfo mAppInfo) {
        boolean isDown = false;
        if (mAppInfo != null && mAppInfo.getmAppVersion() != null) {
            String file_location = mAppInfo.getmAppVersion().getFile_location();
            String suffix = getExtensionName(file_location);

            if (mAppInfo.getApp_type() == 3 || mAppInfo.getApp_type() == 4) { //暂时就这两种类型进行 后续添加
                mAppInfo.getmAppVersion().setSuffix(suffix); //将后缀名赋值
                if (mAppInfo.getApp_type() == 4 ) {  //针对apk进行拦截
                    boolean exists = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + mAppInfo.getApp_id() + File.separator + getFileName(file_location)).exists();
                    if (apkIntercept(context, suffix, mAppInfo)) {
                        isDown = false;
                        mAppInfo.setApk_flag(3);//过滤设置直接进入
                    } else if (exists) {
                        if (mAppInfo.getApk_flag() != 1 || mAppInfo.getApk_flag() != 2) {
                            mAppInfo.setApk_flag(2); //表示桌面已经卸载了 而缓存中存在这个apk
                            mAppInfo.setIsUpdate(true);
                        }
                        isDown = true;
                    } else {
                        if (mAppInfo.getApk_flag() != 1 || mAppInfo.getApk_flag() != 2) {
                            mAppInfo.setApk_flag(1); //此处拿到本地文件已经被删除 而且桌面也已经被卸载
                            mAppInfo.getmAppVersion().setMustupdated(1);//如果本地不存在了 则强制升级
                        }

                        isDown = true;
                    }
                }else if(mAppInfo.getApp_type() == 3 ){
                    double fileSize = FileSizeUtil.getFileOrFilesSize(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + mAppInfo.getApp_id(), 3);
                    if(fileSize == 0){
                        mAppInfo.getmAppVersion().setMustupdated(1);//如果本地不存在了 则强制升级
                        mAppInfo.setApk_flag(2);
                    }
                    isDown = true;
                }else{
                    isDown = true;
                }
            }
        }
        return isDown;

    }

    /**
     * 获取url扩展名
     *
     * @param filename
     * @return
     */
    public String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


    public String getFileName(String filename) {
        if(TextUtils.isEmpty(filename)){
            return "";
        }
        filename = filename.substring(filename.lastIndexOf("/") + 1);
        return filename;
    }

    public boolean apkIntercept(Context context, String suffix, AppInfo mAppInfo) {
        if (mAppInfo.getApp_type() == 4) {
            String packageName = mAppInfo.getmAppVersion().getPackage_name();
            PackageManager pckMan = context.getPackageManager();
            List<PackageInfo> packageInfo = pckMan.getInstalledPackages(0);
            for(int i = 0; i < packageInfo.size();i++){
                if(packageInfo.get(i).packageName.equals(packageName)){
                    int versionCode = packageInfo.get(i).versionCode;
                    long currentVersionCode = mAppInfo.getmAppVersion().getVersion_number();
                    if(currentVersionCode > versionCode){
                        return false;
                    }
                }
            }
//            for (AppVersionConfig mAppVersionConfig : mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
//                if ("packagename".equals(mAppVersionConfig.getConfig_code())) {
//                    packageName = mAppVersionConfig.getConfig_value().toString();
//                    break;
//                }
//            }
            boolean isApkInstall = isPkgInstalled(context, packageName);
            return isApkInstall;
        }
        return false;
    }


    /**
     * 获取本地是否安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    private boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;

        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
