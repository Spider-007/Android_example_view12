package htmitech.com.componentlibrary.listener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.minxing.kit.api.callback.MXRequestCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 敏行统一抽象类进行管理
 */

public abstract class ICallLogin {
    /**
     * 登录成功和失败的提供回调接口
     * @param mICallLoginMXListener
     */
    abstract public void setICallLoginMXListener(ICallLoginMXListener mICallLoginMXListener);

    /**
     * 敏行登录方法
     * @param context
     * @param usernameString
     * @param passwordString
     */
    abstract public void login(Context context, String usernameString, String passwordString);

    /**
     * 敏行loading方法
     * @param context
     * @param usernameString
     * @param passwordString
     * @param isSSOLogin
     */
    abstract public void loading(Context context, String usernameString, String passwordString,boolean isSSOLogin);



    abstract public void logout(Context context, MXKitLogoutListener mMXKitLogoutListener);


    /**
     * 敏行
     * @param context
     * @param isAPPFTT
     */
    abstract public void prepareResource(Context context,boolean isAPPFTT);

    /**
     * 关于敏行消息沟通方法提供封装
     * @param context
     * @param userId
     */
    abstract public void chatMX(Context context,String userId);

    /**
     * 敏行初始化方法
     * @param context
     * @param clientConfigSdcardRoot
     * @param clientShowContactOcu
     * @param clientShowContactCompany
     * @param clientShowMultiChat
     * @param ClientShowContanctVip
     * @param clientCellPhone
     * @param clientRefresh
     * @param clientId
     * @param clientSaterEnable
     * @param fileForbidden
     */
    abstract public void mXInit(final Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl);




    abstract public void mXRefreshInit(final Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl);


    abstract public void setICallMXInit(ICallMXInit mICallMXInit);


    /**
     * 敏行的分享的方法
     * @param context
     * @param mMXShareLinkListener
     */
    abstract public void setMXShareLink(Context context,MXShareLinkListener mMXShareLinkListener);


    /**
     *
     * @param context
     * @param checkUpgradeMark
     * @param id
     * @param views
     */
    abstract public void refreshAlertIcon(Context context,boolean checkUpgradeMark,int id, View...views);


    /**
     * 获取敏行角标
     * @param context
     * @return
     */
    abstract public  int badgeMXCount(Context context);


    abstract public boolean checkNetworkCircleUnread(Context context);

    abstract public void setCircleAutoRefresh(Context context);

    abstract public ArrayList<Integer> getNetworkIds(Context context);

    abstract public void switchNetwork(Context context,int mxAppId );

    abstract public boolean isCurrentUserNull(Context context);


    abstract public void initChatListener(Context context,ShareListener mShareListener,ChatFinishListener mChatFinishListener);


    abstract public void switchCircleGroup(int groupId);


    abstract public void shareTextToChat(Context context,String shareContent);

    abstract public void shareImageToChat(Context context,Uri imageUri);

    abstract public void shareImagesToChat(Context context, List<Uri> uris);


    abstract public void shareToMail(Context context,Uri mailUri);

    abstract public String buildConfigInfo(Context context);

    abstract public void viewFavorite(Context context);

    abstract public void createTask(Context context ,String title);

    abstract public void setupActivity(Context context,String title);

    abstract public void poll(Context context,String title);

    abstract public void shareTextToCircle(Context context,String title);

    abstract public void shareToCircle(Context context,String title);


    abstract public void shareToCircle(Context context,Intent intent);

    abstract public void shareImageToCircle(Context context,Uri imageUri);

    abstract public void shareImagesToCircle(Context context,List<Uri> uris);

    abstract public void startScan(Context context,String title);

    abstract public void startSendText(Context context,String title);

    abstract public void selectUser(Context context,String title);

    abstract public String  getAppSignaturePermission();

    abstract public void setStartGesturePsd(Context context,boolean flag);

    abstract public void chat(Context context,String[] loginNames);

    abstract public String getPushHost();

    abstract public String getServerHost();

    abstract public void switchHttpServerConfig(Context context,String serverConfig);

    abstract public void switchPushServerConfig(Context context,String pushConfig);

    abstract public void changePassword(Context context);

    abstract public void contactsConfig(Context context);

    abstract public String getAppVersionName();

    abstract public String getDownloadApkRoot();

    abstract public String smartPatch(Context context,String filePath,String fileName);

    /**
     * 扫一扫
     * @param context
     */
    abstract public void startScan(Context context);


    /**
     *添加常用联系人
     * @param context
     */
    abstract public void addContacts(Context context);


    /**
     * 发起聊天
     */
    abstract public void startNewChat(Context context);

    /**
     * 跳敏行通讯录
     */

    abstract public void startMXContactsActivity(Context context);


    abstract public void addOrDeleteContact(Context context,boolean isAdd,String personId, MXRequestCallBack mMXRequestCallBack);
}
