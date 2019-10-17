package com.htmitech.proxy.adapter;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.activity.DeviceAuditPhotoActivity;
import com.htmitech.proxy.activity.DeviceAuditProgressActivity;
import com.htmitech.proxy.activity.DeviceSafeMainActivity;
import com.htmitech.proxy.activity.DeviceVerifyPasswordActivity;
import com.htmitech.proxy.doman.DeviceApplyInfo;
import com.htmitech.proxy.doman.DeviceAuditRequest;
import com.htmitech.proxy.doman.DeviceSafeConfigResult;
import com.htmitech.proxy.doman.DeviceUserListResultItem;
import com.htmitech.proxy.myenum.DeviceStateEnum;
import com.htmitech.proxy.pop.DeviceUnbindPopWindow;
import com.htmitech.proxy.util.ZTActivityUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by yanxin on 2017-7-27.
 */
public class DeviceUserAdapter extends BaseAdapter implements ObserverCallBackType {
    public ArrayList<DeviceUserListResultItem> dataList;
    private LayoutInflater Inflater;
    public Context context;
    public View view;
    public DeviceSafeConfigResult deviceSafeConfig;
    public String opertUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATE_BIND_STATUS;
    String auditUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GetDeviceSaveAudit;
    public int bindNum;
    public DeviceUserAdapter mDeviceUserAdapter;
    private DeviceUnbindPopWindow mDeviceUnbindPopWindow;
    private DeviceUserListResultItem mDeviceUserListRequest;
    private Gson mGson = new Gson();
    String deviceId = ((TelephonyManager) HtmitechApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

    public DeviceUserAdapter(Context context, ArrayList<DeviceUserListResultItem> dataList, View view, int bindNum, DeviceSafeConfigResult deviceSafeConfig) {
        Inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.context = context;
        this.view = view;
        this.bindNum = bindNum;
        this.deviceSafeConfig = deviceSafeConfig;
        this.mDeviceUserAdapter = this;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public DeviceUserListResultItem getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        DeviceUserListResultItem deviceUserListResultItem = (DeviceUserListResultItem) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = Inflater.inflate(R.layout.activity_bind_device_item, null);
            holder.icon_iv = (ImageView) convertView
                    .findViewById(R.id.device_icon);
            holder.tv_device_name = (TextView) convertView
                    .findViewById(R.id.device_name);
            holder.icon_bind = (ImageView) convertView
                    .findViewById(R.id.bind_icon);
            holder.tv_bind_state = (TextView) convertView
                    .findViewById(R.id.bind_state);
            holder.bind_time = (TextView) convertView
                    .findViewById(R.id.bind_time);
            holder.tv_no_bind = (TextView) convertView
                    .findViewById(R.id.tv_no_bind);
//            holder.tv_no_bind.setOnClickListener(new MyClickListener(DeviceStateEnum.UNBIND.bindState, deviceUserListResultItem));
            holder.tv_bind = (TextView) convertView
                    .findViewById(R.id.tv_bind);
            holder.tv_audit = (TextView) convertView
                    .findViewById(R.id.tv_audit);
            holder.tv_black = (TextView) convertView
                    .findViewById(R.id.tv_black);
            holder.bottomItem = (LinearLayout) convertView
                    .findViewById(R.id.bottom_item);
            holder.tv_loss = (TextView) convertView
                    .findViewById(R.id.tv_loss);
            holder.tv_unbind = (TextView) convertView
                    .findViewById(R.id.tv_unbind);
            holder.ll_state_root = (LinearLayout) convertView
                    .findViewById(R.id.ll_state_root);
            holder.tv_unblack = (TextView) convertView
                    .findViewById(R.id.tv_unblack);
            holder.tv_unloss = (TextView) convertView
                    .findViewById(R.id.tv_unloss);
            holder.tv_look = (TextView) convertView
                    .findViewById(R.id.tv_look);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_audit.setOnClickListener(new MyClickListener(-1, deviceUserListResultItem));
        holder.tv_look.setOnClickListener(new MyClickListener(DeviceStateEnum.LOOK.appplyType, deviceUserListResultItem));
        holder.tv_black.setOnClickListener(new MyClickListener(DeviceStateEnum.PULLBLACK.appplyType, deviceUserListResultItem));
        holder.tv_unblack.setOnClickListener(new MyClickListener(DeviceStateEnum.UNPULLBLACK.appplyType, deviceUserListResultItem));
        holder.tv_bind.setOnClickListener(new MyClickListener(DeviceStateEnum.BIND.appplyType, deviceUserListResultItem));
        holder.tv_loss.setOnClickListener(new MyClickListener(DeviceStateEnum.LOSS.appplyType, deviceUserListResultItem));
        holder.tv_unloss.setOnClickListener(new MyClickListener(DeviceStateEnum.UNLOSS.appplyType, deviceUserListResultItem));
        holder.tv_unbind.setOnClickListener(new MyClickListener(DeviceStateEnum.UNBIND.appplyType, deviceUserListResultItem));
        judgeDeviceState(holder, dataList.get(position));
        holder.tv_device_name.setText(TextUtils.isEmpty(deviceUserListResultItem.deviceName) ? "安卓设备" : deviceUserListResultItem.deviceName);
        Integer auditStatus = 100;
        String stateName = DeviceStateEnum.getBindName(deviceUserListResultItem.bindStatus);
        if (deviceUserListResultItem.statusFlag == 2) {
            try {
                auditStatus = Integer.parseInt(deviceUserListResultItem.auditStatus);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != auditStatus && auditStatus < 3) {
                if (deviceId.equals((deviceUserListResultItem.deviceSn))) {
                    holder.tv_bind_state.setText(deviceUserListResultItem.applyTypeStr + deviceUserListResultItem.auditStatusStr + "          [本机]");
                } else {
                    holder.tv_bind_state.setText(deviceUserListResultItem.applyTypeStr + deviceUserListResultItem.auditStatusStr);
                }

            } else {
                if (deviceId.equals((deviceUserListResultItem.deviceSn))) {
                    holder.tv_bind_state.setText(deviceUserListResultItem.applyTypeStr + "           [本机]");
                } else {
                    holder.tv_bind_state.setText(deviceUserListResultItem.applyTypeStr);
                }

            }
        } else if (deviceId.equals((deviceUserListResultItem.deviceSn))) {
            holder.tv_bind_state.setText((stateName.equals("") ? "未绑定" : stateName) + "       [本机]");
        } else {
            holder.tv_bind_state.setText(stateName.equals("") ? "未绑定" : stateName);
        }
        holder.bind_time.setText(deviceUserListResultItem.updateTime == null ? "" : deviceUserListResultItem.updateTime);
        if (deviceUserListResultItem.deviceType.equals(2)) {
            holder.icon_iv.setBackgroundResource(R.drawable.icon_list_paid);
        }

        holder.bottomItem.setVisibility(deviceUserListResultItem.isExtra ? View.VISIBLE : View.GONE);
        Log.e("Position", position + "" + deviceUserListResultItem.isExtra);
        return convertView;
    }


    public class ViewHolder {
        public ImageView icon_iv;
        public TextView tv_device_name;
        public ImageView icon_bind;
        public TextView tv_bind_state;
        public TextView bind_time;
        public TextView tv_no_bind;  //未绑定
        public TextView tv_bind;     //已绑定
        public TextView tv_audit;    //审核中
        public TextView tv_black;    //已拉黑
        public TextView tv_loss;     //已挂失
        public TextView tv_unbind;   //解绑        暂时不做
        public TextView tv_unblack;   //解除黑名单
        public TextView tv_unloss;   //解除挂失
        public TextView tv_look;   //查看
        public LinearLayout ll_state_root;  //操作按钮根目录
        public LinearLayout bottomItem;
    }

    public void judgeDeviceState(ViewHolder holder, DeviceUserListResultItem data) {
        if (holder == null) return;
        if (data.statusFlag == 2) {    //审核中
            holder.tv_look.setVisibility(View.VISIBLE);
            holder.icon_bind.setImageResource(R.drawable.icon_check);
            holder.tv_loss.setVisibility(View.GONE);
            holder.tv_black.setVisibility(View.GONE);
            holder.tv_bind.setVisibility(View.GONE);
            holder.tv_unblack.setVisibility(View.GONE);
            holder.tv_unloss.setVisibility(View.GONE);
            holder.tv_unbind.setVisibility(View.GONE);
        } else if (data.bindStatus == DeviceStateEnum.BIND.bindState) {
            //当前为绑定状态
            holder.tv_unbind.setVisibility(View.VISIBLE);
            if (!deviceId.equals(data.deviceSn)) {
                holder.tv_loss.setVisibility(View.VISIBLE);
                holder.tv_black.setVisibility(View.VISIBLE);
            } else {
                holder.tv_loss.setVisibility(View.GONE);
                holder.tv_black.setVisibility(View.GONE);
            }

            holder.tv_bind.setVisibility(View.GONE);
            holder.tv_unblack.setVisibility(View.GONE);
            holder.tv_unloss.setVisibility(View.GONE);
            holder.tv_look.setVisibility(View.GONE);
            holder.icon_bind.setImageResource(R.drawable.icon_binding);
        } else if (data.bindStatus == DeviceStateEnum.UNBIND.bindState) {
            //当前为未绑定状态
            if (!deviceId.equals(data.deviceSn)) {
                holder.tv_black.setVisibility(View.VISIBLE);
                holder.tv_unbind.setVisibility(View.GONE);
                holder.tv_unblack.setVisibility(View.GONE);
                holder.tv_unloss.setVisibility(View.GONE);
                holder.tv_loss.setVisibility(View.VISIBLE);
            } else {
                holder.tv_loss.setVisibility(View.GONE);
                holder.tv_black.setVisibility(View.GONE);
                holder.tv_unbind.setVisibility(View.GONE);
                holder.tv_unblack.setVisibility(View.GONE);
                holder.tv_unloss.setVisibility(View.GONE);
            }
            holder.tv_look.setVisibility(View.GONE);
            holder.tv_bind.setVisibility(View.VISIBLE);
            holder.icon_bind.setImageResource(R.drawable.icon_no_binding);

        } else if (data.bindStatus == DeviceStateEnum.PULLBLACK.bindState) {
            //当前状态为拉黑
            holder.tv_unblack.setVisibility(View.VISIBLE);
            holder.icon_bind.setImageResource(R.drawable.icon_blacklist);
            holder.tv_unbind.setVisibility(View.GONE);
            holder.tv_loss.setVisibility(View.VISIBLE);
            holder.tv_black.setVisibility(View.GONE);
            holder.tv_bind.setVisibility(View.GONE);
            holder.tv_unloss.setVisibility(View.GONE);
            holder.tv_look.setVisibility(View.GONE);
        } else if (data.bindStatus == DeviceStateEnum.LOSS.bindState) {
            //当前状态为挂失
            holder.tv_unloss.setVisibility(View.VISIBLE);
            holder.icon_bind.setImageResource(R.drawable.icon_loss);
            holder.tv_unbind.setVisibility(View.GONE);
            holder.tv_loss.setVisibility(View.GONE);
            holder.tv_black.setVisibility(View.GONE);
            holder.tv_bind.setVisibility(View.GONE);
            holder.tv_unblack.setVisibility(View.GONE);
            holder.tv_look.setVisibility(View.GONE);
        }
        if (null != deviceSafeConfig && deviceSafeConfig.userBindingDeviceLimit == 0) {
            holder.tv_bind.setVisibility(View.GONE);
        }
    }

    class MyClickListener implements View.OnClickListener, ObserverCallBackType {
        public DeviceUserListResultItem mDeviceUserListResultItem;
        public int state = -100;

        /*
        * @param state标志当前点击的按钮对应什么状态
        * */
        public MyClickListener(int state, DeviceUserListResultItem mDeviceUserListResultItem) {
            this.mDeviceUserListResultItem = mDeviceUserListResultItem;
            this.state = state;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //解除绑定
                case R.id.tv_unbind:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    IDeviceState.ISCheckEnum mISCheckEnum = DeviceStateEnum.UNBIND.deviceOperate(context);
                    try {
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyRemoveBinding;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditRemoveBinding;
                        if (isNeedCheck == 1) {
                            //绑定前需要校验
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                            //需要审批
//                            toAudit(mDeviceUserListResultItem,DeviceStateEnum.UNBIND.bindState);
                            auditFromServer(mDeviceUserListResultItem, state);
                        } else {
                            updateDeviceState(mDeviceUserListResultItem, state);
                        }

//                        switch (mISCheckEnum){
//                            case IS_NEET_CHECK:
//                                toVerify(mDeviceUserListResultItem,mISCheckEnum.getIeedAuditValue(),DeviceStateEnum.UNBIND.bindState);
//                                break;
//                            case IS_NEET_AUDIT:
//                                auditFromServer(mDeviceUserListResultItem,state);
//                                break;
//                            case OTHER:
//                                updateDeviceState(mDeviceUserListResultItem, state);
//                                break;
//                        }
                    } catch (Exception e) {
                        Log.e("DeviceMoudle", e.getMessage());

                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                    //解除黑名单
                case R.id.tv_unblack:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyPullBlack;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditPullBlack;
                        if (isNeedCheck == 1) {
                            //绑定前需要校验
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                            //需要审批
                            auditFromServer(mDeviceUserListResultItem, state);
                        } else {
                            updateDeviceState(mDeviceUserListResultItem, state);
                        }
                    } catch (Exception e) {
                        Log.e("DeviceMoudle", e.getMessage());
                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                    //绑定设备
                case R.id.tv_bind:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        if (deviceSafeConfig != null && bindNum >= deviceSafeConfig.userBindingDeviceLimit) {
                            mDeviceUnbindPopWindow = new DeviceUnbindPopWindow(context, null, this, DeviceStateEnum.UNBIND.appplyType);
                            mDeviceUnbindPopWindow.show(view);
                            state = DeviceStateEnum.UNBIND.appplyType;
                            return;
                        }
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyAddBinding;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditAddBinging;
                        if (isNeedCheck == 1) {
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                              /*
                            *当需要审批，但当前绑定数量为0，且在白名单中包含‘skip_first_binding’这个字符串时可以跳过审核，
                            *当条件不满足上述要求的时候需要进行审核执行以下操作
                            * */
                            if ((bindNum == 0 && deviceSafeConfig.skipAuditList.contains("skip_first_binding"))) {
                                updateDeviceState(mDeviceUserListResultItem, state);
                            } else {
                                auditFromServer(mDeviceUserListResultItem, state);
                            }
                        } else {
                            updateDeviceState(mDeviceUserListResultItem, state);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "设备配置异常", Toast.LENGTH_SHORT).show();
                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                    //拉黑设备
                case R.id.tv_black:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyPullBlack;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditPullBlack;
                        if (isNeedCheck == 1) {
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                            auditFromServer(mDeviceUserListResultItem, state);
                        } else {
                            mDeviceUnbindPopWindow = new DeviceUnbindPopWindow(context, mDeviceUserAdapter, this, DeviceStateEnum.PULLBLACK.appplyType);
                            mDeviceUnbindPopWindow.show(view);
//                            updateDeviceState(mDeviceUserListResultItem, state);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "设备配置异常", Toast.LENGTH_SHORT).show();
                        Log.e("DeviceMoudle", e.getMessage());
                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                    //设备挂失
                case R.id.tv_loss:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyLossReport;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditLossReport;
                        if (isNeedCheck == 1) {
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                            auditFromServer(mDeviceUserListResultItem, state);
                        } else {
                            mDeviceUnbindPopWindow = new DeviceUnbindPopWindow(context, mDeviceUserAdapter, this, DeviceStateEnum.LOSS.appplyType);
                            mDeviceUnbindPopWindow.show(view);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "设备配置异常", Toast.LENGTH_SHORT).show();
                        Log.e("DeviceMoudle", e.getMessage());
                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                    //解除挂失
                case R.id.tv_unloss:
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyLossReport;
                        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditLossReport;
                        if (isNeedCheck == 1) {
                            //绑定前需要校验
                            toVerify(mDeviceUserListResultItem, isNeedAudit, state);
                        } else if (isNeedAudit == 1) {
                            //需要审批
                            auditFromServer(mDeviceUserListResultItem, state);
                        } else {
                            updateDeviceState(mDeviceUserListResultItem, state);
                        }
                    } catch (Exception e) {
                        Log.e("DeviceMoudle", e.getMessage());
                    } finally {
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                        break;
                    }
                case R.id.tv_look:        //查看
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("deviceUserListResultItem", mDeviceUserListResultItem);
                    ZTActivityUnit.switchTo((Activity) context, DeviceAuditProgressActivity.class, params);
//                    Intent mIntent = new Intent(context,DeviceAuditProgressActivity.class);
//                    context.startActivity(mIntent);
                    break;
                case R.id.tv_submit:
                    if (null != mDeviceUnbindPopWindow && mDeviceUnbindPopWindow.isShowing()) {
                        mDeviceUnbindPopWindow.dismiss();
                    }
                    if (!Network.checkNetWork(context)) {
                        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateDeviceState(mDeviceUserListResultItem, state);
                    break;
                default:
                    break;
            }

        }

        /*
        * 申请请求
        * */
        DeviceAuditRequest mDeviceAuditRequest;

        public void auditFromServer(DeviceUserListResultItem mDeviceUserListResultItem, int state) {

            mDeviceAuditRequest = new DeviceAuditRequest();
            mDeviceAuditRequest.deviceSn = mDeviceUserListResultItem.deviceSn;
            mDeviceAuditRequest.deviceId = mDeviceUserListResultItem.deviceId;
            mDeviceAuditRequest.applySource = 1;
            mDeviceAuditRequest.verifyMethod = 1;
            mDeviceAuditRequest.applyType = state;
            mDeviceAuditRequest.needAudit = 1;
            mDeviceAuditRequest.groupCorpId = OAConText.getInstance(context).group_corp_id;
            mDeviceAuditRequest.userId = OAConText.getInstance(context).UserID;
            AnsynHttpRequest.requestByPostWithToken(context, mDeviceAuditRequest, auditUrl, CHTTP.POSTWITHTOKEN
                    , MyClickListener.this, "audit", "0");
        }

        @Override
        public void success(String requestValue, int type, String requestName) {
            if (requestName.equals("audit")) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, auditUrl, mDeviceAuditRequest, MyClickListener.this, requestName, "0");
                if (requestValue != null && !requestValue.equals("")) {
                    DeviceApplyInfo mDeviceApplyInfo = mGson.fromJson(requestValue.toString(), DeviceApplyInfo.class);
                    if (mDeviceApplyInfo.getCode() == 200) {
                        mDeviceUserListResultItem.applyId = mDeviceApplyInfo.result;
                        toAudit(mDeviceUserListResultItem, state);
                        ((DeviceSafeMainActivity) context).getDeviceListFromServer();
                    } else {
                        Toast.makeText(context, mDeviceApplyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }

        @Override
        public void fail(String exceptionMessage, int type, String requestName) {
            if (requestName.equals("audit")) {
                Toast.makeText(context, "申请失败 ！", Toast.LENGTH_SHORT).show();
//                toAudit(mDeviceUserListResultItem,DeviceStateEnum.UNBIND.bindState);
            }
        }

        @Override
        public void notNetwork() {
            Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void callbackMainUI(String successMessage) {

        }
    }

    /* DeviceAuditRequest mDeviceAuditRequest;
     public void auditFromServer(DeviceUserListResultItem mDeviceUserListResultItem,int state){

         mDeviceAuditRequest = new DeviceAuditRequest();
         mDeviceAuditRequest.deviceSn = mDeviceUserListResultItem.deviceSn;
         mDeviceAuditRequest.deviceId = mDeviceUserListResultItem.deviceId;
         mDeviceAuditRequest.applySource = 1;
         mDeviceAuditRequest.verifyMethod = 1;
         mDeviceAuditRequest.applyType = state;
         mDeviceAuditRequest.needAudit = 1;
         mDeviceAuditRequest.groupCorpId = OAConText.getInstance(context).group_corp_id;
         mDeviceAuditRequest.userId = OAConText.getInstance(context).UserID;
         AnsynHttpRequest.requestByPostWithToken(context, mDeviceAuditRequest, auditUrl, CHTTP.POSTWITHTOKEN
                 , this, "audit", "0");
     }*/
    public void toVerify(DeviceUserListResultItem mDeviceUserListResultItem, int needAudit, int state) {
        Map<String, Object> params = new HashMap<String, Object>();
        mDeviceUserListResultItem.applyType = state + "";
        mDeviceUserListResultItem.needAudit = needAudit + "";
        mDeviceUserListResultItem.bindStatus = DeviceStateEnum.getBindState(state);
        params.put("deviceUserListResultItem", mDeviceUserListResultItem);
        params.put("deviceSafeConfig", deviceSafeConfig);
        params.put("bindNum", bindNum);
        params.put("app_id", ((DeviceSafeMainActivity) context).app_id == null ? "" : ((DeviceSafeMainActivity) context).app_id);
        ZTActivityUnit.switchTo((Activity) context, DeviceVerifyPasswordActivity.class, params);

    }

    public void toAudit(DeviceUserListResultItem mDeviceUserListResultItem, int state) {
        Map<String, Object> params = new HashMap<String, Object>();
        mDeviceUserListResultItem.applyType = state + "";
        mDeviceUserListResultItem.needAudit = "1";//需要审批
        params.put("deviceUserListResultItem", mDeviceUserListResultItem);
        params.put("app_id", ((DeviceSafeMainActivity) context).app_id == null ? "" : ((DeviceSafeMainActivity) context).app_id);
        ZTActivityUnit.switchTo((Activity) context, DeviceAuditPhotoActivity.class, params);

    }

    public void updateDeviceState(DeviceUserListResultItem mDeviceUserListResultItem, int state) {
        mDeviceUserListRequest = new DeviceUserListResultItem();
        mDeviceUserListRequest.userId = OAConText.getInstance(context).UserID;
        mDeviceUserListRequest.groupCorpId = OAConText.getInstance(context).group_corp_id;
        mDeviceUserListRequest.deviceId = mDeviceUserListResultItem.deviceId;
        mDeviceUserListRequest.loginName = OAConText.getInstance(context).login_name;
        mDeviceUserListRequest.deviceSn = mDeviceUserListResultItem.deviceSn;
        mDeviceUserListRequest.statusFlag = mDeviceUserListResultItem.statusFlag;
        mDeviceUserListRequest.applySource = "1";
        mDeviceUserListRequest.applyType = state + "";
        mDeviceUserListRequest.verifyMethod = "1";
        mDeviceUserListRequest.needAudit = "0";
        mDeviceUserListRequest.bindStatus = DeviceStateEnum.getBindState(state);
        AnsynHttpRequest.requestByPostWithToken(context, mDeviceUserListRequest, opertUrl, CHTTP.POSTWITHTOKEN
                , DeviceUserAdapter.this, "opert", "0");
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("opert")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, auditUrl, mDeviceUserListRequest, this, requestName, "0");
            ((DeviceSafeMainActivity) context).getDeviceListFromServer();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("opert")) {
            Toast.makeText(context, "操作失败,请联系管理员！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notNetwork() {
        Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

}
