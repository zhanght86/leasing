package com.pqsoft.project.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

public class TrialReportService {
	private String basePath = "TrialReport.";
	
	//查询初审报告相关信息
	public Map<String,Object> getTrialReportHeadMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"getTrialReportMainMess", param);
	}
	
	//查询初审报告细项
	public List<Object> getTrialReportDetailMess(Map<String,Object> param){
		return Dao.selectList(basePath+"getTrialDetailMess", param);
	}
	
	public int addModfiyTrialReport(Map<String,Object> param){
		int result = 0 ;
		if(param.containsKey("HEAD_ID") && param.get("HEAD_ID") !=null && !"".equals(param.get("HEAD_ID").toString())){
			//删除细项数据
			Dao.delete(basePath+"delTrialDetail",param);
			result = Dao.update(basePath+"updateTrialReportHeadMess",param);
		}else{
			param.put("REPORT_TYPE", "1");
			param.put("CREATOR", Security.getUser().getName());
		    result = Dao.insert(basePath+"addTrialReportHead", param);
		}
		//承租人相关资产资料
		List<Object> shortLoan = JSONArray.fromObject(StringUtils.nullToString(param.get("shortLoan")));
		addTrialDetail(shortLoan);
		List<Object> longLoan = JSONArray.fromObject(StringUtils.nullToString(param.get("longLoan")));
		addTrialDetail(longLoan);
		List<Object> landHouse = JSONArray.fromObject(StringUtils.nullToString(param.get("landHouse")));
		addTrialDetail(landHouse);
		List<Object> haveEqs  = JSONArray.fromObject(StringUtils.nullToString(param.get("haveEqs")));
		addTrialDetail(haveEqs);
		List<Object> otherAssets = JSONArray.fromObject(StringUtils.nullToString(param.get("otherAssets")));
		addTrialDetail(otherAssets);
		List<Object> bankCun = JSONArray.fromObject(StringUtils.nullToString(param.get("bankCun")));
		addTrialDetail(bankCun);
		//担保人资产资料保存
		List<Object> assureLandHouse = JSONArray.fromObject(StringUtils.nullToString(param.get("assureLandHouse")));
		addTrialDetail(assureLandHouse);
		List<Object> assureHaveEqs  = JSONArray.fromObject(StringUtils.nullToString(param.get("assureHaveEqs")));
		addTrialDetail(assureHaveEqs);
		List<Object> assureOtherAssets = JSONArray.fromObject(StringUtils.nullToString(param.get("assureOtherAssets")));
		addTrialDetail(assureOtherAssets);
		List<Object> assureBankCun = JSONArray.fromObject(StringUtils.nullToString(param.get("assureBankCun")));
		addTrialDetail(assureBankCun);
		return result ; 
	}
	
	public int addTrialDetail(List<Object> ListMess){
		int result = 0 ;
		if(ListMess.size() > 0){
			for (Object obj : ListMess) {
				Map<String,Object> newParam = (Map<String,Object>)obj;
				result = Dao.insert(basePath+"addTrialReportDetail", newParam);
			}
		}
		return result;
	}
	
	public Map<String,Object> getBaseMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"getBaseMess", param);
	}
}
