package com.pqsoft.margin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.margin.service.MarginService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;

/**
 * 经销商入网保证金
 * @author 郑商城
 * @date 2015-08-27
 */
public class MarginAction extends Action{

	MarginService marginService = new MarginService();
	
	@Override
	public Reply execute() {
		return null;
	}

	/**
	 * to保证金列表页
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "进入列表显示" })
	public Reply toMgMargin(){
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("showMargin/showMargin.vm", context)); 
	}
	
	/**
	 * 保证金列表页
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "列表显示" })
	public Reply showMargin(){
		Map<String,Object> map = _getParameters() ;
		BaseUtil.getDataAllAuth(map);
		Page page = marginService.showMargin(map) ;
		return new ReplyAjaxPage(page);
	}
	
	/**
	 * 入网保证金明细
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "明细列表显示" })
	public Reply toShowMarginDetail(){
		Map<String,Object> param = _getParameters() ;
		JSONObject jsonObj = JSONObject.fromObject((param.get("paramJson")));
		VelocityContext context = new VelocityContext();
		context.put("marginDetailList", marginService.showMarginDetail(jsonObj));
		context.put("param", jsonObj);
		return new ReplyHtml(VM.html("showMargin/showMarginDetail.vm", context)); 
	}
	
	/**
	 * 逾期保证金收账
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "逾期收账" })
	public Reply updateMarginYQ(){
		Map<String, Object> param = _getParameters();
		
		param.put("ID", param.get("yqId"));
		
		if(param.get("indeedlatemargin") != null && param.get("indeedlatemargin") != ""){
			param.put("INDEEDLATEMARGIN", Double.parseDouble(param.get("indeedlatemargin").toString())+
					Double.parseDouble(param.get("ssyqbzj").toString()));
		}else {
			param.put("INDEEDLATEMARGIN", param.get("ssyqbzj"));
		}
		
		Boolean flag = false;
		int res = marginService.updateMargin(param);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUP_ID", param.get("sup_id"));
		map.put("T_REFOUND_MARGIN_ID", param.get("ID"));
		map.put("RECEMARGIN", param.get("payablelatemargin"));
		map.put("TYPE", "2");
		map.put("TIME", param.get("TIME"));
		map.put("PROJECT_NO", param.get("PROJECT_NO"));
		map.put("PAYMENT_NO", param.get("PAYMENT_NO"));
		map.put("PROMARGIN", param.get("ssyqbzj"));
		marginService.insertMarginDetail(map);
		if(res >0){
			 flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	/**
	 * 初始保证金收账
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "初始收账" })
	public Reply updateMargin(){
		Map<String, Object> param = _getParameters();
		param.put("ID", param.get("csId"));
		
		if(param.get("indeedinitialmargin") != null && param.get("indeedinitialmargin") != ""){
			param.put("INDEEDINITIALMARGIN", Double.parseDouble(param.get("indeedinitialmargin").toString())+
					Double.parseDouble(param.get("sscsbzj").toString()));
		}else{
			param.put("INDEEDINITIALMARGIN", param.get("sscsbzj"));
		}
		
		Boolean flag = false;
		int res = marginService.updateMargin(param);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUP_ID", param.get("sup_id"));
		map.put("T_REFOUND_MARGIN_ID", param.get("ID"));
		map.put("RECEMARGIN", param.get("payableinitial"));
		map.put("TYPE", "1");
		map.put("TIME", param.get("TIME"));
		map.put("PROJECT_NO", param.get("PROJECT_NO"));
		map.put("PROMARGIN", param.get("sscsbzj"));
		marginService.insertMarginDetail(map);
		
		if(res >0){
			 flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	/**
	 * 退款
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "退款" })
	public Reply updateMarginTK(){
		Map<String, Object> param = _getParameters();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("T_REFOUND_MARGIN_ID", param.get("ID"));
		
		map.put("SUP_ID", param.get("sup_id_tk"));
		map.put("PROJECT_NO", param.get("PROJECT_NO"));
		map.put("PAYMENT_NO", param.get("PAYMENT_NO"));
		map.put("TIME", param.get("TIME"));
		map.put("PROMARGIN", "-" + param.get("jine"));
		if("1".equals(param.get("type"))){
			map.put("TYPE", "3");
			
		}else if("2".equals(param.get("type"))){
			map.put("TYPE", "4");
		}
		int res = marginService.insertMarginDetail(map);
		Boolean flag = false;
		if(res >0){
			 flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	/**
	 * 初始保证金数据插入
	 * @param map
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "插入" })
	public Reply insertMargin(Map<String, Object> map){
		List<Map<String, Object>> listMax = marginService.getMaxMargin();
		if(Double.parseDouble(map.get("RWBZJJE").toString()) 
				> Double.parseDouble(listMax.get(0).get("CODE").toString()) ){
			return new ReplyAjax("保证金金额已超过经销商入网保证金最高金额!");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("SUP_ID", map.get("CUST_ID"));
		
//		if(map.get("PROJECT_NO") != null && map.get("PROJECT_NO") != ""){
//			param.put("PROJECT_NO", map.get("PROJECT_NO"));
//		}
//		if(map.get("PAYMENT_NO") != null && map.get("PAYMENT_NO") != ""){
//			param.put("PAYMENT_NO", map.get("PAYMENT_NO"));
//		}
		if(map.get("PAYMENTTYPE") != null && map.get("PAYMENTTYPE") != ""){
			param.put("PAYMENTTYPE", map.get("PAYMENTTYPE"));
		}
		
		List<Map<String, Object>> list = marginService.showOneMargin(param);
		
		int res = 0;
		if(list != null && list.size() > 0){
			if((Double.parseDouble(list.get(0).get("PAYABLEINITIALMARGIN").toString()) 
					+ Double.parseDouble(map.get("RWBZJJE").toString())) 
					> Double.parseDouble(listMax.get(0).get("CODE").toString()) ){
				return new ReplyAjax("保证金金额已超过经销商入网保证金最高金额!");
			}
			param.put("ID", list.get(0).get("ID"));
			param.put("PAYABLEINITIALMARGIN", Double.parseDouble(map.get("RWBZJJE").toString())+
					Double.parseDouble(list.get(0).get("PAYABLEINITIALMARGIN").toString()));
			marginService.updateMargin(param);
//			marginService.updateMarginDetail(param);
		}else{
			param.put("PAYABLEINITIALMARGIN", map.get("RWBZJJE"));
			marginService.insertMargin(param);
			
//			List<Map<String, Object>> lis = marginService.showOneMargin(param);
//			
//			Map<String, Object> paramDetail = new HashMap<String, Object>();
//			paramDetail.put("T_REFOUND_MARGIN_ID", lis.get(0).get("ID"));
//			paramDetail.put("SUP_ID", map.get("CUST_ID"));
//			paramDetail.put("RECEMARGIN", map.get("RWBZJJE"));
//			paramDetail.put("TYPE", "1");
//			if(map.get("PROJECT_NO") != null && map.get("PROJECT_NO") != ""){
//				paramDetail.put("PROJECT_NO", param.get("PROJECT_NO"));
//			}
//			res = marginService.insertMarginDetail(paramDetail);
		}
		Boolean flag = false;
		if(res >0){
			 flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	/**
	 * 退款金额最大值
	 * @param map
	 * @return
	 */
	@aDev(email = "zhengsc@pqsoft.cn", name = "郑商城", code = "1000003")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "经销商入网保证金", "退款金额" })
	public Reply marginDetailPromargin(){
		Map<String, Object> param = _getParameters();
		if(param.get("type") != null && param.get("type") != ""){
			if("1".equals(param.get("type"))){
				param.put("TYPE", "3");
			}else if("2".equals(param.get("type"))){
				param.put("TYPE", "4");
			}
		}
		List<Map<String, Object>> list = marginService.showMarginDetail(param);
		if(list != null && list.size() > 0){
			String res = list.get(0).get("PROMARGIN").toString();
			return new ReplyAjax(res);
		}else{
			String res = "";
			return new ReplyAjax(res);
		}
	}
}
