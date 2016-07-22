package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 * 判断是否临牌的事件
 * @author Administrator
 *
 */
public class IsTempLCN  implements DecisionHandler{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param PROJECT_ID
	 * @author xingsumin
	 * @datetime 2016年1月14日17:42:31
	 */
	@Override
	public String decide(OpenExecution execution)  {
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		if(null!=PROJECT_ID&&!PROJECT_ID.equals("")){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PROJECT_ID", PROJECT_ID);
			//判断是否为临时车牌   0：临时，1：正式
			List<Map<String, Object>> certificates = Dao.selectList("buyCertificate.getCarnumTypeByProjectId", map);
			if(null!=certificates &&certificates.size()>0){
				Map<String, Object> m=certificates.get(0);
				if("0".equals(m.get("CARNUM_TYPE"))){
					//临时牌照返回 '是'，其他返回 '否'。
					return "是";
				}else{
					return "否";
				}
			}else{
				//历史数据 牌照 为空 直接到下一节点
				return "否";
			}
		}
		throw new ActionException("系统错误，请联系管理员！");
	}
}
