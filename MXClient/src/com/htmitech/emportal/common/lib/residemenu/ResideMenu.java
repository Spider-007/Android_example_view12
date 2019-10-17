package com.htmitech.emportal.common.lib.residemenu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.widget.RoundImageView;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import com.htmitech.proxy.util.StateListDrawableUtil;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.client.util.Utils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * User: special Date: 13-12-10 Time: 下午10:44 Mail: specialcyci@gmail.com
 */
public class ResideMenu extends FrameLayout implements ObserverCallBackType {

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    private static final int PRESSED_MOVE_HORIZANTAL = 2;
    private static final int PRESSED_DOWN = 3;
    private static final int PRESSED_DONE = 4;
    private static final int PRESSED_MOVE_VERTICAL = 5;

    private ImageView imageViewShadow;
    private ImageView imageViewBackground;
    private LinearLayout layoutLeftMenu;
    //    private LinearLayout layoutRightMenu;
    private LinearLayout scrollViewLeftMenu;
    //    private ScrollView scrollViewRightMenu;
    private LinearLayout scrollViewMenu;
    /**
     * the activity that view attach to
     */
    private Activity activity;
    /**
     * the decorview of the activity
     */
    private ViewGroup viewDecor;
    /**
     * the viewgroup of the activity
     */
    private TouchDisableView viewActivity;
    /**
     * the flag of menu open status
     */
    private boolean isOpened;
    private GestureDetector gestureDetector;
    private float shadowAdjustScaleX;
    private float shadowAdjustScaleY;
    /**
     * the view which don't want to intercept touch event
     */
    private List<View> ignoredViews;
    private List<ResideMenuItem> leftMenuItems;
    private List<ResideMenuItem> rightMenuItems;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private OnMenuListener menuListener;
    private float lastRawX;
    private boolean isInIgnoredView = false;
    private int scaleDirection = DIRECTION_LEFT;
    private int pressedState = PRESSED_DOWN;
    private List<Integer> disabledSwipeDirection = new ArrayList<Integer>();
    // valid scale factor is between 0.0f and 1.0f.
    private float mScaleValue = 0.5f;

    //当前用户
    private RelativeLayout slidUser;
    private TextView textviewCurrentUserName = null;
    private RoundImageView imageviewCurrentUserAvatar = null;

    //退出登录
    private TextView exitloginTextView = null;

    private TextView default_tv;

    public Context mContext = null;

    String LoginOutUrl = "";//退出登入接口url

    private int number = 0;
    private UserMessageScrollView left_menu_scroll;

    public ResideMenu(Context context) {
        super(context);
        mContext = context;
        initViews(mContext);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu, this);
        scrollViewLeftMenu = (LinearLayout) findViewById(R.id.sv_left_menu);
//        scrollViewRightMenu = (ScrollView) findViewById(R.id.sv_right_menu);
        imageViewShadow = (ImageView) findViewById(R.id.iv_shadow);
        layoutLeftMenu = (LinearLayout) findViewById(R.id.layout_left_menu);
        left_menu_scroll = (UserMessageScrollView) findViewById(R.id.left_menu_scroll);
//        layoutRightMenu = (LinearLayout) findViewById(R.id.layout_right_menu);
        imageViewBackground = (ImageView) findViewById(R.id.iv_background);
        default_tv = (TextView) findViewById(R.id.default_tv);
        textviewCurrentUserName = (TextView) findViewById(R.id.textview_currentUserName);
        imageviewCurrentUserAvatar = (RoundImageView) findViewById(R.id.imageview_residemenu_avatar);

