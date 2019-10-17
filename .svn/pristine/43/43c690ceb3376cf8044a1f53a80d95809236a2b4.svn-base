package com.htmitech_updown.updownloadmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.adapter.UploadFinishDeleteAdapter;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.listener.UploadFinishDeleteCheckBoxClickListener;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;

import java.util.ArrayList;
import java.util.List;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.log.DeleteFileUtil;

public class UploadFinishDeleteActivity extends cn.feng.skin.manager.base.BaseFragmentActivity implements View.OnClickListener, UploadFinishDeleteCheckBoxClickListener {

    private TextView tvName;
    private ImageButton imageButtonBack;
    private TextView textViewCancle;
    private TextView textViewSelectAll;
    private Button buttonDelete;
    private ListView listView;
    private List<UploadFileInfoBean> uploadFileInfoBeanList;
    private UploadFinishDeleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_finish_delete);
        initView();
        initDate();
    }

    private void initView() {
        tvName = (TextView) findViewById(R.id.title_name);
        tvName.setText("已选择0个文件");
        textViewCancle = (TextView) findViewById(R.id.title_left_text_button);
        textViewCancle.setVisibility(View.VISIBLE);
        textViewCancle.setOnClickListener(this);
        textViewSelectAll = (TextView) findViewById(R.id.title_right_text_button);
        textViewSelectAll.setVisibility(View.VISIBLE);
        textViewSelectAll.setText("全选");
        textViewSelectAll.setOnClickListener(this);
        imageButtonBack = (ImageButton) findViewById(R.id.title_left_button);
        imageButtonBack.setVisibility(View.GONE);
        buttonDelete = (Button) findViewById(R.id.bt_upload_finish_delete);
        buttonDelete.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lv_upload_finish_delete);
    }

    private void initDate() {
        uploadFileInfoBeanList = new ArrayList<>();
        uploadFileInfoBeanList = DbUtil.getUploadFileInfoFinishList();
        adapter = new UploadFinishDeleteAdapter(this, uploadFileInfoBeanList, this);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_left_text_button) {//取消
            finish();
        } else if (v.getId() == R.id.title_right_text_button) {//全选
            if (textViewSelectAll.getText().equals("全选")) {
                textViewSelectAll.setText("取消全选");
                adapter.selectAll();
            } else if (textViewSelectAll.getText().equals("取消全选")) {
                textViewSelectAll.setText("全选");
                adapter.cancleAll();
            }
        } else if (v.getId() == R.id.bt_upload_finish_delete) {//删除按钮
            delectFile();
        }
    }

    public void delectFile() {
        boolean hasSelect = false;
        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
            if (uploadFileInfoBeanList.get(i).delectChecked) {
                hasSelect = true;
                Log.e("DelectFile", uploadFileInfoBeanList.get(i).createTime);
                DbUtil.delectFile(uploadFileInfoBeanList.get(i).TASK_ID);
                DeleteFileUtil.deleteFile(uploadFileInfoBeanList.get(i).filePath);
            }
        }
        if (!hasSelect) {
            Toast.makeText(this, "您还未选择要删除的文件，请选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoFinishList();
        if (this.uploadFileInfoBeanList != null) {
            this.uploadFileInfoBeanList.clear();
            this.uploadFileInfoBeanList.addAll(uploadFileInfoBeanList);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckBoxClickListener() {
        int count = 0;
        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
            if (uploadFileInfoBeanList.get(i).delectChecked) {
                count++;
            }
        }
        tvName.setText("已选择" + count + "个文件");
    }
}
