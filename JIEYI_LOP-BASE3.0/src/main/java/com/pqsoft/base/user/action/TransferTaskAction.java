/**
 * TODO 
 * @author LUYANFENG @ 2015年5月28日
 */
package com.pqsoft.base.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;



import com.pqsoft.base.user.service.TransferService;
import com.pqsoft.base.user.service.UserService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

/**
 * TODO change privilege
 * 员工业务移交处理类
 * @author LUYANFENG @ 2015年5月28日
 *
 */
public class TransferTaskAction extends Action {

	private TransferService tranServ = new TransferService();
	private UserService userServ = new UserService();
	
	
	/**
	 * 移交记录明细页面
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply toTransferDetail() {
		VelocityContext vc = new VelocityContext();
		
		Map<String, Object> params = _getParameters();
		String EMPLOYEE_ID = (String) params.get("EMPLOYEE_ID");
		String t_id = (String) params.get("t_id");
		Map<String,Object> transfer = this.tranServ.getTransferHead(t_id);
//		List<Map<String,Object>> transferDetail = this.tranServ.getTransferDetail(t_id);
		
		Map<String,Object> qryMap = new HashMap<String,Object>();
		qryMap.put("EMPLOYEE_ID", EMPLOYEE_ID);
		Map<String, Object> userInfo = this.userServ.getUserInfoById(qryMap);
		vc.put("transfer", transfer);
		vc.put("user", userInfo);
		
		return new ReplyHtml(VM.html("base/user/toTransferDetail.vm", vc ));
	}
	/**
	 * 移交记录列表页面
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@Override
	public Reply execute() {
		VelocityContext vc = new VelocityContext();
		return new ReplyHtml(VM.html("base/user/transferList.vm", vc ));
	}
	
	
	
	/**
	 * 移交记录列表数据
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply listData(){
		Map<String, Object> params = _getParameters();
		Page page = this.tranServ.getTranPage(params);
		
		return new ReplyAjaxPage(page);
	}
	
	/**
	 * 业务移交页面
	 * @author : LUYANFENG @ 2015年5月28日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply toTransfer(){
		
		Map<String, Object> params = _getParameters();
		String EMPLOYEE_ID = (String) params.get("EMPLOYEE_ID");
		if(StringUtils.isBlank(EMPLOYEE_ID)){
			throw new ActionException("缺少参数！EMPLOYEE_ID");
		}
		Map<String,Object> qryMap = new HashMap<String,Object>();
		
		qryMap.put("EMPLOYEE_ID", EMPLOYEE_ID);
		Map<String, Object> userInfo = this.userServ.getUserInfoById(qryMap);
		
		VelocityContext vc = new VelocityContext();
		vc.put("user", userInfo);
		return new ReplyHtml(VM.html("base/user/toTransfer.vm", vc ));
	}
	
	
	/**
	 * 取客户
	 * @author : LUYANFENG @ 2015年5月28日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply cust_list(){
		Map<String, Object> params = _getParameters();
		String EMPLOYEE_CODE = (String) params.get("EMPLOYEE_CODE");
		if(StringUtils.isBlank(EMPLOYEE_CODE)){
			throw new ActionException("少参数");
		}
		Page custPage = this.tranServ.getCustPage(params);
		return new ReplyAjaxPage(custPage);
	}
	/**
	 * 已移交过的客户
	 * @author : LUYANFENG @ 2015年5月28日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply transfer_cust_list(){
		Map<String, Object> params = _getParameters();
		String t_id = (String) params.get("t_id");
		if(StringUtils.isBlank(t_id)){
			throw new ActionException("少参数");
		}
		
		Page custPage = this.tranServ.getTransferCustPage(params);
		return new ReplyAjaxPage(custPage);
	}
	
	
	
	
	/**
	 * 移交客户相关的业务
	 * @author : LUYANFENG @ 2015年5月28日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply doTransfer(){
		JSONObject json = new JSONObject();
		json.put("ok", false);
		//TODO 
		Map<String, Object> params = _getParameters();
		String cust_data_str = (String) params.get("data");
		String transferor = (String) params.get("transferor");
		String transferee = (String) params.get("transferee");
		String transferAll = (String) params.get("transferAll");
		String resume = (String) params.get("resume");
		
		boolean isok = this.tranServ.doTransfer(cust_data_str , transferor, transferee ,transferAll , resume);
		json.put("ok", isok);
		
		return new ReplyJson(json );
	}
	
	
	/**
	 * 移交 接收人s
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply emp_list(){
		Map<String, Object> param = _getParameters();
		Page pageData = this.userServ.getPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	
	/**
	 * 移交客户项目数据
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply project_list(){
		Map<String, Object> param = _getParameters();
		Page pageData = this.tranServ.getProjectPageByCust(param);
		return new ReplyAjaxPage(pageData);
	}
	/**
	 * 移交后客户项目数据
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	@aAuth(type=aAuthType.USER )
	@aPermission(name={"权限管理", "业务移交"})
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply transfer_project_list(){
		Map<String, Object> param = _getParameters();
		Page pageData = this.tranServ.getProjectPageAfterTransfer(param);
		return new ReplyAjaxPage(pageData);
	}

	
	
}
