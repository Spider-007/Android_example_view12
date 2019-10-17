package com.htmitech.emportal.ui.announcement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.announcement.entity.AnnouncementListResult;
import com.htmitech.emportal.ui.announcement.entity.GetNoticeListByConditionResult;

import java.util.ArrayList;

/**
 * Created by yanxin on 2017-3-30.
 */
public class AnnnounceMentListAdapter extends BaseAdapter {
    public Context mContext;
    public int showimgValue;
    public int showwaysValue;
    /*
    * @param showimgValue   是否列表显示图片
    * @param showwaysValue  列表的展示方式
    * */
    public AnnnounceMentListAdapter(Context context,int showimgValue,int showwaysValue){
        mContext = context;
        this.showimgValue = showimgValue;
        this.showwaysValue = showwaysValue;
    }

    public ArrayList<GetNoticeListByConditionResult> mList;

    public void setData(ArrayList<GetNoticeListByConditionResult> mList){
        this.mList = mList;
        if(mList != null)
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GetNoticeListByConditionResult getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.announceent_list_item, null);
            holder.tv_title = (TextView)convertView.findViewById(R.id.ann_list_title);
            holder.iv_type = (ImageView)convertView.findViewById(R.id.iv_flag);
            holder.left_info =(TextView) convertView.findViewById(R.id.left_info);
            holder.right_info = (TextView)convertView.findViewById(R.id.right_info);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(mList.get(position).title);
        holder.left_info.setText(mList.get(position).otherInfo2);
        holder.right_info.setText(mList.get(position).otherInfo1);
        if(mList.get(position).iconurl != null && !mList.get(position).iconurl.equals("")){
            if(showimgValue == 1)     //是否列表显示图片
                BitmapUtils.instance().display(holder.iv_type,mList.get(position).iconurl);
            else
                holder.iv_type.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView left_info;
        TextView right_info;
        ImageView iv_type;
    }
}
