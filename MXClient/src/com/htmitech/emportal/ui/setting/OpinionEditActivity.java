package com.htmitech.emportal.ui.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.common.PreferenceKeys;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;

/** 意见输入界面 */
public class OpinionEditActivity extends SlidingBackAcitivity implements
		OnClickListener, IBaseCallback {
	private TextView mTextViewOk;
	private ArrayList<String> mItems = new ArrayList<String>();
	private ListView mListView;
	private OpinionAdapter mAdapter;
	private Button mBtn_add;
	private DialogFragment mNewFragment;

	protected int getLayoutById() {
		return R.layout.activity_optioninput_edit;
	}

	/**
	 * 初始化UI
	 */
	protected void initView() {
		findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
		mTextViewOk = (TextView) findViewById(R.id.textview_titlebar_more);
		mTextViewOk.setText("保存");
		mTextViewOk.setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.textview_titlebar_title)).setText("审批意见");
		mTextViewOk.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.listview_optioninput);
		String userOpinion = AppPreferenceHelper.getInstance(this).getString(
				PreferenceKeys.KEY_OPINION_SAVE, "同意|拟同意");
		String[] mItemArray = org.apache.commons.lang3.StringUtils.split(
				userOpinion, "|");
		for (int i = 0; i < mItemArray.length; i++) {
			mItems.add(mItemArray[i]);
		}

		mAdapter = new OpinionAdapter();
		mListView.setAdapter(mAdapter);

		mBtn_add = (Button) findViewById(R.id.btn_optioninput_add);
		mBtn_add.setOnClickListener(this);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		mListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						toDeleteMsg(position);
						return false;
					}
				});

	}

	public void toDeleteMsg(final int entityIndex) {
		mAdapter.setDeleteIndex(entityIndex);
		mNewFragment = MyAlertDialogFragment.newInstance("确实要删除此条记录吗?",
				R.drawable.prompt_warn, cancelListener, confirmListener,true);
		mNewFragment.show(getSupportFragmentManager(), "dialog");
	}

	private DialogCancelListener cancelListener = new DialogCancelListener() {
		@Override
		public void onCancel(BaseDialog dialog) {
			// TODO Auto-generated method stub
			mNewFragment.dismiss();
		}
	};

	private DialogConfirmListener confirmListener = new DialogConfirmListener() {
		public void onConfirm(BaseDialog  dialog) {
			mNewFragment.dismiss();
			int index = mAdapter.getDeleteIndex();
			mItems.remove(index);
			mAdapter.notifyDataSetChanged();
		}
	};

	class OpinionAdapter extends BaseAdapter {

		int removeEntityIndex;
		boolean isHaveAdd = false;

		public boolean isHaveAdd() {
			return isHaveAdd;
		}

		public void setHaveAdd(boolean isHaveAdd) {
			this.isHaveAdd = isHaveAdd;
		}

		public void setDeleteIndex(int index) {
			removeEntityIndex = index;
		}

		public int getDeleteIndex() {
			return removeEntityIndex;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = getLayoutInflater().inflate(
					R.layout.listitem_opinion, null);
			EditText title = (EditText) convertView
					.findViewById(R.id.edittext_opinion_title);
			title.setText(mItems.get(position));
			if (isHaveAdd && position == mItems.size() - 1) {
				title.requestFocus();
				isHaveAdd = false;
				
				title.postDelayed(new Runnable() {
		            @Override
		            public void run() {
		                    InputMethodManager imm =
		                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		                    // imm.showSoftInput(mEditText, 0);
		            }
		        }, 50);
			}
			return convertView;
		}
	}

	@Override
	public void onSuccess(int requestTypeId, Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgview_titlebar_back:
			finish();
			break;
		case R.id.textview_titlebar_more:
			StringBuffer inputString = new StringBuffer();
			for (int i = 0; i < mListView.getChildCount(); i++) {
				LinearLayout layout = (LinearLayout) mListView.getChildAt(i);// 获得子item的layout
				EditText et = (EditText) layout
						.findViewById(R.id.edittext_opinion_title);// 从layout中获得控件,根据其id
				inputString.append(et.getText().toString() + "|");
			}
			AppPreferenceHelper.getInstance(this).putString(
					PreferenceKeys.KEY_OPINION_SAVE, inputString.toString());
			Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.btn_optioninput_add:
			mItems.add("");
			mAdapter.setHaveAdd(true);
			mAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

}
