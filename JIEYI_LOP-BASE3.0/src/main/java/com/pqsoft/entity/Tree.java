package com.pqsoft.entity;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: Tree 
 * @Description: TODO() 
 * @author 程龙达
 * @date 2013-11-12 下午07:52:30 
 *  
 */
public class Tree {
	private String id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<Tree> children = new ArrayList<Tree>();
	private String iconCls;
	private String pid;
	private Tree parentTree ;
	private String clidrensStr = "";
	private String currStr = "";
	
	
	public boolean hasParentTree(){
		return this.parentTree!=null;
	}
	public Tree getParentTree() {
		return parentTree;
	}
	public void setParentTree(Tree parentTree) {
		this.parentTree = parentTree;
	}
	public String getCurrStr() {
		return currStr;
	}
	public void setCurrStr(String currStr) {
		this.currStr = currStr;
	}
	public String getClidrensStr() {
		return clidrensStr;
	}
	public void setClidrensStr(String clidrensStr) {
		this.clidrensStr = clidrensStr;
	}
	public Boolean hasChilren(){
		return children != null && children.size() > 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Override
	public String toString(){
		return "id:"+id+"	text:"+text+"	attributes:"+attributes+"	children:"+children;
	}
	
	
}
