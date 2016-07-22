package com.pqsoft.Transfer.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

/**
 * 档案移交申请流程管理服务
 * 
 * @author King 2013-10-21
 */
public class DossierTransferAppService {
	private String namespace = "dossierTransfer.";

	/**
	 * 查询可以移交的档案文件
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public List<Map<String, Object>> doShowDossierTransferAppList(Map<String, Object> map) {
		map.put("F_TYPE", "文件类型");
		map.put("S_TYPE", "档案文件状态");
		System.out.println("-------------------map="+map);
		return Dao.selectList(namespace + "doShowDossierTransferAppList", map);
	}

	/**
	 * 保存移交档案申请
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public String doAddTransferApp(Map<String, Object> map) {
		String ID = Dao.getSequence("SEQ_DOSSIER_HANDAPPLY");
		map.put("ID", ID);
		Dao.insert(namespace + "doAddTransferApp", map);
		return ID;
	}
	/**
	 * 修改移交档案申请
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public int  doUpdateTransferApp(Map<String, Object> map) {
		return Dao.insert(namespace + "doUpdateTransferApp", map);
	}
	/**
	 * 查询档案移交申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public Map<String, Object> doShowTransferApp(Map<String, Object> map) {
		Map<String, Object> rstMap = Dao.selectOne(namespace + "doShowTransferApp", map);
		Dao.getClobTypeInfo2(rstMap, "FILEINFO");
		return rstMap;
	}
	/**
	 * 保存移交确认信息
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public int doAddTransferDesign(Map<String,Object> map){
		return Dao.insert(namespace+"doAddTransferDesign", map);
	}
	
	/**
	 * 根据移交申请ID删除为确认移交的信息
	 * @param TRANSFER_APP_ID
	 * @author:King 2013-10-22
	 */
	public void doDelTransferDesign(String TRANSFER_APP_ID){
		Dao.delete(namespace+"doDelTransferDesign", TRANSFER_APP_ID);
	}
}
