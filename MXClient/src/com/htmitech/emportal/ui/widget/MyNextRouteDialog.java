package com.htmitech.emportal.ui.widget;

import java.util.Arrays;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.RouteInfo;
import com.pinyin4android.PinyinUtil;

public class MyNextRouteDialog extends BaseDialog implements OnClickListener {

	private DialogConfirmListener onConfirmListener;

	private DialogCancelListener onCancelListener;

	private DialogClearListener onClearListener;

	private TextView title;

	private Button btn_confirm, btn_cancel,btn_clear;

	private ListView listview;

	private MyRouteBaseAdapter adapter;

	private EditText searchEt;

	private String titleStr, confirmBtnStr, cancelBtnStr;

	@SuppressWarnings("unused")
	private int titleStrId = -1, contentStrId = -1, confirmBtnStrId = -1,
			cancelBtnStrId = -1;

	@SuppressWarnings("unused")
	private int[] selectIndexArr;

	private Vector<RouteInfo> vector_routeInfo = new Vector<RouteInfo>();
	private Vector<RouteInfo> temp = new Vector<RouteInfo>();

	@SuppressWarnings("unused")
	private SpannableStringBuilder contentStyle;

	/*********************************************************************/
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextpersondialog_btn_confirm:
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(this);
			}
			break;

		case R.id.nextpersondialog_btn_cancel:
			if (onCancelListener != null) {
				onCancelListener.onCancel(this);
			}
			break;
		case R.id.nextpersondialog_btn_clear:
			if (onClearListener != null) {
				onClearListener.onClear();
				return;
			}
			break;
		}
		dismiss();
	}

	/*********************************************************************/
	public void setViewValue(String titlestr, RouteInfo[] routeInfoArr) {
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)
				title.setText(titleStr);
		}
		for (RouteInfo info : routeInfoArr) {
			vector_routeInfo.addElement(info);
		}

		if (this.listview != null) {
			adapter = new MyRouteBaseAdapter(getContext(), vector_routeInfo);
			listview.setAdapter(adapter);
		}
	}

	private TextWatcher watcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			temp.clear();
			for (int i = 0; i < vector_routeInfo.size(); i++) {
				String routeinfo = vector_routeInfo.elementAt(i).getRouteName();
				String userinfo_pinyin = PinyinUtil.toPinyin(
						MyNextRouteDialog.this.getContext(), routeinfo);
				if (routeinfo.contains(searchEt.getText())
						|| userinfo_pinyin.contains(searchEt.getText())) {
					temp.addElement(vector_routeInfo.elementAt(i));
				}
			}
			RouteInfo[] temparr = new RouteInfo[temp.size()];
			for (int i = 0; i < temp.size(); i++) {
				temparr[i] = temp.elementAt(i);
			}
			adapter.setData(temp);
		}
	};

	/***
	 * 得到被选中的item索引
	 * 
	 * @return
	 */
	public Integer[] getSelectIndexArr() {
		MyRouteBaseAdapter abs = (MyRouteBaseAdapter) listview.getAdapter();
		System.out.println("abs.getCheckValues():"+Arrays.toString(abs.getCheckValues()));
		return abs.getCheckValues();
	}

	public String getSelectRouteId() {
		StringBuffer stringBuffer = new StringBuffer();
		Integer[] indexArr = adapter.getCheckValues();
		for (int i = 0; i < indexArr.length; i++) {
			int index = indexArr[i];
			RouteInfo ui = vector_routeInfo.elementAt(index);
			if (i == 0) {
				stringBuffer.append(ui.getRouteID());
			} else {
				stringBuffer.append("|" + ui.getRouteID());
			}
		}

		return stringBuffer.toString();
	}

	// 清空选择
	public void clearSelect() {
		adapter.setData(vector_routeInfo);
	}

	public void setViewText(int titleStrId, int contentStrId) {
		this.titleStrId = titleStrId;
		this.contentStrId = contentStrId;
	}

	public void setViewText(int titleStrId, SpannableStringBuilder contentStyle) {
		this.titleStrId = titleStrId;
		this.contentStyle = contentStyle;
	}

	public void setButtonText(String confirmBtnStr, String cancelBtnStr) {
		if (confirmBtnStr != null) {
			this.confirmBtnStr = confirmBtnStr;
		}
		if (cancelBtnStr != null) {
			this.cancelBtnStr = cancelBtnStr;
		}
	}

	public void setButtonText(int confirmBtnStrId, int cancelBtnStrId) {
		this.confirmBtnStrId = confirmBtnStrId;
		this.cancelBtnStrId = cancelBtnStrId;
	}

	public void init() {
		titleStr = null;
		confirmBtnStr = null;
		cancelBtnStr = null;

		titleStrId = -1;
		contentStrId = -1;
		confirmBtnStrId = -1;
		cancelBtnStrId = -1;
	}

	/*********************************************************************/
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nextperson_dialog);
		initView();
		setData();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.nextcodedialog_title);
		listview = (ListView) findViewById(R.id.listview_person);
		searchEt = (EditText) findViewById(R.id.searchEt);
		btn_confirm = (Button) findViewById(R.id.nextpersondialog_btn_confirm);
		btn_cancel = (Button) findViewById(R.id.nextpersondialog_btn_cancel);
		btn_clear = (Button) findViewById(R.id.nextpersondialog_btn_clear);
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_confirm.setText(R.string.confirm);
		btn_cancel.setText(R.string.cancel);
		btn_clear.setText(R.string.clear);
	}

	private void setData() {
		if (titleStr != null) {
			title.setText(titleStr);
		} else if (titleStrId != -1) {
			title.setText(titleStrId);
		}

		if (searchEt != null) {
			searchEt.addTextChangedListener(watcher);
		}

		if (confirmBtnStr != null) {
			btn_confirm.setText(confirmBtnStr);
		} else if (confirmBtnStrId != -1) {
			btn_confirm.setText(confirmBtnStrId);
		}
		if (cancelBtnStr != null) {
			btn_cancel.setText(cancelBtnStr);
		} else if (cancelBtnStrId != -1) {
			btn_cancel.setText(cancelBtnStrId);
		}

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.setListItemCheck(arg2);
			}
		});

	}

	public MyNextRouteDialog(Context context) {
		super(context);
		init();
	}

	public MyNextRouteDialog(Context context,
			DialogConfirmListener onConfirmListener) {
		super(context);
		init();
		this.onConfirmListener = onConfirmListener;
	}

	public MyNextRouteDialog(Context context,
			DialogConfirmListener onConfirmListener,
			DialogCancelListener onCancelListener) {
		super(context);
		init();
		this.onConfirmListener = onConfirmListener;
		this.onCancelListener = onCancelListener;
	}

	public MyNextRouteDialog(Context context,
			DialogConfirmListener onConfirmListener,
			DialogCancelListener onCancelListener,
			DialogClearListener onClearListener, int theme) {
		super(context, theme);
		init();
		this.onConfirmListener = onConfirmListener;
		this.onCancelListener = onCancelListener;
		this.onClearListener = onClearListener;
	}

}
