package com.pqsoft.documentApp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.documentApp.service.ApplyDossierService;
import com.pqsoft.documentApp.service.DossierBorrowService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * 权证接口-档案借阅
 * @author wanghl
 * @datetime 2015年6月8日,下午12:02:22
 */
public class DossierBorrowAction extends Action {

	private final String pagePath = "documentApp/";
	
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = { "权证管理", "档案管理", "档案借阅"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		ApplyDossierService service = new ApplyDossierService();
		String dataList = (String)param.get("dataList");
		JSONArray fromObject = JSONArray.fromObject(dataList);
		
		List<Map<String,Object>> dossierList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<fromObject.size();i++){
			Map<String,Object> map = (Map<String, Object>) fromObject.get(i);
			map.put("DOSSIER_STATUS", 0);
			List<Map<String, Object>> showDossierApp = service.toShowDossierApp(map);
			map.put("dossList", showDossierApp);
			dossierList.add(map);
		}
//		for(ListIterator iterator = fromObject.listIterator();iterator.hasNext(); iterator.next()){
//			Map<String,Object> object = fromObject.getJSONObject(iterator.nextIndex());
//		}
		//日期
		context.put("BORROW_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String date = new SimpleDateFormat("yyyy-MMdd").format(new Date());
		//文号
		context.put("BORROW_NUMBER", "QZJY-"+date);
		//呈报人
		context.put("REPORTER",Security.getUser().getName());
		//呈报部门
		context.put("REPORT_DEPARTMENT", Security.getUser().getOrg().getName());
		//资料清单
		context.put("dList", dossierList);
		return new ReplyHtml(VM.html(pagePath+"toDossierBorrowApply.vm", context));
	}
	
