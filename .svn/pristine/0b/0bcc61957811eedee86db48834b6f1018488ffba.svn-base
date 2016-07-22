package com.pqsoft.pigeonhole.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

/**
 * 归档管理
 * 
 * @author King 2013-10-9
 */
public class PigeonholeService {
	private String NAMESPACE = "pigeonhole.";

	/**
	 * 根据项目id(PROJECT_ID)或客户id(CLIENT_ID)或还款计划编号(PAYLIST_CODE)查询归档申请基本信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-9
	 */
	public Map<String, Object> doShowPigeonholeBaseInfo(Map<String, Object> map) {
		map.put("_TYPE", "客户类型");
		return Dao.selectOne(NAMESPACE + "doShowPigeonholeBaseInfo", map);
	}

	/**
	 * 保存归档申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-10
	 */
	public String doAddPigeonholeApplyInfo(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		String id = Dao.getSequence("SEQ_FIL_DOSSIER_APPLY");
		map.put("ID", id);
		Dao.insert(NAMESPACE + "doAddPigeonholeApplyInfo", map);
		return id;
	}
	
	public void doUpdatePigeonholeApplyInfo(Map map){
		Dao.update(NAMESPACE + "doUpdatePigeonholeApplyInfo", map);
	}

	/**
	 * 根据客户id(CLINET_ID)和项目编号(PROJECT_CODE)删除未归档的申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-10
	 */
	public int doDelPigeonholeApplyInfo(Map<String, Object> map) {
		return Dao.delete(NAMESPACE + "doDelPigeonholeApplyInfo", map);
	}

	/**
	 * 根据归档申请ID查询归档申请信息
	 * 
	 * @param DOSSIER_APPLY_ID
	 * @return
	 * @author:King 2013-10-10
	 */
	public Map<String, Object> doShowPigeonholeApplyInfo(String DOSSIER_APPLY_ID) {
		Map<String, Object> map = Dao.selectOne(NAMESPACE + "doShowPigeonholeApplyInfo", DOSSIER_APPLY_ID);
		Dao.getClobTypeInfo2(map, "FILEINFO");
		return map;
	}
	
	/**
	 * 判断文件是否归档
	 */
	public void judgeDocumentsArchive(String jbpmId) {
		Map<String,Object> param = JBPM.getVeriable(jbpmId);
		List<Map<String, Object>> listMap = Dao.selectList(NAMESPACE + "judgeDocumentsArchive", param);
		if(listMap == null || listMap.isEmpty() || listMap.size() <= 0){
			throw new ActionException("请先归档！");
		}
	}

	/**
	 * 根据客户id查询客户档案袋编号
	 * 
	 * @param CLIENT_ID
	 * @param PROJECT_CODE
	 * @return
	 * @author:King 2013-10-10
	 */
	public Map<String, Object> doShowClientPortfolioAndCabinet(String CLIENT_ID, String PROJECT_CODE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_ID", CLIENT_ID);
		map.put("PROJECT_CODE", PROJECT_CODE);
		return Dao.selectOne(NAMESPACE + "doShowClientPortfolioAndCabinet", map);
	}

	/**
	 * 保存插入档案信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-10
	 */
	public int doAddDossierStorage(Map<String, Object> map) {
		try{
			map.put("CREATE_CODE", Security.getUser().getCode());
			return Dao.insert(NAMESPACE + "doAddDossierStorage", map);
		}catch(Exception e){
			throw new ActionException("保存失败",e);
		}
	}

	/**
	 * 删除档案重复数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-10
	 */
	public int doDelDossierStorage(String DOSSIER_APPLY_ID) {
		return Dao.delete(NAMESPACE + "doDelDossierStorage", DOSSIER_APPLY_ID);
	}

	/**
	 * 查询合同档案归档申请信息
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-10-9
	 */
	public List<Map<String, Object>> doShowContractDossierApplyFileList(String PROJECT_ID) {
		return Dao.selectList(NAMESPACE + "doShowContractDossierApplyFileList", PROJECT_ID);
	}
	
