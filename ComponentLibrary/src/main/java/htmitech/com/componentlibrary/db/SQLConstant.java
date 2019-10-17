package htmitech.com.componentlibrary.db;

/**
 * 创建表的实体类
 */
public class SQLConstant {
    public static final String APC_USERDEFINE_PORTAL = "CREATE TABLE [apc_userdefine_portal] (\n" +
            "  [user_id] bigint NOT NULL, \n" +
            "  [portal_id] bigint NOT NULL, \n" +
            "  [is_using] smallint DEFAULT 1, \n" +
            "  [using_home_style] smallint, \n" +
            "  [using_color_style] smallint, \n" +
            "  [using_layout_style] smallint, \n" +
            "  [using_apc_style] smallint, \n" +
            "  [using_font_style] smallint, \n" +
            "  [group_corp_id] bigint, \n" +
            "  [status_flag] smallint, \n" +
            "  [create_by] VARCHAR, \n" +
            "  [create_time] VARCHAR, \n" +
            "  [update_by] VARCHAR, \n" +
            "  [update_time] VARCHAR, \n" +
            "  CONSTRAINT [sqlite_autoindex_apc_userdefine_portal_1] PRIMARY KEY ([user_id], [portal_id]));\n";

    public static final String APP_INFO = "CREATE TABLE [app_info] (\n" +
            "  [app_id] bigint NOT NULL, \n" +
            "  [corp_id] bigint DEFAULT NULL, \n" +
            "  [app_code] varchar(50) DEFAULT NULL, \n" +
            "  [app_name] varchar(50), \n" +
            "  [app_shortname] varchar(20) DEFAULT NULL, \n" +
            "  [app_type] smallint DEFAULT '1', \n" +
            "  [app_logo] varchar(500) DEFAULT NULL, \n" +
            "  [app_desc] varchar(1000) DEFAULT NULL, \n" +
            "  [comp_id] bigint DEFAULT NULL, \n" +
            "  [comp_code] varchar(100) DEFAULT NULL, \n" +
            "  [plugin_id] bigint DEFAULT NULL, \n" +
            "  [plugin_code] varchar(100) DEFAULT NULL, \n" +
            "  [parent_app_id] bigint DEFAULT NULL, \n" +
            "  [app_usergroup_id] bigint DEFAULT NULL, \n" +
            "  [status_flag] smallint DEFAULT '1', \n" +
            "  [current_version] bigint DEFAULT (-1), \n" +
            "  [type_flag] SMALLINT DEFAULT (-1), \n" +
            "  [default_showinparent_flag] smallint(6) DEFAULT 0, \n" +
            "  [parent_appgroup_app_id] BIGINT DEFAULT (-1), \n" +
            "  CONSTRAINT [sqlite_autoindex_app_info_1] PRIMARY KEY ([app_id]));\n";


    public static final String APP_VERSION = "CREATE TABLE app_version (\n" +
            "  app_version_id bigint PRIMARY KEY NOT NULL,\n" +
            "  app_id bigint NOT NULL,\n" +
            "  version_number bigint,\n" +
            "  version_name varchar(20),\n" +
            "  version_type smallint,\n" +
            "  version_desc varchar(50),\n" +
            "  mustupdated smallint,\n" +
            "  app_filesize bigint,\n" +
            "  hash_code varchar(50),\n" +
            "  file_location varchar(200),\n" +
            "  package_name varchar(50),\n" +
            "  package_main varchar(50),\n" +
            "  status_flag smallint\n" +
            ");";

