//package com.jbpm.utils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.utils.StringUtil;
//
///**
// * @author Toybaby
// *
// * 2012-5-7下午04:07:13
// * email toybaby@mail2.tenwa.com.cn
// * function: 公共类，主要封装流程步骤操作中，反复使用的一些方法。
// *
// */
//public class WorkFlowStepDataCtrlUtil {
//	
//	/**
//	 * @param proj_id 项目编号
//	 * func : 拷贝还款计划数据从临时 表到正式表 ，
//	 * 参考 domino个体立项流程WFShantuiDB 中的 SRemoveData 
//	 * @throws Exception 
//	 */
//	@SuppressWarnings("unchecked")
//	public static void copyRentPlanFromTemp(String proj_id,JdbcBaseDao conn ) throws Exception{
//		String sql = "";
//		// 2. 将还款计划从fund_rent_plan_temp表回拷到正式表 并更新his=0
//		sql = "delete from fund_rent_plan where proj_id='"+proj_id+"'";
////		System.out.println("sql2="+sql);
//		conn.doDeleteBySql(sql);
//		sql = "update fund_rent_plan_temp set his='0' where proj_id='"+proj_id+"' ";
////		System.out.println("sql3="+sql);
//		conn.doUpdateBySql(sql);
//		// 3. '判断是否已收首付款，如果已收，则更新还款计划中的第1期租金状态,防止流程退回情况发生
//		sql = "select id from fund_rent_income where proj_id='"+proj_id+"' and plan_list='1'";
////		System.out.println("sql4="+sql);
//		List l =conn.doQueryBySql(sql);
//		if(l.size()>=1)
//		{
//			sql = "update fund_rent_plan_temp set plan_status='1' where proj_id='"+proj_id+"' and  rent_list='1'";
//			conn.doUpdateBySql(sql);
//		}
//		// 4.还款计划拷到正式表
//		sql = "insert into fund_rent_plan (proj_id,rent_list,plan_status,plan_date,eptd_rent,rent,corpus,year_rate,interest," +
//					"rent_overage,corpus_overage,interest_overage,penalty_overage,penalty,rent_type,rent_adjust,his,item_method," +
//					"creator,create_date,modify_date,modificator) " +
//			  "select proj_id,rent_list,plan_status,plan_date,eptd_rent,rent,corpus,year_rate,interest," +
//					"rent_overage,corpus_overage,interest_overage,penalty_overage,penalty,rent_type,rent_adjust,his,item_method," +
//					"creator,create_date,modify_date,modificator " +
//					"from fund_rent_plan_temp where proj_id='"+proj_id+"'";
////		System.out.println("sql5="+sql);
//		conn.doUpdateBySql(sql);
//		// 5. 交易结构条件从临时表拷到正式表
//		sql = "delete from proj_condition where proj_id = '"+proj_id+"'";
////		System.out.println("sql6="+sql);
//		conn.doDeleteBySql(sql);
//		sql = "insert into proj_condition(proj_id,equip_amt,lease_money,lease_term,income_number,year_rate,period_type,settle_method," +
//					"start_date,period_first_date,first_payment,caution_money,lessee_caution_ratio,lessee_caution_money,SALE_CAUTION_RATIO,sale_caution_money," +
//					"handling_charge_ratio,handling_charge,supervision_fee,first_rent,nominalprice,insurance_lessor_ratio,insurance_lessor," +
//					"total_amt,lease_interval,method,prod_id,head_ratio,head_amt,hand_method,is_land,on_card,assuretype,operation_type," +
//					"rent_income_type,THREE_INSURANCE,INFOSUB) " +
//			  "select proj_id,equip_amt,lease_money,lease_term,income_number,year_rate,period_type,settle_method," +
//					"start_date,period_first_date,first_payment,caution_money,lessee_caution_ratio,lessee_caution_money,SALE_CAUTION_RATIO,sale_caution_money," +
//					"handling_charge_ratio,handling_charge,supervision_fee,first_rent,nominalprice,insurance_lessor_ratio,insurance_lessor," +
//					"total_amt,lease_interval,method,prod_id,head_ratio,head_amt,hand_method,is_land,on_card,assuretype,operation_type," +
//					"rent_income_type,THREE_INSURANCE,INFOSUB from proj_condition_temp where proj_id='"+proj_id+"'";
////		System.out.println("sql7="+sql);
//		conn.doUpdateBySql(sql);
//	}
//	
//	/**
//	 * @param proj_id 项目编号
//	 * @param proj_status  项目状态
//	 * @throws Exception 
//	 * @func 更新起租日期，更新proj_condition_temp表中的period_first_date   (代理SProjIntoSQL1) 插入保险为另外一个方法
//	 */
//	public static void updateProjInfo(String proj_id,String proj_status,JdbcBaseDao conn ) throws Exception{
//		String sql = "";
//		// 3.1 更新两张表
//		sql = "update proj_info set proj_status='"+proj_status+"',proj_approvedate=(select equip_delivery_date from proj_equip where " +
//				"proj_id='"+proj_id+"' and rownum=1) where proj_id='"+proj_id+"'";
//		conn.doUpdateBySql(sql);
//		sql = "update proj_condition_temp set period_first_date=(select equip_delivery_date from proj_equip where " +
//				"proj_id='"+proj_id+"' and rownum=1) where proj_id='"+proj_id+"'";
//		conn.doUpdateBySql(sql);
//	}
//	/**
//	 * @param proj_id 项目编号
//	 * @param proj_status  项目状态
//	 * @throws Exception 
//	 * @func 更新起租日期，更新proj_condition_temp表中的period_first_date   (代理SProjIntoSQL1) 插入保险为另外一个方法
//	 */
//	public static void updateProjInfoNoState(String proj_id,JdbcBaseDao conn ) throws Exception{
//		String sql = "";
//		// 3.1 更新两张表
//		sql = "update proj_info set proj_approvedate=(select equip_delivery_date from proj_equip where " +
//		"proj_id='"+proj_id+"' and rownum=1) where proj_id='"+proj_id+"'";
//		conn.doUpdateBySql(sql);
//		sql = "update proj_condition_temp set period_first_date=(select equip_delivery_date from proj_equip where " +
//		"proj_id='"+proj_id+"' and rownum=1) where proj_id='"+proj_id+"'";
//		conn.doUpdateBySql(sql);
//	}
//	
//	/**
//	 * @param proj_id
//	 * @param CustomerName 客户名称
//	 * @param FinanceTerm 租赁期限
//	 * @param tmpCompany 
//	 * @throws Exception 
//	 * @func 插入保险信息
//	 */
//	public static void updateInsureInfo(String proj_id,String CustomerName,String FinanceTerm,String tmpCompany,JdbcBaseDao conn ) throws Exception{
//		String sql = "";
//		// 6.2 插入保险信息
//		sql = "delete FROM proj_insurance WHERE proj_id='"+proj_id+"'";
//		conn.doDeleteBySql(sql);
//		sql = "insert into proj_insurance( proj_id,product,productno,production,factory,lessee,insurer,insurant,owner,insur_cost," +
//				"insu_fee,start_date,end_date,insur_date,bedno,engineno,useplace,productiondate) " +
//				"select '"+proj_id+"',prod_id,model_id,equip_sn,manufacturer,'"+CustomerName+"','"+tmpCompany+"','" + tmpCompany +"','" + tmpCompany +"',equip_price," +
//				"insurance,to_char(sysdate,'YYYY-MM-DD'),to_char(trunc(add_months(sysdate,'"+FinanceTerm+"')-1),'YYYY-MM-DD')," +
//						"'"+FinanceTerm+"',bodyNo,engineerNo,useArea,leave_factory_date from proj_equip where proj_id='"+proj_id+"'";
//		conn.doUpdateBySql(sql);
//	}
//	/**
//	 * @param proj_id
//	 * @throws Exception
//	 * @func 更新 第一期租金还款方式 ，参照 domino代理  WFshantuiDB   updateItemMethod
//	 */
//	@SuppressWarnings("unchecked")
//	public static void updateItemMethod(String proj_id,JdbcBaseDao conn ) throws Exception{
//		String sql = "";
//		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
//		Map<String, String> m = new HashMap<String,String>();
//		sql = "SELECT item_method FROM fund_fund_charge_plan WHERE proj_id = '"+proj_id+"' and funds_type='第1期租金'";
//		l = conn.doQueryBySql(sql);
//		String item_method = "";
//		String operation_type = "";
//		if(l.size()>0){
//			m = l.get(0);
//			item_method = m.get("item_method");
//			sql = "update fund_rent_plan set item_method='" +item_method +"' where proj_id='"+proj_id+"' and rent_list='1'";
//			conn.doUpdateBySql(sql);
//			sql = "update fund_rent_plan_temp set item_method='"+item_method +"' where proj_id='"+proj_id+"' and rent_list='1'";
//			conn.doUpdateBySql(sql);
//		}
//		
//		sql = "SELECT operation_type FROM proj_condition_temp WHERE proj_id = '"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			m = l.get(0);
//			operation_type = StringUtil.nullToString(m.get("operation_type"));
//			if(!operation_type.trim().isEmpty()){
//				sql = "update proj_condition_temp set operation_type='" +operation_type +"' where proj_id= '"+proj_id+"'";
//				conn.doUpdateBySql(sql);
//				sql = "update proj_condition set operation_type='" +operation_type +"' where proj_id= '"+proj_id+"'";
//				conn.doUpdateBySql(sql);
//			}
//		}
//	}
//	
//	/**
//	 * @param proj_id
//	 * @throws Exception 
//	 * @func 往proj_info表中插入起租确认日期
//	 */
//	@SuppressWarnings("unchecked")
//	public static void insertIntoConfiredDate(String proj_id,JdbcBaseDao conn ) throws Exception{
//		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
//		Map m = new HashMap();
//		String sql = "";
//		String rent_total_amt = "";
//		String interest_total_amt = "";
//		String ConfiredDate = StringUtil.getSystemDate();
//		String leaseBuyDate = "";
//		String insuranceDate = "";
//		String DBBailDateStr = "";
//		String SZQflag = "0";
//		// 1. 查询租金总额
//		sql = "select total_amt from proj_condition where proj_id = '"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			m = l.get(0);
//			rent_total_amt = StringUtil.nullToString(m.get("total_amt"));
//		}
//		// 2. 查询利息总额
//		sql = "select nvl(sum(nvl(interest,0)),0) as interest_amt from fund_rent_plan where proj_id = '"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			m = l.get(0);
//			interest_total_amt =  StringUtil.nullToString(m.get("interest_amt"));
//		}
//		// 3. 更新项目基本信息
//		sql = "SELECT * FROM proj_info where proj_id = '"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			sql = "update proj_info set proj_qzconfirm_date='"+ConfiredDate+"',rent_total_amt='"+rent_total_amt+"'," +
//					"interest_total_amt='"+interest_total_amt+"' where proj_id='"+proj_id+"' ";
//			conn.doUpdateBySql(sql);
//		}
//		// 4. 更新proj_sub_info表
//		sql = "select * from proj_sub_info where proj_id = '"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			sql = "update proj_sub_info set qz_date='"+ConfiredDate+"',end_date=to_char(add_months(to_date('"+ConfiredDate+"','YYYY-MM-DD'),3),'YYYY-MM-DD') where proj_id = '"+proj_id+"'";
//			conn.doUpdateBySql(sql);
//		}
//		// 5. 更新数据库的起租确认日期
//		int day = Integer.parseInt(ConfiredDate.substring(8, 10));
//		if(day<=5){
//			leaseBuyDate = ConfiredDate.substring(0, 7)+"-15";
//		}else if(day<=15){
//			leaseBuyDate = ConfiredDate.substring(0, 7)+"-25";
//		}else if(day<=25){
//			leaseBuyDate = "to_char(add_months(to_date('"+ConfiredDate+"','YYYY-MM-DD'),1),'YYYY-MM')||'-05'";
//		}else{
//			leaseBuyDate = "to_char(add_months(to_date('"+ConfiredDate+"','YYYY-MM-DD'),1),'YYYY-MM')||'-15'";
//		}
//		//保险日期
//		if (day<=20){
//			insuranceDate = ConfiredDate.substring(0, 7)+"-25";
//		}else{
//			leaseBuyDate = "to_char(add_months(to_date('"+ConfiredDate+"','YYYY-MM-DD'),1),'YYYY-MM')||'-25'";
//		}
//		//DBBailDateStr
//		sql = "select max(plan_date) as maxDate  from fund_rent_plan_temp where proj_id='"+proj_id+"'";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			m = l.get(0);
//			DBBailDateStr = StringUtil.nullToString(m.get("maxDate"));
//		}
//		sql = "update fund_fund_charge_plan set plan_date='" + DBBailDateStr + "'  where funds_type='保证金' and funds_mode='付款' and proj_id = '" + proj_id + "'";
//		conn.doUpdateBySql(sql);
//		//判断当前项目"+projectNo+"的供应商是否陕重汽或陕西通力
//		//陕重汽 ADMR-8586BY，陕西通力 ADMR-8NL5NS
//		sql = "SELECT id FROM proj_info WHERE (proj_id = '"+proj_id+"' and agent_id in(select bmbh from jb_gsbm where sjbm in('ADMR-8586BY','ADMR-8NL5NS'))) ";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			SZQflag = "1";
//		}
//		sql = "SELECT id FROM proj_info WHERE (proj_id = '"+proj_id+"' and agent_id in(select bmbh from jb_gsbm where sjbm in('ADMR-7YS9JF','ADMR-7ZN6RJ','ADMR-8ANBAJ'))) ";
//		l = conn.doQueryBySql(sql);
//		if(l.size()>0){
//			SZQflag = "2";
//		}
//		//start cld 2012-0503 建机改为周三放款
////		String SZQleaseBuyDatetmp = StringUtil.getSystemDate();
//		String next_wed = "";//下周三的日期
//		if("1".equals(SZQflag)){
//			 sql = "select NEXT_WEDNESDAY('"+StringUtil.getSystemDate()+"') as next_wed from dual";
//			 l = conn.doQueryBySql(sql);
//			 if(l.size()>0){
//				 m = l.get(0);
//				 next_wed = StringUtil.nullToString(m.get("next_wed"));
//			 }
//			 
//			 sql = "update fund_fund_charge_plan set plan_date='" + next_wed + "'  where funds_type='租赁物购买价款' and funds_mode='付款' and proj_id = '" + proj_id + "'";
//		}else if("2".equals(SZQflag)){
//			sql = "select next_wednesday_jj('"+StringUtil.getSystemDate()+"') as next_wed from dual";
//			l = conn.doQueryBySql(sql);
//			if(l.size()>0){
//				m = l.get(0);
//				next_wed = StringUtil.nullToString(m.get("next_wed"));
//			}
//			sql = "update fund_fund_charge_plan set plan_date='" + next_wed + "'  where funds_type='租赁物购买价款' and funds_mode='付款' and proj_id = '" + proj_id + "'";
//		}else{
//			sql = "update fund_fund_charge_plan set plan_date=" + leaseBuyDate + "  where funds_type='租赁物购买价款' and funds_mode='付款' and proj_id = '" + proj_id + "'";
//		}
////		System.out.println("当前项目的放款日期为"+leaseBuyDate+" 执行sql="+sql);
////		Statement st = conn.getConn().createStatement();
////		try 
////			st.executeUpdate(sql);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		conn.doUpdateBySql(sql);
////		System.out.println("sdafksadjfklsadflkasjdfl");
//		sql = "update fund_fund_charge_plan set plan_date=" + leaseBuyDate + "  where funds_type='其他费用支付' and funds_mode='付款' and proj_id = '" + proj_id + "'";
////		System.out.println("sql111111111111111111111="+sql);
//		conn.doUpdateBySql(sql);
//		sql = "update fund_fund_charge_plan set plan_date='" + insuranceDate + "'  where funds_type='保险费支付' and funds_mode='付款' and proj_id = '" + proj_id + "'";
////		System.out.println("sql22222222222222222222222="+sql);
//		conn.doUpdateBySql(sql);
//		//保险费支付日期和担保费支付日期一样
//		sql = "update fund_fund_charge_plan set plan_date='" + insuranceDate + "'  where funds_type='担保费' and funds_mode='付款' and proj_id = '" + proj_id + "'";
////		System.out.println("sql33333333333333333="+sql);
//		conn.doUpdateBySql(sql);
//		
//	}
//}
