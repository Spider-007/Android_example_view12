package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * Created by htmitech on 2018/8/9.
 */

public class QualityBookSearchNewsgetStationListBean {


    /**
     * success : true
     * msg : 查询成功
     * totals : null
     * results : [{"dzid":"dz_10000","dzname":"北京铁路局到站地","status":"1","updatetime":"2018-08-09"},{"dzid":"dz_10001","dzname":"太原铁路局到站地","status":"1","updatetime":"2018-08-11"},{"dzid":"dz_10002","dzname":"上海铁路局到站地","status":"1","updatetime":"2018-08-13"},{"dzid":"dz_10003","dzname":"武汉铁路局到站地","status":"1","updatetime":"2018-08-15"},{"dzid":"dz_10004","dzname":"成都铁路局到站地","status":"1","updatetime":"2018-08-17"}]
     */

    private boolean success;
    private String msg;
    private Object totals;
    private List<ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getTotals() {
        return totals;
    }

    public void setTotals(Object totals) {
        this.totals = totals;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * dzid : dz_10000
         * dzname : 北京铁路局到站地
         * status : 1
         * updatetime : 2018-08-09
         */

        private String dzid;
        private String dzname;
        private String status;
        private String updatetime;
        private String incode;
        private boolean ischecked;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "dzid='" + dzid + '\'' +
                    ", dzname='" + dzname + '\'' +
                    ", status='" + status + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    ", ischecked=" + ischecked +
                    '}';
        }

        public boolean ischecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }

        public String getDzid() {
            return dzid;
        }

        public void setDzid(String dzid) {
            this.dzid = dzid;
        }

        public String getDzname() {
            return dzname;
        }

        public void setDzname(String dzname) {
            this.dzname = dzname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getIncode() {
            return incode;
        }

        public void setIncode(String incode) {
            this.incode = incode;
        }
    }
}
