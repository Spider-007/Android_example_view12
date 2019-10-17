package com.htmitech.proxy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.base.MyBaseAdapter;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.detail.CheckForm;
import com.htmitech.proxy.doman.Basic;
import com.htmitech.proxy.doman.ClearCacheDoman;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htrf-pc on 2017/8/29.
 */
public class GeneralDialogAdapter<T> extends MyBaseAdapter<T> {
    private ArrayList<T> generalArrayList;
    private LayoutInflater inflater;
    private boolean isCheckImage = true;//是否存在选中图标
    public GeneralDialogAdapter(Context context, ArrayList<T> generalArrayList,boolean isCheckImage) {
        super(generalArrayList, context);
        this.isCheckImage = isCheckImage;
        this.generalArrayList = generalArrayList;
//        if(generalArrayList != null){
//            for(){
//
//            }
//        }
        inflater = LayoutInflater.from(context);
    }

    public GeneralDialogAdapter(Context context, ArrayList<T> generalArrayList) {
        super(generalArrayList, context);
        this.generalArrayList = generalArrayList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        Basic genneral = (Basic) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_dialog_general_pull_down, null);
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.cb_general = (CheckBox) convertView
                    .findViewById(R.id.cb_general) ;
            holder.ll_top = (LinearLayout) convertView
                    .findViewById(R.id.ll_top);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.cb_general.setVisibility(isCheckImage ? View.VISIBLE : View.GONE);

        holder.cb_general.setChecked(genneral.isCheck);

        holder.tv_title.setText(genneral.checkForm.name);


        holder.tv_title.setTextColor(genneral.isCheck ? getContext().getResources().getColor(R.color.lanse):getContext().getResources().getColor(R.color.black));

        if(!isCheckImage){
            holder.ll_top.setBackgroundResource(genneral.isCheck ? R.color.huise : R.color.white);
        }

        return convertView;
    }

    public void setData(ArrayList<T> datas){
        this.generalArrayList = datas;
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv_title;
        CheckBox cb_general;
        LinearLayout ll_top;
    }

}
