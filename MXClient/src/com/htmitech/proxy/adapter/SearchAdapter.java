package com.htmitech.proxy.adapter;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.base.MyBaseAdapter;
import com.htmitech.emportal.R;
import com.htmitech.proxy.activity.SearchActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import htmitech.com.componentlibrary.entity.Dics;

public class SearchAdapter extends MyBaseAdapter<Dics> {

    private LayoutInflater lInflater;
    private List<Dics> datas;
    private String keywrods;
    private int type;
    private SearchActivity.ISearchDelete mISearchDelete;
    public SearchAdapter(List<Dics> datas, String keywrods, Context context) {
        super(datas, context);
        lInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.keywrods = keywrods;
        type = 0;
    }

    public SearchAdapter(List<Dics> datas, String keywrods, Context context, int type, SearchActivity.ISearchDelete mISearchDelete) {
        super(datas, context);
        this.type = type;
        this.mISearchDelete = mISearchDelete;
        lInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.keywrods = keywrods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        Dics mDics = (Dics) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.activity_search_adapter, null);
            holder.tv_search_text_name = (TextView) convertView
                    .findViewById(R.id.tv_search_text_name);

            holder.tv_delete = (TextView) convertView
                    .findViewById(R.id.tv_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(type == 1){
            holder.tv_delete.setVisibility(View.VISIBLE);
        }else{
            holder.tv_delete.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(keywrods)){
            holder.tv_search_text_name.setText(matcherSearchContent(mDics.name,new String[]{keywrods}));
        }else{
            holder.tv_search_text_name.setText(mDics.name);
        }
        holder.tv_delete.setOnClickListener(new DeleteOnClickListener(mDics));
//
        return convertView;
    }

    public class DeleteOnClickListener implements View.OnClickListener{
        public Dics mDics;

        public DeleteOnClickListener(Dics mDics){
            this.mDics = mDics;
        }

        @Override
        public void onClick(View view) {
            if(mISearchDelete != null){
                mISearchDelete.searchDelete(mDics);
            }
        }
    }

    public void setData(List<Dics> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void remove(int postion){
        if(datas.size() > 0){
            datas.remove(postion);
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv_search_text_name;
        TextView tv_delete;
    }


    public SpannableStringBuilder matcherSearchContent(String text, String[] keyword1) {
        String[] keyword = new String[keyword1.length];
        System.arraycopy(keyword1, 0, keyword, 0, keyword1.length);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;
        String wordReg;
        for (int i = 0; i < keyword.length; i++) {
            String key = "";
            //  处理通配符问题
            if (keyword[i].contains("*") || keyword[i].contains("(") || keyword[i].contains(")")) {
                char[] chars = keyword[i].toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                        key = key + "\\" + String.valueOf(chars[k]);
                    } else {
                        key = key + String.valueOf(chars[k]);
                    }
                }
                keyword[i] = key;
            }

            wordReg = "(?i)" + keyword[i];   //忽略字母大小写
            Pattern pattern = Pattern.compile(wordReg);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {

                if (BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 1) {
                    span = new ForegroundColorSpan(getContext().getResources().getColor(com.htmitech.emportal.R.color.ht_hred_title));
                } else if (BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 3) {
                    span = new ForegroundColorSpan(getContext().getResources().getColor(com.htmitech.emportal.R.color.ht_red_dangzheng));
                }  else {
                    span = new ForegroundColorSpan(getContext().getResources().getColor(com.htmitech.emportal.R.color.ht_hred_title));
                }
                spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_MARK_MARK);
            }
        }

        return spannable;
    }
}
