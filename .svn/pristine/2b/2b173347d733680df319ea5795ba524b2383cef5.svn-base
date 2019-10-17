package com.htmitech.emportal.ui.document.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.ui.document.data.DocumentBaseEntity;
import com.htmitech.emportal.ui.document.data.DocumentEntity;
import com.htmitech.emportal.ui.document.data.DocumentNodeEntity;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * 资料库顶层分类，gridview Adapter
 *
 * @author gulbel
 */

public class DocumentListAdapter extends BaseAdapter {


    private LayoutInflater lInflater;
    private List<DocumentBaseEntity> list = new Vector<DocumentBaseEntity>();
    private List<DocumentBaseEntity> list1 = new Vector<DocumentBaseEntity>();
    private Context context;
    private static HashMap<Integer, Boolean> isSelectedMap;// 用来控制CheckBox的选中状况
    private static HashMap<Integer, Integer> isvisibleMap;// 用来控制CheckBox的显示状况
    public boolean needDelete = false;
    private String app_id;
    public DocumentListAdapter(Context context,String app_id) {
        this.context = context;
        lInflater = LayoutInflater.from(context);
        isSelectedMap = new HashMap<Integer, Boolean>();
        isvisibleMap = new HashMap<Integer, Integer>();
        this.app_id = app_id;
        initDate();
    }


    public List<DocumentBaseEntity> getList() {
        return list;
    }

    public void setList(List<DocumentBaseEntity> list) {
        this.list = list;
    }


    public void AddDocumentNodeList(List<DocumentNodeEntity> nodeList) {
        if (nodeList == null) return;
        if (this.list != null && list.size() > 0) {
            if (list1 != null) {
                list1.clear();
            }
            for (DocumentBaseEntity l : list
                    ) {
                list1.add(l);
            }
            list.clear();
        }
        for (int i = 0; i < nodeList.size(); i++) {

            this.list.add(nodeList.get(i));
        }
        for (DocumentBaseEntity l : list1
                ) {
            list.add(l);

        }
        initDate();
    }

    public void AddDocumentList(List<DocumentBaseEntity> fileList) {
        if (fileList == null) return;
        for (int i = 0; i < fileList.size(); i++) {
            this.list.add(fileList.get(i));
        }
        initDate();
    }

    public void Clear() {
        if(null !=this.list && this.list.size() > 0 ){
            for (int i = 0; i < this.list.size(); i++){
                this.list.clear();
            }
        }
        if(null !=  list1 && this.list1.size() > 0){
            for (int i = 0; i < this.list1.size(); i++){
                this.list1.clear();
            }
        }
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

    public static HashMap<Integer, Boolean> getIsSelectedMap() {
        return isSelectedMap;
    }

    public static HashMap<Integer, Integer> getIsvisibleMap() {
        return isvisibleMap;
    }

    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelectedMap().put(i, false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        DocumentBaseEntity item = (DocumentBaseEntity) getItem(position);
        switch (item.getType()) {
            case DocumentBaseEntity.TYPE_DOCUMENT_NODE:
                /*DocumentNodeViewHolder nodeHolder;
                if (convertView == null) {
					nodeHolder = new DocumentNodeViewHolder();
					convertView = lInflater.inflate(R.layout.documentnode_listview_item, null);
					nodeHolder.textView = (TextView) convertView
							.findViewById(R.id.textview_listitem);
					nodeHolder.imageView = (ImageView) convertView
							.findViewById(R.id.imageview_listitem);
					nodeHolder.imageView.setTag(nodeHolder);
					convertView.setTag(nodeHolder);
				} else {
					nodeHolder = (DocumentNodeViewHolder) convertView.getTag();
				}*/

                DocumentNodeViewHolder nodeHolder;
                nodeHolder = new DocumentNodeViewHolder();
                convertView = lInflater.inflate(R.layout.documentnode_listview_item, null);
                nodeHolder.textView = (TextView) convertView
                        .findViewById(R.id.textview_listitem);
                nodeHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.imageview_listitem);
//                nodeHolder.imageView.setTag(nodeHolder);
                DocumentNodeEntity node = (DocumentNodeEntity) list.get(position);
                String bitmapUrl = node.getNodeIconDownloadURL();
                if (bitmapUrl != null && bitmapUrl.length() > 0) {
                    Glide.with(context).load(bitmapUrl).
                            placeholder(R.drawable.icon_folder).
                            error(R.drawable.icon_folder).into(nodeHolder.imageView);
//                    BitmapUtils.instance().display(nodeHolder.imageView, bitmapUrl, new BitmapLoadCallBack<ImageView>() {
//                        @Override
//                        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
//                            container.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
//                            container.setImageResource(R.drawable.ic_folder);
//                        }
//                    });
                }else{
                    nodeHolder.imageView.setImageResource(R.drawable.icon_folder);
                }
                nodeHolder.textView.setText(node.name);
                /*nodeHolder.imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DocumentNodeViewHolder nodeHolder = (DocumentNodeViewHolder)v.getTag();
					}
				});*/
                return convertView;
            case DocumentBaseEntity.TYPE_DOCUMENT:
                DocumentViewHolder docHolder;
				/*if (convertView == null) {
					docHolder = new DocumentViewHolder();
					convertView = lInflater.inflate(R.layout.document_listview_item, null);
					docHolder.textView_DocName = (TextView) convertView
							.findViewById(R.id.textview_listitem);
					docHolder.textView_ModifiedUserName = (TextView) convertView
							.findViewById(R.id.docInfo_UserName_listitem);
					docHolder.textView_Time = (TextView) convertView
							.findViewById(R.id.docInfo_Time_listitem);
					docHolder.imageView = (ImageView) convertView
							.findViewById(R.id.imageview_listitem);
					docHolder.imageView.setTag(docHolder);
					convertView.setTag(docHolder);
				} else {
					docHolder = (DocumentViewHolder) convertView.getTag();
				}*/


                docHolder = new DocumentViewHolder();
                convertView = lInflater.inflate(R.layout.document_listview_item, null);
                docHolder.textView_DocName = (TextView) convertView
                        .findViewById(R.id.textview_listitem);
                docHolder.textView_ModifiedUserName = (TextView) convertView
                        .findViewById(R.id.docInfo_UserName_listitem);
                docHolder.textView_Time = (TextView) convertView
                        .findViewById(R.id.docInfo_Time_listitem);
                docHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.imageview_listitem);
                docHolder.imageView.setTag(docHolder);

                docHolder.progressBar = (ProgressBar) convertView
                        .findViewById(R.id.progressbar_document_download);

                docHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_select);

