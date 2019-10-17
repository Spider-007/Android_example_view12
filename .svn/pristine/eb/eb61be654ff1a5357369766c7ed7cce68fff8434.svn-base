package htmitech.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import htmitech.com.componentlibrary.entity.FieldItem;

/**
 * Created by Administrator on 2018/7/6.
 * 文件选择-音频
 */

public class FilePickerMusic implements Parcelable {
    public String path;
    public String fileMediumUrl;
    public String name;
    public String album;
    public String artist;
    public long size;
    public long duration;
    public int time;
    public boolean isCheck;//是否被选择

    private FieldItem item_workflow;


    private String file_id;

    private int type;// 1 音频 2 视频


    public FilePickerMusic(){

    }

    public FilePickerMusic(String name, String path, String album, String artist, long size, long duration) {
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.name = name;
        this.path = path;
        this.size = size;
    }

    protected FilePickerMusic(Parcel in) {
        path = in.readString();
        fileMediumUrl = in.readString();
        name = in.readString();
        album = in.readString();
        artist = in.readString();
        size = in.readLong();
        duration = in.readLong();
        time = in.readInt();
        isCheck = in.readByte() != 0;
        file_id = in.readString();
        type = in.readInt();
    }

    public static final Creator<FilePickerMusic> CREATOR = new Creator<FilePickerMusic>() {
        @Override
        public FilePickerMusic createFromParcel(Parcel in) {
            return new FilePickerMusic(in);
        }

        @Override
        public FilePickerMusic[] newArray(int size) {
            return new FilePickerMusic[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileMediumUrl() {
        return fileMediumUrl;
    }

    public void setFileMediumUrl(String fileMediumUrl) {
        this.fileMediumUrl = fileMediumUrl;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public FieldItem getItem_workflow() {
        return item_workflow;
    }

    public void setItem_workflow(FieldItem item_workflow) {
        this.item_workflow = item_workflow;
    }

    public boolean isNet(){
        return path.contains("http://");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(path);
        dest.writeString(fileMediumUrl);
        dest.writeString(name);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeLong(size);
        dest.writeLong(duration);
        dest.writeInt(time);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(file_id);
        dest.writeInt(type);
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof FilePickerMusic){
            FilePickerMusic filePickerMusic = (FilePickerMusic) o;
            return filePickerMusic.getPath().equals(getPath());
        }
        return super.equals(o);
    }
}
