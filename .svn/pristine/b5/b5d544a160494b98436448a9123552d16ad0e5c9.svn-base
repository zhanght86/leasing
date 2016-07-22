package com.pqsoft.projectContraceControl.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.projectContraceControl.services.ProjectContraceControlService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 合同生成
 * @author caizhongyang
 *
 */
public class ProjectContraceControlAction extends Action {
	public VelocityContext context = new VelocityContext();
	private String path="projectContraceControl/";
	private ProjectContraceControlService service=new ProjectContraceControlService();
	@Override
	@aPermission (name = { "合同生成" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"projectContraceControl.vm", context));
	}
	
	
	@aPermission (name = { "合同生成" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getDataListContractControl() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"projectContractControlManage.vm",context));
	}

	
	@aPermission (name = { "合同生成" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply pageAjaxProjectControl() {
		Map<String, Object> param = _getParameters();
		BaseUtil.getDataAllAuth(param);
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission (name = { "合同生成" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply projectContraceControlTest() {
		//http://localhost:8080/lop-project/projectContraceControl/ProjectContraceControl!projectContraceControlTest.action
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"projectContractControlManageTest.vm",context));
	}
	
	/**申请审批**/
	@aPermission (name = {"合同生成"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply checkProjectContractByJbpm(){
		Map<String,Object> param = _getParameters();
		List<String> list = JBPM.getDeploymentListByModelName("合同撤销审批",
				null, Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		
		jmap.put("ID", param.get("ID").toString());
		String jbpmId = JBPM.startProcessInstanceById(pid,
				"", "","", jmap).getId();
		//map.put("STATUS", 1);
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("ID", param.get("ID").toString());
		param1.put("STATUS", 99);
		param1.put("CACELTYPE", 1);
		Dao.update("contractProjectManage.updateProjectCacelType", param1);
//		context.put("RST", jbpmId);
//		context.put("MSG", "1");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("RST", jbpmId);
		return new ReplyAjax(jsonObject);
	}
	
	/**
	 * 保存文件
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"合同生成"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply saveFiles() {
		/*文件list*/
		
		List<FileItem> fileList = _getFileItem();
		List<ProjectContraceFile> fileList2=new ArrayList<ProjectContraceFile>();
		if(fileList.size()>0){
			for (int i = 0; i < fileList.size(); i++) {
				FileItem fileItem=fileList.get(i);
				if(null==fileItem.getName()||"".equals(fileItem.getName())){
					fileList.remove(i);
					continue;
				};
				List<FileItem> newFileList=new ArrayList<FileItem>();
				newFileList.add(fileList.get(i));
				Map<String, Object> fileMap = service.uploadFileForOne(newFileList,"hetongqianding");
				ProjectContraceFile projectContraceFile=new ProjectContraceFile();
				projectContraceFile.setFILE_NAME(fileMap.get("FILE_NAME").toString());
				projectContraceFile.setFILE_PATH(fileMap.get("FILE_PATH").toString());
				projectContraceFile.setFILE_TYPE(fileMap.get("FILE_TYPE").toString());
				long filesize= Long.parseLong(fileMap.get("FILE_SIZE").toString());
				int roundFileSize=Math.round((filesize/1024/1024)*100)/100;
				String fileSizeContext="";
				if(0==roundFileSize){
					roundFileSize=Math.round((filesize/1024)*100)/100;
					fileSizeContext=roundFileSize+"KB";
				}else{
					fileSizeContext=roundFileSize+"MB";
				}
				projectContraceFile.setFILE_SIZE(fileSizeContext);
				fileList2.add(projectContraceFile);
			}
		}
		return new ReplyAjax(fileList2);
	} 
	/**
	 * 保存文件信息
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"合同生成"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply addProjectControlFileContext() {
		Map<String, Object> param = _getParameters();
		JSONArray list=JSONArray.fromObject(param.get("list"));
		/*文件list*/
		param.put("num", list.size());
		param.put("list", list);
		return new ReplyAjax(service.addProjectControlFileContext(param));
	}
	
	/**
	 * 展示文件信息
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"合同生成"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply queryProjectControlFileContext() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.queryProjectControlFileContext(param));
	}
}