                if (needDelete) {
                    docHolder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    docHolder.checkBox.setVisibility(View.GONE);
                }
                DocumentEntity doc = (DocumentEntity) list.get(position);
                docHolder.checkBox.setOnClickListener(new DocumentCheckOnclick(doc));
                docHolder.checkBox.setChecked(doc.isCheck);
				/*String bitmapExtUrl = doc.getDocExtName(); //文件类型图片
				if (bitmapExtUrl!=null && bitmapExtUrl.length() > 0)
					BitmapUtils.instance().display(docHolder.imageView, bitmapUrl);*/

                Integer imageResource = CommonFileType.MIME_icon_MapTable_Instance().get(doc.extname);
                if (imageResource != null && imageResource != 0)
                    docHolder.imageView.setImageResource(imageResource);


                docHolder.textView_DocName.setText(doc.filename);
                docHolder.textView_ModifiedUserName.setText(doc.updateBy);
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d = format1.parse(doc.createTime.replace('T', ' '));
                    docHolder.textView_Time.setText(format1.format(d));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator +app_id + File.separator + doc.filename);
                if (file.exists()) {
                    docHolder.textView_ModifiedUserName.setText("已下载");
                } else {
                    docHolder.textView_ModifiedUserName.setText("未下载");
                }
                return convertView;
            default:
                return null;
        }

    }

    public class DocumentNodeViewHolder {
        ImageView imageView;
        TextView textView;

    }

    public class DocumentViewHolder {
        public ImageView imageView;
        public TextView textView_DocName;
        public TextView textView_ModifiedUserName;
        public TextView textView_Time;
        public ProgressBar progressBar;
        public CheckBox checkBox;
    }

    private class DocumentCheckOnclick implements View.OnClickListener {
        private DocumentEntity mDocumentEntity;


        public DocumentCheckOnclick(DocumentEntity mDocumentEntity) {
            this.mDocumentEntity = mDocumentEntity;
        }

        @Override
        public void onClick(View v) {
            mDocumentEntity.isCheck = !mDocumentEntity.isCheck;
        }
    }

    public void canleDelete() {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof DocumentEntity){
                DocumentEntity doc = (DocumentEntity) list.get(i);
                doc.isCheck = false;
            }

        }
    }

    public void selectAllItem() {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof DocumentEntity){
                DocumentEntity doc = (DocumentEntity) list.get(i);
                doc.isCheck = true;
            }


        }
    }

    public void canleAllItem() {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof DocumentEntity){
                DocumentEntity doc = (DocumentEntity) list.get(i);
                doc.isCheck = false;
            }


        }
    }

}