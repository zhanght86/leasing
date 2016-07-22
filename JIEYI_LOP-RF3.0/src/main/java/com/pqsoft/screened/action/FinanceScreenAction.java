package com.pqsoft.screened.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.screened.service.FinanceScreenService;
import com.pqsoft.screened.service.ScreenedService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.StringUtils;

public class FinanceScreenAction extends Action {
	private final FinanceScreenService service=new FinanceScreenService();
	private final String  pagePath = "financeScreen/";
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "融资筛选","列表显示"})
	@Override
	public Reply execute() {
		VelocityContext context=new VelocityContext();
		context.put("PLAN_DATE",DateUtil.getSysDate("yyyy-MM-dd"));
		context.put("repayTypeList",new DataDictionaryMemcached().get("支付方式"));
		//融资方式查询
		context.put("refundway", new ScreenedService().getRefundBailoutway()); 
		return new ReplyHtml(VM.html(pagePath+"financeScreenMG.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "融资筛选","列表显示"})
	public Reply queryMGData(){
		//获取页面参数
		Map<String,Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(_getParameters().get("param"));
		m.remove("param");
		m.putAll(json);

		if(m.get("PLAN_DATE") == null || "".equals((m.get("PLAN_DATE")==null?"":m.get("PLAN_DATE").toString()))){
			m.put("PLAN_DATE",DateUtil.getSysDate("yyyy-MM-dd"));
		}
		if(!m.containsKey("PAY_BASE")||StringUtils.isBlank(m.get("PAY_BASE"))){
			m.put("PAY_BASE", "0");
		}
		Page page=service.getDataPage(m);
		if(StringUtils.isNotBlank(m.get("PAY_WAY"))){
			for(int i=0;i<page.getData().size();i++){
				Map<String,Object> mm=(Map<String, Object>) page.getData().get(i);
				mm.putAll(m);
				service.queryRZTJ(mm);
			}
		}
		return new ReplyAjaxPage(page);
	}
	
	

	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "融资筛选","创建项目"})
	public Reply createProject() {
		//页面参数
		Map<String,Object> map = _getParameters();
		JSONObject object = JSONObject.fromObject(map.get("dataJson"));
		object.put("USERNAME",  Security.getUser().getName());//登陆用户名
		object.put("USERCODE",  Security.getUser().getCode());//登陆用户编号、
		map.putAll(object);
		//项目id
		String[] payd = object.get("PRO_IDS").toString().split(",");
		
		//插入融资项目主表
		String pro_id = new ScreenedService().createProjectHead(object);	 
		map.put("RZ_ID",pro_id);
		object.put("PROJECTID", pro_id);
		boolean flag = false;
		String msg = "";
		
		if(pro_id.equals(null)){
			flag = false;
			msg = "融资项目添加失败";
		}else {
			service.insertProjectDetailByProids(map);
			//移植支付表
			for(int i=0;i<payd.length-1;i++){
				object.put("PRO_ID",payd[i]);
				object.put("RE_PAY_ID",Dao.getSequence("SEQ_RE_REFINANCE_RENT_HEAD"));
				//移植到融资对应的表
				service.transplantsRefundPay(object);
				
				object.put("PAYID", payd[i]);
				//移植到融资对应的还款计划子表
				new ScreenedService().transplantsRefundPayDetail(object);
				//移植设备
				service.transplantsRefundPayEquipment(object);
				//融资机构每个项目的融资基数
				service.addOrganrefund(object);
			}
			
			//发起退款评审流程
			List<String> prcessList = JBPM.getDeploymentListByModelName("融资项目审批");
			String nextAssignee=null;
			if(prcessList.size() > 0){
				Map<String, Object> jmap = new HashMap<String, Object>();
				jmap.put("PROJECT_ID",pro_id);
	    		jmap.put("PROJECT_CODE",object.get("PROJECT_CODE")+"");
				String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"", "","",jmap).getId();
			    nextAssignee=new TaskService().getAssignee(jbpm_id);
				msg += jbpm_id+"已发起！";
				flag = true;
			}else{
				flag = false;
				msg = "未找到流程";
				throw new ActionException("未找到流程");
			}
			
			
			return new ReplyAjax(flag,nextAssignee,"下一个操作人为:").addOp(new OpLog("融资筛选","新建项目","新建项目"));
		}
		
		return new ReplyAjax(flag,msg).addOp(new OpLog("融资筛选","新建项目","新建项目"));
	}
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "融资管理", "融资筛选","创建项目"})
	public Reply showFileList(){
		VelocityContext context=new VelocityContext();
		context.put("list", service.queryRZTJ(_getParameters()));
		return new ReplyHtml(VM.html(pagePath+"rz_fileList.vm", context));
	}
}
