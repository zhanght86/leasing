package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;

public class BuyBack {

	private Map<String, Object> param = null;

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	
	/**
	 * 退回操作
	 * doReturn
	 * @date 2013-11-25 上午01:09:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void doReturn(String jbpmId){
		this.getVeriable(jbpmId);
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		param.put("IS_END_STATUS", 0);
		Dao.update("BuyBack.buyBackNormal", param);
	}
	
	
	
//	/**
//	 * 财务评审通过
//	 * yesCW
//	 * @date 2013-11-25 上午01:09:14
//	 * @author 杨雪
//	 * @return 返回值类型
//	 * @throws Exception
//	 */
//	@SuppressWarnings("rawtypes")
//	public void yesCW(String jbpmId){
//		this.getVeriable(jbpmId);
//		//判断是否核销，未核销则抛出异常提醒核销
//		List list = Dao.selectList("BuyBack.query_binn_paid", param);
//		if(list!=null&&!list.isEmpty()){
//			throw new ActionException("未核销完成或者核销出错，请联系管理员！");
//		}
//		if(!jbpmId.contains("提前结清")){
//			param.put("USERCOE", Security.getUser().getCode());
//			param.put("USERNAME", Security.getUser().getName());
//			param.put("IS_END_STATUS", 1);
//			Dao.update("BuyBack.buyBackNormal", param);
//		}
//	}
	
	/**
	 * 财务评审通过
	 * yesCW
	 * @date 2013-11-25 上午01:09:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void yesCW(String PAY_ID,String PAYLIST_CODE){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		param.put("PAY_ID", PAY_ID);
		//判断是否核销，未核销则抛出异常提醒核销
		List list = Dao.selectList("BuyBack.query_binn_paid", param);
		if(list!=null&&!list.isEmpty()){
			throw new ActionException("未核销完成或者核销出错，请联系管理员！");
		}
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		param.put("IS_END_STATUS", 1);
		Dao.update("BuyBack.buyBackNormal", param);
	}
	/**
	 * 财务评审通过 提前结清
	 * yesCW
	 * @date 2013-11-25 上午01:09:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void yesCW_TQJQ(String PAY_ID,String PAYLIST_CODE){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		param.put("PAY_ID", PAY_ID);
		//判断是否核销，未核销则抛出异常提醒核销
		List list = Dao.selectList("BuyBack.query_binn_paid", param);
		if(list!=null&&!list.isEmpty()){
			throw new ActionException("未核销完成或者核销出错，请联系管理员！");
		}
			param.put("USERCOE", Security.getUser().getCode());
			param.put("USERNAME", Security.getUser().getName());
			param.put("IS_END_STATUS", 1);
			Dao.update("BuyBack.buyBackNormal", param);
	}
	/**
	 * 财务评审通过  分期回购
	 * yesCW_fq
	 * @date 2013-11-25 上午01:09:14
	 * @return 返回值类型  
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void yesCW_fq(String jbpmId){
		this.getVeriable(jbpmId);
		//判断是否核销，未核销则抛出异常提醒核销
		List list = Dao.selectList("BuyBack.query_binn_paid_fq", param);
		if(list!=null&&!list.isEmpty()){
			throw new ActionException("未核销完成或者核销出错，请联系管理员！");
		}
	}
	/**
	 * 债权评审通过
	 * yesCW
	 * @date 2013-11-25 上午01:09:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void yesZq(String PAY_ID,String PAYLIST_CODE){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		param.put("PAY_ID", PAY_ID);
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		param.put("IS_END_STATUS", 2);
		Dao.update("BuyBack.buyBackNormal", param);
		param.put("END_STATUS", param.get("END_STATUS"));
		//评审通过后修改支付表状态等
		PayTaskService se = new PayTaskService();
		if(PAYLIST_CODE!=null&&!"".equals(PAYLIST_CODE)){
			Map temp = new HashMap();
			temp.put("PAYLIST_CODE", PAYLIST_CODE);
			se.changeSucceed(temp);
		}
//		if(jbpmId.contains("分期")){
//			Dao.update("BuyBack.toUpdataProStatus", param);
//			//如果是分期回购支付表状态改为正常
//			Dao.update("BuyBack.toUpdatePlanStatus", param);
//		}
		
	}
	
	/**
	 * 财务部长退回
	 * noPassZQBZ
	 * @date 2013-12-2 下午06:10:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
//	public void noPassZQBZ(String jbpmId){
//		this.getVeriable(jbpmId);
//		Dao.delete("BuyBack.delBuyBack", param);
//	}
	
	/**
	 * 财务退回
	 * CWnoPass
	 * @date 2013-12-2 下午09:34:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
//	public void CWnoPass(String jbpmId){
//		this.getVeriable(jbpmId);
//		
//		//查看付款单
//		List<Map<String,Object>> fundList = Dao.selectList("rentDk.getFundList", jbpmId);
//		if(fundList!=null){//删除申请单
//			for(int i=0; i<fundList.size(); i++){
//				Map<String,Object> fund = fundList.get(i);
//				Dao.delete("rentDk.delFundAccount", fund);
//				Dao.delete("rentDk.delFundDetail", fund);
//				Dao.delete("rentDk.delFundHead", fund);
//			}
//		}
//		
//		//删除逾期数据
//		Map<String,Object> overDue = Dao.selectOne("rentDk.getOverBuyBack", jbpmId);
//		if(overDue!=null){
//			Dao.delete("rentDk.delOverDue", overDue);
//		}
//	}
	
	/**
	 * 财务退回
	 * CWnoPass
	 * @date 2013-12-2 下午09:34:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
//	public void CWnoPass1(String jbpmId){
//		this.getVeriable(jbpmId);
//		
//		//查看付款单
//		List<Map<String,Object>> fundList = Dao.selectList("rentDk.getFundList", jbpmId);
//		if(fundList!=null){//删除申请单
//			for(int i=0; i<fundList.size(); i++){
//				Map<String,Object> fund = fundList.get(i);
//				Dao.delete("rentDk.delFundAccount", fund);
//				Dao.delete("rentDk.delFundDetail", fund);
//				Dao.delete("rentDk.delFundHead", fund);
//			}
//		}
//		
//		//删除逾期数据
//		Map<String,Object> overDue = Dao.selectOne("rentDk.getOverBuyBack", jbpmId);
//		if(overDue!=null){
//			Dao.delete("rentDk.delOverDue", overDue);
//		}
//		
//		Dao.delete("BuyBack.delBuyBack", param);
//	}
}
