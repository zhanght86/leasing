package com.pqsoft.Transfer.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * 
 * @author King 2013-10-22
 */
public class DossierTransferManagerService {
	private String namespace="dossierTransfer.";
	/**
	 * 查询档案移交主数据列表
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public Page doShowDossierTransferManager(Map<String,Object> map){
		return PageUtil.getPage(map, namespace+"doShowTransferMain", namespace+"doShowTransferMainCount");
	}
	
	/**
	 * 查询移交明细文件
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public List<Map<String,Object>> doShowBorrowDetailList(Map<String,Object> map){
		return Dao.selectList(namespace+"doShowBorrowDetailList", map);
	}
}
