package com.pqsoft.base.channel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.base.channel.service.AssureCreditService;
import com.pqsoft.base.channel.service.ChannelService;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.SecuUtil;
import com.pqsoft.util.StringUtils;

public class ChannelTaskAction {
	private Map<String, Object> param = null;
	private String namespace = "bpm.channel.";
	private ChannelService service = new ChannelService();

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	/**
	 * 
	 * @Title: startProcess 
	 * @Description: (流程发起action) 
	 * @param jbpmId 
	 * @return void 
	 * @throws 
	 */
	public void startProcess(String jbpmId){
		//获取流程变量
		this.getVeriable(jbpmId);
		
//		String SUP_ID = String.valueOf(param.get("SUP_ID"));
//		String CREDIT_ID = String.valueOf(param.get("CREDIT_ID"));
//		String APP_TYPE = String.valueOf(param.get("APP_TYPE"));
		
		//插入申请单
//		 * SUP_ID	供应商id
//		 * APP_TYPE 类型
//		 * USERNAME 申请人
		SecuUtil.putUserInfo(param);
		param.put("APPLY_STATUS", "1");//T_CREDIT_CHANNEL表申请状态 1 申请中  -1未申请
		param.put("APP_ID", service.insertCreditApp(param));

		JBPM.setExecutionVariable(jbpmId, param);
		param.put("JBPMID", jbpmId);
		service.updateJbpmId(param);
	}
	
	/**'
	 * 
	 * @Title: deleteProcess 
	 * @Description: TODO(删除流程) 
	 * @param jbpmId 
	 * @return void 
	 * @throws 
	 */
	public void deleteProcess(String jbpmId){
		//获取流程变量
		this.getVeriable(jbpmId);
		Map<String,Object> p = JBPM.getVeriable(jbpmId);
		service.deleteCreditApply(p);
	}
	/**
	 * 
	 * @Title: endProcess 
	 * @Description: TODO(流程审批通过) 
	 * @param jbpmId 
	 * @return void 
	 * @throws 
	 */
	public void confirmProcess(String jbpmId){
		ChannelService cService = new ChannelService();
		cService.doend(jbpmId);
	}
	/**
	 * 
	 * @Title: endProcess 
	 * @Description: TODO(流程结束) 
	 * @param jbpmId 
	 * @return void 
	 * @throws 
	 */
	public void endProcess(String jbpmId){
		ChannelService cService = new ChannelService();
		cService.doFinal(jbpmId);
	}
	
	//向核销表中插入应收额度记录
	public void addAccounts(String jbpmId){
	    Map<String,Object> param = JBPM.getVeriable(jbpmId);
	    //查询对应申请单信息
	    AssureCreditService assureService = new AssureCreditService();
	    Map<String,Object> appDan = assureService.getOneApplyDan(param);
	    Map<String,Object> newParam = new HashMap<String, Object>();
	    newParam.put("SUP_ID", StringUtils.nullToString(appDan.get("SUP_ID")));
	    newParam.put("SUP_NAME", StringUtils.nullToString(param.get("SUP_NAME"))) ;
	    newParam.put("FUND_TYPE", StringUtils.nullToString(appDan.get("ASSURE_TYPE"))) ;
	    String fund_name = "";
	    if(StringUtils.nullToString(appDan.get("ASSURE_TYPE")).equals("1")){
	    	fund_name="保证金";
	    }else if(StringUtils.nullToString(appDan.get("ASSURE_TYPE")).equals("2")){
	    	fund_name="固定资产";
	    }else if(StringUtils.nullToString(appDan.get("ASSURE_TYPE")).equals("3")){
	    	fund_name="有价证劵";
	    }
	    newParam.put("FUND_NAME", fund_name) ;
	    newParam.put("STATUS", "0") ;
	    newParam.put("CREATOR", Security.getUser().getName()) ;
	    newParam.put("PLAN_AMT", StringUtils.nullToString(appDan.get("ASSURE_MONEY"))) ;
	    newParam.put("APP_ID", StringUtils.nullToString(appDan.get("ID"))) ;
	    newParam.put("ASSURE_PAYMENT_MODE", StringUtils.nullToString(appDan.get("ASSURE_PAYMENT_MODE"))) ;
	    newParam.put("APP_ID", StringUtils.nullToString(appDan.get("ID"))) ;
	    newParam.put("CUR_AMT", StringUtils.nullToString(appDan.get("ASSURE_MONEY"))) ;
	    newParam.put("EXPAND_MULTIPLE", StringUtils.nullToString(appDan.get("ENLARGE_MULTIPLE"))) ;
	    newParam.put("ASSURE_AMOUNT", StringUtils.nullToString(appDan.get("ASSURE_AMOUNT_TOTAL")));
	    newParam.put("ASSURE_AMOUNT_HIST", StringUtils.nullToString(appDan.get("ASSURE_AMOUNT"))) ;
	    ChannelService channelService = new ChannelService();
	    channelService.addChannelFund(newParam);
	}
	
