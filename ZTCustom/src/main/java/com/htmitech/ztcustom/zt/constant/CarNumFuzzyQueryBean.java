package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * Created by htmitech on 2018/8/8.
 */

public class CarNumFuzzyQueryBean {


    /**
     * success : true
     * totals : 10
     * msg : null
     * results : [{"vehicleno":"5070490","vehiclename":null},{"vehicleno":"5070491","vehiclename":null},{"vehicleno":"5070492","vehiclename":null},{"vehicleno":"5070493","vehiclename":null},{"vehicleno":"5070494","vehiclename":null},{"vehicleno":"5070495","vehiclename":null},{"vehicleno":"5070496","vehiclename":null},{"vehicleno":"5070497","vehiclename":null},{"vehicleno":"5070498","vehiclename":null},{"vehicleno":"5070499","vehiclename":null}]
     */

    private boolean success;
    private String totals;
    private Object msg;
    private List<ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * vehicleno : 5070490
         * vehiclename : null
         */

        private String vehicleno;
        private Object vehiclename;
        private String totalWeight;

        private String reportStation;

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getReportStation() {
            return reportStation;
        }

        public void setReportStation(String reportStation) {
            this.reportStation = reportStation;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        private String sendDate;

        public String getVehicleno() {
            return vehicleno;
        }

        public void setVehicleno(String vehicleno) {
            this.vehicleno = vehicleno;
        }

        public Object getVehiclename() {
            return vehiclename;
        }

        public void setVehiclename(Object vehiclename) {
            this.vehiclename = vehiclename;
        }
    }
}
