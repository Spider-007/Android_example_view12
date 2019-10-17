package htmitech.com.componentlibrary.unit;


public class ServerUrlConstant {

    /********************************************* 接口方法: ******************************************************/
    /**
     * 蓝波今朝的定制代码开发
     */
    public static final String GET_GATEWAY = "gateway/thirdSVController/usersv/";
    /**
     * 登录
     ***/
    public static final String USERINFO_LOGIN_METHOD = "logincontroller/login";
    /**
     * 获取当前人员所有的快捷键
     **/
    public static final String OCUINFO_GETOCULIST_METHOD = "GetMobileData/GetUserShortcuts";
    /**
     * 保存当前人员所有的快捷键
     **/
    public static final String OCUINFO_SAVEOCULIST_METHOD = "GetMobileData/SaveUserShortcuts";
    /**
     * 查询待办已办
     ***/
    public static final String OA_GETDOCLIST_METHOD = "GetMobileData/GetDbYbList";
    //    public static final String OA_GETDOCLIST_METHOD_JAVA = "workflow/toDoAndHasToDoList";
    public static final String OA_GETDOCLIST_METHOD_JAVA = "gateway/flowClawController/transmit/getDocList";
    /**
     * 获取公文详细
     ***/
    public static final String OA_GETDOCINFO_METHOD = "GetMobileData/GetDocInfo";
    //    public static final String OA_GETDOCINFO_METHOD_JAVA = "workflow/decorationDetail";
    public static final String OA_GETDOCINFO_METHOD_JAVA = "gateway/flowClawController/transmit/getDocDataAndDefine";
    public static final String OA_GETDOCINFO_NODEFINE_METHOD_JAVA = "gateway/flowClawController/transmit/getDocData";

    /**
     * 2018年5月15日 10:46:14 新增流程批量办理
     */
    public static final String OA_GETDOCINFO_HANDLE_DOC = "gateway/flowClawController/transmit/handleBatchDoc";

    /**
     * 获得正文的流程
     **/
    public static final String OA_GETDOC_FLOW_METHOD = "GetMobileData/GetDocFlow";
    //    public static final String OA_GETDOC_FLOW_METHOD_JAVA = "workflow/doHistoryNodes";
    public static final String OA_GETDOC_FLOW_METHOD_JAVA = "gateway/flowClawController/transmit/getDocFlowHistoryList";
    /**
     * 同步數據专用(.net)
     **/
    public static final String GETSYNCDATA_METHOD = "GetMobileData/GetSyncData";
    /**
     * 同步數據专用(java)
     **/
    public static final String DATA_CRAB_GETSYNCDATA = "syncdatacontroller/getSyncData";

    /**
     * 获得正文文字内容
     **/
    public static final String OA_GETDOC_TEXT_METHOD = "GetMobileData/GetWord_Text";
    /**
     * 准备下载正文
     **/
    public static final String OA_DOWNFILE_ISFinish_DOCFile_METHOD = "GetMobileData/DownFileIsFinish_DocFile";
    //    public static final String OA_DOWNFILE_ISFinish_DOCFile_METHOD_JAVA = "workflow/downloadDoc";
    public static final String OA_DOWNFILE_ISFinish_DOCFile_METHOD_JAVA = "gateway/flowClawController/transmit/downloadDocFile";
    /**
     * 准备下载附件
     **/
    public static final String OA_DOWNFILE_ISFinish_ATTFile_METHOD = "GetMobileData/DownFileIsFinish_Attachment";
    //    public static final String OA_DOWNFILE_ISFinish_ATTFile_METHOD_JAVA = "workflow/downloadAttachment";
    public static final String OA_DOWNFILE_ISFinish_ATTFile_METHOD_JAVA = "gateway/flowClawController/transmit/downloadAttachmentFile";
    /**
     * 办理
     **/
    public static final String OA_DOAction_METHOD = "GetMobileData/DoAction";
    //    public static final String OA_DOAction_METHOD_JAVA = "workflow/doInstance";
    public static final String OA_DOAction_METHOD_JAVA = "gateway/flowClawController/transmit/handleDoc";

    /**
     * 获取签批文件信息
     */
    public static final String OA_GETSIGNFILEINFO = "workflow/getSignFileInfo";

    /**
     * 上传签批文件
     */
    public static final String OA_UPLOAD_SIGNINFO_BYBASE_64 = "workflow/uploadSignFileByBase64";
    /*
    * pdf签批文件上传
    * */
    public static final String UPLOAD_PDF_SIGN = "gateway/flowClawController/transmit/uploadSignFileByBase64";


