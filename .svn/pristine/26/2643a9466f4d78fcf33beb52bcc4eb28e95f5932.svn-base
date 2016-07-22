package com.pqsoft.fusionChart.action;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.velocity.VelocityContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.CreateXml;
import com.pqsoft.fusionChart.service.ReportCreditService;

/**
 * <p>
 * Title: 融资租赁信息系统平台 报表管理 图表
 * </p>
 * <p>
 * Description: 客户统计报表； 项目收益统计报表；
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * @author 吴剑东  wujiandongit@163.com 
 * @version 1.0
 */
public class ReportCreditAction extends Action{
	
	private ReportCreditService service = new ReportCreditService();
	
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理","报表图表","项目报表"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("analysis/report/ladder.vm", context));
	}

	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"报表管理","报表图表","根据模块卡片返回页面"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@SuppressWarnings("unchecked")
	public void goReportView(){
		Map<String,Object> map = _getParameters();
		String TIMES = map.get("BEGIN_TIME")+"";
		String[] timeArr = TIMES.split("@");
		map.put("BEGIN_TIME", timeArr.length>0?timeArr[0]:"");
		map.put("END_TIME", timeArr.length>1?timeArr[1]:"");
		// 统计类型
		map.put("CREDIT_TYPE", "101");
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		// 当前月份
		map.put("CREDIT_DATE", df.format(date));
		
		// 更新当前月份数据
		service.updateByTypeDate(map);
		
		String xmlStr = service.getChangjiaXml(map);
		if(xmlStr.length() > 0){
			service.doStringXmlExp(xmlStr);
		}
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"报表管理","报表图表"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply goReportData(){
		Map<String,Object> param = _getParameters();
		System.out.println(param);
		Page pagedata = service.goReportData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 报表数据保存
	 * @author:  吴剑东
	 * @date:    2014-2-25 下午03:58:24
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理","报表图表","保存"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSaveData() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		System.out.println(param);
		try {
			result = service.saveData(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 * 报表数据删除
	 * @author:  吴剑东
	 * @date:    2014-2-25 下午03:58:24
	 */
	@aPermission(name = {"报表管理","报表图表","删除"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply doDeleteData() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteData(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			throw new ActionException("删除报表数据操作失败");
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理","报表图表","修改"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateData() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateData(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}
}
