package com.pqsoft.call.service;
/**
 *  05、呼叫中心审批
 *  @author 韩晓龙
 */
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class CallCenterFlowService {
	
	private String basePath = "callCenterFlow.";
	
	public CallCenterFlowService() {
	}

	/**
	 *   根据项目编号查询租赁物信息验证信息
	 */
	public List selectProductDetailById(Map<String,Object> param){
		param.put("PERIOD", "租赁周期");
		param.put("ITEM_NAME", "租金");
		return Dao.selectList(basePath+"queryProductDetailById",param);
	}
	
	/**
	 *  插入租赁物信息验证信息
	 */
	public int insertProductDetailById(Map<String,Object> param){
		return Dao.insert(basePath+"addProductMessage",param);
	}
	
	/**
	 *  根据项目编号删除相关租赁物信息验证信息
	 */
	public int deleteProductDetailById(Map<String,Object> param){
		return Dao.delete(basePath+"deleteProductMessage",param);
	}
	
	/**
	 *  插入自然人信息验证信息
	 */
	public int insertNPDetailById(Map<String,Object> param){
		return Dao.insert(basePath+"addNPMessage",param);
	}
	
	/**
	 *  根据项目编号删除自然人信息验证信息
	 */
	public int deleteNPDetailById(Map<String,Object> param){
		return Dao.delete(basePath+"deleteNPMessage",param);
	}
	
	/**
	 *  插入法人信息验证信息
	 */
	public int insertLPDetailById(Map<String,Object> param){
		return Dao.insert(basePath+"addLPMessage",param);
	}
	
	/**
	 *  根据项目编号删除法人信息验证信息
	 */
	public int deleteLPDetailById(Map<String,Object> param){
		return Dao.delete(basePath+"deleteLPMessage",param);
	}
	
	/**
	 *   根据项目编号查询已保存的租赁物验证信息
	 */
	public List selectProductAlreadyDetailById(Map<String,Object> param){
		List list = Dao.selectList(basePath+"showProductDetailById",param);
		//如果查询为空的情况下到项目表查询信息
		if(list==null||list.isEmpty()){
			list = Dao.selectList(basePath+"showProductDetailById2",param);
		}
		return list;
	}
	
	/**
	 *   根据项目编号查询已保存的自然人验证信息
	 */
	public Object  selectNPAlreadyDetailById(Map<String,Object> param){
		Map list = Dao.selectOne(basePath+"showPersonNPDetailById",param);
		//如果查询为空的情况下到项目表查询信息
		if(list==null||list.isEmpty()){
			list = Dao.selectOne(basePath+"showPersonNPDetailById2",param);
		}
		System.out.println("====================list="+list);
		return list;

	}
	
	public Map queryPhone(Map<String,Object> param){
		Map map=Dao.selectOne(basePath+"queryPhone", param);
		if(map!=null){
			Dao.getClobTypeInfo2(map,"IDCARD_PHOTO");
			Dao.getClobTypeInfo2(map,"SPOUSTIDCARD_PHOTO");
		}
		return map;
	}
	
	/**
	 *   根据项目编号查询已保存的法人验证信息
	 */
	public Object  selectLPAlreadyDetailById(Map<String,Object> param){
		Map list = Dao.selectOne(basePath+"showPersonLPDetailById",param);
		//如果查询为空的情况下到项目表查询信息
		if(list==null||list.isEmpty()){
			list = Dao.selectOne(basePath+"showPersonLPDetailById2",param);
		}
		return list;
	}
	
	/**
	 *   根据项目编号查询已保存的法人验证信息
	 */
	public Object  selectProjIdByProjectId(Map<String,Object> param){
		return Dao.selectOne(basePath+"selectProjIdByProjectId",param);
	}
	
	/**
	 *   根据项目编号查询已保存的自然人验证信息条数
	 */
	public int  getCountNP(Map<String,Object> param){
		return Dao.selectInt(basePath+"getCountNP",param);
	}
	
	/**
	 *   根据项目编号查询已保存的法人验证信息条数
	 */
	public int  getCountLP(Map<String,Object> param){
		return Dao.selectInt(basePath+"getCountLP",param);
	}
	
	/**
	 *   根据项目编号查询已保存的租赁物验证信息条数
	 */
	public int  getCountEquip(Map<String,Object> param){
		return Dao.selectInt(basePath+"getCountEquip",param);
	}
	
	/**
	 *   根据项目编号查询判断租赁物验证信息是否通过
	 */
	public int  judgeEquip(Map<String,Object> param){
		param.put("ISAGREE", "是");
		return Dao.selectInt(basePath+"judgeEquip",param);
	}
	
	/**
	 *   根据项目编号查询判断租赁物验证信息是否通过
	 */
	public int  judgeNp(Map<String,Object> param){
		param.put("ISAGREE", "是");
		return Dao.selectInt(basePath+"judgeNp",param);
	}
	
	/**
	 *   根据项目编号查询判断租赁物验证信息是否通过
	 */
	public int  judgeLp(Map<String,Object> param){
		param.put("ISAGREE", "是");
		return Dao.selectInt(basePath+"judgeLp",param);
	}
	
	public void doAddCustBaseINfoXZS(Map<String,Object> param){
		//通过项目编号查询出客户ID和项目ID
		Map map=Dao.selectOne(basePath+"queryProjectByCode",param);
		param.putAll(map);
		
		param.put("USERCODE",Security.getUser().getCode());
		param.put("USERNAME",Security.getUser().getName());
		Dao.insert(basePath+"doAddCustBaseINfoXZS", param);
	}
}
