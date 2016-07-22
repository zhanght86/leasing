package com.pqsoft.pendingpool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.common.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class PendingPoolService {

	private final String xmlPath="pendingpool.";
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取余款池数据
	 * toMgPendingPool
	 * @date 2013-10-29 下午02:26:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgPendingPool(Map<String,Object> m){
		Page page = new Page();
		
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgPendingPool", m);
		
		JSONArray array = new JSONArray();
		
		logger.info("查询列表 ： "+list);
		
		for(int i=0; i<list.size(); i++){
			JSONObject obj = new JSONObject();
			Map<String,Object> mm = list.get(i);
			obj.put("INCOME_ID",mm.get("ID"));//主键id
			obj.put("INC_NAME",mm.get("INC_NAME"));//来款人
			obj.put("INC_TIME",mm.get("INC_TIME"));//来款时间
			obj.put("INC_BANK_OPEN",mm.get("INC_BANK_OPEN"));//开户行
			obj.put("INC_BANK_ACCOUNT",mm.get("INC_BANK_ACCOUNT"));//银行账号
			obj.put("INC_MONEY",mm.get("INC_MONEY"));//来款金额
			if(mm.get("INC_TYPE")!=null){//来款方式
				List<Object> type = new DataDictionaryMemcached().get("付款方式");
				if(type!=null){
					for(int j=0; j<type.size(); j++){
						Map<String,Object> type_ = (Map<String, Object>) type.get(j);
						if(type_.get("CODE").equals(mm.get("INC_TYPE").toString())){
							obj.put("INC_TYPE", type_.get("FLAG"));
						}
					}
				}
			}
			//obj.put("INC_TYPE",mm.get("INC_TYPE"));//来款类型
			obj.put("AFFIRM_TYPE",mm.get("AFFIRM_TYPE"));//归集类型
			obj.put("AFFIRM_NAME",mm.get("AFFIRM_NAME"));//归集对象名称
			obj.put("USE_MONEY",mm.get("USE_MONEY"));//基础额度
			obj.put("CANUSE_MONEY",mm.get("CANUSE_MONEY"));//可用金额
			if(mm.get("STATUS")!=null){
				if("1".equals(mm.get("STATUS").toString())){
					obj.put("STATUS", "解冻");
				}else if("0".equals(mm.get("STATUS").toString())){
					obj.put("STATUS","冻结");
				}
			}
			obj.put("operation",mm.get("ID"));//操作
			array.add(obj);
		}
		logger.info("列表遍历结果 ： "+array);
		
		page.addDate(array, Dao.selectInt(xmlPath+"toMgPendingPoolNu", m));
		return page;
	}
	
	/**
	 * 根据来款账号获取来款信息
	 * toShowPending
	 * @date 2013-10-29 下午02:28:49
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toShowPending(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toGetPending", map);
	}
	
	/**
	 * 插入来款信息
	 * doInsertPending
	 * @date 2013-10-29 下午02:31:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertPending(Map<String,Object> map){
		return Dao.insert(xmlPath+"doAddPending", map);
	}
	
	/**
	 * 修改来款信息
	 * doUpdatePending
	 * @date 2013-10-29 下午02:32:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdatePending(Map<String,Object> map){
		int i = 0;
		if(map.get("DEPOSIT_ID")==""){
			i = Dao.update(xmlPath+"doUpPending", map);
		}else {
			i = Dao.update(xmlPath+"doUpPending", map);
			if(i>0){
				i = Dao.update(xmlPath+"doUpdatePool", map);
			}
		}
		
		return i;
	}
	
	/**
	 * 作废来款资金
	 * doZFfunds
	 * @date 2013-10-29 下午06:47:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doZFfunds(Map<String,Object> map){
		return Dao.update(xmlPath+"doZFfunds",map);
	}
	
	/**
	 * 选择归类集对象名称
	 * toChosePer
	 * @date 2013-10-29 下午08:08:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> toChosePer(Map<String,Object> map){
		if(map.get("AFFIRM_TYPE")!=null){
			if("1".equals(map.get("AFFIRM_TYPE").toString())){
				return Dao.selectList(xmlPath+"toGetSuppliers", map);
			}else if("2".equals(map.get("AFFIRM_TYPE").toString())){
				return Dao.selectList(xmlPath+"toGetCust", map);
			}
		}
		
		return null;
	}
	
	/**
	 * 来款账户认款
	 * doInsertPerPool
	 * @date 2013-10-29 下午09:28:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertPerPool(Map<String,Object> map){
		Map<String,Object> perIncome = Dao.selectOne(xmlPath+"toGetPending", map);//查看来款账户信息
		String pool_id = Dao.getSequence("SEQ_FI_DEPOSIT_POOL");
		Map<String,Object> poolData = new HashMap<String,Object>();
		poolData.put("POOL_ID", pool_id);
		poolData.put("OWNER_ID", map.get("AFFIRM_ID"));
		poolData.put("BASE_MONEY", perIncome.get("INC_MONEY"));
		poolData.put("CANUSE_MONEY", perIncome.get("INC_MONEY"));
		poolData.put("USERCODE", map.get("USERCODE"));
		int i = Dao.insert(xmlPath+"doInsertPool", poolData);//插入待处理资金池
		if(i>0){
			Map<String,Object> pending = new HashMap<String,Object>();
			pending.put("DEPOSIT_ID", pool_id);
			pending.put("USERCODE", map.get("USERCODE"));
			pending.put("INCOME_ID", map.get("INCOME_ID"));
			pending.put("AFFIRM_ID", map.get("AFFIRM_ID"));
			pending.put("AFFIRM_NAME", map.get("AFFIRM_NAME"));
			pending.put("AFFIRM_TYPE", map.get("AFFIRM_TYPE"));
			i = Dao.update(xmlPath+"doUpPending", pending);//来款账户认款
			if(i>0){
				return i;
			}
		}
		return 0;
	}
}
