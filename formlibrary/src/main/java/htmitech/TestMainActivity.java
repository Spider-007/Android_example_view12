package htmitech;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.feng.skin.manager.base.BaseActivity;
import htmitech.com.componentlibrary.MediaRecorderWindow;
import htmitech.com.componentlibrary.SelectPicPopupWindow;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.FieldItem;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.entity.PhotoModel;
import htmitech.com.formlibrary.R;
import htmitech.entity.FilePickerMusic;
import htmitech.formConfig.AudioSelect4002;
import htmitech.formConfig.SelectPhoto6001_6002_6101_6102;
import htmitech.listener.ICellOnclick6102;
import htmitech.util.BitmapFactoryUtil;
import htmitech.view.FormContainerView;

/**
 * Created by htrf-pc on 2018-3-19.
 */
public class TestMainActivity extends BaseActivity {
    private FormContainerView mFormContainerView;
    private Map<String, Object> fieldMap = new HashMap<String, Object>();
    private List<EditField> EditFileds = new ArrayList<EditField>(); // 缓存已经编辑的表单字段，回发用。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        mFormContainerView = (FormContainerView) findViewById(R.id.linearlayout_formdetail);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonStr = readRawFile();
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                String tabStrin = jsonObject.getJSONObject("result").getJSONArray("tabItems").getJSONObject(0).toJSONString();
                final InfoTab mHTMRDataTable = JSON.parseObject(tabStrin, InfoTab.class);
                final DocResultInfo mDocResultInfo = JSON.parseObject(jsonStr, DocResultInfo.class);
                TestMainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFormContainerView.setFormMap(new HashMap<String, Object>());
                        mFormContainerView.initData(mHTMRDataTable, mDocResultInfo, new TextView(TestMainActivity.this), EditFileds, fieldMap, "0", 1, 1);
                        mFormContainerView.setmCellOnclickLisener4002_4101(new ICellOnclick6102() {
                            private FieldItem item;
                            private MediaRecorderWindow mMediaRecorderWindow;
                            private SelectPicPopupWindow menuWindow;

                            @Override
                            public <T> void onClick(View v, T t) {
                                item = (FieldItem) t;
                                menuWindow = new SelectPicPopupWindow(TestMainActivity.this, this);
                                if (item.getInput().equals("4001") || item.getInput().equals("4002") || item.getInput().equals("4101") || item.getInput().equals("4102")) {

                                    menuWindow.setFromAudio(item.getInput());

                                } else {

                                    menuWindow.setFromVideo();

                                }
                                //显示窗口
                                menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


                            }

                            @Override
                            public void onClick(View v) {
                                if (v.getId() == R.id.bt_call) {
                                    if (menuWindow.isShowing())
                                        menuWindow.dismiss();
                                    mMediaRecorderWindow = new MediaRecorderWindow(TestMainActivity.this);
                                    mMediaRecorderWindow.setmIMediaRecorederListener(new MediaRecorderWindow.IMediaRecorederListener() {

                                        @Override
                                        public void getMdeiaRecorederFilePath(String filePath,long timer) {
                                            if (mMediaRecorderWindow.isShowing())
                                                mMediaRecorderWindow.dismiss();
                                            FilePickerMusic music = new FilePickerMusic(filePath.substring(filePath.lastIndexOf("/")), filePath, "", "", 0, 0);
                                            Resources res = TestMainActivity.this.getResources();
                                            music.setType(1);
                                            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon_tape_preview);
                                            BitmapFactoryUtil.get().addBitmap(filePath, bmp);
                                            music.setFile_id(item.getFieldId());
                                            music.setDuration(timer);
                                            music.setItem_workflow(item);
                                            ArrayList<FilePickerMusic> currentFilePickerMusic = new ArrayList<FilePickerMusic>();
                                            currentFilePickerMusic.add(music);
                                            ((AudioSelect4002) mFormContainerView.getFormMap().get(music.getFile_id())).updateAudioVideo(TestMainActivity.this, currentFilePickerMusic, 0);
                                        }
                                    });
                                    mMediaRecorderWindow.show(mFormContainerView);
                                } else if (v.getId() == R.id.bt_send) {

                                    if (menuWindow.isShowing())
                                        menuWindow.dismiss();
                                    Intent intent = new Intent(TestMainActivity.this, FilePickerActivity.class);
                                    intent.putExtra("item", item);
                                    intent.putExtra("type", item.getInput());
                                    startActivityForResult(intent, 10);
                                } else if (v.getId() == R.id.bt_save) {
                                    if (menuWindow.isShowing())
                                        menuWindow.dismiss();
                                }
                            }
                        });
                    }
                });
                System.out.print(mHTMRDataTable.toString());
                Log.e("MainActivity", mHTMRDataTable.toString());
            }
        }).start();
    }

    public Uri queryUriforAudio(String path) {
        File file = new File(path);
        String where = MediaStore.Audio.Media.DATA + "='" + file.getAbsolutePath() + "'";
        Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, null, null);
        if (cursor == null) {
            Log.d("uritest", "queryUriforAudio: uri为空 1");
            return null;
        }
        int id = -1;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        if (id == -1) {
            Log.d("uritest", "queryUriforAudio: uri为空 2");
            return null;
        }
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }

    private String readRawFile() {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(R.raw.bi0116);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            content = new String(buffer);
            return content;
        } catch (IOException e) {
            Log.e("write file", e.toString());
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("close file", e.toString());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            ArrayList<FilePickerMusic> currentFilePickerMusic = data.getParcelableArrayListExtra("currentFilePickerMusic");
            if (currentFilePickerMusic != null && currentFilePickerMusic.size() > 0 && mFormContainerView.getFormMap().get(currentFilePickerMusic.get(0).getFile_id()) != null && mFormContainerView.getFormMap().get(currentFilePickerMusic.get(0).getFile_id()) instanceof AudioSelect4002) {
                ((AudioSelect4002) mFormContainerView.getFormMap().get(currentFilePickerMusic.get(0).getFile_id())).updateAudioVideo(this, currentFilePickerMusic, 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapFactoryUtil.get().recycleAll();//释放缓存中的数据
    }
}
