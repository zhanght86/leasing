//package com.pqsoft.insure.action;
//
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.organization.service.ManageService;
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.insure.service.InsureTypeManagementService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Dao;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.JsonUtil;
//import com.pqsoft.util.ReplyExcel;
//
//public class InsureAction extends Action {
//
//	private VelocityContext context = new VelocityContext();
//	InsureService service = new InsureService();
//	private InsureTypeManagementService insureTypeService = new InsureTypeManagementService();
//	/**
//	 * 保险管理
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply execute() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/insureList.vm", context));
//	}
//
//	/**
//	 * 保险管理
//	 */
//	@aPermission(name = { "保险管理", "新建保单", "列表显示" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
//	public Reply execute1() {
////		Map<String, Object> param = _getParameters();
////		List<Map<String,Object>> list = service.getInsuranceAll(param);
////		context.put("param", param);
////		context.put("data", list);
//		return new ReplyHtml(VM.html("insure/insuranceManageNew.vm", context));
//	}
//	
//	@aPermission(name = { "保险管理", "新建保单", "列表显示" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply getinsureList() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.getInsurance(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 保险管理预约首页
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理申请数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureListing() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.queryPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 保险管理预约导出excel
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理导出excel" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply exportExcel(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		return new ReplyExcel(service.exportData(param),"Insure"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+".xls");
//	}
//	/**
//	 * 保险管理预约审核首页
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理审核数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureCheckListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "申请");
//		Page page = service.insureCheckPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 保险管理预约审核导出excel
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理审核导出excel" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureCheckExportExcel(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		return new ReplyExcel(service.insureCheckExportData(param),"Insure"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+".xls");
//	}
//	/**
//	 * 公共的保险导出，根据保险id查询出来的导出数据
//	 */
//	@aPermission(name = { "保险管理","公共的保险导出" })
//	@aAuth(type = aAuthType.ALL)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureExportExcel(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		Reply reply = new ReplyExcel(service.insureExportData(param),"Insure"+DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-","")+".xls");
//		if("true".equals(param.get("flag_"))){
//			service.updateStatus(param);
//		}
//		return reply ;
//	}
//	/**
//	 * 保险管理预约审核(审核或者驳回)
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理审核(审核或者驳回)" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureCheckApproval(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		service.insureCheckApproval(param);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * 保险预约确认
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约确认首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureAffirm() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/insureAffirm.vm", context));
//	}
//	/**
//	 * 保险管理预约确认首页
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理确认数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureAffirmListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "预约");
//		Page page = service.insureCheckPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 保险管理预约确认导入excel
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约确认导入excel" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply importEXCEL() {
//		List<FileItem> fileList = _getFileItem();
//		service.importEXCEL(fileList.get(0));
//		return this.insureAffirm();
//	}
//	/**
//	 * 保险管理预约审核(审核)
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险预约管理审核(审核)" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureAffirmApproval(){
//		Map<String, Object> param = _getParameters();
//		JSONArray jsonArray = JSONArray.fromObject(param.get("jsonData"));
//		service.insureAffirm2(jsonArray);
//		//service.insureCheckApproval(param);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * DB保险预约确认
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "DB保险预约确认首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureDbAffirm() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/insureDbAffirm.vm", context));
//	}
//	/**
//	 * DB保险管理预约确认首页
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "DB保险预约管理确认数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureDbAffirmListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "预约");
//		param.put("OPERATION_TYPE", "yes");
//		// 数据权限 判断供应商登录只能看到当前供应商数据  生产商看到下属供应商数据
//		ManageService mgService = new ManageService();
//		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
//		if(supMap != null && supMap.get("SUP_ID") != null){
//			param.put("SUP_TYPE", supMap.get("SUP_ID"));
//		}
//		Page page = service.insureCheckPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * DB保险管理预约确认首页
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "DB保险预约管理确认数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply refreshInsureDbAffirmListing() {
//		Map<String, Object> param = _getParameters();
//		Dao.insert("Insure.insert_insur_info2",param);
//		Dao.update("Insure.update_WHETHER_CREATE_INSURE3",param);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * DB保险管理预约确认(审核)
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "DB保险管理预约确认(审核)" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureDbAffirmApproval(){
//		Map<String, Object> param = _getParameters();
//		JSONArray jsonArray = JSONArray.fromObject(param.get("jsonData"));
//		service.insureDbAffirmApproval(jsonArray);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * 续保提醒
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalRemind() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/renewalRemind.vm", context));
//	}
//	/**
//	 * 续保提醒首页数据
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保提醒首页数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalRemindListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "预约");
//		param.put("OPERATION_TYPE", "yes");
//		Page page = service.renewalRemindListing(param);
//		return new ReplyAjaxPage(page);
//	}
//
//	/**
//	 * 调用此方法可以修改状态修改状态
//	 * param.put("status_s", "预约"); param.put("status_e", "申请");
//	 * 可以批量修改（PRO_CODES为空），单个修改（PRO_CODES为修改的设备id）
//	 */
//	@aPermission(name = { "保险管理", "修改状态"})
//	@aAuth(type = aAuthType.ALL)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply updateStatus(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		service.updateStatus(param);
//		return new ReplyAjax("操作成功");
//	}
//	/**
//	 * 调用此方法可以修改状态修改状态
//	 */
//	@aPermission(name = { "保险管理", "修改状态"})
//	@aAuth(type = aAuthType.ALL)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply updateStatus_renewal(){
//		Map<String, Object> param = _getParameters();
//		String [] PRO_CODES = (param.get("PRO_CODES")+"").split(",");
//		//如果点了全选数据，则查询全部内容，即无查询条件
//		String selectAll = param.get("selectAll")+"";
//		if((param.get("PRO_CODES")+"")!=null&&!"".equals(param.get("PRO_CODES")+"")&&!"on".equals(selectAll)){
//			param.put("PRO_CODES", PRO_CODES);
//		}else {
//			param.put("PRO_CODES", "");
//		}
//		service.updateStatus_renewal(param);
//		return new ReplyAjax("操作成功");
//	}
//	
//
//	/**
//	 * 续保录入
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保录入首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalEntering() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/renewalEntering.vm", context));
//	}
//	/**
//	 * 续保录入首页数据
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保录入首页数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalEnteringListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "待录保单信息");
//		param.put("OPERATION_TYPE", "yes");
//		Page page = service.renewalEnteringListing(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 保险查询
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险查询首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureShow() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/insureShow.vm", context));
//	}
//	/**
//	 * 保险查询
//	 */
//	@aPermission(name = { "保险管理", "保险预约管理", "保险查询数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply insureShowListing() {
//		Map<String, Object> param = _getParameters();
//		param.put("status", "起保");
//		Page page = service.insureCheckPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	/**
//	 * 续保确认首页
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保确认首页" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalAffirm() {
//		Map<String, Object> param = _getParameters();
//		context.put("type_", param.get("type_"));
//		Map map = service.queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/renewalAffirm.vm", context));
//	}
//	/**
//	 * 续保确认数据
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "续保确认数据" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply renewalAffirmListing() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.renewalAffirmListing(param);
//		return new ReplyAjaxPage(page);
//	}
//
//	/**
//	 * 维护续保单
//	 */
//	@aPermission(name = { "保险管理", "保险监控", "维护续保单" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "hanjian", email = "hanjian@pqsoft.cn", name = "韩建")
//	public Reply informationEntry() {
//		List<FileItem> fileList = _getFileItem();
//		Map<String, Object> param = _getParameters();
//		param.put("file", fileList.get(0));
//		try {
//			service.informationEntry(param);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ReplyAjax("操作失败");
//		}
//		return new ReplyAjax("操作成功");
//	}
//	
//	@aPermission(name = { "保险管理", "新建保单", "停止新建" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply stopNewPolicy() {
//		Map<String,Object> param = _getParameters();
//		int i=service.nowclose(param);
//		if(i==1)
//		{
//			return new ReplyAjax(true,"");
//		}else
//		{
//			return new ReplyAjax(false,"");
//		}
//	}
//	public Reply toaddNewPolicy() {
//		Map<String, Object> param = _getParameters();
//		// 设备信息
//		Map<String,Object> eqmtType = service.geteq(param);
//		context.put("eqmtType", eqmtType);
//		//承租人信息
//		 context.put("Natural", service.queryNaturaType(param));
//	   //融资公司信息
//		 param.put("FA_NAME", Security.getUser().getOrg().getPlatform());
//		context.put("fhMap", service.getOfficial(param));
//		// 查出所有的险种
//		//context.put("InsuType", insureTypeService.getAll());
//		// 查出所有的保险公司
//		context.put("insuCompany", service.getCompanyAll());
//		return new ReplyHtml(VM.html("insure/insureCreate.vm", context));
//	}
//}
