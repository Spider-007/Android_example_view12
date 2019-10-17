package com.htmitech.ztcustom.zt.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResult;
import com.htmitech.ztcustom.zt.chinarailway.ChilDaccountYZActivity;
import com.htmitech.ztcustom.zt.chinarailway.HomeActivity;
import com.htmitech.ztcustom.zt.dialog.AlertDialog;
import com.htmitech.ztcustom.zt.domain.menu.ListNodes;
import com.htmitech.ztcustom.zt.enums.IntentEnum;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvAdapter extends PagerAdapter {
	private List<HomeActivity.BinnerBitmapImageView> views = null;
	private Activity context;

	public AdvAdapter(Activity context, List<HomeActivity.BinnerBitmapImageView> views) {
		this.views = views;
		this.context = context;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1).mImageView);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(View arg0, final int arg1) {
		final HomeActivity.BinnerBitmapImageView mBinnerBitmapImageView = views.get(arg1);
		mBinnerBitmapImageView.mImageView
				.setOnClickListener(new ImageOnclickListener(
						mBinnerBitmapImageView.appid,
						mBinnerBitmapImageView.mListNodes));
		((ViewPager) arg0).addView(mBinnerBitmapImageView.mImageView, 0);
		return views.get(arg1).mImageView;
	}

	public class ImageOnclickListener implements OnClickListener {
		public String appId;
		public ListNodes mListNodes;
		public String upName;
		public GetCisAccountByAppResult mListDetails ;
		public ImageOnclickListener(String appId, ListNodes mListNodes) {
			this.appId = appId;
			this.mListNodes = mListNodes;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//    <--------------------Administrator -> 2019-8-16:15:29:通过此方法添加 缓存数据--------------------->
			Log.e("YJH", "AdvAdapter - > onClick----->> "+mListNodes.BusinessTypeId);
//			ZTCustomInit.get().getmCache().setmListDetails(mListNodes.BusinessTypeId);
			mListDetails = ZTCustomInit.get().getmCache().getCisAccountDetail();
			//    <--------------------Administrator -> 2019-8-15:18:41: cisAccountId 有问题--------------------->
			String status = mListDetails.cisAccountId;
			String BusinessTypeName = mListDetails.cisName;
			String title = "";
			if(status.equals("2")){
				title = "激活";
			}else if(status.equals("3")){
				title = "验证";
			}else{
				Class<? extends Activity> c = IntentEnum.getActivity(appId);
				if (appId != null && c != null) {
					ZTActivityUnit.switchTo(context, c, null);
				}
				return;
			}
			upName = mListDetails.cisName+"（未激活）";
			new AlertDialog(context).builder().setMsg("请"+title + BusinessTypeName)
					.setTitle(title).setCancelable(false)
					.setNegativeButton("", new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
						}
					}).setPositiveButton("", new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("titleName", upName);
							params.put("ListDetails", mListDetails);
							ZTActivityUnit.switchTo(context, ChilDaccountYZActivity.class, params);
						}
					}).show();
			
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