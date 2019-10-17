package com.htmitech.emportal.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;

public class BottomTabIndicator extends FrameLayout implements
		ViewPager.OnPageChangeListener, View.OnClickListener {

	private MainViewPager mViewPager;
	private TextView mDaiBanTab;
	private TextView mDaiYueTab;
	private TextView mCircleTab;
	private TextView mCommunicationTab;
	private TextView mTextview_sysmsgnum;
	private int messageNum = -1;

	private IBottomItemSelectCallBack mIBottomItemSelectCallBack;

	public void setBottomItemSelectCallBack(IBottomItemSelectCallBack callBack) {
		this.mIBottomItemSelectCallBack = callBack;
	}

	public BottomTabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View.inflate(context, R.layout.bottom_tab_indicator_layout, this);
		mDaiBanTab = (TextView) this.findViewById(R.id.tab_daiban);
		mDaiYueTab = (TextView) this.findViewById(R.id.tab_daiyue);
		mCircleTab = (TextView) this.findViewById(R.id.tab_circle);
		mCommunicationTab = (TextView) this
				.findViewById(R.id.tab_communication);
		mTextview_sysmsgnum = (TextView) this
				.findViewById(R.id.textview_circle_num);

		mDaiBanTab.setOnClickListener(this);
		mDaiYueTab.setOnClickListener(this);
		mCircleTab.setOnClickListener(this);
		mCommunicationTab.setOnClickListener(this);

		findViewById(R.id.linearlayout_tab_daiban).setOnClickListener(this);
		findViewById(R.id.linearlayout_tab_daiyue).setOnClickListener(this);
		findViewById(R.id.linearlayout_tab_cicle).setOnClickListener(this);
		findViewById(R.id.linearlayout_tab_communication).setOnClickListener(
				this);
	}

	public void setViewPager(MainViewPager vp) {
		if (vp != null) {
			this.mViewPager = vp;
			mViewPager.setOnPageChangeListener(this);
		}
	}

	// begin OnPageChangeListener --
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		refreshTab();
		if (mViewPager != null && mIBottomItemSelectCallBack != null) {
			mIBottomItemSelectCallBack.onFragmentTabClick(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	// end OnPageChangeListener --

	// begin View onclick
	@Override
	public void onClick(View v) {
		if (mViewPager == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.tab_daiban:
		case R.id.linearlayout_tab_daiban:
			mViewPager.setCurrentItem(MainViewPager.INDEX_DAIBAN, true);
			break;
		case R.id.tab_daiyue:
		case R.id.linearlayout_tab_daiyue:
			mViewPager.setCurrentItem(MainViewPager.INDEX_DAIYUE, true);
			break;
		case R.id.tab_circle:
		case R.id.linearlayout_tab_cicle:
			mViewPager.setCurrentItem(MainViewPager.INDEX_CIRCLE, true);
			break;
		case R.id.tab_communication:
		case R.id.linearlayout_tab_communication:
			mViewPager.setCurrentItem(MainViewPager.INDEX_COMMUNICATION, true);
			break;
		default:
			break;
		}
		refreshTab();
	}

	public void setMessageNum(int num) {
		if (mTextview_sysmsgnum == null) {
			mTextview_sysmsgnum = (TextView) this
					.findViewById(R.id.textview_circle_num);
		}

		if (num <= 0) {
			mTextview_sysmsgnum.setVisibility(View.INVISIBLE);
		} else {
			mTextview_sysmsgnum.setVisibility(View.VISIBLE);
			mTextview_sysmsgnum.setText(num + "");
		}

		this.messageNum = num;
	}

	public int getMessageNum() {
		return messageNum;
	}

	private void refreshTab() {
		switch (mViewPager.getCurrentItem()) {
		case MainViewPager.INDEX_DAIBAN:
			mDaiBanTab.setTextColor(getResources().getColor(
					R.color.color_title));
			mDaiBanTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_home_p, 0, 0);
			mDaiYueTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mDaiYueTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCommunicationTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_user_n, 0, 0);
			mCommunicationTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCircleTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mCircleTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			break;
		case MainViewPager.INDEX_DAIYUE:
			mDaiBanTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_home_n, 0, 0);
			mDaiBanTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mDaiYueTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_p, 0, 0);
			mDaiYueTab.setTextColor(getResources().getColor(
					R.color.color_title));
			mCommunicationTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_user_n, 0, 0);
			mCommunicationTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCircleTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mCircleTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			break;
		case MainViewPager.INDEX_COMMUNICATION:
			mDaiBanTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_home_n, 0, 0);
			mDaiBanTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mDaiYueTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mDaiYueTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCommunicationTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_user_p, 0, 0);
			mCommunicationTab.setTextColor(getResources().getColor(
					R.color.color_title));
			mCircleTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mCircleTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			break;
		case MainViewPager.INDEX_CIRCLE:
			mDaiBanTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_home_n, 0, 0);
			mDaiBanTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mDaiYueTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_n, 0, 0);
			mDaiYueTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCommunicationTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_user_n, 0, 0);
			mCommunicationTab.setTextColor(getResources().getColor(
					R.color.color_main_tab_text_n));
			mCircleTab.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.ic_msg_p, 0, 0);
			mCircleTab.setTextColor(getResources().getColor(
					R.color.color_title));
			break;
		default:
			break;
		}
	}
	// end View onclick

}
