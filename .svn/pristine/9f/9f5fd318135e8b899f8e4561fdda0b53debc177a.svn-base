package htmitech.com.componentlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import htmitech.com.componentlibrary.datepicker.wheelview.OnWheelScrollListener;
import htmitech.com.componentlibrary.datepicker.wheelview.WheelView;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.ArrayWheelAdapter;
import htmitech.com.componentlibrary.datepicker.wheelview.adapter.NumericWheelAdapter;
import htmitech.com.componentlibrary.media_unit.AudioRecoderUtils;
import htmitech.com.componentlibrary.media_unit.PopupWindowFactory;
import htmitech.com.componentlibrary.media_unit.TimeUtils;


/**
 * tony
 */
public class MediaRecorderWindow {

    private Context context;
    private PopupWindow pop;
    private View view;
    private String[] singleChoiceArray;

    private TextView tv_media_start;
    private TextView mTextView;
    private Button bu_media_ok;
    private Button bt_media_opean;
    private ImageView mImageView;
    private ImageView iv_tape;
    private PopupWindowFactory mPop;
    private LinearLayout ll;
    private LinearLayout ll_tape;

    private String currentPath;

    private AudioRecoderUtils mAudioRecoderUtils;

    private long timer;

    private IMediaRecorederListener mIMediaRecorederListener;

    public IMediaRecorederListener getmIMediaRecorederListener() {
        return mIMediaRecorederListener;
    }

    public void setmIMediaRecorederListener(IMediaRecorederListener mIMediaRecorederListener) {
        this.mIMediaRecorederListener = mIMediaRecorederListener;
    }

    public void setCheckForm() {
        initData();
    }

    public MediaRecorderWindow(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.pop_media_recorder_popwindow, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        initView();
        initPop();
        initData();
    }

    public PopupWindow getPop() {
        return this.pop;
    }

    public void initView() {
        tv_media_start = (TextView) view.findViewById(R.id.tv_media_start);
        bu_media_ok = (Button) view.findViewById(R.id.bu_media_ok);
        ll_tape = (LinearLayout) view.findViewById(R.id.ll_tape);
        iv_tape = (ImageView) view.findViewById(R.id.iv_tape);
        bt_media_opean = (Button) view.findViewById(R.id.bt_media_opean);
        ll = (LinearLayout) view.findViewById(R.id.ll);
    }

    private void initPop() {
        pop.setAnimationStyle(R.style.AnimBottom);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        dw.setAlpha(100);
        pop.setBackgroundDrawable(dw);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
//        lp.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public void initData() {
        //PopupWindow的布局文件
        View view = View.inflate(context, R.layout.layout_microphone, null);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                timer = time;
                mTextView.setText(TimeUtils.long2String(time));

            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                currentPath = filePath;
                mTextView.setText(TimeUtils.long2String(0));
            }
        });
        mPop = new PopupWindowFactory(context, view);

        //Button的touch监听
        ll_tape.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        mPop.showAtLocation(vie, Gravity.CENTER, 0, 0);
                        iv_tape.setImageResource(R.drawable.btn_tape_in_pause);
                        mAudioRecoderUtils.startRecord();
                        tv_media_start.setText("松开保存");
                        bt_media_opean.setText("播放");
                        bu_media_ok.setVisibility(View.VISIBLE);
                        break;

                    case MotionEvent.ACTION_UP:
                        iv_tape.setImageResource(R.drawable.btn_tape_in_play);
                        mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
//                        mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        mPop.dismiss();
                        tv_media_start.setText("点击录音");
                        break;
                }
                return true;
            }
        });

        bt_media_opean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(currentPath)) {
                    playAudio(currentPath);
                } else {
                    if (pop.isShowing()) {
                        pop.dismiss();
                    }
                }

            }
        });
        bt_media_opean.setText("取消");
        bu_media_ok.setVisibility(View.GONE);
        bu_media_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIMediaRecorederListener != null && currentPath != null) {
                    mIMediaRecorederListener.getMdeiaRecorederFilePath(currentPath, timer);
                }
            }
        });

    }

    private View vie;

    public void show(View view) {
        this.vie = view;
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        pop.dismiss();
    }

    /**
     * 播放指定名称的歌曲
     *
     * @param audioPath 指定默认播放的音乐
     */
    public void playAudio(String audioPath) {
        Intent mIntent = new Intent();
        mIntent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file:///" + audioPath);
        mIntent.setDataAndType(uri, "audio/mp3");
        context.startActivity(mIntent);
    }


    public interface IMediaRecorederListener {
        public void getMdeiaRecorederFilePath(String filePath, long times);
    }

    public boolean isShowing() {
        return pop.isShowing();
    }
}
