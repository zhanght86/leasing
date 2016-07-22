package com.pqsoft.insureSettlement.service;
/**
 *  款项确认
 *  @author hanxl
 */
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureSettlementConfirmService {
	
private String basePath = "insureSettlementConfirm.";
	
	public InsureSettlementConfirmService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *   插入保险理赔金记录
	 */
	public int insertPoolInsureFee(Map<String,Object> param){
		return Dao.insert(basePath + "insertPoolInsureFee",param);
	}
	
	/**
	 *   修改一条保险理赔款来款记录状态
	 */
	public int updateInsurePaidFeeStatus(Map<String,Object> param){
		return Dao.update(basePath + "updateInsurePaidFeeStatus",param);
	}
	
	/**
	 *   撤销一条保险理赔款来款记录状态
	 */
	public int cancelInsurePaidFeeStatus(Map<String,Object> param){
		return Dao.update(basePath + "cancelInsurePaidFeeStatus",param);
	}
	
	/**
	 *   删除一条保险理赔款资金池记录
	 */
	public int deletePoolInsureFee(Map<String, Object> param) {
		return Dao.delete(basePath + "deletePoolInsureFee",param);
	}
	
	/**
	 *   按照上传时间生成通知条目
	 */
	public Page queryAllNotice(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAllNotice",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCountNotice", param));
		return page;
	}
	
	/**
	 *   忽略一条保险理赔款来款记录
	 */
	public int ignoreInsurePaidFeeStatusChange(Map<String,Object> param){
		return Dao.update(basePath + "ignoreInsurePaidFeeStatusChange",param);
	}
	
	/**
	 *   根据上传时间忽略未认款项目
	 */
	public int ignoreElseInsurePaidFee(Map<String,Object> param){
		return Dao.update(basePath + "ignoreElseInsurePaidFee",param);
	}
}
