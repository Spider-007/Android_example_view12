package com.htmitech.emportal.ui.document.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.document.data.DocumentNodeEntity;

import java.util.List;

/**
 * 资料库顶层分类，gridview Adapter
 *
 * @author gulbel
 */

public class DocumentGridAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    //键：DocumentNodeEntity   值：用户是否已经选择
    private List<DocumentNodeEntity> list;
    private Context context;

    public DocumentGridAdapter(Context context,
                               List<DocumentNodeEntity> list) {
        this.context = context;
        this.list = list;
        lInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.documentnode_gridview_item, null);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.textview_griditem);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageview_griditem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        DocumentNodeEntity node = (DocumentNodeEntity) list.get(position);

        String bitmapUrl = node.getNodeIconDownloadURL();
//        if (bitmapUrl != null && bitmapUrl.length() > 0) {
//            BitmapUtils.instance().display(holder.imageView, bitmapUrl,new BitmapLoadCallBack<ImageView>() {
//                @Override
//                public void onLoadCompleted(ImageView container, String uri, Bitmap
//                bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
//                    container.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
//                    container.setImageResource(R.drawable.ic_folder);
//                }
//            });
//        }else{
//            holder.imageView.setImageResource(R.drawable.ic_folder);
//        }

        Glide.with(context).load(bitmapUrl).placeholder(R.drawable.icon_folder).error(R.drawable.icon_folder).transform(new GlideRoundTransform(context)).into( holder.imageView);

        holder.textView.setText(node.name);
        /*holder.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewHolder holder = (ViewHolder)v.getTag();
			}
		});*/
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

    }
}
