package com.htmitech.emportal.base;

public interface IBaseCallback {
    /**
     * 返回成功信息.
     *
     * @param requestTypeId 请求id
     * @param result        返回对象，可以涵盖任何数据
     */
    void onSuccess(final int requestTypeId, Object result);

//    /**
//     * 返回错误信息.
//     * 
//     * @param requestType 请求的id
//     * @param statusCode 错误状态，取值不为0
//     * @param result 返回对象，可以涵盖任何数据
//     */
//    void onFail(int requestTypeId, final int statusCode, Object result);


    /**
     * 返回错误信息.
     *
     * @param requestTypeId 请求的id
     * @param statusCode    错误状态
     * @param errorMsg      错误消息
     * @param result        返回对象，可以涵盖任何数据
     */
    void onFail(int requestTypeId, final int statusCode, final String errorMsg, Object result);
}
