package com.htmitech.unit;

import android.app.Activity;
import android.content.Intent;


import com.htmitech.addressbook.R;

import java.io.Serializable;
import java.util.Map;

/**
 * Activity跳转统一
 * @author htrf-pc
 *
 */
public class ActivityUnit {
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

	public static void switchTo(Activity activity,
								Class<? extends Activity> target,int resultCode, Map<String, Object> params) {
		Intent intent = new Intent(activity, target);
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				setValueToIntent(intent, entry.getKey(), entry.getValue());
			}
		}
		switchTo(activity, intent,resultCode);
	}

	public static void switchTo(Activity activity,Intent intent,int resultCode){

		activity.startActivityForResult(intent, resultCode);
		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}


	public static void switchTo(Activity activity, Intent intent) {
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}
	public static void finish(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_out);
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
