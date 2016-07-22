package com.pqsoft.returnVisit.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.returnVisit.service.ReturnVisitService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class ReturnVisitAction extends Action {

	ReturnVisitService service = new ReturnVisitService();

	@aPermission(name = { "资产管理", "巡视管理", "列表显示" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply newInsuranceManage() {
		VelocityContext context = new VelocityContext();
		context.put("content", SkyEye.getRequest().getParameter("content"));
		return new ReplyHtml(VM.html("returnVisit/allConsrack.vm", context));
	}

	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply newInsuranceManage_AJAX() {
		Map<String, Object> m = _getParameters();
		Page pt = service.newInsuranceManage(m);
		return new ReplyAjaxPage(pt);
	}

	@SuppressWarnings("unchecked")
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getEmpByRectId() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = (List<Map<String, Object>>) service
				.getEmpByRectId(param);
		return new ReplyAjax(list);
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Object addReturnVisit() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/AddReturnVisit.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "基本信息" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitJB() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "1");
		context.put("JBXX_LIST", service.getXS(param));
		context.put("custBaseInfo", service.getCustBaseInfo(param));
		System.out.println("-------------------------="
				+ service.getCustBaseInfo(param));
		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/JB.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "基本信息" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitFR() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "2");
		context.put("FRDBXX_LIST", service.getXS(param));

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/FR.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "核心团队" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitHXTD() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "3");
		context.put("HXTD_LIST", service.getXS(param));

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/HX.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "租赁物" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitZLW() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "4");
		context.put("ZLW_LIST", service.getXS(param));

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/ZLW.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "还款能力情况" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitHKNL() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "5");
		context.put("HKNL_LIST", service.getXS(param));

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/HKNL.vm", context));
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "综合评价" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addReturnVisitZHPJ() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		param.put("TYPE", "6");
		context.put("ZHPJ_LIST", service.getXS(param));

		context.put("param", param);
		return new ReplyHtml(VM.html("returnVisit/ZH.vm", context));
	}

	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply addReturnVisitSave() {
		JSONObject json = new JSONObject();
		boolean flag = false;
		Map<String, Object> param = _getParameters();
		if (param.containsKey("P_TIME") && !param.get("P_TIME").equals("")) {

			// 添加
			flag = service.insertReturnVisitXS(param);

		}
		return new ReplyAjax(flag);
	}

	@aPermission(name = { "资产管理", "巡视管理", "新建", "上传附件" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply returnVisitFile() {
		Map<String, Object> param = _getParameters();
		// 设置文件上传的大小 目前设置为50M
		final long MAX_SIZE = 60 * 1024 * 1024;
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(4096);
		ServletFileUpload fp = new ServletFileUpload(dfif);
		fp.setHeaderEncoding("UTF-8");
		fp.setSizeMax(MAX_SIZE);
		List<?> fileList = null;
		try {
			fileList = fp.parseRequest(SkyEye.getRequest());
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("开始上传文件");
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = "/pqsoft/xunShiWenJian/";// 可放在配置文件里配置
		{
			new File(path).mkdirs();
		}
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (!fileitem.getName().isEmpty()
						&& !"".equals(fileitem.getName())) {
					if (fileitem.getSize() == 0)
						continue;
					String fileName = fileitem.getName();
					file = new File(path + File.separator + UUID.randomUUID()
							+ fileitem.getName());
					try {
						fileitem.write(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String filePath = file.getPath();// 上传路径
					param.put("FILE_NAME", fileName);
					param.put("FILE_URL", filePath);
					param.put(param.get("TYPE") + "_PICTURE_NAME", fileName);
					param.put(param.get("TYPE") + "_PICTURE_ADD", filePath);
					logger.info("保存文件完毕：" + file.getAbsolutePath());
				}
			}
		}
		JSONObject json = new JSONObject();
		json.putAll(param);

		return new ReplyJson(JSONArray.fromObject(json));
	}

	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public HttpServletResponse download() {
		Map<String, Object> param = _getParameters();
		HttpServletResponse response = SkyEye.getResponse();
		try {
			// path是指欲下载的文件的路径。
			File file = new File(param.get("PATH").toString());
			// 取得文件名。
			String filename = file.getName();
			logger.info(filename);
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf( "." ) + 1
			// ).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(param
					.get("PATH").toString()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			// FUYULONG 20140717 文件后缀名错误
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(param.get("FILE_NAME").toString().getBytes(
							"GB2312"), "ISO-8859-1"));
			response.addHeader("Content-Length", file.length() + "");
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			// 关闭流
			toClient.close();
			fis.close();
		} catch (IOException ex) {
			logger.error(ex);
		}
		return response;
	}

	// @aPermission(name = { "巡视管理", "修改页面" ,"修改" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateReturnVisit() {
		Map<String, Object> param = _getParameters();
		List<Object> list = service.getXSbyFEID(param);
		return new ReplyAjax(list);
	}

	// @aPermission(name = { "巡视管理", "查看页面" ,"查看" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.LOGIN)
	public Reply showReturnVisit() {
		Map<String, Object> param = _getParameters();
		List<Object> list = service.getXSbyFEID(param);
		return new ReplyAjax(list);
	}

	@Override
	public Reply execute() {
		// TODO
		return null;
	}

	/*****************************************************************************************************************
	 *************** @author yx @date 2015-03-09 *************** 以下为客户资信过程中的家访信息管理
	 *         ******************* *************************
	 *****************************************************************************************************************/
	@aPermission(name = { "业务管理", "家访管理", "列表显示" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.USER)
	public Reply toMgVisitMain() {
		VelocityContext context = new VelocityContext();
		context.put("type", JSONArray.fromObject(new DataDictionaryMemcached().get("疑点类别")));
		return new ReplyHtml(VM.html("jiafang/toMgVisit.vm", context));
	}

	// @aPermission(name = { "巡视管理", "查看页面" ,"查看" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toMgVisit() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		return new ReplyAjaxPage(service.toMgVisit(param));
	}

	@aPermission(name = { "业务管理", "家访管理", "添加" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.USER)
	public Reply toAddVisit() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
		context.put("daikuan", new DataDictionaryMemcached().get("资信是否贷款购买"));
		context.put("type", new DataDictionaryMemcached().get("疑点类别"));
		context.put("peihe", new DataDictionaryMemcached().get("客户配合程度"));
		return new ReplyHtml(VM.html("jiafang/toAddVisit.vm", context));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddReturnVisitSave() {// 从能模块中添加家访数据
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		String msg = "";
		int i = service.doAddReturnVisitSave(param);
		if (i > 0) {
			flag = true;
			msg = "添加成功";
		} else {
			msg = "添加失败";
		}
		return new ReplyAjax(flag, msg);
	}

	@SuppressWarnings("unchecked")
	@aPermission(name = { "业务管理", "家访管理", "查看" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.USER)
	public Reply toViewVisit() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String,Object> visit1 = (Map<String, Object>) service.toViewVisit1(param);//查看家访信息
		Map<String,Object> pro = (Map<String, Object>) service.toGetProjectInfo(param);//查看项目信息		

		context.put("param", param);
		context.put("visit1", visit1);
		context.put("pro", pro);
		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
//		Map<String,Object> visit = (Map<String, Object>) service.toViewVisit(param);
		//疑点类别处理
//		if(visit != null && !"".equals(visit.get("ZX_YD_TYPE").toString())){
//			String[] yd_type = visit.get("ZX_YD_TYPE").toString().split(",");
//			context.put("yd_type", yd_type);
//		}
		
//		context.put("param", param);
//		context.put("visit", visit);
//		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
//		context.put("daikuan", new DataDictionaryMemcached().get("资信是否贷款购买"));
//		context.put("type", new DataDictionaryMemcached().get("疑点类别"));
		context.put("peihe", new DataDictionaryMemcached().get("客户配合程度"));
		return new ReplyHtml(VM.html("jiafang/toViewVisit.vm", context));
	}

	@aPermission(name = { "业务管理", "家访管理", "修改" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.USER)
	public Reply toUpdateVisit() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		
		Map<String,Object> visit1 = (Map<String, Object>) service.toViewVisit1(param);//查看家访信息
		Map<String,Object> pro = (Map<String, Object>) service.toGetProjectInfo(param);//查看项目信息		

		context.put("param", param);
		context.put("visit1", visit1);
		context.put("pro", pro);
		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
		
//		Map<String,Object> visit = (Map<String, Object>) service.toViewVisit(param);
//		//疑点类别处理
//		if(visit != null && !"".equals(visit.get("ZX_YD_TYPE").toString())){
//			String[] yd_type = visit.get("ZX_YD_TYPE").toString().split(",");
//			context.put("yd_type", yd_type);
//		}
//		
//		context.put("param", param);
//		context.put("visit", service.toViewVisit(param));
//		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
//		context.put("daikuan", new DataDictionaryMemcached().get("资信是否贷款购买"));
//		context.put("type", new DataDictionaryMemcached().get("疑点类别"));
//		context.put("peihe", new DataDictionaryMemcached().get("客户配合程度"));
		return new ReplyHtml(VM.html("jiafang/toUpdateVisit.vm", context));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doUpReturnVisitSave() {// 修改家访记录操作
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		String msg = "";
		int i = service.doUpReturnVisitSave(param);
		if (i > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}

	@aPermission(name = { "业务管理", "家访管理", "删除" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.USER)
	public Reply doDelVisit() {
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		String msg = "";
		int i = service.doDelVisit(param);
		if (i > 0) {
			flag = true;
			msg = "删除成功";
		} else {
			msg = "删除失败";
		}
		return new ReplyAjax(flag, msg);
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toGetProjectInfo() {// 流程页面， 家访节点调用改页面
		Map<String, Object> param = _getParameters();
		System.out.println("--jiafang--param:"+param);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		VelocityContext context = new VelocityContext();
		param.put("ZX_SURVEY", Security.getUser().getName());// 调查人员
		param.put("ZX_SURVEY_TIME", df.format(new Date()));///调查日期
		param.put("P_TIME", df.format(new Date()));//调查时间
//		Map<String,Object> visit1 = (Map<String, Object>) service.toViewVisit1(param);//查看家访信息
//		Map<String,Object> pro = (Map<String, Object>) service.toGetProjectInfo(param);//查看项目信息		
		List<Map<String, Object>> visits = service.toViewVisit1Ask(param);//查看家访信息,多次家访记录
		List<Map<String, Object>> pros = service.toGetProjectInfoAsk(param);//查看项目信息		

//		param.put("jfym", "1");//jfym=='0'则为修改页面,jfym=='1'则为查看页面
		context.put("param", param);
		context.put("visits", visits);
		context.put("pros", pros);
		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
		
//		if(visit1!=null){
//			String[] yd_type = visit1.get("ZX_YD_TYPE").toString().split(","); 
//			context.put("yd_type", yd_type);
//		}
		
	    //查看担保人
//		if("2".equals(pro.get("GUARANTEE").toString())||"3".equals(pro.get("GUARANTEE").toString())){
//			context.put("dbr", service.getGUARANTORInfo(pro));
//		}
		
		//查看共同承租人
//		if(pro.get("JOINT_TENANT_ID")!=null){
//			context.put("gcr", service.getGCRInfo(pro));
//		}
		
//		context.put("visit2", service.toGetAddrVister(visit1));
//		context.put("daikuan", new DataDictionaryMemcached().get("资信是否贷款购买"));
//		context.put("type", new DataDictionaryMemcached().get("疑点类别"));
//		context.put("peihe", new DataDictionaryMemcached().get("客户配合程度"));
		return new ReplyHtml(VM.html("jiafang/toGetProjectInfo.vm", context));
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "YX")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddReturnVisitSave1() {// 在流程中添加家访信息
		Map<String, Object> param = _getParameters();
//多次家访,保留家访记录
		service.doDelVisit1(param);
		boolean flag = false;
		String msg = "";
//		if(!"".equals(param.get("TR_ID").toString())){
//			service.doDelVisit(param);
//		}
		param.put("ZX_ASKID", "0");//家访保存时,家访审批状态为0
		int i = service.doAddReturnVisitSave(param);
		if (i > 0) {
			flag = true;
			msg = "添加成功";
		} else {
			msg = "添加失败";
		}
		return new ReplyAjax(flag, msg);
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "杨雪")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "家访管理", "修改调查期限" })
	public Reply doUpINVESTIGATE(){
		Map<String, Object> param = _getParameters();
		boolean flag = false;
		String msg = "";
		int i = service.doUpReturnVisitSave(param);
		if (i > 0) {
			flag = true;
			msg = "修改调查期限成功";
		} else {
			msg = "修改调查期限失败";
		}
		return new ReplyAjax(flag, msg);
	}
}
