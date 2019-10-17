//
//package com.htmitech.emportal.ui.login.data.logindata;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.json.JSONObject;
//
//public class Data {
//
//	
//    
//    private Integer unreadNoticeCount;
//    
//    private String resultSign;
//
//    public void parseJson(JSONObject json) throws Exception {
//        unreadNoticeCount = json.optInt("unread_notice_count");
//        resultSign = json.optString("result_sign");
//    }
//    
//    /**
//     * 
//     * @return
//     *     The unreadNoticeCount
//     */
//    public Integer getUnreadNoticeCount() {
//        return unreadNoticeCount;
//    }
//
//    /**
//     * 
//     * @param unreadNoticeCount
//     *     The unread_notice_count
//     */
//    public void setUnreadNoticeCount(Integer unreadNoticeCount) {
//        this.unreadNoticeCount = unreadNoticeCount;
//    }
//
//    /**
//     * 
//     * @return
//     *     The resultSign
//     */
//    public String getResultSign() {
//        return resultSign;
//    }
//
//    /**
//     * 
//     * @param resultSign
//     *     The result_sign
//     */
//    public void setResultSign(String resultSign) {
//        this.resultSign = resultSign;
//    }
//
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
//
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder().append(unreadNoticeCount).append(resultSign).toHashCode();
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Data) == false) {
//            return false;
//        }
//        Data rhs = ((Data) other);
//        return new EqualsBuilder().append(unreadNoticeCount, rhs.unreadNoticeCount).append(resultSign, rhs.resultSign).isEquals();
//    }
//
//}
