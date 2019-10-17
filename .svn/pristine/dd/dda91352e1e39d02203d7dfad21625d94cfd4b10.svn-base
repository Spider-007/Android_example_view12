package com.htmitech.emportal.common.lib.residemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.AppInfo;

import htmitech.com.componentlibrary.SwitchButton;

/**
 * User: special
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout{

    /** menu item  icon  */
    private ImageView iv_icon;
    /** menu item  title */
    private TextView tv_title;

    private AppInfo mAppInfo;
    private TextView tv_new;

    private ImageView tvJt;

    private View viewLine;

    private SwitchButton mSwitchButton;

    private SwitchButton.OnStateChangedListener mOnStateChangedListener;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title) {
        super(context);
        initViews(context);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, Bitmap icon, String title) {
        super(context);
        initViews(context);
        iv_icon.setImageBitmap(icon);
        tv_title.setText(title);
    }
    public ResideMenuItem(Context context, String title) {
        super(context);
        initViews(context);
        tv_title.setText(title);
    }

    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tvJt = (ImageView) findViewById(R.id.tv_jt);
        tv_new = (TextView)findViewById(R.id.tv_new);
        viewLine = (View)findViewById(R.id.view_line);
        mSwitchButton = (SwitchButton)findViewById(R.id.button);
        mSwitchButton.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                if(mOnStateChangedListener != null){
                    mOnStateChangedListener.toggleToOn();
                }
            }

            @Override
            public void toggleToOff() {
                if(mOnStateChangedListener != null){
                    mOnStateChangedListener.toggleToOff();
                }
            }
        });
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon){
        iv_icon.setImageResource(icon);
    }

    /**
     *
     */
    public ImageView getIcon(){
        return iv_icon;
    }
    /**
     * set the title with resource
     * ;
     * @param title
     */
    public void setTitle(int title){
        tv_title.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }

    public void setmAppInfo(AppInfo mAppInfo){
        this.mAppInfo = mAppInfo;
    }

    public AppInfo getmAppInfo(){
        return mAppInfo;
    }

    public void setNewFlag(){
        tv_new.setVisibility(View.VISIBLE);
    }

    public void setImageJT(int ids){
        tvJt.setImageResource(ids);
    }

    public void setImageState(int visible){
        tvJt.setVisibility(visible);
    }

    public SwitchButton.OnStateChangedListener getmOnStateChangedListener() {
        return mOnStateChangedListener;
    }

    public void setmOnStateChangedListener(SwitchButton.OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }

    public void setmSwitchButton(int visible){
        mSwitchButton.setVisibility(visible);

        mSwitchButton.setState(true);

    }

    public void setTitleColor(){
        tv_title.setTextColor(getContext().getResources().getColor(R.color.black));
    }

    public void setLineBackColor(int color){
        viewLine.setBackgroundColor(getContext().getResources().getColor(color));
    }
}
