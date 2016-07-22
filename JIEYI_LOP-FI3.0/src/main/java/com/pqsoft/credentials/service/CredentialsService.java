package com.pqsoft.credentials.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class CredentialsService {
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "credentials.getCredentialsDcList", "credentials.getCredentialsDcCount");
	}
	
	public Page getMsgPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "credentials.getCredentialsMsgList", "credentials.getCredentialsMsgCount");
	}

	public Excel queryExcel(Map param){
		
		if(param == null){
			param=new HashMap();
		}
		String TYPE="";
		if(param !=null && param.get("TYPE") !=null && !param.get("TYPE").equals("")){
			TYPE=param.get("TYPE").toString();
		}
		
		String START_CREDENTIALS_NUM="";
		if(param !=null && param.get("START_CREDENTIALS_NUM") !=null && !param.get("START_CREDENTIALS_NUM").equals("")){
			START_CREDENTIALS_NUM=this.updateNumCurr(param.get("START_CREDENTIALS_NUM").toString());
		}
		
		List listSheet1=new ArrayList();
		List listSheet2=new ArrayList();
		
		List<Map<String,Object>> list=Dao.selectList("credentials.queryDownData",param);
		
		String CURR_START_CREDENTIALS_NUM=START_CREDENTIALS_NUM;
		for(int i=0;i<list.size();i++){
			Map map=list.get(i);
			String dataType="";
			
			int FLH=1;
			
			if(map !=null && map.get("TYPE") !=null && !map.get("TYPE").equals("")){
				dataType=map.get("TYPE")+"";
			}
			if(dataType.equals("ZJKH") || dataType.equals("KSQK")){
				map.put("FI_CERTIFICATE", CURR_START_CREDENTIALS_NUM);
				map.put("FLH", FLH);
				Dao.update("credentials.updateZjkhType",map);
				FLH++;
				
				//更新明细表的分录号
				List listFlh=Dao.selectList("credentials.queryDownDataListSheetFLH",map);
				for(int hh=0;hh<listFlh.size();hh++){
					Map mapFlh=(Map)listFlh.get(hh);
					mapFlh.put("FLH", FLH);
					Dao.update("credentials.updateFlhDetail",mapFlh);
					FLH++;
				}
				
				listSheet1.addAll(Dao.selectList("credentials.queryDownDataListSheet",map));
				
				listSheet2.addAll(Dao.selectList("credentials.queryXJLLInfo",map));
				
				CURR_START_CREDENTIALS_NUM=this.updateNum(CURR_START_CREDENTIALS_NUM);
				
				
			}
			
		}
		
		
		
		return this.HEAD_Upload(listSheet1, listSheet2);
		
	}
	
	public String updateNumCurr(String CURR_START_CREDENTIALS_NUM){
		int PZLENGTH=4;//凭证位数
		String curr_num=(Integer.parseInt(CURR_START_CREDENTIALS_NUM))+"";
		if(curr_num.length()<PZLENGTH){
			int num=PZLENGTH-curr_num.length();
			for (int i = 0; i < num; i++) {
				curr_num = "0" + curr_num;
			}
		}
		return curr_num;
	}
	
	public String updateNum(String CURR_START_CREDENTIALS_NUM){
		int PZLENGTH=4;//凭证位数
		String curr_num=(Integer.parseInt(CURR_START_CREDENTIALS_NUM)+1)+"";
		if(curr_num.length()<PZLENGTH){
			int num=PZLENGTH-curr_num.length();
			for (int i = 0; i < num; i++) {
				curr_num = "0" + curr_num;
			}
		}
		return curr_num;
	}
	
	
	// 导出
	public Excel HEAD_Upload(List<Map<String,Object>> listSheet1,List<Map<String,Object>> listSheet2) {

		Excel excel = new Excel();
		
		
		List<LinkedHashMap<String,String>> titles=new ArrayList<LinkedHashMap<String,String>>();
		
		
		LinkedHashMap<String, String> title2 = new LinkedHashMap<String, String>();

		title2.put("CREDENTIALS_COMPANY", "公司");
		title2.put("JZ_DATE", "记账日期");
		title2.put("MONTH", "会计期间");
		title2.put("CREDENTIALS_TYPE", "凭证类型");
		title2.put("FI_CERTIFICATE", "凭证号");
		title2.put("BZ", "币种");
		title2.put("FLH", "分录号");
		title2.put("DFFLH", "对方分录号");
		title2.put("ZBINFO", "主表信息");
		title2.put("FBINFO", "附表信息");
		title2.put("FI_PAY_MONEY", "原币");
		title2.put("FI_PAY_MONEY", "本位币");
		title2.put("FI_PAY_MONEY", "报告币");
		title2.put("ZBJEXS", "主表金额系数");
		title2.put("FBJEXS", "附表金额系数");
		title2.put("XZ", "性质");
		title2.put("HSXM1", "核算项目1");
		title2.put("BM1", "编码1");
		title2.put("MC1", "名称1");
		title2.put("HSXM2", "核算项目2");
		title2.put("BM2", "编码2");
		title2.put("MC2", "名称2");
		title2.put("HSXM3", "核算项目3");
		title2.put("BM3", "编码3");
		title2.put("MC3", "名称3");
		title2.put("HSXM4", "核算项目4");
		title2.put("BM4", "编码4");
		title2.put("MC4", "名称4");
		title2.put("HSXM5", "核算项目5");
		title2.put("BM5", "编码5");
		title2.put("MC5", "名称5");
		title2.put("HSXM6", "核算项目6");
		title2.put("BM6", "编码6");
		title2.put("MC6", "名称6");
		title2.put("HSXM7", "核算项目7");
		title2.put("BM7", "编码7");
		title2.put("MC7", "名称7");
		title2.put("HSXM8", "核算项目8");
		title2.put("BM8", "编码8");
		title2.put("MC8", "名称8");
        
		titles.add(title2);

		LinkedHashMap<String, String> title1 = new LinkedHashMap<String, String>();
		title1.put("CREDENTIALS_COMPANY", "公司");
		title1.put("JZ_DATE", "记账日期");
		title1.put("YW_DATE", "业务日期");
        title1.put("MONTH", "会计期间");
        title1.put("CREDENTIALS_TYPE", "凭证类型");
        title1.put("FI_CERTIFICATE", "凭证号");
        title1.put("FLH", "分录号");
        title1.put("REMARK", "摘要");
        title1.put("SUBJECT", "科目");
        title1.put("BZ", "币种");
        title1.put("HL", "汇率");
        title1.put("CREDENTIALS_DIRECTION", "方向");
        title1.put("FI_PAY_MONEY", "原币金额");
        title1.put("COUNT", "数量");
        title1.put("PRICE", "单价");
        title1.put("JF_MONEY", "借方金额");
        title1.put("DF_MONEY", "贷方金额");
        title1.put("FI_APP_NAME", "制单人");
        title1.put("GZ_NAME", "过账人");
        title1.put("FI_CHECK_NAME", "审核人");
        title1.put("FJSL", "附件数量");
        title1.put("GZBJ", "过账标记");
        title1.put("JZPZMK", "机制凭证模块");
        title1.put("SCBJ", "删除标记");
        title1.put("PZXH", "凭证序号");
        title1.put("DW", "单位");
        title1.put("CKXX", "参考信息");
        title1.put("SFYXJLL", "是否有现金流量");
        title1.put("XJLLBJ", "现金流量标记");
        title1.put("YWBH", "业务编号");
        title1.put("JSFS", "结算方式");
        title1.put("JSH", "结算号");
        title1.put("FZZZY", "辅助账摘要");
        title1.put("HSXM1", "核算项目1");
        title1.put("BM1", "编码1");
        title1.put("MC1", "名称1");
        title1.put("HSXM2", "核算项目2");
        title1.put("BM2", "编码2");
        title1.put("MC2", "名称2");
        title1.put("HSXM3", "核算项目3");
        title1.put("BM3", "编码3");
        title1.put("MC3", "名称3");
        title1.put("HSXM4", "核算项目4");
        title1.put("BM4", "编码4");
        title1.put("MC4", "名称4");
        title1.put("HSXM5", "核算项目5");
        title1.put("BM5", "编码5");
        title1.put("MC5", "名称5");
        title1.put("HSXM6", "核算项目6");
        title1.put("BM6", "编码6");
        title1.put("MC6", "名称6");
        title1.put("HSXM7", "核算项目7");
        title1.put("BM7", "编码7");
        title1.put("MC7", "名称7");
        title1.put("HSXM8", "核算项目8");
        title1.put("BM8", "编码8");
        title1.put("MC8", "名称8");
        title1.put("FPH", "发票号");
        title1.put("HPZH", "换票证号");
        title1.put("KH", "客户");
        title1.put("FYLB", "费用类别");
        title1.put("SKR", "收款人");
        title1.put("WL", "物料");
        title1.put("CWZZ", "财务组织");
        title1.put("GYS", "供应商");
        title1.put("FZZYWRQ", "辅助账业务日期");
        title1.put("DQR", "到期日");
		
		titles.add(title1);
		
		
		
		List<List<Map<String,Object>>> data=new ArrayList<List<Map<String,Object>>>();
		data.add(listSheet2);
		data.add(listSheet1);
		
		
		List<String> sheets=new ArrayList<String>();
		sheets.add("现金流量");
		sheets.add("凭证");
		
		
		excel.setSheetNum(2);
		excel.setSheetNames(sheets);
		excel.setTitles(titles);
		excel.setData(data);
		excel.setHasTitle(true);
		return excel;
	}
	
	//查询最大凭证号
	public String getCredentials_Num(){
		return Dao.selectOne("credentials.getCredentials_Num");
	}
	
	public void credentialsBack(Map map){
		Dao.update("credentials.credentialsByDetailBack",map);
		Dao.update("credentials.credentialsBack",map);
	}
}
