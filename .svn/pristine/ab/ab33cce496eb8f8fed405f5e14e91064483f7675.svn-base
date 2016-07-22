package com.pqsoft.eqInvoices.service;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.sequence.SequenceUtils;

public class EqInvoicesService {
	private String basePath = "EqInvoice.";
	
	/**
	 * 管理页面分页查询
	 * @param param
	 * @return
	 */
	public Page getPageData(Map<String,Object> param){
		param.put("PARAMONE", "业务类型");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getEqInvoiceList",param));
		page.addDate(array, Dao.selectInt(basePath+"getEqInvoiceCount", param));
		return page;
	}
	
	public Map<String,Object> getInvoiceAmount(Map<String,Object> param){
		return Dao.selectOne(basePath + "getInvoiceAmount", param) ;
	}
	
	public Map<String,Object> getInvoiceDun(Map<String,Object> param){
		return Dao.selectOne(basePath + "getInvoiceDun", param) ;
	}
	
	/**
	 * 管理页面分页查询
	 * @param param
	 * @return
	 */
	public Page getPageDataDun(Map<String,Object> param){
		param.put("PARAMONE", "业务类型");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDunInvoiceList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDunInvoiceCount", param));
		return page;
	}

	/**
	 * 添加租赁物（设备）发票
	 * @param param
	 * @return
	 */
	public int addEqInvoice(Map<String,Object> param){
		SequenceUtils se = SequenceUtils.getInstance();
		if(param.containsKey("EQINVOICE_LEGER_NO") && (param.get("EQINVOICE_LEGER_NO") == null || "".equals(param.get("EQINVOICE_LEGER_NO").toString().trim()))){
			//发票台账序号增加
			String eq_leger_no = se.nextValue("SEQ_EQINVOICE_LEGER",1000,true)+"";//Dao.getSequence("SEQ_EQINVOICE_LEGER");
			if(eq_leger_no.equals("1")){
				Dao.update(basePath+"clearHisAllLegerNo",param);
			}
			param.put("EQINVOICE_LEGER_NO", eq_leger_no);
			Dao.update(basePath+"updateEqInvoiceLegerNo", param);
		}
		
		return Dao.insert(basePath+"addEqInvoice",param);
	}
	
	/**
	 * 更新放款发票信息
	 * @param param
	 * @return
	 */
	public int updatePaymentEqInvoice(Map<String,Object> param){
		return Dao.update(basePath+"updatePaymentEqInvoice", param);
	}
	
	/**
	 * 更新设备发票信息
	 * @param param
	 * @return
	 */
	public int updateEqInvoice(Map<String,Object> param){
		return Dao.update(basePath+"upateEqInvoice", param);
	}
	
	/**
	 * 删除设备发票信息
	 * @param param
	 * @return
	 */
	public int delEqInvoice(Map<String,Object> param){
		
		return Dao.delete(basePath+"delEqInvoice", param);
	}
	
	/**
	 * 获得设备发票列表
	 * @param param
	 * @return
	 */
	public List<Object> getEqInvoiceList(Map<String,Object> param){
		param.put("DIC_LEGA", "发票认证状态");
		return Dao.selectList(basePath+"getInvoiceList", param);
	}
	
	/**
	 * 获得单条设备发票详细信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOneEqInvoice(Map<String,Object> param){
		return Dao.selectOne(basePath+"getEqInvoiceMess", param);
	}
	
	/**
	 * 获取项目下所有设备
	 * @param param
	 * @return
	 */
	public List<Object> getProEqMess(Map<String,Object> param){
		param.put("DIC_UNIT", "设备单位");
		return Dao.selectList(basePath+"getProEqList", param);
	}
	
	public Map<String,Object> getOneEqMess(Map<String,Object> param){
		param.put("DIC_UNIT", "设备单位");
		return Dao.selectOne(basePath+"getProEqMessByEqId", param);
	}
	
	public Map<String,Object> getInvoiceDetail(Map<String,Object> param){
		return Dao.selectOne(basePath+"getInvoiceDetail", param);
	}
	
