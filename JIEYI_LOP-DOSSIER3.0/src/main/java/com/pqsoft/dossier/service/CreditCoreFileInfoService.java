package com.pqsoft.dossier.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 资信核心材料管理
 * 
 * @author King 2012-2-2
 */
public class CreditCoreFileInfoService {

	private final Logger logger = Logger.getLogger(this.getClass());
	private String namespace = "creditFileList.";

	/**
	 * 查询资信核心材料信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2012-2-2
	 * @throws Exception
	 */
	public Page queryCreditCoreFileListInfo(Map<String, Object> map) {
		JSONObject param = JSONObject.fromObject(map.get("param"));
		map.putAll(param);
		return PageUtil.getPage(map, namespace + "queryCreditCoreFileList",
				namespace + "queryCreditCoreFileCount");
	}

	/**
	 * 添加新的资信核心材料信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2012-2-2
	 */
	public int addCreditCoreFile(Map<String, Object> map) {
		logger.debug("进入CreditListInfoService.addCreditCoreFile()方法");
		return Dao.insert(namespace + "addCreditCoreFile", map);
	}

	/**
	 * 根据主键ID修改资信核心材料信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2012-2-2
	 */
	public int updateCreditCoreFileInfo(Map<String, Object> map) {
		logger.debug("进入CreditListInfoService.updateCreditCoreFileInfo()方法");
		return Dao.update(namespace + "updateCreditCoreFile", map);
	}

	public int deleteCreditCoreFileInfo(Map<String, Object> map) {
		logger.debug("进入CreditListInfoService.deleteCreditCoreFileInfo()方法");
		return Dao.delete(namespace + "deleteCreditCoreFileInfo", map);
	}

	/**
	 * 为导出核心材料清单准备数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2012-2-3
	 */
	public List<Map<String, Object>> queryCreditCoreFileForExp(
			Map<String, Object> map) {
		logger.debug("为导出数据做准备");
		return Dao.selectList(namespace + "exportCreditCoreFile", map);
	}

	/**
	 * 判断文件类型是否已经存在
	 * 
	 * @param map
	 * @return
	 * @author:King 2012-2-3
	 */
	public int isExistCoreFile(Map<String, Object> map) {
		logger.debug("判断材料是否已存在于表中");
		return Dao.selectInt(namespace + "isExistCoreFile", map);
	}

	/**
	 * 从数据字典里查询核心资料类型
	 * 
	 * @param TYPE
	 * @return
	 * @author:King 2012-2-5
	 */
	public List<Map<String, Object>> queryFileForManType(String TYPE) {
		return Dao.selectList(namespace + "queryFileForManType", TYPE);
	}

	/**
	 * 接收文件
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public List<Map<String, Object>> toRecieveProjectFile(
			Map<String, Object> map) {
		return Dao.selectList(namespace + "toRecieveProjectFile", map);
	}

	public List toContractFile(Map map) {
		System.out.println("----------------map=" + map);
		return Dao.selectList(namespace + "toContractFile", map);
	}

	/**
	 * 查询人员信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public Map<String, Object> queryCustInfo(Map<String, Object> map) {
		return Dao.selectOne(namespace + "queryCustInfo", map);
	}

	/**
	 * 合同初审
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public int addProjectChuShen(Map<String, Object> map) {
		return Dao.insert(namespace + "addProjectChuShen", map);
	}

	/**
	 * 作者：杨杰 邮箱 ：bingyyf@foxmail.com 时间：2014-5-22 下午03:42:02 qwe
	 * 位置：SKYEYE-DOSSIER3.0 com.pqsoft.creditlist.service
	 */
	public int updateProjectChuShen(Map<String, Object> map) {
		return Dao.insert(namespace + "updateProjectChuShen", map);
	}

	/**
	 * 作者：杨杰 邮箱 ：bingyyf@foxmail.com 时间：2014-5-22 下午03:51:20 qwe
	 * 位置：SKYEYE-DOSSIER3.0 com.pqsoft.creditlist.service
	 */
	// public List queryProjectChuShen(Map<String,Object> map){
	// return Dao.selectList(namespace+"queryProjectChuShen", map);
	// }

