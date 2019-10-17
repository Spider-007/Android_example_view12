package com.htmitech.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 空页面 错误页面 数据加载中 展示
 */
public class EmptyLayout extends RelativeLayout {

    private Animation mLoadingAnimation;

    private ViewGroup mLoadingView;
    private ViewGroup mEmptyView;
    private ViewGroup mErrorView;

    private TextView mNoWifiMessageView;
    private TextView mEmptyMessageView;
    private TextView mErrorMessageView;


    private RelativeLayout mEmptyRelativeLayout;
    private int mErrorMessageViewId;
    private int mEmptyMessageViewId;
    private int mNoWifiMessageViewId;
    private LayoutInflater mInflater;
    private boolean mViewsAdded;
    private int mLoadingAnimationViewId;
    private OnClickListener onNoWifiButtonClickListener;
    private OnClickListener mEmptyButtonClickListener;
    private OnClickListener mErrorButtonClickListener;


    // ---------------------------
    // static variables
    // ---------------------------
    /**
     * 空页面
     */
    public final static int TYPE_EMPTY = 1;
    /**
     * 无网络连接
     */
    public final static int TYPE_NOWIFI = 2;
    /**
     * 服务器连接异常
     */
    public final static int TYPE_ERROR = 3;

    // ---------------------------
    // default values
    // ---------------------------
    private int mEmptyType = TYPE_NOWIFI;
    private int mErrorDrawable = R.drawable.img_page_none;
    private int mEmptyDrawable = R.drawable.img_empty_nodata;
    private int mNoWifiDrawable = R.drawable.img_no_wifi;
    private int notData = R.drawable.img_no_add;
    private String mErrorMessage = "服务器连接异常";
    private String mEmptyMessage = "当前没有数据";
    private String mNoWifiMessage = "网络连接失败";

    private int mNoWifiButtonId = R.id.buttonLoading;
    private int mErrorViewButtonId = R.id.buttonError;
    private int mEmptyViewButtonId = R.id.buttonEmpty;
    private ProgressBar viewProgress;
    private boolean mShowEmptyButton = true;
    private boolean mShowNoWifiButtton = true;
    private boolean mShowErrorButton = true;

    private List<View> childViews;


    public EmptyLayout(Context context) {
        super(context);
        init();
    }


    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("NewApi")
    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        childViews = new ArrayList<View>();
//        getChildViews();

        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // ---------------------------
    // getters and setters
    // ---------------------------

    /**
     * Gets the loading layout
     *
     * @return the loading layout
     */
    public ViewGroup getLoadingView() {
        return mLoadingView;
    }

    /**
     * Sets loading layout
     *
     * @param loadingView
     */
    public void setLoadingView(ViewGroup loadingView) {
        this.mLoadingView = loadingView;
    }

    /**
     * Sets loading layout resource
     *
     * @param res
     */
    public void setLoadingViewRes(int res) {
        this.mLoadingView = (ViewGroup) mInflater.inflate(res, null);
    }

    /**
     * Gets the empty layout
     *
     * @return the empty layout
     */
    public ViewGroup getEmptyView() {
        return mEmptyView;
    }

    /**
     * Sets empty layout
     *
     * @param emptyView
     */
    public void setEmptyView(ViewGroup emptyView) {
        this.mEmptyView = emptyView;
    }

    /**
     * Sets empty layout resource
     *
     * @param res
     */
    public void setEmptyViewRes(int res) {
        this.mEmptyView = (ViewGroup) mInflater.inflate(res, null);
    }

    /**
     * Gets the error layout
     *
     * @return the error layout
     */
    public ViewGroup getErrorView() {
        return mErrorView;
    }

    /**
     * Sets error layout
     *
     * @param errorView
     */
    public void setErrorView(ViewGroup errorView) {
        this.mErrorView = errorView;
    }

    /**
     * Sets error layout resource
     *
     * @param res
     */
    public void setErrorViewRes(int res) {
        this.mErrorView = (ViewGroup) mInflater.inflate(res, null);
    }

    /**
     * Gets the loading animation
     *
     * @return the loading animation
     */
    public Animation getLoadingAnimation() {
        return mLoadingAnimation;
    }

    /**
     * Sets the loading animation
     *
     * @param animation
     */
    public void setLoadingAnimation(Animation animation) {
        this.mLoadingAnimation = animation;
    }

