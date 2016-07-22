package com.pqsoft.bpm.status;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.StringUtils;

/**
 * 设备拆分多条数据保存
 * 
 * @author King 2013-10-19
 */
public class EquipmentSplit {

	/**
	 * 项目设备拆分
	 * 
	 * @param JBPM_ID
	 * @author:King 2013-10-19
	 */
	public void equipmentSplitByJbpmId(String JBPM_ID) {
		Map<String, Object> param = JBPM.getVeriable(JBPM_ID);
		Dao.update("project.updateEqNamesInfo", param);
		List<Map<String, Object>> list = Dao.selectList("project.doShowEquipmentMoreByProjectId", param.get("PROJECT_ID") + "");
		if (!list.isEmpty() && list.size() > 0) {
			for (Map<String, Object> map : list) {
				int amount = Integer.parseInt(map.get("AMOUNT") + "");
				if (!map.containsKey("INSURE_MONEY") || (map.containsKey("INSURE_MONEY") && StringUtils.isBlank(map.get("INSURE_MONEY")))) {
					map.put("INSURE_MONEY", 0);
				} else
					map.put("INSURE_MONEY", Double.parseDouble(map.get("INSURE_MONEY") + "") / amount);
				map.put("TOTAL_PRICE", map.get("UNIT_PRICE"));
				map.put("AMOUNT", "1");
				for (int i = 0; i < amount; i++) {
					if (map.get("CERTIFICATE_DATE") != null) {
						Date date = (Date) map.get("CERTIFICATE_DATE");
						if (date != null)
							map.put("CERTIFICATE_DATE", UTIL.FORMAT.date(date, "yyyy-MM-dd"));
					}
					Dao.insert("project.insertEquipmentSplit", map);
				}
			}
		}
	}
	
	/**
	 * 更新项目主表设备相关信息
	 * @param JBPM_ID
	 * @author:King 2013-11-14
	 */
	public void projectAppendEqComSupInfo(String JBPM_ID) {
		Map<String, Object> param = JBPM.getVeriable(JBPM_ID);
		projectAppendEqComSupInfoByProjectId(param.get("PROJECT_ID"));
	}
	
