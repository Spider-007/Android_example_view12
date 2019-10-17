package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * Created by htmitech on 2018/8/13.
 */

public class CarVehicleDetailsBean {


    /**
     * success : true
     * totals : 2
     * msg : 查询成功！
     * results : {"runinfo":[{"key":"发运日期","value":"20131202"},{"key":"品名","value":"重轨"},{"key":"轨型","value":"50kg/m"},{"key":"速度","value":"160km/h"},{"key":"热处理要求","value":""},{"key":"到站","value":"邯郸"},{"key":"长度","value":"25"},{"key":"材质","value":"U71Mn"},{"key":"规格","value":"孔轨"},{"key":"车号","value":"5070494,5074371"},{"key":"专用线","value":""},{"key":"收货单位","value":"邯钢运输部"},{"key":"发货根数","value":"80"},{"key":"发货吨数","value":"103.300"}],"runtrack":{"startstation":"包头北","starttime":"20131202","currentstation":"","currenttime":"","arrivestation":"邯郸","arrivetime":null,"vehicleno":"5070494"}}
     */

    private boolean success;
    private String totals;
    private String msg;
    private ResultsBean results;

    @Override
    public String toString() {
        return "CarVehicleDetailsBean{" +
                "success=" + success +
                ", totals='" + totals + '\'' +
                ", msg='" + msg + '\'' +
                ", results=" + results +
                '}';
    }

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
         * runinfo : [{"key":"发运日期","value":"20131202"},{"key":"品名","value":"重轨"},{"key":"轨型","value":"50kg/m"},{"key":"速度","value":"160km/h"},{"key":"热处理要求","value":""},{"key":"到站","value":"邯郸"},{"key":"长度","value":"25"},{"key":"材质","value":"U71Mn"},{"key":"规格","value":"孔轨"},{"key":"车号","value":"5070494,5074371"},{"key":"专用线","value":""},{"key":"收货单位","value":"邯钢运输部"},{"key":"发货根数","value":"80"},{"key":"发货吨数","value":"103.300"}]
         * runtrack : {"startstation":"包头北","starttime":"20131202","currentstation":"","currenttime":"","arrivestation":"邯郸","arrivetime":null,"vehicleno":"5070494"}
         */

        private RuntrackBean runtrack;
        private List<RuninfoBean> runinfo;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "runtrack=" + runtrack +
                    ", runinfo=" + runinfo +
                    '}';
        }

        public RuntrackBean getRuntrack() {
            return runtrack;
        }

        public void setRuntrack(RuntrackBean runtrack) {
            this.runtrack = runtrack;
        }

        public List<RuninfoBean> getRuninfo() {
            return runinfo;
        }

        public void setRuninfo(List<RuninfoBean> runinfo) {
            this.runinfo = runinfo;
        }

        public static class RuntrackBean {
            /**
             * startstation : 包头北
             * starttime : 20131202
             * currentstation :
             * currenttime :
             * arrivestation : 邯郸
             * arrivetime : null
             * vehicleno : 5070494
             */

            private String startstation;
            private String starttime;
            private String currentstation;
            private String currenttime;
            private String arrivestation;
            private String arrivetime;
            private String vehicleno;

            @Override
            public String toString() {
                return "RuntrackBean{" +
                        "startstation='" + startstation + '\'' +
                        ", starttime='" + starttime + '\'' +
                        ", currentstation='" + currentstation + '\'' +
                        ", currenttime='" + currenttime + '\'' +
                        ", arrivestation='" + arrivestation + '\'' +
                        ", arrivetime='" + arrivetime + '\'' +
                        ", vehicleno='" + vehicleno + '\'' +
                        '}';
            }

            public String getStartstation() {
                return startstation;
            }

            public void setStartstation(String startstation) {
                this.startstation = startstation;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getCurrentstation() {
                return currentstation;
            }

            public void setCurrentstation(String currentstation) {
                this.currentstation = currentstation;
            }

            public String getCurrenttime() {
                return currenttime;
            }

            public void setCurrenttime(String currenttime) {
                this.currenttime = currenttime;
            }

            public String getArrivestation() {
                return arrivestation;
            }

            public void setArrivestation(String arrivestation) {
                this.arrivestation = arrivestation;
            }

            public String getArrivetime() {
                return arrivetime;
            }

            public void setArrivetime(String arrivetime) {
                this.arrivetime = arrivetime;
            }

            public String getVehicleno() {
                return vehicleno;
            }

            public void setVehicleno(String vehicleno) {
                this.vehicleno = vehicleno;
            }
        }

        public static class RuninfoBean {
            /**
             * key : 发运日期
             * value : 20131202
             */

            private String key;
            private String value;

            @Override
            public String toString() {
                return "RuninfoBean{" +
                        "key='" + key + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
