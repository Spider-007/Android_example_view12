package com.htmitech.ztcustom.zt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.ztcustom.zt.util.DensityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 损伤类型选择Fragment
 * 
 * @author Tony
 * 
 */
public class DamageTypeFragment extends BaseFragment implements OnClickListener {

	private CallBackDamageTypeGridListener mCallBackDamageTypeGridListener;
	private LinearLayout layout_type;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zt_fragment_damage_type, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);
		try {
			mCallBackDamageTypeGridListener = (CallBackDamageTypeGridListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName()
					+ " must implements interface MyListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		layout_type = (LinearLayout) getView().findViewById(R.id.layout_type);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		ArrayList<Dictitemlist> mDictitemlistList = (ArrayList<Dictitemlist>) mBundle
				.getSerializable("dictitemlist");
		WindowManager wm = getActivity().getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int num = 0;
		int textHight = 70;
		if(width == 1080){
			textHight = 100;
		}else{
			textHight = DensityUtil.dip2px(getActivity(), 30);
		}
		
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, textHight);
		layoutParams.setMargins(0, 10, 20, 10);
		layout.setLayoutParams(layoutParams);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		/*
		 * 排序
		 */
		Comparator comp = new Comparator() {
			public int compare(Object o1, Object o2) {
				Dictitemlist p1 = (Dictitemlist) o1;
				Dictitemlist p2 = (Dictitemlist) o2;
				if (p1.name.length() < p2.name.length())
					return -1;
				else if (p1.name.length() == p2.name.length())
					return 0;
				else if (p1.name.length() > p2.name.length())
					return 1;
				return 0;
			}
		};
		Collections.sort(mDictitemlistList, comp);
		/*
		 * 四个字 每个TextView的宽度
		 */
		int colum4 = (width - 160) / 4;
		/*
		 *两个字 每个TextView的宽度 
		 */
		int colum2 = (width - 80) / 2;
		/*
		 *一个字 每个TextView的宽度 
		 */
		int colum1 = (width - 40) / 1;
		
		

		int indexColum4 = 0, indexColum2 = 0, indexColum1 = 0;
		for (Dictitemlist dic : mDictitemlistList) {
			
			num = (dic.name.length() * 34); //34是根据 字的大小和padding的左右宽度而来的 14 +
			if (num < colum4) {
				if (indexColum4 == 0) {
					layout = new LinearLayout(getActivity());
					layout.setLayoutParams(layoutParams);
				}
				TextView textView = new TextView(getActivity());
				LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
						colum4, textHight, 1);
				textparams.setMargins(20, 0, 0, 0);
				textView.setText(dic.name);
				textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
				textView.setLayoutParams(textparams);
				textView.setTextSize(14);
				textView.setGravity(Gravity.CENTER);
				textView.setPadding(10, 10, 10, 10);
				textView.setOnClickListener(new NameOnClickListener(dic,textView));
				layout.addView(textView);
				indexColum4++;
				if (indexColum4 == 4) {
					indexColum4 = 0;
					layout_type.addView(layout);
				}

			} else if (num < colum2) {
				/*
				 * 把上一个最后一个没有Add进去的给add进去
				 */
				if(indexColum4 != 0){
					indexColum4 = 0;
					layout_type.addView(layout);
				}
				if (indexColum2 == 0) {
					layout = new LinearLayout(getActivity());
					layout.setLayoutParams(layoutParams);
					layout.removeAllViews();
				}
				TextView textView = new TextView(getActivity());
				LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
						colum2, textHight, 1);
				textparams.setMargins(20, 0, 0, 0);
				textView.setText(dic.name);
				textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
				textView.setLayoutParams(textparams);
				textView.setTextSize(14);
				textView.setGravity(Gravity.CENTER);
				textView.setPadding(10, 10, 10, 10);
				textView.setOnClickListener(new NameOnClickListener(dic,textView));
				layout.addView(textView);
				indexColum2++;
				if (indexColum2 == 2) {
					indexColum2 = 0;
					layout_type.addView(layout);
				}
			} else {
				/*
				 * 把上一个最后一个没有Add进去的给add进去
				 */
				if(indexColum2 != 0){
					indexColum2 = 0;
					layout_type.addView(layout);
				}
				layout = new LinearLayout(getActivity());
				layout.setLayoutParams(layoutParams);
				layout.removeAllViews();
				TextView textView = new TextView(getActivity());
				LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
						colum1, textHight, 1);
				textparams.setMargins(20, 0, 0, 0);
				textView.setText(dic.name);
				textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
				textView.setLayoutParams(textparams);
				textView.setTextSize(14);
				textView.setGravity(Gravity.CENTER);
				textView.setPadding(10, 10, 10, 10);
				textView.setOnClickListener(new NameOnClickListener(dic,textView));
				layout.addView(textView);
				layout_type.addView(layout);
			}
		}
		if(indexColum4 != 0 || indexColum2 != 0){
			layout_type.addView(layout);
		}
		layout = new LinearLayout(getActivity());
		layoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 20);
		layout.setLayoutParams(layoutParams);
		layout_type.addView(layout);
	}
	/**
	 * 每个TextView的点击事件
	 * 
	 */
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
				Toast.makeText(getActivity(), "请选择关注的分类项，最多可选4项", Toast.LENGTH_SHORT)
						.show();
			}
			ArrayList<Dictitemlist> mArrayList = new ArrayList<Dictitemlist>();
			for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
				mArrayList.add(mDamageCaeck.mDictitemlist);
			}
			mCallBackDamageTypeGridListener.callBack(mArrayList);
		}
	}
	private ArrayList<DamageCaeck> mDamageCaeckList = new ArrayList<DamageCaeck>();
	private class DamageCaeck {
		Dictitemlist mDictitemlist;
		boolean isFlag;

		public DamageCaeck(Dictitemlist mDictitemlist, boolean isFlag) {
			this.mDictitemlist = mDictitemlist;
			this.isFlag = isFlag;
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			
		}
	}

}
