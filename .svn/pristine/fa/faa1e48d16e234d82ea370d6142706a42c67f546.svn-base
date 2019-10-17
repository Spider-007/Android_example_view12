package com.htmitech.emportal.ui.homepage.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.homepage.PortalSwitchAdapter;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.ConfigStyleUtil;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;

import java.util.ArrayList;

/**
 * Created by yanxin on 2017-8-22.
 * 用于门户选择
 */
public class PortalSwitchPopwindow extends PopupWindow {
    public Context context;
    private final View contentView;
    private final ListView lvSwitch;
    private PortalSwitchAdapter mPortalSwitchAdapter;
    private AppliationCenterDao mAppliationCenterDao;
    private ArrayList<EmpPortal> portalAllList;

    public PortalSwitchPopwindow(Context context) {
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.portal_switch_popwindow, null);
        TextView tvBackground = (TextView) contentView.findViewById(R.id.tv_background);
        tvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setContentView(contentView);
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        //窗口高度
        int screenHeight = dm.heightPixels;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setHeight(screenHeight - DensityUtil.dip2px(context,70));
        this.setHeight(screenHeight - DensityUtil.dip2px(context, 70));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
//        this.setBackgroundDrawable(new BitmapDrawable());

        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = contentView.findViewById(R.id.ll_parent).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) PortalSwitchPopwindow.this.context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) PortalSwitchPopwindow.this.context).getWindow().setAttributes(lp);
            }
        });


        lvSwitch = (ListView) contentView.findViewById(R.id.lv_portal);
        lvSwitch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmpPortal newEmpPortal = null;
                TextView tvSelectFlag;
                TextView tvTitle;

                if (null != portalAllList && portalAllList.size() > 0) {
                    newEmpPortal = portalAllList.get(position);
                }
                boolean isTrue = changePortal(newEmpPortal);
                if(!isTrue) return;
                for (int i = 0; i < parent.getCount(); i++) {
                    View childView = lvSwitch.getChildAt(i);
                    tvSelectFlag = (TextView) childView.findViewById(R.id.tv_select_flag);
                    tvTitle = (TextView) childView.findViewById(R.id.tv_name);
                    tvSelectFlag.setSelected(false);
                    tvTitle.setSelected(false);
                }
                tvSelectFlag = (TextView) view.findViewById(R.id.tv_select_flag);
                tvTitle = (TextView) view.findViewById(R.id.tv_name);
                tvSelectFlag.setSelected(true);
                tvTitle.setSelected(true);

            }
        });
    }

    /*
    * 切换门户修改数据库操作
    * */
    private boolean changePortal(EmpPortal newEmpPortal) {
        if (null == newEmpPortal) {
            return false;
        }
        for (EmpPortal mEmpPortal : portalAllList) {
            mEmpPortal.isCheck = false;
            mAppliationCenterDao.switchPortal(mEmpPortal);
        }
        mAppliationCenterDao.switchPortal(newEmpPortal);
        if (newEmpPortal.mApcUserdefinePortal == null) {
            BookInit.getInstance().getmApcUserdefinePortal().portal_id = newEmpPortal.portal_id;
            BookInit.getInstance().getmApcUserdefinePortal().using_apc_style = newEmpPortal.apc_style;
            BookInit.getInstance().getmApcUserdefinePortal().using_home_style = newEmpPortal.home_style;
            BookInit.getInstance().getmApcUserdefinePortal().using_font_style = newEmpPortal.font_style;
            BookInit.getInstance().getmApcUserdefinePortal().using_color_style = newEmpPortal.color_style;
//                    BookInit.getInstance().getmApcUserdefinePortal().using_home_style = mEmpPortals.home_style;
            BookInit.getInstance().getmApcUserdefinePortal().setPortal_id(newEmpPortal.portal_id);
        } else {
            BookInit.getInstance().setmApcUserdefinePortal(newEmpPortal.mApcUserdefinePortal);
        }
        BookInit.getInstance().setMx_appid(newEmpPortal.getMx_appid() + "");

        MXCurrentUser currentUser = null;
        boolean isHaveCommunity = false;
        try {
            /**
             * 切换社区
             */
            currentUser = MXAPI.getInstance(context).currentUser();

            if (currentUser != null && currentUser.getNetworks() != null) {
                for (int i = 0; i < currentUser.getNetworks().size(); i++) {
                    if ((currentUser.getNetworks().get(i).getId() + "").equals(BookInit.getInstance().getMx_appid())) {
                        isHaveCommunity = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 如果不存在改社区那么将不进行切换社区
/*        if (!isHaveCommunity) {

            String msg = String.format(context.getResources().getString(R.string.login_error_msg_not_application_cfg), newEmpPortal.getPortal_name());
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return false;
        }*/


        mAppliationCenterDao.saveUserDefinePortal(BookInit.getInstance().getmApcUserdefinePortal());
        BookInit.getInstance().setApc_style(newEmpPortal.apc_style);
        BookInit.getInstance().setPortalId(newEmpPortal.portal_id);
        BookInit.getInstance().setPortalName(newEmpPortal.portal_name);
        BookInit.getInstance().getmCallbackMX().updatePortalSizeStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_color_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_font_style + "");
//        BookInit.getInstance().setmApcUserdefinePortal(mAppliationCenterDao.getUserDefinePortal());

        ConfigStyleUtil.changeTextSize(context, new ConfigStyleUtil.FinishPortalSwitch() {
            @Override
            public void finishPortalSwitchActivity() {
                dismiss();
            }
        });
        return true;
    }

    public void setView() {
//        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        ((Activity)context).getWindow().setAttributes(lp);
        mAppliationCenterDao = new AppliationCenterDao(context);
        portalAllList = mAppliationCenterDao.getPortalAll();
        EmpPortal currentPortal = mAppliationCenterDao.getPortalId();
        mPortalSwitchAdapter = new PortalSwitchAdapter(context, portalAllList, currentPortal, portalAllList);
        for (int i = 0; i < portalAllList.size(); i++) {
            if (null != portalAllList && portalAllList.size() > 0 && null != currentPortal) {
                if (currentPortal.portal_id.equals(portalAllList.get(i).getPortal_id())) {
                    mPortalSwitchAdapter.selectIndex(i);
                }
            }
        }
        lvSwitch.setAdapter(mPortalSwitchAdapter);

    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
