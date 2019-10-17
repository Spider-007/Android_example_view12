package entiy;

import android.text.TextUtils;

import com.htmitech.domain.ApcUserdefinePortal;

/**
 * Created by htrf-pc on 2016/12/8.
 */
public class EmpPortal {
    public String portal_id;
    public int home_style;
    public int color_style;
    public String portal_name;
    public boolean isCheck;
    public String portal_logo = "";
    public int apc_style; //新增
    public int font_style;//新增
    public long emi_network_id;
    public String network_code;
    public long mx_appid;
    public String corp_id;
    public long group_corp_id;

    public long getGroup_corp_id() {
        return group_corp_id;
    }

    public void setGroup_corp_id(long group_corp_id) {
        this.group_corp_id = group_corp_id;
    }

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public ApcUserdefinePortal mApcUserdefinePortal;

    public String getPortal_id() {
        return portal_id;
    }

    public void setPortal_id(String portal_id) {
        this.portal_id = portal_id;
    }

    public int getHome_style() {
        return home_style;
    }

    public void setHome_style(int home_style) {
        this.home_style = home_style;
    }

    public int getColor_style() {
        return color_style;
    }

    public void setColor_style(int color_style) {
        this.color_style = color_style;
    }

    public String getPortal_name() {
        if (TextUtils.isEmpty(portal_name)) {
            portal_name = "首页";
        }
        return portal_name;
    }

    public void setPortal_name(String portal_name) {
        this.portal_name = portal_name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getPortal_logo() {
        return portal_logo;
    }

    public void setPortal_logo(String portal_logo) {
        this.portal_logo = portal_logo;
    }

    public int getApc_style() {
        return apc_style;
    }

    public void setApc_style(int apc_style) {
        this.apc_style = apc_style;
    }

    public int getFont_style() {
        if (font_style == -2) {
            font_style = -1;
        }
        return font_style;
    }

    public void setFont_style(int font_style) {
        this.font_style = font_style;
    }

    public long getEmi_network_id() {
        return emi_network_id;
    }

    public void setEmi_network_id(long emi_network_id) {
        this.emi_network_id = emi_network_id;
    }

    public String getNetwork_code() {
        return network_code;
    }

    public void setNetwork_code(String network_code) {
        this.network_code = network_code;
    }

    public long getMx_appid() {
        return mx_appid;
    }

    public void setMx_appid(long mx_appid) {
        this.mx_appid = mx_appid;
    }

    public ApcUserdefinePortal getmApcUserdefinePortal() {
        return mApcUserdefinePortal;
    }

    public void setmApcUserdefinePortal(ApcUserdefinePortal mApcUserdefinePortal) {
        this.mApcUserdefinePortal = mApcUserdefinePortal;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof EmpPortal) {
            EmpPortal empPortal = (EmpPortal) o;
            return empPortal.getPortal_id().equals(portal_id);
        }

        return super.equals(o);
    }
}
