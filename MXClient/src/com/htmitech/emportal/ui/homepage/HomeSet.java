package com.htmitech.emportal.ui.homepage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.minxing.client.ClientTabActivity;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;

public class HomeSet extends BaseFragmentActivity implements OnClickListener,
		OnPageChangeListener {

	// public static String CURRENT_PAGE;
	public static final String METRO_PAGE = "metropage";
	public static final String GRID_PAGE = "gridpage";
	public static final String GROUP_PAGE = "grouppage";
	public static final String GROUP_FZ_PAGE = "group_fz_page";

	private ViewPager vp;
	private ViewPageAdapter vpAdapter;
	private List<View> views;

//	private static final int[] pics = { R.drawable.change_metro_style1, R.drawable.change_grid_style2, R.drawable.change_card_list_style3, R.drawable.change_group_nav_style4};
	private static final int[] pics = { R.drawable.img_change_metro_style1, R.drawable.img_change_grid_style2, R.drawable.img_change_card_style3, R.drawable.img_change_group_nav_style4};


	private ImageView[] dots;

	private int currentIndex;
	
	private String currentMainPage = null;

	private ImageButton leftBackButton;
	private Button rightOkButton;

	private TextView tv_home_style;

	private String localIndex = "";
	private AppliationCenterDao mAppliationCenterDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		currentMainPage = HomePageStyleEnum.getHomePageStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style).code;


		mAppliationCenterDao = new AppliationCenterDao(this);
		Constant.channelConstant = false;
		views = new ArrayList<View>();
		localIndex = HomePageStyleEnum.getHomePageStyle(BookInit.getInstance().getmApcUserdefinePortal().getUsing_home_style()).code;
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(400,
				600);
		for (int i = 0; i < pics.length; i++) {
			ImageView image = new ImageView(this);
			image.setLayoutParams(mParams);
			image.setImageResource(pics[i]);
			views.add(image);
		}

		// 初始化页面
		vp = (ViewPager) findViewById(R.id.viewpager_homeset);
		vpAdapter = new ViewPageAdapter(views,HomeSet.this);
		vp.setAdapter(vpAdapter);

		// 绑定回调
		vp.setOnPageChangeListener(this);
		initDots();
		
		//title
		((TextView)findViewById(R.id.title_name)).setText("首页风格设置");
		
		//返回按钮
		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});
		
//		//确定修改按钮
//		rightOkButton = (Button) findViewById(R.id.title_right_button);
		tv_home_style = (TextView) findViewById(R.id.tv_home_style);
		tv_home_style.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentMainPage();
				ClientTabActivity.HomeSetback = true;
				finishWithAnimation();
				int homeStyle = HomePageStyleEnum.getCodeHomePageStyle(currentMainPage).homeStyle;
				BookInit.getInstance().getmApcUserdefinePortal().setUsing_home_style(homeStyle);
				mAppliationCenterDao.saveUserDefinePortal(BookInit.getInstance().getmApcUserdefinePortal());
				mAppliationCenterDao.saveCurrentPortalMessage(BookInit.getInstance().getmApcUserdefinePortal().getUsing_font_style() + "", BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().portal_id);
				BookInit.getInstance().getmCallbackMX().closeDrawer();
				BookInit.getInstance().getmCallbackMX().updatePortalSizeStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "",BookInit.getInstance().getmApcUserdefinePortal().using_color_style + "",BookInit.getInstance().getmApcUserdefinePortal().getUsing_font_style()+"");
				PreferenceUtils.setIsSetHomePage(true);
				RefreshTotal.addReshActivity();
			}
		});
		setPagePos(0);
//		rightOkButton.setText("确定");
//		rightOkButton.setVisibility(View.VISIBLE);
	}
	

	private void setCurrentMainPage() {
		PreferenceUtils.saveCurrentPage(currentMainPage);
	}
	
	

	// 生成一个原点图标
	private ImageView getDotItem() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
		params.gravity = Gravity.CENTER_VERTICAL;
		params.rightMargin = 6;

		ImageView img = new ImageView(this);
		img.setLayoutParams(params);
		img.setBackgroundResource(R.drawable.dot);
		img.setEnabled(true);
		return img;
	}

	/**
	 * 设置当前原点
	 */
	private void setCurDot(int position) {
		int number =  1;
		if(dots.length == 0){
			number = 0;
		}else{
			number = position % dots.length;
		}
		if (number < 0 || number > pics.length - 1
				|| number == currentIndex)
			return;
		dots[currentIndex].setEnabled(true);
		dots[number].setEnabled(false);
		currentIndex = number;


	}

	// 初始化小点
	private void initDots() {
		LinearLayout linearDot = (LinearLayout) findViewById(R.id.dot_homeset);
		dots = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			// 根据图片数据动态添加圆点
			dots[i] = getDotItem();
			linearDot.addView(dots[i]);
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false); // 设置为白色，即选中状态
	}

	protected void finishWithAnimation() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length
				|| position == vp.getCurrentItem())
			return;
		vp.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurDot(arg0);
		// 修改currentpage
		setPagePos(arg0 % dots.length);
	}
	
	private void setPagePos(int arg0) {
		if (arg0 == 0) {
			currentMainPage = METRO_PAGE;
		} else if (arg0 == 1) {
			currentMainPage = GRID_PAGE;
		} else if(arg0 == 2)
		{
			currentMainPage = GROUP_PAGE;
		}else if(arg0 == 3)
		{
			currentMainPage = GROUP_FZ_PAGE;
		}
		if(localIndex.equals(currentMainPage)){
			tv_home_style.setText("正在使用中");
			tv_home_style.setTextColor(getResources().getColor(R.color.huise));
			tv_home_style.setEnabled(false);
		}else{
			tv_home_style.setText("使用此风格");
			tv_home_style.setTextColor(getResources().getColor(R.color.white));
			tv_home_style.setEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
