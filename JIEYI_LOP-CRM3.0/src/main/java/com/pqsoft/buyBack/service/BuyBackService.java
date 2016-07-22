package com.pqsoft.buyBack.service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class BuyBackService {
	final private static Logger logger = Logger.getLogger(Util.class);
	public Page queryPage(Map<String, Object> m) {
		Page page = new Page(m);
		return PageUtil.getPage(m, "BuyBack.getAllBuyBack", "BuyBack.getAllBuyBack_count");
	}
	public Page buyBackManagementPage(Map<String, Object> m) {
		Page page = new Page(m);
		return PageUtil.getPage(m, "BuyBack.buyBackManagement", "BuyBack.buyBackManagement_count");
	}
	public Page buyBackQueryPage(Map<String, Object> m) {
		Page page = new Page(m);
		return PageUtil.getPage(m, "BuyBack.buyBackQuery", "BuyBack.buyBackQuery_count");
	}
	public Map queryProductAndCompany() {
		List<Object> products = Dao.selectList("BuyBack.queryAllProduct");
		List<Object> companys = Dao.selectList("BuyBack.queryAllCompany");
		Map rm = new HashMap();
		rm.put("products", products);
		rm.put("companys", companys);
		return rm;
	}
	@SuppressWarnings("unchecked")
	public void warning(Map<String, Object> m) {
		//Dao.insert("BuyBack.update_buyBackWarning", m);
		//调用李超写的存过计算罚息
		String ACCOUNT_DATE=m.get("ACCOUNT_DATE")+"";
		String PAYLIST_CODE=m.get("PAY_CODE")+"";
		//如果之前发起过预警则先删除
		Dao.delete("BuyBack.del_fil_buy_back_status0", PAYLIST_CODE);
		boolean flag = strToDate(ACCOUNT_DATE).getTime()>strToDate(dateToStr(new Date())).getTime();
		Map tm = new HashMap();
		tm.put("PAYCODEPARAM", PAYLIST_CODE);
		tm.put("NEW_DATE", ACCOUNT_DATE);
		if(flag){
			Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
		}
		//并更新取整
		Dao.update("BuyBack.round_overdue_appointed", tm);
		tm.put("PAYLIST_CODE", PAYLIST_CODE);
		tm.put("ACCOUNT_DATE", ACCOUNT_DATE);
		double overdue = Dao.selectDouble("BuyBack.query_fi_overdue_ACCOUNT_DATE", tm);
		//删除刚才插入的罚息数据
		Dao.update("BuyBack.del_overdue_appointed", PAYLIST_CODE);
		//插入回购表
		m.put("PAYLIST_CODE", PAYLIST_CODE);
		m.put("PENALTY_AMT", overdue);
		Dao.insert("BuyBack.update_buyBackWarning", m);

	}
	public Page buyBackWarning(Map<String, Object> m) {
		Page page = new Page(m);
		String ORD_ID = BaseUtil.getSupOrgChildren();
		if(ORD_ID!=null&&!"".equals(ORD_ID)&&!"null".equals(ORD_ID)){
			m.put("ORD_ID", ORD_ID);
		}
		List list = Dao.selectList("BuyBack.BuyBackWarning", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("BuyBack.BuyBackWarning_count", m));
		return page;
	}
	public void refreshWarning() {
		Dao.delete("BuyBack.refreshWarning");
	}
	@SuppressWarnings("unchecked")
	public int buyBackNormal(Map<String, Object> m) {
		m.put("ID", m.get("PAY_ID"));
		//得到还款计划编号
		Map pay = Dao.selectOne("PayTask.queryPayByID",m);
		m.put("PAYLIST_CODE", pay.get("PAYLIST_CODE"));
		//插入主表queryPayByID
		Dao.insert("PayTask.upgrade_payplan", m);
		//更新还款计划状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("ID") + "");
		temp.put("STATUS", "5");
		Dao.update("PayTask.update_payplan", temp);

		//插入回购新添加的费用字段
		Map other = new HashMap();
		other.put("NEWID", m.get("NEWID"));
		other.put("ITEM_NAME", "税金");
		other.put("ITEM_MONEY", m.get("taxes"));
		Dao.insert("BuyBack.buyBackOther",other);

		other.put("NEWID", m.get("NEWID"));
		other.put("ITEM_NAME", "回购留购价款");
		other.put("ITEM_MONEY", m.get("liugoujia"));
		Dao.insert("BuyBack.buyBackOther",other);

		other.put("NEWID", m.get("NEWID"));
		other.put("ITEM_NAME", "回购手续费");
		other.put("ITEM_MONEY", m.get("HG_SXF"));
		Dao.insert("BuyBack.buyBackOther",other);

		// 插入明细表并更新当前日期往下的利息为0
		Dao.insert("BuyBack.insert_detialBySelf", m);
		Dao.update("BuyBack.detial_interest_zero",m);

//		// 删除财务表数据
//		Dao.delete("PayTask.deleteBeginning", m);
//		// 同步财务数据
//		Map<String, String> temp1 = new HashMap<String, String>();
//		temp1.put("PAY_ID", m.get("NEWID") + "");
//		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
//		temp1.put("PMT", "PMT租金");
//		temp1.put("ZJ", "租金");
//		temp1.put("SYBJ", "剩余本金");
//		temp1.put("D", "第");
//		temp1.put("QI", "期");
//		Dao.insert("PayTask.synchronizationBeginning", temp1);

		//跟新回购数据
		m.put("PAY_ID", m.get("NEWID"));
		m.put("CREATOR", Security.getUser().getId());
		m.put("BUY_BACK_STATUS", 1);
		m.put("IS_END_STATUS", "0");
		int i = Dao.insert("rentDk.doInsertBuyBack", m);

		//发起流程第一步不能修改项目状态，待流程审批通过后再修改
		//this.doUpdateProStatus(m);
		return i ;
	}

	/**
	 * 添加回购单
	 * toSaveBuyBack
	 * @date 2013-12-1 下午03:05:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void toSaveBuyBack(JSONObject map){
		//跟新回购数据
		map.put("BUY_BACK_STATUS", 2);
		map.put("CREATOR", Security.getUser().getId());
		map.put("IS_END_STATUS", "0");
		map.put("BUY_BACK_STATUS", 2);
		map.put("BZJ", map.get("BZJ_"));
		Dao.insert("rentDk.doInsertBuyBack", map);


	}

	/**
	 * 更新项目状态
	 * doUpdateProStatus
	 * @date 2013-12-1 下午03:07:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void doUpdateProStatus(Map<String,Object> map){
		Dao.update("BuyBack.toUpdataProStatus", map);
	}

	//分期回购
	@SuppressWarnings("unchecked")
	public void buyBackStages(JSONObject m) {

		m.put("ID", m.get("PAY_ID"));
		//得到还款计划编号
		//Map pay = Dao.selectOne("PayTask.queryPayByID",m);
		m.put("PAYLIST_CODE", m.get("PAY_CODE"));
		//插入主表queryPayByID
		Dao.insert("PayTask.upgrade_payplan", m);
		//更新还款计划状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("ID") + "");
		temp.put("STATUS", "5");
		Dao.update("PayTask.update_payplan", temp);

		// 插入明细表并删除未付款的租金信息
		PayTaskService taskService = new PayTaskService();

		List<Map<String, String>> list = taskService.getParsePayList((JSONArray)m.get("data_"));

		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		//Dao.insert("BuyBack.insert_detialBySelf", m);
		//Dao.delete("BuyBack.non-payment_rent",m);

		//插入回购新添加的费用字段
		Map other = new HashMap();
		if(m.get("taxes")!=null&&!"".equals(m.get("taxes"))&&Double.parseDouble(m.get("taxes")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "税金");
			other.put("ITEM_MONEY", m.get("taxes"));
			Dao.insert("BuyBack.buyBackOther",other);
		}

		if(m.get("liugoujia")!=null&&!"".equals(m.get("liugoujia"))&&Double.parseDouble(m.get("liugoujia")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购留购价款");
			other.put("ITEM_MONEY", m.get("liugoujia"));
			Dao.insert("BuyBack.buyBackOther",other);
		}
		if(m.get("HG_SXF")!=null&&!"".equals(m.get("HG_SXF"))&&Double.parseDouble(m.get("HG_SXF")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购手续费");
			other.put("ITEM_MONEY", m.get("HG_SXF"));
			Dao.insert("BuyBack.buyBackOther",other);
		}

		if(m.get("guaranteeFee")!=null&&!"".equals(m.get("guaranteeFee"))&&Double.parseDouble(m.get("guaranteeFee")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购担保费");
			other.put("ITEM_MONEY", m.get("guaranteeFee"));
			Dao.insert("BuyBack.buyBackOther",other);
		}
		if(m.get("serviceFee")!=null&&!"".equals(m.get("serviceFee"))&&Double.parseDouble(m.get("serviceFee")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购管理服务费");
			other.put("ITEM_MONEY", m.get("serviceFee"));
			Dao.insert("BuyBack.buyBackOther",other);
		}
		if(m.get("deposit")!=null&&!"".equals(m.get("deposit"))&&Double.parseDouble(m.get("deposit")+"")>0){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购保证金");
			other.put("ITEM_MONEY", m.get("deposit"));
			Dao.insert("BuyBack.buyBackOther",other);
		}

		if(m.get("otherExpenses")!=null&&!"".equals(m.get("otherExpenses"))){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购其他费用支付");
			other.put("ITEM_MONEY", m.get("otherExpenses"));
			Dao.insert("BuyBack.buyBackOther",other);
		}

		if(m.get("startRent")!=null&&!"".equals(m.get("startRent"))){
			other.put("NEWID", m.get("NEWID"));
			other.put("ITEM_NAME", "回购起租租金");
			other.put("ITEM_MONEY", m.get("startRent"));
			Dao.insert("BuyBack.buyBackOther",other);
		}

//		// 删除财务表数据
//		Dao.delete("PayTask.deleteBeginning", m);
//		
//		// 同步财务数据
//		Map<String, String> temp1 = new HashMap<String, String>();
//		temp1.put("PAY_ID", m.get("NEWID") + "");
//		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
//		temp1.put("PMT", "PMT租金");
//		temp1.put("ZJ", "租金");
//		temp1.put("SYBJ", "剩余本金");
//		temp1.put("D", "第");
//		temp1.put("QI", "期");
//		Dao.insert("PayTask.synchronizationBeginning", temp1);

		//修改申请单
		Dao.update("BuyBack.buyBackNormal",m);
		//修改fil_buy_back的PAY_ID为最新支付表id
		int update = Dao.update("BuyBack.buyBackNEWID", m);
		System.out.println("修改条数"+update);
	}

	/**
	 * 正常回购数据查询
	 * @date 2013-11-20 下午03:00:39
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Map<String,Object> toFindHGData(Map<String,Object> map){

		Map<String,Object> data = new HashMap<String,Object>();

		//查看项目数据
		Map<String,Object> pro = Dao.selectOne("BuyBack.getProData", map);
		data.put("project", pro);


//		Map<String,Object> buy_back = Dao.selectOne("rentDk.getBuyBackData", map);
//		data.put("buy_back", buy_back);


		map.put("LIXI", "利息");
		map.put("BENJIN", "本金");
		map.put("QZZJ", "起租租金");
		//查看租金信息
		Map<String,Object> rentData = Dao.selectOne("BuyBack.getRentData", map);

		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理

		//
		double first_money = Double.parseDouble(rentData.get("FIRST_MONEY")!=null ? rentData.get("FIRST_MONEY")+"":"0.00");//第一期租金

		double mq_money_ = Double.parseDouble(rentData.get("MQ_MONEY")!=null ? rentData.get("MQ_MONEY")+"":"0.00");//首期租金
		rentData.put("first_money", df.format(first_money));

		//计算剩余租金
		double sy_money = 0.00;  //剩余租金
		double rent_hj = Double.parseDouble(rentData.get("RENT_HJ")!=null ? rentData.get("RENT_HJ").toString():"0.00");//租金总和
		double ys_money = Double.parseDouble(rentData.get("YS_MONEY")!=null ? rentData.get("YS_MONEY").toString():"0.00");//已收租金
		//剩余租金
		sy_money = rent_hj - ys_money;
		rentData.put("sy_money", df.format(sy_money));

		//Map<String,Object> bzj = Dao.selectOne("BuyBack.getBZJData", map); 
		//data.put("bzj", bzj);

		data.put("rentData",rentData);
		return data;
	}

	//获取保证金数据
	public Object getProBzj(Map<String,Object> map){
		return Dao.selectOne("BuyBack.getBZJData", map);
	}

	/**
	 * 查看项目信息
	 * getProData
	 * @param PROJECT_ID 項目id
	 * @date 2013-11-24 下午04:13:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getProData(Map<String,Object> map){
		System.out.println("");
		return Dao.selectOne("BuyBack.getProData1", map);
	}


	/**
	 * 查看回购相关数据
	 * toCheckedFormHG
	 * @param BUY_BACK_ID 回購表單id
	 * @date 2013-11-23 下午03:00:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toCheckedFormHG(Map<String,Object> map){
		return Dao.selectOne("BuyBack.getBuyBackData1", map);
	}

	/**
	 * 查看付款申请单
	 * toViewFund
	 * @param FUND_ID 付款單id
	 * @date 2013-11-23 下午03:02:08
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toViewFund(Map<String,Object> map){
		return Dao.selectOne("rentDk.toViewFund", map);
	}

	/**
	 * 修改回購表單
	 * toSaveBuyBack
	 * @param PAY_ID 支付表號
	 * @date 2013-12-3 下午12:19:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int toSaveBuyBack1(JSONObject map){
		return Dao.update("BuyBack.buyBackNormal",map);
	}

	/**
	 * 查看逾期期次
	 * queryFI_OVERDUE
	 * @param PAY_CODE 支付表id
	 * @date 2013-12-1 下午02:54:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object queryFI_OVERDUE(Map<String,Object> map){
		map.put("PAY_CODE", map.get("PAYLIST_CODE"));
		return Dao.selectOne("BuyBack.queryFI_OVERDUE", map);
	}

	/**
	 * 查看客户回购资料
	 * toGetFileHG
	 * @date 2013-11-24 下午06:25:41
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetFileHG(Map<String,Object> map){
		map.put("HGFILE","回购资料");
		return Dao.selectList("BuyBack.getFileHG",map);
	}

	/**
	 * 资料上传
	 * addFile
	 * @date 2013-11-24 下午06:13:52
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addFile(Map<String,Object> map){
		map.put("FILE_TYPE",1);
		return Dao.insert("BuyBack.doInsertFileHG",map);
	}


	/**
	 * 创建文件路径
	 * createDirectory
	 * @date 2013-10-18 上午11:05:13
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public static boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			logger.error(e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 应收金额
	 * getRentData
	 * @date 2013-12-1 下午05:56:20
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object  getRentData(Map<String,Object> map){
		return Dao.selectList("rentDk.getRentData", map);
	}

	/**
	 * 分期付款应收首期款
	 * getFirstMoney
	 * @date 2013-12-1 下午06:11:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getFirstMoney(Map<String,Object> map){
		return Dao.selectOne("rentDk.getFirstMoney", map);
	}

	/**
	 * 分期付款应收首期款
	 * getFirstMoney
	 * @date 2013-12-1 下午06:11:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getFirstMoney1(Map<String,Object> map){
		return Dao.selectOne("rentDk.getFirstMoney1", map);
	}

	/*
	 * 根据提供的结算日期,计算出罚息插入罚息表
	 */
	@SuppressWarnings("unchecked")
	public double refreshPenaltyaAccountsDate(String PAYLIST_CODE,String ACCOUNT_DATE,String PAY_ID){
		double sfine = 0;
		Map map = new HashMap();
		map.put("PAYLIST_CODE",PAYLIST_CODE );
		map.put("ACCOUNT_DATE",ACCOUNT_DATE );
		map.put("PAY_ID",PAY_ID );
		boolean flag = strToDate(ACCOUNT_DATE).getTime() > strToDate(dateToStr(new Date())).getTime();
		/*//查询该收租金但是未收
		List list = Dao.selectList("BuyBack.penaltyaAccountsDate1",map);
		//查询这个此项目的信息
		Map info = Dao.selectOne("BuyBack.penaltyaInfo", map);
		//如果之前刷过结算日的罚息则先删除
		Dao.delete("BuyBack.delete_penaltyaAccountsDate", map);

		Map tempMap = new HashMap();
		tempMap.put("ACCOUNT_DATE", ACCOUNT_DATE);
		tempMap.put("PAYLIST_CODE", PAYLIST_CODE);
		if(list!=null&&!list.isEmpty()&&flag){
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map)list.get(i);
				map.put("BEGINNING_NUM", m.get("BEGINNING_NUM"));
				Map old = Dao.selectOne("BuyBack.query_fi_overdue_today", map);
				OverdueService ov = new OverdueService();
				//计算逾期天数
				long daySub = getDaySub(m.get("BEGINNING_RECEIVE_DATA")+"", ACCOUNT_DATE);
				//实收罚息
				double rfine = 0;
				//应收罚息
				double fine = 0;
				String PENALTY_DATE="";
				if(old!=null&&!old.isEmpty()){
					rfine = old.get("PENALTY_PAID")==null||"".equals(old.get("PENALTY_PAID"))?0:Double.parseDouble(old.get("PENALTY_PAID")+"");

					fine = ov.dueTemp(
							(old.get("RENT_RECE") == null || "".equals(old.get("RENT_RECE")) ? 0 : Double.parseDouble(old.get("RENT_RECE") + ""))
							-
							(old.get("RENT_PAID") == null || "".equals(old.get("RENT_PAID")) ? 0 : Double.parseDouble(old.get("RENT_PAID") + "")),


							(old.get("PENALTY_RECE") == null || "".equals(old.get("PENALTY_RECE")) ? 0 : Double.parseDouble(old.get("PENALTY_RECE") + ""))
							-
							(old.get("PENALTY_PAID") == null || "".equals(old.get("PENALTY_PAID")) ? 0 : Double.parseDouble(old.get("PENALTY_PAID") + "")),


							(int) getDaySub(old.get("CREATE_DATE")+"", ACCOUNT_DATE));

					//实收日期
					PENALTY_DATE = old.get("PENALTY_DATE")!=null&&!"".equals(old.get("PENALTY_DATE"))?old.get("PENALTY_DATE")+"":"";
				}else {
					    fine = ov.dueTemp(
							(m.get("BEGINNING_MONEY") == null || "".equals(m.get("BEGINNING_MONEY")) ? 0 : Double.parseDouble(m.get("BEGINNING_MONEY") + ""))
							- (m.get("BEGINNING_PAID") == null || "".equals(m.get("BEGINNING_PAID")) ? 0 : Double.parseDouble(m.get("BEGINNING_PAID") + "")),
							0,
							(int) getDaySub(m.get("BEGINNING_RECEIVE_DATA")+"", ACCOUNT_DATE));
				}

				fine = Util.formatDouble2(fine);
				rfine = Util.formatDouble2(rfine);

				m.put("PAY_CODE",PAYLIST_CODE); //	支付表编号
				m.put("PERIOD",m.get("BEGINNING_NUM")); //	期次
				m.put("RENT_RECE",m.get("BEGINNING_MONEY")); //	应收租金总额
				m.put("RENT_PAID",m.get("BEGINNING_PAID")); //	实收租金总额
				m.put("PENALTY_RECE",fine); //	应收罚息总额
				m.put("PENALTY_PAID",rfine); //	实收罚息总额
				m.put("CREATE_DATE",ACCOUNT_DATE); //	创建日期
				m.put("RENT_DATE",m.get("BEGINNING_RECEIVE_DATA")); //	应付日期
				m.put("PENALTY_DAY",daySub); //	罚息天数
				m.put("DUE_STATUS",1); //	0：不能核销，1：可以核销-------（初始化为0，当租金核销后反更状态为1可以核销。虚拟核销重置0继续计算，客户来款将租金核销完成后变成1，后继续客户来款完全核销掉）已核销数据变更为2
				m.put("PROJECT_ID",info.get("PROJECT_ID")); //	项目ID
				m.put("CUST_NAME",info.get("NAME")); //	客户名
				m.put("SUP",info.get("SUP_NAME")); //	供应商
				m.put("PAY_START",info.get("DELIVER_DATE")); //	起租时间
				m.put("EQUI",info.get("ENGINE_TYPE")); //	租赁物类型
				m.put("PENALTY_DATE",PENALTY_DATE); //	实收日期
				//删除本期当天的罚息
				//Dao.delete("BuyBack.delete_penaltyaAccountsDate2", m);
				Dao.insert("BuyBack.insert_penaltyaInfo", m);
				sfine+=fine;
			}
			//计算结算日的罚息的时候如果已经核销但是来款时间在应收时间之后（即核销了，但是罚息未核的），这种情况需要把当天罚息拷贝到结算日的那天
			Dao.insert("BuyBack.insert_penaltyaInfo2", tempMap);
		}*/
		//调用李超写的存过计算罚息
		Map tm = new HashMap();
		tm.put("PAYCODEPARAM", PAYLIST_CODE);
		tm.put("NEW_DATE", ACCOUNT_DATE);
//		System.out.println("-------------tm="+tm);
		if(flag){
			Dao.selectOne("BuyBack.calculate_overdue_appointed", tm);
		}
		//并更新取整
		Dao.update("BuyBack.round_overdue_appointed", tm);
		return Util.formatDouble0(Dao.selectDouble("BuyBack.query_fi_overdue_ACCOUNT_DATE", map));

	}
	public void deleteAlreadyData(Map<String, Object> param) {
		Map map = Dao.selectOne("BuyBack.deleteAlreadyData", param);
		int VERSION_CODE = map.get("VERSION_CODE")==null||"".equals(map.get("VERSION_CODE"))?0:Integer.parseInt(map.get("VERSION_CODE")+"");
		//如果版本号为负数则删除主表和明细,同时删除fil_buy_back
		if(VERSION_CODE<=0){
			map.put("PAY_ID", map.get("ID"));
			Dao.delete("BuyBack.deleteAlreadyData_detail", map);
			//Dao.delete("BuyBack.deleteAlreadyData_buy_back", map);
			Dao.delete("BuyBack.deleteAlreadyData_head", map);
		}

	}
	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 *         long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	/**
	 * 得到现在时间
	 *
	 * @return 字符串 yyyy-mm-dd
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public Map<String,Object> selectRepoData(Map<String,Object> param){
		return Dao.selectOne("BuyBack.selectRepoData",param);
	}

	public Map<String, Object> doShowJQBaseByPayId(Map<String,Object> param) {
		String PAY_ID=param.get("PAY_ID")+"";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "提前结清");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		JSONObject SCHEME_MAP=JSONObject.fromObject(mapDate.get("PARAM_VALUE"));

		if(SCHEME_MAP.get("LGJ")==null || SCHEME_MAP.get("LGJ").equals("")){
			SCHEME_MAP.put("LGJ","0");
		}

		//查询基本信息
		String PAYLIST_CODE=param.get("PAYLIST_CODE")+"";
		Map mapInfo=Dao.selectOne("PayTask.queryBaseInfoByPaylistCode", PAYLIST_CODE);
		if(SCHEME_MAP !=null){
			SCHEME_MAP.putAll(mapInfo);
		}

		return SCHEME_MAP;
	}

	public Map<String, Object> doShowZCJQBaseByPayId(Map<String,Object> param) {
		String PAY_ID=param.get("PAY_ID")+"";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "正常结清");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		JSONObject SCHEME_MAP=JSONObject.fromObject(mapDate.get("PARAM_VALUE"));

		if(SCHEME_MAP.get("LGJ")==null || SCHEME_MAP.get("LGJ").equals("")){
			SCHEME_MAP.put("LGJ","0");
		}

		//查询基本信息
		String PAYLIST_CODE=param.get("PAYLIST_CODE")+"";
		Map mapInfo=Dao.selectOne("PayTask.queryBaseInfoByPaylistCode", PAYLIST_CODE);
		if(SCHEME_MAP !=null){
			SCHEME_MAP.putAll(mapInfo);
		}

		return SCHEME_MAP;
	}

	public Map<String, Object> doShowXMHGBaseByPayId(Map<String,Object> param) {
		String PAY_ID=param.get("PAY_ID")+"";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "项目回购");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		JSONObject SCHEME_MAP=JSONObject.fromObject(mapDate.get("PARAM_VALUE"));

		//查询基本信息
		String PAYLIST_CODE=param.get("PAYLIST_CODE")+"";
		Map mapInfo=Dao.selectOne("PayTask.queryBaseInfoByPaylistCode", PAYLIST_CODE);
		if(SCHEME_MAP !=null){
			SCHEME_MAP.putAll(mapInfo);
		}

		return SCHEME_MAP;
	}

	public Map<String,Object> selectRepoData11(Map<String,Object> param){
		return Dao.selectOne("BuyBack.selectRepoData11",param);
	}

	public Map<String,Object> selectTpmPath(Map<String,Object>  param){
		return Dao.selectOne("bpm.pdfTemplate.selectTpmPath", param);
	}
	public void synchronizationCustSuppliers(JSONObject json) {
		json.put("PAY_ID", json.get("NEWID"));
		Map map = Dao.selectOne("BuyBack.query_fcc_name_sup1", json);
		//如果客户表里面没有供应商信息就插入
		if(map==null|| map.get("NAME1")==null||"".equals(map.get("NAME1"))){
			Dao.insert("BuyBack.insert_fil_cust_client_sup", map);
			Map temp = Dao.selectOne("BuyBack.query_fcc_ORG_ID", json);
			map.putAll(temp);
			Dao.update("BuyBack.toUpdata_fcc_ORG_ID",map);
		}
		//如果客户id有值（即新插入的客户NEWID存在）取反，就去查询客户id
		int id=2000000000;
		if(map.get("NEWID")!=null&&!"".equals(map.get("NEWID"))){
			id = Integer.parseInt(map.get("NEWID")+"");
		}else {
			id = Dao.selectInt("BuyBack.query_id_name", map);
		}
		map.put("CLIENT_ID", id);
		map.put("PAY_ID", json.get("PAY_ID"));
		//修改项目的客户信息
		Dao.update("BuyBack.toUpdata_project_sup", map);
	}
	public static Date strToDate(String strDate) {
		if(strDate==null)
		{
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}


	public void calculateParameter(Map<String, Object> param) {
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		param.put("OLD_PAY_ID", settleInfo.get("ID"));
		settleInfo.put("ID", param.get("NEWID"));
		Map map = new HashMap();
		map.put("PAY_ID", settleInfo.get("ID"));
		map.put("PARAM_NAME", "项目回购");
		map.put("PARAM_VALUE", settleInfo.toString());
		map.put("ACCOUNT_DATE",
				settleInfo.get("ACCOUNT_DATE") == null
						|| "".equals(settleInfo.get("ACCOUNT_DATE")) ? ""
						: settleInfo.get("ACCOUNT_DATE"));

		map.put("PAYLIST_CODE",
				settleInfo.get("PAYLIST_CODE") == null
						|| "".equals(settleInfo.get("PAYLIST_CODE") == null) ? ""
						: settleInfo.get("PAYLIST_CODE") == null);


		map.put("IS_END_STATUS", "0");
		Dao.insert("PayTask.insert_paychange_parameter_all", map);
	}

	public void calculateSaveHG(Map<String, Object> m) {

		//获取之前的明细表
		Map mapOld = (Map) Dao.selectOne("PayTask.queryPayOldID", m);



		// 插入明细表（获取以前数据的明细）
		List<Map<String, String>> list = Dao.selectList("BuyBack.getPayDetailByOldId",m);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		// 修改当期往下的利息为0(提前结清使用)，以及租金
		Map mm = new HashMap();
		if (m.get("settleInfo") != null && !"".equals(m.get("settleInfo"))) {
			mm.put("ACCOUNT_DATE", JSONObject.fromObject(m.get("settleInfo"))
					.get("ACCOUNT_DATE"));
			mm.put("NEWID", m.get("NEWID"));
			mm.put("LILV", Double.parseDouble(JSONObject.fromObject(m.get("settleInfo")).get("exemptInterest")+""));
			Dao.update("BuyBack.detial_interest_zero", mm);
			//修改租金
			Dao.update("BuyBack.detial_interest_ZJ", mm);
		}

		// 插入明细的其他费用

		mapOld.put("NEWID", m.get("NEWID") + "");
		Dao.insert("PayTask.payplan_detail_other", mapOld);

		// 插入变更收取的其他费用
		Map<String, String> mo = new HashMap<String, String>();

		mo.put("回购手续费",m.get("HG_SXF") == null || "".equals(m.get("HG_SXF")) ? "" : m.get("HG_SXF") + "");
		mo.put("回购税金",m.get("taxes") == null || "".equals(m.get("taxes")) ? "" : m.get("taxes") + "");
		insertPayplanDetailOther(mo, m.get("NEWID") + "");


		// 修改支付表主表状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("NEWID") + "");
		temp.put("STATUS", "55");
		Dao.update("PayTask.update_payplan", temp);

	}

	// 插入其他费用Detail
	public static void insertPayplanDetailOther(Map<String, String> map,
												String PAY_ID) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map<String, String> tm = new HashMap<String, String>();
			Map.Entry m = (Map.Entry) it.next();
			if (m.getValue() != null && !"".equals(m.getValue())) {
				tm.put("ITEM_NAME", m.getKey() + "");
				tm.put("ITEM_MONEY", m.getValue() + "");
				tm.put("ITEM_FLAG", "4");
				tm.put("PAY_ID", PAY_ID);
				Dao.insert("PayTask.payplan_detail", tm);
			}
		}
	}

	public String calculateSaveJBPMNextCode(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList("PayTask.queryPayMinVersion_code", param);
		param.put("PAY_ID", minPay.get(0).get("ID"));
		Map map = (Map) Dao.selectOne("PayTask.forwardRepaymentShow1", param);// map.put("SUPPLIER_ID",'')
		Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID", param);
		map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PAY_ID", map.get("ID").toString());
		map.put("PROJECT_ID", minPay.get(0).get("PROJECT_ID") + "");
//			List<String> prcessList = JBPM.getDeploymentListByModelName("正常回购审批流程",minPay.get(0).get("PLATFORM_TYPE") + "",Security.getUser().getOrg().getPlatformId());
		List<String> prcessList = JBPM.getDeploymentListByModelName("正常回购审批流程","",Security.getUser().getOrg().getPlatformId());
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
//				new ProjectContractManagerService().doAddProjectContractList(param
//						.get("JBPM_ID").toString());// 保存欲归档文件
			String nextCode = new TaskService().getAssignee(processInstance.getId());

			return nextCode;
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public Map<String, Object> doShowHGByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "项目回购");
		Map mapDate=Dao.selectOne("PayTask.doShowJQByPayId", map);
		JSONObject SCHEME_MAP=JSONObject.fromObject(mapDate.get("PARAM_VALUE"));
		return SCHEME_MAP;
	}
}

