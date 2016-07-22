package com.pqsoft.capitalPool.supplierPool.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class SupplierBailPoolService {
	private String basePath = "SupplierBailPool.";
	
	/**
	 * DB保证金池管理可用余额页面
	 * @param param
	 * @return
	 */
	public Page getDBPageData(Map<String,Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSupDbBailList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSupDbBailCount", param));
		return page;
	}
	
	/**
	 * 添加资金池数据
	 * @param param
	 * @return
	 */
	public int addSupdbBaill(Map<String,Object> param ){
		return Dao.insert(basePath+"addFunds");
	}
	
	/**
	 * 更新资金池相关信息 
	 * @param param
	 * @return
	 */
	public int updateFundsPool(Map<String,Object> param){
		return Dao.update(basePath+"updateFundsPool",param);
	}
	
	/**
	 * 更新资金池相关状态By Re_id
	 * @param param
	 * @return
	 */
	public int updateSupDbRefundId(Map<String,Object> param){
		return Dao.update(basePath+"updateSupDbRefundId",param);
	}
	
    /**
     * 得到资金池信息	
     * @param param
     * @return
     */
	public Map<String,Object> getOneSupdbBail(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectOne(basePath+"getOneSupFunds", param);
	}
	
	/**
	 * 获取供应商DB保证金信息
	 * @param param
	 * @return
	 * 
	 */
	public List<Object> getdbBailList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(basePath+"getDbBailList", param);
	}
	
	/**
	 * 创建退款单
	 * @param param
	 * @return
	 */
	public int addRefundDan(Map<String,Object> param ){
		return Dao.insert(basePath+"addRefundDan",param);
	}
	
	/**
	 * 获取退款单序列
	 * @return
	 */
	public String getRefundDanSeq(){
		return Dao.getSequence("SEQ_FI_REFUND_HEAD");
	}
	
	/**
	 * 获取供应商账户信息（用于退款）
	 * @param param
	 * @return
	 */
	public Map<String,Object> getSupRefundMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"getSupRefundMess", param);
	}
	
	/**
	 * DB保证金退款管理分页信息
	 * @param param
	 * @return
	 */
	public Page getDbRefundDan(Map<String,Object> param){
		param.put("DIC_RE_STATUS","退款单状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDbRefundDanList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDbRefundDanCount", param));
		return page;
	}

	/**
	 * 删除退款单信息
	 * @param param
	 * @return
	 */
	public int delRefundMess(Map<String,Object> param){
		return Dao.delete(basePath+"delRefundMess", param);
	}
	
	/**
	 * 跟新退款单中的信息
	 * @param param
	 * @return
	 */
	public int updateRefundMess(Map<String,Object> param){
		return Dao.update(basePath+"updateRefundMess", param);
	}
	
	/**
	 * 通过退款单id获取退款金额
	 * @param param
	 * @return
	 */
	public Double getRefundMoney(Map<String,Object> param){
		return Dao.selectOne(basePath+"getRefundMoney", param);
	}
	
	/**
	 * 核销 DB保证金冲抵租金时会更实际使用后保证金的剩余额度
	 * @param param
	 * @return
	 */
	public int updateDepositMoney(Map<String,Object> param){
		return Dao.update(basePath+"updateDepositMoney", param);
	}
	
	/**
	 * DB保证金冲抵租金列表信息
	 * @param param
	 * @return
	 */
	public Page getPageDBOffSet(Map<String,Object> param ){
		param.put("DIC_FI_STATUS", "资金池冲抵状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getOffsetDetailList",param));
		page.addDate(array, Dao.selectInt(basePath+"getOffsetDetailCount", param));
		return page;
	}
	
	public Excel getExcelExportRefundData(Map<String,Object> param ,String type){
		Excel excel = new Excel();
		//退款明细
		List<Map<String,Object>> dataDetailList = Dao.selectList(basePath+"getRefundDetailExcel",param);
		LinkedHashMap<String, String> titleDetail = new LinkedHashMap<String, String>();
		titleDetail.put("ROWNO", "序号");
		titleDetail.put("SUP_SHORTNAME", "供应商");
		titleDetail.put("PRO_CODE", "项目编号");
		titleDetail.put("CLIENT_NAME", "客户名称");
		titleDetail.put("START_CONFIRM_DATE", "起租确认日");
		titleDetail.put("END_DATE", "租赁到期日");
		titleDetail.put("BASE_MONEY", "保证金金额");
		titleDetail.put("RE_MONEY", "退款金额");
		titleDetail.put("PROJECT_STATUS", "项目状态");
		titleDetail.put("RE_PAYEE_UNIT", "收款单位");
		excel.addData(dataDetailList);
		excel.addTitle(titleDetail);
		excel.addSheetName(param.get("DETAIL_SHEET").toString());
		excel.setHeadTitles(param.get("DETAIL_HEAD").toString());
		
		//退款主表
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"getDepositRefundMess", param);
		LinkedHashMap<String, String>  title = new LinkedHashMap<String, String>();
		title.put("ROWNO","序号");
		title.put("SUP_SHORTNAME","供应商");
		title.put("RE_PAYEE_UNIT","收款单位");
		title.put("RE_PAYEE_BANK","开户行行名");
		title.put("RE_PAYEE_BANK_ADDR","开户行所在地");
		title.put("RE_PAYEE_ACCOUNT","付款账号");
		title.put("RE_MONEY","付款金额");
		excel.addData(dataList);
		excel.addTitle(title);
		excel.addSheetName(param.get("MAIN_SHEET").toString());
		excel.setHeadTitles(param.get("HEAD_TITLE").toString());
		return excel;
	}
	
	//添加资金池的方法
	public int addDepositPool(){
		return Dao.insert(basePath+"addFunds");
	}
	//更新资金池可用金额 及状态
	
    //提供资金池资金数据
}
