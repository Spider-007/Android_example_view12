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

/**
 * 登录之后需要调用的一个类 是否调用敏行的登录
 */

public class ConcreteLogin extends ICallLogin {

    private ICallLoginMXListener mICallLoginMXListener;

    private LoginFactory mLoginFactory ;

    private ICallMXInit mICallMXInit;

    private static ConcreteLogin mConcreteLoginInit;

    private int type;
    /**
     * 此处的type 是为是否为emi  如果是1 则不是emi  如果是 0 则是emi 默认是有emi的
     *
     * @param type
     */
    public ConcreteLogin(){
        mLoginFactory = new LoginFactory();
    }
    private ConcreteLogin(int type){
        this.type = type;

        mLoginFactory = new LoginFactory();
    }

    public static ConcreteLogin getInstance() {
//        type = 0;
        if (mConcreteLoginInit == null) {
            mConcreteLoginInit = new ConcreteLogin(0);
        }

        return mConcreteLoginInit;
    }

    public void setType(int type){
        this.type = type ;
    }


    public int getType(){
        return type;
    }

    public void setICallLoginMXListener(ICallLoginMXListener mICallLoginMXListener){
        this.mICallLoginMXListener = mICallLoginMXListener;
    }

    @Override
    public void login(Context context, String usernameString, String passwordString) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener);
        mICallLogin.login(context,usernameString,passwordString);
    }

    @Override
    public void loading(Context context, String usernameString, String passwordString, boolean isSSOLogin) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener);
        mICallLogin.loading(context,usernameString,passwordString,isSSOLogin);
    }

    @Override
    public void logout(Context context, MXKitLogoutListener mMXKitLogoutListener) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener);
        mICallLogin.logout(context,mMXKitLogoutListener);
    }

    @Override
    public void prepareResource(Context context, boolean isAPPFTT) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener);
        mICallLogin.prepareResource(context,isAPPFTT);
    }

    @Override
    public void chatMX(Context context,String userId) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener);
        mICallLogin.chatMX(context,userId);
    }

    @Override
    public void mXInit(Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.mXInit(context,clientConfigSdcardRoot,clientShowContactOcu,clientShowContactCompany,clientShowMultiChat,ClientShowContanctVip,clientCellPhone,clientRefresh,clientId,clientSaterEnable,fileForbidden,imgeUrl,pushUrl);
    }

    @Override
    public void mXRefreshInit(Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.mXRefreshInit(context,clientConfigSdcardRoot,clientShowContactOcu,clientShowContactCompany,clientShowMultiChat,ClientShowContanctVip,clientCellPhone,clientRefresh,clientId,clientSaterEnable,fileForbidden,imgeUrl,pushUrl);
    }


    @Override
    public void setICallMXInit(ICallMXInit mICallMXInit) {
        this.mICallMXInit = mICallMXInit;
    }

    @Override
    public void setMXShareLink(Context context, MXShareLinkListener mMXShareLinkListener) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.setMXShareLink(context,mMXShareLinkListener);
    }

    @Override
    public void refreshAlertIcon(Context context, boolean checkUpgradeMark, int id, View... views) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.refreshAlertIcon(context,checkUpgradeMark,id,views);
    }

    @Override
    public int badgeMXCount(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.badgeMXCount(context);
    }

    @Override
    public boolean checkNetworkCircleUnread(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.checkNetworkCircleUnread(context);
    }

    @Override
    public void setCircleAutoRefresh(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.setCircleAutoRefresh(context);
    }

    @Override
    public ArrayList<Integer> getNetworkIds(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.getNetworkIds(context);
    }

    @Override
    public void switchNetwork(Context context,int mxAppId) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.switchNetwork(context,mxAppId);
    }

    @Override
    public boolean isCurrentUserNull(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.isCurrentUserNull(context);
    }

    @Override
    public void initChatListener(Context context, ShareListener mShareListener, ChatFinishListener mChatFinishListener) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.initChatListener(context,mShareListener,mChatFinishListener);
    }

    @Override
    public void switchCircleGroup(int groupId) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.switchCircleGroup(groupId);
    }

    @Override
    public void shareTextToChat(Context context, String shareContent) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareTextToChat(context,shareContent);
    }

    @Override
    public void shareImageToChat(Context context, Uri imageUri) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareImageToChat(context,imageUri);
    }

    @Override
    public void shareImagesToChat(Context context, List<Uri> uris) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareImagesToChat(context,uris);
    }

    @Override
    public void shareToMail(Context context, Uri mailUri) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareToMail(context,mailUri);
    }

    @Override
    public String buildConfigInfo(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.buildConfigInfo(context);
    }

    @Override
    public void viewFavorite(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.viewFavorite(context);
    }

    @Override
    public void createTask(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.createTask(context,title);
    }

    @Override
    public void setupActivity(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.setupActivity(context,title);
    }

    @Override
    public void poll(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.poll(context,title);
    }

    @Override
    public void shareTextToCircle(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareTextToCircle(context,title);
    }

    @Override
    public void shareToCircle(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareToCircle(context,title);
    }

    @Override
    public void shareToCircle(Context context, Intent intent) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareToCircle(context,intent);
    }

    @Override
    public void shareImageToCircle(Context context, Uri imageUri) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareImageToCircle(context,imageUri);
    }

    @Override
    public void shareImagesToCircle(Context context, List<Uri> uris) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.shareImagesToCircle(context,uris);
    }

    @Override
    public void startScan(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.startScan(context,title);
    }

    @Override
    public void startSendText(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.startSendText(context,title);
    }

    @Override
    public void selectUser(Context context, String title) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.selectUser(context,title);
    }

    @Override
    public String getAppSignaturePermission() {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.getAppSignaturePermission();
    }

    @Override
    public void setStartGesturePsd(Context context, boolean flag) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.setStartGesturePsd(context,flag);
    }

    @Override
    public void chat(Context context, String[] loginNames) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.chat(context,loginNames);
    }

    @Override
    public String getPushHost() {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.getPushHost();
    }

    @Override
    public String getServerHost() {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        return mICallLogin.getServerHost();
    }

    @Override
    public void switchHttpServerConfig(Context context, String serverConfig) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.switchHttpServerConfig(context,serverConfig);
    }

    @Override
    public void switchPushServerConfig(Context context, String pushConfig) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.switchPushServerConfig(context,pushConfig);
    }

    @Override
    public void changePassword(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.changePassword(context);
    }

    @Override
    public void contactsConfig(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);
        mICallLogin.contactsConfig(context);
    }

    @Override
    public String getAppVersionName() {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        return mICallLogin.getAppVersionName();
    }

    @Override
    public String getDownloadApkRoot() {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        return mICallLogin.getDownloadApkRoot();
    }

    @Override
    public String smartPatch(Context context, String filePath,String fileName) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        return mICallLogin.smartPatch(context,filePath,fileName);
    }

    @Override
    public void startScan(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        mICallLogin.startScan(context);
    }

    @Override
    public void addContacts(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        mICallLogin.addContacts(context);
    }

    @Override
    public void startNewChat(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        mICallLogin.startNewChat(context);
    }

    @Override
    public void startMXContactsActivity(Context context) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        mICallLogin.startMXContactsActivity(context);
    }

    @Override
    public void addOrDeleteContact(Context context, boolean isAdd, String personId, MXRequestCallBack mMXRequestCallBack) {
        ICallLogin mICallLogin = mLoginFactory.createLogin(type,mICallLoginMXListener,mICallMXInit);

        mICallLogin.addOrDeleteContact(context,isAdd,personId,mMXRequestCallBack);
    }


}