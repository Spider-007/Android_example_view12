package com.htmitech.proxy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.DeviceUserListResultItem;

import cn.feng.skin.manager.base.BaseFragmentActivity;

public class DeviceAuditProgressActivity extends BaseFragmentActivity implements View.OnClickListener{

    private ImageView ivAudit;
    private TextView tvAudit;
    private ImageView auditLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_audit_progress);
        ImageButton mBack = (ImageButton)findViewById(R.id.title_left_button);
        TextView tvName = (TextView)findViewById(R.id.title_name);
        ivAudit = (ImageView)findViewById(R.id.iv_audit);
        auditLine = (ImageView)findViewById(R.id.audit_line);
        tvAudit = (TextView)findViewById(R.id.tv_audit);
        tvName.setText("审核进度");
        mBack.setOnClickListener(this);
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        DeviceUserListResultItem deviceUserListResultItem = (DeviceUserListResultItem) intent.getSerializableExtra("deviceUserListResultItem");
        if(null != deviceUserListResultItem){
            if(deviceUserListResultItem.auditStatus.equals("1")){
                ivAudit.setImageResource(R.drawable.icon_finished);
                tvAudit.setTextColor(Color.parseColor("#297BFB"));
                auditLine.setBackgroundColor(Color.parseColor("#297BFB"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_button:
                finish();
                break;
        }
    }
}
