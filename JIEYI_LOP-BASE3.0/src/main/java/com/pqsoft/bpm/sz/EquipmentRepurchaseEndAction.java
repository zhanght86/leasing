//package com.kernal.jbpm.action.Equipment_repurchase;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.springSecurity.PlatformUser;
//import com.kernal.utils.StringUtil;
//@SuppressWarnings("unchecked")
//public class EquipmentRepurchaseEndAction extends JbpmBaseAction{
//	  /**
//	   * 保存操作(saveAction触发)
//	   * 结束流程时，更新数据库
//	   * @param variablesMap
//	   */
//	 public void execute(HttpServletRequest request,Map<String,Object> variablesMap,JdbcBaseDao conn) throws Exception
//	   {
//		
//		 	PlatformUser user = (PlatformUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			//String login_username = user.getUsername();
//			String agent_id= user.getAgentId();
//			String czyid = user.getUserID();
//			//String agent_id = StringUtil.nullToString(variablesMap.get("agent_id"));
//	    	String proj_id = String.valueOf(variablesMap.get("ProjectNo"));
//	    	String proj_togo = String.valueOf(variablesMap.get("processDefinitionName"));
//	    	String DepositDate_hg = String.valueOf(variablesMap.get("DepositDate_hg"));
//	    	String HireTotalMoney = String.valueOf(variablesMap.get("HireTotalMoney"));
//	    	String SupervisionMoney_1 = String.valueOf(variablesMap.get("SupervisionMoney_1"));
//	    	String TotalMoney = String.valueOf(variablesMap.get("TotalMoney"));
//	    	String RemainMoney = String.valueOf(variablesMap.get("RemainMoney"));
//	    	String caution_money = String.valueOf(variablesMap.get("caution_money"));
//	    	String Other = String.valueOf(variablesMap.get("Other"));
//	    	String INTERESTFREE = String.valueOf(variablesMap.get("INTERESTFREE"));
//	    	String sum_interest = String.valueOf(variablesMap.get("sum_interest"));
//	    	String ManageFree = String.valueOf(variablesMap.get("ManageFree"));
//	    	String sum_penalty = String.valueOf(variablesMap.get("sum_penalty"));
//	    	String son_paying_table = StringUtil.nullToString(variablesMap.get("son_paying_table")).replaceAll("[^\\d]","");
//
//	    	String GetHireMoney = String.valueOf(variablesMap.get("GetHireMoney"));
////	    	int FinanceTerm = Integer.parseInt(String.valueOf(variablesMap.get("FinanceTerm")));
//	    	
////	    	String tmpCompany = "山推租赁有限公司";
////	    	int TimeNumber = 36;//??????????暂时不明白WFShantuiDB中的SprojInfoToSql2里的。46行
//	    	String proj_status = "35";
//	    	String sql = "";
//	    	sql = "select distinct son_paying_table from fund_rent_plan where plan_status=0 and proj_id='"+proj_id+"'";
//	    	List l = conn.doQueryBySql(sql);
//	    	
//	    	if(l.size()>1){
//	    		proj_status="20";
//	    	} 
//	    
//	    	//下一步的进候进行的操作
//			if("下一步".equals(variablesMap.get("currentTaskSubmitButtonText"))){
//				if(proj_togo!=null&&"质量问题结束".equals(proj_togo)){
//					//'FUDG UPDATE 2012-1-11 添加质量问题结束
////					status = "35"  '保留默认值，以避免更新时流程中的项目受到影响
//					proj_status = "36";
//				}
//				sql = "update proj_info set proj_status='"+proj_status+"'  where proj_id = '"+proj_id+"'";
//				conn.doUpdateBySql(sql);
////				//sql ="insert into fund_fund_charge_plan(proj_id,funds_mode,funds_type,funds_name,";
////				//sql +="plan_date,plan_money,item_method,payer,payee) select proj_id,'付款','留购价款',";
////				//sql +="'全款','"+DepositDate_hg+"',plan_money,'非网银',payee,payer ";
////				//sql +="from fund_fund_charge_plan where funds_mode='收款' and funds_type='留购价款'";
////				//sql +=" and  proj_id='"+proj_id+"'";
//				//conn.doUpdateBySql(sql);
//				sql ="select received_amount as xx,agent_id from vi_proj_info where proj_id='"+proj_id+"'";
//				String xx = ((Map)conn.doQueryBySql(sql).get(0)).get("xx").toString();
//				agent_id = ((Map)conn.doQueryBySql(sql).get(0)).get("agent_id")+"";
//				sql ="select (lease_term-received_amount) as left_rent_amount from vi_proj_info  where proj_id='"+proj_id+"'";
//				String left_rent_amount =  ((Map)conn.doQueryBySql(sql).get(0)).get("left_rent_amount").toString();
//				sql ="SELECT frp.proj_id,nvl(COUNT(frp.id),0) AS overdue_amount, nvl(SUM(frp.rent),0) AS overdue_sum,";
//				sql +="sum( to_date('"+DepositDate_hg+"' ,'YYYY-MM-DD')-to_date(plan_date,'YYYY-MM-DD')) as overdue_day FROM  fund_rent_plan frp WHERE ";
//				sql +="nvl(his,0)<1  and frp.plan_status = 0 AND frp.plan_date < '";
//				sql +=DepositDate_hg+"' and proj_Id='"+proj_id+"' GROUP BY frp.proj_id";
//				List list = conn.doQueryBySql(sql);
//				String overdue_amount = "";
//				String overdue_sum = "";
//				String overdue_day = "";
//				if(list.size()>0){
//					
//					Map map = (Map)list.get(0);
//					 overdue_sum = StringUtil.empty2Other(StringUtil.nullToString((map).get("overdue_sum")),"0");
//					 overdue_amount = StringUtil.empty2Other(StringUtil.nullToString((map).get("overdue_amount")),"0");
//					 overdue_day = StringUtil.empty2Other(StringUtil.nullToString((map).get("overdue_day")),"0");
//				}
//				
//				sql ="delete from proj_end_tqzz where proj_id = '"+proj_id+"' and son_paying_table='"+son_paying_table+"'";
//				conn.doUpdateBySql(sql);		
//				sql ="insert into proj_end_tqzz(proj_id,rent_sum,action_date,lg_amt,paid_rent_amount,paid_rent_amt,";
//				sql +=" left_rent_amount,left_rent_amt,type,security_money,other_should_pay,derate_penalty,overdue_amount,";
//				sql +="overdue_rent,overdue_day_amount,overdue_penalty_amt,unto_penalty,sum_pay,taxes,agent_id ,creator,create_date,son_paying_table ) ";
//				sql +=" values('"+proj_id+"','"+HireTotalMoney+"','"+DepositDate_hg+"','"+SupervisionMoney_1+"','"+xx+"',";
//				sql +="'"+GetHireMoney+"','"+left_rent_amount+"','"+RemainMoney+"','"+proj_togo+"','"+caution_money+"'";
//				sql +=",'"+Other+"','"+INTERESTFREE+"','"+overdue_amount+"','"+overdue_sum+"','"+overdue_day+"','"+sum_penalty+"',";
//				sql +="'"+sum_interest+"','"+TotalMoney+"','"+ManageFree+"','"+agent_id+"','"+czyid+"',to_char(sysdate,'yyyy-mm-dd'),"+son_paying_table+")";;
//				conn.doUpdateBySql(sql);
//				//'从资金计划中删除保证金
//				//sql ="delete from fund_fund_charge_plan where proj_id='"+proj_id+"' and funds_type='金保证' and funds_mode='付款'";
//				//conn.doDeleteBySql(sql);
//				//'核销租金
//				//sql ="update fund_rent_plan set plan_status='1' where  nvl(plan_status,0)=0 and proj_id='"+proj_id+"'";
//				//conn.doUpdateBySql(sql);
//				//'核销罚息
//				//sql ="update fund_rent_plan set penalty='0' where  proj_id='"+proj_id+"'";
//				//conn.doUpdateBySql(sql);
//				//--将最后一期款下期开始逾期以及其他计划做历史处理--
////				for(int i = 1;i<=FinanceTerm;i++){
////					
////					sql ="update fund_rent_plan set plan_status='1' where  nvl(plan_status,0)=0 and proj_id='"+proj_id+"' ";
////					sql +="and rent_list='"+i+"'";
////				}
//				//'插入 租金实收信息
//			//	sql ="insert into fund_rent_income( proj_id,rent_type,plan_list,hire_date,rent)";
//				//sql +=" values ('"+proj_id+"','| + tmpRentName + |','|+i+|','| + Format(Now,"yyyy-mm-dd") + |','| + Cstr(rent) +|')";
//				//'插入 罚息实收信息
//			}
//				
//				
//			
//			//退回，不进行任何操作
//			else if("退回".equals(variablesMap.get("currentTaskSubmitButtonText"))){
//			}
//	   }
//	
//}
