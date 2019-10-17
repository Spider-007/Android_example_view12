package com.htmitech.emportal.ui.document.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.ui.document.DocumentStartOrStopListener;
import com.htmitech.emportal.ui.document.data.FsDocResultList;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by yanxin on 2017-9-5.
 */
public class DocumentSubNodeAndListAdapter extends BaseAdapter {
    private LayoutInflater lInflater;
    private List<FsDocResultList> list = new Vector<FsDocResultList>();

    private Context context;
    private static HashMap<Integer, Boolean> isSelectedMap;// 用来控制CheckBox的选中状况
    private static HashMap<Integer, Integer> isvisibleMap;// 用来控制CheckBox的显示状况
    public boolean needDelete = false;
    private String app_id;
    private DocumentStartOrStopListener startOrStopListener;

    public DocumentSubNodeAndListAdapter(Context context, String app_id, DocumentStartOrStopListener startOrStopListener) {
        this.context = context;
        lInflater = LayoutInflater.from(context);
        isSelectedMap = new HashMap<Integer, Boolean>();
        isvisibleMap = new HashMap<Integer, Integer>();
        this.app_id = app_id;
        this.startOrStopListener = startOrStopListener;
        initDate();
    }


    public List<FsDocResultList> getList() {
        return list;
    }

    public void setList(List<FsDocResultList> list) {
        this.list = list;
    }

    public void AddDocumentNodeAndList(List<FsDocResultList> nodeList, boolean isFirst) {
        if (nodeList == null) return;
        if (this.list != null) {
            if (isFirst) {
                this.list.clear();
            }
            for (int i = 0; i < nodeList.size(); i++) {

                this.list.add(nodeList.get(i));
            }
        }

        initDate();
    }


    public void Clear() {
        if (null != this.list && this.list.size() > 0) {
            for (int i = 0; i < this.list.size(); i++) {
                this.list.clear();
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
    public FsDocResultList getItem(int position) {
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
        FsDocResultList item = (FsDocResultList) getItem(position);
        switch (item.type) {
            case 1:
                DocumentNodeViewHolder nodeHolder;
                nodeHolder = new DocumentNodeViewHolder();
                convertView = lInflater.inflate(R.layout.documentnode_listview_item, null);
                nodeHolder.textView = (TextView) convertView
                        .findViewById(R.id.textview_listitem);
                nodeHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.imageview_listitem);
                FsDocResultList node = (FsDocResultList) list.get(position);
                String bitmapUrl = node.picPath;
                if (bitmapUrl != null && bitmapUrl.length() > 0) {
                    Glide.with(context).load(bitmapUrl).
                            placeholder(R.drawable.icon_folder).
                            error(R.drawable.icon_folder).into(nodeHolder.imageView);
                } else {
                    nodeHolder.imageView.setImageResource(R.drawable.icon_folder);
                }
                nodeHolder.textView.setText(node.name);
                return convertView;
            case 2:
                final DocumentViewHolder docHolder;
                docHolder = new DocumentViewHolder();
                convertView = lInflater.inflate(R.layout.document_listview_item, null);
                docHolder.rl_root = (RelativeLayout) convertView.findViewById(R.id.rl_root);
                docHolder.textView_DocName = (TextView) convertView.findViewById(R.id.textview_listitem);
                docHolder.textView_ModifiedUserName = (TextView) convertView.findViewById(R.id.docInfo_UserName_listitem);
                docHolder.textView_Time = (TextView) convertView.findViewById(R.id.docInfo_Time_listitem);
                docHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview_listitem);
                docHolder.imageView.setTag(docHolder);
                docHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar_document_download);
                docHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_select);
                docHolder.imageViewStart = (ImageView) convertView.findViewById(R.id.iv_document_listview_item_start);
                if (needDelete) {
                    docHolder.checkBox.setVisibility(View.VISIBLE);
                } else {
                    docHolder.checkBox.setVisibility(View.GONE);
                }
                final FsDocResultList doc = (FsDocResultList) list.get(position);
                docHolder.checkBox.setOnClickListener(new DocumentCheckOnclick(doc));
                docHolder.checkBox.setChecked(doc.isCheck);
                Integer imageResource = CommonFileType.MIME_icon_MapTable_Instance().get(doc.extname);
                if (imageResource != null && imageResource != 0) {
                    docHolder.imageView.setImageResource(imageResource);
                }
                docHolder.rl_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (doc.isDownLoadFinish) {
                            startOrStopListener.startDownload(doc);
                            docHolder.progressBar.setVisibility(View.GONE);
                        } else {
                            if (doc.isDownLoading) {
                                startOrStopListener.stopDownload(doc);
                                docHolder.imageViewStart.setImageResource(R.drawable.btn_accessory_time_out);
                                doc.isDownLoading = false;
                            } else if (!doc.isDownLoading) {
                                startOrStopListener.startDownload(doc);
                                docHolder.imageViewStart.setImageResource(R.drawable.btn_accessory_time_start);
                                doc.isDownLoading = true;
                            }
                        }
                    }
                });

                if (doc.isDownLoading && !doc.isDownLoadFinish) {
                    docHolder.progressBar.setVisibility(View.VISIBLE);
                    docHolder.progressBar.setMax(doc.total);
                    docHolder.progressBar.setProgress(doc.current);
                } else if (!doc.isDownLoading || doc.isDownLoadFinish) {
                    docHolder.progressBar.setVisibility(View.GONE);
                }
                if (doc.isDownLoadFinish && !doc.isDownLoading) {
                    docHolder.imageViewStart.setImageResource(R.drawable.btn_accessory_look);
                } else if (!doc.isDownLoadFinish && !doc.isDownLoading) {
                    docHolder.imageViewStart.setImageResource(R.drawable.btn_accessory_download);
                } else if (!doc.isDownLoadFinish && doc.isDownLoading) {
                    docHolder.imageViewStart.setImageResource(R.drawable.btn_accessory_time_start);
                }
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

                File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.filename);
                if (file.exists()) {
                    docHolder.textView_ModifiedUserName.setText("");
//                    docHolder.textView_ModifiedUserName.setBackgroundResource(R.drawable.btn_accessory_look);

                } else {
                    docHolder.textView_ModifiedUserName.setText("");
//                    docHolder.textView_ModifiedUserName.setBackgroundResource(R.drawable.btn_accessory_download);
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
        public ImageView imageViewStart;
        public RelativeLayout rl_root;
    }

    private class DocumentCheckOnclick implements View.OnClickListener {
        private FsDocResultList mDocumentEntity;


        public DocumentCheckOnclick(FsDocResultList mDocumentEntity) {
            this.mDocumentEntity = mDocumentEntity;
        }

        @Override
        public void onClick(View v) {
            mDocumentEntity.isCheck = !mDocumentEntity.isCheck;
        }
    }

    public void canleDelete() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof FsDocResultList) {
                FsDocResultList doc = (FsDocResultList) list.get(i);
                doc.isCheck = false;
            }

        }
    }

    public void selectAllItem() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof FsDocResultList) {
                FsDocResultList doc = (FsDocResultList) list.get(i);
                doc.isCheck = true;
            }


        }
    }

    public void canleAllItem() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof FsDocResultList) {
                FsDocResultList doc = (FsDocResultList) list.get(i);
                doc.isCheck = false;
            }


        }
    }

}
