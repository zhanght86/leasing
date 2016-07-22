package com.pqsoft.returnVisit.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class ReturnVisitService {
	List<Object> resultList = new ArrayList<Object>();
	final private static Logger logger = Logger.getLogger(ReturnVisitService.class);
	/**
	 * 查询所有的合同
	 */
	public Page newInsuranceManage(Map<String,Object> m){
		Object Curr=m.get("PAGE_CURR");//当前页
		Object count=m.get("PAGE_COUNT");//每页显示的条数
		int pageCurr = Integer.parseInt(Curr==null?"1":Curr.toString());
		int pageCount = Integer.parseInt(count==null?"10":count.toString());

		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);//开始条数
		m.put("PAGE_END", pageCount * pageCurr);//结束的条数
		Page page = PageUtil.getPage(m, "ReturnVisit.NewInsuranceManage", "ReturnVisit.NewInsuranceManage_count");
		return page;
	}
	/**
	 * 根据合同ID查询车辆
	 */
	public Object getEmpByRectId(Map map){
		return Dao.selectList("ReturnVisit.getEmpByRectId", map);
	}
	public Object selectUploadphotoList(String BECR_ID){
		return Dao.selectList("ReturnVisit.selectUploadphotoList", BECR_ID);
	}
	public Object selectT_VISIT_REVIEWRECORDSByID(){
		return Dao.selectOne("ReturnVisit.selectT_VISIT_REVIEWRECORDSByID", null);
	}
	
	public int updateUploadphoto(String CULP_ID){
		return Dao.update("ReturnVisit.updateUploadphoto", CULP_ID);
	}
	public boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			flag = false;
		}
		return flag;
	}	

	public Object queryVisitReviewRecordByEqID(Map<String, Object> m)
			throws Exception {
		return Dao.selectOne(
				"ReturnVisit.queryVisitReviewRecordByEqID", m);
	}
	public Object queryContractVisitReviewRecordByEqID(Map<String, Object> m)
			throws Exception {
		return Dao.selectOne(
				"ReturnVisit.queryContractVisitReviewRecordByEqID", m);
		}
	public Object selectDictionary(String type){
		return Dao.selectList(
				"ReturnVisit.selectDictionary", type);
	}
	public Object selectForBase(Map<String, Object> m) throws Exception {
		return Dao.selectOne("ReturnVisit.selectForBase",
				m);
	}

	public Object queryUpdateVisit(Map<String, Object> m) throws Exception {
		return Dao.selectOne(
				"ReturnVisit.queryUpdateVisit", m);
	}
	public Object selectUploadphotoOne(String CULP_ID){
		return Dao.selectOne("ReturnVisit.selectUploadphotoOne", CULP_ID);
	}
	public Object pdfbyEQ_ID(Map<String, Object> m) throws Exception {
		return Dao.selectOne("ReturnVisit.pdfbyEQ_ID", m);
	}

	public String insertReturnVisit(Map<String, Object> m) throws Exception {
		int result=Dao.insert("ReturnVisit.insertReturnVisit", m);
		String count="";
		if(result>0){
			count=Dao.getSequence("SEQ_RETURNVISIT_ID");
		}
		return count;
	}
	public int insertReturnCisitT_cust_uploadphoto(Map map){
		return Dao.insert("ReturnVisit.insertReturnCisitT_cust_uploadphoto", map);
	}
	public int updateEquipmentAddress(Map<String,Object> m) throws Exception{
		return Dao.insert("ReturnVisit.updateEquipmentAddress", m);
	}
	public int updateReturnVisitsb(Map<String, Object> m) throws Exception {
		return Dao.update("ReturnVisit.updateReturnVisitsb", m);
	}
	public int updateReturnVisit(Map<String, Object> m) throws Exception {
		return Dao.update("ReturnVisit.updateReturnVisit", m);
	}
	public Object selectContractByPatrol(Map<String,Object> map)throws Exception{
		return Dao.selectOne("ReturnVisit.selectContractByPatrol", map);
	}
	public Object selectUpdateContract(Map<String,Object> m)throws Exception{
		return Dao.selectList("ReturnVisit.selectUpdateContract", m);
	}
	//--------------------打分
	public Object getTempplateByBecrId(Map<String, Object> m) throws Exception {
		return Dao.selectOne("ReturnVisit.getTempplateByBecrId", m);
	}
	/**
	 * @memo  方法说明：获取模版信息
	 *
	 */
	public Object querySelfByid(Map<String, Object> m) {
		List<Object> list = Dao.selectList("PM.SecuEvaluate.queryById",m);
		return list;
	}
	/**
	 * 
	 * @memo  方法说明：获取承租人打分模版及内容
	 *
	 */
	public List<Object> queryByid(Map<String, Object> m) {
		m.put("parentsid", m.get("id"));
		List<Object> tree = tree(m, 2);
		return tree;
		
	}
	public Map<String, Object> getScore(String id) {
		return (Map<String, Object>) Dao.selectOne("ReturnVisit.getScore", id);
	}
	public  List tree(Map map,int level) {
		String preStr = "";
		for (int i = 0; i < level; i++) {
			preStr += "k";
		}
	    List childs = Dao.selectList("PM.SecuEvaluate.queryByParentid",map);
		if (childs != null) {
	    for(int i=0;i<childs.size();i++) {
	       Map m = (Map)childs.get(i);
	       System.out.println(preStr +m.get("TITLE"));
	       Map resultMap = new HashMap();
	       resultMap.put(preStr, m);
	       resultList.add(resultMap);
	       map.put("parentsid", m.get("ID"));
	       List list = Dao.selectList("PM.SecuEvaluate.queryByParentid",map);
	       if(list!=null) {
	    	   tree(map, level + 1);
	       }
	    }
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List getXS(Map<String,Object> param){
		return Dao.selectList("ReturnVisit.getXS", param);
	}
	
	public boolean updateReturnVisitXSbyID(Map<String,Object> param){
		return Dao.update("ReturnVisit.updateReturnVisitXSbyID", param) > 0 ? true : false;
	}
	
	public boolean updateReturnVisitXS(Map<String,Object> param){
		return Dao.update("ReturnVisit.updateReturnVisitXS", param) > 0 ? true : false;
	}
	
	public boolean insertReturnVisitXS(Map<String,Object> param){
		String TYPE=param.get("TYPE")+"";
		param.put("USERCODE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		if(TYPE.equals("1"))
		{
			String CUST_TYPE=param.get("CUST_TYPE")+"";
			if(CUST_TYPE.equals("LP")){//法人
				Dao.insert("ReturnVisit.insertCustInfoFR", param);
			}
			else{//个人
				Dao.insert("ReturnVisit.insertCustInfoGR", param);
			}
		}
		return Dao.update("ReturnVisit.insertReturnVisitXS", param) > 0 ? true : false;
	}
	
	public List<Object> getXSbyFEID( Map<String,Object> param){
		return Dao.selectList("ReturnVisit.getXSbyFEID", param);
	}
	
	@SuppressWarnings("unchecked")
	public Map getCustBaseInfo(Map<String,Object> param){
		return Dao.selectOne("ReturnVisit.queryCustBaseInfo", param);
	}
	
	
	/*****************************************************************************************************************
	 ***************                           @author yx   @date 2015-03-09                           ***************
	 ******************                       以下为客户资信过程中的家访信息管理                                                        *******************
	 ***********************                                                                 *************************
	 *****************************************************************************************************************/
	public Page toMgVisit(Map<String,Object> map){
		map.put("zj", "租金");
		map.put("zxfwd", "资信访问地点");
		map.put("ydlb", "疑点类别");
		return PageUtil.getPage(map, "ReturnVisit.toMgVisit", "ReturnVisit.toMgVisitC");
	}
	
	/**
	 * 更具项目id查看客户名称，id， 编号
	 * @author: yx 
	 * @date: 2015-3-16
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object toGetProjectInfo(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.toGetProjectInfo", map);
	}
	/*
	 * 根据项目id查看家访记录关联信息,为了支持多次家访  added by zengqt,2015-09-17
	 */
	public List<Map<String, Object>> toGetProjectInfoAsk(Map<String,Object> map){
		return Dao.selectList("ReturnVisit.toGetProjectInfo", map);
	}

	/**
	 * 保存新增家访记录
	 * @author: yx 
	 * @date: 2015-3-16
	 * @return_type:int
	 * @param map
	 * @return
	 */
	public int doAddReturnVisitSave(Map<String,Object> map){
		map.put("USERCODE", Security.getUser().getCode());
		map.put("USERNAME", Security.getUser().getName());
		return Dao.insert("ReturnVisit.insertReturnVisitXS",map);
	}
	
	/**
	 * 查看家访记录
	 * @author: yx 
	 * @date: 2015-3-16
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object toViewVisit(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.toViewVisit",map);
	}
	
	/**
	 * 更具项目id查看家访记录
	 * @author: yx 
	 * @date: 2015-3-19
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object toViewVisit1(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.toViewVisit1",map);
	}
	/*
	 * 根据项目id查看家访记录,为了支持多次家访  added by zengqt,2015-09-17
	 */
	public List<Map<String, Object>> toViewVisit1Ask(Map<String,Object> map){
		return Dao.selectList("ReturnVisit.toViewVisit1", map);
	}
	
	public Object toGetAddrVister(Map<String,Object> map){
		if(map != null){
			String addr = map.get("ZX_VISIT_ADDR").toString();
			if("1".equals(addr)){
				return Dao.selectOne("ReturnVisit.toGetAddrVister1",map);//获取承租人家庭住址
			}else if("2".equals(addr)){
				return Dao.selectOne("ReturnVisit.toGetAddrVister2",map);//获取承租人公司地址
			}else if("3".equals(addr)){
				return Dao.selectOne("ReturnVisit.toGetAddrVister3",map);//获取承租人公司&家庭地址
			}
		}
		return null;
	}
	
	//判断是否添加家访详细信息
	public Object getFujianInfo(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.getFujianInfo",map);
	}
	
	/**
	 * 获取共同承租人信息
	 * @author: yx 
	 * @date: 2015-4-9
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object getGCRInfo(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.getGCRInfo", map);
	}
	
	/**
	 * 获取担保人信息
	 * @author: yx 
	 * @date: 2015-4-9
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object getGUARANTORInfo(Map<String,Object> map){
		return Dao.selectOne("ReturnVisit.getGUARANTORInfo",map);
	}
	
	/**
	 * 修改家访记录
	 * @author: yx 
	 * @date: 2015-3-16
	 * @return_type:int
	 * @param map
	 * @return
	 */
	public int doUpReturnVisitSave(Map<String,Object> map){
		return Dao.update("ReturnVisit.doUpVisit", map);
	}
	
	/**
	 * 删除家访记录
	 * @author: yx 
	 * @date: 2015-3-16
	 * @return_type:int
	 * @param map
	 * @return
	 */
	public int doDelVisit(Map<String,Object> map){
		return Dao.delete("ReturnVisit.doDelVisit", map);
	}
	
	public int doDelVisit1(Map<String,Object> map){
		return Dao.delete("ReturnVisit.doDelVisit1", map);
	}
	
	/*
	 * 查询实际费用明细
	 */
	public List<Map<String, Object>> SjCostDetail(Map<String,Object> map){
		return Dao.selectList("ReturnVisit.sjCostDetail", map);
	}
	
}