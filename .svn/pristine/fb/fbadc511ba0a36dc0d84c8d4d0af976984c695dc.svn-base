package com.pqsoft.RefinanceMethod.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class RefinanceMethodService {

	private final String xmlPath="refinanceMethod.";
	
	/**
	 * 查询融资机构信息
	 * findFinancial
	 * @date 2013-10-14 下午05:48:55
	 * @author 杨雪return new ReplyAjax(flag, null).addOp(new OpLog("融资机构管理","添加", paramMap.get("USERCODE").toString()));

	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findFinancial1(Map<String, Object> map) {
		return (Map<String, Object>) Dao.selectOne(xmlPath+"findFinancial", map);
	}
	
	/**
	 * 查询当前结构的一些编号及授信剩余金额
	 * findFinancial
	 * @date 2013-10-14 下午05:54:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object queryCugpCodeList(Map<String,Object> map){		
		return Dao.selectList(xmlPath+"queryCugpCodeList", map);
	}
	
	/**
	 * 添加融资方式
	 * addMethod
	 * @date 2013-10-14 下午06:24:27
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addMethod(Map<String, Object> map) {
		return Dao.insert(xmlPath+"addMethod",map);
	}
	
	/**
	 * 修改授信额度
	 * updateGrantplan
	 * @date 2013-10-14 下午06:27:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateGrantplan(Map<String,Object> map){
		return Dao.update(xmlPath+"updateGrantplan", map);
	}
	
	/**
	 * 根据融资机构ID查询融资方式
	 * findMethodList
	 * @date 2013-10-14 下午07:08:41
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Object> findMethodList(Map<String, Object> map) {
		map.put("TYPE", "再融资方式");
		return Dao.selectList(xmlPath+"findMethodList", map);
	}
	
	/**
	 * 作废融资方式
	 * updateCancel
	 * @date 2013-10-14 下午07:17:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateCancel(Map<String, Object> map) {
		System.out.println(map.get("STATUS")+"==dd==");
		return Dao.update(xmlPath+"updateCancel", map);
	}
	
	/**
	 * 查看融资方式
	 * author：卫闻悦
	 * time:2012-3-29下午04:08:02
	 */
	public Map<String, Object> findMethodSel(Map<String, Object> param) {
		return Dao.selectOne(xmlPath+"findMethodSele", param);
	}
	
	/**
	 * 查询再融资方式
	 * @return
	 */
	public List<Object> findRzMethod() {
		String TYPE ="再融资方式";
		return Dao.selectList(xmlPath+"findRzMethod",TYPE);
	}
	
	/**
	 * 更新融资方式
	 * author：卫闻悦
	 * time:2012-3-30上午10:44:18
	 */
	public int UpdateMethod(Map<String, Object> map) {
		return Dao.insert(xmlPath+"updateFindMethod", map);
	}
	
}
