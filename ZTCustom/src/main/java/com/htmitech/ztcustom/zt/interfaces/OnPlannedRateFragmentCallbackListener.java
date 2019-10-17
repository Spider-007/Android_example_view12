package com.htmitech.ztcustom.zt.interfaces;

public interface OnPlannedRateFragmentCallbackListener {
    void onRequestDataStart();

    void onRequestDataFinish();

    void onItemClickListener(String currentFragment, String goToFragemnt, String typeCode, String gccode, String usedircode, String gcName);
}
