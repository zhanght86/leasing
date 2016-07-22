package com.pqsoft.reportexcel.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.reportexcel.service.ReportExcelCreaditServices;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class ReportExcelCreaditAction extends Action {
	
	public VelocityContext context = new VelocityContext();
	private ReportExcelCreaditServices sevices = new ReportExcelCreaditServices();
	@Override
	@aPermission(name = {"报表管理", "信审综合报表", "列表"})
    @aDev(code = "170051", email = "guoweiwu@jiezhongchina.com.cn", name = "吴国伟")
    @aAuth(type = aAuthType.USER)
	public Reply execute() {
		return new ReplyHtml(VM.html("reportexcel/ReportExportCreadit.vm", context));
	}
	 @aPermission(name = {"报表管理", "信审综合报表", "查询"})
     @aDev(code = "170051", email = "guoweiwu@jiezhongchina.com.cn", name = "吴国伟")
     @aAuth(type = aAuthType.ALL)
	public Reply queryData(){
		Map<String,Object> m = _getParameters();
		Page page = sevices.queryCreaditData(m);
		return new ReplyAjaxPage(page);
	}

	// 导出下载
	 @aPermission(name = {"报表管理", "信审综合报表", "导出"})
	 @aDev(code = "170051", email = "guoweiwu@jiezhongchina.com.cn", name = "吴国伟")
	 @aAuth(type = aAuthType.USER)
	public Reply excelUpload() {
		Map map = _getParameters();
		List listReturn = new ArrayList();
		map.put("dataList", sevices.exportData(map));
		// excel导出
		return new ReplyExcel(sevices.exprotExcel(map), "XS" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "")  +"信审综合报表"+ ".xls"
				//sevices.exportData(map), "XS"
				//+ DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") +"信审综合报表"
				//+ ".xls"
				)
				;
	}
}
