package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.crm.service.CustomerService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.StringUtils;

/**
 * 项目主表更新
 * @author King 2013-11-11
 */
public class ProjectHeadUpdateForJbpm {
	
	/**
	 * 更新信审批准日期
	 * @param jbpmId
	 * @author:King 2013-11-11
	 */
	public void updateCreditRatifyDate(String jbpmId){
		Map<String,Object> map=JBPM.getVeriable(jbpmId);
		Dao.update("project.updateCreditRatifyDate", map);
	}
	/**
	 * 更新项目方案表静态中文数据
	 * @param PROJECT_ID
	 * @author:King 2013-11-17
	 */
	public void doUpdateSchemeValueFlag(String jbpmId){
		Map<String,Object> param=JBPM.getVeriable(jbpmId);
		String PROJECT_ID=param.get("PROJECT_ID")+"";
		String AFFILIATED_COMPANY_ID=Dao.selectOne("project.queryProjectAFFILIATED", PROJECT_ID);
		if(!StringUtils.isBlank(AFFILIATED_COMPANY_ID)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("PROJECT_ID", PROJECT_ID);
			map.put("AFFILIATED_COMPANY_ID", AFFILIATED_COMPANY_ID);
			Dao.update("project.updateEqAFFILIATED_COMPANY", map);
		}
		doUpdateSchemeValueFlagByProjectId(PROJECT_ID);
//		projectService service = new projectService();
//			// 刷新项目资料
//				//项目信息
//				Map<String, Object> projectDetails = service.selectProjectDetails(param);
//				//承租人详细信息
//				Map<String, Object> renterDetails = service.selectRenterDetails(projectDetails);
//				renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
//				renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
//				renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
//				renterDetails.put("PROJECT_ID", param.get("PRO_ID"));
//				renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
//				List<Map<String, Object>> electronicPhotoAlbumData = service.electronicPhotoAlbumData(renterDetails);
//				List<Map<String,Object>> listFile = service.selectProjectFileData(param.get("PRO_ID").toString());
//				for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
//					Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
//					dataMap.put("CLIENT_ID", renterDetails.get("ID"));
//					dataMap.put("TPM_BUSINESS_PLATE", "立项");
//					boolean flag = true;
//					if(listFile != null && !listFile.isEmpty() && listFile.size() > 0){
//						for (int j = 0; j < listFile.size(); j++) {
//							Map<String,Object> mapFile = listFile.get(j);
//							if(mapFile != null && !mapFile.isEmpty()){
//								if(mapFile.containsKey("PDF_PATH") && !mapFile.get("PDF_PATH").equals("")){
//									if(dataMap.containsKey("ORIGINAL_PATH") && !dataMap.get("ORIGINAL_PATH").equals("")){
//										if(mapFile.get("PDF_PATH").equals(dataMap.get("ORIGINAL_PATH"))){
//											flag = false;
//											break;
//										}
//									}
//								}else{
//									if(!dataMap.containsKey("ORIGINAL_PATH") || dataMap.get("ORIGINAL_PATH") == null || dataMap.get("ORIGINAL_PATH").equals("")){
//										flag = false;
//										break;
//									}
//								}
//							}
//						}
//					}
//					if(flag){
//						service.addProjectFile(dataMap);
//					}
//				}
//		
	}
	/**
	 * 更新项目方案表保存的数据字典code值对应的flag值
	 * @param PROJECT_ID
	 * @author:King 2013-11-17
	 */
	public void doUpdateSchemeValueFlagByProjectId(String PROJECT_ID){
		Dao.update("project.doUpdateSchemeValueFlag", PROJECT_ID);
	}
	
	//
	public void doCreateCredit(String PROJECT_ID){
		//查询客户ID
		Map map=Dao.selectOne("project.queryClentByID", PROJECT_ID);
		if(map!=null)
		{
			map.put("PROJECT_ID", PROJECT_ID);
			//先判断有没有数据，有的话就不insert
			int num=Dao.selectInt("project.doqueryCreateDate", map);
			if(num==0){
				Dao.insert("project.doCreateCredit",map);
			}
			
		}
		
		//修改状态
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("PROJECT_ID", PROJECT_ID);
		m.put("STATUS", "10");
		Dao.update("bpm.status.updateProjectStatus", m);
		/*****销售型售后回租，状态要同步更新******/
		Dao.update("bpm.status.updateProjectStatus_tb", m);
		
	}
	
