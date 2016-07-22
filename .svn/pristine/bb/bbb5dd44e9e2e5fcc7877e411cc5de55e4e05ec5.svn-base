package com.pqsoft.project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.AcrobatENG;
import com.pqsoft.util.StringUtils;

/**
 * 项目中合同模板管理
 * 
 * @author King 2013-9-29
 */
public class ProjectContractManagerService {
	private final String NAMESPACE = "projectContractManager.";

	/**
	 * 项目合同文件列表
	 * 
	 * @param PROJECT_ID
	 * @param FILE_CODE
	 *            对应数据字典 PDF模版类型中的CODE
	 * @param CLIENT_ID
	 * @param FILE_NAME
	 *            文件类型名称 如融资租赁合同
	 * @return
	 * @author:King 2013-10-8
	 */
	public List<Map<String, Object>> doShowContractListByProjectId(String PROJECT_ID, String FILE_CODE, String CLIENT_ID, String FILE_NAME) {
		if (StringUtils.isBlank(PROJECT_ID)) { return new ArrayList<Map<String, Object>>(); }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("_TYPE", "PDF模版类型");
		map.put("CLIENT_ID", CLIENT_ID);
		map.put("C_TYPE", "客户类型");
		map.put("FILE_NAME", FILE_NAME);
		// map.put("_STATUS", "启用");
		if (StringUtils.isBlank(FILE_CODE) || FILE_CODE.equals("null")) map.put("CODE_", "");
		else map.put("CODE_", FILE_CODE);
		return Dao.selectList(NAMESPACE + "doShowContractListByProjectId", map);
	}

	/**
	 * 保存合同文件列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-9
	 */
	public int doAddCheckedContract(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		return Dao.insert(NAMESPACE + "doAddCheckedContract", map);
	}

	public int doDelContractByProjectId(Map<String, Object> map) {
		return Dao.delete(NAMESPACE + "doDelContractByProjectId", map);
	}