    /**
     * 获取当前人员能看见的顶层资料库目录
     **/
    public static final String DOCUMENT_GETTOPLEVELNODE_METHOD = "fsdocnode/getDocumentNodeTopLevel";
    /**
     * 获取当前人员能看见的顶层资料库目录
     **/
    public static final String DOCUMENT_GETDOCUMENT_SERCH_METHOD = "fsdocnode/screeningDocumentNode";
    /**
     * 获取当前人员能看见的分层资料库目录，需要传入ParentNodeID
     **/
    public static final String DOCUMENT_GETSUBNODE_METHOD = "fsdocnode/findList";
    /**
     * 获取当前人员能看到的目录节点下的文件
     **/
    public static final String DOCUMENT_GETDOCUMENTLIST_METHOD = "fsdoc/findList";

    /**
     * java端整合后的新资料库获取子目录及文件
     **/
    public static final String DOCUMENT_GETSUBNODEANDLIST_METHOD = "fsdoc/getFsDoc";
    /**
     * 发起流程
     ***/
    public static final String OA_STARTFLOW_METHOD = "GetMobileData/StartDocFlow";
    public static final String OA_STARTFLOW_METHOD_BUILD = "GetMobileData/CreateWork";//重构版本
    public static final String OA_STARTFLOW_METHOD_BUILD_JAVA = "gateway/flowClawController/transmit/startDocFlow";//重构版本

    public static final String OA_STARTFLOW_EVENTAPI = "GetMobileData/EventAPI";//重构版本

    public static final String OA_STARTFLOW_EVENTAPI_JAVA = "gateway/flowClawController/transmit/eventResponse";//重构版本

    /**
     * 表单413的搜索
     */
    public static final String OA_GETDOCINFO_LIST_CONDITION = "gateway/flowClawController/transmit/getDicListByCondition";

    /**
     * H5发起流程
     ***/
    public static final String OA_STARTFLOW_METHOD_H5 = "GetMobileData/StartWorkFlowH5";
    /**
     * 获得用户的常用意见
     ***/
    public static final String USERINFO_GETUSEROPTIONS_METHOD = "GetMobileData/GetUserOptions";
    //    public static final String USERINFO_GETUSEROPTIONS_METHOD_JAVA = "workflow/getUserOptions";
    public static final String USERINFO_GETUSEROPTIONS_METHOD_JAVA = "gateway/flowClawController/transmit/getUserOptionList";
    /**
     * 删除常用联系人
     **/
    public static final String SYNCDATA_DELETE_USER = "User/RemoveCUser";
    public static final String SYNCDATA_DELETE_USER_JAVA = "orgusercontactcontroller/delete";
    /**
     * 添加常用联系人
     **/
    public static final String SYNCDATA_ADD_USER = "User/AddCUser";
    public static final String SYNCDATA_ADD_USER_JAVA = "orgusercontactcontroller/save";
    /**
     *
     */
    public static final String UPDATA_USER_MESSAGE = "User/UpdateUserTXL";
    public static final String UPDATA_USER_MESSAGE_JAVA = "restapi/orguser/update";
    /**
     * 新增常用意见接口
     ***/
    public static final String USERINFO_ADDUSEROPTIONS_METHOD = "GetMobileData/NewUserOptions";
    //    public static final String USERINFO_ADDUSEROPTIONS_METHOD_JAVA = "workflow/addUserOptions";
    public static final String USERINFO_ADDUSEROPTIONS_METHOD_JAVA = "gateway/flowClawController/transmit/addUserOption";
    /**
     * 更新常用意见接口
     ***/
    public static final String USERINFO_EditUSEROPTIONS_METHOD = "GetMobileData/EditUserOptions";
    //    public static final String USERINFO_EditUSEROPTIONS_METHOD_JAVA = "workflow/updateUserOptions";
    public static final String USERINFO_EditUSEROPTIONS_METHOD_JAVA = "gateway/flowClawController/transmit/updateUserOption";
    /**
     * 删除常用意见接口
     ***/
    public static final String USERINFO_DELUSEROPTIONS_METHOD = "GetMobileData/DelUserOptions";
    //    public static final String USERINFO_DELUSEROPTIONS_METHOD_JAVA = "workflow/delUserOptions";
    public static final String USERINFO_DELUSEROPTIONS_METHOD_JAVA = "gateway/flowClawController/transmit/delUserOption";
    /**
     * 获取我发起列表
     ***/
    public static final String GET_MY_SEND_FLOWLIST = "GetMobileData/GetMySendFlowList";
    //    public static final String GET_MY_SEND_FLOWLIST_JAVA = "workflow/myStartList";
    public static final String GET_MY_SEND_FLOWLIST_JAVA = "gateway/flowClawController/transmit/getMyStartList";
    /**
     * 获取我关注列表
     ***/
    public static final String GET_MY_ATTENTION_FLOWLIST = "GetMobileData/GetMyAttentionFlowList";
    //    public static final String GET_MY_ATTENTION_FLOWLIST_JAVA = "workflow/myInterestList";
    public static final String GET_MY_ATTENTION_FLOWLIST_JAVA = "gateway/flowClawController/transmit/getMyInterestList";
    /**
     * 取消添加关注列表
     ***/
    public static final String SET_ATTENTION_YESORNO = "GetMobileData/SetAttentionYesOrNo";
    //    public static final String SET_ATTENTION_YESORNO_JAVA = "workflow/addOrCancelInterest";
    public static final String SET_ATTENTION_YESORNO_JAVA = "gateway/flowClawController/transmit/setAttentionStatus";
    /**
     * 获取签批文件信息
     ***/
    public static final String GET_SIGNPDF_INFO = "gateway/flowClawController/transmit/getSignFileInfo";
    /**
     * 获取所有代办已办数量
     **/
    public static final String USER_DAIBAN_YIBAN_COUNT = "GetMobileData/GetDbYbCount";
    //    public static final String USER_DAIBAN_YIBAN_COUNT_JAVA = "workflow/toDoAndHasToDoCount";
    public static final String USER_DAIBAN_YIBAN_COUNT_JAVA = "gateway/flowClawController/transmit/getDocCount";
    /**
     * 新增 获取所有角标的接口
     */
    public static final String USER_DAIBAN_YIBAN_COUNT_ALL_JAVA = "gateway/commonController/getPortalAppBadge";
    /**
     * 通用表单搜索以及获取角标的
     **/
    public static final String USER_GENERAL_FORM_COUNT = "CommonForm/GetCountByCondition";

