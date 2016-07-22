package com.pqsoft.refundProject.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.screened.service.FinanceScreenService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class RefundProjectSerivce {

	private final String xmlPath = "refundProject.";
	
	/**
	 * 融资项目管理data
	 * toMgProjectData
	 * @date 2013-10-17 下午07:40:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgProjectData(Map<String,Object> map) {
		Page page = new Page(map);
		map.put("TYPE", "再融资方式");
		BaseUtil.getDataAllAuth(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgProjectData", map);//查询数据
		JSONArray json = new JSONArray();
		if(list.size()>0){
			for(int i=0; i<list.size(); i++){
				JSONObject obj = new JSONObject();
				Map<String,Object> data = list.get(i);
				obj.put("PRO_ID", data.get("ID"));//ID
				obj.put("BAILOUTWAY_NAME", data.get("BAILOUTWAY_NAME"));//融资方式
				obj.put("PROJECT_NAME", data.get("PROJECT_NAME"));//项目名称
				obj.put("PROJECT_CODE", data.get("PROJECT_CODE"));//项目编号
				
				//项目状态
				if(data.get("PROJECT_STATUS")==null){
					obj.put("PROJECT_STATUS", "未提交");
				}else if(data.get("PROJECT_STATUS").toString().equals("0")){
					obj.put("PROJECT_STATUS", "内部审核通过");
				}else if(data.get("PROJECT_STATUS").toString().equals("1")){
					obj.put("PROJECT_STATUS", "提交银行");
				}else if(data.get("PROJECT_STATUS").toString().equals("2")){
					obj.put("PROJECT_STATUS", "银行审核通过");
				}else if(data.get("PROJECT_STATUS").toString().equals("4")){
					obj.put("PROJECT_STATUS", "发起放款流程");
				}else if(data.get("PROJECT_STATUS").toString().equals("5")){
					obj.put("PROJECT_STATUS", "银行和内部审核不通过");
				}else if(data.get("PROJECT_STATUS").toString().equals("6")){
					obj.put("PROJECT_STATUS", "确认放款");
				}else if(data.get("PROJECT_STATUS").toString().equals("-1")){
					obj.put("PROJECT_STATUS", "内部审核不通过");
				}
				
				obj.put("CREATE_TIME", data.get("CREATE_TIME"));//创建时间
				obj.put("PAY_TOTAL", data.get("PAY_TOTAL"));//项目实际总额
				obj.put("BANK_TOTAL", data.get("BANK_TOTAL"));//贷款实际总额				
				obj.put("BAILOUTWAY_ID", data.get("BAILOUTWAY_ID"));//融资方式id
				obj.put("COMMIT_BANK", data.get("COMMIT_BANK"));//提交银行审批时间
				obj.put("BANK_FEEDBACK_TIME", data.get("BANK_FEEDBACK_TIME"));//银行反馈时间				
				obj.put("JG_ID", data.get("JG_ID"));//融资机构id
				obj.put("ORGAN_NAME", data.get("ORGAN_NAME"));//融资机构
				obj.put("GUARANT_WAY", data.get("GUARANT_WAY"));//融资担保方式				
				json.add(obj);
			}
		}
		
		page.addDate(json, Dao.selectInt(xmlPath+"toMgProjectCount", map));
		return page;
	}
	
	
	
	/**
	 * 融资项目明细
	 * getProjectCheckInfo
	 * @date 2013-10-17 下午08:03:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Map<String,Object> getProjectCheckInfo(Map<String, Object> map){
		map.put("TYPE", "再融资方式");
		return Dao.selectOne(xmlPath+"getProjectCheckInfo", map);
	}
	
	/**
	 * 查看项目下所包含的合同信息
	 * getProjectPayList
	 * @date 2013-10-17 下午07:55:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> getProjectPayList(Map<String, Object> m) {
		List<Map<String,Object>> list= Dao.selectList(xmlPath+"toShowProPay", m);
		FinanceScreenService fs=new FinanceScreenService();
		for (Map<String, Object> map : list) {
			map.put("PAY_WAY", m.get("PAY_WAY"));
			map.put("FID",  m.get("FID"));
			map.put("CLIENT_NAME",  map.get("NAME"));
			fs.queryRZTJ(map);
		}
		return list;
	}
	
	/**
	 * 查看项目向所包含的项目信息的未还款明细
	 * toMgRentDeatil
	 * @date 2013-10-18 上午10:18:17
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toMgRentDeatil(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toMgRentDeatil", map);
	}
	
	/**
	 * 修改融资项目详细信息
	 * updateProject
	 * @date 2013-10-18 上午11:55:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateProject(Map<String, Object> param) {
		return Dao.update(xmlPath+"updateProject", param);
	}
	
	/**
	 * 提交银行
	 * toCommitBank
	 * @date 2013-10-18 下午02:08:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int toCommitBank(Map<String,Object> map) {
		return Dao.update(xmlPath+"updstatus", map);
	}
	
	/**
	 * 流程通过
	 * updRefundProjectSHStatus
	 * @date 2013-10-23 下午07:50:30
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */	
	public int updRefundProjectSHStatus(Map<String, Object> map) {
		int a = Dao.update(xmlPath+"SHupdstatus", map);
		return a;
	}
	
	/**
	 * 流程不通过
	 * updRefundProjectSHStatus
	 * @date 2013-10-23 下午07:50:30
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */	
	public int NotSHStatus(Map<String,Object> map){
		int i = Dao.delete(xmlPath+"delORGANREFUND", map);
		int j = Dao.delete(xmlPath+"delEQUIPMENT", map);
		int k = Dao.delete(xmlPath+"delRENTDETAIL", map);
		int l = Dao.delete(xmlPath+"delRENTHEAD", map);
		int m = Dao.delete(xmlPath+"delPROJECT", map);		
		return i+j+k+l+m;
	}
}
