package com.htmitech.proxy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.ztcustom.zt.chinarailway.ServiceBookActivity;

import cn.feng.skin.manager.base.BaseActivity;

public class PlatformLead extends BaseActivity {
    private ImageButton leftButton;
    private ImageButton title_left_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        leftButton = (ImageButton) findViewById(R.id.title_left_button);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        TextView lead = (TextView) findViewById(R.id.tv_lead);
        TextView appcenter = (TextView) findViewById(R.id.tv_appcenter);
        TextView call = (TextView) findViewById(R.id.tv_call);
        TextView work = (TextView) findViewById(R.id.tv_work);
        TextView platform = (TextView) findViewById(R.id.tv_platform);
        work.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), AppcenterImageActivity.class);
                intent.putExtra("source", "work");
                startActivity(intent);

            }
        });

        call.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), AppcenterImageActivity.class);
                intent.putExtra("source", "call");
                startActivity(intent);

            }
        });

        appcenter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), AppcenterImageActivity.class);
                intent.putExtra("source", "app");
                startActivity(intent);

            }
        });

        lead.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), LeadActivity.class);
                startActivity(intent);

            }
        });
        platform.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), ServiceBookActivity.class);
                startActivity(intent);

            }
        });

        leftButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finishWithAnimation();

            }
        });

    }

    protected void finishWithAnimation() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

}
