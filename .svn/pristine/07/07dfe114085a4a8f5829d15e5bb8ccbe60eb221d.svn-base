package com.pqsoft.credit.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.credit.service.CreditGuaraServices;
import com.pqsoft.credit.service.CreditServices;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.StringUtils;

public class CreditAction extends Action{

	private Map<String,Object> m = null;
	private final String pagePath = "credit/";
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * getPageParameter 获取页面参数
	 * @date 2014-3-28 上午08:25:35
	 * @author 吴国伟
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.ALL)
	private Map<String,Object> getPageParameter(){
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();			
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME",Security.getUser().getName());
		m.put("USERCODE",Security.getUser().getCode());
		m.put("USERID",Security.getUser().getId());
		return m;
	} 
	
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"项目管理", "资信报告管理", "列表" })
	public Reply queryCreditManagerPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditGuaraServices service = new CreditGuaraServices();
		context.put("param", m);
		context.put("ywlx", service.getSysDicData("业务类型"));
		context.put("lxzt", service.getSysDicData("项目状态位"));
		return new ReplyHtml(VM.html(pagePath+"Credit_manager.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findCreditData() {
		m = getPageParameter();
		CreditServices service = new CreditServices();
		return new ReplyAjaxPage(service.queryCreditManager(m));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"项目管理", "资信报告管理", "查看" })
	public Reply queryCreditPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		projectService service = new projectService();
		System.out.println(m.toString());
		//=================================================================
		// 项目信息
				Map projectMap = service.queryProjectById(m);
				m.putAll(projectMap);
				// 设备信息
				List eqlist = service.queryEqByProjectID(m);

				// 担保人信息
				List GuaranList = service.queryGuaranByProjectID(m);
				
				//设备分组信息 
				List rowlist = service.queryEqCountByProjectID(m);
				context.put("rowList", rowlist);

				
				
				// 扣款类型
				String PROJECT_MODEL = m.get("PROJECT_MODEL") + "";
				List final_Type_List = new ArrayList();
				Map finalMap2 = new HashMap();
				finalMap2.put("FINAL_TYPE", null);
				finalMap2.put("FINAL_NAME", "");
				final_Type_List.add(finalMap2);

				Map finalMap = new HashMap();
				finalMap.put("FINAL_TYPE", "0");
				finalMap.put("FINAL_NAME", "自有客户");
				final_Type_List.add(finalMap);

				Map finalMap1 = new HashMap();
				finalMap1.put("FINAL_TYPE", "1");

				finalMap1.put("FINAL_NAME", "终端客户");
				final_Type_List.add(finalMap1);
				context.put("final_Type_List", final_Type_List);

				// 发票开具类型
				List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
				context.put("invoice_target_type", invoice_target_type);
				context.put("eqList", eqlist);
				context.put("GuaranList", GuaranList);
				context.put("FORMAT", UTIL.FORMAT);
				
				context.put("modelList", new SysDictionaryMemcached().get("业务类型"));

		//==================================================================
		context.put("param", m);
		context.put("PROJECT_ID", m.get("PROJECT_ID"));
		context.put("GUTYPE", null==m.get("GUTYPE")?"租赁方案":m.get("GUTYPE").toString());
		context.put("Bus_Type",service.QueryBusType(m.get("PROJECT_ID").toString()).get("PLATFORM_TYPE").toString());
		context.put("score",service.Fen(m.get("PROJECT_ID").toString()) );
		
		return new ReplyHtml(VM.html(pagePath+"Credit_view.vm",context));
	}
	
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"项目管理", "资信报告管理", "修改" })
	public Reply updateCreditPage(){
		VelocityContext context=new VelocityContext();
		Map<String, Object> map = _getParameters();
		projectService service = new projectService();
		// 项目信息
		Map projectMap = service.queryProjectById(map);
		map.putAll(projectMap);
		
		if("8".equals(projectMap.get("PLATFORM_TYPE")+""))
		{
			List LHSQFSLIST=new DataDictionaryService().queryDataDictionaryByScheme("联合收取方式");
			context.put("LHSQFSLIST", LHSQFSLIST);
			//查询联合租赁信息
			List list=service.getFLByProjectId(map);
			context.put("FL_LIST", list);
			
			context.put("FLINFO_LIST", service.getFLInfo());
		}
		// 设备分组信息
		List rowlist = service.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);
		// 单位
		List unitList = (List) new DataDictionaryMemcached().get("设备单位");
		context.put("unitList", unitList);
		// 开户行
		if (map.get("CUST_ID1") != null) {
			List bank = service.queryBank(map.get("CUST_ID1") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (map.get("CUST_ID") != null) {
			List bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		}

		// 承租人信息
		if (map.get("CUST_ID") != null) {
			map.put("CLIENT_ID", map.get("CUST_ID"));
			map.putAll(service.selectRenterDetails(map));
		}

		// 担保人信息
		List GuaranList = service.queryGuaranByProjectID(map);

		// 担保人类型
		List typeList = new DataDictionaryMemcached().get("客户类型");
		context.put("typeList", typeList);
		List CollateralList = new DataDictionaryMemcached().get("抵押物");
		context.put("CollateralList", CollateralList);

		// 设备信息（商务政策）
		List eqlist = service.queryEqByProjectID(map);

		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);

		context.put("param", map);
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		Map<String, Object> dmap = new HashMap<String, Object>();
		dmap.put("TYPE", "业务类型");
		dmap.put("CODE", PLATFORM_TYPE);
		if ("5".equals(PLATFORM_TYPE)) {
			context.put("BIGPROJCOUNT", service.checkBigProjReportExist(map.get("PROJECT_ID") + ""));
		}
		Map<String, Object> mm = DataDictionaryService.queryDataDictionaryByTypeAndCode(dmap);
		String SHORTNAME = mm.get("SHORTNAME") + "";
		if (SHORTNAME.equals("0"))// 扣款账户为客户
		{
			// 扣款类型
			List final_Type_List = new ArrayList();
			Map finalMap = new HashMap();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 客户名称
			List final_Cust_List = new ArrayList();
			Map custMap = new HashMap();
			custMap.put("CUST_ID", map.get("CLIENT_ID"));
			custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
			final_Cust_List.add(custMap);
			context.put("final_Cust_List", final_Cust_List);

			// 开户行
			List bank = new ArrayList();
			if (StringUtils.isBlank(map.get("CUST_ID"))) bank = service.queryBank(map.get("CUST_ID1") + "");
			else bank = service.queryBank(map.get("CUST_ID") + "");
			if (bank.size() > 0) {
				context.put("bankList", bank);
			}
		} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
		{
			// 扣款类型
			List final_Type_List = new ArrayList();
			Map finalMap = new HashMap();
			finalMap.put("FINAL_TYPE", "1");
			// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
			// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
			// finalMap.put("FINAL_NAME", "担保人");
			// else
			finalMap.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap);
			context.put("final_Type_List", final_Type_List);

			// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
			List final_Cust_List = new ArrayList();
			Map custMap = new HashMap();
			custMap.put("CUST_ID", null);
			custMap.put("CUST_NAME", "--请选择--");
			final_Cust_List.add(custMap);

			final_Cust_List.addAll(service.doSuppByClent(map.get("CLIENT_ID").toString()));
			context.put("final_Cust_List", final_Cust_List);
		} else// 扣款账户为选择
		{
			// 扣款类型
			List final_Type_List = new ArrayList();
			Map finalMap2 = new HashMap();
			finalMap2.put("FINAL_TYPE", null);
			finalMap2.put("FINAL_NAME", "--请选择--");
			final_Type_List.add(finalMap2);

			Map finalMap = new HashMap();
			finalMap.put("FINAL_TYPE", "0");
			finalMap.put("FINAL_NAME", "自有客户");
			final_Type_List.add(finalMap);

			Map finalMap1 = new HashMap();
			finalMap1.put("FINAL_TYPE", "1");
			// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
			// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
			// finalMap1.put("FINAL_NAME", "担保人");
			// else
			finalMap1.put("FINAL_NAME", "终端客户");
			final_Type_List.add(finalMap1);
			context.put("final_Type_List", final_Type_List);

		}

		// 发票开具类型
		List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
		context.put("invoice_target_type", invoice_target_type);
		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));
		context.put("FORMAT", UTIL.FORMAT);
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("GUTYPE", null==map.get("GUTYPE")?"租赁方案":map.get("GUTYPE").toString());
		context.put("Bus_Type",service.QueryBusType(map.get("PROJECT_ID").toString()).get("PLATFORM_TYPE").toString());
		context.put("score",service.Fen(map.get("PROJECT_ID").toString()) );
		
		return new ReplyHtml(VM.html(pagePath+"Credit_update.vm",context));
	}
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资信平台管理", "资信平台管理", "查看客户信息" })
	public Reply queryCreditCustPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		context.put("param", m);
		return new ReplyHtml(VM.html(pagePath+"Credit_cust_view.vm",context));
	}
	
	/*==========流程============*/
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	@aAuth(type = aAuthType.LOGIN)
	public Reply goUpdateCreditPage(){
		this.getPageParameter();
		VelocityContext context=new VelocityContext();
		CreditServices creditService = new CreditServices();
		projectService service = new projectService();
		Map<String,Object> map = creditService.getCreditInfo(m);
		m.put("CLIENT_ID",map.get("CLIENT_ID"));
		//=================================================================
		// 项目信息
				Map projectMap = service.queryProjectById(m);
				m.putAll(projectMap);
				map.putAll(projectMap);
				
				if("8".equals(projectMap.get("PLATFORM_TYPE")+""))
				{
					List LHSQFSLIST=new DataDictionaryService().queryDataDictionaryByScheme("联合收取方式");
					context.put("LHSQFSLIST", LHSQFSLIST);
					//查询联合租赁信息
					List list=service.getFLByProjectId(m);
					context.put("FL_LIST", list);
					
					context.put("FLINFO_LIST", service.getFLInfo());
				}
				// 设备信息
				List eqlist = service.queryEqByProjectID(m);

				// 担保人信息
				List GuaranList = service.queryGuaranByProjectID(m);
				
				//设备分组信息 
				List rowlist = service.queryEqCountByProjectID(m);
				context.put("rowList", rowlist);

				
				
				String PROJECT_MODEL = map.get("PLATFORM_TYPE") + "";
				Map<String, Object> dmap = new HashMap<String, Object>();
				dmap.put("TYPE", "业务类型");
				dmap.put("CODE", PROJECT_MODEL);
				if ("5".equals(PROJECT_MODEL)) {
					context.put("BIGPROJCOUNT", service.checkBigProjReportExist(map.get("PROJECT_ID") + ""));
				}
				Map<String, Object> mm = DataDictionaryService.queryDataDictionaryByTypeAndCode(dmap);
				context.put("account_type", new DataDictionaryMemcached().get("开户行账号类型"));
				String SHORTNAME = mm.get("SHORTNAME") + "";
				if (SHORTNAME.equals("0"))// 扣款账户为客户
				{
					// 扣款类型
					List final_Type_List = new ArrayList();
					Map finalMap = new HashMap();
					finalMap.put("FINAL_TYPE", "0");
					finalMap.put("FINAL_NAME", "自有客户");
					final_Type_List.add(finalMap);
					context.put("final_Type_List", final_Type_List);

					// 客户名称
					List final_Cust_List = new ArrayList();
					Map custMap = new HashMap();
					custMap.put("CUST_ID", map.get("CLIENT_ID"));
					custMap.put("CUST_NAME", map.get("CUSTOMER_NAME"));
					final_Cust_List.add(custMap);
					context.put("final_Cust_List", final_Cust_List);

					// 开户行
					List bank = new ArrayList();
					if (StringUtils.isBlank(map.get("CUST_ID"))) bank = service.queryBank(map.get("CUST_ID1") + "");
					else bank = service.queryBank(map.get("CUST_ID") + "");
					if (bank.size() > 0) {
						context.put("bankList", bank);
					}
				} else if (SHORTNAME.equals("1"))// 扣款账户为终端客户
				{
					// 扣款类型
					List final_Type_List = new ArrayList();
					Map finalMap = new HashMap();
					finalMap.put("FINAL_TYPE", "1");
					// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
					// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
					// finalMap.put("FINAL_NAME", "担保人");
					// else
					finalMap.put("FINAL_NAME", "终端客户");
					final_Type_List.add(finalMap);
					context.put("final_Type_List", final_Type_List);

					// 通过该客户ID查询出该供应商在查出该供应商下的所以客户
					List final_Cust_List = new ArrayList();
					Map custMap = new HashMap();
					custMap.put("CUST_ID", null);
					custMap.put("CUST_NAME", "--请选择--");
					final_Cust_List.add(custMap);

					final_Cust_List.addAll(service.doSuppByClent(map.get("CLIENT_ID").toString()));
					context.put("final_Cust_List", final_Cust_List);
				} else// 扣款账户为选择
				{
					// 扣款类型
					List final_Type_List = new ArrayList();
					Map finalMap2 = new HashMap();
					finalMap2.put("FINAL_TYPE", null);
					finalMap2.put("FINAL_NAME", "--请选择--");
					final_Type_List.add(finalMap2);

					Map finalMap = new HashMap();
					finalMap.put("FINAL_TYPE", "0");
					finalMap.put("FINAL_NAME", "自有客户");
					final_Type_List.add(finalMap);

					Map finalMap1 = new HashMap();
					finalMap1.put("FINAL_TYPE", "1");
					// if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") ||
					// PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
					// finalMap1.put("FINAL_NAME", "担保人");
					// else
					finalMap1.put("FINAL_NAME", "终端客户");
					final_Type_List.add(finalMap1);
					context.put("final_Type_List", final_Type_List);

				}

				// 发票开具类型
				List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
				context.put("invoice_target_type", invoice_target_type);
				context.put("eqList", eqlist);
				context.put("GuaranList", GuaranList);
				context.put("FORMAT", UTIL.FORMAT);
				
				context.put("modelList", new SysDictionaryMemcached().get("业务类型"));

		//==================================================================
		context.put("param", m);
		context.put("PROJECT_ID", m.get("PROJECT_ID"));
		context.put("GUTYPE", null==m.get("GUTYPE")?"租赁方案":m.get("GUTYPE").toString());
		context.put("Bus_Type",service.QueryBusType(m.get("PROJECT_ID").toString()).get("PLATFORM_TYPE").toString());
		context.put("score",service.Fen(m.get("PROJECT_ID").toString()) );
		
		return new ReplyHtml(VM.html(pagePath+"Credit_update.vm",context));
	}
	

}
