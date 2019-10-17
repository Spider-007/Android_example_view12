package com.htmitech.proxy.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.downmanage.DownTaskHandler;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.util.GlideUtil;
import com.minxing.client.util.FastJsonUtils;

import java.io.File;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by heyang on 2017-9-19.
 */

public class AdvertPopWindow extends PopupWindow implements ObserverCallBackType {


    public View mMenuView;
    private RelativeLayout rlBackground;
    private TextView tvTime;
    private TextView tvTitle;
    private TextView tvContent;
    private Button btDetail;
    private ImageView ivClose;
    private ImageView ivHolePic;
    private ImageView ivPopWindow;
    private TextView tvHoleTime;
    private RelativeLayout rlHoleParent;
    private Context context;
    private int time = 15;
    private boolean isJump;
    private boolean isClose;
    private String picID;
    private String picUrl;
    private int showStyle;
    private String title;
    private String content;
    private String app_id;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private AppliationCenterDao mAppliationCenterDao;
    private int jumpStyle;
    private String picGetUrl;
    private View view;
    private PicRequestBean bean;
    private String currentAppId;
    private boolean flag = false;

    public AdvertPopWindow(Context context, Map<String, Object> map, View view) {

        super(context);
        this.context = context;
        this.view = view;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.advert_popwindow, null);
        picGetUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_ADV_PIC;
        initView();
        initData(map);
        initControl();
        if (showStyle == 0) {
            rlBackground.setVisibility(View.VISIBLE);
            rlHoleParent.setVisibility(View.GONE);
            ivClose.setVisibility(View.VISIBLE);
            mMenuView.setBackgroundColor(Color.TRANSPARENT);
            //这一期先不做
            tvTitle.setText(title);
            tvContent.setText(content);
        } else {
            rlBackground.setVisibility(View.GONE);
            rlHoleParent.setVisibility(View.VISIBLE);
            mMenuView.setBackgroundColor(Color.WHITE);
            ivClose.setVisibility(View.GONE);
        }
//		UserHasChooseAdapter mUserHasChooseAdapter = new UserHasChooseAdapter(context,userList,departmentsList);
//		lv_choose.setAdapter(mUserHasChooseAdapter);
        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(com.htmitech.addressbook.R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x90000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    public void initView() {
        rlBackground = (RelativeLayout) mMenuView.findViewById(R.id.rl_advert_pop_background);
        ivPopWindow = (ImageView) mMenuView.findViewById(R.id.iv_advert_pop_window);
        tvTime = (TextView) mMenuView.findViewById(R.id.tv_advert_pop_time);
        tvTitle = (TextView) mMenuView.findViewById(R.id.tv_advert_pop_title);
        tvContent = (TextView) mMenuView.findViewById(R.id.tv_advert_pop_content);
        btDetail = (Button) mMenuView.findViewById(R.id.bt_advert_pop_detail);
        ivClose = (ImageView) mMenuView.findViewById(R.id.iv_advert_pop_close);
        ivHolePic = (ImageView) mMenuView.findViewById(R.id.iv_advert);
        tvHoleTime = (TextView) mMenuView.findViewById(R.id.tv_advert_time);
        rlHoleParent = (RelativeLayout) mMenuView.findViewById(R.id.rl_advert);
    }

    public void initData(Map<String, Object> map) {
        title = (String) map.get("com_app_advertise_move_title");
        content = (String) map.get("com_app_advertise_move_content");
        if (map.get("com_app_advertise_move_jump_second") != null)
            time = Integer.parseInt(map.get("com_app_advertise_move_jump_second").toString());
        if (map.get("com_app_advertise_move_dialog") != null)
            showStyle = Integer.parseInt(map.get("com_app_advertise_move_dialog").toString());
        if (map.get("com_app_advertise_move_jump_type") != null) {
            jumpStyle = Integer.parseInt(map.get("com_app_advertise_move_jump_type").toString());
        }
//        app_id = jumpStyle == 0 ? map.get("com_app_advertise_move_jump_url").toString() : map.get("com_app_advertise_move_jump_app").toString();
        if (jumpStyle == 0 && map.get("com_app_advertise_move_jump_url") != null) {
            app_id = map.get("com_app_advertise_move_jump_url").toString();
        } else if (map.get("com_app_advertise_move_jump_app") != null) {
            app_id = map.get("com_app_advertise_move_jump_app").toString();
        }
        if (app_id != null && !"".equals(app_id)) {
            isJump = true;
        } else {
            isJump = false;
        }
        if (map.get("com_app_advertise_move_jump") != null)
            isClose = Integer.parseInt(map.get("com_app_advertise_move_jump").toString()) == 1 ? true : false;
        if (isClose) {
            tvHoleTime.setText(time + "s 跳过");
        } else {
            tvHoleTime.setText(time + "s");
        }
        tvTime.setText(time + "s");
        currentAppId = (String) map.get("app_id");
        picID = (String) map.get("com_app_advertise_move_pic_default");
        bean = new PicRequestBean();
        bean.pictureId = picID;
        AnsynHttpRequest.requestByPostWithToken(context, bean, picGetUrl, CHTTP.POSTWITHTOKEN, this, "getPicUrl", "");
    }

    public void initControl() {
        //点击全屏图片跳转
        if (isJump) {
            ivHolePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToDetail(app_id);
                }
            });
            btDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToDetail(app_id);
                }
            });
        }
        //如果是可以跳过  那么全屏时候点击秒数按钮可以跳过
        if (isClose)
            tvHoleTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdvertPopWindow.this.dismiss();
                }
            });
        //如果可以跳过那么悬浮框跳过按钮
        if (isClose)
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdvertPopWindow.this.dismiss();
                }
            });
    }


    public void show(View view) {
//        this.showAsDropDown(view);
        showSomething();
        try {
            if (this.isShowing()) {
                this.dismiss();
            }
            this.showAtLocation(view, Gravity.CENTER, 0, 0);
            timeOut();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showSomething() {
        if (isClose && showStyle == 0) {
            ivClose.setVisibility(View.VISIBLE);
        } else {
            ivClose.setVisibility(View.GONE);
        }
        if (isJump) {
            btDetail.setVisibility(View.VISIBLE);
        } else {
            btDetail.setVisibility(View.GONE);
        }
    }

    //倒数之后关闭窗口
    public void timeOut() {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int time = msg.arg1;
                if (showStyle == 0) {
                    tvTime.setText(time + "s");
                } else if (showStyle == 1 && isClose) {
                    tvHoleTime.setText(time + "s 跳过");
                } else if (showStyle == 1 && !isClose) {
                    tvHoleTime.setText(time + "s");
                }
                if (time == 0) {
                    AdvertPopWindow.this.dismiss();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time > 0) {
                    try {
                        Thread.sleep(1000);
                        time--;
                        Message message = Message.obtain();
                        message.arg1 = time;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void jumpToDetail(String currentAppId) {
        if (jumpStyle == 0) {
            BookInit.getInstance().activityWebView(context, currentAppId);
        } else {
            mProxyDealApplication = new ProxyDealApplicationPlugin(context);
            mAppliationCenterDao = new AppliationCenterDao(context);
            if (!TextUtils.isEmpty(currentAppId)) {
                AppInfo appInfos = mAppliationCenterDao.getAppInfo(currentAppId);
                try {
                    int success = mProxyDealApplication.applicationCenterProxy(appInfos);
                    switch (success) {
                        case 1: //强制升级以及下载

                        case 2://可暂时不升级
                            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(context);
                            mClentAppUnit.setActivity(ApplicationAllEnum.ZYYYZX);
                            break;
                    }

                } catch (NotApplicationException e) {
                    e.printStackTrace();
                }
            }
        }
        AdvertPopWindow.this.dismiss();
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("getPicUrl")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context,requestValue, type, picGetUrl, bean, this, requestName, "0");
            if (requestValue != null) {
                picResultBean resultBean = FastJsonUtils.getPerson(requestValue, picResultBean.class);
                if (resultBean.code == 200) {
                    picUrl = resultBean.result.picPath;
//                    picUrl = "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=6ae262a5b11c8701c2bbbab44616f54a/63d0f703918fa0ec4c6bb84c249759ee3c6ddbc7.jpg";
//                    picUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506078253122&di=da3bf523f807e2636d7b5ada0d4eb0ed&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F20%2F20150520124828_HGdUY.gif";
                }
            }
//            String fileName = "adv" + picID + ".png";
            if (TextUtils.isEmpty(picUrl)) {
                return;
            }
            String fileName = picUrl.substring(picUrl.lastIndexOf("/") + 1);
            String dirPath = CommonSettings.DEFAULT_CACHE_IMAGE_FOLDER + File.separator + currentAppId;
            File file = new File(dirPath);
            if (!file.exists()) {
                file.mkdir();
            }
            final String filePath = dirPath + File.separator + fileName;
            if (new File(filePath).exists()) {
                setAdvPic(filePath);
                return;
            }
            AnsynHttpRequest.downLoadTask(context, 0, picUrl, filePath, new DownTaskHandler() {
                @Override
                public void startDown() {

                }

                @Override
                public void onSuccess(int postion) {
                    setAdvPic(filePath);
                }

                @Override
                public void downProgress(int progress, float totalLength) {

                }

                @Override
                public void onFail(String failMessage) {

                }

                @Override
                public void notNetwork() {

                }
            });

        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public void setAdvPic(String picUrl) {
        if (picUrl.endsWith("gif")) {
            GlideUtil.loadGildGif(HtmitechApplication.instance(), picUrl, R.drawable.img_advertising_loading, R.drawable.img_advertising_loading, ivHolePic);
            GlideUtil.loadGildGif(HtmitechApplication.instance(), picUrl, R.drawable.img_advertising_loading, R.drawable.img_advertising_loading, ivPopWindow);
        } else {
            GlideUtil.loadGild(HtmitechApplication.instance(), picUrl, R.drawable.img_advertising_loading, R.drawable.img_advertising_loading, ivHolePic);
            GlideUtil.loadGild(HtmitechApplication.instance(), picUrl, R.drawable.img_advertising_loading, R.drawable.img_advertising_loading, ivPopWindow);
        }
//            rlBackground.setBackground(Drawable.createFromStream(new URL(picUrl).openStream(), null));
        show(view);
    }

}

class PicRequestBean {
    public String pictureId;
}

class picResultBean {
    public int code;

    public String message;

    public String debugMsg;

    public picResult result;
}

class picResult {
    public String pictureId;

    public String groupCorpId;

    public String pictureClassCode;

    public String pictureCode;

    public String filename;

    public String extname;

    public String picPath;

    public String sourcename;

    public String pictureDesc;

    public String filesize;

    public String width;

    public String height;

    public String pictureDate;

    public int statusFlag;

    public String createBy;

    public String createTime;

    public String updateBy;

    public String updateTime;
}
