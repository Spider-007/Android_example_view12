package com.htmitech.downmanage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 展示不支持断点下载 后面可以进行维护支持
 *
 */
public class DownAsyncTaskManager implements Runnable {

    private Context context;
    private static final String TAG = DownAsyncTaskManager.class
            .getSimpleName();
    private int timeOut = 10 * 1000;// 超时

    public boolean isCover = false; //是否覆盖
    public int postion;
    public String url;
    public String filePath;
    public DownTaskHandler handler;

    public DownAsyncTaskManager(String url,int postion, String filePath, DownTaskHandler handler) {
        this.url = url;
        this.filePath = filePath;
        this.handler = handler;
        this.postion = postion;
    }




    @Override
    public void run() {
        Message msg = new Message();
        msg.what = 1;
        mHandler.sendMessage(msg);
        startDownFile();
    }


    public String startDownFile(){
        String filePath = this.filePath;
        String uploadUrl = url;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        Message msg = new Message();
        try {
            int down_step = 1;// 提示step
            int totalSize;// 文件总大小
            int downloadCount = 0;// 已经下载好的大小
            int updateCount = 0;// 已经上传的文件大小
            InputStream inputStream;
            OutputStream outputStream;

            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setConnectTimeout(timeOut);
            httpURLConnection.setReadTimeout(timeOut);
            // 获取下载文件的size
            totalSize = httpURLConnection.getContentLength();
            if (httpURLConnection.getResponseCode() == 404) {
                msg = new Message();
                msg.what = 5;
                mHandler.sendMessage(msg);
               return "";
            }
            inputStream = httpURLConnection.getInputStream();
            File updateFile = new File(filePath);
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            outputStream = new FileOutputStream(filePath, isCover);// 文件存在则覆盖掉
            byte buffer[] = new byte[1024];
            int readsize = 0;

            while ((readsize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readsize);
                downloadCount += readsize;// 时时获取下载到的大小
                /**
                 * 每次增张1%
                 */
                if (updateCount == 0
                        || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                    updateCount += down_step;
                    msg = new Message();
                    msg.what = 2;
                    msg.arg1 = updateCount;
                    msg.obj = 100 + "";
                    mHandler.sendMessage(msg);

                }

            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            inputStream.close();
            outputStream.close();
            if (downloadCount > 0) {
                msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);

                return "下载成功";
            } else {
                msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);
                return "下载失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new Message();
            msg.what = 3;
            mHandler.sendMessage(msg);
            return "下载失败";
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String o = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    handler.startDown();
                    break;
                case 2:
                    int updateCount = msg.arg1;
                    handler.downProgress(updateCount, Float.parseFloat(o));
                    break;
                case 3:
                    handler.onSuccess(postion);
                    break;
                case 4:
                    handler.onFail("下载失败");
                    break;
                case 5:
                    handler.onFail("下载失败：404");
                    break;
            }
        };
    };

}

