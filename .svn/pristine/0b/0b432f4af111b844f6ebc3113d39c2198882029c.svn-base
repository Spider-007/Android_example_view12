package com.htmitech.proxy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.MyView.PaintView;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;

import java.io.File;

/**
 * Created by  on 2017/5/5.
 * 反馈意见 涂鸦操作joe
 */

public class DrawPaneActivity extends BaseFragmentActivity implements View.OnClickListener {

    private PaintView paintView;
    private Button chexiao;
    private Button huifu;
    private Button qingkong;
    private ImageButton title_left_button;
    private TextView title_name;
    public TextView title_right_text_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_pane);
        initView();
        initData();
    }


    private void initView() {
        paintView = (PaintView) findViewById(R.id.paint_layout);
        chexiao = (Button) findViewById(R.id.chexiao);
        huifu = (Button) findViewById(R.id.huifu);
        qingkong = (Button) findViewById(R.id.qingkong);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_name = (TextView) findViewById(R.id.title_name);
        title_right_text_button = (TextView) findViewById(R.id.title_right_text_button);
        title_left_button.setOnClickListener(this);
        chexiao.setOnClickListener(this);
        huifu.setOnClickListener(this);
        qingkong.setOnClickListener(this);
        title_right_text_button.setOnClickListener(this);
    }

    private void initData() {

        title_name.setText("编辑图片");
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("filePath");
        String userId = intent.getStringExtra("userId");
        String portal_id = intent.getStringExtra("portal_id");
        String app_id = intent.getStringExtra("app_id");
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepath).copy(Bitmap.Config.ARGB_8888, true);
            paintView.initBitmap(bm,userId,portal_id,app_id);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.chexiao:
                paintView.undo();
                break;
            case R.id.huifu:
                paintView.redo();
                break;
            case R.id.qingkong:
                paintView.removeAllPaint();
                break;
            case R.id.title_left_button:
                showDialog();
                setDialogValue("图片保存中...");
                new Thread() {
                    public void run() {
                        //耗时操作，完成之后更新UI；
                        final String filePath = paintView.saveBitmap();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新UI
                                Intent intent = new Intent();
                                intent.putExtra("result", filePath);
                                setResult(FeedBackActivity.IMAGE_REQUEST_DRAW, intent);
                                dismissDialog();
                                DrawPaneActivity.this.finish();
                            }
                        });
                    }
                }.start();

                break;
            case R.id.title_right_text_button:
                showDialog();
                setDialogValue("图片保存中...");
                new Thread() {
                    public void run() {
                        //耗时操作，完成之后更新UI；
                        final String filePath = paintView.saveBitmap();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新UI
                                Intent intent = new Intent();
                                intent.putExtra("result", filePath);
                                setResult(FeedBackActivity.IMAGE_REQUEST_DRAW, intent);
                                dismissDialog();
                                DrawPaneActivity.this.finish();
                            }
                        });
                    }
                }.start();

                break;
        }

    }
}
