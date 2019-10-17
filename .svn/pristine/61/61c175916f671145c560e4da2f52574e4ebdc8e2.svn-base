package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.dialog.ActionSheetDialog;
import com.htmitech.ztcustom.zt.domain.DamageSummar;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.Dicttype;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.ParameterList;
import com.htmitech.ztcustom.zt.domain.damage.DamageSummarResult;
import com.htmitech.ztcustom.zt.domain.damage.Table1;
import com.htmitech.ztcustom.zt.domain.damage.Users;
import com.htmitech.ztcustom.zt.enums.DicttypeEnum;
import com.htmitech.ztcustom.zt.enums.TransType;
import com.htmitech.ztcustom.zt.fragment.DamageSummaryFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDataListener;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.DamageUtil;
import com.htmitech.ztcustom.zt.util.DataTimeUtil;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;
import com.htmitech.ztcustom.zt.view.CircleWaveView;

import net.simonvt.datepicker.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 损伤汇总
 *
 * @author Tony
 *         DamageSummaryActivity
 */
public class DamageSummaryActivity extends BaseFragmentActivity implements
        CircleWaveView.RoundOnClick, View.OnClickListener, CallBackDataListener {
    private CircleWaveView mCircleWaveView;
    private Animation upAnimation, downAnimation;
    private LinearLayout ll_Popup;
    private ImageView function;
    private FunctionPopupWindow menuWindow;
    private TextView tvDateTitle;
    private LinearLayout layout_iv_up, layout_iv_down;
    private View CurrentView;
    private String dateTime;
    private String startDate;
    private String endDate;
    private ImageView back;
    public TextView tv_fn5_title_name;
    private ImageView iv_date_left, iv_date_right;
    public int year, monthOfYear, dayOfMonth;
    public int startMonth = 0;
    public Calendar mCalendar;
    public DatePicker mDatePicker;
    private Fragment fragment_summary;
    private boolean isShare = false;
    private String appUrl;
    private String stat_day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentView = LayoutInflater.from(this).inflate(
                R.layout.zt_activity_damagesummar, null);
        setContentView(CurrentView);
        Intent intent = getIntent();
        isShare = intent.getBooleanExtra("flag_share", false);
        appUrl = intent.getStringExtra("share");
        dateTime = intent.getStringExtra("dateTime");
        mCircleWaveView = (CircleWaveView) findViewById(R.id.circle_line);
        ll_Popup = (LinearLayout) findViewById(R.id.ll_popupLayout);
        function = (ImageView) findViewById(R.id.function);
        tvDateTitle = (TextView) findViewById(R.id.tv_title_data);
        layout_iv_up = (LinearLayout) findViewById(R.id.layout_iv_up);
        layout_iv_down = (LinearLayout) findViewById(R.id.layout_iv_down);
        back = (ImageView) findViewById(R.id.ibn_fn5_back);
        tv_fn5_title_name = (TextView) findViewById(R.id.tv_fn5_title_name);
        iv_date_left = (ImageView) findViewById(R.id.iv_date_left);
        iv_date_right = (ImageView) findViewById(R.id.iv_date_right);
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mCircleWaveView.setRadius(50);
        mCircleWaveView.setRoundOnClick(this);
        mCircleWaveView.setNum(0);
        layout_iv_up.setOnClickListener(this);
        function.setOnClickListener(this);
        layout_iv_down.setOnClickListener(this);
        iv_date_left.setOnClickListener(this);
        iv_date_right.setOnClickListener(this);
        ll_Popup.getBackground().setAlpha(220);
        initData();
    }

    public void initData() {
        if (isShare) {
            function.setVisibility(View.GONE);
            layout_iv_up.setVisibility(View.GONE);
            iv_date_left.setEnabled(false);
            iv_date_right.setEnabled(false);
        }
        mDatePicker.basisMouth();
        back.setOnClickListener(this);
        menuWindow = new FunctionPopupWindow(this, this);
        upAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_enter);
        iv_date_left.setVisibility(View.VISIBLE);
        iv_date_right.setVisibility(View.VISIBLE);
        mCircleWaveView.setName("设备伤损总数");
        tv_fn5_title_name.setText("设备伤损情况汇总");
        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_up.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_down.setVisibility(View.VISIBLE);
            }
        });

        downAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_exit);

        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_down.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                layout_iv_up.setVisibility(View.VISIBLE);
            }
        });
        if (isShare) {
            tvDateTitle.setText("" + dateTime);
            String[] timeList = dateTime.split("-");
            if (timeList.length == 1 && dateTime.length() > 0) {
                if (dateTime.length() <= 5) {
                    onClickChild(Integer.parseInt(dateTime.substring(0, 4)), 0, 0, mType.YEAR);
                } else {
                    onClickChild(Integer.parseInt(dateTime.substring(0, 4)), Integer.parseInt(dateTime.substring(5, 7)), 0, mType.MONTH);
                }

            } else {
                dateTime = timeList[0];
                onClickChild(Integer.parseInt(dateTime.substring(0, 4)), Integer.parseInt(dateTime.substring(5, 7)), Integer.parseInt(dateTime.substring(8, 10)), mType.DATA);

            }
            requestData();
        } else {
            mCalendar = Calendar.getInstance();
            year = mCalendar.get(Calendar.YEAR);
            monthOfYear = mCalendar.get(Calendar.MONTH);
            dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            stat_day = ZTCustomInit.get().getmCache().getmGetStatParam().stat_day;
            if (stat_day != null && !stat_day.equals("") && Integer.parseInt(stat_day) > 14 && dayOfMonth > Integer.parseInt(stat_day)) {
                startMonth = 1;
                if ((monthOfYear + 1) == 12) {
                    year += 1;
                    startMonth = 0;
                    monthOfYear = startMonth;
                } else {
                    monthOfYear = monthOfYear + startMonth;
                }
//                mCalendar.add(Calendar.MONTH, 1);
//
//                year = mCalendar.get(Calendar.YEAR);
//
//                monthOfYear = mCalendar.get(Calendar.MONTH);
//
//                dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
//                mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
            }
            tvDateTitle.setText("" + year + "年" + (monthOfYear + 1) + "月");
            monthOfYear = monthOfYear + 1;
        }
        mType = Type.MONTH;
        CurrentView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (menuWindow.isShowing()) {
                    function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                    menuWindow.dismiss();
                }
                return false;
            }
        });
        FragmentManager manager = this.getSupportFragmentManager();
        // 2.根据FragmentManager对象的findFragmentById方法来获取指定的fragment
        DamageSummaryFragment fragment2 = (DamageSummaryFragment) manager.findFragmentById(R.id.fragment_summary);
        fragment2.startMonth = startMonth;
        mGestureDetectorUp = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                        // // System.out.println("水平方向移动距离过大");
                        // return true;
                        // }
                        if (Math.abs(velocityY) < 100) {
                            // System.out.println("手指移动的太慢了");
                            return true;
                        }

                        // 手势向下 down
                        if ((e2.getRawY() - e1.getRawY()) > 100) {
                            mCircleWaveView.setFocusable(false);
                            ll_Popup.setVisibility(View.VISIBLE); // 显示布局
                            ll_Popup.startAnimation(upAnimation);
                            return true;
                        }
                        // 手势向上 up
                        if ((e1.getRawY() - e2.getRawY()) > 100) {
                            return true;
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        mGestureDetectorDown = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                        // // System.out.println("水平方向移动距离过大");
                        // return true;
                        // }
                        if (Math.abs(velocityY) < 100) {
                            // System.out.println("手指移动的太慢了");
                            return true;
                        }

                        // 手势向下 down
                        if ((e2.getRawY() - e1.getRawY()) > 100) {
                            return true;
                        }
                        // 手势向上 up
                        if ((e1.getRawY() - e2.getRawY()) > 100) {
                            layoutDown();
                            return true;
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
        layout_iv_down.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetectorDown.onTouchEvent(event);
                return false;
            }
        });

        layout_iv_up.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                mGestureDetectorUp.onTouchEvent(event);
                return false;
            }
        });

        mDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (e2.getRawX() - e1.getRawX() > 200) {
                            // 右滑
                            finish();
                        }
                        // if (e1.getRawX() - e2.getRawX() > 200 &&
                        // M_SIDE_BACK_STATE) {
                        // //左滑
                        // }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    GestureDetector mDetector;

    GestureDetector mGestureDetectorUp, mGestureDetectorDown;
    StringBuffer share = new StringBuffer();

    public DamageSummar updateDate() {
        DamageSummar mDamageSummar = new DamageSummar();
        ArrayList<ParameterList> array = ZTCustomInit.get().getmCache()
                .getmReportParametersCate().getResult();
        DamageUtil.damageUtil(share, isShare, array, DicttypeEnum.getCodes(), startDate, "TZ",
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId, endDate,
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId, appUrl);
        //ZTCustomInit.get().getmCache().getmListDetails().AccountId
//		if (!isShare) {
//			for (ParameterList mParameterList : array) {
//				mParameterList.getValues().clear();
//				if (mParameterList.getName().equals("sumitem_list")) {
//					mParameterList.getValues().addAll(
//							ZTUnit.getSumitemValue(DicttypeEnum.getCodes()));
//				} else if (mParameterList.getName().equals("datebegin")) {
//					mParameterList.getValues().add(startDate);
//				} else if (mParameterList.getName().equals("sumclass")) {
//					mParameterList.getValues().add("TZ");
//				} else if (mParameterList.getName().equals("orgid")) {
//					mParameterList.getValues().add(
//							app.getmCache().getmListDetails().OrgId);
//				} else if (mParameterList.getName().equals("dateend")) {
//					mParameterList.getValues().add(endDate);
//				} else if (mParameterList.getName().equals("userid")) {
//					mParameterList.getValues().add(
//							app.getmCache().getmListDetails().AccountId);
//				}
//			}
//		}else{
//			for (ParameterList mParameterList : array) {
//				mParameterList.getValues().clear();
//				if (mParameterList.getName().equals("sumitem_list")) {
//					mParameterList.getValues().addAll(
//							ZTUnit.getSumitemValue(DicttypeEnum.getCodes()));
//				} else if (mParameterList.getName().equals("datebegin")) {
//					mParameterList.getValues().add(startDate);
//				} else if (mParameterList.getName().equals("sumclass")) {
//					mParameterList.getValues().add("TZ");
//				} else if (mParameterList.getName().equals("orgid")) {
//					mParameterList.getValues().add(
//							app.getmCache().getmListDetails().OrgId);
//				} else if (mParameterList.getName().equals("dateend")) {
//					mParameterList.getValues().add(endDate);
//				} else if (mParameterList.getName().equals("userid")) {
//					mParameterList.getValues().add(
//							app.getmCache().getmListDetails().AccountId);
//				}
//			}
//		}
//		
        share.append(dateTime + "|");
        mDamageSummar.setParameterList(array);
        mDamageSummar.setReportGuid(CHTTP.REPORTGUIDALL);
        return mDamageSummar;
    }

    @Override
    public void onClick() {
        if (!isShare && !ll_Popup.isShown()) {
            new ActionSheetDialog(DamageSummaryActivity.this)
                    .builder()
                    .setTitle("请选择维度")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .addSheetItem(DicttypeEnum.getNames())
                    .setOnSheetItemClickListener(
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(final String code) {
                                    if (code == null) {
                                        Toast.makeText(
                                                DamageSummaryActivity.this,
                                                "请选择维度!", Toast.LENGTH_SHORT)
                                                .show();
                                        return;
                                    }
                                    showProgressDialog(DamageSummaryActivity.this);
                                    Object object;
                                    String url;
                                    if (code.equals("SSLX")) {
                                        //ZTCustomInit.get().getmCache().getmListDetails().AccountId
                                        object = new Users(ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId);
                                        url = CHTTP.IB05_GETSSLXLIST;
                                    } else {
                                        object = new Dicttype(code);
                                        url = CHTTP.IB04_GETDICTTYPEOBJECT;
                                    }
                                    AnsynHttpRequest.request(
                                            DamageSummaryActivity.this, object,
                                            url, CHTTP.POST,
                                            new ObserverCallBack() {
                                                @Override
                                                public void success(
                                                        String successMessage) {
                                                    if (code.equals("SSLX")) {
                                                        ArrayList<Dictitemlist> dictitemlist = (ArrayList<Dictitemlist>) JSON
                                                                .parseArray(
                                                                        successMessage,
                                                                        Dictitemlist.class);
                                                        DicttypeResult mDicttypeResult = new DicttypeResult();
                                                        mDicttypeResult.class_code = code;
                                                        mDicttypeResult.dictitemlist = dictitemlist;
                                                        Map<String, Object> params = new HashMap<String, Object>();
                                                        params.put(
                                                                "DicttypeResult",
                                                                mDicttypeResult);
                                                        params.put("dateTime",
                                                                dateTime);
                                                        params.put("startTime",
                                                                startDate);
                                                        params.put("endTime",
                                                                endDate);
                                                        params.put("code", code);
                                                        ZTActivityUnit
                                                                .switchTo(
                                                                        DamageSummaryActivity.this,
                                                                        DamageTypeActivity.class,
                                                                        params);
                                                        ZTCustomInit.get().getmCache()
                                                                .getmDicttypeResult()
                                                                .put(code,
                                                                        mDicttypeResult);

                                                    } else {
                                                        DicttypeResult mDicttypeResult = JSON
                                                                .parseObject(
                                                                        successMessage,
                                                                        DicttypeResult.class);
                                                        Map<String, Object> params = new HashMap<String, Object>();
                                                        params.put(
                                                                "DicttypeResult",
                                                                mDicttypeResult);
                                                        params.put("dateTime",
                                                                dateTime);
                                                        params.put("startTime",
                                                                startDate);
                                                        params.put("endTime",
                                                                endDate);
                                                        params.put("code", code);
                                                        ZTActivityUnit
                                                                .switchTo(
                                                                        DamageSummaryActivity.this,
                                                                        DamageTypeActivity.class,
                                                                        params);
                                                        ZTCustomInit.get().getmCache()
                                                                .getmDicttypeResult()
                                                                .put(code,
                                                                        mDicttypeResult);
                                                    }
                                                    dimssDialog();
                                                }

                                                @Override
                                                public void notNetwork() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    dimssDialog();
                                                }

                                                @Override
                                                public void fail(
                                                        String exceptionMessage) {
                                                    // TODO Auto-generated
                                                    // method stub

                                                }
                                            });

                                }
                            }).show();
        }
    }

    /**
     *
     */
    public void layoutUp() {
        mCircleWaveView.setFocusable(false);
        ll_Popup.setVisibility(View.VISIBLE); // 显示布局
        ll_Popup.startAnimation(upAnimation);
    }

    /**
     *
     */
    public void layoutDown() {
        mCircleWaveView.setFocusable(true);
        ll_Popup.setVisibility(View.GONE); // 取出布局
        ll_Popup.startAnimation(downAnimation); // 开始退出动画
        showProgressDialog(DamageSummaryActivity.this);
        AnsynHttpRequest.request(this, updateDate(),
                CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DamageSummarResult mDamageSummarResult = JSON
                                .parseObject(successMessage,
                                        DamageSummarResult.class);
                        ArrayList<Table1> arr = mDamageSummarResult.Result.ListDataSet.Table1;
                        mCircleWaveView.setAllValue(arr.get(arr.size() - 1).value
                                + "");
                        ZTCustomInit.get().getmCache().setmDamageSummarResult(
                                mDamageSummarResult);
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() ==R.id.layout_iv_up ){
            layoutUp();
        }else if(view.getId() ==R.id.layout_iv_down){
            layoutDown();
        }else if(view.getId() ==R.id.function){
            if (!menuWindow.isShowing()) {
                menuWindow = new FunctionPopupWindow(this, this);
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi);
                int popupWidth = menuWindow.mMenuView.getMeasuredWidth();
                int popupHeight = menuWindow.mMenuView.getMeasuredHeight();

                int[] location = new int[2];
                function.getLocationOnScreen(location);
                // 显示窗口
                menuWindow.showAtLocation(function, Gravity.NO_GRAVITY,
                        (location[0] + function.getWidth() / 2) - popupWidth
                                / 2, location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
                menuWindow.update();
            } else {
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                menuWindow.dismiss();
            }
        }else if(view.getId() ==R.id.iv_fun_detail_data){
            if (menuWindow.isShowing()) {
                function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
                menuWindow.dismiss();
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("dateTime", dateTime);
            params.put("type", TransType.DAMAGE);
            params.put("startTime", startDate);
            params.put("endTime", endDate);
            ZTActivityUnit.switchTo(DamageSummaryActivity.this,
                    DamageDetailDataActivity.class, params);
        }else if(view.getId() ==R.id.iv_share){
            String shareString = share.toString();
            shareString = shareString.substring(0, shareString.length() - 1);
            ShareUnit
                    .ShareListener(
                            this,
                            "分享" + tv_fn5_title_name.getText().toString(),
                            "http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
                            shareString, "UU");
        }else if(view.getId() ==R.id.iv_date_left){
            mCalendar.set(year, monthOfYear - 1, dayOfMonth);// 月份是从0开始的，所以11表示12月

            switch (mType) {
                case YEAR:
                    mCalendar.add(Calendar.YEAR, -1); // 月份减1
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
                case MONTH:
                    mCalendar.add(Calendar.MONTH, -1); // 月份减1
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
                case DATA:
                    mCalendar.add(Calendar.DATE, -1); // 月份减1
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
            }

            onClickChild(year, monthOfYear, dayOfMonth, mType);
            mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
            requestData();

        }else if(view.getId() ==R.id.iv_date_right){
            mCalendar.set(year, monthOfYear - 1, dayOfMonth);// 月份是从0开始的，所以11表示12月

            switch (mType) {
                case YEAR:
                    mCalendar.add(Calendar.YEAR, 1);
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
                case MONTH:
                    mCalendar.add(Calendar.MONTH, 1);
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
                case DATA:
                    mCalendar.add(Calendar.DATE, 1);
                    year = mCalendar.get(Calendar.YEAR);
                    monthOfYear = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
                    break;
            }
            mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
            onClickChild(year, monthOfYear, dayOfMonth, mType);
            requestData();
        }else if(view.getId() ==R.id.ibn_fn5_back){
            this.finish();
        }
    }

    Type mType;

    @Override
    public void onClickChild(int year, int monthOfYear, int dayOfMonth,
                             Type mType) {
        // TODO Auto-generated method stub
        this.mType = mType;
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        switch (mType) {
            case YEAR:
                dateTime = "" + year + "年";
                tvDateTitle.setText(dateTime);
                startDate = "" + year + "-1" + "-1";
                endDate = "" + year + "-12" + "-31";
                break;
            case MONTH:
                if (monthOfYear < 10) {
                    dateTime = "" + year + "年0" + monthOfYear + "月";
                } else {
                    dateTime = "" + year + "年" + monthOfYear + "月";
                }

                tvDateTitle.setText(dateTime);
                startDate = "" + year + "-" + monthOfYear + "-1";
                Calendar ca = Calendar.getInstance();
                ca.set(year, monthOfYear - 1, dayOfMonth);
                ca.set(Calendar.DAY_OF_MONTH,
                        ca.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = sdf.format(ca.getTime());
                break;
            case DATA:
                convertWeekByDate(year, monthOfYear, dayOfMonth);
                break;
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式

    private void convertWeekByDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear - 1, dayOfMonth);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        String dateEnd = imptimeEnd.split("-")[2];
        String[] dateStart = imptimeBegin.split("-");
        dateTime = dateStart[0] + "年" + dateStart[1] + "月" + dateStart[2] + "日"
                + "-" + (dateEnd) + "日";
        tvDateTitle.setText(dateTime);
        startDate = imptimeBegin;
        endDate = imptimeEnd;
    }

    public int currentCheckDate;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mCircleWaveView.stop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    public void onClickChildOne(int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        stat_day = ZTCustomInit.get().getmCache().getmGetStatParam().stat_day;
        if (stat_day != null && !stat_day.equals("") && Integer.parseInt(stat_day) > 14 && dayOfMonth > Integer.parseInt(stat_day)) {
            startMonth = 1;
            if ((monthOfYear) == 12) {
                year += 1;
                monthOfYear = startMonth;
            }

        }
//        mDatePicker.updateDate(year, monthOfYear, dayOfMonth);

        if (monthOfYear < 10) {
            dateTime = "" + year + "年0" + monthOfYear + "月";
        } else {
            dateTime = "" + year + "年" + monthOfYear + "月";
        }
//        if((monthOfYear - 1) <= 0){
//            startDate = "" + (year - 1) + "-" + 12 + "-" + (Integer.parseInt(stat_day)+ 1);
//        }else{
//            startDate = "" + year + "-" + (monthOfYear - 1) + "-" + (Integer.parseInt(stat_day)+ 1);
//        }
//
//        Calendar ca = Calendar.getInstance();
//        ca.set(year, monthOfYear - 1, dayOfMonth);
//        ca.set(Calendar.DAY_OF_MONTH,
//                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
//        endDate = "" + year + "-" + (monthOfYear) + "-" + stat_day;

        String[] dataTime = DataTimeUtil.getTime(stat_day);
        startDate = dataTime[0];
        endDate = dataTime[1];
        if (!isShare) {
            tvDateTitle.setText(dateTime);
            requestData();
        }
    }


    public void requestData() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, updateDate(),
                CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        try {
                            DamageSummarResult mDamageSummarResult = JSON.parseObject(successMessage, DamageSummarResult.class);
                            ArrayList<Table1> arr = mDamageSummarResult.Result.ListDataSet.Table1;
                            if (arr.size() == 0) {
                                return;
                            }
                            mCircleWaveView.setAllValue(arr.get(arr.size() - 1).value
                                    + "");
                            ZTCustomInit.get().getmCache().setmDamageSummarResult(
                                    mDamageSummarResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            dimssDialog();
                        }
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }
                });
    }

    @Override
    public void otherOnClick(Table1 mTable1) {
        // TODO Auto-generated method stub
        Log.d("otherOnClick", "mTable1 == " + mTable1.name + mTable1.key);
    }
}
