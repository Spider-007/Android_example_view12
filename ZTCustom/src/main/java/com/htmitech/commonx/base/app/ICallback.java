package com.htmitech.commonx.base.app;

/**
 * 任务回调接口.
 * 
 * @author liuweining
 *
 */
public interface ICallback {
    /**
     * 返回成功信息.
     * 
     * @param statusCode 成功状态，取值为0
     * @param result 返回对象，可以涵盖任何数据
     */
    void onSuccess(final int statusCode, Object result);

    /**
     * 返回错误信息.
     * 
     * @param statusCode 错误状态，取值不为0
     * @param result 返回对象，可以涵盖任何数据
     */
    void onFail(final int statusCode, Object result);

    /**
     * 成功的状态码.
     */
    final static int STATUS_CODE_SUCCESS = 0;

    /**
     * 失败的默认状态码.
     */
    final static int STATUS_CODE_DEFAULT_ERROR = -1;
}
