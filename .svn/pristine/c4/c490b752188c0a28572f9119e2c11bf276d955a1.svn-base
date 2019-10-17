package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.TanShangProgressDetialTable;

import java.text.DecimalFormat;
import java.util.List;

public class TanShangProduceDetialAdapter extends BaseAdapter {

	private List<TanShangProgressDetialTable> list;
	private Context context;
	private int num;
	private TextView tv_detials;

	public TanShangProduceDetialAdapter(List<TanShangProgressDetialTable> list,
			Context context, int num, TextView tv_detials) {
		super();
		this.list = list;
		this.context = context;
		this.num = num;
		this.tv_detials = tv_detials;
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

	DecimalFormat df = new DecimalFormat("#0.000");// 工作量的
	DecimalFormat df_p = new DecimalFormat("#0.0");// 百分率的

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_tanshang_detials_item, null, false);
			vh.tv_1 = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_1);
			vh.tv_2 = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_2);
			vh.tv_3 = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_3);
			vh.tv_4 = (TextView) convertView
					.findViewById(R.id.tv_detialsitem_4);
			convertView.setTag(vh);
		} else if (convertView != null) {
			vh = (ViewHolder) convertView.getTag();
		}
		if (num == 0) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getNlyap()));
			vh.tv_3.setText(df.format(list.get(position).getQnjh()));
			vh.tv_4.setText(df_p.format(list.get(position).getQnjhapl() * 100)
					+ "%");
			tv_detials.setText("全年计划安排进度");
		} else if (num == 1) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getNlywc()));
			vh.tv_3.setText(df.format(list.get(position).getQnjh()));
			vh.tv_4.setText(df_p.format(list.get(position).getQnjhwcl() * 100)
					+ "%");
			tv_detials.setText("全年计划完成进度");
		} else if (num == 2) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getDyap()));
			vh.tv_3.setText(df.format(list.get(position).getDyjh()));
			vh.tv_4.setText(df_p.format(list.get(position).getYdjhapl() * 100)
					+ "%");
			tv_detials.setText("月度安排率");
		} else if (num == 3) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getDywc()));
			vh.tv_3.setText(df.format(list.get(position).getDyap()));
			vh.tv_4.setText(df_p.format(list.get(position).getYdapwcl() * 100)
					+ "%");
			tv_detials.setText("月度完成率");
		} else if (num == 4) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getNlyap()));
			vh.tv_3.setText(df.format(list.get(position).getNlyjh()));
			vh.tv_4.setText(df_p.format(list.get(position).getNljhapl() * 100)
					+ "%");
			tv_detials.setText("年累安排率");
		} else if (num == 5) {
			vh.tv_1.setText(list.get(position).getShortname());
			vh.tv_2.setText(df.format(list.get(position).getNlywc()));
			vh.tv_3.setText(df.format(list.get(position).getNlyap()));
			vh.tv_4.setText(df_p.format(list.get(position).getNljhwcl() * 100)
					+ "%");
			tv_detials.setText("年累完成率");
		}

		return convertView;
	}

	class ViewHolder {
		private TextView tv_1;
		private TextView tv_2;
		private TextView tv_3;
		private TextView tv_4;
	}

}
