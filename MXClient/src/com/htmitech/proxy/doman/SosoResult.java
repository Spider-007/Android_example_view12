package com.htmitech.proxy.doman;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by htrf-pc on 2017/4/13.
 */
public class SosoResult implements Serializable{
    public ArrayList<ReportSosoResult> Result;

    public ReportMessage Message;

    public int Status;

    private String title;

    private String input_type;

    public String getInput_type() {
        return input_type;
    }

    public void setInput_type(String input_type) {
        this.input_type = input_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ReportSosoResult> getResult() {
        return Result;
    }

    public void setResult(ArrayList<ReportSosoResult> result) {
        Result = result;
    }

    public ReportMessage getMessage() {
        return Message;
    }

    public void setMessage(ReportMessage message) {
        Message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
