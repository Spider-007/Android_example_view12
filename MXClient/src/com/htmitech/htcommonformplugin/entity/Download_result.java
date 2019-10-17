package com.htmitech.htcommonformplugin.entity;

import java.io.Serializable;

/**
 * Created by Think on 2017/4/23.
 */

public class Download_result implements Serializable {
    /// <summary>
    /// 文件是否已经成功生成手机端要下载的zip包
    /// </summary>
    public boolean is_finished = false;
    /// <summary>
    /// 错误信息。 如果IsFinished为false，且ErroMsg 为空，则需要重复调用DownFileIsFinish WEB方法，直到IsFinished为true。也就是轮询服务器，看手机端要下载的文件包生成了没有。 （最多轮询10次，也就是20秒）
    /// </summary>
    public String erro_msg;
    /// <summary>
    /// 文件大小等信息
    /// </summary>
    public DownloadFileInfo fileinfo = null;


    public boolean is_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public String getErro_msg() {
        return erro_msg;
    }

    public void setErro_msg(String erro_msg) {
        this.erro_msg = erro_msg;
    }

    public DownloadFileInfo getFileinfo() {
        return fileinfo;
    }

    public void setFileinfo(DownloadFileInfo fileinfo) {
        this.fileinfo = fileinfo;
    }
}



