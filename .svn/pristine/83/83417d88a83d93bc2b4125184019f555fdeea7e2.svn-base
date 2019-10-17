package com.htmitech.myApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.htmitech.api.BookInit;
import com.htmitech.domain.Node;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class BaseApplication{
	public Context  context;
	public static BaseApplication app;
	public ChooseTreeHierarchy mChooseTreeHierarchy;
	public ChooseWay mChooseWay;
	public ArrayList<SYS_User> checkUserChangyong ;
	public ArrayList<SYS_User> checkUserCengji;
	public ArrayList<SYS_User> checkUserTree;
	private BaseApplication(Context context){
		this.context = context;


	}
	public static BaseApplication getApplication (Context  context){
		if(app == null){
			app = new BaseApplication(context);
		}
		return app;
	}
	public void onCreate() {
//		DBManager.getInstance(context);
	}
	public SYS_Department getSYS_Department_() {
		return SYS_Department_;
	}

	public void setSYS_Department_(SYS_Department sYS_Department_) {
		SYS_Department_ = sYS_Department_;
	}
	private List<T_UserRelationship> contactList;
	public void setContext(List<T_UserRelationship> contactList){
		this.contactList = contactList;
	}
	public List<T_UserRelationship> getContactList(){
		return contactList;
	}
	private ArrayList<SYS_User> userList ;

	public SYS_Department SYS_Department_;
	public void clear(){
		SYS_Department_ = new SYS_Department();
		userList = new ArrayList<SYS_User>();
	}
	public ArrayList<SYS_User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<SYS_User> userList) {
		this.userList = userList;
	}

	/**
	 * index == 1 表示常用联系人中选择
	 * 2 表示是组织机构中选择
	 * 3 表示删除
	 * @param index
	 */
	public void setCheck(int index,SYS_User currentSYS_User){
		try{
		mChooseWay = BookInit.getInstance().getmChooseWay();
		mChooseTreeHierarchy = BookInit.getInstance().getmChooseTreeHierarchy();
		Boolean isCheck = false; //单选是false  多选是ture
		switch (mChooseWay){
			case SINGLE_CHOOSE:
				isCheck = false;
				break;
			case MORE_CHOOSE:
				isCheck = true;
				break;
		}
		switch (mChooseTreeHierarchy){
			case TREE:
				Node tempNode = null;
				switch (index){

					case 1:
						boolean isC = currentSYS_User.getIsCheck();
//
						for(Node node :allNode){

							if(node.isPeople ){
								if (node.getmSYS_User().mCheckBox != null)
									node.getmSYS_User().mCheckBox.setChecked(false);
								node.getmSYS_User().setIsCheck(false);
							}
							node.setNumber(-1);
							node.getCheckNumber();
						}
						for(T_UserRelationship mT_UserRelationship :contactList){
							SYS_User mUser = mT_UserRelationship.getmSYS_User();
							if(mUser.mCheckBox != null)
								mUser.mCheckBox.setChecked(false);
							mUser.setIsCheck(false);
						}
						for(Node node :allNode){
							if(node.isPeople && node.getmSYS_User().getUserId().equals(currentSYS_User.getUserId())){
								if (node.getmSYS_User().mCheckBox != null && node.getmSYS_User().mCheckBox.getTag().toString().equals(currentSYS_User.mCheckBox.getTag().toString()))
									node.getmSYS_User().mCheckBox.setChecked(isC);
								node.getmSYS_User().setIsCheck(isC);
								node.setNumber(isC ? 1 : -1);
								node.getCheckNumber();
								break;
							}
						}
						for(T_UserRelationship mT_UserRelationship :contactList){
							if(mT_UserRelationship.getmSYS_User().getUserId().equals(currentSYS_User.getUserId())){
								SYS_User mUser = mT_UserRelationship.getmSYS_User();
								if(mUser.mCheckBox != null && mUser.mCheckBox.getTag().toString().equals(currentSYS_User.mCheckBox.getTag().toString()))
									mUser.mCheckBox.setChecked(isC);
								mUser.setIsCheck(isC);
							}

						}

//						for(Node node :allNode){
//							for(T_UserRelationship mT_UserRelationship :contactList){
//								SYS_User mUser = mT_UserRelationship.getmSYS_User();
//								if(node.getmSYS_User() != null && mUser.getUserId().equals(node.getmSYS_User().getUserId())){
//									if(!isCheck){
//										if(node.getmSYS_User().mCheckBox != null)
//											node.getmSYS_User().mCheckBox.setChecked(false);
//										node.getmSYS_User().setIsCheck(false);
//										node.setNumber(-1);
//										node.getCheckNumber();
//
//										if(currentSYS_User.getUserId().equals(node.getmSYS_User().getUserId())){
//											tempNode = node;
//										}else{
//											if(mUser.mCheckBox != null)
//												mUser.mCheckBox.setChecked(false);
//											mUser.setIsCheck(false);
//
//										}
//									}else{
//										if(currentSYS_User.getUserId().equals(node.getmSYS_User().getUserId())){
//											if(node.getmSYS_User().mCheckBox != null)
//												node.getmSYS_User().mCheckBox.setChecked(mUser.getIsCheck());
//											node.getmSYS_User().setIsCheck(mUser.getIsCheck());
//											node.setNumber(currentSYS_User.getIsCheck() ? 1 : -1);
//											node.getCheckNumber();
//										}
//									}
//								}
//							}
//						}
//						if(!isCheck){
//							if(tempNode.getmSYS_User().mCheckBox != null)
//								tempNode.getmSYS_User().mCheckBox.setChecked(currentSYS_User.getIsCheck());
//							tempNode.getmSYS_User().setIsCheck(currentSYS_User.getIsCheck());
//							tempNode.setNumber(currentSYS_User.getIsCheck() ? 1 : -1);
//							tempNode.getCheckNumber();
//						}
						Log.d("THEE","1");
						break;
					case 2:
//						SYS_User tempUser = null;
						isC = currentSYS_User.getIsCheck();

						for(Node node :allNode){

							if(node.isPeople){
								if (node.getmSYS_User().mCheckBox != null)
									node.getmSYS_User().mCheckBox.setChecked(false);
								node.getmSYS_User().setIsCheck(false);
							}
							node.setNumber(-1);
							node.getCheckNumber();
						}
						for(T_UserRelationship mT_UserRelationship :contactList){
							SYS_User mUser = mT_UserRelationship.getmSYS_User();
							if(mUser.mCheckBox != null)
									mUser.mCheckBox.setChecked(false);
							mUser.setIsCheck(false);
						}
						for(Node node :allNode){
							if(node.isPeople && node.getmSYS_User().getUserId().equals(currentSYS_User.getUserId())){
								if (node.getmSYS_User().mCheckBox != null&& node.getmSYS_User().mCheckBox.getTag().toString().equals(currentSYS_User.mCheckBox.getTag().toString()))
									node.getmSYS_User().mCheckBox.setChecked(isC);
								node.getmSYS_User().setIsCheck(isC);
								node.setNumber(isC ? 1 : -1);
								node.getCheckNumber();
								break;
							}
						}
						for(T_UserRelationship mT_UserRelationship :contactList){
							if(mT_UserRelationship.getmSYS_User().getUserId().equals(currentSYS_User.getUserId())){
								SYS_User mUser = mT_UserRelationship.getmSYS_User();
								if(mUser.mCheckBox != null&& mUser.mCheckBox.getTag().toString().equals(currentSYS_User.mCheckBox.getTag().toString()))
									mUser.mCheckBox.setChecked(isC);
								mUser.setIsCheck(isC);
							}

						}

//
					Log.d("THEE","2");
//						SYS_User tempUser = null;
//						for(Node node :allNode){
//							for(T_UserRelationship mT_UserRelationship :contactList){
//								SYS_User mUser = mT_UserRelationship.getmSYS_User();
//								if(node.getmSYS_User() != null && mUser.getUserId().equals(node.getmSYS_User().getUserId())){
//									if(!isCheck){
//										if(mUser.mCheckBox != null)
//											mUser.mCheckBox.setChecked(false);
//										mUser.setIsCheck(false);
//
//										if(currentSYS_User.getUserId().equals(mUser.getUserId())){
//											tempUser = mUser;
//											tempNode = node;
//										}else{
//											if(!currentSYS_User.getUserId().equals(node.getId())) {
//												if (node.getmSYS_User().mCheckBox != null)
//													node.getmSYS_User().mCheckBox.setChecked(false);
//												node.getmSYS_User().setIsCheck(false);
//												node.setNumber(-1);
//												node.getCheckNumber();
//											}
//										}
//									}else{
//										if(currentSYS_User.getUserId().equals(mUser.getUserId())){
//											if(mUser.mCheckBox != null)
//												mUser.mCheckBox.setChecked(node.getmSYS_User().getIsCheck());
//											mUser.setIsCheck(node.getmSYS_User().getIsCheck());
//										}
//									}
//
//								}
//							}
//						}
//						if(!isCheck){
//							if(tempUser != null) {
//								if (tempUser.mCheckBox != null)
//									tempUser.mCheckBox.setChecked(currentSYS_User.getIsCheck());
//								tempUser.setIsCheck(currentSYS_User.getIsCheck());
//
//							}
//							if(tempNode != null){
//								tempNode.setNumber(currentSYS_User.getIsCheck() ? 1 : -1);
//								tempNode.getCheckNumber();
//							}
//						}
						break;
					case 3:
						for(Node node :allNode){
							for(T_UserRelationship mT_UserRelationship :contactList){
								SYS_User mUser = mT_UserRelationship.getmSYS_User();
								if(node.getmSYS_User() != null &&  mUser.getUserId().equals(node.getmSYS_User().getUserId()) && currentSYS_User.getUserId().equals(mUser.getUserId())){
									if(mUser.mCheckBox != null)
										mUser.mCheckBox.setChecked(false);
									mUser.setIsCheck(false);
									if(node.getmSYS_User().mCheckBox != null)
										node.getmSYS_User().mCheckBox.setChecked(false);
									node.getmSYS_User().setIsCheck(false);
									Log.d("BaseApplication", "currentSYS_User.node");
									if(currentSYS_User.node == null) {
										Log.d("BaseApplication","currentSYS_User.node == null");
										node.setNumber(currentSYS_User.getIsCheck() ? 1 : -1);
										node.getCheckNumber();
									}else{
										Log.d("BaseApplication","currentSYS_User.node != null");
									}
								}
							}
						}
						break;
				}
				break;
			case HIERARCHY:

				switch (index){
					case 1:
						if(!isCheck){
							boolean getIsCheck = currentSYS_User.getIsCheck();
							for(SYS_User user :userListAll){
								if(user.mCheckBox != null)
									user.mCheckBox.setChecked(false);
								user.setIsCheck(false);
								if(user.getUserId().equals(currentSYS_User.getUserId())){
									if(user.mCheckBox != null)
										user.mCheckBox.setChecked(getIsCheck);
									user.setIsCheck(getIsCheck);
								}
							}
							for(T_UserRelationship mT_UserRelationship :contactList){
								SYS_User user = mT_UserRelationship.getmSYS_User();
								if(user.mCheckBox != null)
									user.mCheckBox.setChecked(false);
								user.setIsCheck(false);
								if(user.getUserId().equals(currentSYS_User.getUserId())){
									if(user.mCheckBox != null)
										user.mCheckBox.setChecked(getIsCheck);
									user.setIsCheck(getIsCheck);
								}
							}
						}else{
							for(SYS_User user :userListAll){
								for(T_UserRelationship mT_UserRelationship :contactList){
									SYS_User mUser = mT_UserRelationship.getmSYS_User();
									if(mUser.getUserId().equals(user.getUserId())){
										if(user.mCheckBox != null)
											user.mCheckBox.setChecked(mUser.getIsCheck());
										user.setIsCheck(mUser.getIsCheck());
									}
								}
							}
						}

						break;
					case 2:
						if(!isCheck){ //单选
							{
								boolean getIsCheck = currentSYS_User.getIsCheck();
								for(SYS_User user :userListAll){
									if(user.mCheckBox != null)
										user.mCheckBox.setChecked(false);
									user.setIsCheck(false);
									if(user.getUserId().equals(currentSYS_User.getUserId())){
										if(user.mCheckBox != null)
											user.mCheckBox.setChecked(getIsCheck);
										user.setIsCheck(getIsCheck);
									}
								}
								for(T_UserRelationship mT_UserRelationship :contactList){
									SYS_User user = mT_UserRelationship.getmSYS_User();
									if(user.mCheckBox != null)
										user.mCheckBox.setChecked(false);
									user.setIsCheck(false);
									if(user.getUserId().equals(currentSYS_User.getUserId())){
										if(user.mCheckBox != null)
											user.mCheckBox.setChecked(getIsCheck);
										user.setIsCheck(getIsCheck);
									}
								}
							}
						}else{ //多选
							for(SYS_User user :userListAll){
								for(T_UserRelationship mT_UserRelationship :contactList){
									SYS_User mUser = mT_UserRelationship.getmSYS_User();
									if(mUser.getUserId().equals(user.getUserId())){
										if(mUser.mCheckBox != null)
											mUser.mCheckBox.setChecked(user.getIsCheck());
										mUser.setIsCheck(user.getIsCheck());
									}
								}
							}
						}

						break;
					case 3:
						for(SYS_User user :userListAll){
							for(T_UserRelationship mT_UserRelationship :contactList){
								SYS_User mUser = mT_UserRelationship.getmSYS_User();
								if(mUser.getUserId().equals(user.getUserId() ) && currentSYS_User.getUserId().equals(mUser.getUserId())){
									if(mUser.mCheckBox != null)
										mUser.mCheckBox.setChecked(false);
									mUser.setIsCheck(false);
									if(user.mCheckBox != null)
										user.mCheckBox.setChecked(false);
									user.setIsCheck(false);
								}
							}
						}
						break;
				}

				break;
		}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public List<SYS_User> userListAll;
	public List<Node> allNode;
	public void setUserListAll(List<SYS_User> userListAll){
		this.userListAll = userListAll;
	}
	public void setAllNode(List<Node> allNode){
		this.allNode = allNode;
	}

	public ArrayList<SYS_User> getCheckUserCengji() {
		return checkUserCengji;
	}

	public void setCheckUserCengji(ArrayList<SYS_User> checkUserCengji) {
		this.checkUserCengji = checkUserCengji;
	}

	public ArrayList<SYS_User> getCheckUserChangyong() {
		return checkUserChangyong;
	}

	public void setCheckUserChangyong(ArrayList<SYS_User> checkUserChangyong) {
		this.checkUserChangyong = checkUserChangyong;
	}

	public ArrayList<SYS_User> getCheckUserTree() {
		return checkUserTree;
	}

	public void setCheckUserTree(ArrayList<SYS_User> checkUserTree) {
		this.checkUserTree = checkUserTree;
	}

	/**
	 * 获取所有选择User
	 * @return
	 */
	ArrayList<SYS_User> arrayList = new ArrayList<SYS_User>();
	public ArrayList<SYS_User> getCheckALlUser(){
		if(arrayList == null){
			arrayList = new ArrayList<SYS_User>();
		}
		return arrayList;
	}

	public void setCheckAllUser(ArrayList<SYS_User> arrayList){
		if(arrayList != null && arrayList.size() != 0){
			for(SYS_User mSYS_User : arrayList){
				if(null != mSYS_User && null != mSYS_User.mCheckBox){
					mSYS_User.mCheckBox.setChecked(true);
					mSYS_User.setIsCheck(true);
				}
			}
		}
		this.arrayList = arrayList;
	}
	private ArrayList<SYS_Department> checkSYSDepartment = new ArrayList<SYS_Department>();//选择的部门

	public ArrayList<SYS_Department> getCheckSYSDepartment() {
		return checkSYSDepartment;
	}

}
