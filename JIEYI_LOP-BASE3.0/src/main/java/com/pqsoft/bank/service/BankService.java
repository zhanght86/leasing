package com.pqsoft.bank.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.PageTemplate;

public class BankService {

//	/**
//	* @Description: TODO 银行信息管理页面
//	* @author 
//	* @param m
//	* @return
//	* @throws Exception 
//	 */
//	public PageTemplate queryBankInforManager(Map<String,Object> m){    	
//	
//		Object Curr=m.get("PAGE_CURR");
//		Object count=m.get("PAGE_COUNT");
//		int pageCurr = Integer.parseInt(Curr==null?"1":Curr.toString());
//		int pageCount = Integer.parseInt(count==null?"10":count.toString());
//
//		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
//		m.put("PAGE_END", pageCount * pageCurr);
//
//		PageTemplate page = new PageTemplate(Dao.selectList("Bank.queryBankAllInfo",m), pageCurr,pageCount, Dao.selectInt("Bank.queryBankAllInfo_count",m), 5);
//		return page;
//    }
	
	
	public Page queryPage(Map<String,Object> m)
	{
		Page page = new Page(m);
		m.put("DataDicType", "银行级别");
		List list=Dao.selectList("Bank.queryBankAllInfo",m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map=(Map)list.get(i);
			if(map.get("TYPE")!=null)
			{
				json.put("TYPE",map.get("TYPE"));
				json.put("TYPE_D",map.get("TYPE_D"));
				json.put("TYPE_E",map.get("TYPE_E"));
			}
			json.put("BANK_NAME", map.get("BANK_NAME"));
			json.put("BANK_CODE", map.get("BANK_CODE"));
			json.put("BANK_LINKMAN", map.get("BANK_LINKMAN"));
			json.put("BANK_PHONE", map.get("BANK_PHONE"));
			json.put("BABI_ID", map.get("BABI_ID"));
			array.add(json);
		}
		page.addDate(array, Dao.selectInt("Bank.queryBankAllInfo_count",m));
		return page;
	}
	
	
	/**
	* @Description: TODO 初始化添加银行页面
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object getParentBank(Map<String,Object> m){
		return Dao.selectList("Bank.getParentBank", m);
    }
	
	/**
	* @Description: TODO 查询银行列表
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object queryAllBank(Map<String,Object> m){
		return Dao.selectList("Bank.queryAllBank", m);
    }
	
	/**
	* @Description: TODO 查找所有的主账号 及其所在银行  用于添加
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public List getParentAccount(Map<String,Object> m){
		return Dao.selectList("Bank.getParentAccount", m);
    }
	
	/**
	* @Description: TODO 添加帐号保存
	* @param m
	* @return
	* @throws Exception
	* @date 2013-8-7下午午13:56:20
	 */
	public int createBankAccount(Map<String,Object> m){
		return Dao.insert("Bank.createBankAccount", m);
    }
	
	/**
	* @Description: TODO 根据BABI_ID查询对应银行记录
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object getBankInforById(Map<String,Object> m){
		return Dao.selectOne("Bank.getBankInforById", m);
    }
	
	/**
	* @Description: TODO 查找所有的总银行   用于修改 不包括本身
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object parentBank(Map<String,Object> m){
		return Dao.selectList("Bank.getParentBankForUpdate", m);
    }
	
	/**
	* @Description: TODO 修改银行
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public int updateBank(Map<String,Object> m){
		return  Dao.update("Bank.updateBank", m);
    }
	
	/**
	* @Description: TODO 检测当前银行是否有子银行
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object getChildCountByParentId(Map<String,Object> m){
		return Dao.selectInt("Bank.getChildCountByParentId", m);
    }
	
	/**
	* @Description: TODO 检测当前银行是否有帐号信息
	* @param m
	* @return
	* @throws Exception 
	* @date 2011-9-8上午10:45:39
	 */
	public Object getAccountCountUserBank(Map<String,Object> m){
		return Dao.selectInt("Bank.getChildAccountByBabiId", m);
    }
	
	/**
	* @Description: TODO 删除银行
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public int invalidBank(Map<String,Object> m){
		return Dao.update("Bank.invalidBank", m);
	}
	
	/**
	* @Description: TODO 银行帐号
	* @author 
	* @param m
	* @return
	* @throws Exception 
	*  @date 2013-8-7下午午13:56:20
	 */
	public Page queryAccountPage(Map<String,Object> m)
	{
		Page page = new Page(m);
		m.put("DataDicType", "银行账号类型");
		List list=Dao.selectList("Bank.queryBankAccountAllInfo",m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map=(Map)list.get(i);
			if(map.get("ACCOUNT_TYPE")!=null)
			{
				json.put("ACCOUNT_TYPE",map.get("ACCOUNT_TYPE"));
				json.put("TYPE_D",map.get("TYPE_D"));
				json.put("TYPE_E",map.get("TYPE_E"));
			}
			
			json.put("BANK_NAME", map.get("BANK_NAME"));
			json.put("ACCOUNT_NO", map.get("ACCOUNT_NO"));
			json.put("ACCOUNT_ALIAS", map.get("ACCOUNT_ALIAS"));
			json.put("BABA_ID", map.get("BABA_ID"));
			
			array.add(json);
		}
		page.addDate(array, Dao.selectInt("Bank.queryBankAccountAllInfo_count",m));
		return page;
	}
	
	/**
	* @Description: TODO 帐号全部信息
	* @param m
	* @return
	* @throws Exception 
	*  @date 2013-8-7下午午13:56:20
	 */
	public Object getBankAccountById(Map<String,Object> m){
		return Dao.selectOne("Bank.getBankAccountInforById",m);
    }
	
	/**
	* @Description: TODO 查找所有的主账号 及其所在银行  用于修改 不包括本身
	* @param m
	* @return
	* @throws Exception 
	*  @date 2013-8-7下午午13:56:20
	 */
	public List getParentAccountForUpdate(Map<String,Object> m){
		return Dao.selectList("Bank.getParentAccountForUpdate", m);
    }
	
	/**
	* @Description: TODO 修改帐号
	* @param m
	* @return
	* @throws Exception 
	*  @date 2013-8-7下午午13:56:20
	 */
	public int updateBankAccount(Map<String,Object> m){
		return  Dao.update("Bank.updateBankAccount", m);
    }
	
	/**
	* @Description: TODO 检测当前银行帐号是否有子银行帐号
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-7下午午13:56:20
	 */
	public Object getAccountCountByParentId(Map<String,Object> m){
		return Dao.selectInt("Bank.getAccountCountByParentId", m);
    }
	
	/**
	* @Description: TODO 删除帐号
	* @param m
	* @return
	* @throws Exception 
	 */
	public int invalidBankAccount(Map<String,Object> m){
		return Dao.update("Bank.invalidBankAccount", m);
	}
	
	/**
	* @Description: TODO 添加银行保存
	* @param m
	* @return
	* @throws Exception 
	 */
	public int insertBank(Map<String,Object> m){
		return Dao.insert("Bank.insertBank", m);
    }
}
