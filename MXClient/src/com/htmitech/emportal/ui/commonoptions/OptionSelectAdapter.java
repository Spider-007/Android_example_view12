package com.htmitech.emportal.ui.commonoptions;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.commonoptions.data.AddUserOptionsResult;

public class OptionSelectAdapter extends BaseAdapter implements
		View.OnClickListener {
	private Context context;
	private List<AddUserOptionsResult> data;
	private Callback mCallback;
	private boolean isLook = true;

	public OptionSelectAdapter(Context context, List<AddUserOptionsResult> data,Callback callback) {
		super();
		this.context = context;
		
		this.setData(data);
		this.mCallback = callback;
	}

	public interface Callback {
		public void click(View v);
		
	}

	public void setData(List<AddUserOptionsResult> data) {
		if (data == null) {
			data = new ArrayList<AddUserOptionsResult>();
		}
		this.data = data;
	}
	public void setOptionCheckVisiable(boolean isLook){
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
		if(!isLook){
			holder.checkSelect.setVisibility(View.GONE);
		}

		AddUserOptionsResult mAddUserOptionsResult = data.get(position);
		holder.value.setText(mAddUserOptionsResult.getValue());

		holder.checkSelect.setOnClickListener(this);
		holder.checkSelect.setTag(position);

		holder.Delect.setOnClickListener(this);
		holder.Delect.setTag(position);
		holder.Edit.setOnClickListener(this);
		holder.Edit.setTag(position);
		return convertView;
	}

	private class ViewHolder {
		public TextView value, Delect, Edit;
		public ImageView checkSelect;

	}

	@Override
	public AddUserOptionsResult getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void onClick(View v) {

		mCallback.click(v);
		
		
		
		

	}

}
