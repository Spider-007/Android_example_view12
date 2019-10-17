
package com.htmitech.emportal.ui.plugin.report;

import com.htmitech.emportal.entity.ReportResult;

import java.io.Serializable;
import java.util.List;

/** （通用）参数条件：界面上确定的报表的参数的值， */
@SuppressWarnings("serial")
public class ReportParametersFromUI implements Serializable {
    private List<ReportResult> items;

    public ReportParametersFromUI(List<ReportResult> items) {
        this.items = items;
    }

    public List<ReportResult> getItems() {
        return items;
    }
}
    
