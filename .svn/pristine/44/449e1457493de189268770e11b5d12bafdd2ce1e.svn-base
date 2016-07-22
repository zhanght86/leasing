package com.pqsoft.zcfl.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.UtilChart;
import com.pqsoft.zcfl.service.AssetsReportTableService;

/******* 资产十二级分类报表****@auth: fuyulong 2014年8月04日 *************************/
public class AssetsReportTableAction extends Action {
	
	private final String _PATH = "zcfl/assets/";
	// **实现类服务
	AssetsReportTableService service = new AssetsReportTableService();

	
	//报表工具调用
	UtilChart utilChart=new UtilChart();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资产分类管理", "资产十二级分类报表", "资产涉及业务类型数量统计" })
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("chartReport", utilChart.to_chartPie(param, "报表-租赁物抵押-分析-统计类型", "资产涉及业务类型数量统计", "assetsReport.showGroupLeaseType", "1"));//饼状图
		return new ReplyHtml(VM.html(_PATH+"graphTJLeaseChart.vm", context));
	}

	/**
	 * 查询未抵押统计图表
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午03:20:16
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doChartLease() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		if ("2".equals(param.get("REPORT_QZTX"))) {
			chart = new UtilChart().to_chartBar(null, "报表-租赁物抵押-分析-统计类型", "各级别资产数量统计", "assetsReport.REPORT_X_MONTH,assetsReport.LEASE_REPORT_TYPE,assetsReport.showGroupLeaseFX", "1");//折线图
		} else {
			// 饼状图
			chart = utilChart.to_chartPie(null, "报表-租赁物抵押-分析-统计类型", "资产涉及业务类型数量统计", "assetsReport.showGroupLeaseType", "1");
		}
		return new ReplyAjax(true, chart, null);
	}
	
}
