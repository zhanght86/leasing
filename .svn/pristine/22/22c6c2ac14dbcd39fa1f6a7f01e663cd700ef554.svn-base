package com.pqsoft.fi.payin.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.util.FileCopyUtils;
import org.xml.sax.InputSource;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.fi.payin.service.FundUploadService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.ReplyExcel;

public class FundUploadAction extends Action {

	@Override
	@aPermission(name = { "资金管理", "还款录入", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_upload.vm", context));
	}

	@aPermission(name = { "资金管理", "还款录入", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		return new ReplyAjaxPage(PageUtil.getPage(_getParameters(), "fi.fundupload.getFileList", "fi.fundupload.getFileListCount"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply download() {
		FundUploadService service = new FundUploadService();
		Map<String, Object> m = service.getFile(SkyEye.getRequest().getParameter("id"));
		String path = (String) m.get("PATH");
		return new ReplyFile(new File(path), (String) m.get("NAME"));
	}

	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply upload2() {
		Map<String, Object> output = null;
		FundUploadService service = new FundUploadService();
		try {
			logger.debug("设置文件大小");
			final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(4096);
			File f = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload");
			File f1 = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp");
			f.mkdirs();
			f1.mkdirs();
			// dfif.setRepository(f);// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
			logger.debug("获取servlet上传文件");
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			sfu.setHeaderEncoding("UTF-8");
			sfu.setSizeMax(MAX_SIZE);
			System.out.println("ddd--------");
			List<?> fileList = sfu.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			System.out.println("22222222"+fileList);
			String type = null;
			File file = null;
			File filetemp = null;
			String HouZhui="";
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("TYPE")) {
						type = fileItem.getString("UTF-8");
					}
				} else {
					if (fileItem.getFieldName().equals("file")) {
						logger.debug("开始获取文件：" + fileItem.getName());
						if (!(fileItem.getName().endsWith(".xls") || fileItem.getName().endsWith(".xlsx") || fileItem.getName().endsWith(".txt")))
							throw new ActionException("您上传的文件格式不正确!必须为excel的*.xls;*.xlsx格式或者txt的*.txt格式");
						
						if(fileItem.getName().endsWith(".xls") || fileItem.getName().endsWith(".xlsx")){
							HouZhui="xls";
						}else if(fileItem.getName().endsWith(".txt")){
							HouZhui="txt";
						}
						file = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload" + File.separator + fileItem.getName());
						if (file.exists()) throw new ActionException("文件已经存在，如未上传过该文件重命名后再上传");
						filetemp = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp" + File.separator + fileItem.getName());
						fileItem.write(filetemp);
						System.out.println("dd-----------"+file.getAbsolutePath());
						// 检查完成，开始解析文件
					}
				}
			}
			if (type != null && filetemp != null && HouZhui.equals("xls")) {
				output = service.parseUploadFile(filetemp, type);
			}else if(type != null && filetemp != null && HouZhui.equals("txt")){
				output = service.parseUploadFileTXT(filetemp, type);
			}
			{
				output.put("TYPE", type);
				// if (output.containsKey("flag") && (Boolean)
				// output.get("flag")) {
				logger.debug("文件保存完毕：" + file.getAbsolutePath());
				output.put("PATH", file.getAbsolutePath());
				output.put("FILE", file.getName());
				output.put("FILE_TEMP", filetemp.getName());
				output.put("HOUZHUI",HouZhui);
				// }
			}
		} catch (Exception e) {
			logger.error("文件上传错误", e);
		}
		VelocityContext context = new VelocityContext();
		context.put("output", output);
		context.put("fundupload", SkyEye.getConfig("file.path"));
		return new ReplyHtml(VM.html("fi/payin/fund_upload_view.vm", context));
	}
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply upload() {
		//获取上传文件的操作人姓名和code
		String userName = Security.getUser().getName();
		String userCode = Security.getUser().getCode();
		System.out.println(userName+"---------"+userCode);
		Map<String, Object> output = null;
		FundUploadService service = new FundUploadService();
		try {
			logger.debug("设置文件大小");
			final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(4096);
			File f = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload");
			File f1 = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp");
			File f2 = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload_annex");
			f.mkdirs();
			f1.mkdirs();
			f2.mkdirs();
			// dfif.setRepository(f);// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
			logger.debug("获取servlet上传文件");
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			sfu.setHeaderEncoding("UTF-8");
			sfu.setSizeMax(MAX_SIZE);
			List<?> fileList = sfu.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			System.out.println("22222222----------"+fileList);
			String type = null;
			File file = null;//还款文件
			File file_annex = null;//附件
			File filetemp = null;
			String fileName = "";
			String HouZhui="";
			String savePath = "";
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("TYPE")) {
						type = fileItem.getString("UTF-8");
					}
				} else {
					type = "bankback";
					if (fileItem.getFieldName().equals("file")) {
						System.out.println("sssssss---------"+fileItem);
						logger.debug("开始获取文件：" + fileItem.getName());
						if (!(fileItem.getName().endsWith(".xls") || fileItem.getName().endsWith(".xlsx") || fileItem.getName().endsWith(".txt")))
							throw new ActionException("您上传的文件格式不正确!必须为excel的*.xls;*.xlsx格式或者txt的*.txt格式");
						
						if(fileItem.getName().endsWith(".xls") || fileItem.getName().endsWith(".xlsx")){
							HouZhui="xls";
						}else if(fileItem.getName().endsWith(".txt")){
							HouZhui="txt";
						}
						file = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload" + File.separator + fileItem.getName());
						//if (file.exists()) throw new ActionException("文件已经存在，如未上传过该文件重命名后再上传");
						filetemp = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp" + File.separator + fileItem.getName());
						fileItem.write(filetemp);
						
					}else if(fileItem.getFieldName().equals("file2")){
						long mm = System.currentTimeMillis();
						file_annex = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload_annex" + File.separator + mm+fileItem.getName());
						//if (file.exists()) throw new ActionException("文件已经存在，如未上传过该文件重命名后再上传");
						fileItem.write(file_annex);
						fileName = fileItem.getName();
						savePath = SkyEye.getConfig("file.path") + File.separator + "fundupload_annex" + File.separator + mm+fileItem.getName();
					}
				}
			}
			//写service
			if (type != null && filetemp != null && HouZhui.equals("xls")) {
				output = service.parseUploadFile(filetemp, type);
				//去附件表中查询FUND_UPLOAD_ID的值
				output.put("FUND_UPLOAD_ID", service.searchSquence().get("FUND_UPLOAD_ID"));
				List<Map<String, Object>> list = (List<Map<String, Object>>) output.get("ITEMS");
				List list_Account = new ArrayList();
				//此处存储数据到还款表中---循环插入
				for(int i=0;i<list.size();i++){
					Map<String, Object> addMap = list.get(i);
					addMap.put("FUND_UPLOAD_ID", output.get("FUND_UPLOAD_ID"));
					list_Account.add(addMap.get("FUND_COMECODE"));
					output.put("FUND_COMENAME", addMap.get("FUND_COMENAME"));
					System.out.println("addMap---------"+addMap+"---"+addMap.get("FUND_COMENAME")+"===");
					service.addUpload(addMap);
				}
				output.put("FUND_COMECODE", list_Account);
				//获取资金汇总
				output.put("FUND_COUNT_MONEY",service.searchMoneyCount(output).get("FUND_COUNT_MONEY"));
			}else if(type != null && filetemp != null && HouZhui.equals("txt")){
				output = service.parseUploadFileTXT(filetemp, type);
				//去附件表中查询FUND_UPLOAD_ID的值
				output.put("FUND_UPLOAD_ID", service.searchSquence().get("FUND_UPLOAD_ID"));
				List<Map<String, Object>> contentListData = (List<Map<String, Object>>) output.get("ITEMS");
				//此处存储数据到还款表中---循环插入
				for(int i=0;i<contentListData.size();i++){
					Map<String, Object> addMap = contentListData.get(i);
					addMap.put("FUND_UPLOAD_ID", output.get("FUND_UPLOAD_ID"));
					service.addUpload(addMap);
				}
				output.put("FUND_RESULT","成功");
				//获取资金汇总
				output.put("FUND_COUNT_MONEY",service.searchMoneyCountTXT(output).get("FUND_COUNT_MONEY"));
			}
			//上传人名称和编码
			output.put("FUND_USERNAME",userName);
			output.put("FUND_USERCODE",userCode);
			//附件路径
			output.put("FUND_FILE_PATH",savePath);
			//附件名称
			output.put("FUND_FILE_NAME",fileName);
			//插入附件
			service.insertUploadFilePathCount(output);
			{
				output.put("TYPE", type);
				logger.debug("文件保存完毕：" + file.getAbsolutePath());
				output.put("PATH", file.getAbsolutePath());
				output.put("FILE", file.getName());
				output.put("FILE_TEMP", filetemp.getName());
				output.put("HOUZHUI",HouZhui);
			}
		} catch (Exception e) {
			logger.error("文件上传错误", e);
		}
		VelocityContext context = new VelocityContext();
		context.put("output", output);
		context.put("fundupload", SkyEye.getConfig("file.path"));
		
		
		
