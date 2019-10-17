package com.minxing.client;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.htmitech.emportal.R;
import com.minxing.client.activity.GesturePasswordActivity;
import com.minxing.client.notification.NotificationHolder;
import com.minxing.client.util.BackgroundDetector;
import com.minxing.client.util.ImageUtil;
import com.minxing.client.util.NotificationUtil;
import com.minxing.client.util.Utils;
import com.minxing.kit.MXConstants;
import com.minxing.kit.MXKit;
import com.minxing.kit.MXKit.MXChatNotificationListener;
import com.minxing.kit.MXKit.MXKitLifecycleCallbacks;
import com.minxing.kit.MXKit.MXKitMasterClearListener;
import com.minxing.kit.MXKit.MXKitUserConflictListener;
import com.minxing.kit.MXKit.MXUIListener;
import com.minxing.kit.MXKitConfiguration;
import com.minxing.kit.MXUIEngine.ViewSwitchListener;
import com.minxing.kit.api.bean.ChatMessage;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.mail.MXMail;
import com.umeng.analytics.MobclickAgent;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;

public class AppApplication extends Application {
	public static String MX_LOGTAG = "MXLOG";

	@Override
	public void onCreate() {
		Log.i(MX_LOGTAG, "[AppApplication][onCreate]");
		super.onCreate();
		NotificationUtil.clearAllNotification(getApplicationContext());

		MXKitConfiguration config = new MXKitConfiguration.Builder(getApplicationContext())
				// http server地址// 推送server地址
				.hostOptions(ResourceUtil.getConfString(getApplicationContext(), "client_conf_http_host"),
						ResourceUtil.getConfString(getApplicationContext(), "client_conf_push_host"))
				// SD卡目录
				.sdCardCacheFolder(ResourceUtil.getConfString(getApplicationContext(), "client_conf_sdcard_root"))
				// 设置是否在通讯录中显示公众号操作
				.contactOcu(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_ocu"))
				// 设置是否在通讯录中显示公司通讯录操作。
				.contactCompany(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_company"))
				// 设置是否在通讯录中显示群聊操作
				.contactMultiChat(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_multi_chat"))
				// 设置是否在通讯录中显示特别关注操作
				.contactVip(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_vip"))
				// 设置手机号码隐藏规则
				.encryptCellphone(ResourceUtil.getConfString(getApplicationContext(), "client_encrypt_cellphone"))
				.appForceRefresh(ResourceUtil.getConfBoolean(getApplicationContext(), "client_app_center_force_refresh"))
				.appClientId(ResourceUtil.getConfString(getApplicationContext(), "client_app_client_id"))
				.waterMarkEnable(ResourceUtil.getConfBoolean(getApplicationContext(), "client_water_mark_enable"))
				.fileDownloadForbidden(ResourceUtil.getConfBoolean(getApplicationContext(), "file_download_forbidden")).build();
		MXKit.getInstance().init(getApplicationContext(), config);

		ImageUtil.initImageEngine(getApplicationContext());
		MXKit.getInstance().setConflictListener(new MXKitUserConflictListener() {

			@Override
			public void onUserConflict(MXError error) {
				// 用户被踢出后处理逻辑
				if (Utils.isApplicationForeground(getApplicationContext())) {
					Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
					finishIntent.putExtra("error_message", error.getMessage());
					finishIntent.setAction("finish");
					finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(finishIntent);
				}
			}
		});

		MXKit.getInstance().setMasterClearListener(new MXKitMasterClearListener() {

			@Override
			public void onKitMasterClear() {
				Utils.toast(getApplicationContext(), R.string.master_clear_alert, Toast.LENGTH_SHORT);

				PreferenceUtils.clearAllPreference(getApplicationContext());

				Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
				finishIntent.setAction("master_clear");
				finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(finishIntent);
			}
		});

		MXKit.getInstance().setupNotification(new MXChatNotificationListener() {
			@Override
			public void onChatNotify(Context context, ChatMessage message,boolean flag) {
				NotificationUtil.handleMessageNotification(context, message, false);
			}

			@Override
			public void dismissNotification(Context context, int chatID) {
				if (NotificationHolder.getInstance().checkChatMessage(chatID)) {
					NotificationUtil.clearAllNotification(context);
				}
			}

			@Override
			public void onMessageRevoked(Context context, ChatMessage revokedMessage) {
				if (NotificationHolder.getInstance().checkChatMessage(revokedMessage.getChatID())) {
					NotificationUtil.handleMessageNotification(context, revokedMessage, true);
				}
			}
		});

		// 用以返回主界面
		MXKit.getInstance().setMXUIListener(new MXUIListener() {

			@Override
			public void switchToMainScreen(Context context) {
				Intent intent = new Intent(context, ClientTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}

			@Override
			public void reLaunchApp(Context context) {
				Intent intent = new Intent(context, ClientTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				context.startActivity(intent);
			}
		});

		MXKit.getInstance().setViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void switchToCircle(Context context, int groupID) {
				Intent intent = new Intent(context, ClientTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra(MXConstants.IntentKey.SHOW_CURRENT_GROUP_WORK_CIRCLE, groupID);
				context.startActivity(intent);
			}
		});
		MXKit.getInstance().setLifecycleCallbacks(new MXKitLifecycleCallbacks() {

			@Override
			public void onActivityStop(Activity activity) {
			}

			@Override
			public void onActivityStart(Activity activity) {
				if (BackgroundDetector.getInstance().isGesturePwdViewEnabled()) {
					Intent intent = new Intent(activity, GesturePasswordActivity.class);
					intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
					intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
					activity.startActivity(intent);
					BackgroundDetector.getInstance().setPasswordCheckActive(true);
				}
				BackgroundDetector.getInstance().setDetectorStop(false);
				BackgroundDetector.getInstance().onAppStart(activity);
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
			}

			@Override
			public void onActivityResume(Activity activity) {
				// 友盟统计回调
				MobclickAgent.onResume(activity);
			}

			@Override
			public void onActivityPause(Activity activity) {
				BackgroundDetector.getInstance().onAppPause(activity);
				// 友盟统计回调
				MobclickAgent.onPause(activity);
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
				BackgroundDetector.getInstance().setDetectorStop(true);
				if (activity != null) {
					if (activity.getParent() != null) {
						activity.getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
					} else {
						activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
					}
				}
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
		MXMail.getInstance().init(this);
		// MXDataPlugin.getInstance().setMXScanListener(new MXScanListener() {
		// @Override
		// public boolean onScanSuccessed(Context context, String scanResult) {
		// Uri uri = Uri.parse(scanResult);
		// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		// context.startActivity(intent);
		// return true;
		// }
		// });
	}
	
	
//	@TargetApi(21/*Build.VERSION_CODES.LOLLIPOP*/)
//	private void handleStatusBarColor(Activity activity) {
//		if (Build.VERSION.SDK_INT >= 21/* Build.VERSION_CODES.LOLLIPOP */){
//			Window window = activity.getWindow();
//			// clear FLAG_TRANSLUCENT_STATUS flag:
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//			// finally change the color
//			window.setStatusBarColor(activity.getResources().getColor(R.color.status_bar_color));
//		}
//	}
}
