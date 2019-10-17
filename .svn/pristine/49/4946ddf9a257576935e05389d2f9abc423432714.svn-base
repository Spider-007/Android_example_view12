package com.htmitech.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.addressbook.ChooseGeneralActivity;
import com.htmitech.addressbook.InitWebView;
import com.htmitech.addressbook.PeopleMessageActivity;
import com.htmitech.app.Constant;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.domain.ApcUserdefinePortal;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_User;
import com.htmitech.fragment.AddressFragment;
import com.htmitech.fragment.UpdateUserDetailsMessageFragment;
import com.htmitech.listener.CallBackRequestListener;

import htmitech.com.componentlibrary.content.ComponentConstant;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.listener.CallbackMX;
import com.htmitech.listener.ISearchResult;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.CheckStatus;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.BitmapCache;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImp;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImpOne;
import com.htmitech.unit.ActivityUnit;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 初始化回调的接口
 * @author htrf-pc
 *
 */
public class BookInit {

	private CallbackMX mCallbackMX;



	private static BookInit mBookInit;

	private String crrentUserId;

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String currentUserName;

	private boolean isBoradCast;

	private AddressFragment mAddressFragment;

	private UpdateUserDetailsMessageFragment mUserDetailsMessageFragment;

	public String sdCardPath = "";

	public String bookType = Constant.LOING_INIT;

	private Map<String,AddressFragment> myMap = new HashMap<String,AddressFragment>();

	private String phoneUrl;

	private View convertViews;

	private View linearViews;

	private SYS_User currentUser; // 当前传递的User对象

	private SYS_User indexCurrentUser; //当前用户的所有信息

	private CallCheckAllUserListener mCallCheckUserListener;

	public int indexs;

	private String[] cellPhoneLength = new String[2];


	private OrgUser orgUser;

	private CallBackRequestListener mClassBackRequestListener;

	private PeopleHeadEnum mPeopleHeadEnum = PeopleHeadEnum.THENAME; //设置一个默认的

	private ChooseWayEnum mChooseWayEnum = ChooseWayEnum.PEOPLECHOOSE;  //设置一个默认选择 人员

	private ChooseWay mChooseWay = null; //默认多选

	private ChooseTreeHierarchy mChooseTreeHierarchy;// = ChooseTreeHierarchy.TREE; //默认是树形

	private ChooseSystemBook mChooseSystemAddressBook = ChooseSystemBook.ADDRESSBOOK; // 默认是从本地通讯录中选择

	private BottomButtonSlyteEnum mBottomButtonEnum ; // 底部按钮，是否是可以拖动 还是放在底部  默认是可以拖动的

	private CheckStatus mCheckStatus = null; //默认没有

	private String txlAppId;

	private String portalId = "";

	private Boolean flag;

	private int apc_style = 3;
	/**2017年6月5日15:39:31*/
	private String emi_network_id;
	private String mx_appid;
	private String network_code;
	private String network_name;
	private ApcUserdefinePortal mApcUserdefinePortal;
	private String portalName;
	//新增
	private String corp_id;
	private boolean isCrowdOut = false;//是否被挤掉了
	private HashMap<String,ArrayList<SYS_User>> tempPeopleAppInfo = new HashMap<String,ArrayList<SYS_User>>();
	private String group_corp_id;

	public String getGroup_corp_id() {
		return group_corp_id;
	}

	public void setGroup_corp_id(String group_corp_id) {
		this.group_corp_id = group_corp_id;
	}
	public HashMap<String, ArrayList<SYS_User>> getTempPeopleAppInfo() {
		return tempPeopleAppInfo;
	}

	public void setTempPeopleAppInfo(HashMap<String, ArrayList<SYS_User>> tempPeopleAppInfo) {
		this.tempPeopleAppInfo = tempPeopleAppInfo;
	}

