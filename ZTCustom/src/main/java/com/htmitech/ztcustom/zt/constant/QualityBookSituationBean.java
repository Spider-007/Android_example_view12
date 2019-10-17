package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * Created by htmitech on 2018/8/9.
 */

public class QualityBookSituationBean {


    /**
     * success : true
     * totals : 7
     * msg : 查询成功
     * results : [{"zbsid":"G2016B0150","senddate":"20160306","guixing":"60kg/m","caizhi":"U71MN","sudu":"","dunshu":"340480","genshu":null,"dzname":"轧钢站","uploadid":"B03A000000","cftype":"B"},{"zbsid":"2016H0170-1","senddate":"20160110","guixing":"310","caizhi":"YQ450NQR1","sudu":"","dunshu":"50277","genshu":null,"dzname":"济南","uploadid":"B03A000000","cftype":"B"},{"zbsid":"G20142907","senddate":"20140930","guixing":"50kg/m","caizhi":"U71Mn","sudu":"","dunshu":"20660","genshu":null,"dzname":"自提","uploadid":"B03A000000","cftype":"B"},{"zbsid":"G2016XB060","senddate":"20160301","guixing":"60N","caizhi":"U75V","sudu":"","dunshu":"338733","genshu":null,"dzname":"河口南","uploadid":"B03A000000","cftype":"B"},{"zbsid":"G20142960","senddate":"20141005","guixing":"50kg/m","caizhi":"U71Mn","sudu":"160km/h","dunshu":"103300","genshu":null,"dzname":"双寨","uploadid":"B03A000000","cftype":"B"},{"zbsid":"G20160854","senddate":"20160319","guixing":"50kg/m","caizhi":"U71MN","sudu":"","dunshu":"103300","genshu":null,"dzname":"南口","uploadid":"B03A000000","cftype":"B"},{"zbsid":"G20160889","senddate":"20160323","guixing":"QU100","caizhi":"U71MN","sudu":"","dunshu":"36296","genshu":null,"dzname":"自提","uploadid":"B03A000000","cftype":"B"}]
     */

    private boolean success;
    private String totals;
    private String msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
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
         * zbsid : G2016B0150
         * senddate : 20160306
         * guixing : 60kg/m
         * caizhi : U71MN
         * sudu :
         * dunshu : 340480
         * genshu : null
         * dzname : 轧钢站
         * uploadid : B03A000000
         * cftype : B
         */

        private String zbsid;
        private String senddate;
        private String guixing;
        private String caizhi;
        private String sudu;
        private String dunshu;
        private String genshu;
        private String dzname;
        private String uploadid;
        private String cftype;

        public String getZbsid() {
            return zbsid;
        }

        public void setZbsid(String zbsid) {
            this.zbsid = zbsid;
        }

        public String getSenddate() {
            return senddate;
        }

        public void setSenddate(String senddate) {
            this.senddate = senddate;
        }

        public String getGuixing() {
            return guixing;
        }

        public void setGuixing(String guixing) {
            this.guixing = guixing;
        }

        public String getCaizhi() {
            return caizhi;
        }

        public void setCaizhi(String caizhi) {
            this.caizhi = caizhi;
        }

        public String getSudu() {
            return sudu;
        }

        public void setSudu(String sudu) {
            this.sudu = sudu;
        }

        public String getDunshu() {
            return dunshu;
        }

        public void setDunshu(String dunshu) {
            this.dunshu = dunshu;
        }

        public String getGenshu() {
            return genshu;
        }

        public void setGenshu(String genshu) {
            this.genshu = genshu;
        }

        public String getDzname() {
            return dzname;
        }

        public void setDzname(String dzname) {
            this.dzname = dzname;
        }

        public String getUploadid() {
            return uploadid;
        }

        public void setUploadid(String uploadid) {
            this.uploadid = uploadid;
        }

        public String getCftype() {
            return cftype;
        }

        public void setCftype(String cftype) {
            this.cftype = cftype;
        }
    }
}
