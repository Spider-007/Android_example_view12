package com.htmitech.emportal.ui.applicationcenter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.htmitech.MyView.ClassifyView;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.htcommonformplugin.entity.Searchcondition;
import com.htmitech.htexceptionmanage.entity.AlertCountInfo;
import com.htmitech.htexceptionmanage.entity.ManageExceptionparameter;
import com.htmitech.htexceptionmanage.utils.ExceptionAngleUtils;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.BadgeAllUnit;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.interfaces.CallBackRemove;
import com.htmitech.proxy.util.AngleUntil;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;


public class DragAdapter extends BaseAdapter {
    /** TAG*/
    private final static String TAG = "ApplicationCenterActivi";
    /** 是否显示底部的ITEM */
    private boolean isItemShow = false;
    private Context context;
    /** 控制的postion */
    private int holdPosition;
    /** 是否改变 */
    private boolean isChanged = false;
    /** 是否可见 */
    boolean isVisible = true;
    /** 可以拖动的列表（即用户选择的频道列表） */
    public List<AppInfo> channelList;
    /** TextView 频道内容 */
    class HolderView
    {
        private TextView item_text;
        private ImageView iv_icon;
        private ImageView iv_delete;
        private ImageView img_no_installed;
        private TextView angle_nulber;
        private TextView class_angle_nulber;
        private ClassifyView classify_view;
        private RelativeLayout iv_image_layout;
        private CircleProgressView circleProgressbar;
        private TextView tv_check;
    }
    private boolean isDelete = false;
    /** 要删除的position */
    public int remove_position = -1;

    private Handler mHandler = new Handler();
    private AppliationCenterDao mAppliationCenterDao;
    public int removeIndwx;
    private Animation shake;
    /**
     * 指定隐藏的position
     */
    private int hideposition = -1;

    private CallBackRemove mCallBackRemove;

    public int getRemoveIndwx() {
        return removeIndwx;
    }

    public void setRemoveIndwx(int removeIndwx) {
        this.removeIndwx = removeIndwx;
    }


    public void setCallBackRemove(CallBackRemove mCallBackRemove){
        this.mCallBackRemove = mCallBackRemove;
    }

    public DragAdapter(Context context, List<AppInfo> channelList,AppliationCenterDao mAppliationCenterDao) {
        this.context = context;
        if(channelList == null){
            channelList = new ArrayList<AppInfo>();
        }
        this.channelList = channelList;
        shake = AnimationUtils.loadAnimation(
                context, R.anim.adim_application_center);
        shake.setFillAfter(true);
        this.mAppliationCenterDao = mAppliationCenterDao;

    }


