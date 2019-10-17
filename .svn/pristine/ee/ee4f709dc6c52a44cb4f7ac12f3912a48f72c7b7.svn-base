package com.htmitech.proxy.managerApp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.emportal.R;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppReturnClass;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.BaseApplicationEnum;
import com.htmitech.proxy.pop.AdvertPopWindow;
import com.htmitech.proxy.util.HTActivityUnit;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.tab.MenuTabItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.content.ConcreteLogin;

/**
 * 基础应用的入口 以及插件
 */
public class ClentAppUnit implements CallBackLeftJC {
    public Context context;

    private ConcreteLogin mConcreteLogin;

    private static ClentAppUnit mClentAppUnit = null;

    public static ClentAppUnit getInstance(Context context) {
        if (mClentAppUnit == null) {
            mClentAppUnit = new ClentAppUnit(context);
        }
        return mClentAppUnit;
    }

    public ClentAppUnit setContext(Context context) {
        this.context = context;
        return this;
    }

    private ClentAppUnit(Context context) {
        this.context = context;
        mConcreteLogin = ConcreteLogin.getInstance();
    }

    /**
     * @param mApplicationAllEnum
     * @param map
     */
    public void setActivitys(ApplicationAllEnum mApplicationAllEnum, Map<String, Object> map) throws NotApplicationException {
//        AppReturnClass c = BaseApplicationEnum.getActivity(mApplicationAllEnum, this);
//
//
//        if(c == null){
//            throw new NotApplicationException("BaseApplicationEnum 没有找到对应的Activity");
//        }
//
////        if(!c.value.equals("MXOnClick")){
////            HTActivityUnit.switchTo((Activity) context, c.mClass, map);
////        }

        setActivity(mApplicationAllEnum, map);
    }

    /**
     * 传递至需要增加传递参数
     *
     * @param appInfo
     * @param map
     */
    public void setActivity(AppInfo appInfo, Map<String, Object> map) throws NotApplicationException {
        if (appInfo == null) {
            return;
        }
        ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(appInfo);
        if (mApplicationAllEnum == null) {
            throw new NotApplicationException("ApplicationAllEnum为空了");
        }
        mApplicationAllEnum.mAppInfo = appInfo;
        setActivity(mApplicationAllEnum, map);

    }

