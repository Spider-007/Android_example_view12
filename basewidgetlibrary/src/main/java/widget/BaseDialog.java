package widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class BaseDialog extends Dialog {

	public BaseDialog(Context context) {
		super(context);
	}
	
	public BaseDialog(Context context , int theme) {
		super(context,theme);
	}
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_SEARCH:
		case KeyEvent.KEYCODE_CALL:
		case KeyEvent.KEYCODE_STAR:
		case KeyEvent.KEYCODE_CAMERA:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
