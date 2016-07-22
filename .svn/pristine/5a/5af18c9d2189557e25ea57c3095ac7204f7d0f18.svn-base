package com.pqsoft.leaseDate.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leaseDate.service.LeaseDateService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;

public class LeaseDateAction extends Action{

	private String basePath="leaseDate/";
	private LeaseDateService service = new LeaseDateService();
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aPermission(name = { "合同管理", "设定起租日", "列表显示"})
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context=new VelocityContext();
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("还款计划状态");
		List<Object> queryDataDictionary_ywlx = new SysDictionaryMemcached().get("业务类型");
		String ORD_ID = BaseUtil.getSupOrgChildren();
		// 当前操作员是否为供应商
		boolean flag = false;
		if (ORD_ID != null && !"".equals(ORD_ID)) {
			flag = true;
		}

		context.put("flag", flag);
		context.put("queryDataDictionary", queryDataDictionary);
		context.put("ywlx", queryDataDictionary_ywlx);
		return new ReplyHtml(VM.html(basePath+"leaseDate.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	@aPermission(name = { "合同管理", "设定起租日", "列表显示"})
	public Reply pageDate(){
		Map<String,Object> param = _getParameters();
		
		return new ReplyAjaxPage(service.queryPage(param));
	}

}
