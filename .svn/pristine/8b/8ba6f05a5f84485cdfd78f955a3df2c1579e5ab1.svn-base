package htmitech.com.componentlibrary.log;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 统计日志log
 * tony
 * 2018年5月10日09:52:49
 */

public class StatisHandler {

    private final static String TAG = "StatisHandler";

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private File dir;
    private String path = "/mnt/sdcard/htmitech/statis/";
    private String fileName;

    private void newFileStatis() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        long timestamp = System.currentTimeMillis();
        String time = formatter.format(new Date());
        fileName = "statis-" + time + "-" + timestamp + ".txt";
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }


    /**
     * 将使用时长的日志保存
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public String saveStatis(String str) {

        try {
            if (dir == null) {
                newFileStatis();
            }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                FileOutputStream fos = new FileOutputStream(path + fileName, true);
                fos.write(str.toString().getBytes());
                fos.write("\n".getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "statis log file...", e);
        }
        return null;
    }


    public void close() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CopyFileUtil.copyFolder(path, "/mnt/sdcard/htmitech/statis_temp");
                    DeleteFileUtil.deleteDirectory(path);
                    dir = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public boolean forceDelete(File f)
    {
        boolean result = false;
        int tryCount = 0;
        while(!result && tryCount++ <10)
        {
            System.gc();
            result = f.delete();
        }
        return result;
    }
}
