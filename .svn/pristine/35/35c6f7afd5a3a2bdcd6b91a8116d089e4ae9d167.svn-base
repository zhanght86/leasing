package com.pqsoft.renterpool.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.entity.Excel;
import com.pqsoft.fi.payin.service.FundDecService;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.rentWrite.service.rentWriteService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.DateUtil;

public class CashDepositService {

	private final String xmlPath = "cashDeposit.";
	
	/**
	 * 承租人保证金池管理数据
	 * @param param
	 * @return
	 */
	public Page toMgRefund(Map<String,Object> param ){
		param.put("ZJC_STATUS", "资金池状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgCashDeposit",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgCashDepositNu", param));
		return page;
	}
	
	/**
	 * 承租人保证金池管理数据
	 * @param param
	 * @return
	 */
	public Page toMgRefundNew(Map<String,Object> param ){
		param.put("ZJC_STATUS", "资金池状态");
		param.put("PAY_STATUS", "还款计划状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgCashDepositNew",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgCashDepositNuNew", param));
		return page;
	}
	
	/**
	 * 查看供应商
	 * getSuppliers
	 * @date 2013-11-18 下午04:22:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getSuppliers(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"getSuppliers",map);
	}
	
	public Object queryBankInfo(Map<String,Object> map){
		String TYPE=map.get("TYPE").toString();
		if(TYPE.equals("2")){//客户
			//先查询银行修正表
			Map mapDate=Dao.selectOne(xmlPath+"baseCustBank", map);
			if(mapDate == null){
				return Dao.selectOne(xmlPath+"getCustBankInfo",map);
			}
			else{
				return mapDate;
			}
		}else{//供应商
			Map mapDate=Dao.selectOne(xmlPath+"baseSuppBank", map);
			if(mapDate == null){
				return Dao.selectOne(xmlPath+"getSuppBankInfo",map);
			}
			else{
				return mapDate;
			}
		}
	}
	
	/**
	 * 获取序列
	 * getRefundDanSeq
	 * @date 2013-11-1 上午11:01:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public String getRefundDanSeq(){
		return Dao.getSequence("SEQ_FI_REFUND_HEAD");
	}
	
	/**
	 * 插入退款
	 * addRefundCUST
	 * @date 2013-10-31 下午05:13:34
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addRefundCUST(Map<String,Object> map){
		System.out.println("------------------map="+map);
		return Dao.insert(xmlPath+"addRefundCUST", map);
	}
	
	/**
	 * 更新资金池相关状态By Re_id
	 * @param param
	 * @return
	 */
	public int updateCUSTRefundId(Map<String,Object> param){
		return Dao.update(xmlPath+"updateCUSTRefundId",param);
	}
	
	public int updateCUSTRefundIdNew(Map<String,Object> param){
		return Dao.update(xmlPath+"updateCUSTRefundIdNew",param);
	}
	
	
	
	/**
	 * 承租人保证金冲抵租金列表信息
	 * @param param
	 * @return
	 */
	public Page toMgCDdetail(Map<String,Object> param ){
		param.put("ZJ_STATUS", "资金池冲抵状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgCDChargeAgainst",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgCDChargeAgainstNu", param));
		return page;
	}
	
	
	/**
	 * 承租人保证金冲抵租金细单
	 * getCDDetainlByFundId
	 * @date 2013-11-19 上午11:02:27
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getCDDetainlByFundId(Map<String,Object> map){
		map.put("BJ_MONEY","本金");
		map.put("LX_MONEY","利息");
		return Dao.selectList(xmlPath+"getCDDetainlByFundId", map);
	}
	
	/**
	 * 承租人保证金退款
	 * @param param
	 * @return
	 */
	public Page toMgCDRefund(Map<String,Object> param ){
		param.put("TK_STATUS", "退款单状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgCDRefund",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgCDRefundNu", param));
		return page;
	}
	
	/**
	 * 查看退款明细
	 * getRefundDetailData
	 * @date 2013-11-18 下午05:48:57
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getRefundDetailData(Map<String,Object> map){
		map.put("BZJ", "保证金");
		return Dao.selectList(xmlPath+"getRefundDetailData", map);
	}
	
	//查看资金池数据
	public Object getPoolData(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"getPoolData", map);
	}
	
	//查看退看明细
	public Object toRefundData(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toRefundData",map);
	}
	
	//查看申请单
	public Map<String, Object> getRefundData(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"getRefundData", map);
	}
	
	
	//修改申请单
	public int doUpdateRefund(Map<String,Object> map){
		int i = 0;
		
		i = Dao.update(xmlPath+"doUpdateRefund", map);
		if(i>0){	
			int num = Dao.selectInt(xmlPath+"getReDetailSum", map);//退回申请
			if(num>0){//若实收已存在， 先删除
				Dao.delete(xmlPath+"delReDetTail", map);
			}
			
			List<Map<String,Object>> poolList = Dao.selectList(xmlPath+"getPoolData", map);
			if(poolList!=null){
				for(int k=0; k<poolList.size();k++){
			Map<String,Object> pool = poolList.get(k);
					pool.put("RE_ID", map.get("RE_ID"));
					pool.put("PRO_ID", pool.get("PROJECT_ID"));
					pool.put("REFUND_REALITY_MONEY", pool.get("CANUSE_MONEY"));
					i = Dao.insert(xmlPath+"doInsertAccountRe", pool);
				}
			}			
		}
		return i;
	}
	
	//修改申请单状态
	public int doUpateRefundStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpdateRefund", map);
	}
	
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-结清抵扣申请                                                                                                       *
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	/**
	 * 承租人保证金池结清抵扣申请
	 * @param param
	 * @return
	 */
	public Page toMgJQDKData(Map<String,Object> param ){
		param.put("hx_status", "核销状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgJQDKData",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgJQDKDataNu", param));
		return page;
	}
	
	
	/**
	 * 结清抵扣申请
	 * toMgDeductionBZJ
	 * @date 2013-11-3 下午03:38:28
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgDeductionBZJ(Map<String,Object> map){
		Page page = new Page(map);
		map.put("BENJIN", "本金");
		map.put("LIXI", "利息");
		List<Map<String,Object>> list = Dao.selectList("deductionBZJ.toMgDeductionBZJ", map);
		JSONArray array = new JSONArray();
		DecimalFormat df = new DecimalFormat(".00"); 
		for(int i=0;i<list.size();i++) {
			Map<String,Object> m = list.get(i);
			JSONObject obj = new JSONObject();
			
			obj.put("PRO_ID", m.get("PRO_ID"));//项目id
			obj.put("PRO_CODE", m.get("PRO_CODE"));//项目编号
			obj.put("CLIENT_ID", m.get("CLIENT_ID"));//客户编号
			obj.put("CUST_NAME", m.get("CUST_NAME"));//客户名称
			obj.put("PRODUCT_NAME", m.get("PRODUCT_NAME"));//租赁物信息
			obj.put("COMPANY_NAME", m.get("COMPANY_NAME"));//厂商
			obj.put("ZJ_MONEY", m.get("ZJ_MONEY"));//未交租金
			
			obj.put("WS_NUM", m.get("WHQI"));//未交期次
			obj.put("WS_WYJ", m.get("WS_WYJ"));//未收逾期
			obj.put("BZJ_MONEY", m.get("BZJ_MONEY"));//保证金
			
			obj.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));//支付表
			
			double bc_zj = m.get("ZJ_MONEY")==null?0d:Double.parseDouble(m.get("ZJ_MONEY").toString());//租金
			double ws_yq = m.get("WS_WYJ")==null?0d:Double.parseDouble(m.get("WS_WYJ").toString());//违约金
			double bzj = m.get("BZJ_MONEY")==null?0d:Double.parseDouble(m.get("BZJ_MONEY").toString());//保证金
			double deduction_yue = bc_zj + ws_yq - bzj;//抵扣后所需余额
			double sy_bzj = 0d;
		
			//当剩余租金-本项目的保证金  > 0： 这时抵扣后所需余额不为0， 其值为剩余租金与保证金的差值; 剩余保证金为0
			if(deduction_yue>0){
				obj.put("DEDUCTION_YUE", df.format(deduction_yue));
				obj.put("SY_BZJ", 0);
			}else {//当剩余租金-本项目的保证金  <= 0, 这时抵扣后需要的月为0， 剩余保证金金额为：小于等于零的部分
				obj.put("DEDUCTION_YUE", 0);
				sy_bzj = -deduction_yue;
				obj.put("SY_BZJ", df.format(sy_bzj));
			}
			
			array.add(obj);
		}
		
		page.addDate(array, Dao.selectInt("deductionBZJ.toMgDeductionBZJNu", map));
		return page;		
	}
	
	
	/**
	 * 判断项目是否有被锁住的资金
	 * checkedLockTypeRent
	 * @date 2013-12-22 下午12:05:18
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String checkedLockTypeRent(Map<String,Object> map){
		
		//获取基础数据
		Map<String, Object> data = JSONObject.fromObject(map.get("data"));
		
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));//申请扣划的客户的信息
		String lockType_ = "";
		if(detailData.size()>0){
			for(int i=0; i<detailData.size(); i++){
				Map<String,Object> m = detailData.get(i);
				m.put("PROJECT_ID", m.get("PRO_ID"));
				Map<String,Object> loctType = Dao.selectOne("deductionBZJ.checkedLockTypeRent", m);
				
				System.out.println("-------yx-------"+loctType);
				
				if(loctType != null){
					if(Integer.parseInt(loctType.get("LOCKTYPE").toString())==2){
						lockType_  += m.get("PAYLIST_CODE")+" ";
					}
				}
				
			}
		}
		
		return lockType_;
	}
	
	
	/**
	 * 插入数据
	 * doInsertFundHead
	 * @date 2013-11-3 下午08:14:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertFundHead(Map<String, Object> map){
		
		int a = this.doInsertCHBZJ(map);
		
		
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		
		Map<String, Object> data = JSONObject.fromObject(map.get("data"));
		
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));//基础数据:供应商，付款方式，付款日期，应付款金额，实际付款金额，项目数量

		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));//申请扣划的客户的信息
		
		int i = 0;
		
		baseData.put("HEAD_ID", HEAD_ID);
		baseData.put("USERCODE", map.get("USERCODE"));//用户编号
		baseData.put("USERNAME", map.get("USERNAME"));//用户姓名
		baseData.put("FI_STATUS", 5);
		if(a>0){
			//插入申請單
			i = Dao.insert("deductionBZJ.doInsertFundHead", baseData);
			
			if (i > 0) {
				if (detailData.size()>0) {
					for (int k = 0; k < detailData.size(); k++) {
						Map<String, Object> m = (Map<String, Object>) detailData.get(k);
						if(m.get("ZJ_MONEY")!=null&&Double.parseDouble(m.get("ZJ_MONEY").toString())>0){
							
							Map<String,Object> mm = new HashMap<String,Object>();
							mm.put("BENJIN", "本金");
							mm.put("LIXI", "利息");
							mm.put("PRO_ID", m.get("PRO_ID"));
							//查看未收租金
							List<Map<String,Object>> detail = Dao.selectList("deductionBZJ.getRentDetail", mm);
							//若detail不为空， 插入明细
							if(detail.size()>0){
								for(int j=0; j<detail.size(); j++){
									Map<String, Object> detailM = detail.get(j);
									detailM.put("D_FUND_ID",HEAD_ID);
									detailM.put("D_TO_THE_PAYEE", m.get("CUST_NAME"));
									detailM.put("D_CLIENT_NAME", m.get("CUST_NAME"));
									detailM.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
									detailM.put("D_PAY_PROJECT", detailM.get("BEGINNING_NAME").toString());
									detailM.put("D_PAY_MONEY", detailM.get("BEGINNING_MONEY").toString());
									detailM.put("D_RECEIVE_DATE", detailM.get("BEGINNING_RECEIVE_DATA"));
									detailM.put("D_PAY_CODE", detailM.get("PAYLIST_CODE").toString());
									detailM.put("D_PROJECT_CODE", m.get("PRO_CODE"));
									detailM.put("D_BEGING_ID", detailM.get("BEGINNING_ID"));
									detailM.put("D_STATUS", 1);
									detailM.put("PERIOD", detailM.get("BEGINNING_NUM"));
									i = Dao.insert("deductionBZJ.doInsertAppDetail", detailM);//插入申请细单
									
									//修改期初表状态
									detailM.put("BEGINNING_STATUS",1);
									detailM.put("BEGINNING_ID",detailM.get("BEGINNING_ID"));
									Dao.insert("deductionBZJ.doUpdateBegining",detailM);
									
									
									//跟新租金中间表：FI_R_BEGINNING_JOIN, 传入支付表编号和期次
									detailM.put("pay_code",detailM.get("PAYLIST_CODE").toString());
									detailM.put("pay_number",detailM.get("BEGINNING_NUM"));
									Dao.update("deductionBZJ.doChangeBeAndYQ",detailM);
									
								}
							}
						}
						
						if(m.get("WS_WYJ")!=null && Double.parseDouble(m.get("WS_WYJ").toString())>0){
							//查看项目是否有逾期
							List<Map<String,Object>> yq_list = Dao.selectList("deductionBZJ.getOverDue",m);
							if(yq_list != null){
								for(int n=0; n<yq_list.size(); n++){
									Map<String,Object> yq_m = yq_list.get(n);
									//yq_m = Dao.selectOne("deductionBZJ.getBEGINNING_ID",yq_m);
									yq_m.put("D_FUND_ID",HEAD_ID);
									yq_m.put("D_TO_THE_PAYEE", m.get("CUST_NAME"));
									yq_m.put("D_CLIENT_NAME", m.get("CUST_NAME"));
									yq_m.put("D_CLIENT_CODE", m.get("CLIENT_ID"));
									yq_m.put("D_PAY_PROJECT", "违约金");
									yq_m.put("D_PAY_MONEY", yq_m.get("WS_YQ").toString());
									yq_m.put("D_RECEIVE_DATE", yq_m.get("RENT_DATE"));
									yq_m.put("D_PAY_CODE", yq_m.get("PAY_CODE").toString());
									yq_m.put("D_PROJECT_CODE", m.get("PRO_CODE"));
									yq_m.put("D_BEGING_ID", yq_m.get("BEGINNING_ID"));
									yq_m.put("D_STATUS", 2);
									yq_m.put("PERIOD",  yq_m.get("PERIOD"));
									i = Dao.insert("deductionBZJ.doInsertAppDetail", yq_m);
									
									
									//跟新租金中间表：FI_R_BEGINNING_JOIN, 传入支付表编号和期次
									yq_m.put("pay_code",yq_m.get("PAY_CODE").toString());
									yq_m.put("pay_number",yq_m.get("PERIOD"));
									Dao.update("deductionBZJ.doChangeBeAndYQ",yq_m);
								}
							}
						}
					}
				}
			}
		}
		return i;
	}
	
	/**
	 * 插入冲红申请单
	 * 
	 * 在进行结清抵扣申请前， 首先向系统中插入一条负的保证金金额， 以避免同一笔资金出现多次重复使用的现象
	 * 
	 * toInsertCHBZJ
	 * @date 2013-11-14 下午02:37:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertCHBZJ(Map<String,Object> map){
		
		
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);
		
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));//基础数据:供应商，付款方式，付款日期，应付款金额，实际付款金额，项目数量

		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));//申请扣划的客户的信息
		
		FundNotEBankService  chService = new FundNotEBankService();
		
		int k=0;
		
		if(detailData.size()>0){
			for(int i=0; i<detailData.size(); i++){
				//付款单id
				String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
				Map<String,Object> detail = detailData.get(i);
				if(detail.get("PRO_ID")!= null&&detail.get("SY_BZJ")!=null){//当项目id和剩余保证金不为空的时候，向付款单插入一条负的资金，作为融资公司的出账资金
					//查看项目所在资金池
					List<Map<String,Object>> head = Dao.selectList(xmlPath+"getPoolData_1", detail);
					//父类付款单id
					String FI_SUPERCLASS_ID = "";
					if(head!=null){
						for(int a=0; a<head.size(); a++){
							Map<String,Object> m = head.get(a);
						    FI_SUPERCLASS_ID += m.get("SOURCE_ID")!=null&&!"".equals(m.get("SOURCE_ID"))?m.get("SOURCE_ID").toString()+",":"";
						}
					}

					//插入资金池流出的数据金额： 保证金结清抵扣之前， 首先出入插入一条负的保证金， 该保证金虚记录为融资公司冲红所用的保证金
					Map<String,Object> CH = new HashMap<String,Object>();
					CH.put("HEAD_ID", HEAD_ID);//付款单号
					
					CH.put("FI_PAY_MONEY", -Double.parseDouble(detail.get("BZJ_MONEY").toString()));//应收金额
					CH.put("FI_PROJECT_NUM", 1);//项目数量
					CH.put("USERNAME", map.get("USERNAME"));//申请人
					CH.put("USERCODE", map.get("USERCODE"));//申请人编号
					CH.put("FI_PAY_DATE", baseData.get("FI_PAY_DATE"));//应付款时间
					CH.put("FI_FLAG", baseData.get("FI_FLAG"));//付款类型
					CH.put("FI_SUPERCLASS_ID", FI_SUPERCLASS_ID);//父类资金付款单
					
					k = chService.doInsertAppBaseData(CH);//插入金额为负的保证金。
					
					if(k>0){//插入冲红细单
						Map<String,Object> red = new HashMap<String,Object>();
						red.put("D_FUND_ID", HEAD_ID);//付款单id
						red.put("D_CLIENT_CODE", detail.get("CLIENT_ID").toString());//客户编号id
						red.put("D_CLIENT_NAME", detail.get("CUST_NAME").toString());//客户名称
						red.put("D_PAY_PROJECT", "保证金");
						red.put("D_PAY_MONEY", detail.get("BZJ_MONEY"));//保证金
						String D_PAY_CODE = Dao.selectOne(xmlPath+"getPaylist", detail);
						red.put("D_PAY_CODE",D_PAY_CODE);
						red.put("D_PROJECT_CODE", detail.get("PRO_CODE").toString());
						
						k = Dao.insert("fundNotEBank.doInsertAppDetail", red);
					}
				}
			}
		}
		return k;
	}
	
	/**
	 * 查看申请细单数据
	 * @param Map  
	 *       FUND_ID  申请单id
	 * getJQDKDetail
	 * @date 2013-11-15 上午11:38:21
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getJQDKDetail(Map<String,Object> map){
		Page page = new Page();
		map.put("BENJIN", "本金");
		map.put("LIXI", "利息");
		map.put("WEIYUEJIN", "违约金");
		//根据申请单id查看对应细单信息
		List<Map<String,Object>> list = Dao.selectList("deductionBZJ.getJQDKDetail", map);
		DecimalFormat df = new DecimalFormat(".00"); 
		JSONArray array = new JSONArray();
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				JSONObject obj = new JSONObject();
				Map<String,Object> m = list.get(i);
				
				obj.put("PRO_ID", m.get("PRO_ID"));//项目id
				obj.put("PRO_CODE", m.get("PRO_CODE"));//项目编号
				obj.put("CLIENT_ID", m.get("CLIENT_ID"));//客户编号
				obj.put("CUST_NAME", m.get("CUST_NAME"));//客户名称
				obj.put("EQUIPMENINFOS", m.get("EQUIPMENINFOS"));//租赁物信息
				obj.put("COMPANY_NAMES", m.get("COMPANY_NAMES"));//厂商
				obj.put("BJ_MONEY", m.get("BJ_MONEY"));//未交本金
				obj.put("LX_MONEY", m.get("LX_MONEY"));//未交利息
				obj.put("ZJ_MONEY", m.get("ZJ_MONEY"));//未交租金
				
				obj.put("WS_NUM", m.get("WSQC"));//未交期次
				obj.put("WS_YQ", m.get("WYJ_MONEY"));//未收逾期
				obj.put("VALUE_STR", m.get("VALUE_STR"));//保证金
				
				obj.put("PAYLIST_CODE", m.get("D_PROJECT_CODE"));//支付表
				
				double bc_zj = Double.parseDouble(m.get("ZJ_MONEY")==null?"0":m.get("ZJ_MONEY").toString());//租金
				double ws_yq = Double.parseDouble(m.get("WYJ_MONEY")==null?"0":m.get("WYJ_MONEY").toString());//违约金
				double bzj = Double.parseDouble(m.get("VALUE_STR")==null?"0":m.get("VALUE_STR").toString());//保证金
				double deduction_yue = bc_zj + ws_yq - bzj;//抵扣后所需余额
				double sy_bzj = 0.00;
			
				//当剩余租金-本项目的保证金  > 0： 这时抵扣后所需余额不为0， 其值为剩余租金与保证金的差值; 剩余保证金为0
				if(deduction_yue>0){
					obj.put("DEDUCTION_YUE", df.format(deduction_yue));
					obj.put("SY_BZJ", 0);
				}else {//当剩余租金-本项目的保证金  <= 0, 这时抵扣后需要的月为0， 剩余保证金金额为：小于等于零的部分
					obj.put("DEDUCTION_YUE", 0);
					sy_bzj = -deduction_yue;
					obj.put("SY_BZJ", df.format(sy_bzj));
				}
				
				array.add(obj);
			}
		}
		page.addDate(array, Dao.selectInt("deductionBZJ.getJQDKDetailCount", map));
		return page;
	}
	
	/**
	 * 更新申请单表头
	 * doUpdateFHead
	 * @date 2013-11-15 下午03:02:02
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doUpdateFHead(Map<String,Object> map){
		Object data_ = map.get("data");

		Map<String, Object> data = JSONObject.fromObject(data_);

		// 获取页面参数
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		return Dao.update("fundNotEBank.toUpdateFHead", baseData);
	}
	
	/**
	 * 核销， 保存申请明细
	 * doSaveAccount
	 * @date 2013-11-15 下午03:26:10
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doSaveAccount(Map<String,Object> map){
		Object obj = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(obj);
		
		DecimalFormat df = new DecimalFormat(".00"); 
		
		//获取页面基础配置数据   
		Map<String,Object> baseData = JSONObject.fromObject(data.get("getBaseData"));		
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		String FUND_ID=baseData.get("FUND_ID").toString();//申请单id
		
		//金额分拆
		String MONEYORDERSAVE=(baseData.get("MONEYORDERSAVE")!=null && !baseData.get("MONEYORDERSAVE").equals(""))?baseData.get("MONEYORDERSAVE").toString():"";
		FundNotEBankService fneService = new FundNotEBankService();
		if(MONEYORDERSAVE.length()>0){
			fneService.moneyFenChai(MONEYORDERSAVE,FUND_ID);
		}
		
		//查看调用的资金池数据
		List<Map<String,Object>> poolL = Dao.selectList("deductionBZJ.getPoolByFundId", baseData);
		if(poolL!=null){//回更资金池中的數據
			for(int a=0;a<poolL.size(); a++){
				Map<String,Object> poolM = poolL.get(a);
				poolM.put("POOL_ID", poolM.get("FA_POOL_ID"));
				Dao.update("deductionBZJ.updateBZJPoolByPoolid", poolM);
			}
		}
		
		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt("fundNotEBank.toGetAccountCount", baseData);
		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。
			i = Dao.delete("fundNotEBank.delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}
		
		//本次实付
		double FI_REALITY_ACCOUNT=(baseData.get("FI_REALITY_ACCOUNT")!=null && !baseData.get("FI_REALITY_ACCOUNT").equals(""))?Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()):0d;
		

		//来款单子表
		List<Map<String,Object>> listDe=Dao.selectList("deductionBZJ.getDetailDataByFund",baseData);
		double ys_money = 0.00;//剩余租金	
		
		if(listDe!=null){
			for(int k=0; k<listDe.size(); k++){
				Map<String,Object> rentList = listDe.get(k);
				
				ys_money = Double.parseDouble(rentList.get("D_PAY_MONEY").toString());
				baseData.put("D_PROJECT_CODE", rentList.get("D_PROJECT_CODE"));
				//根据客项目id， 查看客户保证金
				List<Map<String,Object>> poolList = Dao.selectList("deductionBZJ.findPoolData", baseData);
				double CANUSE_MONEY = 0.00; //保证金剩余金额
				if(poolList!=null){	
					if(ys_money==0){
						break;
					}
					
					for(int n=0; n<poolList.size(); n++){
						Map<String,Object> poolMap = poolList.get(n);
						CANUSE_MONEY = poolMap.get("CANUSE_MONEY")!=null?Double.parseDouble(poolMap.get("CANUSE_MONEY").toString()):0d;
						if(ys_money >= CANUSE_MONEY){
							rentList.put("FA_POOL_ID", poolMap.get("POOL_ID"));
							rentList.put("D_PAY_MONEY", df.format(CANUSE_MONEY));
							rentList.put("FA_CAN_USE_MONEY", df.format(CANUSE_MONEY));
							//插入實收明細
							i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
							
							//本期剩余应收
							ys_money = ys_money - CANUSE_MONEY;
							rentList.put("D_PAY_MONEY", df.format(ys_money));
							
							CANUSE_MONEY -= CANUSE_MONEY;
							
							//跟新資金池中數據
							poolMap.put("CANUSE_MONEY", df.format(CANUSE_MONEY));
							Dao.update("deductionBZJ.updateBZJPool",poolMap);								
						}else {
							rentList.put("FA_POOL_ID", poolMap.get("POOL_ID"));
							rentList.put("FA_CAN_USE_MONEY", df.format(CANUSE_MONEY));
							//插入實收明細
							i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
							
							ys_money = 0d;
							CANUSE_MONEY -= Double.parseDouble(rentList.get("D_PAY_MONEY").toString());//剩餘保證金
							//跟新資金池中數據
							poolMap.put("CANUSE_MONEY", df.format(CANUSE_MONEY));
							Dao.update("deductionBZJ.updateBZJPool",poolMap);
						}
						
						if(ys_money==0){
							break;
						}
					}
				}
				
				if(FI_REALITY_ACCOUNT>0&&ys_money>0){
					
					if(FI_REALITY_ACCOUNT>=ys_money){//當時候金額大於等於應收金額時， 直接添加實收明細
						//插入實收明細
						rentList.put("D_PAY_MONEY", ys_money);
						i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
						FI_REALITY_ACCOUNT -= ys_money;//實收金額減小
					}else {
						rentList.put("D_PAY_MONEY", FI_REALITY_ACCOUNT);
						//插入實收明細
						i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
						
						ys_money -= FI_REALITY_ACCOUNT; //剩餘應收金額	
						
						FI_REALITY_ACCOUNT = 0;
					}
				}
			}
		}
		return i;
	}
	
	/**
	 * 当资金足额是核销租金
	 * doCheckedFund
	 * @date 2013-11-15 下午03:42:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCheckedFund(Map<String, Object> map) {
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>>  detailData = JSONArray.fromObject(data.get("getDetailData"));
		rentWriteService rentService = new rentWriteService();
		
		int i = 0;
		int n = 0;
		if (detailData.size()>0) {
			for (int j = 0; j < detailData.size(); j++) {
				Map<String, Object> de = detailData.get(j);
				de.put("USERCODE", map.get("USERCODE"));
				de.put("USERNAME", map.get("USERNAME"));
				de.put("FUND_ID", de.get("HEAD_ID"));// 付款单号
				
				
				//查询核销时间，操作罚息
				Map mapTime=Dao.selectOne("rentWrite.queryFundTime", de);
				String FI_ACCOUNT_DATE=(mapTime!=null && mapTime.get("FI_ACCOUNT_DATE")!=null && !mapTime.get("FI_ACCOUNT_DATE").equals(""))?mapTime.get("FI_ACCOUNT_DATE").toString():"";
				
				Dao.update("rentWrite.doUpdateDetail",de);
				// 根据申请单id查看应收金额和实收总金额
				List<Map<String, Object>> detialL = Dao.selectList("deductionBZJ.getDetailDataByFund", de);
				// 当不为空时跟新应收明细
				if (detialL.size() > 0) {
					for (int k = 0; k < detialL.size(); k++) {
						Map<String, Object> realMoney = detialL.get(k);					
						if(realMoney.get("D_BEGING_ID")!=null){
							Dao.update("rentWrite.updateBegin", realMoney);
//							Dao.update("rentWrite.updateBeginJoin",realMoney);
							Map mapMo = Dao.selectOne("rentWrite.selectmony", realMoney);
							if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
								realMoney.put("TIME", FI_ACCOUNT_DATE);
								Dao.update("rentWrite.updateBeginState1", realMoney);
								realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
								rentService.dunUpdateStatus11(realMoney);
							}
							
							//复制一期单签日期的逾期数据
							//Dao.update("deductionBZJ.doChangeOverDueCopy", realMoney);
						}
					}
				}
				
				// 根据申请单id查看支付表编号和期次, 删除除了当前日期的逾期记录
				List<Map<String, Object>> payCodeL = Dao.selectList("deductionBZJ.getDetailDataByFund", de);
				if (detialL.size() > 0) {
					for (int k = 0; k < payCodeL.size(); k++) {
						Map<String, Object> payCode = detialL.get(k);	
						
						// 跟新租金中间表：FI_R_BEGINNING_JOIN, 传入支付表编号和期次
						payCode.put("PAYLIST_CODE", payCode.get("D_PAY_CODE"));
						payCode.put("BEGINNING_NUM", payCode.get("PERIOD"));
						Dao.delete("rentWrite.deleteJoinDate",payCode);
						Dao.insert("rentWrite.insertJoinDate",payCode);
						
						
						//删除除了当前日期的逾期记录
						Dao.delete("deductionBZJ.deleteDunDateAll",payCode);
						
						/*//删除除了当前日期的逾期记录
						OverdueService overDue = new OverdueService();
						overDue.deleteByPay(payCode.get("D_PAY_CODE").toString(), payCode.get("PERIOD").toString());
						
						payCode.put("pay_code", payCode.get("D_PAY_CODE"));
						Dao.update("deductionBZJ.doChangeOverDue",payCode);*/
					}
				}
				
				
				List finnList = new ArrayList();
				Map finnMap = new HashMap();

				// 如果有余额挂账，向池子插入一条数据。为接口提供数据
				Map mapFund1 = Dao.selectOne("rentWrite.queryFundHeadById1", de);
				finnMap.put("Money", mapFund1.get("FI_REALITY_ACCOUNT"));
				finnMap.put("TYPE", "贷方");
				finnMap.put("REMARK", "");
				finnList.add(finnMap);

				Map mapFund = Dao.selectOne("rentWrite.queryFundHeadById", de);
				if (mapFund != null) {
					String FI_FLAG = mapFund.get("FI_FLAG").toString();
					if (FI_FLAG.equals("7")) {// 挂账到承租人资金池
						Map mapPool = new HashMap();
						mapPool.put("SOURCE_ID", mapFund.get("ID"));
						mapPool.put("TYPE", 5);
						mapPool.put("STATUS", 1);
						mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));
						mapPool.put("CANUSE_MONEY", mapFund
								.get("FI_TAGE_MONEY"));
						mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
						Dao.insert("rentWrite.insertPoolDate", mapPool);
					} else if (FI_FLAG.equals("8"))// 挂账供应商电汇资金池
					{
						Map mapPool = new HashMap();
						mapPool.put("SOURCE_ID", mapFund.get("ID"));
						mapPool.put("TYPE", 2);
						mapPool.put("STATUS", 1);
						mapPool.put("BASE_MONEY", mapFund.get("FI_TAGE_MONEY"));
						mapPool.put("CANUSE_MONEY", mapFund
								.get("FI_TAGE_MONEY"));
						mapPool.put("OWNER_ID", mapFund.get("FI_TAGE_ID"));
						Dao.insert("rentWrite.insertPoolDate", mapPool);
					}
					finnMap.put("Money", mapFund.get("FI_TAGE_MONEY"));
					finnMap.put("TYPE", "借方");
					finnMap.put("REMARK", "");
					finnList.add(finnMap);
				}
				
				this.insertBeginByDun(de.get("HEAD_ID").toString(),FI_ACCOUNT_DATE);
				rentService.queryPaylist_codeNum(de.get("HEAD_ID").toString());
				
				// 更新申请表状态，核销时间， 核销银行				
				n = Dao.update("rentWrite.doUpdateHeadStatus", de);
			}

