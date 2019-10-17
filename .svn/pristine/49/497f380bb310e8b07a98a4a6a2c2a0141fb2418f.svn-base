package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.CheJianDayReportResultBean;
import com.htmitech.ztcustom.zt.view.hvlistview.MyHScrollView;

import java.util.List;

public class CheJianDayReportTableAdapter extends BaseAdapter {

	private List<CheJianDayReportResultBean> list;
	private Context context;
	private RelativeLayout mHead;

	public CheJianDayReportTableAdapter(List<CheJianDayReportResultBean> list,
			Context context, RelativeLayout mHead) {
		super();
		this.list = list;
		this.context = context;
		this.mHead = mHead;
		notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, ViewGroup continer) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.cheijan_day_report_item, null, false);

			MyHScrollView scrollView1 = (MyHScrollView) convertView
					.findViewById(R.id.horizontalScrollView1);
			vh.scrollView=scrollView1;
			
			vh.tv_jhh = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_jhh);
			vh.tv_gq = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tsgq);
			vh.tv_bz = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tsbz);
			vh.tv_sblx = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_sblx);
			vh.tv_xb = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_xb);
			vh.tv_xm = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_xm);
			vh.tv_xingbie = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_xingbie);
			vh.tv_qdlc = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_qdlc);
			vh.tv_zdlc = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_zdlc);
			vh.tv_tsbs = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tsbs);
			vh.tv_tsjhs = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tsjhs);
			vh.tv_tsaps = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tsaps);
			vh.tv_tswcs = (TextView) convertView
					.findViewById(R.id.tv_chejianitem_tswcs);
			MyHScrollView headSrcrollView = (MyHScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							scrollView1));
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_jhh.setText(list.get(position).getJhh());
		vh.tv_gq.setText(list.get(position).getGq_shortname());
		vh.tv_bz.setText(list.get(position).getBz_shortname());
		vh.tv_xm.setText(list.get(position).getLine_name());
		vh.tv_xb.setText(list.get(position).getLine_typename());
		vh.tv_sblx.setText(list.get(position).getSblx_name());
		vh.tv_xingbie.setText(list.get(position).getXb_name());
		vh.tv_qdlc.setText(list.get(position).getStart_milage());
		vh.tv_zdlc.setText(list.get(position).getEnd_milage());
		vh.tv_tsbs.setText(list.get(position).getTs_bs());
		vh.tv_tsjhs.setText(list.get(position).getTs_jhs() + "");
		vh.tv_tsaps.setText(list.get(position).getTs_aps() + "");
		vh.tv_tswcs.setText(list.get(position).getTs_wcs() + "");
		return convertView;
	}

	class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
		MyHScrollView mScrollViewArg;

		public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
			mScrollViewArg = scrollViewar;
		}

		@Override
		public void onScrollChanged(int l, int t, int oldl, int oldt) {
			mScrollViewArg.smoothScrollTo(l, t);
		}
	};

	class ViewHolder {
		private TextView tv_jhh;
		private TextView tv_gq;
		private TextView tv_bz;
		private TextView tv_sblx;
		private TextView tv_xb;
		private TextView tv_xm;
		private TextView tv_xingbie;
		private TextView tv_qdlc;
		private TextView tv_zdlc;
		private TextView tv_tsbs;
		private TextView tv_tsjhs;
		private TextView tv_tsaps;
		private TextView tv_tswcs;

		HorizontalScrollView scrollView;
	}

}
