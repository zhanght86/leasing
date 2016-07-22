package com.pqsoft.base.area.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AreaService {

	private String basePath = "Area.";
	
	// 根据AREA_ID 查询区域子集
	public List<Object> getQuYuSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQuYuSubset", param);
	}

	/**
	 * 查询下级区域 
	 * 返回值类型为列表
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getSubset(Map<String, Object> param) {
		return Dao.selectList(basePath + "selectSubset", param);
	}

	// 插入 区域 数据
	public int insertArea(Map<String, Object> param) {
		String LEVEL_TYPE = param.get("LEVEL_TYPE").toString();
		if (LEVEL_TYPE.equals("quyu")) {
			param.put("AREA_LEVEL", "0");
		}
		if (LEVEL_TYPE.equals("guojia")) {
			param.put("AREA_LEVEL", "1");
		}
		if (LEVEL_TYPE.equals("shengfen")) {
			param.put("AREA_LEVEL", "2");
		}
		if (LEVEL_TYPE.equals("shi")) {
			param.put("AREA_LEVEL", "3");
		}
		if (LEVEL_TYPE.equals("quxian")) {
			param.put("AREA_LEVEL", "4");
		}
		String ID = Dao.getSequence("SEQ_T_SYS_AREA");
		param.put("ID", ID);
		return Dao.insert(basePath + "insertArea", param);
	}
	
	public int updateArea(Map<String, Object> param){
		return Dao.update(basePath + "updateArea", param);
	}

	// 查询一条区域数据
	public Object selectArea(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectArea", param);
	}

	/**
	 * 查询全部国家
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> selectAllCountries() {
		return Dao.selectList(basePath + "selectAllCountries");
	}


	/**
	 * 查询全部区域
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllQuYu() {
		return Dao.selectList(basePath + "getAllQuYu");
	}
	

	/**
	 * 查询全部省份
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllProvince() {
		return Dao.selectList(basePath + "getAllProvince");
	}

	/**
	 * 查询全部市
	 * @param PARENT_ID 下级区域父ID
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getAllCity() {
		return Dao.selectList(basePath + "getAllCity");
	}

	// 删除区域数据
	public boolean deleteArea(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteAREARoot", param) > 0 ? true : false;
	}
	/**
	 * add by lishuo 01-13-2016
	 *  删除区域数据同时删除区域负责人信息
	 * @param param
	 * @return
	 */
	public boolean deleteAreaMan(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteAreaMan", param) > 0 ? true : false;
	}
	// 删除区域配置
	public boolean deleteConfig(Map<String, Object> param) {
		param.put("AREA_ID",param.get("ID"));
		return Dao.update(basePath + "deleteConfig", param) > 0 ? true : false;
	}

	/**
	 * 修改页面调用时需传参数：区域ID 用逗号分隔  eg：1,2,3,4
	 * 添加页面调用时传空值即可
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-3-29  下午02:31:54
	 */
	public static String areaPlug(String AreaId) {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AREA_ID", AreaId);
		if (param.containsKey("AREA_ID") && param.get("AREA_ID") != null && !param.get("AREA_ID").toString().equals("")) {
			String[] AREA_ID = param.get("AREA_ID").toString().split("[,，]");
			String PARENT_ID = "";
			for (int i = 0; i < AREA_ID.length - 1; i++) {
				PARENT_ID = AREA_ID[i];
				if (!PARENT_ID.equals("")) {
					param.put("PARENT_ID", PARENT_ID);
					int j = i + 1;
					context.put("AREA_LIST_" + j, Dao.selectList("Area.selectSubset", param));
					context.put("ID_" + j, AREA_ID[i + 1]);
				}
			}
			param.put("COUNTRIY_ID", AREA_ID[0]);
		}
		context.put("param", param);
		context.put("COUNTRIY_LIST", Dao.selectList("Area.selectAllCountries"));
		return VM.html("base/area/areaPlugin.vm", context);
	}

	// 查询区域列表
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "selectAreaList", param));
		page.addDate(array, Dao.selectInt(basePath + "selectAreaCount", param));
		return page;
	}

	// 通过平台查询省份
	public List<Object> getProvince(Map<String, Object> param) {
		if(!param.containsKey("AREA_LEVEL"))
			param.put("AREA_LEVEL", "3");
		return Dao.selectList(basePath + "selectProvince_FHFA", param);
	}
	
	public List<Object> getShengFenData(Map<String, Object> param) {
		return Dao.selectList(basePath + "getShengFenData", param);
	}
	
	public List<Object> getQSFData(Map<String, Object> param) {
		return Dao.selectList(basePath + "getQSFData", param);
	}
	
	public List<Object> selectShengShi(Map<String, Object> param) {
		return Dao.selectList(basePath + "selectShengShi", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 查询输入的员工姓名
	 */
	public List<Object> SerchUser(Map<String, Object> param) {
		return Dao.selectList(basePath + "SerchUser", param);
	}
	
	/**
	 * add by lishuo 01-11-2016
	 * 查询该用户的ORG_ID
	 */
	public Map<String,Object> queryOrg_ID(Map<String, Object> param) {
		return Dao.selectOne(basePath + "queryOrg_ID", param);
	}
	
	
	/**
	 * add by lishuo 01-07-2016
	 * 查询插入必要的数据
	 */
	public Map<String,Object> SearchAreaManInfo(Map<String, Object> param) {
		return Dao.selectOne(basePath + "SearchAreaManInfo", param);
	}
	
	/**
	 * add by lishuo 01-08-2016
	 * 查询修改的名字
	 */
	public Map<String,Object> SearchForModify(Map<String, Object> param) {
		return Dao.selectOne(basePath + "SearchForModify", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 查询插入表的id
	 */
	public Map<String,Object> SerchIDByName(Map<String, Object> param) {
		return Dao.selectOne(basePath + "SerchIDByName", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 查询插入表的id
	 */
	public Map<String,Object> SerchID(Map<String, Object> param) {
		return Dao.selectOne(basePath + "SerchID", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 查询修改的数据ID
	 */
	public Map<String,Object> queryIDByArea_ID(Map<String, Object> param) {
		return Dao.selectOne(basePath + "queryIDByArea_ID", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 插数据
	 */
	public int insertAreaMan(Map<String, Object> param){
		String ID = Dao.getSequence("SEQ_T_SYS_ORG_ASSIGN_MAN");
		param.put("ID", ID);
		return Dao.insert(basePath + "insertAreaMan", param);
	}
	
	/**
	 * add by lishuo 01-07-2016
	 * 修改区域负责人数据
	 */
	public int updateAreaMan(Map<String, Object> param){
		return Dao.update(basePath + "updateAreaMan", param);
	}
	
	public boolean saveConfig(Map<String, Object> param){
		String AREA_ID = param.get("CONFIG_ID").toString();
		String shengShiIds = param.get("SHENG_SHI_IDS").toString();
		String[] SHENG_SHI_IDS = null;
		if(!shengShiIds.equals("")){
			SHENG_SHI_IDS = (shengShiIds).split(",");
		}
		int i = Dao.update(basePath + "deleteConfig", AREA_ID);
		boolean success = true;
		if (i >= 0) {
			if(SHENG_SHI_IDS != null){
				for (int j = 0 ; j < SHENG_SHI_IDS.length; j++) {
					String SHENG_SHI_ID = SHENG_SHI_IDS[j];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ID", SHENG_SHI_ID);
					map.put("AREA_ID", AREA_ID);
					int k = Dao.update(basePath + "addConfig", map);
					if (k < 0) {
						success = false;
						Dao.rollback();
						break;
					}else{
						int n = Dao.update(basePath + "addConfigSS", map);
						if (n < 0) {
							success = false;
							Dao.rollback();
							break;
						}
					}
				}
			}
		} else {
			success = false;
		}
		return success;
	}
	
	/**
	 * TODO
	 *@requestFunction
	 * 根据条件，拿到区域
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月26日 下午2:06:50
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getAreas(Map<String, Object> param) {
		return Dao.selectList(basePath + "getAreas", param);
	}

}
