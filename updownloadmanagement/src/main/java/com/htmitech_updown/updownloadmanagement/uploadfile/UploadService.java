package com.htmitech_updown.updownloadmanagement.uploadfile;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.htmitech_updown.updownloadmanagement.UploadFileContast;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.FileInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018-7-4.
 */

public class UploadService extends Service {
    private static String START_UPLOAD = "START_UPLOAD";

    private ExecutorService executor;//线程池

    private static final int MAX_DOWNLOADING_TASK = 2; //最大同时下载数

    @Override
    public void onCreate() {
        super.onCreate();

        executor = Executors.newFixedThreadPool(MAX_DOWNLOADING_TASK);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //上传文件服务

        try {
            if (START_UPLOAD.equals(intent.getAction())) {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                new UpChunksThread(fileInfo).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    public void addTask(ChunkInfo chunkInfo) {
        executor.submit(new UpChunksThread(chunkInfo));
    }

    /**
     * 上传文件线程
     */
    public class UpChunksThread extends Thread {

        private FileInfo fileInfo;

        public UpChunksThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {

            String upResult;
            if (!fileInfo.isChunk()) {
                ChunkInfo chunkInfo = new ChunkInfo();
                chunkInfo.setGguid(fileInfo.getGguid());
                chunkInfo.setMd5(fileInfo.getMd5());
                chunkInfo.setChunk(0);
                chunkInfo.setChunks(1);
                chunkInfo.setFilePath(fileInfo.getFilePath());
                upResult = new UpFileToServer(UploadService.this).upToServer(chunkInfo, "upchunk");
                System.err.print(upResult);
            } else {
                long fileLength = fileInfo.getFileLength();
                int chunks = (int) (fileLength / UploadFileContast.chunkSize + (fileLength % UploadFileContast.chunkSize > 0 ? 1 : 0));
                for (int i = 0; i < chunks; i++) {//当前分片值从1开始
                    ChunkInfo chunkInfo = new ChunkInfo();
                    chunkInfo.setMd5(fileInfo.getMd5());
                    chunkInfo.setChunk(i);
                    chunkInfo.setChunks(chunks);
                    chunkInfo.setFilePath(fileInfo.getFilePath());
                    upResult = new UpFileToServer(UploadService.this).upToServer(chunkInfo, "upchunks");
                    System.err.print(upResult);
                }
            }


        }
    }
}
