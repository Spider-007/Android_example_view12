package com.htmitech.ztcustom.zt.app;


import android.text.TextUtils;

import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResult;
import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResultRoot;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.GetDictTypeList;
import com.htmitech.ztcustom.zt.domain.GetStatParam;
import com.htmitech.ztcustom.zt.domain.ReportParameters;
import com.htmitech.ztcustom.zt.domain.damage.DamageSummarResult;
import com.htmitech.ztcustom.zt.domain.longin.GetChildAccount;
import com.htmitech.ztcustom.zt.domain.longin.ListDetails;
import com.htmitech.ztcustom.zt.domain.menu.GetAppMenu;
import com.htmitech.ztcustom.zt.domain.permissions.GetUserAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cache {
	public ReportParameters mReportParameters; //报表返回参数 缓存 总数值列表

	public ReportParameters mReportParametersCate; //设备伤损汇总数据

	public ReportParameters mReportParametersAll;//报表返回参数 缓存 总分类数据

	public DamageSummarResult mDamageSummarResult;//设备伤损汇总数值列表(TotalValue)

	public DamageSummarResult mDamageSummarResultCate;//IR02-MR-设备伤损汇总分类数据(AllData)

	public ArrayList<Dictitemlist> dictitemlist; //伤损类型列表

	public ReportParameters mDynamicReportParameters;//现存伤损动态排行

	public Map<String, DicttypeResult> mDicttypeResult = new HashMap<String, DicttypeResult>(); // 得到单个字典类型的数据列表

	public GetAppMenu mGetAppMenu; //获得APP所有菜单项列表

	public GetUserAuth mGetUserAuth; //-获得用户的权限信息

	public GetCisAccountByAppResultRoot mgetCisAccountByAppResultRoot; //获取全部子账号信息

	public GetChildAccount mGetChildAccount;//-获得子业务系统列表

	public ListDetails mListDetails; // 获取当前业务	detail

	public GetStatParam mGetStatParam;


	public GetCisAccountByAppResult mCisAccountDetail; // 目标系统子账号信息

	public String currentUserId = "";//当前用户ID

	public ArrayList<String> sscdList;
	public ArrayList<String> sblxList;
	public ArrayList<String> czztList;

	public ArrayList<String> getSblxList() {
		if (null == sblxList) {
			sblxList = new ArrayList<>();
		}
		return sblxList;
	}

	public ArrayList<String> getSscdList() {
		if (null == sscdList) {
			sscdList = new ArrayList<>();
		}
		return sscdList;
	}

	public ArrayList<String> getCzztList() {
		if (null == czztList) {
			czztList = new ArrayList<>();
		}
		return czztList;
	}

	public void setCzztList(ArrayList<String> czztList) {
		this.czztList = czztList;
	}

	public void setSblxList(ArrayList<String> sblxList) {
		this.sblxList = sblxList;
	}

	public void setSscdList(ArrayList<String> sscdList) {
		this.sscdList = sscdList;
	}

	public ArrayList<GetDictTypeList> getmGetDictTypeList() {
		if (null == mGetDictTypeList) {
			mGetDictTypeList = new ArrayList<GetDictTypeList>();
		}
		return mGetDictTypeList;
	}

	public void setmGetDictTypeList(ArrayList<GetDictTypeList> mGetDictTypeList) {
		this.mGetDictTypeList = mGetDictTypeList;
	}

	private ArrayList<Dictitemlist> mDicitemList;//获取位置单个数据词典

	public ArrayList<Dictitemlist> getmDicitemList() {
		if (null == mDicitemList) {
			mDicitemList = new ArrayList<Dictitemlist>();
		}
		return mDicitemList;
	}

	public void setmDicitemList(ArrayList<Dictitemlist> mDicitemList) {
		this.mDicitemList = mDicitemList;
	}

	//类型单个数据字典
	private ArrayList<Dictitemlist> lxDicitemList;

	public ArrayList<Dictitemlist> getLxDicitemList() {
		if (null == lxDicitemList) {
			lxDicitemList = new ArrayList<Dictitemlist>();
		}
		return lxDicitemList;
	}

	public void setLxDicitemList(ArrayList<Dictitemlist> lxDicitemList) {
		this.lxDicitemList = lxDicitemList;
	}

	public ArrayList<GetDictTypeList> mGetDictTypeList = new ArrayList<GetDictTypeList>(); // 获取所有词典

	public ListDetails getmListDetails() {
		if (null == mListDetails) {
			mListDetails = new ListDetails();
		}
		return mListDetails;
	}

	public GetCisAccountByAppResult getCisAccountDetail() {
		if (null == mCisAccountDetail) {
			mCisAccountDetail = new GetCisAccountByAppResult();
		}
		return mCisAccountDetail;
	}

	public GetCisAccountByAppResultRoot getCisAccountByAppResultRoot() {
		if (null == mgetCisAccountByAppResultRoot) {
			mgetCisAccountByAppResultRoot = new GetCisAccountByAppResultRoot();
		}
		return getCisAccountByAppResultRoot();
	}


	public void setmListDetails(String BusinessTypeId) {
		for (ListDetails details : mGetChildAccount.ListDetail) {
			if (null != details.BusinessTypeId && details.BusinessTypeId.equals(BusinessTypeId)) {
//				mListDetails = details;
				break;
			}
		}
	}

	public ReportParameters getmDynamicReportParameters() {
		if (null == mDynamicReportParameters) {
			mDynamicReportParameters = new ReportParameters();
		}
		return mDynamicReportParameters;
	}

	public void setmDynamicReportParameters(
			ReportParameters mDynamicReportParameters) {
		if (null == mDynamicReportParameters) {
			this.mDynamicReportParameters = new ReportParameters();
		}
		this.mDynamicReportParameters = mDynamicReportParameters;
	}

	public GetChildAccount getmGetChildAccount() {
		if (null == mGetChildAccount) {
			mGetChildAccount = new GetChildAccount();
		}
		return mGetChildAccount;
	}

	public void setmGetChildAccount(GetChildAccount mGetChildAccount) {
		this.mGetChildAccount = mGetChildAccount;
		//初始化默认业务系统  仅供分享使用
		for (ListDetails details : mGetChildAccount.ListDetail) {
			if (!TextUtils.isEmpty(details.cisAccountId)) {
				if (null != details.cisDeptType && details.cisDeptType.equals(ZTCustomInit.get().getmCache().getmListDetails().cisDeptType)) {
					mListDetails = details;
					return;
				}
			}
		}
		mListDetails = mGetChildAccount.ListDetail.get(0);
	}

	public GetUserAuth getmGetUserAuth() {
		if (null == mGetUserAuth) {
			mGetUserAuth = new GetUserAuth();
		}
		return mGetUserAuth;
	}

	public void setmGetUserAuth(GetUserAuth mGetUserAuth) {
		if (null == mGetUserAuth) {
			this.mGetUserAuth = new GetUserAuth();
		}
		this.mGetUserAuth = mGetUserAuth;
	}

	public GetAppMenu getmGetAppMenu() {
		if (null == mGetAppMenu) {
			mGetAppMenu = new GetAppMenu();
		}
		return mGetAppMenu;
	}

	public void setmGetAppMenu(GetAppMenu mGetAppMenu) {
		if (null == mGetAppMenu) {
			this.mGetAppMenu = new GetAppMenu();
		}
		this.mGetAppMenu = mGetAppMenu;
	}

	public Map<String, DicttypeResult> getmDicttypeResult() {
		return mDicttypeResult;
	}

	public void setmDicttypeResult(Map<String, DicttypeResult> mDicttypeResult) {
		this.mDicttypeResult = mDicttypeResult;
	}

	public ArrayList<Dictitemlist> getDictitemlist() {

		return dictitemlist;
	}

	public void setDictitemlist(ArrayList<Dictitemlist> dictitemlist) {
		this.dictitemlist = dictitemlist;
	}

	public DamageSummarResult getmDamageSummarResult() {
		return mDamageSummarResult;
	}

	public void setmDamageSummarResult(DamageSummarResult mDamageSummarResult) {
		this.mDamageSummarResult = mDamageSummarResult;
	}

	public ReportParameters getmReportParameters() {
		return mReportParameters;
	}

	public void setmReportParameters(ReportParameters mReportParameters) {
		this.mReportParameters = mReportParameters;
	}

	public DamageSummarResult getmDamageSummarResultCate() {
		return mDamageSummarResultCate;
	}

	public void setmDamageSummarResultCate(
			DamageSummarResult mDamageSummarResultCate) {
		this.mDamageSummarResultCate = mDamageSummarResultCate;
	}

	public ReportParameters getmReportParametersCate() {
		return mReportParametersCate;
	}

	public void setmReportParametersCate(ReportParameters mReportParametersCate) {
		this.mReportParametersCate = mReportParametersCate;
	}

	public ReportParameters getmReportParametersAll() {
		return mReportParametersAll;
	}

	public void setmReportParametersAll(ReportParameters mReportParametersAll) {
		this.mReportParametersAll = mReportParametersAll;
	}

	public GetStatParam getmGetStatParam() {
		if (null == mGetStatParam) {
			mGetStatParam = new GetStatParam();
		}
		return mGetStatParam;
	}

	public void setmGetStatParam(GetStatParam mGetStatParam) {
		this.mGetStatParam = mGetStatParam;
	}

}
