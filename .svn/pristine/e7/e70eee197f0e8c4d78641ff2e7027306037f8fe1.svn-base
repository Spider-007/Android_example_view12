package htmitech.com.componentlibrary.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.minxing.kit.api.callback.MXRequestCallBack;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.ChatFinishListener;
import htmitech.com.componentlibrary.listener.ICallLogin;
import htmitech.com.componentlibrary.listener.ICallLoginMXListener;
import htmitech.com.componentlibrary.listener.ICallMXInit;
import htmitech.com.componentlibrary.listener.MXKitLogoutListener;
import htmitech.com.componentlibrary.listener.MXShareLinkListener;
import htmitech.com.componentlibrary.listener.ShareListener;
import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 * Created by Administrator on 2018/4/26.
 */

public class NotEMILogin extends ICallLogin {
    public NotEMILogin() {

    }

    public ICallLoginMXListener mxListener;

    public void setICallLoginMXListener(ICallLoginMXListener mxListener) {
        this.mxListener = mxListener;
    }


    @Override
    public void setICallMXInit(ICallMXInit mICallMXInit) {

    }

    @Override
    public void setMXShareLink(Context context, MXShareLinkListener mMXShareLinkListener) {
//        ToastUtil.showShort(context, "对不起，暂不支持分享功能！");
    }


    @Override
    public void login(Context context, String usernameString, String passwordString) {
        mxListener.onMXSuccess();
    }

    @Override
    public void loading(Context context, String usernameString, String passwordString, boolean isSSOLogin) {
        mxListener.onMXSuccess();
    }

    @Override
    public void logout(Context context, MXKitLogoutListener mMXKitLogoutListener) {
        mMXKitLogoutListener.onLogout();
    }

    @Override
    public void prepareResource(Context context, boolean isAPPFTT) {
        mxListener.onMXSuccess();
    }

    @Override
    public void chatMX(Context context, String userId) {
        ToastUtil.showShort(context, "对不起暂不支持聊天");
    }

    @Override
    public void mXInit(final Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {

    }

    @Override
    public void mXRefreshInit(Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {

    }

    @Override
    public void refreshAlertIcon(Context context, boolean checkUpgradeMark, int id, View... views) {

    }

    @Override
    public int badgeMXCount(Context context) {
        return 0;
    }

    @Override
    public boolean checkNetworkCircleUnread(Context context) {
        return false;
    }

    @Override
    public void setCircleAutoRefresh(Context context) {

    }

    @Override
    public ArrayList<Integer> getNetworkIds(Context context) {
        return null;
    }

    @Override
    public void switchNetwork(Context context, int mxAppId) {

    }

    @Override
    public boolean isCurrentUserNull(Context context) {
        return false;
    }

    @Override
    public void initChatListener(Context context, ShareListener mShareListener, ChatFinishListener mChatFinishListener) {

    }

    @Override
    public void switchCircleGroup(int groupId) {

    }

    @Override
    public void shareTextToChat(Context context, String shareContent) {

    }

    @Override
    public void shareImageToChat(Context context, Uri imageUri) {

    }

    @Override
    public void shareImagesToChat(Context context, List<Uri> uris) {

    }

    @Override
    public void shareToMail(Context context, Uri mailUri) {

    }

    @Override
    public String buildConfigInfo(Context context) {
        return "";
    }

    @Override
    public void viewFavorite(Context context) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void createTask(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void setupActivity(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void poll(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void shareTextToCircle(Context context, String title) {

    }

    @Override
    public void shareToCircle(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void shareToCircle(Context context,Intent intent) {

    }

    @Override
    public void shareImageToCircle(Context context, Uri imageUri) {

    }

    @Override
    public void shareImagesToCircle(Context context, List<Uri> uris) {

    }

    @Override
    public void startScan(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void startSendText(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void selectUser(Context context, String title) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public String getAppSignaturePermission() {
        return "";
    }

    @Override
    public void setStartGesturePsd(Context context, boolean flag) {

    }

    @Override
    public void chat(Context context, String[] loginNames) {
        if (null != mxListener)
            mxListener.onMXSuccess();
    }

    @Override
    public String getPushHost() {
        return "";
    }

    @Override
    public String getServerHost() {
        return "";
    }

    @Override
    public void switchHttpServerConfig(Context context, String serverConfig) {

    }

    @Override
    public void switchPushServerConfig(Context context, String pushConfig) {

    }

    @Override
    public void changePassword(Context context) {

    }

    @Override
    public void contactsConfig(Context context) {

    }

    @Override
    public String getAppVersionName() {
        return "";
    }

    @Override
    public String getDownloadApkRoot() {
        return "";
    }

    @Override
    public String smartPatch(Context context,String filePath, String fileName) {
        return "";
    }

    @Override
    public void startScan(Context context) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void addContacts(Context context) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void startNewChat(Context context) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void startMXContactsActivity(Context context) {
        ToastUtil.showShort(context, "该功能暂未开放！");
    }

    @Override
    public void addOrDeleteContact(Context context, boolean isAdd, String personId, MXRequestCallBack mMXRequestCallBack) {

    }


}
