package com.pqsoft.quartzjobs.jobs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.action.SysAction;
import com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate;
import com.pqsoft.base.task.service.TaskClaimService;
import com.pqsoft.blackcust.service.BlackCustManageService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.insure.util.DateUtil;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;

public class ReconsiderTaskTimeoutJob extends AbstractJob {
	private  final Logger logger= Logger.getLogger(super.getClass());
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		 logger.info("拒绝待复议超时黑名单定时任务========ReconsiderTaskTimeoutJob开始执行");
		//拒绝待复议超时
		Date sqfyTimeOut=null;
		
		TaskClaimService taskClaimService= new TaskClaimService();
		//获取满足条件拒绝待复议超时节点
		 List<Map<String, Object>> tasks =taskClaimService.getReconsiderTimeOut();
			
		if(null!=tasks&& tasks.size()>0){
			for(int i =0;i<tasks.size();i++){
					Date distributionTime=(Date) tasks.get(i).get("DISTRIBUTION_DATE");
					if(null!=distributionTime){
						 //复议的时间为7天//jzzl-83 时间修改为10天
						 if(distributionTime.compareTo(DateUtil.addDate(new Date(),-10))<0){
							  doReTimeOutTask(tasks.get(i).get("DATA_ID").toString(),tasks.get(i).get("PROJECT_ID").toString(),distributionTime);
						  }
					}
			}
			 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行结束");
		}else{
			 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob未查询出符合条件任务");
		}
	}
	
	private  void  doReTimeOutTask(String taskId,String projectId,Date distributionTime) {
		String tranName_="不通过";
		//TODO 下一节点name
		String nextname_="评审不通过1";
		//进入下一环节
		try {
			TaskService service = new TaskService();
			MemoService memoService = new MemoService();
			TaskClaimService taskClaimService= new TaskClaimService();
			
			Map<String, Object> config = service.getConfig(taskId);
			if (config == null) throw new ActionException("该节点未做任何配置，请联系管理员");
			String jbpmId = service.getJbpmIdBytaskId(taskId);
			jbpmId = service.getShortName(jbpmId);
			{
				// TODO taskId 更新当前节点状态0未分配1已分配2已认领5已完成
				new TaskClaimService().updClaimProcedure(taskId, "1");
			}
			// ------------ TODO 进入下一节点
			if (nextname_ != "") nextname_ = "提交至" + nextname_;
			// ------------ 记录备注 操作类型
			Map<String, Object> task = service.getTask(taskId);
			String url = service.getContentUrl(jbpmId, task.get("NAME_") + "", (String) config.get("FORM"));
			String memo_text = "拒绝待复议处理超时,流程结束";
			String wmemo_text = "拒绝待复议处理超时,流程结束";
			//查询是否有该任务，如果数据库表中没有该任务，则添加；已有任务则更新
			Map<String,Object> taskSubmit=memoService.getMemosByTaskId(taskId);
			
			//如果已有任务，更新
			if(taskSubmit!=null && taskSubmit.size()>0){
				memoService.updateMemoSubmit(taskId, "系统", "", memo_text, nextname_, url, "BROWSER", wmemo_text,tranName_);
			}else{
				memoService.addMemo(taskId, (String) task.get("EXECUTION_ID_"), (String) task.get("NAME_"), url,"系统","",memo_text, wmemo_text,tranName_);
			}
			
			//传阅？
			service.addCy(taskId, (String) config.get("CY"));
			//SkyEye.getRequest().setAttribute("_TEMP_OP", (String) param.get("nextOpCode"));
			if (tranName_ == null || "".equals(tranName_)) {
				JBPM.completeTask(taskId);
			} else {
				JBPM.completeTask(taskId, tranName_);
			}
			// 发邮件
			String taskName="";
			List<Map<String, Object>> list = service.getTaskByJbpmId(jbpmId);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				map.put("LAST_NODE", task.get("NAME_"));
				Dao.update("bpm.task.updLastNode",map);
				Map<String, Object> claim = new HashMap<String, Object>();
				// TYPE:流程1数据2; TASK_NAME:任务名称;DATA_ID:数据id;DATA_NAME:描述;
				claim.put("TYPE", "1");
				taskName=map.get("ACTIVITY_NAME_").toString();
				claim.put("TASK_NAME", map.get("ACTIVITY_NAME_"));
				claim.put("DATA_ID", map.get("DBID_"));
				claim.put("USER_CODE", map.get("CODE"));
				claim.put("DATA_NAME", jbpmId);
				claim.put("PROJECT_ID", map.get("PROJECT_ID"));
				claim.put("URL", "bpm/Task!toShow.action?taskId=" + map.get("DBID_"));
				Map<String,Object> Create=Dao.selectOne("Task.Claim.getCreateMan",jbpmId);
				claim.put("CREATEMAN_NAME", Create.get("OP_NAME"));
				claim.put("CREATEMAN_CODE", Create.get("OP_CODE"));
				claim.put("TASK_CREATETIME", Create.get("START_DATE"));
				try {
					new TaskClaimService().addClaim(claim);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<Map<String, Object>> list1 = service.getTaskByJbpmId(jbpmId);
			for(int j=0;j<list1.size();j++){
				try {
					Map<String, Object> map = list1.get(j);
					map.put("JBPM_ID", jbpmId);
					if (map != null && map.get("CODE") != null && !map.get("CODE").equals("")) {
						SysAction.setMsg(map.get("CODE")+"", "通知：收到新任务--" + jbpmId);
						String TYPE = "";
						// 邮件通知
						if ("yyyy".equals(map.get("EMAIL"))) {
							TYPE = "EMAIL"; 
							new RunInterfaceTemplate().sendJbpmMessage("task节点", map, TYPE);
						}
						if ("yyyy".equals(map.get("SMS"))) {
							// 通知短信
							TYPE = "SMS"; 
							new RunInterfaceTemplate().sendJbpmMessage("task节点", map, TYPE);
						}
					}
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
			String assignee = service.getAssigneeJobName(jbpmId);
			if (assignee == null) logger.info("提交成功！该任务已进入下一节点："+taskName+"");
			logger.info( "提交成功！该任务已进入下一节点：" + assignee+"");
			
			
			// ------------ 执行后续操作
			// ------------ TODO  projectId 对于拒绝拉黑的，将改客户设置成黑名单 
			
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("PROJECT_ID", projectId);
			//获取信审意见信息
			List<Map<String, Object>> letterOpts=taskClaimService.getLetterOptionByProjectId(param);
			if(null!=letterOpts&&letterOpts.size()>0){
				BlackCustManageService blackService=new BlackCustManageService();
				Map<String, Object> maps = letterOpts.get(0);
				 List<Map<String, Object>> spjlList = (List<Map<String, Object>>)DataDictionaryMemcached.getList("审批结论");
				 int jj=0;
				 int jjlh=0;
				 int khfq=0;
				 if(spjlList!=null&&spjlList.size()>0){
					 for (int i=0;i<spjlList.size();i++){
						 if(spjlList.get(i).get("SHORTNAME").equals("JJ")){
							 jj=Integer.parseInt(spjlList.get(i).get("REMARK").toString().trim());
						 }else  if(spjlList.get(i).get("SHORTNAME").equals("JJLH")){
							 jjlh=Integer.parseInt(spjlList.get(i).get("REMARK").toString().trim());
						 }else   if(spjlList.get(i).get("SHORTNAME").equals("KHFQ")){
							 khfq=Integer.parseInt(spjlList.get(i).get("REMARK").toString().trim());
						 }
					 }
				 }
				 
				//获取客户信息
					List<Map<String, Object>> custInfos=taskClaimService.getCustInfoByProjectId(param);
					if(null!=custInfos&&custInfos.size()>0){
						Map<String, Object> blackMap= new  HashMap<String, Object>();
						blackMap.put("CUST_NAME", custInfos.get(0).get("NAME"));
						//证件号码
						blackMap.put("ID_CARD_NO", custInfos.get(0).get("ID_CARD_NO"));
						//黑名单状态0否1是
						blackMap.put("STATUS", "1");
						//进入时间
						blackMap.put("SETIN_DATE", DateUtil.format(new Date(),"yyyy-MM-dd"));
						//客户id(对应FIL_CUST_CLIENT的ID)
						blackMap.put("CUST_CLIENT_ID", custInfos.get(0).get("ID"));
						
						//对于拒绝拉黑的，将改客户设置成黑名单，黑名单有效期时长到9999-12-31
						 if(null==maps.get("ENDTRIAL_SPJL")){
							 //无终审结论，判断初审结论
							 if(maps.get("FIRSTTRIAL_SPJL").equals("4")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,初审结论为拒绝拉黑");
								 blackMap.put("GETOUT_DATE", DateUtil.format(DateUtil.addDate(distributionTime, jjlh), "yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }else if (maps.get("FIRSTTRIAL_SPJL").equals("3")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,初审结论为拒绝");
								 blackMap.put("GETOUT_DATE",DateUtil.format(DateUtil.addDate(distributionTime, jj),"yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }else if(maps.get("FIRSTTRIAL_SPJL").equals("5")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,初审结论为客户放弃");
								 blackMap.put("GETOUT_DATE",DateUtil.format(DateUtil.addDate(distributionTime, khfq),"yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }
							 
						 }else{
							 //有终审结论
							 //无终审结论，判断初审结论
							 if(maps.get("ENDTRIAL_SPJL").equals("4")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,终审结论为拒绝拉黑");
								 blackMap.put("GETOUT_DATE", DateUtil.format(DateUtil.addDate(distributionTime, jjlh), "yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }else if (maps.get("ENDTRIAL_SPJL").equals("3")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,终审结论为拒绝");
								 blackMap.put("GETOUT_DATE",DateUtil.format(DateUtil.addDate(distributionTime, jj),"yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }else if(maps.get("ENDTRIAL_SPJL").equals("5")){
								//原因内容
								 blackMap.put("CASE_RECORDS", "拒绝待复议超时,终审结论为客户放弃");
								 blackMap.put("GETOUT_DATE",DateUtil.format(DateUtil.addDate(distributionTime, khfq),"yyyy-MM-dd"));
								 blackService.addBlackCust(blackMap);
								 logger.info("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob执行,添加黑名单成功");
							 }
						 }
						
					}
			}
			 logger.info("ReconsiderTaskTimeoutJob执行,流程状态追加 '-终拒' ——开始，项目ID："+projectId );
			//修改流程状态 add by 邢素敏 2016年3月29日15:45:57
				Map<String, Object> statusParam = new HashMap<String, Object>();
				statusParam.put("ID", projectId);
				statusParam.put("STATUS_NEW", "终拒");
			 Dao.update("bpm.task.updateProStatusNew", statusParam);
			 logger.info("ReconsiderTaskTimeoutJob执行,流程状态修追加 '-终拒' ——结束");
			Dao.commit();
			Dao.close();
			
		} catch (ActionException e) {
			Dao.rollback();
			Dao.close();
			logger.error("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob异常。信息："+e);
		} catch (Exception e) {
			Dao.rollback();
			Dao.close();
			logger.error("拒绝待复议节点超时定时任务========ReconsiderTaskTimeoutJob任务处理失败："+e);
		}
	}
}