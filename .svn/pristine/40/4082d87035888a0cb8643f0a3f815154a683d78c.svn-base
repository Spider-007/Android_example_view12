package com.htmitech.emportal.ui.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.ClassifyView;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.minxing.kit.api.bean.MXAppInfo;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<BinnerBitmapMessage> currentOcuList;
    private MXAppInfo showAllTodoOcuInfo, showAllYibanInfo, showAddressBook;
    private final String flag = "OA_todo_";
    private final String allDaiban = "OA_todo_AllHasDone";
    private final String allDaiban_CN = "所有待办";

    private final String allYiban = "OA_todo_AllToDo";
    private final String allYiban_CN = "所有已办";

    private final String addressBook = "addressBook";
    private final String getAddressBook_CN = "通讯录";

    private PortalHomeFragmentActivity mContext;
    private int countPerPage = 15;

    public ListViewAdapter(List<BinnerBitmapMessage> ocuList, PortalHomeFragmentActivity mContext) {
        this.mContext = mContext;
        showAllTodoOcuInfo = new MXAppInfo();
        showAllYibanInfo = new MXAppInfo();
        showAddressBook = new MXAppInfo();
        if (this.currentOcuList != null) {
            currentOcuList.clear();
        }
        currentOcuList = ocuList;
    }

    public List<BinnerBitmapMessage> getCurrentOcuList() {
        return currentOcuList;
    }

    public void setCurrentOcuList(List<BinnerBitmapMessage> currentOcuList) {
        if (this.currentOcuList != null) {
            currentOcuList.clear();
        }
        this.currentOcuList = currentOcuList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return currentOcuList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return currentOcuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        BinnerBitmapMessage message = currentOcuList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(HtmitechApplication.instance())
                    .inflate(R.layout.layout_home_listitem, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.rl_clasify = (RelativeLayout) convertView.findViewById(R.id.rl_clasify);
            holder.rl_not_classify = (RelativeLayout) convertView.findViewById(R.id.rl_not_classify);
            holder.classify_view = (ClassifyView) convertView.findViewById(R.id.classify_view);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.iv_right = (ImageView) convertView.findViewById(R.id.iv_right);
            holder.img_no_installed = (ImageView) convertView.findViewById(R.id.img_no_installed);
            holder.tv_line = (TextView) convertView.findViewById(R.id.tv_line);
            holder.angle_nulber = (TextView) convertView.findViewById(R.id.angle_nulber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.angle_nulber.setTag(message.appid);
        message.angle_nulber = holder.angle_nulber;
        holder.tv_name.setText(message.appInfo.getApp_shortname());
        holder.angle_nulber.setText(message.number);
        if(!message.number.equals("") && message.number.contains("+")){
            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(mContext, 20),DeviceUtils.dip2px(mContext,15));
            layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            message.angle_nulber.setLayoutParams(layoutParms);
        }

        holder.angle_nulber.setVisibility(message.numberFlag);
//        if (message.mBitmap != null && !message.mBitmap.equals("")) {
//            holder.iv_icon.setImageBitmap(message.mBitmap);
//        } else {
//            holder.iv_icon.setImageResource(R.drawable.icon_app_centre_normal);
//        }

        if(message.appInfo.getApp_type() == 7){
//            holderView.classify_view.getBackground().setAlpha(120);
            holder.classify_view.setVisibility(View.VISIBLE);

            holder.classify_view.initAppInfo(message.appInfo.getClassifyAppInfo(),10,10);
//			mViewHolder.class_angle_nulber.setVisibility(View.GONE);
//            holderView.class_angle_nulber.setText("20");
            holder.rl_not_classify.setVisibility(View.GONE);
            holder.rl_clasify.setVisibility(View.VISIBLE);
        }else{
            holder.rl_clasify.setVisibility(View.GONE);
            holder.rl_not_classify.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(message.appInfo.getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(mContext)).into(holder.iv_icon);
        holder.iv_right.setTag(position);
        if(message.appInfo.getApk_flag() == 2){
            holder.img_no_installed.setVisibility(View.VISIBLE);
            holder.img_no_installed.setImageResource(R.drawable.img_no_installed);
        }else if(message.appInfo.getApk_flag() == 1){
            holder.img_no_installed.setVisibility(View.VISIBLE);
            holder.img_no_installed.setImageResource(R.drawable.img_new);
        }else{
            holder.img_no_installed.setVisibility(View.GONE);
        }
        holder.tv_line.setVisibility(View.GONE);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
        private ImageView iv_icon;
        private ImageView iv_right;
        private TextView tv_line;
        private TextView angle_nulber;
        private RelativeLayout rl_clasify;
        private ClassifyView classify_view;
        private RelativeLayout rl_not_classify;
        private ImageView img_no_installed;
    }
}
