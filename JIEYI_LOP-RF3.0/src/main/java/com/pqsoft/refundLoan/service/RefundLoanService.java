package com.pqsoft.refundLoan.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RefundLoanService {

	private final String xmlPath = "refundLoan.";
	
	/**
	 * 放款管理-列表页数据
	 * toMgLoan
	 * @date 2013-10-21 下午07:59:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgLoan(Map<String,Object> map){
		Page page = new Page(map);
		map.put("TYPE", "再融资方式");
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgLoan", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			Map<String,Object> m = list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("ID", m.get("ID")); 
			obj.put("PROJECT_STATUS",m.get("PROJECT_STATUS"));//项目状态
			obj.put("PROJECT_NAME", m.get("PROJECT_NAME")); //项目名称
			obj.put("PROJECT_CODE", m.get("PROJECT_CODE")); //项目编号
			obj.put("DATECOUNT", m.get("DATECOUNT")); 
			obj.put("CREATE_TIME", m.get("CREATE_TIME"));//项目创建时间 
			obj.put("COMMIT_BANK", m.get("COMMIT_BANK"));//提交银行时间 
			obj.put("PAY_TOTAL", m.get("PAY_TOTAL")); //项目实际总额
			obj.put("LOAN_AMOUNT", m.get("LOAN_AMOUNT")); //放款金额
			obj.put("BAILOUTWAY_NAME", m.get("BAILOUTWAY_NAME")); //融资方式
			obj.put("ORGAN_NAME", m.get("ORGAN_NAME")); //融资机构
			obj.put("operate", m.get("PROJECT_STATUS")); //融资机构
			array.add(obj);
		}
		
		page.addDate(array, Dao.selectInt(xmlPath+"toMgLoanCount", map));
		return page;
 	}

	/**
	 * 获取当前项目对应融资机构中支付类型
	 * getPaytypeFromFhfa
	 * @date 2013-10-22 上午10:22:52
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getPaytypeFromFhfa(Map<String,Object> map){
		return (Map<String,Object>)Dao.selectOne(xmlPath+"getPaytypeFromFhfa", map);
	}
	
	/**
	 * 获取当前项目对应融资机构中支付类型的名称
	 * * getPaytypeFromFhfa
	 * @date 2013-10-22 上午10:22:52
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getPaytypeName(Map<String,Object> map){
		if(map !=null && map.get("REPAY_TYPE") !=null && !map.get("REPAY_TYPE").equals("")){
			String REPAY_TYPE=map.get("REPAY_TYPE").toString();
			String[] aa=REPAY_TYPE.split(",");
			if(aa.length == 1){
				REPAY_TYPE="'"+REPAY_TYPE+"'";
				map.put("REPAY_TYPE",REPAY_TYPE);
			}
		}
		return Dao.selectList(xmlPath+"getPaytypeName", map);
	}
	
	/**
	 * 获取项目信息
	 * getProject
	 * @date 2013-10-22 上午10:35:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getProject(Map<String, Object> m) {
		return Dao.selectOne(xmlPath+"getProject", m);
	}
	
	/**
	 * 录入放款信息
	 * @author 楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public int addLoan(Map<String, Object> map){
		//判断是否存在放款项目， 如果存在， 那么先删除， 在添加
		Map<String,Object> loan = Dao.selectOne(xmlPath+"getLoanData", map);
		if(loan!=null){//若放款项目存在， 那么先删除
			if(loan.get("ID")!=null&&!"".equals(loan.get("ID").toString())){
				Dao.delete(xmlPath+"delLoan", loan);
			}
		}
		
		//添加放款項目
		int i = Dao.insert(xmlPath+"addLoan", map);
		return i;
	}
	
	/**
	 * 修改项目表中的项目状态
	 * @author 楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public int updateProjectStatus(Map<String, Object> map){
		return 	Dao.update(xmlPath+"updateProjectStatus", map);
	}
	
	/**
	 * 添加放款失败原因
	 * @author 楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public int addLoanFailureResaon(Map<String, Object> map){
		int i = Dao.insert(xmlPath+"addLoanFailureResaon", map);
		return i;
	}
	
	/**
	 * 获取放款信息根据ID
	 * @author 楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public Object getLoanByID(Map<String, Object> map){
		return Dao.selectOne(xmlPath+"getLoanByID", map);
	}
	
	/**
	 * 修改放款信息
	 * updateLoan
	 * @date 2013-10-22 下午12:04:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateLoan(Map<String, Object> map){
		int i = Dao.insert(xmlPath+"updateLoan", map);
		return i;
	}
	
	/**
	 * 查看放款详细信息
	 * @author  楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public Object queryLoanDetail(Map<String, Object> map){		
		return Dao.selectOne(xmlPath+"queryLoanDetail", map);
	}
	
	/**
	 * 关联融资方式、项目表、放款表进行查询
	 * @author楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public Object getCreditQuotaLoan(Map<String, Object> map){		
		return Dao.selectOne(xmlPath+"queryFhfaLoanProject", map);
	}
	
	/**
	 * 获取当前项目的授信余额
	 * @author楊雪
	 * @createDate 2013-10-22<br>
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getBalanceCreditByPid(Map<String,Object> map){
		return (Map<String,Object>)Dao.selectOne(xmlPath+"getBalanceCreditByPid", map);
	}
	
	/**
	 * 确认资金到账后，更改授信余额和实际使用额度
	 * @author楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public int updateCreditQuota(Map<String, Object> map)throws SQLException {
		int i = Dao.update(xmlPath+"updateCreditQuota", map);
		return i;
	}
	
	/**
	 * 修改当前项目的授信余额
	 * @author楊雪
	 * @createDate 2013-10-22<br>
	 * @return
	 * @throws Exception
	 */
	public int updateBalanceCreditByPid(Map<String,Object> map){
		return Dao.update(xmlPath+"updateBalanceCreditByPid", map);
	}
	
	public int updateBailoutwayByPid(Map<String,Object> m) {
		return Dao.update(xmlPath+"updateBailoutwayByPid", m);
	}
	
	public int invalid(Map<String, Object> m) {
		return Dao.update(xmlPath+"invalid", m);
	}
	
	/**
	 * 获取银行账号
	 * getBankAccount
	 * @date 2013-10-23 上午10:19:17
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getBankAccount(Map<String,Object> m){
		return Dao.selectList(xmlPath+"getBankAccount", m);
	}
}
