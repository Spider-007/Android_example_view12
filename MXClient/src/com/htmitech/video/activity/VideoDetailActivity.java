package com.htmitech.video.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.htmitech.emportal.R;
import com.htmitech.video.BDVideoView;
import com.htmitech.video.bean.VideoDetailInfo;
import com.htmitech.video.listener.SimpleOnVideoControlListener;
import com.htmitech.video.utils.DisplayUtils;
import com.minxing.kit.internal.BaseActivity;

public class VideoDetailActivity extends BaseActivity {

    private BDVideoView videoView;
    private boolean isFrist = true;

    public static void start(Context context, VideoDetailInfo info) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("info", info);
        intent.putExtra("videoPath", info.getVideoPath());
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_detail);
        VideoDetailInfo info = null;
        try {
            info = (VideoDetailInfo) getIntent().getSerializableExtra("info");
        } catch (Exception e) {

        }
        String videoPath = getIntent().getStringExtra("videoPath");
        String videoName = getIntent().getStringExtra("videoName");
        if (info == null) {
            info = new VideoDetailInfo();
            info.videoPath = videoPath;
            info.title = videoName;
        }

        videoView = (BDVideoView) findViewById(R.id.vv);
        videoView.setOnVideoControlListener(new SimpleOnVideoControlListener() {

            @Override
            public void onRetry(int errorStatus) {
                // TODO: 2017/6/20 调用业务接口重新获取数据
                // get info and call method "videoView.startPlayVideo(info);"
            }

            @Override
            public void onBack() {
//                onBackPressed();
                finish();
            }

            @Override
            public void onFullScreen() {
                DisplayUtils.toggleScreenOrientation(VideoDetailActivity.this);
            }
        });
        videoView.startPlayVideo(info);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        videoView.onStop();
        videoView.onResumeStart("pause");
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        videoView.onStop();
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFrist) {
            isFrist = false;
            videoView.onStart();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        videoView.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        if (!DisplayUtils.isPortrait(this)) {
//            if (!videoView.isLock()) {
//                DisplayUtils.toggleScreenOrientation(this);
//            }
//        } else {
//            super.onBackPressed();
//        }

        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
//        /**
//         * 设置为横屏
//         */
//        if (DisplayUtils.isPortrait(this)) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        videoView.onStart();
        if (!isFrist) {
            videoView.onResumeStart("play");
        }
    }
}
