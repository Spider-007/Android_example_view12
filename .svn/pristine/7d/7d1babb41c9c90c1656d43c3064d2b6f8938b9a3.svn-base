package com.htmitech_updown.updownloadmanagement.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.adapter.UploadFileNeedUpAdapter;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.listener.UploadFileNetConnectionListener;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadStatus;
import com.htmitech_updown.updownloadmanagement.view.CustomCircleProgressBar;
import com.htmitech_updown.updownloadmanagement.view.NetClickListener;
import com.htmitech_updown.updownloadmanagement.view.NetPopupWindow;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 */

public class UpLoadFileFragment extends Fragment implements View.OnClickListener, UploadFileNetConnectionListener {

    private Context context;
    private String TAG = "Fragment";
    private ImageView imageViewTopNotice;
    private TextView textViewTopNotice;
    private ListView listView;
    private List<UploadFileInfoBean> uploadFileInfoBeanList;
    private UploadFileNeedUpAdapter adapter;
    private LinearLayout linearLayoutTopTab;
    private NetPopupWindow popupWindow;
    private Button buttonAllResume;
    private boolean errorFlag = false;
    private boolean isWifiConnect;
    private boolean isMobileConnect;
    private boolean isNetDisconnect = false;
    private ArrayList<String> finishTaskIdList;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //移动数据
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //wifi网络
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        isWifiConnect = wifiNetInfo.isConnected();
        isMobileConnect = mobNetInfo.isConnected();
        finishTaskIdList = new ArrayList<>();
        uploadFileInfoBeanList = DbUtil.getUploadFileInfoSCLBList();
        // 注册广播接收器，接收下载进度信息和结束信息
        IntentFilter filter = new IntentFilter();
        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
            filter.addAction(uploadFileInfoBeanList.get(i).TASK_ID);
        }
        getActivity().registerReceiver(mReceiver, filter);
        // 注册广播接收器，接收结束后刷新数据的广播
        IntentFilter filterFinish = new IntentFilter();
        filterFinish.addAction("updownloadfinish");
        getActivity().registerReceiver(mReceiverLoadFinish, filterFinish);
        //注册广播，当上传报服务器错误，发送广播
        IntentFilter filterError = new IntentFilter();
        filterError.addAction("updownloaderror");
        getActivity().registerReceiver(mReceiverLoadError, filterError);
        //注册广播接收器，当wifi发生变化时接受广播
        IntentFilter filterWiFi = new IntentFilter();