	/**
	 * 删除初审数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public int deleteProjectChuShen(Map<String, Object> map) {
		return Dao.delete(namespace + "deleteProjectChuShen", map);
	}

	/**
	 * 查看合同初审数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public List<Map<String, Object>> showProjectChuShen(Map<String, Object> map) {
		return Dao.selectList(namespace + "showProjectChuShen", map);
	}

	public Map projectStaMap(Map<String, Object> map) {
		return Dao.selectOne(namespace + "projectStaMap", map);
	}

	// update auther:yangyong date：2014-3-18 for：接收文件页面数据
	/**
	 * 查询接收文件
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-3-16
	 */
	public List<Map<String, Object>> toRecieveProjectFile1(
			Map<String, Object> map) {
		return Dao.selectList(namespace + "toRecieveProjectFile1", map);
	}

	// update auther:yangyong date：2014-3-18 for：接收文件页面数据
	/**
	 * 根据文件名称以及资料清单类型 获取该名称所在数据字典的id
	 * 
	 * @auther:YuanYe
	 */
	public String getDataIdByType(Map<String, Object> map) {
		Integer dataid = (Integer) Dao.selectOne(namespace + "getdtataid", map);
		return dataid.toString();

	}

	/**
	 * 查询所有未通过初审的项目
	 * 
	 * @return
	 */
	public Page findAllNotRecieveproject(Map<String, Object> map) {
		JSONObject param = JSONObject.fromObject(map.get("param"));
		map.putAll(param);
		return PageUtil.getPage(map, namespace + "findAllNotRecieveProject",
				namespace + "findAllNotRecieveProjectCount");
	}

	public int updateProjectBuQi(Map<String, Object> map) {
		System.out.println("归档--->map" + map);
		return Dao.update(namespace + "updateProjectDoc", map);
	}

