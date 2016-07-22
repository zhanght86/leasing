package com.pqsoft.fundNotEBank.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundEbank.service.FundEbankService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;

public class FundNotEBankService {

	FundEbankService eService = new FundEbankService();
	private final String xmlPath = "fundNotEBank.";

	/****************************************************** 资金扣划-非网银-申请 *****************************************************/
	
	/**
	 * 查询首期款-非网银申请数据 findDeductData
	 * 
	 * @date 2013-9-24 上午11:04:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgDeductData(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		String org_id = BaseUtil.getSupOrgChildren();
		map.put("org_id", org_id);
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "toMgDeductData", map);
		List<Object> hexiao_satus = (List) new DataDictionaryMemcached().get("核销状态");
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			json.put("FI_PAY_MONEY", m.get("FI_PAY_MONEY"));// 应付金额
			json.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));// 应付时间
			json.put("FI_REALITY_MONEY", m.get("FI_REALITY_MONEY"));// 实付金额
			json.put("FI_ACCOUNT_DATE", m.get("FI_ACCOUNT_DATE"));// 实付日期
			json.put("FI_APP_CODE", m.get("FI_APP_CODE"));// 申请人编号
			json.put("FI_APP_NAME", m.get("FI_APP_NAME"));// 申请人
			json.put("FI_APP_DATE", m.get("FI_APP_DATE"));// 首期时间
			// json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态
			if (hexiao_satus != null) {
				for (int j = 0; j < hexiao_satus.size(); j++) {
					Map<String, Object> hexiao = (Map<String, Object>) hexiao_satus.get(j);
					if (hexiao.get("CODE").equals(m.get("FI_STATUS").toString())&&("0".equals(m.get("FI_STATUS").toString())||"4".equals(m.get("FI_STATUS").toString()))) {
						json.put("FI_STATUS", hexiao.get("FLAG"));
					}
				}
			}
			json.put("FI_REMARK", m.get("FI_REMARK"));// 备注
			array.add(JSONObject.fromObject(json));
		}
		page.addDate(array, Dao.selectInt(xmlPath + "toMgDeductCount", map));// 计数
		return page;
	}

	/**
	 * 获取申请资金扣划数据 toMgDeductCount
	 * 
	 * @date 2013-9-25 下午02:50:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toGetAppData(Map<String, Object> map) {
		Page page = new Page(map);
		
		Map<String,Object> SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		
		// 首期数据获取
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "toGetAppData", map);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			json.put("PRO_CODE", m.get("PRO_CODE"));// 项目编号
			json.put("CLIENT_ID", m.get("CLIENT_ID"));// 承租人编号
			json.put("CLIENT_NAME", m.get("CLIENT_NAME"));// 承租人
			json.put("PRODUCT_NAME", m.get("PRODUCT_NAME"));// 租赁物类型
			json.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));// 支付表编号
			json.put("COMPANY_NAME", m.get("COMPANY_NAMES"));// 厂商名称
			json.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC"));// 租赁物购买价款
			json.put("FIRST_PAYMENT_TYPE", m.get("FIRST_PAYMENT_TYPE"));// 首期款付款方式
			json.put("OTHER_MONEY", m.get("OTHER_MONEY"));// 其他费用
			json.put("DB_MONEY", m.get("DB_MONEY"));// DB保证金
			json.put("CS_MONEY", m.get("CS_MONEY"));// 厂商保证金     
			json.put("YS_MONEY", m.get("YS_MONEY"));// 应收款金额
			json.put("BCYS_MONEY", m.get("BCYS_MONEY1"));// 本次应收款金额
			json.put("REALITY_MONEY", m.get("BCYS_MONEY1"));// 实际款金额
			//json.put("VALUE_STR", m.get("VALUE_STR"));// 放款方式
			json.put("FIRST_MONEY", m.get("FIRST_MONEY"));// 首期款
			json.put("PAYMENT_STATUS", m.get("PAYMENT_STATUS"));// 首期放款政策
			array.add(JSONObject.fromObject(json));
		}
		page.addDate(array, Dao.selectInt(xmlPath + "toGetDeductCount", map));// 计数
		return page;
	}

	/**
	 * 获取厂商 toGetCompany
	 * 
	 * @date 2013-9-25 下午08:29:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetCompany(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "toGetCompany", map);
	}

	/**
	 * 获取租赁物类型 toGetProduct
	 * 
	 * @date 2013-9-25 下午08:30:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetProduct(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "toGetProduct", map);
	}
	
	

	/**
	 * 提交申请数据 doInsertAppData
	 * 
	 * @date 2013-9-26 下午07:15:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAppData(Map<String, Object> map) {
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");

		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));//基础数据:供应商，付款方式，付款日期，应付款金额，实际付款金额，项目数量

		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));//申请扣划的客户的信息

		int i = 0;

		baseData.put("HEAD_ID", HEAD_ID);
		baseData.put("USERCODE", map.get("USERCODE"));//用户编号
		baseData.put("USERNAME", map.get("USERNAME"));//用户姓名
		baseData.put("USERID", Security.getUser().getId());//用戶編號
		Map<String,Object> org_id = Dao.selectOne(xmlPath+"getOrgId", baseData);
		
		//baseData.put("FI_FLAG", 1);// 核销方式 首期款-非网银
		if(org_id!=null){
			baseData.put("FI_ORG_ID", org_id.get("ORG_ID"));
		}
		
		//获取供应商id和供应商名称
		ManageService ms = new ManageService();
		Map<String,Object> supp = (Map<String,Object>) ms.getSupByUserId(Security.getUser().getId());
		
		if(supp != null && supp.get("SUP_ID") != null){
			baseData.put("SUP_ID", supp.get("SUP_ID"));//供应商id
			baseData.put("SUPPLIER_NAME", supp.get("SUP_SHORTNAME"));//供应商简称
		}
		
		i = this.doInsertAppBaseData(baseData);
		if (i > 0) {
			if (detailData != null) {
				for (int k = 0; k < detailData.size(); k++) {
					Map<String, Object> m = (Map<String, Object>) detailData.get(k);

					m.put("USERCODE", map.get("USERCODE"));
					m.put("FI_PAY_DATE", map.get("FI_PAY_DATE"));
					m.put("FI_FLAG", baseData.get("FI_FLAG"));
					m.put("FI_TAGE_ID", baseData.get("FI_TAGE_ID"));
					m.put("PROJECT_ID", m.get("PROJECT_HEAD_ID"));
					
					if(Double.parseDouble(m.get("REALITY_MONEY").toString()) == Double.parseDouble(m.get("BCYS_MONEY").toString())){
						doInsertAppDetail1(m, HEAD_ID, i);
					}else {
						m.put("REALITY_MONEY",  m.get("REALITY_MONEY"));
						doInsertAppDetail_1(m, HEAD_ID, i);
					}			
				}
				return i;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 项目期初表数据刷入资金明细表, 当首期款支付方式为网银支付时  足额支付
	 * 
	 * @param m
	 *            项目ID或支付表code
	 * @param HEAD_ID
	 *            资金主表ID
	 * @param i
	 *            项目数
	 * @author: 吴剑东
	 * @date: 2013-10-8 下午12:30:43
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAppDetail(Map<String, Object> m, String HEAD_ID, int i) {
		List<Map<String, Object>> detail = new ArrayList();
		m.put("PROJECT_ID", m.get("PROJECT_HEAD_ID"));
		Map<String,Object> pay_way = Dao.selectOne(xmlPath+"getRentPayWayWY",m);
		
		
		if(pay_way!=null){
			if(("2".equals(pay_way.get("PAY_WAY")+"")||"4".equals(pay_way.get("PAY_WAY")+"")||"6".equals(pay_way.get("PAY_WAY")+""))){
				m.put("benjin","本金");
				List<Map<String,Object>> rent = Dao.selectList(xmlPath+"getRent",m);
				if(rent != null){
					for(int a=0; a<rent.size(); a++){
						Map<String,Object> rent_ = rent.get(a);
						rent_.put("D_FUND_ID",HEAD_ID);
						rent_.put("D_PROJECT_CODE", m.get("PRO_CODE"));
						rent_.put("D_TO_THE_PAYEE", m.get("USERCODE"));
						rent_.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
						rent_.put("D_CLIENT_NAME", m.get("CLIENT_NAME"));
						// 逐条刷入资金明细表
						i = Dao.insert(xmlPath + "doInsertAppDetail", rent_);
					}
				}
			}
		}
		
		
		
		detail = Dao.selectList(xmlPath + "toFindAppData", m);
		
		
		if (detail != null) {
			for (Object obj : detail) {
				Map<String, Object> d_data = (Map<String, Object>) obj;
				
				d_data.put("D_BEGING_ID", d_data.get("D_BEGING_ID"));
				d_data.put("D_PAY_PROJECT", d_data.get("D_PAY_PROJECT"));
				d_data.put("D_PAY_MONEY", d_data.get("D_PAY_MONEY"));
				d_data.put("D_PROJECT_CODE", d_data.get("D_PROJECT_CODE"));
				d_data.put("D_FUND_ID", HEAD_ID);
				d_data.put("D_TO_THE_PAYEE", m.get("USERCODE"));
				d_data.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
				d_data.put("D_CLIENT_NAME", m.get("CLIENT_NAME"));
				d_data.put("D_PROJECT_CODE", m.get("PRO_CODE"));
				d_data.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));
				d_data.put("D_RECEIVE_MONEY", d_data.get("D_PAY_MONEY"));
				d_data.put("PROJECT_HEAD_ID", m.get("PROJECT_HEAD_ID"));
				
				// 逐条刷入资金明细表
				Dao.insert(xmlPath + "doInsertAppDetail", d_data);
				
			}
//			m.put("FIRST_APP_STATE", "1");
//			// 修改项目主表首付款状态为已申请
//			i = Dao.update(xmlPath + "doUpdateProject", m);
		}
		return i;
	}
	
	
	/**
	 * 项目期初表数据刷入资金明细表,  
	 * @param  
	 *   PAYMENT_STATUS="3" : 当放款方式为部分差额放款时，不核销起租租金和留购价款
	 *     
	 *   PAYMENT_STATUS="4" : 当放款方式为DB差额放款时，不核销DB保证金
	 *   
	 *    1、 项目id      PROJECT_HEAD_ID
	 *    2、 申请表单id  HEAD_ID
	 *    3、放款政策           PAYMENT_STATUS  
	 * @date 2013-12-7 上午12:47:48
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAppDetail1(Map<String, Object> m, String HEAD_ID, int i) {
		
		List<Map<String, Object>> detail = new ArrayList();
		m.put("PROJECT_ID", m.get("PROJECT_HEAD_ID"));
		System.out.println("wjd--m--"+m);
		detail = Dao.selectList(xmlPath + "toFindAppData2", m);
		
		//放款方式判断， 当为起初放款时加入第一期租金
		Map<String,Object> pay_way = Dao.selectOne(xmlPath+"getRentPayWay",m);
		if(pay_way!=null){
			if("2".equals(pay_way.get("PAY_WAY")+"")||"4".equals(pay_way.get("PAY_WAY")+"")||"6".equals(pay_way.get("PAY_WAY")+"")){
				m.put("benjin","本金");
				Map<String,Object> rent = Dao.selectOne(xmlPath+"getRent",m);
				if(rent != null){
					detail.add(rent);//第一期租金加入明细项
				}
			}
		}
		
		if (detail != null) {
			for (Object obj : detail) {
				Map<String, Object> d_data = (Map<String, Object>) obj;
				
				d_data.put("D_BEGING_ID", d_data.get("D_BEGING_ID"));
				d_data.put("D_PAY_PROJECT", d_data.get("D_PAY_PROJECT"));
				d_data.put("D_PAY_MONEY", d_data.get("D_PAY_MONEY"));
				d_data.put("D_PROJECT_CODE", d_data.get("D_PROJECT_CODE"));
				d_data.put("D_FUND_ID", HEAD_ID);
				d_data.put("D_TO_THE_PAYEE", m.get("USERCODE"));
				d_data.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
				d_data.put("D_CLIENT_NAME", m.get("CLIENT_NAME"));
				d_data.put("D_PROJECT_CODE", m.get("PRO_CODE"));
				d_data.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));
				d_data.put("D_RECEIVE_MONEY", d_data.get("D_PAY_MONEY"));
				d_data.put("PROJECT_HEAD_ID", m.get("PROJECT_HEAD_ID"));
				
				// 逐条刷入资金明细表
				Dao.insert(xmlPath + "doInsertAppDetail", d_data);
				
			}
			// 修改项目主表首付款状态为已申请
			Dao.update(xmlPath + "doUpdateProjectByApp", m);
		}
		
		return i;
	}
	
	/**
	 * 当项目为不足额申请时，将实际申请金额分别分配到首期款的每一个项目中去
	 * 分配原则： 按照应收期初表中item_flag状态在"1","3"."4"的项目中”BEGINNING_ID“序列有大到小的顺序分配申请金额
	 * 
	 * @param  m
	 *         项目id或支付表code或首期款支付方式或实际申请额度
	 * @param  HEAD_ID
	 *        申请单id
	 * @paran   i
	 *         项目数量
	 * @date 2013-10-26 下午02:34:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertAppDetail_1(Map<String, Object> m, String HEAD_ID, int i) {
		DecimalFormat df = new DecimalFormat (".##");
		double reality_money = m.get("REALITY_MONEY")!=null?Double.parseDouble(m.get("REALITY_MONEY").toString()):Double.parseDouble("0.00");//实际支付金额
//		double bcys_money = m.get("BCYS_MONEY")!=null?Double.parseDouble(m.get("BCYS_MONEY").toString()):Double.parseDouble("0.00");//应收金额					
		double sy_money = reality_money;
		
		//放款方式判断， 当为起初放款时加入第一期租金
		Map<String,Object> pay_way = Dao.selectOne(xmlPath+"getRentPayWay",m);
		
		
		
		List<Map<String,Object>> detail = new ArrayList<Map<String,Object>>();
		detail = Dao.selectList(xmlPath+"toFindAppData2", m);
		
		if(pay_way!=null){
			if("2".equals(pay_way.get("PAY_WAY")+"")||"4".equals(pay_way.get("PAY_WAY")+"")||"6".equals(pay_way.get("PAY_WAY")+"")){
				m.put("benjin","本金");
				Map<String,Object> rent = Dao.selectOne(xmlPath+"getRent",m);
				if(rent != null){
					detail.add(rent);//第一期租金加入明细项
				}
			}
		}
		
		
		if(detail!=null){
			for(int k=0; k<detail.size(); k++){
				Map<String,Object> d_data = detail.get(k);
				d_data.put("D_BEGING_ID", d_data.get("D_BEGING_ID"));
				d_data.put("D_PAY_PROJECT", d_data.get("D_PAY_PROJECT"));
				d_data.put("D_PROJECT_CODE", d_data.get("D_PROJECT_CODE"));
				d_data.put("D_FUND_ID", HEAD_ID);
				d_data.put("D_TO_THE_PAYEE", m.get("USERCODE"));
				d_data.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
				d_data.put("D_CLIENT_NAME", m.get("CLIENT_NAME"));
				d_data.put("D_PROJECT_CODE", m.get("PRO_CODE"));
				d_data.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));
				d_data.put("PROJECT_HEAD_ID", m.get("PROJECT_HEAD_ID"));
				
				if(k==0){//当时第一次循环
					
					//在首次循环时：当应收金额小于实际申请额度，则向申请明细表中插入当期应收金额， 并计算当期的剩余额度
					if(Double.parseDouble(d_data.get("D_PAY_MONEY").toString())<reality_money){						
						
						d_data.put("D_PAY_MONEY", d_data.get("D_PAY_MONEY"));//应收金额
						d_data.put("D_RECEIVE_MONEY", d_data.get("D_PAY_MONEY"));//实收金额							
						sy_money = reality_money-Double.parseDouble(d_data.get("D_PAY_MONEY").toString());//计算剩余额度
						
						// 逐条刷入资金明细表
						i = Dao.insert(xmlPath + "doInsertAppDetail", d_data);	
					}else {////在首次循环时：当应收金额大于实际申请额度，则向申请明细表中插入当期应收金额=实际申请额度， 并修改项目的状态
						
						d_data.put("D_PAY_MONEY", df.format(reality_money));//应收金额
						d_data.put("D_RECEIVE_MONEY", df.format(reality_money));//实收金额
						
						// 逐条刷入资金明细表
						i = Dao.insert(xmlPath + "doInsertAppDetail", d_data);
						if (i > 0) {
							// 修改项目主表首付款状态为已申请
							Dao.update(xmlPath + "doUpdateProjectByApp", d_data);
							return 0;
						} else {
							return 0;
						}
					}					
				}else {//当k>1时
					
					//当应收金额小于应收的剩余额度时且不是最后一个项目时，插入申请单明细
					if(Double.parseDouble(d_data.get("D_PAY_MONEY").toString())<sy_money && sy_money!=0&&k!=detail.size()-1){
						d_data.put("D_PAY_MONEY", d_data.get("D_PAY_MONEY"));//应收金额
						d_data.put("D_RECEIVE_MONEY", d_data.get("D_PAY_MONEY"));//实收金额							
						sy_money = sy_money-Double.parseDouble(d_data.get("D_PAY_MONEY").toString());//计算剩余额度
						// 逐条刷入资金明细表
						i = Dao.insert(xmlPath + "doInsertAppDetail", d_data);
					}else{//当应收金额大于或等于应收的剩余额度时且是最后一个项目时，插入申请单明细，并变更项目状态
						d_data.put("D_PAY_MONEY", df.format(sy_money));//应收金额
						d_data.put("D_RECEIVE_MONEY", df.format(sy_money));//实收金额
						
						// 逐条刷入资金明细表
						i = Dao.insert(xmlPath + "doInsertAppDetail", d_data);
						if (i > 0) {
							// 修改项目主表首付款状态为已申请
							Dao.update(xmlPath + "doUpdateProjectByApp", d_data);
							
						}
					}
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * 添加付款单 doInsertAppBaseData
	 * 
	 * @date 2013-9-27 下午03:41:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertAppBaseData(Map<String, Object> map) {
		map.put("FI_STATUS",0);
		return Dao.insert(xmlPath + "doInsertFundHead", map);
	}

	/**
	 * 查看申请明细 getFundDetail
	 * 
	 * @date 2013-9-30 上午10:27:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getFundDetail(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "getFundDetail", map);
	}

	/**
	 * 提交申请单 dosubmitApp
	 * 
	 * @date 2013-9-29 下午05:14:12
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int dosubmitApp(Map<String, Object> map) {
		int i = 0;

		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));

		if (detailData != null) {
			for (int k = 0; k < detailData.size(); k++) {
				Map<String, Object> m = (Map<String, Object>) detailData.get(k);
				m.put("FI_STATUS", 5);
				i = Dao.update(xmlPath + "dosubmitApp", m);
			}
			if (i > 0) {
				return i;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/****************************************************** 资金扣划-非网银-核销 ********************************************************/

