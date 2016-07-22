package com.pqsoft.fusionChart.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;


/**
 * <p>
 * Title: 融资租赁信息系统平台 报表中心
 * </p>
 * <p>
 * Description: 报表中心-项目分析service；
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * @author 吴剑东  wujiandongit@163.com 
 * @version 1.0
 */
public class ReportProjectService {
	
	
	/**
	 * 根据模版-类型,查询数据发布版本
	 * @author 吴剑东 2013-5-20
	 */
	public List<Map<String,Object>> selectDataRelByModCard(Map<String,Object> map){
		return Dao.selectList("reportProject.selectDataRelByModCard",map);
	}
	
	
}
