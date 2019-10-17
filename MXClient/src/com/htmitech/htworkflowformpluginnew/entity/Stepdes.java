package com.htmitech.htworkflowformpluginnew.entity;


import java.io.Serializable;

public class Stepdes implements Serializable {

    /**
     * "Action": "管理员[提交]：", "Actiontime": "2015-05-05T14:08:00", "StepName":
     * "起草", "StepOrder": 1, "OA_UserID": "admin", "UserID": "admin"
     */
    public String StepName;
    public int StepOrder;
    public String Action;
    public String Actiontime;
    public String OAUserID;
    public String UserID;
    public String Comments;
    public String LoginName;
    public String userName;


    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public int getStepOrder() {
        return StepOrder;
    }

    public void setStepOrder(int stepOrder) {
        StepOrder = stepOrder;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getActiontime() {
        return Actiontime;
    }

    public void setActiontime(String actiontime) {
        Actiontime = actiontime;
    }

    public String getOAUserID() {
        return OAUserID;
    }

    public void setOAUserID(String OAUserID) {
        this.OAUserID = OAUserID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
