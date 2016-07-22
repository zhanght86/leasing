package com.pqsoft.gpsPlan.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.gpsPlan.service.GpsPlanManageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

//@aResource(name="GPS管理")

public class GpsPlanManageAction extends Action{
	
	private Logger logger = Logger.getLogger(getClass());
	public VelocityContext context = new VelocityContext();
	
	Map<String, Object> mapContext = null;
	
//	@aOperation(name = "[GPS管理]融资项目列表-设备")
	public Reply toManager() throws Exception {
//		VelocityContext context = new VelocityContext();
//		
//		Map<String, Object> m = new HashMap<String, Object>();
//		Enumeration<?> en = request.getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//		}
		this.getPageParameter();
		mapContext.put("MODIFY_ID", Security.getUser().getId());
//					Map map=_getParameters();
//					context.put("map",map);
		//ContractService contractService = new ContractService();
		GpsPlanManageService service = new GpsPlanManageService();
		
		mapContext.put("EM_ID", Security.getUser().getId());
		mapContext.put("EM_NAME",Security.getUser().getName());
		context.put("data", service.getContract(mapContext));
		Map DataMap=new HashMap();
		DataMap.put("DATATYPE", "业务类型");
		context.put("platformlist",DataDictionaryService.queryDataDictionary(DataMap)); //业务类型
		System.out.println("----------------------map222="+mapContext);
		System.out.println("----------------------map="+context);
		return new ReplyHtml(VM.html("gpsPlan/contractByEquManage.vm", context));
	}
	
//	@aOperation(name = "[GPS管理]GPS管理确认页面")
	public Reply toGPSManager()  {
		this.getPageParameter();
		mapContext.put("MODIFY_ID", Security.getUser().getId());

		GpsPlanManageService service = new GpsPlanManageService();
		
		mapContext.put("EM_ID", Security.getUser().getId());
		mapContext.put("EM_NAME",Security.getUser().getName());
		context.put("data", service.getGPSDate(mapContext));
		Map DataMap=new HashMap();
		DataMap.put("DATATYPE", "业务类型");
		context.put("platformlist",DataDictionaryService.queryDataDictionary(DataMap)); //业务类型
		return new ReplyHtml(VM.html("gpsPlan/gpsManage.vm", context));
	}
	
	
//	//根据合同号查询下面的设备
////	@aOperation(name="根据合同号查询设备")
//	public Object EquipmentByContractId() throws Exception {
//		
//		GpsPlanManageService service = new GpsPlanManageService();
//		
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> m = new HashMap<String, Object>();
//		Enumeration<?> en = request.getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//		}
//		
//		List list=(List)service.getEmpByRectId(m.get("RECT_ID").toString());
//		//判断是否有合同设备信息
//		if(list.size()>0){
//			context.put("morChild", list);
//			context.put("RECT_ID", m.get("RECT_ID").toString());
//			context.put("FINANCE_TYPE", m.get("FINANCE_TYPE").toString());
//			return new ReplyHtml(VM.html("gpsPlan/contractByEquipment.vm", context));
//		}
//		return null;
//	}
	
//	//开机申请
////	@aOperation(name = "[GPS管理]开/停机申请")
//	public Object applyOpen() throws Exception 
//	{
//		GpsPlanManageService service=new GpsPlanManageService();
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> m = new HashMap<String, Object>();
//		Enumeration<?> en = request.getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//		}
//		//流程过来
//		if(m.get("jbpm_id")!=null && !m.get("jbpm_id").equals(""))
//		{
//			if(m.get("jbpm_id").equals("1"))
//			{
//				m.put("tagtype", 8);
//			}
//		}
//		//
//		int num=0;
//		if(m.get("gpsId")!=null && !m.get("gpsId").equals(""))
//		{
//			//通过GPSID 查询出资信ID和设备ID
//			Map mapgps=new HashMap();
//			mapgps=service.getEqIdANDCreditIdByGpsId(m);
//			if(mapgps!=null)
//			{
//				if(mapgps.get("EQMENTID")!=null && !mapgps.get("EQMENTID").equals(""))
//				{
//					m.put("EQID", mapgps.get("EQMENTID"));
//				}
//				
//				if(mapgps.get("CREDIT_HEAD_ID")!=null && !mapgps.get("CREDIT_HEAD_ID").equals(""))
//				{
//					m.put("CREDIT_HEAD_ID", mapgps.get("CREDIT_HEAD_ID"));
//				}
//				
//			}
//			
//			m.put("GPSID",m.get("gpsId"));
//		}
//		
//		
//		//读取资信ID
//		String creditId="";
//		if(m.get("CREDIT_HEAD_ID")!=null && m.get("CREDIT_HEAD_ID")!="")
//		{
//			creditId=m.get("CREDIT_HEAD_ID").toString();
//		}
//		//读取设备ID
//		String eqId="";
//		if(m.get("EQID")!=null && m.get("EQID")!="")
//		{
//			eqId=m.get("EQID").toString();
//		}
//		
//		//判断为修改还是创建(1:开机创建,2:开机修改,3:停机创建,4:停机修改)
//		String tagType="";
//		if(m.get("tagtype")!=null && m.get("tagtype")!="")
//		{
//			tagType=m.get("tagtype").toString();
//		}
//		
//		
//		//获取GPS基本信息
//		Map map=new HashMap();
//		map=this.queryCreidtInfo(creditId,eqId);
//		
//		
//		
//		String tagNum="0";//0:开机  1：停机
//		
//		//如果为创建和修改做不同处理
//		Map map1=new HashMap();//保存GPS当前信息
//		if(tagType.equals("1"))
//		{
//			map1=(Map)service.selectCreditEquipmentByEqId(eqId);
//			map1.put("TYPE", map1.get("GPS_STATE"));
//			map1.put("CUST_NAME", this.USERCODE);
//			map1.put("CUST_ID", this.USERID);
//			tagNum="1";
//		}else if(tagType.equals("2"))
//		{
//			String GPSID="";
//			if(m.get("GPSID")!=null && m.get("GPSID")!="")
//			{
//				GPSID=m.get("GPSID").toString();
//			}
//			context.put("GPSID", GPSID);
//			map1=(Map)service.selectGPSBYInfo(GPSID);
//			tagNum="1";
//		}
//		else if(tagType.equals("3"))
//		{
//			map1=(Map)service.selectCreditEquipmentByEqId(eqId);
//			map1.put("TYPE", map1.get("GPS_STATE"));
//			map1.put("CUST_NAME", this.USERCODE);
//			map1.put("CUST_ID", this.USERID);
//			tagNum="0";
//		}else
//		{
//			String GPSID="";
//			if(m.get("GPSID")!=null && m.get("GPSID")!="")
//			{
//				GPSID=m.get("GPSID").toString();
//			}
//			context.put("GPSID", GPSID);
//			map1=(Map)service.selectGPSBYInfo(GPSID);
//			if(map1!=null)
//			{
//				if(map1.get("TYPE")!=null && !map1.get("TYPE").equals(""))
//				{
//					tagNum=map1.get("TYPE").toString();
//				}
//			}
//		}
//		//施工环境的配置
//		List<Object> list=service.selectDirList();
//		context.put("dictionary", list);
//		context.put("gpsInfo", map1);
//		context.put("credit", map);
//		context.put("tagNum", tagNum);
//		context.put("tagType", tagType);
//		context.put("EQID", eqId);
//		context.put("CONF", this.USERNAME);
//		context.put("CONF_ID", this.USERID);
//		if(tagType.equals("1")|| tagType.equals("3"))
//		{
//			return new ReplyHtml(VM.html("gpsPlan/createGPSPlan.vm", context));
//		}
//		else if(tagType.equals("2")|| tagType.equals("4"))
//		{
//			return new ReplyHtml(VM.html("gpsPlan/updateGPSPlan.vm", context));
//		}
//		else if(tagType.equals("5") || tagType.equals("8"))
//		{
//			return new ReplyHtml(VM.html("gpsPlan/gpsPlanView.vm", context));
//		}
//		else if(tagType.equals("6"))
//		{
//			return new ReplyHtml(VM.html("gpsPlan/gpsPlanTime.vm", context));
//		}
//		else if(tagType.equals("7"))
//		{
//			GpsPlanPdf pdf=new GpsPlanPdf();
//			pdf.gpsPlanPdfView(response, context);
//		}
//		return null;
//	}
	
	
	
//	//查询资信信息
////	@aOperation(name="(GPS管理)查询资信信息")
//	private Map<String,Object> queryCreidtInfo(String creditId,String eqId)
//	{
//		GpsPlanManageService service=new GpsPlanManageService();
//		
//		Map<String,Object> creditMap=new HashMap<String, Object>();
//		//查看资信头表信息
//		creditMap.put("head", service.selectCreditHeadByHeadId(creditId));
//		//查询资信设备
//		creditMap.put("equipmentList", service.selectCreditEquipmentByHeadId(eqId));
//		
//		//开停机信息
//		creditMap.put("record", service.selectGPSInfoByEqId(eqId));
//		
//		return creditMap;
//	}
//	
//	//创建GPS保存
////	@aOperation(name = "[GPS管理]开/停机申请保存")
//	public Reply createSaveGPSPlan()throws Exception 
//	{
////		VelocityContext context = new VelocityContext();
////		Map<String, Object> m = new HashMap<String, Object>();
////		Enumeration<?> en = request.getParameterNames();
////		while (en.hasMoreElements()) {
////			Object enN = en.nextElement();
////			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
////			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
////		}
////		
////		m.put("CREDIT_ID", this.USERID);
//		
//		this.getPageParameter();
//		mapContext.put("MODIFY_ID", Security.getUser().getId());
//		
//		GpsPlanManageService service=new GpsPlanManageService();
//		String id=service.createSaveGPSPlan(mapContext);
//		
//		if(id!=null || !"".equals(id))
//		{
//			String gpsId = id;
//			String jbpm_id = "1";
//			Map<String, Object> bpm = new HashMap<String, Object>();
//			bpm.put("gpsId", gpsId);
//			bpm.put("jbpm_id", jbpm_id);
//			
//			//程小强 添加当前操作人
//			bpm.put("USERNAME", USERNAME);
//			bpm.put("USERID", USERID);
//			bpm.put("PROJECT_ID", m.get("PROJECT_ID"));
//			bpm.put("BECR_ID", m.get("BECR_ID"));
//			
//			JBPM jbpm = mSGA.getBean("JBPM");
//			List<Object> list = jbpm.getDeploymentListByModelName("GPS停开机流程");
//			if (list.size() > 0) {
//				jbpm.startProcessInstanceById((String) list.get(0), bpm);
//				// ------------------新增备注
////				bpm.put("TASK_NAME", "开始");
////				bpm.put("OP_TYPE", "发起流程");
////				bpm.put("MEMO", "start");
////				BaseDao<?> dao = BaseDao.newInstance();
////				dao.insert("bpm.memo.add", bpm);
////				//----------------
////			
////				//将当前操作人和项目id加入流程实例
////				System.out.println(bpm.get("BECR_ID").toString()+"----------------------------------");
////				dao.update("bpm.taskmanage.udateProcinst", bpm);
//			} else {
//				logger.debug("未找到对应GPS停开机流程");
//				mSGA.rollbackSqlSession();
//			}
////			InsExpandBean isExpandBean = new InsExpandBean();
////			isExpandBean.setGpsId(id);
////			isExpandBean.setFormNames("gpsapply");
////		TODO 流程调整	new ProcessAction().doStartAfterProject(isExpandBean, "GPSID",
////					"GPS开关机流程", "0");
//		}
////		response.sendRedirect("GpsPlanManage!toManager.action");
//	    Map<String,Object> flag =new HashMap<String, Object>();
//		
//	    flag.put("flag", true);
//		
//		return new ReplyJson(JSONArray.fromObject(flag));
//	}
//	
	//修改GPS信息
//	@aOperation(name = "[GPS管理]开/停机申请修改")
//	public Reply updateGPSPlan()throws Exception 
//	{
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> m = new HashMap<String, Object>();
//		Enumeration<?> en = request.getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//		}
//		
//		m.put("MODIFY_ID", this.USERID);
//		GpsPlanManageService service=new GpsPlanManageService();
//		service.updateGPSPlan(m);
//		//response.sendRedirect("GpsPlanManage!toManager.action");
//	}
	
