package widget;

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

import com.example.basewidgetlibrary.R;
import com.pinyin4android.PinyinUtil;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interface.DialogCancelListener;
import Interface.DialogClearListener;
import Interface.DialogConfirmListener;
import Interface.DialogSelectAllUserListener;
import htmitech.com.componentlibrary.entity.AuthorInfo;

public class MyNextPersonDialog extends BaseDialog implements OnClickListener {

	private DialogConfirmListener onConfirmListener;

	private DialogCancelListener onCancelListener;

	private DialogClearListener onClearListener;

	private TextView title;

	private Button btn_confirm, btn_cancel, btn_clear;

	private ListView listview;

	private MyBaseAdapter adapter;

	private EditText searchEt;

	private String titleStr, confirmBtnStr, cancelBtnStr;
	
	private boolean IsMultiSelectUser = false;

	@SuppressWarnings("unused")
	private int titleStrId = -1, contentStrId = -1, confirmBtnStrId = -1,
			cancelBtnStrId = -1;

	@SuppressWarnings("unused")
	private int[] selectIndexArr;

	private Vector<AuthorInfo> vector_userInfo = new Vector<AuthorInfo>();
	private Vector<AuthorInfo> temp = new Vector<AuthorInfo>();
	public AuthorInfo[] temparr = null;

	@SuppressWarnings("unused")
	private SpannableStringBuilder contentStyle;

	/*********************************************************************/
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.nextpersondialog_btn_confirm) {
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(this);
			}

		} else if (i == R.id.nextpersondialog_btn_cancel) {
			if (onCancelListener != null) {
				onCancelListener.onCancel(this);
			}

		} else if (i == R.id.nextpersondialog_btn_clear) {
			if (onClearListener != null) {
				onClearListener.onClear();
				return;
			}

		} else if (i == R.id.nextpersondialog_btn_selectfromallusers) {
			if (onSelectAllUserListener != null) {
				onSelectAllUserListener.onSelectAllUser();
				return;
			}
		}
		dismiss();
	}

	/*********************************************************************/
	public void setViewValue(String titlestr, AuthorInfo[] authorInfoArr, boolean isMultiSelectUser,boolean enabledAllUserSelect) {
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)
				title.setText(titleStr);
		}
		if(null != authorInfoArr){
			for (AuthorInfo info : authorInfoArr) {
				if (!vector_userInfo.contains(info)) {
					vector_userInfo.addElement(info);
				}
			}
		}


		IsMultiSelectUser = isMultiSelectUser;


		if (this.listview != null) {
			adapter = new MyBaseAdapter(getContext(), vector_userInfo,IsMultiSelectUser);
			listview.setAdapter(adapter);
		}
		temparr = new AuthorInfo[vector_userInfo.size()];
		vector_userInfo.toArray(temparr);
		

		
		findViewById(R.id.line_SelectfromAllusers).setVisibility(
					enabledAllUserSelect ? View.VISIBLE : View.GONE);
			
	}
	
