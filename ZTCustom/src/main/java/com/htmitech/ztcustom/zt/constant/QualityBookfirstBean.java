package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * Created by htmitech on 2018/8/9.
 */

public class QualityBookfirstBean {


    /**
     * success : true
     * totals : null
     * msg : 查询成功
     * results : {"mainlist":[{"gcid":"10000","gcname":"北京铁路局","currentmonthnum":"50","currentmonthallnum":"80"},{"gcid":"10001","gcname":"太原铁路局","currentmonthnum":"58","currentmonthallnum":"86"},{"gcid":"10002","gcname":"上海铁路局","currentmonthnum":"66","currentmonthallnum":"92"},{"gcid":"10003","gcname":"武汉铁路局","currentmonthnum":"74","currentmonthallnum":"98"},{"gcid":"10004","gcname":"成都铁路局","currentmonthnum":"82","currentmonthallnum":"104"}],"mainsum":{"currentmonthnum":"330","currentmonthallnum":"460"}}
     */

    private boolean success;
    private Object totals;
    private String msg;
    private ResultsBean results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getTotals() {
        return totals;
    }

    public void setTotals(Object totals) {
        this.totals = totals;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * mainlist : [{"gcid":"10000","gcname":"北京铁路局","currentmonthnum":"50","currentmonthallnum":"80"},{"gcid":"10001","gcname":"太原铁路局","currentmonthnum":"58","currentmonthallnum":"86"},{"gcid":"10002","gcname":"上海铁路局","currentmonthnum":"66","currentmonthallnum":"92"},{"gcid":"10003","gcname":"武汉铁路局","currentmonthnum":"74","currentmonthallnum":"98"},{"gcid":"10004","gcname":"成都铁路局","currentmonthnum":"82","currentmonthallnum":"104"}]
         * mainsum : {"currentmonthnum":"330","currentmonthallnum":"460"}
         */

        private MainsumBean mainsum;
        private List<MainlistBean> mainlist;

        public MainsumBean getMainsum() {
            return mainsum;
        }

        public void setMainsum(MainsumBean mainsum) {
            this.mainsum = mainsum;
        }

        public List<MainlistBean> getMainlist() {
            return mainlist;
        }

        public void setMainlist(List<MainlistBean> mainlist) {
            this.mainlist = mainlist;
        }

        public static class MainsumBean {
            /**
             * currentmonthnum : 330
             * currentmonthallnum : 460
             */

            private String currentmonthnum;
            private String currentmonthallnum;

            @Override
            public String toString() {
                return "MainsumBean{" +
                        "currentmonthnum='" + currentmonthnum + '\'' +
                        ", currentmonthallnum='" + currentmonthallnum + '\'' +
                        '}';
            }

            public String getCurrentmonthnum() {
                return currentmonthnum;
            }

            public void setCurrentmonthnum(String currentmonthnum) {
                this.currentmonthnum = currentmonthnum;
            }

            public String getCurrentmonthallnum() {
                return currentmonthallnum;
            }

            public void setCurrentmonthallnum(String currentmonthallnum) {
                this.currentmonthallnum = currentmonthallnum;
            }
        }

        public static class MainlistBean {
            /**
             * gcid : 10000
             * gcname : 北京铁路局
             * currentmonthnum : 50
             * currentmonthallnum : 80
             */

            private String gcid;
            private String gcname;
            private String currentmonthnum;
            private String currentmonthallnum;

            @Override
            public String toString() {
                return "MainlistBean{" +
                        "gcid='" + gcid + '\'' +
                        ", gcname='" + gcname + '\'' +
                        ", currentmonthnum='" + currentmonthnum + '\'' +
                        ", currentmonthallnum='" + currentmonthallnum + '\'' +
                        '}';
            }

            public String getGcid() {
                return gcid;
            }

            public void setGcid(String gcid) {
                this.gcid = gcid;
            }

            public String getGcname() {
                return gcname;
            }

            public void setGcname(String gcname) {
                this.gcname = gcname;
            }

            public String getCurrentmonthnum() {
                return currentmonthnum;
            }

            public void setCurrentmonthnum(String currentmonthnum) {
                this.currentmonthnum = currentmonthnum;
            }

            public String getCurrentmonthallnum() {
                return currentmonthallnum;
            }

            public void setCurrentmonthallnum(String currentmonthallnum) {
                this.currentmonthallnum = currentmonthallnum;
            }
        }
    }
}