	//开停机确认时间
//	@aOperation(name = "[GPS管理]开/停机确认时间")
	public Reply updateGPSPlanTime()
	{
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> m = new HashMap<String, Object>();
//		Enumeration<?> en = request.getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			m.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//			context.put(enN.toString(), _getParameters().get(enN.toString()).toString().trim());
//		}
		this.getPageParameter();
		mapContext.put("MODIFY_ID", Security.getUser().getId());
//		mapContext.put("MODIFY_ID", this.USERID);
		GpsPlanManageService service=new GpsPlanManageService();
		service.updateGPSPlanTime(mapContext);
		return this.toGPSManager();
//		return this.toGPSManaresponse.sendRedirect("GpsPlanManage!toGPSManager.action");

	}
	
	
//	@aOperation(name = "[GPS管理]GPS外部链接的修改")
	public Reply updateGPSURL()throws Exception 
	{
		this.getPageParameter();
		mapContext.put("MODIFY_ID", Security.getUser().getId());
		GpsPlanManageService service=new GpsPlanManageService();
		service.updateGPSURL(mapContext);
		return this.toManager();

	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
		mapContext = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			mapContext.put(enN.toString(), para);
			context.put(enN.toString(), para);
		}
		mapContext.put("USERNAME", Security.getUser().getName());
		mapContext.put("USERCODE", Security.getUser().getCode());
		mapContext.put("USERID", Security.getUser().getId());
	}

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
