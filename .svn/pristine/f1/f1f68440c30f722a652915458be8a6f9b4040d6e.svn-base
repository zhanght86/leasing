package com.pqsoft.manufacturerApproval.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class ManufacturerApprovalService {
	private String basePath = "manufacturerApproval.";
	
	public ManufacturerApprovalService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("TAG", "反馈信审");
		param.put("TAG1", "企划担当签约");
		param.put("TAG2", "未审核");
		param.put("TAG3", "模式");//抓立项流程的特点，都含有 "模式"这个字眼
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}

	/**
	 * 查询基本项目信息TODO
	 * DLD, --供应商 
	 * CS_ID,--厂商编号
	 * PROJ_ID,--项目编号
	 * KH_TYPE --客户类型
	 */
	public Object selectProjInfo(Map<String, Object> param) {
		param.put("TAG", "租赁周期");
		param.put("TAG1", "放款政策");
		param.put("TAG2", "租赁包");
		param.put("TAG3", "牌抵挂");
		param.put("TAG4", "监控设备");
		param.put("TAG5", "放款账户");
		return Dao.selectOne(basePath + "selectProjInfo",param);
	}
	
	/**
	 * 查询承租人(自然人)信息
	 * KH_ID, --客户id 
	 * KH_NAME, --承租人姓名 
	 * KH_CARD_NO, --承租人身份证号 
	 * KH_PHONE, --承租人手机
	 * PO_NAME, --配偶名称 
	 * PO_CARD_NO, --配偶身份证号 
	 * PO_PHONE --配偶手机
	 */
	public Object selectNPCustInfo(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectNPCustInfo",param);
	}
	
	/**
	 * 查询承租人(法人)信息 TODO
	 * FR_NAME, --法人客户名称
	 * ZC_ADDR, --地址
	 * FD_PRER, --法定代表人
	 * ZC_MONEY, --注册资本 
	 * CUST_NATURE, --客户类型 
	 * ORAGNIZATION_CODE, --组织机构代码
	 * MAIN_BUSINESS, --主营业务
	 * REGISTE_DATE, --注册日期
	 * REGISTE_PHONE, --电话
	 */
	public Object selectLPCustInfo(Map<String, Object> param) {
		param.put("TAG", "法人");
		return Dao.selectOne(basePath + "selectLPCustInfo",param);
	}
	
	/**
	 *   根据项目id查询租赁物明细
	 *	  PRODUCT_NAME,--租赁物名称
	 *   MODEL,--型号
	 *   CC_CODE,--出厂编号
	 *   ENG_NO,--发动机号
	 *   BODY_NO--车架号
	 */
	public List<Map<String, Object>> selectProductInfoByProjID(Map<String, Object> param) {
		return Dao.selectList(basePath + "selectProductInfoByProjID",param);
	}
	
	/**
	 *   根据项目id查询总经理审批通过日期
	 */
	public Object selectZJLTimeByProjID(Map<String, Object> param) {
		param.put("TAG1", "总经理审批");
		param.put("TAG2", "模式");
		return Dao.selectOne(basePath + "selectZJLTimeByProjID",param);
	}
	
	/**
	 *   根据项目id,流程名称查询立项流程中的意见和时间
	 *   TASK_NAME, --流程名称
	 *	  LC_END_TIME, --流程处理时间
	 *	  OP_TYPE,--流程操作
	 *	  MEMO --意见
	 */
	public List<Map<String, Object>> selectMemoAndTimeByProjIDAndFlowName(Map<String, Object> param,String flowName) {
		param.put("TAG", "模式");
		param.put("MES", "(未填写)");
		param.put("FLOWNAME", flowName);
		return Dao.selectList(basePath + "selectMemoAndTimeByProjIDAndFlowName",param);
	}
	
	/**
	 *   插入一条厂家审核意见信息
	 */
	public int insertManufacturerAdvice(Map<String,Object> param){
		param.put("STATUS", "已审核");//有记录就是已审核
		return Dao.insert(basePath + "insertManufacturerAdvice",param);
	}
	
	/**
	 *   删除厂家审核意见信息
	 */
	public int deleteManufacturerAdvice(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteManufacturerAdvice",param);
	}
	
	/**
	 * 查询承租人(自然人)信息  终端客户
	 * KH_ID, --客户id 
	 * KH_NAME, --承租人姓名 
	 * KH_CARD_NO, --承租人身份证号 
	 * KH_PHONE, --承租人手机
	 * PO_NAME, --配偶名称 
	 * PO_CARD_NO, --配偶身份证号 
	 * PO_PHONE --配偶手机
	 */
	public Object selectTerminalNPCustInfo(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectTerminalNPCustInfo",param);
	}
	
	/**
	 * 查询承租人(法人)信息 TODO  终端客户
	 * FR_NAME, --法人客户名称
	 * ZC_ADDR, --地址
	 * FD_PRER, --法定代表人
	 * ZC_MONEY, --注册资本 
	 * CUST_NATURE, --客户类型 
	 * ORAGNIZATION_CODE, --组织机构代码
	 * MAIN_BUSINESS, --主营业务
	 * REGISTE_DATE, --注册日期
	 * REGISTE_PHONE, --电话
	 */
	public Object selectTerminalLPCustInfo(Map<String, Object> param) {
		param.put("TAG", "法人");
		return Dao.selectOne(basePath + "selectTerminalLPCustInfo",param);
	}
	
	/**
	 *  查询陕重汽整车的数量 总价
	 */
	public Object selectZCCountByProjID(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectZCCountByProjID",param);
	}
	
	/**
	 *  查询陕重汽挂车的数量 总价
	 */
	public Object selectBGCCountByProjID(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectBGCCountByProjID",param);
	}
	
	/**
	 * 上传附件
	 */
	public int insertManufacturerApprovalFile(Map<String,Object> param){
		return Dao.insert(basePath+"insertManufacturerApprovalFile",param);
	}
	
	/**查询一个项目下的所有附件
	 */
	public List selectAllFiles(Map<String,Object> param){
		return Dao.selectList(basePath+"selectAllFiles",param);
	}
	
	/**根据id删除一个附件
	 */
	public int deleteManufacturerFileById(Map<String,Object> param){
		return Dao.delete(basePath+"deleteManufacturerFileById",param);
	}
	
	/**根据id查询一个附件
	 */
	public Object selectManufacturerFileById(Map<String,Object> param){
		return Dao.selectOne(basePath+"selectManufacturerFileById",param);
	}

}
