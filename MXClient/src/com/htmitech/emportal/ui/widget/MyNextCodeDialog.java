package com.htmitech.emportal.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.htmitech.emportal.R;

import java.util.ArrayList;

public class MyNextCodeDialog extends BaseDialog implements OnClickListener {

	private DialogConfirmListener onConfirmListener;

	private DialogCancelListener onCancelListener;

	private TextView title;

	private RadioGroup  radioGroup ;
	private LinearLayout  check_layout ;

	private Button btn_confirm, btn_cancel;

	private String titleStr, confirmBtnStr, cancelBtnStr;

	@SuppressWarnings("unused")
	private int titleStrId = -1, contentStrId = -1, confirmBtnStrId = -1,
			cancelBtnStrId = -1;
	
	private int selectIndex = -1 ;
	private long selectChildId = -1L ;
	
	@SuppressWarnings("unused")
	private SpannableStringBuilder contentStyle;

	/*********************************************************************/
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextcodedialog_btn_confirm:
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(this);
			}
			break;

		case R.id.nextcodedialog_btn_cancel:
			if (onCancelListener != null) {
				onCancelListener.onCancel(this);
			}
			break;
		}
		dismiss();
	}

	/*********************************************************************/
	public void setViewValue(String titlestr, View  view) {
		check_layout.setVisibility(View.GONE);
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)  
				title.setText(titleStr);
		}
		if (this.radioGroup != null) {
			this.radioGroup.addView(view);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					selectChildId = checkedId ;
				}
			}); 
		}
	}
	//路由多选
	public void setViewValueCheck(String titlestr, View  view) {
		radioGroup.setVisibility(View.GONE);
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)
				title.setText(titleStr);
		}
		if (this.check_layout != null) {
			this.check_layout.addView(view);
		}
	}
	
	/***
	 * 得到被选中的item索引
	 * @return
	 */
	public int getSelectIndex(){
		if(selectChildId <=  -1 ) return -1 ;
		
		int sum = radioGroup.getChildCount();
		for(int i =0 ; i <sum ; i++){
			int id = radioGroup.getChildAt(i).getId();
			if(selectChildId == id){
				selectIndex = i ;
			}
		}
		return  selectIndex ;
	}

	public Integer[] getSelectIndexs(){

		Integer[] mreturnValeu = null;
		int sum = check_layout.getChildCount();
		ArrayList<Integer> mlist = new ArrayList<Integer>();
		for(int i =0 ; i <sum ; i++){
			CheckBox checkBox= (CheckBox) check_layout.getChildAt(i);
			if(checkBox.isChecked()){
				mlist.add(i);
			}
		}
		mreturnValeu = new Integer[mlist.size()];
		mlist.toArray(mreturnValeu);

		return  mreturnValeu ;
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
		setContentView(R.layout.nextcode_dialog);
		initView();
		setData();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.nextcodedialog_title);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup_nextcode);
		check_layout = (LinearLayout) findViewById(R.id.check_layout);
		btn_confirm = (Button) findViewById(R.id.nextcodedialog_btn_confirm);
		btn_cancel = (Button) findViewById(R.id.nextcodedialog_btn_cancel);
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_confirm.setText(R.string.confirm);
		btn_cancel.setText(R.string.cancel);
	}
	
	private void setData() {
		if (titleStr != null) {
			title.setText(titleStr);
		} else if (titleStrId != -1) {
			title.setText(titleStrId);
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
	}

	public MyNextCodeDialog(Context context) {
		super(context);
		init();
	}

	public MyNextCodeDialog(Context context,
			DialogConfirmListener onConfirmListener) {
		super(context);
		init();
		this.onConfirmListener = onConfirmListener;
	}

	public MyNextCodeDialog(Context context,
			DialogConfirmListener onConfirmListener,
			DialogCancelListener onCancelListener , int theme) {
		super(context,theme);
		init();
		this.onConfirmListener = onConfirmListener;
		this.onCancelListener = onCancelListener;
	}

}
