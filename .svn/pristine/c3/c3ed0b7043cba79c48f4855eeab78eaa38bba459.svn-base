package com.pqsoft.project.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.common.Logger;
import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.buyBack.action.ExcelTest;
import com.pqsoft.buyBack.service.BuyBackService;
import com.pqsoft.crm.action.CustomerAction;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 项目中合同模板管理
 * 
 * @author King 2013-9-29
 */
public class ProjectContractManagerAction extends Action {
	private String path = "project/";
	private ProjectContractManagerService service = new ProjectContractManagerService();
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Reply execute() {
		return null;
	}

	// ***************************************************************************************************************

	/**
	 * 流程中选择归档文件页面--------留购-----租金变更-----正常回购-----分期回购----资料补齐-----资料变更
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doShowRetentionData() {
		Map<String, Object> param = _getParameters();
		String LC_TYPE = "";
		if (param.get("LC_TYPE").equals("LG")) {
			LC_TYPE = "留购";
		} else if (param.get("LC_TYPE").equals("ZCHG")) {
			LC_TYPE = "正常回购";
		} else if (param.get("LC_TYPE").equals("FQHG")) {
			LC_TYPE = "分期回购";
		} else if (param.get("LC_TYPE").equals("ZJBG")) {
			LC_TYPE = "租金变更";
		} else if (param.get("LC_TYPE").equals("ZLBQ")) {
			LC_TYPE = "资料补齐";
		} else if (param.get("LC_TYPE").equals("ZLBG")) {
			LC_TYPE = "资料变更";
		}
		VelocityContext context = new VelocityContext();
		param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID").toString()));
		param.put("CLIENT_ID", param.get("CLIENTID"));
		context.put("param", param);
		context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE").toString(), LC_TYPE, null));
		// if(param.get("LC_TYPE").equals("LG")){
		// return new ReplyHtml(VM.html(path + "projectContractManagerLG.vm",
		// context));
		// }else{
		return new ReplyHtml(VM.html(path + "projectContractManager.vm", context));
		// }
	}

	/**
	 * 查询项目中选中的合同列表-----留购-----租金变更-----正常回购-----分期回购----资料补齐-----资料变更
	 * 
	 * @return
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doShowProjectContractListData() {
		Map<String, Object> param = _getParameters();
		String LC_TYPE = "";
		if (param.get("LC_TYPE").equals("LG")) {
			LC_TYPE = "留购";
		} else if (param.get("LC_TYPE").equals("ZCHG")) {
			LC_TYPE = "正常回购";
		} else if (param.get("LC_TYPE").equals("FQHG")) {
			LC_TYPE = "分期回购";
		} else if (param.get("LC_TYPE").equals("ZJBG")) {
			LC_TYPE = "租金变更";
		} else if (param.get("LC_TYPE").equals("ZLBQ")) {
			LC_TYPE = "资料补齐";
		} else if (param.get("LC_TYPE").equals("ZLBG")) {
			LC_TYPE = "资料变更";
		}
		VelocityContext context = new VelocityContext();
		String CLIENT_ID = null;
		if (param.containsKey("CLIENT_ID")) {
			CLIENT_ID = param.get("CLIENT_ID") + "";
		}
		List<Map<String, Object>> list = service.doShowProjectContractList(param.get("PROJECT_ID") + "", null, null, CLIENT_ID, null, LC_TYPE);
		if (!list.isEmpty() && list.size() > 0) context.put("FILELIST", list);
		else {
			param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID") + ""));
			context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", LC_TYPE, null));
		}
		context.put("param", param);
		if (param.get("LC_TYPE").equals("ZCHG") || param.get("LC_TYPE").equals("FQHG")) {
			return new ReplyHtml(VM.html(path + "projectContractForJbpmRepo.vm", context));
		} else {
			return new ReplyHtml(VM.html(path + "projectContractForJbpm.vm", context));
		}
	}

	/**
	 * 保存项目合同列表--------留购-----租金变更-----正常回购-----分期回购----资料补齐-----资料变更
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddCheckedContractData() {
		Map<String, Object> param = _getParameters();
		PigeonholeService pService = new PigeonholeService();
		pService.doDelPigeonholeApplyInfo(param);
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
			m.put("DOSSIER_APPLY_ID", APPLY_ID);
			JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);
		} catch (Exception e) {
			throw new ActionException("归档申请失败", e);
		}
		if (param.containsKey("FILEINFO")) {
			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
			int i = 0;
			service.doDelContractByProjectId(list.get(0));
			String LC_TYPE = "";
			for (Map<String, Object> map : list) {
				if (param.get("LC_TYPE").equals("LG")) {
					LC_TYPE = "留购";
				} else if (param.get("LC_TYPE").equals("ZCHG")) {
					LC_TYPE = "正常回购";
				} else if (param.get("LC_TYPE").equals("FQHG")) {
					LC_TYPE = "分期回购";
				} else if (param.get("LC_TYPE").equals("ZJBG")) {
					LC_TYPE = "租金变更";
				} else if (param.get("LC_TYPE").equals("ZLBQ")) {
					LC_TYPE = "资料补齐";
				} else if (param.get("LC_TYPE").equals("ZLBG")) {
					LC_TYPE = "资料变更";
				}
				map.put("TPM_BUSINESS_PLATE", LC_TYPE);
				i += service.doAddCheckedContract(map);
			}
			if (i == list.size()) return new ReplyAjax(m).addOp(new OpLog("合同文件列表", "保存", "项目id为" + list.get(0).get("PROJECT_ID")));
			else throw new ActionException("保存失败");
		} else {
			return new ReplyAjax(false, "没有索引到需要的数据");
		}
	}

	/**
	 * 适用回购流程
	 * 生成合同excel文件方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PAYLIST_CODE 支付表编号
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply ExcelFile() {
		BuyBackService service1 = new BuyBackService();
		Map<String, Object> param = _getParameters();
		// param.put("PAYLIST_CODE", "SFPDJT130408-1");
		// param.put("TPM_ID", "77");
		param.putAll(service1.selectTpmPath(param));// 查询模版path
		if (StringUtils.isBlank(param.get("FILE_PATH").toString())) { throw new ActionException("没有找到指定的合同模板"); }
		param.putAll(service1.selectRepoData(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次

			if (param.containsKey("PENALTY_RECE") && !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE").toString());
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额

			if (param.containsKey("LEASE_TOPRIC") && !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC").toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值

			if (param.containsKey("BEGINNING_PAID") && !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID").toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金

			if (param.containsKey("START_ZJ") && !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金

			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计

			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计

			if (param.containsKey("DEPOSIT") && !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			param.put("DEPOSIT", DEPOSIT);// 保证金

			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价

			YS_ZJ_ZE = ZJ_ZJ + DEPOSIT + LEAVE;
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额

			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额

			if (param.containsKey("RENT_RECE") && !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE").toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金

			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计

			if (param.containsKey("UNPAID_INTEREST") && !param.get("UNPAID_INTEREST").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get("UNPAID_INTEREST").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息

			HJ = SY_ZJ_ZE + RENT_RECE + LEAVE + QT_FY - UNPAID_INTEREST;
			param.put("HJ", HJ);// 合计

			if (param.containsKey("BEGINNING_NUM") && !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM").toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次
		}
		return new ExcelTest(param);
	}

	// ***************************************************************************************************************

	/**
	 * 进入项目合同列表页面
	 * PROJECT_ID
	 * 
	 * @return
	 * @author:King 2013-9-29
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toMgProjectContract() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID") + ""));
		context.put("param", param);
		context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", "立项", null));
		// context.put("FILELIST",
		// service.doShowContractListByProjectId(param.get("PROJECT_ID") + ""));
		return new ReplyHtml(VM.html(path + "projectContractManager.vm", context));
	}

	/**
	 * 保存项目合同列表
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doAddCheckedContract() {
		Map<String, Object> param = _getParameters();
		PigeonholeService pService = new PigeonholeService();
		pService.doDelPigeonholeApplyInfo(param);
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			String APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
			m.put("DOSSIER_APPLY_ID", APPLY_ID);
			JBPM.setExecutionVariable(param.get("JBPM_ID") + "", m);
		} catch (Exception e) {
			throw new ActionException("归档申请失败", e);
		}
		if (param.containsKey("FILEINFO")) {
			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
			int i = 0;
			service.doDelContractByProjectId(list.get(0));
			for (Map<String, Object> map : list) {
				map.put("TPM_BUSINESS_PLATE", "立项");
				i += service.doAddCheckedContract(map);
			}
			if (i == list.size()) return new ReplyAjax(m).addOp(new OpLog("合同文件列表", "保存", "项目id为" + list.get(0).get("PROJECT_ID")));
			else throw new ActionException("保存失败");
		} else {
			return new ReplyAjax(false, "没有索引到需要的数据");
		}
	}

	/**
	 * 下载合同模板
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doDownContract() {
		Map<String, Object> param = _getParameters();
		String FILE_NAME = null == param.get("NAME") ? null : param.get("NAME") + "";
		List<Map<String, Object>> list = service.doShowContractListByProjectId(param.get("PROJECT_ID") + "", param.get("CODE") + "", null, FILE_NAME);
		if (list != null && list.size() > 0) {
			param.putAll(list.get(0));
			return new ReplyFile(new File(param.get("FILE_PATH") + ""),
					-1 == (param.get("DOWNFILENAME") + "").lastIndexOf(".") ? param.get("DOWNFILENAME") + ""
							+ (param.get("DOWNFILENAME") + "").substring((param.get("DOWNFILENAME") + "").lastIndexOf(".")) : param
							.get("DOWNFILENAME") + "");
		} else {
			return new ReplyAjax(false, "没有索引到文件模板");
			//throw new ActionException("没有索引到文件模板");
		}
	}

	/**
	 * 下载项目资料文件
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doDownProjectFile() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = service.doShowProjectContractList(param.get("PROJECT_ID") + "", "0", null, null, param.get("FILE_ID") + "",
				null);
		// List<Map<String,Object>>
		// list=service.doShowContractListByProjectId(param.get("PROJECT_ID")+"",param.get("CODE")+"");
		if (list != null && list.size() > 0) {
			param.putAll(list.get(0));
			return new ReplyFile(new File(param.get("PDF_PATH") + ""), -1 == (param.get("NAME") + "").lastIndexOf(".") ? param.get("NAME")
					+ (param.get("PDF_PATH") + "").substring((param.get("PDF_PATH") + "").lastIndexOf(".")) : param.get("NAME") + "");
		} else throw new ActionException("没有索引到文件模板");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doDownProjectFile2() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = service.doShowProjectContractList(param.get("PROJECT_ID") + "", null, null, null, param.get("FILE_ID") + "",
				null);
		if (list != null && list.size() > 0) {
			param.putAll(list.get(0));
			return new ReplyFile(new File(param.get("PDF_PATH") + ""), -1 == (param.get("NAME") + "").lastIndexOf(".") ? param.get("NAME")
					+ (param.get("PDF_PATH") + "").substring((param.get("PDF_PATH") + "").lastIndexOf(".")) : param.get("NAME") + "");
		} else throw new ActionException("没有索引到文件模板");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doDownProjectFile3() {
		String id = SkyEye.getRequest().getParameter("id");
		Map<String, Object> m = service.getFilePic(id);
		return new ReplyFile(new File(m.get("PATH") + ""), (String) m.get("NAME"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lc")
	public Reply batDownByFileId() {
		ProjectContractManagerService service = new ProjectContractManagerService();
		String fileIds = SkyEye.getRequest().getParameter("fileIds");
		if (fileIds == null) return null;
		ZipOutputStream out = null;
		byte[] buffer = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			out = new ZipOutputStream(baos);
			for (String fileId : fileIds.split("[,，;；]")) {
				if (fileId == null || "".equals(fileId)) continue;
				Map<String, Object> m = service.getProjectFileById(fileId);
				if (m == null) continue;
				String path = (String) m.get("PDF_PATH");
				if (path == null || "".equals(path)) continue;
				File f = new File(path);
				if (!f.exists()) throw new ActionException("文件不存在，请联系管理员:" + f.getPath());
				// String name = (String) m.get("NAME");
				out.putNextEntry(new ZipEntry(f.getName()));
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
		return new ReplyFile(baos.toByteArray(), "资料附件.zip");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lc")
	public Reply batDownByPicId() {
		ProjectContractManagerService service = new ProjectContractManagerService();
		String fileIds = SkyEye.getRequest().getParameter("fileIds");
		if (fileIds == null) return null;
		ZipOutputStream out = null;
		byte[] buffer = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			out = new ZipOutputStream(baos);
			for (String fileId : fileIds.split("[,，;；]")) {
				try {
					if (fileId == null || "".equals(fileId)) continue;
					Map<String, Object> m = service.getFilePic(fileId);
					if (m == null) continue;
					String path = (String) m.get("PATH");
					if (path == null || "".equals(path)) continue;
					File f = new File(path);
					if (!f.exists()) throw new ActionException("文件不存在，请联系管理员:" + f.getPath());
					// String name = (String) m.get("NAME");
					out.putNextEntry(new ZipEntry(f.getName()));
					out.setEncoding("GBK");
					FileInputStream fis = new FileInputStream(f);
					int len;
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					fis.close();
					out.closeEntry();
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ReplyFile(baos.toByteArray(), "资料附件.zip");
	}

	/**
	 * 查询项目中选中的合同列表
	 * 
	 * @return
	 * @author:King 2013-10-9
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doShowProjectContractList() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String CLIENT_ID = null;
		if (param.containsKey("CLIENT_ID")) {
			CLIENT_ID = param.get("CLIENT_ID") + "";
		}
		// List<Map<String, Object>> list =
		// service.doShowProjectContractList(param.get("PROJECT_ID") + "", null,
		// null, CLIENT_ID, null, "立项");
		// if (!list.isEmpty() && list.size() > 0) context.put("FILELIST",
		// list);
		// else {
		param.putAll(service.doShowClientTypeByProjectId(param.get("PROJECT_ID") + ""));
		context.put("param", param);
		context.put("FILELIST", service.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", "立项", null));
		// }
		return new ReplyHtml(VM.html(path + "projectContractForJbpm.vm", context));
	}

	/**
	 * 查询项目资料---立项时的项目资料
	 * 
	 * @return
	 * @author:King 2013-10-21
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doShowElectronicPhotoAlbum1() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String CLIENT_ID = null;
		if (param.containsKey("CLIENT_ID")) {
			CLIENT_ID = param.get("CLIENT_ID") + "";
		}
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractList(param.get("PROJECT_ID") + "", "0", null, CLIENT_ID, null, "立项"));
		return new ReplyHtml(VM.html(path + "projectAppendixForJbpm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply doUpdateElectronicPhotoAlbum1() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String CLIENT_ID = null;
		if (param.containsKey("CLIENT_ID")) {
			CLIENT_ID = param.get("CLIENT_ID") + "";
		}
		param.put("CUSTOMER_TYPE", service.getCustType(CLIENT_ID));
		List<Map<String, Object>> list = service.getCreditFileListSrc(param);
		context.put("FILE_LIST", list);
		context.put("param", param);
		context.put("securityUser", Security.getUser().getCode());
		context.put("FILELIST", service.doShowProjectContractList(param.get("PROJECT_ID") + "", "0", null, CLIENT_ID, null, "立项"));
		return new ReplyHtml(VM.html(path + "projectAppendixForJbpmUp.vm", context));
	}

	/**
	 * DB确认租赁物和DB上传起租要件 需要开 其他文件 上传
	 * 
	 * @return
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply doUpdateElectronicPhotoAlbum2() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String CLIENT_ID = null;
		if (param.containsKey("CLIENT_ID")) {
			CLIENT_ID = param.get("CLIENT_ID") + "";
		}
		param.put("CUSTOMER_TYPE", service.getCustType(CLIENT_ID));
		List<Map<String, Object>> listXMZLFJ = new DataDictionaryMemcached().get("项目资料附件");
		if (listXMZLFJ != null && listXMZLFJ.size() > 0) {
			for (Map<String, Object> map : listXMZLFJ) {
				if (map.get("REMARK") != null) map.put("REMARK", map.get("REMARK").toString().replaceAll(" ", ""));
			}
		}
		context.put("FILE_LIST1", listXMZLFJ);
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractList(param.get("PROJECT_ID") + "", "0", null, CLIENT_ID, null, "立项"));
		return new ReplyHtml(VM.html(path + "projectAppendixForJbpmUp2.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply toContractFile() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractListDossier2(param.get("PROJECT_ID") + "", "立项合同"));
		return new ReplyHtml(VM.html(path + "contractFile.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply toContractFileAB() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractListDossier2(param.get("PROJECT_ID") + "", "A-B审批"));
		return new ReplyHtml(VM.html(path + "contractFile.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply toContractFileLG() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractListDossier2(param.get("PROJECT_ID") + "", "项目留购"));
		return new ReplyHtml(VM.html(path + "contractFile.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply toContractFileBG() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("FILELIST", service.doShowProjectContractListDossier2(param.get("PROJECT_ID") + "", "变更资料"));
		return new ReplyHtml(VM.html(path + "contractFile.vm", context));
	}

	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply doUploadProjectFile() {
		Map<String, Object> map = _getParameters();
		List<FileItem> list = _getFileItem();
		if (map == null || map.isEmpty()) {
			try {
				map = new CustomerAction().getPostParams(list);
			} catch (Exception e) {
				e.printStackTrace();
				new ActionException("上传文件失败");
			}
		}
		String filedirpath = SkyEye.getConfig("file.path");
		filedirpath = filedirpath == null ? "/pqsoft/file" : filedirpath.toString();
		File dir = new File(filedirpath);
		{
			dir.mkdirs();
		}
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				String fileName = _getFileName(fileItem.getName());
				if (fileName == null || "".equals(fileName)) continue;
				Map<String, Object> param = new HashMap<String, Object>(map);
				param.put("NAME", fileName);
				File file = new File(filedirpath + File.separator + System.currentTimeMillis() + fileName);
				try {
					FileCopyUtils.copy(FileCopyUtils.copyToByteArray(fileItem.getInputStream()), file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (file != null) {
					param.put("PDF_PATH", file.getPath());
					param.put("CREATE_CODE", map.get("securityUser"));
					param.put("TPM_BUSINESS_PLATE", "立项");
					param.put("FILE_TYPE", "0");
					ProjectContractManagerService service = new ProjectContractManagerService();
					service.uploadProjectFile(param);
				}
			}
		}
		return new ReplyAjax();
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply doUploadProjectFileUp() {
		Map<String, Object> param = _getParameters();
		List<FileItem> list = _getFileItem();
		String filedirpath = SkyEye.getConfig("file.path");
		filedirpath = filedirpath == null ? "/pqsoft/file" : filedirpath.toString();
		File dir = new File(filedirpath);
		{
			dir.mkdirs();
		}
		File file = null;
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				String fileName = _getFileName(fileItem.getName());
				param.put("NAME", fileName);
				file = new File(filedirpath + File.separator + System.currentTimeMillis() + fileName);
				try {
					FileCopyUtils.copy(FileCopyUtils.copyToByteArray(fileItem.getInputStream()), file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		if (file == null) { return new ReplyAjax(false, "未检测到上传的文件"); }
		param.put("PDF_PATH", file.getPath());
		param.put("CREATE_CODE", Security.getUser().getCode());
		ProjectContractManagerService service = new ProjectContractManagerService();
		service.uploadProjectFileUp(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "lichao")
	public Reply doDelFile() {
		String id = SkyEye.getRequest().getParameter("id");
		ProjectContractManagerService service = new ProjectContractManagerService();
		service.delFile(id);
		return new ReplyAjax();
	}

	/**
	 * 生成合同pdf文件公用方法 需要传递TPM_ID(文件类型id(t_pdftemplate_maintenance表ID)) PATH(文件路径)
	 * PROJECT_ID 项目id
	 * 
	 * @throws Exception
	 * @author:King 2012-4-6
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply ExpContractFile() {
		Map<String, Object> map = _getParameters();
		// 文件类型id 用于匹配查找对应的表单sql
		String TPM_ID = map.get("TPM_ID") + "";
		// pdf文件的路径
		String path = map.get("FILE_PATH") + "";
		if (StringUtils.isBlank(path)) { throw new ActionException("没有找到指定的合同模板"); }
		String name = null == map.get("FILE_NAME") ? UUID.randomUUID() + ".pdf" : map.get("FILE_NAME") + ".pdf";
		String outputPath = service.ExpContractFile(TPM_ID, map, path, name);
		return new ReplyFile(new File(outputPath), name);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply showPicture() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "showPicute.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply showContractPicture() {
		String id = SkyEye.getRequest().getParameter("FILE_ID");
		return new ReplyHtml("<img src='" + SkyEye.getRequest().getContextPath() + "/project/ProjectContractManager!doDownProjectFile3.action?id="
				+ id + "' />");
	}

	/**
	 * 进入生成合同页面
	 * 
	 * @return
	 * @author:King 2014-1-11
	 */
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toShowPdfList() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String project_id = param.get("PROJECT_ID") + "";
		context.put("PdfList", service.queryPdfList(project_id));
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "showPdfList.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply showContractPicturePath() {
		String path = SkyEye.getRequest().getParameter("path");
		return new ReplyHtml("<img src='" + path + "' />");
	}
}
