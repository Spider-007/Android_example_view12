package com.htmitech.adapter;

import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.htmitech.domain.Node;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.TreeHelper;
import com.htmitech.listener.OnTreeNodeClickListener;

/**
 *tony
 * 树节点的父adapter
 */
public abstract class TreeListViewAdapter extends BaseAdapter
{

	protected Context mContext;
	/**
	 * 存储所有可见的Node
	 */
	protected List<Node> mNodes;
	protected LayoutInflater mInflater;
	/**
	 * 存储所有的Node
	 */
	protected List<Node> mAllNodes;

	/**
	 * 点击的回调接口
	 */
	private OnTreeNodeClickListener onTreeNodeClickListener;

	private boolean isSoSo = true;

	public void setOnTreeNodeClickListener(
			OnTreeNodeClickListener onTreeNodeClickListener)
	{
		this.onTreeNodeClickListener = onTreeNodeClickListener;
	}

	/**
	 * 
	 * @param context
	 * @param datas
	 * @param defaultExpandLevel
	 *            默认展开几级树
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public TreeListViewAdapter( Context context, List<SYS_Department> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		mContext = context;
		/**
		 * 对所有的Node进行排序
		 */
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		/**
		 * 过滤出可见的Node
		 */
		mNodes = TreeHelper.filterVisibleNode(mAllNodes);
		mInflater = LayoutInflater.from(context);

	}

	/**
	 *
	 * @param isFlag
	 * isFlag 为true 还原
	 * false 变化筛选
	 */
	public void setData(boolean isFlag){
		this.isSoSo = isFlag;
		if(isFlag)
		{
			mNodes = TreeHelper.filterVisibleNode(mAllNodes);
		}
		else
		{
			mNodes = TreeHelper.filterVisibleNodeShow(mAllNodes);
		}
	}

	/**
	 * 相应ListView的点击事件 展开或关闭某节点
	 * 
	 * @param position
	 */
	public void expandOrCollapse(int position)
	{
		Node n = mNodes.get(position);

		if (n != null)// 排除传入参数错误异常
		{
			if (!n.isLeaf())
			{
				n.setExpand(!n.isExpand());
				mNodes = TreeHelper.filterVisibleNode(mAllNodes);
				notifyDataSetChanged();// 刷新视图
			}
		}
	}

	public List<Node> getAllNodes(){
		return mAllNodes;
	}

	@Override
	public int getCount()
	{
		return mNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Node node = mNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		if(!node.isPeople){
			if(isSoSo){
				convertView.setPadding(node.getLevel() * 25, 3, 3, 3);
			}else{
				convertView.setPadding(25, 3, 3, 3);
			}
		}else{
			convertView.setPadding(25, 3, 3, 3);
		}
		convertView.setOnClickListener(new DepartmentPeopleOnClickListener(position));



		return convertView;
	}

	public abstract View getConvertView(Node node, int position,
			View convertView, ViewGroup parent);

	/**
	 * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
	 */

	class DepartmentPeopleOnClickListener implements View.OnClickListener {
		public int position;
		public DepartmentPeopleOnClickListener(int position){
			this.position = position;
		}
		@Override
		public void onClick(View positions) {
			if (onTreeNodeClickListener != null)
			{
				expandOrCollapse(position);
//				positions.startAnimation(new ViewExpandAnimation(positions));
				if (onTreeNodeClickListener != null)
				{
					onTreeNodeClickListener.onClick(mNodes.get(position),
							position);
				}
			}
		}
	}
}
