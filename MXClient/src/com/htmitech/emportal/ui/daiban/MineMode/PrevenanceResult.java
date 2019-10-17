package com.htmitech.emportal.ui.daiban.MineMode;

import com.htmitech.emportal.ui.daiban.minewidget.ui.SlideView;

import java.io.Serializable;

/**
 * Created by yanxin on 2016-9-27.
 */
public class PrevenanceResult implements Serializable {
    public long Id;
    public String UserId;
    public String DocId;
    public String DocTitle;
    public String SendFrom;
    public String SendDate;
    public String DocType;
    public String iconId;
    public String Kind;
    public String AttentionFlag;
    public String AllowPush;
    public SlideView slideView;
}
