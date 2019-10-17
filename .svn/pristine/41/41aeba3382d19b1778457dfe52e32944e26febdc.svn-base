package com.htmitech.emportal.ui.appcenter.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.ui.homepage.BinnerBitmapImageView;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import java.util.ArrayList;
import java.util.List;

public class AdvAdapter extends PagerAdapter {
	private List<BinnerBitmapImageView> views = null;
	private Activity context;
	private HtmitechApplication app;
	private ProxyDealApplicationPlugin mProxyDealApplication;
	private AppliationCenterDao mAppliationCenterDao;
	private ArrayList<AppInfo> buttomAppinfoList;
	public AdvAdapter(Activity context, List<BinnerBitmapImageView> views,
					  HtmitechApplication app, ProxyDealApplicationPlugin mProxyDealApplication,
					  AppliationCenterDao mAppliationCenterDao, ArrayList<AppInfo> buttomAppinfoList) {
		this.views = views;
		this.context = context;
		this.app = app;
		this.mProxyDealApplication = mProxyDealApplication;
		this.mAppliationCenterDao = mAppliationCenterDao;
		this.buttomAppinfoList = buttomAppinfoList;

	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
//		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(ViewGroup parent, final int arg1) {
		final BinnerBitmapImageView mBinnerBitmapImageView = views.get(arg1 % views.size());
//		mBinnerBitmapImageView.mImageView.setTag(mBinnerBitmapImageView.avatarUrl);

		try {
			parent.addView(mBinnerBitmapImageView.mImageView,0);
		}catch (Exception e){
		}

		mBinnerBitmapImageView.mImageView.setOnClickListener(new OnClickListenerAdapter(mBinnerBitmapImageView));
		return mBinnerBitmapImageView.mImageView;
	}

	public class OnClickListenerAdapter implements View.OnClickListener{
		private BinnerBitmapImageView mHomeGridSysle;
		public OnClickListenerAdapter(BinnerBitmapImageView mHomeGridSysle){
			this.mHomeGridSysle = mHomeGridSysle;
		}
		@Override
		public void onClick(View v) {
//			BookInit.getInstance().activityWebView(context,mHomeGridSysle.avatarUrl);
			String appid = mHomeGridSysle.appid;
			String avatarUrl = mHomeGridSysle.avatarUrl;

			if(!TextUtils.isEmpty(avatarUrl)){
				BookInit.getInstance().activityWebView(context, avatarUrl);
			}else if(!TextUtils.isEmpty(appid)){
				AppInfo appInfos = mAppliationCenterDao.getAppInfo(appid);
				int index = -1;
				try {
					for (int i = 0; i < buttomAppinfoList.size(); i++) {
						AppInfo appInfo = buttomAppinfoList.get(i);
						if (appInfo.getApp_id() == Long.parseLong(appid) || appInfo.getParent_app_id() == Long.parseLong(appid)) {
							index = i;
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					int success = mProxyDealApplication.applicationCenterProxy(appInfos);

					switch (success) {
						case 1: //强制升级以及下载

						case 2://可暂时不升级
							com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(context);
							mClentAppUnit.setActivity(ApplicationAllEnum.ZYYYZX);
							break;
					}

				} catch (NotApplicationException e) {
					e.printStackTrace();
				}
			}


//			if (!curOnClick.equals("") && curOnClick.startsWith("http")) {
//				String url = "";
//				if(curOnClick.contains(";")){
//					url = curOnClick.split(";")[1];
//				}else{
//					url = curOnClick;
//				}
//
//				BookInit.getInstance().activityWebView(context, url);
//			} else {
//
//			}
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
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