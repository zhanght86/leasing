package com.pqsoft.dossier.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.PageUtil;

public class DossierManagerService {
	private String nameSpace = "dossierManager.";

	/**
	 * 查询档案列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-8
	 */
	public Page getDossierManagerList(Map<String, Object> map) {
		map.put("SWBK_TYPE", "PDF模版所属商务板块");
		map.put("YEWU_TYPE", "业务类型");
		map.put("_TYPE", "档案管理系统");
		if(map.containsKey("DOSSIER_TYPE")){
			if("合同档案".equals(map.get("DOSSIER_TYPE"))){
				map.put("DOSSIER_TYPE_FLAG", "1");
			}else
				map.put("DOSSIER_TYPE_FLAG", "2");
		}else{
			map.put("DOSSIER_TYPE", "合同档案");
			map.put("DOSSIER_TYPE_FLAG", "1");
		}
		return PageUtil.getPage(map, nameSpace + "doShowDossierManagerList", nameSpace + "doShowDossierManagerListCount");
	}

	/**
	 * 查询一个档案柜档案袋下的文件列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-8
	 */
	public Page doShowDetailList(Map<String, Object> map) {
		map.put("DOSSIER_STATUS", "档案文件状态");
		map.put("_TYPE", "档案管理系统");
		Page page = new Page(map);
		List<Map<String, Object>> list = Dao.selectList(nameSpace + "doShowDetailList", map);
		page.addDate(JSONArray.fromObject(list), list.size());
		return page;
	}
	
	public List<Map<String,Object>> doShowDetailList1(Map<String, Object> map) {
		map.put("DOSSIER_STATUS", "档案文件状态");
		map.put("_TYPE", "档案管理系统");
		List<Map<String, Object>> list = Dao.selectList(nameSpace + "doShowDetailList", map);
		return list;
	}
	
	public void getPicture(Map<String, Object> param) {
		FileCatalogUtil fcu = new FileCatalogUtil();
		param.put("RENTER_NAME", param.get("CLIENT_NAME"));
		param.put("RENTER_CODE", param.get("CUST_ID"));
		param.put("FILE_PATH", param.get("PROJECT_CODE")+"/"+param.get("DOSSIER_FILE_TYPE")+"/"+param.get("FILE_NAME"));
		{
			if(param !=null && !param.isEmpty() && param.containsKey("RENTER_NAME") && param.containsKey("RENTER_CODE") && param.containsKey("FILE_PATH")){
				String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString(),true);
				List<Map<String,Object>> listMap = fcu.selectFileList(catalogId);
				if(listMap != null && !listMap.isEmpty() && listMap.size() > 0){
					param.put("PICTURE", "YES");
				}else{
					param.put("PICTURE", "NO");
				}
			}
		}
	}
	
	/**
	 * 更新档案存放表状态
	 * @param DOSSIER_ID 档案存放表id
	 * @author:King 2013-10-13
	 */
	public int doUpdateDossierStatusById(String DOSSIER_ID,String STATUS){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("DOSSIER_ID", DOSSIER_ID);
		map.put("STATUS", STATUS);
		return Dao.update(nameSpace+"doUpdateDossierStatusById", map);
	}
	/**
	 * 根据ID产查看档案存放表明细
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-30  上午11:57:21
	 */
	public Map<String,Object> selectDossierStorage(Map<String,Object> param){
		return Dao.selectOne(nameSpace+"selectDossierStorage",param);
	}
	
	/**
	 * 获取档案业务列表
	 * @param map
	 * @return
	 * @author:King 2014-3-3
	 */
	public Page doMgDossierLedger(Map<String,Object>map){
		return PageUtil.getPage(map, nameSpace+"doMgDossierLedgerList", nameSpace+"doMgDossierLedgerCount");
	}
	
	/**
	 * 获取档案业务台账列表
	 * @param map
	 * @return
	 * @author:King 2014-3-4
	 */
	public List<Map<String,Object>> doDossierLedgerList(Map<String,Object> map){
		return Dao.selectList(nameSpace+"doMgDossierLedgerList", map);
	}
	
	/**
	 * 导出档案业务台账数据
	 * @param param
	 * @author:King 2014-3-4
	 */
	public Excel doExpDossierLedger (Map<String,Object> param){
		//数据
		List<Map<String,Object>> list=this.doDossierLedgerList(param);
		//表头		
		String columnString="项目编号,供应商名称,客户名称,产品名称,台量,归档日期,A00,A01,A03,A04,A06,A07,三方A48,四方A49,C05,C06,C13,C15,保单,合格证,发动机证,绿本,发票,其他,备注";
		String columnName="PROJECT_CODE,SUP_SHORTNAME,CLIENT_NAME,PRODUCT_NAME,AMOUNT,CREATE_DATE,A00,A01,A03,A04,A06,A07,A48,A49,C05,C06,C13,C15,BD,HGZ,FDJZ,LB,FP,QT,REMARK";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excel		
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("档案业务台账"+DateUtil.getSysDate("yyyyMMdd")+".xls");
		excel.addSheetName("档案业务台账");
		excel.addData(list);
		return excel;
	}
	
	/**
	 * 接收档案文件
	 * @param ID
	 * @return
	 * @author King 2014年8月10日
	 */
	public int doRecieveFile(Object ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		map.put("STATUS", "0");
		return Dao.update(nameSpace+"doRecieveFile",map);
	}
	
	/**
	 * 档案入柜
	 * @param map
	 * @return
	 * @author King 2014年8月10日
	 */
	public int dossierFileRG(Map<String,Object> map){
		return Dao.update(nameSpace+"dossierFileRG", map);
	}

	/**
	 * 撤消改变
	 * @param map
	 * @return
	 * @author pei 2015年6月25日
	 */
	public int doDestoryFile(Object ID) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		map.put("STATUS", "4");
		return Dao.update(nameSpace+"doRecieveFile",map);
	}
	
	
	public List<Map<String,Object>> getpayment(Map<String,Object> map){
		return Dao.selectList(nameSpace+"getpayment",map);
	}
}
