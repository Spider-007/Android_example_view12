package com.htmitech.ztcustom.zt.enums;

import android.app.Activity;

import com.htmitech.ztcustom.zt.chinarailway.CarVehicleActivity;
import com.htmitech.ztcustom.zt.chinarailway.DamageSummaryActivity;
import com.htmitech.ztcustom.zt.chinarailway.DeliveryDynamicsActivity;
import com.htmitech.ztcustom.zt.chinarailway.DynamicActivity;
import com.htmitech.ztcustom.zt.chinarailway.GeneralInformationActivity;
import com.htmitech.ztcustom.zt.chinarailway.PlannedCashRateActivity;
import com.htmitech.ztcustom.zt.chinarailway.ProduceProgressActivity;
import com.htmitech.ztcustom.zt.chinarailway.QualityBookActivity;
import com.htmitech.ztcustom.zt.chinarailway.QualityObjectionActivity;
import com.htmitech.ztcustom.zt.chinarailway.ServiceBookActivity;
import com.htmitech.ztcustom.zt.chinarailway.TanShangCompleteSumActivity;
import com.htmitech.ztcustom.zt.chinarailway.TanShangDayReportActivity;
import com.htmitech.ztcustom.zt.chinarailway.WeldingBaseActivity;
import com.htmitech.ztcustom.zt.chinarailway.ZBRealTimeBroadActivity;


/**
 * activity跳转
 *
 * @author Tony
 */
public enum IntentEnum {

    SBSS("MobileReport_ZGTW_SBSSHZ", DamageSummaryActivity.class),
    SCSS("MobileReport_ZGTW_XCSSPM", DynamicActivity.class),
    TSSC("MobileReport_ZGTW_TSSCJD", ProduceProgressActivity.class),
    TSRB("MobileReport_ZGTW_TSGZRB", TanShangDayReportActivity.class),
    TSWCHZ("MobileReport_ZGTW_TSWCHZ", TanShangCompleteSumActivity.class),
    SSBB("MobileReport_ZGTW_SSTZBB", ZBRealTimeBroadActivity.class),
    //    GYDT("MobileReport_ZGTW_GYDT", CopyOfsupplyActivity.class),
    ZHXX("MobileReport_ZGTW_ZHXX", GeneralInformationActivity.class),
    FHDT("MobileReport_ZGTW_FHDT", DeliveryDynamicsActivity.class),
    ZLYY("MobileReport_ZGTW_ZLYY", QualityObjectionActivity.class),
    HGJD("MobileReport_ZGTW_HGJD", WeldingBaseActivity.class),
    CLZZ("MobileReport_ZGTW_CLZZ", CarVehicleActivity.class),
    ZBS("MobileReport_ZGTW_ZBS", QualityBookActivity.class),
    FWZN("MobileReport_ZGTW_FWZN", ServiceBookActivity.class),
    JHDX("MobileReport_ZGTW_FWZN", PlannedCashRateActivity.class);
    private String code;
    private Class<? extends Activity> tableClass;

    private IntentEnum(String code, Class<? extends Activity> tableName) {
        this.code = code;
        this.tableClass = tableName;
    }

    /**
     * 获取获取相对应的activity
     *
     * @param code
     * @return
     */
    public static Class<? extends Activity> getActivity(String code) {
        for (IntentEnum c : IntentEnum.values()) {
            if (c.code.equals(code)) {
                return c.tableClass;
            }
        }
        return null;
    }
}
