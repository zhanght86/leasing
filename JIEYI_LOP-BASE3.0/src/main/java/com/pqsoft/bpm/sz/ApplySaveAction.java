//package com.kernal.jbpm.action.Equipment_repurchase;
//
//import java.sql.CallableStatement;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.CallableStatementCallback;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.springSecurity.PlatformUser;
//import com.kernal.utils.StringUtil;
//
///**
// * 设备回购流程
// * @author cld	
// *
// * 2012-5-7下午04:20:03
// * function:
// *
// */
//public class ApplySaveAction extends JbpmBaseAction 
//{
//	  /**
//     * 保存操作(saveAction触发)
//     * @param variablesMap
//     */
//	@SuppressWarnings("unchecked")
//	public  String save(HttpServletRequest request,Map<String,Object> variablesMap,JdbcBaseDao conn ) throws Exception
//    {	
//		//准备数据。判断是否需要抵扣
//		String sql = "";
//		String dk_remark = "1";
//		String proj_id=String.valueOf(variablesMap.get("ProjectNo"));
//		String son_paying_table = StringUtil.empty2Other(StringUtil.nullToString(variablesMap.get("son_paying_table")), "0");
//		//新增khbh 用于记录实际回购方
//		String khbh=String.valueOf(variablesMap.get("Khbh"));
//	
//		dk_remark = String.valueOf(variablesMap.get("dk_remark"));
//		//剩余租金12-8-16由于调息回更改还款计划。改为动态取值
//		String HireTotalMoney="";
//		String RemainMoney = "";
//
//		PlatformUser user = (PlatformUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		//String login_username = user.getUsername();
//		//String agent_id= user.getAgentId();
//		String czyid = user.getUserID();
//		//String czyid = (String)request.getSession().getAttribute("czyid");
//    	String DepositDate_hg = String.valueOf(variablesMap.get("DepositDate_hg"));
//    	String proj_togo =  String.valueOf(request.getParameter("proj_togo"));
//    	String INTERESTFREE = String.valueOf(variablesMap.get("INTERESTFREE"));
//    	String NOMINALPRICE_SALE = String.valueOf(variablesMap.get("NOMINALPRICE_SALE"));
//    	String caution_money = String.valueOf(variablesMap.get("caution_money"));
//    	String SupervisionMoney_1 = String.valueOf(variablesMap.get("SupervisionMoney_1"));
//    	//String maxpenalty = StringUtil.nullToDoubleString(variablesMap.get("sum_penalty")); 
//    	String ManageFree = String.valueOf(variablesMap.get("ManageFree"));
//    	String Other = String.valueOf(variablesMap.get("Other"));
//    	variablesMap.put("proj_togo", proj_togo);
//    	variablesMap.put("dk_remark", dk_remark);
//    	variablesMap.put("NOMINALPRICE_SALE", NOMINALPRICE_SALE);
//    	String sum_penalty = "0";
//    	//System.out.println(proj_togo+":"+DepositDate_hg);
//    	String sum_interest="0";
//    	String TotalMoney = "0";
//    	String sum_corpus ="0";
//    	String lists="";//剩余期次
//    	String lists_re="";//已核销期次
//    	String min_list="";//已核销期次
//    	
//    		//'计算违约金------包含已经逾期但是未还租金的违约金，根据结清日期计算
//    		sql="call penalty_calculate_tool('"+proj_id+"','"+DepositDate_hg+"','"+proj_togo+"','"+son_paying_table+"')";
//    		
//    		System.out.println(sql);
//    		conn.getJdbcTemplate().execute(sql, new CallableStatementCallback(){
//
//				@Override
//				public Object doInCallableStatement(CallableStatement cs)
//						throws SQLException, DataAccessException {
//					cs.executeUpdate();
//					return null;
//				}
//    			
//    		});
//    		sql = "select plan_date,case when to_number(substr(plan_date,9,2))>=to_number( substr('"+DepositDate_hg+"',9,2)) ";
//			sql += "	then  substr('"+DepositDate_hg+"',1,8) || substr(plan_date,9,2)   ";
//			sql += "	else  substr(to_char(add_months(to_date('"+DepositDate_hg+"','yyyy-mm-dd'),1),'yyyy-mm-dd'),1,8) || substr(plan_date,9,2) end  as flag_date ";
//			sql += "	from fund_rent_plan where proj_id = '"+proj_id+"' and rent_list = 2 and rownum  = 1";
//			String flag_date = "";
//			List temp = conn.doQueryBySql(sql);
//			if(temp.size()>0){
//				Map m = (Map)temp.get(0);
//				flag_date = StringUtil.nullToString(m.get("flag_date"));
//			}
//			
//    		//'取得未收违约金sum_penalty------和未到期利息sum_interest
//    		sql = "select a.HireTotalMoney, interest_.min_list,penalty_.sum_penalty,interest_.sum_interest,interest_.sum_corpus,interest_.lists,lists.lists_re from (select nvl(sum(FRP.PENALTY),0)  as sum_penalty from FUND_RENT_PLAN FRP ";
//    		sql += " LEFT JOIN  FUND_RENT_INCOME FRI ON FRP.PROJ_ID = FRI.PROJ_ID AND FRP.RENT_LIST = FRI.PLAN_LIST ";
//			sql += " AND FRP.SON_PAYING_TABLE = FRI.SON_PAYING_TABLE  AND FRI.RENT_TYPE ='违约金' ";
//			sql += " where frp.proj_id='"+proj_id+"' and frp.son_paying_table ='"+son_paying_table+"' AND FRP.PENALTY>0 ";
//			sql += " and FRP.PLAN_DATE<='"+flag_date+"' AND FRI.ID IS NULL";
//			sql += " ) penalty_ left join ";
//    		sql += "(select nvl(sum(interest),0) as sum_interest,min(rent_list) min_list,nvl(sum(corpus),0) as sum_corpus,nvl(count(id),0) lists from fund_rent_plan where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' and plan_status<>'1' ";
//    		sql += "and  plan_date>'"+flag_date+"') interest_ on 1=1 ";
//    		sql += " left join ";
//    		sql += " (    select nvl(count(id),0) lists_re ";
//    		sql += "  from fund_rent_plan where proj_id='"+proj_id+"' and plan_status='1' and son_paying_table ='"+son_paying_table+"'  ";
//    		sql += "  ) lists on 1=1  left join " ;
//    		sql += "( select sum(rent)+(select head_amt from proj_condition where proj_id= '"+proj_id+"' and rownum =1) as HireTotalMoney    ";
//    		sql += "  from fund_rent_plan where proj_id ='"+proj_id+"' and son_paying_table ='"+son_paying_table+"'  group by proj_id) a on 1=1 ";
//        	//System.out.println(sql);
//    		List l = conn.doQueryBySql(sql);
//			if(l.size()>0){
//				Map m = (Map)l.get(0);
//				sum_penalty = StringUtil.nullToDoubleString(m.get("sum_penalty"));
//				sum_interest = StringUtil.nullToDoubleString(m.get("sum_interest"));
//				sum_corpus = StringUtil.nullToDoubleString(m.get("sum_corpus"));
//				lists = StringUtil.nullToDoubleString(m.get("lists"));
//				lists_re = StringUtil.nullToDoubleString(m.get("lists_re"));
//				min_list = StringUtil.nullToString(m.get("min_list"));
//				HireTotalMoney = StringUtil.nullToString(m.get("HireTotalMoney"));
//				variablesMap.putAll(m);
//			}
//			sql = "SELECT SUM(NVL(RENT, 0)) + PROJ_CONDITION.HEAD_AMT GetHireMoney FROM FUND_RENT_PLAN ";
//			sql += "LEFT JOIN PROJ_CONDITION ON PROJ_CONDITION.PROJ_ID = FUND_RENT_PLAN.PROJ_ID  ";
//			sql += " WHERE FUND_RENT_PLAN.proj_id='"+proj_id+ "' and FUND_RENT_PLAN.son_paying_table='"+son_paying_table+"' and nvl(FUND_RENT_PLAN.plan_status,0)=1";
//			sql += " GROUP BY PROJ_CONDITION.HEAD_AMT ";
//			l = conn.doQueryBySql(sql);
//			 String GetHireMoney = "0";
//			if(l.size()>0){
//				Map m = (Map)l.get(0);
//				
//				GetHireMoney = String.valueOf(m.get("GetHireMoney"));
//				variablesMap.putAll(m);
//			}
//			//未收本金合计
//			sql ="select NVL(sum(nvl(corpus,0)),0) as maxcorpus,NVL(sum(nvl(rent,0)),0) as sumrent  from fund_rent_plan where proj_id='"+proj_id+ "' and son_paying_table='"+son_paying_table+"' and nvl(plan_status,0)=0";
//			 l = conn.doQueryBySql(sql);
//			 String maxcorpus = "0";
//			if(l.size()>0){
//				Map m = (Map)l.get(0);
//				
//				maxcorpus = String.valueOf(m.get("maxcorpus"));
//				RemainMoney = String.valueOf(m.get("sumrent"));
//				variablesMap.putAll(m);
//			}
//			//未收利息合计
//			sql ="select sum(nvl(interest,0)) as maxinterest  from fund_rent_plan where proj_id='"+proj_id+"'and son_paying_table='"+son_paying_table+"' "; 
//			sql += " and nvl(plan_status,0)=0 and plan_date<= '"+flag_date+"'";
//			 l = conn.doQueryBySql(sql);
//			 String maxinterest = "0";
//			if(l.size()>0){
//				Map m = (Map)l.get(0);
//				
//				maxinterest = StringUtil.nullToDoubleString(String.valueOf(m.get("maxinterest")));
//				variablesMap.putAll(m);
//			}
//			if(dk_remark.equals("1")){
//				dk_remark="0";
//			//总计
//			TotalMoney = //(Double.parseDouble(sum_penalty)//违约金
//						(Double.parseDouble(sum_penalty)
//						//+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("RemainMoney")))//剩余租金
//						+Double.parseDouble(StringUtil.nullToDoubleString(RemainMoney))//剩余租金
//						+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("Other")))//其他应收款
//						+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("ManageFree")))//税金
//						//-Double.parseDouble(String.valueOf(variablesMap.get("NoAccrual")))//未到期利息
//						-Double.parseDouble(sum_interest)
//						*Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("INTERESTFREE")))/100//利息减免
//						+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("NOMINALPRICE_SALE")))//留购价款
//						-Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("caution_money")))//保证金抵扣
//						-Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("SupervisionMoney_1"))))//留购价款抵扣
//						+"";//
//			}else{
//				dk_remark="2";
//				//总计
//				TotalMoney = //(Double.parseDouble(sum_penalty)//违约金
//							(Double.parseDouble(sum_penalty)
//							//+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("RemainMoney")))//剩余租金
//							+Double.parseDouble(StringUtil.nullToDoubleString(RemainMoney))//剩余租金
//							+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("Other")))//其他应收款
//							+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("ManageFree")))//税金
//							//-Double.parseDouble(String.valueOf(variablesMap.get("NoAccrual")))//未到期利息
//							-Double.parseDouble(sum_interest)
//							*Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("INTERESTFREE")))/100//利息减免
//							+Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("NOMINALPRICE_SALE"))))//留购价款
//							//-Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("caution_money")))//保证金抵扣
//							//-Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("SupervisionMoney_1"))))//留购价款抵扣
//							+"";//
//				caution_money="0";
//				SupervisionMoney_1="0";
//				variablesMap.put("caution_money", caution_money);
//				variablesMap.put("SupervisionMoney_1", SupervisionMoney_1);
//			}
//			
//			TotalMoney=String.format("%.2f", Double.parseDouble(TotalMoney));
//			variablesMap.put("TotalMoney", TotalMoney);
//			//删除项目提前终止计划表中信息
//			sql = "delete from proj_end_charge where proj_id='"+proj_id+"'and son_paying_table='"+son_paying_table+"'";
//			conn.doDeleteBySql(sql);
//			//插入项目提前终止计划表中对应信息
//			//增加actual_repurchase_id用于记录 实际回购方 khbh
//			sql = "insert into proj_end_charge(other_should_pay,taxes,proj_id,plan_money,corpus,interest,penalty,plan_date,remark,is_free,nominalprice,caution_dk,";
//			sql +="nominalprice_dk,creator,create_date,dk_flag,dk_remark,son_paying_table,actual_repurchase_id) values ('"+Other+"','"+ManageFree+"','"+proj_id+"','"+TotalMoney+"','"+maxcorpus+"',";
//			sql +="'"+(Double.parseDouble(maxinterest))*Double.parseDouble(StringUtil.nullToDoubleString(variablesMap.get("INTERESTFREE")))/100;
//			sql +="','"+sum_penalty+"','"+DepositDate_hg+"','"+proj_togo+"','"+INTERESTFREE+"',";
//			sql +="'"+NOMINALPRICE_SALE+"','"+caution_money+"','"+SupervisionMoney_1+"' ,'"+czyid+"',to_char(sysdate,'yyyy-mm-dd'),'"+dk_remark+"','"+dk_remark+"','"+son_paying_table+"','" + khbh + "')";
//			//System.out.println(sql );
//			conn.doUpdateBySql(sql);
//			
//			//每次保存刷新支付方式 yuq 2012-11-22
//			sql = "update fund_rent_plan set hg_remark=null,hg_date=null,item_method=(select rent_paytype from proj_info p where p.proj_id=fund_rent_plan.proj_id) where proj_id='"+proj_id+"' and nvl(plan_status,0)=0  and son_paying_table='"+son_paying_table+"'";
//			conn.doUpdateBySql(sql);
//			sql = "update fund_rent_income set remark = null where remark ='"+proj_togo+"' and proj_id='"+proj_id+"' and  son_paying_table='"+son_paying_table+"' ";
//			conn.doUpdateBySql(sql);
//			sql = "update fund_rent_plan set penalty_remark=null  where proj_id='"+proj_id+"' and penalty_remark='"+proj_togo+"' and  son_paying_table='"+son_paying_table+"' ";
//			conn.doUpdateBySql(sql);
//			
//			//将未还的租金标识为设备回购
//			sql = "update fund_rent_plan set hg_remark='待回购项目',hg_date='"+DepositDate_hg+"' where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' and plan_status<>'1'";
//			conn.doUpdateBySql(sql);
//			//将未还的违约金标识为设备回购
//			sql = "update fund_rent_plan set penalty_remark='待回购项目' where nvl(penalty,0)>0 and proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"'  and not exists ";
//			sql += " (select id from fund_rent_income where rent_type='违约金' and proj_id = fund_rent_plan.proj_Id and plan_list=fund_rent_plan.rent_list and son_paying_table=fund_rent_plan.son_paying_table)";
//			conn.doUpdateBySql(sql);
//			
//			
//			 sql = "update fund_rent_plan set item_method=(select item_method from fund_rent_plan_temp where ROWNUM=1 and rent_list=1 and proj_id ='";
//			 sql +=proj_id+"') where proj_id='"+proj_id+"' and rent_list=1 and son_paying_table='"+son_paying_table+"'";
//			 conn.doUpdateBySql(sql);
//			 sql = "update fund_rent_plan set item_method=(select rent_paytype from proj_info where  ROWNUM=1  and proj_id ='";
//			 sql +=proj_id+"') where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' and rent_list<>1";
//			 conn.doUpdateBySql(sql);
//			 
//			//屏蔽租金扣划，将租金还款方式标识为设备回购中
//			/**sql = "update fund_rent_plan set item_method='"+proj_togo+"中' where proj_id='"+proj_id+"'";
//			conn.doUpdateBySql(sql);*/
////			//将未还的租金标识为设备回购
////			sql = "update fund_rent_plan set hg_remark='"+proj_togo+"',hg_date='"+DepositDate_hg+"' where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' and plan_status<>'1'";
////			conn.doUpdateBySql(sql);
////			//将未还的违约金标识为设备回购
////			sql = "update fund_rent_plan set penalty_remark='"+proj_togo+"' where nvl(penalty,0)>0 and proj_id='"+proj_id+"' and not exists ";
////			sql += " (select id from fund_rent_income where rent_type='违约金' and proj_id=fund_rent_plan.proj_Id and plan_list=fund_rent_plan.rent_list)";
////			conn.doUpdateBySql(sql);
//			//'FUDG CREATE 2011-12-16 为避免网银扣划和设备回购重复扣划，特将租金扣划表数据删除
//			/**sql ="delete from t_rent_hire where  proj_id='"+proj_id+"'";
//			conn.doDeleteBySql(sql);
//			
//			//早期不抵扣时用的
//			sql ="insert into fund_fund_charge_plan(proj_id,funds_mode,funds_type,funds_name,plan_date,plan_money,item_method,payer,payee)";
//			sql +=" select proj_id,'付款','留购价款','全款','"+DepositDate_hg+"',plan_money,'非网银',payee,payer ";
//			sql +=" from fund_fund_charge_plan where funds_mode='收款' and funds_type='留购价款' and  proj_id='"+proj_id+"'";
//			//conn.doUpdateBySql(sql);
//			
//			//sql ="delete from proj_end_tqzz where proj_id = '"+proj_id+"'";
//			//conn.doUpdateBySql(sql);	
//			
//			//sql ="insert into proj_end_tqzz(proj_id,rent_sum,action_date,lg_amt,paid_rent_amount,paid_rent_amt,left_rent_amount,left_rent_amt,";
//			//sql +="type,security_money,other_should_pay,derate_penalty,overdue_amount,overdue_rent,overdue_day_amount,overdue_penalty_amt,";
//			//sql +="unto_penalty,sum_pay,taxes,agent_id)";
//			//sql +="values('"+proj_id+"','|+Cstr(rent_sum)+|','|+action_date+|','|+Cstr(lg_amt)+|','|+Cstr(paid_rent_amount)+|','|+Cstr(paid_rent_amt)+|','|+Cstr(left_rent_amount)+|','|+Cstr(left_rent_amt)+|','|+q_type+|','|+Cstr(security_money)+|','|+Cstr(other_should_pay)+|','|+Cstr(derate_penalty)+|','|+Cstr(overdue_amount)+|','|+Cstr(overdue_rent)+|','|+Cstr(overdue_day_amount)+|','|+Cstr(doc_user.LateFeeAccrual(0))+|','|+Cstr(unto_penalty)+|','|+Cstr(sum_pay)+|','|+taxes+|','|+agent_id+|')|	
//	//";
//			*/
//			return sum_penalty+"@@"+sum_interest+"@@"+TotalMoney+"@@"+lists+"@@"+lists_re+"@@"+min_list+"@@"+proj_togo+"@@"+HireTotalMoney+"@@"+RemainMoney+"@@"+GetHireMoney;
//		
//    	
//    }
//}
//
