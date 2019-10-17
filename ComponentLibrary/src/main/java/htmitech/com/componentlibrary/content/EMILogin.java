package htmitech.com.componentlibrary.content;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.minxing.kit.MXConstants;
import com.minxing.kit.MXDataPlugin;
import com.minxing.kit.MXKit;
import com.minxing.kit.MXKitConfiguration;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ChatMessage;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.bean.MXUser;
import com.minxing.kit.api.bean.ShareLink;
import com.minxing.kit.api.callback.MXApiCallback;
import com.minxing.kit.api.callback.MXJsonCallBack;
import com.minxing.kit.api.callback.MXRequestCallBack;
import com.minxing.kit.api.callback.UserCallback;
import com.minxing.kit.internal.common.bean.im.ConversationGraph;
import com.minxing.kit.mail.MXMail;
import com.minxing.kit.ui.chat.ChatManager;
import com.minxing.kit.ui.circle.CircleManager;
import com.minxing.kit.ui.contacts.ContactManager;
import com.minxing.kit.ui.contacts.MXContactsActivity;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.R;
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

public class EMILogin extends ICallLogin {

    public ICallLoginMXListener mxListener;

    private ICallMXInit mICallMXInit;

    private CircleManager circleManager;

    public EMILogin() {
        circleManager = MXUIEngine.getInstance().getCircleManager();
    }


    public void setICallLoginMXListener(ICallLoginMXListener mxListener) {
        this.mxListener = mxListener;
    }

    @Override
    public void setICallMXInit(ICallMXInit mICallMXInit) {
        this.mICallMXInit = mICallMXInit;
    }

    /**
     * 敏行的分享功能
     *
     * @param context
     * @param mMXShareLinkListener
     */
    @Override
    public void setMXShareLink(Context context, final MXShareLinkListener mMXShareLinkListener) {

        MXDataPlugin.getInstance().setMXShareLinkListener(new MXDataPlugin.MXShareLinkListener() {
            @Override
            public boolean onLinkClicked(Context context, ShareLink shareLink) {
                return mMXShareLinkListener.onLinkClicked(shareLink.getAppUrl(), shareLink.getTitle());
            }
        });

    }

    @Override
    public void refreshAlertIcon(Context context, boolean checkUpgradeMark, int id, View... views) {
        MXCurrentUser currentUser = MXAPI.getInstance(context).currentUser();
        MXUtil.refreshHandlerLayout(context, views[0], checkUpgradeMark, id, currentUser);
        MXUtil.refreshHandlerLayout(context, views[1], checkUpgradeMark, id, currentUser);
        MXUtil.refreshHandlerLayout(context, views[2], checkUpgradeMark, id, currentUser);
        MXUtil.refreshHandlerLayout(context, views[3], checkUpgradeMark, id, currentUser);
    }

    @Override
    public int badgeMXCount(Context context) {
        MXCurrentUser currentUser = MXAPI.getInstance(context).currentUser();
        if (currentUser == null) {
            return -1;
        }
        int unreadMessage = MXAPI.getInstance(context).queryNetworkChatUnread(
                currentUser.getNetworkID());

        return unreadMessage;
    }

    @Override
    public boolean checkNetworkCircleUnread(Context context) {
        MXCurrentUser currentUser = MXAPI.getInstance(context).currentUser();
        return MXAPI.getInstance(context).checkNetworkCircleUnread(
                currentUser.getNetworkID());
    }

    @Override
    public void setCircleAutoRefresh(Context context) {
        MXAPI.getInstance(context).setCircleAutoRefresh();
    }

