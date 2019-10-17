package com.htmitech.domain;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ****************************************** 部门
 * ******************************************
 * 
 * @author tony
 * 
 */
public class SYS_Department implements Serializable {
	@TreeNodeId
	public String DepartmentCode = "";
	public String ShortName = "";
	@TreeNodeLabel
	public String FullName = "";
	public String OrganiseType = "";
	@TreeNodePid
	public String ParentDepartment = "";
	public String PostCode = "";
	public String Telephone = "";
	public String Fax = "";
	public String Address = "";
	public Short orgStatus; //组织机构状态：-1: 删除，0-禁用，1-正常
	private boolean isCheck;
	public View checkView = null;
	public CheckBox mCheckBox = null;
	public String orgCode;
	public String treeCode;
	public String tree_level;
	public List<SYS_Department> SYS_DepartmentList = new ArrayList<SYS_Department>();
	public List<SYS_User> SYS_User = new ArrayList<SYS_User>();

	public Node node;

	public List<SYS_Department> getSYS_DepartmentList() {
		return SYS_DepartmentList;
	}
	public int level = 0;
	public SYS_Department parentSYS_Department;
	public int number = 0;
	public int checkNumber = 0;
	public void setNumber(int number) {
		if(number != 0) {
			this.number = number;
		}
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getTree_level() {
		return TextUtils.isEmpty(tree_level) ? "" : tree_level;
	}

	public void setTree_level(String tree_level) {
		this.tree_level = tree_level;
	}

	public void setOrgStatus(Short orgStatus) {
		this.orgStatus = orgStatus;
	}

	public Short getOrgStatus() {
		return orgStatus;
	}

	public int getNumber() {
		return number;
	}

	public SYS_Department getParentSYS_Department() {
		return parentSYS_Department;
	}

	public void setParentSYS_Department(SYS_Department parentSYS_Department) {
		this.parentSYS_Department = parentSYS_Department;
	}

	public void setSYS_DepartmentList(List<SYS_Department> sYS_DepartmentList) {
		SYS_DepartmentList = sYS_DepartmentList;
	}

	public List<SYS_User> getSYS_User() {
		return SYS_User;
	}

	public boolean getIsCheck(){
		return isCheck;
	}
	public void setIsCheck(boolean isCheck){
		this.isCheck = isCheck;
	}

	public void setSYS_User(List<SYS_User> sYS_User) {
		SYS_User = sYS_User;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public String Remark;
	public int IsDelete;
	public String CreatedBy;
	public String CreatedDate;
	public String ModifiedBy;
	public String ModifiedDate;
	public String UniversalPwd;
	public String Pinyin = "";
	public String OULabel;
	public Integer OULevel;
	public String ADCode;
	public String AppCode;
	public String UniversalCode;
	public Integer IsVirtual;
	public String IP;
	public String Port;
	public String ThirdDepartmentId;
	public Integer DisOrder;
	public String suoxie = "";

	public String getSuoxie() {
		return suoxie;
	}

	public void setSuoxie(String suoxie) {
		this.suoxie = suoxie;
	}

	public String getDepartmentCode() {
		return DepartmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		DepartmentCode = departmentCode;
	}

	public String getShortName() {
		return ShortName;
	}

	public void setShortName(String shortName) {
		ShortName = shortName;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getOrganiseType() {
		return OrganiseType;
	}

	public void setOrganiseType(String organiseType) {
		OrganiseType = organiseType;
	}

	public String getParentDepartment() {
		return ParentDepartment;
	}

	public void setParentDepartment(String parentDepartment) {
		ParentDepartment = parentDepartment;
	}

	public String getPostCode() {
		return PostCode;
	}

	public void setPostCode(String postCode) {
		PostCode = postCode;
	}

	public String getTelephone() {
		return Telephone;
	}

	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public int getIsDelete() {
		return IsDelete;
	}

	public void setIsDelete(int isDelete) {
		IsDelete = isDelete;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public String getUniversalPwd() {
		return UniversalPwd;
	}

	public void setUniversalPwd(String universalPwd) {
		UniversalPwd = universalPwd;
	}

	public String getPinyin() {
		return Pinyin;
	}

	public void setPinyin(String pinyin) {
		Pinyin = pinyin;
	}

	public String getOULabel() {
		return OULabel;
	}

	public void setOULabel(String oULabel) {
		OULabel = oULabel;
	}

	public Integer getOULevel() {
		return OULevel;
	}

	public void setOULevel(Integer oULevel) {
		OULevel = oULevel;
	}

	public String getADCode() {
		return ADCode;
	}

	public void setADCode(String aDCode) {
		ADCode = aDCode;
	}

	public String getAppCode() {
		return AppCode;
	}

	public void setAppCode(String appCode) {
		AppCode = appCode;
	}

	public String getUniversalCode() {
		return UniversalCode;
	}

	public void setUniversalCode(String universalCode) {
		UniversalCode = universalCode;
	}

	public Integer getIsVirtual() {
		return IsVirtual;
	}

	public void setIsVirtual(Integer isVirtual) {
		IsVirtual = isVirtual;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}

	public String getThirdDepartmentId() {
		return ThirdDepartmentId;
	}

	public void setThirdDepartmentId(String thirdDepartmentId) {
		ThirdDepartmentId = thirdDepartmentId;
	}

	public Integer getDisOrder() {
		return DisOrder;
	}

	public void setDisOrder(Integer disOrder) {
		DisOrder = disOrder;
	}

	// 重写
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null || !(o instanceof SYS_Department)) {

			return false;
		}
		SYS_Department mSYS_Department = (SYS_Department) o;

		return mSYS_Department.DepartmentCode.equals(DepartmentCode);
	}
	/**
	 * 是否是叶子界点
	 *
	 * @return
	 */
	public boolean isLeaf()
	{
		return SYS_DepartmentList.size() == 0;
	}

	/**
	 * 是否展开
	 */
	private boolean isExpand = false;

	public boolean isExpand()
	{
		return isExpand;
	}

	/**
	 * 设置展开
	 *
	 * @param isExpand
	 */
	public void setExpand(boolean isExpand)
	{
		this.isExpand = isExpand;
		if (!isExpand)
		{

			for (SYS_Department node : SYS_DepartmentList)
			{
				node.setExpand(isExpand);
			}
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}


	/**
	 * 获取level
	 */
	public int getLevel()
	{
		return parentSYS_Department == null ? 0 : parentSYS_Department.getLevel() + 1;
	}

	/**
	 * 是否为跟节点
	 *
	 * @return
	 */
	public boolean isRoot()
	{
		return parentSYS_Department == null;
	}

	/**
	 * 判断父节点是否展开
	 *
	 * @return
	 */
	public boolean isParentExpand()
	{
		if (parentSYS_Department == null)
			return false;
		return parentSYS_Department.isExpand();
	}

	private boolean isChecked = false;//是否选中
	private boolean isHideChecked = true;//CheckBox是否隐藏

	public boolean isChecked() {
		return isCheck;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isHideChecked() {
		return isHideChecked;
	}

	public void setIsHideChecked(boolean isHideChecked) {
		this.isHideChecked = isHideChecked;
	}

	public void setCheckNumber(int check){
		checkNumber += check;
		if(checkNumber <= 0){
			checkNumber = 0;
		}
	}
}