    /**
     * Sets the resource of loading animation
     *
     * @param animationResource
     */
    public void setLoadingAnimationRes(int animationResource) {
        mLoadingAnimation = AnimationUtils.loadAnimation(getContext(), animationResource);
    }


    /**
     * @return
     */
    public int getEmptyType() {
        return mEmptyType;
    }

    /**
     * @param emptyType
     */
    public void setEmptyType(int emptyType) {
        this.mEmptyType = emptyType;
        changeEmptyType();
    }

    /**
     * @return
     */
    public String getErrorMessage() {
        return mErrorMessage;
    }

    /**
     * @param errorMessage  the error message
     * @param messageViewId
     */
    public void setErrorMessage(String errorMessage, int messageViewId) {
        this.mErrorMessage = errorMessage;
        this.mErrorMessageViewId = messageViewId;
    }

    /**
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    /**
     * @return
     */
    public String getEmptyMessage() {
        return mEmptyMessage;
    }

    /**
     * @param emptyMessage  the message
     * @param messageViewId
     */
    public void setEmptyMessage(String emptyMessage, int messageViewId) {
        this.mEmptyMessage = emptyMessage;
        this.mEmptyMessageViewId = messageViewId;
    }

    /**
     * @param emptyMessage the message
     */
    public void setEmptyMessage(String emptyMessage) {
        this.mEmptyMessage = emptyMessage;
    }

    /**
     * Gets the message which will be shown when the list is being loaded
     *
     * @return
     */
    public String getLoadingMessage() {
        return mNoWifiMessage;
    }

    /**
     * @param loadingMessage the message
     * @param messageViewId
     */
    public void setLoadingMessage(String loadingMessage, int messageViewId) {
        this.mNoWifiMessage = loadingMessage;
        this.mNoWifiMessageViewId = messageViewId;
    }

    /**
     * @param loadingMessage the message
     */
    public void setLoadingMessage(String loadingMessage) {
        this.mNoWifiMessage = loadingMessage;
    }

    /**
     * @return
     */
    public int getLoadingAnimationViewId() {
        return mLoadingAnimationViewId;
    }

    /**
     * @param loadingAnimationViewId the id of the view
     */
    public void setLoadingAnimationViewId(int loadingAnimationViewId) {
        this.mLoadingAnimationViewId = loadingAnimationViewId;
    }

    /**
     * Gets the OnClickListener which perform when LoadingView was click
     *
     * @return
     */
    public OnClickListener getNowifiButtonClickListener() {
        return onNoWifiButtonClickListener;
    }

    /**
     * Sets the OnClickListener to LoadingView
     *
     * @param noWifiButtonClickListener OnClickListener Object
     */
    public void setNoWifiButtonClickListener(OnClickListener noWifiButtonClickListener) {
        this.onNoWifiButtonClickListener = noWifiButtonClickListener;
    }

    /**
     * Gets the OnClickListener which perform when EmptyView was click
     *
     * @return
     */
    public OnClickListener getEmptyButtonClickListener() {
        return mEmptyButtonClickListener;
    }

    /**
     * Sets the OnClickListener to EmptyView
     *
     * @param emptyButtonClickListener OnClickListener Object
     */
    public void setEmptyButtonClickListener(OnClickListener emptyButtonClickListener) {
        this.mEmptyButtonClickListener = emptyButtonClickListener;
    }

    /**
     * Gets the OnClickListener which perform when ErrorView was click
     *
     * @return
     */
    public OnClickListener getErrorButtonClickListener() {
        return mErrorButtonClickListener;
    }

    /**
     * Sets the OnClickListener to ErrorView
     *
     * @param errorButtonClickListener OnClickListener Object
     */
    public void setErrorButtonClickListener(OnClickListener errorButtonClickListener) {
        this.mErrorButtonClickListener = errorButtonClickListener;
    }

    /**
     * Gets if a button is shown in the empty view
     *
     * @return if a button is shown in the empty view
     */
    public boolean isEmptyButtonShown() {
        return mShowEmptyButton;
    }

    /**
     * Sets if a button will be shown in the empty view
     *
     * @param showEmptyButton will a button be shown in the empty view
     */
    public void setShowEmptyButton(boolean showEmptyButton) {
        this.mShowEmptyButton = showEmptyButton;
    }

