package com.pqsoft.project.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;

import com.esa2000.PfxSignShell;
import com.esa2000.SignShell;
import com.esa2000.pdfsign.util.Log4jLoader;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.project.service.ContractService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ContractAction extends Action {

	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context = new VelocityContext();
	Map<String, Object> m = null;
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	private final String pagePath = "contract/";

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	ContractService service = new ContractService();

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	public Reply contractMain() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "contractMain.vm", context));
	}

	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getDataList() {
		this.getPageParameter();
		List<Object> list = (List) new SysDictionaryMemcached().get("项目状态位");
		context.put("list", list);
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("PContext", m);
		context.put("isDelAuth", Security.hasPermission(new String[] { "项目管理", "合同一览", "项目删除" }));
		return new ReplyHtml(VM.html("project/contract_Manager.vm", context));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.ALL)
	private void getPageParameter() {
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME", Security.getUser().getName());
		m.put("USERCODE", Security.getUser().getCode());
		m.put("USERID", Security.getUser().getId());
	}

	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjax() {
		Map<String, Object> param = com.pqsoft.skyeye.api.Action._getParameters();
		BaseUtil.getDataAllAuth(param);
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getInfo() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		if (!map.containsKey("PROJECT_ID") || StringUtils.isEmpty(map.get("PROJECT_ID") + "") || "null".equals(map.get("PROJECT_ID") + "")) { return new ReplyHtml(
				"<script>alert('没有找到合同模版信息');window.history.go(-1);</script>"); }
		List<Map<String, Object>> TempList = (List<Map<String, Object>>) service.queryContractTemplateList(map);
		// new MaterialMgtService().getFileList("",
		// map.get("PROJECT_ID").toString()) ;
        /*合同模版权限2016年3月31日 14:12:45吴国伟*/
		//String userId = Security.getUser().getId();
		Map<String,Object> orgMap = new HashMap<String,Object>();
		orgMap.put("ORG_ID", Security.getUser().getOrg().getId());//获取当前用户对应的组织架构 ID
		// 根据用户ID获取对应分公司的模板和印章权限
		Map<String,Object> template_permissions = service.queryOrgDataByIdMb(orgMap);
		
		List<Map<String, Object>> contractTempList = new ArrayList<Map<String,Object>>();
		if(null != template_permissions){
			String tempPerStr = template_permissions.get("TEMPLATE_PERMISSIONS").toString();
			String[] tempArray = tempPerStr.split(",");
			for(Map<String,Object> tempMap:TempList){
				String F_NAME= tempMap.get("FILE_NAME").toString();
				for(int i=0;i<tempArray.length;i++){
					if(tempArray[i].equals(F_NAME)){
						contractTempList.add(tempMap);
						break;
					}
				}
				
			}
		}
		
		
		Map baseInfoMap = service.baseInfoMap(map);
		map.putAll(baseInfoMap);
		context.put("map", map);
		context.put("contractTempList", contractTempList);
		List<Map<String,Object>> history=service.gethistoryContract(map);
		context.put("history", history);
		/** -- yipan modified 2016-02-01 14:29:44 start -- **/
		Map<String, Object> rsPro = Dao.selectOne("buyCertificate.getRepaymentScheduleProject", map);
		context.put("rsPro", rsPro);
		/** -- yipan modified 2016-02-01 14:29:44 end -- **/
		
		return new ReplyHtml(VM.html("contract/ContractInfo.vm", context));
	}
	
	@aPermission(name = { "合同管理", "电子合同导出", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply getInfo_New() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		if (!map.containsKey("PROJECT_ID") || StringUtils.isEmpty(map.get("PROJECT_ID") + "") || "null".equals(map.get("PROJECT_ID") + "")) { return new ReplyHtml(
				"<script>alert('没有找到合同模版信息');window.history.go(-1);</script>"); }
		List<Map<String, Object>> TempList = (List<Map<String, Object>>) service.queryContractTemplateList(map);
		// new MaterialMgtService().getFileList("",
		// map.get("PROJECT_ID").toString()) ;
        /*合同模版权限2016年3月31日 14:12:45吴国伟*/
		//String userId = Security.getUser().getId();
		Map<String,Object> orgMap = new HashMap<String,Object>();
		orgMap.put("ORG_ID", Security.getUser().getOrg().getId());//获取当前用户对应的组织架构 ID
		// 根据用户ID获取对应分公司的模板和印章权限
		Map<String,Object> template_permissions = service.queryOrgDataByIdMb(orgMap);
		
		List<Map<String, Object>> contractTempList = new ArrayList<Map<String,Object>>();
		if(null != template_permissions){
			String tempPerStr = template_permissions.get("TEMPLATE_PERMISSIONS").toString();
			String[] tempArray = tempPerStr.split(",");
			for(Map<String,Object> tempMap:TempList){
				String F_NAME= tempMap.get("FILE_NAME").toString();
				for(int i=0;i<tempArray.length;i++){
					if(tempArray[i].equals(F_NAME)){
						contractTempList.add(tempMap);
						break;
					}
				}
				
			}
		}
		
		
		Map baseInfoMap = service.baseInfoMap(map);
		map.putAll(baseInfoMap);
		context.put("map", map);
		context.put("contractTempList", contractTempList);
		List<Map<String,Object>> history=service.gethistoryContract(map);
		context.put("history", history);
		/** -- yipan modified 2016-02-01 14:29:44 start -- **/
		Map<String, Object> rsPro = Dao.selectOne("buyCertificate.getRepaymentScheduleProject", map);
		context.put("rsPro", rsPro);
		/** -- yipan modified 2016-02-01 14:29:44 end -- **/
		
		return new ReplyHtml(VM.html("contract/ContractInfo_New.vm", context));
	}

	
	@aPermission(name = { "合同管理", "电子合同导出", "列表显示", "历史下载" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "xuguangming", email = "xugm@pqsoft.com", name = "xgm")
	public ReplyFile downPdfTemplate() {
		Map<String, Object> param = _getParameters();
		String pdfPath = param.get("PATH").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		return new ReplyFile(file, fileName);
	}
	/**
	 * 导出合同pdf文件公用方法
	 * 
	 * @throws Exception
	 * @author:King 2012-4-6
	 */
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "合同管理", "电子合同导出", "电子合同-导出" })
	@aAuth(type = aAuthType.USER)
	public Reply ExpContractFile() {
		Map<String, Object> map = _getParameters();
		System.out.println(map.get("PROJECT_ID"));
		// 文件类型id 用于匹配查找对应的表单sql
		String TPM_ID = map.get("TPM_ID") + "";
		// pdf文件的路径
		String path = map.get("FILE_PATH") + "";
		if (StringUtils.isBlank(path)) { throw new ActionException("没有找到指定的合同模板"); }
		String name = null == map.get("FILE_NAME") ? UUID.randomUUID() + ".pdf" : map.get("FILE_NAME") + ".pdf";
		String outputPath = service.ExpContractFile(TPM_ID, map, path, name);
		File outputFile = new File(outputPath);
		String Time=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		Map<String, Object> param=Dao.selectOne("bpm.status.getEmail",map);
		int num=Integer.parseInt(param.get("SEND_NUM").toString())+1;
		String targetPath = outputFile.getParent() + File.separator + Time + num + outputFile.getName();
		// del gengchangbao jzzl-122  换另一个印章 start
		//Map<String, Object> m = service.getElectronicSignature(map);
		//String text = m.get("TPM_SEAL_SERVICE").toString();
		//String root = SkyEye.getConfig("file.path");
		//String imgPath = root + File.separator + "baseRes" + File.separator + m.get("TPM_SIGNATURE_LOGO") + ".gif";
		// del gengchangbao jzzl-122  换另一个印章 end
		try {
			// 获取角色ID 
			//String userId = Security.getUser().getId();
			Map<String,Object> orgMap = new HashMap<String,Object>();
			//mod gengchangbao 2016年4月18日15:26:02      分公司相关的数据取得     start
			//orgMap.put("USER_ID",  userId);
			orgMap.put("ORG_ID",  Security.getUser().getOrg().getId());//获取当前用户对应的组织架构 ID
			//mod gengchangbao 2016年4月18日15:26:02      分公司相关的数据取得     end
			// 根据用户ID获取对应分公司的模板和印章权限
			List<Map<String,Object>> orgDataList = service.queryOrgDataById(orgMap);
			Map<String,Object> orgData = null;
			String templateType = ""; //模版类型
			String pfxSealPath = "";//签约主体
			String tpmSealService = "";//签章位置
			if (orgDataList != null && orgDataList.size() > 0) {
				for (int i = 0; i < orgDataList.size(); i++) {
					orgData = orgDataList.get(i);
					// 获取签约主体【印章信息】
					if (orgData != null && orgData.get("SEAL_PATH") != null 
							&& !"".equals(orgData.get("SEAL_PATH")) 
							&& orgData.get("TEMPLATE_TYPE") != null 
								&& !"".equals(orgData.get("TEMPLATE_TYPE"))) {
						//获取印章类型
						pfxSealPath = orgData.get("SEAL_PATH").toString();
						//获取需要签约印章的合同模板
						templateType = orgData.get("TEMPLATE_TYPE").toString();
						break;
					}
				}
			}
//			// 获取签约主体【印章信息】
//			if (orgData != null && orgData.get("SEAL_PATH") != null 
//					&& !"".equals(orgData.get("SEAL_PATH")) 
//					&& orgData.get("TEMPLATE_TYPE") != null 
//						&& !"".equals(orgData.get("TEMPLATE_TYPE"))) {
//				//获取印章类型
//				pfxSealPath = orgData.get("SEAL_PATH").toString();
//				//获取需要签约印章的合同模板
//				templateType = orgData.get("TEMPLATE_TYPE").toString();
//			}
			
			//if (templateType != null && templateType.equals(map.get("FILE_NAME")) 模块包含多个  2016年3月30日 13:09:14 吴国伟修改
			if(templateType != null && (templateType.indexOf(map.get("FILE_NAME").toString())!=-1)
					&& map.get("TPM_SEAL_SERVICE") != null 
					&& !"".equals(map.get("TPM_SEAL_SERVICE"))) {
				tpmSealService =  map.get("TPM_SEAL_SERVICE").toString(); 
				 // 获取印章位置
				  String[] tpm = tpmSealService.split(",");
				  if (tpm.length > 2) {
					  Log4jLoader.loadLog4j();  
					  PfxSignShell signShell = new PfxSignShell();
					  signShell.loadLic("/pqsoft/dat/azt.lic");
					  signShell.init(outputPath, targetPath, true);
					  signShell.initSoftSeal(pfxSealPath);
					  //通过坐标定位盖章
					  signShell.addSeal(Integer.valueOf(tpm[0]), Integer.valueOf(tpm[1]), Integer.valueOf(tpm[2]));
					  signShell.sign();
					  signShell.close();
				  }
				 
			}
			//add JZZL-151 gengchangbao 2016年4月12日 Start
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) service.queryContractSealInfoList(map);
			if (tempList != null && tempList.size() > 0) {
				Map<String, Object> sealInfo = null;
				String tempTargetPath = "";
				for (int i = 0; i < tempList.size(); i++) {
					sealInfo = tempList.get(i);
					pfxSealPath = (String)sealInfo.get("SEAL_PATH");
					tpmSealService = (String)sealInfo.get("TPM_SEAL_SERVICE");
					 String[] tpm = tpmSealService.split(",");
					if (pfxSealPath != null && !"".equals(pfxSealPath)) {
						 if (tpm.length > 2) {
							  Log4jLoader.loadLog4j();  
							  PfxSignShell signShell = new PfxSignShell();
							  signShell.loadLic("/pqsoft/dat/azt.lic");
							  if (!new File(targetPath).exists()) {
								  signShell.init(outputPath, targetPath, true);
								} else {
								  tempTargetPath = outputFile.getParent() + File.separator + Time + ++num + outputFile.getName();
								  signShell.init(targetPath, tempTargetPath, true);
								  targetPath = tempTargetPath;
								}
							 
							  signShell.initSoftSeal(pfxSealPath);
							  //通过坐标定位盖章
							  signShell.addSeal(Integer.valueOf(tpm[0]), Integer.valueOf(tpm[1]), Integer.valueOf(tpm[2]));
							  signShell.sign();
							  signShell.close();
						  }
					}
				}
			}
			//add JZZL-151 gengchangbao 2016年4月12日 End
			// del gengchangbao jzzl-122  换另一个印章 start
//			OASClient.execPdf(outputPath, targetPath, text, imgPath);
			Map<String, Object> hisMap  = new HashMap<String, Object>();
			if (!new File(targetPath).exists()) {
				hisMap.put("NAME", outputFile.getName());
				hisMap.put("PATH", outputPath);
			} else {
				hisMap.put("NAME", Time +num+ outputFile.getName());
				hisMap.put("PATH", targetPath);
			}
			hisMap.put("TYPE", name);
			hisMap.put("PROJECT_ID",map.get("PROJECT_ID"));
//			
			Dao.insert("bpm.status.insHistoryContract",hisMap);
			map.put("SEND_NUM", num);
			Dao.update("weixin_view.UpdProjecctEmail",map);
			// del gengchangbao jzzl-122  换另一个印章 end
		} catch (Exception e) {
//************************************** 2015-08-04   fuyulong 无视签章失败**************************************
			throw new ActionException("添加签章失败", e);  
//************************************** 2015-08-04   fuyulong 无视签章失败**************************************
		}
		if (!new File(targetPath).exists()) targetPath = outputPath;
		Map<String,Object> version = service.getContractVersion(map);
		
		/** -- yipan modified 2016-01-22 13:04:28 start -- **/
		if(null != version){
			Integer v_num=Integer.parseInt(String.valueOf(version.get("CONTRACT_VERSION")));
			if("0".equals(String.valueOf(v_num)) && v_num!=null){
				v_num++;
				int a=v_num;
				map.put("VERSION",a);
				service.changeVersionNum(map);
				map.put("CON_VERSION", v_num);
			}else{
				Integer versionNum =Integer.parseInt(String.valueOf(version.get("CONTRACT_VERSION")));
				versionNum++;
				int a=versionNum;
				map.put("VERSION",a);
				service.changeVersionNum(map);
				map.put("CON_VERSION", versionNum);
			}
		}
		/** -- yipan modified 2016-01-22 13:04:28 end -- **/
		
		return new ReplyFile(new File(targetPath), name);
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	// @aPermission(name = { "项目管理", "评审会议纪要","导出" })
	@aAuth(type = aAuthType.USER)
	public Reply expCreditWindInfo() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		Map contractTempMap = service.queryContractTemplateMap(map);
		if (contractTempMap == null) {
			throw new ActionException("没有找到指定的模板");
		} else {
			String TPM_ID = contractTempMap.get("TPM_ID") + "";
			String path = contractTempMap.get("FILE_PATH") + "";
			if (StringUtils.isBlank(path)) { throw new ActionException("没有找到指定的模板"); }

			String name = null == contractTempMap.get("FILE_NAME") ? UUID.randomUUID() + ".pdf" : contractTempMap.get("FILE_NAME") + ".pdf";
			String outputPath = service.ExpContractFile(TPM_ID, contractTempMap, path, name);
			return new ReplyFile(new File(outputPath), name);
		}

	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	 @aPermission(name = { "项目管理", "评审会议纪要" })
	@aAuth(type = aAuthType.USER)
	public Reply expCreditWindInfoPDF() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println(map+"===================");
		List<Map<String,Object>> contractTempList = service.queryContractTemplateList1(map);
		Map<String,Object> contractTempMap=contractTempList.get(0);
		contractTempMap.put("PROJECT_ID", map.get("PROJECT_ID"));
		contractTempMap.put("CLIENT_ID", map.get("CLIENT_ID"));
		if (contractTempMap == null) {
			throw new ActionException("没有找到指定的模板");
		} else {
			String TPM_ID = contractTempMap.get("TPM_ID") + "";
			String path = contractTempMap.get("TEMPLATE_PATH") + "";
			if (StringUtils.isBlank(path)) { throw new ActionException("没有找到指定的模板"); }

			String name = null == contractTempMap.get("FILE_NAME") ? UUID.randomUUID() + ".pdf" : contractTempMap.get("FILE_NAME") + ".pdf";
			String outputPath = service.ExpContractFile(TPM_ID, contractTempMap, path, name);
			return new ReplyFile(new File(outputPath), name);
		}

	}
	
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "合同管理", "电子合同导出", "电子合同-打包下载" })
	@aAuth(type = aAuthType.USER)
	public Reply downFilesZip() {
		Map<String, Object> map = _getParameters();
		String fileparam = map.get("filess").toString();
		JSONArray jsonArray = JSONArray.fromObject(fileparam);
		JSONObject jsonObject = new JSONObject();
		ArrayList<Map<String, Object>> allfiles = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			Map<String, Object> jsonObject2 = (Map<String, Object>) jsonObject;
			Map<String, Object> obj = jsonObject2;
			allfiles.add(obj);

		}
		String tmpFileName = "contract.zip";
		byte[] buffer = new byte[1024];
		String filedirpath = SkyEye.getConfig("file.path");
		filedirpath = filedirpath == null ? "/pqsoft/file" : filedirpath.toString();
		String strZipPath = filedirpath + File.pathSeparator +UUID.randomUUID() + tmpFileName;
		// 需要同时下载的两个文件result.txt ，source.txt
		File[] file1 = new File[allfiles.size()];
		// String zipname = "contract";
		for (int j = 0; j < allfiles.size(); j++) {

			String str = ((Map) allfiles.get(j)).get("pdfTempId") + "";
			String path = ((Map) allfiles.get(j)).get("tempPath") + "";
			String fileName = ((Map) allfiles.get(j)).get("FILE_NAME") + "";
			String root = SkyEye.getConfig("file.path.temp");

			if (fileName.contains(".pdf") || fileName.contains(".PDF")) {

			} else {
				fileName = fileName + ".pdf";
			}
			String pdfWrite = root + File.separator + fileName;

			file1[j] = new File(pdfWrite);

		}
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			for (int i = 0; i < file1.length; i++) {
				try {
					if (!file1[i].exists()) {

						Map<String, Object> tempMap = allfiles.get(i);
						if (tempMap == null) {
							tempMap = new HashMap();
						}
						tempMap.put("PROJECT_ID", map.get("PROJECT_ID"));
						tempMap.put("CLIENT_ID", map.get("CLIENT_ID"));

						// 文件类型id 用于匹配查找对应的表单sql
						String TPM_ID = tempMap.get("pdfTempId") + "";
						// pdf文件的路径
						String path = tempMap.get("tempPath") + "";
						if (StringUtils.isBlank(path)) { throw new ActionException("没有找到指定的合同模板"); }
						String name = null == tempMap.get("FILE_NAME") ? UUID.randomUUID() + ".pdf" : tempMap.get("FILE_NAME") + ".pdf";
						service.ExpContractFile(TPM_ID, tempMap, path, name);
					}
					FileInputStream fis = new FileInputStream(file1[i]);
					out.putNextEntry(new ZipEntry(file1[i].getName()));
					// 设置压缩文件内的字符编码，不然会变成乱码
					out.setEncoding("GBK");
					int len;
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}

					out.closeEntry();
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("文件不存在");
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyFile(new File(strZipPath), tmpFileName);
	}

	// 还款明细导出导出
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "合同管理", "电子合同导出", "电子合同-导出还款明细" })
	@aAuth(type = aAuthType.USER)
	public Reply uploadPayDetail() {
		Map<String, Object> param = _getParameters();
		service.uploadPayDetail(param);
		return null;
	}

	@aPermission(name = { "项目管理", "项目一览", "核心要件", "主页" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply contractAttachment() {
		Map<String, Object> map = _getParameters();
		Map baseInfoMap = service.baseInfoMap(map);
		map.putAll(baseInfoMap);
		context.put("map", map);

		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
		// 支付表
		// 先查询还没有建立支付表的设备
		context.put("listPay", service.queryPayListByNotEq(map));// 支付表未完成
//		if (PLATFORM_TYPE.equals("11")) {
//			context.put("listPayDXMED", service.queryDXMPayList(map));// 已生成支付表
//		} else {
			context.put("listPayQZING", service.queryPayListByQZINGEq(map));// 支付表起租中
			context.put("listPayQZED", service.queryPayListQZByEq(map));// 已起租
//		}

		// 买卖合同
		context.put("buyNotCreate", service.queryPayNotBuyCreate(map));// 买卖合同未完成
		context.put("invoiceAppNotCreate", service.queryPayNotInvoiceAppCreate(map));
		context.put("listBuyEd", service.queryPayListBuy(map));// 买卖合同已新建
		context.put("listInvoiceApp", service.queryInvoiceAppList(map));

		context.put("map", map);
		return new ReplyHtml(VM.html("contract/contractAttachment.vm", context));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "项目管理", "项目一览", "核心要件", "买卖合同/开票协议", "新建" })
	@aAuth(type = aAuthType.LOGIN)
	public Reply selectPayplanById() {
		Map<String, Object> map = _getParameters();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String type = map.get("TYPE").toString();
		List<Map<String, Object>> listBuyContract = service.queryPayNotBuyCreate(map);
		List<Map<String, Object>> listInvoiceApp = service.queryPayNotInvoiceAppCreate(map);
		if ("invoiceApplication".equals(type)) list = listInvoiceApp;
		if ("buyContract".equals(type)) list = listBuyContract;

		return new ReplyJson(JSONArray.fromObject(list));
	}
}
