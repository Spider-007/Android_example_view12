package com.htmitech.emportal.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;

public class NewTopTabIndicator extends FrameLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private String[] titleArray = new String[]{};
    private Context context;
    private ImageView cursor;// 动画图片
    private LinearLayout mBackground;
    private View mLeftBackground;
    private View mRightBackground;

    private int currIndex = 0;// 当前页卡编号
    private int tabNum = 0;
    private int mCursorWidth = 100;
    private ViewPager mViewPager;
    private LinearLayout layout;
    private int selectColor, defaultColor;

    private int mMarginLeft = 0;
    private int mMarginRight = 0;
    private final EmpPortal empPortal;
    private int mScreenWidth;
    private HorizontalScrollView head_scrollview;

    private OnPageSelect mOnPageSelect;

    private View view;

    public void setmOnPageSelect(OnPageSelect mOnPageSelect) {
        this.mOnPageSelect = mOnPageSelect;
    }

    public OnPageSelect getmOnPageSelect() {
        return mOnPageSelect;
    }

    public NewTopTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
        empPortal = mAppliationCenterDao.getPortalId();
    }

    public void setCommonData(ViewPager mViewPager, String[] titleArray, int selectColor, int defaultColor) {
        this.mViewPager = mViewPager;
        this.titleArray = titleArray;
        this.selectColor = selectColor;
        this.defaultColor = defaultColor;
        initTab(context);
    }

    /***
     * 设置cursor margin
     *
     * @param marginLeft
     * @param marginRight
     */
    public void setMarginLR(int marginLeft, int marginRight) {
        this.mMarginLeft = marginLeft;
        this.mMarginRight = marginRight;
    }

    public void setCommonData2(ViewPager mViewPager, String[] titleArray, int resSelectColor, int resDefaultColor) {
        this.setCommonData(mViewPager, titleArray, context.getResources().getColor(resSelectColor), context
                .getResources().getColor(resDefaultColor));
    }

    private void initTab(Context context) {
        tabNum = this.titleArray.length;
        View.inflate(context, R.layout.layout_newtabindicator, this);
        InitTextView();
        InitImageView();
        if(mViewPager != null){
            mViewPager.setOnPageChangeListener(this);
            mViewPager.setCurrentItem(0);
        }
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {

        layout = (LinearLayout) findViewById(R.id.linearLayout_newtabindicator_bar);
        head_scrollview = (HorizontalScrollView) findViewById(R.id.head_scrollview);
        for (int i = 0; i < tabNum; i++) {
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
            View view = View.inflate(context, R.layout.layout_newtabtitle_item, null);
            if (tabNum > 3) {
                params.setMargins(DeviceUtils.dip2px(context, 10), 0, DeviceUtils.dip2px(context, 10), 0);
            }
            TextView textView = (TextView) view.findViewById(R.id.tab_indicator_first);
            if (null != empPortal && empPortal.color_style == 3 && i == 0) {    //红色
                textView.setTextColor(Color.parseColor("#DC5656"));
            } else if (i == 0) {
                textView.setTextColor(Color.parseColor("#297BFB"));
            } else {
                textView.setTextColor(Color.parseColor("#ff555555"));
            }
//            textView.setTextColor(i == 0 ? this.selectColor : defaultColor);
            textView.setText(titleArray[i]);
            textView.setTag(i);
            params.gravity = Gravity.CENTER_VERTICAL;
            textView.setOnClickListener(this);
            layout.addView(view, params);
        }
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.imageview_newtabindicator_cursor);
        // DisplayMetrics dm = new DisplayMetrics();
        // WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // wm.getDefaultDisplay().getMetrics(dm);
        // int screenW = dm.widthPixels;// 获取分辨率宽度

//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(DeviceUtils.dip2px(getContext(), mCursorWidth), DeviceUtils.dip2px(
//                        getContext(), 2));
//        cursor.setLayoutParams(params);

        mBackground = (LinearLayout) findViewById(R.id.linearLayout_newtabindicator_background);
        mLeftBackground = findViewById(R.id.v_newtabindicator_left);
        mRightBackground = findViewById(R.id.v_newtabindicator_right);

//        refresh(0, 0);

        selectTab(0);
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag != null) {
//            AdapterFragmentForCollection mFormFragment = (AdapterFragmentForCollection) mViewPager.getAdapter();

//            FormFragment fragment = (FormFragment) mFormFragment.getItem(0);
//            if(fragment != null){
//                ArrayList<ComboBox> comboxList = fragment.getComboxList();
//                for(ComboBox mComboBox : comboxList){
//                    mComboBox.closePopupWindow();
//                }
//            }
            if(mViewPager != null){
                mViewPager.setCurrentItem(Integer.parseInt(tag.toString()), true);
            }else{
                this.view = view;

                onPageSelected(Integer.parseInt(tag.toString()));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Animation animation = null;
        // float fromX = position * bmpW + (int) (positionOffsetPixels * 1.0f / tabNum * positionOffset);
        // float toX = position * bmpW;
        // animation = new TranslateAnimation(fromX, toX, 0, 0);
        // animation.setFillAfter(true);// True:图片停在动画结束位置
        // animation.setDuration(300);
        // cursor.startAnimation(animation);
//        refresh(position, positionOffset);
    }

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View checkView = layout.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
            head_scrollview.smoothScrollTo(i2, 0);
            // mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
            // mItemWidth , 0);
        }

        for (int j = 0; j < layout.getChildCount(); j++) {
            View checkView = layout.getChildAt(j);
            TextView tv = (TextView) checkView.findViewById(R.id.tab_indicator_first);
            ImageView imageView = (ImageView) checkView.findViewById(R.id.imageview_newtabindicator_cursor);
            if (j == tab_postion) {
                if (null != empPortal && empPortal.color_style == 1) {
                    imageView.setBackgroundColor(context.getResources().getColor(R.color.form_bg_user));
                    tv.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                } else if (null != empPortal && empPortal.color_style == 3) {
                    imageView.setBackgroundColor(context.getResources().getColor(R.color.form_bg_user_red));
                    tv.setTextColor(context.getResources().getColor(R.color.form_bg_user_red));
                } else {
                    imageView.setBackgroundColor(context.getResources().getColor(R.color.form_bg_user));
                    tv.setTextColor(context.getResources().getColor(R.color.form_bg_user));
                }
                imageView.setVisibility(VISIBLE);
            } else {
                tv.setTextColor(this.defaultColor);
                imageView.setVisibility(GONE);
            }
        }
    }

    private void refresh(int position, float mPositionOffset) {

        View leftView = layout.getChildAt(position);
        View rightView = layout.getChildAt(position + 1);

        int leftCenter = 0;
        if (leftView != null) {
            leftCenter = (leftView.getLeft() + leftView.getRight()) / 2;
        }

        int rightCenter = 0;
        if (rightView != null) {
            rightCenter = (rightView.getLeft() + rightView.getRight()) / 2;
        }

        int indicatorCenter = (int) (leftCenter + (rightCenter - leftCenter) * mPositionOffset);

        int indicatorWidth = cursor.getWidth();

        LinearLayout.LayoutParams leftParams = (LinearLayout.LayoutParams) mLeftBackground.getLayoutParams();
        leftParams.width = indicatorCenter - indicatorWidth / 2;
        mLeftBackground.setLayoutParams(leftParams);
        mLeftBackground.invalidate();

        LinearLayout.LayoutParams rightParams = (LinearLayout.LayoutParams) mRightBackground.getLayoutParams();
        rightParams.width = mBackground.getWidth() - indicatorCenter + indicatorWidth / 2;
        mRightBackground.setLayoutParams(rightParams);
        mRightBackground.invalidate();

        LinearLayout.LayoutParams parentParams = (LinearLayout.LayoutParams) mBackground.getLayoutParams();
        parentParams.setMargins(mMarginLeft, 0, mMarginRight, 0);
        mBackground.setLayoutParams(parentParams);
        mBackground.invalidate();
    }

    /**
     * 页卡切换监听
     */
    @Override
    public void onPageSelected(int position) {
//        TextView tv = (TextView) layout.getChildAt(position);
//        if(null != empPortal && empPortal.color_style == 3 ){    //红色
//            tv.setTextColor(Color.parseColor("#DC5656"));
//        }else{
//            tv.setTextColor(Color.parseColor("#297BFB"));
//        }
////        tv.setTextColor(this.selectColor);
//        TextView tv_last = (TextView) layout.getChildAt(currIndex);
//        tv_last.setTextColor(this.defaultColor);

        if(mOnPageSelect != null){
            mOnPageSelect.onPageSelect(view,position);
        }
        if(mViewPager != null){
            mViewPager.setCurrentItem(position);
        }
        try {
            selectTab(position);
        }catch (Exception e){

        }

        currIndex = position;


    }

    public interface OnPageSelect{
        void onPageSelect(View view,int position);
    }
}
