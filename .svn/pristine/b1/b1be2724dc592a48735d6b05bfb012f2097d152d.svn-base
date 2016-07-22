package com.pqsoft.leeds.talkSkill.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.leeds.talkSkill.service.CallVerifyService;
import com.pqsoft.leeds.talkSkill.service.TalkSkillService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.util.StringUtils;
/**
 * 电话核实
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class CallVerifyAction extends Action {
	private CallVerifyService service = new CallVerifyService();
	@Override
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "电话核实"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		List<Map<String, Object>> tplTypes = new TalkSkillService().getTplTypes(params);
		context.put("tplTypes", tplTypes);
		
		String proId = params.get("PROJECT_ID").toString();
		List<Map<String, Object>> recs = Dao.execSelectSql("SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT,A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, B.NAME, B.TYPE FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID");
		Map<String, Object> rec = recs.get(0);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
		String TYPE = rec.get("TYPE").toString();
//		String proCode = new CustInfoInputService().getProCode(proId);
		String hasGuarantee = rec.get("GUARANTEE")==null?"1":rec.get("GUARANTEE").toString();
		String hasJt = rec.get("JOINT_TENANT")==null?"1":rec.get("JOINT_TENANT").toString();
		
		//取得tabs、对应的url
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		
		//承租人
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人");
		m.put("url", "/customers/Customers!toViewCustInfo.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&JD=11&PROJECT_ID="+proId);
		tabs.add(m);
//		//上传图片
//		Map<String, Object> m0 = new HashMap<String, Object>();
//		m0.put("title", "附件资料");
//		m0.put("container", "iframe");
//		m0.put("url", "/leeds/cust_info_input/CustInfoInput!seeImg.action?"
//				+ "PROJECT_ID="+proId);
//		tabs.add(m0);
		//共同承租人
		if(!hasJt.trim().equals("1")) {
			String JOINT_TENANT_ID=rec.get("JOINT_TENANT_ID").toString();
			String JOINT_TENANT_TYPE=rec.get("JOINT_TENANT_TYPE").toString();
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人");
			m2.put("url", "/customers/Customers!toViewCustInfoGTCZR.action?"
					+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM&PROJECT_ID="+proId);
			tabs.add(m2);
		}
		//担保人
		if(!hasGuarantee.trim().equals("1")) {
			Map<String,Object> dbr=Dao.selectOne("project.getDbr",params);
			if(dbr ==null)
			{
				dbr=new HashMap<String,Object>();
			}
			dbr.put("TIME", new Date().getTime());
			
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "担保人");
			m2.put("url", "/credit/CreditGuarantor!toViewGuarantorInfo.action?"
					+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=view&date="+dbr.get("TIME"));
			tabs.add(m2);
		}
		String SHOW_FLAG = SkyEye.getRequest().getParameter("SHOW_FLAG");
		if ("1".equals(SHOW_FLAG)) {
			for (Map<String, Object> map : tabs) {
				map.put("url", map.get("url") + "&SHOW_FLAG=1");
			}
		}
		context.put("tabs1", JSONArray.fromObject(tabs));
		return new ReplyHtml(VM.html("leeds/talkSkill/toCallVerifyMain.vm", context));
	}
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "电话核实"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply loadTalkSkillResults() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		List<Map<String, Object>> tplTypes = new TalkSkillService().getTplTypes(params);
		
		context.put("tplTypes", tplTypes);
		return new ReplyHtml(VM.html("leeds/talkSkill/loadTalkSkillResults.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "电话核实", "加载模板列表"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply loadTpls() {
		Map<String, Object> params = _getParameters();
//		Page page = service.queryMainPage(params);
		List<Map<String, Object>> items = service.loadTpls(params);
		Page page = new Page();
		page.addDate(JSONArray.fromObject(items), items.size());
		return new ReplyAjaxPage(page);
	}
	
	
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "电话核实", "保存记录"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply saveConsist() {
		Map<String, Object> params = _getParameters();
		String TSC_ID = "";
		if(StringUtils.isNotBlank(params.get("TSC_ID"))) {
			TSC_ID = params.get("TSC_ID").toString();
			service.updateConsist(params);
		} else {
			TSC_ID = service.saveConsist(params);
		}
		return new ReplyAjax(TSC_ID);
	}
}
