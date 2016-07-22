package com.pqsoft.baseScheme.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.base.product.service.ProductService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

/**
 * 金融产品管理设置管理
 * 
 * @author King 2013-8-27
 */
public class BaseSchemeAction extends Action {
	private BaseSchemeService service = new BaseSchemeService();
	private String path = "baseScheme/";
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "金融产品管理设置", "列表" })
	public Reply baseSchemeList() {
		VelocityContext context = new VelocityContext();
		context.put("COMPANY_ID", new CompanyService().getAllCompanySortByName());
		context.put("PRODUCT_ID", new ProductService().getProBig(null));
		return new ReplyHtml(VM.html(path + "baseSchemeManager.vm", context));
	}
	
	/**
	 * 金融产品管理设置 查询列表
	 * @return
	 * @author:King 2013-11-26
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply baseSchemeListPage() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(json);
		return new ReplyAjaxPage(service.baseSchemeManager(param));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "金融产品管理设置", "添加" })
	public Reply toAddBaseScheme() {
		VelocityContext context = new VelocityContext();
		//针对供应商显示的别名
		context.put("ALIASES", new SysDictionaryMemcached().get("政策别名"));
		List<Map<String, Object>> list = service.querySchemeCellFromDictionary();
//		List<Map<String,Object>> CATENA_IDList=null;
//		List<Map<String,Object>> SPlist=null;
//		List<Map<String,Object>> pplist=null;
		for (Map<String, Object> map : list) {
			List lst=new ArrayList();
			String CODE=map.get("CODE")+"";
			String FLAG=map.get("FLAG")+"";
			//厂商
			if("COMPANY_ID".equals(CODE.toUpperCase())){
				lst =service.getAllCompany();
			}else if("SUPPLIER_ID".equals(CODE.toUpperCase())){
				//供应商
				lst =service.doShowSupplierInfo(null);
			}else if("BUSINESS_PLATFORM".equals(CODE.toUpperCase())){
				//业务平台
				lst=service.getFHFA_MANAGER();
			}else if("INDUSTRY_INVOLVED".equals(CODE.toUpperCase())){
				//所属行业
				lst=service.getFHFA_MANAGERSUBINFO(null,null,"行业类型");
			}else if("BUSINESS_TYPE".equals(CODE.toUpperCase())){
				//所属业务
				lst=service.getFHFA_MANAGERYW(null,null,"业务类型");
			}else if("AREA".equals(CODE.toUpperCase())){
				//所属区域
				lst=service.getFhfaArea(null,null,2);
			}else if("SP".equals(CODE.toUpperCase())){
				//SP
				lst=service.querysp("2");
//				SPlist=lst;
			}else if("QUOTA_ID".equals(CODE.toUpperCase())){
				//指标公司
				lst=service.querysp("5");
			}else if("SUPPLIER_GROUP".equals(CODE.toUpperCase())){
				//经销集团
				lst=service.querysp("4");
			}else if("BIG_AREA".equals(CODE.toUpperCase())){
				//所属大区
				lst=service.getFhfaAreaBig(null,null);
			}else if("PRODUCT_ID".equals(CODE.toUpperCase())){
				//租赁物
//				lst =service.getProBig(null);
			}else if("CATENA_ID".equals(CODE.toUpperCase())){
				//租赁物车系
//				lst =service.getProCATENA(null);
			}else if("PRODUCT_TYPE_ID".equals(CODE.toUpperCase())){
				//车型
//				lst =service.getAllProductType(null,null,"");
				lst.add(new HashMap());
			}else if("COOPERATION_AGENCY".equals(CODE.toUpperCase())){
				//联合租赁 合作机构
				lst=service.querysp("6");
			}else if("LEASE_PERIOD".equals(CODE.toUpperCase())){
				//租赁周期
				lst=SysDictionaryMemcached.getList("租赁周期");
			}else{
				lst = new DataDictionaryMemcached().get(FLAG);
			}
			map.put("LIST", lst);
			if (CODE.toUpperCase().contains("_PERCENT")) {
				// 显示计算基数
				map.put("isPercent", true);
			} else if (CODE.toUpperCase().contains("_MONEY")||CODE.toUpperCase().contains("PRICE")) {
				//计入费用类型
				map.put("isMoney", true);
			} 
		}
//		context.put("CATENA_IDList", service.getProCATENA(null,null));
//		context.put("SPlist", SPlist);
//		context.put("pplist", service.getProBig(null,null));
		context.put("DATALIST", list);
		context.put("SCHEME_CODE", service.getSchemeCode());
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		context.put("FYJSJS", new DataDictionaryMemcached().get("费用计算基数"));
		//手续费收取方式
		context.put("SQFS", new SysDictionaryMemcached().get("收取方式"));
		context.put("DSFS", new SysDictionaryMemcached().get("代收方式"));
		context.put("DSFSLX", new SysDictionaryMemcached().get("代收方式"));
		return new ReplyHtml(VM.html(path + "baseSchemeAdd.vm", context));
	}

	/**
	 * 进入商务政策明细查看页面
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "金融产品管理设置", "查看" })
	public Reply toShowBaseScheme() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Map<String,Object>> list=service.doSelectSchemeDetailByName(param);
		context.put("SCHEMEDETAIL", list);
		context.put("FEERATELIST", service.doSelectBaseSchemeFeeRate(param));
		context.put("YEARRATELIST", service.doSelectBaseSchemeYearRate(param));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		context.put("FYJSJS", new DataDictionaryMemcached().get("费用计算基数"));
		//所属行业
		context.put("HY", service.getFHFA_MANAGERSUBINFO(null,null,"行业类型"));
		//所属业务
		context.put("YW",service.getFHFA_MANAGERYW(null,null,"业务类型"));
//		所属区域
//		context.put("QY",service.getFhfaArea(null,null,2));
		//手续费收取方式
		context.put("SQFS", new SysDictionaryMemcached().get("收取方式"));
		context.put("DSFSLX", new SysDictionaryMemcached().get("代收方式"));
		context.put("ZQ", new SysDictionaryMemcached().get("租赁周期"));
		param.putAll(list.get(0));
		context.put("PARAM", param);
		return new ReplyHtml(VM.html(path + "baseSchemeShow.vm", context));
	}

	/**
	 * 进入商务政策修改页面
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "参数配置", "金融产品管理设置", "修改" })
	public Reply toUpdateBaseScheme() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Map<String,Object>> lsts=service.doSelectSchemeDetailForUpdateByName(param);
		String COMPANY_ID=null;
		String PRODUCT_ID=null;
		String CATENA_ID=null;
		String BUSINESS_PLATFORM=null;
		for (Map<String, Object> map : lsts) {
			if("COMPANY_ID".equals(map.get("KEY_NAME_EN"))&&map.get("VALUE_STR")!=null&& !"".equals(map.get("VALUE_STR"))){
					COMPANY_ID=","+map.get("VALUE_STR")+",";
			}else if("PRODUCT_ID".equals(map.get("KEY_NAME_EN"))&&map.get("VALUE_STR")!=null&& !"".equals(map.get("VALUE_STR"))){
				PRODUCT_ID =","+map.get("VALUE_STR")+",";
			}else if("CATENA_ID".equals(map.get("KEY_NAME_EN"))&&map.get("VALUE_STR")!=null&& !"".equals(map.get("VALUE_STR"))){
				CATENA_ID=","+map.get("VALUE_STR")+",";
			}else if("BUSINESS_PLATFORM".equals(map.get("KEY_NAME_EN"))&&map.get("VALUE_STR")!=null&& !"".equals(map.get("VALUE_STR"))){
				BUSINESS_PLATFORM=","+map.get("VALUE_STR")+",";
			}
		}
		context.put("SCHEMEDETAIL", lsts);
		List<Map<String, Object>> list = service.querySchemeCellFromDictionary();
		for (Map<String, Object> map : list) {
			List lst=new ArrayList();
			String CODE=map.get("CODE")+"";
			String FLAG=map.get("FLAG")+"";
			//厂商
			if("COMPANY_ID".equals(CODE.toUpperCase())){
				lst =service.getAllCompany();
			}else if("SUPPLIER_ID".equals(CODE.toUpperCase())){
				//供应商
				Map<String,Object> mm=new HashMap<String, Object>();
				mm.put("COMPANY_ID", COMPANY_ID);
				lst =service.doShowSupplierInfo(mm);
			}else if("PRODUCT_ID".equals(CODE.toUpperCase())){
				//产品
				lst =service.getProBig(COMPANY_ID,null);
			}else if("CATENA_ID".equals(CODE.toUpperCase())&&PRODUCT_ID!=null){
				//租赁物车系
				lst =service.getProCATENA(PRODUCT_ID,null);
			}else if("PRODUCT_TYPE_ID".equals(CODE.toUpperCase())&&CATENA_ID!=null){
				//车型
				lst =service.getAllProductType(COMPANY_ID,CATENA_ID,null);
			}else if("BUSINESS_PLATFORM".equals(CODE.toUpperCase())){
				//业务平台
				lst=service.getFHFA_MANAGER();
			}else if("INDUSTRY_INVOLVED".equals(CODE.toUpperCase())){
				//所属行业
				lst=service.getFHFA_MANAGERSUBINFO(null,null,"行业类型");
			}else if("BUSINESS_TYPE".equals(CODE.toUpperCase())){
				//所属业务
				lst=service.getFHFA_MANAGERYW(null,null,"业务类型");
			}else if("BIG_AREA".equals(CODE.toUpperCase())){
				//所属大区
				lst=service.getFhfaAreaBig(null,null);
			}else if("AREA".equals(CODE.toUpperCase())){
				//所属省份
				lst=service.getFhfaArea(BUSINESS_PLATFORM,null,2);
			}else if("SP".equals(CODE.toUpperCase())){
				//SP
				lst=service.querysp("2");
			}else if("SUPPLIER_GROUP".equals(CODE.toUpperCase())){
				//经销集团
				lst=service.querysp("4");
			}else if("QUOTA_ID".equals(CODE.toUpperCase())){
				//指标公司
				lst=service.querysp("5");
			}else if("COOPERATION_AGENCY".equals(CODE.toUpperCase())){
				//联合租赁 合作机构
				lst=service.querysp("6");
			}else if("LEASE_PERIOD".equals(CODE.toUpperCase())){
				//租赁周期
				lst=SysDictionaryMemcached.getList("租赁周期");				
			}else{
				lst = new DataDictionaryMemcached().get(FLAG);
			}
			map.put("LIST", lst);
			if (CODE.toUpperCase().contains("_PERCENT")) {
				// 显示计算基数
				map.put("isPercent", true);
			} else if (CODE.toUpperCase().contains("_MONEY")||CODE.toUpperCase().contains("PRICE")) {
				//计入费用类型
				map.put("isMoney", true);
			} 
		}
		//针对供应商显示的别名
		context.put("ALIASES", new SysDictionaryMemcached().get("政策别名"));
		context.put("DATALIST", list);
		context.put("feeList", service.doSelectBaseSchemeFeeRate(param));
		context.put("yearRateList", service.doSelectBaseSchemeYearRate(param));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		context.put("FYJSJS", new DataDictionaryMemcached().get("费用计算基数"));
		param.putAll(lsts.get(0));
		//手续费收取方式
		context.put("SQFS", new SysDictionaryMemcached().get("收取方式"));
		context.put("DSFSLX", new SysDictionaryMemcached().get("代收方式"));
		context.put("PARAM", param);
		return new ReplyHtml(VM.html(path + "baseSchemeUpdate.vm", context));
	}

	/**
	 * 校验商务政策名称是否存在
	 * 
	 * @return
	 * @author:King 2013-8-31
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply doCheckBaseSchemeNameIsExist() {
		int i = service.queryBaseSchemeCount(_getParameters());
		if (i > 0) {
			return new ReplyAjax(false, "该政策名称已经存在！");
		} else
			return new ReplyAjax();
	}

	/**
	 * 保存商务政策明细信息
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "保存(政策信息)" })
	public Reply doAddBaseScheme() {
		Map<String, Object> map = _getParameters();
		int i = service.queryBaseSchemeCount(map);
		if (i > 0) {
			return new ReplyAjax(false, "该政策名称已经存在！");
		}
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		for (Object object : jsonArray) {
			JSONObject m = (JSONObject) object;
			m.put("SCHEME_NAME", map.get("SCHEME_NAME"));
			m.put("SCHEME_CODE", map.get("SCHEME_CODE"));
			m.put("ALIASES", map.get("ALIASES"));
			service.doAddBaseScheme(m);
		}
		return new ReplyAjax(true).addOp(new OpLog("金融产品管理设置", "添加", "添加商务政策：" + map.get("SCHEME_NAME")));
	}

	/**
	 * 修改商务政策明细信息
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "修改", "保存(政策信息修改)" })
	public Reply doUpdateBaseScheme() {
		Map<String, Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
		for (Object object : jsonArray) {
			Map<String, Object> m = (Map<String, Object>) object;
			m.put("SCHEME_NAME", map.get("SCHEME_NAME"));
			m.put("ALIASES", map.get("ALIASES"));
			m.put("SCHEME_CODE", map.get("SCHEME_CODE"));
			if(StringUtils.isBlank(m.get("VALUE_STR")))
				m.put("VALUE_STR", "");
			service.doUpdateBaseScheme(m);
		}
		service.doUpdateBaseSchemeFeeRate_base(map);
		service.doUpdateBaseSchemeYearRate_base(map);
		return new ReplyAjax(true).addOp(new OpLog("金融产品管理设置", "修改", "添加商务政策：" + map.get("SCHEME_NAME")));
	}

	/**
	 * 查询商务政策年利率信息
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doSelectBaseSchemeYearRate() {
		List<Map<String, Object>> list = service.doSelectBaseSchemeYearRate(_getParameters());
		log.info(list);
		Page page = new Page();
		page.addDate(JSONArray.fromObject(list), list.size());
		return new ReplyAjaxPage(page);
	}

	/**
	 * 商务政策年利率添加保存
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "保存(利率)" })
	public Reply doAddBaseSchemeYearRate() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doAddBaseSchemeYearRate(map);
		if (i > 0) {
			return new ReplyAjax(i).addOp(new OpLog("金融产品管理设置", "添加", "商务政策ID为" + map.get("SCHEME_ID") + "的年利率信息" + map));
		} else
			return new ReplyAjax(false, "添加失败");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "删除(利率)" })
	public Reply doDelBaseSchemeYearRate() {
		Map<String, Object> map = _getParameters();
		int i = service.doDelBaseSchemeYearRate(map);
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("金融产品管理设置", "利率删除", "商务政策ID为" + map.get("SCHEME_ID") + "的年利率信息" + map));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 添加商务政策手续费率
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "保存(手续费率)" })
	public Reply doAddBaseSchemeFeeRate() {
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doAddBaseSchemeFeeRate(map);
		if (i > 0) {
			return new ReplyAjax(i).addOp(new OpLog("金融产品管理设置", "添加", "商务政策ID为" + map.get("SCHEME_ID") + "的手续费率信息" + map));
		} else
			return new ReplyAjax(false, "添加失败");
	}

	/**
	 * 删除商务政策手续费率
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "删除(手续费率)" })
	public Reply doDelBaseSchemeFeeRate() {
		Map<String, Object> map = _getParameters();
		int i = service.doDelBaseSchemeFeeRate(map);
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("金融产品管理设置", "手续费率删除", "商务政策ID为" + map.get("SCHEME_ID") + "的手续费率信息" + map));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 查看商务政策手续费率
	 * 
	 * @return
	 * @author:King 2013-8-30
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply doShowBaseSchemeFeeRate() {
		List<Map<String, Object>> list = service.doSelectBaseSchemeFeeRate(_getParameters());
		Page page = new Page();
		page.addDate(JSONArray.fromObject(list), list.size());
		return new ReplyAjaxPage(page);
	}

	/**
	 * 删除商务政策数据
	 * 
	 * @return
	 * @author:King 2013-9-2
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "参数配置", "金融产品管理设置", "删除" })
	public Reply doDelBaseScheme() {
		Map<String,Object> map=_getParameters();
		int i = service.doDelBaseScheme(map);
		if (i > 0) {
			service.doDelBaseSchemeFeeRate(map);
			service.doDelBaseSchemeYearRate(map);
			return new ReplyAjax().addOp(new OpLog("金融产品管理设置", "删除", "删除商务政策" + _getParameters().get("SCHEME_NAME")));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 修改商务政策年利率
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "保存(修改年利率)" })
	public Reply doUpdateBaseSchemeYearRate() {
		int i = service.doUpdateBaseSchemeYearRate(_getParameters());
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("金融产品管理设置", "修改年利率保存", _getParameters() + ""));
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}

	/**
	 * 修改商务政策手续费率
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "添加", "保存(修改手续费率)" })
	public Reply doUpdateBaseSchemeFeeRate() {
		Map<String,Object> param=_getParameters();
		List<Map<String,Object>> list=service.doSelectBaseSchemeFeeRate(param);
//		Map<String,Object> map=new HashMap<String,Object>();
//		if(list.size()>0)
//			map=list.get(0);
//		else
//			map=param;
		int i = service.doUpdateBaseSchemeFeeRate(param);
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("金融产品管理设置", "修改手续费率保存", _getParameters() + ""));
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}

	/**
	 * 同步商务政策元素
	 * 
	 * @return
	 * @author:King 2013-9-7
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "参数配置", "金融产品管理设置", "同步新商务政策元素" })
	public Reply updateNewSchemeElement() {
		int i = service.updateNewSchemeElement();
		if (i > 0) {
			return new ReplyAjax(true, "同步成功" + i + "条数据").addOp(new OpLog("金融产品管理设置", "同步", "同步商务政策元素"));
		} else
			return new ReplyAjax(false, "无同步元素产生");
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", name = "qijianglong", email = "qijl@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "测算" })
	public Reply schemeMeasure()
	{
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// 商务政策
		if (param.get("SCHEME_CODE") != null) {
			List<Map<String, Object>> SCHEMEDETAIL =service .doSelectSchemeDetailByName(param.get("SCHEME_CODE") + "", "0", "1");
			context.put("SCHEMEDETAIL", SCHEMEDETAIL);
		}
		context.put("param", param);
		//数据字典工具
		context.put("dicTag", Util.DICTAG);
		context.put("zllist", SysDictionaryMemcached.getList("租赁周期"));
		context.put("sqfslist", SysDictionaryMemcached.getList("收取方式"));
		if(param.get("SCHEME_CODE")!=null)
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "schemeMeasure.vm", context)).addOp(new OpLog("金融产品管理设置", "商务政策测算", "商务政策测算"));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", name = "qijianglong", email = "qijl@pqsoft.cn")
	public Reply queryYearRateBySchem()
	{
		BaseSchemeService baseSchService=new BaseSchemeService();
		Map<String, Object> param = _getParameters();
		Map<String,Object> map=baseSchService.queryYearRateBySchem(param);
		if(StringUtils.isBlank(map)||!map.containsKey("YEAR_RATE"))
			return new ReplyAjax(false);
		else
			return new ReplyAjax(JSONArray.fromObject(map)).addOp(new OpLog("金融产品管理设置", "商务政策测算", "计算年利率"));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", name = "qijianglong", email = "qijl@pqsoft.cn")
	public Reply queryFeeBySchem()
	{
		BaseSchemeService baseSchService=new BaseSchemeService();
		Map<String, Object> param = _getParameters();
		Map map=baseSchService.queryFeeBySchem(param);
		return new ReplyAjax(JSONArray.fromObject(map)).addOp(new OpLog("金融产品管理设置", "商务政策测算", "计算手续费"));
	}
	
	/**
	 * 根据厂商查询供应商
	 * @return
	 * @author:King 2013-12-1
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply getSuppliers(){
		return new ReplyJson(JSONArray.fromObject(service.getSuppliers(_getParameters())));
	}
	
	/**
	 * 筛选平台下的行业、业务、厂商信息
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getHY(){
		Map<String,Object> param=_getParameters();
		//所属行业
		String FMA_ID= param.get("FMA_ID")+"";
		String FMA_NAME=param.get("FMA_NAME")+"";
		if(StringUtils.isBlank(FMA_ID)){
			FMA_ID=null;
		}
		if(StringUtils.isBlank(FMA_NAME)){
			FMA_NAME=null;
		}
		List<Map<String,Object>> lst=service.getFHFA_MANAGERSUBINFO(FMA_ID,FMA_NAME,param.get("_TYPE")+"");
		return new ReplyJson(JSONArray.fromObject(lst));
	}
	/**
	 * 筛选平台下的行业、业务、厂商信息
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getFHFA_MANAGERYW(){
		Map<String,Object> param=_getParameters();
		//所属行业
		String FMA_ID= param.get("FMA_ID")+"";
		String FMA_NAME=param.get("FMA_NAME")+"";
		if(StringUtils.isBlank(FMA_ID)){
			FMA_ID=null;
		}
		if(StringUtils.isBlank(FMA_NAME)){
			FMA_NAME=null;
		}
		List<Map<String,Object>> lst=service.getFHFA_MANAGERYW(FMA_ID,FMA_NAME,param.get("_TYPE")+"");
		return new ReplyJson(JSONArray.fromObject(lst));
	}
	
	/**
	 * 筛选平台下的区域
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getArea(){
		Map<String,Object> param=_getParameters();
		String FMA_ID= param.get("FMA_ID")+"";
		String FMA_NAME=param.get("FMA_NAME")+"";
		if(StringUtils.isBlank(FMA_ID)){
			FMA_ID=null;
		}
		if(StringUtils.isBlank(FMA_NAME)){
			FMA_NAME=null;
		}
		//所属区域
		System.out.println("================"+FMA_ID);
		List<Object> lst=service.getFhfaArea(FMA_NAME,null,2);
		return new ReplyJson(JSONArray.fromObject(lst));
	}
	/**
	 * 厂商
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getCompany(){
		Map<String,Object> param=_getParameters();
		String FMA_ID= param.get("FMA_ID")+"";
		String FMA_NAME=param.get("FMA_NAME")+"";
		if(StringUtils.isBlank(FMA_ID)){
			FMA_ID=null;
		}
		if(StringUtils.isBlank(FMA_NAME)){
			FMA_NAME=null;
		}
		//厂商
		List<Map<String,Object>> lst=service.getAllCompany(FMA_ID,FMA_NAME);
		return new ReplyJson(JSONArray.fromObject(lst));
	}
	
	/**
	 * 根据厂商查询供应商
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply doShowSupplierInfo(){
		Map<String,Object> param=_getParameters();
		List<Map<String,Object>> list=service.doShowSupplierInfo(param);
		return new ReplyJson(JSONArray.fromObject(list));
	}
	
	/**
	 * 根据厂商选产品
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getProBig(){
		Map<String,Object> param=_getParameters();
		String COMPANY_ID=param.get("COMPANY_ID")+"";
		if(StringUtils.isBlank(COMPANY_ID)||StringUtils.isBlank(COMPANY_ID.replace(",", "")))
			COMPANY_ID=null;
		String PRODUCT_NAME=param.get("PRODUCT_NAME")+"";
		if(StringUtils.isBlank(PRODUCT_NAME))
			PRODUCT_NAME=null;
		List<Object> list=service.getProBig(COMPANY_ID,PRODUCT_NAME);
		return new ReplyJson(JSONArray.fromObject(list));
	} 
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getProBig1(){
		Map<String,Object> param=_getParameters();
		System.out.println(param);
		String COMPANY_ID=param.get("COMPANY_ID")+"";
		System.out.println("COMPANY_ID="+COMPANY_ID);
		if(StringUtils.isBlank(COMPANY_ID)||StringUtils.isBlank(COMPANY_ID.replace(",", "")))
			COMPANY_ID=null;
		String PRODUCT_NAME=param.get("PRODUCT_NAME")+"";
		if(StringUtils.isBlank(PRODUCT_NAME))
			PRODUCT_NAME=null;
		List<Object> list=service.getProBig(COMPANY_ID,PRODUCT_NAME);
		return new ReplyAjax(list);
	} 
	/**
	 * 根据租赁物选择车系
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getProCATENA(){
		Map<String,Object> param=_getParameters();
		String PRODUCT_ID=param.get("PRODUCT_ID")+"";
		String CATENA_NAME=param.get("CATENA_NAME")+"";
		if(StringUtils.isBlank(PRODUCT_ID)||StringUtils.isBlank(PRODUCT_ID.replace(",", "")))
			PRODUCT_ID=null;
		if(StringUtils.isBlank(CATENA_NAME))
			CATENA_NAME=null;
		
		List<Object> list=service.getProCATENA(PRODUCT_ID,CATENA_NAME);
		return new ReplyJson(JSONArray.fromObject(list));
	} 
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getProCATENA1(){
		Map<String,Object> param=_getParameters();
		String PRODUCT_ID=param.get("PRODUCT_ID")+"";
		String CATENA_NAME=param.get("CATENA_NAME")+"";
		if(StringUtils.isBlank(PRODUCT_ID)||StringUtils.isBlank(PRODUCT_ID.replace(",", "")))
			PRODUCT_ID=null;
		if(StringUtils.isBlank(CATENA_NAME))
			CATENA_NAME=null;
		
		List<Object> list=service.getProCATENA(PRODUCT_ID,CATENA_NAME);
		return new ReplyAjax(list);
	} 
	/**
	 * 根据车系选择车型
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getAllProductType(){
		Map<String,Object> param=_getParameters();
		String CATENA_ID=param.get("CATENA_ID")+"";
		String COMPANY_ID=param.get("COMPANY_ID")+"";
		if(StringUtils.isBlank(CATENA_ID)||",".equals(CATENA_ID))
			CATENA_ID=null;
		if(StringUtils.isBlank(COMPANY_ID)||",".equals(COMPANY_ID))
			COMPANY_ID=null;
		List<Object> list=service.getAllProductType(COMPANY_ID,CATENA_ID,null);
		return new ReplyJson(JSONArray.fromObject(list));
	} 
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getAllProductType1(){
		Map<String,Object> param=_getParameters();
		String CATENA_ID=param.get("CATENA_ID")+"";
		String COMPANY_ID=param.get("COMPANY_ID")+"";
		String CXX_NAME=param.get("CXX_NAME")+"";
		if(StringUtils.isBlank(CATENA_ID)||",".equals(CATENA_ID))
			CATENA_ID=null;
		if(StringUtils.isBlank(COMPANY_ID)||",".equals(COMPANY_ID))
			COMPANY_ID=null;
		if(StringUtils.isBlank(CXX_NAME)||",".equals(CXX_NAME))
			CXX_NAME=null;
		List<Object> list=service.getAllProductType(COMPANY_ID,CATENA_ID,CXX_NAME);
		return new ReplyAjax(list);
	} 
	/**
	 * 根据车系选择车型
	 * @return
	 * @author:King 2014-3-28
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	public Reply getFhfaAreaBig(){
		Map<String,Object> param=_getParameters();
		String FMA_NAME=param.get("FMA_NAME")+"";
		if(StringUtils.isBlank(FMA_NAME))
			FMA_NAME=null;
		List<Map<String,Object>> list=service.getFhfaAreaBig(null,FMA_NAME);
		return new ReplyJson(JSONArray.fromObject(list));
	} 
	
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
	@aPermission(name = { "参数配置", "金融产品管理设置", "复制" })
	public Reply copyScheme(){
		Map<String,Object> param=_getParameters();
		Map<String,Object> cm=new HashMap<String, Object>();
		cm.put("SCHEME_NAME", param.get("NEW_SCHEME_NAME"));
		cm.put("ALIASES", param.get("OLD_ALIASES"));
		int i = service.queryBaseSchemeCount(cm);
		if (i >0) {
			return new ReplyAjax(false, "该政策名称已经存在！");
		}
		try{
		param.put("CREATE_CODE", Security.getUser().getCode());
		}catch(Exception e){
			param.put("CREATE_CODE", "god");
		}
		param.put("NEW_SCHEME_CODE", service.getSchemeCode());
		int m=service.doCopyScheme(param);
		if(m>=1)
			return new ReplyAjax(true,"复制成功!").addOp(new OpLog("金融产品管理设置", "复制", "原产品名称[类别]:"+param.get("OLD_SCHEME_NAME")+"["+param.get("OLD_ALIASES")+"]复制为:"+param.get("NEW_SCHEME_NAME")));
		else{
			return new ReplyAjax(false,"复制失败,请联系管理员");
			
		}
	}
	
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",name="King",email="jinfu@pqsoft.cn")
//	@aPermission(name = { "参数配置", "金融产品管理设置", "筛选sp" })
	public Reply querysp(){
		List<Map<String,Object>> list=service.querysp(_getParameters().get("SUP_TYPE")+"",_getParameters().get("SUP_NAME"));
		return new ReplyAjax(list);
	}
}
