package com.pqsoft.Code.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CodeService {

	private String basePath = "Code.";
	public Page getCodeManagerApply(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"queryCodeManager",param));
		page.addDate(array, Dao.selectInt(basePath+"queryCodeManagerCount", param));
		return page;
	}
	
	public int addCode(Map m)
	{
		return Dao.insert(basePath+"addCode",m);
	}
	
	public int delCode(Map m)
	{
		return Dao.delete(basePath+"delCode",m);
	}
	
	public int doUpdateCode(Map m)
	{
		return Dao.update(basePath+"doUpdateCode",m);
	}
	public static SimpleDateFormat sdfy =new SimpleDateFormat("yyyy");
	public static SimpleDateFormat sdfm =new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat sdfd =new SimpleDateFormat("yyyy-MM-dd");
	public static String getCode(String type, String clientId, String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TYPE", type);
		map.put("ID", projectId);
		Map<String, Object> m = (Map<String, Object>) Dao.selectOne("Code.getCode", type);
		if (m == null) throw new RuntimeException("无法获取对应类型的编码规则:" + type);
		/*{
			Date RESET_DATE = (Date) m.get("RESET_DATE");
			if(RESET_DATE==null){
				Dao.update("Code.resetCode", map);
				return getCode(type, clientId, projectId);
			}
			Date now = new Date();
			if ("1".equals(m.get("RESET_TYPE"))) {
				String flag1 = sdfd.format(RESET_DATE);
				String flag2 = sdfd.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
					return getCode(type, clientId, projectId);
				}
			}else if("2".equals(m.get("RESET_TYPE"))){
				String flag1 = sdfm.format(RESET_DATE);
				String flag2 = sdfm.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
					return getCode(type, clientId, projectId);
				}
			}else if("3".equals(m.get("RESET_TYPE"))){
				String flag1 = sdfy.format(RESET_DATE);
				String flag2 = sdfy.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
					return getCode(type, clientId, projectId);
				}
			}
		}*/
		String exp = (String) m.get("EXPRESSION");
		if (exp == null) throw new RuntimeException("无法获取对应类型的编码规则:" + type);
		if (exp.indexOf("{流水号}") > 0) {
			int SERIAL_LEN = Integer.parseInt(m.get("SERIAL_LEN").toString());
			String SERIAL = m.get("SERIAL").toString();
			int c = SERIAL_LEN - SERIAL.length();
			for (int i = 0; i < c; i++) {
				SERIAL = "0" + SERIAL;
			}
			exp = exp.replaceAll("\\{流水号\\}", SERIAL);
		}
		if (exp.indexOf("{业务类型}") >= 0) {
			if (projectId == null) throw new RuntimeException("无对应项目");
			String t = Dao.selectOne("Code.getPlatform", projectId);
			if (t == null) throw new RuntimeException("读取编码失败，无法匹配业务类型");
			exp = exp.replaceAll("\\{业务类型\\}", t);
		}
//		if (exp.indexOf("{项目类型}") >= 0) {
//			if (projectId == null) throw new RuntimeException("无对应项目");
//			String t = Dao.selectOne("Code.getProjectType", projectId);
//			if (t == null) throw new RuntimeException("读取编码失败，无法匹配项目类型");
//			exp = exp.replaceAll("\\{项目类型\\}", t);
//		}
		if (exp.indexOf("{年份}") >= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			exp = exp.replaceAll("\\{年份\\}", ((Integer) calendar.get(Calendar.YEAR)).toString());
		}
		if (exp.indexOf("{月份}") >= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int cm = (Integer) calendar.get(Calendar.MONTH);
			cm = cm + 1;
			exp = exp.replaceAll("\\{月份\\}", cm < 10 ? ("0" + cm) : cm + "");
		}
		if (exp.indexOf("{日期}") >= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			exp = exp.replaceAll("\\{日期\\}", ((Integer) calendar.get(Calendar.DATE)).toString());
		}
		if (exp.indexOf("{省份简称}") >= 0) {
			if (projectId == null) throw new RuntimeException("无对应项目");
			String t = Dao.selectOne("Code.getAreaShortName", projectId);
			if (t == null) throw new RuntimeException("读取编码失败，无法匹配项目省份简称");
			exp = exp.replaceAll("\\{省份简称\\}", t);
		}
		if (exp.indexOf("{客户类型}") >= 0) {
			String t = null;
			if (projectId == null && clientId == null) throw new RuntimeException("无对应项目");
			if (clientId != null) {
				t = Dao.selectOne("Code.getClientType", clientId);
			}
			if (t == null || projectId != null) {
				t = Dao.selectOne("Code.getClientTypeByPro", projectId);
			}
			if (t == null) throw new RuntimeException("读取编码失败，无法匹配客户类型");
			exp = exp.replaceAll("\\{客户类型\\}", t);
		}
		if (exp.indexOf("{项目编号}") >= 0) {
			if (projectId == null) throw new RuntimeException("无对应项目");
			String t = Dao.selectOne("Code.getProCode", projectId);
			if (t == null) throw new RuntimeException("读取编码失败，无法匹配项目编号");
			exp = exp.replaceAll("\\{项目编号\\}", t);
		}
		if (exp.indexOf("{融资租赁合同编号}") >= 0) {
			if (projectId == null) throw new RuntimeException("无对应项目");
			String t = Dao.selectOne("Code.getLeaseCode", projectId);
			if (t == null) throw new RuntimeException("读取编码失败，无法匹配融资租赁合同编号");
			exp = exp.replaceAll("\\{融资租赁合同编号\\}", t);
		}
		if (exp.indexOf("{产品编号}") >= 0){
			if (projectId == null) throw new RuntimeException("无对应项目");
			String scheme_code = Dao.selectOne("Code.getSchemeCode", projectId);
			if(scheme_code == null) throw new RuntimeException("读取编码失败，无法匹配产品编号");
			exp = exp.replaceAll("\\{产品编号\\}", scheme_code);
		}
		if(exp.indexOf("{经销商属省}") >= 0){
			if (projectId == null) throw new RuntimeException("无对应项目");
			String suppliersArea = Dao.selectOne("Code.getSuppliersArea", projectId);
			exp = exp.replaceAll("\\{经销商属省\\}", suppliersArea==null?"":suppliersArea);
		}
		if (Dao.update("Code.seqCode", m) != 1) { throw new RuntimeException("编码被占用，请重新提交."); }
		return exp;
	}
	public static List<Object> queryDataDictionary(String name) {
		return Dao.selectList("Code.queryDataDictionaryForName", name);
	}
	public void doupdateone(Integer i)
	{
	   Dao.update("Code.updateone", i);
	   Dao.commit();
	   Dao.close();
	}
	
	/**
	 * 计划任务 流水号重置
	 * @author wanghl
	 * @datetime 2015年7月3日,下午3:34:35
	 */
	public void codeJob(){
		List<Map<String,Object>> list = Dao.selectList("Code.getCodeList");
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = list.get(i);
			Date RESET_DATE = (Date) map.get("RESET_DATE");
			if(RESET_DATE==null){
				Dao.update("Code.resetCode", map);
			}
			Date now = new Date();
			if ("1".equals(map.get("RESET_TYPE"))) {
				String flag1 = sdfd.format(RESET_DATE);
				String flag2 = sdfd.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
				}
			}else if("2".equals(map.get("RESET_TYPE"))){
				String flag1 = sdfm.format(RESET_DATE);
				String flag2 = sdfm.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
				}
			}else if("3".equals(map.get("RESET_TYPE"))){
				String flag1 = sdfy.format(RESET_DATE);
				String flag2 = sdfy.format(now);
				if (!flag2.equals(flag1)) {
					Dao.update("Code.resetCode", map);
				}
			}
		}
	}
}
