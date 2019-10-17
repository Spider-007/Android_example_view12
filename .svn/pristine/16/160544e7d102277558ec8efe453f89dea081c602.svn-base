package htmitech.com.componentlibrary.log;

import android.os.Handler;

import htmitech.com.componentlibrary.listener.ILogUpdateCallListener;

/**
 * Created by Administrator on 2018-6-27.
 */

public class StatisLog implements ILogUpdateCallListener {
    private StatisHandler mStatisHandler;

    @Override
    public void logMessage(String logMesg) {
        synchronized (mStatisHandler) {
            mStatisHandler.saveStatis(logMesg);
        }
    }

    @Override
    public void closeMseeage() {

        synchronized (mStatisHandler) {
            mStatisHandler.close();
        }
    }

    public StatisLog() {
        mStatisHandler = new StatisHandler();
        handler.postDelayed(runnable, 10 * 60 * 1000);// 打开定时器，执行操作
    }

    Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            // 在此处添加执行的代码
            synchronized (mStatisHandler) {
                mStatisHandler.close();
            }
            handler.postDelayed(this, 10 * 60 * 1000);// 10分钟的延迟
        }
    };

}
