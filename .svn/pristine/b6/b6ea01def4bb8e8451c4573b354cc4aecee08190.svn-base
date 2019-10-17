package com.htmitech.htworkflowformpluginnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.R;
import com.htmitech.htworkflowformpluginnew.adapter.MuliteRouteSelectPeopleAdapter;
import com.htmitech.htworkflowformpluginnew.entity.RouteInfo;
import com.htmitech.htworkflowformpluginnew.entity.SelectRouteInfo;
import com.htmitech.htworkflowformpluginnew.util.SystemUser2SYSUser;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.entity.AuthorInfo;

/**
 * Created by heyang on 2018/01/15.
 */

public class MuliteRouteSelectPeopleActivity extends BaseFragmentActivity implements View.OnClickListener {


    private ListView listView;
    private Button buttonConfim;
    private ImageButton title_left_button;
    private TextView title_name;
    private ArrayList<RouteInfo> listRouteInfo;
    private ArrayList<RouteInfo> listRouteInfoTemp;
    private String selectTitle;
    private MuliteRouteSelectPeopleAdapter adapter;
    private ArrayList<SelectRouteInfo> SelectRoutes;//存放要上传的路由和人员信息
    private Map<String, ArrayList<SYS_User>> mapSysUser;
    private List<RouteInfo> listAllCanSellect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulite_route_select_people);
        initView();
        initData();
    }

    protected void initView() {
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_left_button.setOnClickListener(this);
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("多路由人员选择");
        listView = (ListView) findViewById(R.id.lv_mulite_route_select_people);
        buttonConfim = (Button) findViewById(R.id.bt_mulite_route_select_people);
        buttonConfim.setOnClickListener(this);
    }

    private void initData() {
        listAllCanSellect = new ArrayList<RouteInfo>();
        SelectRoutes = new ArrayList<SelectRouteInfo>();
        mapSysUser = new HashMap<String, ArrayList<SYS_User>>();
        Intent intent = getIntent();
        listRouteInfo = (ArrayList<RouteInfo>) intent.getSerializableExtra("ListRouteInfo");
//        selectTitle = intent.getStringExtra("title");
        listRouteInfoTemp = new ArrayList<RouteInfo>();
        for (int i = 0; i < listRouteInfo.size(); i++) {
            if (listRouteInfo.get(i).isAllowSelectUser) {//用户可以自由选择
                listAllCanSellect.add(listRouteInfo.get(i));
                listRouteInfoTemp.add(listRouteInfo.get(i));
                if (listRouteInfo.get(i).routeAuthors != null && listRouteInfo.get(i).routeAuthors.size() != 0) {
                    RouteInfo routeInfo = new RouteInfo();
                    routeInfo.routeID = null;
                    ArrayList<AuthorInfo> RouteAuthors = new ArrayList<AuthorInfo>();
                    for (int k = 0; k < listRouteInfo.get(i).routeAuthors.size(); k++) {
                        if (k < listRouteInfo.get(i).routeAuthors.size() - 1) {
                            routeInfo.routeName += listRouteInfo.get(i).routeAuthors.get(k).getUserName() + " | ";
                        } else {
                            routeInfo.routeName += listRouteInfo.get(i).routeAuthors.get(k).getUserName();
                        }
                        //得到需要上传的用选择的人员信息 （但是还没有选择的）
                        AuthorInfo authorInfo = new AuthorInfo();
                        authorInfo.setUserId(listRouteInfo.get(i).routeAuthors.get(k).getUserId());
                        authorInfo.setUserName(listRouteInfo.get(i).routeAuthors.get(k).getUserName());
                        RouteAuthors.add(authorInfo);
                    }
                    listRouteInfoTemp.add(routeInfo);
                    //得到需要上传的不用选择的数据
                    SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                    selectRouteInfo.routeId = listRouteInfo.get(i).routeID;
                    selectRouteInfo.routeName = listRouteInfo.get(i).routeName;
                    selectRouteInfo.isAllowSelectUser = listRouteInfo.get(i).isAllowSelectUser ? 1 : 0;
                    selectRouteInfo.isFreeSelectUser = listRouteInfo.get(i).isFreeSelectUser ? 1 : 0;
                    selectRouteInfo.isMultiSelectUser = listRouteInfo.get(i).isMultiSelectUser ? 1 : 0;
                    selectRouteInfo.routeAuthors = RouteAuthors;
                    //向需要上传的SelectRoutes中添加数据
                    SelectRoutes.add(selectRouteInfo);
                }
            } else {//用户不可以选择人员
                listRouteInfoTemp.add(listRouteInfo.get(i));
                if (listRouteInfo.get(i).routeAuthors != null && listRouteInfo.get(i).routeAuthors.size() != 0) {
                    RouteInfo routeInfo = new RouteInfo();
                    routeInfo.routeID = null;
                    ArrayList<AuthorInfo> RouteAuthors = new ArrayList<AuthorInfo>();
                    for (int k = 0; k < listRouteInfo.get(i).routeAuthors.size(); k++) {
                        if (k < listRouteInfo.get(i).routeAuthors.size() - 1) {
                            routeInfo.routeName += listRouteInfo.get(i).routeAuthors.get(k).getUserName() + " | ";
                        } else {
                            routeInfo.routeName += listRouteInfo.get(i).routeAuthors.get(k).getUserName();
                        }
                        //得到需要上传的不用选择的人员信息
                        AuthorInfo authorInfo = new AuthorInfo();
                        authorInfo.setUserId(listRouteInfo.get(i).routeAuthors.get(k).getUserId());
                        authorInfo.setUserName(listRouteInfo.get(i).routeAuthors.get(k).getUserName());
                        RouteAuthors.add(authorInfo);
                    }
                    listRouteInfoTemp.add(routeInfo);
                    //得到需要上传的不用选择的数据
                    SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                    selectRouteInfo.routeId = listRouteInfo.get(i).routeID;
                    selectRouteInfo.routeName = listRouteInfo.get(i).routeName;
                    selectRouteInfo.isAllowSelectUser = listRouteInfo.get(i).isAllowSelectUser ? 1 : 0;
                    selectRouteInfo.isFreeSelectUser = listRouteInfo.get(i).isFreeSelectUser ? 1 : 0;
                    selectRouteInfo.isMultiSelectUser = listRouteInfo.get(i).isMultiSelectUser ? 1 : 0;
                    selectRouteInfo.routeAuthors = RouteAuthors;
                    //向需要上传的SelectRoutes中添加数据
                    SelectRoutes.add(selectRouteInfo);
                }
            }
        }
        adapter = new MuliteRouteSelectPeopleAdapter(this, listRouteInfoTemp);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final RouteInfo routeInfo = listRouteInfoTemp.get(position);
                if (routeInfo.getRouteID() == null) {
                    return;
                }
                if (!routeInfo.isAllowSelectUser()) {
                    return;
                }
                ArrayList<SYS_User> userList = SystemUser2SYSUser.system2SysUser(routeInfo.routeAuthors);
                //打开通讯录界面，选择人员
                ArrayList<SYS_User> sys_userArrayList = new ArrayList<SYS_User>();
                if (null != mapSysUser.get(routeInfo.getRouteID())) {
                    sys_userArrayList.addAll(mapSysUser.get(routeInfo.getRouteID()));
                }
                selectTitle = routeInfo.getRouteName();
//                BaseApplication.getApplication(MuliteRouteSelectPeopleActivity.this).setCheckAllUser(sys_userArrayList);
                BookInit.getInstance().setCallCheckUserListener(MuliteRouteSelectPeopleActivity.this, ChooseWayEnum.PEOPLECHOOSE, (!routeInfo.isMultiSelectUser ? ChooseWay.SINGLE_CHOOSE : ChooseWay.MORE_CHOOSE), ChooseTreeHierarchy
                        .HIERARCHY, ChooseSystemBook.SYSTEM, selectTitle, routeInfo.isFreeSelectUser, userList, new CallCheckAllUserListener() {
                    @Override
                    public void checkAll(ArrayList<SYS_User> checkAllUser, ArrayList<SYS_Department> checkAllDepartment) {
                        if (checkAllUser != null && checkAllUser.size() != 0) {

                            //如果原来有的话就先移除
                            if (position < listRouteInfoTemp.size() - 1 && listRouteInfoTemp.get(position + 1).routeID == null) {
                                ArrayList<SelectRouteInfo> SelectRoutesTemp = new ArrayList<SelectRouteInfo>();
                                SelectRoutesTemp.addAll(SelectRoutes);
                                for (int i = 0; i < SelectRoutesTemp.size(); i++) {
                                    if (SelectRoutesTemp.get(i).routeId.equals(listRouteInfoTemp.get(position).getRouteID())) {
                                        SelectRoutes.remove(SelectRoutesTemp.get(i));
                                    }
                                }
                                listRouteInfoTemp.remove(position + 1);
                                if (null != mapSysUser.get(routeInfo.getRouteID())) {
                                    mapSysUser.remove(routeInfo.getRouteID());
                                }
                            }

                            mapSysUser.put(routeInfo.getRouteID(), checkAllUser);
                            ArrayList<AuthorInfo> mAuthorInfoList = new ArrayList<AuthorInfo>();
                            String strName = "";
                            for (SYS_User mSYS_User : checkAllUser) {
                                AuthorInfo mAuthorInfo = new AuthorInfo();
                                mAuthorInfo.setUserId(mSYS_User.getUserId());
                                mAuthorInfo.setUserName(mSYS_User.getFullName());
                                mAuthorInfoList.add(mAuthorInfo);
                                strName += mSYS_User.getFullName() + "|";
                            }
                            //想需要上传的集合中添加数据
                            boolean hasExit = false;
                            for (int i = 0; i < SelectRoutes.size(); i++) {//遍历查看需要上报的集合里面是否已经有了这个对象
                                if (SelectRoutes.get(i).routeId.equals(listRouteInfoTemp.get(position).routeID)) {//原来集合里面已经加过
                                    hasExit = true;
                                    if (SelectRoutes.get(i).routeAuthors != null) {
                                        SelectRoutes.get(i).routeAuthors.clear();
                                        SelectRoutes.get(i).routeAuthors.addAll(mAuthorInfoList);
                                    } else {
                                        SelectRoutes.get(i).routeAuthors = mAuthorInfoList;
                                    }
                                }
                            }
                            if (!hasExit) {
                                SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                                selectRouteInfo.routeId = listRouteInfoTemp.get(position).routeID;
                                selectRouteInfo.routeName = listRouteInfoTemp.get(position).routeName;
                                selectRouteInfo.routeAuthors = mAuthorInfoList;
                                //向需要上传的SelectRoutes中添加数据
                                SelectRoutes.add(selectRouteInfo);
                            }

                            if (position < listRouteInfoTemp.size() - 1) {
                                //如果点击的条目下一个条目的RouteID为空，说明下一个条目是人员 那么就是之前已经添加过了 这时只需要修改一下routename就可以 否则向list中添加一个新的条目
                                if (listRouteInfoTemp.get(position + 1).routeID == null) {
                                    listRouteInfoTemp.get(position + 1).routeName = strName.substring(0, strName.length() - 1);
                                } else {
                                    //向listRouteInfoTemp中添加数据，更新adapter
                                    RouteInfo routeInfoCallBack = new RouteInfo();
                                    routeInfoCallBack.routeID = null;
                                    routeInfoCallBack.routeName = strName.substring(0, strName.length() - 1);
                                    listRouteInfoTemp.add(position + 1, routeInfoCallBack);
                                }
                            } else if (position == listRouteInfoTemp.size() - 1) {
                                //向listRouteInfoTemp中添加数据，更新adapter
                                RouteInfo routeInfoCallBack = new RouteInfo();
                                routeInfoCallBack.routeID = null;
                                routeInfoCallBack.routeName = strName.substring(0, strName.length() - 1);
                                listRouteInfoTemp.add(position + 1, routeInfoCallBack);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            //移除添加过之后后悔了取消的部门和人员数据
                            if (position < listRouteInfoTemp.size() - 1 && listRouteInfoTemp.get(position + 1).routeID == null) {
                                ArrayList<SelectRouteInfo> SelectRoutesTemp = new ArrayList<SelectRouteInfo>();
                                SelectRoutesTemp.addAll(SelectRoutes);
                                for (int i = 0; i < SelectRoutesTemp.size(); i++) {
                                    if (SelectRoutesTemp.get(i).routeId.equals(listRouteInfoTemp.get(position).getRouteID())) {
                                        SelectRoutes.remove(SelectRoutesTemp.get(i));
                                    }
                                }
                                listRouteInfoTemp.remove(position + 1);
                                if (null != mapSysUser.get(routeInfo.getRouteID())) {
                                    mapSysUser.remove(routeInfo.getRouteID());
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_button:
                finish();
                break;
            case R.id.bt_mulite_route_select_people:
                //点击确定按钮
                ArrayList<SelectRouteInfo> SelectRoutesTemp = new ArrayList<SelectRouteInfo>();
                SelectRoutesTemp.addAll(SelectRoutes);
                for (int i = 0; i < listAllCanSellect.size(); i++) {//遍历循环，找到可以人为填写但是没有填写的
                    boolean hasExit = false;
                    for (int k = 0; k < SelectRoutesTemp.size(); k++) {
                        if (SelectRoutesTemp.get(k).routeId.equals(listAllCanSellect.get(i).routeID)) {
                            hasExit = true;
                            break;
                        }
                    }
                    if (!hasExit) {
                        SelectRouteInfo selectRouteInfo = new SelectRouteInfo();
                        selectRouteInfo.routeId = listAllCanSellect.get(i).routeID;
                        selectRouteInfo.routeName = listAllCanSellect.get(i).routeName;
                        selectRouteInfo.isAllowSelectUser = listAllCanSellect.get(i).isAllowSelectUser ? 1 : 0;
                        selectRouteInfo.isFreeSelectUser = listAllCanSellect.get(i).isFreeSelectUser ? 1 : 0;
                        selectRouteInfo.isMultiSelectUser = listAllCanSellect.get(i).isMultiSelectUser ? 1 : 0;
                        selectRouteInfo.routeAuthors = listAllCanSellect.get(i).routeAuthors;
                        //向需要上传的SelectRoutes中添加数据
                        SelectRoutes.add(selectRouteInfo);
                    }
                }
                //遍历循环找出 没有人员信息为空的部分
                for (int i = 0; i < SelectRoutes.size(); i++) {
                    if (SelectRoutes.get(i).routeAuthors == null || SelectRoutes.get(i).routeAuthors.size() == 0) {
                        for (int k = 0; k < listRouteInfo.size(); k++) {
                            if (listRouteInfo.get(k).getRouteID().equals(SelectRoutes.get(i).routeId)) {
                                if (listRouteInfo.get(k).isAllowSelectUser && listRouteInfo.get(k).isFreeSelectUser) {
                                    Toast.makeText(this, "您还有路由未选择人员，请检查后重试", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("SelectRoutes", SelectRoutes);
                setResult(1001, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
