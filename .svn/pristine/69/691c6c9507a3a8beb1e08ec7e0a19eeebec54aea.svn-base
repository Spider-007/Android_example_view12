package com.htmitech.ztcustom.zt.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.chinarailway.ZBRealTimeItemActivity;
import com.htmitech.ztcustom.zt.domain.ZBBroadResult;
import com.htmitech.ztcustom.zt.domain.ZBBroadUp;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.view.ZBWaterDropListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ZBReportFragment extends Fragment implements
		ZBWaterDropListView.IWaterDropListViewListener {
	private ZBWaterDropListView listview;
	private ArrayList<ZBBroadResult> list;
	private mSimpleAdapter adapter;
	private String keyword = "";
	private TextView tv_no_value;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				listview.stopRefresh();
				break;
			case 2:
				listview.stopLoadMore();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.zb_fragment_report, null);
		listview = (ZBWaterDropListView) view.findViewById(R.id.lv_report);
		tv_no_value = (TextView) view.findViewById(R.id.tv_no_value);
		tv_no_value.setVisibility(View.GONE);
		getData("",0);
		list = new ArrayList<ZBBroadResult>();
		int resource = R.layout.zb_waterdroplistview_item;
		String[] from = { "title_name", "sign", "time" };
		int[] to = { R.id.tv_title_name, R.id.tv_sign,
				R.id.tv_report_time };
		adapter = new mSimpleAdapter();
		listview.setAdapter(adapter);
		listview.setWaterDropListViewListener(this);
		listview.setPullLoadEnable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				list.get(position - 1).setStatus("true");
				adapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.putExtra("msgid", list.get(position - 1).getMsgid());
				intent.setClass(getActivity(), ZBRealTimeItemActivity.class);
				startActivity(intent);

			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case 0:
			if (resultCode == 1) {
				keyword = data.getStringExtra("keyword");
			}
			break;
		default:
			break;
		}
		
	}

	class mSimpleAdapter extends BaseAdapter {

		private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
		LayoutInflater inflater = null;
		public mSimpleAdapter() {
			inflater = LayoutInflater.from(getActivity());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler mViewHodler = null;
			ZBBroadResult mZBBroadResult = list.get(position);
			if(convertView == null){
				mViewHodler = new ViewHodler();
				convertView = inflater.inflate( R.layout.zb_waterdroplistview_item, null);
				mViewHodler.ivRed = (ImageView) convertView.findViewById(R.id.iv_inner_red);
				mViewHodler.iv_title_pic = (ImageView) convertView.findViewById(R.id.iv_title_pic);
				mViewHodler.tv_title_name = (TextView) convertView.findViewById(R.id.tv_title_name);
				mViewHodler.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
				mViewHodler.tv_report_time = (TextView) convertView.findViewById(R.id.tv_report_time);
				convertView.setTag(mViewHodler);
			}else{
				mViewHodler = (ViewHodler) convertView.getTag();
			}
			if(mZBBroadResult.getIsnew().equals("0") || mZBBroadResult.getStatus().equals("true")){
				mViewHodler.ivRed.setVisibility(View.GONE);
			}else{
				mViewHodler.ivRed.setVisibility(View.VISIBLE);
			}
			if(mZBBroadResult.getSblx().equals("DXHF") || mZBBroadResult.getSblx().equals("ZXHF") || mZBBroadResult.getSblx().equals("ZXHF")|| mZBBroadResult.getSblx().equals("HF")){
				mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.welding_line));
			}else if(mZBBroadResult.getSblx().equals("DC")){
				mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.turnout));
			}else if(mZBBroadResult.getSblx().equals("ZXGG")||mZBBroadResult.getSblx().equals("GG")){
				mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.rail));
			}
			mViewHodler.tv_title_name.setText(""+mZBBroadResult.getMsgdesc());
			mViewHodler.tv_sign.setText(""+mZBBroadResult.getWzlxname());
			mViewHodler.tv_report_time.setText(""+mZBBroadResult.getMsgtime());
			int colorPos = position % colors.length;
			if (colorPos == 1)
				convertView.setBackgroundColor(Color.rgb(0, 20, 67));
			else
				convertView.setBackgroundColor(Color.rgb(0, 9, 49));
			return convertView;
		}

		public class ViewHodler{
			ImageView ivRed;
			TextView tv_title_name;
			TextView tv_sign;
			TextView tv_report_time;
			ImageView iv_title_pic;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
	}
	public void setKeyWord(String keyWord){
		this.keyword = keyWord;
		list.clear();
	}
	private ZBBroadUp updateData(String msgid) {

		ZBBroadUp broadup = new ZBBroadUp();
		broadup.setUserId(   ZTCustomInit.get().getmCache().currentUserId);
		broadup.setMsgType("1");
		broadup.setKeyWord(keyword);
		broadup.setBegintime("");
		broadup.setRowCount("10");
		broadup.setMsgid(msgid);
		return broadup;
	}
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void getData(final String endTime,final int type) {


		AnsynHttpRequest.request(getActivity(), updateData(endTime),
				CHTTP.GETLISTREALTIMEBROAD, CHTTP.POST, new ObserverCallBack() {

					@Override
					public void success(String successMessage) {
						ArrayList<ZBBroadResult> crrentList = (ArrayList<ZBBroadResult>) JSON.parseArray(
								successMessage, ZBBroadResult.class);
						if(crrentList == null || crrentList.size() == 0){
							if(endTime.equals("")){
								tv_no_value.setVisibility(View.VISIBLE);
								listview.setVisibility(View.GONE);
							}
							return ;
						}
						list.addAll(crrentList);
						adapter.notifyDataSetChanged();
						handler.sendEmptyMessage(type);
					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub
					}
				});


	}

	@Override
	public void onRefresh() {
		list.clear();
		getData("",1);

	}

	@Override
	public void onLoadMore() {
		if(list.size() == 0){
			return ;
		}
		getData(list.get(list.size() - 1).getMsgid(),2);
	}

}
