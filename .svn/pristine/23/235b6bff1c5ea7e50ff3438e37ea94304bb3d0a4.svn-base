package com.pqsoft.invoice.service;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

public class EmsMgService {
	private String nameSpace = "ems.";
	/**
	 * 
	 * @Title: selectEmsMgPageData 
	 * @Description: TODO(查询票据管理界面数据) 
	 * @param param
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page selectEmsMgPageData(Map<String, Object> param) {
		Page page = new Page(param);
		page.addDate(JSONArray.fromObject(Dao.selectList(nameSpace + "selectEmsInfoPage",param)), Dao.selectInt(nameSpace + "selectEmsInfoPageCount", param));
		return page;
	}
	
	public Excel getExportApplyExcel(Map<String, Object> param) {
		param.put("SQL_TARGET_FLAG", "发票_对象_类型");
		param.put("SQL_CUST_TYPE", "客户类型");
		if(StringUtils.isBlank(param.get("target_type"))){
			param.put("target_type", "供应商");
		}
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(nameSpace + "getTargetEmsExcelData",param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("ID", "唯一标识");
		title.put("target_type", "对象类型");
		title.put("target_cust_type", "类型");
		title.put("full_name", "全称");
		title.put("shortname", "简称");
		title.put("ems_flag", "邮寄地址编号");
		title.put("ems_to_name", "收件人");
		title.put("ems_to_phone", "收件人电话");
		title.put("ems_to_dw", "邮寄单位");
		title.put("ems_to_addr", "邮寄地址");
		title.put("ems_to_city", "邮寄城市");
		title.put("ems_to_code", "邮编");
		//封装excel
		Excel excel = new Excel();
//		excel.setName("CardCheck"+DateUtil.getSysDate("yyyymmddss")+".xls");
//		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
//		excel.hasTitle(true);
		return excel;
	}

	public List doSelectEmsInfo(Map<String, Object> param) {
		List<Map<String,Object>> l = Dao.selectList(nameSpace + "selectEmsInfo",param);
		return l;
	}

	public String updateOrAddEmsInfo(Map<String, Object> param) {
		String target_type = StringUtils.nullToString(param.get("TARGET_TYPE"));
		String target_id = StringUtils.nullToString(param.get("TARGET_ID"));
		String ems_flag = StringUtils.nullToString(param.get("EMS_FLAG"));
		String ems_id = "";
		int num = 0;
		String msg = "";
		if(StringUtils.isBlank(ems_flag)){
			List i = Dao.selectList(nameSpace + "selectEmsByInfo", param);
			if(i.size()>0){
				msg = "请检查录入数据是否正确，数据库中已存在这个地址"+((Map)i.get(0)).get("EMS_FLAG");
				return msg;
			}
			//新票据
			//
			ems_id = Dao.getSequence("SEQ_T_SYS_EMS_INFO");
			param.put("SQL_SEQ_ID", ems_id);
			num += Dao.insert(nameSpace + "insertEmsInfo", param);
			msg = "新增加地址成功";
		}else{
			param.put("EMS_FLAG", ems_flag);
			ems_id = ((Map)Dao.selectList(nameSpace + "selectEmsByEmsFlag", param).get(0)).get("ID").toString();
//				((Map)(.get(0))).get("EMS_FLAG");
			//修改
			num += Dao.update(nameSpace + "updateEmsInfo", param);
			msg = "关联地址或修改地址成功";
		}
		param.put("EMS_ID", ems_id);
		/*param.put("EMS_FLAG", ems_flag);
		param.put("TARGET_ID", target_id);
		param.put("DIC_TYPE", "发票_对象_类型");
		param.put("TARGET_TYPE", target_type);
		num += Dao.update(nameSpace + "updateClientEmsId", param);
		num += Dao.update(nameSpace + "updateSupplierId", param);
		num += Dao.update(nameSpace + "updateCompanyId", param);*/
		num += Dao.update(nameSpace + "updateInvoiceAppEmsId", param);
		return msg;
	}

	public int uploadInvoiceEmsInfo(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				
				String invoice_code = StringUtils.nullToString(row.get(0));
				String ems_name = StringUtils.nullToString(row.get(1));
				String ems_num = StringUtils.nullToString(row.get(2));
				String ems_date = StringUtils.nullToString(row.get(3));
				String EMS_ADDR = StringUtils.nullToString(row.get(4));
				String EMS_PER = StringUtils.nullToString(row.get(5));
				String EMS_POST = StringUtils.nullToString(row.get(6));
				ems_date =ems_date.substring(0,4)+"-"+ems_date.substring(5,7)+"-"+ems_date.substring(8,10) ;
				if(StringUtils.nullToString(row.get(0)).equals("")){
					continue;
				}
				m.put("invoice_code", invoice_code);
				m.put("ems_name", ems_name);
				m.put("ems_num", ems_num);
				m.put("ems_date", ems_date);
				m.put("EMS_ADDR", EMS_ADDR);
				m.put("EMS_PER", EMS_PER);
				m.put("EMS_POST", EMS_POST);
				count += Dao.update(nameSpace + "updateCheckByInvoiceId" , m);
				count += Dao.update(nameSpace + "updateCheckByInvoiceId2" , m);
				
			}
		}
		return count;
	}

	public Map<String,Object> getEMSInfoById(Map<String,Object> param){
		return Dao.selectOne(nameSpace +"getEMSInfoById", param) ;
	}
	
	public int synchronizationEms(Map<String, Object> param) {
		return Dao.update(nameSpace + "synchronizationEms", param);
	}
	
}