    /**
     * @param appInfo
     */
    public void setActivity(AppInfo appInfo) {

        ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(appInfo);
        if (mApplicationAllEnum == null) {
            //如果在基础应用中没有找到这个枚举值的话，那么用插件进行替换
            //还得重新进行赋值，将插件的appId url appName重新赋值
            mApplicationAllEnum = ApplicationAllEnum.CJ;

        }
        AppReturnClass c = BaseApplicationEnum.getActivity(mApplicationAllEnum, this);
        if (c != null) {
            if (!c.value.equals("MXOnClick")) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("extra_datas", "ClientTabActivity");
                map.put("addressFragmentType", Constant.HOME_INIT);
                map.put("app_id", mApplicationAllEnum.appId);
                HTActivityUnit.switchTo((Activity) context, c.mClass, map);
            }
        } else {
            ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(context);
            try {
                mProxyDealApplication.applicationCenterProxy(mApplicationAllEnum.mAppInfo);
            } catch (NotApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    public void setActivity(ApplicationAllEnum mApplicationAllEnum) throws NotApplicationException {
        setActivity(mApplicationAllEnum, new HashMap<String, Object>());
    }

    /**
     * 获取一个Activity实例对象
     *
     * @param mApplicationAllEnum
     * @param map
     * @return
     */
    public Intent getActivity(ApplicationAllEnum mApplicationAllEnum, Map<String, Object> map) throws NotApplicationException {
        mApplicationAllEnum.map = map;
        AppReturnClass c = BaseApplicationEnum.getActivity(mApplicationAllEnum, this);
        if (c != null) {
            if (!c.value.equals("MXOnClick")) {
                map.put("extra_datas", "ClientTabActivity");
                map.put("addressFragmentType", Constant.HOME_INIT);

                if (mApplicationAllEnum.mAppInfo != null && mApplicationAllEnum.mAppInfo.getmAppVersion() != null && mApplicationAllEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                    map.put("appName", mApplicationAllEnum.mAppInfo.getApp_name());
                    if(Constant.IS_DZKF && ApplicationAllEnum.DB.mAppInfo != null){

                        map.put("appShortName", ApplicationAllEnum.DB.mAppInfo.getApp_shortname());

                    }else{
                        map.put("appShortName", mApplicationAllEnum.mAppInfo.getApp_shortname());
                    }
                    map.put("app_id",mApplicationAllEnum.mAppInfo.getApp_id()+ ""); //传入工作流构建需要的appId
                    map.put("appCode",mApplicationAllEnum.mAppInfo.getApp_code());
                    map.put("app_version_id", mApplicationAllEnum.mAppInfo.getmAppVersion().getApp_version_id() + "");
                    for (AppVersionConfig mAppVersionConfig : mApplicationAllEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                        if (!map.containsKey(mAppVersionConfig.getConfig_code())) {
                            map.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                        }
                    }
                }
//                if(mApplicationAllEnum.mAppInfo != null){
//                    map.put("app_id",mApplicationAllEnum.mAppInfo.getApp_id()+ ""); //传入工作流构建需要的appId
//                    map.put("Type",true);
//                    map.put("appName",mApplicationAllEnum.mAppInfo.getApp_name());
//                }
//                HTActivityUnit.switchTo((Activity) context, c.mClass, map);
            }
            return HTActivityUnit.getSwitchActivity(context, c.mClass, map);
        }
        throw new NotApplicationException("没有发现对应的Activity");
    }

    /**
     * @param mApplicationAllEnum
     */
    public void setActivity(ApplicationAllEnum mApplicationAllEnum, Map<String, Object> map) throws NotApplicationException {
        mApplicationAllEnum.map = map;
        AppReturnClass c = BaseApplicationEnum.getActivity(mApplicationAllEnum, this);

        if (c != null) {
            if (!c.value.equals("MXOnClick")) {
                map.put("extra_datas", "ClientTabActivity");
                map.put("addressFragmentType", Constant.HOME_INIT);

                if (mApplicationAllEnum.mAppInfo != null && mApplicationAllEnum.mAppInfo.getmAppVersion() != null && mApplicationAllEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                    map.put("appName", mApplicationAllEnum.mAppInfo.getApp_name());
                    map.put("appShortName", mApplicationAllEnum.mAppInfo.getApp_shortname());
                    map.put("app_version_id", mApplicationAllEnum.mAppInfo.getmAppVersion().getApp_version_id() + "");
                    for (AppVersionConfig mAppVersionConfig : mApplicationAllEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                        if (!map.containsKey(mAppVersionConfig.getConfig_code())) {
                            map.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                        }
                    }
                }
                if (mApplicationAllEnum.mAppInfo != null) {
                    map.put("app_id", mApplicationAllEnum.mAppInfo.getApp_id() + ""); //传入工作流构建需要的appId
                    map.put("Type", true);
                    map.put("appName", mApplicationAllEnum.mAppInfo.getApp_name());
                    map.put("parent_app_id", ""+mApplicationAllEnum.mAppInfo.getParent_app_id());
                    map.put("appShortName", mApplicationAllEnum.mAppInfo.getApp_shortname());
                }
//                if (c.mClass.getName().equals(AdvertActivity.class.getName()) && Integer.parseInt(map.get("com_app_advertise_move_show").toString()) == 1) {
//                    HTActivityUnit.switchTo((Activity) context, c.mClass, map);
//                } else {
//                    return;
//                }

                String topActivity = getTopActivity(context);
                if(topActivity.contains("ClientTabActivity")){
                    for (MenuTabItem mMenuTabItem : ClientTabActivity.mTabHost.getMenuTabItem()) {
                        if(mMenuTabItem.getButtomEnum() == mApplicationAllEnum && mMenuTabItem.getmAppInfo().getApp_id() == mApplicationAllEnum.mAppInfo.getApp_id()){
                            ClientTabActivity.mTabHost.setCurrentTabByTag(mMenuTabItem.getTag());
                            BookInit.getInstance().getmCallbackMX().closeDrawer();
                            return;
                        }
                    }
                }

                HTActivityUnit.switchTo((Activity) context, c.mClass, map);
            }
        } else {
            ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(context);
            try {
                if (mApplicationAllEnum == ApplicationAllEnum.ZY) {//对主页进行过滤
                    return;
                }
                if (mApplicationAllEnum != null)
                    mProxyDealApplication.applicationCenterProxy(mApplicationAllEnum.mAppInfo);
                else {
                    throw new NotApplicationException("枚举值没空");
                }
            } catch (NotApplicationException e) {
                e.printStackTrace();
            }
        }
    }


    public String getTopActivity(Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else
            return null;
    }
    /**
     * 调用敏形的 后续再自行添加
     *
     * @param mLeftEnum
     */
    @Override
    public void onClickLeft(ApplicationAllEnum mLeftEnum) {
        switch (mLeftEnum) {
            case WDSC:
                mConcreteLogin.viewFavorite(context);
                break;
            case CJRW:
                mConcreteLogin.createTask(context, context.getResources().getString(R.string.circle_menu_create_task));
                break;
            case ZZHD:
                mConcreteLogin.setupActivity(context, context.getResources().getString(R.string.circle_menu_activity));
                break;
            case FQTP:
                mConcreteLogin.poll( context, context.getResources().getString(R.string.circle_menu_poll));
                break;
            case GZFX:
                mConcreteLogin.shareToCircle(context,"");
                break;
            case SYS:
                mConcreteLogin.startScan( context,"");
                break;
            case FBXX:
                mConcreteLogin.startSendText(context, context.getResources().getString(R.string.circle_menu_send_text));
                break;
            case CJQL://新增创建群聊
                mConcreteLogin.selectUser(context, context.getString(R.string.interface_create_graph));
                break;
            case ADV_PAGE:
                //广告页的展示
                String move_show = mLeftEnum.map.get("com_app_advertise_move_show") == null ? "" : mLeftEnum.map.get("com_app_advertise_move_show").toString();
                if (TextUtils.isEmpty(move_show)) {
                    move_show = "0";
                }
                if (Integer.parseInt(move_show) == 1) {
                    new AdvertPopWindow(context, mLeftEnum.map, mLeftEnum.mAppInfo.getView());
                }
//                Log.d("ADV","广告页");
//                Toast.makeText(context,"弹出广告页",Toast.LENGTH_LONG).show();
                break;

        }
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }


}