			if (n > 0) {
				return n;
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	/***
	 * 调用资金池中的金额是， 直接核销
	 * doCheckedBZJ
	 * @date 2013-11-17 下午01:57:16
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCheckedBZJ(Map<String,Object> map){
		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
		Object obj = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(obj);
		
		//获取页面基础配置数据   
		Map<String,Object> baseData = JSONObject.fromObject(data.get("getBaseData"));		
		baseData.put("USERCODE", map.get("USERCODE"));
		baseData.put("USERNAME", map.get("USERNAME"));
		String FUND_ID=baseData.get("FUND_ID").toString();//申请单id
		
		//金额分拆
		String MONEYORDERSAVE=(baseData.get("MONEYORDERSAVE")!=null && !baseData.get("MONEYORDERSAVE").equals(""))?baseData.get("MONEYORDERSAVE").toString():"";
		FundNotEBankService fneService = new FundNotEBankService();
		if(MONEYORDERSAVE.length()>0){
			fneService.moneyFenChai(MONEYORDERSAVE,FUND_ID);
		}
		
		//查看调用的资金池数据
		List<Map<String,Object>> poolL = Dao.selectList("deductionBZJ.getPoolByFundId", baseData);
		if(poolL!=null){//回更资金池中的數據
			for(int a=0;a<poolL.size(); a++){
				Map<String,Object> poolM = poolL.get(a);
				poolM.put("POOL_ID", poolM.get("FA_POOL_ID"));
				Dao.update("deductionBZJ.updateBZJPoolByPoolid", poolM);
			}
		}

		// 判断核销实时表中是否已经有实收明细， 如果有则先删除。否则直接添加
		int i = Dao.selectInt("fundNotEBank.toGetAccountCount", baseData);
		if (i > 0) {// 当核资金/租金扣划到账明细中存在数据，先删除。
			i = Dao.delete("fundNotEBank.delAccountByHeadId", baseData);// 但用户重复点击“到账日期”，并修改对应数据后点击提交是先删除已存在的资金扣划明细
		}
		
		//本次实付
		double FI_REALITY_ACCOUNT=(baseData.get("FI_REALITY_ACCOUNT")!=null && !baseData.get("FI_REALITY_ACCOUNT").equals(""))?Double.parseDouble(baseData.get("FI_REALITY_ACCOUNT").toString()):0d;
		//本次应付
		//double FI_REALITY_MONEY=(baseData.get("FI_REALITY_MONEY")!=null && !baseData.get("FI_REALITY_MONEY").equals(""))?Double.parseDouble(baseData.get("FI_REALITY_MONEY").toString()):0d;
		
		List<Map<String,Object>> finnList=new ArrayList<Map<String,Object>>();
		Map<String,Object> finnMap=new HashMap<String,Object>();
		
		Map<String,Object> poolData = Dao.selectOne("fundNotEBank.queryFundHeadById1", baseData);//查看本次来款金额， 为余款挂账做准备
		finnMap.put("Money", poolData.get("FI_REALITY_ACCOUNT"));
		finnMap.put("TYPE", "贷方");
		finnMap.put("REMARK", "");
		finnList.add(finnMap);
		
		double FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT;//剩余来款
		
		//来款单子表
		List<Map<String,Object>> listDe=Dao.selectList("deductionBZJ.getDetailDataByFund",baseData);
		double ys_money = 0.00;//剩余租金	
		
		if(listDe!=null){
			for(int k=0; k<listDe.size(); k++){
				Map<String,Object> rentList = listDe.get(k);
				
				ys_money = Double.parseDouble(rentList.get("D_PAY_MONEY").toString());
				
				//根据客项目id， 查看客户保证金
				List<Map<String,Object>> poolList = Dao.selectList("deductionBZJ.findPoolData", baseData);
				double CANUSE_MONEY = 0d; //保证金剩余金额
				if(poolList!=null){	
					if(ys_money==0){
						break;
					}
					
					for(int n=0; n<poolList.size(); n++){
						Map<String,Object> poolMap = poolList.get(n);
						CANUSE_MONEY = poolMap.get("CANUSE_MONEY")!=null?Double.parseDouble(poolMap.get("CANUSE_MONEY").toString()):0d;
						if(ys_money >= CANUSE_MONEY){
							rentList.put("FA_POOL_ID", poolMap.get("POOL_ID"));
							rentList.put("D_PAY_MONEY", df.format(CANUSE_MONEY));
							rentList.put("FA_CAN_USE_MONEY", df.format(CANUSE_MONEY));
							//插入實收明細
							i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
							
							ys_money -= CANUSE_MONEY;
							rentList.put("D_PAY_MONEY", df.format(ys_money));
							
							CANUSE_MONEY -= CANUSE_MONEY;
							
							//跟新資金池中數據
							poolMap.put("CANUSE_MONEY", df.format(CANUSE_MONEY));
							Dao.update("deductionBZJ.updateBZJPool",poolMap);	
							
							
						}else {
							rentList.put("FA_POOL_ID", poolMap.get("POOL_ID"));
							rentList.put("FA_CAN_USE_MONEY", df.format(CANUSE_MONEY));
							//插入實收明細
							i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
							
							ys_money = 0d;
							CANUSE_MONEY -= Double.parseDouble(rentList.get("D_PAY_MONEY").toString());//剩餘保證金
							//跟新資金池中數據
							poolMap.put("CANUSE_MONEY", df.format(CANUSE_MONEY));
							Dao.update("deductionBZJ.updateBZJPool",poolMap);
						}
						
						if(ys_money==0){
							break;
						}
					}
				}
				
				if(FI_REALITY_ACCOUNT_YU>0&&ys_money>0){
					
					if(FI_REALITY_ACCOUNT_YU>=ys_money){//當時候金額大於等於應收金額時， 直接添加實收明細
						//插入實收明細
						rentList.put("D_PAY_MONEY", ys_money);
						i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
						FI_REALITY_ACCOUNT_YU -= ys_money;//實收金額減小
						FI_REALITY_ACCOUNT_YU  = Double.parseDouble(df.format(FI_REALITY_ACCOUNT_YU));
					}else if(FI_REALITY_ACCOUNT_YU < ys_money){
						if(FI_REALITY_ACCOUNT_YU > 0)//当剩余金额大于零时， 使用剩余金额
						{
							rentList.put("FA_ACCOINT_MONEY", FI_REALITY_ACCOUNT_YU);
							i = Dao.insert("rentWrite.doInsertAccountByDetail", rentList);
							FI_REALITY_ACCOUNT_YU -= 0d;
						}	if(FI_REALITY_ACCOUNT_YU==0){//当剩余金额等于零时， 开始使用资金池的数据
							JSONArray listLi = JSONArray.fromObject(data.get("getPoolData"));
							for(int j=0;j<listLi.size();j++){
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
//												double CANUSE_MONEY_1=Double.parseDouble(pList.get("CANUSE_MONEY").toString());
												if(dichong_money>0 && dichong_money>=CANUSE_MONEY){
													//冻结池子金额
													//插入数据到细表中
													rentList.put("FA_POOL_ID", pList.get("POOL_ID"));
													rentList.put("FA_ACCOINT_MONEY", CANUSE_MONEY);
													rentList.put("FA_CAN_USE_MONEY", CANUSE_MONEY);
													Dao.insert(xmlPath+"doInsertAccount", rentList);
													rentList.put("CANUSE_MONEY", 0);
													Dao.update(xmlPath+"updatePoolStateByfundId",rentList);
												}else{
													//冻结池子金额
													//插入数据到细表中
													rentList.put("FA_POOL_ID", pList.get("POOL_ID"));
													rentList.put("FA_ACCOINT_MONEY", dichong_money);
													rentList.put("FA_CAN_USE_MONEY", CANUSE_MONEY);
													Dao.insert(xmlPath+"doInsertAccount", rentList);
													rentList.put("CANUSE_MONEY", CANUSE_MONEY-dichong_money);
													Dao.update(xmlPath+"updatePoolStateByfundId",rentList);
													dichong_money=0;
												}
												
												if(dichong_money==0){
													break;//跳出
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		rentWriteService rentService = new rentWriteService();
		i = Dao.update("rentWrite.doUpdateHeadStatus", baseData);
		//查询核销时间，操作罚息
		Map mapTime=Dao.selectOne("rentWrite.queryFundTime", baseData);
		String FI_ACCOUNT_DATE=(mapTime!=null && mapTime.get("FI_ACCOUNT_DATE")!=null && !mapTime.get("FI_ACCOUNT_DATE").equals(""))?mapTime.get("FI_ACCOUNT_DATE").toString():"";
		
		Dao.update("rentWrite.doUpdateDetail",baseData);
		if (i > 0) {
			
			// 根据申请单id查看应收金额和实收总金额
			List<Map<String, Object>> detialL = Dao.selectList("deductionBZJ.getDetailDataByFund", baseData);
			// 当不为空时跟新应收明细
			if (detialL.size() > 0) {
				for (int k = 0; k < detialL.size(); k++) {
					Map<String, Object> realMoney = detialL.get(k);					
					if(realMoney.get("D_BEGING_ID")!=null){
						Dao.update("rentWrite.updateBegin", realMoney);
//						Dao.update("rentWrite.updateBeginJoin",realMoney);
						Map mapMo = Dao.selectOne("rentWrite.selectmony", realMoney);
						if (Double.parseDouble(mapMo.get("MONEY").toString()) <= 0) {
							realMoney.put("TIME", FI_ACCOUNT_DATE);
							Dao.update("rentWrite.updateBeginState1", realMoney);
							realMoney.put("BEGINNING_ID", realMoney.get("D_BEGING_ID"));
							rentService.dunUpdateStatus11(realMoney);
						}
						
						//复制一期单签日期的逾期数据
						//Dao.update("deductionBZJ.doChangeOverDueCopy", realMoney);
					}
				}
			}
			
			// 根据申请单id查看支付表编号和期次, 删除除了当前日期的逾期记录
			List<Map<String, Object>> payCodeL = Dao.selectList("deductionBZJ.getDetailDataByFund", baseData);
			if (detialL.size() > 0) {
				for (int k = 0; k < payCodeL.size(); k++) {
					Map<String, Object> payCode = detialL.get(k);	
					
					// 跟新租金中间表：FI_R_BEGINNING_JOIN, 传入支付表编号和期次
					payCode.put("PAYLIST_CODE", payCode.get("D_PAY_CODE"));
					payCode.put("BEGINNING_NUM", payCode.get("PERIOD"));
					Dao.delete("rentWrite.deleteJoinDate",payCode);
					Dao.insert("rentWrite.insertJoinDate",payCode);
					
					//删除除了当前日期的逾期记录
					OverdueService overDue = new OverdueService();
					overDue.deleteByPay(payCode.get("D_PAY_CODE").toString(), payCode.get("PERIOD").toString());
					
					payCode.put("pay_code", payCode.get("D_PAY_CODE"));
					Dao.update("deductionBZJ.doChangeOverDue",payCode);
				}
			}
			

			this.insertBeginByDun(baseData.get("FUND_ID").toString(),FI_ACCOUNT_DATE);
			rentService.queryPaylist_codeNum(baseData.get("FUND_ID").toString());
		}
		return i;
	}
	
	//核销冲红申请单
	@SuppressWarnings("unchecked")
	public int doHXBzj(Map<String,Object> map){	

		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
		 
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));
		
		int n = 0;
		
		if(detailData.size()>0){
			for(int i=0; i<detailData.size(); i++){
				Map<String,Object> detail = detailData.get(i);
				detail.put("FUND_ID", detail.get("HEAD_ID"));
				//根据申请单获取项目对应资金池id
				List<Map<String,Object>> pool = Dao.selectList("deductionBZJ.findCHBZJ", detail);
				//父类付款单id
				String FI_SUPERCLASS_ID = "";
				//金额
				double bzj_money = 0.00;
				for(int k=0; k<pool.size(); k++){
					Map<String,Object> m = pool.get(k);
					detail.put("USERNAME", map.get("USERNAME"));
					detail.put("USERCODE", map.get("USERCODE"));
					FI_SUPERCLASS_ID += m.get("SOURCE_ID")!=null&&!"".equals(m.get("SOURCE_ID"))?m.get("SOURCE_ID").toString()+",":"";
					bzj_money += m.get("BASE_MONEY")!=null&&!"".equals(m.get("BASE_MONEY"))?Double.parseDouble(m.get("BASE_MONEY").toString()):0d;
					detail.put("FI_REALITY_MONEY", -bzj_money);
					detail.put("FI_REALITY_ACCOUNT",-bzj_money);
					detail.put("FI_SUPERCLASS_ID", FI_SUPERCLASS_ID);
					//查看申请单信息
					Map<String,Object> mFund = Dao.selectOne("deductionBZJ.getFundBySuppId", detail);
					if(mFund!=null){
						detail.put("FUND_ID",mFund.get("ID"));
					}
					detail.put("FI_STATUS", 2);
					//跟新申请单
					n = Dao.update("fundNotEBank.toUpdateFHead", detail);
					
					//根据申请单id， 查看申请明细
					Map<String,Object> detailM = Dao.selectOne("deductionBZJ.getDetailDataByFund", detail);
					
					detailM.put("D_FUND_ID", mFund.get("ID"));
					detailM.put("D_BEGING_ID", detailM.get("D_BEGING_ID"));
					detailM.put("ID", detailM.get("ID"));
					detailM.put("D_PAY_MONEY", df.format(bzj_money));
					
					//添加实收明细
					n = Dao.insert("rentWrite.doInsertAccountByDetail", detailM);
					
//					if(n>0){
//						 Map<String,Object> poolM = new HashMap<String,Object>();
//						 poolM.put("FA_POOL_ID", m.get("POOL_ID"));
//						 poolM.put("CANUSE_MONEY", 0);
//						 n = Dao.update("fundNotEBank.updatePoolStateByfundId", poolM);
//					}
					
					FI_SUPERCLASS_ID = "";
						
				}
			
			}
		}
		return n;
	}
	
	//核销冲红申请单
	@SuppressWarnings("unchecked")
	public int doHXBzj_1(Map<String,Object> map){	

		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
		 
		// 获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		Map<String, Object> baseData = JSONObject.fromObject(data.get("getBaseData"));

		Map<String,Object> dataM = new HashMap<String,Object>();
		int n = 0;
		
		if(baseData != null){
			//根据申请单获取项目对应资金池id
			List<Map<String,Object>> pool = Dao.selectList("deductionBZJ.findCHBZJ", baseData);
			//父类付款单id
			String FI_SUPERCLASS_ID = "";
			//金额
			double bzj_money = 0.00;
			for(int k=0; k<pool.size(); k++){
				Map<String,Object> m = pool.get(k);
				dataM.put("USERNAME", map.get("USERNAME"));
				dataM.put("USERCODE", map.get("USERCODE"));
				FI_SUPERCLASS_ID += m.get("SOURCE_ID")!=null&&!"".equals(m.get("SOURCE_ID"))?m.get("SOURCE_ID").toString()+",":"";
				bzj_money += m.get("BASE_MONEY")!=null&&!"".equals(m.get("BASE_MONEY"))?Double.parseDouble(m.get("BASE_MONEY").toString()):0d;
				dataM.put("FI_REALITY_MONEY", -bzj_money);
				dataM.put("FI_REALITY_ACCOUNT",-bzj_money);
				dataM.put("FI_SUPERCLASS_ID", FI_SUPERCLASS_ID);
				//查看申请单信息
				Map<String,Object> mFund = Dao.selectOne("deductionBZJ.getFundBySuppId", dataM);
				if(mFund!=null){
					dataM.put("FUND_ID",mFund.get("ID"));
				}
				dataM.put("FI_STATUS", 2);
				//跟新申请单
				n = Dao.update("fundNotEBank.toUpdateFHead", dataM);
				
				//根据申请单id， 查看申请明细
				Map<String,Object> detailM = Dao.selectOne("deductionBZJ.getDetailDataByFund", dataM);
				
				detailM.put("D_FUND_ID", mFund.get("ID"));
				detailM.put("D_BEGING_ID", detailM.get("D_BEGING_ID"));
				detailM.put("ID", detailM.get("ID"));
				detailM.put("D_PAY_MONEY", df.format(bzj_money));
				
				//添加实收明细
				n = Dao.insert("rentWrite.doInsertAccountByDetail", detailM);
				
				if(n>0){
					 Map<String,Object> poolM = new HashMap<String,Object>();
					 poolM.put("FA_POOL_ID", m.get("POOL_ID"));
					 poolM.put("CANUSE_MONEY", 0);
					 n = Dao.update("fundNotEBank.updatePoolStateByfundId", poolM);
				}
				
				FI_SUPERCLASS_ID = "";
			}
		}
		
		return n;
	}
	
	// 当租金都核销完成后将当期违约金发出来可以核销map里有BEGINNING_ID
	@SuppressWarnings("unchecked")
	public void dunUpdateStatusToday(Map map) {
		Map<String,Object> mapBegin=Dao.selectOne("rentWrite.mapBegin", map);
		Map<String,Object> payListMap = Dao.selectOne("rentWrite.queryDunMoneyStatus", mapBegin);
		Dao.update("deductionBZJ.updateRENT_PAIDDueToday",payListMap);
		if (payListMap != null && payListMap.get("SHENYUMONEY") != null
				&& !payListMap.get("SHENYUMONEY").equals("")) {
			double SHENYUMONEY = Double.parseDouble(payListMap.get(
			"SHENYUMONEY").toString());
			if (SHENYUMONEY <= 0.0001)// 标示这一期都核销完了，修改状态
			{
				// 1，修改实际到账日期时候的状态为可核销状态
				// 2.删除比时间到账日期大的日期生产的罚息
				// 3.重新计算罚息
				payListMap.put("TIME", map.get("TIME"));
				Dao.update("rentWrite.updateDunStatus", payListMap);
				Dao.delete("rentWrite.deleteDunDate", payListMap);
			}
		}
	}
	
	// 当核销完成后，通过核销单判断此核销单中有没有核销违约金，当有违约金将此条数据插入到起初表
	public void insertBeginByDun(String fund_id, Object REALITY_TIME) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("FUND_ID", fund_id);
		List<Map<String,Object>> list = Dao.selectList("rentWrite.queryDDunByFund", map);
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> mapDate = (Map<String,Object>) list.get(i);
			// 修改逾期表中该违约金的状态
			Map<String,Object> mapDunDate = new HashMap<String,Object>();
			mapDunDate.put("DUE_STATUS", 2);
			mapDunDate.put("OVERDUE_STATUS", 1);
			mapDunDate.put("PAYLIST_CODE", mapDate.get("D_PAY_CODE"));
			mapDunDate.put("BEGINNING_NUM", mapDate.get("PERIOD"));
			mapDunDate.put("REALITY_TIME", REALITY_TIME);
			mapDunDate.put("D_RECEIVE_MONEY", mapDate.get("D_PAY_MONEY"));
			Map<String,Object> dunMap=Dao.selectOne("rentWrite.fi_overDueOne",mapDunDate);
			if(dunMap!=null){
				mapDunDate.put("DUNID", dunMap.get("DUNID"));
				Dao.update("rentWrite.updateOverDunStaute11", mapDunDate);
				Dao.delete("rentWrite.deleteDunDateAll11", mapDunDate);
			}
			
			Dao.update("rentWrite.updateFundDateByJoinDunNew", mapDunDate);
		}
	}
	
	
	/**
	 * 判断是否有还没有核销的违约金--即为当期的违约金
	 * getDetailDataByFundId
	 * @date 2013-12-24 下午03:14:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getDetailDataByFundId(Map<String,Object> map){
		//根据核销单查询所有支付表期次和支付表号
		Map<String, Object> data = JSONObject.fromObject(map.get("data"));
		List<Map<String, Object>>  detailData = JSONArray.fromObject(data.get("getDetailData"));
		
		String overDuePayCode = "";
		if(detailData.size()>0){
			for(int a=0; a<detailData.size();a++){
				Map<String,Object> mm = detailData.get(a);
				mm.put("FUND_ID", mm.get("HEAD_ID"));// 付款单号
				List<Map<String,Object>> detailL = Dao.selectList("deductionBZJ.getDetailDataByFundId", mm);
				if(detailL.size()>0){
					for(int i=0; i<detailL.size(); i++){
						Map<String,Object> overDue = detailL.get(i);
						Map<String,Object> overDue_ = Dao.selectOne("deductionBZJ.getOverDuePayCodebyFundId", overDue);
						if(overDue_!=null){
							overDuePayCode += overDue_.get("PAY_CODE")==null?"":overDue_.get("PAY_CODE").toString()+"  ";
						}
					}
				}
			}
		}
		return overDuePayCode;
	}
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-结清抵扣作废                                                                                                       *
	 ************************                                                           ************************
	 **********************************************************************************************************/
	/**
	 * 资金扣划非网银申请-作废操作（doCancellation）
	 * 
	 * @date 2013-10-9 下午04:58:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCancellation(Map<String, Object> map) {
		// 获取页面参数
		Map<String, Object> data = JSONObject.fromObject(map.get("data"));
		List<Map<String, Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));

		int i = 0;
		int n = 0;
		String HEAD_ID = "";
		// 当页面参数不为空时， 遍历参数。并作废付款单
		if (detailData.size()>0) {
			for (int k = 0; k < detailData.size(); k++) {

				Map<String, Object> de = detailData.get(k);
				// 付款单号
				 HEAD_ID =  de.get("HEAD_ID").toString();
				
				// 根据付款单号查看所有选中的项目,获取应收起初表id, 将应收起初起初表置为可申请状态
				List<Map<String, Object>> projectL = Dao.selectList(xmlPath + "getFundDetailByFundId", HEAD_ID);
				if (projectL.size()>0) {// 当不为空时					
					for(int a=0; a<projectL.size(); a++){
						Map<String,Object> mm = projectL.get(a);
						String PROJECT_ID = mm.get("PROJECT_ID")==null?"":mm.get("PROJECT_ID").toString();
						
						//查看项目所在资金池
						mm.put("PRO_ID",PROJECT_ID);
						List<Map<String,Object>> head = Dao.selectList(xmlPath+"getPoolData_1", mm);
						//父类付款单id
						String FI_SUPERCLASS_ID = "";
						if(head!=null){
							for(int b=0; b<head.size(); b++){
								Map<String,Object> m = head.get(b);
							    FI_SUPERCLASS_ID += m.get("SOURCE_ID")!=null&&!"".equals(m.get("SOURCE_ID"))?m.get("SOURCE_ID").toString()+",":"";
							}
						}
						
						String fund_id = Dao.selectOne(xmlPath+"getZfch",FI_SUPERCLASS_ID);
						
						// 根据付款单号作废付款单应收明细
						Dao.update(xmlPath + "deleteFundDetailByIds", fund_id);
						
						// 根据付款单号作废付款单实收明细
						Dao.update(xmlPath + "deleteFundAccountByIds", fund_id);
						
						// 根据付款单号作废付款单
						n = Dao.update(xmlPath + "deleteFundHeadByIds", fund_id);
					}
					
					
					
					List<Map<String,Object>> aa = Dao.selectList(xmlPath+"getBeginningList1", HEAD_ID);
					if(aa.size()>0){
						for(int a=0; a<aa.size(); a++){
							Map<String,Object> b = aa.get(a);
							if(b.get("D_STATUS")!=null&&"1".endsWith(b.get("D_STATUS").toString())&&b.get("D_BEGING_ID")!=null){
								String beginning = b.get("D_BEGING_ID").toString();								
								 //根据应收期初表编号， 回更应收起初表状态
								 Dao.update(xmlPath + "doUpdatebeginingStatus", beginning);
							}else if(b.get("D_STATUS")!=null&&"2".endsWith(b.get("D_STATUS").toString())&&b.get("D_PAY_CODE")!=null&&b.get("PERIOD")!=null){
								String PAY_CODE = b.get("D_PAY_CODE").toString();	
								String PERIOD = b.get("PERIOD").toString();	
								b.put("PAY_CODE", PAY_CODE);								
								b.put("PERIOD", PERIOD);
								b.put("HEAD_ID", HEAD_ID);
								 
								//根据申请单号， 回更逾期表数据
								 Dao.update(xmlPath + "doUpdateOVERDUEstatus", b);
							}
						}
					}
					 
					//根据申请单号， 回更资金池数据
					 Dao.update(xmlPath + "doUpdatePOOLmoney", HEAD_ID);	
					 
					 //根据应收期初表编号， 回更应收起初表状态
					 Dao.update(xmlPath + "doUpdatebeginingJoinStatus", HEAD_ID);	
					
					// 根据付款单号作废付款单应收明细
					Dao.delete(xmlPath + "deleteFundDetailByIds", HEAD_ID);
					
					// 根据付款单号作废付款单实收明细
					Dao.delete(xmlPath + "deleteFundAccountByIds", HEAD_ID);
					
					// 根据付款单号作废付款单
					i = Dao.delete(xmlPath + "deleteFundHeadByIds", HEAD_ID);
				}
			}
			return i+n;
		}

		return i+n;
	}
	
	
	/**
	 * 导出Excel 基于查询
	 * excelMor
	 * @date 2013-10-21 下午05:05:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Excel excelDetail(Map<String,Object> params)  {
		
		params.put("BZJ", "保证金");
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(xmlPath+"getRefundDetailData",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>(); 
		title.put("PRO_CODE", "项目编号");
		title.put("CUST_NAME", "客户名称");
		title.put("PRODUCT_NAME", "租赁物名称");
		title.put("LEASE_TOPRIC", "租赁物购买价款");
		title.put("FINANCE_TOPRIC", "融资金额");
		title.put("COMPANY_NAME", "厂商");
		title.put("TYPE_NAME", "出厂编号");
		title.put("AMOUNT", "台量");
		title.put("START_CONFIRM_DATE", "起租确认日期");
		title.put("RE_DATE", "预退款日期");
		title.put("BZJ", "款项名称");
		title.put("RE_MONEY", "退款金额");
		title.put("PAY_DATE", "租赁到期日");
		//封装excle
		Excel excel = new Excel();
		excel.setName("承租人保证金退款详细"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		return excel;
	}
	 
	/**
	 * 冲抵细单导出
	 * excelCDDetail
	 * @date 2013-11-19 下午09:21:46
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Excel excelCDDetail(Map<String, Object> params){
		params.put("BJ_MONEY","本金");
		params.put("LX_MONEY","利息");
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(xmlPath+"getCDDetainlByFundId",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>(); 
		title.put("FUND_ID", "付款单号");
		title.put("FI_ACCOUNT_DATE", "付款时间");
		title.put("PRO_CODE", "项目编号");
		title.put("PRO_NAME", "项目名称");
		title.put("SUP_SHORTNAME", "供应商");
		title.put("PRODUCT_NAME", "租赁物");
		title.put("LEASE_TERM", "租赁期次");
		title.put("CUST_NAME", "客户名称");
		title.put("D_RECEIVE_DATE", "计划日期");
		title.put("FA_CAN_USE_MONEY", "保证金");
		title.put("CANUSE_MONEY", "剩余保证金");
		title.put("ZYBZJ", "剩余保证金");
		title.put("ZJ_MONEY", "租金");
		title.put("BJ_MONEY", "本金");
		title.put("LX_MONEY", "利息");
		title.put("XJZJ_MONEY", "需交租金");
		title.put("WS_YQ", "逾期金额");
		//封装excle
		Excel excel = new Excel();
		excel.setName("承租人保证金退款详细"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		return excel;
	}
	
	//查询是否逾期，逾期返回false，没有的话返回true
	public boolean IsDunByPayListCOde(Map<String, Object> map){
		int num=Dao.selectInt(xmlPath+"IsDunByPayListCOde", map);
		if(num>0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void updatePayInfo(Map<String, Object> map){
		//冻结支付表，刷新租金核销数据
		Dao.update(xmlPath+"updatePayInfoByPaylistCode",map);
		Dao.update(xmlPath+"upMoneyToJoin",map);
		Dao.update(xmlPath+"updatePoolInfoStatus",map);
	}
	
	//查询页面显示基本信息
	public Map queryBaseInfoByPoolID(Map<String, Object> map){
		return Dao.selectOne(xmlPath+"queryBaseInfoByPoolID",map);
	}
	
	
	
	//平均抵扣
	public List queryPJDKList(Map<String, Object> map){
		//抵扣完成后剩余金额
		double DKSHENGYUMONEY=0d;
		double shiyongMoney=0d;//已使用金额
		List list=Dao.selectList(xmlPath+"queryPayListByPaylistCode",map);
		if(map !=null && map.get("CANUSE_MONEY")!=null){
			double CANUSE_MONEY=Double.parseDouble(map.get("CANUSE_MONEY").toString());
			if(CANUSE_MONEY>0){//有抵扣的金额
				
				//查询今天及今天以后的期次
				Map mapPeroid=Dao.selectOne(xmlPath+"queryPeriodByPaylistCode",map);
				if(mapPeroid !=null && mapPeroid.get("PEROID")!=null && !mapPeroid.get("PEROID").equals("")){
					int PEROID=Integer.parseInt(mapPeroid.get("PEROID").toString());
					
					//查询可以抵扣的总期次
					int DKQC=Dao.selectInt(xmlPath+"dkPeriodByPaylistCode", map);
					
					DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
					//算出平均金额
					double PjMoney=Double.parseDouble(df.format(CANUSE_MONEY/DKQC));
					
					int shiyongNum=0;//已使用期次
					
					for(int i=0;i<list.size();i++){
						Map mapDate=(Map)list.get(i);
						int BEGINNING_NUM=Integer.parseInt(mapDate.get("BEGINNING_NUM").toString());
						if(BEGINNING_NUM>=PEROID)
						{
							//处理
							double BEGINNING_SHENG=Double.parseDouble(mapDate.get("BEGINNING_SHENG").toString());//可抵扣金额
							if(BEGINNING_SHENG<PjMoney){
								mapDate.put("DKMONEY", BEGINNING_SHENG);
								shiyongMoney=shiyongMoney+BEGINNING_SHENG;
								shiyongNum++;
								if(shiyongNum<DKQC){//如果小于总期次，说明下面还有期次
									int newDkqc=DKQC-shiyongNum;
									double newMoney=CANUSE_MONEY-shiyongMoney;
									PjMoney=Double.parseDouble(df.format(newMoney/newDkqc));
								}
								
							}
							else{//抵扣金额不大于剩余金额
								if(i == list.size()-1){//最后一期
									mapDate.put("DKMONEY", CANUSE_MONEY-shiyongMoney);
									shiyongMoney=CANUSE_MONEY;
									shiyongNum++;
								}
								else{
									mapDate.put("DKMONEY", PjMoney);
									shiyongMoney=shiyongMoney+PjMoney;
									shiyongNum++;
								}
							}
						}else{//不能抵扣
							mapDate.put("DKMONEY", 0);
						}
						
					}
					
					DKSHENGYUMONEY=CANUSE_MONEY-shiyongMoney;//剩余金额
					
				}
				
				
			}
		}
		
		map.put("DKSHENGYUMONEY", DKSHENGYUMONEY);
		map.put("SHIYONGMONEY", shiyongMoney);
		
		return list;
	}
	
	//期末冲抵
	public List queryQMDKList(Map<String, Object> map){
		//抵扣完成后剩余金额
		double DKSHENGYUMONEY=0d;
		double shiyongMoney=0d;//已使用金额
		List list=Dao.selectList(xmlPath+"queryPayListByPaylistCode",map);
		if(map !=null && map.get("CANUSE_MONEY")!=null){
			double CANUSE_MONEY=Double.parseDouble(map.get("CANUSE_MONEY").toString());
			if(CANUSE_MONEY>0){//有抵扣的金额
				
				//查询今天及今天以后的期次
				Map mapPeroid=Dao.selectOne(xmlPath+"queryPeriodByPaylistCode",map);
				if(mapPeroid !=null && mapPeroid.get("PEROID")!=null && !mapPeroid.get("PEROID").equals("")){
					int PEROID=Integer.parseInt(mapPeroid.get("PEROID").toString());
					
					DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理

					for(int i=0;i<list.size();i++){
						Map mapDate=(Map)list.get(i);
						int BEGINNING_NUM=Integer.parseInt(mapDate.get("BEGINNING_NUM").toString());
						if(BEGINNING_NUM>=PEROID)
						{
							//处理
							double BEGINNING_SHENG=Double.parseDouble(mapDate.get("BEGINNING_SHENG").toString());//可抵扣金额
							mapDate.put("DKMONEY", BEGINNING_SHENG);
							shiyongMoney=shiyongMoney+BEGINNING_SHENG;
							
						}else{//不能抵扣
							mapDate.put("DKMONEY", 0);
						}
						
					}
					
					DKSHENGYUMONEY=CANUSE_MONEY-shiyongMoney;//剩余金额
					
				}
				
				
			}
		}
		
		map.put("SHIYONGMONEY", shiyongMoney);
		map.put("DKSHENGYUMONEY", DKSHENGYUMONEY);
		
		
		return list;
	}
	
	//平均冲抵通过方法  
	public void isPJCDPass(String POOL_ID,String PAYLIST_CODE){
		Map mapBase=new HashMap();
		mapBase.put("POOL_ID", POOL_ID);
		mapBase.put("PAYLIST_CODE", PAYLIST_CODE);
		Map map=this.queryBaseInfoByPoolID(mapBase);
		System.out.println("---------------111111----------map="+map);
		List dataList=this.queryPJDKList(map);
		System.out.println("---------------22222----------map="+map);
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE=Dao.selectOne("fi.fund.getFundCode");
		Map mapDate=new HashMap();
		//1.创建来款资金
		{
			
			mapDate.put("PAY_ID", map.get("PAY_ID"));
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("FUND_ACCEPT_DATE", map.get("PAY_DATE"));//来款时间
			mapDate.put("FUND_COMENAME", map.get("CUST_NAME"));//来款时间
			mapDate.put("FUND_RECEIVE_MONEY", map.get("SHIYONGMONEY"));//来款金额
			mapDate.put("FUND_DOCKET", "客户保证金平均冲抵！");//来款摘要
			mapDate.put("FUND_ID", FUND_ID);
			mapDate.put("FUND_FUNDCODE", FUND_CODE);
			mapDate.put("FUND_NOTDECO_STATE", "7");
			mapDate.put("FUND_STATUS", "0");
			mapDate.put("FUND_ISCONGEAL", "0");
			mapDate.put("FUND_RED_STATUS", "0");
			mapDate.put("FUND_PRANT_ID", "0");
			mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
			mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
			mapDate.put("FUND_CLIENT_ID", map.get("CUST_ID"));//认款
			mapDate.put("FUND_CLIENT_NAME", map.get("CUST_NAME"));
			mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
			mapDate.put("FUND_TYPE", 2);
			Dao.insert("fi.fund.add", mapDate);
		}
		
		map.put("FUND_ID", FUND_ID);
		//2.来款资金平摊到每一期
		this.FundDec(dataList,mapDate,map);
		//3.来款资金写入到明细表
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", POOL_ID);
		poolDeatil.put("FUND_ID", FUND_ID);
		poolDeatil.put("FUND_CODE", FUND_CODE);
		poolDeatil.put("TYPE", 1);
		poolDeatil.put("USERMONEY", map.get("SHIYONGMONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
		//4.修改资金池主表信息
		System.out.println("--------------map="+map);
		map.put("POOL_ID", POOL_ID);
		Dao.update(xmlPath+"updatePoolInfoByPoolId", map);
		//5.恢复支付表状态
		Dao.update(xmlPath+"updateStatusByPaylistCode", map);
		//6.核销中间表数据刷新
		Dao.update(xmlPath+"upMoneyToJoin",map);
		
	}
	//平均冲抵不通过方法
	public void isPJCDNoPass(String POOL_ID,String PAYLIST_CODE){
		Map map=new HashMap();
		map.put("POOL_ID", POOL_ID);
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		//1.修改支付表状态
		Dao.update(xmlPath+"updateStatusByPaylistCode", map);
		//2.核销中间表数据刷新
		Dao.update(xmlPath+"upMoneyToJoin",map);
		//3.修改资金池主表信息
		Dao.update(xmlPath+"updatePoolInfoStatusBACK",map);
	}
	
	public void FundDec(List dataList,Map FUNDMAP,Map baseInfo){
		Map map=new HashMap();
		String fund_head_id = Dao.getSequence("SEQ_FUND_HEAD");
		
		map.put("USER_CODE", Security.getUser().getCode());
		map.put("USER_NAME", Security.getUser().getName());
		map.put("fund_head_id", fund_head_id);
		
		int num=0;
		
		for(int hh=0;hh<dataList.size();hh++){
			Map moneyMap=(Map)dataList.get(hh);
			double DKMONEY=(moneyMap !=null && moneyMap.get("DKMONEY")!=null && !moneyMap.get("DKMONEY").equals(""))?Double.parseDouble(moneyMap.get("DKMONEY").toString()):0d;
			if(DKMONEY>0){//需要抵扣
				num++;
				List listDetail = Dao.selectList("rentWriteNew.queryDetailByPayNum", moneyMap);
				if (listDetail.size() > 0)// 确保费用没用被其他方式核销掉或者锁定
				{

					for (int i = 0; i < listDetail.size(); i++) {
						Map detaMap = (Map) listDetail.get(i);
						map.put("FI_PRO_NAME", "租金");
						map.put("FI_FUND_CODE", "1");
						detaMap.put("FUND_ACCEPT_DATE", FUNDMAP.get("FUND_ACCEPT_DATE"));
						detaMap.put("fund_head_id", fund_head_id);
						detaMap.put("CUSTNAME", baseInfo.get("CUST_NAME"));
						detaMap.put("CUST_ID", baseInfo.get("CUST_ID"));
						detaMap.put("PRO_CODE", baseInfo.get("PRO_CODE"));
						int flag = 0;
						
								if (i == 0)// 先扣利息
								{
									double moneyLixi = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									if (DKMONEY <= moneyLixi  && DKMONEY>0) {
										detaMap.put("D_PAY_MONEYLB", moneyLixi);
										detaMap.put("MONEYCURR", DKMONEY);
										detaMap.put("D_STATUS", 1);
										flag = Dao.insert("rentWriteNew.createDetailByBankDate", detaMap);
										DKMONEY = 0;
									} else if(DKMONEY > moneyLixi && DKMONEY>0){
										detaMap.put("D_PAY_MONEYLB", moneyLixi);
										detaMap.put("MONEYCURR", moneyLixi);
										detaMap.put("D_STATUS", 1);
										flag = Dao.insert("rentWriteNew.createDetailByBankDate", detaMap);
										DKMONEY = DKMONEY - moneyLixi;
									}
								} else// 再扣本金
								{
									double moneyBenj = Double.parseDouble(detaMap.get("BEGINNING_MONEY").toString());
									detaMap.put("D_PAY_MONEYLB", moneyBenj);
									if (DKMONEY <= moneyBenj && DKMONEY>0) {
										detaMap.put("MONEYCURR", DKMONEY);
										detaMap.put("D_STATUS", 1);
										flag = Dao.insert("rentWriteNew.createDetailByBankDate", detaMap);
										DKMONEY = 0;
									} else if(DKMONEY>moneyBenj  && DKMONEY>0){
										detaMap.put("D_PAY_MONEYLB", moneyBenj);
										detaMap.put("MONEYCURR", moneyBenj);
										detaMap.put("D_STATUS", 1);
										flag = Dao.insert("rentWriteNew.createDetailByBankDate", detaMap);
										DKMONEY = DKMONEY - moneyBenj;
									}
								}
						
						
					}
				}
			}
		}
		
		
		String FI_FAG = "13";
		
		map.put("FI_FAG",FI_FAG);
		map.put("USER_ID", Security.getUser().getId());
		// 组织机构应该取缓存 后面在改
		map.put("ORG_ID", Security.getUser().getOrg().getId());
		
		map.put("APP_CREATE", "租金科");
		map.put("FI_PAY_MONEY", baseInfo.get("SHIYONGMONEY"));//核销单金额
		map.put("FI_PROJECT_NUM", num);//核销单项目数
		map.put("FI_PAY_TYPE", "1");//默认值 没意义
		map.put("BANK_NAME", baseInfo.get("CUST_NAME"));//来款人
//		map.put("BANK_ACCOUNT", src.get("FUND_COMECODE"));//来款帐号
		map.put("FI_ACCOUNT_DATE", FUNDMAP.get("FUND_ACCEPT_DATE"));
		map.put("FI_PAY_DATE", FUNDMAP.get("FUND_ACCEPT_DATE"));
		map.put("fund_head_id", fund_head_id);
		map.put("FUND_ID", baseInfo.get("FUND_ID"));
		Dao.insert("rentWriteNew.createFundHead_Not", map);
		
		//核销确认处理
		FundDecService fundS=new FundDecService();
		fundS.HXPASS(fund_head_id);
	}
	
	//期末冲抵通过方法
	public void isQMCDPass(String POOL_ID,String PAYLIST_CODE){
		Map mapBase=new HashMap();
		mapBase.put("POOL_ID", POOL_ID);
		mapBase.put("PAYLIST_CODE", PAYLIST_CODE);
		Map map=this.queryBaseInfoByPoolID(mapBase);
		List dataList=this.queryQMDKList(map);
		
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE=Dao.selectOne("fi.fund.getFundCode");
		Map mapDate=new HashMap();
		//1.创建来款资金
		{
			mapDate.put("PAY_ID", map.get("PAY_ID"));
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("FUND_ACCEPT_DATE", map.get("PAY_DATE"));//来款时间
			mapDate.put("FUND_COMENAME", map.get("CUST_NAME"));//来款时间
			mapDate.put("FUND_RECEIVE_MONEY", map.get("SHIYONGMONEY"));//来款金额
			mapDate.put("FUND_DOCKET", "客户保证金平均冲抵！");//来款摘要
			mapDate.put("FUND_ID", FUND_ID);
			mapDate.put("FUND_FUNDCODE", FUND_CODE);
			mapDate.put("FUND_NOTDECO_STATE", "7");
			mapDate.put("FUND_STATUS", "0");
			mapDate.put("FUND_ISCONGEAL", "0");
			mapDate.put("FUND_RED_STATUS", "0");
			mapDate.put("FUND_PRANT_ID", "0");
			mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
			mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
			mapDate.put("FUND_CLIENT_ID", map.get("CUST_ID"));//认款
			mapDate.put("FUND_CLIENT_NAME", map.get("CUST_NAME"));
			mapDate.put("FUND_PAY_CODE", PAYLIST_CODE);//将要分解到那张支付表
			mapDate.put("FUND_TYPE", 2);
			Dao.insert("fi.fund.add", mapDate);
		}
		
		map.put("FUND_ID", FUND_ID);
		//2.来款资金核销期末数期
		this.FundDec(dataList,mapDate,map);
		//3.来款资金写入到明细表
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", POOL_ID);
		poolDeatil.put("FUND_ID", FUND_ID);
		poolDeatil.put("FUND_CODE", FUND_CODE);
		poolDeatil.put("TYPE", 1);
		poolDeatil.put("USERMONEY", map.get("SHIYONGMONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
		//4.修改资金池主表信息
		map.put("POOL_ID", POOL_ID);
		Dao.update(xmlPath+"updatePoolInfoByPoolId1", map);
		//5.恢复支付表状态
		Dao.update(xmlPath+"updateStatusByPaylistCode", map);
		//6.核销中间表数据刷新
		Dao.update(xmlPath+"upMoneyToJoin",map);
	}
	
	//期末冲抵不通过方法
	public void isQMCDNoPass(String POOL_ID,String PAYLIST_CODE){
		Map map=new HashMap();
		map.put("POOL_ID", POOL_ID);
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		//1.修改支付表状态
		Dao.update(xmlPath+"updateStatusByPaylistCode", map);
		//2.核销中间表数据刷新
		Dao.update(xmlPath+"upMoneyToJoin",map);
		//3.修改资金池主表信息
		Dao.update(xmlPath+"updatePoolInfoStatusBACK",map);
	}
	
	public int doPayListCodeIng(Map<String, Object> m){
		return Dao.selectInt("PayTask.doPayListCodeIng", m);
	}
	
	public List queryDetailByPoolId(Map<String, Object> m){
		return Dao.selectList(xmlPath+"queryDetailByPoolId",m);
	}
	
	//查询页面显示基本信息
	public Map queryBaseInfoByPoolIDBackMoney(Map<String, Object> map){
		return Dao.selectOne(xmlPath+"queryBaseInfoByPoolIDBackMoney",map);
	}
	
	public void isTKPass(String POOL_ID,String PAYLIST_CODE){
		Map mapDetailInfo=Dao.selectOne(xmlPath+"queryDetailInfoByPoolId", POOL_ID);
		//将可用金额修改及状态
		Dao.update(xmlPath+"updatePoolStatus", POOL_ID);
		Dao.update(xmlPath+"updatePoolHeadStatus2",mapDetailInfo);
		//写入子表信息
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", POOL_ID);
		poolDeatil.put("FUND_ID", mapDetailInfo.get("RE_ID"));
		poolDeatil.put("FUND_CODE", mapDetailInfo.get("RE_CODE"));
		poolDeatil.put("TYPE", 2);
		poolDeatil.put("USERMONEY", mapDetailInfo.get("RE_MONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
	}
	
	public void isTKNoPass(String POOL_ID,String PAYLIST_CODE){
		Map mapDetailInfo=Dao.selectOne(xmlPath+"queryDetailInfoByPoolId", POOL_ID);
		//修改保证金表状态
		//查询支付表是否结束，如结束状态为解冻，如没有结束为冻结
		int num=Dao.selectInt(xmlPath+"queryPayHeadStatusInfo", PAYLIST_CODE);//3/6结束
		if(num>0){
			Dao.update(xmlPath+"updatePoolStatus1", POOL_ID);
		}
		else{
			Dao.update(xmlPath+"updatePoolStatus0", POOL_ID);
		}
		Dao.update(xmlPath+"updatePoolHeadStatus3",mapDetailInfo);
		
		Dao.insert(xmlPath+"callPool");
	}
	
	public int changeMoneyCome(Map<String, Object> mapInfo){
		Map map=this.queryBaseInfoByPoolID(mapInfo);
		
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE=Dao.selectOne("fi.fund.getFundCode");
		Map mapDate=new HashMap();
		//1.创建来款资金
		{
			mapDate.put("PAY_ID", map.get("PAY_ID"));
			mapDate.put("PAYLIST_CODE", mapInfo.get("PAYLIST_CODE"));
			mapDate.put("FUND_ACCEPT_DATE", map.get("PAY_DATE"));//来款时间
			mapDate.put("FUND_COMENAME", map.get("CUST_NAME"));//来款时间
			mapDate.put("FUND_RECEIVE_MONEY", mapInfo.get("RE_MONEY"));//来款金额
			mapDate.put("FUND_DOCKET", "客户保证金转来款！");//来款摘要
			mapDate.put("FUND_ID", FUND_ID);
			mapDate.put("FUND_FUNDCODE", FUND_CODE);
			mapDate.put("FUND_NOTDECO_STATE", "4");
			mapDate.put("FUND_STATUS", "0");
			mapDate.put("FUND_ISCONGEAL", "0");
			mapDate.put("FUND_RED_STATUS", "0");
			mapDate.put("FUND_PRANT_ID", "0");
			mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
			mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
			mapDate.put("FUND_CLIENT_ID", map.get("CUST_ID"));//认款
			mapDate.put("FUND_CLIENT_NAME", map.get("CUST_NAME"));
			mapDate.put("FUND_PAY_CODE", mapInfo.get("PAYLIST_CODE"));//将要分解到那张支付表
			mapDate.put("FUND_TYPE", mapInfo.get("RE_PAYEE_TYPE"));
			Dao.insert("fi.fund.add", mapDate);
		}
		
		//写明细表
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", mapInfo.get("POOL_ID"));
		poolDeatil.put("FUND_ID", FUND_ID);
		poolDeatil.put("FUND_CODE", FUND_CODE);
		poolDeatil.put("TYPE", 3);
		poolDeatil.put("USERMONEY", mapInfo.get("RE_MONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
		
		//修改资金池主表
		mapInfo.put("SHIYONGMONEY", mapInfo.get("RE_MONEY"));
		int num=Dao.selectInt(xmlPath+"queryPayHeadStatusInfo", mapInfo.get("PAYLIST_CODE")+"");//3/6结束
		int nubmer=0;
		if(num>0){
			nubmer=Dao.update(xmlPath+"updatePoolInfoByPoolId1", mapInfo);
		}
		else{
			nubmer=Dao.update(xmlPath+"updatePoolInfoByPoolId", mapInfo);
		}
		Dao.insert(xmlPath+"callPool");
		return nubmer;
	}
	
	
	/**
	 * DB保证金池管理数据
	 * @param param
	 * @return
	 */
	public Page toMgRefundNew_DB(Map<String,Object> param ){
		param.put("ZJC_STATUS", "资金池状态");
		param.put("PAY_STATUS", "还款计划状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(xmlPath+"toMgCashDeposit_DB",param));
		page.addDate(array, Dao.selectInt(xmlPath+"toMgCashDepositNu_DB", param));
		return page;
	}
	
	//查询页面显示基本信息
	public Map queryDBByPoolIDBackMoney(Map<String, Object> map){
		return Dao.selectOne(xmlPath+"queryDBByPoolIDBackMoney",map);
	}
	
	
	public void isDBTKPass(String POOL_ID,String PAYLIST_CODE){
		Map mapDetailInfo=Dao.selectOne(xmlPath+"queryDetailInfoByPoolId", POOL_ID);
		//将可用金额修改及状态
		Dao.update(xmlPath+"updatePoolStatus", POOL_ID);
		Dao.update(xmlPath+"updatePoolHeadStatus2",mapDetailInfo);
		//写入子表信息
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", POOL_ID);
		poolDeatil.put("FUND_ID", mapDetailInfo.get("RE_ID"));
		poolDeatil.put("FUND_CODE", mapDetailInfo.get("RE_CODE"));
		poolDeatil.put("TYPE", 2);
		poolDeatil.put("USERMONEY", mapDetailInfo.get("RE_MONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
		Dao.insert(xmlPath+"callDBPool");
	}
	
	public void isDBTKNoPass(String POOL_ID,String PAYLIST_CODE){
		Map mapDetailInfo=Dao.selectOne(xmlPath+"queryDetailInfoByPoolId", POOL_ID);
		//修改保证金表状态
		//查询支付表是否结束，如结束状态为解冻，如没有结束为冻结
		int num=Dao.selectInt(xmlPath+"queryPayHeadStatusInfo", PAYLIST_CODE);//3/6结束
		if(num>0){
			Dao.update(xmlPath+"updatePoolStatus1", POOL_ID);
		}
		else{
			Dao.update(xmlPath+"updatePoolStatus0", POOL_ID);
		}
		Dao.update(xmlPath+"updatePoolHeadStatus3",mapDetailInfo);
		Dao.insert(xmlPath+"callDBPool");
	}
	
	
	public int changeDBMoneyCome(Map<String, Object> mapInfo){
		Map map=this.queryBaseInfoByPoolID(mapInfo);
		
		String FUND_ID=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE=Dao.selectOne("fi.fund.getFundCode");
		Map mapDate=new HashMap();
		//1.创建来款资金
		{
			mapDate.put("PAY_ID", map.get("PAY_ID"));
			mapDate.put("PAYLIST_CODE", mapInfo.get("PAYLIST_CODE"));
			mapDate.put("FUND_ACCEPT_DATE", map.get("PAY_DATE"));//来款时间
			mapDate.put("FUND_COMENAME", map.get("SUP_NAME"));//来款时间
			mapDate.put("FUND_RECEIVE_MONEY", mapInfo.get("RE_MONEY"));//来款金额
			mapDate.put("FUND_DOCKET", "供应商保证金转来款！");//来款摘要
			mapDate.put("FUND_ID", FUND_ID);
			mapDate.put("FUND_FUNDCODE", FUND_CODE);
			mapDate.put("FUND_NOTDECO_STATE", "4");
			mapDate.put("FUND_STATUS", "0");
			mapDate.put("FUND_ISCONGEAL", "0");
			mapDate.put("FUND_RED_STATUS", "0");
			mapDate.put("FUND_PRANT_ID", "0");
			mapDate.put("FUND_IMPORT_PERSON", Security.getUser().getName());
			mapDate.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
			mapDate.put("FUND_CLIENT_ID", map.get("CUST_ID"));//认款
			mapDate.put("FUND_CLIENT_NAME", map.get("CUST_NAME"));
			mapDate.put("FUND_PAY_CODE", mapInfo.get("PAYLIST_CODE"));//将要分解到那张支付表
			mapDate.put("FUND_TYPE", mapInfo.get("RE_PAYEE_TYPE"));
			Dao.insert("fi.fund.add", mapDate);
		}
		
		//写明细表
		Map poolDeatil=new HashMap();
		poolDeatil.put("POOL_ID", mapInfo.get("POOL_ID"));
		poolDeatil.put("FUND_ID", FUND_ID);
		poolDeatil.put("FUND_CODE", FUND_CODE);
		poolDeatil.put("TYPE", 3);
		poolDeatil.put("USERMONEY", mapInfo.get("RE_MONEY"));
		Dao.insert(xmlPath+"insertPoolDetail",poolDeatil);
		
		//修改资金池主表
		mapInfo.put("SHIYONGMONEY", mapInfo.get("RE_MONEY"));
		int num=Dao.selectInt(xmlPath+"queryPayHeadStatusInfo", mapInfo.get("PAYLIST_CODE")+"");//3/6结束
		int nubmer=0;
		if(num>0){
			nubmer=Dao.update(xmlPath+"updatePoolInfoByPoolId1", mapInfo);
		}
		else{
			nubmer=Dao.update(xmlPath+"updatePoolInfoByPoolId", mapInfo);
		}
		
		return nubmer;
	}
}
