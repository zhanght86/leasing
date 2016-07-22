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
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.insure.util.DateUtil;
import com.pqsoft.letterOpinion.service.LetterOpinionService;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;

public class AutoDealTaskTimeoutJob extends AbstractJob {
	private  final Logger logger= Logger.getLogger(super.getClass());
	private TaskClaimService taskClaimService= new TaskClaimService();
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		 logger.info("节点超时定时任务========AutoDealTaskTimeoutJob开始执行");
		//补充资料超时
		Date bczlTimeOut=null;
		//家访报告超时
		Date jfbgTimeOut=null;
		//补征信超时
		Date bzxTimeOut=null;
		//获取满足条件超时节点
		 List<Map<String, Object>> tasks =taskClaimService.getTimeOutTask();
		 //获取超时常量配置
		 List<Map<String, Object>> timeOutLists = (List<Map<String, Object>>)DataDictionaryMemcached.getList("定时客户放弃超时时间");
		 if(null!=timeOutLists&&timeOutLists.size()>0){
			  for(int i=0;i<timeOutLists.size();i++){
				   if(timeOutLists.get(i).get("SHORTNAME").equals("BCZL")){
					   bczlTimeOut=DateUtil.addDate(new Date(), -Integer.parseInt(timeOutLists.get(i).get("REMARK").toString().trim()));
				   }else if(timeOutLists.get(i).get("SHORTNAME").equals("JFBG")){
					   jfbgTimeOut=DateUtil.addDate(new Date(),- Integer.parseInt(timeOutLists.get(i).get("REMARK").toString().trim()));
				   }else if(timeOutLists.get(i).get("SHORTNAME").equals("BZX")){
					   bzxTimeOut=DateUtil.addDate(new Date(), -Integer.parseInt(timeOutLists.get(i).get("REMARK").toString().trim()));
				   }
			  }
		 }
		if(null!=tasks&& tasks.size()>0){
			for(int i =0;i<tasks.size();i++){
				if(null!=tasks.get(i).get("TASK_NAME")){
					Date distributionTime=(Date) tasks.get(i).get("DISTRIBUTION_DATE");
					if(null!=distributionTime){
						 if(tasks.get(i).get("TASK_NAME").equals("补充资料")&&distributionTime.compareTo(bczlTimeOut)<0){
							  //补充资料超时
							  doTimeOutTask(tasks.get(i).get("DATA_ID").toString(),"补充资料");
							  
						  }else if(tasks.get(i).get("TASK_NAME").equals("家访报告")&&distributionTime.compareTo(jfbgTimeOut)<0){
							  //家访超时
							  doTimeOutTask(tasks.get(i).get("DATA_ID").toString(),"家访");
						  }else if(tasks.get(i).get("TASK_NAME").equals("补征信")&&distributionTime.compareTo(bzxTimeOut)<0){
							  //补征信超时
							  doTimeOutTask(tasks.get(i).get("DATA_ID").toString(),"补征信");
							  
						  }
						
					}
				}
				
			}
			 logger.info("节点超时定时任务========AutoDealTaskTimeoutJob执行结束");
		}else{
			logger.info("节点超时定时任务========AutoDealTaskTimeoutJob未查询出满足条件结果");
		}
	}
	
	private  void  doTimeOutTask(String taskId,String type) {
		logger.info("节点超时定时任务========当前节点名称："+type+";taskId="+taskId);
		String SPJL="";
		if(type.equals("补充资料")){
			SPJL="6";
		}else if(type.equals("家访")){
			SPJL="7";
		}else if(type.equals("补征信")){
			SPJL="8";
		}
		String tranName_="客户放弃";
		//TODO 下一节点name
		String nextname_="客户放弃";
		//进入下一环节
		try {
			TaskService service = new TaskService();
			MemoService memoService = new MemoService();
			LetterOpinionService letterService = new LetterOpinionService();
			
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
			String memo_text = type+"处理超时，默认为客户自动放弃";
			String wmemo_text = type+"处理超时，默认为客户自动放弃";
			//查询是否有该任务，如果数据库表中没有该任务，则添加；已有任务则更新
			Map<String,Object> taskSubmit=memoService.getMemosByTaskId(taskId);
			
			//如果已有任务，更新
			if(taskSubmit!=null && taskSubmit.size()>0){
				memoService.updateMemoSubmit(taskId, "系统", "", taskSubmit.get("MEMO")+memo_text, nextname_, url, "BROWSER", null,tranName_);
			}else{
				memoService.addMemo(taskId, (String) task.get("EXECUTION_ID_"), (String) task.get("NAME_"), url,"系统","",memo_text, "",tranName_);
			}
			//jzzl-83  超时信息不再写入信审意见，只写入审批意见  add by 邢素敏
			
			//信审意见
//			Map<String,Object>  params= new  HashMap<String,Object>();
//			params.put("TASK_ID", taskId);
//			Map<String,Object> projectmap=taskClaimService.getProjectIdBytTaskId(params);
//			if(null!=projectmap){
//				Map<String,Object> letterSubmit=letterService.getLetterOpinion(projectmap);
//				if(null!=letterSubmit&&letterSubmit.size()>0){
//					letterSubmit.put("ENDTRIAL_SPJL", SPJL);
//					letterSubmit.put("FIRSTTRIAL_STATUS", "2");
//					letterSubmit.put("ENDTRIAL_STATUS", "2");
//					if(null!=letterSubmit.get("FIRSTTRIAL_CREATEDATA")){
//						letterSubmit.put("FIRSTTRIAL_CREATEDATA", DateUtil.format((Date)letterSubmit.get("FIRSTTRIAL_CREATEDATA"), "yyyy-MM-dd"));
//					}
//					if(null!=letterSubmit.get("FIRSTTRIAL_UPDDATA")){
//						letterSubmit.put("FIRSTTRIAL_UPDDATA", DateUtil.format((Date)letterSubmit.get("FIRSTTRIAL_UPDDATA"), "yyyy-MM-dd"));
//					}
//					if(null!=letterSubmit.get("ENDTRIAL_CREATEDATA")){
//						letterSubmit.put("ENDTRIAL_CREATEDATA", DateUtil.format((Date)letterSubmit.get("ENDTRIAL_CREATEDATA"), "yyyy-MM-dd"));
//					}
//					if(null!=letterSubmit.get("ENDTRIAL_UPDDATE")){
//						letterSubmit.put("ENDTRIAL_UPDDATE", DateUtil.format((Date)letterSubmit.get("ENDTRIAL_UPDDATE"), "yyyy-MM-dd"));
//					}
//					letterSubmit.put("ENDTRIAL_STATUS", "2");
//					letterSubmit.put("ENDTRIAL_UPDDATE", DateUtil.format(new Date(), "yyyy-MM-dd"));
//					//更新信审意见
//					letterService.updateLetterOpinion(letterSubmit);
//				}else{
//				   //添加信审意见
//					letterSubmit=   new  HashMap<String,Object>();
//					letterSubmit.put("PROJECT_ID", projectmap.get("PROJECT_ID"));
//					letterSubmit.put("ENDTRIAL_SPJL", SPJL);
//					letterSubmit.put("FIRSTTRIAL_STATUS", "2");
//					letterSubmit.put("ENDTRIAL_STATUS", "2");
//					letterSubmit.put("ENDTRIAL_UPDDATE", DateUtil.format(new Date(), "yyyy-MM-dd"));
//					letterService.addLetterOpinion(letterSubmit);
//				}
//				
//			}
			
			
			
			
			
			
			//传阅？
			//service.addCy(taskId, (String) config.get("CY"));
			//SkyEye.getRequest().setAttribute("_TEMP_OP", (String) param.get("nextOpCode"));
			if (tranName_ == null || "".equals(tranName_)) {
				JBPM.completeTask(taskId);
			} else {
				JBPM.completeTask(taskId, tranName_);
			}
			// ------------ 执行后续操作
			// ------------ TODO 设置新任务操作人 需考虑会签情况，用流程图默认功能实现。
			//拒件原因(不需要)
			//service.updnotGoMemo(param);
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
		} catch (ActionException e) {
			logger.error("节点超时定时任务========AutoDealTaskTimeoutJob执行异常",e);
		} catch (Exception e) {
			logger.error("节点超时定时任务========AutoDealTaskTimeoutJob执行异常",e);
		}
	}
	
}