package com.htmitech.proxy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.utils.UIUtils;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.doman.schedulebean.ScheduleDeleteRequestBean;
import com.htmitech.proxy.doman.schedulebean.ScheduleListItemBean;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.ScheduleUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

/**
 * Created by yanxin on 2017-9-15.
 */
public class ScheduleListAdapter extends BaseAdapter implements ObserverCallBackType {
    public Context context;
    public ArrayList<ScheduleListItemBean> dataList;
    public ArrayList<View> viewList = new ArrayList<View>();
    private final LayoutInflater inflater;
    /*
    * 删除按钮的宽度,每次修改布局文件需要修改对应的大小值，
    * 才可以保证侧滑不超出控件大小范围
    * */
    int measuredWidth = UIUtils.dip2px(51);
    private final DisplayMetrics dm;
    private final int screenWidth;
    public int flag;//0详情页 1搜索页
    public String app_id;
    public String keyword;
    public String selectDate;

    public ScheduleListAdapter(Context context, ArrayList dataList, int flag, String app_id, String keyword, String selectDate) {
        this.context = context;
        this.dataList = dataList;
        this.flag = flag;
        this.app_id = app_id;
        this.keyword = keyword;
        this.selectDate = selectDate;
        inflater = LayoutInflater.from(context);
        dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ScheduleListItemBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        final int tmpPosition = position;
        if (null == convertView) {
            if (flag == 1) {
                convertView = inflater.inflate(R.layout.activity_search_list_item, null);
            } else {
                convertView = inflater.inflate(R.layout.activity_schedule_list_item, null);
            }
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams layoutParams = mViewHolder.content.getLayoutParams();
        layoutParams.width = dataList.get(position).rawX;
        mViewHolder.content.setLayoutParams(layoutParams);
        if (position % 2 != 0) {
            mViewHolder.llScheduleRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));

        } else {
            mViewHolder.llScheduleRoot.setBackgroundColor(Color.parseColor("#F8F8F8"));
        }
//        mViewHolder.tvScheduleTitle.setText(dataList.get(position).schTitle);
        if (!TextUtils.isEmpty(keyword)) {
            mViewHolder.tvScheduleTitle.setText(ScheduleUtils.setSpecifiedTextsColor(dataList.get(position).schTitle, keyword, Color.parseColor("#297BFB")));
        } else {
            mViewHolder.tvScheduleTitle.setText(dataList.get(position).schTitle);
        }

