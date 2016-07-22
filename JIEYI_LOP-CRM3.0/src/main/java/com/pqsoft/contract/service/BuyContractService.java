package com.pqsoft.contract.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

public class BuyContractService {

	private final String BUYCONTRACT_PATH="buyContract." ;
	private final String PAYMENTDETAIL_PATH = "payment_crm." ;
	private final String PAYMENTTERM_PATH = "paymentTerm." ;
	
	//获取买卖合同下一个序列值
	public Long getBuyContractNextval(){
		return (Long)Dao.selectOne(BUYCONTRACT_PATH + "getBuyContractNextval") ;
	}
	
	//获取下一个序列值
	public Long getPaymentDetailNextval(){
		return (Long)Dao.selectOne(PAYMENTDETAIL_PATH + "getPaymentDetailNextval") ;
	}
	
	//获取下一个序列值
	public Long getPaymentTermNextval(){
		return (Long)Dao.selectOne(PAYMENTTERM_PATH + "getPaymentTermNextval") ;
	}
	
	//通过Id查询
	public Map<String,Object> getBuyContractById(Long id){
		Map<String,Object> refer = new HashMap<String,Object>() ;
		refer.put("ID", id) ;
		return Dao.selectOne(BUYCONTRACT_PATH+"getBuyContractById", refer) ;
	}
	
	//通过Id查询
	public Map<String,Object> getBuyContractAndFilRentPlanHeadById(Long id){
		Map<String,Object> refer = new HashMap<String,Object>() ;
		refer.put("ID", id) ;
		return Dao.selectOne(BUYCONTRACT_PATH+"getBuyContractAndFilRentPlanHeadById", refer) ;
	}
	
	//通过合同ID获取 paymentdetail 列表
	public List<Map<String,Object>> getPaymentDetailsByBuyContractId(Long id){
		return Dao.selectList(PAYMENTDETAIL_PATH + "getPaymentDetailsByBuyContractId" , id) ;
	}
	
	//通过paymentDetailId获取paymentTerm列表
	public List<Map<String,Object>> getPaymentTermsByPaymentDetailId(Map<String,Object> param){
		return Dao.selectList(PAYMENTTERM_PATH + "getPaymentTermsByPaymentDetailId" , param) ;
	}
	
	public List<Long> getTermIdsByPaymentDetailId(Map<String,Object> param){
		return Dao.selectList(PAYMENTTERM_PATH + "getTermIdsByPaymentDetailId" , param) ;
	}

	public Page queryPage(Map<String, Object> m) {
		Page page = new Page(m);
		List list = Dao.selectList(BUYCONTRACT_PATH + "getAllBuyContract", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt(BUYCONTRACT_PATH + "getAllBuyContract_count", m));
		return page;
	}
	
