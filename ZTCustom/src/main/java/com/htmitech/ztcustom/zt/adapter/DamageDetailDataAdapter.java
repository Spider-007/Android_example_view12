package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.Table;
import com.htmitech.ztcustom.zt.view.MyHScrollView;


import java.util.ArrayList;

public class DamageDetailDataAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ArrayList<Table>> mTableList;
	private LayoutInflater inflater;
	private RelativeLayout mHead;

	public DamageDetailDataAdapter(Context context,
			ArrayList<ArrayList<Table>> mTableList, RelativeLayout mHead) {
		this.context = context;
		this.mTableList = mTableList;
		this.mHead = mHead;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTableList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mTableList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler mViewHodler = null;
		ArrayList<Table> mTables = mTableList.get(arg0);
		if (arg1 == null) {
			mViewHodler = new ViewHodler();
			arg1 = inflater
					.inflate(R.layout.zt_fragment_detail_data_item, null);
			LinearLayout layout = (LinearLayout) arg1
					.findViewById(R.id.layout_addview);
			TextView unit = (TextView) arg1.findViewById(R.id.unit);
			MyHScrollView scrollView1 = (MyHScrollView) arg1
					.findViewById(R.id.horizontalScrollView1);
			MyHScrollView headSrcrollView = (MyHScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							scrollView1));
			for (Table mTable : mTables) {
				if (mTable.key.equals("shortname")) {
					unit.setText("" + mTable.value);
					mViewHodler.textList.add(unit);
				} else {
					layout.addView(addView1(mTable.value));
					mViewHodler.textList.add(tv_detail);
				}
			}
			arg1.setTag(mViewHodler);
		} else {
			mViewHodler = (ViewHodler) arg1.getTag();
		}
		for (int i = 0; i < mViewHodler.textList.size(); i++) {
			mViewHodler.textList.get(i).setText("" + mTables.get(i).value);
		}
		if (arg0 % 2 != 0) {
			arg1.setBackgroundColor(context.getResources().getColor(
					R.color.railway_back));
		} else {
			arg1.setBackgroundColor(context.getResources().getColor(
					R.color.fragment_detail_list_back));
		}
		return arg1;
	}

	public class ViewHodler {
		ArrayList<TextView> textList = new ArrayList<TextView>();
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

	TextView tv_detail;

	private View addView1(String titleName) {
		// TODO 动态添加布局(xml方式)
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		View view = inflater.inflate(
				R.layout.zt_fragment_detail_data_item_text, null);
		tv_detail = (TextView) view.findViewById(R.id.tv_detail);
		tv_detail.setText(titleName);
		view.setLayoutParams(lp);
		return view;

	}

	public void setData(ArrayList<ArrayList<Table>> mTableList,
			RelativeLayout mHead) {
		this.mTableList = mTableList;
		this.mHead = mHead;
		this.notifyDataSetChanged();
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
