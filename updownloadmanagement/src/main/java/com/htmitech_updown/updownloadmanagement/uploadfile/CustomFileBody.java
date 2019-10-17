package com.htmitech_updown.updownloadmanagement.uploadfile;

import com.htmitech_updown.updownloadmanagement.UploadFileContast;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;

import org.apache.http.entity.mime.content.AbstractContentBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2018-7-4.
 */

public class CustomFileBody extends AbstractContentBody {
    private File file = null;
    private int chunk = 0;  //第几个分片
    private int chunks = 1;  //总分片数
    private long chunkLength = UploadFileContast.chunkSize; //分片大小1MB
    private boolean isStop = false;
    private ChunkInfo chunkInfo;

    public CustomFileBody(File file) {
        this(file, "application/octet-stream");
    }

    public CustomFileBody(ChunkInfo chunkInfo) {
        this(new File(chunkInfo.getFilePath()), "application/octet-stream");
        this.chunk = chunkInfo.getChunk();
        this.chunks = chunkInfo.getChunks();
        this.chunkInfo = chunkInfo;
        this.file = new File(chunkInfo.getFilePath());
        if (this.chunk == this.chunks) {
            //先不判断，固定1M
            //this.chunkLength=this.file.length()-(this)
        }
    }

    public CustomFileBody(File file, String mimeType) {
        super(mimeType);
        if (file == null) {
            throw new IllegalArgumentException("File may not be null");
        } else {
            this.file = file;
        }
    }


    @Override
    public String getFilename() {
        return this.file.getName();
    }


    public String getCharset() {
        return null;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }


    public String getTransferEncoding() {
        return "binary";
    }

    public long getContentLength() {
        return this.file.length();
    }


    public void writeTo(OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        } else {
            //不使用FileInputStream
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.file, "r");
            try {
                //int size = 1024 * 1;//1KB缓冲区读取数据
                byte[] tmp = new byte[1024];
                //randomAccessFile.seek(chunk * chunkLength);
                if (chunk + 1 < chunks) {//中间分片
                    randomAccessFile.seek(chunk * chunkLength);
                    int n = 0;
                    long readLength = 0;//记录已读字节数
                    while (readLength <= chunkLength - 1024) {
                        n = randomAccessFile.read(tmp, 0, 1024);
//                        if (chunkInfo.getUploadStatus() == UploadStatus.UPLOAD_STATUS_PAUSE || chunkInfo.getUploadStatus() == UploadStatus.UPLOAD_STATUS_ERROR) {//点击了暂停上传
//                            chunkInfo.setDownLoading(false);
//                            //需要记住当前片值 以及当前生产的一个MD5的一个随机key可以进入数据库进行查找
//
//                            DbUtil.addUpDownState(chunkInfo);
//                            return;
//                        }
                        readLength += 1024;
                        out.write(tmp, 0, n);
                    }
                    if (readLength <= chunkLength) {
                        n = randomAccessFile.read(tmp, 0, (int) (chunkLength - readLength));
                        out.write(tmp, 0, n);
                    }
                } else {
                    randomAccessFile.seek(chunk * chunkLength);
                    int n = 0;
                    while ((n = randomAccessFile.read(tmp, 0, 1024)) != -1) {

//                        if (chunkInfo.getUploadStatus() == UploadStatus.UPLOAD_STATUS_PAUSE || chunkInfo.getUploadStatus() == UploadStatus.UPLOAD_STATUS_ERROR) {//点击了暂停上传
//                            chunkInfo.setDownLoading(false);
//                            //需要记住当前片值 以及当前生产的一个MD5的一个随机key可以进入数据库进行查找
//                            DbUtil.addUpDownState(chunkInfo);
//                            return;
//                        }

                        out.write(tmp, 0, n);
                    }
                }
                out.flush();
            } finally {
                randomAccessFile.close();
            }
        }
    }

    public File getFile() {
        return this.file;
    }
}
