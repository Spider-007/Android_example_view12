/**
 * Created by xiaoheidepro on 16/9/29.
 */
var HT_Event = new Object;

HT_Event.text=101;//文本
HT_Event.text_multi=102;//文本多行
HT_Event.text_multi_row=3;//默认行数
HT_Event.text_editable=103;//文本单行自增

HT_Event.hidden=10000;//隐藏
HT_Event.readonly=10020;//只读
HT_Event.radio=5003;//单选
HT_Event.weuiradio=1001//单选
HT_Event.button=2001;//按钮
HT_Event.checkbox=3001;//复选框
HT_Event.checkbox_switch=3002;//开关
HT_Event.select=4001;//下拉列表
HT_Event.select_slide=4002;
HT_Event.select_slide_bool = false;
HT_Event.weuidate=5003;// 日期
HT_Event.weuidate_bool = false;

HT_Event.htrf_num_Integer=200;
HT_Event.htrf_num_decimal=201;
HT_Event.htrf_num_Integer_thousands=202;
HT_Event.htrf_num_decimal_thousands=203;
HT_Event.htrf_date=300;// 日期
HT_Event.htrf_datetime=301;// 日期时间
HT_Event.htrf_year=302;// 年
HT_Event.htrf_month=303;// 月
HT_Event.htrf_week=304;// 周
HT_Event.htrf_select_id=401;//下拉选择（结果取ID）
HT_Event.htrf_select_name=402;//下拉选择（结果取Name）
HT_Event.htrf_select_value=403;//下拉选择（结果取Value）
HT_Event.htrf_select_input=404;//下拉选择，支持输入（结果取Name）
HT_Event.htrf_radio_id=501;//单选按钮（结果取ID）
HT_Event.htrf_radio_name=502;//单选按钮（结果取Name）
HT_Event.htrf_radio_value=503;//单选按钮（结果取Value）
HT_Event.htrf_checkbox_id=511;//多选按钮（结果取ID）
HT_Event.htrf_checkbox_name=512;//多选按钮（结果取Name）
HT_Event.htrf_checkbox_value=513;//多选按钮（结果取Value）
HT_Event.htrf_select_userId_single=601;//人员单选 从【通讯录】中选择人员，只能单选（结果取ID）
HT_Event.htrf_select_userName_single=602;//从【通讯录】中选择人员，只能单选 （结果取Name，用户中文姓名）
HT_Event.htrf_select_loginName_single=603;//从【通讯录】中选择人员，只能单选 （结果取LoginName，用户登录名）
HT_Event.htrf_select_userId_multi=611;//人员多选 多个结果以|分割 从【通讯录】中选择人员，支持多选（结果取ID）
HT_Event.htrf_select_userName_multi=612;//从【通讯录】中选择人员，支持多选（结果取Name，用户中文姓名）
HT_Event.htrf_select_loginName_multi=613;//从【通讯录】中选择人员，支持多选（结果取LoginName，用户登录名）
HT_Event.htrf_moving_userId_single=701;//人员单选 从【系统用户】中选择人员，只能单选（结果取ID）
HT_Event.htrf_moving_userName_single=702;//从【系统用户】中选择人员，只能单选 （结果取Name，用户中文姓名）
HT_Event.htrf_moving_loginName_single=703;//从【系统用户】中选择人员，只能单选 （结果取LoginName，用户登录名）
HT_Event.htrf_moving_userId_multi=711;//人员多选 多个结果以|分割 从【系统用户】中选择人员，支持多选（结果取ID）
HT_Event.htrf_moving_userName_multi=712;//从【系统用户】中选择人员，支持多选（结果取Name，用户中文姓名）
HT_Event.htrf_moving_loginName_multi=713;//从【系统用户】中选择人员，支持多选（结果取LoginName，用户登录名）
HT_Event.htrf_emi_userId_single=801;//人员单选 从【EMI】中选择人员，只能单选（结果取ID）
HT_Event.htrf_emi_userName_single=802;//从【EMI】中选择人员，只能单选 （结果取Name，用户中文姓名）
HT_Event.htrf_emi_loginName_single=803;//从【EMI】中选择人员，只能单选 （结果取LoginName，用户登录名）
HT_Event.htrf_emi_userId_multi=811;//人员多选 多个结果以|分割 从【EMI】中选择人员，支持多选（结果取ID）
HT_Event.htrf_emi_userName_multi=812;//从【EMI】中选择人员，支持多选（结果取Name，用户中文姓名）
HT_Event.htrf_emi_loginName_multi=813;//从【EMI】中选择人员，支持多选（结果取LoginName，用户登录名）
HT_Event.htrf_select_deptName_single=901;//部门单选 部门选择，只能单选（结果取Name）
HT_Event.htrf_select_deptId_single=902;// 部门选择，只能单选（结果取ID）
HT_Event.htrf_select_deptName_multi=911;//部门多选  多个结果以|分割 部门选择，支持多选 （结果取Name）
HT_Event.htrf_select_deptId_multi=912;//部门选择，支持多选 （结果取ID）
HT_Event.htrf_select_insti_frame=1001;//不限人员、部门。选择的结果有可能是部门，也有可能是人员。只限单选