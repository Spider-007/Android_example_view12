package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.EventBusMessage;
import com.htmitech.ztcustom.zt.constant.QualityBookSearchNewsReceivegoodsBean;
import com.htmitech.ztcustom.zt.util.CharacterParser;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;


/**
 * Created by htmitech on 2018/8/14.
 */

public class QualityBookSearchNewsReceivegoodsAdapter extends RecyclerView.Adapter<QualityBookSearchNewsReceivegoodsAdapter.MyViewHolder> implements SectionIndexer {

    private Context context;
    private List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsList;
    private MyViewHolder myViewHolder;
    private HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();

    public QualityBookSearchNewsReceivegoodsAdapter(Context context, List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsList) {
        this.context = context;
        this.receiveGoodsList = receiveGoodsList;
        initMap(receiveGoodsList);
    }

    public void initMap(List<QualityBookSearchNewsReceivegoodsBean.ResultsBean> receiveGoodsList) {
        if(!receiveGoodsList.isEmpty()){
            for (int i = 0; i < receiveGoodsList.size(); i++) {
                hashMap.put(i,false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qualitybook_keywordes_search, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.tv_search.setText(receiveGoodsList.get(position).getShdwname());
        holder.checkboxSearch.setChecked(false);
//        holder.rlQualitybook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        holder.checkboxSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventBusMessage(receiveGoodsList.get(position).getShdwname(),receiveGoodsList.get(position).getShdwid()));
                CheckBox checkbox = (CheckBox) view;
                initMap(receiveGoodsList);
                notifyDataSetChanged();
                if(checkbox.isChecked()){
                    hashMap.put(position,true);
                }else{
                    hashMap.put(position,false);
                }
            }
        });

        if(hashMap != null){
            holder.checkboxSearch.setChecked(hashMap.get(position));
        }

        try {
            if (receiveGoodsList != null) {
                String c = receiveGoodsList.get(position).getShdwname().substring(0, 1);
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
        }catch (Exception e) {
                e.printStackTrace();
            }


    }




    @Override
    public int getItemCount() {
        return receiveGoodsList != null ? receiveGoodsList.size() : 0;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        try {
            for (int j = 0; j < getItemCount(); j++) {
                String c = receiveGoodsList.get(j).getShdwname().substring(0, 1);
                String s = CharacterParser.getInstance().getSelling(c).toUpperCase();
                char c2 = s.charAt(0);
                if (c2 == section) {
                    return j;
                }
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        try {
        String c = receiveGoodsList.get(i).getShdwname().substring(0, 1);
        String s = CharacterParser.getInstance().getSelling(c).toUpperCase();
        char c1 = s.charAt(0);
        return c1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox checkboxSearch;
        private final TextView tv_search;
        private final RelativeLayout rlQualitybook;
        private final TextView tv_Header;


        public MyViewHolder(View itemView) {
            super(itemView);
            checkboxSearch = (CheckBox) itemView.findViewById(R.id.checkbox_search);
            tv_search = (TextView) itemView.findViewById(R.id.tv_search);
            rlQualitybook = (RelativeLayout) itemView.findViewById(R.id.rl_qualitybook);
            tv_Header = (TextView) itemView.findViewById(R.id.tv_head);
        }
    }



}
