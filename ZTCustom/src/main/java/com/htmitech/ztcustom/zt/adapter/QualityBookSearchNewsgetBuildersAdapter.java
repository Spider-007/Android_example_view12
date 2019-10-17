package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.EventBusMessage;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsgetBuilderBean;
import com.htmitech.ztcustom.zt.util.CharacterParser;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;


/**
 * Created by htmitech on 2018/8/14.
 */

public class QualityBookSearchNewsgetBuildersAdapter extends RecyclerView.Adapter<QualityBookSearchNewsgetBuildersAdapter.MyViewHolder> implements SectionIndexer {

    private Context context;
    private List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderList;
    private MyViewHolder holder;
    private HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
    private int check = 0;

    public QualityBookSearchNewsgetBuildersAdapter(Context context, List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderList) {
        this.context = context;
        this.getBuilderList = getBuilderList;
        initMap(getBuilderList);
        Log.e("check2", "onClick: " + "yiyunxing");
    }


    public void initMap(List<QualityBookSearchNewsgetBuilderBean.ResultsBean> getBuilderList) {
        if (!getBuilderList.isEmpty()) {
            for (int i = 0; i < getBuilderList.size(); i++) {
                hashMap.put(i, false);
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qualitybook_keywordes_search, null);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_search.setText(getBuilderList.get(position).getJsdwname());
        holder.checkboxSearch.setChecked(false);

//        holder.rlQualitybook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new EventBusMessage(getBuilderList.get(position).getJsdwname(), getBuilderList.get(position).getJsdwid()));
//                initMap(getBuilderList);
//                notifyDataSetChanged();
//                if(check == 0){
//                    holder.checkboxSearch.setChecked(false);
//                    check = 1;
//                }else if(check == 1){
//                    holder.checkboxSearch.setChecked(true);
//                    check = 0;
//                }
//
//                if ( holder.checkboxSearch.isChecked()) {
//                    hashMap.put(position, true);
//                } else {
//                    hashMap.put(position, false);
//                }
//
//
//            }
//        });

        holder.checkboxSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view;
                initMap(getBuilderList);
                notifyDataSetChanged();
                if (checkbox.isChecked()) {
                    hashMap.put(position, true);
                    EventBus.getDefault().post(new EventBusMessage(getBuilderList.get(position).getJsdwname(), getBuilderList.get(position).getJsdwid()));
                } else {
                    hashMap.put(position, false);
                }

            }
        });

        if (hashMap != null) {
            holder.checkboxSearch.setChecked(hashMap.get(position));
            Log.e("checked", "onBindViewHolder: "+"zhixingle");
        }

        if (getBuilderList != null) {
            String c = getBuilderList.get(position).getJsdwname().substring(0, 1);
            String s = CharacterParser.getInstance().getSelling(c).toUpperCase();
            char c3 = s.charAt(0);
            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                holder.tv_Header.setVisibility(View.VISIBLE);
                holder.tv_Header.setText("" + c3);
            } else {
                holder.tv_Header.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemCount() {
        return getBuilderList != null ? getBuilderList.size() : 0;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int j = 0; j < getItemCount(); j++) {
            String c = getBuilderList.get(j).getJsdwname().substring(0, 1);
            String s = CharacterParser.getInstance().getSelling(c).toUpperCase();
            char c2 = s.charAt(0);
            if (c2 == section) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        String c = getBuilderList.get(i).getJsdwname().substring(0, 1);
        String s = CharacterParser.getInstance().getSelling(c).toUpperCase();
        char c1 = s.charAt(0);
        return c1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkboxSearch;
        private final TextView tv_search;
        private final RelativeLayout rlQualitybook;
        private final TextView tv_Header;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkboxSearch = (CheckBox) itemView.findViewById(R.id.checkbox_search);
            tv_search = (TextView) itemView.findViewById(R.id.tv_search);
            tv_Header = (TextView) itemView.findViewById(R.id.tv_head);
            rlQualitybook = (RelativeLayout) itemView.findViewById(R.id.rl_qualitybook);

        }
    }


}
