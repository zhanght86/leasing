package com.pqsoft.base.channel.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.BigDecimalUtil;
import com.pqsoft.util.SecuUtil;
import com.pqsoft.util.StringUtils;

public class ChannelService {
	private String basePath = "Channel.";
	public Map<String,Object> getOneSupplier(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSupp", param);
	}
	public List<Object> getAllArea(){
		return Dao.selectList(basePath+"selectArea");
	}	
	public List<Object> getLinkManList(Map<String,Object> param){
		return Dao.selectList(basePath+"getLinkManList", param);
	}	
	public List<Object> getInvestsByType(Map<String,Object> param){
		return Dao.selectList(basePath+"getInvestsByType", param);
	}
	/**
	 * 根据供应商ID查询上传文件信息
	 * @param file_type 
	 */
	public List<Object> findSupFileUploads(String suppliers_id, String file_type,String app_id,String app_name){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("SUP_ID", suppliers_id);
		dataMap.put("APP_ID", app_id);
		dataMap.put("APP_NAME", app_name);
		dataMap.put("FIL_TYPE", file_type);
		return Dao.selectList(basePath+"findSupFileUploads", dataMap);
	}
	
	public List<Object> getCreditFileUploads(String suppliers_id, List<Object> file_type,String app_id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("SUP_ID", suppliers_id);
		dataMap.put("APP_ID", app_id);
		List<Object> files = new ArrayList<Object>();
		for (Object object : file_type) {
			Map<String,Object> file = (Map<String,Object>)object;
			files.add(file.get("CODE").toString());
		}
		dataMap.put("FIL_TYPE", files);
		return Dao.selectList(basePath+"getCreditFileUpLoads", dataMap);
	}
	
