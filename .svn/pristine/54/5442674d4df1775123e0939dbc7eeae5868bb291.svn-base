package com.pqsoft.approve.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ApproveReportService {

	private final String xmlPath = "approve.";
	
	/**
	 * 审批总报表
	 * toMgApproveAllReport
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgApproveAllReport(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgApproveAllReport", xmlPath+"toMgApproveAllReportC");
	}
	
	/**
	 * 审批总报表-导出
	 * toExcelApproveAll
	 * @date 2015年5月15日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toExcelApproveAll(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toExcelApproveAll", map);
	}	
	/**
	 * 特批处理总报表
	 * toMgSpecialProcedureAllData
	 * @date 2015年5月8日
	 * @auther yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgSpecialProcedureAllData(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgSpecialProcedureAllData", xmlPath+"toMgSpecialProcedureAllDataC");
	}
	
	/**
	 * 特批处理总报表-导出
	 * toImportSpecial
	 * @date 2015年5月11日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportSpecial(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportSpecial", map);
	}
	
	/**
	 * 特批处理明细报表
	 * toMgSpecialProDetail
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgSpecialProDetail(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgSpecialProDetail", xmlPath+"toMgSpecialProDetailC");
	}
	
	/**
	 * 特批处理明细报表-导出
	 * toImportSpecialProDetail
	 * @date 2015年5月11日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportSpecialProDetail(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportSpecialProDetail", map);
	}
	
	/**
	 * 拒绝申请统计明细
	 * toMgRefusePro
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgRefusePro(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgRefusePro", xmlPath+"toMgRefuseProC");
	}
	
	/**
	 * 拒绝申请统计明细-导出
	 * toImportRefusePro
	 * @date 2015年5月11日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportRefusePro(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportRefusePro", map);
	}
	
	/**
	 * 拒绝申请统计明细
	 * toMgRefuseReason
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgRefuseReason(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgRefuseReason", xmlPath+"toMgRefuseReasonC");
	}
	
	/**
	 * 拒绝申请统计明细-导出
	 * toImportRefuseReason
	 * @date 2015年5月11日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportRefuseReason(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportRefuseReason", map);
	}
	
	/**
	 * 拒绝原因比率
	 * toMgRefuseReasonRateData
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgRefuseReasonRateData(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgRefuseReasonRateData", xmlPath+"toMgRefuseReasonRateDataC");
	}
	
	/**
	 *  拒绝原因比率-导出
	 * toImportRefuseReasonRate
	 * @date 2015年5月11日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportRefuseReasonRate(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportRefuseReasonRate", map);
	}
	
	/**
	 * 拒绝区域分类
	 * toMgRefuseAreaData
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgRefuseAreaData(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgRefuseAreaData", xmlPath+"toMgRefuseAreaDataC");
	}
}
