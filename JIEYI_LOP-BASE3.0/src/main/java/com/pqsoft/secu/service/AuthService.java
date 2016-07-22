package com.pqsoft.secu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.pqsoft.skyeye.api.Dao;

public class AuthService {
	private final Logger logger = Logger.getLogger(this.getClass());

	public Object getRes(Map<String, Object> param) {
		return Dao.selectList("auth.getRes", param);
	}

	public Object getPer(Map<String, Object> param) {
		return Dao.selectList("auth.getPer", param);
	}

	public List<TreeJson> getPerTree(Map<String, Object> param) {
		if (param.get("RES_NAME") != null && !param.get("RES_NAME").equals("")) {
			return TreeJson.formatTree((List) Dao.selectList("auth.getPerTreeRES_NAME", param));
		} else {
			return TreeJson.formatTree((List) Dao.selectList("auth.getPerTree", param));
		}
	}

	public List<TreeJson> getAdminTree(Map<String, Object> param) {
		return TreeJson.formatTree((List) Dao.selectList("auth.getAdminTree", param));
	}

	public void doSetRolePer(Map<String, Object> param) {
		if (param.get("RES_NAME").equals("")) {
			Dao.delete("auth.deleteRolePer", param);
		} else {
			String perids = (String) param.get("perids");
			if (perids != null && perids != "") {
				String[] ids = perids.split(",");
				for (String id : ids) {
					param.put("ID", id);
					Dao.delete("auth.deleteRolePerByPerId", param);
				}
			}
		}
		String sids = (String) param.get("sids");
		if (sids != null && sids != "") {
			String[] ids = sids.split(",");
			for (String id : ids) {
				param.put("PER_ID", id);

				Map mapPer = Dao.selectOne("auth.queryPerName", param);
				if (mapPer != null && mapPer.get("CODEALL") != null) {
					param.put("PER_NAME", mapPer.get("CODEALL"));
				}
				Dao.insert("auth.addRolePer", param);
			}
		}

		if (param != null && param.get("ROLE_ID") != null) {
			String ROLE_ID = param.get("ROLE_ID").toString();
			new AuthMemcached().clean(ROLE_ID);
		}
	}

	public void doSetAdmin(Map<String, Object> param) {
		Dao.update("auth.adminclean");
		String idsstr = (String) param.get("ids");
		if (idsstr != null && idsstr != "") {
			String[] ids = idsstr.split(",");
			for (String id : ids) {
				param.put("ID", id);
				param.put("ADMIN", "0");// 启用
				Dao.update("auth.setAdmin", param);
			}
		}
		idsstr = (String) param.get("idsf");
		if (idsstr != null && idsstr != "") {
			String[] ids = idsstr.split(",");
			for (String id : ids) {
				param.put("ID", id);
				param.put("ADMIN", "1");// 模糊
				Dao.update("auth.setAdmin", param);
			}
		}
	}

	public List<?> getRolePerList(Map<String, Object> param) {

		if (param != null && param.get("ROLE_ID") != null) {
			String ROLE_ID = param.get("ROLE_ID").toString();
			return new AuthMemcached().get(ROLE_ID);
		} else {
			return null;
		}
	}

	// 添加新角色
	public int addBtnGetRole(Map<String, Object> param) {
		int i = Dao.insert("auth.addBtnGetRole", param);
		return i;
	}

	// 修改角色
	public int updateBtnGetRole(Map<String, Object> param) {
		int i = Dao.update("auth.updateBtnGetRole", param);
		return i;
	}

	// 删除角色
	public int deleteBtnGetRole(Map<String, Object> param) {
		int i = Dao.delete("auth.deleteBtnGetRole", param);
		return i;
	}

	// 修改角色页面数据
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUpdateRole(Map<String, Object> param) {
		Map<String, Object> mapRole = (Map<String, Object>) Dao.selectOne("auth.selectRoleById", param);
		return mapRole;
	}

	// 查询角色
	public List<Object> queryRoleList(Map<String, Object> m) {
		return Dao.selectList("auth.selectAllRole", m);
	}
}
