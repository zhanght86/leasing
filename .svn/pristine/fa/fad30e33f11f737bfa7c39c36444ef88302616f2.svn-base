package com.pqsoft.project.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.project.service.TrialReportService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.StringUtils;

public class TrialReportAction extends Action {
	private String path= "project/";
	private TrialReportService service = new TrialReportService();

	@Override
	//@aPermission(name = { "项目管理", "项目一览", "添加初审报告" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> proInfo = service.getBaseMess(param);
		context.put("proInfo", proInfo);
		context.put("client_row","2".equals(proInfo.get("TYPE").toString())?38:47);
		context.put("assure_row","11");
		context.put("PROJECT_ID", param.get("PROJECT_ID").toString());
		return new ReplyHtml(VM.html(path+"addBigProject.vm", context));
	}
	
	//@aPermission(name = { "项目管理", "项目一览", "修改初审报告（大项目）" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply showTrialReport(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//承租人资产信息
		Map<String,Object> reportMess = service.getTrialReportHeadMess(param);
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("PROJECT_ID", StringUtils.nullToString(param.get("PROJECT_ID")));
		newParam.put("TYPE", "1");
		List<Object> shortLoan = service.getTrialReportDetailMess(newParam);
		shortLoan.add(1);
		context.put("shortLoan", shortLoan);
		newParam.put("TYPE", "2");
		List<Object> longLoan = service.getTrialReportDetailMess(newParam);
		longLoan.add(1);
		context.put("longLoan", longLoan);
		newParam.put("TYPE", "3");
		List<Object> landHouse = service.getTrialReportDetailMess(newParam);
		context.put("landHouse", landHouse);
		newParam.put("TYPE", "4");
		List<Object> haveEqs = service.getTrialReportDetailMess(newParam);
		context.put("haveEqs", haveEqs);
		newParam.put("TYPE", "5");
		List<Object> otherAssets = service.getTrialReportDetailMess(newParam);
		context.put("otherAssets", otherAssets);
		newParam.put("TYPE", "6");
	    List<Object> bankCun = service.getTrialReportDetailMess(newParam);
	    context.put("bankCun", bankCun);
	    //担保人资产信息
	    newParam.put("TYPE", "7");
	    List<Object> assureLandHouse = service.getTrialReportDetailMess(newParam);
	    context.put("assureLandHouse", assureLandHouse);
	    newParam.put("TYPE", "9");
	    List<Object> assureHaveEqs = service.getTrialReportDetailMess(newParam);
	    context.put("assureHaveEqs", assureHaveEqs);
	    newParam.put("TYPE", "10");
	    List<Object> assureOtherAssets = service.getTrialReportDetailMess(newParam);
	    context.put("assureOtherAssets", assureOtherAssets);
	    newParam.put("TYPE", "8");
	    List<Object> assureBankCun = service.getTrialReportDetailMess(newParam);
	    context.put("assureBankCun", assureBankCun);
		context.put("proInfo", reportMess);
		int row_num = "1".equals(reportMess.get("REPORT_TYPE").toString())?47:38;
		context.put("client_row",  row_num+shortLoan.size()+longLoan.size()+haveEqs.size()+landHouse.size()+otherAssets.size()+bankCun.size());
		context.put("assure_row", 11+assureLandHouse.size()+assureHaveEqs.size()+assureOtherAssets.size()+assureBankCun.size());
		context.put("param", param);
		context.put("PROJECT_ID", StringUtils.nullToString(param.get("PROJECT_ID")));
		return new ReplyHtml(VM.html(path+"addBigProject.vm", context));
	}
	
	//@aPermission(name = { "项目管理", "项目一览", "保存初审报告（大项目）" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply saveTrialReport(){
		Map<String,Object> param = _getParameters();
		Boolean flag = true ; 
		String msg = "";
		int result = service.addModfiyTrialReport(param);
		if(result > 0 ){
			flag = true ; 
            msg = "操作成功！";		
		}else{
			flag = false ; 
			msg = "操作失败！";
		}
		return new ReplyAjax(flag, msg);
	}
	
	//@aPermission(name = { "项目管理", "项目一览", "查看大项目信息（大项目）" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply showBigProjectDetail(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		//承租人资产信息
		Map<String,Object> reportMess = service.getTrialReportHeadMess(param);
		if(reportMess==null)
			reportMess=new HashMap<String,Object>();
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("PROJECT_ID", StringUtils.nullToString(param.get("PROJECT_ID")));
		newParam.put("TYPE", "1");
		List<Object> shortLoan = service.getTrialReportDetailMess(newParam);
		shortLoan.add(1);
		context.put("shortLoan", shortLoan);
		newParam.put("TYPE", "2");
		List<Object> longLoan = service.getTrialReportDetailMess(newParam);
		longLoan.add(1);
		context.put("longLoan", longLoan);
		newParam.put("TYPE", "3");
		List<Object> landHouse = service.getTrialReportDetailMess(newParam);
		context.put("landHouse", landHouse);
		newParam.put("TYPE", "4");
		List<Object> haveEqs = service.getTrialReportDetailMess(newParam);
		context.put("haveEqs", haveEqs);
		newParam.put("TYPE", "5");
		List<Object> otherAssets = service.getTrialReportDetailMess(newParam);
		context.put("otherAssets", otherAssets);
		newParam.put("TYPE", "6");
	    List<Object> bankCun = service.getTrialReportDetailMess(newParam);
	    context.put("bankCun", bankCun);
	    //担保人资产信息
	    newParam.put("TYPE", "7");
	    List<Object> assureLandHouse = service.getTrialReportDetailMess(newParam);
	    context.put("assureLandHouse", assureLandHouse);
	    newParam.put("TYPE", "9");
	    List<Object> assureHaveEqs = service.getTrialReportDetailMess(newParam);
	    context.put("assureHaveEqs", assureHaveEqs);
	    newParam.put("TYPE", "10");
	    List<Object> assureOtherAssets = service.getTrialReportDetailMess(newParam);
	    context.put("assureOtherAssets", assureOtherAssets);
	    newParam.put("TYPE", "8");
	    List<Object> assureBankCun = service.getTrialReportDetailMess(newParam);
	    context.put("assureBankCun", assureBankCun);
		context.put("proInfo", reportMess);
		int row_num = "1".equals(reportMess.get("REPORT_TYPE")+"")?47:38;
		context.put("client_row",  row_num+shortLoan.size()+longLoan.size()+haveEqs.size()+landHouse.size()+otherAssets.size()+bankCun.size());
		context.put("assure_row", 11+assureLandHouse.size()+assureHaveEqs.size()+assureOtherAssets.size()+assureBankCun.size());
		context.put("param", param);
		context.put("PROJECT_ID", StringUtils.nullToString(param.get("PROJECT_ID")));
		return new ReplyHtml(VM.html(path+"TtrialBigProjectPage.vm", context));
	}

}