    /**
     * 租赁物发票上传录入
     * @param files
     * @return
     */
	public int uploadEqInvoiceExcel(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				String PAYLIST_CODE = row.get(0).toString();
				List<Object> proEqMessList = Dao.selectList(basePath+"getProEqByPayListCode", PAYLIST_CODE);
				for (Object proEq : proEqMessList) {
					Map<String, Object> m  = new HashMap<String,Object>();;
					Map<String,Object> proEqMess = (Map<String,Object>)proEq;
					m.put("PROJECT_ID", proEqMess.get("PROJECT_ID").toString());
					m.put("PAY_ID", proEqMess.get("PAY_ID").toString());
					m.put("SALES_UNIT_ID", proEqMess.get("SALES_UNIT_ID").toString());
					m.put("PRO_EQUIPMENT_ID", proEqMess.get("PRO_EQUIPMENT_ID").toString());
					m.put("CREATE_USER", Security.getUser().getName());
					m.put("INVOICE_CODE", row.get(1));
					m.put("INVOICE_DATE", row.get(2));
					m.put("INVOICE_AMOUNT", row.get(3)); 
					m.put("INVOICE_TAX", row.get(4));
					m.put("TAI_NUM", row.get(5));
					m.put("DRAWER", row.get(6));
					if(row.size()>0 && m != null){
						if(proEqMess.containsKey("EQINVOICE_LEGER_NO") &&(proEqMess.get("EQINVOICE_LEGER_NO") == null || "".equals(proEqMess.get("EQINVOICE_LEGER_NO").toString().trim()))){
							//发票台账序号增加
							SequenceUtils se = SequenceUtils.getInstance();
							String eq_leger_no = se.nextValue("SEQ_EQINVOICE_LEGER",1000,true)+"";//Dao.getSequence("SEQ_EQINVOICE_LEGER");
							if(eq_leger_no.equals("1")){
								Dao.update(basePath+"clearHisAllLegerNo",m);
							}
							m.put("EQINVOICE_LEGER_NO", eq_leger_no);
							Dao.update(basePath+"updateEqInvoiceLegerNo", m);
						}
						count += Dao.insert(basePath+"addEqInvoice",m);
					}
				}
			}
		}
		return count;
	}
	
	/**
	 * 租赁物发票上传批量认证
	 * @param files
	 * @return
	 */
	public int uploadCheckInvoiceMess(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				String INVOICE_CODE = row.get(0).toString();
				if(row.size()>0 &&  !"".equals(INVOICE_CODE)){
					String LEGA_STATUS = row.get(1).toString();
					m.put("INVOICE_CODE", INVOICE_CODE);
					Map<String,Object> InvoiceMess = getInvoiceDetail(m);
					//项目放款回更
					if(LEGA_STATUS !=null && "1".equals(LEGA_STATUS)){
						paymentService payService = new paymentService();
						Map<String,Object> newParam = new HashMap<String, Object>();
						newParam.put("INVOICE_NUM", InvoiceMess.containsKey("INVOICE_CODE") && InvoiceMess.get("INVOICE_CODE")!=null ? InvoiceMess.get("INVOICE_CODE").toString():"");
						newParam.put("PROJECT_CODE", InvoiceMess.containsKey("PRO_CODE") && InvoiceMess.get("PRO_CODE")!=null ? InvoiceMess.get("PRO_CODE").toString():"");
						newParam.put("PAY_TYPE", 1);
						newParam.put("INVOICE_DATE", InvoiceMess.containsKey("INVOICE_DATE") && InvoiceMess.get("INVOICE_DATE")!=null ? InvoiceMess.get("INVOICE_DATE").toString():"");
						payService.updatePMByDate(newParam);
					}
					
					m.put("LEGA_STATUS", LEGA_STATUS);
					m.put("LEGA_USER", Security.getUser().getName());
					count += Dao.update(basePath+"updateCheckByBillCode",m);
				}
			}
		}
		return count;
	}
	
	/**
	 * 导出进项发票信息
	 * @param param
	 * @return
	 */
	public Excel getExcelInvoices(Map<String,Object> param){
		List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getExcelInvoices", param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();
		title.put("SUP_SHORTNAME", "供应商");
		title.put("CLIENT_NAME", "客户名称");
		title.put("PLATFORM_TYPE", "业务类型");
		title.put("LEASE_CODE", "合同编号");
		title.put("PAYLIST_CODE", "还款计划编号");
		title.put("TYPE_NAME", "机型");
		title.put("ENGINE_TYPE", "机号");
		title.put("LEASE_TOPRIC", "租赁物购买价款");
		title.put("INVOICE_CODE", "发票号");
		title.put("INVOICE_DATE", "发票日期");
		title.put("CREATE_TIME", "录入日期");
		title.put("INVOICE_AMOUNT", "实际放款日期");
		title.put("INVOICE_TAX", "税额");
		
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.setHeadTitles("发票登记表");
		excel.setHeadDate(true);
		excel.hasRownum();
		return excel;
	}
    
	
	public List<Object> getProDunMess(Map<String,Object> param){
		return Dao.selectList(basePath+"getProDunList", param);
	}
	
	/**
	 * 添加代收违约金发票
	 * @param param
	 * @return
	 */
	public int addDunInvoice(Map<String,Object> param){
		SequenceUtils se = SequenceUtils.getInstance();
		
			//发票台账序号增加
			String eq_leger_no = se.nextValue("SEQ_DUNINVOICE_LEGER",1000,true)+"";//Dao.getSequence("SEQ_EQINVOICE_LEGER");
			if(eq_leger_no.equals("1")){
				Dao.update(basePath+"clearHisAllLegerNoDun",param);
			}
			param.put("EQINVOICE_LEGER_NO", eq_leger_no);
			Dao.update(basePath+"updateEqInvoiceLegerNoDun", param);
		
		param.put("STATUSINV", "1");
		Dao.update(basePath+"updateDunInvoiceCode",param);
		return Dao.insert(basePath+"addEqInvoice",param);
	}
	
	public int updateDunInvoice(Map<String,Object> param){
		Dao.update(basePath+"updateDunInvCode",param);
		return Dao.update(basePath+"upateEqInvoice", param);
	}
	
	/**
	 * 更新设备发票信息
	 * @param param
	 * @return
	 */
	public int updateDunHGInvoice(Map<String,Object> param){
		//项目放款回更
		if(param.containsKey("LEGA_STATUS") && param.get("LEGA_STATUS") !=null && "2".equals(param.get("LEGA_STATUS").toString())){
			//先查询是什么发票，如果是违约金发票，将清空违约金表的发票号码
			Map MapInv=Dao.selectOne(basePath+"selEqInvoice", param);
			if(MapInv!=null && MapInv.get("PAYLIST_CODE")!=null && !MapInv.get("PAYLIST_CODE").equals("")){
				Dao.update(basePath+"updateOverDueInvCodeClear",MapInv);
			}
		}
		return Dao.update(basePath+"upateEqInvoice", param);
	}
	
	/**
	 * 删除设备发票信息
	 * @param param
	 * @return
	 */
	public int delDunInvoice(Map<String,Object> param){
		//先查询是什么发票，如果是违约金发票，将清空违约金表的发票号码
		Map MapInv=Dao.selectOne(basePath+"selEqInvoice", param);
		if(MapInv!=null && MapInv.get("PAYLIST_CODE")!=null && !MapInv.get("PAYLIST_CODE").equals("")){
			Dao.update(basePath+"updateOverDueInvCodeClear",MapInv);
		}
		return Dao.delete(basePath+"delEqInvoice", param);
	}
	
	 /**
     * 租赁物发票上传录入
     * @param files
     * @return
     */
	public int uploadDunInvoiceExcel(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				String PAYLIST_CODE = row.get(0).toString();
				List<Object> proEqMessList = Dao.selectList(basePath+"getProEqByPayListCode", PAYLIST_CODE);
				for (Object proEq : proEqMessList) {
					Map<String, Object> m  = new HashMap<String,Object>();;
					Map<String,Object> proEqMess = (Map<String,Object>)proEq;
					m.put("PROJECT_ID", proEqMess.get("PROJECT_ID").toString());
					m.put("PAY_ID", proEqMess.get("PAY_ID").toString());
					m.put("SALES_UNIT_ID", proEqMess.get("SALES_UNIT_ID").toString());
					m.put("PRO_EQUIPMENT_ID", proEqMess.get("PRO_EQUIPMENT_ID").toString());
					m.put("CREATE_USER", Security.getUser().getName());
					m.put("INVOICE_CODE", row.get(1));
					m.put("INVOICE_DATE", row.get(2));
					m.put("INVOICE_AMOUNT", row.get(3)); 
					m.put("INVOICE_TAX", row.get(4));
					m.put("TAI_NUM", row.get(5));
					m.put("PERIOD", row.get(5));
					m.put("DRAWER", row.get(6));
					m.put("PAYLIST_CODE", PAYLIST_CODE);
					if(row.size()>0 && m != null){
						
							//发票台账序号增加
							SequenceUtils se = SequenceUtils.getInstance();
							String eq_leger_no = se.nextValue("SEQ_DUNINVOICE_LEGER",1000,true)+"";//Dao.getSequence("SEQ_EQINVOICE_LEGER");
							if(eq_leger_no.equals("1")){
								Dao.update(basePath+"clearHisAllLegerNoDun",m);
							}
							m.put("EQINVOICE_LEGER_NO", eq_leger_no);
							m.put("STATUSINV", "1");
							
							Map overMap=Dao.selectOne(basePath+"selDunDate",m);
							if(overMap!=null && overMap.get("OVERDUE_ID")!=null && !overMap.get("OVERDUE_ID").equals("")){
								m.put("OVERDUE_ID", overMap.get("OVERDUE_ID"));
								Dao.update(basePath+"updateDunInvoiceCode",m);
								count += Dao.insert(basePath+"addEqInvoice",m);
							}
					}
				}
			}
		}
		return count;
	}
	
	/**
	 * 租赁物发票上传批量认证
	 * @param files
	 * @return
	 */
	public int uploadDunCheckInvoiceMess(List<File> files) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				String INVOICE_CODE = row.get(0).toString();
				if(row.size()>0 &&  !"".equals(INVOICE_CODE)){
					String LEGA_STATUS = row.get(1).toString();
					m.put("INVOICE_CODE", INVOICE_CODE);
					
					//不通过违约金表回更
					if(LEGA_STATUS !=null && "2".equals(LEGA_STATUS)){
						Map MapInv=Dao.selectOne(basePath+"selEqInvoice", m);
						if(MapInv!=null && MapInv.get("PAYLIST_CODE")!=null && !MapInv.get("PAYLIST_CODE").equals("")){
							Dao.update(basePath+"updateOverDueInvCodeClear",MapInv);
						}
					}
					
					m.put("LEGA_STATUS", LEGA_STATUS);
					m.put("LEGA_USER", Security.getUser().getName());
					count += Dao.update(basePath+"updateCheckByBillCode",m);
				}
			}
		}
		return count;
	}
	
	/**
	 * 导出进项发票信息
	 * @param param
	 * @return
	 */
	public Excel getExcelInvoicesDun(Map<String,Object> param){
		List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getExcelInvoices", param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("SUP_SHORTNAME", "供应商");
		title.put("CLIENT_NAME", "客户名称");
		title.put("PLATFORM_TYPE", "业务类型");
		title.put("LEASE_CODE", "合同编号");
		title.put("PAYLIST_CODE", "还款计划编号");
		title.put("TAI_NUM", "期次");
		title.put("INVOICE_CODE", "发票号");
		title.put("INVOICE_DATE", "发票日期");
		title.put("CREATE_TIME", "录入日期");
		title.put("INVOICE_AMOUNT", "代收金额");
		title.put("INVOICE_TAX", "税额");
		
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.setHeadTitles("发票登记表");
		excel.setHeadDate(true);
		excel.hasRownum();
		return excel;
	}
}
