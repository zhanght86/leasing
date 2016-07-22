package com.pqsoft.insure.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.FileCopyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.entity.Excel;
import com.pqsoft.insure.util.DateUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.Util;
@SuppressWarnings("unchecked")
public class InsureService{

	public Page queryPage(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.getAllInsure", m);
		//拿到起租日和租赁周期计算结束日期
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			String LEASE_TERM = map.get("LEASE_TERM")+"";
			String LEASE_PERIOD = map.get("LEASE_PERIOD")+"";
			String DELIVER_DATE = map.get("DELIVER_DATE")+"";
			String END_DATE = Util.getMonth(DELIVER_DATE, Integer.parseInt(LEASE_TERM)*Integer.parseInt(LEASE_PERIOD));
			map.put("END_DATE", END_DATE);
			map.put("FAVOREE", "山重");
			map.put("DELIVER_DATE1", map.get("DELIVER_DATE"));
			map.put("TOTAL_PERIODS", Integer.parseInt(LEASE_TERM)*Integer.parseInt(LEASE_PERIOD));
		}
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getAllInsure_count", m));
		return page;
	}

	public Map queryProductAndCompany() {
		List<Object> products = Dao.selectList("Insure.queryAllProduct");
		List<Object> companys = Dao.selectList("Insure.queryAllCompany");
		Map rm = new HashMap();
		rm.put("products", products);
		rm.put("companys", companys);
		return rm;
	}
	public Excel exportData(Map<String, Object> params) {
		List list = Dao.selectList("Insure.getAllInsureEXCEL", params);
		//导出excel的时候相应的生成保险数据
		Dao.insert("Insure.insert_insur_info", params);
		Dao.update("Insure.update_WHETHER_CREATE_INSURE", params);
		//初始化一些参数
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String LEASE_TERM = map.get("LEASE_TERM")+"";
			String LEASE_PERIOD = map.get("LEASE_PERIOD")+"";
			String DELIVER_DATE = map.get("DELIVER_DATE")+"";
			String END_DATE = Util.getMonth(DELIVER_DATE, Integer.parseInt(LEASE_TERM)*Integer.parseInt(LEASE_PERIOD));
			map.put("END_DATE", END_DATE);
			map.put("FAVOREE", "山重");
			map.put("FAVOREE2", "山重");
			map.put("FAVOREE3", "山重");
			map.put("INSURANCE_COMPANY", "");
			map.put("INSURANCE_TYPE", "");
			map.put("DELIVER_DATE1", map.get("DELIVER_DATE"));
			map.put("TOTAL_PERIODS", Integer.parseInt(LEASE_TERM)*Integer.parseInt(LEASE_PERIOD));
		}
		
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(list);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("PRO_CODE", "项目编号");
		title.put("SUPPLIERS_NAME", "供应商");
		title.put("INSURANCE_COMPANY", "保险公司");
		title.put("INSURANCE_TYPE", "险种");
		title.put("FAVOREE", "投保人");
		title.put("FAVOREE2", "被保险人");
		title.put("FAVOREE3", "受益人");
		title.put("NAME", "发票名称");
		title.put("COMPANY_NAME", "厂商");
		title.put("AREA", "使用区域");
		title.put("WHOLE_ENGINE_CODE", "出厂日期");
		title.put("PRODUCT_NAME", "租赁物名称");
		title.put("SPEC_NAME", "型号");
		title.put("WHOLE_ENGINE_CODE", "出厂编号");
		title.put("CAR_SYMBOL", "车架号");
		title.put("ENGINE_TYPE", "发动机号");
		title.put("PILFER", "有无盗抢险");
		title.put("UNIT_PRICE", "保险金额");
		title.put("MONEY", "实收保费");
		title.put("DELIVER_DATE", "保险起期");
		title.put("END_DATE", "保险止期");
		title.put("TOTAL_PERIODS", "保险期限");
		excel.addTitle(title);
		return excel;
	}

	public Page insureCheckPage(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.insureCheckPage", m);
		//拿到起租日和租赁周期计算结束日期
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			String INSUR_TERM = map.get("INSUR_TERM")+"";
			String DELIVER_DATE = map.get("DELIVER_DATE")+"";
			String END_DATE = Util.getMonth(DELIVER_DATE, Integer.parseInt(INSUR_TERM));
			map.put("END_DATE", END_DATE);
			map.put("FAVOREE", "山重");
			map.put("DELIVER_DATE1", map.get("DELIVER_DATE"));
			map.put("TOTAL_PERIODS", Integer.parseInt(INSUR_TERM));
		}
		//只有一行的时候添加一个空行
		if(list.size()==1){
			list.add(new HashMap<String, Object>());
		}
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.insureCheckPage_count", m));
		return page;
	}

	public Excel insureCheckExportData(Map<String, Object> params) {
		if(params.get("status_")==null||"".equals(params.get("status_"))){
			params.put("status_", "申请");
		}
		List list = Dao.selectList("Insure.insureCheckPageEXCEL", params);
		//初始化一些参数
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String INSUR_TERM = map.get("INSUR_TERM")+"";
			String DELIVER_DATE = map.get("DELIVER_DATE")+"";
			String END_DATE = Util.getMonth(DELIVER_DATE, Integer.parseInt(INSUR_TERM));
			map.put("END_DATE", END_DATE);
			if(!"yes".equals(map.get("OPERATION_TYPE"))){
				map.put("FAVOREE", "山重");
				map.put("FAVOREE2", "山重");
				map.put("FAVOREE3", "山重");
			}else {
				map.put("FAVOREE", map.get("SUPPLIERS_NAME"));
				map.put("FAVOREE2", map.get("SUPPLIERS_NAME"));
				map.put("FAVOREE3", map.get("SUPPLIERS_NAME"));
			}
			map.put("INSURANCE_COMPANY", "");
			map.put("INSURANCE_TYPE", "");
			map.put("DELIVER_DATE1", map.get("DELIVER_DATE"));
			map.put("TOTAL_PERIODS", INSUR_TERM);
		}
		
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(list);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("PRO_CODE", "项目编号");
		title.put("SUPPLIERS_NAME", "供应商");
		title.put("INSURANCE_COMPANY", "保险公司");
		title.put("INSURANCE_TYPE", "险种");
		title.put("FAVOREE", "投保人");
		title.put("FAVOREE2", "被保险人");
		title.put("FAVOREE3", "受益人");
		title.put("NAME", "发票名称");
		title.put("COMPANY_NAME", "厂商");
		title.put("AREA", "使用区域");
		title.put("WHOLE_ENGINE_CODE", "出厂日期");
		title.put("PRODUCT_NAME", "租赁物名称");
		title.put("SPEC_NAME", "型号");
		title.put("WHOLE_ENGINE_CODE", "出厂编号");
		title.put("CAR_SYMBOL", "车架号");
		title.put("ENGINE_TYPE", "发动机号");
		title.put("PILFER", "有无盗抢险");
		title.put("UNIT_PRICE", "保险金额");
		title.put("MONEY", "实收保费");
		title.put("DELIVER_DATE", "保险起期");
		title.put("END_DATE", "保险止期");
		title.put("TOTAL_PERIODS", "保险期限");
		excel.addTitle(title);
		return excel;
	}

	public void insureCheckApproval(Map<String, Object> param) {
		//parm='yes'表示审批通过，只需要把status状态改为‘预约’
		if("yes".equals(param.get("parm"))){
			param.put("status_s", "预约");
			param.put("status_e", "申请");
			this.updateStatus1(param);
		}else {//parm='no'表示审批不通过，删除insur_info表数据同时更新fil_project_head里面whether_create_insure字段为空
			if(param.get("status_")==null||"".equals(param.get("status_"))){
				param.put("status_", "申请");
			}
			Dao.update("Insure.update_WHETHER_CREATE_INSURE2", param);
			Dao.delete("Insure.delete_insur_info", param);
		}
	}

	public void importEXCEL(FileItem file) {
		List<List<String>> lists = new ArrayList<List<String>>();//每个sheet
		List<String> list = null;//每行
		try {
			Workbook book = Workbook.getWorkbook(file.getInputStream());
			Sheet[] sheets = book.getSheets();
			Sheet sheet = null;
			Cell cell = null;
			sheet = sheets[0];
			for(int j = 1 ; j < sheet.getRows() ; j++){//行(第一行为表头，所以从第二行开始循环)
				list = new ArrayList<String>();//sheet中每行
				if("".equals(sheet.getCell(0, j).getContents()))  break;//如果第一列为空（项目编号为空），说明下面没有数据，即推出循环
				for(int k = 0 ; k < sheet.getColumns() ; k++){//列
					cell = sheet.getCell(k, j);//k列j行
					if(cell.getType()==CellType.DATE){
						list.add(DateUtil.format(((DateCell) cell).getDate(), "yyyy-MM-dd"));
					}else {
						list.add(cell.getContents().trim());
					}
				}
				lists.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(lists!=null&&!lists.isEmpty()) this.insureAffirm(lists);
	}
	//----------------------------------------------------------------------
	/*
	 * 保险确认操作
	 * 
	 */
	public void insureAffirm(List<List<String>> ls){
		for(int i=0;i<ls.size();i++){
			List<String> list = ls.get(i);
			String PROJ_NO = list.get(0);//项目编号
			String EQUIP_SN = list.get(1);//出厂编号
			String INSUR_NO = list.get(2);//保单号
			String INSUR_DATE = list.get(3);//保单日期
			String INVOICE_NO = list.get(4);//发票编号
			String INVOICE_DATE = list.get(5);//发票日期
			String INSUR_START_DATE = list.get(6);//保险开始日期
			String INSUR_TERM = list.get(7);//保险期限
			String INSUR_END_DATE = Util.getMonth(INSUR_START_DATE, Integer.parseInt(INSUR_TERM));//保险结束日期
			Map m = new HashMap();
			m.put("PROJ_NO", PROJ_NO);
			m.put("EQUIP_SN", EQUIP_SN);
			m.put("INSUR_NO", INSUR_NO);
			m.put("INSUR_DATE", INSUR_DATE);
			m.put("INVOICE_NO", INVOICE_NO);
			m.put("INVOICE_DATE", INVOICE_DATE);
			m.put("INSUR_START_DATE", INSUR_START_DATE);
			m.put("INSUR_TERM", INSUR_TERM);
			m.put("INSUR_END_DATE", INSUR_END_DATE);
			m.put("INSUR_TYPE", "全保");
			m.put("STATUS", "起保");
			Dao.insert("Insure.insureAffirm", m);
		}
	}
	/*
	 * 保险确认操作
	 * 
	 */
	public void insureAffirm2(JSONArray ls){
		for(int i=0;i<ls.size();i++){
			JSONObject list = ls.getJSONObject(i);
			String PROJ_NO = list.get("PROJ_NO")+"";//项目编号
			String EQUIP_SN = list.get("EQUIP_SN")+"";//出厂编号
			String INSUR_NO = list.get("INSUR_NO")+"";//保单号
			String INSUR_DATE = list.get("INSUR_DATE")+"";//保单日期
			String INVOICE_NO = list.get("INVOICE_NO")+"";//发票编号
			String INVOICE_DATE = list.get("INVOICE_DATE")+"";//发票日期
			String INSUR_START_DATE = list.get("INSUR_START_DATE")+"";//保险开始日期
			String INSUR_TERM = list.get("INSUR_TERM")+"";//保险期限
			String INSUR_END_DATE = Util.getMonth(INSUR_START_DATE, Integer.parseInt(INSUR_TERM));//保险结束日期
			Map m = new HashMap();
			m.put("PROJ_NO", PROJ_NO);
			m.put("EQUIP_SN", EQUIP_SN);
			m.put("INSUR_NO", INSUR_NO);
			m.put("INSUR_DATE", INSUR_DATE);
			m.put("INVOICE_NO", INVOICE_NO);
			m.put("INVOICE_DATE", INVOICE_DATE);
			m.put("INSUR_START_DATE", INSUR_START_DATE);
			m.put("INSUR_TERM", INSUR_TERM);
			m.put("INSUR_END_DATE", INSUR_END_DATE);
			m.put("INSUR_TYPE", "全保");
			m.put("STATUS", "起保");
			Dao.insert("Insure.insureAffirm", m);
		}
	}
	/*
	 * DB保险确认操作
	 * 
	 */
	public void insureDbAffirmApproval(JSONArray ls){
		for(int i=0;i<ls.size();i++){
			JSONObject list = ls.getJSONObject(i);
			String PROJ_NO = list.get("PROJ_NO")+"";//项目编号
			String EQUIP_SN = list.get("EQUIP_SN")+"";//出厂编号
			String INSUR_NO = list.get("INSUR_NO")+"";//保单号
			String INSUR_DATE = list.get("INSUR_DATE")+"";//保单日期
			String INVOICE_NO = list.get("INVOICE_NO")+"";//发票编号
			String INVOICE_DATE = list.get("INVOICE_DATE")+"";//发票日期
			String INSUR_START_DATE = list.get("INSUR_START_DATE")+"";//保险开始日期
			String INSUR_TERM = list.get("INSUR_TERM")+"";//保险期限
			String INSUR_END_DATE = Util.getMonth(INSUR_START_DATE, Integer.parseInt(INSUR_TERM));//保险结束日期
			Map m = new HashMap();
			m.put("PROJ_NO", PROJ_NO);
			m.put("EQUIP_SN", EQUIP_SN);
			m.put("INSUR_NO", INSUR_NO);
			m.put("INSUR_DATE", INSUR_DATE);
			m.put("INVOICE_NO", INVOICE_NO);
			m.put("INVOICE_DATE", INVOICE_DATE);
			m.put("INSUR_START_DATE", INSUR_START_DATE);
			m.put("INSUR_TERM", INSUR_TERM);
			m.put("INSUR_END_DATE", INSUR_END_DATE);
			//查询项目期限和保险期限比较，如果保险期限小于项目期限则为半保
			Map tmp = Dao.selectOne("Insure.query_lease_num", PROJ_NO);
			if(Double.parseDouble(tmp.get("LEASE_NUM")+"")>Double.parseDouble(INSUR_TERM)){
				m.put("INSUR_TYPE", "半保");
				m.put("STATUS", "续保");
			}else {
				m.put("INSUR_TYPE", "全保");
				m.put("STATUS", "起保");
			}
			Dao.update("Insure.insureAffirm", m);
		}
	}
	
	//修改状态param.put("status_s", "预约"); param.put("status_e", "申请");
	public void updateStatus(Map<String, Object> param){
		Dao.update("Insure.update_insur_info", param);
	}
	//修改状态param.put("status_s", "预约"); param.put("status_e", "申请");
	public void updateStatus1(Map<String, Object> param){
		Dao.update("Insure.update_insur_info1", param);
	}
	public void updateStatus_renewal(Map<String, Object> param){
		Dao.update("Insure.update_insur_info_renewal", param);
	}
	public Page renewalRemindListing(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.query_renewal_remind", m);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			map.put("FAVOREE", "山重");
		}
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.query_renewal_remind_count", m));
		return page;
	}

	public Page renewalEnteringListing(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.query_renewalEntering", m);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			map.put("FAVOREE", "山重");
			Map tmp = new HashMap();
			tmp.put("status","待续2");
			tmp.put("proj_no", map.get("PROJ_NO"));
			List tlist = Dao.selectList("Insure.query_insur_by_projno_status", tmp);
			if(tlist!=null&&!tlist.isEmpty()){
				tmp = (Map)tlist.get(0);
			}
			map.put("INSUR_NO", tmp.get("INSUR_NO"));
			map.put("INSUR_DATE", tmp.get("INSUR_DATE"));
			map.put("INVOICE_NO", tmp.get("INVOICE_NO"));
			map.put("INVOICE_DATE", tmp.get("INVOICE_DATE"));
			map.put("INSUR_START_DATE", tmp.get("INSUR_START_DATE"));
			map.put("INSUR_TERM", tmp.get("INSUR_TERM"));
		}
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.query_renewalEntering_count", m));
		return page;
	}

	public Excel insureExportData(Map<String, Object> params) {
		if(params.get("status_")==null||"".equals(params.get("status_"))){
			params.put("status_", "申请");
		}
		List list = Dao.selectList("Insure.insurePageEXCEL", params);
		//初始化一些参数
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String INSUR_TERM = map.get("INSUR_TERM")+"";
			String DELIVER_DATE = map.get("DELIVER_DATE")+"";
			String END_DATE = Util.getMonth(DELIVER_DATE, Integer.parseInt(INSUR_TERM));
			map.put("END_DATE", END_DATE);
			if(!"yes".equals(map.get("OPERATION_TYPE"))){
				map.put("FAVOREE", "山重");
				map.put("FAVOREE2", "山重");
				map.put("FAVOREE3", "山重");
			}else {
				map.put("FAVOREE", map.get("SUPPLIERS_NAME"));
				map.put("FAVOREE2", map.get("SUPPLIERS_NAME"));
				map.put("FAVOREE3", map.get("SUPPLIERS_NAME"));
			}
			map.put("INSURANCE_COMPANY", "");
			map.put("INSURANCE_TYPE", "");
			map.put("DELIVER_DATE1", map.get("DELIVER_DATE"));
			map.put("TOTAL_PERIODS", INSUR_TERM);
		}
		
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(list);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("PRO_CODE", "项目编号");
		title.put("SUPPLIERS_NAME", "供应商");
		title.put("INSURANCE_COMPANY", "保险公司");
		title.put("INSURANCE_TYPE", "险种");
		title.put("FAVOREE", "投保人");
		title.put("FAVOREE2", "被保险人");
		title.put("FAVOREE3", "受益人");
		title.put("NAME", "发票名称");
		title.put("COMPANY_NAME", "厂商");
		title.put("AREA", "使用区域");
		title.put("WHOLE_ENGINE_CODE", "出厂日期");
		title.put("PRODUCT_NAME", "租赁物名称");
		title.put("SPEC_NAME", "型号");
		title.put("WHOLE_ENGINE_CODE", "出厂编号");
		title.put("CAR_SYMBOL", "车架号");
		title.put("ENGINE_TYPE", "发动机号");
		title.put("PILFER", "有无盗抢险");
		title.put("UNIT_PRICE", "保险金额");
		title.put("MONEY", "实收保费");
		title.put("DELIVER_DATE", "保险起期");
		title.put("END_DATE", "保险止期");
		title.put("TOTAL_PERIODS", "保险期限");
		excel.addTitle(title);
		return excel;
	}
	public Page renewalAffirmListing(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.renewalAffirm", m);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			map.put("FAVOREE", "山重");
			Map tmp = new HashMap();
			tmp.put("status","待续2");
			tmp.put("proj_no", map.get("PROJ_NO"));
			List tlist = Dao.selectList("Insure.query_insur_by_projno_status", tmp);
			if(tlist!=null&&!tlist.isEmpty()){
				tmp = (Map)tlist.get(0);
			}
			map.put("INSUR_NO", tmp.get("INSUR_NO"));
			map.put("INSUR_DATE", tmp.get("INSUR_DATE"));
			map.put("INVOICE_NO", tmp.get("INVOICE_NO"));
			map.put("INVOICE_DATE", tmp.get("INVOICE_DATE"));
			map.put("INSUR_START_DATE", tmp.get("INSUR_START_DATE"));
			map.put("INSUR_TERM", tmp.get("INSUR_TERM"));
		}
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.renewalAffirm_count", m));
		return page;
	}

	public void informationEntry(Map<String, Object> param) throws Exception {
		//先保存文件
		String path = SkyEye.getConfig("file.path")+File.separator+"insure"+File.separator;
		FileItem file = (FileItem)param.get("file");
		InputStream stream = file.getInputStream();
		if(file.getSize()>0){
			new File(path).mkdirs();
			path = path+UUID.randomUUID()+file.getName().substring(file.getName().indexOf('.'),file.getName().length());
			File newFile = new File(path);
			newFile.createNewFile();
			FileCopyUtils.copy(stream, new FileOutputStream(newFile));
			//插入保险单图片
			param.put("PICTURE_SRC", path);
		}
		//插入数据库相应的数据
		//如果有之前插入的状态为‘待续2’则删除
		param.put("status", "待续2");
		Dao.delete("Insure.informationEntry_delete", param);
		//查询项目周期和保险周期比较
		Map tnum = Dao.selectOne("Insure.query_lease_num", param);
		double lease_num = Double.parseDouble(tnum.get("LEASE_NUM")+"");
		//查询已经投保的期次
		Map tinsur_term = Dao.selectOne("Insure.query_insur_term", param);
		double insur_term = Double.parseDouble(tinsur_term.get("INSUR_TERM")+"");
		//如果当前投保期次总和小于项目周期则为半保
		if(insur_term+Double.parseDouble(param.get("INSUR_TERM")+"")<lease_num){
			param.put("INSUR_TYPE", "半保");
		}else {
			param.put("INSUR_TYPE", "全保");
		}
		//计算保险终止日期（由起始日期加上保险期次）
		String INSUR_END_DATE = Util.getMonth(param.get("INSUR_START_DATE")+"", Integer.parseInt(param.get("INSUR_TERM")+""));
		param.put("INSUR_END_DATE", INSUR_END_DATE);
		//插入相应数据并修改
		Dao.insert("Insure.informationEntry_insert", param);
		param.put("ID", param.get("NEWID"));
		Dao.update("Insure.informationEntry_update", param);
	}
	public List<Map<String,Object>> getInsuranceAll(Map<String,Object> m)
	{
		return Dao.selectList("Insure.getInsuranceAll",m);
	}
	public Page getInsurance(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList("Insure.getInsuranceAll", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("Insure.getInsuranceAll_count", m));
		return page;
	}
	public int nowclose(Map<String, Object> m)
	{
		return Dao.update("Insure.nowclose",m);
	}
	public Map<String, Object> geteq(Map<String, Object> m)
	{
		return Dao.selectOne("Insure.geteq",m);
	}
	public Map<String,Object> queryNaturaType(Map<String, Object> m)
	{
		return Dao.selectOne("Insure.queryNaturaType",m);
	}
	public Map<String,Object> getOfficial(Map<String,Object> m)
	{
		return Dao.selectOne("Insure.getOfficial",m);
	}
	public List<Map<String,Object>> getCompanyAll()
	{
		return Dao.selectList("Insure.getCompanyAll");
	}
}
