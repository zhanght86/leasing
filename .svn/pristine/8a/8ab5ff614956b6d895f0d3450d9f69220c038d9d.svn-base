package com.pqsoft.bpm.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.documentApp.service.DossierBorrowService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;

public class DossierBorrowStatus {

	/**
	 * 档案室借出  
	 * @param BORROW_ID
	 * @author wanghl
	 * @datetime 2015年6月16日,下午3:25:54
	 */
	public void borrowerLoanPass(String BORROW_ID){
		DossierBorrowService service = new DossierBorrowService();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("BORROW_ID", BORROW_ID);
		List<Map<String, Object>> borrowList = service.selectBorrowDetail(map);
		for(int i=0;i<borrowList.size();i++){
			Map<String, Object> borrowMap = borrowList.get(i);
			if("1".equals(borrowMap.get("STATUS").toString())){
				if(borrowMap.get("DOSSIER_COUNT")==null||"".equals(borrowMap.get("DOSSIER_COUNT").toString())){
					throw new ActionException("总份数为空");
				}
				if(borrowMap.get("DOSSIER_NUMBER")==null||"".equals(borrowMap.get("DOSSIER_NUMBER").toString())){
					throw new ActionException("借出数量为空");
				}
				int DOSSIER_COUNT = Integer.parseInt(borrowMap.get("DOSSIER_COUNT").toString()) ;
				int DOSSIER_NUMBER = Integer.parseInt(borrowMap.get("DOSSIER_NUMBER").toString()) ;
				if(DOSSIER_NUMBER==DOSSIER_COUNT){
					//总数量和借出数量相等 ，状态改为已借出
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("ID", borrowMap.get("ID"));
					param.put("DOSSIER_COUNT", 0);
					param.put("DOSSIER_STATUS", 1);
					service.updateDossierStorage(param);
				}else if(DOSSIER_COUNT>DOSSIER_NUMBER){
					//总数量大于借出数量 ，总数量减借出数量
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("ID", borrowMap.get("ID"));
					param.put("DOSSIER_COUNT", DOSSIER_COUNT-DOSSIER_NUMBER);
					service.updateDossierStorage(param);
				}else {
					throw new ActionException("借出数量不能大于总数量");
				}
			}
		}
	}
	
	/**
	 * 借阅人归还后部门负责人审批  
	 * @param BORROW_ID
	 * @author wanghl
	 * @datetime 2015年6月16日,下午3:30:42
	 */
	public void borrowerReturnPass(String BORROW_ID){
		DossierBorrowService service = new DossierBorrowService();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("BORROW_ID", BORROW_ID);
		List<Map<String, Object>> borrowList = service.selectBorrowDetail(map);
		//直接移交的list
		List<Map<String,Object>> yjList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<borrowList.size();i++){
			Map<String, Object> borrowMap = borrowList.get(i);
			//直接移交
			if("1".equals(borrowMap.get("HANDOVER_STATUS").toString())){
				yjList.add(borrowMap);
			}
			else if("1".equals(borrowMap.get("STATUS").toString())){
				int DOSSIER_COUNT = Integer.parseInt(borrowMap.get("DOSSIER_COUNT").toString()) ;
				int DOSSIER_NUMBER = Integer.parseInt(borrowMap.get("DOSSIER_NUMBER").toString()) ;
				
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("ID", borrowMap.get("ID"));
				param.put("DOSSIER_COUNT", DOSSIER_COUNT+DOSSIER_NUMBER);
				param.put("DOSSIER_STATUS", 0);
				service.updateDossierStorage(param);
			}
		}
		//如果有直接移交的资料
		if(yjList.size()>0){
			Map<String,Object> borrow = service.selectBorrow(map);
			String ID = Dao.getSequence("SEQ_API_DOCUMENT_TRANSFER");
			map.put("ID", ID);
			//提交时间
			map.put("HANDIN_DATE", borrow.get("BORROW_DATE"));
			//呈报人
			map.put("HANDIN_PERSON", borrow.get("REPORTER"));
			//接受人名称
			map.put("RECEIVE_PER", borrow.get("BORROWER"));
			//接受人身份证
			map.put("RECEIVE_IDCARD", borrow.get("ID_CARD"));
			//接受人联系电话
			map.put("RECEIVE_PHONE", borrow.get("TEL"));
			//移交方式1直递2邮寄
			map.put("TRANSFER_TYPE", borrow.get("RECEIVE_MODE"));
			//邮寄地址
			map.put("MAILING_ADDRESS", borrow.get("MAILING_ADDRESS"));
			//邮政编码
			map.put("ZIP_CODE", borrow.get("ZIP_CODE"));
			//备注
			map.put("REMARK", borrow.get("BORROW_REMARK"));
			map.put("USERCODE", Security.getUser().getCode());
			//移交状态0未移交, 1移交已发起, 2已移交
			map.put("STATUS", "2");
			//呈报人电话
			map.put("HANDIN_PHONE", "");
			
			int i = Dao.insert("dossierApp.transfer.doInsertTransfer", map);
			if(i<=0){
				throw new ActionException("移交资料插入失败");
			}
			Map<String,Object> detial = new HashMap<String, Object>();
			for(int x=0;x<yjList.size();x++){
				Map<String, Object> map2 = yjList.get(x);
				detial.put("TRANSFER_ID", ID);
				detial.put("USERCODE", Security.getUser().getCode());
				detial.put("STORAGE_ID", map2.get("STORAGE_ID"));
				detial.put("LEASE_CODE", map2.get("LEASE_CODE"));
				detial.put("TRANSFER_NUMBER", map2.get("DOSSIER_NUMBER"));
				Dao.insert("dossierApp.transfer.doInsertTransferDetail", detial);
				Map<String,Object> map3 = new HashMap<String, Object>();
				map3.put("ID", map2.get("STORAGE_ID"));
				map3.put("STATUS", "3");
				Dao.update("dossierApp.borrow.updateDossierStorage", map3);
			}
		}
	}
}
