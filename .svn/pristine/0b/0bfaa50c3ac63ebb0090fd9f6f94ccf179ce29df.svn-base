package com.pqsoft.pigeonhole.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.dossier.service.DossierConfigService;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.StringUtils;

/**
 * 归档管理action
 * 
 * @author King 2013-10-9
 */
public class PigeonholeAction extends Action {
	private PigeonholeService service = new PigeonholeService();
	private String path = "pigeonhole/";

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 进入档案归档申请页面
	 */
	@aAuth(type = aAuthType.LOGIN)
//	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "档案管理", "档案管理列表","归档申请" })
	public Reply toPigeonholeApplyForm() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> baseMap = service.doShowPigeonholeBaseInfo(param);
		baseMap.putAll(param);
		String PROJECT_ID = baseMap.get("PROJECT_ID") + "";
		context.put("BASEINFO", baseMap);
		if (param.containsKey("DOSSIER_TYPE") && "合同档案".equals(param.get("DOSSIER_TYPE"))){
			context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(baseMap.get("TPM_CUSTOMER_TYPE")+"", null, "1"));
//			context.put("FILELIST", service.doShowContractDossierApplyFileList(PROJECT_ID));
		} else {
			param.put("TPM_CUSTOMER_TYPE", baseMap.get("TPM_CUSTOMER_TYPE"));
			context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(baseMap.get("TPM_CUSTOMER_TYPE")+"", null, "0"));
