package com.htmitech.htworkflowformpluginnew.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.htcommonformplugin.entity.Actions;

import java.util.List;

import htmitech.com.componentlibrary.entity.ActionInfo;

public class FunctionPopupWindow extends PopupWindow {

    // private static TextView tv_detail_data;
    // private static TextView tv_share;
    public View mMenuView;
    // private ImageView iv_detail_data;
    // private ImageView iv_share;
    public LinearLayout layout_detail, layout_grad;
    public static Context context;
    private OnClickListener itemsOnClick;

    public FunctionPopupWindow(Context context, OnClickListener itemsOnClick,
                               int size) {
        super(context);
        this.context = context;
        this.itemsOnClick = itemsOnClick;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.zt_layout_function1, null);
        layout_detail = (LinearLayout) mMenuView
                .findViewById(R.id.layout_detail);
        // tv_share = (TextView) mMenuView.findViewById(R.id.tv_share);
        // iv_detail_data = (ImageView) mMenuView
        // .findViewById(R.id.iv_detail_data);
        // iv_share = (ImageView) mMenuView.findViewById(R.id.iv_share);
        layout_detail = (LinearLayout) mMenuView
                .findViewById(R.id.layout_detail);
        layout_grad = (LinearLayout) mMenuView.findViewById(R.id.layout_grad);
        // iv_detail_data.setOnClickListener(itemsOnClick);
        // iv_share.setOnClickListener(itemsOnClick);
        // 设置按钮监听
        // but_delete.setOnClickListener(itemsOnClick);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

//		int width = wm.getDefaultDisplay().getWidth();
//		int height = wm.getDefaultDisplay().getHeight();
//		if (width == 1080) {
//			h = 180;
//		}
        int h = DeviceUtils.dip2px(context, 60);

        this.setHeight(size * h);
        mMenuView.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        // 设置SelectPicPopupWindow弹出窗体可点击
        // this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        // tv_share.setVisibility(View.INVISIBLE);
        // tv_detail_data.setVisibility(View.INVISIBLE);
        // iv_detail_data.startAnimation(moveToViewLocation());
        // iv_share.startAnimation(moveToViewLocation());

    }

    public void initArcMenu(List<ActionInfo> actionList,String TodoFlag) {
        final int itemCount = actionList.size();
        for (int i = 0; i < itemCount; i++) {
//            if (!actionList.get(i).getActionID().equalsIgnoreCase("reject")) { // 退回加在最后一个
            FloatingActionButton item = new FloatingActionButton(context);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            if (actionList.get(i).getActionID().equalsIgnoreCase("submit")&& TodoFlag!=null&&TodoFlag.equals("0")) // 提交
                // item.setColorNormalResId(R.color.pink);
                item.setIcon(R.drawable.btn_bottom_action_submit);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("readed")&& TodoFlag!=null&& TodoFlag.equals("0")) // 已读
                item.setIcon(R.drawable.btn_action_read);
                // item.setColorNormalResId(R.color.color_26ffffff);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("addreader")) // 阅知
                item.setIcon(R.drawable.btn_action_yuezhi);
                // item.setColorNormalResId(R.color.color_33000000);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("getback")&& TodoFlag!=null&& TodoFlag.equals("0")) // 拿回
                item.setIcon(R.drawable.btn_action_takeback);
                // item.setColorNormalResId(R.color.color_44b2b2b2);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Share")) // 分享
                item.setIcon(R.drawable.btn_bottom_action_share);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("save")&& TodoFlag!=null&& TodoFlag.equals("0"))
                item.setIcon(R.drawable.btn_bottom_action_save);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Select"))     //筛选
                item.setIcon(R.drawable.btn_bottom_action_save);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("attention"))
                item.setIcon(R.drawable.btn_bottom_action_attention);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("reject"))
                item.setIcon(R.drawable.btn_bottom_action_save);
            else
                item.setIcon(R.drawable.btn_bottom_action_submit);
            // item.setColorNormalResId(R.color.color_55555555); //暂存
                /* item.setText(actionList.get(i).getActionName()); */
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textparams.setMargins(DeviceUtils.dip2px(context, 10), 0, DeviceUtils.dip2px(context, 10), 0);
            textView.setText(actionList.get(i).getActionName());
            item.setTitle(actionList.get(i).getActionName());
            textView.setBackgroundColor(context.getResources().getColor(
                    R.color.bantouming));
            textView.setLayoutParams(textparams);
            textView.setPadding(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5), DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5));
            textView.setTextSize(14);
            textView.getBackground().setAlpha(150);
            textView.setTextColor(context.getResources().getColor(
                    R.color.white));
            textView.setGravity(Gravity.CENTER);
            if (actionList.get(i).getActionID().equals("Attention")) {
                item.setTag(actionList.get(i).getActionName());
                textView.setTag(actionList.get(i).getActionName());
            } else {
                item.setTag(actionList.get(i).getActionID());
                textView.setTag(actionList.get(i).getActionID());
            }
            item.setOnClickListener(itemsOnClick);
            textView.setOnClickListener(itemsOnClick);
            item.setColorNormalResId(R.color.white);
            item.setColorPressedResId(R.color.color_00000000);
            textView.setVisibility(View.INVISIBLE);
            item.setVisibility(View.INVISIBLE);
            layout.addView(textView);
            layout.addView(item);
            textView.setVisibility(View.INVISIBLE);
            item.startAnimation(moveToViewLocation(textView, item));
            layout_detail.addView(layout);
            // menu.addButton(item);
