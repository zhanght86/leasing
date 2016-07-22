package com.pqsoft.bpm.status;

import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * A-B流程状态
 * 
 * @author King 2013-11-4
 */
public class AChangeBStatus {
	
	/**
	 * A-B流程通过
	 * @param jbpmId
	 * @author:King 2013-11-4
	 */
	public void aChangeBPass(String jbpmId) {
		try {
			Map<String,Object> map=JBPM.getVeriable(jbpmId);
			//变更项目表客户id及扣款银行id
			Dao.update("achangeb.updateClient",map);
			//变更项目表开票对象id 当开票对象类型为客户时
			Dao.update("achangeb.updateInvoiceTargetId",map);
			
			map.putAll((Map<? extends String, ? extends Object>) Dao.selectOne("achangeb.doShowMoneyAtoB", map));
			//变更ab记录表信息
			Dao.update("achangeb.updateAchangeB", map);	
			
			//更改留购价款的开票对象信息
			Dao.update("achangeb.updateFundDetailInvoice",map);
		} catch (Exception e) {
			throw new ActionException("A转B过程中失败，请联系管理员");
		}
	}
	

	
	// 刷新项目资料
	public void doAddFileInToProFileTable(String jbpmId){

	}
		// 刷新项目资料
	public void doAddFileInToProFileTable1(Map<String,Object> map){
		projectService service=new projectService();
//		Map<String,Object> map=JBPM.getVeriable(jbpmId);
		//项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		map.put("CLIENT_ID", map.get("NEW_CLIENT_ID"));
		map.put("FILE_TYPE", "0");
		new ProjectContractManagerService().doDelProjectFile(map);
		Map<String, Object> renterDetails =service.selectRenterDetails(map);
		renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
		renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
		renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
		renterDetails.put("PROJECT_ID", map.get("PROJECT_ID"));
		renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
		List<Map<String, Object>> electronicPhotoAlbumData = service.electronicPhotoAlbumData(renterDetails);
		List<Map<String,Object>> listFile = service.selectProjectFileData(map.get("PROJECT_ID").toString());
		for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
			Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
			dataMap.put("CLIENT_ID", renterDetails.get("ID"));
			dataMap.put("TPM_BUSINESS_PLATE", "立项");
//			boolean flag = true;
//			if(listFile != null && !listFile.isEmpty() && listFile.size() > 0){
//				for (int j = 0; j < listFile.size(); j++) {
//					Map<String,Object> mapFile = listFile.get(j);
//					if(mapFile != null && !mapFile.isEmpty()){
//						if(mapFile.containsKey("PDF_PATH") && !mapFile.get("PDF_PATH").equals("")){
//							if(dataMap.containsKey("ORIGINAL_PATH") && !dataMap.get("ORIGINAL_PATH").equals("")){
//								if(mapFile.get("PDF_PATH").equals(dataMap.get("ORIGINAL_PATH"))){
//									flag = false;
//									break;
//								}
//							}
//						}else{
//							if(!dataMap.containsKey("ORIGINAL_PATH") || dataMap.get("ORIGINAL_PATH") == null || dataMap.get("ORIGINAL_PATH").equals("")){
//								flag = false;
//								break;
//							}
//						}
//					}
//				}
//			}
//			if(flag){
				service.addProjectFile(dataMap);
			}
//		}
	}
}
