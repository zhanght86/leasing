package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;


public class LeaseApplicationJbpm {
	private String basePath = "leaseApplication.";

	// 提取合格证
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
	
	private Map<String,Object> extractUpdateEq(Map<String,Object> param){
		Map<String,Object> result = new HashMap<String,Object>() ;
		result.put("CERTIFICATE_ID", param.get("CERTIFICATE_ID")) ;
		result.put("code", param.get("code")) ;
		result.put("status", 1) ;
		return result ;
	}

	// 生成支付表通过
	public void leaseApplicationPass(String PAY_ID) {
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
			
			String PAYLIST_CODE = "";
			
			if(PLATFORM_TYPE.equals("11") ){
				PAYLIST_CODE=mapProjec_code.get("PAYLIST_CODE")+"";
				map.put("PAYLIST_CODE", PAYLIST_CODE);
			}
			else{
				// 生成支付表号
				Map MapPayCode = (Map) Dao.selectOne(basePath + "createPayListCode", map);
				if (MapPayCode == null) {
					throw new ActionException("生成支付表号通过失败，请联系管理员");
				} else {
					PAYLIST_CODE = MapPayCode.get("PAYLIST_CODE").toString();
				}
				map.put("PAYLIST_CODE", PAYLIST_CODE);
				
				// 支付表号写进设备表
				Dao.update(basePath + "updateEqPayListCodeByPayID", map);
			}
			
			
			

			// TODO
			// 查询开票协议
			// String Billing_Agreement="";
			// Map
			// BillingMap=(Map)Dao.selectOne(basePath+"queryBilling_Agreement",
			// map);
			// if(BillingMap==null){
			// throw new ActionException("开票协议没有录入不能通过，请联系管理员");
			// }
			// else{
			// Billing_Agreement=BillingMap.get("BILLING_AGREEMENT_ID").toString();
			// }
			// map.put("BILLING_AGREEMENT_ID", Billing_Agreement);
			//
			// 支付表号和开票协议写进支付表主表，和修改支付表主表状态
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
			
			if(PAYLIST_CODE.length()>0){
				//客户保证金写入保证金管理中，分方式处理
				Dao.insert(basePath+"insertBZJPoolInfo", PAYLIST_CODE);
				//DB客户保证金写入保证金管理中
				Dao.insert(basePath+"insertDBBZJPoolInfo", PAYLIST_CODE);
			}

			// 写数据到跟踪表
			// 查询项目信息
			Map baseInfo = Dao.selectOne("bpm.status.queryProjectInfoPay",
					PAY_ID);

			baseInfo.put("USER_NAME", Security.getUser().getName());
			baseInfo.put("USER_CODE", Security.getUser().getCode());
			baseInfo.put("TYPE", "起租");
			baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			
			Map dateMap=Dao.selectOne("bpm.status.FIRSTDAYBYPAY", map);
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
		List<Map<String,Object>> eqs = Dao.selectList("buyCertificate.selectGenerateCertificateEquipment",
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

	// 生成支付表通过
	public void leaseApplicationPassLeft(String PAY_ID) {
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
			
			String PAYLIST_CODE = "";
			
			if(PLATFORM_TYPE.equals("11") ){
				PAYLIST_CODE=mapProjec_code.get("PAYLIST_CODE")+"";
				map.put("PAYLIST_CODE", PAYLIST_CODE);
			}
			else{
				// 生成支付表号
				Map MapPayCode = (Map) Dao.selectOne(basePath + "createPayListCode", map);
				if (MapPayCode == null) {
					throw new ActionException("生成支付表号通过失败，请联系管理员");
				} else {
					PAYLIST_CODE = MapPayCode.get("PAYLIST_CODE").toString();
				}
				map.put("PAYLIST_CODE", PAYLIST_CODE);
				
				// 支付表号写进设备表
				Dao.update(basePath + "updateEqPayListCodeByPayID", map);
			}
			

			// TODO
			// 查询开票协议
			// String Billing_Agreement="";
			// Map
			// BillingMap=(Map)Dao.selectOne(basePath+"queryBilling_Agreement",
			// map);
			// if(BillingMap==null){
			// throw new ActionException("开票协议没有录入不能通过，请联系管理员");
			// }
			// else{
			// Billing_Agreement=BillingMap.get("BILLING_AGREEMENT_ID").toString();
			// }
			// map.put("BILLING_AGREEMENT_ID", Billing_Agreement);
			//
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
			Map baseInfo = Dao.selectOne("bpm.status.queryProjectInfoPay",
					PAY_ID);

			baseInfo.put("USER_NAME", Security.getUser().getName());
			baseInfo.put("USER_CODE", Security.getUser().getCode());
			baseInfo.put("TYPE", "起租");
			baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			Map dateMap=Dao.selectOne("bpm.status.FIRSTDAYBYPAY", map);
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

	// 生成支付表驳回
	public void leaseApplicationNotPass(String PAY_ID) {
		try {
			Map map = new HashMap();
			map.put("PAY_ID", PAY_ID);
			
			
			Map mapProjec_code = Dao.selectOne(basePath + "mapProjec_code", map);
			
			//业务类型
			String PLATFORM_TYPE="1";
			if(mapProjec_code!=null && mapProjec_code.get("PLATFORM_TYPE")!=null && !mapProjec_code.get("PLATFORM_TYPE").equals("")){
				PLATFORM_TYPE=mapProjec_code.get("PLATFORM_TYPE")+"";
			}
			
			if(PLATFORM_TYPE.equals("11")){
				
				
				//基础数据
				Map mapbase=Dao.selectOne(basePath+"queryHeadInfoByPayId", map);
				//起租日和还款日
				Map mapDate=Dao.selectOne(basePath+"queryHeadDateByPayId", map);
				System.out.println("------------mapDate="+mapDate);
				//利息差
				double DateLXMoney=0d;
				double residualPrincipal=mapbase.get("FINANCE_TOPRIC")!=null ? Double.parseDouble(mapbase.get("FINANCE_TOPRIC").toString()):0d;
				double annualRate=mapbase.get("YEAR_INTEREST")!=null ? Double.parseDouble(mapbase.get("YEAR_INTEREST").toString()):0d;
				int LEASE_PERIOD=1;
				if(StringUtils.isNotBlank(mapbase.get("LEASE_PERIOD")))
					LEASE_PERIOD=Integer.parseInt(mapbase.get("LEASE_PERIOD")+"");
				String PAY_WAY=mapbase.get("PAY_WAY")+"";
				PayTaskService pay=new PayTaskService();
				DateLXMoney=pay.getDateLiXiMoney(mapDate.get("START_DATE")+"", mapDate.get("REPAYMENT_DATE")+"", residualPrincipal, annualRate,LEASE_PERIOD,PAY_WAY);
				System.out.println("------------DateLXMoney="+DateLXMoney);
				if(DateLXMoney>0){
					map.put("DateLXMoney", DateLXMoney);
					Dao.update(basePath+"updateDetailByPayIDOne", map);
				}
				
				Dao.update(basePath+"updateDetailByPayID", map);
				
				Dao.update(basePath+"updateRentPlanStartDateNewNotPass",map);
				Dao.update(basePath+"updateEqByPayIdNotPass",map);
			}
			else{
				// //查询开票协议
				// String Billing_Agreement="";
				// Map
				// BillingMap=(Map)Dao.selectOne(basePath+"queryBilling_Agreement",
				// map);
				// if(BillingMap==null){
				// throw new ActionException("开票协议没有录入不能通过，请联系管理员");
				// }
				// else{
				// Billing_Agreement=BillingMap.get("BILLING_AGREEMENT_ID").toString();
				// }

				// 设备表更改状态，清空pay_id和开票协议ID
				Dao.update(basePath + "updateEQNotPass", map);

				// TODO 等待开票协议确定后删除
				// 删除开票协议

				// 删除支付表头表
				Dao.delete(basePath + "deletePlanHead", map);

				// 删除支付表子表
				Dao.delete(basePath + "deletePlanDetail", map);
			}
			

		} catch (Exception e) {
			throw new ActionException("生成支付表驳回失败，请联系管理员");
		}
	}

	/**
	 * 判断发货申请实付录入发货信息
	 * 
	 * @param DOSSIER_APPLYID
	 * @throws Exception
	 * @author:King 2012-11-8
	 */
	public void isLeaseApply(String PAY_ID) throws Exception {
		Map map = new HashMap();
		map.put("PAY_ID", PAY_ID);
		int COUNTNUM = Dao.selectInt(basePath + "LeaseSendStatus11", map);
		if (COUNTNUM == 0) {
			throw new Exception("请录入发货信息！");
		}

		Map mapPayWay = (Map) Dao.selectOne(basePath + "queryPayWayByPayId",
				map);
		String PAY_WAY = mapPayWay.get("PAY_WAY") != null ? mapPayWay.get(
				"PAY_WAY").toString() : "1";
		Map fundMap = new HashMap();
		if (PAY_WAY.equals("2") || PAY_WAY.equals("4") || PAY_WAY.equals("6")) {// 期初
			fundMap = Dao.selectOne(
					basePath + "queryBeginByCodeMoneyAllPayWay", mapPayWay);
		} else {// 期末
			fundMap = Dao.selectOne(basePath + "queryBeginByCodeMoneyAll",
					mapPayWay);
		}
		double money = Double.parseDouble(fundMap.get("MONEY").toString());// 应收
		double RECEIVE_MONEY = Double.parseDouble(fundMap.get("RECEIVE_MONEY")
				.toString());// 实收
		if (money > RECEIVE_MONEY) {
			throw new Exception("首期款没有付清，不能发货申请！");
		}
	}

	public void isSQApply(String PAY_ID) throws Exception {
		Map map = new HashMap();
		map.put("PAY_ID", PAY_ID);

		Map mapPayWay = (Map) Dao.selectOne(basePath + "queryPayWayByPayId",
				map);
		String PAY_WAY = mapPayWay.get("PAY_WAY") != null ? mapPayWay.get(
				"PAY_WAY").toString() : "1";
		Map fundMap = new HashMap();
		if (PAY_WAY.equals("2") || PAY_WAY.equals("4") || PAY_WAY.equals("6")) {// 期初
			fundMap = Dao.selectOne(
					basePath + "queryBeginByCodeMoneyAllPayWay", mapPayWay);
		} else {// 期末
			fundMap = Dao.selectOne(basePath + "queryBeginByCodeMoneyAll",
					mapPayWay);
		}
		double money = Double.parseDouble(fundMap.get("MONEY").toString());// 应收
		double RECEIVE_MONEY = Double.parseDouble(fundMap.get("RECEIVE_MONEY")
				.toString());// 实收
		if (money > RECEIVE_MONEY) {
			throw new Exception("首期款没有付清，不能通过！");
		}
	}

	public void LeaseSendStatus(String PAY_ID) throws Exception {
		Map map = new HashMap();
		map.put("PAY_ID", PAY_ID);
		int COUNTNUM = Dao.selectInt(basePath + "LeaseSendStatus", map);
		if (COUNTNUM == 0) {
			throw new Exception("请先导出发货通知单！");
		}
	}

	// 整个流程审批通过,验证发货单是否保存和是否上传验收单
	public void LeaseSendPass(String PAY_ID) throws Exception {
		Map map = new HashMap();
		map.put("PAY_ID", PAY_ID);
		// 先判断验收单是否保存和是否上传验收单
		int num = Dao.selectInt(basePath + "queryYSPass", map);
		if (num == 0) {
			throw new Exception("请先录入实际收货时间，并保存！");
		} else {
			// 查询发货验收单是否有录入，没有录入不让过
			int number = Dao.selectInt(basePath + "queryYSPass11", map);
			if (number == 0) {
				throw new Exception("请先上传验收单！");
			} else {
				// 整个流程审批通过（修改发货单状态，修改设备表状态）
				map.put("STATUS", 1);
				Dao.update(basePath + "updateSendStatus", map);
				Dao.update(basePath + "updateEQByPayId", map);
			}

		}

	}
	public static void main(String[] args) {
		System.out.println(0<0);
	}
	// 生成支付表通过
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
			
			/*String PAYLIST_CODE = "";
			
			if(PLATFORM_TYPE.equals("11") ){
				PAYLIST_CODE=mapProjec_code.get("PAYLIST_CODE")+"";
				map.put("PAYLIST_CODE", PAYLIST_CODE);
			}
			else{
				// 生成支付表号
				Map MapPayCode = (Map) Dao.selectOne(basePath + "createPayListCode", map);
				if (MapPayCode == null) {
					throw new ActionException("生成支付表号通过失败，请联系管理员");
				} else {
					PAYLIST_CODE = MapPayCode.get("PAYLIST_CODE").toString();
				}*/
				map.put("PAYLIST_CODE", PAYLIST_CODE);
				
				// 支付表号写进设备表
				Dao.update(basePath + "updateEqPayListCodeByPayID", map);
			//}
			

			// TODO
			// 查询开票协议
			// String Billing_Agreement="";
			// Map
			// BillingMap=(Map)Dao.selectOne(basePath+"queryBilling_Agreement",
			// map);
			// if(BillingMap==null){
			// throw new ActionException("开票协议没有录入不能通过，请联系管理员");
			// }
			// else{
			// Billing_Agreement=BillingMap.get("BILLING_AGREEMENT_ID").toString();
			// }
			// map.put("BILLING_AGREEMENT_ID", Billing_Agreement);
			//
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
			Map baseInfo = Dao.selectOne("bpm.status.queryProjectInfoPay",
					PAY_ID);

			baseInfo.put("USER_NAME", Security.getUser().getName());
			baseInfo.put("USER_CODE", Security.getUser().getCode());
			baseInfo.put("TYPE", "起租");
			baseInfo.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
			Map dateMap=Dao.selectOne("bpm.status.FIRSTDAYBYPAY", map);
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

	
}
