package com.htmitech.htworkflowformpluginnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.app.Constant;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.daiban.DaiBanListFragment;
import com.htmitech.htworkflowformpluginnew.entity.Doc;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowHaveDoneListFragment;
import com.minxing.client.ClientTabActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class WorkFlowFormAdapter extends BaseAdapter {


    private ArrayList<Doc> entityList = new ArrayList<Doc>();
    private Context mContext;
    Boolean isHaveRead;
    boolean isHaveBg = false;
    private static int colorSwap = 0;
    public boolean IS_GYL ;
    public WorkFlowHaveDoneListFragment.WorkFlowAdapterCheck mWorkFlowAdapterCheck;

    public WorkFlowHaveDoneListFragment.WorkFlowAdapterCheck getmWorkFlowAdapterCheck() {
        return mWorkFlowAdapterCheck;
    }

    public void setmWorkFlowAdapterCheck(WorkFlowHaveDoneListFragment.WorkFlowAdapterCheck mWorkFlowAdapterCheck) {
        this.mWorkFlowAdapterCheck = mWorkFlowAdapterCheck;
    }

    public WorkFlowFormAdapter(Context context, Boolean isRead) {
        mContext = context;
        isHaveRead = isRead;
    }

    public void clearData() {
        entityList.clear();
        notifyDataSetChanged();
    }

    public void setData(boolean needClear, Vector<Doc> temp) {
        if (temp == null) {
            temp = new Vector<Doc>();
        }
        if (needClear)
            entityList.clear();
        for (Doc entity : temp) {
            entityList.add(entity);
        }

        notifyDataSetChanged();
    }

    public int getCount() {
        if (entityList != null)
            return entityList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return entityList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return isHaveRead ? 1 : 0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private String isDate(String dateAndTime) {
        //取日期
        String dateAndTimeSwap = dateAndTime;
        String[] date = dateAndTime.split(" ");
        String[] dateNum = date[0].split("-");
        String[] Time = date[1].split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int yesterday = calendar.get(Calendar.DAY_OF_MONTH);

        String yesterdayTmp = "";
        if (yesterday < 10)
            yesterdayTmp = "0";

        //day + ""
        //yesterdayTmp +""+ yesterday

        dateAndTimeSwap = dateNum[1] + "-" + dateNum[2] + " " + Time[0] + ":" + Time[1];

        if (dateNum[2].equals(day + "")) {
            dateAndTimeSwap = "今天   " + Time[0] + ":" + Time[1];
        } else if (dateNum[2].equals(yesterdayTmp + "" + yesterday)) {
            dateAndTimeSwap = "昨天   " + Time[0] + ":" + Time[1];
        }

        return dateAndTimeSwap;
    }

    /***
     * 布局方案一的适配
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (DaiBanTopTabIndicator.currentPage == 1) {
        if (DaiBanListFragment.getItemtCount() > 0) {
            if (ClientTabActivity.todoTabItem != null) {
                ClientTabActivity.todoTabItem.showMarker();
            }
        } else {
            if (ClientTabActivity.todoTabItem != null) {
                ClientTabActivity.todoTabItem.hideMarker();
            }
        }
        //}
        ViewHolder holder = null;
        MyListener myListener = null;
        if (convertView == null) {
            holder = new ViewHolder();
            myListener = new MyListener(position);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.todolist_item3, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_sendfrom = (TextView) convertView.findViewById(R.id.tv_sendfrom);
            holder.tv_doc = (TextView) convertView.findViewById(R.id.tv_doc);
            holder.tv_caption = (TextView) convertView.findViewById(R.id.tv_caption); //说明的字段
            holder.tv_check = (TextView) convertView.findViewById(R.id.tv_check);
            holder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //修改层级背景
        Doc doc = (Doc) getItem(position);
        doc.getFlowName();
        doc.getDocTitle();
        doc.getTodoFlag();

        holder.tv_title.setText(doc.getDocTitle());
        holder.tv_type.setText(doc.getFlowName());
//		holder.tv_time.setText(isDate(doc.getSendDate()));
        holder.tv_time.setText(doc.getSendDate());
        holder.tv_sendfrom.setText(doc.getSendFrom() == null ? "" : doc.getSendFrom());
        holder.tv_doc.setOnClickListener(myListener);
        // 1,已办
        // 0,未办
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                Glide.with(mContext).load(doc.getIconUrl()).placeholder(R.drawable.icon_email).error(R.drawable.icon_email).transform(new GlideRoundTransform(mContext)).into(holder.iv_type);
                break;
            case 1:
                Glide.with(mContext).load(doc.getIconUrl()).placeholder(R.drawable.icon_email_taken).error(R.drawable.icon_email_taken).transform(new GlideRoundTransform(mContext)).into(holder.iv_type);
                break;
        }
        if(Constant.IS_DZKF){
            holder.tv_title.setMaxLines(3);
            holder.tv_check.setVisibility(View.VISIBLE);
        }else{
            holder.tv_title.setMaxLines(2);
            holder.tv_check.setVisibility(View.GONE);
        }
        if(IS_GYL){
            holder.tv_check.setVisibility(View.VISIBLE);
            holder.tv_check.setSelected(doc.isCheck());
            holder.tv_check.setOnClickListener(new CheckOnclick(doc,holder.tv_check) {
            });
        }else{
            holder.tv_check.setVisibility(View.GONE);

        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title, tv_type, tv_time, tv_sendfrom, tv_doc,tv_caption;
        TextView tv_check;
        ImageView iv_type;
    }

    private class MyListener implements View.OnClickListener {
        int mPosition;

        public MyListener(int inPosition) {
            mPosition = inPosition;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(HtmitechApplication.instance(), entityList.get(mPosition).getDocTitle() + ">>>>>>>" + entityList.get(mPosition).getSendFrom(), Toast.LENGTH_SHORT).show();
        }

    }


    public class CheckOnclick implements View.OnClickListener{
        public Doc doc;
        private TextView tv_check;
        public CheckOnclick(Doc doc,TextView tv_check){
            this.doc = doc;
            this.tv_check = tv_check;
        }

        @Override
        public void onClick(View view) {
            if(mWorkFlowAdapterCheck != null){


                tv_check.setSelected(!doc.isCheck());
                doc.setCheck(!doc.isCheck());

                mWorkFlowAdapterCheck.selectAdapterCheck(doc);
            }

        }
    }
}
