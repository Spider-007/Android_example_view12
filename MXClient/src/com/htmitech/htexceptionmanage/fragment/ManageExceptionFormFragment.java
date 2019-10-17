package com.htmitech.htexceptionmanage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.ObservableScrollView;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.htexceptionmanage.activity.ManageExceptionDetalActivity;
import com.htmitech.htexceptionmanage.entity.ExceptionList;
import com.htmitech.htexceptionmanage.entity.SourceContentInfo;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import mobilereport.com.chatkit.domain.TextStyle;
import mobilereport.com.chatkit.util.WaterMarkTextUtil;

/**
 *	异常提醒 Form详情
 * @author joe
 * @date 2017-9-25 14:44:20
 *
 */
public class ManageExceptionFormFragment extends MyBaseFragment implements ScrollViewListener{

	private TextView tv_alert_id;
	private TextView tv_alert_type;
	private TextView tv_alert_receive;
	private TextView tv_alert_source;
	private TextView tv_alert_time;
	private TextView tv_alert_title;
	private TextView tv_alert_content;
	private int isWaterSecurity;


	private ObservableScrollView zoom;//手势放大
	private SourceContentInfo mSourceContentInfo;
	private ExceptionList exceptionList;


	/**
	 * 获取布局id，用于setContentView。
	 *
	 * @return id
	 */
	protected int getLayoutId() {
		return R.layout.fragment_exception_form;
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
		tv_alert_time = (TextView) findViewById(R.id.tv_alert_time);
		tv_alert_type = (TextView) findViewById(R.id.tv_alert_type);
		tv_alert_id = (TextView) findViewById(R.id.tv_alert_id);
		tv_alert_receive = (TextView) findViewById(R.id.tv_alert_receive);
		tv_alert_source = (TextView) findViewById(R.id.tv_alert_source);
		tv_alert_title = (TextView) findViewById(R.id.tv_alert_title);
		tv_alert_content = (TextView) findViewById(R.id.tv_alert_content);
		initValues();
	}

	private void initValues() {
		zoom = (ObservableScrollView) findViewById(R.id.sc_freement);
		zoom.setScrollViewListener(this);
		exceptionList = ((ManageExceptionDetalActivity)getActivity()).getExceptionList();
		isWaterSecurity = ((ManageExceptionDetalActivity)getActivity()).getIsWaterSecurity();

		tv_alert_time.setText(exceptionList.getCreateTime());
		tv_alert_type.setText(exceptionList.getSourceTypeName());
		tv_alert_id.setText(exceptionList.getAlertId());
		tv_alert_receive.setText(exceptionList.getAlertUserList());
		tv_alert_source.setText(exceptionList.getSourceUserName());
		tv_alert_title.setText(exceptionList.getAlertTitle());
		tv_alert_content.setText(exceptionList.getAlertContent());

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
