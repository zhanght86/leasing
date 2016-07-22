package com.pqsoft.borrow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 * 档案借还管理
 * 
 * @author King 2012-1-10
 */
public class DossierBorrowService {
	private String namespace = "dossierBorrow.";

	/**
	 * 查询可以借阅的档案文件
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public List<Map<String, Object>> doShowDossierBorrowApplyList(Map<String, Object> map) {
		map.put("F_TYPE", "文件类型");
		map.put("S_TYPE", "档案文件状态");
		return Dao.selectList(namespace + "doShowDossierBorrowApplyList", map);
	}

	/**
	 * 保存档案借阅申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public String doAddBorrowApp(Map<String, Object> map) {
		String id = Dao.getSequence("SEQ_DOSSIER_BORROWAPP");
		map.put("ID", id);
		map.put("CREATE_CODE", Security.getUser().getCode());
		int i = Dao.insert(namespace + "doAddBorrowApp", map);
		if (i > 0)
			return id;
		else
			return "";
	}
	/**
	 * 保存档案借阅申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public int doUpdateBorrowAppSave(Map<String, Object> map) {
		int i = Dao.insert(namespace + "doUpdateBorrowAppSave", map);
		return i;
	}

	/**
	 * 查询借阅申请信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-11
	 */
	public Map<String, Object> doShowBorrowApp(String BORROW_APP_ID) {
		Map<String, Object> rstMap = Dao.selectOne(namespace + "doShowBorrowApp", BORROW_APP_ID);
		Dao.getClobTypeInfo2(rstMap, "DOSSIERINFO");
		return rstMap;
	}

	/**
	 * 查询需要借出的档案在档案存放表中最新状态
	 * 
	 * @param list
	 * @author:King 2013-10-12
	 */
	@SuppressWarnings("unchecked")
	public void doShowDossierStatus(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			System.out.println(map);
			map.put("_TYPE","档案文件状态");
			map.putAll((Map) Dao.selectOne(namespace + "doShowDossierStatus", map));
		}
	}

	/**
	 * 插入档案借阅登记信息
	 * @param map
	 * @return
	 * @author:King 2013-10-13
	 */
	public int doAddBorrowOutInfo(Map<String,Object> map){
		map.put("OPERATOR_CODE", Security.getUser().getCode());
		return Dao.insert(namespace+"doAddBorrowOutInfo", map);
	}
	
	/**
	 * 查询已借出的档案信息
	 * @param map
	 * @return
	 * @author:King 2013-10-13
	 */
	public List<Map<String,Object>> doShowDossierBorrowedInfo(Map<String,Object> map){
		map.put("_TYPE", "档案借出状态");
		map.put("_DTYPE", "档案文件状态");
		return Dao.selectList(namespace+"doShowDossierBorrowedInfo", map);
	}

	/**
	 * 变更借阅档案状态
	 * @param DOSSIER_BORROWED_ID
	 * @param STATUS
	 * @return
	 * @author:King 2013-10-13
	 */
	public int updateDossierBorrowStatusById(String DOSSIER_BORROWED_ID,String STATUS){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("DOSSIER_BORROWED_ID", DOSSIER_BORROWED_ID);
		map.put("STATUS", STATUS);
		return Dao.update(namespace+"updateDossierBorrowStatusById", map);
	}
	
	// public void dd(){
	// Map<String,Object> map=new HashMap<String,Object>();
	// map.put("a", "a");
	// map.put("b", "b");
	// map.put("c", "c");
	// this.ddd(map);
	// System.out.println(map);
	// }
	// public void ddd(Map<String,Object> mp){
	// Map<String,Object> map=new HashMap<String,Object>();
	// map.put("aa", "aa");
	// map.put("ab", "ab");
	// map.put("c", "ac");
	// mp.putAll(map);
	// }
	//	
	// public static void main(String[] args) {
	// new DossierBorrowService().dd();
	// }
}