	/**
	 * 查询首期款-非网银核销数据 findDeductData
	 * 
	 * @date 2013-9-24 上午11:04:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgAppAlready(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		String org_id = BaseUtil.getSupOrgChildren();
		map.put("org_id", org_id);
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "toMgAppAlready", map);
		List<Object> hexiao_satus = (List) new DataDictionaryMemcached().get("核销状态");
		List<Object> PAY_TYPE = (List) new DataDictionaryMemcached().get("付款方式");
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			// json.put("FI_PAY_TYPE", m.get("FI_PAY_TYPE"));//付款方式

			// 付款方式
			if (PAY_TYPE != null) {
				for (int k = 0; k < PAY_TYPE.size(); k++) {
					Map<String, Object> c = (Map<String, Object>) PAY_TYPE.get(k);
					if (c.get("CODE").equals(m.get("FI_PAY_TYPE").toString())) {
						json.put("FI_PAY_TYPE", c.get("FLAG"));
					}
				}
			}

			json.put("FI_PAY_MONEY", m.get("FI_PAY_MONEY"));// 应付金额
			json.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));// 应付时间
			json.put("SUP_ID", m.get("SUP_ID"));// 供应商id
			json.put("PRO_CODE", m.get("PRO_CODE"));// 供应商id
			json.put("SUPPLIER_NAME", m.get("SUPPLIER_NAME"));// 供应商名称
			json.put("FI_PROJECT_NUM", m.get("FI_PROJECT_NUM"));// 项目数量
			json.put("FI_REALITY_MONEY", m.get("FI_REALITY_MONEY"));// 实付金额
			json.put("FI_REALITY_BANK", m.get("FI_REALITY_BANK"));// 实付银行
			json.put("FI_ACCOUNT_DATE", m.get("FI_ACCOUNT_DATE"));// 实付日期
			json.put("FI_APP_CODE", m.get("FI_APP_CODE"));// 申请人编号
			json.put("FI_APP_NAME", m.get("FI_APP_NAME"));// 申请人
			json.put("FI_APP_DATE", m.get("FI_APP_DATE"));// 申请时间
			json.put("FI_CHECK_CODE", m.get("FI_CHECK_CODE"));// 核销人编号
			json.put("FI_CHECK_NAME", m.get("FI_CHECK_NAME"));// 核销人
			json.put("FI_CHECK_DATE", m.get("FI_CHECK_DATE"));// 核销时间
			// json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态

			if (hexiao_satus != null) {// 核销状态判断
				for (int j = 0; j < hexiao_satus.size(); j++) {
					Map<String, Object> c = (Map<String, Object>) hexiao_satus.get(j);
					if (c.get("CODE").equals(m.get("FI_STATUS").toString())) {
						json.put("FI_STATUS", c.get("FLAG"));
					}
				}
			}

			json.put("FI_REMARK", m.get("FI_REMARK"));// 备注
			json.put("FI_SUPPLIERS_ID", m.get("FI_SUPPLIERS_ID"));

			array.add(JSONObject.fromObject(json));
		}

		page.addDate(array, Dao.selectInt(xmlPath + "toMgAppAlreadyCount", map));// 计数
		return page;
	}

	/**
	 * 查询申请单明细 getFundDetail
	 * 
	 * @date 2013-9-29 下午03:46:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getChedkedDetail(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "getFundDetail", map);

		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("FUND_ID", m.get("FUND_ID"));
			json.put("D_CLIENT_CODE", m.get("D_CLIENT_CODE"));// 客户编号
			json.put("D_CLIENT_NAME", m.get("D_CLIENT_NAME"));// 客户名称
			json.put("D_PAY_CODE", m.get("D_PAY_CODE"));// 支付表编号
			json.put("D_PROJECT_CODE", m.get("D_PROJECT_CODE"));// 项目编号
			json.put("PRODUCT_NAME", m.get("PRODUCT_NAME"));// 设备
			json.put("COMPANY_NAME", m.get("COMPANY_NAME"));// 供应商
			json.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC"));// 租赁物总价
			json.put("FIRST_MONEY", m.get("FIRST_MONEY"));// 首期款
			json.put("DB_MONEY", m.get("DB_MONEY"));// DB保证金
			json.put("CS_MONEY", m.get("CS_MONEY"));// 厂商保证金     
			json.put("OTHER_MONEY", m.get("OTHER_MONEY"));// 其他费用
			json.put("YS_MONEY", m.get("YS_MONEY"));// 应收金额
			json.put("PAYMENT_STATUS", m.get("PAYMENT_STATUS"));// 放款方式
			if("1".equals(m.get("VALUE_STR"))){
				json.put("VALUE_STR", "全额放款");
			}else{
				json.put("VALUE_STR", "差额放款");
			}
			json.put("RECEIVABLE", m.get("RECEIVABLE"));// 本次应收金额
			json.put("VERITABLE_MONEY", m.get("VERITABLE_MONEY"));// 本次实收金额
			array.add(JSONObject.fromObject(json));
		}

		page.addDate(array, 10);// 计数
		return page;
	}

	/**
	 * 根据资金编号查看资金申请单 toGetFundData
	 * 
	 * @date 2013-10-8 下午12:23:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetFundData(Map<String, Object> map) {
		return Dao.selectOne(xmlPath + "toGetFundData", map);
	}

	/**
	 * 跟新资金表头数据 doUpdateFHead
	 * 
	 * @date 2013-10-8 下午01:37:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doUpdateFHead(Map<String, Object> map) {
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		return Dao.update(xmlPath + "toUpdateFHead", baseData);
	}
	
	/**
	 * 根据资金编号获取挂账人
	 * getCustByFundId
	 * @date 2013-10-27 下午02:33:41
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getCustByFundId(Map<String,Object> map) {
		return Dao.selectList(xmlPath+"getCustByFundId", map);
	}
	
	public int checkFirstInfoByHeadId(Map<String,Object> map) {
		return Dao.selectInt(xmlPath+"checkFirstInfoByHeadId", map);
	}
	
	//通过fundId查询供应商List
	public List<Map<String,Object>> querySuppByFundID(Map<String,Object> map){
		return Dao.selectList("rentWrite.querySuppByFundID",map);
	}
	
	//通过fundId查询客户List
	public List<Map<String,Object>> queryCustByFundID(Map<String,Object> map){
		return Dao.selectList(xmlPath+"queryCustByFundID",map);
	}

	//求上次保存金额
	public double queryPoolMoneyDe(Map<String,Object> map){
		Map<String,Object> mapDate=Dao.selectOne(xmlPath+"querySvaePoolMoney", map);
		if(mapDate!=null)
		{
			return (mapDate!=null && mapDate.get("CANUSE_MONEY")!=null && !mapDate.get("CANUSE_MONEY").equals(""))?Double.parseDouble(mapDate.get("CANUSE_MONEY").toString()):0d;
		}
		else{
			return 0d;
		}
	}
	
	//求租金池剩余金额
	public double queryPoolMoney(Map<String,Object> map){
		Map<String,Object> mapDate=Dao.selectOne(xmlPath+"queryPoolTypeOwnerMoney", map);
		if(mapDate!=null)
		{
			return (mapDate!=null && mapDate.get("CANUSE_MONEY")!=null && !mapDate.get("CANUSE_MONEY").equals(""))?Double.parseDouble(mapDate.get("CANUSE_MONEY").toString()):0d;
		}
		else{
			return 0d;
		}
		
	}
	
	//查询是否有池子存在
	public int queryPoolNumber(Map<String,Object> map){
		return Dao.selectInt(xmlPath+"queryPoolNumber", map);
	}
	
	/**
	 * 跟新核销实时表， 用来记录每一次核销的详细情况 
	 * 
	 * @date 2013-10-8 下午03:07:13
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAccount(Map<String, Object> map) {

		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		@SuppressWarnings("unused")
		List<Map<String,Object>> getDetailData = JSONArray.fromObject(data.get("getDetailData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		
		
		//金额分拆
		String MONEYORDERSAVE=(baseData.get("MONEYORDERSAVE")!=null && !baseData.get("MONEYORDERSAVE").equals(""))?baseData.get("MONEYORDERSAVE").toString():"";
		if(MONEYORDERSAVE.length()>0){
			this.moneyFenChai(MONEYORDERSAVE,baseData.get("FUND_ID").toString());
		}
		
		
		//实际到账金额(实际客户打款金额)
		//Double FI_PAY_MONEY = baseData.get("FI_PAY_MONEY")!=null?Double.parseDouble(baseData.get("FI_PAY_MONEY").toString()):Double.parseDouble("0.00");
		
		//实付金额（申请金额）
		double FI_REALITY_ACCOUNT = baseData.get("FI_REALITY_ACCOUNT")!=null?Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()):Double.parseDouble("0.00");
		
		@SuppressWarnings("unused")
		double FI_REALITY_ACCOUNT_YUE = FI_REALITY_ACCOUNT;
		
		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt(xmlPath + "toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			//删除之前看有没有和租金池对应的数据，有的话先解冻
			Map poolMap=Dao.selectOne(xmlPath+"queryAcountPollById", baseData);
			if(poolMap!=null && poolMap.get("POOLIDS")!=null && !poolMap.get("POOLIDS").equals("")){
				Dao.update(xmlPath+"updatePOOLStatus",poolMap);
			}
			
			i = Dao.delete(xmlPath + "delAccountByHeadId", baseData);// 当用户重复点击“到账日期”，并修改对应数据后点击提交时先删除已存在的资金扣划明细
		}		
		
		List<Map<String, Object>> accountL = Dao.selectList(xmlPath + "toGetAccountList", baseData);// 查看申请单明细信息，
		// 为资金到账扣划明细插入数据做准备
		if (accountL != null) {
			// 遍历明细信息并插入
			for (int k = 0; k < accountL.size(); k++) {
				Map<String, Object> account = accountL.get(k);
				// 插入资金/租金扣划到账明细
				i = Dao.insert(xmlPath + "doInsertAccount", account);			
				}
		}
			
		return i;
	}
	
	/**
	 * 核销扣划: 当为不足额核销时， 调用资金池数据。 这是直接核销资金
	 * doInsertAccount_1
	 * @date 2013-11-4 下午05:42:39
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAccount_1(Map<String,Object> map){
		
		boolean flag = false;
		
		Map<String, Object> data = JSONObject.fromObject(map.get("data"));

		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		//List<Map<String,Object>> getDetailData = JSONArray.fromObject(data.get("getDetailData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		
		//付款单号
		String FUND_ID=baseData.get("FUND_ID").toString();
		
		//金额分拆
		String MONEYORDERSAVE = baseData.get("MONEYORDERSAVE")==null?"":baseData.get("MONEYORDERSAVE").toString();
		if(MONEYORDERSAVE.length()>0){
			this.moneyFenChai(MONEYORDERSAVE,baseData.get("FUND_ID").toString());
		}
		
		
		// 判断核销实时表中是否已经有实收明细， 如果有则先删除   否则直接添加
		int i = Dao.selectInt(xmlPath + "toGetAccountCount", baseData);

		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。

			//删除之前看有没有和租金池对应的数据，有的话先解冻
			Map<String,Object> poolMap=Dao.selectOne(xmlPath+"queryAcountPollById", baseData);
			if(poolMap!=null && poolMap.get("POOLIDS")!=null && !poolMap.get("POOLIDS").equals("")){
				Dao.update(xmlPath+"updatePOOLStatus",poolMap);
			}
			
			i = Dao.delete(xmlPath + "delAccountByHeadId", baseData);// 当用户重复点击“到账日期”，并修改对应数据后点击提交时先删除已存在的资金扣划明细
		}
		
		
		//实际到账金额(实际客户打款金额)
		//Double FI_PAY_MONEY = baseData.get("FI_PAY_MONEY")!=null?Double.parseDouble(baseData.get("FI_PAY_MONEY").toString()):Double.parseDouble("0.00");
		//实付金额（申请金额）
		Double FI_REALITY_ACCOUNT = baseData.get("FI_REALITY_ACCOUNT")!=null?Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()):0.00;
		//余款挂账人id
		//String FI_TAGE_ID=(baseData.get("FI_TAGE_ID")!=null && !baseData.get("FI_TAGE_ID").equals(""))?baseData.get("FI_TAGE_ID").toString():null;
		
		
		List<Map<String,Object>> finnList=new ArrayList<Map<String,Object>>();
		Map<String,Object> finnMap=new HashMap<String,Object>();
		
		Map<String,Object> poolData = Dao.selectOne(xmlPath+"queryFundHeadById1", baseData);//查看本次来款金额， 为余款挂账做准备
		finnMap.put("Money", poolData.get("FI_REALITY_ACCOUNT"));
		finnMap.put("TYPE", "贷方");
		finnMap.put("REMARK", "");
		finnList.add(finnMap);
		
		List<Map<String, Object>> accountL = Dao.selectList(xmlPath + "toGetAccountList", baseData);// 查看申请单明细信息，
		String[] proCodes = new String[accountL.size()];
		
		double FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT;//剩余来款
		
		for(int k=0; k<accountL.size(); k++){
			Map<String,Object> account = accountL.get(k);
			proCodes[k] = account.get("D_PROJECT_CODE")+"";
			
			if(flag){
				break;
			}
			
			//应付款金额
			double D_PAY_MONEY = Double.parseDouble(account.get("D_PAY_MONEY")==null&&"".equals(account.get("D_PAY_MONEY").toString())?"0":account.get("D_PAY_MONEY").toString());
			
			//当剩余金额>本次应收金额， 那么插入此次的应收金额
			if(FI_REALITY_ACCOUNT_YU >= D_PAY_MONEY){
				
				Dao.insert(xmlPath+"doInsertAccount", account);
				//剩余金额
				FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT_YU - D_PAY_MONEY;
			}else if(FI_REALITY_ACCOUNT_YU<D_PAY_MONEY){
				if(FI_REALITY_ACCOUNT_YU>0){
					account.put("FA_ACCOINT_MONEY", FI_REALITY_ACCOUNT_YU);
					Dao.insert(xmlPath+"doInsertAccount",account);
					FI_REALITY_ACCOUNT_YU=0d;
					
				}
				
				if(FI_REALITY_ACCOUNT_YU==0){//当剩余金额等于零时， 开始使用资金池的数据
					
					JSONArray listLi = JSONArray.fromObject(data.get("getPoolData"));
					for(int j=0;j<listLi.size();j++){
						if(flag){
							break;
						}
						
						Map<String,Object> pool = listLi.getJSONObject(j);
						if(pool!=null && baseData.get("FI_TAGE_ID")!=null && !baseData.get("FI_TAGE_ID").equals("")){
							if(pool.get("POOL_TYPE")!=null){
								double dichong_money=(pool.get("dichong_money")!=null && !pool.get("dichong_money").equals(""))?Double.parseDouble(pool.get("dichong_money").toString()):0d;
								//租金池
								finnMap.put("Money", dichong_money);
								finnMap.put("TYPE", "借方");
								finnMap.put("REMARK", "");
								finnList.add(finnMap);
								Map mappool=new HashMap();
								mappool.put("OWNER_ID", baseData.get("FI_TAGE_ID"));
								mappool.put("TYPE", pool.get("POOL_TYPE"));
								List listpool=Dao.selectList(xmlPath+"queryPoolTypeOwner", mappool);
								for(int h=0;h<listpool.size();h++){
									Map<String,Object> pList = (Map<String, Object>) listpool.get(h);
									if(pList.get("CANUSE_MONEY")!=null && !pList.get("CANUSE_MONEY").equals("")){
										double CANUSE_MONEY=Double.parseDouble(pList.get("CANUSE_MONEY").toString());
										if(dichong_money>0 && dichong_money>=CANUSE_MONEY){
											//冻结池子金额
											//插入数据到细表中
											account.put("FA_POOL_ID", pList.get("POOL_ID"));
											account.put("FA_ACCOINT_MONEY", CANUSE_MONEY);
											account.put("FA_CAN_USE_MONEY", CANUSE_MONEY);
											Dao.insert(xmlPath+"doInsertAccount", account);
											account.put("CANUSE_MONEY", 0);
											Dao.update(xmlPath+"updatePoolStateByfundId",account);
										}else{
											//冻结池子金额
											//插入数据到细表中
											account.put("FA_POOL_ID", pList.get("POOL_ID"));
											account.put("FA_ACCOINT_MONEY", dichong_money);
											account.put("FA_CAN_USE_MONEY", CANUSE_MONEY);
											Dao.insert(xmlPath+"doInsertAccount", account);
											account.put("CANUSE_MONEY", CANUSE_MONEY-dichong_money);
											Dao.update(xmlPath+"updatePoolStateByfundId",account);
											dichong_money=0;
										}
										
										if(dichong_money==0){
											flag=true;
											break;//跳出
										}
									}
								}
							}
						}
					}
					
					break;
				}
			}
		}
		
		
		
		// 更新申请表状态，核销时间， 核销银行
		int n = Dao.update(xmlPath + "doUpdateHeadStatus", baseData);
		int m = 0;
		if ( n > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;

			// 根据申请单id查看应收金额和实收总金额
			List<Map<String, Object>> detialL = Dao.selectList(xmlPath + "getDetailDataB", baseData);
			// 当不为空时跟新应收明细
			if (detialL != null) {
				for (int k = 0; k < detialL.size(); k++) {
					Map<String, Object> realMoney = detialL.get(k);
					float beginning_money = Float.parseFloat(realMoney.get("BEGINNING_MONEY").toString());// 应收金额
					float real_money = Float.parseFloat(realMoney.get("REAL_MONEY").toString());// 实收金额
					realMoney.put("BEGINNING_PAID", real_money);
					realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
					
					if (beginning_money == real_money) {// 当应收金额==实收金额时，应收明细中的核销状态变更为核销已完成(1);
						realMoney.put("BEGINNING_FLAG", 1);								
					} else {// 当应收金额!=实收金额时，应收明细中的核销状态为核销未完成(0)								
						realMoney.put("BEGINNING_FLAG", 0);
					}
					
					// 更新应收初期表数据：实收金额和核销状态。
					realMoney.put("REALITY_TIME", baseData.get("FI_ACCOUNT_DATE"));
					m = Dao.update(xmlPath + "doUpdateBeginning", realMoney);
					if(m>0){
						//若项目跟新成功， 向资金池中添加数据
						realMoney.put("USERCODE",map.get("USERCODE"));
						realMoney.put("SOURCE_ID",FUND_ID);
						
						realMoney.put("FA_ACCOINT_MONEY",realMoney.get("D_PAY_MONEY"));
					    this.doinsertPool(realMoney);
					}
				}
			}
		}
		

		// 付款单项目编号
		List<String> la = Arrays.asList(proCodes);
		Set<String> sl = new LinkedHashSet<String>();
		sl.addAll(la);
		for (Iterator iterator = sl.iterator(); iterator.hasNext();) {
			baseData.put("PRO_CODE", (String) iterator.next());
			baseData.put("deducted_id", baseData.get("PRO_CODE"));
			// 核销第一期租金和添加开票人信息   传入项目编号
			eService.doConfirmFirstRentByProId(baseData);
			
			// 更新项目首付款和DB保证金状态 核销日期
			Dao.selectOne(xmlPath+"updateFirstDBByProCode",baseData);
		}
		
		return i;
	}

	/**
	 * 在核销界面中勾选需要核销数据，核销变更申请表状态并更新应收期初表的实收金额和核销状态 doCheckedFund
	 * 
	 * @date 2013-10-8 下午05:25:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCheckedFund(Map<String, Object> map) {
		
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));
		
		int i = 0;
		// 失败判断标识
		int t = 0;
		int m = 0;
		if (detailData != null) {
			for (int j = 0; j < detailData.size(); j++) {
				Map<String, Object> de = detailData.get(j);
				de.put("USERCODE", map.get("USERCODE"));
				de.put("USERNAME", map.get("USERNAME"));
				de.put("FUND_ID", de.get("HEAD_ID"));// f付款单号
				
				// 更新申请表状态，核销时间， 核销银行
				de.put("FI_STATUS", "2");
				i = Dao.update(xmlPath + "doUpdateHeadStatus", de);
				if (i > 0) {// 若跟新成功， 回更应收起初表的实收金额和核销状态;
					// 根据申请单id查看应收金额和实收总金额
					List<Map<String, Object>> detialL = Dao.selectList(xmlPath + "getDetailData", de);

					String[] proCodes = new String[detialL.size()];
					// 当不为空时跟新应收明细
					if (detialL.size() > 0) {
						for (int k = 0; k < detialL.size(); k++) {
							Map<String, Object> realMoney = detialL.get(k);
							proCodes[k] = realMoney.get("PRO_CODE")+"";
							float beginning_money = Float.parseFloat(realMoney.get("BEGINNING_MONEY").toString());// 应收金额
							float real_money = Float.parseFloat(realMoney.get("REAL_MONEY").toString());// 实收金额
							realMoney.put("BEGINNING_PAID", real_money);
							realMoney.put("BEGINNING_ID", realMoney.get("FA_BEGING_ID"));
							
							if (beginning_money == real_money) {// 当应收金额==实收金额时，应收明细中的核销状态变更为核销已完成(1);
								realMoney.put("BEGINNING_FLAG", 1);								
							} else {// 当应收金额!=实收金额时，应收明细中的核销状态为核销未完成(0)								
								realMoney.put("BEGINNING_FLAG", 0);
							}
				
							// 更新应收初期表数据：实收金额和核销状态。
							realMoney.put("REALITY_TIME", de.get("FI_ACCOUNT_DATE"));
							m = Dao.update(xmlPath + "doUpdateBeginning", realMoney);
							if(m>0){
								//项目更新成功向资金池中插入数据
								realMoney.put("USERCODE",map.get("USERCODE"));
								realMoney.put("SOURCE_ID",de.get("HEAD_ID"));
								
								realMoney.put("FA_ACCOINT_MONEY",realMoney.get("FA_ACCOINT_MONEY"));
								this.doinsertPool(realMoney);
								
							}
						}
						
						
					}else{
						de.put("FI_STATUS", "3");
						t = Dao.update(xmlPath + "doUpdateHeadStatus", de);
						continue ;
					}
					
					// 付款单项目编号
					List<String> la = Arrays.asList(proCodes);
					Set<String> sl = new LinkedHashSet<String>();
					sl.addAll(la);
					for (Iterator iterator = sl.iterator(); iterator.hasNext();) {
						de.put("PRO_CODE", (String) iterator.next());
						de.put("deducted_id", de.get("PRO_CODE"));
						// 核销第一期租金和添加开票人信息   传入项目编号
						eService.doConfirmFirstRentByProId(de);
						
						// 更新项目首付款和DB保证金状态 核销日期
						Dao.selectOne(xmlPath+"updateFirstDBByProCode",de);
					}
					
					
					List finnList=new ArrayList();
					Map finnMap=new HashMap();
					
					//如果有余额挂账，向池子插入一条数据。为接口提供数据
					Map mapFund1=Dao.selectOne(xmlPath+"queryFundHeadById1",de);
					finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
					finnMap.put("TYPE", "贷方");
					finnMap.put("REMARK", "");
					finnList.add(finnMap);
					
					Map mapFund=Dao.selectOne(xmlPath+"queryFundHeadById",de);
					if(mapFund!=null){
						String FI_FLAG=mapFund.get("FI_FLAG").toString();
						if(FI_FLAG.equals("1")){//挂账到承租人资金池
							Map mapPool=new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));//申请单id
							mapPool.put("TYPE", 5);//类型
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));//基础额度
							mapPool.put("CANUSE_MONEY", mapFund.get("FI_TAGE_MONEY"));//可使用额度
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));//承租人id
							mapPool.put("USERCODE", Security.getUser().getCode());//操作人
							
							Dao.insert(xmlPath+"doInsertPool", mapPool);
						}
						else if(FI_FLAG.equals("6"))//挂账供应商电汇资金池
						{
							Map mapPool=new HashMap();
							mapPool.put("SOURCE_ID", mapFund.get("ID"));//申请单id
							mapPool.put("TYPE", 2);//类型
							mapPool.put("STATUS", 1);
							mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));//基础额度
							mapPool.put("CANUSE_MONEY", mapFund.get("FI_TAGE_MONEY"));//可使用额度
							mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));//承租人id
							mapPool.put("USERCODE", Security.getUser().getCode());//操作人
							
							Dao.insert(xmlPath+"doInsertPool", mapPool);
						}
						finnMap.put("Money", mapFund.get("FI_TAGE_MONEY"));
						finnMap.put("TYPE", "借方");
						finnMap.put("REMARK", "");
						finnList.add(finnMap);
					}
				}
			}
		}
		
		if(t == 0 && i>0 && m>0 ){
			return i+m;
		}else{
			Dao.rollback();
			return 0;
		}
	}
	
	/**
	 * 核销成功 向DB保证金池和承租人保证金池中出入数据
	 * doinsertPool
	 * @date 2013-11-4 下午07:43:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doinsertPool(Map<String,Object> poolMap){
		int i = 0;
		Map<String,Object> pool = new HashMap<String,Object>();
		//当项目名称不为空的时候
		if(poolMap!=null){
			if(poolMap.get("BEGINNING_NAME")!=null && poolMap.get("FA_ACCOINT_MONEY")!=null && !"".equals(poolMap.get("FA_ACCOINT_MONEY"))){
				if(poolMap.get("BEGINNING_NAME").toString().equals("DB保证金")){//当项目为DB保证金时， 插入DB保证金池	
					
					pool.put("OWNER_ID", poolMap.get("SUP_ID"));//供应商id
					pool.put("PROJECT_ID", poolMap.get("PRO_ID"));//项目id
					pool.put("BASE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));//基础金额
					pool.put("CANUSE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));//可用金额
					pool.put("TYPE", "1");//资poolMap池类型
					pool.put("USERCODE", poolMap.get("USERCODE"));//操作人
					pool.put("SOURCE_ID", poolMap.get("SOURCE_ID"));//付款单号id
					i = Dao.insert(xmlPath+"doInsertPool",pool);
					if(i<1){return 0;}
				}else if(poolMap.get("BEGINNING_NAME").toString().equals("保证金")){
					pool.put("OWNER_ID", poolMap.get("CLIENT_ID"));
					pool.put("PROJECT_ID", poolMap.get("PRO_ID"));
					pool.put("BASE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));
					pool.put("CANUSE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));
					pool.put("TYPE", "4");
					pool.put("USERCODE", poolMap.get("USERCODE"));
					pool.put("SOURCE_ID", poolMap.get("SOURCE_ID"));
					
					i = Dao.insert(xmlPath+"doInsertPool",pool);
					if(i<1){return 0;}
				}else if(poolMap.get("BEGINNING_NAME").toString().equals("厂商保证金")){//当项目为厂商保证金时， 插入厂商保证金池	
					
					pool.put("OWNER_ID", poolMap.get("SUP_ID"));//供应商id
					pool.put("PROJECT_ID", poolMap.get("PRO_ID"));//项目id
					pool.put("BASE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));//基础金额
					pool.put("CANUSE_MONEY", poolMap.get("FA_ACCOINT_MONEY"));//可用金额
					pool.put("TYPE", "8");//资poolMap池类型
					pool.put("USERCODE", poolMap.get("USERCODE"));//操作人
					pool.put("SOURCE_ID", poolMap.get("SOURCE_ID"));//付款单号id
					i = Dao.insert(xmlPath+"doInsertPool",pool);
					if(i<1){return 0;}
				}
			}
		}
		
		return i;
	}
	
	
	//金额拆分方法
	public void moneyFenChai(String MONEYORDERSAVE,String Fund_id){
		Map<String,Object> mapDate=new HashMap<String,Object>();
		mapDate.put("FUND_ID", Fund_id);
		//先删后增
		Dao.delete(xmlPath+"deleteFundSplit",mapDate);
		String[] a=MONEYORDERSAVE.split(",");
		for(int i=0;i<a.length;i++){
			double money=(a[i]!=null && !a[i].equals(""))?Double.parseDouble(a[i]):0d;
			if(money>0){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("FUND_MONEY", money);
				map.put("FUND_ID", Fund_id);
				Dao.insert(xmlPath+"insertFundSplit",map);
			}
			
		}
	}
	
	/****************************************************** 资金扣划-非网银-清单查看 ********************************************************/
	public Page findDetialDataList(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		String org_id = BaseUtil.getSupOrgChildren();
		map.put("org_id", org_id);
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "findDetialDataList", map);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("FUND_ID", m.get("FUND_ID"));//申请单号
			json.put("D_CLIENT_CODE", m.get("D_CLIENT_CODE"));// 客户编号
			json.put("D_CLIENT_NAME", m.get("D_CLIENT_NAME"));// 客户名称
			json.put("PRODUCT_NAME", m.get("PRODUCT_NAME"));//设备
			json.put("SUP_SHORTNAME", m.get("SUP_SHORTNAME"));//供应商
			json.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC"));// 租赁物总价值
			json.put("D_PROJECT_CODE", m.get("D_PROJECT_CODE"));// 项目编号
			json.put("FIRST_PAYMENT_TYPE", m.get("FIRST_PAYMENT_TYPE"));//首期款付款方式
			if("1".equals(m.get("PAYMENT_STATUS"))){
				json.put("PAYMENT_STATUS", "全额放款");
			}else if("3".equals(m.get("PAYMENT_STATUS"))){				
				json.put("PAYMENT_STATUS", "部分差额放款");
			}else if("4".equals(m.get("PAYMENT_STATUS"))){
				json.put("PAYMENT_STATUS", "DB差额放款");
			}
			json.put("RECEIVABLE", m.get("RECEIVABLE"));// 本次应收
			json.put("VERITABLE_MONEY", m.get("VERITABLE_MONEY"));// 本次实收
			json.put("FIRST_MONEY", m.get("FIRST_MONEY"));// 首期款
			json.put("DB_MONEY", m.get("DB_MONEY"));// DB保证金
			json.put("CS_MONEY", m.get("CS_MONEY"));// 厂商保证金     
			json.put("OTHER_MONEY", m.get("OTHER_MONEY"));//其他费用
			json.put("YS_MONEY", m.get("YS_MONEY"));//应收金额
			array.add(JSONObject.fromObject(json));
		}
		page.addDate(array, Dao.selectInt(xmlPath + "findDetailDataListCount", map));// 计数
		return page;
	}


	/****************************************************** 资金扣划-非网银-驳回 ********************************************************/

	/**
	 * 资金扣划非网银核销-驳回操作（doReject） 在驳回操作中将申请当中的到账日期， 核销银行， 到账金额制空， 核销状态会更为未核销状态。
	 * 同时将资金扣划到账明细表中数据根据申请单号清除（即表FI_FUND_ACCOUNT）
	 * 
	 * @date 2013-10-8 下午05:25:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doReject(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));
		int i = 0;
		// 遍历参数，驳回
		if (detailData != null) {
			for (int k = 0; k < detailData.size(); k++) {
				Map<String, Object> de = detailData.get(k);
				de.put("FUND_ID", de.get("HEAD_ID"));
				i = Dao.update(xmlPath + "doReject", de);// 回更申请单明细。
				if (i > 0) {
					String FUND_ID = de.get("HEAD_ID").toString();// 付款单号
					Dao.delete(xmlPath + "doRejectAccount", FUND_ID);// 清除资金扣划到账明细。
				}
			}
			if (i > 0) {
				return i;
			}
		}

		return 0;
	}

	/****************************************************** 资金扣划-非网银-作废（申请页面） ********************************************************/

	/**
	 * 资金扣划非网银申请-作废操作（doCancellation）
	 * 
	 * @date 2013-10-9 下午04:58:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doCancellation(Map<String, Object> map) {
		System.out.println("wjd----"+map);
		//更新项目首付款状态
		Dao.update(xmlPath + "updateProFirstByfundIds", map);
		//删除付款单明细数据
		Dao.delete(xmlPath + "deleteFundDeatilByHeadIds", map);
		//删除付款单数据
		return Dao.delete(xmlPath + "deleteFundHeadByIds", map);
	}
	
	/****************************************************** 资金扣划-非网银-查询 *********************************************************************************/
	/**
	 * 查询首期款-非网银申请数据 findDeductData
	 * 
	 * @date 2013-9-24 上午11:04:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgDeductData1(Map<String, Object> map) {
		Page page = new Page(map);
		// 首期数据获取
		String org_id = BaseUtil.getSupOrgChildren();
		map.put("org_id", org_id);
		List<Map<String, Object>> list = Dao.selectList(xmlPath + "toMgDeductData1", map);
		List<Object> hexiao_satus = (List) new DataDictionaryMemcached().get("核销状态");
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			json.put("FI_PAY_MONEY", m.get("FI_PAY_MONEY"));// 应付金额
			json.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));// 应付时间
			json.put("FI_REALITY_MONEY", m.get("FI_REALITY_MONEY"));// 实付金额
			json.put("FI_ACCOUNT_DATE", m.get("FI_ACCOUNT_DATE"));// 实付日期
			json.put("FI_APP_CODE", m.get("FI_APP_CODE"));// 申请人编号
			json.put("FI_APP_NAME", m.get("FI_APP_NAME"));// 申请人
			json.put("FI_APP_DATE", m.get("FI_APP_DATE"));// 首期时间
			// json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态
			if (hexiao_satus != null) {
				for (int j = 0; j < hexiao_satus.size(); j++) {
					Map<String, Object> hexiao = (Map<String, Object>) hexiao_satus.get(j);
					if (hexiao.get("CODE").equals(m.get("FI_STATUS").toString())) {
						json.put("FI_STATUS", hexiao.get("FLAG"));
					}
				}
			}
			json.put("FI_REMARK", m.get("FI_REMARK"));// 备注
			array.add(JSONObject.fromObject(json));
		}
		page.addDate(array, Dao.selectInt(xmlPath + "toMgDeductCount1", map));// 计数
		return page;
	}
	
	/********************************************************* 资金扣划-差额放款核销租金(将首期款全部核销)  *******************************************************/
	
	/**
	 * 资金扣划-差额放款首期款核销	
	 * 
	 * @传入是分别为
	 *    1：PROJECT_ID  项目id
	 *    
	 *    2：PAY_DATE   支付时间（实际放款时间）
	 *    
	 * @date 2013-11-5 下午20:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int CHEFKhexiao(Map<String,Object> map){		
		int i = 0;
		//项目id
		String PROJECT_ID = "";
		String PRO_CODE = "";
		PROJECT_ID = map.get("PROJECT_ID") != null ? map.get("PROJECT_ID").toString():"";
		//申请单id
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");

		//根据项目id计算本次付款金额
		Map<String,Object> ys_money = Dao.selectOne(xmlPath+"toFindHexiaoDataByProid", map);
		double ys_money_ = 0.00;//应收金额
		ys_money_ = Double.parseDouble(ys_money.get("YS_MONEY") != null ? ys_money.get("YS_MONEY").toString():"0.00");
		if(ys_money.get("PAY_WAY")!=null){
			if("2".equals(ys_money.get("PAY_WAY")+"")||"4".equals(ys_money.get("PAY_WAY")+"")||"6".equals(ys_money.get("PAY_WAY")+"")){
				Map ONEMap=Dao.selectOne("payment.query_Total_ONE", map);
				if(ONEMap!=null && ONEMap.get("BEGINNING_MONEY")!=null && !"".equals(ONEMap.get("BEGINNING_MONEY")+"")){
					ys_money_ += Double.parseDouble(ONEMap.get("BEGINNING_MONEY").toString());
				}
			}
		}
		Map<String,Object> fund = new HashMap<String,Object>();
		fund.put("HEAD_ID", HEAD_ID);
		fund.put("FI_PAY_MONEY", ys_money_);//付款金额
		fund.put("FI_PAY_DATE", map.get("PAY_DATE"));//付款日期
		fund.put("FI_PROJECT_NUM", 1);//项目数量
		fund.put("FI_REALITY_MONEY", ys_money_);//实际付款金额
		fund.put("FI_REALITY_ACCOUNT", ys_money_);//到账金额  供应商或承租人实际打款金额
		fund.put("FI_ACCOUNT_DATE", map.get("PAY_DATE"));//实际到账日期-核销时间
		fund.put("USERNAME", Security.getUser().getName());//用户名
		fund.put("USERCODE", Security.getUser().getCode());//用户编号
		fund.put("FI_STATUS", 2);//核销状态
		fund.put("FI_FLAG", 20); //20 放款抵扣
		i = Dao.insert(xmlPath+"doInsertFundHead",fund);///插入申请单主表



		if(i>0){//当核销单主表插入成功，插入细单
			
			if("2".equals(ys_money.get("PAY_WAY"))||"4".equals(ys_money.get("PAY_WAY"))||"6".equals(ys_money.get("PAY_WAY"))){
				ys_money.put("benjin", "本金");
				ys_money.put("PROJECT_ID",PROJECT_ID);
				List<Map<String,Object>> rent_ = Dao.selectList(xmlPath+"getRent",ys_money);
				if(rent_!=null){
					for(int m=0; m<rent_.size(); m++){
						Map<String,Object> o = rent_.get(m);
						o.put("D_FUND_ID", HEAD_ID);
						//插入申请细单
						i = Dao.insert(xmlPath+"doInsertAppDetail", o);
						
							
						Map FA_MAP = Dao.selectOne(xmlPath+"getIdDetail",o);
						String FA_DETAIL_ID = FA_MAP.get("FD_ID")+"";
						o.put("FA_FUND_ID", HEAD_ID);//申请单id
						o.put("FA_BEGING_ID", o.get("D_BEGING_ID"));//应收起初表id
						o.put("FA_DETAIL_ID", FA_DETAIL_ID);//申请细单id
						o.put("FA_ACCOINT_MONEY", o.get("D_PAY_MONEY"));//实际支付金额
						
						Dao.insert(xmlPath+"doInsertAccount",o);//插入实际付款细单
						
					}
				}
			}
			
			//获取付款明细单
			List<Map<String,Object>> detail = Dao.selectList(xmlPath+"toFindDetailByProid", PROJECT_ID);
			if(detail!=null){
				for(int n=0; n<detail.size(); n++){
					Map<String,Object> detailL = detail.get(n);
					PRO_CODE = detailL.get("D_PROJECT_CODE")+"";
					
					detailL.put("D_FUND_ID", HEAD_ID);
					//插入申请细单
					i = Dao.insert(xmlPath+"doInsertAppDetail", detailL);
					
					if(i>0){
						Map FA_MAP = Dao.selectOne(xmlPath+"getIdDetail",detailL);
						String FA_DETAIL_ID = FA_MAP.get("FD_ID")+"";
						detailL.put("FA_FUND_ID", HEAD_ID);//申请单id
						detailL.put("FA_BEGING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("FA_DETAIL_ID", FA_DETAIL_ID);//申请细单id
						detailL.put("FA_ACCOINT_MONEY", detailL.get("D_PAY_MONEY"));//实际支付金额
						
						Dao.insert(xmlPath+"doInsertAccount",detailL);//插入实际付款细单
						
						//修改应收起初表实收金额和实际状态
						detailL.put("BEGINNING_PAID",detailL.get("D_PAY_MONEY"));//实际支付金额
						detailL.put("BEGINNING_FLAG",1);//核销装填
						detailL.put("BEGINNING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("REALITY_TIME",  map.get("PAY_DATE"));
						Dao.update(xmlPath+"doUpdateBeginning", detailL);
					}
					
					if(detailL.get("D_PAY_PROJECT")!=null&&detailL.get("D_PAY_PROJECT").toString().equals("DB保证金")&&detailL.get("D_PAY_MONEY")!=null&&Double.parseDouble(detailL.get("D_PAY_MONEY").toString())>0){
						Map<String,Object> pool = new HashMap<String,Object>();
						pool.put("OWNER_ID", detailL.get("SUP_ID"));
						pool.put("PROJECT_ID", detailL.get("PROJECT_ID"));
						pool.put("BASE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("CANUSE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("TYPE", 1);
						pool.put("USERCODE", Security.getUser().getCode());
						pool.put("SOURCE_ID", HEAD_ID);
						
						Dao.insert(xmlPath+"doInsertPool", pool);
					}else if(detailL.get("D_PAY_PROJECT")!=null&&detailL.get("D_PAY_PROJECT").toString().equals("保证金")&&detailL.get("D_PAY_MONEY")!=null&&Double.parseDouble(detailL.get("D_PAY_MONEY").toString())>0){
						Map<String,Object> pool = new HashMap<String,Object>();
						pool.put("OWNER_ID", detailL.get("CLIENT_ID"));
						pool.put("PROJECT_ID", detailL.get("PROJECT_ID"));
						pool.put("BASE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("CANUSE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("TYPE", 4);
						pool.put("USERCODE", Security.getUser().getCode());
						pool.put("SOURCE_ID", HEAD_ID);
						
						Dao.insert(xmlPath+"doInsertPool", pool);
					}
				}
			}
			
			//修改项目状态
			map.put("FIRST_APP_STATE", 2);
			map.put("SQFKSS_DATE",  map.get("PAY_DATE"));
			map.put("PROJECT_HEAD_ID",map.get("PROJECT_ID"));
			i = Dao.update(xmlPath+"doUpdateProject", map);
			
			FundEbankService eService = new FundEbankService();
			//核销第一期租金和添加开票人信息： 传入项目id
			if(!"".equals(PRO_CODE)){
				map.put("deducted_id", PRO_CODE);
				eService.doConfirmFirstRentByProId(map);
			}
		}
		return i;		
	}

	/********************************************************* 资金扣划-差额放款核销租金(核销部分首期款：核起租租金和留购价款)  *******************************************************/
	/**
	 * 资金扣划-部分差额放款	
	 * 
	 * @部分差额放款：
	 * 			
	 * 		只核起租租金和留购价款
	 * 		
	 *    1：PROJECT_ID  项目id
	 *    
	 *    2：PAY_DATE   支付时间（实际放款时间）
	 *    
	 * @date 2013-11-5 下午20:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int CHEFKhexiao_BFCE(Map<String,Object> map){		
		int i = 0;
		//项目id
		String PROJECT_ID = "";
		PROJECT_ID = map.get("PROJECT_ID") != null ? map.get("PROJECT_ID").toString():"";
		//申请单id
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");

		//根据项目id计算本次付款金额
		Map<String,Object> ys_money = Dao.selectOne(xmlPath+"toFindHexiaoDataByProid01", map);
		double ys_money_ = 0.00;//应收金额
		ys_money_ = Double.parseDouble(ys_money.get("YS_MONEY") != null ? ys_money.get("YS_MONEY").toString():"0.00");
		
		Map<String,Object> fund = new HashMap<String,Object>();
		fund.put("HEAD_ID", HEAD_ID);
		fund.put("FI_PAY_MONEY", ys_money_);//付款金额
		fund.put("FI_PAY_DATE", map.get("PAY_DATE"));//付款日期
		fund.put("FI_PROJECT_NUM", 1);//项目数量
		fund.put("FI_REALITY_MONEY", ys_money_);//实际付款金额
		fund.put("FI_REALITY_ACCOUNT", ys_money_);//到账金额  供应商或承租人实际打款金额
		fund.put("FI_ACCOUNT_DATE", map.get("PAY_DATE"));//实际到账日期-核销时间
		fund.put("USERNAME", Security.getUser().getName());//用户名
		fund.put("USERCODE", Security.getUser().getCode());//用户编号
		fund.put("FI_STATUS", 2);//核销状态
		fund.put("FI_FLAG", 20); //20 放款抵扣
		i = Dao.insert(xmlPath+"doInsertFundHead",fund);///插入申请单主表



		if(i>0){//当核销单主表插入成功，插入细单
			
			//获取付款明细单
			List<Map<String,Object>> detail = Dao.selectList(xmlPath+"toFindDetailByProid01", PROJECT_ID);
			if(detail!=null){
				for(int n=0; n<detail.size(); n++){
					Map<String,Object> detailL = detail.get(n);
					
					detailL.put("D_FUND_ID", HEAD_ID);
					//插入申请细单
					i = Dao.insert(xmlPath+"doInsertAppDetail", detailL);
					
					if(i>0){
						Map FA_MAP = Dao.selectOne(xmlPath+"getIdDetail",detailL);
						String FA_DETAIL_ID = FA_MAP.get("FD_ID")+"";
						detailL.put("FA_FUND_ID", HEAD_ID);//申请单id
						detailL.put("FA_BEGING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("FA_DETAIL_ID", FA_DETAIL_ID);//申请细单id
						detailL.put("FA_ACCOINT_MONEY", detailL.get("D_PAY_MONEY"));//实际支付金额
						
						Dao.insert(xmlPath+"doInsertAccount",detailL);//插入实际付款细单
						
						//修改应收起初表实收金额和实际状态
						detailL.put("BEGINNING_PAID",detailL.get("D_PAY_MONEY"));//实际支付金额
						detailL.put("BEGINNING_FLAG",1);//核销装填
						detailL.put("BEGINNING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("REALITY_TIME",  map.get("PAY_DATE"));
						Dao.update(xmlPath+"doUpdateBeginning", detailL);
					}
					
				}
			}
			
			//修改项目状态
			map.put("FIRST_APP_STATE", 2);
			map.put("SQFKSS_DATE",  map.get("PAY_DATE"));
			map.put("PROJECT_HEAD_ID",map.get("PROJECT_ID"));
			i = Dao.update(xmlPath+"doUpdateProject", map);
		}
		return i;		
	}
	
	/********************************************************* 资金扣划-差额放款核销租金(核销DB保证金)  *******************************************************/
	/**
	 * 资金扣划-db差额放款
	 * 
	 * @部分差额放款：
	 * 			
	 * 		在这里不核销手续费和承租人保证金
	 * 		
	 *    1：PROJECT_ID  项目id
	 *    
	 *    2：PAY_DATE   支付时间（实际放款时间）
	 *    
	 * @date 2013-11-5 下午20:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int CHEFKhexiao_DBCE(Map<String,Object> map){		
		int i = 0;
		//项目id
		String PROJECT_ID = "";
		PROJECT_ID = map.get("PROJECT_ID") != null ? map.get("PROJECT_ID").toString():"";
		//申请单id
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");

		//根据项目id计算本次付款金额
		Map<String,Object> ys_money = Dao.selectOne(xmlPath+"toFindHexiaoDataByProid02", map);
		double ys_money_ = 0.00;//应收金额
		ys_money_ = Double.parseDouble(ys_money.get("YS_MONEY") != null ? ys_money.get("YS_MONEY").toString():"0.00");
		
		Map<String,Object> fund = new HashMap<String,Object>();
		fund.put("HEAD_ID", HEAD_ID);
		fund.put("FI_PAY_MONEY", ys_money_);//付款金额
		fund.put("FI_PAY_DATE", map.get("PAY_DATE"));//付款日期
		fund.put("FI_PROJECT_NUM", 1);//项目数量
		fund.put("FI_REALITY_MONEY", ys_money_);//实际付款金额
		fund.put("FI_REALITY_ACCOUNT", ys_money_);//到账金额  供应商或承租人实际打款金额
		fund.put("FI_ACCOUNT_DATE", map.get("PAY_DATE"));//实际到账日期-核销时间
		fund.put("USERNAME", Security.getUser().getName());//用户名
		fund.put("USERCODE", Security.getUser().getCode());//用户编号
		fund.put("FI_STATUS", 2);//核销状态
		fund.put("FI_FLAG", 20); //20 放款抵扣
		i = Dao.insert(xmlPath+"doInsertFundHead",fund);///插入申请单主表



		if(i>0){//当核销单主表插入成功，插入细单
			
			//获取付款明细单
			List<Map<String,Object>> detail = Dao.selectList(xmlPath+"toFindDetailByProid02", PROJECT_ID);
			if(detail!=null){
				for(int n=0; n<detail.size(); n++){
					Map<String,Object> detailL = detail.get(n);
					
					detailL.put("D_FUND_ID", HEAD_ID);
					//插入申请细单
					i = Dao.insert(xmlPath+"doInsertAppDetail", detailL);
					
					if(i>0){
						Map FA_MAP = Dao.selectOne(xmlPath+"getIdDetail",detailL);
						String FA_DETAIL_ID = FA_MAP.get("FD_ID")+"";
						detailL.put("FA_FUND_ID", HEAD_ID);//申请单id
						detailL.put("FA_BEGING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("FA_DETAIL_ID", FA_DETAIL_ID);//申请细单id
						detailL.put("FA_ACCOINT_MONEY", detailL.get("D_PAY_MONEY"));//实际支付金额
						
						Dao.insert(xmlPath+"doInsertAccount",detailL);//插入实际付款细单
						
						//修改应收起初表实收金额和实际状态
						detailL.put("BEGINNING_PAID",detailL.get("D_PAY_MONEY"));//实际支付金额
						detailL.put("BEGINNING_FLAG",1);//核销装填
						detailL.put("BEGINNING_ID", detailL.get("D_BEGING_ID"));//应收起初表id
						detailL.put("REALITY_TIME",  map.get("PAY_DATE"));
						Dao.update(xmlPath+"doUpdateBeginning", detailL);
					}
					if(detailL.get("D_PAY_PROJECT")!=null&&detailL.get("D_PAY_PROJECT").toString().equals("DB保证金")&&detailL.get("D_PAY_MONEY")!=null&&Double.parseDouble(detailL.get("D_PAY_MONEY").toString())>0){
						Map<String,Object> pool = new HashMap<String,Object>();
						pool.put("OWNER_ID", detailL.get("SUP_ID"));
						pool.put("PROJECT_ID", detailL.get("PROJECT_ID"));
						pool.put("BASE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("CANUSE_MONEY", detailL.get("D_PAY_MONEY"));
						pool.put("TYPE", 1);
						pool.put("USERCODE", Security.getUser().getCode());
						pool.put("SOURCE_ID", HEAD_ID);
						
						Dao.insert(xmlPath+"doInsertPool", pool);
					}
					
				}
			}
			
			//修改项目状态
			map.put("FIRST_APP_STATE", 2);
			map.put("SQFKSS_DATE",  map.get("PAY_DATE"));
			map.put("PROJECT_HEAD_ID",map.get("PROJECT_ID"));
			i = Dao.update(xmlPath+"doUpdateProject", map);
		}
		return i;		
	}
	
	/************************************************************ 资金扣划-跟新还款计划（首期款）**************************************************************/
	
	/**
	 * 更新非租金还款日期到还款计划明细表以及还款计划主表
	 * 
	 * @param PAYLIST_CODE
	 *            还款计划编号
	 * @param DATE
	 *            付款日
	 * @param list
	 *            此次变更涉及到的费用类型 如起租租金、保险费等 可以为空
	 * @author:King 2013-10-19
	 */
	public void updateFirstPayDateForRent(String PAYLIST_CODE, String DATE, List<String> list) {
		if (list == null || list.isEmpty() || list.size() < 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PAYLIST_CODE", PAYLIST_CODE);
			map.put("PAY_DATE", DATE);
			map.put("ITEM_NAME", null);
			Dao.update(xmlPath + "updateFirstPayDateForRentHead", map);
			// Dao.update(xmlPath + "updateFirstPayDateForRentDetail", map);
			// Dao.update(xmlPath + "updateFirstPayDateForBeginning", map);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PAYLIST_CODE", PAYLIST_CODE);
			map.put("PAY_DATE", DATE);
			for (String string : list) {
				map.put("ITEM_NAME", string);
				if (string.equals("起租租金"))
					Dao.update(xmlPath + "updateFirstPayDateForRentHead", map);
				// Dao.update(xmlPath + "updateFirstPayDateForRentDetail", map);
				// Dao.update(xmlPath + "updateFirstPayDateForBeginning", map);
			}
		}
	}

	/**
	 * 更新非租金还款日期到还款计划明细表以及还款计划主表
	 * 
	 * @param PAYLIST_CODE
	 *            还款计划编号
	 * @param DATE
	 *            付款日
	 * @author:King 2013-10-19
	 */
	public void updateFirstPayDateForRent(String PAYLIST_CODE, String DATE) {
		updateFirstPayDateForRent(PAYLIST_CODE, DATE, null);
	}

	/**
	 * 更新非租金还款日期到还款计划明细表以及还款计划主表 将当前操作日期作为付款日
	 * 
	 * @param PAYLIST_CODE
	 * @author:King 2013-10-19
	 */
	public void updateFirstPayDateForRent(String PAYLIST_CODE) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		updateFirstPayDateForRent(PAYLIST_CODE, sdf.format(Calendar.getInstance().getTime()), null);
	}

	/**
	 * 更新非租金还款日期到还款计划明细表以及还款计划主表 将当前操作日期作为付款日
	 * 
	 * @param JBPM_ID
	 * @author:King 2013-10-19
	 */
	@SuppressWarnings("unchecked")
	public void updateFirstPayDateForRentForJbpm(String JBPM_ID) {
		Map<String, Object> param = JBPM.getVeriable(JBPM_ID);
		param.putAll((Map<? extends String, ? extends Object>) Dao.selectList(xmlPath + "doShowPayIdByProjectId", param.get("PROJECT_ID")).get(0));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		updateFirstPayDateForRent(param.get("PAYLIST_CODE") + "", sdf.format(Calendar.getInstance().getTime()), null);
	}
}
