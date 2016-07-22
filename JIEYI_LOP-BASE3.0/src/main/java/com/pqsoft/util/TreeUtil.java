package com.pqsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Tree;

public class TreeUtil {

	/**
	 * 将原始tree集合转换成标准有关联的tree集合
	 * 
	 * @Title: formate
	 * @param tree
	 * @return List<Tree>
	 * @throws
	 */

	public static List<Tree> formate(List<Tree> tree) {
		List<Tree> newTree = new ArrayList<Tree>();
		// id为Key的map
		Map<String, Tree> idTree = new HashMap<String, Tree>();
		// pid为key的map
		Map<String, Tree> pidTree = new HashMap<String, Tree>();
		for (Tree e : tree) {
			idTree.put(e.getId(), e);
			pidTree.put(e.getPid(), e);
		}
		// 遍历原始数据
		for (Tree e : tree) {
			// 判断是否有父节点
			Tree pTree = idTree.get(e.getPid());
			e.setParentTree(pTree);

			if (pTree == null) {
				// 无：加到newTree中
				newTree.add(e);
			} else {
				// 有：添加的父节点的children集合中
				pTree.getChildren().add(e);
			}
			// 判断是否有子节点
			if (pidTree.get(e.getId()) != null) {
				e.setState("closed");
			}

		}
		// System.out.println(newTree);
		return newTree;
	}

	public static List<Tree> formate1(List<Tree> tree, boolean flag) {
		List<Tree> newTree = new ArrayList<Tree>();
		// id为Key的map
		Map<String, Tree> idTree = new HashMap<String, Tree>();
		// pid为key的map
		Map<String, Tree> pidTree = new HashMap<String, Tree>();
		for (Tree e : tree) {
			idTree.put(e.getId(), e);
			pidTree.put(e.getPid(), e);
		}
		// 遍历原始数据
		for (Tree e : tree) {
			// 判断是否有父节点
			Tree pTree = idTree.get(e.getPid());
			if (pTree == null) {
				// 无：加到newTree中
				newTree.add(e);
			} else {
				// 有：添加的父节点的children集合中
				if (flag) {
					e.setParentTree(pTree);
				}
				pTree.getChildren().add(e);
			}
			// 判断是否有子节点
			if (pidTree.get(e.getId()) != null) {
				e.setState("closed");
			}
		}
		if (flag) {
			for (Tree t : newTree) {
				t.setChildren(new ArrayList<Tree>());
			}
			for (Tree t : tree) {
				// 无子节点
				if (t.getChildren().size() < 1) {
					if (t.getParentTree() == null) {
						// t.getChildren().add(t);
					} else if (t.getParentTree().getParentTree() == null && !t.hasChilren()) {
						t.getParentTree().getChildren().add(t);
					} else if (t.getParentTree().getParentTree().getParentTree() == null && !t.hasChilren()) {
						t.getParentTree().getParentTree().getChildren().add(t);
					} else if (t.getParentTree().getParentTree().getParentTree().getParentTree() == null && !t.hasChilren()) {
						t.getParentTree().getParentTree().getParentTree().getChildren().add(t);
					}
				}
			}
			for (Tree t : tree) {
				t.setParentTree(null);
			}
		}
		return newTree;
	}

	public static JSONArray parseToJSONArray(List<Tree> tree, boolean flag) {
		return JSONArray.fromObject(formate1(tree, flag));
	}

	/* <div id="leftAccordion" class="easyui-accordion"
	 * data-options="fit:true,border:false,animate:false;height:200px;">
	 * <div title="Title2" data-options="iconCls:'icon-reload',selected:true"
	 * style="padding:10px;">
	 * content2
	 * </div>
	 * #foreach($item in $!vm_menu_accordion)
	 * <ul id="tree$velcotiyCount" style="width:205px" class="easyui-tree"
	 * data-options="collapsabled:false,animate:true,data:$!item.children,onClick:
	 * function(node){
	 * if(node.attributes.url){
	 * addTab(node.text,node.attributes.url);
	 * }
	 * $(this).tree('toggle',node.target);
	 * }
	 * "></ul>
	 * #end
	 * </div> */
	public static String parseToHtml(List<Tree> tree, boolean flag) {
		List<Tree> trees = formate1(tree, flag);

		List<Tree> currTree = new ArrayList<Tree>();
		// parseToStr(tree,currTree);
		StringBuffer treeHtml = new StringBuffer();
		treeHtml.append("").append(" <div id=\"leftAccordion\" style=\"width:205px;\">");// class=\"easyui-accordion\"
		int index = 0;
		for (Tree pTree : trees) {
			boolean f = false;
			if (index == 1) f = true;
			treeHtml.append("<div title='" + pTree.getText() + "' data-options=\"iconCls:'" + pTree.getIconCls().trim() + "',selected:" + f
					+ "\" style='width:205px'>");
			treeHtml.append("<ul id='tree" + (++index) + "' style='width:205px' class='easyui-tree' data-options='collapsabled:false,animate:true,"
					+ "data:" + JSONArray.fromObject(pTree.getChildren()) + ",onClick:"
					+ "function(node){if(node.attributes.url){addTab(node.text,node.attributes.url);}$(this).tree(\"toggle\",node.target);}" + "'>");
			treeHtml.append("</ul>");
			treeHtml.append("</div>");
			index++;
		}
		treeHtml.append(" </div>");

		// System.out.println(tree);
		return treeHtml.toString();
	}

	// tempCurrStr.append("<ul id=\"tree$"+(++i)+"\" style=\"width:205px\" class=\"easyui-tree\")"
	// )
	// .append("				data-options=\"collapsabled:false,animate:true,data:"+JSONArray.fromObject(e.getChildren())+",onClick:")
	// .append("				function(node){")
	// .append("					if(node.attributes.url){")
	// .append("						addTab(node.text,node.attributes.url);")
	// .append("					}")
	// .append("					$(this).tree('toggle',node.target);")
	// .append("				}")
	// .append("			\">")
	//
	// .append("</ul>");
	private static void parseToStr(List<Tree> trees, List<Tree> currTree) {
		List<Tree> currTree1 = new ArrayList<Tree>();
		int count = currTree.size();// 当前子tree数量
		int i = 0;
		if (count == 0) {// 无子
			for (Tree e : trees) {
				StringBuffer tempCurrStr = new StringBuffer();
				if (!e.hasChilren()) {
					currTree1.add(e);
					tempCurrStr.append("<li ").append("").append("").append("/><span>").append(e.getText()).append("</span></li>");
					e.setCurrStr(tempCurrStr.toString());
					i++;
				}
			}
		} else {

			for (Tree e : currTree) {
				StringBuffer tempCurrStr = new StringBuffer();
				Tree pTree;
				if (e.hasParentTree()) {
					pTree = e.getParentTree();
					currTree1.add(pTree);
					tempCurrStr.append("<li ").append("/><span>" + pTree.getText() + "</span>");
					StringBuffer tempCurrStr1 = new StringBuffer();
					tempCurrStr1.append("<ul class=\'easyui-tree\'>");
					for (Tree cl : pTree.getChildren()) {
						tempCurrStr1.append(cl.getCurrStr());
					}
					tempCurrStr1.append("</ul>");
					tempCurrStr.append(tempCurrStr1).append("</li>");
					pTree.setCurrStr(tempCurrStr.toString());
					pTree.setClidrensStr(tempCurrStr1.toString());
					i++;
				}
			}
		}
		if (i > 0) {
			parseToStr(trees, currTree1);
		}
	}
}
