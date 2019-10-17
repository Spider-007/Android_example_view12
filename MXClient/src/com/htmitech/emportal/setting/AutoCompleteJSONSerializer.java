package com.htmitech.emportal.setting;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class AutoCompleteJSONSerializer {
	
	private static final String TAG = "AutoComplete";
	private static final String FILENAME  = "AutoComplete.json";
	
	public static final String JSON_LOGIN = "login";
	public static final String JSON_DEBUG = "debug";
	public static final String JSON_PUSH = "push";
	public static final String JSON_IM = "im";
	public static final String JSON_API = "api";
	public static final String JSON_CODE = "software_code";
	public static final String JSON_DZ = "dz_ip";

	private Context mContext;
	private String mFilename;
	
	AutoCompleteJSONSerializer(Context context) {
		this.mContext = context;
		mFilename = FILENAME;
	}
	
	public ArrayList<String> getAnyOne(String flag) {
		ArrayList<String> object = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}

			if (!jsonString.toString().equals("") && jsonString.length() < 1) {
				return null;
			}
			
			JSONObject json = new JSONObject(jsonString.toString());
			JSONArray jarr = json.optJSONArray(flag);
			if(jarr != null)
			{
				for (int i = 0; i < jarr.length(); i++) {
					object.add((String)jarr.get(i));
				}
			}

			
		} catch (IOException e) {
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

	
	public void saveAllToFile(ArrayList<String> loginTextView, ArrayList<String> debugTextView,
			ArrayList<String> pushTextView, ArrayList<String> imTextView, ArrayList<String> apiTextView, ArrayList<String> softWareCodeTextView,ArrayList<String> serverIpTextView,ArrayList<String> dzIpTextView)
			throws JSONException, IOException {
		
		JSONObject json = new JSONObject();
		JSONArray array1 = new JSONArray();
		JSONArray array2 = new JSONArray();
		JSONArray array3 = new JSONArray();
		JSONArray array4 = new JSONArray();
		JSONArray array5 = new JSONArray();
		JSONArray array6 = new JSONArray();
		JSONArray array7 = new JSONArray();
		JSONArray array8 = new JSONArray();
		for (int i = 0; i < loginTextView.size(); i++) {
			array1.put(loginTextView.get(i));
		}
		
		for (int i = 0; i < debugTextView.size(); i++) {
			array2.put(debugTextView.get(i));
		}
		
		for (int i = 0; i < pushTextView.size(); i++) {
			array3.put(pushTextView.get(i));
		}
		
		for (int i = 0; i < imTextView.size(); i++) {
			array4.put(imTextView.get(i));
		}
		
		for (int i = 0; i < apiTextView.size(); i++) {
			array5.put(apiTextView.get(i));
		}

		for (int i = 0; i < softWareCodeTextView.size(); i++) {
			array6.put(softWareCodeTextView.get(i));
		}

		for (int i = 0; i < serverIpTextView.size(); i++) {
			array7.put(serverIpTextView.get(i));
		}
		for (int i = 0; i < dzIpTextView.size(); i++) {
			array8.put(dzIpTextView.get(i));
		}
		json.put(JSON_LOGIN, array1);
		json.put(JSON_DEBUG, array2); 
		json.put(JSON_PUSH, array3);
		json.put(JSON_IM, array4);
		json.put(JSON_API, array5);
		json.put(JSON_CODE, array6);
		json.put(JSON_CODE, array7);
		json.put(JSON_DZ, array8);

		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(json.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	
}
















