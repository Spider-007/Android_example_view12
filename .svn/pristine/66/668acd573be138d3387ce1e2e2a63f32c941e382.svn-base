package Interface;

import android.content.Context;
import com.htmitech.myEnum.LogManagerEnum;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public interface INetWorkManager {
    /**
     *请求之前先进行调用funactionStart获取FunactionId
     *
     *
     * 此方法过期 现在使用新的方法进行
     */
    @Deprecated
    public void logFunactionStart(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode);

    /**
     * 请求之前调用funactionstart 来获取funactionId
     * @param context
     * @param mObserverCallBackType
     * @param mLogManagerEnum
     */
    public void logFunactionStart(Context context, ObserverCallBackType mObserverCallBackType, LogManagerEnum mLogManagerEnum);
    /**
     * 结束的时候进行调用
     * resultStatus ---> 成功  1 失败 0
     */
    public void logFunactionFinsh(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode, String resultInfo, State upState);

    enum State{
        UNKONW(0),
        FAIL(2),
        SUCCESS(1),
        CANCEL(3);
        int stateValue;
        State(int stateValue){
            this.stateValue = stateValue;
        }

        public int getStateValue() {
            return stateValue;
        }
    }
    /**
     * 唤醒时调好用
     * @param context
     * @param mObserverCallBackType
     * @param requestName
     * @param funactionCode
     */

    public void logFunactionOnce(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode);

    public void setAppId(String appId);

}
