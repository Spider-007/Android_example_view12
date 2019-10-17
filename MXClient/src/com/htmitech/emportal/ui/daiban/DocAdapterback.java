/*package com.htmitech.emportal.ui.daiban;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.Doc;




public class DocAdapter extends ArrayAdapter<Doc> {
	private Context mContext;
	Boolean isHaveRead;
	boolean isHaveBg = false;
	
	public DocAdapter(ArrayList<Doc> Docs, Context context, Boolean isRead) {
		super(context, R.layout.todolist_item, Docs);
		mContext = context;
		isHaveRead = isRead;
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.todolist_item, null);
		}
		
		
		if (isHaveBg) {
			TableRow tableRowItem = (TableRow) convertView.findViewById(R.id.tableRow_item);
			tableRowItem.setBackgroundColor( Color.parseColor("#faf7f7"));
		}
		isHaveBg = !isHaveBg;
		// R.id.itemIcon 
		Doc doc = getItem(position);
		TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
		titleTextView.setText(doc.getDocTitle());
		TextView infoTextView = (TextView) convertView.findViewById(R.id.info);
		infoTextView.setText(doc.getDocType());
		TextView timeTextView = (TextView) convertView.findViewById(R.id.time);
		timeTextView.setText(isDate(doc.getSendDate()));
		
		ImageView iConImage = (ImageView) convertView.findViewById(R.id.itemIcon);
		if (doc.getIconId() == null || "".equals(doc.getIconId()) 
				|| !(doc.getIconId().endsWith(".png")) || !(doc.getIconId().endsWith(".jpg")) ) {
			if (isHaveRead) {
				iConImage.setImageResource(R.drawable.ic_article_old);
			} else {
				iConImage.setImageResource(R.drawable.ic_article_new);
			}
		}
		else
			BitmapUtils.instance().display(iConImage, doc.getIconId());
		
		
		if (position != getCount()) {
			if (isHaveRead) {
				BitmapUtils.instance().configDefaultLoadFailedImage(R.drawable.ic_article_old);
				//iConImage.setImageResource(R.drawable.ic_article_old);
			} else {
				BitmapUtils.instance().configDefaultLoadFailedImage(R.drawable.ic_article_new);
				//iConImage.setImageResource(R.drawable.ic_article_new);
			}
			
			
		}
		
		return convertView;
	}
	
}
*/