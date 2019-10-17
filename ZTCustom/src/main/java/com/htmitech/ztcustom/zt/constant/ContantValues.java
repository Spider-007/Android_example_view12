package com.htmitech.ztcustom.zt.constant;


import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class ContantValues {

    //	public static final String REQUEST_IP = "http://114.112.89.94:8081/";//测试服务器IP
    public static final String REQUEST_IP = PreferenceUtils.getOaLoginUrl() + "/";//测试服务器IP
//    public static final String REQUEST_IP = "http://111.33.90.17:8091" + "/";//测试服务器IP

    // 环形饼状图和条形柱状图
    public static final String TANSHANG_PRODUCE_YEAR = REQUEST_IP + "ZTCloudAPI/api/MobileReport/GetListDefiniensHasDataByIDAndParameters";
    // 环形饼状图和条形柱状图
    public static final String GETVALUES = REQUEST_IP + "ZTCloudAPI/api/MobileReport/GetDataParametersByReportID?reportGuid=";
    // 折线图
    public static final String TANSHANG_ZHEXIAN = REQUEST_IP + "ZTCloudAPI/api/MobileReport/GetChartDefiniensHasDataByIDAndParameters";
    // 单位探伤工作日报
    public static final String DANWEIDAYREPORT = REQUEST_IP + "ZTCloudAPI/api/ZTGetMobileData/IS03_GetUnitDailyPlan";
    // 车间探伤工作日报
    public static final String CHEJIANDAYREPORT = REQUEST_IP + "ZTCloudAPI/api/ZTGetMobileData/IS04_GetCjDailyPlan";
    // 探伤完成情况汇总
    public static final String TANSHANGQINGKUANGHUIZONG = REQUEST_IP + "ZTCloudAPI/api/ZTGetMobileData/IS05_GetCompletePlanStat";
    //获取组织结构
    public static final String GETZUZHIJIGOU = REQUEST_IP + "ZTCloudAPI/api/ZTGetMobileData/IB03_GetOrgList";
    //获取班组的探伤日报
    public static final String GETBANZUDAYREPORT = REQUEST_IP + "ZTCloudAPI/api/ZTGetMobileData/IS04_GetBzDailyPlan";

    //新发折断重伤
    public static final String NEWFINDINJURY = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_XFZDZS";

    //重伤处置
    public static final String INJURYDISPOSE = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZSCZ";

    //重伤列表
    public static final String INJURYDISPOSELIST = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_WCZWXH";

    //伤损明细
    public static final String INJURYDISPOSELISTDETAIL = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_SSMX";

    //探伤任务
    public static final String DECETIONTASK = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_TSRW";

    //谈伤车任务
    public static final String DECETIONCARTASK = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_TSCRW";

    //谈伤车报警
    public static final String DECETIONCARALAEM = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_TSCBJ";


    //发货动态汇总（发货动态一级界面）(测试)
//    public static final String DELIVERYDYNAMICSONE = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_FHDTXMLSHZ";
    //发货动态汇总（发货动态一级界面）
    public static final String DELIVERYDYNAMICSONE =  ServerUrlConstant.SERVER_EMPAPI_URL() + "gateway/rscm/transmit/fydtLXSumQry";

    //发货动态列表查询（发货动态二级界面大维修）(测试)
//    public static final String DELIVERYDYNAMICSTWOFIX = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_FHDTLJHZ";
    //发货动态列表查询（发货动态二级界面大维修）
    public static final String DELIVERYDYNAMICSTWOFIX =  ServerUrlConstant.SERVER_EMPAPI_URL() + "gateway/rscm/transmit/fydtLJSumQry";

    //发货动态列表查询（发货动态二级界面非大维修及大维修三级界面）(测试)
//    public static final String DELIVERYDYNAMICSTWONOFIXORTHREEFIX = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_FHDTHZ";
    //发货动态列表查询（发货动态二级界面非大维修及大维修三级界面）
    public static final String DELIVERYDYNAMICSTWONOFIXORTHREEFIX =  ServerUrlConstant.SERVER_EMPAPI_URL() + "gateway/rscm/transmit/fydtSumQry";

    //发货动态列表查询（发货动态大维修四级界面非大维修三级界面）(测试)
//    public static final String DELIVERYDYNAMICSTGENERAL = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_FHDTLB";
    //发货动态列表查询（发货动态大维修四级界面非大维修三级界面）
    public static final String DELIVERYDYNAMICSTGENERAL =  ServerUrlConstant.SERVER_EMPAPI_URL() + "gateway/rscm/transmit/fydtListQry";

    //发货动态明细(测试)
//    public static final String DELIVERYDYNAMICSDETAIL = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_FHDTMX";
    //发货动态明细
    public static final String DELIVERYDYNAMICSDETAIL =  ServerUrlConstant.SERVER_EMPAPI_URL() + "gateway/rscm/transmit/fydtDetailQry";

    //得到用户的信息 供应链平台（测试）
//    public static final String GETUSERDETAILINFORMATION = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_GETUSER";
    //得到用户的信息 供应链平台
    public static final String GETUSERDETAILINFORMATION = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_GETUSER";

    //得到质量异议列表 供应链平台（测试）
//    public static final String GETZLYYLBCX = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_LBCX";
    //得到质量异议列表 供应链平台
    public static final String GETZLYYLBCX = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_LBCX";

    //得到质量异议页签数量 供应链平台（测试）
//    public static final String GETZLYYTABCOUNT = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_YQSL";
    //得到质量异议页签数量 供应链平台
    public static final String GETZLYYTABCOUNT = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_YQSL";

    //得到质量异议详细数据 供应链平台（测试）
//    public static final String GETZLYYDETAILSEARCH = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_MXCH";
    //得到质量异议详细数据 供应链平台
    public static final String GETZLYYDETAILSEARCH = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_MXCH";


    //得到质量异议提报的数据字典 供应链平台（测试）
//    public static final String GETZLYYDIC = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_SJZD";
    //得到质量异议提报的数据字典 供应链平台
    public static final String GETZLYYDIC = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_SJZD";

    //质量异议提报数据 供应链平台（测试）
//    public static final String SUBMITZLYY = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TBSJ";
    //质量异议提报数据 供应链平台
    public static final String SUBMITZLYY = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TBSJ";


    //质量异议提上传文件 供应链平台（测试）
//    public static final String UPLOADFILEPATH = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TPSC_FileUpload";
    //质量异议上传文件 供应链平台
    public static final String UPLOADFILEPATH = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TPSC_FileUpload";

    //质量异议提上传文件 供应链平台（测试）
//    public static final String ZLYYFILEDOWNLOAD = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TPSC_FileDownload";
    //质量异议上传文件 供应链平台
    public static final String ZLYYFILEDOWNLOAD = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_TPSC_FileDownload";

    //质量异处理和完结 供应链平台（测试）
//    public static final String DEALOVERACTION = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLWJ";
    //质量异议处理和完结 供应链平台
    public static final String DEALOVERACTION = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLWJ";

    //焊轨基地——待焊轨柱状图 供应链平台（测试）
//    public static final String WAITTINGWELDINGBAR = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGZT";
    //焊轨基地——待焊轨柱状图 供应链平台
    public static final String WAITTINGWELDINGBAR = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGZT";

    //焊轨基地——成品轨柱状图 供应链平台（测试）
//    public static final String PRODUCTWELDINGBAR = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGZT";
    //焊轨基地——成品轨柱状图 供应链平台
    public static final String PRODUCTWELDINGBAR = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGZT";

    //焊轨基地 待焊轨按项目 供应链平台（测试）
//    public static final String WELDINGBASEDETAILDHGXM = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGXM";
    //焊轨基地 待焊轨按项目 供应链平台
    public static final String WELDINGBASEDETAILDHGXM = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGXM";

    //焊轨基地 待焊轨按钢厂 供应链平台（测试）
//    public static final String WELDINGBASEDETAILDHGGC = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGGC";
    //焊轨基地 待焊轨按钢厂 供应链平台
    public static final String WELDINGBASEDETAILDHGGC = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_DHGGC";

    //焊轨基地 成品轨按项目 供应链平台（测试）
//    public static final String WELDINGBASEDETAILCPGXM = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGXM";
    //焊轨基地 成品轨按项目 供应链平台
    public static final String WELDINGBASEDETAILCPGXM = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGXM";

    //焊轨基地 成品轨按钢厂 供应链平台（测试）
//    public static final String WELDINGBASEDETAILCPGGC = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGGC";
    //焊轨基地 成品轨按钢厂 供应链平台
    public static final String WELDINGBASEDETAILCPGGC = REQUEST_IP + "ZTCloudAPI/api/ZTHGJDGetMobileData/GetZHCX_HGJD_CPGGC";

    //得到质量异议处理情况列表 供应链平台（测试）
//    public static final String GETZLYYCLQKLIST = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLQKLB";
    //得到质量异议处理情况列表 供应链平台
    public static final String GETZLYYCLQKLIST = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLQKLB";

    //质量异议填写处理情况 供应链平台（测试）
//    public static final String SUBMITQUALITYOBJECTIONDETAILCLQK = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLQK";
    //质量异议填写处理情况 供应链平台
    public static final String SUBMITQUALITYOBJECTIONDETAILCLQK = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_CLQK";

    //得到质量异议流程列表 供应链平台（测试）
//    public static final String GETZLYYDQLCLIST = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_LCLB";
    //得到质量异议流程列表 供应链平台
    public static final String GETZLYYDQLCLIST = REQUEST_IP + "ZTCloudAPI/api/ZTZHCXGetMobileData/GetZHCX_ZLYY_LCLB";


    //车辆追踪 车号模糊查询 (测试)
    // public static final String CARNUMFUZZYQUERY =  "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_CLZZ_GETVEHICLENO";
    public static final String CARNUMFUZZYQUERY = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_CLZZ_GETVEHICLENO";

    //车辆动态(车辆追踪 二级页面 车辆详情)（测试）
    // public static final String VEHICLEDYNAMICS = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_CLZZ_CLZT";
    public static final String VEHICLEDYNAMICS = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_CLZZ_CLZT";


    //质保书页面 获取建设单位列表
    // public static final String GETBUILDER = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_JSDW";
    public static final String GETBUILDER = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_JSDW";

    //质保书页面 获取收货单位列表
//    public static final String GETRECREIVEGOODS = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_SHDW";
    public static final String GETRECREIVEGOODS = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_SHDW";

    //质保书页面 获取到站列表
//    public static final String GETSTATION = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_DZ";
    public static final String GETSTATION = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_DZ";


    //质保书页面 质保书首页 质保书首页汇总
    // public static final String QUALITYBOOKFIRSTZONG = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_MAIN";
    public static final String QUALITYBOOKFIRSTZONG = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_MAIN";

    //质保书二级页面 质保书全路情况
//    public static final String QUALITYBOOKSITUATION = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_SECOND";
    public static final String QUALITYBOOKSITUATION = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_SECOND";

    //质保书三级页面 质保书详情
//    public static final String QUALITYBOOKDETAIL = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_DETAIL";
    public static final String QUALITYBOOKDETAIL = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_ZBS_DETAIL";

    //用户注册申请
//    public static final String USERREGISTE = "http://htrf.dscloud.me:8030/ZTZSCloudApi/api/ZTZHZBSMobileData/ZTGSZHCX_USER_APPLY";
//    public static final String USERREGISTE = REQUEST_IP + "ZTCloudAPI/api/ZTZHZBSMobileData/ZTGSZHCX_USER_APPLY";
    public static final String USERREGISTE =  ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ZT_REGISTER_SAVE;


}
