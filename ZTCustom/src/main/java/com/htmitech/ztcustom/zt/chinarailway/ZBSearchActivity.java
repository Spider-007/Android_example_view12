package com.htmitech.ztcustom.zt.chinarailway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.ZBSearchAutoAdapter;
import com.htmitech.ztcustom.zt.domain.ZBSearchAutoData;

import java.util.ArrayList;
import java.util.Arrays;

public class ZBSearchActivity extends Activity implements OnClickListener {

	public static final String SEARCH_HISTORY = "search_history";
	private ListView mAutoListView;
	private TextView mSearchButtoon;
	private TextView mAutoEdit;
	private ZBSearchAutoAdapter mSearchAutoAdapter;
	private Button mCancel;
	private LinearLayout layout_clean_all;
	private TextView tv_clean_all;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zb_activity_tf_search);
		init();
		
	}

	private void init() {
		mSearchAutoAdapter = new ZBSearchAutoAdapter(this, -1, this);
		mAutoListView = (ListView) findViewById(R.id.auto_listview);
		layout_clean_all = (LinearLayout) findViewById(R.id.layout_clean_all);
		tv_clean_all = (TextView) findViewById(R.id.tv_clean_all);
		mAutoListView.setAdapter(mSearchAutoAdapter);
		mAutoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				ZBSearchAutoData data = (ZBSearchAutoData) mSearchAutoAdapter.getItem(position);
				mAutoEdit.setText(data.getContent());
				Intent intent = new Intent();
				intent.putExtra("keyword",  data.getContent()); 
			    setResult(1, intent);
				finish();
			}
		});
		tv_clean_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp = ZBSearchActivity.this.getSharedPreferences(
						ZBSearchActivity.SEARCH_HISTORY, 0);
				SharedPreferences.Editor edit = sp.edit();
				edit.clear();
				edit.commit();
				mSearchAutoAdapter.notifyData();
			}
		});
		mSearchButtoon = (TextView) findViewById(R.id.search_button);
		mSearchButtoon.setOnClickListener(this);
		mAutoEdit = (TextView) findViewById(R.id.auto_edit);
		mAutoEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(s.toString().length() > 0){
					layout_clean_all.setVisibility(View.GONE);
				}else{
					layout_clean_all.setVisibility(View.VISIBLE);
				}
				mSearchAutoAdapter.performFiltering(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		
		mCancel = (Button) findViewById(R.id.cancel_button);
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.search_button) {//搜索按钮
			saveSearchHistory();
			mSearchAutoAdapter.initSearchHistory();
		} else {
			ZBSearchAutoData data = (ZBSearchAutoData) v.getTag();
			mAutoEdit.setText(data.getContent());
		}
		
		Intent intent = new Intent();
		intent.putExtra("keyword",  mAutoEdit.getText().toString().trim()); 
	    setResult(1, intent);
		finish();
	}

	/*
	 * 保存搜索记录
	 */
	private void saveSearchHistory() {
		String text = mAutoEdit.getText().toString().trim();
		if (text.length() < 1) {
			return;
		}
		SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
		String longhistory = sp.getString(SEARCH_HISTORY, "");
		String[] tmpHistory = longhistory.split(",");
		ArrayList<String> history = new ArrayList<String>(
				Arrays.asList(tmpHistory));
		if (history.size() > 0) {
			int i;
			for (i = 0; i < history.size(); i++) {
				if (text.equals(history.get(i))) {
					history.remove(i);
					break;
				}
			}
			history.add(0, text);
		}
		if (history.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < history.size(); i++) {
				sb.append(history.get(i) + ",");
			}
			sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
		} else {
			sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
		}
	}

}
