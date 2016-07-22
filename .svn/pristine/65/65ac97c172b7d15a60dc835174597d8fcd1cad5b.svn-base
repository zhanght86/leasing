package com.pqsoft.condition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bailoutCondition.service.BailoutConditionService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class ConditionService {

	private final String xmlPath = "condition.";

	/**
	 * 融资方式管理-管理页面数据 toMgFhfaData
	 * 
	 * @date 2013-10-11 下午07:35:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgConditionData(Map<String, Object> map) {
		return PageUtil.getPage(map, xmlPath + "toMgCondition", xmlPath
				+ "toMgConditionCount");
	}

	/**
	 * 添加条件
	 * 
	 * @param map
	 * @return
	 */
	public int insetConditionService(Map<String, Object> map) {
		map.put("CONDITIONTYPENAME", "其它类型");
		map.put("CREATE_ID", Security.getUser().getId());
		map.put("ORG_ID", Security.getUser().getOrg().getId());
		return Dao.insert(xmlPath + "INSERT_CONDITION", map);
	}

	/**
	 * 修改条件
	 * 
	 * @param map
	 * @return
	 */
	public int updataConditionService(Map<String, Object> map) {
		return Dao.update(xmlPath + "UPDATE_CONDITION", map);

	}

	/**
	 * 查询某一条条件信息
	 * 
	 * @param map
	 * @return
	 */
	public Object getOneCoditionService(Map<String, Object> map) {
		return map;

	}

	/**
	 * 查询数据字典
	 * 
	 * @return
	 */
	public Object queryDataDictionary() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lesseetype", "客户类型");
		map.put("devicetype", "设备类型");
		map.put("lesseetypefiles", "承租人档案");
		map.put("configtypefiles", "资料清单类型");
		return Dao.selectList(xmlPath + "queryDataDictionaryForName", map);

	}

	/**
	 * 写入数据源字典条件信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int insertDataDictionary() {
		int sizese = 0; // 记录添加了多少条数据
		List<Map<String, Object>> dataDictionarylist = (List<Map<String, Object>>) queryDataDictionary();
		Map<String, Object> conditionmap = new HashMap<String, Object>();

		//查询该平台下所有的融资条件  用于判断哪些需要同步  哪些不需要同步
		List<Map<String, Object>> allst = (List<Map<String, Object>>) new BailoutConditionService()
				.getAllConditionService();
		try {
			for (int i = 0; i < dataDictionarylist.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) dataDictionarylist
						.get(i);
				boolean flag = true;
				for (Map<String, Object> map1 : allst) {
					String CNAME = map.get("FLAG") + "";
					if (CNAME.equals(map1.get("CNAME")))
						flag = false;
				}
				if (flag) {
					map.put("SOURCETYPE", "tdata");
					conditionmap.put("CONDITIONID", map.get("DATA_ID"));
					conditionmap.put("CONDITIONTYPENAME", map.get("TYPE"));
					conditionmap.put("CNAME", map.get("FLAG"));
					conditionmap.put("SOURCETYPE", "tdata");
					conditionmap.put("CREATE_ID", Security.getUser().getId());
					conditionmap.put("ORG_ID", Security.getUser().getOrg()
							.getId());
					Dao.insert(xmlPath + "INSERT_CONDITION", conditionmap);
					sizese++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		// List<Map<String,Object>> pdfFileList =
		// Dao.selectList(xmlPath+"queryPdfFileList");
		// for(int i=0;i<pdfFileList.size();i++){
		// Map<String,Object> map=pdfFileList.get(i);
		// map.put("SOURCETYPE", "pdf");
		// map.put("DATA_ID", map.get("ID"));
		// List list =
		// Dao.selectList("refund.condition.selectCIDCondition",map);
		// if(list==null||list.size()==0){
		// conditionmap.put("CONDITIONID",map.get("DATA_ID"));
		// conditionmap.put("CONDITIONTYPENAME", "合同档案类型");
		// conditionmap.put("CNAME",map.get("FILE_NAME"));
		// conditionmap.put("SOURCETYPE", "pdf");
		// conditionmap.put("CREATE_ID", Security.getUser().getId());
		// conditionmap.put("ORG_ID", Security.getUser().getOrg().getId());
		// Dao.insert("refund.condition.INSERT_CONDITION", conditionmap);
		// sizese++;
		// }
		// }
		return sizese;

	}
}