	/**
	 * 查询项目所需的合同列表文件
	 * 
	 * @param PROJECT_ID
	 * @param FILE_TYPE
	 *            文件类型 0 图像采集文件 1 合同pdf文件
	 * @param TPM_CODE
	 * @param CLIENT_ID
	 * @param FILE_ID
	 * @param FILE_MODEL
	 *            文件使用类型 如立项、留购、租金变更、回购等
	 * @return
	 * @author:King 2013-10-9
	 */
	public List<Map<String, Object>> doShowProjectContractList(String PROJECT_ID, String FILE_TYPE, String TPM_CODE, String CLIENT_ID,
			String FILE_ID, String FILE_MODEL) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("FILE_TYPE", FILE_TYPE);
		map.put("CLIENT_ID", CLIENT_ID);
		map.put("TPM_CODE", TPM_CODE);
		map.put("FILE_ID", FILE_ID);
		map.put("TPM_BUSINESS_PLATE", FILE_MODEL);
		return Dao.selectList(NAMESPACE + "doShowProjectContractList", map);
	}

	/**
	 * 查询项目所需的合同列表文件
	 * 
	 * @param PROJECT_ID
	 * @param FILE_TYPE
	 *            文件类型 0 图像采集文件 1 合同pdf文件
	 * @param TPM_CODE
	 * @param CLIENT_ID
	 * @param FILE_ID
	 * @param FILE_MODEL
	 *            文件使用类型 如立项、留购、租金变更、回购等
	 * @return
	 * @author:King 2013-10-9
	 */
	public List<Map<String, Object>> doShowProjectContractListDossier(String PROJECT_ID, String FILE_TYPE, String TPM_CODE, String CLIENT_ID,
			String FILE_ID, String FILE_MODEL) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("FILE_TYPE", FILE_TYPE);
		map.put("CLIENT_ID", CLIENT_ID);
		map.put("TPM_CODE", TPM_CODE);
		map.put("FILE_ID", FILE_ID);
		map.put("TPM_BUSINESS_PLATE", FILE_MODEL);
		return Dao.selectList(NAMESPACE + "doShowProjectContractListDossier", map);
	}

	public List<Map<String, Object>> doShowProjectContractListDossier2(String PROJECT_ID, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("TYPE", type);
		String picContid = Dao.selectOne(NAMESPACE + "getPicContractId", map);
		if (picContid == null) return null;
		map.put("PICCONTID", picContid);
		List<Map<String, Object>> list = Dao.selectList(NAMESPACE + "doShowProjectContractListDossier2", map);
		for (Map<String, Object> map2 : list) {
			String path = (String) map2.get("PDF_PATH");
			if (path == null) continue;
			path.split("");
		}
		return list;
	}

	/**
	 * 查询文件
	 */
	public Map<String, Object> getProjectFileById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", id);
		return Dao.selectOne(NAMESPACE + "getProjectFileById", map);
	}

	/**
	 * 查询承租人档案文件列表
	 * 
	 * @return
	 * @author:King 2013-10-11
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> doShowCustomerFileList() {
		return new DataDictionaryMemcached().get("承租人档案");
	}

	/**
	 * 导出生成合同pdf文件
	 * 
	 * @param TPM_ID
	 *            (文件类型id(t_pdftemplate_maintenance表ID))
	 * @param map
	 *            表单sql所需参数
	 * @param inputPath
	 *            pdf模板文件路径
	 * @param fileName
	 *            导出生成的文件名
	 * @author:King 2013-10-25
	 */
	public String ExpContractFile(String TPM_ID, Map<String, Object> map, String inputPath, String fileName) {
		// 解析读取填充表单数据
		HashMap<String, Object> mm = AcrobatENG.getFills(TPM_ID, map);
		// String PERCENT = null == mm.get("PERCENT") ? "0.00d" : mm
		// .get("PERCENT").toString();
		// if (Double.parseDouble(PERCENT) == 0) {
		// mm.put("PERCENT", "");
		// }
		// 将原来的小写金额转换为大写金额
		HashMap<String, Object> rstMap = UtilFinance.numToUpCase(mm);
		String root = SkyEye.getConfig("file.path.temp");
		if (fileName.contains(".pdf") || fileName.contains(".PDF")) {

		} else {
			fileName = fileName + ".pdf";
		}
		String outputPath = root + File.separator + fileName;
		try {
			System.out.println("outputPath=" + outputPath);
			AcrobatENG.mergeAcrobat(inputPath, rstMap, outputPath);
		} catch (Exception e) {
			e.getMessage();
			throw new ActionException("导出错误" + e.getMessage());
		}
		return outputPath;
	}

	/**
	 * 删除项目文件表信息
	 * 
	 * @param map
	 * @author:King 2013-11-4
	 */
	public void doDelProjectFile(Map<String, Object> map) {
		Dao.delete(NAMESPACE + "doDelProjectFile", map);
	}

	/**
	 * 查询档案文件配置--数据字典
	 * 
	 * @param CLIENT_TYPE
	 *            客户类型 个人 法人或对应的CODE 均可
	 * @param JBPMMODULE
	 *            流程启动模块
	 * @param FILE_TYPE
	 *            文件类型 0 项目资料清单 1合同文件
	 * @return <br>
	 *         返回的map 包含 FILE_NAME(文件名称),CODE (唯一标示),FIL_PAGE(文件页数)
	 *         ,CLIENT_TYPE_NAME(适用的客户类型),FILE_COUNT(文件数量),REMARK(适用流程)
	 * @author:King 2013-11-5
	 */
	public List<Map<String, Object>> doShowDossierFileConfigFromDataDictionary(String CLIENT_TYPE, String JBPMMODULE, String FILE_TYPE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_TYPE", CLIENT_TYPE);
		map.put("JBPMMODULE", JBPMMODULE);
		map.put("DOSSIER_CONFIG", "档案文件配置");
		map.put("CLIENT_TYPE_CONFIG", "客户类型");
		if (StringUtils.isBlank(FILE_TYPE)) {
			map.put("FILE_TYPE", null);
		} else if (FILE_TYPE.equals("0")) {
			map.put("FILE_TYPE", "P");
		} else if (FILE_TYPE.equals("1")) {
			map.put("FILE_TYPE", "C");
		}
		return Dao.selectList(NAMESPACE + "doShowDossierFileConfigFromDataDictionary", map);
	}

	/**
	 * 根据项目ID查询客户类型
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-11-5
	 */
	public Map<String, Object> doShowClientTypeByProjectId(String PROJECT_ID) {
		return Dao.selectOne(NAMESPACE + "doShowClientTypeByProjectId", PROJECT_ID);
	}

	/**
	 * 保存合同附件并同步到归档申请表
	 * 
	 * @param jbpmId
	 * @author:King 2013-12-6
	 */
	public void doAddProjectContractList(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		String PROJECT_ID = null;
		if (!StringUtils.isBlank(param.get("PROJECT_ID"))) {
			PROJECT_ID = param.get("PROJECT_ID") + "";
			param.putAll(this.doShowClientTypeByProjectId(PROJECT_ID));
			// 确认流程匹配模块
			String JBPMID = jbpmId.substring(0, jbpmId.indexOf("."));
			param.put("_TYPE", "流程定义模块");
			param.put("JBPMID", JBPMID);
			List<String> jbpmList = Dao.selectList(NAMESPACE + "queryJbpmModel", param);
			String JBPM_MODEL = null;
			if (jbpmList.size() > 0) JBPM_MODEL = jbpmList.get(0);
			else JBPM_MODEL = JBPMID;

			List<Map<String, Object>> list = this.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", JBPM_MODEL, null);
			/***************************** 准备数据 ********************/
			String CLIENT_ID = param.get("CLIENTID") + "";
			List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				Map<String, Object> rstMap = new HashMap<String, Object>();
				rstMap.put("TPM_CODE", map.get("CODE"));
				rstMap.put("TPM_TYPE", map.get("FILE_NAME"));
				rstMap.put("TPM_CUSTOMER_TYPE", map.get("CLIENT_TYPE_NAME"));
				rstMap.put("PROJECT_ID", PROJECT_ID);
				rstMap.put("FILE_REMARK", map.get("REMARK"));
				rstMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
				rstMap.put("DOSSIERTYPE", "1");
				rstMap.put("DOSSIER_PAGE", map.get("FILE_PAGE"));
				rstMap.put("DOSSIER_COUNT", map.get("FILE_COUNT"));
				rstMap.put("FILE_TYPE", map.get("FILE_TYPE"));
				rstMap.put("CLIENT_ID", CLIENT_ID);
				String REMARK = map.get("REMARK") + "";
				if (REMARK.contains(JBPM_MODEL)) {
					REMARK = JBPM_MODEL;
				}
				rstMap.put("TPM_BUSINESS_PLATE", REMARK);
				rstList.add(rstMap);
			}
			mm.put("PROJECT_CODE", param.get("PROJECT_CODE"));
			mm.put("CLIENT_ID", CLIENT_ID);
			mm.put("SEND_TYPE", "1");
			mm.put("FILEINFO", JSONArray.fromObject(rstList) + "");

			/********************* 更新保存数据并做归档处理 ****************************/
			PigeonholeService pService = new PigeonholeService();
			pService.doDelPigeonholeApplyInfo(mm);
			Map<String, Object> m = new HashMap<String, Object>();
			try {
				String APPLY_ID = pService.doAddPigeonholeApplyInfo(mm);
				m.put("DOSSIER_APPLY_ID", APPLY_ID);
				JBPM.setExecutionVariable(jbpmId, m);
			} catch (Exception e) {
				throw new ActionException("归档申请失败", e);
			}
			if (rstList.size() > 0) {
				int i = 0;
				this.doDelContractByProjectId(rstList.get(0));
				for (Map<String, Object> map : rstList) {
					i += this.doAddCheckedContract(map);
				}
			} else {
				throw new ActionException("没有索引到需要的数据");
			}
		} else {
			throw new ActionException("没有找到匹配的项目,请联系管理员！");
		}
	}

	public void uploadProjectFile(Map<String, Object> param) {
		Map<String, Object> pro = Dao.selectOne(NAMESPACE + "getProForFile", param);
		if (pro != null) {
			if (param.get("CLIENT_ID") == null || "".equals(param.get("CLIENT_ID"))) param.put("CLIENT_ID", pro.get("CLIENT_ID"));
			param.put("PAYLIST_CODE", pro.get("PRO_CODE") + "-1");
		}
		// param.put("CREATE_CODE", Security.getUser().getCode());
		System.out.println("king=" + param);
		Dao.insert(NAMESPACE + "uploadProFile", param);
		param.put("TPM_BUSINESS_PLATE", "立项");
		Dao.delete(NAMESPACE + "uploadProFileClean", param);
	}

	public void delFile(String id) {
		Dao.delete(NAMESPACE + "delFile", id);
	}

	public void uploadProjectFileUp(Map<String, Object> param) {
		Dao.update(NAMESPACE + "uploadProjectFileUp", param);
	}

	public String getCustType(String client_id) {
		return Dao.selectOne(NAMESPACE + "getCustType", client_id);
	}

	public Map<String, Object> getFilePic(String id) {
		Map<String, Object> m = Dao.selectOne(NAMESPACE + "getFilePic", id);
		return m;
	}

	/**
	 * 查询需要下载的合同模板
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2014-1-11
	 */
	public List<Map<String, Object>> queryPdfList(String PROJECT_ID) {
		return Dao.selectList(NAMESPACE + "queryPdfList", PROJECT_ID);
	}

	public List<Map<String, Object>> getCreditFileListSrc(Map<String, Object> param) {
		param = Dao.selectOne("crm.customer.getCreditForFileList", param);
		if (param != null) { return Dao.selectList("crm.customer.getCreditFileListSrc", param); }
		return null;
	}

}
