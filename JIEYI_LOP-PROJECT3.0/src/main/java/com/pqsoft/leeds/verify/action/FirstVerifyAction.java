package com.pqsoft.leeds.verify.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
/**
 * 初审、终审
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class FirstVerifyAction extends Action {
//	private VerifyService service = new VerifyService();
	@Override
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "初审"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		String SHOW_FLAG="";
		if(params !=null && params.get("SHOW_FLAG")!=null && !params.get("SHOW_FLAG").equals("")){
			SHOW_FLAG=params.get("SHOW_FLAG")+"";
		}
		context.put("params", params);
		
//		String proId = "556104";
		String proId = params.get("PROJECT_ID").toString();
		//取得tabs、对应的url
		//承租人
		List<Map<String, Object>> recs = Dao.execSelectSql(""
				+ "SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT, "
				+ "A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
				+ " A.PLATFORM_TYPE, B.NAME, B.TYPE, "
				+ " C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID "
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  "
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID"
						+ " AND A.ID=C.PROJECT_ID(+)");
		Map<String, Object> rec = recs.get(0);
		context.put("rec", rec);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
		String TYPE = rec.get("TYPE").toString();
		String SCHEME_ID = rec.get("SCHEME_ID")==null?"0":rec.get("SCHEME_ID").toString();
		String SCHEME_ROW_NUM = rec.get("SCHEME_ROW_NUM")==null?"0":rec.get("SCHEME_ROW_NUM").toString();
		String PLATFORM_TYPE = rec.get("PLATFORM_TYPE").toString();
		String proCode = new CustInfoInputService().getProCode(proId);
		String hasGuarantee = rec.get("GUARANTEE")==null?"1":rec.get("GUARANTEE").toString();
		String hasJt = rec.get("JOINT_TENANT")==null?"1":rec.get("JOINT_TENANT").toString();
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人");
//		m.put("url", "/customers/Customers!toViewCustInfo.action?"
//				+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&JD=11&PROJECT_ID="+proId+"&ISCS=1");

		m.put("url", "/customers/Customers!toViewCustInfoMain.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
		tabs.add(m);
		
		if(!(SCHEME_ID=="0" || SCHEME_ROW_NUM=="0")) {
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("title", "方案");
			if(SHOW_FLAG.equals("1")){
				m3.put("url", "/project/project!SchemeView.action?"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID
						);
				tabs.add(m3);
			}else{
				m3.put("url", "/project/project!SchemeUpdate.action?"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID
						);
				tabs.add(m3);
			}
			
			
		}
		//附件资料
//		Map<String, Object> m1 = new HashMap<String, Object>();
//		m1.put("title", "附件资料");
//		m1.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
//				+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId);
//		tabs.add(m1);
		//历史车贷记录
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("title", "历史租赁记录");
		m11.put("url", "/customers/Customers!findche.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+proId);
		tabs.add(m11);
		//共同承租人
		if(!hasJt.trim().equals("1")) {
			String JOINT_TENANT_ID=rec.get("JOINT_TENANT_ID").toString();
			String JOINT_TENANT_TYPE=rec.get("JOINT_TENANT_TYPE").toString();
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人");
			m2.put("url", "/customers/Customers!toViewCustInfoGTCZR.action?"
					+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM&PROJECT_ID="+proId+"&ISCS=1");
			tabs.add(m2);
			
			Map<String,Object> GTCZRMap=new HashMap<String,Object>();
			GTCZRMap.put("GTCZR_CLIENT_ID", JOINT_TENANT_ID);
			GTCZRMap.put("GTCZR_CUST_TYPE", JOINT_TENANT_TYPE);
			context.put("GTCZRMap", GTCZRMap);
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
					+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=view&date="+dbr.get("TIME")+"&ISCS=1");
			tabs.add(m2);
			
			Map<String,Object> DBRMap=new HashMap<String,Object>();
			DBRMap.put("DBR_CLIENT_ID", dbr.get("CLIENT_ID"));
			DBRMap.put("DBR_CUST_TYPE", dbr.get("TYPE"));
			context.put("DBRMap", DBRMap);
		}
		
		//家访信息
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("title", "尽职调查任务分配");
		m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?"
				+ "PROJECT_ID="+proId+"&task_name="+params.get("TASK_NAME").toString()+"&CLIENT_ID="+CLIENT_ID);
		tabs.add(m3);
//		Map<String,Object> visit1 = Dao.selectOne("ReturnVisit.toViewVisit1",params);
//		if(visit1!=null){
			//家访资料
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("title", "承租人尽职调查报告");
			m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1");
			tabs.add(m4);
			if (!hasJt.trim().equals("1")) {
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "共同承租人尽职调查报告");
				m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
						+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
				tabs.add(m2);
			}
			// 担保人
			if (!hasGuarantee.trim().equals("1")) {
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "担保人尽职调查报告");
				m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
						+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1&JFBG=JFBG");
				tabs.add(m2);
			}
//		}
//		
//		
//		//DTI
//		Map<String, Object> dtiMap = new HashMap<String,Object>();
//		dtiMap.put("title", "计算器 ");
//		dtiMap.put("url", "/leeds/DTI/DTI.action?PROJECT_ID="+proId);
//		tabs.add(dtiMap);
		
		//客户操作记录
		Map<String, Object> mjl = new HashMap<String, Object>();
		mjl.put("title", "操作记录");
		mjl.put("url", "/customers/Customers!czjlPage.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId+"&ISCS=1");
		tabs.add(mjl);
		
		context.put("tabs", JSONArray.fromObject(tabs));
		context.put("JDMC", "JDMC");
		
		return new ReplyHtml(VM.html("leeds/verify.firstVerify/toMain.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "家访调查报告"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply toJiaFang(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		String proId = params.get("PROJECT_ID").toString();
		
		//取得tabs、对应的url
		//承租人
		List<Map<String, Object>> recs = Dao.execSelectSql(""
				+ "SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT, "
				+ "A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
				+ " A.PLATFORM_TYPE, B.NAME, B.TYPE, "
				+ " C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID, C.ID SCHEME_ID_ACTUAL "
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  "
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID"
						+ " AND A.ID=C.PROJECT_ID(+)");
		Map<String, Object> rec = recs.get(0);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
//		String TYPE = rec.get("TYPE").toString();
		String proCode = new CustInfoInputService().getProCode(proId);
		String hasGuarantee = rec.get("GUARANTEE") == null ? "1" : rec.get("GUARANTEE").toString();
		String hasJt = rec.get("JOINT_TENANT") == null ? "1" : rec.get("JOINT_TENANT").toString();
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();	
		
		//家访信息
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("title", "尽职调查");
		m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?"
				+ "PROJECT_ID="+proId+"&task_name="+params.get("TASK_NAME").toString()+"&task=jf&CLIENT_ID="+CLIENT_ID);
		tabs.add(m3);
		
//		if("家访报告".equals(params.get("TASK_NAME").toString())){
			//家访资料
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("title", "承租人尽职调查报告");
			m1.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
			tabs.add(m1);
//		}
		if (!hasJt.trim().equals("1")) {
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人尽职调查报告");
			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
			tabs.add(m2);
		}
		// 担保人
		if (!hasGuarantee.trim().equals("1")) {
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "担保人尽职调查报告");
			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1&JFBG=JFBG");
			tabs.add(m2);
		}
			
		//0无1查看2修改
		//方案
		if(params.containsKey("FA")){
			if(params.get("FA").toString().equals("0")){
				
			}else if(params.get("FA").toString().equals("1") || "1".equals(params.get("SHOW_FLAG"))){
				Map<String, Object> mm = new HashMap<String, Object>();
				mm.put("title", "方案");
				mm.put("url", "/project/project!SchemeView.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + rec.get("SCHEME_ROW_NUM")
						+ "&PLATFORM_TYPE=" + rec.get("PLATFORM_TYPE") + "&SCHEME_ID=" + rec.get("SCHEME_ID"));
				tabs.add(mm);
			}else if(params.get("FA").toString().equals("2")){
				Map<String, Object> mm = new HashMap<String, Object>();
				mm.put("title", "方案");
				mm.put("url", "/project/project!SchemeUpdate.action?LC=LC&"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+rec.get("SCHEME_ROW_NUM")
						+ "&PLATFORM_TYPE="+rec.get("PLATFORM_TYPE")+"&SCHEME_ID="+rec.get("SCHEME_ID")+"&SCHEME_ID_ACTUAL="+rec.get("SCHEME_ID_ACTUAL")
						);
				tabs.add(mm);
				
			}
		}
		
		//0无1查看2修改
		//承租人
		if(params.containsKey("CZR")){
			if(params.get("CZR").toString().equals("0")){
				
			}else if(params.get("CZR").toString().equals("1") || "1".equals(params.get("SHOW_FLAG"))){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", "承租人");
				m.put("url", "/customers/Customers!toViewCustInfoMain.action?"
						+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+rec.get("TYPE")+"&tab=view&PROJECT_ID="+proId);
				tabs.add(m);
			}else if(params.get("CZR").toString().equals("2")){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", "承租人");
				m.put("url", "/customers/Customers!toUpdateCustInfoMain.action?"
						+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+rec.get("TYPE")+"&tab=view&PROJECT_ID="+proId);
				tabs.add(m);
			}
		}
			
		context.put("tabs", JSONArray.fromObject(tabs));
		return new ReplyHtml(VM.html("leeds/verify.firstVerify/toJiaFang.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "初审"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply last() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
//		String proId = "556104";
		String proId = params.get("PROJECT_ID").toString();
		//取得tabs、对应的url
		//承租人
		List<Map<String, Object>> recs = Dao.execSelectSql(""
				+ "SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT, "
				+ "A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
				+ " A.PLATFORM_TYPE, B.NAME, B.TYPE, "
				+ " C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID "
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  "
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID"
						+ " AND A.ID=C.PROJECT_ID(+)");
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		Map<String, Object> rec  = recs.get(0);
		String proCode =null;
		String CLIENT_ID =rec.get("CLIENT_ID").toString();
		String hasJt=null;
		String hasGuarantee=null;
		String TYPE = rec.get("TYPE").toString();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人");
		m.put("url", "/customers/Customers!toViewCustInfoMain.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
		tabs.add(m);
		
		for (int i = 0; i < recs.size(); i++) {
			rec = recs.get(i);
			context.put("rec", rec);
		
			String SCHEME_ID = rec.get("SCHEME_ID")==null?"0":rec.get("SCHEME_ID").toString();
			String SCHEME_ROW_NUM = rec.get("SCHEME_ROW_NUM")==null?"0":rec.get("SCHEME_ROW_NUM").toString();
			String PLATFORM_TYPE = rec.get("PLATFORM_TYPE").toString();
			proCode = new CustInfoInputService().getProCode(proId);
			hasGuarantee = rec.get("GUARANTEE")==null?"1":rec.get("GUARANTEE").toString();
			hasJt = rec.get("JOINT_TENANT")==null?"1":rec.get("JOINT_TENANT").toString();

			int a=i+1;
			if(!(SCHEME_ID=="0" || SCHEME_ROW_NUM=="0")) {
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "方案"+a);
//				m3.put("url", "/project/project!projectShow.action?"
//						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
//						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID
//						);
				m3.put("url", "/project/project!SchemeView.action?"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID
						);
				tabs.add(m3);
			}
		}
		
		//附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "附件资料");
		m1.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
				+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId);
		tabs.add(m1);
		//历史车贷记录
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("title", "历史租赁记录");
		m11.put("url", "/customers/Customers!findche.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+proId);
		tabs.add(m11);
		//共同承租人
		if(!hasJt.trim().equals("1")) {
			String JOINT_TENANT_ID=rec.get("JOINT_TENANT_ID").toString();
			String JOINT_TENANT_TYPE=rec.get("JOINT_TENANT_TYPE").toString();
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人");
			m2.put("url", "/customers/Customers!toViewCustInfoGTCZR.action?"
					+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM&PROJECT_ID="+proId);
			tabs.add(m2);
			
			Map<String,Object> GTCZRMap=new HashMap<String,Object>();
			GTCZRMap.put("GTCZR_CLIENT_ID", JOINT_TENANT_ID);
			GTCZRMap.put("GTCZR_CUST_TYPE", JOINT_TENANT_TYPE);
			context.put("GTCZRMap", GTCZRMap);
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
			
			Map<String,Object> DBRMap=new HashMap<String,Object>();
			DBRMap.put("DBR_CLIENT_ID", dbr.get("CLIENT_ID"));
			DBRMap.put("DBR_CUST_TYPE", dbr.get("TYPE"));
			context.put("DBRMap", DBRMap);
		}
		
		//家访信息
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("title", "家访任务");
		m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?"
				+ "PROJECT_ID="+proId);
		tabs.add(m3);
		//DTI
		Map<String, Object> dtiMap = new HashMap<String,Object>();
		dtiMap.put("title", "计算器 ");
		dtiMap.put("url", "/leeds/DTI/DTI.action?PROJECT_ID="+proId+"&disabled=1");
		tabs.add(dtiMap);
		Map<String,Object> visit1 = Dao.selectOne("ReturnVisit.toViewVisit1",params);
		if(visit1!=null){
			//家访资料
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("title", "承租人尽职调查报告");
			m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1");
			tabs.add(m4);
		}
		if (!hasJt.trim().equals("1")) {
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人尽职调查报告");
			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
			tabs.add(m2);
		}
		// 担保人
		if (!hasGuarantee.trim().equals("1")) {
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "担保人尽职调查报告");
			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?"
					+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1&JFBG=JFBG");
			tabs.add(m2);
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		context.put("taskBz","ZS");
		context.put("JDMC", "JDMC");
		return new ReplyHtml(VM.html("leeds/verify.firstVerify/toMain.vm", context));
	}
	
	
}
