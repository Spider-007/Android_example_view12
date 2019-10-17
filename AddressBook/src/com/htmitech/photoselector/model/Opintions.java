package com.htmitech.photoselector.model;

import java.io.Serializable;

/**
 * Created by Think on 2017/4/19.
 */

public class Opintions implements Serializable {

    public String opinion_text;
    public String save_time;
    public String user_id;
    public String user_name;
    public String login_name;

    public String getOpinion_text() {
        return opinion_text;
    }

    public void setOpinion_text(String opinion_text) {
        this.opinion_text = opinion_text;
    }

    public String getSave_time() {
        return save_time;
    }

    public void setSave_time(String save_time) {
        this.save_time = save_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }
}
