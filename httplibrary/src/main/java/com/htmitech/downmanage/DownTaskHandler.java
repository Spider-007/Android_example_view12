package com.htmitech.downmanage;

/**
 * Created by htrf-pc on 2016/11/10.
 */
public interface DownTaskHandler {
    //开始下载
    public void startDown();

    //结束下载完成
    public void onSuccess(int postion);

    //下载进度  progress 当前进度， totalLength 总进度
    public void downProgress(int progress, float totalLength);

    //下载错误
    public void onFail(String failMessage);

    //无网络
    public void notNetwork();


}
