package com.pqsoft.target.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.PageUtil;

public class TargetService {

	private final String xmlPath = "mgTargets.";
	
	/**
	 * 分页查询管理页面指标价格数据
	 * @author yx
	 * @date 2015-03-03
	 * @param map
	 * @return
	 */
	public Page toMgTarget(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgTarget", xmlPath+"toMgTargetC");
	} 
	
	//查看指标公司名称
	public Object toViewSuppliers(){
		return Dao.selectList(xmlPath+"toViewSuppliers");
	}
	
	/**
	 * 根据指标价格id查看指标
	 * @author yx
	 * @date 2015-03-03
	 * @param map
	 * @return
	 */
	public Object toViewTarget(Map<String,Object> map){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> m = Dao.selectOne(xmlPath+"toViewTarget", map);
		List<Map<String,Object>> m1 = Dao.selectList(xmlPath+"toViewTargeDetail", map);
		result.put("target", m);
		result.put("detail", m1);
		return result;
	}
	
	/**
	 * 添加指标价格
	 * @author yx
	 * @date 2015-03-03
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doAddTarget(Map<String,Object> map){
		String id = Dao.getSequence("seq_fil_target_price");
		JSONObject json = JSONObject.fromObject(map.get("resutlData"));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("SUPPLIERS_ID", json.get("SUPPLIERS_ID"));
		data.put("TARGET_PRICE", json.get("TARGET_PRICE"));
		data.put("DISTRICT", json.get("DISTRICT"));
		data.put("TARGET_TOTAL", json.get("TARGET_TOTAL"));
		data.put("SATRT_DATE", json.get("SATRT_DATE"));
		data.put("END_DATE", json.get("END_DATE"));
		data.put("USERCODE", map.get("USERCODE"));
		data.put("TARGET_SY", json.get("TARGET_TOTAL"));
		data.put("ID", id);
		int i = Dao.insert(xmlPath+"doAddTarget", data);
		if(i>0){
			List<Map<String, Object>> list = json.getJSONArray("target");
			for(Map<String,Object> m:list){
				data.putAll(m);	
				data.put("TP_ID", id);
				i = Dao.insert(xmlPath+"doAddTargetDetail", data);//面审人员添加
			}
		}
		return i;
	}
	
	/**
	 * 修改指标价格
	 * @author yx
	 * @date 2015-03-03
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doUpTarget(Map<String,Object> map){
		
		JSONObject json = JSONObject.fromObject(map.get("resutlData"));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("FR_ID", json.get("FR_ID"));
		data.put("SUPPLIERS_ID", json.get("SUPPLIERS_ID"));
		data.put("TARGET_PRICE", json.get("TARGET_PRICE"));
		data.put("DISTRICT", json.get("DISTRICT"));
		data.put("TARGET_TOTAL", json.get("TARGET_TOTAL"));
		data.put("SATRT_DATE", json.get("SATRT_DATE"));
		data.put("END_DATE", json.get("END_DATE"));
		data.put("USERCODE", map.get("USERCODE"));
		data.put("TARGET_SY", json.get("TARGET_TOTAL"));
		int i = Dao.update(xmlPath+"doUpTarget", data);
		if(i>0){
			List<Map<String, Object>> list = json.getJSONArray("target");
			System.out.println("==========yx==========="+list.size());
			for(Map<String,Object> m:list){
				data.putAll(m);	
				if(m.get("DETAIL_ID")==null){
					data.put("TP_ID", json.get("FR_ID"));
					i = Dao.insert(xmlPath+"doAddTargetDetail", data);//面审人员添加
				}else{
					data.put("ID", m.get("DETAIL_ID").toString());
					i = Dao.update(xmlPath+"doUpTargetDetail",data);
				}
			}
		}
		
		return i;
	}
	
	/**
	 * 删除指标价格
	 * @author yx
	 * @date 2015-03-03 
	 * @param map
	 * @return
	 */
	public int doDelTarget(Map<String,Object> map){
		return Dao.delete(xmlPath+"doDelTarget",map);
	}
	
	/**
	 * 融资指标管理
	 * @author yx
	 * @date 2015-03-04
	 * @param map
	 * @return
	 */
	public Page toMgFinancintPro(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgFinancintPro", xmlPath+"toMgFinancintProC");
	} 
	
	/**
	 * 根据指标公司id查看融资指标价格， 区域， 使用期限
	 * @author yx
	 * @date 2015-03-04
	 * @param map
	 * @return
	 */
	public Object toViewTargetList(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toViewTargetList", map);
	}
	
	/**
	 * 修改指标公司拥有指标数
	 * @author yx
	 * @date 2015-03-04
	 * @param map
	 * @return
	 */
	public Object doUpTargetNum(Map<String,Object> map){
		//计算剩余指标数
		int num = Integer.parseInt(map.get("TARGET_SY").toString())-1;
		//计算已使用指标数
		int num_ = Integer.parseInt(map.get("TARGET_TOTAL").toString())-num;
		map.put("TARGET_SY", num);
		map.put("TARGET_YSY", num_);
		return Dao.update(xmlPath+"doUpTargetNum",map);
	}
	
	/**
	 * 生成指标管理费列表
	 * @param list 指标公司管理费收取标准
	 * @param eqCount 设备数量
	 * @param lease_term 租赁期数
	 * @author King 2015年3月6日
	 */
	public List<String> createTargetPayList(List<Map<String,Object>> list,int eqCount,int lease_term){
		//TODO 需要杨雪支持表结构
		List<String> lst=new ArrayList<String>();
		for (Map<String, Object> map : list) {
			double money=UtilFinance.mul(Double.parseDouble(map.get("MONEY")+""),eqCount);
			int start=Integer.parseInt(map.get("START")+"");
			int stop=Integer.parseInt(map.get("STOP")+"");
			for(int i=start-1;i<stop;i++){
				if(i<lease_term){
					lst.add(money+"");
				}
			}
		}
		return lst;
	}
}