	/**
	 * 查询合同模版详细信息
	 */
	public Map<String, Object> selectPdfTemplateManagementData(Map<String, Object> param) {
		return Dao.selectOne(NAMESPACE + "selectPdfTemplateManagementData", param);
	}

	/**
	 * 查询客户档案申请归档信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public List<Map<String, Object>> doShowCustomerDossierApplyFileList(Map<String, Object> map) {
		map.put("_TYPE", "承租人档案");
		return Dao.selectList(NAMESPACE+"doShowCustomerDossierApplyFileList", map);
	}
	
	public List<Map<String, Object>> doShowDossierStorageApplyList(Map<String, Object> map) {
		map.put("F_TYPE", "文件类型");
		map.put("S_TYPE", "档案文件状态");
		return Dao.selectList(NAMESPACE + "doShowDossierStorageList", map);
	}

	/**
	 * 查询档案文件配置--数据字典
	 * 
	 * @param CLIENT_TYPE
	 *            客户类型 个人 法人或对应的CODE 均可
	 * @param JBPMMODULE
	 *            流程启动模块
	 * @param FILE_TYPE 文件类型  0 项目资料清单  1合同文件
	 * @return <br>
	 *         返回的map 包含 FILE_NAME(文件名称),CODE (唯一标示),FIL_PAGE(文件页数)
	 *         ,CLIENT_TYPE_NAME(适用的客户类型),FILE_COUNT(文件数量),REMARK(适用流程)
	 * @author:King 2013-11-5
	 */
	public List<Map<String, Object>> doShowDossierFileConfigFromDataDictionary(String CLIENT_TYPE, String JBPMMODULE,String FILE_TYPE) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("CLIENT_TYPE", CLIENT_TYPE);
		map.put("JBPMMODULE", JBPMMODULE);
		map.put("DOSSIER_CONFIG", "档案文件配置");
		map.put("CLIENT_TYPE_CONFIG", "客户类型");
		if (StringUtils.isBlank(FILE_TYPE)) {
			map.put("FILE_TYPE", null);
		} else if(FILE_TYPE.equals("0")){
			map.put("FILE_TYPE", "P");
		}else if (FILE_TYPE.equals("1")) {
			map.put("FILE_TYPE", "C");
		}
		return Dao.selectList(NAMESPACE+"doShowDossierFileConfigFromDataDictionary", map);
	}
	public List<Map<String, Object>> doShowDossierFileConfigFromDataDictionary1() {
		return Dao.selectList(NAMESPACE+"doShowDossierFileConfigFromDataDictionary1");
	}
	
	
	//查询项目资料
	public List<Map<String, Object>> doShowFile(String CLIENT_TYPE){
		Map<String,Object> map=new HashMap<String,Object>();
		if (StringUtils.isBlank(CLIENT_TYPE)) {
			map.put("CLIENT_TYPE", "1");
		} else if(CLIENT_TYPE.equals("LP")){
			map.put("CLIENT_TYPE", "2");
		}else if (CLIENT_TYPE.equals("NP")) {
			map.put("CLIENT_TYPE", "1");
		}
		
		return Dao.selectList(NAMESPACE+"doShowFile",map);
	}
	
	public int doDelContractByProjectId(Map<String, Object> map) {
		return Dao.delete(NAMESPACE + "doDelContractByProjectId", map);
	}
	
	public int doAddCheckedContract(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		return Dao.insert(NAMESPACE + "doAddCheckedContract", map);
	}
	
	public Map queryInfoByProjectId(String project_id){
		Map map=new HashMap();
		map.put("PROJECT_ID", project_id);
		return Dao.selectOne(NAMESPACE+"queryInfoByProjectId", map);
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
	 * 根据客户ID查询客户类型
	 * 
	 * @param CLIENT_ID
	 * @return
	 * @author:King 2013-11-5
	 */
	public Map<String, Object> doShowClientTypeByClient(String CLIENT_ID) {
		return Dao.selectOne(NAMESPACE + "doShowClientTypeByClient", CLIENT_ID);
	}

	public Map<String,Object> getCustCode(String ID){
		return Dao.selectOne("customers.getCustCode",ID);
	}

}