/*	public void addViewValue(AuthorInfo[] authorInfoArr) {
		
		for (AuthorInfo info : authorInfoArr) {
			vector_userInfo.addElement(info);
		}

		if (this.listview != null) {
			adapter = new MyBaseAdapter(getContext(), vector_userInfo);
			listview.setAdapter(adapter);
		}
		
		temparr = new AuthorInfo[vector_userInfo.size()];
		vector_userInfo.toArray(temparr);
	}*/
	

	private TextWatcher watcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			temp.clear();
			boolean flag = false;
			for (int i = 0; i < vector_userInfo.size(); i++) { 					//联系人信息集合遍历
				String userinfo = vector_userInfo.elementAt(i).getUserName();  	//拿到第一个人的名字
				String userinfo_pinyin = PinyinUtil.toPinyin(					//把联系人的名字转成拼音形式
						MyNextPersonDialog.this.getContext(), userinfo);
				/**
				 * 2015-8-3 
				 * 修改乱码导致的对话框异常退出
				 */
				Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
				Matcher m = p_str.matcher(userinfo);
				if (m.find() && m.group(0).equals(userinfo)) {
				} else {
					continue;
				}
				
				PinyinUtil.toPinyin(
						MyNextPersonDialog.this.getContext(),				
						userinfo.charAt(1)).substring(0, 1);
				
				StringBuffer sb = new StringBuffer();							//创建一个容器
				for (int j = 0; j < userinfo.length(); j++) {					//获取联系人姓名的长度					
					sb.append(PinyinUtil.toPinyin(								
							MyNextPersonDialog.this.getContext(),				
							userinfo.charAt(j)).substring(0, 1));				//返回这个列表的
				}
				userinfo_pinyin = userinfo_pinyin.replace(" ", "");
				String input = searchEt.getText().toString();
				if (sb.toString().startsWith(input)
						|| sb.toString().endsWith(input) //1
						|| userinfo.contains(input)			//2
						|| userinfo_pinyin.contains(input)	//3
						|| sb.toString().equalsIgnoreCase(input)
						|| sb.toString().contains(input)) {
					temp.addElement(vector_userInfo.elementAt(i));
				}
			}
			temparr = new AuthorInfo[temp.size()];
			for (int i = 0; i < temp.size(); i++) {
				temparr[i] = temp.elementAt(i);
			}
			adapter.setData(temp,IsMultiSelectUser);
		}
	};

	/***
	 * 得到被选中的item索引
	 * 
	 * @return
	 */
	public Integer[] getSelectIndexArr() {
		MyBaseAdapter abs = (MyBaseAdapter) listview.getAdapter();
		return abs.getCheckValues();
	}
	
	public AuthorInfo[] getNewVector() {
		if (temparr != null && temparr.length > 0) {
			return temparr;
		}
		return null;
	}
	private DialogSelectAllUserListener onSelectAllUserListener;
	public void setSeletAllUserView(boolean enabledAllUserSelect) {
		findViewById(R.id.line_SelectfromAllusers).setVisibility(
				enabledAllUserSelect ? View.VISIBLE : View.GONE);
	}
	public void setOnSelectAllUserListener(
			DialogSelectAllUserListener onSelectAllUserListener) {
		this.onSelectAllUserListener = onSelectAllUserListener;
	}
	public String getSelectPersonId() {
		StringBuffer stringBuffer = new StringBuffer();
		Integer[] indexArr = adapter.getCheckValues();
		for (int i = 0; i < indexArr.length; i++) {
			int index = indexArr[i];
			AuthorInfo ui = vector_userInfo.elementAt(index);
			if (i == 0) {
				stringBuffer.append(ui.getUserId());
			} else {
				stringBuffer.append("|" + ui.getUserId());
			}
		}

		return stringBuffer.toString();
	}

	// 清空选择
	public void clearSelect() {
		adapter.setData(vector_userInfo,IsMultiSelectUser);
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
		findViewById(R.id.nextpersondialog_btn_selectfromallusers)
		.setOnClickListener(this);
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

	public MyNextPersonDialog(Context context) {
		super(context);
		init();
	}

	public MyNextPersonDialog(Context context,
                              DialogConfirmListener onConfirmListener) {
		super(context);
		init();
		this.onConfirmListener = onConfirmListener;
	}

	public MyNextPersonDialog(Context context,
                              DialogConfirmListener onConfirmListener,
                              DialogCancelListener onCancelListener) {
		super(context);
		init();
		this.onConfirmListener = onConfirmListener;
		this.onCancelListener = onCancelListener;
	}

	public MyNextPersonDialog(Context context,
                              DialogConfirmListener onConfirmListener,
                              DialogCancelListener onCancelListener,
                              DialogClearListener onClearListener, int theme) {
		super(context, theme);
		init();
		this.onConfirmListener = onConfirmListener;
		this.onCancelListener = onCancelListener;
		this.onClearListener = onClearListener;
	}
	boolean selectallusers = false;
	Vector<String> ids = null;
	public void setViewAllUserValue(String titlestr, AuthorInfo[] authorInfoArr, boolean isMultiSelectUser) {
		IsMultiSelectUser = isMultiSelectUser;
		selectallusers = true;
		ids = new Vector<String>();
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)
				title.setText(titleStr);
		}
		if (authorInfoArr != null)
			for (AuthorInfo info : authorInfoArr) {
				if (!vector_userInfo.contains(info)) {
					vector_userInfo.addElement(info);
				}
			}

		if (this.listview != null) {
			adapter = new MyBaseAdapter(getContext(), vector_userInfo, IsMultiSelectUser);
			listview.setAdapter(adapter);
		}
		temparr = new AuthorInfo[vector_userInfo.size()];
		vector_userInfo.toArray(temparr);
	}

}