	/**
	 * 保存合同附件并同步到归档申请表
	 * 
	 * @param PROJECT_ID
	 * @param TYPE_
	 *            文件对应的流程类型
	 * @author:King 2013-12-6
	 */
	public void doAddProjectContractList(String PROJECT_ID, String TYPE_) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isBlank(TYPE_)){
			TYPE_="立项合同";
		}
		param.put("PROJECT_ID", PROJECT_ID);
		if (!StringUtils.isBlank(param.get("PROJECT_ID"))) {
			PROJECT_ID = param.get("PROJECT_ID") + "";
			param.putAll((Map<? extends String, ? extends Object>) Dao
					.selectOne(namespace + "queryDossierApplyInfo", param));
			List<Map<String, Object>> list = Dao.selectList(namespace
					+ "queryDossierApply", param);
			/***************************** 准备数据 ********************/
			String CLIENT_ID = param.get("CLIENT_ID") + "";
			List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			// FILE_TYPE 文件类型 1 合同档案 2 承租人档案
			String FILE_TYPE = "1";
			if(StringUtils.isNotBlank(list)&&list.size()>=1&& !list.isEmpty()){
				for (Map<String, Object> map : list) {
					Map<String, Object> rstMap = new HashMap<String, Object>();
					rstMap.put("TPM_CODE", TYPE_);
					rstMap.put("TPM_TYPE", map.get("FILE_NAME"));
					rstMap.put("TPM_CUSTOMER_TYPE", map.get("CLIENT_TYPE_NAME"));
					rstMap.put("PROJECT_ID", PROJECT_ID);
					rstMap.put("FILE_REMARK", map.get("REMARK"));
					rstMap.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
//					if (StringUtils.isNotBlank(map.get("TYPE"))
//							&& !map.get("TYPE").toString().contains("合同")) {
//						// 承租人档案
//						rstMap.put("DOSSIERTYPE", "2");
//					} else {
//						// 合同档案
//						rstMap.put("DOSSIERTYPE", FILE_TYPE);
//					}
					rstMap.put("DOSSIERTYPE", FILE_TYPE);
					rstMap.put("DOSSIER_PAGE", map.get("FILE_PAGE"));
					rstMap.put("DOSSIER_COUNT", map.get("FILE_COUNT"));
					rstMap.put("FILE_TYPE", map.get("DOSSIER_TEMP"));
					rstMap.put("CLIENT_ID", CLIENT_ID);
					rstMap.put("TPM_BUSINESS_PLATE", TYPE_);
					rstList.add(rstMap);
				}
				mm.put("PROJECT_CODE", param.get("LEASE_CODE"));
				mm.put("CLIENT_ID", CLIENT_ID);
				mm.put("SEND_TYPE", param.get("SENDTYPE"));
				mm.put("FILEINFO", JSONArray.fromObject(rstList) + "");
	
				/********************* 更新保存数据并做归档处理 ****************************/
				PigeonholeService pService = new PigeonholeService();
				pService.doDelPigeonholeApplyInfo(mm);
				Map<String, Object> m = new HashMap<String, Object>();
				try {
					String APPLY_ID = pService.doAddPigeonholeApplyInfo(mm);
					m.put("DOSSIER_APPLY_ID", APPLY_ID);
					m.put("PROJECT_ID", PROJECT_ID);
					Dao.update(namespace
							+ "updateFIL_CONTRACT_RECIEVEFILEDossierApplyId", m);
					FileCatalogUtil fcu = new FileCatalogUtil();
	
					for (Map<String, Object> map : list) {
						if ((map.get("FILE_REMARK") + "").contains("留购")) {
							map.put("DOSSIER_FILE_TYPE", "项目留购");
						} else if ((map.get("FILE_REMARK") + "").contains("分期回购")) {
							map.put("DOSSIER_FILE_TYPE", "分期回购");
						} else if ((map.get("FILE_REMARK") + "").contains("正常回购")) {
							map.put("DOSSIER_FILE_TYPE", "正常回购");
						} else if ((map.get("FILE_REMARK") + "").contains("立项")) {
							map.put("DOSSIER_FILE_TYPE", "立项合同");
						} else if ((map.get("FILE_REMARK") + "").contains("租金变更")) {
							map.put("DOSSIER_FILE_TYPE", "租金变更");
						} else if ((map.get("FILE_REMARK") + "").contains("资料变更")) {
							map.put("DOSSIER_FILE_TYPE", "资料变更");
						} else if ((map.get("FILE_REMARK") + "").contains("资料补齐")) {
							map.put("DOSSIER_FILE_TYPE", "资料补齐");
						} else if ((map.get("FILE_REMARK") + "").contains("提前结清")) {
							map.put("DOSSIER_FILE_TYPE", "提前结清");
						} else if ((map.get("FILE_REMARK") + "").contains("A-B审批")) {
							map.put("DOSSIER_FILE_TYPE", "A-B审批");
						} else {
							map.put("DOSSIER_FILE_TYPE", "立项合同");
						}
						try {
							String CLIENT_CODE = null;
							Map<String, Object> ma = new HashMap<String, Object>();
							ma.put("ID", map.get("CLIENT_ID").toString());
							Map<String, Object> custMap = Dao.selectOne(
									"customers.getCustCode", ma);
							CLIENT_CODE = custMap.get("CUST_ID") + "";
							fcu.getCatalogIdByPath(
									map.get("CLIENT_NAME").toString(),
									CLIENT_CODE,
									map.get("PROJECT_CODE") + "/"
											+ map.get("DOSSIER_FILE_TYPE") + "/"
											+ map.get("FILE_NAME"), true);
						} catch (Exception e) {
							Log.error("同步档案时---图像采集未同步");
						}
						;
						map.put("CLIENT_ID", CLIENT_ID);
						map.put("CLIENT_NAME", param.get("CLIENT_NAME"));
						map.put("PLATFORM_TYPE", param.get("PLATFORM_TYPE"));
						map.put("TPM_BUSINESS_PLATE", TYPE_);
						map.put("STATUS", "3");
						map.put("DOSSIER_APPLY_ID", APPLY_ID);
						map.put("DOSSIER_COUNT", map.get("FILE_COUNT"));
						map.put("DOSSIER_PAGE", map.get("FILE_PAGE"));
						map.put("DOSSIER_TEMP", map.get("DOSSIER_TEMP"));
//						if (StringUtils.isNotBlank(map.get("TYPE"))
//								&& !map.get("TYPE").toString().contains("合同")) {
//							// 承租人档案
//							map.put("FILE_TYPE", "2");
//							map.put("PROJECT_CODE", "");
//						} else {
//							// 合同档案
//							map.put("FILE_TYPE", FILE_TYPE);
//							map.put("PROJECT_CODE", param.get("LEASE_CODE"));
//						}
							// 合同档案
							map.put("FILE_TYPE", FILE_TYPE);
							map.put("PROJECT_CODE", param.get("LEASE_CODE"));
	
						new PigeonholeService().doAddDossierStorage(map);
					}
				} catch (Exception e) {
					throw new ActionException("归档申请失败", e);
				}
			}
		} else {
			throw new ActionException("没有找到匹配的项目,请联系管理员！");
		}
	}
}
