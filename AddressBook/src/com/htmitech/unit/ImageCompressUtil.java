package com.htmitech.unit;

import android.content.Context;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 图片压缩工具类，封装Luban压缩工具
 * @author joe
 * @date 2017-8-10 10:37:12
 */

public class ImageCompressUtil {

    private onCompressFileCallBack compressFileCallBack;
    private File file;
    private Context context;

    public ImageCompressUtil(Builder builder) {
        this.file = builder.file;
        this.compressFileCallBack = builder.compressFileCallBack;
        this.context = builder.context;
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }
    public ImageCompressUtil setCompressFile(File mFile){

        try {
            Luban.with(context)
                    .load(mFile) //传人要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            compressFileCallBack.onCompressStart();
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            compressFileCallBack.onCompressFileResult(file);
                        }
                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            throw new RuntimeException(e);
                        }
                    }).launch();
        } catch (Exception e) {
            compressFileCallBack.onCompressError(e);
        }

        return this;
    }

    public interface onCompressFileCallBack {
         void onCompressStart();
         void onCompressFileResult(File file);
         void onCompressError(Exception e);
    }

    public static class Builder{
        private Context context;
        private onCompressFileCallBack compressFileCallBack;
        private File file;
        public Builder(Context context) {
            this.context = context;
        }
        public Builder load(File file) {
            this.file = file;
            return this;
        }
        public Builder setCompressListener(onCompressFileCallBack listener) {
            this.compressFileCallBack = listener;
            return this;
        }
        private ImageCompressUtil build() {
            return new ImageCompressUtil(this);
        }
        public void launch() {
            build().setCompressFile(file);
        }

    }



}
