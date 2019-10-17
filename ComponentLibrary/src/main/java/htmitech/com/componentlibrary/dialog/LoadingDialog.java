package htmitech.com.componentlibrary.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import htmitech.com.componentlibrary.R;


/**
 * 加载中Dialog
 * 
 * @author lexyhp
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;
	private int layoutResId;
	private String message = null;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文
	 *            要传入的dialog布局文件的id
	 */
	public LoadingDialog(Context context) {
		super(context);
		this.layoutResId = R.layout.view_tips_loading;
		message = context.getResources().getString(R.string.loading);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutResId);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_msg.setText(this.message);
	}

	public void setValue(String value){
		if(tips_loading_msg != null)
			tips_loading_msg.setText(value);
		this.message = value;
	}

}