//			context.put("FILELIST", service.doShowCustomerDossierApplyFileList(param));
		}
		return new ReplyHtml(VM.html(path + "pigeonholeApply.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "归档申请", "提交保存" })
	public Reply doAddPigeonholeApply() {
		Map<String, Object> param = _getParameters();
		PigeonholeService pService = new PigeonholeService();
		pService.doDelPigeonholeApplyInfo(param);
		String APPLY_ID = null;
		try {
			APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
		} catch (Exception e) {
			throw new ActionException("归档申请失败");
		}
		List<String> list=JBPM.getDeploymentListByModelName("dossierStore");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("DOSSIER_APPLY_ID", APPLY_ID);
		JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", param.get("PAYLIST_CODE")+"", map);
		return new ReplyAjax(true).addOp(new OpLog("档案管理-归档申请", "提交保存", "归档申请ID为" + APPLY_ID));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "归档申请", "提交保存" })
	public Reply doAddPigeonholeApplyForProject() {
		Map<String, Object> param = _getParameters();
		System.out.println("==================param="+param);
		PigeonholeService pService = new PigeonholeService();
		pService.doDelPigeonholeApplyInfo(param);
		String APPLY_ID = null;
		try {
			APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("DOSSIER_APPLY_ID", APPLY_ID);
			JBPM.setExecutionVariable(param.get("JBPM_ID").toString(), m);
		} catch (Exception e) {
			throw new ActionException("归档申请失败");
		}
		return new ReplyAjax(true).addOp(new OpLog("档案管理-归档申请", "提交保存", "归档申请ID为" + APPLY_ID));
	}
	
	@SuppressWarnings( { "unchecked" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "归档申请", "查看" })
	public Reply toShowDossierApp(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		String DOSSIER_APPLY_ID = param.get("DOSSIER_APPLY_ID") + "";
		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID);
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		if (list.get(0).containsKey("PAYLIST_CODE")) {
			map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
		}
		context.put("BASEINFO", map);
		context.put("FILELIST", list);
		return new ReplyHtml(VM.html(path+"showPigeonholeApply.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "归档申请修改", "加载数据" })
	public Reply toUpdateDossierApp(){
		//TODO King 档案归档修改i页面
		return null;
	}
	
	/**
	 * 进入档案归档上传文件页面
	 */
	@SuppressWarnings( { "unchecked" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案入柜", "表单" })
	public Reply toDossierConfirm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		String DOSSIER_APPLY_ID = param.get("DOSSIER_APPLY_ID") + "";
		if("".equals(DOSSIER_APPLY_ID)){DOSSIER_APPLY_ID="0";}//5-16加的为空的处理
		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID);
		if(null != map){//5-16加的为空的处理吴国伟
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		if(!list.isEmpty()&&list.size()>0){
			if (list.get(0).containsKey("PAYLIST_CODE")) {
				map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
			}
	
			/**
			 * 获取项目或客户对应的最新使用的档案袋和档案柜编号
			 */
			Map<String, Object> portfoliMap = service.doShowClientPortfolioAndCabinet(null == map.get("CLIENT_ID") ? "" : map.get("CLIENT_ID").toString(), null == map.get("PROJECT_CODE") ? "" : map.get("PROJECT_CODE").toString());
			if (portfoliMap == null || portfoliMap.size() < 1 || portfoliMap.isEmpty()) {
				if (StringUtils.isBlank(map.get("PROJECT_CODE")))
					map.put("PORTFOLIO_NUMBER", "CZR-" + DateUtil.getSysDate());
				else
					map.put("PORTFOLIO_NUMBER", "HT-" + map.get("PROJECT_CODE"));
			} else {
				map.put("PORTFOLIO_NUMBER", portfoliMap.get("PORTFOLIO_NUMBER"));
				if (!StringUtils.isBlank(portfoliMap.containsKey("CABINET_NUMBER") ? portfoliMap.get("CABINET_NUMBER") : "")) {
					String CABINET_NUMBERS[] = portfoliMap.get("CABINET_NUMBER").toString().split("-");
					map.put("HEAD", CABINET_NUMBERS[0]);
					map.put("ROW", CABINET_NUMBERS[1]);
					map.put("LINE", CABINET_NUMBERS[2]);
				} else {
					map.put("ROW", "");
					map.put("LINE", "");
				}
			}
		  }
			context.put("CABINETLIST", new DossierConfigService().doShowDossierConfigList(new HashMap()));
			context.put("BASEINFO", map);
			context.put("FILELIST", list);
		}
		return new ReplyHtml(VM.html(path + "dossierStore.vm", context));
	}

	/**
	 * 进入档案归档上传文件页面
	 */
	@SuppressWarnings( { "unchecked" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = {"档案管理", "档案管理列表","归档申请"})
//	@aPermission(name = { "流程任务", "档案入柜", "表单" })
	public Reply toDossierConfirm1() {
		String PROJECT_ID=null;
		Map<String, Object> param =_getParameters();
			param.put("TASK_NAME", "档案上传资料");
			PROJECT_ID = param.get("PROJECT_ID") + "";
			if(StringUtils.isBlank(param.get("PROJECT_ID"))){
				param.putAll(service.doShowClientTypeByClient(param.get("CLIENT_ID")+""));
			}else
				param.putAll(service.doShowClientTypeByProjectId(PROJECT_ID));
			
			// 确认流程匹配模块 
			/*
			 * XXX 查T_SYS_DATA_DICTIONARY ??
			 * FIXME
			 */
			List<Map<String, Object>> list1 = service.doShowDossierFileConfigFromDataDictionary1();
//			List<Map<String, Object>> list1 = service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", null, null);
			/***************************** 准备数据 ********************/
			String CLIENT_ID = param.get("CLIENTID") + "";
			List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			for (Map<String, Object> map : list1) {
				Map<String, Object> rstMap = new HashMap<String, Object>();
				rstMap.put("TPM_CODE", map.get("CODE"));
				rstMap.put("TPM_TYPE", map.get("FILE_NAME"));
				rstMap.put("TPM_CUSTOMER_TYPE", map.get("CLIENT_TYPE_NAME"));
				rstMap.put("PROJECT_ID", PROJECT_ID);
				rstMap.put("FILE_REMARK", map.get("REMARK"));
				rstMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
				rstMap.put("DOSSIERTYPE", "1");
				rstMap.put("DOSSIER_PAGE", map.get("FILE_PAGE"));
				rstMap.put("DOSSIER_COUNT", map.get("FILE_COUNT"));
				rstMap.put("FILE_TYPE", map.get("FILE_TYPE"));
				rstMap.put("CLIENT_ID", CLIENT_ID);
				String REMARK = map.get("REMARK") + "";
				rstMap.put("TPM_BUSINESS_PLATE", REMARK);
				rstList.add(rstMap);
			}
			
			//查询项目资料  --T_DOSSIER_CREDITCOREFILE
			String CLIENT_TYPE=param.get("CLIENT_TYPE")+"";
			List<Map<String, Object>> list2=service.doShowFile(param.get("CLIENT_TYPE") + "");
			for (Map<String, Object> map : list2) {
				Map<String, Object> rstMap = new HashMap<String, Object>();
//				rstMap.put("TPM_CODE", map.get("CODE"));
				rstMap.put("TPM_TYPE", map.get("FILE_NAME"));
				if(CLIENT_TYPE.equals("LP")){
					rstMap.put("TPM_CUSTOMER_TYPE", map.get("法人"));
				}
				else{
					rstMap.put("TPM_CUSTOMER_TYPE", map.get("个人"));
				}
				
				rstMap.put("PROJECT_ID", PROJECT_ID);
				rstMap.put("FILE_REMARK", map.get("TYPE")+"资料");
				rstMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
				rstMap.put("DOSSIERTYPE", "1");
				rstMap.put("DOSSIER_PAGE", "1");
				rstMap.put("DOSSIER_COUNT", map.get("FILECOUNT"));
//				rstMap.put("FILE_TYPE", map.get("FILE_TYPE"));
				rstMap.put("CLIENT_ID", CLIENT_ID);
				rstMap.put("TPM_BUSINESS_PLATE", map.get("TYPE")+"资料");
				rstList.add(rstMap);
			}
			
			mm.put("PROJECT_CODE", param.get("PROJECT_CODE"));
			mm.put("CLIENT_ID", CLIENT_ID);
			mm.put("SEND_TYPE", "1");
			mm.put("FILEINFO", JSONArray.fromObject(rstList) + "");

			/********************* 更新保存数据并做归档处理  FIL_DOSSIER_APPLY****************************/
			PigeonholeService pService = new PigeonholeService();
			pService.doDelPigeonholeApplyInfo(mm);
			Map<String, Object> m = new HashMap<String, Object>();
			String APPLY_ID =null;
			try {
				APPLY_ID= pService.doAddPigeonholeApplyInfo(mm);
				m.put("DOSSIER_APPLY_ID", APPLY_ID);
			} catch (Exception e) {
				throw new ActionException("归档申请失败", e);
			}
//			if (rstList.size() > 0) {
//				int i = 0;
//				service.doDelContractByProjectId(rstList.get(0));
//				for (Map<String, Object> map : rstList) {
//					i += service.doAddCheckedContract(map);
//				}
//			} else {
//				throw new ActionException("没有索引到需要的数据");
//			}
	
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		String DOSSIER_APPLY_ID = APPLY_ID;
		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID); // FIL_DOSSIER_APPLY
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		if (list.get(0).containsKey("PAYLIST_CODE")) {
			map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
		}

		/**
		 * 获取项目或客户对应的最新使用的档案袋和档案柜编号
		 */
		Map<String, Object> portfoliMap = service.doShowClientPortfolioAndCabinet(
				  null == map.get("CLIENT_ID") ? "" : map.get("CLIENT_ID").toString()
				, null == map.get("PROJECT_CODE") ? "" : map.get("PROJECT_CODE").toString()
		);
		if (portfoliMap == null || portfoliMap.size() < 1 || portfoliMap.isEmpty()) {
			if (StringUtils.isBlank(map.get("PROJECT_CODE")))
				map.put("PORTFOLIO_NUMBER", "CZR-" + DateUtil.getSysDate());
			else
				map.put("PORTFOLIO_NUMBER", "HT-" + map.get("PROJECT_CODE"));
		} else {
			map.put("PORTFOLIO_NUMBER", portfoliMap.get("PORTFOLIO_NUMBER"));
			if (!StringUtils.isBlank(portfoliMap.containsKey("CABINET_NUMBER") ? portfoliMap.get("CABINET_NUMBER") : "")) {
				String CABINET_NUMBERS[] = portfoliMap.get("CABINET_NUMBER").toString().split("-");
				map.put("HEAD", CABINET_NUMBERS[0]);
				map.put("ROW", CABINET_NUMBERS[1]);
				map.put("LINE", CABINET_NUMBERS[2]);
			} else {
				map.put("ROW", "");
				map.put("LINE", "");
			}
		}
		context.put("CABINETLIST", new DossierConfigService().doShowDossierConfigList(new HashMap()));
		context.put("BASEINFO", map);
		context.put("FILELIST", list);
		return new ReplyHtml(VM.html(path + "dossierStore.vm", context));
	}

	/**
	 * 获取档案柜行与列
	 * 
	 * @return
	 * @author:King 2013-10-10
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案入柜", "选择档案柜" })
	public Reply doShowCabRowLine() {
		Map<String, Object> param = _getParameters();
		param = new DossierConfigService().doShowDossierConfigList(param).get(0);
		return new ReplyAjax(param);
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案入柜", "档案存档入柜保存" })
	public Reply doAddDossierStorage() {
		Map<String, Object> param = _getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		System.out.println("list="+list);
		if(!list.isEmpty()&&list.size()>0){
			for (Map<String, Object> map : list) {
				if (i == 0) {
					if (map.containsKey("DOSSIER_APPLY_ID") && !StringUtils.isBlank(map.get("DOSSIER_APPLY_ID")))
						service.doDelDossierStorage(map.get("DOSSIER_APPLY_ID") + "");
				}
				//创建图像采集目录
	//			List<Map<String,Object>> dataProcessType = new DataDictionaryMemcached().get("档案资料所属流程类型");
	//			for (int j = 0; j < dataProcessType.size(); j++) {
				
						if((map.get("FILE_REMARK")+"").contains("留购")){
							map.put("DOSSIER_FILE_TYPE", "项目留购");
						}else if((map.get("FILE_REMARK")+"").contains("分期回购")){
							map.put("DOSSIER_FILE_TYPE", "分期回购");
						}else if((map.get("FILE_REMARK")+"").contains("正常回购")){
							map.put("DOSSIER_FILE_TYPE", "正常回购");
						}else if((map.get("FILE_REMARK")+"").contains("立项")){
							map.put("DOSSIER_FILE_TYPE", "立项合同");
						}else if((map.get("FILE_REMARK")+"").contains("租金变更")){
							map.put("DOSSIER_FILE_TYPE", "租金变更");
						}else if((map.get("FILE_REMARK")+"").contains("资料变更")){
							map.put("DOSSIER_FILE_TYPE", "资料变更");
						}else if((map.get("FILE_REMARK")+"").contains("资料补齐")){
							map.put("DOSSIER_FILE_TYPE", "资料补齐");
						}else if((map.get("FILE_REMARK")+"").contains("提前结清")){
							map.put("DOSSIER_FILE_TYPE", "提前结清");
						}else if((map.get("FILE_REMARK")+"").contains("A-B审批")){
							map.put("DOSSIER_FILE_TYPE", "A-B审批");
						}else{
							map.put("DOSSIER_FILE_TYPE", "立项合同");
						}
						
	//			}
				String CLIENT_CODE =null; 
				Map<String,Object> custMap=service.getCustCode(map.get("CLIENT_ID").toString());
				CLIENT_CODE=custMap.get("CUST_ID")+"";
				fcu.getCatalogIdByPath(map.get("CLIENT_NAME").toString(),CLIENT_CODE, map.get("PROJECT_CODE")+"/"+map.get("DOSSIER_FILE_TYPE")+"/"+map.get("FILE_NAME"),true);
	//			if(StringUtils.isEmpty(map.get("FILE_TYPE"))||map.get("FILE_TYPE")==null||"null".equals(map.get("FILE_TYPE"))){
	//				map.put("FILE_TYPE", "1");
	//			}
				System.out.println("==============="+map);
				i += service.doAddDossierStorage(map);
			}
		}
		return new ReplyAjax().addOp(new OpLog("档案上传", "档案存档入柜", "归档申请ID为:" + list.get(0).get("DOSSIER_APPLY_ID")));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "流程任务", "档案入柜", "查看" })
	public Reply toShowDossierStorage(){
		Map<String, Object> param = _getParameters();
		VelocityContext context=new VelocityContext();
		List<Map<String,Object>> list=service.doShowDossierStorageApplyList(param);
		context.put("FILELIST", list);
		context.put("baseInfo", list.get(0));
		return new ReplyHtml(VM.html(path+"showDossierStore.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐江龙")
//	@aPermission(name = { "流程任务", "档案入柜", "修改" })
	public Reply toUpdateDossierStorage(){
		Map<String, Object> param = _getParameters();
		VelocityContext context=new VelocityContext();
		String DOSSIER_APPLY_ID = param.get("DOSSIER_APPLY_ID") + "";
		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID);
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		if(!list.isEmpty()&&list.size()>0){
			if (list.get(0).containsKey("PAYLIST_CODE")) {
				map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
			}
			Map map1=list.get(0);
			Map map2=new HashMap();
			if(map1!=null && map1.get("PROJECT_ID")!=null && !map1.get("PROJECT_ID").equals("")){
				map2=service.queryInfoByProjectId(map1.get("PROJECT_ID").toString());
				map2.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
				
			}
			
			context.put("baseInfo", map2);
			context.put("FILELIST", list);
		}
		context.put("param", param);
		context.put("APPLY_ID", DOSSIER_APPLY_ID);
		return new ReplyHtml(VM.html(path+"updateDossierStore.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐江龙")
	//	@aPermission(name = { "流程任务", "档案入柜", "修改保存" })
	public Reply doSaveCheckedContractData(){
		Map<String, Object> param = _getParameters();
		service.doUpdatePigeonholeApplyInfo(param);
		if (param.containsKey("FILEINFO")) {
			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
			int i = 0;
			if(!list.isEmpty()&&list.size()>0){
				service.doDelContractByProjectId(list.get(0));
				for (Map<String, Object> map : list) {
					i += service.doAddCheckedContract(map);
				}
				if(i>0){
					return new ReplyAjax(false, "保存成功");
				}
				else{
					return new ReplyAjax(false, "保存失败");
				}
			}else{
				return new ReplyAjax(false, "没有索引到需要保存的数据");
			}
		}else{
			return new ReplyAjax(false, "保存失败");
		}
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐江龙")
//	@aPermission(name = { "流程任务", "档案入柜", "查看" })
	public Reply viewDossierStorage(){
		Map<String, Object> param = _getParameters();
		VelocityContext context=new VelocityContext();
		String DOSSIER_APPLY_ID = param.get("DOSSIER_APPLY_ID") + "";
		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID);
		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
		if(!list.isEmpty()&&list.size()>0){
			if (list.get(0).containsKey("PAYLIST_CODE")) {
				map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
			}
			Map map1=list.get(0);
			Map map2=new HashMap();
			if(map1!=null && map1.get("PROJECT_ID")!=null && !map1.get("PROJECT_ID").equals("")){
				map2=service.queryInfoByProjectId(map1.get("PROJECT_ID").toString());
				map2.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
				
			}
			context.put("baseInfo", map2);
			context.put("FILELIST", list);
			context.put("APPLY_ID", DOSSIER_APPLY_ID);
		}
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"viewDossierStore.vm", context));
	}
}