	//资信调查可以修改资料，当修改资料后会写到资料表中
	//资信通过
	public void doCreatePass(String PROJECT_ID){
		
		//查询客户ID
		// 刷新项目资料
		Map map=new HashMap();
		map.put("PROJECT_ID", PROJECT_ID);
		projectService service=new projectService();
		// 项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		// 承租人详细信息
		Map<String, Object> renterDetails = service.selectRenterDetails(projectDetails);
		renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
		renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
		renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
		renterDetails.put("PROJECT_ID", PROJECT_ID);
		renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
		
		//判断必选资料上船没
		Map mapZL=new HashMap();
		mapZL.put("PRO_CODE", projectDetails.get("PRO_CODE"));
		mapZL.put("TYPE_", "ZX");
		List<Map<String, Object>> listXMZLFJ = new CustomerService().getProjectFileListSrc(mapZL);
		boolean uploudBool=true;
		String FILE_NAME="";
		
		FileCatalogUtil fcu = new FileCatalogUtil();
		for (int i = 0; i < listXMZLFJ.size(); i++) {
			Map<String, Object> xmzlfjMap = listXMZLFJ.get(i);
			Map FILEMAP=new HashMap();
			
				if (xmzlfjMap!=null && xmzlfjMap.get("CODE_TYPE_FLAG")!=null && !xmzlfjMap.get("CODE_TYPE_FLAG").equals("") && xmzlfjMap.get("CODE_TYPE_FLAG").toString().equals("1")) {//1必选
					FILEMAP.put("FILE_PATH", projectDetails.get("PRO_CODE") + "/项目资料附件清单/" + xmzlfjMap.get("CODE"));
					String catalogId = fcu.getCatalogIdByPath(renterDetails.get("NAME").toString(), renterDetails.get("CUST_CODE").toString(), FILEMAP.get("FILE_PATH").toString(), false);
					List<Map<String, Object>> listMap = fcu.selectFileList(catalogId);
					FILE_NAME= xmzlfjMap.get("CODE")+"";
					if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
						;
					} else {
						uploudBool=false;
						break;
					}
					
				}
			
		}
		if(!uploudBool){
			throw new ActionException("请上传资料-"+FILE_NAME+"!");
		}
		
		
		List<Map<String, Object>> electronicPhotoAlbumData = service.electronicPhotoAlbumData(renterDetails);
		//先删后写入
		service.deleteProjectFile(renterDetails);
		for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
			Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
			dataMap.put("CLIENT_ID", renterDetails.get("ID"));
			dataMap.put("TPM_BUSINESS_PLATE", "立项");
			service.addProjectFile(dataMap);
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("PROJECT_ID", PROJECT_ID);
		m.put("STATUS", "7");
		Dao.update("bpm.status.updateProjectStatus", m);
		/*****销售型售后回租，状态要同步更新******/
		Dao.update("bpm.status.updateProjectStatus_tb", m);
		
	}
	
	//资信调查可以修改资料，当修改资料后会写到资料表中
	//资信通过
	public void doCreateBack(String PROJECT_ID){
		//查询客户ID
		// 刷新项目资料
		Map map=new HashMap();
		map.put("PROJECT_ID", PROJECT_ID);
		projectService service=new projectService();
		// 项目信息
		Map<String, Object> projectDetails = service.selectProjectDetails(map);
		// 承租人详细信息
		Map<String, Object> renterDetails = service.selectRenterDetails(projectDetails);
		renterDetails.put("RENTER_NAME", renterDetails.get("NAME"));
		renterDetails.put("RENTER_CODE", renterDetails.get("CUST_CODE"));
		renterDetails.put("CUSTOMER_TYPE", renterDetails.get("TYPE"));
		renterDetails.put("PROJECT_ID", PROJECT_ID);
		renterDetails.put("PRO_CODE", projectDetails.get("PRO_CODE"));
		List<Map<String, Object>> electronicPhotoAlbumData = service.electronicPhotoAlbumData(renterDetails);
		//先删后写入
		service.deleteProjectFile(renterDetails);
		for (int i = 0; i < electronicPhotoAlbumData.size(); i++) {
			Map<String, Object> dataMap = electronicPhotoAlbumData.get(i);
			dataMap.put("CLIENT_ID", renterDetails.get("ID"));
			dataMap.put("TPM_BUSINESS_PLATE", "立项");
			service.addProjectFile(dataMap);
		}
		
	}
}
