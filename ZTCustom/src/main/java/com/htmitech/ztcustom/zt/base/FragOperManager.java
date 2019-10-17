package com.htmitech.ztcustom.zt.base;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragOperManager {
    /**
     * FragmentActivity 实例
     */
    private FragmentActivity context;

    /**
     * Fragment 管理器
     */
    private FragmentManager fManager;

    /**
     * 装Fragment的容器
     */
    private int containerViewId;

    /**
     * 该Activity所有fragment的集合
     */
    private List<Fragment> fragments;

    /**
     * @param context         FragmentActivity 实例
     * @param containerViewId 容器Id
     */
    public FragOperManager(FragmentActivity context, int containerViewId) {
        super();
        this.context = context;
        this.containerViewId = containerViewId;
        fManager = this.context.getSupportFragmentManager();
        fragments = new ArrayList<Fragment>();
    }

    /**
     * @param fragment    要替换的 fragment
     * @param tag         fragment 标签
     * @param isBackStack 是否要回滚
     */
    public void replaceFragment(Fragment fragment, String tag, boolean isBackStack) {
        fragments.add(fragment);
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(containerViewId, fragment, tag);
        if (isBackStack) {
            fTransaction.addToBackStack(tag);
        }
        fTransaction.commit();
    }

    /**
     * @param fragment    要替换的 fragment
     * @param tag         fragment 标签
     * @param isBackStack 是否要回滚
     */
    public void addFragment(Fragment fragment, String tag, boolean isBackStack) {
        fragments.add(fragment);
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.add(containerViewId, fragment, tag);
        if (isBackStack) {
            fTransaction.addToBackStack(tag);
        }
        fTransaction.commit();
    }

    /**
     * 模拟按下返回键
     * tag可以为null或者相对应的tag，flags只有0和1(POP_BACK_STACK_INCLUSIVE)两种情况
     * <p>
     * 如果tag为null，flags为0时，弹出回退栈中最上层的那个fragment。
     * <p>
     * 如果tag为null ，flags为1时，弹出回退栈中所有fragment。
     * <p>
     * 如果tag不为null，那就会找到这个tag所对应的fragment，flags为0时，弹出该
     * <p>
     * fragment以上的Fragment，如果是1，弹出该fragment（包括该fragment）以
     * <p>
     * 上的fragment。
     */
    public void goBack(String name, int flags) {
        fManager.popBackStack(name, flags);
        if (fragments.size() > 0) {
            fragments.remove(fragments.size() - 1);
        }
    }

    /**
     * 通过tag获取到某个Fragment
     *
     * @param tag 标签
     * @return
     */
    public Fragment getFragmentByTag(String tag) {
        return fManager.findFragmentByTag(tag);
    }

    /**
     * 删除某个Fragment
     *
     * @param fragment 实例
     */
    public void removeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            fragments.remove(fragment);
        }
    }

    /**
     * 隐藏Fragment 没有删除view
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
        }
    }

    /**
     * 获取所有的Fragment
     *
     * @return
     */
    public List<Fragment> getFragmentList() {
        return fManager.getFragments();
    }

    /**
     * 获取Fragment管理器
     *
     * @return
     */
    public FragmentManager getFragmentManager() {
        return fManager;
    }

    /**
     * 设置容器的Id
     *
     * @param containerViewId
     */
    public void setContainerViewId(int containerViewId) {
        this.containerViewId = containerViewId;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}