    /**
     * 获取通用表单所有代办已办
     **/
    public static final String USER_GENERAL_FORM_TODO_LIST = "CommonForm/GetListByCondition";

    /**
     * Java登入接口
     **/
    public static final String USER_LOGIN_EMPAPI = "logincontroller/login";
    /**
     * Java登入接口
     **/
    public static final String REFRESH_TOKEN = "accesstokencontroller/accesstoken";
    /**
     * 首次登陆上传手机端信息
     */
    public static final String USER_LOGIN_MESSAGE = "logdevicefunction/save";
    /**
     * Java退出接口
     **/
    public static final String USER_LOGINOUT_EMPAPI = "logincontroller/loginout";
    /**
     * 搜索接口
     */
    public static final String REPORT_SOSO = "ReportList/getdictsbyfieldid";
    /**
     * 搜索接口
     */
    public static final String BAR_LINE_REPORT_SOSO = "ReportBarLine/getdictsbyfieldid";
    /**
     * 获取值接口
     **/
    public static final String REPORT_VALUE = "ReportList/getdata";

    /**
     * 获取值接口
     **/
    public static final String PIC_REPORT_VALUE = "ReportPie/getdata";

    /**
     * 获取值接口
     **/
    public static final String PIC_REPORT_SOSO = "ReportPie/getdictsbyfieldid";
    /**
     * 获取值接口
     **/
    public static final String BARLINE_REPORT_VALUE = "ReportBarLine/getdata";
    /**
     * 获取我移除的
     */
    public static final String USER_GETREMOVE = "app/getremovedapp";

    /**
     * （门户属性自定义）用户门户自定义属性OK
     */
    public static final String USET_PORTAL_DEFINE = "apcuserdefineportal/SetUserPortalDefine";

    /**
     * 添加我移除的
     */
    public static final String ADD_REMOVE = "app/addremovedapp";
    /**
     * 拖动排序
     */
    public static final String RESORTAPP = "app/resortapp";
    /**
     * 移除应用
     */
    public static final String REMOVE = "app/removeapp";
    /**
     * java日志启动
     **/
    public static final String LOG_FUNCTION_EMPM_START = "gateway/logcrabController/transmit/LogFunctionStart";
    /**
     * java日志关闭
     **/
    public static final String LOG_FUNCTION_EMPM_FINISH = "gateway/logcrabController/transmit/LogFunctionFinish";

    /**
     * 日志启动
     **/
    public static final String LOG_FUNCTION_START = "AppPortLogin/LogFunctionStart";
    /**
     * 日志关闭
     **/
    public static final String LOG_FUNCTION_FINISH = "AppPortLogin/LogFunctionFinish";