    public static final String APP_VERSION_CONFIG = "CREATE TABLE app_version_config (\n" +
            "  app_version_id bigint NOT NULL,\n" +
            "  app_id bigint NOT NULL,\n" +
            "  config_name varchar(50),\n" +
            "  config_value varchar(200),\n" +
            "  config_remark varchar(50),\n" +
            "  status_flag smallint,\n" +
            "  config_code varchar(100) NOT NULL,\n" +
            "primary key (app_version_id, app_id, config_code)\n" +
            ");\n";
    public static final String APPCLIENT_VERSION = "CREATE TABLE [appclient_version] (\n" +
            "  [id] int(11) NOT NULL, \n" +
            "  [env_type] smallint DEFAULT NULL, \n" +
            "  [reset_client] smallint DEFAULT NULL, \n" +
            "  [mustupdated] smallint DEFAULT NULL, \n" +
            "  [update_desc] varchar(2000) DEFAULT NULL, \n" +
            "  [type] smallint DEFAULT NULL, \n" +
            "  [version_name] varchar(255) DEFAULT NULL, \n" +
            "  [version_no] int DEFAULT NULL, \n" +
            "  [file_id] bigint DEFAULT NULL, \n" +
            "  [status_flag] smallint DEFAULT NULL, \n" +
            "  [create_time] datetime DEFAULT NULL, \n" +
            "  [update_time] datetime DEFAULT NULL, \n" +
            "  [create_by] bigint DEFAULT NULL, \n" +
            "  [update_by] bigint DEFAULT NULL, \n" +
            "  [group_corp_id] bigint DEFAULT NULL, \n" +
            "  [plist_url] varchar(255) DEFAULT NULL, \n" +
            "  [filename] CHAR(100), \n" +
            "  [filesize] INT(11), \n" +
            "  [download_url] VARCHAR(250), \n" +
            "  CONSTRAINT [sqlite_autoindex_appclient_version_1] PRIMARY KEY ([id]));\n" +
            "\n";
    public static final String EMP_CORP_PORTAL = "CREATE TABLE emp_corp_portal\n" +
            "(\n" +
            "   corp_id              bigint not null ,\n" +
            "   portal_id            bigint not null ,\n" +
            "   group_corp_id        bigint not null ,\n" +
            "   is_default           smallint default 1 ,\n" +
            "   status_flag          smallint ,\n" +
            "   create_by            bigint default 0 ,\n" +
            "   create_time          datetime ,\n" +
            "   update_by            bigint default 0,\n" +
            "   update_time          datetime,\n" +
            "   primary key (corp_id, portal_id)\n" +
            ");\n" +
            "\n";
    public static final String EMP_PORTAL = "CREATE TABLE emp_portal (\n" +
            "  portal_id bigint PRIMARY KEY NOT NULL,\n" +
            "  corp_id bigint,\n" +
            "  group_corp_id bigint,\n" +
            "  portal_code varchar(100),\n" +
            "  portal_name varchar(100),\n" +
            "  portal_logo varchar(500),\n" +
            "  color_style int,\n" +
            "  home_style int,\n" +
            "  status_flag smallint,\n" +
            "  is_default int,\n" +
            "  pic_path text,\n" +
            "  apc_style smallint,\n" +
            "  font_style smallint,\n" +
            "  emi_network_id bigint,\n" +
            "  network_code varchar(50),\n" +
            "  mx_appid bigint\n" +
            ");\n" +
            "\n";
    public static final String FORM_EXTENSION_FILES = "CREATE TABLE form_extension_files (ID INTEGER PRIMARY KEY AUTOINCREMENT,data_id text NOT NULL,form_id text NOT NULL,user_id text NOT NULL,app_id text,subsystemid text,other_id text,field_id text,ext_field_type text,file_id text,file_path text,status_flag smallint);\n";