    /**
     * Gets if a button is shown in the loading view
     *
     * @return if a button is shown in the loading view
     */
    public boolean isLoadingButtonShown() {
        return mShowNoWifiButtton;
    }

    /**
     * Sets if a button will be shown in the loading view
     *
     * @param showLoadingButton will a button be shown in the loading view
     */
    public void setShowLoadingButton(boolean showLoadingButton) {
        this.mShowNoWifiButtton = showLoadingButton;
    }

    /**
     * Gets if a button is shown in the error view
     *
     * @return if a button is shown in the error view
     */
    public boolean isErrorButtonShown() {
        return mShowErrorButton;
    }

    /**
     * Sets if a button will be shown in the error view
     *
     * @param showErrorButton will a button be shown in the error view
     */
    public void setShowErrorButton(boolean showErrorButton) {
        this.mShowErrorButton = showErrorButton;
    }

    /**
     * Gets the ID of the button in the loading view
     *
     * @return the ID of the button in the loading view
     */
    public int getmNoWifiButtonId() {
        return mNoWifiButtonId;
    }

    /**
     * Sets the ID of the button in the loading view. This ID is required if you want the button the loading view to be click-able.
     *
     * @param loadingViewButtonId the ID of the button in the loading view
     */
    public void setLoadingViewButtonId(int loadingViewButtonId) {
        this.mNoWifiButtonId = loadingViewButtonId;
    }

    /**
     * Gets the ID of the button in the error view
     *
     * @return the ID of the button in the error view
     */
    public int getErrorViewButtonId() {
        return mErrorViewButtonId;
    }

    /**
     * Sets the ID of the button in the error view. This ID is required if you want the button the error view to be click-able.
     *
     * @param errorViewButtonId the ID of the button in the error view
     */
    public void setErrorViewButtonId(int errorViewButtonId) {
        this.mErrorViewButtonId = errorViewButtonId;
    }

    /**
     * Gets the ID of the button in the empty view
     *
     * @return the ID of the button in the empty view
     */
    public int getEmptyViewButtonId() {
        return mEmptyViewButtonId;
    }

    /**
     * Sets the ID of the button in the empty view. This ID is required if you want the button the empty view to be click-able.
     *
     * @param emptyViewButtonId the ID of the button in the empty view
     */
    public void setEmptyViewButtonId(int emptyViewButtonId) {
        this.mEmptyViewButtonId = emptyViewButtonId;
    }


    public int getErrorDrawable() {
        return mErrorDrawable;
    }

    /**
     * 设置错误的图片
     *
     * @param mErrorDrawable 错误图片资源id
     */
    public void setErrorDrawable(int mErrorDrawable) {
        this.mErrorDrawable = mErrorDrawable;
    }

    public int getEmptyDrawable() {
        return mEmptyDrawable;
    }

    /**
     * 设置空的图片
     *
     * @param mEmptyDrawable 错误空资源id
     */
    public void setEmptyDrawable(int mEmptyDrawable) {
        this.mEmptyDrawable = mEmptyDrawable;
    }

    public int getLoadingDrawable() {
        return mNoWifiDrawable;
    }

    /**
     * 设置Loading的图片
     *
     * @param mNoWifiDrawable 错误Loading资源id
     */
    public void setNoWifiDrawable(int mNoWifiDrawable) {
        this.mNoWifiDrawable = mNoWifiDrawable;
    }

    private void getChildViews() {
        int childCount = getChildCount();
        Log.d("EmptyLayout", "ChildCount:" + childCount);
        View view;
        for (int i = 0; i < childCount; i++) {
            view = getChildAt(i);
            if (isEmptyView(view)) {
                continue;
            }
            childViews.add(view);
        }
    }

    private void hideChildView() {
        for (View view : childViews) {
            if (isEmptyView(view)) {
                continue;
            }
            view.setVisibility(GONE);
        }
    }

    /**
     * 判断view 对象是否是EmptyView
     *
     * @param view
     * @return
     */
    private boolean isEmptyView(View view) {
        if ((view == null || mEmptyRelativeLayout == view || view == mLoadingView || view == mEmptyView || view == mErrorView)) {
            return true;
        }
        return false;
    }