	/**
	 * 单个借阅
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月24日,上午11:11:56
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = { "权证管理", "档案管理", "档案借阅"})
	public Reply toBorrowAppForm(){
		VelocityContext context = new VelocityContext();
		DossierBorrowService service = new DossierBorrowService();
//		ApplyDossierService service = new ApplyDossierService();
		Map<String,Object> param = _getParameters();
		
		param.put("DOSSIER_STATUS", 0);
		List<Map<String, Object>> showDossierApp = service.toShowDossierApp(param);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dossList", showDossierApp);
		map.putAll(param);
		List<Map<String,Object>> dossierList = new ArrayList<Map<String,Object>>();
		dossierList.add(map);
		
		//日期
		context.put("BORROW_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String date = new SimpleDateFormat("yyyy-MMdd").format(new Date());
		//文号
		context.put("BORROW_NUMBER", "QZJY-"+date);
		//呈报人
		context.put("REPORTER",Security.getUser().getName());
		//呈报部门
		context.put("REPORT_DEPARTMENT", Security.getUser().getOrg().getName());
		//资料清单
		context.put("dList", dossierList);
		return new ReplyHtml(VM.html(pagePath+"toDossierBorrowApply.vm", context));
	}
	
	/**
	 * 发起借阅申请
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月15日,下午3:22:02
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply startBorrowByJbpm(){
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		System.out.println(param.get("jsonStr"));
		//添加档案借阅
		service.addBorrow(param);
		String borrow_id = param.get("borrow_id").toString();
		
		JSONArray fromObject = JSONArray.fromObject(param.get("jsonStr"));
		for(int i=0;i<fromObject.size();i++){
			Map<String,Object> map = (Map<String, Object>) fromObject.get(i);
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dosslist");
			for(int j=0;j<list.size();j++){
				Map<String, Object> map2 = list.get(j);
				map2.put("BORROW_ID", borrow_id);
				map2.put("LEASE_CODE", map.get("LEASE_CODE"));
				//默认可借
				map2.put("STATUS", 1);
				//添加借阅清单
				service.addBorrwDetail(map2);
			}
		}
		//发起流程
		List<String> list = JBPM.getDeploymentListByModelName("档案借阅审批",
				null, Security.getUser().getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		jmap.put("BORROW_ID", borrow_id);
		String jbpmId = JBPM.startProcessInstanceById(pid,null, null,null, jmap).getId();
		String nextCode = new TaskService().getAssignee(jbpmId);
		return new ReplyAjax(true, "提交成功！");
	}
	
	/**
	 * 部门负责人审批
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月15日,下午2:24:02
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowBorrowJbpm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		//查询档案借阅申请表
		Map<String, Object> selectBorrow = service.selectBorrow(param);
		//查询借阅清单的融资租赁合同编号
		List<Map<String,Object>> leaseCodeList = service.selectDetailLeaseCode(param);
		List<Map<String,Object>> bdList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<leaseCodeList.size();i++){
			Map<String, Object> map = leaseCodeList.get(i);
			map.put("BORROW_ID", param.get("BORROW_ID"));
			List<Map<String, Object>> detailList = service.selectBorrowDetail(map);
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("detailList", detailList);
			bdList.add(map2);
		}
		context.put("borrow", selectBorrow);
		context.put("bdList", bdList);
		return new ReplyHtml(VM.html(pagePath+"borrow/toShowBorrowJbpm.vm", context));
	}
	
	/**
	 * 部门负责人审批1
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月15日,下午2:24:24
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowBorrowJbpm1(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		//查询档案借阅申请表
		Map<String, Object> selectBorrow = service.selectBorrow(param);
		//查询借阅清单的融资租赁合同编号
		List<Map<String,Object>> leaseCodeList = service.selectDetailLeaseCode(param);
		List<Map<String,Object>> bdList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<leaseCodeList.size();i++){
			Map<String, Object> map = leaseCodeList.get(i);
			map.put("BORROW_ID", param.get("BORROW_ID"));
			//资料清单 1 可借
			map.put("STATUS", 1);
			List<Map<String, Object>> detailList = service.selectBorrowDetail(map);
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("detailList", detailList);
			bdList.add(map2);
		}
		context.put("borrow", selectBorrow);
		context.put("bdList", bdList);
		return new ReplyHtml(VM.html(pagePath+"borrow/toShowBorrowJbpm.vm", context));
	}
	
	/**
	 * 查看档案借出流程表单
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月12日,上午11:58:01
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowDossierOutJbpm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		//查询档案借阅申请表
		Map<String, Object> selectBorrow = service.selectBorrow(param);
		//查询借阅清单的融资租赁合同编号
		List<Map<String,Object>> leaseCodeList = service.selectDetailLeaseCode(param);
		List<Map<String,Object>> bdList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<leaseCodeList.size();i++){
			Map<String, Object> map = leaseCodeList.get(i);
			map.put("BORROW_ID", param.get("BORROW_ID"));
			List<Map<String, Object>> detailList = service.selectBorrowDetail(map);
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("detailList", detailList);
			bdList.add(map2);
		}
		context.put("borrow", selectBorrow);
		context.put("bdList", bdList);
		return new ReplyHtml(VM.html(pagePath+"borrow/toShowDossierOutJbpm.vm", context));
	}
	/**
	 * 保存档案借出流程表单
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月15日,上午11:41:35
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply saveDossierOutJbpm(){
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		
		String BORROW_ID = (String) param.get("BORROW_ID");
		String jsonStr = (String)param.get("jsonStr");
		JSONArray fromObject = JSONArray.fromObject(jsonStr);
		for(int i=0;i<fromObject.size();i++){
			Map<String,Object> map = (Map<String, Object>) fromObject.get(i);
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dossList");
			for(int j=0;j<list.size();j++){
				Map<String, Object> map2 = list.get(j);
				map2.put("BORROW_ID", BORROW_ID);
				//更新资料清单
				service.updateBorrowDetail(map2);
				//资料清单状态
//				String status = (String) map2.get("STATUS");
//				if("1".equals(status)){
//					//更新T_API_DOCUMENT_DOSSIERSTORAGE状态为已借出
//					
//				}
			}
		}
		return new ReplyAjax(true, "成功");
	}
	
	/**
	 * 借阅人归还流程表单
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月12日,下午4:53:14
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowBorrowerReturnJbpm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		//查询档案借阅申请表
		Map<String, Object> selectBorrow = service.selectBorrow(param);
		//查询借阅清单的融资租赁合同编号
		List<Map<String,Object>> leaseCodeList = service.selectDetailLeaseCode(param);
		List<Map<String,Object>> bdList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<leaseCodeList.size();i++){
			Map<String, Object> map = leaseCodeList.get(i);
			map.put("BORROW_ID", param.get("BORROW_ID"));
			//资料清单 1 可借
			map.put("STATUS", 1);
			List<Map<String, Object>> detailList = service.selectBorrowDetail(map);
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("detailList", detailList);
			bdList.add(map2);
		}
		context.put("borrow", selectBorrow);
		context.put("bdList", bdList);
		return new ReplyHtml(VM.html(pagePath+"borrow/toShowBorrowerReturnJbpm.vm", context));
	}
	
	/**
	 * 保存借阅人归还
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月15日,上午11:43:12
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply saveBorrowerReturnJbpm(){
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		
		String BORROW_ID = (String) param.get("BORROW_ID");
		String jsonStr = (String)param.get("jsonStr");
		JSONArray fromObject = JSONArray.fromObject(jsonStr);
		for(int i=0;i<fromObject.size();i++){
			Map<String,Object> map = (Map<String, Object>) fromObject.get(i);
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dossList");
			for(int j=0;j<list.size();j++){
				Map<String, Object> map2 = list.get(j);
				map2.put("BORROW_ID", BORROW_ID);
				//更新资料清单
				service.updateBorrowDetail(map2);
				//资料清单状态 1可借 
//				String status = (String) map2.get("STATUS");
//				//原件是否归还 1归还
//				String ORIGINAL_RETURN = (String)map2.get("ORIGINAL_RETURN");
//				if("1".equals(status)&&"1".equals(ORIGINAL_RETURN)){
//					//更新T_API_DOCUMENT_DOSSIERSTORAGE状态为已归还
//					
//				}
			}
		}
		return new ReplyAjax(true, "成功");
	}
	
	/**
	 * 直接移交
	 * @return
	 * @author wanghl
	 * @datetime 2015年7月16日,上午11:21:15
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply saveBorrowerHandoverJbpm(){
		Map<String,Object> param = _getParameters();
		DossierBorrowService service = new DossierBorrowService();
		String BORROW_ID = (String) param.get("BORROW_ID");
		String jsonStr = (String)param.get("jsonStr");
		JSONArray fromObject = JSONArray.fromObject(jsonStr);
		for(int i=0;i<fromObject.size();i++){
			Map<String,Object> map = (Map<String, Object>) fromObject.get(i);
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dossList");
			for(int j=0;j<list.size();j++){
				Map<String, Object> map2 = list.get(j);
				map2.put("BORROW_ID", BORROW_ID);
				//更新资料清单
				service.updateBorrowDetail(map2);
			}
		}
		return new ReplyAjax(true,"成功");
	}

}
