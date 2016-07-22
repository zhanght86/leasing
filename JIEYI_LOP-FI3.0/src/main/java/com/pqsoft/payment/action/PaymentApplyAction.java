package com.pqsoft.payment.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.allinpay.xmltrans.service.TranxServiceImpl;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class PaymentApplyAction extends Action {

	private PaymentApplyService service = new PaymentApplyService();

	@Override
	@aPermission(name = { "业务管理", "放款申请" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		// try{
		// DateSiteUtil.setDateData("436717", "3");//参数PAY_ID,3固定为放款日期类型标示
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		return new ReplyHtml(VM.html("payment/paymentApply.vm", context));
	}

	@aPermission(name = { "业务管理", "放款申请" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSuppId());
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSpId());
		}
		BaseUtil.getDataAllAuth(param);
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new PaymentApplyService().getPage(param));
	}

	@aPermission(name = { "业务管理", "放款申请" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply InitiateProcess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.getProject(param);
		if(map.containsKey("PAYMENT_HEAD_ID")){
		if(StringUtils.isBlank(map.get("PAYMENT_HEAD_ID"))){
			String msg="流程已发起";
			return new ReplyAjax(false,msg);
		}}
		
	    /******************************校验经销商是否可以做放款**********************************************/
		if(StringUtils.isNotBlank(map.get("SUPPLIERS_ID"))){
			Map<String,Object> mm=new HashMap<String, Object>();
			mm.put("TYPE", "2");
			mm.put("ID", map.get("SUPPLIERS_ID")+"");
			mm.put("ACTIONTYPE", "2");
			boolean flag =true;
			try {
				Class<?> risk=Class.forName("com.pqsoft.riskMana.service.RiskControlService");
				flag = (Boolean) risk.getMethod("quoteCalculateTest", Map.class).invoke(risk.newInstance(), mm);
				if(flag==false)
					throw new ActionException("该经销商已被禁止放款申请，请联系管理员或风控人员");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/****************************************************************************/
		Map<String, Object> ffph = service.payMent_C_Submit(param);
		ffph.put("STATUS", 2);
		ffph.put("FLAG", 1);
		map.remove("ID");
		ffph.putAll(map);
		Map<String, Object> mapDate = service.PayHead_Status_Submit(ffph);
		List<Map<String, Object>> eq=service.getEq(map);
		Map<String, Object> eqq=eq.get(0);
//		getVinContrast(eqq);
		Map jbMap = Dao.selectOne("paymentApply.getJbpm_ID", mapDate);
		mapDate.put("PAYMENT_JBPM_ID", mapDate.get("ID"));
		int PaymentNum=service.getPaymentNum(mapDate);
		if(PaymentNum>1){
			jbMap.put("SUP_FKFS", "4".equals(map.get("PAYMENT_TYPE").toString())?"正常放款":"提前放款");
			jbMap.put("IS_DEKOU", "1".equals(map.get("ISDEKOU").toString())?"否":"是");
			new JBPM().setExecutionVariable(mapDate.get("JBPM_ID").toString(), jbMap);
		}
		String nextCode = new TaskService().getAssignee(mapDate.get("JBPM_ID").toString());
		if(nextCode==null)
		{
			nextCode="";
		}
		
//		//调用签约接口
//		//1.查询出项目ID
//		Map proMap=service.getProByPayment_Id(param);
//		if(proMap !=null && proMap.get("PROJECT_ID") !=null && !proMap.get("PROJECT_ID").equals("")){
//			String PROJECT_ID=proMap.get("PROJECT_ID")+"";
//			Map mapQy=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
//			if(mapQy ==null){//没有数据则新增一条
//				String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
//				Map mapWYQY=new HashMap();
//				mapWYQY.put("PROJECT_ID", PROJECT_ID);
//				mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
//				Dao.insert("BankAccountSign.insertWYQYInfo",mapWYQY);
//			}
//			
//			List list=Dao.selectList("BankAccountSign.queryWYQYInfoOne",proMap);
//			Dao.update("BankAccountSign.updateAGEGEADLINEOne",proMap);
//			//list为刷网银接口
//		}
		
		return new ReplyAjax(true, "流程发起成功，您的任务已经提交到下一节点: ", "流程发起成功，您的任务已经提交到下一节点: " +nextCode).addOp(new OpLog("业务管理", "放款申请", "发起流程："));
	}

	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getAdditional() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		int PaymentNum=service.getPaymentNum(param);
		if(PaymentNum>1){
			context.put("fp", "fp");
		}
		// 客户、担保人、共同承租人
		Map<String,Object> payment = service.getpayment(param);
		context.put("param", payment);
		Map<String, Object> map = service.getProject(payment);
		
		if(StringUtils.isNotBlank(map.get("LENDING_TYPE"))){
			String LENDING_TYPE[]=map.get("LENDING_TYPE").toString().split(",");
			List<Map<String, Object>> fkfs = new DataDictionaryMemcached().get("放款方式");
			Map<String, Object> fkfsmap=new HashMap<String, Object>();
			context.put("fkfs", fkfs);
			List<Map<String, Object>> LENDINGTYPE=new ArrayList<Map<String, Object>>();
			for(int i=0;i<LENDING_TYPE.length;i++){
				for(int j=0;j<fkfs.size();j++){
					fkfsmap=fkfs.get(j);
					if(fkfsmap.get("CODE").equals(LENDING_TYPE[i])){
						LENDINGTYPE.add(fkfsmap);
					}
					fkfsmap=new HashMap<String, Object>();
				}
			}
			map.put("LENDING_TYPE", LENDINGTYPE);
		}
		
		List<Map<String, Object>> list=service.getPeople(map);
		context.put("list", list);
		// 设备
//		List eqlist = service.queryEqByProjectIDByScheme(map);
		List eqlist = service.getEq(map);
		context.put("eq", eqlist);
		// 付款银行
//		Map<String, Object> PaymentBank = service.PaymentBank();
//		context.put("PaymentBank", PaymentBank);
		// 收款银行
		Map<String, Object> ReceivablesBank = service.ReceivablesBank(map);
		context.put("sk", ReceivablesBank);
		List<Map<String, Object>> khzh = new DataDictionaryService().queryDataDictionaryByScheme("开户行所属总行");
		context.put("khzh", khzh);
		List<Map<String, Object>> zffs = new DataDictionaryService().queryDataDictionaryByScheme("支付方式");
		//放款保证金金额
		payment.put("KEY_NAME_EN", "LOAN_BOND");
		Map<String, Object> fkbzj=Dao.selectOne("paymentApply.getFkbzj",payment);
		if(fkbzj!=null && "".equals(fkbzj.get("VALUE_STR").toString())){
			context.put("fkbzj", fkbzj.get("VALUE_STR").toString());
		}else{
			context.put("fkbzj", 0);
		}
		context.put("zffs", zffs);
		List<Map<String, Object>> ywlx = new SysDictionaryMemcached().get("业务类型");
		context.put("ywlx", ywlx);
		List<Map<String, Object>> zlzq = new SysDictionaryMemcached().get("租赁周期");
		context.put("zlzq", zlzq);
		Map<String,Object> projectMap = service.queryProjectById(map);
//		List<Map<String,Object>> vin=service.getVinContrast(payment);
//		context.put("vin", vin);
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		Dao.getClobTypeInfo2(projectMap, "SCHEME_CLOB");
		JSONArray SCHEME_CLOB1=JSONArray.fromObject(projectMap.get("SCHEME_CLOB"));
		/***************************************************************/
		context.put("SCHEMEDETAIL",SCHEME_CLOB1);
		context.put("dicTag", Util.DICTAG);
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		Map<String, Object> fkje=new HashMap<String, Object>();
		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		DecimalFormat df = new DecimalFormat(".00");
//		fkje.put("KEY_NAME_ZN", value);
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			if(((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE")) && !"".equals(mClob.get("VALUE_STR").toString()) && !mClob.get("VALUE_STR").toString().equals("0") && !mClob.get("VALUE_STR").toString().equals("0.0"))
			{
				fkje=new HashMap<String, Object>();
				if(mClob.containsKey("FYGS") && "JRSQK".equals(mClob.get("FYGS").toString())){
					if(mClob.containsKey("DSFS") && "1".equals(mClob.get("DSFS").toString())){
						fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
						fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
						fkje.put("DSFS", "代收");
						fkje1.add(fkje);
					}else{
						fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
						fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
						fkje.put("DSFS", "不代收");
						fkje1.add(fkje);
					}
				}
				if(mClob.containsKey("FYGS") && "JRRZE".equals(mClob.get("FYGS").toString()) && !"GPS费用".equals(mClob.get("KEY_NAME_ZN").toString()))
				{
					fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
					fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
					fkje.put("DSFS", "计入融资额");
					fkje1.add(fkje);
				}
			}
			if(mClob.containsKey("KEY_NAME_ZN")){
				if((mClob.get("KEY_NAME_ZN").toString()).equals("客户保证金")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("BZJ", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("成本率")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("CBL", String.format("%.2f", Double.parseDouble(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("尾款")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("WK", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("购置税")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("GZS", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("保险费")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("BXF", mClob.get("VALUE_STR"));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("车船税")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("CCS", mClob.get("VALUE_STR"));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("经销商保证金")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("JXSBZJ", mClob.get("VALUE_STR"));
				}
			}
		}
		String MANAGEMENT_FEEDSTYPE="";//null或者0为不代收
		if(projectMap.get("MANAGEMENT_FEEDSTYPE") !=null && !projectMap.get("MANAGEMENT_FEEDSTYPE").equals("")){
			MANAGEMENT_FEEDSTYPE=projectMap.get("MANAGEMENT_FEEDSTYPE")+"";
		}
		
		if(!MANAGEMENT_FEEDSTYPE.equals("1")){
			Map<String, Object> fkje2=new HashMap<String, Object>();
			fkje2.put("KEY_NAME_ZN", "手续费");
			fkje2.put("VALUE_STR", projectMap.get("POUNDAGE_PRICE"));
			fkje2.put("DSFS", "不代收");
			fkje1.add(fkje2);
		}
		context.put("map", map);
		context.put("fkje1", fkje1);
		if(param.get("YHBC")!=null && !"".equals(param.get("YHBC").toString()))
		{
			context.put("YHBC", param.get("YHBC"));
		}
		
		List<Map<String, Object>> projects = service.getProjectDetailBase(param);
		
		context.put("projects", projects);
		
		// 查询已经情况的费用明细
		param.put("IS_PAY", 1);
		List<Map<String,Object>> detailList = service.findXmmxlist(param);
		
		// add gengchangbao JZZL-115 2016年3月9日 start 
		Map<String,Object> parProject = null; // 重签合同数据【主要获取前版【废弃】项目ID】
		List<Map<String,Object>> reSignDetailList  = null;
		List<Map<String,Object>> parProjectList = service.queryParentProjectById(map);
		if (parProjectList != null && parProjectList.size() > 0) {
			parProject = parProjectList.get(0);
			if (parProject.get("PARENT_ID") != null && !"".equals(parProject.get("PARENT_ID"))) {
				context.put("PAR_PROJECT_ID", parProject.get("PARENT_ID"));
				
				//获取重签合同前版【废弃】放款详细金额 
				// 查询重签合同编号废弃合同已经支付费用明细
				Map<String, Object> parProjectMap = new HashMap<String, Object>();
				parProjectMap.put("PROJECT_ID", parProject.get("PARENT_ID"));
				//获取前版合同已支付费用明细
				reSignDetailList = service.findReSignXmmxlist(parProjectMap);
				if (reSignDetailList != null && reSignDetailList.size() > 0) {
					reSignDetailList = service.findXmmxlist(parProjectMap);
				}
			}
		}
		if (reSignDetailList != null && reSignDetailList.size() > 0) {
			//detail = getProjectDetailAndBase(detaillist);
			//整合重签合同费用明细与旧合同实付金额的差额
			detailList = getParentDifference(context,detailList,reSignDetailList);
			
		}
		
		
		
		// add gengchangbao JZZL-115 2016年3月9日 end
		
		context.put("dList", detailList);
		
		context.put("dList_length", detailList.size());
		
		context.put("PROJECT_ID", param.get("PROJECT_ID"));
		
		
		/*if(detaillist.size()>0){
			
			
			
			Map<String, Object> mapstr = detaillist.get(0);
			String strs =  (String) mapstr.get("SIGN");
			String[] a = strs.split(",");
			
			String signvalue =  (String) mapstr.get("SIGNVALUE");
			String[] b = signvalue.split(",");
			String ova ="";
			String strname ="";
			String strvalue ="";
			List list1 = new ArrayList();
			for(int i=0;i<a.length;i++){
				for(int y=0;y<b.length;y++){
					if(i==y){
						//String c = "'"+a[i]+":"+b[y]+"'";
						//ova+=c+",";
						strname += a[i]+",";
						strvalue += b[i]+",";
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("MC", a[i]);
						map1.put("PAY_MONEY", b[y]);
						list1.add(map1);
					}
				}
			}
			context.put("strname",strname);
			context.put("strvalue",strvalue);
			ova = "["+ova+"]";
			//String ss = str.substring(0,ova.length()-1);
			//JSONArray json = JSONArray.fromObject(ova);
			//Map<String, Object> map1 = new HashMap<String, Object>();
			//map1.put("MC", "其他费用");
			//map1.put("PAY_MONEY", "0");
			//list1.add(map1);
			context.put("list",list1);
		
			context.put("datestr",a);
			for(int i =0;i<a.length;i++){
				if(a[i].equals("1")){
					context.put("sjcgj","车辆实际采购价");
					context.put("sjcgj1","1");
				
				}
				if(a[i].equals("2")){
					context.put("clgzs","车辆购置税");
					context.put("clgzs2","2");
					
				}
				if(a[i].equals("3")){
					context.put("bx","商业险");
					context.put("bx3","3");
				
				}
				if(a[i].equals("4")){
					context.put("jqx","交强险 ");
					context.put("jqx4","4");
				
				}
				if(a[i].equals("5")){
					context.put("ccs","车船税");
					context.put("ccs5","5");
				}
				if(a[i].equals("6")){
					context.put("lqf","路桥费 ");
					context.put("lqf6","6");
				}
				if(a[i].equals("7")){
					context.put("hbf","环保标费");
					context.put("hbf7","7");
				}
				if(a[i].equals("8")){
					context.put("spf","上牌费");
					context.put("spf8","8");
				}
				if(a[i].equals("9")){
					context.put("lpf","临牌费");
					context.put("lpf9","9");
				}
				if(a[i].equals("10")){
					context.put("lpf","其他费用");
					context.put("qtfy10","10");
				}
			}
			
			context.put("datestr",a);
			
		}*/
		
		
		
		
		return new ReplyHtml(VM.html("payment/paymentAdditional.vm", context));
	}
	
	
	/**
	 * 
	 * 整合重签合同费用明细与旧合同实付金额的差额
	 * @param detaillist 实付【废弃合同】类型和价格
	 * @param schemeMoney 应付款项
	 * @return 价钱类型和对应的值Map对象
	 */
	private List<Map<String, Object>> getParentDifference(VelocityContext context, List<Map<String, Object>> detaillist,List<Map<String, Object>> reSignDetailList) {
		// 实付金额【废弃合同】
		Map<String, Object> reSignDetail = new HashMap<String, Object>();
		reSignDetail.put("money1", "0");	//车辆实际采购价
		reSignDetail.put("money2", "0");	//车辆购置税
		reSignDetail.put("money3", "0");	//商业险
		reSignDetail.put("money4", "0");	//交强险
		reSignDetail.put("money5", "0");	//车船税
		reSignDetail.put("money6", "0");	//路桥费
		reSignDetail.put("money7", "0");	//环保标费
		reSignDetail.put("money8", "0");	//上牌费
		reSignDetail.put("money9", "0");	//临牌费
		reSignDetail.put("money10", "0");	//其他费用
		// 前版已付设备费用明细整理 Start
		for (Map<String, Object> map : reSignDetailList) {
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			if("1".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money1", map.get("APPLY_MONEY"));	//车辆实际采购价
				} else {
					reSignDetail.put("money1", "0");
				}
				
			}else if("2".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money2", map.get("APPLY_MONEY"));	//车辆购置税
				} else {
					reSignDetail.put("money2", "0");
				}
			}else if("3".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money3", map.get("APPLY_MONEY"));	//商业险
				} else {
					reSignDetail.put("money3", "0");
				}
			}else if("4".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money4", map.get("APPLY_MONEY"));	//交强险
				} else {
					reSignDetail.put("money4", "0");
				}
			}else if("5".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money5", map.get("APPLY_MONEY"));	//车船税
				} else {
					reSignDetail.put("money5", "0");
				}
			}else if("6".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money6", map.get("APPLY_MONEY"));	//路桥费
				} else {
					reSignDetail.put("money6", "0");
				}
			}else if("7".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money7", map.get("APPLY_MONEY"));	//环保标费
				} else {
					reSignDetail.put("money7", "0");
				}
			}else if("8".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money8", map.get("APPLY_MONEY"));	//上牌费
				} else {
					reSignDetail.put("money8", "0");
				}
			}else if("9".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money9", map.get("APPLY_MONEY"));	//临牌费
				} else {
					reSignDetail.put("money9", "0");
				}
			}else if("10".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null && !"".equals(map.get("APPLY_MONEY"))) {
					reSignDetail.put("money10", map.get("APPLY_MONEY"));	//其他费用
				} else {
					reSignDetail.put("money10", "0");
				}
			}
		}
		// 前版已付设备费用明细整理 end
		
		BigDecimal sfje; //作废合同实付金额
		BigDecimal yfje; //重签合同应付金额
		BigDecimal cj; //差价
		for (Map<String, Object> map : detaillist) {
			yfje = new BigDecimal(0);
			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
			
			if("1".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money1").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("2".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money2").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("3".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money3").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("4".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money4").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("5".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money5").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("6".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money6").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("7".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money7").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("8".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money8").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("9".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money9").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}else if("10".equals(TYPEID)){
				if (map.get("APPLY_MONEY") != null) {
					yfje = new BigDecimal(map.get("APPLY_MONEY").toString());
				}
				sfje = new BigDecimal(reSignDetail.get("money10").toString());
				cj = yfje.subtract(sfje);
				int r=cj.compareTo(BigDecimal.ZERO); 
				if (r < 0){
					//cj = BigDecimal.ZERO;
				}
				map.put("PAYABLE_MONEY", cj);
			}
		}
		
		return detaillist;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "放款申请", "附加页面", "银行添加" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply updBank() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true;
//		// 更新经销商放款方式到 JBPM4_VARIABLE
		Map jbMap = Dao.selectOne("paymentApply.getJbpm_ID", m);
//		Map supMap = Dao.selectOne("paymentApply.getSupFkfs", m);
//		if (supMap != null) {
		jbMap.put("SUP_FKFS", "4".equals(m.get("PAYMENT_TYPE").toString())?"正常放款":"提前放款"); // 放款方式
		jbMap.put("IS_DEKOU", "1".equals(m.get("ISDEKOU").toString())?"否":"是"); //是否代收
		jbMap.put("PAY_MONEY", m.get("PAY_MONEY").toString());
//			jbMap.putAll(supMap);
//		}
		new JBPM().setExecutionVariable(m.get("JBPM_ID").toString(), jbMap);
//		// ///////////////////////////////////////////
		//判断同一项目是否存在提前放款还未通过的流程
//		if(!"4".equals(m.get("PAYMENT_TYPE").toString())){
//			int i=Dao.selectInt("paymentApply.getSupNum", m);
//			if(i>0){
//				msg = "已有一单提前特殊放款没有结束!";
//				flag = false;
//				return new ReplyAjax(flag, msg);
//			}
//		}
		int result = service.updBank(m);
		if (result > 0) {
			msg = "保存成功!";
			flag = true;
			return new ReplyAjax(flag, msg).addOp(new OpLog("放款申请", "银行添加", m.toString()));
		} else {
			msg = "保存失败!";
			flag = false;
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "放款申请", "附加页面", "银行添加" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply savePaymentBank() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true;
		int result = service.savePaymentBank(m);
		if (result > 0) {
			msg = "保存成功!";
			flag = true;
			return new ReplyAjax(flag, msg).addOp(new OpLog("放款申请", "银行添加", m.toString()));
		} else {
			msg = "保存失败!";
			flag = false;
			return new ReplyAjax(flag, msg);
		}
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getAdditional1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("baseParam", param);
		// 客户、担保人、共同承租人
		Map<String,Object> payment = service.getpayment(param);
		context.put("param", payment);
		Map<String, Object> map = service.getProject(payment);
		map.put("PAYMENT_ID", map.get("ID"));
		payment.put("KEY_NAME_EN", "YSZJQC");
		Map<String,Object> ys=Dao.selectOne("paymentApply.getFkbzj",payment);
		if(ys !=null ){
			map.put("YSZJQC", ys.get("VALUE_STR").toString());
		}
		Map<String,Object> sqk=service.getCollectionMoney(map);
		if(sqk!=null){
			if(ys !=null ){
				sqk.put("YSZJQC", ys.get("VALUE_STR").toString());
			}
			List<Map<String,Object>> listsqk=service.getCollectionDetailed(sqk);
			context.put("listsqk", listsqk);
		}
		context.put("sqk", sqk);
		List<Map<String, Object>> list=service.getPeople(map);
		context.put("list", list);
		// 设备
		List<Map<String,Object>> eqlist = service.getEq(map);
		context.put("eq", eqlist);
//		List<Map<String,Object>> vin=service.getVinContrast(payment);
//		context.put("vin", vin);
		// 付款银行
		List<Map<String, Object>> PaymentBank = service.PaymentBank();
		context.put("PaymentBank", PaymentBank);
		// 收款银行
		List<Map<String, Object>> zffs = new DataDictionaryService().queryDataDictionaryByScheme("支付方式");
		context.put("zffs", zffs);
		List<Map<String, Object>> ywlx = new SysDictionaryMemcached().get("业务类型");
		context.put("ywlx", ywlx);
		List<Map<String, Object>> zlzq = new SysDictionaryMemcached().get("租赁周期");
		context.put("zlzq", zlzq);
		List<Map<String, Object>> fkfs = new DataDictionaryMemcached().get("放款方式");
		context.put("fkfs", fkfs);
		Map<String,Object> projectMap = service.queryProjectById(map);
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		Dao.getClobTypeInfo2(projectMap, "SCHEME_CLOB");
		JSONArray SCHEME_CLOB1=JSONArray.fromObject(projectMap.get("SCHEME_CLOB"));
		/***************************************************************/
		context.put("SCHEMEDETAIL",SCHEME_CLOB1);
		context.put("dicTag", Util.DICTAG);
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		Map<String, Object> fkje=new HashMap<String, Object>();
		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		DecimalFormat df = new DecimalFormat(".00");
		float wds=0.00f;
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			
			if((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE"))
			{
				if( !"".equals(mClob.get("VALUE_STR").toString()) && !mClob.get("VALUE_STR").toString().equals("0") && !mClob.get("VALUE_STR").toString().equals("0.0") && mClob.containsKey("FYGS") && !"尾款".equals(mClob.get("KEY_NAME_ZN").toString()))
				{
					fkje=new HashMap<String, Object>();
					if(mClob.containsKey("FYGS") && "JRSQK".equals(mClob.get("FYGS").toString())){
						if(mClob.containsKey("DSFS") && "1".equals(mClob.get("DSFS").toString())){
							fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
							fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
							fkje.put("DSFS", "代收");
							fkje1.add(fkje);
						}else{
							wds=wds+ (StringUtils.isBlank((String)mClob.get("VALUE_STR")) ? 0 : Float.parseFloat(mClob.get("VALUE_STR").toString()));
							fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
							fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
							fkje.put("DSFS", "不代收");
							fkje1.add(fkje);
						}
					}
					if(mClob.containsKey("FYGS") && "JRRZE".equals(mClob.get("FYGS").toString()) && !"GPS费用".equals(mClob.get("KEY_NAME_ZN").toString()))
					{
						fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
						fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
						fkje.put("DSFS", "计入融资额");
						fkje1.add(fkje);
					}
				}
			}
			if(mClob.containsKey("KEY_NAME_ZN")){
				if((mClob.get("KEY_NAME_ZN").toString()).equals("客户保证金")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("BZJ", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("成本率")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("CBL", String.format("%.2f", Double.parseDouble(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("尾款")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("WK", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("购置税")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("GZS", df.format(Float.parseFloat(mClob.get("VALUE_STR").toString())));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("保险费")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("BXF", mClob.get("VALUE_STR"));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("车船税")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("CCS", mClob.get("VALUE_STR"));
				}else if((mClob.get("KEY_NAME_ZN").toString()).equals("经销商保证金")){
					if("".equals(mClob.get("VALUE_STR").toString())){
						mClob.put("VALUE_STR", "0");
					}
					map.put("JXSBZJ", mClob.get("VALUE_STR"));
				}
			}
		}
		if("1".equals(projectMap.get("MANAGEMENT_FEETYPE"))){
			fkje.put("KEY_NAME_ZN", "手续费");
			fkje.put("VALUE_STR", projectMap.get("POUNDAGE_PRICE"));
			fkje.put("DSFS", "不代收");
			wds=wds+Float.parseFloat(projectMap.get("POUNDAGE_PRICE").toString());
			fkje1.add(fkje);
		}
		context.put("map", map);
		context.put("fkje1", fkje1);
		context.put("wds", wds);
		if(param.containsKey("HX"))
		{
			Map<String,Object> money=Dao.selectOne("paymentApply.getSqk", map);
			if(map.containsKey("YSZJQC")){
				Map<String,Object> ysmoney=Dao.selectOne("paymentApply.getYs",map);
				if(Float.parseFloat(ysmoney.get("MONEY").toString())+Float.parseFloat(money.get("MONEY").toString()==""?"0":money.get("MONEY").toString())>0.0f){
					context.put("HX", "HX");
				}
			}else if(money !=null && money.containsKey("MONEY") && !"0".equals(money.get("MONEY").toString())){
				context.put("HX", "HX");
			}
		}
		
		return new ReplyHtml(VM.html("payment/paymentAdditional2.vm", context));
	}

	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getAdditional2() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		// 客户、担保人、共同承租人
		Map payment = service.getpayment(param);
		context.put("param", payment);
		Map<String, Object> map = service.getProject(payment);
		List<Map<String, Object>> list=service.getPeople(map);
		context.put("list", list);
		context.put("map", map);
		// 设备
		List eqlist = service.queryEqByProjectIDByScheme(map);
		context.put("eq", eqlist);
		// 付款银行
//		Map<String, Object> PaymentBank = service.PaymentBank();
//		context.put("PaymentBank", PaymentBank);
		// 收款银行
		List<Map<String, Object>> zffs = new DataDictionaryService().queryDataDictionaryByScheme("支付方式");
		context.put("zffs", zffs);
		List<Map<String, Object>> ywlx = new SysDictionaryMemcached().get("业务类型");
		context.put("ywlx", ywlx);
		List<Map<String, Object>> zlzq = new SysDictionaryMemcached().get("租赁周期");
		context.put("zlzq", zlzq);
		List<Map<String, Object>> fkfs = new DataDictionaryMemcached().get("放款方式");
		context.put("fkfs", fkfs);
		Map projectMap = service.queryProjectById(map);
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		Map<String, Object> fkje=new HashMap<String, Object>();
		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		float wds=0.00f;
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			
			if((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE"))
			{
				if( !"".equals(mClob.get("VALUE_STR").toString()) && !mClob.get("VALUE_STR").toString().equals("0") && !mClob.get("VALUE_STR").toString().equals("0.0") && mClob.containsKey("FYGS") && !"尾款".equals(mClob.get("KEY_NAME_ZN").toString()))
				{
					fkje=new HashMap<String, Object>();
					if(mClob.containsKey("DSFS") && !"".equals(mClob.get("DSFS").toString()) && "0".equals(mClob.get("DSFS").toString()))
					{
						wds=wds+ (StringUtils.isBlank((String)mClob.get("VALUE_STR")) ? 0 : Float.parseFloat(mClob.get("VALUE_STR").toString()));
					}
					if(mClob.containsKey("FYGS") && "JRSQK".equals(mClob.get("FYGS").toString()) && mClob.containsKey("DSFS")){
						if(mClob.containsKey("DSFS") && "1".equals(mClob.get("DSFS").toString())){
							fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
							fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
							fkje.put("DSFS", "代收");
							fkje1.add(fkje);
						}else{
							fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
							fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
							fkje.put("DSFS", "不代收");
							fkje1.add(fkje);
						}
					}
					if(mClob.containsKey("FYGS") && "JRRZE".equals(mClob.get("FYGS").toString()) && !"GPS费用".equals(mClob.get("KEY_NAME_ZN").toString()))
					{
						fkje.put("KEY_NAME_ZN",mClob.get("KEY_NAME_ZN").toString());
						fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
						fkje.put("DSFS", "计入融资额");
						fkje1.add(fkje);
					}
					
				}
			}
		}
		context.put("fkje1", fkje1);
		context.put("wds", wds);
		context.put("CARD", "CARD");
		return new ReplyHtml(VM.html("payment/paymentAdditional1.vm", context));
	}

	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute111() {
		//第二个
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		Map payment = service.getpayment(params);
		Map<String, Object> map = service.getProject(payment);
		context.put("params", map);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		// 附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "请款审核");
		m1.put("url", "/payment/PaymentApply!getAdditional1.action?HX=HX&PAYMENT_JBPM_ID=" + params.get("PAYMENT_JBPM_ID"));
		tabs.add(m1);
		
		// 保险管理-新建保单，只有在购买商业险的时候才会被显示
		if("购买商业险".equals(params.get("TASK_NAME") + "")){
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("title", "新建保单");
			m11.put("url", "/insure/Insurance.action?LEASE_CODE="+ map.get("LEASE_CODE") +"&PAYMENT_JBPM_ID=" + params.get("PAYMENT_JBPM_ID"));
			tabs.add(m11);
		}
		
		List<Map<String, Object>> tabs1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("title", "放款资料");
		m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID").toString()+"&PHASE=放款前&taskStatue=1&PROJECT_ID="+map.get("PROJECT_ID").toString());
		tabs1.add(m2);
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("title", "承租人");
		m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PROJECT_ID="+map.get("PROJECT_ID").toString()+"&CUST_TYPE=承租人&YHBC=YHBC");
		tabs1.add(m4);
		context.put("tabs2", JSONArray.fromObject(tabs1));
		context.put("tabs", JSONArray.fromObject(tabs));
		return new ReplyHtml(VM.html("payment/toMain.vm", context));
	}
	
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute1111() {
		//第二个
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		Map payment = service.getpayment(params);
		Map<String, Object> map = service.getProject(payment);
		context.put("params", map);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		// 附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "请款审核");
		m1.put("url", "/payment/PaymentApply!getAdditional1.action?BCYH=BCYH&PAYMENT_JBPM_ID=" + params.get("PAYMENT_JBPM_ID"));
		tabs.add(m1);

		context.put("tabs", JSONArray.fromObject(tabs));
		List<Map<String, Object>> tabs1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("title", "放款资料");
		m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID").toString()+"&PHASE=放款前&taskStatue=1&PROJECT_ID="+map.get("PROJECT_ID").toString());
		tabs1.add(m2);
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("title", "承租人");
		m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PROJECT_ID="+map.get("PROJECT_ID").toString()+"&CUST_TYPE=承租人&YHBC=YHBC");
		tabs1.add(m4);
		context.put("tabs2", JSONArray.fromObject(tabs1));
		return new ReplyHtml(VM.html("payment/toMain.vm", context));
	}

	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute11() {
		//第一个节点
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		String YHBC = SkyEye.getRequest().getParameter("YHBC");
		Map<String,Object> payment = service.getpayment(params);
		Map<String, Object> map = service.getProject(payment);
		context.put("params", map);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		
		// 附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "请款审核");
		m1.put("url", "/payment/PaymentApply!getAdditional.action?" + "PAYMENT_JBPM_ID=" + params.get("PAYMENT_JBPM_ID")+"&YHBC="+YHBC+"&PROJECT_ID="+params.get("PROJECT_ID"));
		tabs.add(m1);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人信息");
		m.put("url", "/customers/Customers!toUpdateCustInfoMain.action?"
				+ "CLIENT_ID="+map.get("CLIENT_ID").toString()+"&TYPE="+map.get("TYPE").toString()+"&tab=view&PROJECT_ID="+map.get("PROJECT_ID").toString());
		tabs.add(m);
		
		if(null != map.get("PARENT_ID") && !"".equals(map.get("PARENT_ID"))){
			Map<String, Object> mjl = new HashMap<String, Object>();
			mjl.put("title", "款项明细比对");
			mjl.put("url", "/customers/Customers!getLoanDetails.action?JD=leeds&PROJECT_ID=" + map.get("PROJECT_ID").toString() + "&SCHEME_ROW_NUM=" + map.get("SCHEME_ROW_NUM")
					+ "&PLATFORM_TYPE=" +  map.get("PLATFORM_TYPE").toString() + "&SCHEME_ID=" + map.get("SCHEME_ID"));
			tabs.add(mjl);
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		
		List<Map<String, Object>> tabs1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("title", "放款资料");
		m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID").toString()+"&PHASE=放款前&taskStatue=1&PROJECT_ID="+map.get("PROJECT_ID").toString()+"&YHBC="+YHBC);
		tabs1.add(m2);
		
		
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("title", "承租人");
		m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PROJECT_ID="+map.get("PROJECT_ID").toString()+"&CUST_TYPE=承租人&YHBC=YHBC");
		tabs1.add(m4);
		context.put("tabs2", JSONArray.fromObject(tabs1));
		if(YHBC !=null && YHBC!=""){
			context.put("YHBC", YHBC);
		}
		return new ReplyHtml(VM.html("payment/toMain.vm", context));
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute1() {
		//后督
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		Map<String, Object> map1=service.getpayment(params);
		Map<String, Object> map=service.getProject(map1);
		context.put("params", map);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		//附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "请款审核");
		m1.put("url", "/payment/PaymentApply!getAdditional1.action?"
				+ "PAYMENT_JBPM_ID="+params.get("PAYMENT_JBPM_ID"));
		tabs.add(m1);
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("title", "放款后");
		m3.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID")+"&PROJECT_ID="+map.get("PROJECT_ID")+"&PHASE=放款后");
		tabs.add(m3);
		
		context.put("tabs", JSONArray.fromObject(tabs));
		List<Map<String, Object>> tabs1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("title", "放款资料");
		m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID").toString()+"&PHASE=放款前&taskStatue=1&PROJECT_ID="+map.get("PROJECT_ID").toString());
		tabs1.add(m2);
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("title", "承租人");
		m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PROJECT_ID="+map.get("PROJECT_ID").toString()+"&CUST_TYPE=承租人&YHBC=YHBC");
		tabs1.add(m4);
		
		context.put("tabs2", JSONArray.fromObject(tabs1));
		return new ReplyHtml(VM.html("payment/toMain.vm", context));
	}
	public Reply seeImg() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		// PROJECT_ID，加载项目上传的附件
		String proId = params.get("PROJECT_ID").toString();
		String phase = params.get("PHASE") == null ? "立项" : params.get("PHASE").toString();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> pics = new MaterialMgtService().getFileInfoList(phase, proId);
		context.put("pics", pics);
		return new ReplyHtml(VM.html("leeds/imgViewer/seeImg.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "业务管理", "放款申请", "导出" })
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply exportExcel() {
		Map<String, Object> param = _getParameters();
		/*JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);*/
		ManageService mgService = new ManageService();
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if (supMap != null && supMap.get("SUP_ID") != null) {
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		return new ReplyExcel(service.exportData(), "workFlow"
				+ DateUtil.getSysDate() + Math.abs(new Random(10000).nextInt())
				+ ".xls");
	}
	/**
	 * 
	 * @author xgm 2015年4月1日
	 */
	@aPermission(name = { "业务管理", "放款申请", "附加页面","核销首期款" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply doVerification () {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
		m.put("ORGANIZATION_ID", Security.getUser().getOrg().getPlatformId());
		String FUND_ID=service.getFund(m);
		if(FUND_ID.equals("请录入来款"))
		{
			msg ="核销失败,请录入来款!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}else if(FUND_ID.equals("首期款已核销")){
			msg ="首期款已核销!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else if(FUND_ID.equals("没有首期款")){
			msg ="没有首期款!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else if(FUND_ID.equals("首期款核销完成")){
			msg ="首期款核销完成!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else{
		msg ="首期款核销已提交，请等待结果!";
		flag = true; 
		}
		return new ReplyAjax(flag, msg);
		
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public void getVinContrast(Map<String,Object> eq) {
		com.pqsoft.skyeye.enc.MD5 getMD5 = new com.pqsoft.skyeye.enc.MD5();
		Map<String, Object> param = eq;
		param.put("EQ_ID", param.get("ID"));
		param.put("VIN", param.get("CAR_SYMBOL"));
		JSONArray json=new JSONArray();
		String msg = "验证成功！";
		Boolean flag = true ;
//		String vin = "LSGLP83X3DF241588";
		List list=service.getVinList(param);
		if(list.size()>0){
			json=JSONArray.fromObject(list);
			msg = "VIN码已验证！";
//			return new ReplyAjax(flag,json,msg);
		}
		String vin=param.get("VIN").toString();
		String apikey = "e2da58f6-67bf-443b-af34-286f9a474035";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Date date = new Date();
		String time = df.format(date);
		int HOUR = date.getHours();
		int MINUTE = date.getMinutes();
		int SECOND = date.getSeconds();
		String HOUR1=HOUR<10?"0"+HOUR:HOUR+"";
		String MINUTE1=MINUTE<10?"0"+MINUTE:MINUTE+"";
		String SECOND1=SECOND<10?"0"+SECOND:SECOND+"";
		String md5 = vin + df1.format(date)
				+ "445b6609-edec-413e-b28a-12017680c340";
		String SECRET = getMD5.digest(md5);
		String urlStr = "http://car.iautos.cn/Maverick/Vin/ApiKey/" + apikey
				+ "/GetTrimIdAndTrimName/Vin/" + vin + "/" + time + "/" + HOUR1
				+ "/" + MINUTE1 + "/" + SECOND1 + "/" + SECRET;
		String vinContrast=getURLContent(urlStr);
		if("".equals(vinContrast)){
			msg = "未查询到VIN码数据！";
			flag = false ;
		}else {
			json=JSONArray.fromObject(vinContrast);
			if(json==null)
			{
				msg = "VIN码错误！";
				flag = false ;
			}else{
				Map<String, Object> m=(Map<String, Object>) json.get(0);
				m.put("EQ_ID", param.get("EQ_ID"));
				m.put("VIN", param.get("VIN"));
				int i=service.insVinContrast(m);
				if(i<=0){
					msg = "插入数据失败！";
					flag = false ;
				}
			}
		}
//		return new ReplyAjax(flag,json,msg);
	}
	/**
	 * 程序中访问http数据接口
	 */
	public static String getURLContent(String urlStr) {
		/** 网络的url地址 */
		URL url = null;
		/** http连接 */
		HttpURLConnection httpConn = null;
		/**//** 输入流 */
		BufferedReader in = null;
		String result = "";
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF-8"));
			result = FileCopyUtils.copyToString(in);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ActionException("调用VIN接口失败！");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		return result;
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public void ceshi(){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		map.put("CUST_USERID", "10000001");
		map.put("SN", FUND_ID);
		map.put("ACCOUNT_TYPE", "00");
		map.put("ACCOUNT_NO", "631686680");
		map.put("ACCOUNT_PROP", "0");
		map.put("ACCOUNT_NAME", "建元鼎铭国际融资租赁有限公司");
		map.put("AMOUNT", "100");
		map.put("CURRENCY", "CNY");
		map.put("REMARK", "测试");
		map.put("BANK_NAME", "中国民生银行北京东二环支行");
		list.add(map);
		map=new HashMap<String,Object>();
		map.put("TOTAL_ITEM", "1");
		map.put("TOTAL_SUM", "100");
		new FIinterface().tranx(list, map,"100003");
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public void ceshi1(){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		map.put("CUST_USERID", "10000001");
		map.put("SN", FUND_ID);
		map.put("ACCOUNT_TYPE", "00");
		map.put("ACCOUNT_NO", "11014748807003");
		map.put("ACCOUNT_PROP", "0");
		map.put("ACCOUNT_NAME", "建元鼎铭国际融资租赁有限公司");
		map.put("AMOUNT", "100");
		map.put("CURRENCY", "CNY");
		map.put("REMARK", "测试");
		map.put("BANK_NAME", "平安银行北京国贸支行");
		list.add(map);
		map=new HashMap<String,Object>();
		map.put("TOTAL_ITEM", "1");
		map.put("TOTAL_SUM", "100");
		new FIinterface().tranx(list, map,"100002");
	}
	
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public void ceshi2(){
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("通联财务接口");
		String URLhttp11="";
		Map<String,Object>  m=new HashMap<String,Object>();
		m=cwjk.get(0);
		URLhttp11=m.get("CODE").toString();
		TranxServiceImpl tranxService=new TranxServiceImpl();
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
		try {
			tranxService.queryTradeNew(URLhttp11, "200100000014454-1432026143706", isfront,"","");
//			tranxService.queryTradeNew(URLhttp11, "200100000014454-1431596969839", isfront,"","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public void ceshi3(){
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("通联财务接口");
		String URLhttp11="";
		Map<String,Object>  m=new HashMap<String,Object>();
		m=cwjk.get(0);
		URLhttp11=m.get("CODE").toString();
		TranxServiceImpl tranxService=new TranxServiceImpl();
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
		try {
			tranxService.signNotice1(URLhttp11, isfront);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply selCollection(){
		Map<String, Object> m = _getParameters();
		Map<String,Object> param=service.getCollectionMoney(m);
		List<Map<String,Object>> list=service.getCollectionDetailed(param);
		param.put("list", list);
		return new ReplyAjax(true,param, "");
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金放款日志", "刷新" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply updLendingLog(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				try {
					PaymentApplyService service = new PaymentApplyService();
					service.tranxTradeNew_JYZL();
					//放款回执
					service.paymentReceipt_JYZL();
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}

				System.out.println("耗时："
						+ ((System.currentTimeMillis() - start) / 1000)+"秒");
			}
		}).start();
		String msg = "刷新完成";
		Boolean flag = true;
		return new ReplyAjax(flag, msg);
	}
	
	@aPermission (name = {"业务管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getPaymentType(){
		List<Object> queryDataDictionary = DataDictionaryService.queryDataDictionary("付款金额方式");
		return new ReplyAjax(JSONArray.fromObject(queryDataDictionary));
	}
	
	@aPermission (name = {"业务管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "luoxinahong", email = "yixinggu123@163.com", name = "骆先宏")
	public Reply getProjectHeadStatus(){
		Map<String, Object> m = _getParameters();
		String projectId=(String) m.get("projectId");
		Map map=Dao.selectOne("paymentApply.getProjectHeadStatus", projectId);
		boolean flag=true;
		if(map.containsKey("STATUS")){
			String status=map.get("STATUS").toString();
			if("0".equals(status)){
				flag=false;
			}
		}
		return new ReplyAjax(flag, "");
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资金管理", "放款清单管理", "列表" })
	@aDev(code = "xingsumin", email = "suminxing@ jiezhongchina.com", name = "邢素敏")
	public Reply paymentApplyList()
	{
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("payment/paymentApplyList.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资金管理", "放款清单管理", "列表" })
	@aDev(code = "xingsumin", email = "suminxing@ jiezhongchina.com", name = "邢素敏")
	public Reply getPaymentApplyListPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new PaymentApplyService().getPaymentApplyListPage(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资金管理", "放款清单管理", "列表" })
	@aDev(code = "xingsumin", email = "suminxing@ jiezhongchina.com", name = "邢素敏")
	public Reply exportPaymentApplyList() {
		Map<String, Object> param = _getParameters();
		return new ReplyExcel(new PaymentApplyService().exportPaymentApplyList(param), "workFlow"
				+ DateUtil.getSysDate() + Math.abs(new Random(10000).nextInt())
				+ ".xls");
	}
	
}
