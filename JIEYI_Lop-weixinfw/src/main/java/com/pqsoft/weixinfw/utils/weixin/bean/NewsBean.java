/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 */
package com.pqsoft.weixinfw.utils.weixin.bean;

import java.io.Serializable;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 *
 */
public class NewsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String title ;
	private String description ;
	private String url ;
	private String picurl ;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the picurl
	 */
	public String getPicurl() {
		return picurl;
	}
	/**
	 * @param picurl the picurl to set
	 */
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	@Override
	public String toString() {
		return "NewsBean [title=" + title + ", description=" + description
				+ ", url=" + url + ", picurl=" + picurl + "]";
	}
	
	

}
