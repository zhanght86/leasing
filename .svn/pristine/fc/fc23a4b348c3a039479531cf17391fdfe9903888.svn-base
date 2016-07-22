package com.pqsoft.analysisBySynthesis.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class BankWaterService {

	/**
	 * 查看银行流水列表
	 * tofindBANKWATER
	 * @date 2014-3-30 下午10:37:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object tofindBANKWATER(Map<String,Object> map){		
		return PageUtil.getPage(map, "bankwater.tofindBANKWATER", "bankwater.tofindBANKWATERCount");
	}
	
	
	/**
	 * 查看单个银行流水-根据id查询
	 * tofindBANKWATEROne
	 * @date 2014-3-30 下午10:38:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object tofindBANKWATEROne(Map<String,Object> map){
		return Dao.selectOne("bankwater.tofindBANKWATER", map);
	}
	
	/**
	 * 银行流水详细
	 * tofindBANKWATERCHILD
	 * @date 2014-3-30 下午10:41:54
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object tofindBANKWATERCHILD(Map<String,Object> map){
		return Dao.selectOne("bankwater.tofindBANKWATERCHILD", map);
	}
	
	public Page tofindBANKWATERINFO(Map<String,Object> map){	
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList("bankwater.tofindBANKWATERCHILD", map)), 10);
		return page;
	}
	
	/**
	 * 添加银行流水项目
	 * doInertBANKWATER
	 * @date 2014-3-30 下午10:44:20
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInertBANKWATER(Map<String,Object> map){
		return Dao.insert("bankwater.doInertBANKWATER", map);
	}
	
	/**
	 *  
	 * doInertBANKWATERCHILD
	 * @date 2014-3-30 下午10:45:25
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInertBANKWATERCHILD(Map<String,Object> map){
		return Dao.update("bankwater.doInertBANKWATERCHILD",map);
	}
	
	/**
	 * 修改银行流水
	 * doUpdateBANKWATER
	 * @date 2014-3-30 下午10:46:05
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateBANKWATER(Map<String,Object> map){
		return Dao.update("bankwater.doUpdateBANKWATER",map);
	}
	
	/**
	 * 删除银行流水
	 * doDelBANKWATER
	 * @date 2014-3-31 下午10:07:59
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelBANKWATER(Map<String,Object> map){
		return Dao.delete("bankwater.doDelBANKWATER", map);
	}
	
	/**
	 * 删除银行流水-详细
	 * doDelBANKWATERCHILD
	 * @date 2014-3-31 下午10:10:10
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelBANKWATERCHILD(Map<String,Object> map){
		return Dao.delete("bankwater.doDelBANKWATERCHILD", map);
	}
}
