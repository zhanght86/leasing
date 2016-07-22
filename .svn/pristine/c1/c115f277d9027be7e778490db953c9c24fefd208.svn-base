package com.pqsoft.customers.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.customers.service.ActualCostService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * 财务放款申请
 * @date 2016年1月5日 15:46:01
 * @author YClimb
 *
 */
public class ActualCostAction extends Action {
	
	public ActualCostService service = new ActualCostService();

	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * 登记实际费用页面展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置","实际费用明细" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "yipan")
	public Reply actualCostReg(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map.toString());
		
		projectService proService = new projectService();
		List<Map<String,Object>> detailList =proService.findXmmxlist(map);
		
		List<Map<String,Object>> balanceList =proService.findXmmxBalanceList(map);
		
		context.put("detaillist", detailList);
		context.put("balanceList", balanceList);
		
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("sign",map.get("sign"));//权限判断1查看2修改
		
		return new ReplyHtml(VM.html("customers/actualCost/actualCostReg.vm",context));
	}
	
	/**
	 * 实际放款页面展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置","实际费用明细" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "yipan")
	public Reply actualLoan(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map.toString());
		
		projectService proService = new projectService();
		List<Map<String,Object>> detaillist =proService.findXmmxlist(map);
		
		context.put("detaillist", detaillist);
		
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("sign",map.get("sign"));//权限判断1查看2修改
		
		return new ReplyHtml(VM.html("customers/actualCost/actualLoan.vm",context));
	}
	
	/**
	 * 申请费用页面展示
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "参数配置","实际费用明细" })
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "yipan")
	public Reply actualApplyFee(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map.toString());
		
		CustomersService service = new CustomersService();
		List<Map<String,Object>> detailBase = service.getSlsjfyInfo(map);
		
		projectService proService = new projectService();
		List<Map<String,Object>> detaillist =proService.findXmmxlist(map);
		
		Map<String, Object> detail = getProjectDetailAndBase(detaillist);
		context.put("detaillist",detail);
		
		context.put("detailBase", detailBase);
		
		context.put("PROJECT_ID", map.get("PROJECT_ID"));
		context.put("sign",map.get("sign"));//权限判断1查看2修改
		
		return new ReplyHtml(VM.html("customers/actualCost/actualApplyFee.vm",context));
	}

	/**
	 * 将ProjectDetailBase得到的类型和价格进行拼串，最后返回Map对象
	 * @param detaillist 类型和价格
	 * @return 价钱类型和对应的值Map对象
	 */
	private Map<String, Object> getProjectDetailAndBase(List<Map<String, Object>> detaillist) {
		Map<String,Object> detail = new HashMap<String, Object>();
		for (Map<String, Object> map : detaillist) {
			
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			if("1".equals(TYPEID)){
				detail.put("CLSJ", map);	//车辆实际采购价
			}else if("2".equals(TYPEID)){
				detail.put("CLGZS", map);	//车辆购置税
			}else if("3".equals(TYPEID)){
				detail.put("SYBX", map);	//商业险
			}else if("4".equals(TYPEID)){
				detail.put("JQX", map);		//交强险
			}else if("5".equals(TYPEID)){
				detail.put("CCS", map);		//车船税
			}else if("6".equals(TYPEID)){
				detail.put("LQF", map);		//路桥费
			}else if("7".equals(TYPEID)){
				detail.put("HBFY", map);	//环保标费
			}else if("8".equals(TYPEID)){
				detail.put("SPF", map);		//上牌费
			}else if("9".equals(TYPEID)){
				detail.put("LPF", map);		//临牌费
			}else if("10".equals(TYPEID)){
				detail.put("QTFY", map);	//其他费用
			}
		}
		return detail;
	}

	/**
	 * 申请费用保存/实际放款数据提交
	 * @return
	 */
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "yipan")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax updateActualCost() {
		Map<String, Object> param = _getParameters();
		int res = service.updateActualCost(param);
		boolean falg = false;
		if (res > 0) {
			falg = true;
		}
		return new ReplyAjax(falg);
	}
	
	/**
	 * 登记实际费用
	 * @return
	 */
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "yipan")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax regActualCost(){
		Map<String, Object> param = _getParameters();
		int res = service.regActualCost(param);
		boolean falg = false;
		if (res > 0) {
			falg = true;
		}
		return new ReplyAjax(falg);
	}

}
