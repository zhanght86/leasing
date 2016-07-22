package com.pqsoft.documentApp.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class DossierBorrowService {

	
	
	public int addBorrow(Map<String,Object> param){
		return Dao.insert("dossierApp.borrow.addBorrow", param);
	}
	
	public int addBorrwDetail(Map<String,Object> param){
		return Dao.insert("dossierApp.borrow.addBorrowDetail", param);
	}
	
	public List<Map<String,Object>> toShowDossierApp(Map<String,Object> param){
		return Dao.selectList("dossierApp.borrow.toShowDossierApp",param);
	}
	
	/**
	 * 查询一条借阅申请信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月10日,上午11:56:38
	 */
	public Map<String,Object> selectBorrow(Map<String,Object> param){
		return Dao.selectOne("dossierApp.borrow.selectBorrow", param);
	}
	
	/**
	 * 查询借阅申请清单     需要LEASE_CODE和BORROW_ID
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月10日,下午12:00:13
	 */
	public List<Map<String,Object>> selectBorrowDetail(Map<String,Object> param){
		return Dao.selectList("dossierApp.borrow.selectBorrowDetail", param);
	}
	
	/**
	 * 查询清单融资租赁合同号
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月10日,下午1:41:09
	 */
	public List<Map<String,Object>> selectDetailLeaseCode(Map<String,Object> param){
		return Dao.selectList("dossierApp.borrow.selectDetailLeaseCode",param);
	}
	
	/**
	 * 更新资料清单
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月12日,下午2:12:32
	 */
	public int updateBorrowDetail(Map<String,Object> param){
		return Dao.update("dossierApp.borrow.updateBorrowDetail", param);
	}
	
	/**
	 * 更新权证接口--档案申请明细
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月16日,下午3:17:03
	 */
	public int updateDossierStorage(Map<String,Object> param){
		return Dao.update("dossierApp.borrow.updateDossierStorage", param);
	}
	
}
