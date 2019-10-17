package com.htmitech.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.htmitech.utils.CompressUtil;
import com.htmitech.utils.FileNameGenerator;
import com.htmitech.utils.MD5FileNameGenerator;
import com.htmitech.utils.OAConText;

import java.io.File;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.content.ComponentConstant;
import htmitech.com.componentlibrary.listener.ObserverCallBack;

//解压
public class FileThread implements Runnable {
    public ObserverCallBack mObserverCallBack;
    private Context context;
    private String zipName;

    public FileThread(Context context, String zipName, ObserverCallBack mObserverCallBack) {
        this.zipName = zipName;
        this.mObserverCallBack = mObserverCallBack;
        this.context = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Message msg = new Message();
        try {

//			String[] spUrl = zipUrl.toString().split("/");
//			int len = spUrl.length;
//			String endUrl = spUrl[len - 1];
            Log.d("FiledThread", "zipUrl === " + zipName);
//			String zipName = zipUrl.split(".")[0];
            /** 解压 **/
            ComponentInit.getInstance().getSuccess().setProgressbar("正在解压文件...");
            String destination = ComponentConstant.SDCARD_PATH;
            File srcFile = new File(destination + "/" + zipName);
            if (!srcFile.exists()) {
                Log.e("123", "");
                srcFile.mkdirs();
            }
            try {
                File[] files = null;
                if (ComponentConstant.NEW_SYS_INTERFACE) {
                    FileNameGenerator fileNameGenerator = new MD5FileNameGenerator();
                    String password = fileNameGenerator.generate(OAConText.getInstance(context.getApplicationContext()).login_name);
                    files = CompressUtil.unzip(srcFile, destination, password);
                } else {
                    files = CompressUtil.unzip(srcFile, destination, "password");
                }

                Log.d("fileThread", "" + files[0].getPath());
                //紧接着解析TXT文件 并插入数据库
                ReadFile mReadFile = new ReadFile(context);
                mReadFile.readTxtFile(files[0]);
                msg.what = 1;
                msg.obj = "加载完成";
                mHandler.sendMessage(msg);
                Log.d("FiledThread", "文件解压成功！！");
            } catch (Exception e) {
                msg.obj = e.getMessage();
                msg.what = 2;
                mHandler.sendMessage(msg);
                e.printStackTrace();
                Log.d("FiledThread", "文件解压失败！！");
            }

        } catch (Exception e) {
            msg.obj = e.getMessage();
            msg.what = 2;
            mHandler.sendMessage(msg);
            e.printStackTrace();
            Log.d("FiledThread", "文件解压失败！！");
        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String o = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    mObserverCallBack.callbackMainUI(o);
                    break;
                case 2:
                    mObserverCallBack.fail(o);
                    break;
            }
        }

        ;
    };
}
