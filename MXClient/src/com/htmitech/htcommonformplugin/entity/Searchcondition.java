package com.htmitech.htcommonformplugin.entity;

import java.util.List;

/**
 * 查询条件
 */
public class Searchcondition {

    /// <summary>
    /// 平台相关，应用ID（必传）
    /// </summary>
    public String app_id = "";
    /// <summary>
    /// 平台相关，平台用户ID（必传）
    /// </summary>
    public String user_id = "";
    /// <summary>
    /// 要查询的时间段，查询条件的开始时间
    /// </summary>
    public String starttime = "";
    /// <summary>
    /// 要查询的时间段，查询条件的结束时间
    /// </summary>
    public String endtime = "";
    /// <summary>
    /// 要查询的时间段，通过设定最近n天来设定的，如，常用的：最近3天，最近7天，最近一个月（30天）
    /// </summary>
    public int days;
    /// <summary>
    /// 标题关键字，支持模糊搜索
    /// </summary>
    public String title_keyword = "";
    /// <summary>
    /// 待办已办标记：0，待办；1已办； 空表示不限定
    /// </summary>
    public String todoflag = "";

    /// <summary>
    /// 我发起的标记：0，不限；1只查询我发起的；
    /// </summary>
    public String mystartflag = "";
    /// <summary>
    /// 我关注的标记：0，不限；1只查询我关注的；
    /// </summary>
    public String myfavflag = "";
    /// <summary>
    /// 他人关注的标记：0，不限；1只查询他人关注的；
    /// </summary>
    public String otherfavflag = "";
    /// <summary>
    /// 模块名称关键字，
    /// 支持模糊搜索，如“公文呈批件”，“公文收文”模块，输入“公文”来检索出所有流程名称中含“公文”的流程待办或已办项目
    /// </summary>
    public String modulename = "";

    /// <summary>
    /// 通用查询标记：扩展用
    /// </summary>
    public String flag = "";

    /// <summary>
    /// 其他条件，如果给出的常用查询条件不能包含，则选择更通用的条件传递方式。该方式是一个list。 包含了一组条件，该组条件会原封不动的传递到目标系统的接口中。
    /// 本质上，查询就是一些参数。明确了哪些字段分别要查询的参数值是什么，传递到目标系统即可。
    /// 如，目标系统中查询条件可能这样组合： BLR（办理人）=当前登录人 && 督办单位 字段'DBDW'='秘书一科'
    /// 移动端只需要配置字段： 'BLR':'{account_id}'(平台user_id对应的目标系统的account_id); 'DBDW':'秘书一科'。 至于中间的逻辑关系是“与”还是“或”，手机端不关心。 只要把值传递到业务系统的实现接口即可。
    /// </summary>
    public List<Conidtionfiled> otherconditions;

    /// <summary>
    /// 翻页使用变量，当前页码。（从0开始）
    /// </summary>
    public int page_num;
    /// <summary>
    /// 翻页使用变量，每页最多能容纳的记录行数。
    /// </summary>
    public int page_size;

    public String TimeoutValue;//新增
}
