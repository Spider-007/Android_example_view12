package htmitech;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.adapter.FilePickAdapter;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.unit.Utils;
import htmitech.com.formlibrary.R;
import htmitech.entity.FilePickerMusic;
import htmitech.listener.ISelectFilePickerVideoAudio;
import htmitech.util.BitmapFactoryUtil;


public class FilePickerActivity extends BaseFragmentActivity implements ISelectFilePickerVideoAudio {

    private WebView webview;
    private ListView show_list;
    private TextView title_right_text_button;
    private ArrayList names;
    private ArrayList descs;
    private ArrayList<FilePickerMusic> musics;
    private ArrayList<FilePickerMusic> currentFilePickerMusic;
    private FieldItem item;
    public static final int VIDEO = 0;//音频
    public static final int MOVIE = 1;//视频
    private FilePickAdapter filePickAdapter;
    private FilePickAdapter filePickAdapterMovie;
    private boolean isRadio = true;//是否为单选 true为单选  false 为多选
    private int type;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case VIDEO:
                    filePickAdapter = new FilePickAdapter(FilePickerActivity.this, musics);
                    filePickAdapter.setmISelectFilePickerVideoAudio(FilePickerActivity.this);
                    filePickAdapter.setRadio(isRadio);
                    show_list.setAdapter(filePickAdapter);
                    break;

                case MOVIE:
                    filePickAdapterMovie = new FilePickAdapter(FilePickerActivity.this, musics);
                    filePickAdapterMovie.setmISelectFilePickerVideoAudio(FilePickerActivity.this);
                    filePickAdapterMovie.setRadio(isRadio);
                    show_list.setAdapter(filePickAdapterMovie);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        show_list = (ListView) findViewById(R.id.lv);
        title_right_text_button = (TextView) findViewById(R.id.title_right_text_button);
        ((ImageButton)findViewById(R.id.title_left_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerActivity.this.finish();
            }
        });
        TextView title_name = (TextView)findViewById(R.id.title_name);
        names = new ArrayList();
        descs = new ArrayList();
        musics = new ArrayList();
        Intent intent = getIntent();
        String input = intent.getStringExtra("type");
        item = (FieldItem) intent.getSerializableExtra("item");

        if(item == null){
            item = new FieldItem();
        }
        if (!TextUtils.isEmpty(input)) {
            if (input.equals("4101") || input.equals("4102") || input.equals("4001") || input.equals("4002")) {
                if(input.equals("4101") || input.equals("4102") ){
                    isRadio = false;
                }else{
                    isRadio = true;
                }
                type = 1;
                title_name.setText("请选择音频");
            } else {
                if(input.equals("5101") || input.equals("5102") ){
                    isRadio = false;
                }else{
                    isRadio = true;
                }
                type = 2;
                title_name.setText("请选择视频");
            }
        }
//        type = 1;
        if (type == 1) {           //当type为1的时候为调用音频
            getVideoList();
        } else if (type == 2) { //当type为1的时候为调用视频
            getMovieList();
        }
        title_right_text_button.setVisibility(View.VISIBLE);
        title_right_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("currentFilePickerMusic", currentFilePickerMusic);
                setResult(10, intent);
                FilePickerActivity.this.finish();
            }
        });
        show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 2) {
                    Utils.playVideo(FilePickerActivity.this,musics.get(position).path);
                } else {
                    Utils.playAudio(FilePickerActivity.this,musics.get(position).path);
                }
            }
        });
    }



    /*
    * 获取音频信息
    * */
    public void getVideoList() {
        if (null != musics && musics.size() > 0) {
            musics.clear();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor c = getContentResolver().query(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                            MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                    while (c.moveToNext()) {
                        String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
                        String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                        String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)); // 专辑
                        String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
                        long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                        int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                        int time = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 歌曲的id
                        // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                        FilePickerMusic music = new FilePickerMusic(name, path, album, artist, size, duration);
                        Resources res = FilePickerActivity.this.getResources();
                        music.setType(1);
                        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon_tape_preview);
                        BitmapFactoryUtil.get().addBitmap(path,bmp);
                        music.setFile_id(item.getFieldId());
                        music.setItem_workflow(item);
                        musics.add(music);
                    }
                    //设置adapter
                    Message message = new Message();
                    message.what = VIDEO;
                    mHandler.sendMessage(message);

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /*
    * 获取视频列表
    * */
    public void getMovieList() {
        if (null != musics && musics.size() > 0) {
            musics.clear();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                        String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                        if(!mimeType.contains("mp4")){
                            continue;
                        }
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                        FilePickerMusic music = new FilePickerMusic(title, path, album, artist, size, duration);
                        music.setType(2);
                        music.setFile_id(item.getFieldId());
                        music.setItem_workflow(item);
                        BitmapFactoryUtil.get().addBitmap(path,getVideoThumbnail(path));
                        musics.add(music);
                    }
                    //设置adapter
                    Message message = new Message();
                    message.what = MOVIE;
                    mHandler.sendMessage(message);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void serAdapter() {
        if (type == 1) {
            filePickAdapter.notifyDataSetChanged();
        } else if (type == 2) {
            filePickAdapterMovie.notifyDataSetChanged();
        }
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void checkFilePickerVideoAudio(ArrayList<FilePickerMusic> filePickerMusic) {
        this.currentFilePickerMusic = filePickerMusic;
//        currentFilePickerMusic.setFile_id(item.getFieldId());
//        currentFilePickerMusic.setItem_workflow(item);
    }
}
