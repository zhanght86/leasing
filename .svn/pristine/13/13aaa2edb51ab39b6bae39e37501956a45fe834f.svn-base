package com.pqsoft.refundProject.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.refundProject.service.ProjectFileService;
import com.pqsoft.refundProject.service.RefundProjectSerivce;
import com.pqsoft.screened.service.ScreenedService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.Format;
import com.pqsoft.util.FileUtil;

public class RefundProjectAction extends Action {

	private final ScreenedService screened = new ScreenedService();
    RefundProjectSerivce service = new RefundProjectSerivce();
	private final Logger logger = Logger.getLogger(RefundProjectAction.class);
	private final String pagePath = "refundProject/";
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","列表显示"})
	public Reply execute() {
		//页面参数
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("refundway", screened.getRefundBailoutway());
		return new ReplyHtml(VM.html(pagePath+"toMgProject.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","列表显示"})
	public Reply toMgPrjectData() {
		//页面参数
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgProjectData(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply toShowPro() {
		String projectId = SkyEye.getRequest().getParameter("PROJECT_ID");
		Map<String, Object> param = new HashMap<String, Object>();
		VelocityContext context = new VelocityContext();
		param.put("ID", projectId);
		param.put("PROJECT_ID", projectId);
		context.put("param", param);
		Map<String,Object> mm=service.getProjectCheckInfo(param);
		param.put("PAY_WAY", mm.get("BAILOUTWAY_ID"));
		param.put("FID", mm.get("JG_ID"));
		context.put("data", mm);//项目下合同明细
		context.put("paylist", service.getProjectPayList(param));//项目下的合同信息		
		context.put("typelist", new DataDictionaryMemcached().get("融资资料附件类型"));//资料类型
		context.put("filelist", new ProjectFileService().getFileList(projectId));
		context.put("toMgRentDeatil", service.toMgRentDeatil(param));//项目下包含的合同的未还款明细
		return new ReplyHtml(VM.html(pagePath+"refundProjectShow.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","修改"})
	public Reply toUpdatePro() {
		String projectId = SkyEye.getRequest().getParameter("PROJECT_ID");
		Map<String, Object> param = new HashMap<String, Object>();
		VelocityContext context = new VelocityContext();
		param.put("ID", projectId);
		param.put("PROJECT_ID", projectId);
		context.put("param", param);
		Map<String,Object> mm=service.getProjectCheckInfo(param);
		param.put("PAY_WAY", mm.get("BAILOUTWAY_ID"));
		param.put("FID", mm.get("JG_ID"));
		context.put("data", mm);//项目下合同明细
		context.put("paylist", service.getProjectPayList(param));//项目下的合同信息		
		context.put("typelist", new DataDictionaryMemcached().get("融资资料附件类型"));//资料类型
		context.put("filelist", new ProjectFileService().getFileList(projectId));
		context.put("toMgRentDeatil", service.toMgRentDeatil(param));//项目下包含的合同的未还款明细
		return new ReplyHtml(VM.html(pagePath+"refundProjectUpdate.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","修改"})
	public Reply doUpdate() {
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		try {
			System.out.println("param="+param);
			int i = service.updateProject(param);
			if(i<0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("融资项目管理-修改","修改操作","修改"));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","提交银行"})
	public Reply refundProjectStatus() {
		Map<String,Object> map = _getParameters();
		int i = service.toCommitBank(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资项目管理","提交银行","提交状态"));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply getProjectPayList() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("data", service.getProjectCheckInfo(map));//项目下合同明细
		context.put("toMgRentDeatil", service.toMgRentDeatil(map));//项目下包含的合同的未还款明细
		return new ReplyHtml(VM.html(pagePath+"checkPaylist.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply doUpload() {//上传附件
		JSONObject re = new JSONObject();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String customerPath = File.separator + "refundfile";
			final long MAX_SIZE = 10 * 1024 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path");
			// 拿到配置文件中设置的上传文件路径
			FileUtil.createDirectory(root);// 调用创建存放上传文件 文件夹方法
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			List<?> fileList = fp.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			Map<String, Object> param = new HashMap<String, Object>();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					param.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = SkyEye.getConfig("file.path") + File.separator + customerPath + File.separator
							+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					FileUtil.createDirectory(dir);
					File file = new File(dir + File.separator + UUID.randomUUID() + fileItem.getName());
					fileItem.write(file);
					re.put("FILE_NAME", fileItem.getName());
					param.put("FILE_NAME", fileItem.getName());
					param.put("PATH", file.getPath().toString().replace("\\", "/"));
				}
			}
			param.putAll(_getParameters());//页面参数
			param.put("CREATE_ID", Security.getUser().getId());//用户id
			map.put("flag", 1 == new ProjectFileService().addFile(param));
			map.put("ID", param.get("ID"));
			map.put("CREATE_DATE", new Format().date(new Date(), "yyyy-MM-dd HH:mm:ss"));
			map.put("USERNAME", Security.getUser().getName());//用户名
			map.put("FILE_TYPE", param.get("FILE_TYPE"));
			return new ReplyAjax(JSONObject.fromObject(map));
		} catch (Exception e) {
			e.getStackTrace();
			logger.error(e, e);
		}
		map.put("flag", false);
		return new ReplyAjax(JSONObject.fromObject(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply doDownload(){  //下载文件
		ProjectFileService service = new ProjectFileService();
		String path = service.getFile(SkyEye.getRequest().getParameter("id"));
		FileUtil.download(path, SkyEye.getResponse());
		File file = new File(SkyEye.getRequest().getParameter("FILE_NAME"));
		return new ReplyFile(file, "文件下载");
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply doDelete() {//删除上传文件
		String id = SkyEye.getRequest().getParameter("id");
		ProjectFileService service = new ProjectFileService();
		int i = service.delete(id);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资项目管理-查看","删除上传附件","文件删除"));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资项目管理","查看"})
	public Reply downSoft(){//下载案例
		Map<String, Object> map = _getParameters();
		File file = new File(map.get("SOFT_NAME").toString());
		if (file.exists()) {
			FileUtil.download(map.get("SOFT_NAME").toString(), SkyEye.getResponse());
			return new ReplyFile(file,"融资资料下载成功");
		} else return new ReplyFile(file,"融资资料下载失败");
	}
	

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "wujd")
	public Reply dataDownByProId() {
		ProjectFileService service = new ProjectFileService();
		Map<String, Object> map = _getParameters();
		System.out.println(map);
		if (map.get("PRO_ID") == null || "".equals(map.get("PRO_ID"))) return null;
		// 获取融资条件 
		String conditionone = service.getConditiononeList(map);
		if (conditionone == null || "".equals(conditionone)) throw new ActionException("未找到融资条件方式，请先配置融资条件方式！");

		JSONArray json = null;
		
		if (StringUtils.isNotEmpty(conditionone) && !"null".equals(conditionone)){
			json = JSONArray.fromObject(conditionone);
		}
		List<Map<String, Object>> conlist = new ArrayList<Map<String, Object>>();
		if (json != null && !"null".equals(json))conlist = json;
		String contionIds = "";
		if (conlist != null && !conlist.isEmpty() && conlist.size() > 0) {
			for (int j = 0; j < conlist.size(); j++) {
				Map<String, Object> mm = conlist.get(j);
				if(j == 0){
					contionIds += mm.get("contionId") + "";
				}else{
					contionIds += "," + mm.get("contionId");
				}
			}
		}else{
			throw new ActionException("未找到融资条件方式，请先配置融资条件方式！");
		}
		map.put("contionIds", contionIds);
		// 获取融资项目对应业务项目文件
		List<Map<String, Object>> filelist = service.getDateFileListByProid(map);
		
		
		ZipOutputStream out = null;
		byte[] buffer = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			out = new ZipOutputStream(baos);
			for (int i = 0;i<filelist.size();i++) {
				Map<String, Object> m = filelist.get(i);
				if (m == null) continue;
				String path = (String) m.get("PATH");
				if (path == null || "".equals(path)) continue;
				File f = new File(path);
				if (!f.exists()) continue;
				String name = (String) m.get("FILENAME");
				out.putNextEntry(new ZipEntry(name + f.getName()));
				out.setEncoding("GBK");
				FileInputStream fis = new FileInputStream(f);
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				fis.close();
				out.closeEntry();
			}
		} catch (Exception e) {} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ReplyFile(baos.toByteArray(), map.get("PROJECT_CODE")+"项目-资料附件.zip");
	}

	
	/****************************************************************************************************************************************
	 *                                                                                                                                      *
	 *                                                       以下为流程表单信息                                                                                                                                                                      *
	 *                                                                                                                                      *
	 ***************************************************************************************************************************************/
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资项目管理","流程表单"})
//	public Object refundProjectManagerInfo() throws Exception {
//		Map<>
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		context.put("paylist", service.getProjectPayList(param));//项目下的合同信息		
//		context.put("typelist", new DataDictionaryMemcached().get("融资资料附件类型"));//资料类型
//		context.put("filelist", new ProjectFileService().getFileList(projectId));
//		context.put("data", service.getProjectCheckInfo(param));//项目下合同明细
//		context.put("toMgRentDeatil", service.toMgRentDeatil(param));//项目下包含的合同的未还款明细
//		return new ReplyHtml(VM.html("screened/refundProjectManagerInfo.vm", context));
//	}
}