        slidUser = (RelativeLayout) findViewById(R.id.slid_currentuser);
        slidUser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				closeMenu();  //可关闭左侧菜单
//				MXAPI.getInstance(mContext).viewCurrentUser();
                BookInit.getInstance().setPeopleMessageActivity(mContext);
            }
        });


        //退出登陆
        exitloginTextView = (TextView) findViewById(R.id.exitlogin_btn);


        GradientDrawable normal = StateListDrawableUtil.getDrawable(10, getResources().getColor(R.color.ht_hred_title), 10, getResources().getColor(R.color.transparent));

        GradientDrawable press = StateListDrawableUtil.getDrawable(10, getResources().getColor(R.color.lanse), 10, getResources().getColor(R.color.transparent));

        StateListDrawable selector = StateListDrawableUtil.getSelector(normal, press);
        exitloginTextView.setBackground(selector);
        exitloginTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.showSystemDialog(mContext, getResources().getString(R.string.system_tip),
                        mContext.getResources().getString(R.string.warning_logout), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * 退出敏行成功后再退出EmpApi
                                 */
                                ConcreteLogin.getInstance().logout(mContext, new htmitech.com.componentlibrary.listener.MXKitLogoutListener() {
                                    @Override
                                    public void onLogout() {
                                        //EmpApi退出
                                        EmpApiLoginOut();
                                    }
                                });
//                                MXKit.getInstance().logout(mContext, new MXKitLogoutListener() {
//                                    @Override
//                                    public void onLogout() {
//
//                                    }
//                                });
                            }
                        }, null, true);
            }
        });

    }

    /**
     * 退出登入接口
     **/
    public void EmpApiLoginOut() {
        LoginOutUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.USER_LOGINOUT_EMPAPI;
        AnsynHttpRequest.requestByPostWithToken(mContext, null, LoginOutUrl, CHTTP.POSTWITHTOKEN, this, "LoginOut", "0");
    }

    public void updateCurrentUserAvatar(String user) {
        try {
            BookInit.getInstance().getPhotoBitMap(mContext, imageviewCurrentUserAvatar, default_tv);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textviewCurrentUserName.setText(user);
    }


    /**
     * use the method to set up the activity which residemenu need to show;
     *
     * @param activity
     */
    public void attachToActivity(Activity activity) {
        initValue(activity);
        setShadowAdjustScaleXByOrientation();
        viewDecor.addView(this, 0);
        setViewPadding();
    }

    private void initValue(Activity activity) {
        this.activity = activity;
        leftMenuItems = new ArrayList<ResideMenuItem>();
        rightMenuItems = new ArrayList<ResideMenuItem>();
        ignoredViews = new ArrayList<View>();
        viewDecor = (ViewGroup) activity.getWindow().getDecorView();
        viewActivity = new TouchDisableView(this.activity);

        View mContent = viewDecor.getChildAt(0);
        viewDecor.removeViewAt(0);
        viewActivity.setContent(mContent);
        addView(viewActivity);

        ViewGroup parent = (ViewGroup) scrollViewLeftMenu.getParent();
        parent.removeView(scrollViewLeftMenu);
//        parent.removeView(scrollViewRightMenu);
    }

    private void setShadowAdjustScaleXByOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            shadowAdjustScaleX = 0.034f;
            shadowAdjustScaleY = 0.12f;
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            shadowAdjustScaleX = 0.06f;
            shadowAdjustScaleY = 0.07f;
        }
    }

    /**
     * set the menu background picture;
     *
     * @param imageResrouce
     */
    public void setBackground(int imageResrouce) {
        imageViewBackground.setImageResource(imageResrouce);
    }

    /**
     * the visiblity of shadow under the activity view;
     *
     * @param isVisible
     */
    public void setShadowVisible(boolean isVisible) {
        if (isVisible)
            imageViewShadow.setImageResource(R.drawable.shadow);
        else
            imageViewShadow.setImageBitmap(null);
    }

    /**
     * add a single items to left menu;
     *
     * @param menuItem
     */
    @Deprecated
    public void addMenuItem(ResideMenuItem menuItem) {
        this.leftMenuItems.add(menuItem);
        layoutLeftMenu.addView(menuItem);
    }

    /**
     * add a single items;
     *
     * @param menuItem
     * @param direction
     */
    public void addMenuItem(ResideMenuItem menuItem, int direction) {
        if (direction == DIRECTION_LEFT) {
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200);
//            menuItem.setLayoutParams(params);
            this.leftMenuItems.add(menuItem);
            layoutLeftMenu.addView(menuItem);
        } else {
            this.rightMenuItems.add(menuItem);
//            layoutRightMenu.addView(menuItem);
        }
    }

    /**
     * set the menu items by array list to left menu;
     *
     * @param menuItems
     */
    @Deprecated
    public void setMenuItems(List<ResideMenuItem> menuItems) {
        this.leftMenuItems = menuItems;
        rebuildMenu();
    }

    /**
     * set the menu items by array list;
     *
     * @param menuItems
     * @param direction
     */
    public void setMenuItems(List<ResideMenuItem> menuItems, int direction) {
        if (direction == DIRECTION_LEFT)
            this.leftMenuItems = menuItems;
        else
            this.rightMenuItems = menuItems;
        rebuildMenu();
    }

    private void rebuildMenu() {
        layoutLeftMenu.removeAllViews();
//        layoutRightMenu.removeAllViews();
        for (int i = 0; i < leftMenuItems.size(); i++)
            layoutLeftMenu.addView(leftMenuItems.get(i), i);
//        for (int i = 0; i < rightMenuItems.size(); i++)
//            layoutRightMenu.addView(rightMenuItems.get(i), i);
    }

    /**
     * get the left menu items;
     *
     * @return
     */
    @Deprecated
    public List<ResideMenuItem> getMenuItems() {
        return leftMenuItems;
    }

    /**
     * get the menu items;
     *
     * @return
     */
    public List<ResideMenuItem> getMenuItems(int direction) {
        if (direction == DIRECTION_LEFT)
            return leftMenuItems;
        else
            return rightMenuItems;
    }

    /**
     * if you need to do something on the action of closing or opening menu, set
     * the listener here.
     *
     * @return
     */
    public void setMenuListener(OnMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public OnMenuListener getMenuListener() {
        return menuListener;
    }

    /**
     * we need the call the method before the menu show, because the padding of
     * activity can't get at the moment of onCreateView();
     */
    private void setViewPadding() {
        this.setPadding(viewActivity.getPaddingLeft(),
                viewActivity.getPaddingTop(), viewActivity.getPaddingRight(),
                viewActivity.getPaddingBottom());
    }

    /**
     * show the reside menu;
     */
    public void openMenu(int direction) {
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(this.getApplicationWindowToken(), 0);
        }
        setScaleDirection(direction);

        isOpened = true;
        AnimatorSet scaleDown_activity = buildScaleDownAnimation(viewActivity,
                mScaleValue, mScaleValue);
        AnimatorSet scaleDown_shadow = buildScaleDownAnimation(imageViewShadow,
                mScaleValue + shadowAdjustScaleX, mScaleValue
                        + shadowAdjustScaleY);
        AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 1.0f);
        scaleDown_shadow.addListener(animationListener);
        scaleDown_activity.playTogether(scaleDown_shadow);
        scaleDown_activity.playTogether(alpha_menu);
        scaleDown_activity.start();
    }

    /**
     * close the reslide menu;
     */
    public void closeMenu() {

        isOpened = false;
        AnimatorSet scaleUp_activity = buildScaleUpAnimation(viewActivity,
                1.0f, 1.0f);
        AnimatorSet scaleUp_shadow = buildScaleUpAnimation(imageViewShadow,
                1.0f, 1.0f);
        AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 0.0f);
        scaleUp_activity.addListener(animationListener);
        scaleUp_activity.playTogether(scaleUp_shadow);
        scaleUp_activity.playTogether(alpha_menu);
        scaleUp_activity.start();
    }

    @Deprecated
    public void setDirectionDisable(int direction) {
        disabledSwipeDirection.add(direction);
    }

    public void setSwipeDirectionDisable(int direction) {
        disabledSwipeDirection.add(direction);
    }

    private boolean isInDisableDirection(int direction) {
        return disabledSwipeDirection.contains(direction);
    }

    private void setScaleDirection(int direction) {

        int screenWidth = getScreenWidth();
        float pivotX;
        float pivotY = getScreenHeight() * 0.5f;

        if (direction == DIRECTION_LEFT) {
            scrollViewMenu = scrollViewLeftMenu;
            pivotX = screenWidth * 1.5f;
        } else {
//            scrollViewMenu = scrollViewRightMenu;
            pivotX = screenWidth * -0.5f;
        }

        ViewHelper.setPivotX(viewActivity, pivotX);
        ViewHelper.setPivotY(viewActivity, pivotY);
        ViewHelper.setPivotX(imageViewShadow, pivotX);
        ViewHelper.setPivotY(imageViewShadow, pivotY);
        scaleDirection = direction;
    }

    /**
     * return the flag of menu status;
     *
     * @return
     */
    public boolean isOpened() {
        return isOpened;
    }

    private OnClickListener viewActivityOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isOpened())
                closeMenu();
        }
    };

    private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (isOpened()) {
                showScrollViewMenu();
                if (menuListener != null)
                    menuListener.openMenu();
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // reset the view;
            if (isOpened()) {
                viewActivity.setTouchDisable(true);
                viewActivity.setOnClickListener(viewActivityOnClickListener);
            } else {
                viewActivity.setTouchDisable(false);
                viewActivity.setOnClickListener(null);
                hideScrollViewMenu();
                if (menuListener != null)
                    menuListener.closeMenu();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    /**
     * a helper method to build scale down animation;
     *
     * @param target
     * @param targetScaleX
     * @param targetScaleY
     * @return
     */
    private AnimatorSet buildScaleDownAnimation(View target,
                                                float targetScaleX, float targetScaleY) {

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                ObjectAnimator.ofFloat(target, "scaleY", targetScaleY));

        scaleDown.setInterpolator(AnimationUtils.loadInterpolator(activity,
                android.R.anim.decelerate_interpolator));
        scaleDown.setDuration(250);
        return scaleDown;
    }

    /**
     * a helper method to build scale up animation;
     *
     * @param target
     * @param targetScaleX
     * @param targetScaleY
     * @return
     */
    private AnimatorSet buildScaleUpAnimation(View target, float targetScaleX,
                                              float targetScaleY) {

        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                ObjectAnimator.ofFloat(target, "scaleY", targetScaleY));

        scaleUp.setDuration(250);
        return scaleUp;
    }

    private AnimatorSet buildMenuAnimation(View target, float alpha) {

        AnimatorSet alphaAnimation = new AnimatorSet();
        alphaAnimation.playTogether(ObjectAnimator.ofFloat(target, "alpha",
                alpha));

        alphaAnimation.setDuration(250);
        return alphaAnimation;
    }

    /**
     * if there ware some view you don't want reside menu to intercept their
     * touch event,you can use the method to set.
     *
     * @param v
     */
    public void addIgnoredView(View v) {
        ignoredViews.add(v);
    }

    /**
     * remove the view from ignored view list;
     *
     * @param v
     */
    public void removeIgnoredView(View v) {
        ignoredViews.remove(v);
    }

    /**
     * clear the ignored view list;
     */
    public void clearIgnoredViewList() {
        ignoredViews.clear();
    }

    /**
     * if the motion evnent was relative to the view which in ignored view
     * list,return true;
     *
     * @param ev
     * @return
     */
    private boolean isInIgnoredView(MotionEvent ev) {
        Rect rect = new Rect();
        for (View v : ignoredViews) {
            v.getGlobalVisibleRect(rect);
            if (rect.contains((int) ev.getX(), (int) ev.getY()))
                return true;
        }
        return false;
    }

    private void setScaleDirectionByRawX(float currentRawX) {
        if (currentRawX < lastRawX)
            setScaleDirection(DIRECTION_RIGHT);
        else
            setScaleDirection(DIRECTION_LEFT);
    }

    private float getTargetScale(float currentRawX) {
        float scaleFloatX = ((currentRawX - lastRawX) / getScreenWidth()) * 0.75f;
        scaleFloatX = scaleDirection == DIRECTION_RIGHT ? -scaleFloatX
                : scaleFloatX;

        float targetScale = ViewHelper.getScaleX(viewActivity) - scaleFloatX;
        targetScale = targetScale > 1.0f ? 1.0f : targetScale;
        targetScale = targetScale < 0.5f ? 0.5f : targetScale;
        return targetScale;
    }

    private float lastActionDownX, lastActionDownY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float currentActivityScaleX = ViewHelper.getScaleX(viewActivity);
        if (currentActivityScaleX == 1.0f)
            setScaleDirectionByRawX(ev.getRawX());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastActionDownX = ev.getX();
                lastActionDownY = ev.getY();
                isInIgnoredView = isInIgnoredView(ev) && !isOpened();
                if (lastActionDownX >= 0 && lastActionDownX <= 80) {
                    pressedState = PRESSED_DOWN;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInIgnoredView || isInDisableDirection(scaleDirection))
                    break;

                if (pressedState != PRESSED_DOWN
                        && pressedState != PRESSED_MOVE_HORIZANTAL)
                    break;

                int xOffset = (int) (ev.getX() - lastActionDownX);
                int yOffset = (int) (ev.getY() - lastActionDownY);

                if (pressedState == PRESSED_DOWN) {
                    if (yOffset > 25 || yOffset < -25) {
                        pressedState = PRESSED_MOVE_VERTICAL;
                        break;
                    }
                    if (xOffset < -50 || xOffset > 50) {
                        pressedState = PRESSED_MOVE_HORIZANTAL;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                } else if (pressedState == PRESSED_MOVE_HORIZANTAL) {
                    if (currentActivityScaleX < 0.95)
                        showScrollViewMenu();

                    float targetScale = getTargetScale(ev.getRawX());
                    ViewHelper.setScaleX(viewActivity, targetScale);
                    ViewHelper.setScaleY(viewActivity, targetScale);
                    ViewHelper.setScaleX(imageViewShadow, targetScale
                            + shadowAdjustScaleX);
                    ViewHelper.setScaleY(imageViewShadow, targetScale
                            + shadowAdjustScaleY);
                    ViewHelper.setAlpha(scrollViewMenu, (1 - targetScale) * 2.0f);

                    lastRawX = ev.getRawX();
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:

                if (isInIgnoredView)
                    break;
                if (pressedState != PRESSED_MOVE_HORIZANTAL)
                    break;

                pressedState = PRESSED_DONE;
                if (isOpened()) {
                    if (currentActivityScaleX > 0.56f)
                        closeMenu();
                    else
                        openMenu(scaleDirection);
                } else {
                    if (currentActivityScaleX < 0.94f) {
                        openMenu(scaleDirection);
                    } else {
                        closeMenu();
                    }
                }

                break;

        }
        lastRawX = ev.getRawX();
        return super.dispatchTouchEvent(ev);
    }

    public int getScreenHeight() {
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void setScaleValue(float scaleValue) {
        this.mScaleValue = scaleValue;
    }

    @Override
    public void success(String requestValue, int type, String requestName) {

        //调用此方法防止token失效，返回的是token没有失效所生成的结果
        if (requestName.equals("LoginOut")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(mContext, requestValue, type, LoginOutUrl, null, this, requestName, "");
            if (requestValue != null && !requestValue.equals("")) {
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                if (entity != null && entity.code == 200) {//EmpApi退出登入成功
                    //清除Token缓存
                    PreferenceUtils.clearToken();
                    Intent finishIntent = new Intent(mContext, ClientTabActivity.class);
                    finishIntent.setAction("finish");
                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                /*finishIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finishIntent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false);
								finishIntent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1);
								finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                    mContext.startActivity(finishIntent);
                } else {
                    Toast.makeText(mContext, "退出失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "退出失败", Toast.LENGTH_SHORT).show();
            }
        }
//        //假设里面有一个返回类型 表示是token的 还是正常请求的  Type = 1  表示的token  2 表示正常请求
//        if(type == 1){
//            if (requestValue != null && !requestValue.equals("")) {
//                RefreshTokenEntity entity = FastJsonUtils.getPerson(requestValue, RefreshTokenEntity.class);
//                if (entity.code == 200) {//刷新成功
//                    if (entity.result.accessToken != null) {
//                        PreferenceUtils.saveAccessToken(entity.result.accessToken);
//                    }
//                    if (entity.result.refreshToken != null) {
//                        PreferenceUtils.saveAccessToken(entity.result.refreshToken);
//                    }
//                    AnsynHttpRequest.requestByPostWithToken(mContext, null, LoginOutUrl, CHTTP.POSTWITHTOKEN, this);  //再次请求退出接口
//                }else{
//                    RefreshToken.RefreshAccessToken(this);  //失败继续刷新
//                    Log.e("CODE", entity.code + "=====" + entity.message);
//                }
//            }else{
//                Log.e("REFRESHTOKEN","===网络请求失败===");
//            }
//        }else if(type==2){
//            if (requestValue != null && !requestValue.equals("")) {
//                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
//                if (entity.code == 200) {//EmpApi退出登入成功
//                    //清除Token缓存
//                    PreferenceUtils.clearToken();
//                    Intent finishIntent = new Intent(mContext, ClientTabActivity.class);
//                    finishIntent.setAction("finish");
//                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                /*finishIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                finishIntent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false);
//								finishIntent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1);
//								finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
//                    mContext.startActivity(finishIntent);
//                } else if(entity.code == 900) {
//                    RefreshToken.RefreshAccessToken(this);
//                }else{
//                    Toast.makeText(mContext, "退出失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }}
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("LoginOut")) {
            if (exceptionMessage.contains("超时") || exceptionMessage.contains("TIMEOUT")) {
                Toast.makeText(mContext, "请求超时", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void notNetwork() {
        Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public interface OnMenuListener {

        /**
         * the method will call on the finished time of opening menu's
         * animation.
         */
        public void openMenu();

        /**
         * the method will call on the finished time of closing menu's animation
         * .
         */
        public void closeMenu();
    }

    private void showScrollViewMenu() {
        if (scrollViewMenu != null && scrollViewMenu.getParent() == null) {
            addView(scrollViewMenu);
        }
    }

    private void hideScrollViewMenu() {
        if (scrollViewMenu != null && scrollViewMenu.getParent() != null) {
            removeView(scrollViewMenu);
        }
    }

    /**
     * 设置头像是否展示
     */
    public void setSlidUser(int isVisibility) {
        slidUser.setVisibility(isVisibility);
    }


    public void setNumber(int number) {
        this.number = number;
        int screenHeight = getScreenHeight();
        int itemHeight = DensityUtil.dip2px(mContext, 41);
        int top_buttom = itemHeight + DensityUtil.dip2px(mContext, 139);
        if ((number * itemHeight + top_buttom) > screenHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, screenHeight - top_buttom);
            left_menu_scroll.setLayoutParams(params);
        }
    }
}
