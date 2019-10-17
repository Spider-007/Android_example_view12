package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.interfaces.CallBackDamageTypeGridListener;

import java.util.ArrayList;

public class DamageTypeGridAdapter extends BaseAdapter {
	public Context context;
	public ArrayList<Dictitemlist> array;
	public LayoutInflater inflater;
	private CallBackDamageTypeGridListener mCallBackDamageTypeGridListener;

	public DamageTypeGridAdapter(Context context,
			ArrayList<Dictitemlist> array,
			CallBackDamageTypeGridListener mCallBackDamageTypeGridListener) {
		this.context = context;
		this.array = array;
		this.mCallBackDamageTypeGridListener = mCallBackDamageTypeGridListener;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return array.get(arg0);
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
		Dictitemlist mDictitemlist = array.get(arg0);
		if (arg1 == null) {
			mViewHodler = new ViewHodler();
			arg1 = inflater
					.inflate(R.layout.zt_fragment_damage_type_adapter, null);
			mViewHodler.tvName = (TextView) arg1.findViewById(R.id.tv_name);
			if(mViewHodler.tvName.length() > 5){
				arg1.setLayoutParams(new GridView.LayoutParams(80, 100));//重点行
			}
			arg1.setTag(mViewHodler);
		} else {
			mViewHodler = (ViewHodler) arg1.getTag();
		}
		mViewHodler.tvName.setText(mDictitemlist.getName());
		mViewHodler.tvName.setOnClickListener(new NameOnClickListener(
				mDictitemlist, mViewHodler.tvName));
		return arg1;
	}


	class ViewHodler {
		TextView tvName;
	}

	private class DamageCaeck {
		Dictitemlist mDictitemlist;
		boolean isFlag;

		public DamageCaeck(Dictitemlist mDictitemlist, boolean isFlag) {
			this.mDictitemlist = mDictitemlist;
			this.isFlag = isFlag;
		}
	}

	private ArrayList<DamageCaeck> mDamageCaeckList = new ArrayList<DamageCaeck>();

	public class NameOnClickListener implements OnClickListener {

		private Dictitemlist mDictitemlist;

		private TextView tvName;

		public NameOnClickListener(Dictitemlist mDictitemlist, TextView tvName) {
			this.mDictitemlist = mDictitemlist;
			this.tvName = tvName;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			boolean isFlag = true;
			for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
				if (mDamageCaeck.mDictitemlist.getName().equals(
						mDictitemlist.getName())) {
					mDamageCaeckList.remove(mDamageCaeck);
					tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
					isFlag = false;
					break;
				}
			}
			if (mDamageCaeckList.size() < 4) {
				if (isFlag) {
					tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_down);
					mDamageCaeckList
							.add(new DamageCaeck(mDictitemlist, isFlag));
				}
			} else {
				Toast.makeText(context, "请选择关注的分类项，最多可选4项", Toast.LENGTH_SHORT)
						.show();
			}
			ArrayList<Dictitemlist> mArrayList = new ArrayList<Dictitemlist>();
			for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
				mArrayList.add(mDamageCaeck.mDictitemlist);
			}
			mCallBackDamageTypeGridListener.callBack(mArrayList);
		}
	}
}
