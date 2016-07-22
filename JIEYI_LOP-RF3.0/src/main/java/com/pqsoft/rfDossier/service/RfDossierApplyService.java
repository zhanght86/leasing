package com.pqsoft.rfDossier.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * 融资档案管理服务
 * 
 * @author King 2013-11-1
 */
public class RfDossierApplyService {
	private String nameSpace = "rfDossier.";

	/**
	 * 查询融资档案管理页数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-1
	 */
	public Page doShowMgRfDossierPage(Map<String, Object> map) {
		return PageUtil.getPage(map, nameSpace + "doShowMgRfDossier", nameSpace + "doShowMgRfDossierCount");
	}
}
