package com.htmitech.domain;

import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.myEnum.ChooseWayEnum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * tony
 *
 */
public class TreeHelper
{

	/**
	 * 传入我们的普通bean，转化为我们排序后的Node
	 * 
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static  List<Node> getSortedNodes(List<SYS_Department> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException

	{
		List<Node> result = new ArrayList<Node>();
		// 将用户数据转化为List<Node>
		List<Node> nodes = convetData2Node(datas);
		// 拿到根节点
		List<Node> rootNodes = getRootNodes(nodes);
		// 排序以及设置Node间关系
		for (Node node : rootNodes)
		{
			addNode(result, node, defaultExpandLevel, 1);
		}
		return result;
	}

	/**
	 * 过滤出所有可见的Node
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Node> filterVisibleNode(List<Node> nodes)
	{
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes)
		{
			// 如果为跟节点，或者上层目录为展开状态
			if (node.isRoot() || node.isParentExpand())
			{
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}

	public static List<Node> filterVisibleNodeShow(List<Node> nodes)
	{
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes)
		{
			if (node.isShow)
			{
				setNodeIcon(node);
				result.add(node);
			}
		}
		List<Node> result_paixu = new ArrayList<Node>();
		//对Node进行排序 开始展示部门 后面展示人员
		for(Node node : result)
		{
			if(!node.isPeople)
			{
				result_paixu.add(node);
			}
		}
		for(Node node : result)
		{
			if(node.isPeople)
			{
				result_paixu.add(node);
			}
		}
		return result_paixu;
	}

	public static void getAllUser(List<SYS_Department> datas,List<SYS_User> users ,List<Node> nodes) throws IllegalAccessException {
//		userAllList.addAll(currentSYS_Department.getSYS_User());
		Node node = null;
		for (SYS_Department t : datas)
		{
			String id = null;
			String pId = null;
			String label = null;
			Class<? extends Object> clazz = t.getClass();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field f : declaredFields)
			{
				if (f.getAnnotation(TreeNodeId.class) != null)
				{
					f.setAccessible(true);
					id = (String) f.get(t);
				}
				if (f.getAnnotation(TreeNodePid.class) != null)
				{
					f.setAccessible(true);
					pId = (String) f.get(t);
				}
				if (f.getAnnotation(TreeNodeLabel.class) != null)
				{
					f.setAccessible(true);
					label = (String) f.get(t);
				}
				if (id != null && pId != null && label != null)
				{
					break;
				}
			}
			number = 0;
			getAllUserNumber(t);
			t.setNumber(number);
			node = new Node(id, pId, label,t,false);
			nodes.add(node);
		}
		ChooseWayEnum mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
		switch(mChooseWayEnum){
			case DEPARTMENTCHOOSE:
				break;
			case FREECHOOSE:
			case PEOPLECHOOSE:
				for (SYS_User t : users)
				{
					String id = null;
					String pId = null;
					String label = null;
					Class<? extends Object> clazz = t.getClass();
					Field[] declaredFields = clazz.getDeclaredFields();
					for (Field f : declaredFields)
					{
						if (f.getAnnotation(TreeNodeId.class) != null)
						{
							f.setAccessible(true);
							id = (String) f.get(t);
						}
						if (f.getAnnotation(TreeNodePid.class) != null)
						{
							f.setAccessible(true);
							pId = (String) f.get(t);
						}
						if (f.getAnnotation(TreeNodeLabel.class) != null)
						{
							f.setAccessible(true);
							label = (String) f.get(t);
						}
						if (id != null && pId != null && label != null)
						{
							break;
						}
					}
					node = new Node(id, pId, label,t,true);
					nodes.add(node);
				}
				break;
		}

		Iterator in = datas.iterator();
		while (in.hasNext()) {
			SYS_Department supSYS_Department = (SYS_Department) in.next();
			getAllUser(supSYS_Department.getSYS_DepartmentList(),supSYS_Department.getSYS_User(),nodes);
		}
	}
	static int number = 0;
	// 查询该集目录下的所有User
	public static void getAllUserNumber(SYS_Department currentSYS_Department) {
		number += currentSYS_Department.getSYS_User().size();
		Iterator in = currentSYS_Department.getSYS_DepartmentList().iterator();
		while (in.hasNext()) {
			SYS_Department supSYS_Department = (SYS_Department) in.next();
			getAllUserNumber(supSYS_Department);
		}
	}
	/**
	 * 将我们的数据转化为树的节点
	 * 
	 * @param datas
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static  List<Node> convetData2Node(List<SYS_Department> datas)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<Node> nodes = new ArrayList<Node>();
		getAllUser(datas,new ArrayList<SYS_User>(),nodes);

		/**
		 * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
		 */
		for (int i = 0; i < nodes.size(); i++)
		{
			Node n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++)
			{
				Node m = nodes.get(j);
				if (m.getpId().equals(n.getId()))
				{
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId().equals(n.getpId()))
				{
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		// 设置图片
		for (Node n : nodes)
		{
			setNodeIcon(n);
		}
		return nodes;
	}

	private static List<Node> getRootNodes(List<Node> nodes)
	{
		List<Node> root = new ArrayList<Node>();
		for (Node node : nodes)
		{
			if (node.isRoot())
				root.add(node);
		}
		return root;
	}

	/**
	 * 把一个节点上的所有的内容都挂上去
	 */
	private static void addNode(List<Node> nodes, Node node,
			int defaultExpandLeval, int currentLevel)
	{

		nodes.add(node);
		if (defaultExpandLeval >= currentLevel)
		{
			node.setExpand(false);
		}

		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addNode(nodes, node.getChildren().get(i), defaultExpandLeval,
					currentLevel + 1);
		}
	}

	/**
	 * 设置节点的图标
	 * 
	 * @param node
	 */
	private static void setNodeIcon(Node node)
	{
		if (node.getChildren().size() > 0 && node.isExpand())
		{
			node.setIcon(R.drawable.icon_minus);
		} else if (node.getChildren().size() > 0 && !node.isExpand())
		{
			node.setIcon(R.drawable.icon_plus);
		} else
			node.setIcon(-1);

	}

}