//            }
        }
    }

    public void initArcMenu(List<ActionInfo> actionList) {
        final int itemCount = actionList.size();
        for (int i = 0; i < itemCount; i++) {
//            if (!actionList.get(i).getActionID().equalsIgnoreCase("reject")) { // 退回加在最后一个
            FloatingActionButton item = new FloatingActionButton(context);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            if (actionList.get(i).getActionID().equalsIgnoreCase("submit")) // 提交
                // item.setColorNormalResId(R.color.pink);
                item.setIcon(R.drawable.btn_action_submit);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("readed")) // 已读
                item.setIcon(R.drawable.btn_action_read);
                // item.setColorNormalResId(R.color.color_26ffffff);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("addreader")) // 阅知
                item.setIcon(R.drawable.btn_action_yuezhi);
                // item.setColorNormalResId(R.color.color_33000000);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("getback")) // 拿回
                item.setIcon(R.drawable.btn_action_takeback);
                // item.setColorNormalResId(R.color.color_44b2b2b2);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Share")) // 分享
                item.setIcon(R.drawable.btn_action_share);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("save"))
                item.setIcon(R.drawable.btn_action_save);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("Select"))     //筛选
                item.setIcon(R.drawable.btn_action_save);
            else if (actionList.get(i).getActionID()
                    .equalsIgnoreCase("attention"))
                item.setIcon(R.drawable.btn_action_attention);
            else if (actionList.get(i).getActionID().equalsIgnoreCase("reject"))
                item.setIcon(R.drawable.btn_action_save);
            else
                item.setIcon(R.drawable.btn_action_submit);
            // item.setColorNormalResId(R.color.color_55555555); //暂存
                /* item.setText(actionList.get(i).getActionName()); */
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textparams.setMargins(DeviceUtils.dip2px(context, 10), 0, DeviceUtils.dip2px(context, 10), 0);
            textView.setText(actionList.get(i).getActionName());
            textView.setBackgroundColor(context.getResources().getColor(
                    R.color.bantouming));
            textView.setLayoutParams(textparams);
            textView.setPadding(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5), DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5));
            textView.setTextSize(14);
            textView.getBackground().setAlpha(150);
            textView.setTextColor(context.getResources().getColor(
                    R.color.white));
            textView.setGravity(Gravity.CENTER);
            if (actionList.get(i).getActionID().equals("Attention")) {
                item.setTag(actionList.get(i).getActionName());
                textView.setTag(actionList.get(i).getActionName());
            } else {
                item.setTag(actionList.get(i).getActionID());
                textView.setTag(actionList.get(i).getActionID());
            }
            item.setOnClickListener(itemsOnClick);
            textView.setOnClickListener(itemsOnClick);
            item.setColorNormalResId(R.color.white);
            item.setColorPressedResId(R.color.color_00000000);
            textView.setVisibility(View.INVISIBLE);
            item.setVisibility(View.INVISIBLE);
            layout.addView(textView);
            layout.addView(item);
            textView.setVisibility(View.INVISIBLE);
            item.startAnimation(moveToViewLocation(textView, item));
            layout_detail.addView(layout);
            // menu.addButton(item);