	//审批通过变更相关数据信息
	public void updateAssureAmount(String jbpmId){
		Map<String,Object> param = JBPM.getVeriable(jbpmId);
		AssureCreditService assureService = new AssureCreditService();
		//查询申请单详细
	    Map<String,Object> appDan = assureService.getOneApplyDan(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//更新申请单状态
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("STATUS", "1");
		newParam.put("APPLY_STATUS", "1");
		newParam.put("END_DATE", sdf.format(new Date()));
		newParam.put("APPLY_ID", param.get("APPLY_ID").toString());
        assureService.upAssureApplyDan(newParam);
		//更新保证额度
        if(appDan.containsKey("ASSURE_PAYMENT_MODE")&&(appDan.get("ASSURE_PAYMENT_MODE") == null || !"1".equals(appDan.get("ASSURE_PAYMENT_MODE").toString()))){
	        CreditAmountManagerService creditService = new CreditAmountManagerService();
	        newParam.put("ASSURE_AMOUNT", StringUtils.nullToString(appDan.get("APPLY_AMOUNT")));
	        newParam.put("CREDIT_ID", StringUtils.nullToString(appDan.get("CREDIT_ID")));
	        creditService.updateCreditAmount(newParam);
        }
	}
	
	//财务核销完
	public void crossApplyFinal(String jbpmId){
		Map<String,Object> param = JBPM.getVeriable(jbpmId);
		AssureCreditService assureService = new AssureCreditService();
		//查询申请单详细
	    Map<String,Object> appDan = assureService.getOneApplyDan(param);
		Map<String,Object> heXiaoMess = assureService.getWriteOffMess(param);
		if(heXiaoMess.containsKey("LAST_AMT") && heXiaoMess.get("LAST_AMT") !=null && Double.parseDouble(heXiaoMess.get("LAST_AMT").toString()) > 0){
			throw new ActionException("存在担保额度未核销完！");
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//更新申请单状态
			Map<String,Object> newParam = new HashMap<String, Object>();
			newParam.put("APPLY_STATUS", "1");
			newParam.put("STATUS", "1");
			newParam.put("END_DATE", sdf.format(new Date()));
			newParam.put("APPLY_ID", param.get("APPLY_ID").toString());
	        assureService.upAssureApplyDan(newParam);
			//更新保证额度
	        if(appDan.containsKey("ASSURE_PAYMENT_MODE")&&(appDan.get("ASSURE_PAYMENT_MODE") == null || !"1".equals(appDan.get("ASSURE_PAYMENT_MODE").toString()))){
		        CreditAmountManagerService creditService = new CreditAmountManagerService();
		        newParam.put("ASSURE_AMOUNT", StringUtils.nullToString(appDan.get("APPLY_AMOUNT")));
		        newParam.put("CREDIT_ID", StringUtils.nullToString(appDan.get("CREDIT_ID")));
		        creditService.updateCreditAmount(newParam);
	        }
		}
	}
	
	//审批不通过操作
	public void notCrossApply(String jbpmId){
		Map<String,Object> param = JBPM.getVeriable(jbpmId);
		AssureCreditService assureService = new AssureCreditService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//更新申请单状态
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("APPLY_STATUS", "2");
		newParam.put("STATUS", "0");
		newParam.put("END_DATE", sdf.format(new Date()));
		newParam.put("APPLY_ID", param.get("APPLY_ID").toString());
        assureService.upAssureApplyDan(newParam);
	}
	
	//删除保证额度申请单
	public void delApplyDan(String jbpmId){
		Map<String,Object> param = JBPM.getVeriable(jbpmId);
		param.put("APP_ID", StringUtils.nullToString(param.get("APPLY_ID"))); 
		service.deleteCreditApply(param);
	}
}
