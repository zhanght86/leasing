package com.pqsoft.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class MemoService {

	private final String mapper = "bpm.memo.";
	
	// 设置初始化备注，用于任务生成的时候
		public boolean addMemo(String taskId, String jbpmId, String taskName, String url,String opName,String opCode,String memo,String wmemo,String TRAN_NAME_) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("TASK_ID", taskId);
			param.put("JBPM_ID", jbpmId);
			param.put("TASK_NAME", taskName);
			param.put("URL", url);
			param.put("OP_NAME", opName);
			param.put("OP_CODE", opCode);
			param.put("MEMO", memo);
			param.put("WMEMO", wmemo);
			/**有条件通过字段**/
			param.put("TRAN_NAME_", TRAN_NAME_);
			return 1 == Dao.insert(mapper + "add", param);
		}
	
	// 设置初始化备注，用于任务生成的时候
	public boolean addMemo(String taskId, String jbpmId, String taskName, String url,String opName,String opCode,String memo,String wmemo) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		param.put("JBPM_ID", jbpmId);
		param.put("TASK_NAME", taskName);
		param.put("URL", url);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("MEMO", memo);
		param.put("WMEMO", wmemo);
		return 1 == Dao.insert(mapper + "add", param);
	}

	// 设置初始化备注，用于任务生成的时候
	public boolean addMemo(String taskId, String jbpmId, String taskName, String url) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		param.put("JBPM_ID", jbpmId);
		param.put("TASK_NAME", taskName);
		param.put("URL", url);
		return 1 == Dao.insert(mapper + "add", param);
	}

	public boolean addMemo(String jbpmId, String opName, String opCode, String taskName, String memo) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("JBPM_ID", jbpmId);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("TASK_NAME", taskName);
		param.put("MEMO", memo);
		return 1 == Dao.insert(mapper + "add", param);
	}

	// 添加备注-预留
	public boolean addMemo(String taskId, String jbpmId, String opName, String opCode, String taskName, String filePath, String clientType) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		param.put("JBPM_ID", jbpmId);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("TASK_NAME", taskName);
		param.put("FILE_PATH", filePath);
		param.put("CLIENT_TYPE", clientType);
		return 1 == Dao.insert(mapper + "add", param);
	}
	
	// 修改备注
	public boolean upMemo(String taskId, String opName, String opCode, String memo, String opType, String url, String clientType,String WMEMO) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("OP_TYPE", opType);
		param.put("MEMO", memo);
		param.put("WMEMO", WMEMO);
		param.put("URL", url);
		param.put("CLIENT_TYPE", clientType);
		Map<String, Object> m = Dao.selectOne("bpm.task.getTaskOpCode", taskId);
		if (m != null && !opCode.equals(m.get("CODE"))) {
			param.put("OP_NAME", m.get("NAME") + "[实际操作人:" + opName + "]");
		}
		return 1 == Dao.update(mapper + "upMemo", param);
	}
	
	// 修改备注
		public boolean upMemo(String taskId, String opName, String opCode, String memo, String opType, String url, String clientType,String WMEMO,String TRAN_NAME_) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("TASK_ID", taskId);
			param.put("OP_NAME", opName);
			param.put("OP_CODE", opCode);
			param.put("OP_TYPE", opType);
			param.put("MEMO", memo);
			param.put("WMEMO", WMEMO);
			param.put("URL", url);
			param.put("CLIENT_TYPE", clientType);
			/**有条件通过字段**/
			param.put("TRAN_NAME_", TRAN_NAME_);
			Map<String, Object> m = Dao.selectOne("bpm.task.getTaskOpCode", taskId);
			if (m != null && !opCode.equals(m.get("CODE"))) {
				param.put("OP_NAME", m.get("NAME") + "[实际操作人:" + opName + "]");
			}
			return 1 == Dao.update(mapper + "upMemo", param);
		}


	// 查询备注数量
	public int getMemoCount(String taskId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		return Dao.selectInt(mapper + "getMemoCount", param);
	}

	public List<Map<String, Object>> getMemos(String jbpmId) {
		String[] strs = jbpmId.split("[.]");
		if (strs.length > 2) {
			jbpmId = strs[0] + "." + strs[1];
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpmId);
		return Dao.selectList(mapper + "getMemos", param);
	}

	/**
	 * 任务记录信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getMemo(String id) {
		return Dao.selectOne(mapper + "getMemo", id);
	}

	public Map<String, Object> getMemoByTaskId(String id) {
		return Dao.selectOne(mapper + "getMemoByTaskId", id);
	}
	
	//意见记录修改
	public Map<String, Object> getMemosByTaskId(String id) {
		return Dao.selectOne(mapper + "getMemosByTaskId", id);
	}
	
	//追加附件URL
	public boolean updateMemo(String taskid,String filepath){
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskid);
		param.put("FILE_PATH", filepath);
		boolean flag=false;
		int k=Dao.update(mapper + "upMemoFilePath", param);
		if(k==1){
				flag=true;
		}
		return flag;
	}
	
	//更新任务意见
	public boolean updateMemoSubmit(String taskId, String opName, String opCode, String memo, String opType, String url, String clientType,String WMEMO,String TRAN_NAME_) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("TASK_ID", taskId);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("OP_TYPE", opType);
		param.put("MEMO", memo);
		param.put("WMEMO", WMEMO);
		param.put("URL", url);
		param.put("CLIENT_TYPE", clientType);
		Map<String, Object> m = Dao.selectOne("bpm.task.getTaskOpCode", taskId);
		if (m != null && !opCode.equals(m.get("CODE"))) {
			param.put("OP_NAME", m.get("NAME") + "[实际操作人:" + opName + "]");
		}
		return 1 == Dao.update(mapper + "updateMemo", param);
	}
	
	/**
	 * 获取角色权限画面的Tab
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getTabRights (String id) {
		return Dao.selectOne(mapper + "getTabRights", id);
	}
	
	/**
	 * 根据ProjectId获取流程ID
	 * 		add gengchangbao 2016/2/1  JZZL-93
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getJbpmIdByProjectId(String id) {
		return Dao.selectOne(mapper + "getJbpmIdByProjectId", id);
	}
}
