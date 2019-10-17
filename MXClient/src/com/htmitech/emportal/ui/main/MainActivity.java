/*package com.htmitech.emportal.ui.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.common.lib.residemenu.ResideMenu;
import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.emportal.ui.appcenter.AppCenterActivity;
import com.htmitech.emportal.ui.circle.CircleFragmentActivity;
import com.htmitech.emportal.ui.communication.CommunicationFragmentActivity;
import com.htmitech.emportal.ui.contacts.ContactActivity;
import com.htmitech.emportal.ui.daiban.DaiBanFragmentActivity;
import com.htmitech.emportal.ui.daiyue.DaiYueFragmentActivity;
import com.htmitech.emportal.ui.widget.BottomTabIndicator;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.FixedSpeedScroller;
import com.htmitech.emportal.ui.widget.JazzyViewPager.TransitionEffect;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.MyMessageDialog;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.minxing.client.AppConstants;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.LoginActivity;
import com.minxing.client.activity.SystemSettingGesturePasswordActivity;
import com.minxing.client.activity.SystemSettingMessageNotificationActivity;
import com.minxing.client.tab.MenuTabHost;
import com.minxing.client.upgrade.AppUpgradeInfo;
import com.minxing.client.util.BackgroundDetector;
import com.minxing.client.util.PreferenceUtils;
import com.minxing.client.util.Utils;
import com.minxing.client.widget.CircleTopRightPopMenu;
import com.minxing.client.widget.SystemMainTopRightPopMenu;
import com.minxing.kit.MXConstants;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.CirclePopCenter;
import com.minxing.kit.api.bean.MXCircleGroup;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.ui.chat.ChatManager;
import com.minxing.kit.ui.chat.MXChatActivity;
import com.minxing.kit.ui.chat.ChatManager.OnChatFinishListener;
import com.minxing.kit.ui.chat.ChatManager.ShareListener;
import com.minxing.kit.ui.circle.CircleManager;
import com.minxing.kit.ui.circle.CircleManager.OnGroupChangeListener;
import com.minxing.kit.ui.circle.MXCircleActivity;

public class MainActivity extends MyBaseFragmentActivity implements
		ViewPager.OnPageChangeListener, IBottomItemSelectCallBack {

	private LocalActivityManager mManager = null;
	private MainViewPager mViewpager;
	private ViewPagerAdapter mViewPagerAdapter;
	private DaiBanFragmentActivity mDaiBanFragment;
	private DaiYueFragmentActivity mDaiYueFragment;
	private CommunicationFragmentActivity mCommunicationFragment;
	private CircleFragmentActivity mCircleFragment;
	private MyMessageDialog mMessageDialog;
	public BottomTabIndicator mBottomBar;

	public static final int POSITION_DAI_BAN = 0;
	public static final int POSITION_DAI_YUE = 1;
	public static final int POSITION_COMMUNICATION = 2;
	public static final int POSITION_CIRCLE = 3;


	private static final int PAGE_SIZE = 4;
	private ResideMenu mResideMenu;

	private long mInterval = 0; // 兩次返回鍵的间隔时间
	private final long mDiffTime = 2000; // 两次返回键小于2秒 则退出

	private BroadcastReceiver receiver = null;
	
	
	private Intent intentChat = null;
	
	private final ArrayList<View> list = new ArrayList<View>();
	
	public void onCreate(Bundle savedInstanceState) {
		mManager = new LocalActivityManager(this, true);
		mManager.dispatchCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		
		
	}

	private View getView(String id, Intent intent) {
		
		return mManager.startActivity(id, intent).getDecorView();
	}
	
	private View refreshView(String id, Intent intent) {
		mManager.destroyActivity(id, true);
		return mManager.startActivity(id, intent).getDecorView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if (getIntent().getAction() != null && !"".equals(getIntent().getAction()) && getIntent().getAction().equals("finish")) {
			// 打开登录页面
			Intent intent = new Intent(this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, getIntent().getBooleanExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false));
			intent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, getIntent().getIntExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1));
			startActivity(intent);
			finish();
			return;
		}
	}

	protected void initView() {
		// View 初始化
		mViewpager = (MainViewPager) this.findViewById(R.id.main_viewpager);
		// mViewpager.setTransitionEffect(TransitionEffect.Accordion);
		mViewpager.setTransitionEffect(TransitionEffect.Standard);
		changeScroller(mViewpager);
		setSpeed(300);
		mViewpager.setNoScroll(false);
		mViewpager.setOffscreenPageLimit(3);
		mViewpager.setOnPageChangeListener(this);

		
		
		
		Intent intent = new Intent(this, DaiBanFragmentActivity.class);
		list.add(getView(POSITION_DAI_BAN + "", intent));
		Intent intent1 = new Intent(this, DaiYueFragmentActivity.class);
		list.add(getView(POSITION_DAI_YUE + "", intent1));
		
		
	
		intentChat = new Intent(this, MXChatActivity.class);
		initChatHeaderView(true);
		list.add(getView(POSITION_COMMUNICATION + "", intentChat));
		
		Intent intent3 = new Intent(this, MXCircleActivity.class);
		initCircleHeaderView(true);
		list.add(getView(POSITION_CIRCLE + "", intent3));

		

		mViewPagerAdapter = new ViewPagerAdapter(list);

		mViewpager.setAdapter(mViewPagerAdapter);
		mViewpager.setCurrentItem(POSITION_DAI_BAN);

		mBottomBar = (BottomTabIndicator) this
				.findViewById(R.id.main_bottom_bar);
		mBottomBar.setViewPager(mViewpager);
		mBottomBar.setBottomItemSelectCallBack(this);

		if (mResideMenu == null) {
//			mResideMenu = new ResideMenu(HtmitechApplication.getInstance());
			mResideMenu = new ResideMenu(MainActivity.this);
		}
		mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
		// mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
		mResideMenu.attachToActivity(this);
		initResideMenu();
		
		initChatListener();
//		updateAll();
//		receiver = new BroadcastReceiver() {
//			@Override
//			public void onReceive(Context context, Intent intent) {
//				if (intent.getAction().equals(MXConstants.BroadcastAction.MXKIT_REVOKE_DISPATCH_UNSEEN)
//						|| intent.getAction().equals(AppConstants.MXCLIENT_REFRESH_NEW_VERSION)) {
//					updateAll();
//					if (PreferenceUtils.checkUpgradeMark(MainActivity.this)) {
////						slidingMenu.UpgradeNewVersionMark(true);
//
//						AppUpgradeInfo upgradeInfo = (AppUpgradeInfo) intent.getSerializableExtra(AppConstants.MXCLIENT_UPGRADE_INFO);
//						if (upgradeInfo != null && upgradeInfo.getVersion() != null && !"".equals(upgradeInfo.getVersion())) {
//							Utils.showUpgradeDialog(MainActivity.this, upgradeInfo);
//						}
//					} else {
////						slidingMenu.UpgradeNewVersionMark(false);
//					}
//				}
//			}
//		};

//		IntentFilter filter = new IntentFilter();
//		filter.addAction(MXConstants.BroadcastAction.MXKIT_REVOKE_DISPATCH_UNSEEN);
//		filter.addAction(AppConstants.MXCLIENT_REFRESH_NEW_VERSION);
//		registerReceiver(receiver, filter);
	}

	
	
	private void updateAll() {
		MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
		if (currentUser == null) {
			return;
		}
		
		
		Log.d("MainActivity", "updateAll");
		
		int unreadMessage = MXAPI.getInstance(this).queryNetworkChatUnread(currentUser.getNetworkID());
		if (unreadMessage > 0) {
			String unReadCount = unreadMessage <= 99 ? String.valueOf(unreadMessage) : "...";
//			chatTabItem.showNumberMarker(unReadCount);  显示数字
			Log.d("MainActivity", "收到消息：" + unreadMessage);
			//刷新消息页面
			intentChat = new Intent(this, MXChatActivity.class);
			initChatHeaderView(true);
			ViewPagerAdapter mbViewPagerAdapter = (ViewPagerAdapter)mViewpager.getAdapter();
			mbViewPagerAdapter.list.set(POSITION_COMMUNICATION, (refreshView(POSITION_COMMUNICATION + "", intentChat)));
			mbViewPagerAdapter.notifyDataSetChanged();
//			mViewpager.setAdapter(mbViewPagerAdapter);
//			if (mViewpager.getCurrentItem() == POSITION_COMMUNICATION) 
//				mViewpager.setCurrentItem(POSITION_COMMUNICATION);
			
		} else {
//			chatTabItem.hideNumberMarker();
		}

		if (MXAPI.getInstance(this).checkNetworkCircleUnread(currentUser.getNetworkID())) {
//			circleTabItem.showMarker();
//			if (mTabHost.getCurrentTabTag().equals(MenuTabHost.TAB_TAG_CIRCLES)) {
//				boolean isAPPSwitchToBackground = BackgroundDetector.getInstance().isApplicationSentToBackground(this);
//				boolean isScreenLocked = BackgroundDetector.getInstance().isScreenLocked(this);
//				if (!isAPPSwitchToBackground && !isScreenLocked) {
//					MXAPI.getInstance(this).setCircleAutoRefresh();
//				}
//			} else {
//				MXAPI.getInstance(this).setCircleAutoRefresh();
//			}
		} else {
//			circleTabItem.hideMarker();
		}
//		refreshAlertIcon();
	}
	
//	private void refreshAlertIcon() {
//		MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
//		refreshHandlerLayout(chatHandler, currentUser);
//		refreshHandlerLayout(contactsHandler, currentUser);
//		refreshHandlerLayout(appCenterHandler, currentUser);
//		refreshHandlerLayout(circleHandler, currentUser);
//	}
	
	*//**
	 * 初始化聊天header view
	 * 
	 *//*
	private void initChatHeaderView(Boolean showHandle) {
		ChatManager chatManager = MXUIEngine.getInstance().getChatManager();
		
		RelativeLayout chatHeader = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.chat_header_view, null);
		View chatHandler = chatHeader.findViewById(R.id.system_handle);
		ImageButton bacKBtn = (ImageButton) chatHeader.findViewById(R.id.title_left_back_button);
		if (showHandle) {
			chatHandler.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MainActivity.this.getResideMenu().openMenu(
							ResideMenu.DIRECTION_LEFT);
//					drawerLayout.openDrawer(Gravity.START);
//					if (slidingMenu != null) {
//						MXCurrentUser user = MXAPI.getInstance(MainActivity.this).currentUser();
//						slidingMenu.refreshNetwork(user);
//					}
				}
			});
		} else {
			chatHandler.setVisibility(View.GONE);
			bacKBtn.setVisibility(View.VISIBLE);
			bacKBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		final ImageButton addButton = (ImageButton) chatHeader.findViewById(R.id.title_right_new_function);
		addButton.setVisibility(View.VISIBLE);
		final SystemMainTopRightPopMenu functionPopMenu = new SystemMainTopRightPopMenu(MainActivity.this);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Handler().post(new Runnable() {
					public void run() {
						if (!functionPopMenu.isShowing()) {
							functionPopMenu.showAsDropDown(addButton);
						}
					}
				});
			}
		});
		chatManager.setHeaderView(chatHeader);
		chatManager.setBackListener(new ChatManager.HomeScreenBackListener() {

			@Override
			public boolean onBack(Context context) {
				moveTaskToBack(true);
				return true;
			}
		});
	}

	
	*//**
	 * 初始化圈子header view
	 * 
	 *//*
	private void initCircleHeaderView(Boolean showHandle) {
		final CircleManager circleManager = MXUIEngine.getInstance().getCircleManager();
		RelativeLayout circleHeader = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.circle_header_view, null);
		View circleHandler = (LinearLayout) circleHeader.findViewById(R.id.system_handle);
		final ImageButton addButton = (ImageButton) circleHeader.findViewById(R.id.title_right_new_function);

		LinearLayout middleTitleBar = (LinearLayout) circleHeader.findViewById(R.id.middle_title_bar);
		final TextView title = (TextView) circleHeader.findViewById(R.id.title);
		final CircleTopRightPopMenu circleTopRightPopMenu = new CircleTopRightPopMenu(MainActivity.this);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Handler(MainActivity.this.getMainLooper()).post(new Runnable() {
					public void run() {
						if (!circleTopRightPopMenu.isShowing()) {
							circleTopRightPopMenu.showAsDropDown(addButton);
						}
					}
				});
			}
		});

		if (showHandle) {
			circleHandler.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MainActivity.this.getResideMenu().openMenu(
							ResideMenu.DIRECTION_LEFT);
					MXCurrentUser user = MXAPI.getInstance(MainActivity.this).currentUser();
					//初始化用户信息
					if(user != null){
						MainActivity.this.getResideMenu().updateCurrentUserAvatar(user);
					}
//					drawerLayout.openDrawer(Gravity.START);
//					if (slidingMenu != null) {
//						MXCurrentUser user = MXAPI.getInstance(ClientTabActivity.this).currentUser();
//						slidingMenu.refreshNetwork(user);
//					}
				}
			});
		} else {
			ImageButton bacKBtn = (ImageButton) circleHeader.findViewById(R.id.title_left_back_button);
			circleHandler.setVisibility(View.GONE);
			bacKBtn.setVisibility(View.VISIBLE);
			bacKBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}

		middleTitleBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取组管理界面
				CirclePopCenter circlePopCenter = circleManager.getCirclePopCenter();
				if (!circlePopCenter.isShowing()) {
					circlePopCenter.showAsDropDown(v);
				}
			}
		});

		circleManager.setOnGroupChangeListener(new OnGroupChangeListener() {

			@Override
			public void onGroupChange(MXCircleGroup group) {
				// 监听组切换事件来更新标题
				title.setText(group.getName());
			}
		});
		circleManager.setHeaderView(circleHeader);
		circleManager.setBackListener(new CircleManager.HomeScreenBackListener() {

			@Override
			public boolean onBack(Context context) {
				moveTaskToBack(true);
				return true;
			}
		});
	}

	
	
	private void initChatListener() {
		ChatManager chatManager = MXUIEngine.getInstance().getChatManager();
		chatManager.setShareListener(new ShareListener() {
			@Override
			public void onSuccess() {
//				showTabByTag(MenuTabHost.TAB_TAG_CHAT);
			}
		});

		chatManager.setOnChatFinishListener(new OnChatFinishListener() {
			@Override
			public void onBackToChatRoot(Context context) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//				showTabByTag(MenuTabHost.TAB_TAG_CHAT);
			}
		});
	}

	
	
	初始化右侧按钮列表菜单
	private void initResideMenu() {
		
		MXCurrentUser user = MXAPI.getInstance(MainActivity.this).currentUser();
		//初始化用户信息
		if(user != null){
			mResideMenu.updateCurrentUserAvatar(user);
		}
		
		新消息提醒
		ResideMenuItem mResideMenuItem_SettingMessageNotification = new ResideMenuItem(
				HtmitechApplication.getInstance(), R.drawable.btn_person, "新消息提醒");
		((LinearLayout) mResideMenuItem_SettingMessageNotification).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SystemSettingMessageNotificationActivity.class);
				startActivity(intent);
			}
		}); 
		mResideMenu.addMenuItem(mResideMenuItem_SettingMessageNotification,
				ResideMenu.DIRECTION_LEFT);
		
		
		手势密码
		ResideMenuItem mResideMenuItem_GesturePassword = new ResideMenuItem(
				HtmitechApplication.getInstance(), R.drawable.btn_person, "手势密码");
		((LinearLayout) mResideMenuItem_GesturePassword).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SystemSettingGesturePasswordActivity.class);
				startActivity(intent);
			}
		}); 
		mResideMenu.addMenuItem(mResideMenuItem_GesturePassword,
				ResideMenu.DIRECTION_LEFT);
		
		通讯录
		ResideMenuItem mResideMenuItem_addressBook = new ResideMenuItem(
				HtmitechApplication.getInstance(), R.drawable.btn_person, "通讯录");
		((LinearLayout) mResideMenuItem_addressBook).setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ContactActivity.class);
				startActivity(intent);
				
			}
		}); 
		mResideMenu.addMenuItem(mResideMenuItem_addressBook,
				ResideMenu.DIRECTION_LEFT);
		
		应用中心
		
		ResideMenuItem mResideMenuItem_appmarket = new ResideMenuItem(
				HtmitechApplication.getInstance(), R.drawable.btn_person,
				"应用中心");
		((LinearLayout) mResideMenuItem_appmarket).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				initAppcenterHeaderView(false);
				Intent intent = new Intent(MainActivity.this, AppCenterActivity.class);
				startActivity(intent);
				
			}
		}); 
		mResideMenu.addMenuItem(mResideMenuItem_appmarket,
				ResideMenu.DIRECTION_LEFT);
		
//		ResideMenuItem mResideMenuItem_specialSettings = new ResideMenuItem(
//				HtmitechApplication.getInstance(), R.drawable.btn_person,
//				"个性化设置");
//		mResideMenu.addMenuItem(mResideMenuItem_specialSettings,
//		ResideMenu.DIRECTION_LEFT);
		
		
		ResideMenuItem mResideMenuItem_version = new ResideMenuItem(
				HtmitechApplication.getInstance(), R.drawable.btn_person,
				"版本信息");
		

		mResideMenu.addMenuItem(mResideMenuItem_version,
				ResideMenu.DIRECTION_LEFT);
	}

	public ResideMenu getResideMenu() {
		return mResideMenu;
	}

	protected int getLayoutById() {
		return R.layout.activity_main;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// toExit();
			long spent = System.currentTimeMillis() - mInterval;
			if (spent < mDiffTime) {
				exit();
			} else {
				ToastInfo toast = ToastInfo.getInstance(HtmitechApplication
						.instance());
				toast.setView(
						LayoutInflater.from(HtmitechApplication.instance()),
						R.drawable.prompt_warn, R.string.app_exit_tip);
				toast.show(Toast.LENGTH_SHORT);
				mInterval = System.currentTimeMillis();
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			smothScorllToView(POSITION_DAI_BAN);
		}
		return true;
	}

	public void toExit() {
		try {
			mMessageDialog = new MyMessageDialog(this, R.style.mydialog,
					confirmListener, cancelListener);
			mMessageDialog.setViewText(R.drawable.prompt_warn, "你真的要退出吗?");
			mMessageDialog.show();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private DialogCancelListener cancelListener = new DialogCancelListener() {
		@Override
		public void onCancel() {
			if (mMessageDialog != null) {
				mMessageDialog.dismiss();
			}
		}
	};

	private DialogConfirmListener confirmListener = new DialogConfirmListener() {
		public void onConfirm() {
			if (mMessageDialog != null)
				mMessageDialog.dismiss();
			exit();
		}
	};

	private void exit() {
		finish();
		HtmitechApplication.instance().exit();
	}

	public void smothScorllToView(int id) {
		mViewpager.setCurrentItem(id, true);
	}

	class ViewPagerAdapter extends PagerAdapter {
		public List<View> list = new ArrayList<View>();

		public ViewPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}

	@Override
	public void onFragmentTabClick(int position) {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		if (fragments == null)
			return;
		if (position >= fragments.size()) {
			return;
		}
		
		if (list.size() == 0)
			return;
		switch (position) {
		case POSITION_DAI_BAN:
//			Intent intent = new Intent(this, DaiBanFragmentActivity.class);
//			((ViewPagerAdapter)mViewpager.getAdapter()).list.set(POSITION_DAI_BAN, (refreshView(POSITION_DAI_BAN + "", intent)));
//			((ViewPagerAdapter)mViewpager.getAdapter()).notifyDataSetChanged();
			Log.d("onFragmentTabClick", "POSITION_DAI_BAN");
			break;
		case POSITION_DAI_YUE:
//			Intent intent2 = new Intent(this, DaiYueFragmentActivity.class);
//			((ViewPagerAdapter)mViewpager.getAdapter()).list.set(POSITION_DAI_YUE, (refreshView(POSITION_DAI_YUE + "", intent2)));
//			((ViewPagerAdapter)mViewpager.getAdapter()).notifyDataSetChanged();
			Log.d("onFragmentTabClick", "POSITION_DAI_YUE");
			break;
		case POSITION_COMMUNICATION:
//			intentChat = new Intent(this, MXChatActivity.class);
//			initChatHeaderView(true);
//			ViewPagerAdapter mbViewPagerAdapter = (ViewPagerAdapter)mViewpager.getAdapter();
//			mbViewPagerAdapter.list.set(POSITION_COMMUNICATION, (refreshView(POSITION_COMMUNICATION + "", intentChat)));
//			mbViewPagerAdapter.notifyDataSetChanged();
			Log.d("onFragmentTabClick", "POSITION_COMMUNICATION");
			break;
		case POSITION_CIRCLE:
//			MXAPI.getInstance(MainActivity.this).forceRefreshCircle();
			Log.d("onFragmentTabClick", "POSITION_CIRCLE");
			break;
		}
	}

	FixedSpeedScroller mScroller = null;

	private void setSpeed(int duration) {
		if (null != mScroller) {
			mScroller.setmDuration(duration);
		}
	}

	private void changeScroller(ViewPager viewPager) {
		try {
			Field mField;
			mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			mScroller = new FixedSpeedScroller(this,
					new AccelerateDecelerateInterpolator());
			mField.set(viewPager, mScroller);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case POSITION_DAI_BAN:
			Log.d("onPageSelected", "POSITION_DAI_BAN");
//			if (list.size() == 0)
//				return;
//			
//			Intent intent = new Intent(this, DaiBanFragmentActivity.class);
//			list.set(0, (refreshView(POSITION_DAI_BAN + "", intent)));
//			
//			
//			mViewPagerAdapter = new ViewPagerAdapter(list);
//
//			mViewpager.setAdapter(mViewPagerAdapter);
			
			break;
		case POSITION_DAI_YUE:
			Log.d("onPageSelected", "POSITION_DAI_YUE");
//			if (list.size() == 0)
//				return;
//			
//			Intent intent2 = new Intent(this, DaiYueFragmentActivity.class);
//			list.set(1, (refreshView(POSITION_DAI_YUE + "", intent2)));
//			mViewPagerAdapter = new ViewPagerAdapter(list);
//			mViewpager.setAdapter(mViewPagerAdapter);
			
			break;
		case POSITION_COMMUNICATION:
			Log.d("onPageSelected", "POSITION_DAI_YUE");
//			if (list.size() == 0)
//				return;
//			intentChat = new Intent(this, MXChatActivity.class);
//			initChatHeaderView(true);
//			list.set(2, (refreshView(POSITION_COMMUNICATION + "", intentChat)));
//			mViewPagerAdapter = new ViewPagerAdapter(list);
//			mViewpager.setAdapter(mViewPagerAdapter);
//			Log.d("onPageSelected", "POSITION_COMMUNICATION");
			break;
		case POSITION_CIRCLE:
			Log.d("onPageSelected", "POSITION_CIRCLE");
			MXAPI.getInstance(MainActivity.this).forceRefreshCircle();
			
			break;
		}
	}
}
*/