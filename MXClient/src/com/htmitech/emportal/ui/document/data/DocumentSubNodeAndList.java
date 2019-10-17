package com.htmitech.emportal.ui.document.data;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-9-5.
 * 资料库子目录与文件返回实体（java端整合后新接口）
 */
public class DocumentSubNodeAndList implements Serializable {
    public int code;
    public String message;
    public String debugMsg;
    public SubNodeAndListResult result;
}