	/**
	 * 更新项目主表设备相关信息
	 * @param PROJECT_ID
	 * @author:King 2013-11-14
	 */
	public void projectAppendEqComSupInfoByProjectId(Object PROJECT_ID){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		List<Map<String, Object>> list = Dao.selectList("project.projectAppendEqComSupInfo", param);
		Map<String,Object> scheme=new HashMap<String,Object>();
		scheme.put("PROJECT_ID", PROJECT_ID);
		scheme.put("GUARANTEE_MODEL", "GUARANTEE_MODEL");
		scheme=Dao.selectOne("project.getGuaranteeModel", scheme);
//		String GUARANTEE_MODEL=null==scheme.get("VALUE_STR")?"":scheme.get("VALUE_STR").toString();
//		System.out.println("GUARANTEE_MODEL="+GUARANTEE_MODEL);
		StringBuffer SUPPLIERS_ID = new StringBuffer();
		StringBuffer SUP_SHORTNAME = new StringBuffer();
		StringBuffer COMPANY_ID = new StringBuffer();
		StringBuffer COMPANY_NAME = new StringBuffer();
		StringBuffer BUSINESS_SECTOR = new StringBuffer();
		StringBuffer PRODUCT_NAME = new StringBuffer();
		StringBuffer ENGINE_TYPE = new StringBuffer();
		StringBuffer WHOLE_ENGINE_CODE = new StringBuffer();
		StringBuffer AREA_NAME = new StringBuffer();
		StringBuffer EQUIPMENINFOS = new StringBuffer();
		StringBuffer CAR_SYMBOL = new StringBuffer();
		StringBuffer TYPE_NAME = new StringBuffer();
		int AMOUNT = 0;
		int i=0;
		for (Map<String, Object> map : list) {
			if (SUPPLIERS_ID.indexOf(map.get("SUPPLIERS_ID") + "") == -1)
				SUPPLIERS_ID.append(map.get("SUPPLIERS_ID")).append(",");
			if (SUP_SHORTNAME.indexOf(map.get("SUP_SHORTNAME") + "") == -1)
				SUP_SHORTNAME.append(map.get("SUP_SHORTNAME")).append(",");
			if (COMPANY_ID.indexOf(map.get("COMPANY_ID") + "") == -1)
				COMPANY_ID.append(map.get("COMPANY_ID")).append(",");
			if (COMPANY_NAME.indexOf(map.get("COMPANY_NAME") + "") == -1)
				COMPANY_NAME.append(map.get("COMPANY_NAME")).append(",");
			if (BUSINESS_SECTOR.indexOf(map.get("BUSINESS_SECTOR") + "") == -1)
				BUSINESS_SECTOR.append(map.get("BUSINESS_SECTOR")).append(",");
			if (PRODUCT_NAME.indexOf(map.get("PRODUCT_NAME") + "") == -1)
				PRODUCT_NAME.append(map.get("PRODUCT_NAME")).append(",");
			if (ENGINE_TYPE.indexOf(map.get("ENGINE_TYPE") + "") == -1&&StringUtils.isNotBlank(map.get("ENGINE_TYPE")))
				ENGINE_TYPE.append(map.get("ENGINE_TYPE")).append(",");
			if (WHOLE_ENGINE_CODE.indexOf(map.get("WHOLE_ENGINE_CODE") + "") == -1&&StringUtils.isNotBlank(map.get("WHOLE_ENGINE_CODE")))
				WHOLE_ENGINE_CODE.append(map.get("WHOLE_ENGINE_CODE")).append(",");
			if (AREA_NAME.indexOf(map.get("AREA_NAME") + "") == -1)
				AREA_NAME.append(map.get("AREA_NAME")).append(",");
			if(TYPE_NAME.indexOf(map.get("TYPE_NAME") + "") == -1&&StringUtils.isNotBlank(map.get("TYPE_NAME")))
				TYPE_NAME.append(map.get("TYPE_NAME")).append(",");
			AMOUNT += Integer.parseInt(map.get("AMOUNT") + "");
			//只更新一条数据
			if (EQUIPMENINFOS.indexOf(map.get("EQUIPMENINFOS") + "") == -1&&i==0){
				EQUIPMENINFOS.append(map.get("PRODUCT_NAME")).append("/").append(map.get("TYPE_NAME")).append("/");
				if(StringUtils.isNotBlank(map.get("EQUIPMENINFOS")))
					EQUIPMENINFOS.append(map.get("WHOLE_ENGINE_CODE")).append(",");
				i++;
			}
			if (CAR_SYMBOL.indexOf(map.get("CAR_SYMBOL") + "") == -1&&StringUtils.isNotBlank(map.get("CAR_SYMBOL")))
				CAR_SYMBOL.append(map.get("CAR_SYMBOL")).append(",");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUP_ID", SUPPLIERS_ID.substring(0, 1 <= SUPPLIERS_ID.length() ? SUPPLIERS_ID.length() - 1 : SUPPLIERS_ID.length()));
		map.put("SUP_SHORTNAME", SUP_SHORTNAME.substring(0, 1 <= SUP_SHORTNAME.length() ? SUP_SHORTNAME.length() - 1 : SUP_SHORTNAME.length()));
		map.put("COMPANY_ID", COMPANY_ID.substring(0, 1 <= COMPANY_ID.length() ? COMPANY_ID.length() - 1 : COMPANY_ID.length()));
		map.put("COMPANY_NAME", COMPANY_NAME.substring(0, 1 <= COMPANY_NAME.length() ? COMPANY_NAME.length() - 1 : COMPANY_NAME.length()));
		map.put("BUSINESS_SECTOR", BUSINESS_SECTOR.substring(0, 1 <= BUSINESS_SECTOR.length() ? BUSINESS_SECTOR.length() - 1 : BUSINESS_SECTOR.length()));
		map.put("PRODUCT_NAME", PRODUCT_NAME.substring(0, 1 <= PRODUCT_NAME.length() ? PRODUCT_NAME.length() - 1 : PRODUCT_NAME.length()));
		map.put("ENGINE_TYPE", ENGINE_TYPE.substring(0, 1 <= ENGINE_TYPE.length() ? ENGINE_TYPE.length() - 1 : ENGINE_TYPE.length()));
		map.put("TYPE_NAME", TYPE_NAME.substring(0, 1 <= TYPE_NAME.length() ? TYPE_NAME.length() - 1 : TYPE_NAME.length()));
		map.put("WHOLE_ENGINE_CODE", WHOLE_ENGINE_CODE.substring(0, 1 <= WHOLE_ENGINE_CODE.length() ? WHOLE_ENGINE_CODE.length() - 1 : WHOLE_ENGINE_CODE.length()));
		map.put("AREA_NAME", AREA_NAME.substring(0, 1 <= AREA_NAME.length() ? AREA_NAME.length() - 1 : AREA_NAME.length()));

		map.put("AMOUNT", getAMOUNT(PROJECT_ID+"",AMOUNT,PRODUCT_NAME+"",null));
		map.put("EQUIPMENINFOS", EQUIPMENINFOS.substring(0, 1 <= EQUIPMENINFOS.length() ? EQUIPMENINFOS.length() - 1 : EQUIPMENINFOS.length()));
		map.put("CAR_SYMBOL", CAR_SYMBOL.substring(0, 1 <= CAR_SYMBOL.length() ? CAR_SYMBOL.length() - 1 : CAR_SYMBOL.length()));
		map.put("PROJECT_ID", param.get("PROJECT_ID"));
		Dao.update("project.updateProjectEqComSup", map);
		
		//维护设备表设备名称、型号名称、系别名称中文
		Dao.update("project.updateEquZn", map);
	}

	/**
	 * 分期销售模式的设备分期单价，用于发票
	 * 
	 * @param jbpmId
	 * @author:King 2013-11-6
	 */
	@SuppressWarnings("unchecked")
	public void updateAveragePrice(String jbpmId) {
		try {
			Map<String, Object> map = JBPM.getVeriable(jbpmId);
			List<Map<String, Object>> list = Dao.selectList("project.doShowEqUnitPrice", map);
			map.put("PMT", "PMT租金");
			map.put("DBF", "担保费");
			map.put("SXF", "手续费");
			map.put("LGJ", "留购价款");
			map.put("QZZJ", "起租租金");
			map.putAll((Map<? extends String, ? extends Object>) Dao.selectOne("project.doShowUpdateAveragePrice", map));
			String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
			// 分期模式
			if (PROJECT_MODEL.equals("3")) {
				Double percent = Double.parseDouble(null == map.get("PERCENT") ? "1" : map.get("PERCENT") + "");
				Double ALLMONEY = Double.parseDouble(null == map.get("ALLMONEY") ? "1" : map.get("ALLMONEY") + "");
				for (Map<String, Object> map2 : list) {
					double AVERAGE_PRICE = Math.round(Double.parseDouble(map2.get("UNIT_PRICE") + "") * percent);
//					double AVERAGE_PRICE = Math.round(Double.parseDouble(map2.get("UNIT_PRICE") + "") * percent * 100);
//					AVERAGE_PRICE = AVERAGE_PRICE / 100;
					map2.put("AVERAGE_PRICE", AVERAGE_PRICE);
					ALLMONEY -= AVERAGE_PRICE;
				}
				Double AVERAGE_PRICE = Double.parseDouble(list.get(list.size() - 1).get("AVERAGE_PRICE") + "");
				AVERAGE_PRICE += ALLMONEY;
				list.get(list.size() - 1).put("AVERAGE_PRICE", AVERAGE_PRICE);
				for (Map<String, Object> map2 : list) {
					Dao.update("project.updateAveragePrice", map2);
				}
			}
		} catch (Exception e) {
			throw new ActionException("分期模式费用分摊过程错误");
		}
	}
	
	
	/**
	 * 分期销售模式的设备分期单价，用于发票
	 * 
	 * @param jbpmId
	 * @author:King 2013-11-6
	 */
	@SuppressWarnings("unchecked")
	public void updateAveragePriceLX(Map<String, Object> map) {
		map.put("PROJECT_ID",map.get("project_id"));
		try {
			List<Map<String, Object>> list = Dao.selectList("project.doShowEqUnitPrice", map);
			map.put("PMT", "PMT租金");
			map.put("DBF", "担保费");
			map.put("SXF", "手续费");
			map.put("LGJ", "留购价款");
			map.put("QZZJ", "起租租金");
			map.putAll((Map<? extends String, ? extends Object>) Dao.selectOne("project.doShowUpdateAveragePrice", map));
			String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
			// 分期模式
			if (PLATFORM_TYPE.equals("3")) {
				Double percent = Double.parseDouble(null == map.get("PERCENT") ? "1" : map.get("PERCENT") + "");
				Double ALLMONEY = Double.parseDouble(null == map.get("ALLMONEY") ? "1" : map.get("ALLMONEY") + "");
				for (Map<String, Object> map2 : list) {
					double AVERAGE_PRICE = Math.round(Double.parseDouble(map2.get("UNIT_PRICE") + "") * percent );
//					double AVERAGE_PRICE = Math.round(Double.parseDouble(map2.get("UNIT_PRICE") + "") * percent * 100);
//					AVERAGE_PRICE = AVERAGE_PRICE / 100;
					map2.put("AVERAGE_PRICE", AVERAGE_PRICE);
					ALLMONEY -= AVERAGE_PRICE;
				}
				Double AVERAGE_PRICE = Double.parseDouble(list.get(list.size() - 1).get("AVERAGE_PRICE") + "");
				AVERAGE_PRICE += ALLMONEY;
				list.get(list.size() - 1).put("AVERAGE_PRICE", AVERAGE_PRICE);
				for (Map<String, Object> map2 : list) {
					Dao.update("project.updateAveragePrice", map2);
				}
			}
		} catch (Exception e) {
			throw new ActionException("分期模式费用分摊过程错误");
		}
	}
	
	/**
	 * 获取设备数量
	 * @param PROJECT_ID
	 * @param AMOUNT
	 * @param PRODUCT_NAME 设备名称
	 * @param GUARANTEE_MODE 担保模式CODE
	 * @return
	 * @author:King 2013-11-20
	 */
	public int getAMOUNT(String PROJECT_ID,int AMOUNT,String PRODUCT_NAME,String GUARANTEE_MODE){
//		if(!PRODUCT_NAME.equals("资产包")&&GUARANTEE_MODE!=null){
//			if(!PRODUCT_NAME.equals("资产包")&&GUARANTEE_MODE!=null&&GUARANTEE_MODE.equals("B_MODE")){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("XH1", "SX4-G(有挂车)");
			map.put("XH2", "BGC(挂车)");
			map.put("XH3", "BGC_01");
			map.put("XH4", "BGC");
			map.put("PROJECT_ID", PROJECT_ID);
			AMOUNT=Dao.selectInt("project.getEqAMOUNT", map);
//			return AMOUNT;
//		}else{
			return AMOUNT;
//		}
	}
}
