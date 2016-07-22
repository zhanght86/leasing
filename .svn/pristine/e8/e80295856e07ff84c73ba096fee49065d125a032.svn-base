package com.pqsoft.crm.service;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.status.LeaseApplicationJbpm;
import com.pqsoft.contract.service.BuyContractService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.delivery.service.InvoiceApplicationService;
import com.pqsoft.fhfaManager.FhfaManagerService;
import com.pqsoft.leaseApplication.service.LeaseApplicationService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

public class splitPaymentService {
	private String namespace = "bpm.status.";
	private String basePath = "leaseApplication.";
	public void autoCopyRent(String PROJECT_ID,String SCHEME_ID) {
		// ///////////////////////支付表数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("SCHEME_ID", SCHEME_ID);
		map = Dao.selectOne(namespace + "getEquipmentData", map);
		map.put("SCHEME_ID", SCHEME_ID);
		// 生成支付表号
		Map<String,Object> MapPayCode =  Dao.selectOne(
				"leaseApplication.createPayListCode", map);
		String PAYLIST_CODE = MapPayCode.get("PAYLIST_CODE").toString();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
//		projectService projectSer = new projectService();
//		Map<String,Object> baseMap = projectSer.queryInfoByEqBase(map);
//		LeaseApplicationService las = new LeaseApplicationService();
//		List eqlist = las.queryEqByChange(map);
		// 获取支付表ID
		String PAY_ID = Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");
		// 查询方案和支付表
		this.queryScheme(map, PAY_ID);

		// ///////////////////////////////付款约定数据

		// 判断是否分批起租
		int rentCount = Dao.selectInt(namespace + "getRentCount", map);
		System.out.println(map+"===========================");
		Map<String, Object> RentHeadData = Dao.selectOne(namespace + "getRentHeadData", map);
		Map<String, Object> buyContractData = Dao.selectOne(namespace
				+ "getCreateBuyContractData", map);
		buyContractData.put("PAYLIST_CODE", PAYLIST_CODE);
		buyContractData.put("SCHEME_ID", SCHEME_ID);
		buyContractData.put("SQ_PRICE", RentHeadData.get("FIRSTPAYALL") + "");
		buyContractData.put("PLATFORM_TYPE", RentHeadData.get("PLATFORM_TYPE")
				+ "");
		String LEASE_ORDER_CODE = CodeService.getCode("买卖合同编号",
				buyContractData.get("CLIENT_ID") + "", PROJECT_ID);
		buyContractData.put("LEASE_ORDER_CODE", LEASE_ORDER_CODE);
		buyContractData.put("PRODUCT_CODE", LEASE_ORDER_CODE);
		buyContractData.put("PROJECT_ID", PROJECT_ID);
		buyContractData.put("PAY_ID", PAY_ID);

		// 平台ID
		String platformId = Security.getUser().getOrg().getPlatformId();
		Long fhfa_id = null == platformId ? null : Long.valueOf(platformId);
		FhfaManagerService fhfaManagerService = new FhfaManagerService();
		Map<String, Object> fhfaManager = fhfaManagerService.selectFHFAManagerById(fhfa_id);
		buyContractData.put("LESSEE_NAME", fhfaManager.get("FA_NAME") + "");
		if (rentCount == 1) {
			String BUY_CONTRACT_ID = Dao.getSequence("SEQ_FIL_BUY_CONTRACT");
			buyContractData.put("BUY_CONTRACT_ID", BUY_CONTRACT_ID);
			Dao.insert(namespace + "autoCreateBuyContract", buyContractData);
			this.addBuyContract(PROJECT_ID, buyContractData);
		}
		// 自动起租
		this.leaseApplicationPassAuto(PAY_ID, PAYLIST_CODE);

		// 自动生成开票协议
		InvoiceApplicationService IaService = new InvoiceApplicationService();
		String clientId = IaService.getClientIdByProjectId(map) == null ? null
				: IaService.getClientIdByProjectId(map).toString();
		String invoiceAppCode = CodeService.getCode("开票协议编号", clientId, map
				.get("PROJECT_ID").toString());
		Map<String, Object> invoMap = new HashMap<String, Object>();
		invoMap.put("NO", invoiceAppCode);

		invoMap.put("BUY_CODE", map.get("LEASE_ORDER_CODE"));
		invoMap.put("PAY_ID", PAY_ID);
		invoMap.put("PAYLIST_CODE", PAYLIST_CODE);
		invoMap.put("PROJECT_ID", map.get("PROJECT_ID"));
		invoMap.put("CONTRACT_ID", map.get("PROJECT_ID"));
		invoMap.put("INVOICE_APPLICATION_ID",
				IaService.getInvoiceApplicationNextVal());
		invoMap.put("EMS_ID", IaService.getEMSInfoNextVal());

		// 查询融资租赁信息 甲方
		invoMap.put("PARTANAME", fhfaManager.get("FA_NAME"));
		invoMap.put("PARTAPHTOE", fhfaManager.get("FA_PHONE"));
		invoMap.put("PARTADUTY_ID", fhfaManager.get("TAX_NUM"));
		invoMap.put("PARTAADRS", fhfaManager.get("REG_ADD"));
		invoMap.put("PARTABANK", fhfaManager.get("FA_BINK"));
		invoMap.put("PARTANUMBER", fhfaManager.get("FA_ACCOUNT"));
		invoMap.put("PARTAATTORNEY", fhfaManager.get("LEGAL_PERSON"));
		invoMap.put("PARTACODE", fhfaManager.get("ORG_CODE"));

		// 查询客户信息 乙方
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("PAY_ID", PAY_ID);
		Map<String, Object> custInfo = IaService.searchCustInfo(payMap);
		invoMap.put("CODE", custInfo.get("LEASE_CODE"));
		invoMap.put("PARTBNAME", custInfo.get("NAME"));
		
		invoMap.put("PARTBBANK", custInfo.get("BANK_NAME"));
		invoMap.put("PARTBNUMBER", custInfo.get("BANK_ACCOUNT"));
		invoMap.put("PARTBDUTY", custInfo.get("TAX_CODE"));

		if (custInfo.get("TYPE") != null && custInfo.get("TYPE").equals("LP")) {
			invoMap.put("PARTBATTORNEY", custInfo.get("LEGAL_PERSON"));
			invoMap.put("PARTBCODE_OR_CARD", custInfo.get("ORAGNIZATION_CODE"));
			invoMap.put("PARTBPHONE", custInfo.get("REGISTE_PHONE"));
			invoMap.put("PARTBADRS", custInfo.get("REGISTE_ADDRESS"));
			invoMap.put("W_CODE_OR_CARD", custInfo.get("ORAGNIZATION_CODE"));
			invoMap.put("W_PHONE", custInfo.get("REGISTE_PHONE"));
			invoMap.put("W_ADDR", custInfo.get("REGISTE_ADDRESS"));
		} else {
			invoMap.put("PARTBCODE_OR_CARD", custInfo.get("ID_CARD_NO"));
			invoMap.put("PARTBPHONE", custInfo.get("TEL_PHONE"));
			invoMap.put("PARTBADRS", custInfo.get("HOUSE_ADDRESS"));
			invoMap.put("W_CODE_OR_CARD", custInfo.get("ID_CARD_NO"));
			invoMap.put("W_PHONE", custInfo.get("TEL_PHONE"));
			invoMap.put("W_ADDR", custInfo.get("HOUSE_ADDRESS"));
		}

		if (custInfo.get("TAX_QUALIFICATION") != null
				&& !custInfo.get("TAX_QUALIFICATION").equals("")) {
			// 纳税人资质
			String taxTYPE = "纳税资质";
			List<Object> taxTYPEList = (List) new DataDictionaryMemcached()
					.get(taxTYPE);
			for (int i = 0; i < taxTYPEList.size(); i++) {
				Map taxMap = (Map) taxTYPEList.get(i);
				if (taxMap.get("CODE") != null
						&& !taxMap.get("CODE").equals("")
						&& taxMap
								.get("CODE")
								.toString()
								.equals(custInfo.get("TAX_QUALIFICATION")
										.toString())) {
					invoMap.put("PARTB_TAX_QUALIFICATION", taxMap.get("FLAG"));
					invoMap.put("W_TAX_QUALIFICATION", taxMap.get("FLAG"));
				}
			}
		}
		
		//租金发票对象
		invoMap.put("W_NAME", custInfo.get("NAME"));
		invoMap.put("W_BANK", custInfo.get("BANK_NAME"));
		invoMap.put("W_BANK_NUMBER", custInfo.get("BANK_ACCOUNT"));
		invoMap.put("W_DUTY", custInfo.get("TAX_CODE"));
		
		invoMap.put("ZHENGJIFAPIAO", "客户");
		invoMap.put("INVOICEPATTERN", "FP_MONTH");

		Dao.insert("invoiceApplication.insertInvoiceApplication", invoMap);
		

	}
	public void queryScheme(Map<String,Object> m, String PAY_ID) {
		String basePath = "leaseApplication.";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PAYLIST_CODE", m.get("PAYLIST_CODE").toString());
		// 先查出本次拆分的比例
		m.put("MONEYCF", Dao.selectDouble(basePath + "queryEqInId", m));
		map.put("MONEYCF", Dao.selectDouble(basePath + "queryEqInId", m));
		m.put("MONEYTOTAL", Dao.selectDouble(basePath + "queryEqByScheme", m));
		map.put("MONEYTOTAL", Dao.selectDouble(basePath + "queryEqByScheme", m));
		BaseSchemeService bsService = new BaseSchemeService();
		System.out.println(m+"===================");
		List<Map<String, Object>> SCHEME_CLOB=bsService.getSchemeClob(null, m.get("PROJECT_ID") + "",m.get("SCHEME_ROW_NUM") + "");
		map.put("SCHEMEDETAIL",SCHEME_CLOB);
		Map<String,Object> mapbase = Dao.selectOne(basePath
				+ "getSchemeBaseInfoByProjectIdINT", m);
		map.putAll(mapbase);

		if (mapbase != null) {
			mapbase.put("SCHEME_ID", mapbase.get("ID"));
			List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
			for (Map<String, Object> map2 : clobList) {
				if("BEFORETHREE_PERCENT".equals(map2.get("KEY_NAME_EN")+"")){
					double   f1   = 1;
					if(StringUtils.isNotBlank(map2.get("VALUE_STR"))){
						BigDecimal b=new   BigDecimal(Double.parseDouble(map2.get("VALUE_STR").toString())/100);  
						f1   =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
					}
					mapbase.put(map2.get("KEY_NAME_EN")+"", f1);
				}else{
					mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
				}
			}
			
			try{
				mapbase.put("AMOUNT", (m.get("EQ_IDS")+"").split(",").length);
			}catch(Exception e){}
			
			
			mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
			mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
			mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
			mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
			mapbase.put("pay_way", mapbase.get("PAY_WAY"));
			mapbase.put("date", m.get("START_DATE_CHANGE"));
			mapbase.put("date1", m.get("REPAYMENT_DATE_CHANGE"));
			mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
			mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
			mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
			mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
			try{
				if(StringUtils.isNotBlank(m.get("EQ_IDS")))
					mapbase.put("AMOUNT", (m.get("EQ_IDS")+"").split(",").length);
			}catch(Exception e){}
			PayTaskService payTast = new PayTaskService();
			JSONObject page=null;
			try {
				page = payTast.quoteCalculateTest(mapbase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<Map<String, String>> irrList = (List<Map<String, String>>) page
					.get("ln");
			double ZJHJ = 0.00;
			Map hjmap = new HashMap();
			for (int t = 0; t < irrList.size(); t++) {
				if (irrList.get(t) != null) {
					hjmap = irrList.get(t);
					if (hjmap.get("zj") != null && hjmap.get("zj") != "") {
						ZJHJ = ZJHJ
								+ Double.parseDouble(hjmap.get("zj").toString());
					}
				}
			}
			map.put("MONTH_PRICE", ZJHJ);
			map.put("detailList", irrList);
		}
		map.put("PROJECT_ID", m.get("PROJECT_ID").toString());
		map.put("SCHEME_ID", m.get("SCHEME_ID").toString());
		this.updateEqStatus(map, PAY_ID);
	}
	public void addBuyContract(String PROJECT_ID, Map<String,Object> map) {
		BuyContractService bcs = new BuyContractService();

		// map.put("PAYMENT_HEAD_ID",map.get("PAY_ID")+"") ;
		map.put("PAY_NAME", "设备款");
		map.put("PAY_TYPE", 1);
		map.put("STATUS", 1);
		map.put("PAYEE_NAME", map.get("SELLER_UNIT_NAME"));
		map.put("PAY_BANK_ACCOUNT", map.get("SELLER_ACCOUNT"));
		map.put("PAY_BANK_ADDRESS", map.get("SELLER_PAY_BANK_ADDRESS"));
		map.put("PROJECT_ID", PROJECT_ID);
		// 查询代收金额
		Map<String,Object> dkje = Dao.selectOne("paymentApply.getDkje", map);
		Clob clob = (Clob) dkje.get("SCHEME_CLOB");
		String str = null ;
		try {
			str=clob.getSubString(1, (int) clob.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ActionException("报价详细未录入全，请联系管理员");
		}
		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
		JSONArray SCHEME_CLOB=JSONArray.fromObject(str);
		Double rze=0.00d;
		Double ds=0.00d;
		for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map mClob = (Map) iterator.next();
			
			if((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE"))
			{
				if(!"".equals(mClob.get("VALUE_STR").toString()) && !mClob.get("VALUE_STR").toString().equals("0") &&  mClob.containsKey("FYGS") )
				{
					if("JRSQK".equals(mClob.get("FYGS").toString())){
						if(mClob.containsKey("DSFS") && !"".equals(mClob.get("DSFS").toString()) && "1".equals(mClob.get("DSFS").toString()))
						{
							ds=ds+ (StringUtils.isBlank((String)mClob.get("VALUE_STR")) ? 0 : Double.parseDouble(mClob.get("VALUE_STR").toString()));
						}
					}else if("JRRZE".equals(mClob.get("FYGS").toString()) && !"GPS费用".equals(mClob.get("KEY_NAME_ZN").toString())){
						rze=rze+(StringUtils.isBlank((String)mClob.get("VALUE_STR")) ? 0 : Double.parseDouble(mClob.get("VALUE_STR").toString()));
					}
				}
			}
		}
//		Double fkje=Double.parseDouble(map.get("LEASE_TOPRIC").toString())+rze-ds;
		Double fkje=Double.parseDouble(map.get("LEASE_TOPRIC").toString());
		map.put("PAY_MONEY",fkje);
		map.put("PROJECT_CODE", map.get("PRO_CODE"));
		bcs.insertPaymentDetail(map);

		List<Map<String, Object>> typeList = bcs.queryPaymentMouldDetail(map);
		for (Map<String, Object> temp : typeList) {
			if (temp != null && temp.size() > 0) {
				temp.put("PAYLIST_CODE", map.get("PAYLIST_CODE") + "");
				temp.put("PAYMENT_ID", map.get("BUY_CONTRACT_ID") + "");
				bcs.insertPaymentTerm(temp);
			}
		}
	}
	public void updateEqStatus(Map param, String PAY_ID) {
		LeaseApplicationService las = new LeaseApplicationService();
		String basePath = "leaseApplication.";
		PayTaskService pay = new PayTaskService();
		// 方案
		Object schemeObject = null;
		// 从方案中获取首期款和其他费用
		// 获得支付表ID
		// String PAY_ID=Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");

		// 是否固定利息
		String LXTQSQ = param.get("LXTQSQ") != null ? param.get("LXTQSQ") + ""
				: "1";// 1:否，2：是

//		// 计算起租日期和还款日期利息差(前面已经计算)
//		double DateLXMoney = 0.0;
		// double residualPrincipal=param.get("FINANCE_TOPRIC")!=null ?
		// Double.parseDouble(param.get("FINANCE_TOPRIC").toString()):0d;
		// double annualRate=param.get("YEAR_INTEREST")!=null ?
		// Double.parseDouble(param.get("YEAR_INTEREST").toString()):0d;
		// DateLXMoney=pay.getDateLiXiMoney(param.get("START_DATE_CHANGE")+"",
		// param.get("REPAYMENT_DATE_CHANGE")+"", residualPrincipal,
		// annualRate);

		// 保存支付表头表(先不生成支付表号，当流程审批通过后在给支付表编号并且刷数据到财务系统)
		// 保存支付表子表
		double ZJHJ = Double.parseDouble(param.get("MONTH_PRICE").toString());
		double LXHJ = Double.parseDouble(param.get("GDLX").toString());
		JSONArray josn = JSONArray.fromObject(param.get("detailList"));

		List<Map<String, String>> list = pay.getParsePayList(josn);
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}

		// 获取元方案信息
		Map headMap = Dao.selectOne(basePath + "queryHeadByScheme", param);
		Map returnMap = las.jiexiScheme(headMap, param, PAY_ID, LXTQSQ, LXHJ);
		if (headMap != null) {
			Map headBaseInfoMap = Dao.selectOne(basePath + "queryHeadBaseInfo",
					param);
			headMap.putAll(headBaseInfoMap);
			headMap.put("PAY_ID", PAY_ID);
			headMap.put("STATUS", "1");
			headMap.put("START_DATE", param.get("START_DATE_CHANGE"));
			headMap.put("REPAYMENT_DATE", param.get("REPAYMENT_DATE_CHANGE"));
			headMap.put("SCHEME_CLOB", returnMap.get("SCHEME_CLOB").toString());
			headMap.put("MONEY_ALL", ZJHJ);

			if (LXTQSQ.equals("2")) {
				headMap.put("GDLX", LXHJ);
			}

			headMap.put("STATUS", "0");
			headMap.put("VERSION_CODE", "1");
			headMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE").toString());
			
			//取得方案下的客户保证金
			headMap.put("DEPOSIT_VALUE", Dao.selectDouble(basePath + "queryKhBzjByScheme", param));
			
			Dao.insert(basePath + "insertRent_Plan_head", headMap);
		}

		List listFY = (List) returnMap.get("listFY");
		for (int num = 0; num < listFY.size(); num++) {
			Map mapFY = (Map) listFY.get(num);
			Dao.insert("PayTask.payplan_detail", mapFY);
		}

		// 修改设备表状态并反更支付表ID
		param.put("PAY_ID", PAY_ID);
		Dao.update(basePath + "updateEqStatus", param);
		// 更新设备表
		System.out.println(param+"===============");
		Dao.insert(basePath + "updateProject_Equipment", param);

	}
	public void leaseApplicationPassAuto(String PAY_ID,String PAYLIST_CODE) {
		try {
			Map map = new HashMap();
			map.put("PAY_ID", PAY_ID);
			Map mapProjec_code = Dao.selectOne(basePath + "mapProjec_code", map);
			map.putAll(mapProjec_code);
			
			//业务类型
			String PLATFORM_TYPE="1";
			if(mapProjec_code!=null && mapProjec_code.get("PLATFORM_TYPE")!=null && !mapProjec_code.get("PLATFORM_TYPE").equals("")){
				PLATFORM_TYPE=mapProjec_code.get("PLATFORM_TYPE")+"";
			}
				map.put("PAYLIST_CODE", PAYLIST_CODE);
				// 支付表号写进设备表
				Dao.update(basePath + "updateEqPayListCodeByPayID", map);
			// 支付表号和开票协议写进支付表主表，和修改支付表主表状态
			System.out.println("-----------map="+map);
			Dao.update(basePath + "updateHeadByID", map);
			

			// 刷数据到应收期初表
			// 获取其他费用和首期款费用
			List QTFY = Dao.selectList(basePath + "queryQTFYByPayID", map);
			for (int i = 0; i < QTFY.size(); i++) {
				Map qtMap = (Map) QTFY.get(i);
				qtMap.put("CREATE_ID", Security.getUser().getId());
				Dao.insert("PayTask.insert_r_beginning", qtMap);
			}
			List ZJFY = Dao.selectList(basePath + "queryZJByPayID", map);
			for (int i = 0; i < ZJFY.size(); i++) {
				Map zjMap = (Map) ZJFY.get(i);
				zjMap.put("CREATE_ID", Security.getUser().getId());
				Dao.insert("PayTask.insert_r_beginning", zjMap);
			}

			// 如果该支付表为期初，则修改第一期为首期款
			// 查询支付方式
			Map PAYWAYMap = Dao.selectOne(basePath + "queryPayWayByPayID", map);
			if (PAYWAYMap != null && PAYWAYMap.get("PAY_WAY") != null
					&& !PAYWAYMap.get("PAY_WAY").equals("")) {
				Dao.update(basePath + "updateOneItemFlag", map);
			}
			
			//如果应收金额为0，则修改成已核销（不用核销）
			Dao.update(basePath + "updateFIRBFlag",map);
			
			if(PAYLIST_CODE.length()>0){
				//客户保证金写入保证金管理中，分方式处理
				Dao.insert(basePath+"insertBZJPoolInfo", PAYLIST_CODE);
				//DB客户保证金写入保证金管理中
				Dao.insert(basePath+"insertDBBZJPoolInfo", PAYLIST_CODE);
			}

			// 无需发货，需像发货中添加一天数据（并像验收表添加一天数据）
			String SENDNOTICEID = Dao.getSequence("SEQ_EQUIPMENT_SENDNOTICE");
			map.put("SENDNOTICEID", SENDNOTICEID);
			map.put("USERNAME", Security.getUser().getName());
			map.put("CREATE_ID", Security.getUser().getId());
			String CODE = CodeService.getCode("发货编号",
					mapProjec_code.get("CUST_ID") + "",
					mapProjec_code.get("PROJECT_ID") + "");
			map.put("SEND_LEASE_CODE", CODE);
			map.put("PAY_CODE", PAYLIST_CODE);
			Dao.insert(basePath + "doAddDeliveryProduct", map);
			Dao.insert(basePath + "insertRECEIPT", map);

			// 写数据到跟踪表
			// 查询项目信息
			Map<String,Object> baseInfo = Dao.selectOne("bpm.status.queryProjectInfoPay",
					PAY_ID);

			baseInfo.put("USER_NAME", Security.getUser().getName());
			baseInfo.put("USER_CODE", Security.getUser().getCode());
			baseInfo.put("TYPE", "起租");
			baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			Map<String,Object> dateMap=Dao.selectOne("bpm.status.FIRSTDAYBYPAY", map);
			String MEMO = "已起租，请尽快收首期款";
			if(dateMap !=null && dateMap.get("FIRSTPAYDATE")!=null && !dateMap.get("FIRSTPAYDATE").equals("")){
				String FIRSTPAYDATE=dateMap.get("FIRSTPAYDATE").toString();
				MEMO="已起租，请在"+FIRSTPAYDATE+"前收首期款。";
				
			}
			else{
				MEMO = "已起租，请尽快收首期款";
			}
			baseInfo.put("MEMO", MEMO);
			Dao.insert("bpm.status.insertTrack", baseInfo);			

		} catch (Exception e) {
			throw new ActionException("生成支付表通过失败，请联系管理员", e);
		}
		List<Map<String, Object>> eqs = Dao
				.selectList(
						"buyCertificate.selectGenerateCertificateEquipment",
						PAY_ID);
		int generateCertificateFlag = 0;
		int updateEqFlag = 0 ;
		if (eqs != null && eqs.size() > 0) {
			Map<String,Object> eq = eqs.get(0) ;
			Map<String, Object> ceritficate = extractCeritficate(eq);
			try{
				generateCertificateFlag=Dao.insert("buyCertificate.insertCertificate", ceritficate);
				Map<String,Object> updateEq = extractUpdateEq(ceritficate) ;
				updateEq.put("EQUIPID", eq.get("ID")) ;
				updateEqFlag=Dao.update("buyCertificate.updateCertificate_id",updateEq) ;
				if (generateCertificateFlag<=0)
					throw new ActionException("生成合格证失败，请联系管理员");
				if (updateEqFlag<=0)
					throw new ActionException("生成合格证,反写设备表失败，请联系管理员");
			}catch(Exception e){
				Dao.rollback();
				throw new ActionException("生成合格证失败，请联系管理员");
			}
		}
	}
	
	private Map<String,Object> extractUpdateEq(Map<String,Object> param){
		Map<String,Object> result = new HashMap<String,Object>() ;
		result.put("CERTIFICATE_ID", param.get("CERTIFICATE_ID")) ;
		result.put("code", param.get("code")) ;
		result.put("status", 1) ;
		return result ;
	}
	
	private Map<String, Object> extractCeritficate(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("CERTIFICATE_ID", Dao.selectOne("buyCertificate.getCertificateid"));
		
		result.put("code", param.get("CERTIFICATE_NUM"));

		result.put("NO", Dao.selectOne("buyCertificate.getMaxno"));
		result.put("USERNAME", Security.getUser().getName());
		result.put("thingName", param.get("PRODUCT_NAME"));

		result.put("model", param.get("SPEC_NAME"));
		result.put("carSysmle", param.get("CAR_SYMBOL"));

		result.put("certificateDate", param.get("CERTIFICATE_DATE"));
		result.put("engineType", param.get("ENGINE_TYPE"));
		result.put("engineCode", param.get("ENGINE_CODE"));
		result.put("wholeEngineCode", param.get("WHOLE_ENGINE_CODE"));
		result.put("payid", param.get("PAY_ID"));

		return result;
	}
}
