package com.pqsoft.base.company.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.company.service.CompanyInfoService;
import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CompanyAction extends Action {
	private CompanyService compservice = new CompanyService();
	private CompanyInfoService comInfoService = new CompanyInfoService();
	private String path = "base/company/";

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("TAXQUA", new DataDictionaryMemcached().get("纳税资质"));
		context.put("TPM_BUSINESS_PLATE_LIST", new DataDictionaryMemcached().get("PDF模版所属商务板块"));
		context.put("PContext", param);
		return new ReplyHtml(VM.html(path+"companyManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		param.put("TYPE", "PDF模版所属商务板块");
		Page pagedata = compservice.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","查看"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply companyDetail(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("company", compservice.getCompanyDetail(param));
		return new ReplyHtml(VM.html(path+"companyDetail.vm",context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply updateCompany(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("type", findDictDataType());
		context.put("company", compservice.getCompanyDetail(param));
		context.put("companyInfo", comInfoService.getOneCompanyInfo(param));
		return new ReplyHtml(VM.html(path+"updateCompany.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","保存修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply saveCompany(){
		Map<String,Object> param = JSONObject.fromObject( _getParameters().get("params"));
		Boolean flag = true;
		String msg = "";
		Map<String, Object> company = (Map<String, Object>) JSONArray.fromObject(param.get("COMPANY")).get(0) ;
		int result = compservice.updateCompany(company);
		//校验是否有附属信息
		int count = comInfoService.selectCount(company);
		Map<String, Object> companyInfo = (Map<String, Object>) JSONArray.fromObject(param.get("COMPANYINFO")).get(0) ;
		companyInfo.put("COMPANY_ID", company.get("COMPANY_ID"));
		companyInfo.put("NAME", company.get("COMPANY_NAME"));
		companyInfo.put("ID_CARD_NO", company.get("LINK_IDCARD"));
		companyInfo.put("PHONE", company.get("LINK_TELEPHONE"));
		companyInfo.put("TEL_PHONE", company.get("LINK_MOBILE"));
		companyInfo.put("ORAGNIZATION_CODE", company.get("ORAGNIZATION_CODE"));
		companyInfo.put("POST", company.get("ZIP"));
		if (count > 0) {
			comInfoService.updateCompanyInfo(companyInfo);
		} else {
			comInfoService.addCompanyInfo(companyInfo);
		}
		
		if(result>0){
			flag = true;
			msg = "保存成功！";
		}else{
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","添加"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addCompany(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		Map<String, Object> checkCom = checkCompany(param); 
		if ((Boolean) checkCom.get("CHECK")) {
			flag = false;
			msg = checkCom.get("MESS").toString();
			return new ReplyAjax(flag,msg);
		}
		int result = compservice.addCompany(param);
		if(result>0){
			flag = true;
			msg = "保存成功！";
		}else{
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","厂商设置","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delCompany(){
		Map<String,Object> param = _getParameters();
		int result = compservice.getCompanyProject(param);
		if(result >0 ){
			return new ReplyAjax(false, "厂商存在有关联的业务，无法删除！");
		}
		result = compservice.delCom(param);
//		comInfoService.delCompanyInfo(param);
		
		if(result >0 ){
			return new ReplyAjax(true, "删除成功！");
		}else{
			return new ReplyAjax(false, "删除失败！");
		}
	}
	
	/**
	 * 验证厂商的唯一性
	 * Author: SnowWolf
	 * Time: 2015年3月4日
	 * @param param void
	 */
	public Map<String, Object> checkCompany(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		int i = compservice.getcompanyByNameOrCode(param);
		if (i > 0) {
			result.put("CHECK", true);
			result.put("MESS", "厂商名称或厂商编号不唯一,请重新填写");
		}else {
			result.put("CHECK", false);
		}
		return result;
	}
	
	/**
	 * 获取数据字典参数
	 * Author: SnowWolf
	 * Time: 2015年3月6日
	 * @return Object
	 */
	public Object findDictDataType(){
		Map<String, Object> type = new HashMap<String,Object>();
		//客户类型判断
		String TYPE = "客户类型";
		List<Object> list = (List)new DataDictionaryMemcached().get(TYPE);
		type.put("LIST", list);
		
		//证件类型
		String id_type = "证件类型";
		List<Object>  id_typeL = (List)new DataDictionaryMemcached().get(id_type);
		type.put("ID_TYPEL", id_typeL);
		
		//公司性质
		String com_type = "公司性质";
		List<Object> com_typeL = (List)new DataDictionaryMemcached().get(com_type);
		type.put("COM_TYPEL", com_typeL);
		
		//民族
		List<Object> nationL = (List)new DataDictionaryMemcached().get("民族");
		type.put("NATIONL", nationL);
		
		//本地户籍
		List<Object> register = (List)new DataDictionaryMemcached().get("本地户籍");
		type.put("REGISTER", register);
		
		//纳税资质
		List<Object> seniority = (List)new DataDictionaryMemcached().get("纳税资质");
		type.put("SENIORITY", seniority);
		
		//纳税情况
		List<Object> situation = (List)new DataDictionaryMemcached().get("纳税情况");
		type.put("SITUATION", situation);
		
		//婚姻状况
		List<Object> marital_status = (List)new DataDictionaryMemcached().get("婚姻状况");
		type.put("MARITAL_STATUS", marital_status);
		
		//文化程度
		List<Object> degree_edu = (List)new DataDictionaryMemcached().get("文化程度");
		type.put("DEGREE_EDU", degree_edu);
		
		//从事职业
		List<Object> profession = (List)new DataDictionaryMemcached().get("职务");
		type.put("PROFESSION", profession);
		
		//行业分类
		List<Object> INDUSTRY_FICATION_List = (List)new DataDictionaryMemcached().get("行业类型");
		type.put("INDUSTRY_FICATION_LIST", INDUSTRY_FICATION_List);
		
		//企业规模
		List<Object> SCALE_ENTERPRISE_List = (List)new DataDictionaryMemcached().get("企业规模");
		type.put("SCALE_ENTERPRISE_LIST", SCALE_ENTERPRISE_List);
		
		//兴趣爱好
		List<Object> XQAH_List = (List)new DataDictionaryMemcached().get("兴趣爱好");
		type.put("XQAH_LIST", XQAH_List);
		//性格
		List<Object> XG_List = (List)new DataDictionaryMemcached().get("性格");
		type.put("XG_LIST", XG_List);
		//身体状态
		List<Object> STZT_List = (List)new DataDictionaryMemcached().get("身体状态");
		type.put("STZT_LIST", STZT_List);
		//客户来源
		List<Object> CUST_SOURCE = (List)new DataDictionaryMemcached().get("客户来源");
		type.put("CUST_SOURCE", CUST_SOURCE);
		//经销商放款方式
		List<Object> LENDING_TYPE = (List)new DataDictionaryMemcached().get("经销商放款方式");
		type.put("LENDING_TYPE", LENDING_TYPE);
		
		return type;
	}

}
