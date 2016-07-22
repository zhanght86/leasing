package com.pqsoft.secu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class MenuService {

	public List<TreeJson> getAllMenu(Map<String, Object> param) {
		return TreeJson.formatTree((List) Dao.selectList("SecMenu.getAllMenu", param));
	}

	// 修改角色页面数据
	@SuppressWarnings("unchecked")
	public menuEntity getUpdateMenu(Map<String, Object> param) {
		ArrayList<menuEntity> list = new ArrayList<menuEntity>();
		if (param != null && param.get("MENU_ID") != null) {
			String MENU_ID = param.get("MENU_ID").toString();
			list = (ArrayList<menuEntity>) new MenuMemcached().get("" + MENU_ID);
		}
		return list.get(0);
	}

	public String addMenu(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_SECU_MENU");
		param.put("ID", ID);
		if (Dao.insert("SecMenu.addMenu", param) > 0) {
			new MenuMemcached().clean(ID);
			return ID;
		} else {
			return null;
		}
	}

	public int updateMenu(Map<String, Object> param) {
		int count = Dao.update("SecMenu.updateMenu", param);
		if (count > 0) {
			if (param != null && param.get("ID") != null) {
				String ID = param.get("ID").toString();
				new MenuMemcached().clean(ID);
			}
		}
		return count;
	}

	public int addMenuPicture(Map<String, Object> param) {
		int count = Dao.update("SecMenu.addMenuPictuer", param);
		if (count > 0) {
			if (param != null && param.get("MENU_ID") != null) {
				String ID = param.get("MENU_ID").toString();
				new MenuMemcached().clean(ID);
			}
		}
		return count;
	}

	public int doDelete(Map<String, Object> m) {
		int count = Dao.update("SecMenu.delMenu", m);
		if (count > 0) {
			if (m != null && m.get("MENU_ID") != null) {
				String ID = m.get("MENU_ID").toString();
				new MenuMemcached().clean(ID);
			}
		}
		return count;
	}
}