    public static final String FS_PICTRUE = "CREATE TABLE fs_picture (\n" +
            "  picture_id bigint(20),\n" +
            "  pic_path varchar(200),\n" +
            "  group_corp_id bigint(20),\n" +
            "  picture_class_code varchar(100),\n" +
            "  purpose_code varchar(100),\n" +
            "  picture_code varchar(100),\n" +
            "  filename varchar(50),\n" +
            "  extname varchar(10),\n" +
            "  sourcename varchar(100),\n" +
            "  picture_desc varchar(1000),\n" +
            "  filesize int(11),\n" +
            "  width smallint(6),\n" +
            "  height smallint(6),\n" +
            "  picture_date datetime,\n" +
            "  status_flag tinyint(4),\n" +
            "  create_by bigint(20),\n" +
            "  create_time datetime,\n" +
            "  update_by bigint(20),\n" +
            "  update_time datetime\n" +
            ");\n" +
            "\n" +
            "CREATE UNIQUE INDEX index_pis ON fs_picture (picture_id);\n" +
            "\n" +
            "CREATE UNIQUE INDEX Index_pid ON fs_picture (picture_id);\n";

        public static final String MOBILE_APP_INFO = "CREATE TABLE mobile_app_info (\n" +
                "  app_id bigint PRIMARY KEY NOT NULL,\n" +
                "  current_version bigint DEFAULT(-1),\n" +
                "    ext1 text DEFAULT(NULL),\n" +
                "      ext2 text DEFAULT(NULL),\n" +
                "      ext3 text DEFAULT(NULL),\n" +
                "      ext4 text DEFAULT(NULL)\n" +
                ");\n" +
                "\n";
        public static final String ORG_ORG_TREE = "CREATE TABLE [org_org_tree](\n" +
                "    [org_id] int(11) PRIMARY KEY NOT NULL, \n" +
                "    [group_corp_id] int(11) NOT NULL, \n" +
                "    [corp_id] int(11), \n" +
                "    [org_type] smallint(6) DEFAULT (1), \n" +
                "    [org_code] varchar(50), \n" +
                "    [org_name] varchar(100), \n" +
                "    [org_shortname] varchar(30), \n" +
                "    [org_pinyin] varchar(200), \n" +
                "    [parent_org_id] int(11), \n" +
                "    [display_order] int(11), \n" +
                "    [tree_code] varchar(100), \n" +
                "    [tree_level] smallint(6), \n" +
                "    [org_phone] varchar(50), \n" +
                "    [org_fax] varchar(50), \n" +
                "    [org_address] varchar(255), \n" +
                "    [org_postalcode] varchar(20), \n" +
                "    [org_desc] varchar(1000), \n" +
                "    [status_flag] smallint(6) DEFAULT (1), \n" +
                "    [third_dept_id] varchar(50), \n" +
                "    [create_by] int(11), \n" +
                "    [create_time] datetime, \n" +
                "    [update_by] int(11), \n" +
                "    [update_time] datetime, \n" +
                "    [efs1] varchar(200), \n" +
                "    [efs2] varchar(200), \n" +
                "    [efs3] varchar(200), \n" +
                "    [efs4] varchar(200), \n" +
                "    [efs5] varchar(200), \n" +
                "    [tree_name] text, \n" +
                "    [org_class] text, \n" +
                "    [public_phone] text, \n" +
                "    [emi_type] SMALLINT(6) DEFAULT 0, \n" +
                "    [third_dept_code] text, \n" +
                "    [emi_dept_code] text, \n" +
                "    [user_count] text);\n" +
                "\n";
        public static final String ORG_USER = "CREATE TABLE [org_user](\n" +
                "    [user_id] int(11) PRIMARY KEY NOT NULL, \n" +
                "    [group_corp_id] int(11), \n" +
                "    [user_code] varchar(50), \n" +
                "    [user_name] varchar(30), \n" +
                "    [user_type] smallint(6) DEFAULT (0), \n" +
                "    [login_type] smallint(6) DEFAULT (1), \n" +
                "    [login_name] varchar(50), \n" +
                "    [login_password] varchar(100), \n" +
                "    [user_nickname] varchar(20), \n" +
                "    [user_pyname] varchar(50), \n" +
                "    [title] varchar(30), \n" +
                "    [emi_type] smallint(6) DEFAULT (0), \n" +
                "    [admin_type] smallint(6) DEFAULT (0), \n" +
                "    [gender] smallint(6), \n" +
                "    [birthday] datetime, \n" +
                "    [head_type] smallint(6), \n" +
                "    [head_picture_id] int(11), \n" +
                "    [email] varchar(100), \n" +
                "    [mobile_phone] varchar(30), \n" +
                "    [office_phone] varchar(30), \n" +
                "    [home_phone] varchar(30), \n" +
                "    [fax] varchar(30), \n" +
                "    [office_address] varchar(100), \n" +
                "    [postal_code] varchar(10), \n" +
                "    [third_user_id] varchar(50), \n" +
                "    [remark] varchar(200), \n" +
                "    [status_flag] smallint(6) DEFAULT (1), \n" +
                "    [create_by] int(11), \n" +
                "    [create_time] datetime, \n" +
                "    [update_by] int(11), \n" +
                "    [update_time] datetime, \n" +
                "    [efs1] varchar(200), \n" +
                "    [efs2] varchar(200), \n" +
                "    [efs3] varchar(200), \n" +
                "    [efs4] varchar(200), \n" +
                "    [efs5] varchar(200), \n" +
                "    [efs6] varchar(200), \n" +
                "    [efs7] varchar(200), \n" +
                "    [efs8] varchar(200), \n" +
                "    [efs9] varchar(200), \n" +
                "    [efs10] varchar(200), \n" +
                "    [efi1] int(11), \n" +
                "    [efi2] int(11), \n" +
                "    [efi3] int(11), \n" +
                "    [efi4] int(11), \n" +
                "    [efi5] int(11), \n" +
                "    [efd1] datetime, \n" +
                "    [efd2] datetime, \n" +
                "    [efd3] datetime, \n" +
                "    [efn1] decimal(15, 4), \n" +
                "    [efn2] decimal(15, 4), \n" +
                "    [efn3] decimal(15, 4), \n" +
                "    [emi_user_id] text);\n" +
                "\n" +
                "CREATE UNIQUE INDEX [Index_gl]\n" +
                "ON [org_user](\n" +
                "    [group_corp_id], \n" +
                "    [login_name]);\n" +
                "\n";
        public static final String ORG_USER_CONTACT = "CREATE TABLE org_user_contact (\n" +
                "  user_id int(11) NOT NULL,\n" +
                "  contact_id int(11) NOT NULL,\n" +
                "  group_corp_id int(11),\n" +
                "  contact_org_id int(11),\n" +
                "  contact_name varchar(100),\n" +
                "  display_order int(11),\n" +
                "  contact_info varchar(1000),\n" +
                "  status_flag smallint(6) DEFAULT(1),\n" +
                "  create_time datetime,\n" +
                "  update_time datetime, create_by text, update_by text,\n" +
                "  PRIMARY KEY(user_id, contact_id)\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX Index_UC ON org_user_contact (user_id, contact_id);\n" +
                "\n";