    /**
     * java唤醒功能
     **/
    public static final String LOG_FUNCTION_EMPM_ONCE = "LogFunctionOnce";

    public static final String LOG_FUNCTION_EMPM_ONCE_JAVA = "gateway/logcrabController/transmit/LogFunctionOnce";
    /**
     * 唤醒功能
     **/
    public static final String LOG_FUNCTION_ONCE = "AppPortLogin/LogFunctionOnce";
    /**
     * 发送回执信息
     **/
    public static final String RECEIPT_MESSAGE = "Push/ReceiveMessage";
    /**
     * 通知公告列表
     ***/
    public static final String GET_ANNOUNCEMENT_LIST = "Notice/GetNoticeList";
    public static final String GET_ANNOUNCEMENT_LIST_JAVA = "gateway/noticeController/transmit/getNoticeListByCondition";

    /*
    * 版本更新
    * */
    public static final String GET_VERSION_UPDATE = "appclientversion/hasnewappversion";
    public static final String VERSION_UPDATE = "appclientversion/proupgrade";     //升级备用通道
    /*
    * 获取日程列表
    * */
    public static final String GET_SCHEDULE_LIST = "schschedule/findPage";
    /*
    * 获取日程详情
    * */
    public static final String GET_SCHEDULE_DETAIL = "schschedule/getDetail";
    /*
    * 创建日程
    * */
    public static final String CREATE_SCHEDULE = "schschedule/save";
    /*
    * 更新日程
    * */
    public static final String UPDATE_SCHEDULE = "schschedule/update";
    /*
    * 删除日程
    * */
    public static final String DELETE_SCHEDULE = "schschedule/deleteschedule";
    /*
    * 获取常用地点
    * */
    public static final String GET_COMMON_PLACE = "schplace/getcommonplacelist";
    /*
  * 确认我的日程
  * */
    public static final String CONFIRM_MINE_SCHEDULE = "schusers/certain";
    public static final String OA_SCHEDULE_EVENTAPI_JAVA = "schschedule/transmit";//重构版本
    public static final String ZT_REGISTER_SAVE = "zt/userapply/save";//中铁注册接口

    /***********************************************************
     * URL
     *******************************************************************************/
    public static String SERVER_BASE_URL() {
        String loginUrl = PreferenceUtils.getOaLoginUrl();
        String apiDir = PreferenceUtils.getApiUrl();
        return loginUrl + "/" + apiDir + "/api/";
//		return CommonSettings.DEBUG ? ( debugUrl + "/" + apiDir+ "/api/" )
//				: ( loginUrl + "/" + apiDir +"/api/" );

		/*return CommonSettings.DEBUG ? "http://114.112.89.94:8081/mobilecloudapi/api/"
                : "http://114.112.89.94:8081/cloudapi/api/";*/
    }

    public static String SERVER_EMPAPI_URL() {
        String empapiUrl = PreferenceUtils.getDebugUrl();
        String apiDir = "data-crab";
        return PreferenceUtils.getDebugUrl() + "/" + apiDir + "/";  //"http://htrf.dscloud.me:8040
    }

    public static String SERVER_JAVA_URL() {
        String empapiUrl = PreferenceUtils.getDebugUrl();
        String apiDir = "data-crab";
        return PreferenceUtils.getDebugUrl() + "/" + apiDir + "/";  //"http://htrf.dscloud.me:8040
    }

    public static String SERVER_DZ_URL() {
        return PreferenceUtils.getDzUrl();
    }


    /************************************************************* h5 url ************************************************************/

    /*********************************************************************************************************************************/

    /************************************************************* 通用表单构件 ************************************************************/
    /**
     * 获取代办已办列表
     */
    public static final String GET_COMMONFORM_LIST = "CommonForm/GetListByCondition";
    /**
     * 获取代办已办数目
     */
    public static final String GET_COMMONFORM_Count = "CommonForm/GetCountByCondition";
    /**
     * 获取表单详情
     */
    public static final String GET_COMMONFORM_DETAL = "CommonForm/GetDetail";
    /**
     * 准备下载附件
     **/
    public static final String GET_COMMONFORM_DownAttachmentFile_IsFinish = "CommonForm/DownAttachmentFile_IsFinish";
    /**
     * 取消添加关注操作
     ***/
    public static final String SET_COMMONFORM_YESORNO = "CommonForm/SetAttentionYesOrNo_CommonForm";
    /**
     * 提交操作
     **/
    public static final String SET_COMMONFORM_DoAction = "CommonForm/DoAction";
    /**
     * 获取桌面角标数
     **/
    public static final String GET_COUNTNEED_Count = "CountNeed/GetNeedCount";