	public String getCorp_id() {
		return TextUtils.isEmpty(corp_id) ? "" : corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	private int isWaterSecurity;
	private ArrayList<SYS_User> checkAllUser ;
	private ISearchResult mISearchResult;

	public ISearchResult getmISearchResult() {
		if(mISearchResult == null){
			mISearchResult = new ISearchResult.DefaultSearchResult();
		}
		return mISearchResult;
	}

	public void setmISearchResult(ISearchResult mISearchResult) {
		this.mISearchResult = mISearchResult;
	}

	public int getIsWaterSecurity() {
		return isWaterSecurity;
	}

	public void setIsWaterSecurity(int isWaterSecurity) {
		this.isWaterSecurity = isWaterSecurity;
	}

	public void setCheckAllUser(ArrayList<SYS_User> checkAllUser){
		this.checkAllUser = checkAllUser;
	}

	public ArrayList<SYS_User> getCheckAllUser(){
		if(checkAllUser == null){
			checkAllUser = new ArrayList<SYS_User>();
		}
		return checkAllUser;
	}
	public ApcUserdefinePortal getmApcUserdefinePortal() {
		if(mApcUserdefinePortal == null){
			mApcUserdefinePortal = new ApcUserdefinePortal();
		}
		return mApcUserdefinePortal;
	}

	public boolean isCrowdOut() {
		return isCrowdOut;
	}

	public void setIsCrowdOut(boolean isCrowdOut) {
		this.isCrowdOut = isCrowdOut;
	}

	public void setmApcUserdefinePortal(ApcUserdefinePortal mApcUserdefinePortal) {

		this.mApcUserdefinePortal = mApcUserdefinePortal;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public int getApc_style() {
		if(apc_style == 0){
			apc_style = 3;
		}
		return apc_style;
	}

	public void setApc_style(int apc_style) {
		this.apc_style = apc_style;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public OrgUser getOrgUser() {
		return orgUser;
	}

	public String getEmi_network_id() {
		return emi_network_id;
	}

	public void setEmi_network_id(String emi_network_id) {
		this.emi_network_id = emi_network_id;
	}

	public String getMx_appid() {
		if(TextUtils.isEmpty(mx_appid)){
			mx_appid = "0";
		}
		return mx_appid;
	}

	public void setMx_appid(String mx_appid) {
		this.mx_appid = mx_appid;
	}

	public String getNetwork_code() {
		return network_code;
	}

	public void setNetwork_code(String network_code) {
		this.network_code = network_code;
	}

	public String getNetwork_name() {
		return network_name;
	}

	public void setNetwork_name(String network_name) {
		this.network_name = network_name;
	}

	public void setOrgUser(OrgUser orgUser) {
		this.orgUser = orgUser;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public CheckStatus getmCheckStatus() {
		return mCheckStatus;
	}
	BitmapCache mBitmapCache ;
	public void setmCheckStatus(CheckStatus mCheckStatus) {
		this.mCheckStatus = mCheckStatus;
	}

	public BottomButtonSlyteEnum getmBottomButtonEnum() {
		return mBottomButtonEnum;
	}
	/**
	 * 设置底部按钮风格 1 表示浮动按钮  2表示底部
	 */
	public void setmBottomButtonEnum(BottomButtonSlyteEnum mBottomButtonEnum) {
		this.mBottomButtonEnum = mBottomButtonEnum;
	}

	public void setCallBackRequestListener(CallBackRequestListener mClassBackRequestListener){

		this.mClassBackRequestListener = mClassBackRequestListener;

	}
	public CallBackRequestListener getCallBackRequestListener(){

		return mClassBackRequestListener;

	}

	public String getTxlAppId() {
		return txlAppId;
	}

	public void setTxlAppId(String txlAppId) {
		this.txlAppId = txlAppId;
	}

	public Bitmap getBitmap() {
		return mBitmapCache.getBitmap("bitmap");
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmapCache.putBitmap("bitmap", bitmap);
	}
	public void bitmapClean(){
		mBitmapCache.clean("bitmap");
	}
	public int getIndexs() {
		return indexs;
	}

	public View getConvertViews() {
		return convertViews;
	}

	public View getLinearViews() {
		return linearViews;
	}

	public void setIndexs(int indexs) {
		this.indexs = indexs;
	}

	public void setLinearViews(View linearViews) {
		this.linearViews = linearViews;
	}

	public void setConvertViews(View convertViews) {
		this.convertViews = convertViews;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}



	public Map<String, AddressFragment> getMyMap() {
		return myMap;
	}

	public void setPhoneUrl(String phoneUrl) {
		this.phoneUrl = phoneUrl;
		Constant.PHONEURL = phoneUrl;
	}

	public UpdateUserDetailsMessageFragment getmUserDetailsMessageFragment() {
		return mUserDetailsMessageFragment;
	}

	public String getSdCardPath() {
		return sdCardPath;
	}

	public void setSdCardPath(String sdCardPath) {
		File file = new File(sdCardPath);
		if(!file.exists()){
			file.mkdirs();
		}
		Constant.SDCARD_PATH = sdCardPath;
		ComponentConstant.SDCARD_PATH = sdCardPath;
		this.sdCardPath = sdCardPath;
	}

	public CallbackMX getmCallbackMX() {
		return mCallbackMX;
	}
	public void setCrrentUserId(String crrentUserId){
		this.crrentUserId = crrentUserId;
	}

	public String getCrrentUserId(){
		return crrentUserId;
	}
	public void setmCallbackMX(CallbackMX mCallbackMX) {
		this.mCallbackMX = mCallbackMX;
	}

	private BookInit() {
		mUserDetailsMessageFragment = new UpdateUserDetailsMessageFragment();
		mAddressFragment = new AddressFragment ();
		myMap.put(bookType,mAddressFragment);
		mBitmapCache = new BitmapCache();
	}

	public void setBoradCast(boolean isBoradCast){
		this.isBoradCast = isBoradCast;
	}
	public boolean getIsBoradCast(){
		return isBoradCast;
	}
	public static BookInit getInstance() {
		if (mBookInit == null) {
			mBookInit = new BookInit();
		}
		return mBookInit;
	}

	public static void clean(){
		mBookInit = null;
	}

	public AddressFragment getmAddressFragment() {
		return mAddressFragment;
	}
	public void setmAddressFragment(AddressFragment mAddressFragment){
		this.mAddressFragment = mAddressFragment;
	}

	/**
	 *
	 * @param mCallCheckUserListener 回调当前选择人或者部门
	 * @param mChooseWayEnum  选择人员方式 人员选择 部门选择 路由选择
	 * @param mChooseWay     选择方式  单选 还是多选
	 * @param mChooseTreeHierarchy  展示方式  是树形 还是层级
	 * @param context
	 */
	public void setCallCheckUserListener(Context context,ChooseWayEnum mChooseWayEnum,ChooseWay mChooseWay,ChooseTreeHierarchy mChooseTreeHierarchy,ChooseSystemBook mChooseSystemAddressBook,String title,boolean isFreeSelectUser,ArrayList<SYS_User> userArrayList,CallCheckAllUserListener mCallCheckUserListener){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title",title);
		map.put("systemUser",userArrayList);
		map.put("isFreeSelectUser", isFreeSelectUser);
//		if(!isBoradCast){
//			Toast.makeText(context, "通讯录数据同步中，请稍后再进入", Toast.LENGTH_LONG).show();
//		}else{
//			ActivityUnit.switchTo((Activity) context, ChooseGeneralActivity.class, map);
//		}
		ActivityUnit.switchTo((Activity) context, ChooseGeneralActivity.class, map);
		this.mCallCheckUserListener = mCallCheckUserListener;
		this.mChooseWayEnum = mChooseWayEnum;
		this.mChooseWay = mChooseWay;
		this.mChooseTreeHierarchy = mChooseTreeHierarchy;
		this.mChooseSystemAddressBook = mChooseSystemAddressBook;
	}
	public CallCheckAllUserListener getCallCheckAllUserListener(){
		return mCallCheckUserListener;
	}

	/**
	 * 跳转
	 * @param mContext
	 */
	public void setPeopleMessageActivity(Context mContext){
//		if(!isBoradCast){
//			Toast.makeText(mContext, "通讯录数据同步中，请稍后再进入", Toast.LENGTH_LONG).show();
//		}else{
//			ActivityUnit.switchTo((Activity) mContext, PeopleMessageActivity.class, null);
//		}
		ActivityUnit.switchTo((Activity) mContext, PeopleMessageActivity.class, null);
//		Intent intent = new Intent(mContext, PeopleMessageActivity.class);
//		mContext.startActivity(intent);
	}

	/**
	 * 调用网页的统一入口
	 * @param mContext
	 * @param url
	 */
	public void activityWebView(Context mContext,String url)
	{
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("url",url);
		ActivityUnit.switchTo((Activity) mContext, InitWebView.class, map);
	}


	/**
	 * 设置头像方式
	 *
	 * SURNAME(1),//姓
	 THENAME(2),//名字
	 DEFAULT(3);//默认的
	 */
	public void setPeopleHead(int value){
		mPeopleHeadEnum = PeopleHeadEnum.getHead(value);
	}
	/**
	 * 设置头像方式
	 *
	 * SURNAME(1),//姓
	 THENAME(2),//名字
	 DEFAULT(3);//默认的
	 */
	public void setPeopleHead(PeopleHeadEnum mPeopleHeadEnum){
		this.mPeopleHeadEnum = mPeopleHeadEnum;
	}
	/**
	 * 获取头像Enum
	 */
	public PeopleHeadEnum getPeopleHeadEnum(){
		return mPeopleHeadEnum;
	}

	/**
	 * 设置路由选择 部门选择  人员选择  自由选择
	 */
	public void setChooseWayEnum(ChooseWayEnum mChooseWayEnum){
		this.mChooseWayEnum = mChooseWayEnum;
	}

	/**
	 * 获取设置的路由选择
	 */

	public ChooseWayEnum getChooseWayEnum(){
		return mChooseWayEnum;
	}


	/**
	 * 设置是否是单选还是多选  0表示多选 1表示单选 默认是多选
	 */
	public void setmChooseWay(ChooseWay mChooseWay) {
		this.mChooseWay = mChooseWay;
	}

	public ChooseWay getmChooseWay() {
		return mChooseWay;
	}
	/**
	 * 设置是树形
	 * 还是层级
	 *
	 */
	public void setmChooseTreeHierarchy(ChooseTreeHierarchy mChooseTreeHierarchy) {
		this.mChooseTreeHierarchy = mChooseTreeHierarchy;
	}
	/**
	 * 获取层级
	 * 或者树形
	 */
	public ChooseTreeHierarchy getmChooseTreeHierarchy() {
		return mChooseTreeHierarchy;
	}

	/**
	 * 设置选择方式 是系统选择 还是本地选择
	 * @param mChooseSystemAddressBook
	 */
	public void setmChooseSystemAddressBook(ChooseSystemBook mChooseSystemAddressBook) {
		this.mChooseSystemAddressBook = mChooseSystemAddressBook;
	}

	public ChooseSystemBook getmChooseSystemAddressBook() {
		return mChooseSystemAddressBook;
	}

	public SYS_User getCurrentUser() {
		if(currentUser == null){
			currentUser = new SYS_User();
		}
		return currentUser;
	}

	public void setCurrentUser(SYS_User currentUser) {
		this.currentUser = currentUser;
	}

	public SYS_User getIndexCurrentUser() {
		return indexCurrentUser;
	}

	public void setIndexCurrentUser(SYS_User indexCurrentUser) {
		this.indexCurrentUser = indexCurrentUser;
	}

	/**
	 * 获取设置头像
	 * @param context
	 * @param imageView
	 * @throws ParseException
	 */
	public void getPhotoBitMap(Context context,ImageView imageView,TextView default_tv) throws ParseException {
		LoadUserAvatar avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
		if(getBitmap() != null){
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(getBitmap());
		}else{
			imageView.setVisibility(View.VISIBLE);
			SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(context);
			SYS_User mSYS_User = mSYS_UserDAO.findLoginNameSYS_User(crrentUserId);


			if(mSYS_User == null ){
				return;
			}
			int headType = Short.parseShort(Constant.ADDRESS_HEADER_TYPE);
			String phoneSrl = mSYS_User.getPhotosurl();
			final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
			if(phoneSrls.equals("")){
				String name = mSYS_User.getFullName();
				if(headType == 4){
					name = mSYS_User.getUserNickname();
				}
				int color = mSYS_User.getColor();
				if(color == 0){
					mSYS_User.setColor(Constant.colorList[1]);
					color = Constant.colorList[1];
				}
				GradientDrawable myGrad = (GradientDrawable)default_tv.getBackground();
				myGrad.setColor(color);
				Pattern p = Pattern.compile("[a-zA-Z]");
				Matcher m  = p.matcher(name);
				int start = 0,end = 0;
				switch (mPeopleHeadEnum){
					case DEFAULT:
						default_tv.setVisibility(View.GONE);
						break;
					case SURNAME:
						start = 0;
						end = 1;
						break;
					case THENAME:
						start = name.length() - 2;
						end = name.length();
						break;
				}

				switch(headType){
					case 0:
						default_tv.setVisibility(View.VISIBLE);
						break;
					case 1:
						default_tv.setVisibility(View.GONE);
						break;
					case 2:
						start = name.length() - 2;
						end = name.length();
						break;
					case 3:
						start = 0;
						end = 1;
						break;
					case 4:
						start = 0;
						end = name.length();
						break;
				}

				if(m.matches()){
					default_tv.setText(name.substring(start, end).toUpperCase() + "");
				}else{
					if(name.length() < 2){
						default_tv.setText(name);
					} else {
						default_tv.setText(name.substring(start,end) + "");
					}
				}
//				mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
				mSYS_User.nameJan = default_tv.getText().toString();
			}else{
				imageView.setVisibility(View.VISIBLE);
				default_tv.setVisibility(View.GONE);
				Bitmap bitmap = avatarLoader.loadImage(imageView,
						phoneSrls, new LoadUserAvatar.ImageDownloadedCallBack() {

							@Override
							public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
								if (imageView.getTag() == phoneSrls) {
									if (BookInit.getInstance().getBitmap() != null) {
										imageView.setImageBitmap(BookInit.getInstance().getBitmap());
									} else {
										imageView.setImageBitmap(bitmap);
									}
									BookInit.getInstance().setBitmap(bitmap);
								}
							}

						});

				if (bitmap != null) {
					if(BookInit.getInstance().getBitmap() != null){
						imageView.setImageBitmap(BookInit.getInstance().getBitmap());
					}else{
						imageView.setImageBitmap(bitmap);
						BookInit.getInstance().setBitmap(bitmap);
					}

				}
			}


//			int color = mSYS_User.getColor();
//			if(color == 0){
//				mSYS_User.setColor(Constant.colorList[1]);
//				color = Constant.colorList[1];
//			}
//			GradientDrawable myGrad = (GradientDrawable)default_tv.getBackground();
//			myGrad.setColor(color);
//			int headType = mSYS_User.getHeadType();
//
//			int start = 0,end = 0;
//			String name = mSYS_User.getFullName();
//			if(headType == 4){
//				name = mSYS_User.getUserNickname();
//			}
//			Pattern p = Pattern.compile("[a-zA-Z]");
//			Matcher m  = p.matcher(name);
//			imageView.setVisibility(View.GONE);
//			default_tv.setVisibility(View.VISIBLE);
//			headType = 0;
//			switch (headType) {
//				case 0:
//					imageView.setVisibility(View.VISIBLE);
//					default_tv.setVisibility(View.GONE);
//					final String avatar = mSYS_User.getPhotosurl();
//					Bitmap bitmap = avatarLoader.loadImage(imageView,
//							avatar, new LoadUserAvatar.ImageDownloadedCallBack() {
//
//								@Override
//								public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
//									if (imageView.getTag() == avatar) {
//										if(BookInit.getInstance().getBitmap() != null){
//											imageView.setImageBitmap(BookInit.getInstance().getBitmap());
//										}else{
//											imageView.setImageBitmap(bitmap);
//										}
//										BookInit.getInstance().setBitmap(bitmap);
//									}
//								}
//
//							});
//
//					if (bitmap != null) {
//						if(BookInit.getInstance().getBitmap() != null){
//							imageView.setImageBitmap(BookInit.getInstance().getBitmap());
//						}else{
//							imageView.setImageBitmap(bitmap);
//							BookInit.getInstance().setBitmap(bitmap);
//						}
//
//					}
//					break;
//				case 1:
//
//					imageView.setVisibility(View.VISIBLE);
//					default_tv.setVisibility(View.GONE);
//					break;
//				case 2:
//					start = name.length() - 2;
//					end = name.length();
//					break;
//				case 3:
//					start = 0;
//					end = 1;
//
//					break;
//				case 4:
//					start = 0;
//					end = name.length();
//					break;
//			}
//			if(m.matches()){
//				default_tv.setText(name.substring(start, end).toUpperCase() + "");
//			}else{
//				if(name.length() < 2){
//					default_tv.setText(name);
//				} else {
//					default_tv.setText(name.substring(start, end) + "");
//				}
//			}

		}
	}

	public void setCellPhoneLength(String[] cellPhoneLength) {
		this.cellPhoneLength = cellPhoneLength;
	}

	public String[] getCellPhoneLength() {
		return cellPhoneLength;
	}

	public CallBackImageSelectImp mCallBackImageSelectImp;

	public CallBackImageSelectImp getmCallBackImageSelectImp() {
		return mCallBackImageSelectImp;
	}

	public void setmCallBackImageSelectImp(CallBackImageSelectImp mCallBackImageSelectImp) {
		this.mCallBackImageSelectImp = mCallBackImageSelectImp;
	}
	public CallBackImageSelectImpOne mCallBackImageSelectImpOne;

	public CallBackImageSelectImpOne getmCallBackImageSelectImpOne() {
		return mCallBackImageSelectImpOne;
	}

	public void setmCallBackImageSelectImpOne(CallBackImageSelectImpOne mCallBackImageSelectImpOne) {
		this.mCallBackImageSelectImpOne = mCallBackImageSelectImpOne;
	}



}