        public static final String ORG_USER_ORG = "CREATE TABLE [org_user_org](\n" +
                "    [org_id] int(11), \n" +
                "    [user_id] int(11), \n" +
                "    [group_corp_id] int(11), \n" +
                "    [corp_id] int(11), \n" +
                "    [is_manager] smallint(6), \n" +
                "    [org_title] varchar(40), \n" +
                "    [user_titlename] varchar(20), \n" +
                "    [office_phone], \n" +
                "    [fax] varchar(50), \n" +
                "    [create_time] datetime, \n" +
                "    [update_time] datetime, \n" +
                "    [display_order] INT(11), \n" +
                "    [org_order] text, \n" +
                "    [status_flag] text, \n" +
                "    [create_by] text, \n" +
                "    [update_by] text, \n" +
                "    [efs1] text, \n" +
                "    [efs2] text, \n" +
                "    [efs3] text, \n" +
                "    PRIMARY KEY([org_id] ASC, [user_id] ASC));\n" +
                "\n";

        public static final String ORG_USERFIELD = "CREATE TABLE org_userfield (\n" +
                "  userfield_id int(11) PRIMARY KEY NOT NULL,\n" +
                "  corp_id int(11),\n" +
                "  field_name varchar(50),\n" +
                "  display_level smallint(6),\n" +
                "  display_name varchar(50),\n" +
                "  display_order smallint(6),\n" +
                "  safe_level smallint(6),\n" +
                "  usergroup_protect smallint(6),\n" +
                "  protect_mode varchar(50),\n" +
                "  can_edit smallint(6) DEFAULT(0),\n" +
                "  action_type smallint(6) DEFAULT('0'),\n" +
                "  action_define varchar(1000) DEFAULT(NULL),\n" +
                "  create_by int(11) DEFAULT('0'),\n" +
                "  create_time datetime DEFAULT(NULL),\n" +
                "  update_by int(11) DEFAULT('0'),\n" +
                "  update_time datetime DEFAULT(NULL)\n" +
                ", group_corp_id text);\n" +
                "\n";
        public static final String PORTAL_APP = "CREATE TABLE portal_app (\n" +
                "  portal_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  picture_normal varchar(500),\n" +
                "  picture_selected varchar(500),\n" +
                "  picture_disabled varchar(500),\n" +
                "  appcenter_include smallint,\n" +
                "  appcenter_include_atfirst smallint,\n" +
                "  appcenter_remove smallint,\n" +
                "  appcenter_diplay_order int,\n" +
                "  tab_include smallint,\n" +
                "  leftmenu_include smallint,\n" +
                "  righttopmenu_include smallint,\n" +
                "  status_flag smallint, display_title VARCHAR(200),\n" +
                "  PRIMARY KEY(portal_id, app_id)\n" +
                ");\n" +
                "\n";
        public static final String PORTAL_LEFT_MENU = "CREATE TABLE portal_left_menu (\n" +
                "  menu_item_id bigint PRIMARY KEY NOT NULL,\n" +
                "  portal_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  picture_normal varchar(500),\n" +
                "  picture_selected varchar(500),\n" +
                "  picture_disabled varchar(500),\n" +
                "  display_title varchar(50),\n" +
                "  display_order bigint,\n" +
                "  status_flag smallint\n" +
                ");\n" +
                "\n";