    private void showChildView() {
        for (View view : childViews) {
            if (isEmptyView(view)) {
                continue;
            }
            view.setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏EmptyView
     */
    private void hideEmptyView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }

        if (mErrorView != null) {
            mErrorView.setVisibility(GONE);
        }
    }

    /**
     * 展示错误信息
     *
     * @param resId 图片资源id
     * @param text
     */
    public void showError(int resId, String text) {
        setErrorDrawable(resId);
        setEmptyMessage(text);
        showError();
    }

    public void showError() {
        getChildViews();
        hideChildView();
        this.mEmptyType = TYPE_ERROR;
        changeEmptyType();
    }


    /**
     * 展示空信息
     *
     * @param resId 图片资源id
     * @param text
     */
    public void showEmpty(int resId, String text) {

        setEmptyDrawable(resId);
        setEmptyMessage(text);
        showEmpty();
    }


//    public void showEmpty(int resId) {
//
//        setEmptyDrawable(R.drawable.trans_bg);
//        setEmptyMessage("");
//        showEmpty();
//        if(viewProgress != null)
//            viewProgress.setVisibility(VISIBLE);
//    }
    public void showEmpty() {

        getChildViews();
        hideChildView();
        this.mEmptyType = TYPE_EMPTY;
        changeEmptyType();

    }

    /**
     * 展示加载中
     *
     * @param resId 图片资源id
     * @param text
     */
    public void showNoWifi(int resId, String text) {
        setNoWifiDrawable(resId);
        setLoadingMessage(text);
        showNowifi();
    }

    public void showNowifi() {
        getChildViews();
        hideChildView();
        this.mEmptyType = TYPE_NOWIFI;
        changeEmptyType();
    }

    /**
     * 隐藏EmptyLayout
     */
    public void hide() {
        showChildView();
        hideEmptyView();
    }


    private void changeEmptyType() {

        setDefaultValues();
        refreshMessages();
        if(viewProgress != null){
            viewProgress.setVisibility(GONE);
        }
        // insert views in the root view
        if (!mViewsAdded) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mEmptyRelativeLayout = new RelativeLayout(getContext());
            mEmptyRelativeLayout.setGravity(Gravity.CENTER);
            mEmptyRelativeLayout.setLayoutParams(lp);
            if (mEmptyView != null) mEmptyRelativeLayout.addView(mEmptyView);
            if (mLoadingView != null) mEmptyRelativeLayout.addView(mLoadingView);
            if (mErrorView != null) mEmptyRelativeLayout.addView(mErrorView);
            mViewsAdded = true;
            mEmptyRelativeLayout.setVisibility(VISIBLE);
            addView(mEmptyRelativeLayout);
        }


