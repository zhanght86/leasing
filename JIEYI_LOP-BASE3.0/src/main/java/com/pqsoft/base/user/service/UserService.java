package com.pqsoft.base.user.service;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.StringUtils;


public class UserService {
    private String basepath = "User.";
	 
	/**
	 * 分页显示
	 * @param param
	 * @return
	 */
	public Page getPageData(Map<String,Object> param){
		param.put("EMPLOYEE_STATUS", "员工状态");
		param.put("PERSON_TYPE", "员工类型");
		param.put("XINGBIE", "性别");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basepath+"selectUser",param));
		page.addDate(array, Dao.selectInt(basepath+"selectUserCount", param));
		return page;
	}

	/**
	 * 更新用户的信息
	 * 
	 * @param userId
	 *            用户id<br>
	 * @throws Exception
	 * @return 插入数据的记录数
	 */
	public int updateUserById(Map<String, Object> map){
		return Dao.update(basepath+"updateUserById", map);
	}
	
	/**
	 * 删除员工信息
	 * @param param
	 * @return
	 */
	public int delUser(Map<String,Object> param ){
		int result = 0 ;
		result = Dao.delete(basepath+"removeOrgUser", param);
		result = Dao.delete(basepath+"delOneUser",param);
		return result ;
	}
 
	
	/**
	 * 根据用户id查询用户的详细信息
	 * 
	 * @author jinhongdong
	 * @param userId
	 */
	public Map<String, Object> getUserInfoById(Map<String,Object> userId) {
		return Dao.selectOne(basepath+"selectUserByUserId", userId);
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getSelectOrgParentList(String userId) {
		List<Map<String, Object>> list = Dao.selectList(basepath + "selectOrgParentList", userId);
		String returnName = "";
		String returnName2 = ""; 
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(list.size() > 3) {
			for(int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String name = map.get("NAME").toString();
				
				if((i== 0) ||(i%2 == 0)) {
					if(StringUtils.isBlank(returnName)) {
						returnName = name;
					} else {
						returnName = name  + " 》 " + returnName ;
					}
					
				} else {
					
					if(StringUtils.isBlank(returnName2)) {
						returnName2 = name;
					} else {
						returnName2 = name  + " 》 " + returnName2 ;
					}
				}
			}
			returnMap.put("NAME", returnName2 + "\n " + returnName);
		} else {
			
			for (Map<String, Object> map : list) {
				String name = map.get("NAME").toString();
				if(StringUtils.isBlank(returnName)) {
					returnName = name;
				} else {
					returnName = name  + " 》 " + returnName ;
				}
			}
			returnMap.put("NAME", returnName);
		}
		
		return returnMap;
	}

	/**
	 * 检查工号是否存在
	 * 
	 * @param EMPLOYEE_CODE
	 *            工号
	 * @return
	 */
	public int validateUserCode(String EMPLOYEE_CODE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EMPLOYEE_CODE", EMPLOYEE_CODE);
		return Dao.selectInt(basepath+"selectUserCountByCode", map);
	}
 
	/**
	 * 保存用户信息
	 * @param m
	 */
	public int saveUser(Map<String, Object> map){
		int i = Dao.insert(basepath+"saveUser", map);
		return i;
	}
	/**
	 * 查询序列
	 * 
	 * @param m
	 */
	public int selectSeq() {
		int i = Integer.parseInt(((Map<String,Object>)Dao.selectOne(basepath+"selectSeq")).get("SEQ").toString());
		return i;
	}

	/**
	 * 重置密码
	 * @param userId
	 *            用户id<br>
	 */
	public int updatePwdByUserId(String userId) {
		return Dao.update(basepath+"updateUserPWD", userId);
	}
	/**
	 * 获取老密码
	 * @param EMPLOYEE_CODE
	 * @return
	 */
	public  boolean  validateUserPwd(String EMPLOYEE_CODE,String pwd){
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("EMPLOYEE_CODE", EMPLOYEE_CODE);
		String password = Dao.selectOne(basepath+"selectUserByOldPwd", map);
		if(password==null) password="";
		return password.equals(pwd);
	}
	/**
	 * 更新密码
	 * @param EMPLOYEE_CODE
	 * @return
	 */
	public  boolean  updateUserPwd(String EMPLOYEE_CODE,String pwd,String oldPwd) {
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("EMPLOYEE_CODE", EMPLOYEE_CODE);
		map.put("EMPLOYEE_PWD", pwd);
		map.put("EMPLOYEE_OLDPWD", oldPwd);
		boolean flag = Dao.update(basepath+"updateUserByOldPwd", map)==1;
	
		return flag;
	}
 
	/**
	 * 根据用户ID查电话号码
	 * @param userId
	 * @return
	 */
	public String getTelephoneByUserId(String userId){
		String telephone= Dao.selectOne(basepath+"getTelephoneByUserId", userId);
		return telephone==null?"":telephone;
	}
 
	/**
	 * 查询部门
	 * @author 
	 * @return
	 */
	public List<?> getOrganinzation(){
		return Dao.selectList(basepath+"getOrganinzation");
	}

	public Map<String, Object> selectImageById(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return Dao.selectOne(basepath+"selectImageByUserId", param);
	}

 
}