        public static final String PORTAL_RIGHTTOP_MENU = "CREATE TABLE portal_righttop_menu (\n" +
                "  righttop_item_id bigint PRIMARY KEY NOT NULL,\n" +
                "  portal_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  picture_normal varchar(500),\n" +
                "  picture_selected varchar(500),\n" +
                "  picture_disabled varchar(500),\n" +
                "  display_title varchar(50),\n" +
                "  display_order bigint,\n" +
                "  status_flag smallint\n" +
                ");\n" +
                "\n";
        public static final String PORTAL_TAB = "CREATE TABLE portal_tab (\n" +
                "  tab_item_id bigint PRIMARY KEY NOT NULL,\n" +
                "  portal_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  picture_normal varchar(500),\n" +
                "  picture_selected varchar(500),\n" +
                "  picture_disabled varchar(500),\n" +
                "  display_title varchar(50),\n" +
                "  display_order bigint,\n" +
                "  status_flag smallint\n" +
                ");\n" +
                "\n";

        public static final String PORTAL_TAB_MENU = "CREATE TABLE portal_tab_menu (\n" +
                "  tab_item_id bigint NOT NULL,\n" +
                "  righttop_item_id bigint NOT NULL,\n" +
                "  display bigint,\n" +
                "  status_flag smallint,\n" +
                "  PRIMARY KEY(tab_item_id, righttop_item_id)\n" +
                ");\n" +
                "\n";
        public static final String SHORTCUTKEYS = "CREATE TABLE shortcutkeys (\n" +
                "  parent_app_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  type smallint NOT NULL,\n" +
                "  status_flag smallint NOT NULL,\n" +
                "  app_status_flag smallint NOT NULL,\n" +
                "  display_order integer,\n" +
                "  PRIMARY KEY(parent_app_id, app_id, type)\n" +
                ");\n" +
                "\n";

