package com.pqsoft.bpm.document;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pqsoft.documentApp.service.ApplyDossierService;
import com.pqsoft.documentApp.service.ApplyTransferService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class DocumentDossier {

	/**
	 * 档案归档通过
	 *  @author yangxue 2015-08-10
	 * @param DOSSIER_APPLY_ID
	 * @throws Exception
	 */	
	public void yes(String DOSSIER_APPLY_ID) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("STATUS", 2);
		map.put("DOSSIER_APPLY_ID", DOSSIER_APPLY_ID);
		ApplyDossierService service = new ApplyDossierService();
		int count = Dao.selectInt("dossierApp.toCheckedApply", map);
		if(count>0){
			throw new Exception("档案归档，请先将资料清单入柜");
		}		
		service.toUpdateStatus(map);
		List<Map<String,Object>> lookFactor=service.lookFactorList(map);//根据归档申请ID查出相关的数据
		for(Map<String,Object> jam:lookFactor){
			System.out.println("jam:"+jam);
			jam.put("DOSSIER_APPLY_ID", DOSSIER_APPLY_ID);
			Map<String,Object> fileMap=service.toShowFileStatue(jam);//查出归档状态
			if(fileMap!=null){
				jam.put("DOSSIER_STATUS", fileMap.get("CODE"));
			}else{
				jam.put("DOSSIER_STATUS", 0);
			}
			System.out.println("=====fM======"+fileMap);
			service.updateFactorStatue(jam); //修改档案状态0 未申请归档 1已申请未归档 2部分已归档 3全部已归档 
		}
	}
	
	/**
	 * 档案归档不通过
	 *  @author yangxue 2015-08-10
	 * @param DOSSIER_APPLY_ID
	 * @throws Exception
	 */
	public void no(String DOSSIER_APPLY_ID) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("STATUS", -1);
		map.put("DOSSIER_APPLY_ID", DOSSIER_APPLY_ID);
		ApplyDossierService service = new ApplyDossierService();
		service.toUpdateStatus(map);
	}
	
	/**
	 * 档案移交通过
	 * @author yangxue 2015-08-10
	 * @param DETAIL_ID
	 * @throws Exception
	 */
	public void yesTransfer(String TRANSFER_ID) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("STATUS", 3);
		map.put("TRANSFER_ID", TRANSFER_ID);
		map.put("USERCODE", Security.getUser().getCode());
		ApplyTransferService service1 = new ApplyTransferService();
		String dossierApp = service1.getApplyDossier(TRANSFER_ID);
		ApplyDossierService service = new ApplyDossierService();
		map.put("DOSSIER_APPLY_ID", dossierApp);
		service.toUpdateStatus(map);
		map.put("STATUS", 2);
		System.out.println("=========map=====yx======"+map);
		service1.doTransferStatus(map);
	}
	
	/**
	 * 档案移交不通过
	 * @author yangxue 2015-08-10
	 * @param DETAIL_ID
	 * @throws Exception
	 */
	public void noTransfer(String TRANSFER_ID) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("STATUS", -1);
		map.put("TRANSFER_ID", TRANSFER_ID);
		map.put("USERCODE", Security.getUser().getCode());
		System.out.println("=========map=====yx======"+map);
		ApplyTransferService service = new ApplyTransferService();		
		service.doTransferStatus(map);
	}
}
