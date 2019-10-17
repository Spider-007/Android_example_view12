package com.htmitech.htworkflowformpluginnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.htworkflowformpluginnew.entity.UserOptionsResult;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunxl on 2018-1-26.
 */
public class OptionSelectAdapter extends BaseAdapter {
    private Context context;
    private List<UserOptionsResult> data;
    private Callback mCallback;
    private boolean isLook = true;

    public OptionSelectAdapter(Context context, List<UserOptionsResult> data, Callback callback) {
        super();
        this.context = context;

        this.setData(data);
        this.mCallback = callback;
    }

    public interface Callback {
        public void click(View v, String optionText, String optionId);

    }

    public void setData(List<UserOptionsResult> data) {
        if (data == null) {
            data = new ArrayList<UserOptionsResult>();
        }
        this.data = data;
    }

    public void setOptionCheckVisiable(boolean isLook) {
        this.isLook = isLook;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void removeItem(int position) {

        data.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int mPosition = position;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.options_select_item, null);
            holder.value = (TextView) convertView
                    .findViewById(R.id.tv_options_text);
            holder.checkSelect = (ImageView) convertView
                    .findViewById(R.id.iv_check_select);
            holder.Delect = (TextView) convertView
                    .findViewById(R.id.tv_delselect);
            holder.Edit = (TextView) convertView
                    .findViewById(R.id.tv_editselect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!isLook) {
            holder.checkSelect.setVisibility(View.GONE);
        }

        UserOptionsResult mAddUserOptionsResult = data.get(position);
        holder.value.setText(mAddUserOptionsResult.opinionText);

        holder.checkSelect.setOnClickListener(new OptionOnClickListener(mAddUserOptionsResult.opinionText, mAddUserOptionsResult.opinionId));
        holder.checkSelect.setTag(position);

        if (mAddUserOptionsResult.getOpinionType() == 0) {
            holder.Delect.setVisibility(View.GONE);
            holder.Edit.setVisibility(View.GONE);
        } else if (mAddUserOptionsResult.getOpinionType() == 1) {
            holder.Delect.setVisibility(View.VISIBLE);
            holder.Edit.setVisibility(View.VISIBLE);
            holder.Delect.setOnClickListener(new OptionOnClickListener(mAddUserOptionsResult.opinionText, mAddUserOptionsResult.opinionId));
            holder.Delect.setTag(position);
            holder.Edit.setOnClickListener(new OptionOnClickListener(mAddUserOptionsResult.opinionText, mAddUserOptionsResult.opinionId));
            holder.Edit.setTag(position);
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView value, Delect, Edit;
        public ImageView checkSelect;

    }

    @Override
    public UserOptionsResult getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class OptionOnClickListener implements View.OnClickListener {

        String optionText;
        String optionId;

        public OptionOnClickListener(String optionText, String optionId) {
            this.optionText = optionText;
            this.optionId = optionId;
        }

        @Override
        public void onClick(View v) {
            mCallback.click(v, optionText, optionId);
        }
    }
}

