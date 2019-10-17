package com.htmitech.ztcustom.zt.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.chinarailway.ChilDaccountYZActivity;
import com.htmitech.ztcustom.zt.chinarailway.HomeActivity;
import com.htmitech.ztcustom.zt.dialog.AlertDialog;
import com.htmitech.ztcustom.zt.domain.longin.ListDetails;
import com.htmitech.ztcustom.zt.domain.menu.ListNodes;
import com.htmitech.ztcustom.zt.enums.IntentEnum;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageGridAdapter extends BaseAdapter{
	public Activity context;
	private ArrayList<HomeActivity.BinnerBitmapMessage> mBinnerBitmapMessageList;
	public LayoutInflater inflater;
	/** 控制的postion */
	private int holdPosition;
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	public boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 控制的postion */
	public HomePageGridAdapter(Activity context, ArrayList<HomeActivity.BinnerBitmapMessage> mBinnerBitmapMessageList){
		this.context = context;
		this.mBinnerBitmapMessageList = mBinnerBitmapMessageList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mBinnerBitmapMessageList.size();
	}

	@Override
	public HomeActivity.BinnerBitmapMessage getItem(int arg0) {
		// TODO Auto-generated method stub
		return mBinnerBitmapMessageList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = inflater.inflate(R.layout.zt_fragment_gridview_adapter, null);
		HomeActivity.BinnerBitmapMessage mBinnerBitmapMessage = mBinnerBitmapMessageList.get(arg0);
		ImageView image = (ImageView) arg1.findViewById(R.id.iv_image);
		image.setImageBitmap(mBinnerBitmapMessage.mBitmap);
		if (isChanged && (arg0 == holdPosition) && !isItemShow) {
			image.setVisibility(View.INVISIBLE);
			image.setSelected(true);
			image.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (arg0 == -1 + mBinnerBitmapMessageList.size())) {
			image.setVisibility(View.INVISIBLE);
			image.setSelected(true);
			image.setEnabled(true);
		}
//		image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zt_gridview_item_anim));
//		image.setOnClickListener(new ImageOnclickListener(mBinnerBitmapMessage.appid,mBinnerBitmapMessage.mListNodes));
		return arg1;
	}
	
	public class ImageOnclickListener implements OnClickListener {
		public String appId;
		public ListNodes mListNodes;
		public String upName;
		public ListDetails mListDetails ;
		public ImageOnclickListener(String appId, ListNodes mListNodes) {
			this.appId = appId;
			this.mListNodes = mListNodes;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			ZTCustomInit.get().getmCache().setmListDetails(mListNodes.BusinessTypeId);
//			mListDetails = ZTCustomInit.get().getmCache().getmListDetails();
//			ZTCustomInit.get().getmCache()
			String status = mListDetails.AccountStatus;
			String BusinessTypeName = mListDetails.BusinessTypeName;
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
			upName = mListDetails.BusinessTypeName+"（未激活）";
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

	/** 拖动变更排序 */
	public void exchange(int dragPostion, int dropPostion) {
		HomeActivity.BinnerBitmapMessage dragItem = getItem(dragPostion);
		holdPosition = dropPostion;
		if (dragPostion < dropPostion) {
			mBinnerBitmapMessageList.add(dropPostion + 1, dragItem);
			mBinnerBitmapMessageList.remove(dragPostion);
		} else {
			mBinnerBitmapMessageList.add(dropPostion, dragItem);
			mBinnerBitmapMessageList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}