		return new ReplyHtml(VM.html("fi/payin/fund_upload_view.vm", context));
	}
	
	/*@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply uploadPath(){
		Map<String, Object> param = _getParameters();
		System.out.println("param2-----------"+param);
		System.out.println(param.get("FUND_UPLOAD_ID")+"===========");
		System.out.println(param.get("FUND_RESULT")+"-------------");
		Map<String, Object> output2 = null;//output2为第二次上传附件
		FundUploadService service = new FundUploadService();
			try {
				final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
				DiskFileItemFactory dfif = new DiskFileItemFactory();
				File f = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload");
				File f1 = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp");
				f.mkdirs();
				f1.mkdirs();
				// dfif.setRepository(f);// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
				logger.debug("获取servlet上传文件");
				ServletFileUpload sfu = new ServletFileUpload(dfif);
				sfu.setHeaderEncoding("UTF-8");
		
				List<?> fileList = sfu.parseRequest(SkyEye.getRequest());
				Iterator<?> it = fileList.iterator();
				String type = null;
				File file = null;
				File filetemp = null;
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("TYPE")) {
							type = fileItem.getString("UTF-8");
						}
					} else {
						if (fileItem.getFieldName().equals("file")) {
							logger.debug("开始获取文件：" + fileItem.getName());
							file = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload" + File.separator + fileItem.getName());
							if (file.exists()) throw new ActionException("文件已经存在，如未上传过该文件重命名后再上传");
							filetemp = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp" + File.separator + fileItem.getName());
							fileItem.write(filetemp);
							// 检查完成，开始解析文件
						}
					}
				}
				Map<String,Object> pmap = new HashMap<String, Object>();
				//获取上传路径
				pmap.put("FUND_FILE_PATH", file.getAbsolutePath());
				System.out.println("ddd---------"+pmap.get("FUND_FILE_PATH"));
				//通过传参个数区别上传文件类型
				if(param.size()==1){
					//获取主表id
					pmap.put("FUND_UPLOAD_ID",param.get("FUND_UPLOAD_ID"));
					System.out.println("ddd---------"+pmap.get("FUND_UPLOAD_ID"));
					
					//获取资金汇总
					pmap.put("FUND_COUNT_MONEY",service.searchMoneyCount(pmap).get("FUND_COUNT_MONEY"));
					System.out.println("ddd---------"+pmap.get("FUND_COUNT_MONEY"));
					System.out.println("pmap-----------"+pmap);
				}else{
					//获取主表id
					pmap.put("FUND_UPLOAD_ID",param.get("FUND_UPLOAD_ID"));
					System.out.println("ddd---------"+pmap.get("FUND_UPLOAD_ID"));
					pmap.put("FUND_RESULT",param.get("FUND_RESULT"));
					System.out.println("ddd---------"+param.get("FUND_RESULT"));
					//获取资金汇总
					pmap.put("FUND_COUNT_MONEY",(service.searchMoneyCountTXT(pmap)).get("FUND_COUNT_MONEY"));
					System.out.println("ddd---------"+pmap.get("FUND_COUNT_MONEY"));
				}
				//上传附件信息、还款金额汇总插入数据库
				service.insertUploadFilePathCount(pmap);
				//从数据库获取附件信息详细
				output2 = service.searchUploadFilePath(pmap);
				System.out.println("ddd---------"+output2);
			}catch (Exception e) {
				logger.error("文件上传错误", e);
			}
		VelocityContext context = new VelocityContext();
		context.put("output2", output2);
		
		return new ReplyHtml(VM.html("fi/payin/fund_upload_filepath.vm", context));
		//return new ReplyAjaxPage(null);
	}*/

	//@aPermission(name = { "资金管理", "还款录入" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doSave() {
		boolean flag = true;
		try {
			FundUploadService service = new FundUploadService();
			HttpServletRequest request = SkyEye.getRequest();
			String filename = request.getParameter("FILE");
			String HouZhui="";
			if(filename.endsWith(".xls") || filename.endsWith(".xlsx")){
				HouZhui="xls";
			}else if(filename.endsWith(".txt")){
				HouZhui="txt";
			}
			
			String FILE_TEMP = request.getParameter("FILE_TEMP");
			String type = request.getParameter("FILE_TYPE");
			File file = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload" + File.separator + filename);
			if (file.exists()) throw new ActionException("文件重复");
			file.createNewFile();
			File temp = new File(SkyEye.getConfig("file.path") + File.separator + "funduploadtemp" + File.separator + FILE_TEMP);
			FileCopyUtils.copy(temp, file);
			Map<String, Object> output =new HashMap<String, Object>();
			if (HouZhui.equals("xls")) {
				output = service.parseUploadFile(file, type);
			}else if(HouZhui.equals("txt")){
				output = service.parseUploadFileTXT(file, type);
			}
			//修改附件表中的status字段
			output.put("FUND_UPLOAD_ID", request.getParameter("FUND_UPLOAD_ID"));
			service.updateStatus(output);
			try {
				{
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("PATH", file.getAbsolutePath());
					param.put("TYPE", type);
					param.put("NAME", file.getName());
					service.addUploadFile(param);
				}
				System.out.println(output+"-------output----------22-------");
				flag = service.saveViewData(output,HouZhui);
				temp.delete();
			} catch (Exception e) {
				file.delete();
				throw e;
			}
		} catch (Exception e) {
			throw new ActionException("处理失败", e);
		}
		return new ReplyAjax(flag, null);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", name = "QJL", email = "qijl@pqsoft.cn")
	public Reply ExpFundModel() {
		Map<String, Object> param = _getParameters();
		try {
			return new ReplyFile(UTIL.RES.getResource("classpath:file/fund_Upload_template.xls").getInputStream(), "fund_Upload_template.xls");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//页面中显示上传附件信息
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply uploadFileAll_PathCount(){
		Map<String, Object> param = _getParameters();
		param.put("JUDGE_GUARANTOR", "0");
		System.out.println("-------action--------");
		FundUploadService service = new FundUploadService();
		return new ReplyAjaxPage(service.uploadFileAll_Path(param));
	}
	//跳转到附件查询页面
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
    public Reply show(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_upload_filepath.vm", context));
    }
	//根据FUND_UPLOAD_ID查询还款明细
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply fundRepayment(){
		Map<String, Object> param = _getParameters();
		param.put("JUDGE_GUARANTOR", "0");
		System.out.println("-------action--------"+param);
		FundUploadService service = new FundUploadService();
		VelocityContext context = new VelocityContext();
		context.put("list",service.fundRepayment(param));
		return new ReplyHtml(VM.html("fi/payin/fund_repayment.vm", context));
	}
	//ajax判断上传还款文件是否已存在
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply isExist(){
		Map<String,Object> map = _getParameters();
		System.out.println(map.get("name")+"----------name---------");
		File file = new File(SkyEye.getConfig("file.path") + File.separator + "fundupload" + File.separator + map.get("name") );
		if (file.exists()){
			return new ReplyAjax(false, "文件已存在，如未上传过该文件，请重命名后再上传");
		}else {
			return new ReplyAjax(true, "*通过");
		}
	}
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply downLoadFile(){
		Map map = _getParameters();
		System.out.println("map---------"+map);
		return new ReplyFile(new File(map.get("FUND_FILE_PATH") + ""), map.get("FUND_FILE_NAME") + "").addOp(new OpLog("资金管理", "还款录入", "上传", "下载错误"));
	}
	//从数据库解析数据，生成xml文档
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply createXml2(){
		String xmlPath = "D:\\pqsoft\\file\\xml\\from_oracle.xml";
		FundUploadService service = new FundUploadService();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("FUND_UPLOAD_ID", 389);
		map = service.searchById(map);
		//创建根节点root
		Element root = new Element("ROOT");
		//将根节点放入document文档中
		Document doc = new Document(root);
		System.out.println("---------ssss------------");
		//主表记录
		List list = (List) map.get("FI_R_UPLOAD_PATH_COUNT");
		for(int i=0;i<list.size();i++){
			Element element1 = new Element("datas");
			Map<String,Object> map2 = (Map<String, Object>) list.get(i);
			System.out.println(map2+"----ddd-----");
			element1.setAttribute("id",map2.get("FUND_UPLOAD_ID")+"");
			//主表数据
			Element element2 = new Element("main");
			Set set = map2.keySet();
			System.out.println(map2.keySet()+"-------------");
			Iterator iter = set.iterator(); 
			while(iter.hasNext()){
				String str = (String) iter.next();
				System.out.println(set.size()+"--------"+str+"=="+map2.get(str));
				Element element3 = new Element("data");
				element3.setAttribute("name", str);
				element3.setText(map2.get(str)+"");
				element2.addContent(element3);
			}
			element1.addContent(element2);
			//子表数据
			Element element4 = new Element("details");
			//子表记录
			List list2 = (List) map.get("FI_R_UPLOAD_FILE_JOIN");
			for(int m=0;m<list2.size();m++){
				Map<String,Object> map3 = (Map<String, Object>) list2.get(m);
				System.out.println(map3+"------map3------");
				Element element5 = new Element("detail");
				element5.setAttribute("id", map3.get("FUND_ID")+"");
				Set set2 = map3.keySet();
				Iterator iter2 = set2.iterator();
				while(iter2.hasNext()){
					String str2 = (String) iter2.next();
					Element element6 = new Element("data");
					element6.setAttribute("main", str2);
					element6.setText(map3.get(str2)+"");
					element5.addContent(element6);
				}
				element4.addContent(element5);
			}
			element1.addContent(element4);
			root.addContent(element1);
		}
		try {
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(doc, new FileOutputStream(xmlPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 此处 for 循环增加子节点
		//System.out.println(map+"----------------");
		return null;
	}
	//从数据库解析数据，生成xml文档
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply createXml(){
		FundUploadService service = new FundUploadService();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("FUND_UPLOAD_ID", 389);
		map = service.searchById(map);
		//创建根节点root
		Element root = new Element("ROOT");
		//将根节点放入document文档中
		Document doc = new Document(root);
		System.out.println("---------ssss------------");
		//主表记录
		List list = (List) map.get("FI_R_UPLOAD_PATH_COUNT");
		for(int i=0;i<list.size();i++){
			Element element1 = new Element("datas");
			Map<String,Object> map2 = (Map<String, Object>) list.get(i);
			System.out.println(map2+"----ddd-----");
			element1.setAttribute("id",map2.get("FUND_UPLOAD_ID")+"");
			//主表数据
			Element element2 = new Element("main");
			Set set = map2.keySet();
			System.out.println(map2.keySet()+"-------------");
			Iterator iter = set.iterator(); 
			while(iter.hasNext()){
				String str = (String) iter.next();
				System.out.println(set.size()+"--------"+str+"=="+map2.get(str));
				Element element3 = new Element("data");
				element3.setAttribute("name", str);
				element3.setText(map2.get(str)+"");
				element2.addContent(element3);
			}
			element1.addContent(element2);
			//子表数据
			Element element4 = new Element("details");
			//子表记录
			List list2 = (List) map.get("FI_R_UPLOAD_FILE_JOIN");
			for(int m=0;m<list2.size();m++){
				Map<String,Object> map3 = (Map<String, Object>) list2.get(m);
				System.out.println(map3+"------map3------");
				Element element5 = new Element("detail");
				element5.setAttribute("id", map3.get("FUND_ID")+"");
				Set set2 = map3.keySet();
				Iterator iter2 = set2.iterator();
				while(iter2.hasNext()){
					String str2 = (String) iter2.next();
					Element element6 = new Element("data");
					element6.setAttribute("main", str2);
					element6.setText(map3.get(str2)+"");
					element5.addContent(element6);
				}
				element4.addContent(element5);
			}
			element1.addContent(element4);
			root.addContent(element1);
		}
		Format xmlFormat = Format.getCompactFormat();
		xmlFormat.setEncoding("utf-8");
		XMLOutputter outputter = new XMLOutputter(xmlFormat);
	    String ss = outputter.outputString(doc).trim();
		System.out.println(ss+"------String--------");
		return null;
	}
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply parseXml2(){
		FundUploadService service = new FundUploadService();
		String xmlPath = "D:\\pqsoft\\file\\xml\\from_oracle.xml";
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(new FileInputStream(xmlPath));
			Element root = doc.getRootElement();
			List list = root.getChildren();
			List list_main = new ArrayList();
			for(Iterator iter = list.iterator();iter.hasNext();){
				Element element = (Element) iter.next();
				String id = element.getAttributeValue("id");
				System.out.println(id+"-----------------");
				List list2 = element.getChildren();
				for(int i=0;i<list2.size();i++){
					Element ele = (Element)list2.get(i);
					System.out.println(ele.getName()+"---------getName()--------");
					if("main".equals(ele.getName())){
						List list3 =  ele.getChildren();
						Map<String,Object> map_main = new HashMap<String, Object>();
						for(Iterator iter3 = list3.iterator();iter3.hasNext();){
							Element element_3 = (Element)iter3.next();
							String name = element_3.getAttributeValue("name");
							System.out.println(name+"-----------------");
							String text = element_3.getText();
							System.out.println(text+"------------------");
							map_main.put(name, text);
						}
						map_main.put("FUND_UPLOAD_ID",id);
						//插入数据库主表信息
						//service.insertParseXml(map_main);
						System.out.println("------------map_main="+map_main);
					}else if("details".equals(ele.getName())){
						//子表details
						List list4 = ele.getChildren("detail");
						List listMap = new ArrayList();
						for(Iterator iter4 = list4.iterator();iter4.hasNext();){
							Element element_4 = (Element)iter4.next();
							String id2 = element_4.getAttributeValue("id");
							System.out.println(id2+"-------id2-----------");
							List list5 = element_4.getChildren();
							Map<String,Object> map_details = new HashMap<String, Object>();
							for(Iterator iter5 = list5.iterator();iter5.hasNext();){
								Element element5 = (Element)iter5.next();
								String main = element5.getAttributeValue("main");
								String text2 = element5.getText();
								System.out.println(main+"------------------");
								System.out.println(text2+"----------------------");
								map_details.put(main, text2);
							}
							map_details.put("FUND_UPLOAD_ID",id);
							map_details.put("FUND_ID", id2);
							listMap.add(map_details);
						}
						System.out.println("------------listMap="+listMap);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@aPermission(name = { "资金管理", "还款录入", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply parseXml(){
		FundUploadService service = new FundUploadService();
		String xmlPath = "<country><name>中国</name>"
			+"<province><name>黑龙江</name><citys><city>哈尔滨</city><city>大庆</city></citys></province>"
			+"<province><name>广东</name><citys><city>广州</city><city>深圳</city><city>珠海</city></citys></province>"
			+"</country>";
		StringReader read = new StringReader(xmlPath);
		InputSource source = new InputSource(read);
		SAXBuilder build = new SAXBuilder();
		try {
			Document doc = build.build(source);
			Element root = doc.getRootElement();
			System.out.println(root.getName()+"----------root------------");
			List list = (List) root.getChildren("name");
			for(int i=0;i<list.size();i++){
				Element element2 = (Element) list.get(i);
				System.out.println(element2.getText()+"------11-------");
			}
			List list2 = root.getChildren("province");
			for(int j=0;j<list2.size();j++){
				Element element3 = (Element)list2.get(j);
				Element ele1 = element3.getChild("name");
				System.out.println(ele1.getText()+"------22-----");
				Element ele2 = element3.getChild("citys");
				System.out.println(ele2.getName()+"---------33---------");
				List list3 = (List) ele2.getChildren("city");
				for(Iterator iter = list3.iterator();iter.hasNext();){
					Element ele3 = (Element) iter.next();
					System.out.println(ele3.getText()+"---------city-value---------");
				}
			}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@aPermission(name = { "参数配置", "科目对比", "列表页" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply sys_subject(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/Sys_subject.vm", context));
	}
	@aPermission(name = { "参数配置", "科目对比", "列表页" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply checkAll(){
		Map<String,Object> param = _getParameters();
		FundUploadService service = new FundUploadService();
		return new ReplyAjaxPage(service.checkAll(param));
	}
	@aPermission(name = { "参数配置", "科目对比", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply addSub(){
		Map<String,Object> param = _getParameters();
		FundUploadService service = new FundUploadService();
		int num=service.addSubject(param);
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		return new ReplyAjax(flag, "添加成功");
	}
	@aPermission(name = { "参数配置", "科目对比", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply updateSub(){
		Map<String,Object> param = _getParameters();
		FundUploadService service = new FundUploadService();
		int num=service.updateSubject(param);
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		return new ReplyAjax(flag, "修改成功");
	}
	@aPermission(name = { "参数配置", "科目对比", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	public Reply delSub(){
		Map<String,Object> param = _getParameters();
		FundUploadService service = new FundUploadService();
		int num=service.delSubject(param);
		boolean flag=false;
		if(num>0){
			flag=true;
		}
		return new ReplyAjax(flag, "删除成功");
	}
}
