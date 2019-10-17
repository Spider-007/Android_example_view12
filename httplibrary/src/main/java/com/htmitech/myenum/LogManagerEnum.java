package com.htmitech.myEnum;

/**
 * logFunctionCode以及Id的参数配置
 */
public enum LogManagerEnum {

    APP_MOBILE_LOGIN("mobilelogin") {//登录

        @Override
        public String getFunctionId() {
            return functionId;
        }
    },
    APP_FIRST_START("apponfirststart") {//第一次启动

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_RESTART("apponrestart") {//再次启动

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_PAUSE_TOBACK("apponpausetoback") {//进入后台

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, GETUSEROPINTION("get_user_options") {//获取常用意见

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_RESUME("apponresume") {//唤醒

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_UP_LOAD_FILE("") {//移动端上传文件的接口（正文里面的ofd  pdf等文件）

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DOC_FLOW("getdocflow") {//查看流程实例的历史流程

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DOC_TEXT("getdoctext") {//查看流程实例的正文概要信息（包括下载正文和手机端展示）

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DOWN_LOAD_DOC("downloaddoc") {//移动端下载正文文件并查看正文（包括下载正文和手机端展示）

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DOWN_LOAD_ATTACHMENT("downloadattachment") {
        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DOC_INFO("getdocinfo") {//查看流程实例的表单、有无正文、有无附件和附件列表信息、可进行的操作等“详情信息”

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_DO_ACTION("dodocaction") {//工作流提交
        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DELECTUSEROPINTION("del_user_options") {//删除常用意见

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_CENTER_ADD("addremovedapp") {//添加应用到我的“应用中心”

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_CENTER_DELETE("removeapp") {//从我的“应用中心”中移除选定的应用

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, APP_CENTER_RESORT("resortapp") {//改变我的“应用中心”中的顺序以便我以我的习惯快速找到应用

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, ANNOUNCEMENT_LIST("notice_getlist") {//通知公告日志

        @Override
        public String getFunctionId() {
            return functionId;
        }

    }, GET_COMMONFORM_DO_ACTION("commonform_doaction") {//通用表单提交

        @Override
        public String getFunctionId() {
            return functionId;
        }

    }, GET_COMMONFORM_DETAIL("commonform_getdetail") {//通用表单详情

        @Override
        public String getFunctionId() {
            return functionId;
        }

    }, COMMONFORM_DOWN_LOAD_ATTACHMENT("commonform_down") {//通用表单附件下载

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, OBJECTIVESYS_ACTIVATE("objectivesys_activate") {//目标系统激活

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, STARTWORKFLOWH5("startworkflowh5") {//工作流H5发起详情

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWGETDOCLIST("getdoclist") {//工作流代办

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWGETDOCLISTYB("getdoclist!yb") {//工作流已办

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWMYATTENTION("workflow_myattention") {//工作流我的关注列表

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWMYSTART("workflow_mystart") {//工作流我的发起列表

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, GETDOCCOUNT("getdoccount") {//工作流代办已办角标数量

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWATTENTION("workflow_attention") {//工作流添加关注，取消关注

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, WORKFLOWATTACHMENTDOWNLOAD("workflow_attachment_download") {//工作流附件下载

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, GGENERAL("") {//通用

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, BAR_LINE_GETDATA("report_barline_getdata") {//柱线图的获取数据

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, BAR_LINE_GETDICTS("report_barline_getdictsbyfieldid") {//柱线图获取某个查询条件可能的取值（筛选）

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, MYINFO_UPDATE_INFO("myinfo_update_info") {//个人信息-修改信息

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, MYINFO_UPDATE_PIC("myinfo_update_pic") {//个人信息-修改头像

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, COMMONFORM_MAIN_LEADER("commonform_main_leader") {
        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, HOME_PAGE_API("homepageapi") {//新版磁铁

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DOCUMENT_MAIN("document_main") {//文档库目录

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DOCUMENT_LIST("document_getlist") {//文档库目录

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DOCUMENT_DOWNLOAD("document_download") {//文档库下载

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DOCUMENT_SUB("document_sub") {//文档库子目录

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, ADD_USER_RELATIONSHIP("add_user_relationship") {//添加常用联系人

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, REMOVE_USER_RELATIONSHIP("remove_user_relationship") {//添加常用联系人

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, VIDEO_PLAY("vedio_news_play") {//新闻视频播放

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, VIDEO_LIST("vedio_news_list") {//新闻列表

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, BB_LIST("report_list_getdata") {//报表

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, DODO_ACTION_NATIVE("dodocaction") {//原生办理

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, STARTDOC_FLOW("startdocflow") {//原生发起

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, EVENT_API("event_api") {//事件级联

        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULELIST("gescheduletList"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULEDETAIL("getscheduledetail"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULECREATE("creatschedule"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULEUPDATE("updateschedule"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULEDELETE("deleteschedule"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    },SCHEDULECONFIRM("confirmschedule"){
        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, ADDUSEROPINTION("new_user_options") {//添加常用意见

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, EDITUSEROPINTION("edit_user_options") {//修改常用意见

        @Override
        public String getFunctionId() {
            return functionId;
        }
    }, CHANGEUSERPASSWORD("change_user_password") {//修改密码

        @Override
        public String getFunctionId() {
            return functionId;
        }
    };
    public String functionCode = "";

    public String functionId = "0";
    public String app_id;
    public String appVersionId;
    public long currentTimeMillis;

    LogManagerEnum(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public abstract String getFunctionId();


    public static LogManagerEnum getLogManagerEnum(String funcation) {
        for (LogManagerEnum a : LogManagerEnum.values()) {
            if (a.functionCode.equals(funcation)) {
                return a;
            }
        }
        return null;
    }

}
