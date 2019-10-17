package com.example.archivermodule.pdfsign;//package com.htmitech.emportal.ui.pdfsign;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.htmitech.emportal.R;
//import com.kinggrid.signatureserver.SignatureInfo;
//
//import java.util.List;
//
//public class GridAdapter extends BaseAdapter {
//
//	class ViewHolder {
//		ImageView image;
//		TextView text;
//	}
//
//	private Context context;
//	private List<SignatureInfo> signatureInfos;
//	private int selectedId;
//
//	public GridAdapter(Context context, List<SignatureInfo> signatureInfos){
//		this.context = context;
//		this.signatureInfos = signatureInfos;
//	}
//
//	public void setSelectedId(int id){
//		selectedId = id;
//	}
//
//	@Override
//	public int getCount() {
//		return signatureInfos.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return signatureInfos.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if(convertView == null){
//			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = inflater.inflate(R.layout.item_layout, null);
//			holder = new ViewHolder();
//			holder.image = (ImageView) convertView.findViewById(R.id.image);
//			holder.text = (TextView) convertView.findViewById(R.id.name);
//
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		byte[] b = signatureInfos.get(position).getSignData();
//		Log.d("tbz","bmp size = " + b.length);
//		Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
//		Log.d("tbz","bitmap config = " + bmp.getConfig());
//		holder.image.setImageBitmap(bmp);
//
//		holder.text.setText(signatureInfos.get(position).getFileName());
//		if(selectedId == position){
//			convertView.setBackgroundColor(Color.BLUE);
//		}else{
//			convertView.setBackgroundColor(Color.TRANSPARENT);
//		}
//		return convertView;
//	}
//
//}
