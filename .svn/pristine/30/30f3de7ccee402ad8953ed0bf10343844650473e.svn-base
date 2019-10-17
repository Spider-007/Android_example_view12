package com.htmitech.emportal.ui.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.ui.daiban.DaiBanListFragment;
import com.minxing.client.ClientTabActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class DocAdapter3 extends BaseAdapter {


	private ArrayList<Doc> entityList = new ArrayList<Doc>();
	private Context mContext;
	Boolean isHaveRead;
	boolean isHaveBg = false;
	private static int colorSwap = 0;

	public DocAdapter3(Context context, Boolean isRead) {
		mContext = context;
		isHaveRead = isRead;
	}
	
	public void clearData() {
		entityList.clear();
		notifyDataSetChanged();
	}

	public void setData(boolean needClear,  Vector<Doc> temp) {
		/*if (pullStyle == PULL_REFRESH) {
			entityList.clear();
		} else if (pullStyle == PULL_LOADMORE) {

		}*/

		if (needClear)
			entityList.clear();
		for (Doc entity : temp) {
            entityList.add(entity);
        }

		notifyDataSetChanged();
	}

	public int getCount() {
		if (entityList != null)
			return entityList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entityList.get(position);
	}
	
	@Override
	public int getItemViewType(int position) {
        return isHaveRead ? 1 : 0;
    }

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private String isDate(String dateAndTime){
		//取日期
		String dateAndTimeSwap = dateAndTime;
		String[] date = dateAndTime.split(" ");
		String[] dateNum = date[0].split("-");
		String[] Time = date[1].split(":");

		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		int yesterday = calendar.get(Calendar.DAY_OF_MONTH);

		String yesterdayTmp = "";
		if (yesterday < 10) 
			yesterdayTmp = "0";
		
		//day + ""
		//yesterdayTmp +""+ yesterday
		
		dateAndTimeSwap = dateNum[1] + "-" + dateNum[2] + " " + Time[0] + ":"+ Time[1];
		
		if (dateNum[2].equals(day + "")) {
			dateAndTimeSwap = "今天   " + Time[0] + ":"+ Time[1];
		} else if (dateNum[2].equals(yesterdayTmp +""+ yesterday)) {
			dateAndTimeSwap = "昨天   " + Time[0] + ":"+ Time[1];
		}
		
		return dateAndTimeSwap;
	}
	
	/***
	 * 布局方案一的适配
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//if (DaiBanTopTabIndicator.currentPage == 1) {
			if (DaiBanListFragment.getItemtCount() > 0) {
				if (ClientTabActivity.todoTabItem != null) {
					ClientTabActivity.todoTabItem.showMarker();
				}
			} else {
				if (ClientTabActivity.todoTabItem != null) {
					ClientTabActivity.todoTabItem.hideMarker();
				}
			}
		//}
		ViewHolder holder = null;
        MyListener myListener = null;
		if (convertView == null) {
			holder = new ViewHolder();
            myListener=new MyListener(position);
			convertView = LayoutInflater.from(mContext).inflate(R.layout.todolist_item3, null);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_sendfrom = (TextView) convertView.findViewById(R.id.tv_sendfrom);
            holder.tv_doc = (TextView) convertView.findViewById(R.id.tv_doc);
			holder.iv_type  = (ImageView) convertView.findViewById(R.id.iv_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//修改层级背景
		Doc doc = (Doc) getItem(position);
        doc.getDocType();
        doc.getDocTitle();
        doc.getTodoFlag();

		holder.tv_title.setText(doc.getDocTitle());
		holder.tv_type.setText(doc.getDocType());
//		holder.tv_time.setText(isDate(doc.getSendDate()));
		holder.tv_time.setText(doc.getSendDate());
		holder.tv_sendfrom.setText(doc.getSendFrom());
        holder.tv_doc.setOnClickListener(myListener);
		// 1,已办
		// 0,未办 
		int type = getItemViewType(position);
		switch (type) {
		case 0:
//			if (doc.getIconId() == null || "".equals(doc.getIconId())
//					|| !(
//							doc.getIconId().endsWith(".png") || doc.getIconId().endsWith(".jpg")
//						) )
//				holder.iv_type.setImageResource(R.drawable.icon_email);
//			else
//				BitmapUtils.instance().display(holder.iv_type, doc.getIconId());


			Glide.with(mContext).load(doc.getIconId()).placeholder(R.drawable.icon_email).error(R.drawable.icon_email).transform(new GlideRoundTransform(mContext)).into( holder.iv_type);

			break;
		case 1:
//			if (doc.getIconId() == null || "".equals(doc.getIconId())
//					|| !(
//							doc.getIconId().endsWith(".png") || doc.getIconId().endsWith(".jpg")
//						) )
//				holder.iv_type.setImageResource(R.drawable.icon_email_taken);
//			else
//				BitmapUtils.instance().display(holder.iv_type, doc.getIconId());
			Glide.with(mContext).load(doc.getIconId()).placeholder(R.drawable.icon_email_taken).error(R.drawable.icon_email_taken).transform(new GlideRoundTransform(mContext)).into( holder.iv_type);

			break;
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_title ,tv_type ,tv_time,tv_sendfrom,tv_doc;
		ImageView iv_type;
	}
    private class MyListener implements View.OnClickListener{
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(HtmitechApplication.instance(), entityList.get(mPosition).getDocTitle() + ">>>>>>>" + entityList.get(mPosition).getSendFrom(), Toast.LENGTH_SHORT).show();
        }

    }

	
	
	/***
	 * 布局方案2的测试
	 */
	
	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.todolist_item2, null);
		}
		
		if (isHaveBg) {
			TableRow tableRowItem = (TableRow) convertView.findViewById(R.id.tableRow_item);
			tableRowItem.setBackgroundColor( Color.parseColor("#faf7f7"));
		}
		isHaveBg = !isHaveBg;
		// R.id.itemIcon 
		//1201F9
		Doc doc = getItem(position);
		TextView titleTextView = (TextView) convertView.findViewById(R.id.textview_title);
		titleTextView.setText(doc.getDocTitle());
		
		TextView infoTextView = (TextView) convertView.findViewById(R.id.textview_type);
		infoTextView.setText(doc.getDocType());
		
		TextView dateTextView = (TextView) convertView.findViewById(R.id.textview_senddate);
		dateTextView.setText((doc.getSendDate().split(" "))[0]);
		
		TextView sendformTextView = (TextView) convertView.findViewById(R.id.textview_sendfrom);
		sendformTextView.setText(doc.getSendFrom());
		
		TextView timeTextView = (TextView) convertView.findViewById(R.id.textview_sendtime);
		timeTextView.setText((doc.getSendDate().split(" "))[1]);
		
		ImageView imageType = (ImageView) convertView.findViewById(R.id.imageview_type);
		colorSwap++;
		if (colorSwap > 2 && colorSwap < 4) {
			imageType.setBackgroundColor( Color.parseColor("#1201F9"));
		}else if(colorSwap > 4 && colorSwap < 6) {
			imageType.setBackgroundColor( Color.parseColor("#54F901"));
		}else if(colorSwap > 6 && colorSwap < 8) {
			imageType.setBackgroundColor( Color.parseColor("#FF8909"));
		}else if(colorSwap > 8 && colorSwap < 10) {
			imageType.setBackgroundColor( Color.parseColor("#B81023"));
		}else if(colorSwap > 10) {
			imageType.setBackgroundColor( Color.parseColor("#F8D40D"));
		}
		if (colorSwap > 12) {
			colorSwap = 0;
		}
		
		ImageView iConImage = (ImageView) convertView.findViewById(R.id.itemIcon);
		if (doc.getIconId() == null || "".equals(doc.getIconId())) {
			if (isHaveRead) {
				iConImage.setImageResource(R.drawable.ic_article_old);
			} else {
				iConImage.setImageResource(R.drawable.ic_article_new);
			}
		}
		
		String bitmapUrl = doc.getIconId();
		if (position != getCount()) {
			if (isHaveRead) {
				BitmapUtils.instance().configDefaultLoadFailedImage(R.drawable.ic_article_old);
				//iConImage.setImageResource(R.drawable.ic_article_old);
			} else {
				BitmapUtils.instance().configDefaultLoadFailedImage(R.drawable.ic_article_new);
				//iConImage.setImageResource(R.drawable.ic_article_new);
			}
			BitmapUtils.instance().display(iConImage, bitmapUrl);
		}
		
		return convertView;
	}*/
	
}
