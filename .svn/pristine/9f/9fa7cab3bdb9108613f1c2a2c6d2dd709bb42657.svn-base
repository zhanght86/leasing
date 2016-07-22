package com.pqsoft.crm.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import net.sf.json.JSONObject;

import com.pqsoft.crm.service.BankSignMgService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.CollectionUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ExcelUtilMoreRow;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.SecuUtil;
import com.pqsoft.util.StringUtils;

public class BankSignMgAction extends Action {
	
	private BankSignMgService service = new BankSignMgService();
	private Map<String,Object> m = null;
	
	@Override
	public Reply execute() {
		return null;
	}
	
	/**
	 * 
	 * @Title: toShowBankSignApplyPage 
	 * @Description: (跳转申请页面) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银申请" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toShowBankSignApplyPageOld() {
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("dic_cust_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("客户类型")));//银行接口
		context.put("dic_sign_bank", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("开户行")));//开户行
		return new ReplyHtml(VM.html("crm/bankSignApplyMg.vm", context));
	}
	
	/**
	 * 
	 * @Title: toShowBankSignApplyPage 
	 * @Description: (跳转申请页面) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银申请" })
	@aDev(code = "110", email = "qjl@pqsoft.cn", name = "齐姜龙")
	public Reply toShowBankSignApplyPage() {
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("dic_cust_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("客户类型")));//银行接口
		context.put("dic_sign_flag", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("签约标识")));//开户行
		return new ReplyHtml(VM.html("crm/bankSignApplyMg.vm", context));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignApplyPageData 
	 * @Description: (申请页面数据) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银申请" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectBankSignApplyPageDataOld(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		m.put("bank_flag", StringUtils.nullToOther(m.get("bank_flag"),"CCB"));
		return new ReplyAjaxPage(service.SelectBankSignApplyPageData(m));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignApplyPageData 
	 * @Description: (申请页面数据) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银申请" })
	@aDev(code = "110", email = "qjl@pqsoft.cn", name = "齐姜龙")
	public Reply doSelectBankSignApplyPageData(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		return new ReplyAjaxPage(service.SelectBankSignApplyPageData(m));
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银申请导出/全导出" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doExportApplyExcel(){
		m = _getParameters();
		//加入操作人信息
		SecuUtil.putUserInfo(m);
		
		return new ReplyExcel(service.getExportApplyExcel(m),"cardCheck"+DateUtil.getSysDate()+Math.abs(new Random().nextInt(10000))+".xls").addOp(new OpLog("业务管理", "签约标识", "申请页面导出excel"));
	}
	public static void main(String[] args) {
		int i =  Math.abs(Integer.MIN_VALUE) ;
		int c =  Math.abs(Integer.MAX_VALUE) ;
		int b = Math.abs(new Random().nextInt(100)) ;
		System.out.println(i);
		System.out.println(c);
		System.out.println(b);
	}
	/**
	 * 
	 * @Title: toBankSignConfirmPage 
	 * @Description: (回执上传页面) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "回执上传页面" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toBankSignConfirmPage(){
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("dic_cust_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("客户类型")));//银行接口
		context.put("dic_sign_bank", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("开户行")));//开户行
		return new ReplyHtml(VM.html("crm/bankSignConfirmMg.vm", context));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignConfirmPageData 
	 * @Description: (查询回执上传页面数据) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "回执上传页面数据(必选)" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectBankSignConfirmPageData(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		m.put("bank_flag", StringUtils.nullToOther(m.get("bank_flag"),"CCB"));
		return new ReplyAjaxPage(service.SelectBankSignConfirmPageData(m));
	}
	
	/**
	 * 
	 * @Title: doUploadConfirmExcelInfo 
	 * @Description: (上传excel回执) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "上传回执" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doUploadConfirmExcelInfo(){
		m = _getParameters();
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		int successCount = service.updateConfirmExcelInfo(files,m);
		if(successCount > 0){
			msg = "确认【"+successCount+"】条！";
			flag = true;
		}else{
			msg = "确认失败,请检查上传文件格式！";
		}
//		JSONObject json = new JSONObject();
		return new ReplyAjax(flag,msg).addOp(new OpLog("业务管理", "客户网银签约", "上传回执" ));
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	 * @Title: doResetBankSignApply 
	 * @Description: (客户网银签约重置) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "重置"  })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doResetBankSignApply(){
		boolean flag = true;
		String msg = "";
		Object data = null;
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		int count = service.resetBankSignApply(m);
		if(count>0){
			msg = "重置成功" + count + "条";
		}else{
			msg = "重置失败";
		}
		return new ReplyAjax(flag,data,msg).addOp(new OpLog("业务管理", "客户网银签约", "重置"+msg  ));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignHistPage 
	 * @Description: (客户网银签查询) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银查询"  })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toSelectBankSignHistPage(){
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		VelocityContext context = new VelocityContext();
		context.put("dic_sign_bank", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("开户行")));//开户行
		context.put("cust_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("客户类型")));//开户行
		return new ReplyHtml(VM.html("crm/bankSignHistShow.vm", context));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignHistPageData 
	 * @Description: (客户网银签约查询) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银查询" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectBankSignHistPageData(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		return new ReplyAjaxPage(service.selectBankSignHistPageData(m));
	}
	
	@SuppressWarnings("unchecked")
	public static Map _getParameters()
	{
		Map m = new HashMap();
		Object enN;
		String para;
		for(Enumeration en = SkyEye.getRequest().getParameterNames(); en.hasMoreElements(); m.put(enN.toString(), para.replace("--请选择--", "").replace("--\\u8bf7\\u9009\\u62e9--", "")))
		{
		    enN = en.nextElement();
		    para = SkyEye.getRequest().getParameter(enN.toString()).trim();
		}
		return m;
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doQyApplyExcel(){
		m = _getParameters();
		
		if(m !=null && m.get("sqlData") !=null && !m.get("sqlData").equals("")){
			String sqlData=m.get("sqlData")+"";
			String[] sqlDataL=sqlData.split(",");
			service.sendQyInfo(sqlDataL,sqlData);
		}
		
		return new ReplyAjax(true);
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doExportApplyExcelNew(){
		m = _getParameters();
		String exportAll="false";
		if(m !=null && m.get("exportAll") !=null && !m.get("exportAll").equals("")){
			exportAll=m.get("exportAll")+"";
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(exportAll.equals("false")){
			if(m.get("sqlData") !=null && !m.get("sqlData").equals("")){
				String sqlData=m.get("sqlData")+"";
				String[] sqlDataL=sqlData.split(",");
				dataList=service.sendQyInfoDc(sqlDataL,sqlData);
			}
		}else{
			dataList=service.sendQyInfoDcAll(m);
		}
		
		return new ReplyExcel(service.getExportApplyExcelDc(dataList),"cardCheck"+DateUtil.getSysDate()+Math.abs(new Random().nextInt(10000))+".xls").addOp(new OpLog("业务管理", "签约标识", "申请页面导出excel"));
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply exportExcelNewFC(){
		m = _getParameters();
		String exportAll="false";
		if(m !=null && m.get("exportAll") !=null && !m.get("exportAll").equals("")){
			exportAll=m.get("exportAll")+"";
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(exportAll.equals("false")){
			if(m.get("sqlData") !=null && !m.get("sqlData").equals("")){
				String sqlData=m.get("sqlData")+"";
				String[] sqlDataL=sqlData.split(",");
				dataList=service.sendQyInfoDcFC(sqlDataL,sqlData);
			}
		}else{
			dataList=service.sendQyInfoDcAllFC(m);
		}
		
		m.put("FILE_NAME", "快钱签约模板.xls");
		m.put("PATH", "fccwb.xls");
		m.put("listData", dataList);
		m.put("rowNum", 4);//从excel第几行开始插入（excel行从0开始计算）
		String[] tilteCell=new String[] {"NUM","AGREEMENTNO","BANKNAME","ACCT","BANK_TYPE","ACNAME","BASYD","ID_CARD_TYPE","IDNO","MOBILE","DAYXE","DBXE","JSSF","JSCS","JSYH","JSZHKHH","JSZH","JSZHYHM","YX","KHSJ","YHKJE","BZ"};
		m.put("tilteCell", tilteCell);
		
		return new ExcelUtilMoreRow(m).addOp(new OpLog("业务管理", "签约标识", "申请页面导出excel"));
		
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: (申请页面导出) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply exportExcelNewFCJY(){
		m = _getParameters();
		String exportAll="false";
		if(m !=null && m.get("exportAll") !=null && !m.get("exportAll").equals("")){
			exportAll=m.get("exportAll")+"";
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(exportAll.equals("false")){
			if(m.get("sqlData") !=null && !m.get("sqlData").equals("")){
				String sqlData=m.get("sqlData")+"";
				String[] sqlDataL=sqlData.split(",");
				dataList=service.sendQyInfoDcFCJY(sqlDataL,sqlData);
			}
		}else{
			dataList=service.sendQyInfoDcAllFCJY(m);
		}
		
		m.put("FILE_NAME", "快钱签约模板.xls");
		m.put("PATH", "fccwb.xls");
		m.put("listData", dataList);
		m.put("rowNum", 4);//从excel第几行开始插入（excel行从0开始计算）
		String[] tilteCell=new String[] {"NUM","AGREEMENTNO","BANKNAME","ACCT","BANK_TYPE","ACNAME","BASYD","ID_CARD_TYPE","IDNO","MOBILE","DAYXE","DBXE","JSSF","JSCS","JSYH","JSZHKHH","JSZH","JSZHYHM","YX","KHSJ","YHKJE","BZ"};
		m.put("tilteCell", tilteCell);
		
		return new ExcelUtilMoreRow(m).addOp(new OpLog("业务管理", "签约标识", "申请页面导出excel"));
		
	}
	
	
	/**
	 * 
	 * @Title: toShowBankSignApplyPage 
	 * @Description: (跳转申请页面) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银查询" })
	@aDev(code = "110", email = "qjl@pqsoft.cn", name = "齐姜龙")
	public Reply toShowBankSignApplyPageCX() {
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("dic_cust_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("客户类型")));//银行接口
		context.put("dic_sign_flag", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("签约标识")));//开户行
		return new ReplyHtml(VM.html("crm/bankSignApplyMgCX.vm", context));
	}
	
	/**
	 * 
	 * @Title: doSelectBankSignApplyPageData 
	 * @Description: (申请页面数据) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "110", email = "qjl@pqsoft.cn", name = "齐姜龙")
	public Reply doSelectBankSignApplyPageDataCX(){
		m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		return new ReplyAjaxPage(service.SelectBankSignApplyPageDataCX(m));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户网银签约", "客户网银查询" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadExcel(){
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map m = _getParameters();
		try {
				
			
			list = service.parsebankSignExcel(files.get(0),m);//解析签约模板
			
			int num=0;//生效条数
			//判断选择的银行模版 
			if("2".equals(m.get("bankFlag")+"")){//签约模板
				num=service.updateBankSignStatus(list, "2");
			}else if("6".equals(m.get("bankFlag")+"")){//解约模板
				num=service.updateBankSignStatus(list, "6");
			}
			msg = "数据正在处理，请稍后刷新页面查看！";
			
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag,null,msg);
	}
}