        public static final String USER_APC_PORTAL_APP = "CREATE TABLE user_apc_portal_app (\n" +
                "  portal_id bigint NOT NULL,\n" +
                "  app_id bigint NOT NULL,\n" +
                "  user_using smallint,\n" +
                "  display_order int,\n" +
                "  status_flag smallint,\n" +
                "  PRIMARY KEY(portal_id, app_id)\n" +
                ");\n" +
                "\n";

        public static final String V_CONTACT_VIEW = "CREATE VIEW [V_contact] AS \n" +
                "SELECT [ouc].[user_id] AS [myuser_id], \n" +
                "       [ou].*, \n" +
                "       [cp].[pic_path], \n" +
                "       [ouc].[status_flag] AS [ouc_status_flag]\n" +
                "FROM   [org_user_contact] [ouc]\n" +
                "       LEFT JOIN [org_user] [ou] ON [ouc].[contact_id] = [ou].[user_id]\n" +
                "       LEFT JOIN [fs_picture] [cp] ON [ou].[head_picture_id] = [cp].[picture_id];\n" +
                "\n";
        public static final String V_ORG_USER_ORG_VIEW = "CREATE VIEW [v_org_user_org] AS \n" +
                "SELECT [u].[group_corp_id], \n" +
                "       [ut].[corp_id], \n" +
                "       [ut].[org_id], \n" +
                "       [ut].[org_name], \n" +
                "       [ut].[tree_code], \n" +
                "       [ut].[tree_name], \n" +
                "       [ut].[tree_level], \n" +
                "       [ut].[parent_org_id], \n" +
                "       [u].[user_id], \n" +
                "       [u].[user_name], \n" +
                "       [ouo].[display_order],        \n" +
                "       [ouo].[org_title] org_user_org_org_title,       \n" +
                "       [ouo].[user_titlename] org_user_org_user_titlename,       \n" +
                "       [ouo].[fax] org_user_org_fax,       \n" +
                "       [ouo].[office_phone] org_user_org_office_phone,\n" +
                "       [u].[update_time], \n" +
                "       [u].[birthday], \n" +
                "       [u].[title], \n" +
                "       [u].[gender], \n" +
                "       [u].[email], \n" +
                "       [u].[mobile_phone], \n" +
                "       [u].[office_phone], \n" +
                "       [u].[home_phone], \n" +
                "       [u].[fax], \n" +
                "       [u].[office_address], \n" +
                "       [u].[postal_code], \n" +
                "       [u].[home_phone], \n" +
                "       [u].[fax], \n" +
                "       [u].[efs1], \n" +
                "       [u].[efs2], \n" +
                "       [u].[efs3], \n" +
                "       [u].[efs4], \n" +
                "       [u].[efs5], \n" +
                "       [u].[efs6], \n" +
                "       [u].[efs7], \n" +
                "       [u].[efs8], \n" +
                "       [u].[efs9], \n" +
                "       [u].[efs10], \n" +
                "       [u].[efi1], \n" +
                "       [u].[efi2], \n" +
                "       [u].[efi3], \n" +
                "       [u].[efi4], \n" +
                "       [u].[efi5], \n" +
                "       [u].[efd1], \n" +
                "       [u].[efd2], \n" +
                "       [u].[efd3], \n" +
                "       [u].[efn1], \n" +
                "       [u].[efn2], \n" +
                "       [u].[efn3], \n" +
                "       [u].[status_flag], \n" +
                "       [ouo].[status_flag] AS [ouo_status_flag],\n" +
                "\t   [ouo].[org_order] AS [ouo_org_order]\n" +
                "FROM   [org_user] [u]\n" +
                "       LEFT JOIN [org_user_org] [ouo] ON [u].[user_id] = [ouo].[user_id]\n" +
                "       LEFT JOIN [org_org_tree] [ut] ON [ut].[org_id] = [ouo].[org_id];\n" +
                "\n";

