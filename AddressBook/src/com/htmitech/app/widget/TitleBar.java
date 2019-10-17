package com.htmitech.app.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.addressbook.R;

/**
 * 使用通用的titleBar
 */
public class TitleBar extends RelativeLayout {

    private Drawable left_button_text_back;

    private int right_button_text_color;
    private String right_button_title;
    private float right_button_size;
    private Drawable right_button_text_back;

    private int title_color;
    private String title_name;
    private float title_size;
    private Drawable title_background;

    private TextView tv_title;
    private ImageView tv_left;
    private TextView tv_right;

    private RelativeLayout.LayoutParams leftParams,titleParams,rightParams;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray tda = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        left_button_text_back = tda.getDrawable(R.styleable.TopBar_left_button_text_back);

        right_button_text_color = tda.getColor(R.styleable.TopBar_right_button_text_color, 0);
        right_button_title = tda.getString(R.styleable.TopBar_right_button_title);
        right_button_size = tda.getDimension(R.styleable.TopBar_right_button_size, 0);
        right_button_text_back = tda.getDrawable(R.styleable.TopBar_right_button_text_back);

        title_color = tda.getColor(R.styleable.TopBar_title_color, 0);
        title_name = tda.getString(R.styleable.TopBar_title_name);
        title_size = tda.getDimension(R.styleable.TopBar_title_size, 0);
        title_background = tda.getDrawable(R.styleable.TopBar_title_background);
        tda.recycle();
        /**
         * 接下来是初始化leftButton title rightButton
         */
        tv_left = new ImageView(context,null,R.style.title_button);
        tv_left.setImageDrawable(left_button_text_back);
        tv_left.setScaleType(ImageView.ScaleType.CENTER);

        tv_right = new TextView(context);
        tv_right.setText(right_button_title);
        tv_right.setTextColor(right_button_text_color);
        tv_right.setBackground(right_button_text_back);
        tv_right.setTextSize(right_button_size);

        tv_title = new TextView(context);
        tv_title.setText(title_name);
        tv_title.setTextColor(title_color);
        tv_title.setBackground(title_background);
        tv_title.setTextSize(title_size);

        setBackgroundColor(context.getResources().getColor(R.color.ht_hred_title));

        leftParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(tv_left, leftParams);

        titleParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        tv_title.setGravity(Gravity.CENTER);
        addView(tv_title, titleParams);

        rightParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);

        addView(tv_right,rightParams);

    }

}