	/**
	 * 
	 * @Description: 上传文件	 
	 * @return Map
	 * @throws Exception
	 * @date 2013-8-29
	 */
	public Map<String, Object> uploadFileForOne(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			final long MAX_SIZE = 30 * 1024 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path").toString();//
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			List<?> fileList = fp.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			List<Object> filepathList = new ArrayList<Object>();
			File file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = root+ File.separator+"supplier"+File.separator+ request.getParameter("SUP_NAME").toString()
							+ File.separator+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					BaseUtil.createDirectory(dir);
					file = new File(dir + File.separator + UUID.randomUUID()
							+ fileItem.getName());
					fileItem.write(file);
					 dataMap.put("FIL_NAME", fileItem.getName());
					 dataMap.put("FIL_PATH",file.getPath().toString().replace("\\", "/"));
					filepathList.add(file.getAbsoluteFile());
				}
			}

		} catch (Exception e) {
			 e.printStackTrace();
		}
		return dataMap;
	}
	/**
	 * 供应商添加上传文件信息 
	 */
	public int addSupFileUpload(Map<String,Object> map){
		return Dao.insert(basePath+"addSupFileUpload", map);
	}
	
	/**
	 * 根据ID查询附件信息
	 */
	public Map<String,Object> findDupFileByID(String fil_id){
		return Dao.selectOne(basePath+"findDupFileByID", fil_id);
	}
	
	/**
	 * 删除附件信息
	 */
	public int deleteSupFile(String fil_id){
		return Dao.update(basePath+"deleteSupFile", fil_id);
	}
	/**
	 * @param context 
	 * 
	 * @Title: getOverdueInfos 
	 * @Description: TODO(租金逾期情况) 
	 * @param param
	 * @return Object 
	 * @throws  
	 */
	public List<Map<String,Object>> getOverdueInfos(VelocityContext context, Map<String, Object> param) {
		
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String curr_date = StringUtils.nullToOther(param.get("CURR_DATE"), formate.format(new Date()));
		param.put("CURR_DATE", curr_date);
		
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		String AVG_MONTH ="";
		double AVG_AMOUNT_SUM = 0;
		double AVG_RENT_SUM = 0;
		double CUR_RENT_SUM = 0;
		double AVG_UK_RAT_SUM = 0;
		
		

		double AVG_UK_RAT = 0;
		double AVG_AMOUNT = 0;
		double AVG_RENT = 0;
		double AVG_CUR_RENT = 0;
		double AVG_AMOUNT1 = 0;
		double AVG_RENT1 = 0;
		int ind = 1;
		

		double rentSum11 = 0;//网银已扣划合计
		double rentSum12 = 0;//网银应扣划合计
		double rentSum21 = 0;//网银已扣划合计
		double rentSum22 = 0;//网银应扣划合计
		double rentSum31 = 0;//网银已扣划合计
		double rentSum32 = 0;//网银应扣划合计
		
		double rentSum41 = 0;//网银已扣划合计
		double rentSum42 = 0;//网银应扣划合计
		for(int i = 1; i < 13 ; i++){

			double tem11 = 0;
			double tem12 = 0;
			double tem21 = 0;
			double tem22 = 0;
			double tem31 = 0;
			double tem32 = 0;
			double tem41 = 0;
			double tem42 = 0;
			
			Map<String,Object> m = new HashMap<String,Object>();
			param.put("SQL_MONTH", i);
			Map<String,Object> temp = (Map<String,Object>)Dao.selectList(basePath+"getOverdueAmountMonth",param).get(0);
			m.put("CUR_MONTH",temp.get("CUR_MONTH"));
			m.put("AMOUNT",temp.get("AMOUNT"));
			m.put("AMOUNT1",temp.get("AMOUNT2"));
			AVG_AMOUNT1 = Double.valueOf(StringUtils.nullToOther(temp.get("AMOUNT2"),"0"));
			
			tem11 = Double.valueOf(temp.get("AMOUNT1").toString());
			tem12 = Double.valueOf(temp.get("AMOUNT2").toString());
			temp = (Map<String,Object>)Dao.selectList(basePath+"getOverdueRentMonth",param).get(0);
			m.put("RENT",temp.get("RENT"));

			tem21 = Double.valueOf(temp.get("RENT1").toString());
			tem22 = Double.valueOf(temp.get("RENT2").toString());
			temp = (Map<String,Object>)Dao.selectList(basePath+"getOverdueCurrRentMonth",param).get(0);
			m.put("CUR_RENT",temp.get("CUR_RENT"));
			m.put("RENT1",temp.get("RENT2"));
			AVG_RENT1 = Double.valueOf(StringUtils.nullToOther(temp.get("RENT2"),"0"));

			tem31 = Double.valueOf(temp.get("RENT1").toString());
			tem32 = Double.valueOf(temp.get("RENT2").toString());
//			System.out.println("-------------"+i);
//			temp = (Map<String,Object>)Dao.selectList(basePath+"getUKRAT",param).get(0);
//			m.put("UK_RAT",temp.get("UK_RAT"));
			temp = (Map<String,Object>)Dao.selectList(basePath+"getCurrMonthCustRat",param).get(0);
			m.put("UK_RAT",temp.get("CUSTRAT"));
			
			tem41 = Double.valueOf(temp.get("RENT1").toString());
			tem42 = Double.valueOf(temp.get("RENT2").toString());
			
			
			if(ind<7){
				rentSum11 += tem11;
				rentSum21 += tem21;
				rentSum31 += tem31;
				rentSum41 += tem41;
				if(ind==1){
					rentSum12 += tem12;
					rentSum22 += tem22;
					rentSum32 += tem32;
					rentSum42 += tem42;
				}else{
					rentSum12 += tem11;
					rentSum22 += tem21;
					rentSum32 += tem31;
					rentSum42 += tem41;
				}
				
				AVG_AMOUNT_SUM += Double.parseDouble(m.get("AMOUNT").toString());
				AVG_RENT_SUM += Double.parseDouble(m.get("RENT").toString());
				CUR_RENT_SUM += Double.parseDouble(m.get("CUR_RENT").toString());
				AVG_UK_RAT_SUM += Double.parseDouble(m.get("UK_RAT").toString());
				if(ind==1){
					AVG_MONTH = "~"+ m.get("CUR_MONTH").toString();
				}
				if(ind==6){
					AVG_MONTH = m.get("CUR_MONTH").toString() + AVG_MONTH;
				}
			}
			
			l.add(m);
			ind++;
		}

		AVG_AMOUNT = BigDecimalUtil.div(AVG_AMOUNT_SUM, 6, 2); 
		AVG_RENT = BigDecimalUtil.div(AVG_RENT_SUM, 6, 2); 
		AVG_CUR_RENT = BigDecimalUtil.div(CUR_RENT_SUM, 6, 2);
//		AVG_UK_RAT = BigDecimalUtil.div(AVG_UK_RAT_SUM, 6, 2);

//		AVG_AMOUNT = BigDecimalUtil.div(rentSum11, rentSum12, 2)*100; 
//		AVG_RENT = BigDecimalUtil.div(rentSum21, rentSum22, 2)*100; 
//		AVG_CUR_RENT = BigDecimalUtil.div(rentSum31, rentSum32, 2)*100;
		AVG_UK_RAT = BigDecimalUtil.div(rentSum41, rentSum42, 2)*100;
		
		context.put("overdueInfos", l);
		context.put("AVG_MONTH", AVG_MONTH);
		context.put("AVG_AMOUNT", AVG_AMOUNT);
		context.put("AVG_AMOUNT1", AVG_AMOUNT1);
		context.put("AVG_RENT1", AVG_RENT1);
		context.put("AVG_RENT", AVG_RENT);
		context.put("AVG_CUR_RENT", AVG_CUR_RENT);
		context.put("AVG_UK_RAT", AVG_UK_RAT);
		
		return l;
	}
	/**
	 * 
	 * @Title: savestat 
	 * @Description: TODO(保存申请单) 
	 * @param param 
	 * @return void 
	 * @throws 
	 */
	public void savestat(Map<String, Object> param) {
		Dao.update(basePath+"updatestat",param);
		
	}
	/*
	 * 生成资信申请单
	 * SUP_ID	供应商id
	 * APP_TYPE 类型
	 * SQL_ID 主键
	 * USERNAME 申请人
	 * 
	 * SELECT ${SQL_ID},
		c.CREDIT_ID,t.SUP_ID,#{APP_TYPE},'','','',c.EXPAND_MULTIPLE,0,0,'',SYSDATE,#{USERNAME},0,0
		from T_SYS_SUPPLIERS t 
		LEFT JOIN T_CREDIT_CHANNEL c ON t.SUP_ID = c.SUP_ID 
		WHERE t.SUP_ID = #{SUP_ID} 
		AND NOT EXISTS (SELECT 1 from T_CREDIT_CHANNEL_APPLY tc WHERE tc.SUP_ID = #{SUP_ID}  AND tc.STATUS=0) 
	 */
	public String insertCreditApp(Map<String, Object> param) {
		
		String id = "";
		List l = Dao.selectList(basePath+"getCreditApp",param);
		int i = 0;
		if(l.size() == 0){
			id = Dao.getSequence("SEQ_T_CREDIT_CHANNEL_APPLY");
			param.put("SQL_ID",id);
			i = Dao.insert(basePath+"insertCreditApp",param);
			if(i == 0){
				throw new ActionException("存在审批中的流程，请勿重复发起");
			}
		}else{
			id = ((Map<String,Object>)l.get(0)).get("ID").toString();
		}
		Dao.update(basePath + "updateCreditStatus", param);//更新主表申请状态
		return id;
	}
	
	/*
	 * 审批通过
	 */
	public void doend(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		//申请单id
		String APP_ID = null;
		if (!StringUtils.isBlank(param.get("APP_ID"))) {
			APP_ID = param.get("APP_ID") + "";
//			String JBPMID = jbpmId.substring(0, jbpmId.indexOf("."));
//			param.put("JBPMID", JBPMID);
			param.put("APP_ID", APP_ID);
			param.put("APPLY_STATUS", "1");
			param.put("CONFIRM_DATE", "sysdate");
			param.put("STATUS", "1");
//			param.put("END_DATE", "sysdate");
			
			SecuUtil.putUserInfo(param);
			Dao.update(basePath+"updatestat", param);
		}else{
			throw new ActionException("没有找到匹配的项目,请联系管理员！");
		}
	}
	/*
	 * 审批流程结束
	 */
	public void doFinal(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		//申请单id
		String APP_ID = null;
		if (!StringUtils.isBlank(param.get("APP_ID"))) {
			APP_ID = param.get("APP_ID") + "";
//			String JBPMID = jbpmId.substring(0, jbpmId.indexOf("."));
//			param.put("JBPMID", JBPMID);
			param.put("APP_ID", APP_ID);
//			param.put("CONFIRM_DATE", "sysdate");
			param.put("END_DATE", "sysdate");
			param.put("STATUS", "1");//已通过
			
//			SecuUtil.putUserInfo(param);
			Dao.update(basePath+"updatestat", param);
			Map<String,Object> m = (Map<String,Object>)(Dao.selectList(basePath+"selectCredit", param).get(0));
			String APPLY_TYPE = (String)m.get("APPLY_TYPE");
//			String ENLARGE_MULTIPLE  = (String)m.get("ENLARGE_MULTIPLE");
			param.put("EXPAND_MULTIPLE", m.get("ENLARGE_MULTIPLE"));
			if(StringUtils.isNotBlank(APPLY_TYPE) && APPLY_TYPE.equals("1")){
				param.put("INITIAL_AMOUNT", m.get("APPLY_AMOUNT"));
				param.put("STATUS", "1");
			}else if(StringUtils.isNotBlank(APPLY_TYPE) && APPLY_TYPE.equals("2")){
				param.put("STANDARD_AMOUNT", m.get("APPLY_AMOUNT"));
				param.put("STATUS", "1");
			}else if(StringUtils.isNotBlank(APPLY_TYPE) && APPLY_TYPE.equals("3")){
				param.put("STANDARD_AMOUNT1", m.get("APPLY_AMOUNT"));
				param.put("STATUS", "1");
			}else{
				throw new ActionException("申请单中缺少类型信息,请联系管理员！");
			}
			Dao.update(basePath+"updateCredit", param);
		}else{
			throw new ActionException("没有找到匹配的项目,请联系管理员！");
		}
	}
	
	/*
	 * 获取申请单信息
	 */
	public Map<String,Object> getAppInfo(Map<String, Object> param) {
		Map<String,Object> p = (Map<String,Object>)(Dao.selectList(basePath+"selectCredit", param).get(0));
		return p;
	}
	public Map<String,Object> selectCreditScore(Map<String, Object> param) {
		Map<String,Object> p = new HashMap<String,Object>();
		List<Map<String,Object>> l = Dao.selectList(basePath+"selectCreditScore", param);
		for(Map<String,Object> t : l){
			p.put(StringUtils.nullToString(t.get("EN_NAME")).toUpperCase(), t.get("SCORE"));
			p.put("DESCRIP"+StringUtils.nullToString(t.get("EN_NAME")).substring(5), t.get("DESCRIP"));
			p.put(StringUtils.nullToString(t.get("EN_NAME")).toUpperCase().replace("SCORE", "SCORESUM"), t.get("SCORE_SUM"));
		}
		return p;
	}
	
	/**
	 * 来款核销
	 * 
	 */
	public Page toMgAppAlready(Map<String, Object> param) {
		Page page = new Page(param);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath + "getFundPageData",param)), Dao.selectInt(basePath + "getFundPageDataCount", param));
		return page;
	}
	/**
	 * 来款核销
	 * 
	 */
	public Page toMgAppAlreadyPro(Map<String, Object> param) {
		Page page = new Page(param);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath + "getFundPageProData",param)), Dao.selectInt(basePath + "getFundPageProDataCount", param));
		return page;
	}
	/**
	 * 
	 * @Title: getPoolInfo 
	 * @Description: TODO(资金池信息) 
	 * @param param
	 * @return Object 
	 * @throws 
	 */
	public List getPoolInfo(Map<String, Object> param) {
		List<Map> l = new ArrayList<Map>();
		//
		Map<String,Object> m1 = new HashMap<String,Object>();
		param.put("POOLID", "1");
		m1.put("POOLID", "1");
		m1.put("POOLUSERMONEY", ((Map<String,Object>)Dao.selectList(basePath+"getPoolInfo", param).get(0)).get("SUM_AMT"));
		m1.put("POOLNAME", "DB保证金池");
		l.add(m1);
		//
		Map<String,Object> m2 = new HashMap<String,Object>();
		param.put("POOLID", "2");
		m2.put("POOLID", "2");
		m2.put("POOLUSERMONEY", ((Map<String,Object>)Dao.selectList(basePath+"getPoolInfo", param).get(0)).get("SUM_AMT"));
		m2.put("POOLNAME", "代理垫汇资金池");
		l.add(m2);
		//
		Map<String,Object> m3 = new HashMap<String,Object>();
		param.put("POOLID", "3");
		m3.put("POOLID", "3");
		m3.put("POOLUSERMONEY", ((Map<String,Object>)Dao.selectList(basePath+"getPoolInfo", param).get(0)).get("SUM_AMT"));
		m3.put("POOLNAME", "设备付款资金池");
		l.add(m3);
		//
		Map<String,Object> m4 = new HashMap<String,Object>();
		param.put("POOLID", "4");
		m4.put("POOLID", "4");
		m4.put("POOLUSERMONEY", ((Map<String,Object>)Dao.selectList(basePath+"getPoolInfo", param).get(0)).get("SUM_AMT"));
		m4.put("POOLNAME", "待处理资金池");
		l.add(m4);
		
		return l;
	}
	public int dohexiao(Map<String, Object> param) {
		String id = Dao.getSequence("SEQ_FI_CREDIT_CHANNEL_F_A");
		param.put("SQL_ID", id);
		param.put("STATUS", "1");//1是收款单
		int temp = 0;
		temp += Dao.insert(basePath+"insertApplyInfo", param);
		temp += Dao.update(basePath + "updateFund",param);
		
		//插入来款信息
		String FI_REALITY_ACCOUNT = StringUtils.nullToOther(param.get("FI_REALITY_ACCOUNT"),0);
//		${POOL_ID},${AMT},${FUND_TYPE}
		if(Double.parseDouble(FI_REALITY_ACCOUNT) > 0){
			param.put("FUND_TYPE", "来款");
			Dao.insert(basePath + "insertIntoAccount",param);
		}
		List<Map<String,Object>> poolData = JSONArray.fromObject(param.get("poolData"));
		for(Map<String,Object> st : poolData){
			if(st == null || st.size() == 0){
				continue;
			}
			String poolid = st.get("POOLID").toString();
			String pooltype = st.get("POOL_TYPE").toString();
			String poolusermoney = st.get("POOLUSERMONEY").toString();
			String dichong_money = st.get("dichong_money").toString();
			
			Map<String,Object> pa = new HashMap<String,Object>();
			pa.put("POOLID", poolid);
			pa.put("SUP_ID", param.get("SUP_ID"));
			List<Map<String,Object>> poolDetail = Dao.selectList(basePath+"getPoolDetail", pa);
			double j = Double.parseDouble(dichong_money);
			for(Map<String,Object> te : poolDetail){
				String tPool_id = te.get("POOL_ID").toString();
				Double canuse_money = Double.parseDouble(te.get("CANUSE_MONEY").toString());
				
				
				
				if(canuse_money >= j){
					param.put("FI_REALITY_ACCOUNT", j);
					param.put("POOL_ID", tPool_id);
					param.put("FUND_TYPE", pooltype);
					Dao.insert(basePath + "insertIntoAccount",param);
					
					Map<String,Object> par = new HashMap<String,Object>();
					par.put("POOL_ID", tPool_id);
					par.put("CANUSE_MONEY", canuse_money - j);
					Dao.update(basePath + "updatePoolById", par);
					break;
				}
				param.put("FI_REALITY_ACCOUNT", canuse_money);
				param.put("POOL_ID", tPool_id);
				param.put("FUND_TYPE", pooltype);
				Dao.insert(basePath + "insertIntoAccount",param);
				
				Map<String,Object> par = new HashMap<String,Object>();
				par.put("POOL_ID", tPool_id);
				par.put("CANUSE_MONEY", 0);
				Dao.update(basePath + "updatePoolById", par);
				j -= canuse_money;
			}
			
		}
		//更新资金池信息
		
		return temp;
	}
	
	public void updateJbpmId(Map<String, Object> param) {
//		param.put("APP_ID", "");
		Dao.update(basePath + "updateJbpmId", param);
	}
	public String getFirstProjIdBySupId(Map<String, Object> param) {
		Map<String,Object> res = (Map<String,Object>)Dao.selectOne(basePath + "getFirstProjIdBySupId", param);
		return StringUtils.nullToString(res == null ? "" : res.get("ID"));
	}
	
	public int addChannelFund(Map<String,Object> param ){
		return Dao.insert(basePath+"addChannelFundMess", param);
	}
	
	public int updateChannelFund(Map<String,Object> param){
		return Dao.update(basePath+"updateChannelFund", param);
	}
	/**
	 * 
	 * @Title: savescore 
	 * @Description: (保存打分卡) 
	 * @param param 
	 * @return void 
	 * @throws 
	 */
	public void savescore(Map<String, Object> param) {
		Dao.delete(basePath+"deleteCreditScoreByAppid", param);
		param.put("MODEL","渠道_打分卡");
		
		if(StringUtils.nullToOther(param.get("STATUS"), "-1").equals("-1")){
			// ${SCORE_ID},#{MODEL},#{NAME},#{EN_NAME},${SCORE},#{DESCRIP},${APP_ID}
			for(int i = 1; i <= 5; i++){
				param.put("SCORE_ID",Dao.getSequence("SEQ_T_CREDIT_CHANNEL_SCORE"));
				param.put("NAME",""+i);
				param.put("EN_NAME","score0"+i);
				param.put("SCORE",StringUtils.nullToOther(param.get("score0"+i),"0"));
				param.put("SCORE_SUM",StringUtils.nullToOther(param.get("scoreSum0"+i),"0"));
				param.put("SCORE",StringUtils.nullToOther(param.get("score0"+i),"0"));
				param.put("DESCRIP",param.get("descrip0"+i));
				param.put("APP_ID",param.get("APP_ID"));
				Dao.insert(basePath+"insertCreditScore", param);
			}
		}else{
			for(int i = 1; i <= 6; i++){
				param.put("SCORE_ID",Dao.getSequence("SEQ_T_CREDIT_CHANNEL_SCORE"));
				param.put("NAME",""+i);
				param.put("EN_NAME","score1"+i);
				param.put("SCORE",StringUtils.nullToOther(param.get("score1"+i),"0"));
				param.put("SCORE_SUM",StringUtils.nullToOther(param.get("scoreSum1"+i),"0"));
				param.put("DESCRIP",param.get("descrip1"+i));
				param.put("APP_ID",param.get("APP_ID"));
				Dao.insert(basePath+"insertCreditScore", param);
			}
		}
	}
	
	/*
	 *删除流程数据
	 */
	public void deleteCreditApply(Map<String, Object> param) {
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("SUP_ID", param.get("SUP_ID"));
		p.put("STATUS", "-1");
		p.put("APPLY_STATUS", "-1");
		p.put("APP_ID", param.get("APP_ID"));
		Dao.update(basePath+"updatestat", p);
		p.clear();
		p.put("APP_ID", param.get("APP_ID"));
		Dao.update(basePath+"updateCredit", p);
		
	}
	
	
}
