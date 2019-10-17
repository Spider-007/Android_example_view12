package com.htmitech.htexceptionmanage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.ObservableScrollView;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.htexceptionmanage.activity.ManageExceptionDetalActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;

/**
 *	异常提醒 Text详情
 * @author joe
 * @date 2017-9-25 14:44:20
 *
 */
public class ManageExceptionTextFragment extends MyBaseFragment implements ScrollViewListener{

	private ObservableScrollView zoom;//手势放大
	private TextView tv_source_content;
	private String mSourceContentInfo;
	private int isWaterSecurity;
	private Map<String,String> map = new HashMap<String, String>();
	private LayoutInflater inflater;
	private LinearLayout ll_parent;
	private Gson mGson = new Gson();
	private JSONObject jsonObject;
	/**
	 * 获取布局id，用于setContentView。
	 *
	 * @return id
	 */
	protected int getLayoutId() {
		return R.layout.fragment_exception_text;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 初始化View。
	 */
	protected void initViews() {
		zoom = (ObservableScrollView) findViewById(R.id.sc_freement);
		tv_source_content = (TextView) findViewById(R.id.tv_source_content);
		ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
		zoom.setScrollViewListener(this);
		inflater = LayoutInflater.from(getActivity());
		initValues();
	}

	private void initValues() {
		mSourceContentInfo = ((ManageExceptionDetalActivity)getActivity()).getSourceContentInfo();
		isWaterSecurity = ((ManageExceptionDetalActivity)getActivity()).getIsWaterSecurity();
		tv_source_content.setText(mSourceContentInfo.toString());
		if(mSourceContentInfo != null){
			try {

				jsonObject = JSONObject.parseObject(mSourceContentInfo);
				Set<String> keySet = this.jsonObject.keySet();
				for(String key : keySet){
                    LinearLayout childLayout = (LinearLayout) inflater.inflate(R.layout.layout_exception, null);
                    TextView  tv_exception_name = (TextView) childLayout.findViewById(R.id.tv_exception_name);
                    TextView  tv_exception_value = (TextView) childLayout.findViewById(R.id.tv_exception_value);
                    tv_exception_name.setText(key);
                    tv_exception_value.setText(jsonObject.get(key).toString());
                    ll_parent.addView(childLayout);
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(isWaterSecurity==1){
			TextStyle textStyle = new TextStyle();
			textStyle.setColor("#EAEAEA");
			textStyle.setFontSize(24);
			WaterMarkTextUtil.setWaterMarkTextBg(zoom, getActivity(), OAConText.getInstance(getActivity()).UserName, 1, textStyle);
			zoom.getBackground().setAlpha((int) (0.5 * 255));
		}
	}

	@Override
	public void onScrollChanged() {

	}
	@Override
	public void onZoomText(float zoom) {
//		mTextView_content.setTextSize(zoom);

	}

	@Override
	public void onRequfouch() {

	}

}