    @Override
    public ArrayList<Integer> getNetworkIds(Context context) {
        MXCurrentUser currentUser = null;
        ArrayList<Integer> netWorkIds = new ArrayList<>();

        try {
            /**
             * 切换社区
             */
            currentUser = MXAPI.getInstance(context).currentUser();

            if (currentUser != null && currentUser.getNetworks() != null) {
                for (int i = 0; i < currentUser.getNetworks().size(); i++) {
                    netWorkIds.add(currentUser.getNetworks().get(i).getId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return netWorkIds;
    }

    @Override
    public void switchNetwork(Context context, int mxAppId) {
        try {
            MXAPI.getInstance(context).switchNetwork(context, mxAppId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCurrentUserNull(Context context) {
        return MXAPI.getInstance(context).currentUser() == null;
    }

    @Override
    public void initChatListener(Context context, final ShareListener mShareListener, final ChatFinishListener mChatFinishListener) {
        ChatManager chatManager = MXUIEngine.getInstance().getChatManager();
        chatManager.setShareListener(new ChatManager.ShareListener() {
            @Override
            public void onSuccess() {
                mShareListener.onSuccess();
            }
        });

        chatManager.setOnChatFinishListener(new ChatManager.OnChatFinishListener() {
            @Override
            public void onBackToChatRoot(Context context) {
                mChatFinishListener.onBackToChatRoot(context);
            }
        });
    }

    @Override
    public void switchCircleGroup(int groupId) {
        MXUIEngine.getInstance().switchCircleGroup(groupId);
    }

    @Override
    public void shareTextToChat(Context context, String shareContent) {
        MXAPI.getInstance(context).shareTextToChat(context, shareContent);
    }

    @Override
    public void shareImageToChat(Context context, Uri imageUri) {
        MXAPI.getInstance(context).shareImageToChat(context, imageUri);
    }

    @Override
    public void shareImagesToChat(Context context, List<Uri> uris) {
        MXAPI.getInstance(context).shareImagesToChat(context, uris);
    }

    @Override
    public void shareToMail(Context context, Uri mailUri) {
        MXAPI.getInstance(context).shareToMail(context, mailUri);
    }

    @Override
    public String buildConfigInfo(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("host:").append(MXKit.getInstance().getKitConfiguration().getServerHost()).append("\r\n");
        sb.append("push:").append(MXKit.getInstance().getKitConfiguration().getPushHost()).append("\r\n");
        sb.append("type:").append(MXKit.getInstance().getKitConfiguration().getGrantType()).append("\r\n");
        return sb.toString();
    }

    @Override
    public void viewFavorite(Context context) {
        MXAPI.getInstance(context).viewFavorite();
    }

    @Override
    public void createTask(Context context, String title) {
        circleManager.createTask((Activity) context, title);
    }

    @Override
    public void setupActivity(Context context, String title) {
        circleManager.setupActivity((Activity) context, title);
    }

    @Override
    public void poll(Context context, String title) {
        circleManager.poll((Activity) context, title);
    }

    @Override
    public void shareTextToCircle(Context context, String title) {
        MXAPI.getInstance(context).shareTextToCircle(context, title);
    }

    @Override
    public void shareToCircle(Context context, String title) {
        circleManager.shareToCircle((Activity) context);
    }

    @Override
    public void shareToCircle(Context context,Intent intent) {
        String title = intent.getStringExtra(MXConstant.Share.MXKIT_INTENT_ACTION_DATA_SHARE_TITLE);
        String url = intent.getStringExtra(MXConstant.Share.MXKIT_INTENT_ACTION_DATA_SHARE_URL);
        String description = intent.getStringExtra(MXConstant.Share.MXKIT_INTENT_ACTION_DATA_SHARE_DESCRIPTION);
        String thumbnail_url = intent.getStringExtra(MXConstant.Share.MXKIT_INTENT_ACTION_DATA_SHARE_THUMBNAIL_URL);
        String appName = intent.getStringExtra(MXConstant.Share.MXKIT_INTENT_SHARE_APP_NAME);
        intent.putExtra(MXConstant.Share.MXKIT_INTENT_SHARE_APP_NAME, appName);
        ShareLink link = new ShareLink();
        link.setTitle(title);
        link.setThumbnail(thumbnail_url);
        link.setUrl(url);
        link.setDesc(description);
        MXAPI.getInstance(context).shareToCircle(context, link, appName, true);
    }

    @Override
    public void shareImageToCircle(Context context, Uri imageUri) {
        MXAPI.getInstance(context).shareImageToCircle(context, imageUri);
    }

    @Override
    public void shareImagesToCircle(Context context, List<Uri> uris) {
        MXAPI.getInstance(context).shareImagesToCircle(context, uris);
    }

    @Override
    public void startScan(Context context, String title) {
        MXKit.getInstance().startScan((Activity) context);
    }

    @Override
    public void startSendText(Context context, String title) {
        circleManager.startSendText((Activity) context, title);
    }

    @Override
    public void selectUser(final Context context, final String title) {
        MXAPI.getInstance(context).selectUser(context, title,
                true, true, new UserCallback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onFail(MXError error) {

                    }

                    @Override
                    public void onResult(List<MXUser> userList) {

                        if (userList != null) {
                            String[] loginNames = new String[userList.size()];
                            MXCurrentUser user = MXAPI.getInstance(context).currentUser();
                            for (int i = 0; i < userList.size(); i++) {
                                loginNames[i] = userList.get(i).getLoginName();
                            }
                            ConversationGraph graph = new ConversationGraph();
                            graph.setImage(user.getAvatarUrl());
                            graph.setTitle(title);
                            graph.setUrl("http://www.minxing365.com/");
                            MXAPI.getInstance(context).createGraph((Activity) context, graph, loginNames,
                                    new MXJsonCallBack() {

                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onLoading() {
                                        }

                                        @Override
                                        public void onFail(MXError error) {
                                        }

                                        @Override
                                        public void onResult(String result) {
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onResult(MXUser user) {

                    }
                });
    }

    @Override
    public String getAppSignaturePermission() {
        return MXKit.getInstance().getAppSignaturePermission();
    }

    @Override
    public void setStartGesturePsd(Context context, boolean flag) {
        MXKit.getInstance().setStartGesturePsd(false);
    }

    @Override
    public void chat(final Context context, String[] loginNames) {

        MXAPI.getInstance(context).chat(loginNames, new MXApiCallback() {

            @Override
            public void onLoading() {
            }

            @Override
            public void onFail(MXError error) {
                ToastUtil.showShort(context.getApplicationContext(), error.getMessage());
                if (null != mxListener)
                    mxListener.onMXFail(error.getMessage());
            }

            @Override
            public void onSuccess() {
                if (null != mxListener)
                    mxListener.onMXSuccess();
            }
        });
    }

    @Override
    public String getPushHost() {
        return MXKit.getInstance().getKitConfiguration().getPushHost();
    }

    @Override
    public String getServerHost() {
        return MXKit.getInstance().getKitConfiguration().getServerHost();
    }

    @Override
    public void switchHttpServerConfig(Context context, String serverConfig) {
        MXKit.getInstance().getKitConfiguration().switchHttpServerConfig(context, serverConfig);
    }

    @Override
    public void switchPushServerConfig(Context context, String pushConfig) {
        MXKit.getInstance().getKitConfiguration().switchPushServerConfig(context, pushConfig);
    }

    @Override
    public void changePassword(Context context) {
        MXAPI.getInstance(context).changePassword(context);
    }

    @Override
    public void contactsConfig(Context context) {
        MXAPI.getInstance(context).contactsConfig(context);
    }

    @Override
    public String getAppVersionName() {
        return MXKit.getInstance().getAppVersionName();
    }

    @Override
    public String getDownloadApkRoot() {
        return MXKit.getInstance().getKitConfiguration().getDownloadApkRoot();
    }

    @Override
    public String smartPatch(Context context, String filePath, String fileName) {
        return MXKit.getInstance().smartPatch(context, filePath, MXKit.getInstance().getKitConfiguration().getDownloadApkRoot() + fileName);
    }

    @Override
    public void startScan(Context context) {
        MXKit.getInstance().startScan((Activity) context);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void addContacts(Context context) {
        ContactManager contactManager = MXUIEngine.getInstance().getContactManager();
        contactManager.addContacts((Activity) context);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void startNewChat(Context context) {
        ChatManager chatManager = MXUIEngine.getInstance().getChatManager();
        chatManager.startNewChat((Activity) context);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void startMXContactsActivity(Context context) {
        Intent intent = new Intent(context, MXContactsActivity.class);
        ((Activity) context).startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void addOrDeleteContact(Context context, boolean isAdd, String personId, MXRequestCallBack mMXRequestCallBack) {
        MXAPI.getInstance(context).addOrDeleteContact(context, true, personId, new MXRequestCallBack(context) {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(MXError error) {

            }
        });
    }


    @Override
    public void login(Context context, String usernameString, String passwordString) {
        //开始登陆敏行
        MXKit.getInstance().loginMXKit(context, usernameString, passwordString, new MXKit.MXKitLoginListener() {
            @Override
            public void onSuccess() {
                mxListener.onMXSuccess();
            }

            @Override
            public void onFail(MXError error) {
                String message = "";
                if (error != null) {
                    message = error.getMessage();
                }
                mxListener.onMXFail(message);
            }

            @Override
            public void onSMSVerify() {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void loading(final Context context, final String usernameString, final String passwordString, boolean isSSOLogin) {
        if (isSSOLogin) {
            MXCurrentUser currentUser = MXAPI.getInstance(context).currentUser();
            if (currentUser != null) {
                MXKit.getInstance().logout(context, new MXKit.MXKitLogoutListener() {
                    @Override
                    public void onLogout() {
                        loginWithSSO(context, usernameString, passwordString);
//                    BadgeUtil.resetBadgeCount(LoadingActivity.this);
                    }
                });
            } else {
                loginWithSSO(context, usernameString, passwordString);
            }
        } else {
            MXKit.getInstance().loginMXKitWithToken(context, new MXKit.MXKitLoginListener() {

                @Override
                public void onSuccess() {
                    mxListener.onMXSuccess();
                }

                @Override
                public void onFail(MXError error) {
                    String message = "";
                    if (error != null) {
                        message = error.getMessage();
                    }
                    mxListener.onMXFail(message);
                }

                @Override
                public void onSMSVerify() {
                }
            });
        }

    }

    @Override
    public void logout(Context context, final MXKitLogoutListener mMXKitLogoutListener) {
        MXKit.getInstance().logout(context, new MXKit.MXKitLogoutListener() {
            @Override
            public void onLogout() {
                mMXKitLogoutListener.onLogout();
            }
        });
    }

    @Override
    public void prepareResource(Context context, boolean isAPPFTT) {
        MXKit.getInstance().prepareResource(context, isAPPFTT,
                new MXKit.MXKitPrepareResourceListener() {

                    @Override
                    public void onComplete() {
                        mxListener.onMXSuccess();
                    }

                    @Override
                    public void onProcessing(final String message) {
                        mxListener.onMXFail(message);
                    }

                    @Override
                    public void onFail() {
                        mxListener.onMXSuccess();
                        // 拷贝资源失败
                    }
                });
    }

    @Override
    public void chatMX(Context context, String userId) {
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        if (userId.equalsIgnoreCase("admin")) {

            ToastUtil.showShort(context, "不能发起对admin的聊天");

        } else if (userId.equalsIgnoreCase(MXAPI.getInstance(context)
                .currentUser().getLoginName())) {
            ToastUtil.showShort(context, "不能发起对自己的聊天");
        } else {

            Log.d("FlowFragment", "发起聊天，对：" + userId);
            String[] loginNames = new String[]{userId};
            // TODO Auto-generated method stub
            MXAPI.getInstance(context).chat(loginNames,
                    new MXApiCallback() {
                        @Override
                        public void onLoading() {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onFail(MXError arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub

                        }
                    });
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void mXInit(final Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {
        MXKitConfiguration config = new MXKitConfiguration.Builder(context)
                // http server地址// 推送server地址
                .hostOptions(imgeUrl,// http server地址 修改
                        pushUrl)// 推送server地址
                // SD卡目录
                //
                .sdCardCacheFolder(clientConfigSdcardRoot)
                // 设置是否在通讯录中显示公众号操作
                .contactOcu(clientShowContactOcu)
                // 设置是否在通讯录中显示公司通讯录操作。
                .contactCompany(clientShowContactCompany)
                // 设置是否在通讯录中显示群聊操作
                .contactMultiChat(clientShowMultiChat)
                // 设置是否在通讯录中显示特别关注操作
                .contactVip(ClientShowContanctVip)
                // 设置手机号码隐藏规则
                .encryptCellphone(clientCellPhone)
                .appForceRefresh(clientRefresh)
                .appClientId(clientId)
                .waterMarkEnable(clientSaterEnable)
                .fileDownloadForbidden(fileForbidden).build();
        MXKit.getInstance().init(context, config);

        MXKit.getInstance().setConflictListener(new MXKit.MXKitUserConflictListener() {

            @Override
            public void onUserConflict(MXError error) {
                // 用户被踢出后处理逻辑
                if (MXUtil.isApplicationForeground(context)) {
                    ToastUtil.show(context, String.valueOf(error.getMessage()), Toast.LENGTH_SHORT);
                    Intent finishIntent = new Intent();
                    finishIntent.setAction("finish");
                    finishIntent.setClassName(context, "com.minxing.client.ClientTabActivity");
                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(finishIntent);
                }
            }
        });

        MXKit.getInstance().setMasterClearListener(new MXKit.MXKitMasterClearListener() {

            @Override
            public void onKitMasterClear() {
//                Utils.toast(getApplicationContext(), R.string.master_clear_alert, Toast.LENGTH_SHORT);
                mICallMXInit.onKitMasterClear();
            }
        });

        MXKit.getInstance().setupNotification(new MXKit.MXChatNotificationListener() {
            @Override
            public void onChatNotify(Context context, ChatMessage message, boolean flag) {
                mICallMXInit.onChatNotify(context, message, flag);
            }

            @Override
            public void dismissNotification(Context context, int chatID) {
                mICallMXInit.dismissNotification(context, chatID);
            }

            @Override
            public void onMessageRevoked(Context context, ChatMessage revokedMessage) {
                mICallMXInit.onMessageRevoked(context, revokedMessage);
            }
        });

        // 用以返回主界面
        MXKit.getInstance().setMXUIListener(new MXKit.MXUIListener() {

            @Override
            public void switchToMainScreen(Context context) {
                Intent intent = new Intent();
                intent.setClassName(context, "com.minxing.client.ClientTabActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }

            @Override
            public void reLaunchApp(Context context) {
                Intent intent = new Intent();
                intent.setClassName(context, "com.minxing.client.ClientTabActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

        MXKit.getInstance().setViewSwitchListener(new MXUIEngine.ViewSwitchListener() {

            @Override
            public void switchToCircle(Context context, int groupID) {
                Intent intent = new Intent();
                intent.setClassName(context, "com.minxing.client.ClientTabActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MXConstants.IntentKey.SHOW_CURRENT_GROUP_WORK_CIRCLE, groupID);
                context.startActivity(intent);
            }
        });
        MXKit.getInstance().setLifecycleCallbacks(new MXKit.MXKitLifecycleCallbacks() {

            @Override
            public void onActivityStop(Activity activity) {
            }

            @Override
            public void onActivityStart(Activity activity) {
                mICallMXInit.onActivityStart(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResume(Activity activity) {
                // 友盟统计回调
//				MobclickAgent.onResume(activity);
            }

            @Override
            public void onActivityPause(Activity activity) {
                mICallMXInit.onActivityPause(activity);
                // 友盟统计回调
//				MobclickAgent.onPause(activity);
            }

            @Override
            public void onActivityDestroy(Activity activity) {
            }

            @Override
            public void onActivityCreate(Activity activity, Bundle savedInstanceState) {
//				handleStatusBarColor(activity);
            }

            @Override
            public void onStartActivityForResult(Activity activity) {

                mICallMXInit.onStartActivityForResult(activity);
            }

            @Override
            public void onStartActivity(Activity activity) {
                if (activity != null) {
                    if (activity.getParent() != null) {
                        activity.getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    } else {
                        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    }
                }
            }

            @Override
            public void onActivityFinish(Activity activity) {
                if (activity != null) {
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                }
            }
        });
        MXMail.getInstance().init((Application) context);

        MXKit.getInstance().initForeBackgroundDetector((Application) context);

        ((Application) context).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mICallMXInit.onActivityResumed(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void mXRefreshInit(Context context, String clientConfigSdcardRoot, boolean clientShowContactOcu, boolean clientShowContactCompany, boolean clientShowMultiChat, boolean ClientShowContanctVip, String clientCellPhone, boolean clientRefresh, String clientId, boolean clientSaterEnable, boolean fileForbidden, String imgeUrl, String pushUrl) {
        MXKitConfiguration config = new MXKitConfiguration.Builder(context.getApplicationContext())
                // http server地址// 推送server地址
                .hostOptions(imgeUrl,// http server地址 修改
                        pushUrl)// 推送server地址
                // SD卡目录
                //
                .sdCardCacheFolder(clientConfigSdcardRoot)
                // 设置是否在通讯录中显示公众号操作
                .contactOcu(clientShowContactOcu)
                // 设置是否在通讯录中显示公司通讯录操作。
                .contactCompany(clientShowContactCompany)
                // 设置是否在通讯录中显示群聊操作
                .contactMultiChat(clientShowMultiChat)
                // 设置是否在通讯录中显示特别关注操作
                .contactVip(ClientShowContanctVip)
                // 设置手机号码隐藏规则
                .encryptCellphone(clientCellPhone)
                .appForceRefresh(clientRefresh)
                .appClientId(clientId)
                .waterMarkEnable(clientSaterEnable)
                .fileDownloadForbidden(fileForbidden).build();
        MXKit.getInstance().init(context.getApplicationContext(), config);
    }

    private void loginWithSSO(Context context, String usernameString, String passwordString) {
        MXKit.getInstance().loginMXKit(context, usernameString, passwordString, new MXKit.MXKitLoginListener() {
            @Override
            public void onSuccess() {
                mxListener.onMXSuccess();
            }

            @Override
            public void onFail(MXError error) {
                String message = "";
                if (error != null) {
                    message = error.getMessage();
                }
                mxListener.onMXFail(message);
            }

            @Override
            public void onSMSVerify() {
            }
        });
    }
}
