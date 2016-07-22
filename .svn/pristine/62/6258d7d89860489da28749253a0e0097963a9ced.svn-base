package com.pqsoft.borrow.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * 档案借还管理
 * 
 * @author King 2013-10-14
 */
public class DossierBorrowManagerService {
	private String namespace = "dossierBorrowManager.";

	/**
	 * 查询档案借还管理列表数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-15
	 */
	public Page doShowDossierBorrowManager(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doShowDossierBorrowManager", namespace + "doShowDossierBorrowManagerCount");
	}

	/**
	 * 查询档案借还管理列表数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-15
	 */
	public List<Map<String, Object>> doShowDossierBorrowManagerList(Map<String, Object> map) {
		return Dao.selectList(namespace + "doShowDossierBorrowManager", map);
	}

	/**
	 * 查询一次借阅的明细文件信息
	 * 
	 * @param BORROW_APP_ID
	 * @return
	 * @author:King 2013-10-15
	 */
	public List<Map<String, Object>> doShowBorrowDetailList(String BORROW_APP_ID) {
		return Dao.selectList(namespace + "doShowBorrowDetailList", BORROW_APP_ID);
	}
}
