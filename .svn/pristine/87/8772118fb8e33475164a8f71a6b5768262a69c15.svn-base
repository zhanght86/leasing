//package com.kernal.jbpm.controller;
//
//import java.net.URLDecoder;
//import java.util.Map;
//import java.util.Random;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.servlet.ModelAndView;
//
//import com.kernal.controller.BaseController;
//import com.kernal.jbpm.service.JbpmService;
//import com.kernal.utils.QueryUtil;
//import com.kernal.utils.StringUtil;
//@SuppressWarnings("unchecked")
//public class JbpmController extends BaseController 
//{
//	private JbpmService jbpmService ;
//	public void setJbpmService(JbpmService jbpmService) {
//		this.jbpmService = jbpmService;
//	}
//	public JbpmService getJbpmService() {
//		return jbpmService;
//	}
//	public ModelAndView deployWorkflow(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String zipFilePath = StringUtil.nullToString(request.getParameter("zipFilePath"));
//		zipFilePath = URLDecoder.decode(zipFilePath, "UTF-8");
//		this.getJbpmService().deployWorkflow(zipFilePath);
//		return null;
//	}
//	public ModelAndView rollbackProcessInstance(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String processInstanceId = request.getParameter("currentProcessInstanceId");
//		String deleteAction = StringUtil.nullToString(request.getParameter("currentRollbackAction"));
//		String returnResult = this.getJbpmService().rollbackProcessInstance(processInstanceId,deleteAction,request);
//		//String ajaxCallBackScript = "<script type='text/javascript'>window.top.dropProcessInstanceSuccess('"+returnResult+"');</script>";
//		//this.ajaxReturn(response, ajaxCallBackScript);
//		return null;
//	}
//	public ModelAndView removeDeployedWorkflow(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//	    String deployId = StringUtil.nullToString(request.getParameter("deployId"));
//	    this.getJbpmService().removeDeployedWorkflow(deployId);
//		return null;
//	}
//	public ModelAndView removeProcessInstance(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String processInstanceId = request.getParameter("currentProcessInstanceId");
//		String deleteAction = StringUtil.nullToString(request.getParameter("currentTaskDeleteAction"));
//		String returnResult = this.getJbpmService().removeProcessInstance(processInstanceId,deleteAction,request);
//		String ajaxCallBackScript = "<script type='text/javascript'>window.top.dropProcessInstanceSuccess('"+returnResult+"');</script>";
//		this.ajaxReturn(response, ajaxCallBackScript);
//		return null;
//	}
//	public ModelAndView saveChangeToActivity(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		Map object = QueryUtil.getRequestParameterMap(request);
//		this.getJbpmService().saveChangeToActivity(object);
//		return null;
//	}
//	public ModelAndView startDeployedProcess(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String processDefinitionId = StringUtil.nullToString(request.getParameter("definitionId"));
//		String proj_id = StringUtil.nullToString(request.getParameter("proj_id"));
//		String isNotAllownRepeat  = StringUtil.nullToString(request.getParameter("isNotAllownRepeat"));
//		if(isNotAllownRepeat.trim().equalsIgnoreCase("true"))
//		{
//			if(this.jbpmService.isRepeatRequest(processDefinitionId, proj_id))
//			{
//				String ajaxCallBackScript = "<script type='text/javascript'>alert('该流程不允许重复发起！');window.close();</script>";
//				this.ajaxReturn(response, ajaxCallBackScript);
//				return null;
//			}
//		}
//		Map modelData = QueryUtil.getRequestParameterMap(request);
//		String formPath = this.getJbpmService().startDeployedProcess(request,processDefinitionId, modelData);
//		modelData.put("requestFormPath", formPath);
//		String returnFormPath = "/solutions/workflow/jbpm-core/commonWorkflow.jsp?mathRandom="+new Random().nextInt();
//		return new ModelAndView(returnFormPath,modelData);
//	}
//	public ModelAndView requestProcessForm(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String currentTaskId = StringUtil.nullToString(request.getParameter("currentTaskId"));
//		Map modelData = QueryUtil.getRequestParameterMap(request);
//		String formPath = "";
//		try
//		{
//			formPath = this.getJbpmService().requestProcessTaskForm(request,currentTaskId, modelData);
//		}catch(Exception e)
//		{
//			String ajaxCallBackScript = "<script type='text/javascript'>alert('该任务已被处理！');window.close();</script>";
//			this.ajaxReturn(response, ajaxCallBackScript);
//			return null;
//		}
//		modelData.put("requestFormPath", formPath);
//		String returnFormPath = "solutions/workflow/jbpm-core/commonWorkflow.jsp";
//		return new ModelAndView(returnFormPath,modelData);
//	}
//	public ModelAndView viewHistoryProcessForm(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String deployId = StringUtil.nullToString(request.getParameter("deployId"));
//		String currentProcessInstanceId = StringUtil.nullToString(request.getParameter("currentProcessInstanceId"));
//		String currentTaskId = StringUtil.nullToString(request.getParameter("currentTaskId"));
//		String formPath      = StringUtil.nullToString(request.getParameter("taskFormPath"));
//		Map modelData = QueryUtil.getRequestParameterMapNoDecode(request);//QueryUtil.getRequestParameterMap(request);
//		this.getJbpmService().requestHistoryProcessedTaskForm(request,deployId,currentProcessInstanceId,currentTaskId, modelData);
//		modelData.put("workflow_ishis", "true");
//		modelData.put("requestFormPath", formPath);
//		String returnFormPath = "solutions/workflow/jbpm-core/commonWorkflow.jsp";
//		return new ModelAndView(returnFormPath,modelData);
//	}
//	    
//	public ModelAndView submitProcessedForm(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String currentTaskId = StringUtil.nullToString(request.getParameter("currentTaskId"));
//		Map modelData = QueryUtil.getRequestParameterMapNoDecode(request);
//		String returnResult = "";
//		String ajaxCallBackScript = "";
//		try
//		{
//			returnResult = this.getJbpmService().submitProcessedTaskForm(request,currentTaskId, modelData);
//			ajaxCallBackScript = "<script type='text/javascript'>window.top.ajaxCallBack('"+returnResult+"');window.top.isSavedButtonPressed=true;</script>";
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			ajaxCallBackScript = e.getMessage();
//			ajaxCallBackScript = "<script type='text/javascript'>window.top.alert('"+ajaxCallBackScript+"');window.close();</script>";
//		}
//		this.ajaxReturn(response, ajaxCallBackScript);
//		return null;
//	}
//	public ModelAndView getProcessDefinitionWorkflowPicture(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String  deployId = request.getParameter("deployId");
//	    String  imageResourceName = request.getParameter("imageResourceName");
//	    ServletOutputStream sos = response.getOutputStream();
//	    byte[] workflowPictureByteData = this.getJbpmService().getProcessWorkflowPicture(deployId, imageResourceName);
//	    sos.write(workflowPictureByteData, 0, workflowPictureByteData.length);
//		sos.flush();
//		sos.close();
//		return null;
//	}
//	public ModelAndView getProcessInstanceWorkflowPicture(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		String  processInstanceId = request.getParameter("processInstanceId");
//	    ServletOutputStream sos = response.getOutputStream();
//	    byte[] workflowPictureByteData = this.getJbpmService().getProcessWorkflowPicture(processInstanceId);
//	    sos.write(workflowPictureByteData, 0, workflowPictureByteData.length);
//		sos.flush();
//		sos.close();
//		return null;
//	}
//	public ModelAndView setDelegate(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//		Map delegateInfo = QueryUtil.getRequestParameterMap(request);
//		boolean isCancel = new Boolean(request.getParameter("isCancel")).booleanValue();
//		this.getJbpmService().setDelegate(delegateInfo, isCancel);
//		return null;
//	}
//}