//            }
        }
    }

    public void initArcMenuCommForm(List<Actions> actionList) {
        final int itemCount = actionList.size();
        for (int i = 0; i < itemCount; i++) {
//            if (!actionList.get(i).getActionID().equalsIgnoreCase("reject")) { // 退回加在最后一个
            FloatingActionButton item = new FloatingActionButton(context);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            if (actionList.get(i).getAction_id().equalsIgnoreCase("submit")) // 提交
                // item.setColorNormalResId(R.color.pink);
                item.setIcon(R.drawable.btn_bottom_action_submit);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("readed")) // 已读
                item.setIcon(R.drawable.btn_action_read);
                // item.setColorNormalResId(R.color.color_26ffffff);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("addreader")) // 阅知
                item.setIcon(R.drawable.btn_action_yuezhi);
                // item.setColorNormalResId(R.color.color_33000000);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("getback")) // 拿回
                item.setIcon(R.drawable.btn_action_takeback);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("HZ2881e452388bb20152389bf918005b")) // 提交进展
                item.setIcon(R.drawable.btn_bottom_action_submit);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("Share")) // 分享
                item.setIcon(R.drawable.btn_bottom_action_share);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("save"))
                item.setIcon(R.drawable.btn_bottom_action_save);
            else if (actionList.get(i).getAction_id()
                    .equalsIgnoreCase("attention"))
                item.setIcon(R.drawable.btn_bottom_action_attention);
            else if (actionList.get(i).getAction_id().equalsIgnoreCase("reject"))
                item.setIcon(R.drawable.btn_bottom_action_save);
            else if(actionList.get(i).getAction_id().equalsIgnoreCase("HZ90819d5ae0a391015aea3f6ee81e05")){//办结
                item.setIcon(R.drawable.btn_bottom_action_submit);
            }else if(actionList.get(i).getAction_id().equalsIgnoreCase("HZ28ba875afdcdb7015afde6bfe800a6")){//特殊办结
                item.setIcon(R.drawable.btn_bottom_action_submit);
            }else if(actionList.get(i).getAction_id().equalsIgnoreCase("HZ28ba875af4d1db015af8f2770a12e7")){//定稿
                item.setIcon(R.drawable.btn_bottom_action_submit);
            }else if(actionList.get(i).getAction_id().equalsIgnoreCase("Attention")){//添加关注
                item.setIcon(R.drawable.btn_bottom_action_save);
            }else {
                item.setIcon(R.drawable.btn_bottom_action_submit);
            }
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textparams.setMargins(DeviceUtils.dip2px(context, 10), 0, DeviceUtils.dip2px(context, 10), 0);
            textView.setText(actionList.get(i).getAction_name());
            textView.setBackgroundColor(context.getResources().getColor(
                    R.color.bantouming));
            textView.setLayoutParams(textparams);
            textView.setPadding(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5), DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 5));
            textView.setTextSize(14);
            textView.getBackground().setAlpha(150);
            textView.setTextColor(context.getResources().getColor(
                    R.color.white));
            textView.setGravity(Gravity.CENTER);
            if (actionList.get(i).getAction_id().equals("Attention")) {
                item.setTag(actionList.get(i).getAction_name());
            } else {
                item.setTag(actionList.get(i).getAction_name());
            }
            item.setOnClickListener(itemsOnClick);
            item.setColorNormalResId(R.color.white);
            item.setColorPressedResId(R.color.color_00000000);
            textView.setVisibility(View.INVISIBLE);
            item.setVisibility(View.INVISIBLE);
            layout.addView(textView);
            layout.addView(item);
            textView.setVisibility(View.INVISIBLE);
            item.startAnimation(moveToViewLocation(textView, item));
            layout_detail.addView(layout);
            // menu.addButton(item);
//            }
        }
    }

    public void setLinearVisibility() {
        layout_detail.setVisibility(View.INVISIBLE);
    }

    // // 暂时不实现退出动画
    // public void moveButton() {
    // iv_detail_data.startAnimation(moveToViewBottom());
    // iv_share.startAnimation(moveToViewBottom());
    // }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static AnimationSet moveToViewBottom(
            final TextView... tv_detail_data) {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        set.addAnimation(mHiddenAction);
        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
        set.addAnimation(alphaAnim);
        set.setDuration(500);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for (TextView mTextView : tv_detail_data) {
                    mTextView.setAnimation(AnimationUtils.makeInAnimation(
                            context, false));
                }
                // tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(
                // context, false));
                // tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
                // false));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return set;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static AnimationSet moveToViewLocation(final TextView text,
                                                  final FloatingActionButton button) {

        AnimationSet set = new AnimationSet(true);
        // TranslateAnimation mHiddenAction = new TranslateAnimation(
        // Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
        // 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
        // Animation.RELATIVE_TO_SELF, 0.0f);

        // TranslateAnimation mTranslateAnimation = new TranslateAnimation(
        // Animation.RELATIVE_TO_SELF, 0.0F,
        // Animation.RELATIVE_TO_SELF,0.0F,
        // Animation.RELATIVE_TO_SELF, 0.0F,
        // Animation.RELATIVE_TO_SELF, -300F);// 当前位置移动到指定位置
        Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 设置尺寸变化动画
        Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);

        scaleAnimation.setDuration(1000);
        alphaAnimation.setDuration(1000);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                text.setAnimation(AnimationUtils
                        .makeInAnimation(context, false));
                // tv_detail_data.setVisibility(View.VISIBLE);
                // tv_share.setVisibility(View.VISIBLE);
                // tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(
                // context, false));
                // tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
                // false));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // TranslateAnimation mHiddenAction = new
        // TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
        // Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
        // 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        // mHiddenAction.setDuration(500);
        // mHiddenAction.setAnimationListener(new Animation.AnimationListener()
        // {
        // @Override
        // public void onAnimationStart(Animation animation) {
        //
        // }
        //
        // @Override
        // public void onAnimationEnd(Animation animation) {
        // tv_detail_data.setVisibility(View.VISIBLE);
        // tv_share.setVisibility(View.VISIBLE);
        // tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(context,
        // false));
        // tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
        // false));
        // }
        //
        // @Override
        // public void onAnimationRepeat(Animation animation) {
        //
        // }
        // });
        return set;
    }

}
