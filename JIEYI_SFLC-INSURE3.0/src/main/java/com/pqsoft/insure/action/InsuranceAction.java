package com.pqsoft.insure.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.insure.service.InsuranceService;
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
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;

import net.sf.json.JSONObject;

public class InsuranceAction extends Action{
	private VelocityContext context = new VelocityContext();
	InsuranceService service = new InsuranceService();
	/**
	 * 保险管理
	 */
	@Override
	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
//		List<Map<String,Object>> list = service.getInsuranceAll(param);
//		context.put("data", list);
		context.put("param", param);
		return new ReplyHtml(VM.html("insure/insuranceManageNew.vm", context));
	}
	
	/**
	 * 查询车辆有没有正在生效的(商业险或交强险)保单
	 * @return ture代表有，false代表没有
	 */
	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "ali", email = "zhilixing@jiezhongchina.com", name = "邢治理")
	public Reply hasValidPolicy() {
		Map<String, Object> param = _getParameters();
		int amt = service.queryValidPolicyAmount(param);
		return new ReplyAjax(amt > 0 ? true : false,"");
	}
	
	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply getInsuranceEquipmentAll() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("insure/insuranceManageEquipment.vm", context));
	}
	
	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply getinsureEquipmentList() {
		Map<String, Object> param = _getParameters();
		Page page = service.getInsuranceEquipmentAll(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "70", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply expInsureEquipmentExcel() {
		
		Map<String, Object> param = _getParameters();
		
		List<String> list = new ArrayList<String>();
		list.add("保险清单");
		
		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		
		title.put("INSU_NAME", "客户名称");
		title.put("LEASE_CODE", "融资租赁合同编号");
		title.put("FLAG", "业务类型");
		title.put("COMPANY_NAME", "厂商");
		title.put("SUPPLIERS_NAME", "经销商");
		title.put("PRODUCT_NAME", "品牌名称");
		title.put("SPEC_NAME", "品牌型号");
		title.put("", "车牌号");
		title.put("CAR_SYMBOL", "车架号");
		title.put("ENGINE_TYPE", "发动机编号");
		titles.add(title);
		
		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
		
		List<Map<String, Object>> l = Dao.selectList("Insure.getInsuranceEquipmentList", param);
		data.add(l);
		
		String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime());
		
		Excel excel = new Excel("保险清单", list, titles, data);
		return new ReplyExcel(excel, "保险清单"+ dateStr +".xls");
	}
	
	@aPermission(name = { "保险管理", "保险管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getinsureList() {
		Map<String, Object> param = _getParameters();
		Page page = service.getInsurance(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = { "保险管理", "保险管理", "停止新建" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply stopNewPolicy() {
		Map<String,Object> param = _getParameters();
		int i=service.nowclose(param);
		if(i==1)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
	}
	@aPermission(name = { "保险管理", "保险管理", "新建保单" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply toaddNewPolicy() {
		Map<String, Object> param = _getParameters();
		// 设备信息
		Map<String,Object> eqmtType = service.geteq(param);
		context.put("eqmtType", eqmtType);
		//承租人信息
		 context.put("Natural", service.queryNaturaType(param));
	   //融资公司信息
		 param.put("FA_NAME", Security.getUser().getOrg().getPlatform());
		context.put("fhMap", service.getOfficial(param));
		// 查出所有的险种
		//context.put("InsuType", insureTypeService.getAll());
		// 查出所有的保险公司
		context.put("insuCompany", service.getCompanyAll());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("COUSTNAME", "商业险");
		map.put("PROJECT_ID", param.get("PROJECT_ID"));
		// 查询出表中已存在的费用明细
		Map<String, Object> cpMap = Dao.selectOne("paymentApply.findXmmxlistByCP", map);
		context.put("cpMap", cpMap);
		
		List balance_remark = (List) new DataDictionaryMemcached().get("保费差异原因");
		context.put("balance_remark", balance_remark);
		
		
		//added by xingzhili 2016.01.17 保险起期与止期添加默认值 start
		Calendar curr = Calendar.getInstance();
		curr.add(Calendar.DAY_OF_MONTH, 1);
		context.put("insuStartDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(curr.getTime()));
		curr.add(Calendar.DAY_OF_MONTH, -1);
		curr.add(Calendar.YEAR, 1);
		context.put("insuEndDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(curr.getTime()));
		//added by xingzhili 2016.01.17 保险起期与止期添加默认值 end
		return new ReplyHtml(VM.html("insure/insureCreate.vm", context));
	}
	@aPermission(name = { "保险管理", "保险管理", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getInsuPolicyType()
	{
		Map<String, Object> param = _getParameters();
		JSONObject json=new JSONObject();
		json.put("list", service.getInsuranceType(param));
		json.put("map", service.getCompanyAll1(param));
		return new ReplyAjax(JSONObject.fromObject(json));
	}
	@aPermission(name = { "保险管理", "保险管理", "保存保单" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply createInsuList(){
		InsuranceService service = new InsuranceService();
		Map<String, Object> param = _getParameters();
		param.put("s_employeeId", Security.getUser().getId());
		// 获得选择的险种ID
		Map m=service.createInsuList(param);
		String incu_id = m.get("INCU_ID").toString();
		param.put("insu_id", incu_id);
//		service.updfirstinsu(param);
		// 将险种加入中间表
				Map<String, Object> _q = new HashMap<String, Object>();
				_q.putAll(param);
				_q.put("intp_id", param.get("POLICY"));
				_q.put("type", 2);
				service.createInsutype2InsuList(_q);
		//商业险
//				Map<String, Object> _pq = new HashMap<String, Object>();
//		if(SYPOLICY!=null)
//		{
//			for(int i=0;i<SYPOLICY.length;i++)
//			{
//				_pq.put("insu_id", incu_id);
//				_pq.put("SYPOLICY", SYPOLICY[i]);
//				_pq.put("BAOFEI", BAOFEI[i]);
//				_pq.put("POLICYNMAE", POLICYNMAE[i]);
//				service.insCOMMERCIAL(_pq);
//			}
//		}
//		if(param.get("POLICY").equals(""))
		// 将设备加入中间表
				Map<String, Object> _p = new HashMap<String, Object>();
				_p.putAll(param);
				_p.put("insure_status", 1);
				_p.put("ESTIMATED_PREMIUMS", param.get("INSU_PRICE"));
				_p.put("INSURANCE_FLAG", 2);
				_p.put("type", 2);
				// 更新设备的保险状态为已投保
				//service.updateEqmt(_p);
				// 设备存入保单与设备中间表
				service.createInsueqmt2InsuList(_p);
				if(m.get("i").toString().equals("1"))
				{
					return new ReplyAjax(true,"");
				}else
				{
					return new ReplyAjax(false,"");
				}
	}
	@aPermission(name = {"保险管理", "保险批改", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply manager(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("insuCompany", service.getCompanyAll());
		context.put("param", param);
		return new ReplyHtml(VM.html("insure/insuListManage.vm", context));
	}	
	@aPermission(name = { "保险管理", "保险批改", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getmanager() {
		Map<String, Object> param = _getParameters();
		Page page = service.getmanager(param);
		return new ReplyAjaxPage(page);
	}	
	@aPermission(name = { "保险管理", "保险批改", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply delPolicy(){
		Map<String, Object> param = _getParameters();
		int i=service.delPolicy(param);
		if(i>0)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
	}
	@aPermission(name = { "保险管理", "保险管理", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply selPolicy()
	{
		Map<String, Object> param = _getParameters();
		Map<String, Object> m=service.getPolicy(param);
		context.put("Policy", m);
		m.put("ID", m.get("FPE_ID"));
		Map<String,Object> eqmtType = service.geteq(m);
		context.put("eqmtType", eqmtType);
		return new ReplyHtml(VM.html("insure/insuListManageSel.vm", context));
	}
	@aPermission(name = { "保险管理", "保险批改", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply updPolicy()
	{
		Map<String, Object> param = _getParameters();
		Map<String, Object> m=service.getPolicy(param);
		context.put("Policy", m);
		m.put("ID", m.get("FPE_ID"));
		Map<String,Object> eqmtType = service.geteq(m);
		context.put("eqmtType", eqmtType);
		// 查出所有的险种
		m.put("ID", m.get("INCP_ID"));
		context.put("InsuType", service.getInsuranceType(m));
		// 查出所有的保险公司
		context.put("insuCompany", service.getCompanyAll());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("COUSTNAME", "商业险");
		map.put("PROJECT_ID", param.get("PROJECT_ID"));
		// 查询出表中已存在的费用明细
		Map<String, Object> cpMap = Dao.selectOne("paymentApply.findXmmxlistByCP", map);
		context.put("cpMap", cpMap);
		
		// 保费差异原因
		List balance_remark = (List) new DataDictionaryMemcached().get("保费差异原因");
		context.put("balance_remark", balance_remark);
		return new ReplyHtml(VM.html("insure/insuListManageUpd.vm", context));
	}
	@aPermission(name = { "保险管理", "保险批改", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply updInsuList(){
		InsuranceService service = new InsuranceService();
		Map<String, Object> param = _getParameters();
		param.put("s_employeeId", Security.getUser().getId());
		// 获得选择的险种ID
		int i=service.updInsuList(param);
		i=i+service.delmiddle(param);
		Map<String, Object> _q = new HashMap<String, Object>();
		param.put("insu_id", param.get("ID"));
		_q.putAll(param);
		_q.put("intp_id", param.get("POLICY"));
		_q.put("type", 2);
		service.createInsutype2InsuList(_q);
		Map<String, Object> _p = new HashMap<String, Object>();
		_p.putAll(param);
		_p.put("insure_status", 1);
		_p.put("ESTIMATED_PREMIUMS", param.get("INSU_PRICE"));
		_p.put("INSURANCE_FLAG", 2);
		_p.put("type", 2);
		service.createInsueqmt2InsuList(_p);
		if(i>0)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
	}
	@aPermission(name = { "保险管理", "保单管理", "查看保单号" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply repeatInsuList() {
		Map<String, Object> param = _getParameters();
		int i=service.getCodeCount(param);
		if(i==0)
		{
			return new ReplyAjax(true,"");
		}else
		{
			return new ReplyAjax(false,"");
		}
	}
	@aPermission(name = { "保险管理", "保险管理", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply InsurancePolicy(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getInsurancePolicy(param));
	}
	@aPermission(name = { "保险管理", "续保管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply Renewal(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("insure/insuranceManage-xubao.vm", context));
	}	
	@aPermission(name = { "保险管理", "续保管理", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getRenewal() {
		Map<String, Object> param = _getParameters();
		Page page = service.getRenewal(param);
		return new ReplyAjaxPage(page);
	}	
	@aPermission(name = {"保险管理","续保管理","重新生成保单"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xgm", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply reInsuListForEqu(){
		InsuranceService service = new InsuranceService();
		Map<String, Object> param = _getParameters();
		Map<String, Object> m1=service.getPolicy(param);
		Map<String, Object> upd=new HashMap<String, Object>();
//		if(param.get("LX").equals("RBX"))
//		{
//			upd.put("BXNX", Integer.parseInt(m1.get("BXNX").toString())+1);
//			upd.put("RENEWALMODE", 1);
//			m1.put("FKTYPE", 2);
//			
//			service.insbxfk(m1);
//		}
//		if(param.get("LX").equals("KHCD"))
//		{
////			service.insbxfk(m1);
////			service.xbfkys(m1);
//			m1.put("FKTYPE", 3);
//			upd.put("RENEWALMODE", 2);
//			service.insbxfk(m1);
//		}
		service.upISRENEWAL(m1);
		upd.put("GENERATION", Integer.parseInt(m1.get("GENERATION").toString())+1);
		String start[]=m1.get("INSU_START_DATE").toString().split("-");
		String start1=(Integer.parseInt(start[0])+1)+"-"+start[1]+"-"+start[2];
		m1.put("INSU_START_DATE", start1);
		String end[]=m1.get("INSU_END_DATE").toString().split("-");
		String end1=(Integer.parseInt(end[0])+1)+"-"+end[1]+"-"+end[2];
		m1.put("INSU_END_DATE", end1);
		Map m=service.createInsuList(m1);
		String INCU_ID = m.get("INCU_ID").toString();
		upd.put("INCU_ID", INCU_ID);
		service.updGENERATION(upd);
		Map<String, Object> m2=service.getInsureINSULIST(param);
		m2.put("insu_id", INCU_ID);
		
		m2.put("s_employeeId", m2.get("CREATE_ID"));
		m2.put("intp_id", m2.get("INTP_ID"));
		m2.put("type", m2.get("TYPE"));
		service.createInsutype2InsuList(m2);
		Map<String, Object> m3=service.getInsureEMPT(param);
		m3.put("s_employeeId", m3.get("CREATE_ID"));
		m3.put("eqmt_id", m3.get("EQMT_ID"));
		m3.put("insu_id", INCU_ID);
		m3.put("type", m3.get("TYPE"));
		service.createInsueqmt2InsuList(m3);
		List<Map<String, Object>> list=service.getInsuranceType(param);
		Map<String, Object> m4=new HashMap<String, Object>();
//		for(int i=0;i<list.size();i++)
//		{
//			m4=(Map) list.get(i);
//			m4.put("insu_id", INCU_ID);
//			service.insCOMMERCIAL(m4);
//		}
		//service.reInsuListForEqu(param);
		try {
			SkyEye.getResponse().sendRedirect(SkyEye.getRequest().getContextPath()+"/insure/Insurance!updPolicy.action?ID="+INCU_ID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
//		param.put("ID", INCU_ID);
//		Map<String, Object> ma=service.getPolicy(param);
//		context.put("Policy", ma);
//		ma.put("ID", ma.get("FPE_ID"));
//		Map<String,Object> eqmtType = service.geteq(ma);
//		context.put("eqmtType", eqmtType);
//		// 查出所有的险种
//		ma.put("ID", ma.get("INCP_ID"));
//		context.put("InsuType", service.getInsuranceType(ma));
//		// 查出所有的保险公司
//		context.put("insuCompany", service.getCompanyAll());
//		return new ReplyHtml(VM.html("insure/insuListManageUpd.vm", context));
	}
}
