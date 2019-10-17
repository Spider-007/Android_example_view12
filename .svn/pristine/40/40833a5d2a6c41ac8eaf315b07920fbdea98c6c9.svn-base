package com.htmitech.htexceptionmanage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.CircleProgressView;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.minxing.kit.api.bean.MXAppInfo;

import java.util.ArrayList;
import java.util.List;

public class ExceptionGridViewAdapter extends BaseAdapter {

    private List<BinnerBitmapMessage> currentOcuList;
    private final String allDaiban = "96898629507219540"; //收文的appid
    private final String allDaiban_CN = "所有待办";
    private Context context;
    private String todoFlag = "";

    public ExceptionGridViewAdapter(ArrayList<BinnerBitmapMessage> currentOcuList, String todoFlag, String app_id, int mobileconfig_customer_shortcuts, Context mContext) {
        this.context = HtmitechApplication.instance();
        Bitmap daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_oa_todo_style4);
        Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
        if (todoFlag == null) {
            todoFlag = "0";
        }
        if (todoFlag.equals("0")) {
            currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
        } else {
            currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, "全部", "", 0, null, "todo_flag", todoFlag));
        }
//            currentOcuList.add(new BinnerBitmapMessage(add, "", "添加", "", 0, null, "add", todoFlag));
        this.currentOcuList = currentOcuList;

    }

    public List<BinnerBitmapMessage> getCurrentOcuList() {
        return currentOcuList;
    }

    public void setCurrentOcuList(List<BinnerBitmapMessage> currentOcuList) {
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
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        BinnerBitmapMessage mBinnerBitmapMessage = currentOcuList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(HtmitechApplication.instance())
                    .inflate(R.layout.layout_griditem, null);
            holder = new ViewHolder();
            holder.item_text = (TextView) convertView
                    .findViewById(R.id.name_tv);
            holder.iv_icon = (ImageView) convertView
                    .findViewById(R.id.icon_iv);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.delet_iv);
            holder.angle_nulber = (TextView) convertView.findViewById(R.id.angle_nulber);
            holder.img_no_installed = (ImageView) convertView.findViewById(R.id.img_no_installed);
            holder.circleProgressbar = (CircleProgressView) convertView.findViewById(R.id.circleProgressbar);
            ViewGroup.LayoutParams mLayoutParams = holder.iv_icon.getLayoutParams();
            holder.iv_icon.setLayoutParams(mLayoutParams);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.angle_nulber.setTag(mBinnerBitmapMessage.appid);
        mBinnerBitmapMessage.angle_nulber = holder.angle_nulber;
        holder.angle_nulber.setText(mBinnerBitmapMessage.number);
        if (!mBinnerBitmapMessage.number.equals("") && mBinnerBitmapMessage.number.contains("+")) {
            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
            layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
        }

        holder.angle_nulber.setVisibility(mBinnerBitmapMessage.numberFlag);
        currentOcuList.get(position).numberText = holder.angle_nulber;
        if (position != 0 && mBinnerBitmapMessage.appInfo != null) {
            Glide.with(context).load(mBinnerBitmapMessage.appInfo.getPicture_normal()).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).transform(new GlideRoundTransform(context)).into(holder.iv_icon);
        } else {
            if (currentOcuList.get(position).mBitmap != null) {
                holder.iv_icon.setImageBitmap(currentOcuList.get(position).mBitmap);
            }

        }
//		if(mBinnerBitmapMessage.appInfo != null){
//			if(mBinnerBitmapMessage.appInfo.getApk_flag() == 2){
//				holder.img_no_installed.setVisibility(View.VISIBLE);
//				holder.img_no_installed.setImageResource(R.drawable.img_no_installed);
//			}else if(mBinnerBitmapMessage.appInfo.getApk_flag() == 1){
//				holder.img_no_installed.setVisibility(View.VISIBLE);
//				holder.img_no_installed.setImageResource(R.drawable.img_new);
//			}else{
//				holder.img_no_installed.setVisibility(View.GONE);
//			}
//		}


        if (currentOcuList.get(position).Caption.equals("全部")) {
            if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")){
                holder.iv_icon.setImageResource(R.drawable.btn_all_backlog);
            }else if(!TextUtils.isEmpty(todoFlag) && todoFlag.equals("1")){
                holder.iv_icon.setImageResource(R.drawable.btn_all_finished);
            }else{
                holder.iv_icon.setImageResource(R.drawable.btn_all_backlog);
            }
        }
        holder.item_text.setText(currentOcuList.get(position).Caption);
        return convertView;
    }

    class ViewHolder {
        public TextView item_text;
        public ImageView iv_icon;
        public ImageView iv_delete;
        public ImageView img_no_installed;
        public TextView angle_nulber;
        public CircleProgressView circleProgressbar;
    }

}
