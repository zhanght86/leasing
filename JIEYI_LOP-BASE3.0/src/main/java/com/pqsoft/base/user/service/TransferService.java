/**
 * TODO 
 * @author LUYANFENG @ 2015年5月28日
 */
package com.pqsoft.base.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;

/**
 * TODO 
 * @author LUYANFENG @ 2015年5月28日
 *
 */
public class TransferService {
	
	
	/**
	 * 取移交过的客户
	 * @param param
	 * @return
	 * @author : LUYANFENG @ 2015年6月3日
	 */
	public Page getTransferCustPage(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("transferTask.select_custs2",param));
		page.addDate(array, Dao.selectInt("transferTask.select_custs2_count", param));
		return page;
	}
	
	
	public Page getCustPage(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("transferTask.select_custs",param));
		page.addDate(array, Dao.selectInt("transferTask.select_custs_count", param));
		return page;
	}

	/**
	 * @param cust_data_str
	 * @param transferor
	 * @param transferee
	 * @author : LUYANFENG @ 2015年5月28日
	 */
	public boolean doTransfer(String cust_data_str, String transferor, String transferee ,String transferAll ,String resume) {
		// 客户信息
		JSONArray cust_data_json = null;
		String change_type = "all"; // all | client | project | jbpm
		try {
			if("client".equalsIgnoreCase(transferAll)){
				change_type = "client";
				cust_data_json = JSONArray.fromObject(cust_data_str);
			}else if("project".equalsIgnoreCase(transferAll)){
				change_type = "project";
				cust_data_json = JSONArray.fromObject(cust_data_str);
			}else if("all".equalsIgnoreCase(transferAll)){
				change_type = "all";
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("EMPLOYEE_CODE", transferor);
				List<Object> selectList = Dao.selectList("transferTask.select_custs",param);
				cust_data_json = JSONArray.fromObject(selectList);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("指定的移交客户信息有误。");
		}
		
		
		User transferorU = null;
		try {
			transferorU = Security.getUser(transferor);
			transferorU.getCode().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("指定的移交人信息不正确。");
		}
		User transfereeU = null;
		try {
			transfereeU = Security.getUser(transferee);
			transfereeU.getId().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("指定的接收人信息不正确。");
		}
		
		if(transferor.equals(transferee)){
			throw new ActionException("指定的接收人不能是自己！");
		}
		if(StringUtils.isBlank(resume)){
			throw new ActionException("请输入移交说明！");
		}
		
		try {
			// 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("change_type", change_type);
			map.put("change_man", Security.getUser().getCode());
			map.put("old_owner_code", transferor);
			map.put("old_owner_id", transferorU.getId());
			map.put("old_owner_name",transferorU.getName());
			
			map.put("new_owner_code", transferee );
			map.put("new_owner_id", transfereeU.getId());
			map.put("new_owner_name", transfereeU.getName());
			map.put("new_owner_org_id", transfereeU.getOrg().getId());
			map.put("new_owner_platform_id", transfereeU.getOrg().getPlatformId());
			
			if (StringUtils. isNotBlank(transfereeU.getOrg().getSuppId())){
			    map.put("new_owner_parent_id", transfereeU.getOrg().getSuppId());
			} else if (StringUtils.isNotBlank(transfereeU.getOrg().getSpId())){
			    map.put("new_owner_parent_id", transfereeU.getOrg().getSpId());
			}
			map.put("resume", resume);
			int insert = Dao.insert("transferTask.transfer_log", map);
			if(insert != 1){
				Dao.rollback();
				throw new ActionException("处理出错：10024。");
			}
			//
			for (ListIterator listIterator = cust_data_json.listIterator(); listIterator.hasNext(); listIterator.next()) {
				JSONObject custObj = cust_data_json.getJSONObject(listIterator.nextIndex());
				if("project".equalsIgnoreCase(change_type ) ){ // 是选择项目移交
					Map<String,Object> params__ = new HashMap<String,Object>();
					params__.put("project_id", custObj.get("ID"));
					params__.put("EMPLOYEE_ID", transferorU.getId());
					Map<String, Object> project = Dao.selectOne("transferTask.select_projects", params__ );
					
					
					//TODO copy 一份客户信息给接收人。用cust_id 查有就不加了。
//					String CUST_ID = project.get("CUST_ID").toString();
					
					
					map.remove("jbpm_id_");
					map.remove("cust_id_");
					map.remove("file_id_");
					map.put("project_id_", custObj.get("ID"));
					insert = Dao.insert("transferTask.transfer_log_detail", map);
					if (insert != 1) {
						Dao.rollback();
						throw new ActionException("处理出错：10025。");
					}
					
					//
					map.put("project_id", custObj.get("ID"));
					Dao.update("transferTask.update_owner_project_head", map);
					
					// 
					map.remove("project_id_");
					map.remove("cust_id_");
					map.remove("file_id_");
					map.put("jbpm_id_", project.get("JBPM_ID"));
					insert = Dao.insert("transferTask.transfer_log_detail", map);
					if (insert != 1) {
						Dao.rollback();
						throw new ActionException("处理出错：10025.");
					}
					//
					Set<String> ids = new HashSet<String>();
					ids.add(custObj.get("ID").toString());
					map.put("project_ids", ids);
					Dao.update("transferTask.update_owner_jbpm", map);
					Dao.update("transferTask.update_owner_equipment", map);
					Dao.update("transferTask.update_owner_scheme", map);
					
				}else if("jbpm".equalsIgnoreCase(change_type ) ){ // 不用
					
				}else{// 是选择客户或全部移交
					map.put("cust_id", custObj.get("ID"));
					
					map.remove("file_id_");
					map.remove("jbpm_id_");
					map.remove("project_id_");
					map.put("cust_id_", custObj.get("ID"));
					insert = Dao.insert("transferTask.transfer_log_detail", map);
					if (insert != 1) {
						Dao.rollback();
						throw new ActionException("处理出错：10025。");
					}

					// del gengchangbao JZZL-101 2016/2/22 start 
					//此处更新移动至查询代码之后，防止由于更新导致查询需要移交数据的遗漏
//					Dao.update("transferTask.update_owner_client", map);
//					Dao.update("transferTask.update_owner_credit_cust_client", map);
//					Dao.update("transferTask.update_owner_project_head", map);
					// del gengchangbao JZZL-101 2016/2/22 end

					Map<String,Object> params__ = new HashMap<String,Object>();
					// add gengchangbao JZZL-101 2016/2/22 start
					List client_ids = new ArrayList();
					client_ids.add(custObj.get("ID"));
					params__.put("client_ids", client_ids);
					// add gengchangbao JZZL-101 2016/2/22 end
					params__.put("client_id", custObj.get("ID"));
					params__.put("EMPLOYEE_ID", transferorU.getId());
					List<Map<String, Object>> projects = Dao.selectList("transferTask.select_projects", params__);
					// add gengchangbao JZZL-101 2016/2/22 start 
					//此处更新有上面移动下来
					Dao.update("transferTask.update_owner_client", map);
					Dao.update("transferTask.update_owner_credit_cust_client", map);
					Dao.update("transferTask.update_owner_project_head", map);
					// add gengchangbao JZZL-101 2016/2/22 end
					Set<String> ids = new HashSet<String>();
					if (projects != null) {
						for (Map m : projects) {
							ids.add(m.get("ID").toString());
							
							map.remove("file_id_");
							map.remove("jbpm_id_");
							map.remove("cust_id_");
							map.put("project_id_", m.get("ID").toString());
							insert = Dao.insert("transferTask.transfer_log_detail", map);
							if (insert != 1) {
								Dao.rollback();
								throw new ActionException("处理出错：10025.");
							}
							
							map.remove("file_id_");
							map.remove("project_id_");
							map.remove("cust_id_");
							map.put("jbpm_id_", m.get("JBPM_ID"));
							insert = Dao.insert("transferTask.transfer_log_detail", map);
							if (insert != 1) {
								Dao.rollback();
								throw new ActionException("处理出错：10025.");
							}
						}
					}
					if( !ids.isEmpty()){
						map.put("project_ids", ids);
						Dao.update("transferTask.update_owner_jbpm", map);
						Dao.update("transferTask.update_owner_equipment", map);
						Dao.update("transferTask.update_owner_scheme", map);
					}
					
					
				}
				
				

			}
			return true;
		} catch (Exception e) {
			Dao.rollback();
			e.printStackTrace();
		}
			 
		
		
		return false;
	}

	/**
	 * @param change_man
	 * @param times
	 * @return
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	public Page getTranPage(Map<String,Object> params) {
		String change_man = (String) params.get("change_man");
		String emp_code = (String) params.get("emp_code");
		String times = (String) params.get("times"); // 2015-05-28:2015-05-29
		
		
		Map<String,Object> qryParam = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(times) && times.length() >= 11){
			String[] split = times.split("\\:");
			if(times.trim().startsWith(":")){
				qryParam.put("time_end", split[0]);
			}else if(times.trim().endsWith(":")){
				qryParam.put("time_start", split[0]);
			}else{
				qryParam.put("time_start", split[0]);
				qryParam.put("time_end", split[1]);
			}
			
		}
		qryParam.put("emp_code", emp_code);
		qryParam.put("change_man", change_man);
		Page page = new Page(qryParam);
		JSONArray array = JSONArray.fromObject(Dao.selectList("transferTask.select_transfers",qryParam));
		page.addDate(array, Dao.selectInt("transferTask.select_transfers_count", qryParam));
		return page;
	}

	/**
	 * @param param
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	public Page getProjectPageByCust(Map<String, Object> param) {
		String client_ids = (String) param.get("client_ids");
		String EMPLOYEE_ID = (String) param.get("EMPLOYEE_ID");
		if (StringUtils.isBlank(EMPLOYEE_ID)) {
			throw new ActionException("缺少参数。");
		}
		Map<String, Object> qryParam = new HashMap<String, Object>();
		String qry_project_text = (String) param.get("qry_project_text");
		qryParam.put("qry_project_text", qry_project_text);
		
		if(StringUtils.isNotBlank(client_ids)){
			qryParam.put("client_ids", client_ids.split(","));
		}
		qryParam.put("EMPLOYEE_ID", EMPLOYEE_ID);
		Page page = new Page(qryParam);
		JSONArray array = JSONArray.fromObject(Dao.selectList("transferTask.select_projects", qryParam));
		page.addDate(array,Dao.selectInt("transferTask.select_projects_count", qryParam));
		return page;
	}
	
	/**
	 * 移交后的项目列表
	 * @param param
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	public Page getProjectPageAfterTransfer(Map<String, Object> param) {
		String client_ids = (String) param.get("client_ids");
		String EMPLOYEE_ID = (String) param.get("EMPLOYEE_ID");
		// add gengchangbao JZZL-101 2016/2/22 start
		String tId = (String) param.get("t_id");
		// add gengchangbao JZZL-101 2016/2/22 end
		if (StringUtils.isBlank(client_ids) && StringUtils.isBlank(EMPLOYEE_ID)) {
			throw new ActionException("缺少参数。");
		}
		Map<String, Object> qryParam = new HashMap<String, Object>();
		String qry_project_text = (String) param.get("qry_project_text");
		qryParam.put("qry_project_text", qry_project_text);
		if(StringUtils.isNotBlank(client_ids)){
			qryParam.put("client_ids", client_ids.split(","));
		}
		qryParam.put("EMPLOYEE_ID", EMPLOYEE_ID);
		// add gengchangbao JZZL-101 2016/2/22 start
		qryParam.put("T_ID", tId);
		// add gengchangbao JZZL-101 2016/2/22 end
		Page page = new Page(qryParam);
		JSONArray array = JSONArray.fromObject(Dao.selectList("transferTask.select_projects2", qryParam));
		page.addDate(array,Dao.selectInt("transferTask.select_projects2_count", qryParam));
		return page;
	}

	/**
	 * 移交明细
	 * @param t_id
	 * @author : LUYANFENG @ 2015年5月29日
	 */
	public List<Map<String,Object>> getTransferDetail(String t_id) {
		List<Map<String,Object>> transList = Dao.selectList("transferTask.select_transfer_detail", t_id);
		return transList;
	}

	/**
	 * 移交主体 一个
	 * @param t_id
	 * @author : LUYANFENG @ 2015年5月29日
	 * @aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	 */
	 
	public Map<String, Object> getTransferHead(String t_id) {
		Map<String,Object> qryMap = new HashMap<String,Object>();
		qryMap.put("t_id", t_id);
		return Dao.selectOne("transferTask.select_transfers",qryMap);
	}

}