        try {
            if (ScheduleUtils.isAllDay(dataList.get(position).schBeginTime.split(" ")[0], dataList.get(position).schEndTime.split(" ")[0], selectDate)) {
                mViewHolder.tvTime.setText("全天");
            } else {
                if (flag == 1) {
                    mViewHolder.tvTime.setText(dataList.get(position).schBeginTime);
                } else {
                    mViewHolder.tvTime.setText(dataList.get(position).schBeginTime.split(" ")[1].substring(0, 5) + "\n" + dataList.get(position).schEndTime.split(" ")[1].substring(0, 5));
                }
            }
//            mViewHolder.tvTime.setText(dataList.get(position).schBeginTime.split(" ")[1] + "\n" + dataList.get(position).schEndTime.split(" ")[1]);
            if (dataList.get(position).classfy.equals("1")) {    //备忘
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#FFB61E"));
            } else if (dataList.get(position).classfy.equals("3")) { //出差
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#3DCCB4"));
            } else if (dataList.get(position).classfy.equals("2")) {//会议
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#4EAAFF"));
            } else if (dataList.get(position).classfy.equals("0")) { //其他
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#7EC4FF"));
            }
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatToday = simpleDateFormat.format(today);
            if (dataList.get(position).schStatus == 0) {
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#DC5656"));
            }
            if (ScheduleUtils.FailSchedule(dataList.get(position).schEndTime, formatToday)) {
                mViewHolder.tvFlag.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag != 1) {
            //详情页面不需要删除操作
            mViewHolder.content.setOnTouchListener(new ListViewItemOnTouchListener(mViewHolder, dataList.get(position), position));
        }
        mViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule(dataList.get(tmpPosition), tmpPosition);
            }
        });
        return convertView;
    }

    public int deletePosition = -1;

    private void deleteSchedule(ScheduleListItemBean mScheduleListItemBean, int tmpPosition) {
        deletePosition = tmpPosition;
        try {
//            String url = "http://htrf.dscloud.me:8083/data-crab/schschedule/deleteschedule";
            String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.DELETE_SCHEDULE;
            ScheduleDeleteRequestBean mScheduleDeleteRequestBean = new ScheduleDeleteRequestBean();
            mScheduleDeleteRequestBean.appId = Long.parseLong(app_id);
            mScheduleDeleteRequestBean.corpId = BookInit.getInstance().getCorp_id();
            mScheduleDeleteRequestBean.groupCorpId = OAConText.getInstance(context).group_corp_id;
            mScheduleDeleteRequestBean.schId = Long.parseLong(mScheduleListItemBean.schId);
            mScheduleDeleteRequestBean.userId = OAConText.getInstance(context).UserID;
            AnsynHttpRequest.requestByPostWithToken(context, mScheduleDeleteRequestBean, url, CHTTP.POSTWITHTOKEN, this, "deleteSchedule", LogManagerEnum.GGENERAL.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        JSONObject jsonObject = JSONObject.parseObject(requestValue);
        if (null != jsonObject) {
            Integer code = jsonObject.getInteger("code");
            if (null != code && code == 200) {
                dataList.remove(deletePosition);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Toast.makeText(context, "数据异常请联系后端管理员！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    static class ViewHolder {
        @InjectView(R.id.ll_schedule_root)
        LinearLayout llScheduleRoot;
        @InjectView(R.id.content)
        LinearLayout content;
        @InjectView(R.id.delete)
        TextView delete;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_schedule_title)
        TextView tvScheduleTitle;
        @InjectView(R.id.tv_flag)
        TextView tvFlag;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ListViewItemOnTouchListener implements View.OnTouchListener {
        public ViewHolder mViewHolder;
        public View convertView;
        public ScheduleListItemBean mScheduleListItemBean;
        int position = -1;

        public ListViewItemOnTouchListener(ViewHolder mViewHolder, ScheduleListItemBean mScheduleListItemBean, int position) {
            this.mViewHolder = mViewHolder;
            this.mScheduleListItemBean = mScheduleListItemBean;
            this.position = position;
        }

        float startX;
        float maxstartX;//记录每次操作第一次点击的起始位置，方便计算累计位移
        float moveX;
        int rawX;
        int maxX;    //累计位移
        long currentMS;
        boolean isCheck = true;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    viewList.clear();
                    if (!viewList.contains(v)) {
                        viewList.add(v);
                    }
                    /*
                    * 点击其他条目的时候将已拖出的删除控件显示的view全部初始化为不显示删除控件
                    * */
                    for (int i = 0; i < viewList.size(); i++) {
                        View view = viewList.get(i);
                        ViewGroup.LayoutParams layoutParam = view.getLayoutParams();
                        layoutParam.width = screenWidth;
                        view.setLayoutParams(layoutParam);

                        dataList.get(i).rawX = screenWidth;
                    }
                    startX = event.getRawX();
                    maxstartX = event.getRawX();
                    currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
                case MotionEvent.ACTION_MOVE:
//                    viewList.clear();
//                    viewList.add(v);
//                    moveX = event.getRawX();
//                    maxX = (int) (moveX - maxstartX);
//                    rawX = (int) (moveX - startX);
//                    if ((Math.abs(measuredWidth) >= Math.abs(maxX) && mViewHolder.content.getWidth() > screenWidth - measuredWidth)) {
//                        /*
//                        * 当删除控件的宽度大于等于累计位移并且内容控件显示的宽度
//                        * （getWidth()用这个获取不用getMeasuredWidth()）
//                        * 大于屏幕宽度与删除控件宽度之差，动态修改内容控件的宽度
//                        * （主要用于向左滑动）
//                        * */
////                        if(rawX > 0 && mViewHolder.content.getWidth() <= screenWidth){
//                        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//                        layoutParams.width = mViewHolder.content.getWidth() + rawX;
//                        v.setLayoutParams(layoutParams);
////                            mTestBean.rawX = layoutParams.width;
//
////                        }
//                    } else if (Math.abs(measuredWidth) >= Math.abs(maxX) && rawX > 0 && mViewHolder.content.getWidth() < screenWidth) {
//                        /*
//                        * 当删除控件的宽度大于累计位移并且是向右滑动并且内容控件显示宽度小于屏幕宽度
//                        * （主要用于向右滑动）
//                        * */
//                        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//                        layoutParams.width = mViewHolder.content.getWidth() + rawX;
//                        v.setLayoutParams(layoutParams);
////                        mTestBean.rawX = layoutParams.width;
//                    } else {
//                        if (rawX <= 0) { //向左滑动
//                            /*
//                            * 左滑内容view宽度等于屏幕宽度减去删除控件宽度
//                            * 设置为左滑允许的最大值
//                            * */
//                            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//                            layoutParams.width = screenWidth - measuredWidth;
//                            v.setLayoutParams(layoutParams);
////                            mTestBean.rawX = layoutParams.width;
//                        } else if (rawX > 0) {//向右滑动
//                            /*
//                            * 右滑到内容控件实际宽度等于屏幕宽度时给予右滑允许最大值
//                            * */
//                            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//                            layoutParams.width = screenWidth;
//                            v.setLayoutParams(layoutParams);
////                            mTestBean.rawX = layoutParams.width;
//                        }
//
//                    }
//                    startX = moveX;
                    return true;
                case MotionEvent.ACTION_UP:
                    long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                    if (Math.abs(rawX) > 10 || moveTime > 200) {
                        isCheck = true;  //true滑动   false点击
                        return true; //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                    } else {
                        ViewGroup.LayoutParams layoutParams1 = mViewHolder.content.getLayoutParams();
                        layoutParams1.width = screenWidth;
                        mViewHolder.content.setLayoutParams(layoutParams1);
                        mOnAdapterItemClick.onAdapterClick(position);
                        isCheck = false;
                        return false;
                    }

            }
            return true;
        }
    }

    OnAdapterItemClick mOnAdapterItemClick;

    public interface OnAdapterItemClick {
        public void onAdapterClick(int position);
    }

    public void setOnAdapterItemClick(OnAdapterItemClick mOnAdapterItemClick) {
        this.mOnAdapterItemClick = mOnAdapterItemClick;
    }
}
