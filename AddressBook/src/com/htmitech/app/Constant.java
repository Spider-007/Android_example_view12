
package com.htmitech.app;

import com.htmitech.domain.ScheduleLocation;

public class Constant {
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static String ROOTNODE_STRINGID = ""; //根节点ID
    public static String GROUP_TREE_CODE = "";//根节点的tree_code
    public static String ADDRESS_LIST_MESSAGE = "";//列表显示信息
    public static String ADDRESS_HEADER_TYPE = "2";//头像类型
    public static final String ROOTNODEORGANISE_STRING = "云南省";//根节点部门名称
    public static final String INTO_STRING_SEARCH_STRING = "search";//搜索按钮进入
    public static final String ADDRESS_STRING_BUTTON = "address";//通讯录进入
    public static final String ZIPPASSWORD = "password";
    public static final String[] MESSAGECOLUMNNAME = new String[]{"头像", "姓名", "部门", "职务", "手机号", "工作电话", "分机号", "邮箱", "位置", "个性签名", "教育与工作经历"};
    public static final String ACTION_NAME = "com.htmitech.fragment.AddressFragment";
    public static String SDCARD_PATH;
    public static String SDCARD = "";
    public static String PHONEURL;
    public static final String HOME_INIT = "home";//点击主页面里面的通讯录
    public static final String LOING_INIT = "tab"; //初始化页面的时候进入的
    public static String START_TYPE = "";
    public static final int[] colorList = new int[]{0xffabca55, 0xffe78f75, 0xffebc064, 0xff66b589, 0xff5dbbd6};
    public static float TEXTVIEWSIXE = 1;
    public static final String EVENT_REFRESH_LANGUAGE = "1";
    public static String LOGFUNCTIONID = ""; //日志识别
    public static final long TOKENTIME = 9 * 60 * 1000;
    public static boolean channelConstant = false;
    public static String addGeneralChannel = "";

    public static boolean IS_CONTACT = true;//是否支持常用联系人功能
    public static String IS_WATER_BACKGROUND = "0";//是否支持水印

    public static String com_addressbook_mobileconfig_home_phone_secrecy = "0";//显示电话号码时是否保密显示           0， 完整显示   1， 显示时带星号，但仍然可以拨打和发短信。
    public static String com_addressbook_mobileconfig_office_phone_secrecy = "0";//显示电话号码时是否保密显示           0， 完整显示   1， 显示时带星号，但仍然可以拨打和发短信。
    public static String com_addressbook_mobileconfig_mobile_phone_secrecy = "0";//显示电话号码时是否保密显示           0， 完整显示   1， 显示时带星号，但仍然可以拨打和发短信。

    public static String com_workflow_mobileconfig_flowhistory_display_order = "";//新增

    public static String com_addressbook_mobileconfig_otherorg_show = "0";

    public static String com_addressbook_mobileconfig_viewtype = "0";
    public static String com_addressbook_mobileconfig_viewtype1_level = "0";

    public static final boolean NEW_SYS_INTERFACE = true;//是否启用新同步接口

    public static final boolean HOME_PAGE_REFRESH = false;//表示是否需要支持首页刷新

    public static final String POSITION_MINUTES = "mobile_collect_position_minutes";//. 参数定义：mobile_collect_position_minutes，0：不收集位置信息，>0：要收集位置信息，并根据参数值对应的分钟数更新位置信息。例如参数值为3时，则移动端每3分钟更新一下位置信息；

    public static int minutes = -2;
    public static ScheduleLocation mScheduleLocation = new ScheduleLocation();

    public static String com_workflow_mobileconfig_check_mustinput_except = "";

    public static boolean IS_DZKF = false;//是否为定值开发的：燃油宝
    public static final boolean FUNCATION_CODE = true;
    public static boolean IS_GYL = false;
    public static int color = 0;
}
