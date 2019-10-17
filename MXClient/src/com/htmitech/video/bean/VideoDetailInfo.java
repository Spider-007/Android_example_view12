package com.htmitech.video.bean;


public class VideoDetailInfo implements IVideoInfo {

    public String title = "视频";
    public String videoPath;

    @Override
    public String getVideoTitle() {
        return title;
    }

    @Override
    public String getVideoPath() {
        return videoPath;
    }
}