        // change empty type
        View loadingAnimationView = null;
        if (mLoadingAnimationViewId > 0)
            loadingAnimationView = findViewById(mLoadingAnimationViewId);
        switch (mEmptyType) {
            case TYPE_EMPTY:
                if (mEmptyView != null) mEmptyView.setVisibility(View.VISIBLE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                    if (loadingAnimationView != null && loadingAnimationView.getAnimation() != null)
                        loadingAnimationView.getAnimation().cancel();
                }
                break;
            case TYPE_ERROR:
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                    if (loadingAnimationView != null && loadingAnimationView.getAnimation() != null)
                        loadingAnimationView.getAnimation().cancel();
                }
                break;
            case TYPE_NOWIFI:
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.VISIBLE);
//                        if (mLoadingAnimation != null && loadingAnimationView!=null) {
//                            loadingAnimationView.startAnimation(mLoadingAnimation);
//                        }
//                        else if (loadingAnimationView!=null) {
//                            loadingAnimationView.startAnimation(getRotateAnimation());
//                        }
                }
                break;
            default:
                break;
        }
    }

    private void refreshMessages() {

        if (mEmptyMessageViewId > 0 && mEmptyMessage != null) {
            mEmptyMessageView = ((TextView) mEmptyView.findViewById(mEmptyMessageViewId));
            mEmptyMessageView.setText(mEmptyMessage);
            setTopDrawables(mEmptyMessageView, mEmptyDrawable);

        }
        if (mNoWifiMessageViewId > 0 && mNoWifiMessage != null) {
            mNoWifiMessageView = ((TextView) mLoadingView.findViewById(mNoWifiMessageViewId));
            mNoWifiMessageView.setText(mNoWifiMessage);
            setTopDrawables(mNoWifiMessageView, mNoWifiDrawable);// loading 不能已经有loading image view ，不能直接设置TopDrawable
        }
        if (mErrorMessageViewId > 0 && mErrorMessage != null) {
            mErrorMessageView = ((TextView) mErrorView.findViewById(mErrorMessageViewId));
            mErrorMessageView.setText(mErrorMessage);
            setTopDrawables(mErrorMessageView, mErrorDrawable);
        }
    }


    private void setTopDrawables(TextView textView, int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    private void setDefaultValues() {
        /**
         * 空页面
         */
        if (mEmptyView == null) {
            mEmptyView = (ViewGroup) mInflater.inflate(R.layout.view_empty, null);
        }
        if (!(mEmptyMessageViewId > 0))
            mEmptyMessageViewId = R.id.textViewMessage;
        if (mShowEmptyButton && mEmptyViewButtonId > 0 && mEmptyButtonClickListener != null) {
            View emptyViewButton = mEmptyView.findViewById(mEmptyViewButtonId);
            viewProgress = (ProgressBar) mEmptyView.findViewById(R.id.viewProgress_empty);
            if (emptyViewButton != null) {
                emptyViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewProgress.setVisibility(VISIBLE);
                        mEmptyButtonClickListener.onClick(v);
                    }
                });
                emptyViewButton.setVisibility(View.VISIBLE);
            }
        } else if (mEmptyViewButtonId > 0) {
            View emptyViewButton = mEmptyView.findViewById(mEmptyViewButtonId);
            emptyViewButton.setVisibility(View.GONE);
        }

        /**
         * 无网络
         */
        if (mLoadingView == null) {
            mLoadingView = (ViewGroup) mInflater.inflate(R.layout.view_loading, null);
        }
        mLoadingAnimationViewId = R.id.imageViewLoading;
        if (!(mNoWifiMessageViewId > 0)) mNoWifiMessageViewId = R.id.textViewMessage;
        if (mShowNoWifiButtton && mNoWifiButtonId > 0 && onNoWifiButtonClickListener != null) {
            View loadingViewButton = mLoadingView.findViewById(mNoWifiButtonId);
            viewProgress = (ProgressBar) mLoadingView.findViewById(R.id.viewProgress_loading);
            if (loadingViewButton != null) {
                loadingViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewProgress.setVisibility(VISIBLE);
                        onNoWifiButtonClickListener.onClick(v);
                    }
                });
                loadingViewButton.setVisibility(View.VISIBLE);
            }
        } else if (mNoWifiButtonId > 0) {
            View loadingViewButton = mLoadingView.findViewById(mNoWifiButtonId);
            loadingViewButton.setVisibility(View.GONE);
        }

        /**
         * 服务器错误
         */
        if (mErrorView == null) {
            mErrorView = (ViewGroup) mInflater.inflate(R.layout.view_error, null);
        }
        if (!(mErrorMessageViewId > 0)) mErrorMessageViewId = R.id.textViewMessage;
        if (mShowErrorButton && mErrorViewButtonId > 0 && mErrorButtonClickListener != null) {
            View errorViewButton = mErrorView.findViewById(mErrorViewButtonId);
            viewProgress = (ProgressBar) mErrorView.findViewById(R.id.viewProgress_error);
            if (errorViewButton != null) {
                errorViewButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewProgress.setVisibility(VISIBLE);
                        mErrorButtonClickListener.onClick(v);
                    }
                });
                errorViewButton.setVisibility(View.VISIBLE);
            }
        } else if (mErrorViewButtonId > 0) {
            View errorViewButton = mErrorView.findViewById(mErrorViewButtonId);
            errorViewButton.setVisibility(View.GONE);
        }

    }

    private static Animation getRotateAnimation() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

//    showEmpty
//    showLoading
//    showError
//    setLoadingView
//    setEmptyView
//    setErrorView
//    setLoadingAnimation
//    setErrorMessage
//    setLoadingMessage
//    setEmptyMessage
//    setEmptyViewButtonClickListener
//    setLoadingViewButtonClickListener
//    setErrorViewButtonClickListener
//    setShowEmptyButton
//    setShowLoadingButton
//    setShowErrorButton
}