//        filterWiFi.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filterWiFi.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filterWiFi.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mReceiverNoWiFi, filterWiFi);

        adapter = new UploadFileNeedUpAdapter(context, uploadFileInfoBeanList, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.updownload_prefix_upoadfilefragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        imageViewTopNotice = (ImageView) view.findViewById(R.id.iv_updownload_sclb_top);
        textViewTopNotice = (TextView) view.findViewById(R.id.tv_updownload_sclb_top);
        listView = (ListView) view.findViewById(R.id.lv_updownload_sclb);
        linearLayoutTopTab = (LinearLayout) view.findViewById(R.id.ll_updownload_sclb_top_tab);
        buttonAllResume = (Button) view.findViewById(R.id.bt_upload_sclb_all_resume);
        buttonAllResume.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        if (null != mReceiver) {
            getActivity().unregisterReceiver(mReceiver);
        }
        if (null != mReceiverLoadFinish) {
            getActivity().unregisterReceiver(mReceiverLoadFinish);
        }
        if (null != mReceiverNoWiFi) {
            getActivity().unregisterReceiver(mReceiverNoWiFi);
        }
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String act = "";
                act = intent.getAction();
                ChunkInfo chunkInfo = (ChunkInfo) intent.getSerializableExtra("chunkIntent");
                if (chunkInfo != null) {
                    Log.e("chunk", chunkInfo.getChunk() + "");
                    updateItem(chunkInfo.getChunks(), chunkInfo.getChunk() + 1, act);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver mReceiverLoadFinish = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("updownloadfinish")) {
                    String task_id = intent.getStringExtra("task_id");
                    if (!finishTaskIdList.contains(task_id)) {
                        finishTaskIdList.add(task_id);
                        Log.e("updownloadfinish", "执行了");
                        uploadFileInfoBeanList = DbUtil.getUploadFileInfoSCLBList();
                        adapter.setData(uploadFileInfoBeanList);
                        Intent intentFinishData = new Intent();
                        intentFinishData.setAction("uploadfinishupdatedata");
                        getActivity().sendBroadcast(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver mReceiverNoWiFi = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //获取联网状态的NetworkInfo对象
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    //如果当前的网络连接成功并且网络连接可用
                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                                Log.i(TAG, "WIFI连上");
                                isWifiConnect = true;
                            }
                            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                                Log.i(TAG, "4G连上");
                                isMobileConnect = true;
                            }
                        }
                    } else {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                            Log.i(TAG, "WIFI断开");
                            isWifiConnect = false;
                        }
                        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            Log.i(TAG, "4G断开");
                            isMobileConnect = false;
                        }
                    }
                    if (isWifiConnect) {
                        linearLayoutTopTab.setVisibility(View.GONE);
                        buttonAllResume.setVisibility(View.GONE);
                        //将所有文件恢复上传
                        if (isNetDisconnect) {
                            for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
                                UploadManager.getInstance().resume(uploadFileInfoBeanList.get(i).TASK_ID);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else if (!isWifiConnect && isMobileConnect) {
                        int code = PreferenceUtils.getUploadBigFileNet(UpLoadFileFragment.this.context);
                        if (code == 1) {
                            linearLayoutTopTab.setVisibility(View.GONE);
                            buttonAllResume.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        linearLayoutTopTab.setVisibility(View.VISIBLE);
                        buttonAllResume.setVisibility(View.VISIBLE);
                        isNetDisconnect = true;
                        textViewTopNotice.setText("当前非Wifi环境，文件上传已暂停");
                        imageViewTopNotice.setImageResource(R.drawable.icon_hint_pause);
                        //将所有正在上传和错误的设置为暂停
                        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
                            if (UploadManager.getInstance().getUploadTask(uploadFileInfoBeanList.get(i).TASK_ID).getUploadStatus() == UploadStatus.UPLOAD_STATUS_UPLOADING) {
                                UploadManager.getInstance().pause(uploadFileInfoBeanList.get(i).TASK_ID);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else if (!isWifiConnect && !isMobileConnect) {
                        linearLayoutTopTab.setVisibility(View.VISIBLE);
                        buttonAllResume.setVisibility(View.VISIBLE);
                        isNetDisconnect = true;
                        textViewTopNotice.setText("网络连接中断文件上传暂停，请检查您的网络设置");
                        imageViewTopNotice.setImageResource(R.drawable.icon_hint_pause);
                        //将所有正在上传和错误的设置为暂停
                        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
                            if (UploadManager.getInstance().getUploadTask(uploadFileInfoBeanList.get(i).TASK_ID).getUploadStatus() == UploadStatus.UPLOAD_STATUS_UPLOADING) {
                                UploadManager.getInstance().pause(uploadFileInfoBeanList.get(i).TASK_ID);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };


    BroadcastReceiver mReceiverLoadError = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("updownloaderror")) {
                    Log.e("updownloaderror", "执行了");
                    String resultCode = intent.getStringExtra("ErrorIntent");
                    if (resultCode.equals("-1")) {//404
                        if (errorFlag) {
                            return;
                        }
                        errorFlag = true;
                        //先给出提示
                        linearLayoutTopTab.setVisibility(View.VISIBLE);
                        buttonAllResume.setVisibility(View.VISIBLE);
                        textViewTopNotice.setText("服务器错误，文件上传已暂停");
                        imageViewTopNotice.setImageResource(R.drawable.icon_hint_warning);
                        //将所有正在上传和错误的设置为暂停
                        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
                            if (UploadManager.getInstance().getUploadTask(uploadFileInfoBeanList.get(i).TASK_ID).getUploadStatus() == UploadStatus.UPLOAD_STATUS_UPLOADING) {
                                UploadManager.getInstance().pause(uploadFileInfoBeanList.get(i).TASK_ID);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        errorFlag = false;
                    } else {//其它异常
                        Toast.makeText(context, "上传失败，暂停上传当前文件", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void updateItem(int max, int progress, String task_Id) {
        int positon = -1;
        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
            if (uploadFileInfoBeanList.get(i).TASK_ID.equals(task_Id)) {
                positon = i;
                break;
            }
        }
        if (positon > -1) {
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            /**最后一个可见的位置**/
            int lastVisiblePosition = listView.getLastVisiblePosition();
            /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
            if (positon >= firstVisiblePosition && positon <= lastVisiblePosition) {
                View view = listView.getChildAt(positon - firstVisiblePosition);
                UploadFileNeedUpAdapter.ViewHolder holder = (UploadFileNeedUpAdapter.ViewHolder) view.getTag();
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.imageViewStartOrPause.setVisibility(View.GONE);
                holder.progressBar.setMaxProgress(max);
                holder.progressBar.setProgress(progress);
                uploadFileInfoBeanList.get(positon).chunk = progress;
                uploadFileInfoBeanList.get(positon).chunks = max;
            }
        }
        //记得更新list数据源中position位置的数据，避免滑动后局部刷新失效
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_upload_sclb_all_resume) {
            linearLayoutTopTab.setVisibility(View.GONE);
            buttonAllResume.setVisibility(View.GONE);
            for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
                if (UploadManager.getInstance().getUploadTask(uploadFileInfoBeanList.get(i).TASK_ID).getUploadStatus() != UploadStatus.UPLOAD_STATUS_UPLOADING) {
                    UploadManager.getInstance().resume(uploadFileInfoBeanList.get(i).TASK_ID);
                }
            }
        }
    }

    //开始后发现没连接wifi的回掉
    @Override
    public void noWifi(final String taskId, final ImageView imageView, final CustomCircleProgressBar progressBar) {
        //当前没有wifi，弹出popwindow
        popupWindow = new NetPopupWindow(context, new NetClickListener() {
            @Override
            public void onNetClick(String code) {
                if ("always".equals(code)) {
                    UploadManager.getInstance().resume(taskId);
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else if ("once".equals(code)) {
                    UploadManager.getInstance().resume(taskId);
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else if ("wifi".equals(code)) {

                }
            }
        });

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            //设置我们弹出的PopupWindow的位置，基于某个视图之下
            popupWindow.showAtLocation(getView().findViewById(R.id.upload_big_file_all), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }
}
