package com.pqsoft.suppliersReport.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.suppliersReport.service.SuppliersReportService;
import com.pqsoft.util.UtilChart;

public class SuppliersReportAction extends Action {

	private SuppliersReportService service = new SuppliersReportService();

	private String[] titlesName = {"省份","项目名称","项目编号","业务类型","客户名称","经销商",
			"厂商","车系","车型","起租日期","租赁期数","租赁到期日","设备总额","租金计划编号"};
	private String[] sqlsName = {"PROVINCE_NAME","PRO_NAME","PRO_CODE","PLATFORM_TYPE","CUST_NAME","SUP_NAME",
			"COMPANY_NAME","CATENA_NAME","NAME","ON_HIRE_DATE","LEASE_TERM","END_DATE","LEASE_TOPRIC","PAYLIST_CODE"};
	//报表工具调用
    UtilChart utilChart=new UtilChart();
    
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "经销商报表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("经销商报表", titlesName, sqlsName));
		/********************************************************************/
		AreaService area = new AreaService();
		context.put("proList", area.getAllProvince());
		return new ReplyHtml(VM.html("suppliersReport/suppliersReport.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "经销商报表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply pageData(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.pageData(map));
	}
	

}
