package htmitech.com.componentlibrary.unit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import java.io.Serializable;
import java.util.Map;

import htmitech.com.componentlibrary.R;


/**
 * Activity跳转统一
 * @author htrf-pc
 *
 */
public class HTActivityUnit {
	public static void switchTo(Activity activity,
			Class<? extends Activity> target, Map<String, Object> params) {
		Intent intent = new Intent(activity, target);
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				setValueToIntent(intent, entry.getKey(), entry.getValue());
			}
		}
		switchTo(activity, intent);
	}
	//用于隐式跳转
	public static void switchTo(Activity activity,String target, Map<String, Object> params) throws Exception {
		Intent intent = new Intent();
		if(TextUtils.isEmpty(target)){
			return;
		}
		intent.setClassName(activity,target);
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				setValueToIntent(intent, entry.getKey(), entry.getValue());
			}
		}
		try{
			switchTo(activity, intent);
		}catch (Exception e){
			ToastUtil.showShort(activity,"没有发现对应的类");
//			throw new NotFindException("没有发现对应的类");
		}

	}

	public static Intent getSwitchActivity(Context activity,
										   Class<? extends Activity> target, Map<String, Object> params){
		Intent intent = new Intent(activity, target);
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				setValueToIntent(intent, entry.getKey(), entry.getValue());
			}
		}
		return intent;
	}
	public static void switchTo(Activity activity,
								Class<? extends Activity> target, Map<String, Object> params,int recode) {
		Intent intent = new Intent(activity, target);
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				setValueToIntent(intent, entry.getKey(), entry.getValue());
			}
		}
		switchTo(activity, intent,recode);
	}

	public static void switchTo(Activity activity, Intent intent) {
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}
	public static void switchTo(Activity activity, Intent intent,int recorde ) {
		activity.startActivityForResult(intent,recorde);
		activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	public static void setValueToIntent(Intent intent, String key, Object val) {
		if (val instanceof Boolean)
			intent.putExtra(key, (Boolean) val);
		else if (val instanceof Boolean[])
			intent.putExtra(key, (Boolean[]) val);
		else if (val instanceof String)
			intent.putExtra(key, (String) val);
		else if (val instanceof String[])
			intent.putExtra(key, (String[]) val);
		else if (val instanceof Integer)
			intent.putExtra(key, (Integer) val);
		else if (val instanceof Integer[])
			intent.putExtra(key, (Integer[]) val);
		else if (val instanceof Long)
			intent.putExtra(key, (Long) val);
		else if (val instanceof Long[])
			intent.putExtra(key, (Long[]) val);
		else if (val instanceof Double)
			intent.putExtra(key, (Double) val);
		else if (val instanceof Double[])
			intent.putExtra(key, (Double[]) val);
		else if (val instanceof Float)
			intent.putExtra(key, (Float) val);
		else if (val instanceof Float[])
			intent.putExtra(key, (Float[]) val);
		else if(val instanceof Serializable)
			intent.putExtra(key, (Serializable) val);
			
	}
}
