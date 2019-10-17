package htmitech.com.componentlibrary;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import htmitech.com.componentlibrary.media_unit.TimeUtils;

/**
 * Created by Administrator on 2018-7-13.
 */

public class MediaPlayerActivity extends cn.feng.skin.manager.base.BaseFragmentActivity {
    private SeekBar pro_media_player;
    private ImageView iv_pause;
    private TextView tv_current_time;
    private TextView tv_end_time;
    private String url;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private TimerTask task;
    private ImageButton title_left_button;
    private TextView title_name;

    private static final int UPDATE = 0;

    private int currentSeek;
    private boolean isStop;
    private MyHandler handler;

    private VoisePlayingIcon voise_playint_icon;

    public class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case UPDATE:
                        if (!isStop) {
                            if (mediaPlayer != null) {
                                tv_current_time.setText(TimeUtils.long2String(mediaPlayer.getCurrentPosition()));
                                pro_media_player.setProgress(mediaPlayer.getCurrentPosition());
                            }

                        }
                }
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        handler = new MyHandler(this);
        initView();
        initData();
    }

    private void initView() {
        pro_media_player = (SeekBar) findViewById(R.id.pro_media_player);
        iv_pause = (ImageView) findViewById(R.id.iv_pause);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        title_name = (TextView) findViewById(R.id.title_name);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        voise_playint_icon = (VoisePlayingIcon) findViewById(R.id.voise_playint_icon);

    }

    private void initData() {
        url = getIntent().getStringExtra("url");
//        url = "http://fs.open.kugou.com/5b553339d8bdbe15fb3c69fd3d5526a5/5b4878a8/G011/M02/1C/1F/q4YBAFT9pMCAOGqeAEY5zXPVvDs683.mp3";
        title_name.setText("音频播放");
        pro_media_player.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                if (b) {//<span style="color:#ff0000;"> 注意点</span>
                    try {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

        }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        startMusic();
        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerActivity.this.finish();
            }
        });
    }

    private void startMusic() {
        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            if (url.startsWith("http://")) {
                mediaPlayer.setDataSource(this, Uri.parse(url));
            } else {
                mediaPlayer.setDataSource(url);

            }
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.prepare();//同步的准备方法。
            mediaPlayer.prepareAsync();//异步的准备

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    mediaPlayer.reset();
                    voise_playint_icon.start();
                    mediaPlayer.start();
//                    mediaPlayer.seekTo(currentSeek);
//                    start.setEnabled(false);
                    iv_pause.setImageResource(R.drawable.btn_tape_pause);
                    int max = mediaPlayer.getDuration();
                    tv_end_time.setText(TimeUtils.long2String(max));
                    pro_media_player.setMax(max);

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isStop = true;

                    int max = mediaPlayer.getDuration();
                    tv_current_time.setText(TimeUtils.long2String(max));
                    voise_playint_icon.stop();
                    currentSeek = pro_media_player.getProgress();
                    iv_pause.setImageResource(R.drawable.btn_tape_play);
                }
            });
            iv_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        currentSeek = pro_media_player.getProgress();
                        pauseMusic();
                        voise_playint_icon.stop();
                        iv_pause.setImageResource(R.drawable.btn_tape_play);
                    } else {
                        isStop = false;
                        mediaPlayer.start();
                        voise_playint_icon.start();
                        iv_pause.setImageResource(R.drawable.btn_tape_pause);
                    }
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {

                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(UPDATE);
                        }
                    };
                    timer.schedule(task, 0, 1000);

                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseMusic() {

        if (mediaPlayer != null)
            mediaPlayer.pause();

    }


    private void rePlayMusic() {
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        voise_playint_icon.stop();
        if(timer != null)
            timer.cancel();
        if(task != null)
            task.cancel();
        iv_pause.setImageResource(R.drawable.btn_tape_play);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
}