	public Map<String, Object> doShowRentHeadByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(PAY_ID)) return map;
		map.put("ID", PAY_ID);
		map.put("_TYPE", "租赁周期");
		return Dao.selectOne(BUYCONTRACT_PATH+ "queryRentHead", map);
	}
	
	
	/**
	 * 新增买卖合同
	* @param @param param
	* @param @return
	* @return Long 插入成功,返回主键ID
	* @throws
	* @date 2014-5-12 下午08:11:58 
	* @author WuYanFei
	 */
	public Long insertBuyContract(Map<String,Object> param){
		Long id = getBuyContractNextval();
		param.put("ID", id) ;
		Dao.insert(BUYCONTRACT_PATH + "insertBuyContract" ,param) ;
		return id ;
	}
	
	/**
	 * 
	* @param @param param
	* @param @return 插入成功,返回主键ID
	* @return Long
	* @throws
	* @date 2014-5-12 下午08:20:43 
	* @author WuYanFei
	 */
	public Long insertPaymentDetail(Map<String,Object> param){
		Long id = getPaymentDetailNextval() ;
		param.put("ID", id) ;
		Dao.insert(PAYMENTDETAIL_PATH + "createPaymentDate",param) ;
		return id ;
	}
	
	
	/**
	 * 
	* @param @param param
	* @param @return
	* @return Long 插入成功,返回主键ID
	* @throws
	* @date 2014-5-13 上午09:30:45 
	* @author WuYanFei
	 */
	public Long insertPaymentTerm(Map<String,Object> param){
		Long id = getPaymentTermNextval() ;
		param.put("ID", id) ;
		Dao.insert(PAYMENTTERM_PATH + "insertPaymentTerm",param) ;
		return id ;
	}
	
	
	/**
	 * 新增买卖合同及相关级联表操作
	* @param @param obj
	* @param @return
	* @return int 成功返回1 ,失败返回-1
	* @throws
	* @date 2014-5-13 上午09:34:57 
	* @author WuYanFei
	 */
	public int insertBuyContractAndPayment(JSONObject obj){
		//paymentDetail List
		List<Map<String, Object>> paymentDetailList = obj.getJSONArray("PAYMENT_LIST");
		//约束规则List
		Map<String,List<Map<String, Object>>> paymentTermMapList = obj.getJSONObject("constraintRecord");
		//买卖合同表单信息Map
		Map<String,Object> allInfoMap = obj.getJSONObject("allInfoMap");
		//新增买卖合同
		Long  buyContractId = insertBuyContract(allInfoMap) ;
	
		Object payListCode = null == allInfoMap.get("PAYLIST_CODE") ? null : allInfoMap.get("PAYLIST_CODE") ;
	
		for(int i=0;i<paymentDetailList.size();i++){
			//新增FI_FUND_PAYMENT_DETAIL 表 Map 
			Map<String,Object> paymentDetail = paymentDetailList.get(i) ;
			paymentDetail.put("BUY_CONTRACT_ID", buyContractId) ;
			paymentDetail.put("PAY_NAME","设备款") ;
			paymentDetail.put("PAY_TYPE",1) ;
			paymentDetail.put("STATUS",1) ;
			paymentDetail.put("PAYEE_NAME", allInfoMap.get("SELLER_UNIT_NAME")) ;
			paymentDetail.put("PAY_BANK_ACCOUNT", allInfoMap.get("SELLER_ACCOUNT")) ;
			paymentDetail.put("PAY_BANK_ADDRESS", allInfoMap.get("SELLER_PAY_BANK_ADDRESS")) ;
			paymentDetail.putAll(allInfoMap) ;
			Long paymentDetailId = insertPaymentDetail(paymentDetail) ;			
			//新增FI_FUND_PAYMENT_DETAIL 表 Map 
			List<Map<String,Object>> paymentTermList = paymentTermMapList.get(i+"");
			for(Map<String,Object> temp:paymentTermList){
				if(temp!=null && temp.size()>0){
					temp.put("PAYLIST_CODE",payListCode) ;
					temp.put("PAYMENT_ID",paymentDetailId) ;
					Long paymentTermId = insertPaymentTerm(temp);
				}
			}
		}			
		//如果是销售型售后回租-添加一笔来款
		System.out.println("============"+obj.get("LEASE_MODEL"));
		Map<String,Object> map = new HashMap<String,Object>();
		if("back_leasing".equals(obj.get("LEASE_MODEL"))){
				map.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
				map.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				map.put("FUND_NOTDECO_STATE", "0");
				map.put("FUND_STATUS", "0");
				map.put("FUND_ISCONGEAL", "0");
				map.put("FUND_RED_STATUS", "0");
				map.put("FUND_PRANT_ID", "0");
				map.put("FUND_IMPORT_PERSON", Security.getUser().getName());
				map.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
				
				map.put("FUND_COMENAME",allInfoMap.get("LESSOR_NAME"));//来款人
				map.put("FUND_COMECODE",allInfoMap.get("LESSOR_NAME"));//来款户名
				map.put("FUND_ACCEPT_DATE",allInfoMap.get("SIGN_DATE"));//来款时间
				//map.put("FUND_ACCEPT_NAME",);//收款人
				//map.put("FUND_ACCEPT_CODE",);//收款户名
				map.put("FUND_RECEIVE_MONEY",Dao.selectOne("buyContract.getLEASE_TOPRIC",payListCode));//收款金额
				map.put("FUND_DOCKET","销售型售后回租设备款当做来款");//摘要
				Dao.insert("fi.fund.add", map);
			}
		return 1 ;	
	}
	
	/**
	 * 更新买卖合同
	* @param @param param
	* @param @return
	* @return Long
	* @throws
	* @date 2014-5-13 下午04:36:55 
	* @author WuYanFei
	 */
	public Long updateBuyContract(Map<String,Object> param){
		Long id = Long.valueOf(param.get("ID").toString()) ;
		Dao.update(BUYCONTRACT_PATH + "updateBuyContract", param) ;
		return id ;
	}
	
	public Long updatePaymentDetail(Map<String,Object> param){
		Long id = Long.valueOf(param.get("ID").toString()) ;
		Dao.update(PAYMENTDETAIL_PATH + "updatePaymentDetail", param) ;
		return id ;
	}
	
	public Long updatePaymentTerm(Map<String,Object> param){
		Long id = Long.valueOf(param.get("ID").toString())  ;
		Dao.update(PAYMENTTERM_PATH + "updatePaymentTerm", param) ;
		return id ;
	}
	
	public int updateBuyContractAndPayment(JSONObject obj){
		//买卖合同表单信息Map
		Map<String,Object> allInfoMap = obj.getJSONObject("allInfoMap");
		Map<String,Object> changeInfoMap = obj.getJSONObject("changeInfoMap");
		Object payListCode = null == allInfoMap.get("PAYLIST_CODE") ? null : allInfoMap.get("PAYLIST_CODE") ;
		List<Map<String, Object>> paymentDetailList = obj.getJSONArray("PAYMENT_LIST");
		//约束规则List
		Map<String,List<Map<String, Object>>> paymentTermMapList = obj.getJSONObject("constraintRecord");
		//买卖合同表单信息Map;
		Object buyContractId  = allInfoMap.get("ID") ;
		 
		if(changeInfoMap!=null &&changeInfoMap.size()>0){	
			changeInfoMap.put("ID", buyContractId) ;
			buyContractId = updateBuyContract(changeInfoMap) ;
		}
		//buyContractId = updateBuyContract(allInfoMap) ;
		int i = 0 ;
		for(Map<String,Object> map:paymentDetailList){
			List<Map<String, Object>> paymentTermList = paymentTermMapList.get(i+"");
			allInfoMap.remove("ID") ;
			map.putAll(allInfoMap) ;
			map.put("PAYEE_NAME", allInfoMap.get("SELLER_UNIT_NAME")) ;
			map.put("PAY_BANK_ACCOUNT", allInfoMap.get("SELLER_ACCOUNT")) ;
			map.put("PAY_BANK_ADDRESS", allInfoMap.get("SELLER_PAY_BANK_ADDRESS")) ;
			if(map.get("ID")==null){
				map.put("BUY_CONTRACT_ID", buyContractId) ;
				map.put("PAY_NAME","设备款") ;
				map.put("PAY_TYPE",1) ;
				map.put("STATUS",1) ;
				Long paymentDetailId=insertPaymentDetail(map) ;
				for(Map<String,Object> temp:paymentTermList){
					if(temp!=null && temp.size()>0){
						temp.put("PAYLIST_CODE",payListCode) ;
						temp.put("PAYMENT_ID",paymentDetailId) ;
						Long paymentTermId = insertPaymentTerm(temp);
					}
				}
			}else{
				updatePaymentDetail(map) ;
				String paymentDetailId = map.get("ID").toString() ;
				if(paymentTermList!=null && paymentTermList.size()>0){					
					String paymentTermId = map.get("ID").toString() ;
					for(Map<String,Object> termMap:paymentTermList){
						if((termMap.get("ID")==null || StringUtils.isBlank(termMap.get("ID").toString())) && termMap.get("TERM_ID")!=null){
							termMap.put("PAYLIST_CODE",payListCode) ;
							termMap.put("PAYMENT_ID",paymentDetailId) ;
							insertPaymentTerm(termMap);
						}else{
							if((Boolean)termMap.get("SAVE_OR_DEL_FLAG")){
								Map<String,Object> refer = new HashMap<String,Object>() ;
								refer.put("ID", termMap.get("ID")) ;
								deletePaymentTerm(refer) ;
							}	
						}
					}
				}else{
					Map<String,Object> paymentIdMap = new HashMap<String,Object>() ;
					paymentIdMap.put("TERM_ID", paymentDetailId) ;
					deletePaymentTermByPaymentId(paymentIdMap) ;
				}
				
			}
			i++ ;
		}
		return 1;
	}
	
	public int deletePaymentTermByPaymentId(Map<String,Object> param){
		Dao.delete(PAYMENTTERM_PATH + "deletePaymentTermByPaymentId", param) ;
		return 1;
	}
	
	public int deletePaymentTerm(Map<String,Object> param){
		Dao.delete(PAYMENTTERM_PATH + "deletePaymentTerm", param) ;
		return 1;
	}
	
	public List<Map<String, Object>> queryPaymentByPaylistCode(Map<String,Object> m)
	{
		List<Map<String, Object>> list = Dao.selectList("PayTask.queryPaymentByPaylistCode", m);
		return list;
	}
	
	public List<Map<String, Object>> queryPaymentMouldDetail(Map<String,Object> m)
	{	if(m==null ||m.get("PLATFORM_TYPE")==null)
			return null;
	
		List<Map<String, Object>> list = Dao.selectList("PayTask.queryPaymentMouldDetailByType", m);
		
		return list;
	}
	public String queryProjecdModel(String project_id){
		return Dao.selectOne("buyContract.queryProjecdModel", project_id);
	}
	/**
	 *@requestFunction
	 * 校验放款条件
	 * @return
	 * 返回的Map中，有key为flag的一项。该项值为true，表示符合放款条件。
	 * 为false表示不符合放款条件，Map中会放入msg，为错误提示
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月22日 下午5:50:55
	 */
	public Map validFKTJ(Map<String,Object> param) {
		Map result = new HashMap();
		Map fktj = Dao.selectOne("buyContract.getFktj", param);
		String sql = fktj.get("SQL").toString();
		String[] filelds = fktj.get("SQL_FIELD").toString().split(",");
		String msg = fktj.get("NOTE").toString();
		for(int i=0; i<filelds.length; i++) {
			sql = sql.replaceFirst("\\?", param.get(filelds[i]).toString());
		}
		List<Map> res = Dao.execSelectSql(sql);
		if(res.size()>0 && ((BigDecimal)res.get(0).get("RES")).intValue()==0) {
			result.put("flag", false);
			result.put("msg", msg);
			return result;
		}
		result.put("flag", true);
		return result;
	}
	
}