    /**
     * Java的新接口
     */
    public static final String GET_COUNTNEED_Count_JAVA = "app/getNeedCount";
    /**
     * 获取门户角标数
     **/
    public static final String GET_PORATL_Count = "CountNeed/GetPortalsNeedCount";


    /**
     * 获取门户角标数
     **/
    public static final String GET_PORATL_Count_JAVA = "app/getPortalsNeedCount";


    /**
     * 获取用户应用门户目标系统账号激活列表
     */
    public static final String ObjecToveSyscontrollerGetEmpCisList = "objectovesyscontroller/getEmpCisList";
    /**
     * 激活用户应用对应目标系统
     */
    public static final String ObjecToveSyscontrollerActivate = "objectovesyscontroller/activate";
    /*
    * 获取Cis信息
    * */
    public static final String GETCISACCOUNTBYAPP = "objectovesyscontroller/getCisAccountByApp";
    /**
     * 用户应用对应目标系统是否激活
     */
    public static final String ObjecToveSyscontrollerIsActivateState = "objectovesyscontroller/isActivateState";
    /**
     * 获取反馈问题类型的列表
     */
    public static final String DiccontrollerGetDicList = "diccontroller/getDicList";
    /**
     * 用户反馈问题提交
     */
    public static final String CsfeedbackControllerSubmit = "csfeedbackcontroller/submit";
    /**
     * 用户反馈问题图片上传
     */
    public static final String FilecontrollerPicture = "filecontroller/picture";
    /**
     * 上传使用频率
     */
    public static final String FILE_UPLOAD_LOG_MSG = "logfileuploadcontroller/uploadLogFilesByFormdata";
    /**
     * 通用表单提交，图片上传后，通知文件上传成功
     */
    public static final String SET_COMMONFORM_SaveExtFields = "CommonForm/SaveExtFields";
    /**
     * 设备管理 通过密码身份校验
     */
    public static final String GetDeviceVertifyPassword = "dcsafeconfig/userAuthentication";
    /**
     * 设备管理 申请操作
     */
    public static final String GetDeviceSaveAudit = "dcdevicebindapply/saveandit";
    public static final String GetDeviceSaveApplyPic = "dcdevicebindapply/saveApplyPic";

    /**
     * 获取设备安全配置
     ***/
    public static final String GET_DCSAFE_CONFIG = "dcsafeconfig/getDcSafeConfig";
    /**
     * 保存用户自定义的设备配置
     ***/
    public static final String SAVE_DCSAFE_CONFIG = "dcsafeconfig/savedcsafeconfig";
    /**
     * 更新用户设备绑定状态
     ***/
    public static final String UPDATE_BIND_STATUS = "dcuserdevicebind/updatebindstatus";

    /**
     * 获取设备列表
     ***/
    public static final String GET_DEVICE_LIST = "dcuserdevicebind//findList";
    /*********************************************************************************************************************************/

    /**
     * 获取视频列表
     */
    public static final String GET_VIDEO_LIST = "pjvedionews/findPage";

    /**
     * 得到视频分类
     */
    public static final String GET_VIDEO_CLASSIFI = "pjvideotype/videoClassifyList";
    /**
     * 广告页背景图片
     */
    public static final String GET_ADV_PIC = "filecontroller/getPicture";

    /**
     * 异常列表
     */
    public static final String MANAGE_EXCEPTION_LIST = "sysalertinfo/getListInformation";
    /**
     * 异常详情
     */
    public static final String MANAGE_EXCEPTION_DETAIL = "sysalertinfo/getDetailInformation";

    /**
     * 异常状态（已读/未读）
     */
    public static final String MANAGE_EXCEPTION_CHANGESTATE = "sysalertinfo/updateResponseStatus";

    /**
     * 异常数量（已读/未读）
     */
    public static final String MANAGE_EXCEPTION_ALERTCOUNT = "sysalertinfo/getAlertCount";

    /**
     * 获取上传大文件的文件号
     */

    public static final String GET_BIGFILE_BATCH = "filecontroller/createBigfileBatch";

    /**
     * 上传大文件接口
     */

    public static final String UPLOAD_BIGFILE = "filecontroller/uploadBigfile";

    /**
     * 修改密码
     */
    public static final String CHANGE_USER_PASSWORD = "logincontroller/updatePassword";


    /**
     * 上传非大文件
     */
    public static final String UPLOAD_NORMAL_FILE = "filecontroller/uploadFile";

    /**
     * 上传的文件和流程绑定
     */

    public static final String SAVE_EXTFILES_PATH = "extfielddata/saveExtFields";


}
