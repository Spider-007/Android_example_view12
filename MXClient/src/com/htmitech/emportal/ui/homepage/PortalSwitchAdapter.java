package com.htmitech.emportal.ui.homepage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.EmpPortal;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

/**
 * Created by yanxin on 2017-8-23.
 */
public class PortalSwitchAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<EmpPortal> dataList = new ArrayList<EmpPortal>();
    private LayoutInflater Inflater;
    public   ViewHolder holder;
    public EmpPortal currentPortal;
    public int curPosition = -1;
    public ArrayList<EmpPortal> portalAllList;
    public PortalSwitchAdapter(Context context, ArrayList dataList,EmpPortal currentPortal,ArrayList<EmpPortal> portalAllList){
        this.context = context;
        this.dataList = dataList;
        this.currentPortal = currentPortal;
        this.portalAllList = portalAllList;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public EmpPortal getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
             holder = new ViewHolder();
            convertView = Inflater.inflate(R.layout.portal_switch_popwindow_item, null);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.tv_num = (TextView) convertView
                    .findViewById(R.id.tv_num);
            holder.tv_select_flag = (TextView) convertView
                    .findViewById(R.id.tv_select_flag);
            holder.iv_icon = (ImageView) convertView
                    .findViewById(R.id.iv_icon);
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.ll_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(dataList.get(position).getPortal_name());
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
        boolean aBoolean = sp.getBoolean(portalAllList.get(position).getPortal_id(), false);
        Glide.with(HtmitechApplication.getInstance()).load(getItem(position).portal_logo).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).transform(new GlideRoundTransform(context)).into(holder.iv_icon);
        holder.tv_select_flag.setSelected(getItem(position).isCheck);
        holder.tv_name.setSelected(getItem(position).isCheck);
        if(aBoolean){
            holder.tv_num.setVisibility(View.VISIBLE);
        }else{
            holder.tv_num.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        LinearLayout ll_root;
        ImageView iv_icon;
        TextView tv_select_flag;
        TextView tv_num;
    }
    public void selectIndex(int postion) {
        this.curPosition = postion;
    }
}
