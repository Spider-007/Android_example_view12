package htmitech.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import htmitech.FilePickerActivity;
import htmitech.com.formlibrary.R;
import htmitech.entity.FilePickerMusic;
import htmitech.listener.ISelectFilePickerVideoAudio;
import htmitech.util.BitmapFactoryUtil;

/**
 * Created by Administrator on 2018/7/6.
 */

public class FilePickAdapter extends BaseAdapter {
    public ArrayList<FilePickerMusic> dataList;
    private LayoutInflater Inflater;
    public Context context;
    private boolean isRadio = true;//是否为单选 true为单选  false 为多选
    private ISelectFilePickerVideoAudio mISelectFilePickerVideoAudio;
    private ArrayList<FilePickerMusic> currentFilePickers;
    public boolean isRadio() {
        return isRadio;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    public ISelectFilePickerVideoAudio getmISelectFilePickerVideoAudio() {
        return mISelectFilePickerVideoAudio;
    }

    public void setmISelectFilePickerVideoAudio(ISelectFilePickerVideoAudio mISelectFilePickerVideoAudio) {
        this.mISelectFilePickerVideoAudio = mISelectFilePickerVideoAudio;
    }

    public FilePickAdapter(Context context, ArrayList data) {
        Inflater = LayoutInflater.from(context);
        currentFilePickers = new ArrayList<>();
        this.dataList = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public FilePickerMusic getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = Inflater.inflate(R.layout.file_pick_adapter, null);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.name);
            mViewHolder.cbSelect = (TextView) convertView.findViewById(R.id.cb_select);
            mViewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            mViewHolder.iv_perview = (ImageView) convertView.findViewById(R.id.iv_perview);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.cbSelect.setVisibility(View.VISIBLE);
        mViewHolder.cbSelect.setSelected(dataList.get(position).isCheck);
        mViewHolder.cbSelect.setOnClickListener(new checkChangeListener(dataList.get(position)));
        mViewHolder.tv_size.setText(Formatter.formatFileSize(context, Long.valueOf(dataList.get(position).size))+"");
        mViewHolder.iv_perview.setImageBitmap(BitmapFactoryUtil.get().getBitmap(dataList.get(position).getPath()));
        mViewHolder.name.setText(dataList.get(position).name);
        return convertView;
    }

    public class ViewHolder {
        public TextView name;
        public TextView cbSelect;
        public TextView tv_size;
        public ImageView iv_perview;

    }

    public class checkChangeListener implements View.OnClickListener {
        FilePickerMusic selectItem;

        public checkChangeListener(FilePickerMusic selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        public void onClick(View v) {
            if(isRadio){ //单选
                if (null != dataList) {
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).isCheck) {
                            dataList.get(i).isCheck = false;
                        }
                    }
                }
                selectItem.isCheck = !selectItem.isCheck;
                if(mISelectFilePickerVideoAudio != null){
                    currentFilePickers.clear();
                    currentFilePickers.add(selectItem);
                    mISelectFilePickerVideoAudio.checkFilePickerVideoAudio(currentFilePickers);
                }
                ((FilePickerActivity) context).serAdapter();
            }else{ // 多选
                selectItem.isCheck = !selectItem.isCheck;
                if(currentFilePickers.contains(selectItem)){
                    currentFilePickers.remove(selectItem);
                }else{
                    currentFilePickers.add(selectItem);
                }
                if(mISelectFilePickerVideoAudio != null){
                    mISelectFilePickerVideoAudio.checkFilePickerVideoAudio(currentFilePickers);
                }
                ((FilePickerActivity) context).serAdapter();
            }

        }
    }
}