        public static final String APC_APP_APPGROUP = "CREATE TABLE apc_app_appgroup( appgroup_app_id BIGINT(20) NOT NULL , app_id BIGINT(20) NOT NULL , display_order DECIMAL(9 , 1) DEFAULT NULL , group_corp_id BIGINT(20) NOT NULL , status_flag SMALLINT(6) DEFAULT '1' , create_by BIGINT(20) DEFAULT NULL , create_time DATETIME DEFAULT NULL , update_by BIGINT(20) DEFAULT NULL , update_time DATETIME DEFAULT NULL , PRIMARY KEY(appgroup_app_id , app_id));";

        public static final String V_USER_VIEW = "CREATE VIEW V_User as select ou.*,cp.pic_path from org_user ou left join fs_picture cp on ou.head_picture_id = cp.picture_id;\n" ;

        public static final String COM_PARAM_GROUPCORP = "CREATE TABLE cm_param_groupcorp (\n" +
                "  group_corp_id bigint(20) NOT NULL,\n" +
                "  param_name varchar(50) NOT NULL,\n" +
                "  param_value varchar(500) NOT NULL,\n" +
                "  param_desc varchar(500) DEFAULT NULL,\n" +
                "  status_flag smallint(6) DEFAULT 1,\n" +
                "  create_by bigint(20) DEFAULT NULL,\n" +
                "  create_time datetime DEFAULT NULL ,\n" +
                "  update_by bigint(20) DEFAULT NULL,\n" +
                "  update_time datetime DEFAULT NULL ,\n" +
                "  PRIMARY KEY (group_corp_id,param_name)\n" +
                ");\n";

        public static final String VD_TAB_DEFINE = "CREATE TABLE vd_tab_define (\n" +
                "  define_id bigint(20) NOT NULL  ,\n" +
                "  tab_id varchar(50) NOT NULL ,\n" +
                "  flow_id varchar(50) NOT NULL,\n" +
                "  form_key varchar(50) DEFAULT '0' ,\n" +
                "  tab_name varchar(50) NOT NULL ,\n" +
                "  tab_type smallint(6) NOT NULL DEFAULT '1' ,\n" +
                "  tab_style smallint(6) NOT NULL DEFAULT '0' ,\n" +
                "  tab_purpose smallint(6) NOT NULL DEFAULT '0' ,\n" +
                "  tab_order smallint(6) NOT NULL DEFAULT '1' ,\n" +
                "  tab_visible smallint(6) NOT NULL DEFAULT '1' ,\n" +
                "  tab_content longtext ,\n" +
                "  event_type smallint(6) DEFAULT '-1' ,\n" +
                "  event_api varchar(500) DEFAULT NULL ,\n" +
                "  tab_define_json text ,\n" +
                "  group_corp_id bigint(20) NOT NULL ,\n" +
                "  create_by bigint(20) DEFAULT NULL ,\n" +
                "  create_time bigint(20) DEFAULT NULL ,\n" +
                "  update_by bigint(20) DEFAULT NULL ,\n" +
                "  update_time datetime NOT NULL, \n" +
                "   PRIMARY KEY (define_id))";

        public static final String MOBILE_SEARCH = "CREATE TABLE IF NOT EXISTS mobile_search (time INTEGER NOT NULL ,portal_id varchar(50) NOT NULL,app_id varchar(50) NOT NULL,user_id varchar(50) NOT NULL,field_name varchar(50) NOT NULL,dics_id varchar(50), dics_name varchar(50),dics_value varchar(50), PRIMARY KEY (time,user_id,app_id,portal_id,field_name,dics_id,dics_name));";

}
