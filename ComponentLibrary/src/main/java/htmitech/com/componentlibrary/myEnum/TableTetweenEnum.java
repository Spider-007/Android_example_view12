package htmitech.com.componentlibrary.myEnum;
/**
 * 对应表头找到对应的表
 * @author Tony
 *
 */
public enum TableTetweenEnum {
	SYS_DEPARTMENT("2-0", "org_org_tree"),
	SYS_USER("2-1", "org_user"),
	SYS_ORGUSER("2-2", "org_user_org"),
	TD_USER("2-3", "org_userfield"),
	TD_USERFIELDSECRET("2-5", "fs_picture"),
	T_USERRELATIONSHIP("2-4","org_user_contact"),
	EMP_CORP_PORTAL("2-6","emp_corp_portal"),
	APC_APP_APPGROUP("2-7","apc_app_appgroup"),
	COM_PARAM_GROUPCORP("2-8","cm_param_groupcorp"),

	APP_INFO("0-1","app_info"),//APP信息表
	PORTAL_APP("0-2","portal_app"),//门户与应用关联表
	APP_VERSION("0-3","app_version"),//APP版本信息表
	APP_VERSION_CONFIG("0-4","app_version_config"), //应用的版本参数配置表
	PORTAL_TAB("0-5","portal_tab"),//门户底部导航栏定义
	PORTAL_RIGHTTOP_MENU("0-6","portal_righttop_menu"),//门户的右上角菜单定义
	PORTAL_TAB_MENU("0-7","portal_tab_menu"),//门户底部导航栏对应的右上角菜单显示定义
	PORTAL_LEF_MENU("0-8","portal_left_menu"),//左侧菜单定义
	USER_APC_PORTAL_APP("1-0","user_apc_portal_app"),//用户的门户应用中心自定义列表
	EMP_PORTAL("0-0","emp_portal"),//平台门户信息表
	APPCLIENT_VERSION("1-1","appclient_version"),//版本管理
	PORTAL_DEFINE("1-2","apc_userdefine_portal");//门户属性自定义


	private String code, tableName;

	private TableTetweenEnum(String code, String tableName) {
		this.code = code;
		this.tableName = tableName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	 public static TableTetweenEnum getName(String code) {
         for (TableTetweenEnum c : TableTetweenEnum.values()) {
             if (c.code.equals(code)) {
                 return c;
             }
         }
         return null;
     }

	public static String getTabName(String code){
		for (TableTetweenEnum c : TableTetweenEnum.values()) {
			if (c.code.equals(code)) {
				return c.tableName;
			}
		}
		return "";
	}
}
