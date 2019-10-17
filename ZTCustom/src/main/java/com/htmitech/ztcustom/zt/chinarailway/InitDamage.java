package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.Dicttype;
import com.htmitech.ztcustom.zt.domain.DicttypeList;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.GetDictTypeList;
import com.htmitech.ztcustom.zt.domain.GetStatParam;
import com.htmitech.ztcustom.zt.domain.ReportParameters;
import com.htmitech.ztcustom.zt.domain.damage.Users;
import com.htmitech.ztcustom.zt.domain.longin.GetChildAccount;
import com.htmitech.ztcustom.zt.domain.menu.GetAppMenu;
import com.htmitech.ztcustom.zt.domain.menu.ListNodes;
import com.htmitech.ztcustom.zt.domain.menu.Systems;
import com.htmitech.ztcustom.zt.domain.permissions.GetUserAuth;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;

import htmitech.com.componentlibrary.entity.RequestEntity;

/**
 * 初始化所有信息
 * <p>
 * 字典等
 *
 * @author htrf-pc
 */
public class InitDamage {
    int num = 0;

    public void init(final Context context,
                     final Handler mHandler, final RequestEntity mRequestEntity) {

        // RequestEntity mRequestEntity = new RequestEntity();
        // mRequestEntity.SCode = mLoginEntity.getResult().;
        // mRequestEntity.CVersion = "8.0";
        // mRequestEntity.UserId = "lanyun1";
        // mRequestEntity.IsDev = "0";

        AnsynHttpRequest.request(context, null, CHTTP.GETDATAPARAMETERS
                + CHTTP.REPORTGUID_SSLX, CHTTP.GET, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                ReportParameters mReportParameters = JSON.parseObject(
                        successMessage, ReportParameters.class);
                ZTCustomInit.get().getmCache().setmReportParameters(mReportParameters); // 设置缓存
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 2;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }
        });

        AnsynHttpRequest.request(context, null, CHTTP.GETDATAPARAMETERS
                + CHTTP.REPORTGUID_FENLEI, CHTTP.GET, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                ReportParameters mReportParameters = JSON.parseObject(
                        successMessage, ReportParameters.class);
                ZTCustomInit.get().getmCache().setmReportParametersAll(mReportParameters); // 设置缓存
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub

            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 2;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }
        });

       //    <--------------------Administrator -> 2019-8-16:20:58:获取报表参数--------------------->
        AnsynHttpRequest.request(context, null, CHTTP.GETDATAPARAMETERS
                + CHTTP.REPORTGUIDALL, CHTTP.GET, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                ReportParameters mReportParameters = JSON.parseObject(
                        successMessage, ReportParameters.class);
                ZTCustomInit.get().getmCache().setmReportParametersCate(mReportParameters); // 设置缓存
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                num++;
                Message msg = mHandler.obtainMessage();
                msg.what = 2;
                msg.obj = num;
                mHandler.sendMessage(msg);
            }
        });
        Users object = new Users(ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId);
        Log.e("YJH", "init->cisAccountId--->"+object );
        String url = CHTTP.IB05_GETSSLXLIST;
        AnsynHttpRequest.request(context, object, url, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(
                            String successMessage) {
                        try {
                            ArrayList<Dictitemlist> dictitemlist = (ArrayList<Dictitemlist>) JSON.parseArray(successMessage, Dictitemlist.class);
                            ZTCustomInit.get().getmCache().setDictitemlist(dictitemlist);
                            num++;
                            Message msg = mHandler.obtainMessage();
                            msg.what = 0;
                            msg.obj = num;
                            mHandler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                            num++;
                            Message msg = mHandler.obtainMessage();
                            msg.what = 0;
                            msg.obj = num;
                            mHandler.sendMessage(msg);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated
                        // method stub

                    }

                    @Override
                    public void fail(
                            String exceptionMessage) {
                        // TODO Auto-generated
                        // method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });

        AnsynHttpRequest.request(context, object,
                CHTTP.ZTGETMOBILEDATA, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        Log.d("successMessage", "successMessage === " + successMessage);
                        System.out.println("successMessage === " + successMessage);
                        GetStatParam mGetStatParam = FastJsonUtils.getPerson(successMessage, GetStatParam.class);
                        if (mGetStatParam != null) {
                            ZTCustomInit.get().getmCache().setmGetStatParam(mGetStatParam);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }
                });

      /*  mRequestEntity.CVersion="11.0";
        AnsynHttpRequest.request(context, mRequestEntity, CHTTP.GETAPPMENU,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetAppMenu mGetAppMenu = JSON.parseObject(
                                successMessage, GetAppMenu.class);
                        ZTCustomInit.get().getmCache().setmGetAppMenu(mGetAppMenu); // 设置缓存
                        // 获得APP所有菜单项列表
                        AnsynHttpRequest.request(context, mRequestEntity,
                                CHTTP.GETUSERAUTH, CHTTP.POST,
                                new ObserverCallBack() {

                                    @Override
                                    public void success(String successMessage) {
                                        // TODO Auto-generated method stub
                                        GetUserAuth mGetUserAuth = JSON.parseObject(successMessage, GetUserAuth.class);
                                        if (mGetUserAuth != null && mGetUserAuth.Result != null && mGetUserAuth.Result.ListNode != null) {
                                            ArrayList<ListNodes> myListNodes = mGetUserAuth.Result.ListNode;
                                            ArrayList<ListNodes> nodes = ZTCustomInit.get().getmCache().getmGetAppMenu().ListNode;
                                            for (int i = 0; i < myListNodes.size(); i++) {
                                                String system = myListNodes.get(i).System;

                                                String BusinessTypeId = myListNodes.get(i).BusinessTypeId;
                                                Systems mSystems = JSON.parseObject(system, Systems.class);
                                                for (ListNodes mmListNodes : nodes) {
                                                    if (mmListNodes.Id.equals(myListNodes.get(i).Id)) {
                                                        mmListNodes.mySystem = mSystems;
                                                        mmListNodes.BusinessTypeId = BusinessTypeId;
                                                        break;
                                                    }
                                                }
                                            }
                                            ZTCustomInit.get().getmCache().setmGetUserAuth(mGetUserAuth); // 设置缓存-获得用户的权限信息
                                            Users object = new Users(ZTCustomInit.get().getmCache().getmGetUserAuth().Result.ListNode.get(0).AccountId);

                                            String url = CHTTP.IB05_GETSSLXLIST;
                                            AnsynHttpRequest.request(context, object, url, CHTTP.POST,
                                                    new ObserverCallBack() {
                                                        @Override
                                                        public void success(
                                                                String successMessage) {
                                                            try {
                                                                ArrayList<Dictitemlist> dictitemlist = (ArrayList<Dictitemlist>) JSON.parseArray(successMessage, Dictitemlist.class);
                                                                ZTCustomInit.get().getmCache().setDictitemlist(dictitemlist);
                                                                num++;
                                                                Message msg = mHandler.obtainMessage();
                                                                msg.what = 0;
                                                                msg.obj = num;
                                                                mHandler.sendMessage(msg);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                                num++;
                                                                Message msg = mHandler.obtainMessage();
                                                                msg.what = 0;
                                                                msg.obj = num;
                                                                mHandler.sendMessage(msg);
                                                            }

                                                        }

                                                        @Override
                                                        public void notNetwork() {
                                                            // TODO Auto-generated
                                                            // method stub

                                                        }

                                                        @Override
                                                        public void fail(
                                                                String exceptionMessage) {
                                                            // TODO Auto-generated
                                                            // method stub
                                                            num++;
                                                            Message msg = mHandler.obtainMessage();
                                                            msg.what = 2;
                                                            msg.obj = num;
                                                            mHandler.sendMessage(msg);
                                                        }
                                                    });


                                            */

                                              /**
                                             * 获取时间
                                             *//*
                                            Log.d("successMessage", "mRequestEntity.UserId === " + object.userid);
                                            AnsynHttpRequest.request(context, object,
                                                    CHTTP.ZTGETMOBILEDATA, CHTTP.POST,
                                                    new ObserverCallBack() {

                                                        @Override
                                                        public void success(String successMessage) {
                                                            // TODO Auto-generated method stub
                                                            Log.d("successMessage", "successMessage === " + successMessage);
                                                            System.out.println("successMessage === " + successMessage);
                                                            GetStatParam mGetStatParam = FastJsonUtils.getPerson(successMessage, GetStatParam.class);
                                                            if (mGetStatParam != null) {
                                                                ZTCustomInit.get().getmCache().setmGetStatParam(mGetStatParam);
                                                            }
                                                            num++;
                                                            Message msg = mHandler.obtainMessage();
                                                            msg.what = 0;
                                                            msg.obj = num;
                                                            mHandler.sendMessage(msg);
                                                        }

                                                        @Override
                                                        public void fail(String exceptionMessage) {
                                                            // TODO Auto-generated method stub
                                                            num++;
                                                            Message msg = mHandler.obtainMessage();
                                                            msg.what = 2;
                                                            msg.obj = num;
                                                            mHandler.sendMessage(msg);
                                                        }

                                                        @Override
                                                        public void notNetwork() {
                                                            // TODO Auto-generated method stub

                                                        }
                                                    });
                                        } else {//没有应用权限时直接跳出，进入空白界面
                                            num++;
                                            Message msg = mHandler.obtainMessage();
                                            msg.what = 3;
                                            msg.obj = num;
                                            mHandler.sendMessage(msg);
                                        }
                                    }

                                    @Override
                                    public void notNetwork() {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void fail(String exceptionMessage) {
                                        // TODO Auto-generated method stub
                                        num++;
                                        Message msg = mHandler.obtainMessage();
                                        msg.what = 2;
                                        msg.obj = num;
                                        mHandler.sendMessage(msg);
                                    }
                                });
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });*/

/*        AnsynHttpRequest.request(context, mRequestEntity,
                CHTTP.GETCHILDACCOUNT, CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                    *//*    GetChildAccount mGetChildAccount = JSON.parseObject(
                                successMessage, GetChildAccount.class);
                        ZTCustomInit.get().getmCache().setmGetChildAccount(mGetChildAccount); // 设置缓存-获得子业务系统列表*//*
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);


                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });*/


        //    <--------------------Administrator -> 2019-8-16:16:30:得到的version缓存里面的值是 mAccounts->>catch--------------------->
        //现存伤损动态排行
        AnsynHttpRequest.request(context, mRequestEntity,
                CHTTP.GETDATAPARAMETERS + CHTTP.DYNAMIC, CHTTP.GET,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        ReportParameters mReportParameters = JSON.parseObject(
                                successMessage, ReportParameters.class);
                        //    <--------------------Administrator -> 2019-8-17:11:10: 260714d1-0c1d-410e-9a8e-a680271d5b79--------------------->
                        ZTCustomInit.get().getmCache().setmDynamicReportParameters(
                                mReportParameters); // 设置现存伤损动态排行

                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                });
        /**
         * 获取数据词典
         */
        DicttypeList mDicttypeList = new DicttypeList();
        mDicttypeList.dicttype_list = "all";
        AnsynHttpRequest.request(context, mDicttypeList,
                CHTTP.IB04_GETDICTTYPELIST, CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        ArrayList<GetDictTypeList> mReportParameters = (ArrayList<GetDictTypeList>) JSON
                                .parseArray(successMessage,
                                        GetDictTypeList.class);
                        ZTCustomInit.get().getmCache().setmGetDictTypeList(mReportParameters); // 获取数据词典
                        // 多个
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });

        /**
         * 获取位置单个数据字典
         */

        Dicttype beanwz = new Dicttype();
        beanwz.dicttype = "XB1";
        AnsynHttpRequest.request(context, beanwz, CHTTP.IB04_GETDICTTYPEOBJECT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().setmDicitemList(
                                    dicttypeResult.getDictitemlist());
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });
        /**
         * 获取类型单个数据字典
         */

        Dicttype beanlx = new Dicttype();
        beanlx.dicttype = "GGZLX";
        AnsynHttpRequest.request(context, beanlx, CHTTP.IB04_GETDICTTYPEOBJECT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().setLxDicitemList(
                                    dicttypeResult.getDictitemlist());
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 700;
                        msg.obj = num;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });

        beanlx = new Dicttype();
        beanlx.dicttype = "SBLX";
        AnsynHttpRequest.request(context, beanlx, CHTTP.IB04_GETDICTTYPEOBJECT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().getmDicttypeResult()
                                    .put("SBLX", dicttypeResult);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });

        beanlx = new Dicttype();
        beanlx.dicttype = "SSCD";
        AnsynHttpRequest.request(context, beanlx, CHTTP.IB04_GETDICTTYPEOBJECT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().getmDicttypeResult()
                                    .put("SSCD", dicttypeResult);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });

        beanlx = new Dicttype();
        beanlx.dicttype = "CZZT";
        AnsynHttpRequest.request(context, beanlx, CHTTP.IB04_GETDICTTYPEOBJECT,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().getmDicttypeResult()
                                    .put("CZZT", dicttypeResult);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }
                });


        Dicttype bean = new Dicttype();
        bean.dicttype = "XB1";
        AnsynHttpRequest.request(context, bean,
                CHTTP.IB04_GETDICTTYPEOBJECT, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().getmDicttypeResult()
                                    .put("XB1", dicttypeResult);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }
                });
        bean = new Dicttype();
        bean.dicttype = "Sblx";
        AnsynHttpRequest.request(context, bean,
                CHTTP.IB04_GETDICTTYPEOBJECT, CHTTP.POST,
                new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        DicttypeResult dicttypeResult = FastJsonUtils
                                .getPerson(successMessage, DicttypeResult.class);
                        if (dicttypeResult != null) {
                            ZTCustomInit.get().getmCache().getmDicttypeResult()
                                    .put("Sblx", dicttypeResult);
                        }
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        num++;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = num;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }
                });


    }
}
