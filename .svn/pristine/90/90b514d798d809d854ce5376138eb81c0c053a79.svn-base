package com.pqsoft.refundProject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class ProjectFileService {
	private final String mapper = "projectfile.";

	/**
	 * 添加附件信息
	 * 
	 * @param param
	 * @return
	 */
	public int addFile(Map<String, Object> param) {
		String id = Dao.getSequence("SEQ_REFINANCE_FILE");
		param.put("ID", id);
		return Dao.insert(mapper + "addfile", param);
	}

	/**
	 * 获取文件路径
	 * 
	 * @param id
	 * @return
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-5-11
	 */
	public String getFile(String id) {
		return Dao.selectOne(mapper + "getfile", id);
	}

	/**
	 * 查询文件列表
	 * 
	 * @param creditId
	 * @return
	 * 
	 */
	public List<?> getFileList(String projectId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", projectId);
		return Dao.selectList(mapper + "getFileList", param);
	}
	public int delete(String id) {
		return Dao.update(mapper + "delete", id);
	}

	
	
	public List<Map<String, Object>> getDateFileListByProid(Map<String, Object> map) {
		return Dao.selectList(mapper + "getDateFileListByProid", map);
	}
	
	public String getConditiononeList(Map<String, Object> map) {
		return Dao.selectOne(mapper + "getConditiononeList", map);
	}

}