    public DragAdapter(Context context, List<AppInfo> channelList) {
        this.context = context;
        if(channelList == null){
            channelList = new ArrayList<AppInfo>();
        }
        this.channelList = channelList;
        shake = AnimationUtils.loadAnimation(
                context, R.anim.adim_application_center);
        shake.setFillAfter(true);

    }
    public void setData(List<AppInfo> channelList){
        if(channelList == null){
            channelList = new ArrayList<AppInfo>();
        }
        this.channelList = channelList;
        if(context!=null&&context instanceof ApplicationCenterActivity){
            ((ApplicationCenterActivity) context).setEmplyView(channelList);

        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public AppInfo getItem(int position) {
        // TODO Auto-generated method stub
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        View view = null;
        if (view == null) {
            holderView = new HolderView();
            view = LayoutInflater.from(context).inflate(R.layout.activity_application_center_adapter, parent,false);
            holderView.iv_icon = (ImageView) view.findViewById(R.id.icon_iv);
            holderView.item_text = (TextView) view.findViewById(R.id.name_tv);
            holderView.iv_delete = (ImageView) view.findViewById(R.id.delet_iv);
            holderView.tv_check = (TextView) view.findViewById(R.id.tv_check);
            holderView.img_no_installed = (ImageView) view.findViewById(R.id.img_no_installed);
            holderView.circleProgressbar = (CircleProgressView) view.findViewById(R.id.circleProgressbar);
            holderView.angle_nulber = (TextView) view.findViewById(R.id.angle_nulber);
            holderView.class_angle_nulber = (TextView) view.findViewById(R.id.class_angle_nulber);
            holderView.classify_view = (ClassifyView) view.findViewById(R.id.classify_view);
            holderView.iv_image_layout = (RelativeLayout) view.findViewById(R.id.iv_image_layout);
            LayoutParams mLayoutParams = holderView.iv_icon.getLayoutParams();
            holderView.iv_icon.setLayoutParams(mLayoutParams);
            view.setTag(holderView);
        }else{
            holderView = (HolderView)view.getTag();
        }

        AppInfo iconInfo = getItem(position);
//		R.drawable.mx_icon_public_account
//        if(iconInfo.getPicture_normal() != null && !iconInfo.getPicture_normal().equals("")){
////            holderView.iv_icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            BitmapUtils.instance().display(holderView.iv_icon,
//                    iconInfo.getPicture_normal(),
//                    new BitmapLoadCallBack<ImageView>() {
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void onLoadCompleted(
//                                ImageView container, String uri,
//                                Bitmap bitmap,
//                                BitmapDisplayConfig config,
//                                BitmapLoadFrom from) {
//                            container.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onLoadFailed(ImageView container,
//                                                 String uri, Drawable drawable) {
//                            // TODO Auto-generated method stub
//                        }
//                    });
//        }else{
//            holderView.iv_icon.setImageResource(R.drawable.icon_app_centre_normal);
//        }
        Glide.with(HtmitechApplication.instance()).load(iconInfo.getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into( holderView.iv_icon);

        if (iconInfo.getComp_code().contains("com_workflow") && !iconInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {
            setAngleNumber(iconInfo,holderView.angle_nulber);
        } else if(iconInfo.getComp_code().contains("com_commonform")) {
            setAngleNumberGeneralForm(iconInfo,holderView.angle_nulber);
        }else if(iconInfo.getComp_code().contains("com_alert")){
            setAngleNumberAlert(iconInfo,holderView.angle_nulber);
        }
        iconInfo.setAngleNumber(holderView.angle_nulber);
        holderView.item_text.setText(iconInfo.getApp_shortname());
        holderView.iv_delete.setOnClickListener(new DeleteOnClick(iconInfo, position));
        holderView.tv_check.setOnClickListener(new CheckChildOnClick(iconInfo,holderView.tv_check));
        holderView.tv_check.setSelected(iconInfo.getType_flag() >= 1);
        if(mAppliationCenterDao != null){
            holderView.tv_check.setVisibility(View.VISIBLE);
        }else{
            holderView.tv_check.setVisibility(View.GONE);
        }
        if(iconInfo.getApp_type() == 7){
//            holderView.classify_view.getBackground().setAlpha(120);
            holderView.classify_view.setVisibility(View.VISIBLE);

            holderView.classify_view.initAppInfo(iconInfo.getClassifyAppInfo());
            holderView.class_angle_nulber.setVisibility(View.GONE);
//            holderView.class_angle_nulber.setText("20");
            holderView.iv_image_layout.setVisibility(View.GONE);

            if (TextUtils.isEmpty(BadgeAllUnit.get().getBadge(iconInfo )) || BadgeAllUnit.get().getBadge(iconInfo ).equals("0")) {

                holderView.class_angle_nulber.setVisibility(View.GONE);

            } else {

                holderView.class_angle_nulber.setVisibility(View.VISIBLE);
                if (Integer.parseInt(BadgeAllUnit.get().getBadge(iconInfo)) > 99) {
                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                    layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    holderView.class_angle_nulber.setLayoutParams(layoutParms);
                    holderView.class_angle_nulber.setText("99+");
                }else{
                    if (!TextUtils.isEmpty(BadgeAllUnit.get().getBadge(iconInfo)) && !BadgeAllUnit.get().getBadge(iconInfo).equals("0")) {
                        holderView.class_angle_nulber.setText(BadgeAllUnit.get().getBadge(iconInfo) + "");
                        holderView.class_angle_nulber.setVisibility(View.GONE);
                    }else{
                        holderView.class_angle_nulber.setVisibility(View.GONE);
                    }

                }


            }
        }else{
            holderView.classify_view.setVisibility(View.GONE);
            holderView.class_angle_nulber.setVisibility(View.GONE);
            holderView.iv_image_layout.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(BadgeAllUnit.get().getBadge(iconInfo.getApp_id() + "")) || BadgeAllUnit.get().getBadge(iconInfo.getApp_id() + "").equals("0")) {

                holderView.angle_nulber.setVisibility(View.GONE);

            } else {
                holderView.angle_nulber.setVisibility(View.VISIBLE);
                if (Integer.parseInt(BadgeAllUnit.get().getBadge(iconInfo.getApp_id() + "")) > 99) {
                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                    layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    holderView.angle_nulber.setLayoutParams(layoutParms);
                    holderView.angle_nulber.setText("99+");
                }else{

                    if (!TextUtils.isEmpty(BadgeAllUnit.get().getBadge(iconInfo)) && !BadgeAllUnit.get().getBadge(iconInfo).equals("0")) {
                        holderView.angle_nulber.setText(BadgeAllUnit.get().getBadge(iconInfo) + "");
                        holderView.angle_nulber.setVisibility(View.VISIBLE);
                    }else{
                        holderView.angle_nulber.setVisibility(View.GONE);
                    }
                }


            }
        }
        if (position == getCount()-1){
            if (convertView == null) {
                convertView = view;
            }
        }
        if (isChanged && (position == holdPosition) && !isItemShow) {
            isChanged = false;
        }
        if (!isVisible && (position == -1 + channelList.size())) {

        }
        if(remove_position == position){
            deletInfo(null,position);
        }

        if (!isDelete) {
            holderView.iv_delete.setVisibility(View.GONE);
            view.clearAnimation();

            if(iconInfo.getApk_flag() == 2){
                holderView.img_no_installed.setVisibility(View.VISIBLE);
                holderView.img_no_installed.setImageResource(R.drawable.img_no_installed);
            }else if(iconInfo.getApk_flag() == 1){
                holderView.img_no_installed.setVisibility(View.VISIBLE);
                holderView.img_no_installed.setImageResource(R.drawable.img_new);
            }else{
                holderView.img_no_installed.setVisibility(View.GONE);
            }
        }
        else
        {
            holderView.img_no_installed.setVisibility(View.GONE);
            //增加一个字段是否可以被应用中心删除
            if(iconInfo.getAppcenter_remove() == 1){
                holderView.iv_delete.setVisibility(View.VISIBLE);
            }else{
                holderView.iv_delete.setVisibility(View.GONE);
            }
            if(position != 0)
            view.startAnimation(shake);
        }
        if (hideposition == position) {
            view.setVisibility(View.INVISIBLE);
        }else {
            view.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void setisDelete(boolean isDelete)
    {
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    public class DeleteOnClick implements OnClickListener{
        private AppInfo appInfo;
        private int postion;
        public DeleteOnClick(AppInfo appInfo,int postion){
            this.appInfo = appInfo;
            this.postion = postion;
        }

        @Override
        public void onClick(View v) {
            if(mCallBackRemove != null){
                mCallBackRemove.callBackRemoveApp(appInfo,postion,removeIndwx);
            }

        }
    }

    /**
     * 删除某个position
     * @param position
     */
    public void deletInfo(AppInfo appInfo,int position)
    {
        if(channelList.size() > 0) {
            if(channelList.size()>position) {
              if(appInfo !=null&&appInfo.getApp_id()!=channelList.get(position).getApp_id()){
                  return;
              }else {
                  channelList.remove(position);
//                  StringBuilder sb =  new StringBuilder();
//                  for(AppInfo l :channelList){
//                      sb.append( l.getApp_name().toString()+"|  ");
//                  }
//                  Log.e(TAG, "deletInfo: "+sb );
                  // TODO 暂时删除
//                  if(context!=null&&context instanceof ApplicationCenterActivity){
//                      ((ApplicationCenterActivity) context).setEmplyView(channelList);
//                  }
                  hideposition = -1;
              }

            }
            notifyDataSetChanged();

        }
    }


    /** 添加频道列表 */
    public void addItem(AppInfo channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /** 拖动变更频道排序 */
    public void exchange(int dragPostion, int dropPostion) {
        holdPosition = dropPostion;
        AppInfo dragItem = getItem(dragPostion);
        Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
        if (dragPostion < dropPostion) {
            channelList.add(dropPostion + 1, dragItem);
            channelList.remove(dragPostion);
        } else {
            channelList.add(dropPostion, dragItem);
            channelList.remove(dragPostion + 1);
        }
        isChanged = true;
        notifyDataSetChanged();
    }

    /** 设置删除的position */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
    }

    /** 获取是否可见 */
    public boolean isVisible() {
        return isVisible;
    }

    /** 设置是否可见 */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    /** 显示放下的ITEM */
    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }

    public void setHidePosition(int position) {
        // TODO Auto-generated method stub
        this.hideposition = position;
        notifyDataSetChanged();
    }


    public class CheckChildOnClick implements View.OnClickListener{
        public AppInfo appInfo;
        public TextView checkBox;
        public CheckChildOnClick(AppInfo appInfo,TextView checkBox){
            this.appInfo = appInfo;
            this.checkBox = checkBox;
        }
        @Override
        public void onClick(View v) {
            if(appInfo.getType_flag() == 1){
                checkBox.setSelected(false);
                mAppliationCenterDao.updateChildTypeFlag(appInfo.getApp_id() + "","0");
                appInfo.setType_flag(-1);
            }else{
                checkBox.setSelected(true);
                mAppliationCenterDao.updateChildTypeFlag(appInfo.getApp_id() + "","1");
                appInfo.setType_flag(1);
            }
        }
    }

    private void setAngleNumberGeneralForm(final AppInfo mAppInfo,final TextView textNumber){
        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_GENERAL_FORM_COUNT;
        Searchcondition mSearchcondition = AngleUntil.getSearchcondition(context, mAppInfo);

        if(mSearchcondition == null || mSearchcondition.todoflag.equals("1")){//如果是已办的话那么就不获得角标
            return;
        }
        AnsynHttpRequest.requestByPost(context, mSearchcondition, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                Log.d("AnsynHttpRequest", successMessage);
                JSONObject mJSONObject = JSON.parseObject(successMessage);
                String Result = mJSONObject.getString("Result");

                if (Result != null && !Result.equals("") && !Result.equals("0")) {
                    try {
                        int resultInteger = Integer.parseInt(Result);

                        textNumber.setVisibility(View.VISIBLE);
                        textNumber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                        if (resultInteger > 99) {
                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                            textNumber.setLayoutParams(layoutParms);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void setAngleNumberAlert(final AppInfo mAppInfo,final TextView textNumber){
        ManageExceptionparameter manageExceptionparameter = new ManageExceptionparameter();
        manageExceptionparameter.setUserId(OAConText.getInstance(HtmitechApplication.instance()).UserID);
        manageExceptionparameter.setKeyWord("");
        manageExceptionparameter.setSourceType("");
        manageExceptionparameter.setFilterDays("0");
            if(mAppInfo!=null&&mAppInfo.getmAppVersion()!=null&&mAppInfo.getmAppVersion().getAppVersionConfigArrayList()!=null){
                for(AppVersionConfig appVersionConfig :mAppInfo.getmAppVersion().getAppVersionConfigArrayList()){
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_days")){
                        manageExceptionparameter.setFilterDays(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_sourcetype")){
                        manageExceptionparameter.setSourceType(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_title_keyword")){
                        manageExceptionparameter.setKeyWord(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                }
            }
        ExceptionAngleUtils exceptionAngleUtils =new ExceptionAngleUtils(context,manageExceptionparameter, new ExceptionAngleUtils.IexceptionAlertItem() {
            @Override
            public void AlertItemClick(AlertCountInfo alertCountInfo) {
                if (alertCountInfo != null) {
                    try {
                        try {
                            int resultInteger = Integer.parseInt(alertCountInfo.getNoDealCount());

                            textNumber.setVisibility(View.VISIBLE);
                            textNumber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                            if (resultInteger > 99) {
                                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                textNumber.setLayoutParams(layoutParms);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }


    public void setAngleNumber(final AppInfo mAppInfo,final TextView textNumber) {
//        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;
        DocSearchParameters docSearchParameters = new DocSearchParameters();
//        docSearchParameters.context = OAConText.getInstance(context);
//        docSearchParameters.app_id = mAppInfo.getApp_id() + "";
//        docSearchParameters.Title = "";
//        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
//        docSearchParameters.ModelName = "";
//        docSearchParameters.RecordStartIndex = 0;
//        docSearchParameters.RecordEndIndex = 14;
        String com_workflow_mobileconfig_tabbutton_style = "";
        for (AppVersionConfig mAppVersionConfig : mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
            if("com_workflow_mobileconfig_tabbutton_style".equals(mAppVersionConfig.getConfig_code())){
                com_workflow_mobileconfig_tabbutton_style = mAppVersionConfig.getConfig_value();
            }
        }
        if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && (com_workflow_mobileconfig_tabbutton_style.equals("2") || com_workflow_mobileconfig_tabbutton_style.equals("5"))){
            docSearchParameters.importance = "0";
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && (com_workflow_mobileconfig_tabbutton_style.equals("1") || com_workflow_mobileconfig_tabbutton_style.equals("3"))){
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        }
        docSearchParameters.userId = OAConText.getInstance(context).UserID;
        docSearchParameters.appId = mAppInfo.getApp_id() + "";
        docSearchParameters.title = "";

        docSearchParameters.modelName = "";

        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.ShowNumber(mAppInfo,textNumber,context,docSearchParameters,this);
    }
}