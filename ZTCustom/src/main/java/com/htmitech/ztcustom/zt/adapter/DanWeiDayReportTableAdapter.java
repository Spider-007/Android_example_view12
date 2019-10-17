package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.DanWeiDayReportResultBean;

import java.text.DecimalFormat;
import java.util.List;

public class DanWeiDayReportTableAdapter extends BaseAdapter {

	private List<DanWeiDayReportResultBean> list;
	private Context context;
	private DecimalFormat df;

	public DanWeiDayReportTableAdapter(List<DanWeiDayReportResultBean> list,
			Context context,DecimalFormat df) {
		super();
		this.list = list;
		this.context = context;
		this.df=df;
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
					R.layout.fragment_tanshang_detials_item, null, false);
			vh.tv_danwei = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_1);
			vh.tv_jhs = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_2);
			vh.tv_aps = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_3);
			vh.tv_wcs = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_4);
			if(position%2==0){
				convertView.setBackgroundColor(context.getResources().getColor(R.color.listview_item_jiaoti_color));
			}
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_danwei.setText(list.get(position).getOrgname());
		vh.tv_jhs.setText(df.format(list.get(position).getTs_jhs() == 0 ? 0 :list.get(position).getTs_jhs()));
		vh.tv_aps.setText(df.format(list.get(position).getTs_aps() == 0 ? 0 :list.get(position).getTs_aps()));
		vh.tv_wcs.setText(df.format(list.get(position).getTs_wcs() == 0 ? 0 :list.get(position).getTs_wcs()));
		return convertView;
	}

	class ViewHolder {
		private TextView tv_danwei;
		private TextView tv_jhs;
		private TextView tv_aps;
		private TextView tv_wcs;
	}

}
