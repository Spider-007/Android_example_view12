package com.htmitech.emportal.base;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.htmitech.emportal.R;

/**
 * 手势滑动返回基础类
 * 
 * @author rainkey
 * 
 */
public class SlidingBackAcitivity extends MyBaseFragmentActivity implements SlidingLayout.PanelSlideListener {

    // 是否为手势滑动退出，执行的退出动画有区别
    private boolean mIsSlideFinish = false;

    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_sliding_back);
        overridePendingTransition(R.anim.slide_in_right, R.anim.none);
        FrameLayout rootView = (FrameLayout) findViewById(R.id.content_view);
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.BOTTOM);
        lp.setMargins(0, 0, 0, 0);
        rootView.addView(contentView, lp);

        SlidingLayout slidingPane = (SlidingLayout) findViewById(R.id.sliding_pane);
        int sliderFadeColor = this.getResources().getColor(android.R.color.transparent);

        // slidingPane.setShadowResource(R.drawable.);

        slidingPane.setSliderFadeColor(sliderFadeColor);
        slidingPane.setPanelSlideListener(this);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        // 滑动超过90%就直接返回
        if (slideOffset >= 0.9) {
            mIsSlideFinish = true;
            finish();
        }
        mIsSlideFinish = false;
    }

    @Override
    public void onPanelOpened(View panel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPanelClosed(View panel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finish() {
        super.finish();
        if (!mIsSlideFinish) {
            overridePendingTransition(R.anim.none, R.anim.slide_out_right);
        } else {
            overridePendingTransition(R.anim.none, R.anim.transparent_out);
        }
    }
}
