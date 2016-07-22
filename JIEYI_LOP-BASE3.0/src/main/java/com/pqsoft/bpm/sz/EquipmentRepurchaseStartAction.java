//package com.kernal.jbpm.action.Equipment_repurchase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.utils.StringUtil;
//@SuppressWarnings("unchecked")
//public class EquipmentRepurchaseStartAction extends JbpmBaseAction{
//	 /**
//	  * 写入业务逻辑操作(startAction和endAction均触发)
//	  * @param variablesMap
//	  */
//	 public void execute(HttpServletRequest request,Map<String,Object> variablesMap,JdbcBaseDao conn) throws Exception
//	   {
//		 String cust_id = StringUtil.empty2Other(request.getParameter("cust_id"),StringUtil.nullToString(request.getAttribute("cust_id")));
//		   String proj_id = StringUtil.empty2Other(request.getParameter("proj_id"),StringUtil.nullToString(request.getAttribute("proj_id")));
//		   String cust_type = StringUtil.empty2Other(request.getParameter("cust_type"),StringUtil.nullToString(request.getAttribute("cust_type")));
//		   Map map = new HashMap();
////		   PlatformUser user = (PlatformUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////			String login_username = user.getUsername();
//		   StringBuffer sql_sb = new StringBuffer();
//			
//		   sql_sb.append(" \n   ----项目数据 ")
//	       	.append(" \n   select   vi_cust_all_info.cust_id as cust_id, --客户编号    ")
//    	.append(" \n      	    	        vi_cust_all_info.cust_name as cust_name,--客户名称    ")
//    	.append(" \n      	    	        vi_cust_all_info.cust_type as cust_type,--客户类型    ")
//    	.append(" \n      	    	        dl_mpxx.khbh as khbh,--供应商编号    ")
//    	.append(" \n      	    	        dl_mpxx.khbs  as khbs,--用于生成项目编号的供应商表述    ")
//    	.append(" \n      	    	        dl_mpxx.khmc  as khmc,--供应商全称    ")//新增字段用于实际回购方
//    	.append(" \n                      dl_mpxx.khjc as khjc,--供应商简称    ")
//    	.append(" \n                      dl_mpxx.zzdm as zzdm, --厂商    ")
//    	.append(" \n                      nvl(dl_mpxx_sub.fzjg,'无') as subcompany,--分支机构    ")
//    	.append(" \n                      nvl(proj_id_workflow_key.workflow_no,0) as workflow_no,--该供应商最大的流水号    ")
//    	.append(" \n                      ")
//    	.append(" \n                      proj_info.proj_id as proj_id ,   --项目编号,    ")
//    	.append(" \n                      proj_info.doc_id as doc_id,--文档id（唯一标示）    ")
//    	.append(" \n                      proj_info.cust_id as cust_id,  --客户编号,    ")
//    	.append(" \n                      proj_info.agent_id as agent_id, --供应商编号(khbh)    ")
//    	.append(" \n                      proj_info.proj_manage as proj_manage,--供应商名称    ")
//    	.append(" \n                      proj_info.prod_id as prod_name, --租赁物名称    ")
//    	.append(" \n                      proj_info.on_card as on_card,--是否上牌    ")
//    	.append(" \n                      proj_info.equip_amount as equip_amount,--组猎物明细数量    ")
//    	.append(" \n                      proj_info.bank as  bank,--开户银行    ")
//    	.append(" \n                      proj_info.account as account,--银行卡号    ")
//    	.append(" \n                      proj_info.account_name as account_name,--账户名称    ")
//    	.append(" \n                      proj_condition.first_payment as first_payment,--首付款合计    ")
//    	.append(" \n                      proj_info.deposit_date as deposit_date,--首付款支付日期    ")
//    	.append(" \n                      proj_info.insurance_type as insurance_type,--保险类型    ")
//    	.append(" \n                      proj_info.insurance_money as insurance_money,--保险费    ")
//    	.append(" \n                      proj_info.first_paytype as first_paytype,--首期付款方式（网银、非网银）    ")
//    	.append(" \n                      proj_info.rent_paytype as rent_paytype,--租金付款方式（网银、非网银）    ")
//    	.append(" \n                      proj_info.proj_approvedate as proj_approvedate,--验收日期    ")
//    	.append(" \n                      proj_info.is_monitor as is_monitor,--监控设备(GPS)    ")
//    	.append(" \n                      proj_info.subcompany as subcompany,--分支机构     ")
//    	.append(" \n                      proj_info.is_invoice_month as is_invoice_month,--租金开票方式(按月开具、合并开具)    ")
//    	.append(" \n                      proj_info.customertype as customertype,--客户类型(个体、法人)    ")
//    	.append(" \n                      proj_info.json_equip_list_str as json_equip_list_str,--设备列表    ")
//    	.append(" \n                      proj_info.json_charge_plan_list_str as json_charge_plan_list_str,--资金计划列表    ")
//    	
//    	.append(" \n                      rent_sum.HireMoney_1 as HireMoney_1,---剩余租金总额 ")
//    		.append(" \n                      rent_sum.HireTotalMoney+proj_condition.head_amt as HireTotalMoney,---租金总额 ")
//    		.append(" \n                      rent_sum.GetHireMoney+proj_condition.head_amt as GetHireMoney,---以收租金总额 ")
//    		.append(" \n                      rent_sum.RemainMoney as RemainMoney,---剩余租金总额 ")
//    	
//    	 .append(" \n                     proj_equip.model_id as EquipType_1,--机型    ")
//    	.append(" \n                      proj_equip.equip_sn as ProductNo_1,--出厂编号    ")
//    	
//    	 .append(" \n                       project_info.city as city,--市    ")
//    	 
//    	.append(" \n                      proj_condition.settle_method as settle_method,--租金付款方式（网银、非网银）    ")
//    	.append(" \n                      proj_condition.equip_amt as equip_amt,--设备总金额    ")
//    	.append(" \n                      proj_condition.lease_money as lease_money,--融资金额    ")
//    	.append(" \n                      proj_condition.head_ratio as head_ratio,--起租比例%    ")
//    	.append(" \n                      proj_condition.lease_term as lease_term,--融资期限    ")
//    	.append(" \n                      proj_condition.income_number as income_number,--租金付款次数    ")
//    	.append(" \n                      proj_condition.method as method,--是否规则付款    ")
//    	.append(" \n                      proj_condition.head_amt as head_amt,--1、起租租金    ")
//    	.append(" \n                      nvl(proj_condition.caution_money,0) as caution_money,--2、客户保证金    ")
//    	.append(" \n                      nvl(proj_condition.caution_money_ratio,0) as caution_money_ratio,--客户保证金比率    ")
//    	.append(" \n                      proj_condition.insurance_lessor as insurance_lessor,--4、保险费    ")
//    	.append(" \n                      proj_condition.handling_charge as handling_charge,--6、手续费    ")
//    	.append(" \n                      proj_condition.nominalprice as nominalprice,--7、留购价款：    ")
//    	.append(" \n                      proj_condition.supervision_fee as supervision_fee,--9管理服务费    ")
//    	.append(" \n                      proj_condition.period_first_date as period_first_date,--设备验收日期    ")
//    	.append(" \n                      proj_condition.year_rate as year_rate ,--年利率    ")
//    	.append(" \n                      proj_condition.lease_interval as lease_interval ,--租金间隔(1、月付，2、双月，3、季付）    ")
//    	.append(" \n                      proj_condition.period_type as period_type,--租金支付方式：(1、期初支付,0、期末支付)    ")
//    	.append(" \n                      proj_condition.hand_method as hand_method,--租前息处理：免除表单隐藏字段(免除)常量    ")
//    	.append(" \n                      proj_condition.handling_charge_ratio as handling_charge_ratio,--手续费比例    ")
//    	.append(" \n                     proj_condition.prod_id as prod_id,--租赁物id指向t_dict_node中的nodecode    ")
//    	.append(" \n                      proj_condition.is_land as is_land ,--有无盗抢险    ")
//    	   	.append(" \n                      proj_condition.start_date as HIRESTARTDATE_1 ,--租赁开始日    ")
//    	   	.append(" \n                       proj_condition.first_rent+proj_condition.head_amt as HireStartMoney_1 ,--首期租金    ")
//    	.append(" \n                      proj_condition_temp.first_rent_plan_date as first_rent_plan_date,--首付款支付日期    ")
//    	.append(" \n                      nvl(proj_condition.lessee_caution_money,0) as lessee_caution_money,--5、担保费：    ")
//    	.append(" \n                      nvl(proj_condition.lessee_caution_ratio,0) as lessee_caution_ratio,--担保费率    ")
//    	.append(" \n                      nvl(proj_condition.sale_caution_money,0) as sale_caution_money,--8、DB保证金    ")
//    	.append(" \n                      nvl(proj_condition.sale_caution_ratio,0) as sale_caution_ratio,--DB保证金比率    ")
//    	.append(" \n                      proj_condition.insurance_lessor_ratio as insurance_lessor_ratio,--保险费率    ")
//    	.append(" \n                      proj_condition.assuretype as assuretype,--担保方式：    ")
//    	.append(" \n                      proj_condition.operation_type as operation_type,--业务模式：（建机、保仓税）    ")
//    	.append(" \n                      proj_condition.rent_income_type as rent_income_type,--租金支付方式：(期初支付,期末支付)    ")
//    	.append(" \n                      proj_condition.other_fee as other_fee,--sflc其他费用    ")
//    	.append(" \n                      nvl(proj_condition_temp.THREE_INSURANCE,0) as THREE_INSURANCE,--三者险年保费    ")
//    	.append(" \n                      proj_condition_temp.infoSub as infoSub,--资料后补：(否;是,70%;是,100%)    ")
//    	.append(" \n                      proj_condition.first_rent as first_rent,--第1期租金    ")
//    	.append(" \n                      proj_condition.total_amt  as total_amt,--租金合计   ")
//    	.append(" \n                       project_info.project_name as project_name,--项目名称    ")
//    	.append(" \n                       project_info.project_type as project_type,--工程性质    ")
//    	.append(" \n                       project_info.industry as industry,--所属行业    ")
//    	.append(" \n                       project_info.project_term as project_term,--工程期限(月)    ")
//    	.append(" \n                       project_info.cons_area as cons_area,--是否异地施工    ")
//    	.append(" \n                       project_info.province as province,--省    ")
//    	.append(" \n                       project_info.city as city,--市    ")
//    	.append(" \n                       project_info.cons_place as cons_place,--施工地点    ")
//    	.append(" \n                       project_info.project_amt as project_amt,--施工规模(万)    ")
//    	.append(" \n                       project_info.cont_term as cont_term,--合同时间    ")
//    	.append(" \n                       project_info.cons_content as cons_content,--合同内容    ")
//    	.append(" \n                       project_info.month_income as month_income,--每月收入(万）    ")
//    	.append(" \n                       project_info.cont_property as cont_property,--承包性质    ")
//    	.append(" \n                       project_info.local_economy as local_economy,--本地经济    ")
//    	.append(" \n                       project_info.economy_info  as economy_info,--经济环境    ")
//    	.append(" \n                       project_info.firstsource as firstsource,--首付款来源    ")
//    	.append(" \n                       project_info.rentsource as rentsource,--还租依赖工程款    ")
//    	.append(" \n                       project_info.construction_type as construction_type,--项目结构类型（承包工程）    ")
//    	.append(" \n                       project_info.project_startdate as project_startdate,--开工日期    ")
//    	.append(" \n                       project_info.year_income as year_income,--年营业收入(万)    ")
//    	.append(" \n                       project_info.month_debt as month_debt,--每月债务(万)    ")
//    	.append(" \n                       project_info.tent_price as tent_price,--台班费/月(万)    ")
//    	.append(" \n                       project_info.is_strange as  is_strange-- 是否异地租赁     ")
//    	.append(" \n                        ")
//    	.append(" \n                 from(     ")
//    	.append(" \n                     select     ")
//    	.append(" \n                      proj_id, cust_id,customertype, agent_id, proj_manage, prod_id, on_card, equip_amount, bank, account, account_name,    ")
//    	.append(" \n                      first_payment, deposit_date,insurance_type, insurance_money, first_paytype, rent_paytype, proj_approvedate,    ")
//    	.append(" \n                      doc_id,is_monitor, subcompany, is_invoice_month,json_equip_list_str,json_charge_plan_list_str    ")
//    	.append(" \n                     from proj_info where proj_id='{proj_id}'    ")
//    	.append(" \n                )proj_info    ")
//    	.append(" \n                left join (    ")
//    	.append(" \n                   select cust_id,cust_name,cust_type,bmbh from vi_cust_all_info    ")
//    	.append(" \n                )vi_cust_all_info on proj_info.cust_id = vi_cust_all_info.cust_id    ")
//    	.append(" \n                  left join(    ")
//    	.append(" \n                   select sub_id,login_name from jb_yhxx where login_name ='admin'    ")
//    	.append(" \n                )jb_yhxx on 1=1     ")
//    	.append("\n                 left join (select  model_id,equip_sn from proj_equip where proj_id ='{proj_id}' and rownum=1 ) proj_equip on 1=1")
//    	.append(" \n                left join(    ")
//    	.append(" \n                   select id,fzjg from dl_mpxx_sub     ")
//    	.append(" \n                )dl_mpxx_sub on dl_mpxx_sub.id=jb_yhxx.sub_id    ")
//    	.append(" \n                left join(    ")
//    	.append(" \n                    select khbh,khbs,khjc,khmc,zzdm from dl_mpxx    ")//新增khmc
//    	.append(" \n                )dl_mpxx on  dl_mpxx.khbh = vi_cust_all_info.bmbh     ")
//    	.append(" \n                left join(    ")
//    	.append(" \n                    select khbs,workflow_no from proj_id_workflow_key    ")
//    	.append(" \n                )proj_id_workflow_key on proj_id_workflow_key.khbs = dl_mpxx.khbs    ")
//    	.append(" \n                  ")
//    	.append(" \n                left join(    ")
//    	.append(" \n                    select     ")
//    	.append(" \n                       proj_id,settle_method,equip_amt,lease_money,head_ratio,lease_term,    ")
//    	.append(" \n                       income_number,method,head_amt,caution_money,caution_money_ratio,insurance_lessor,    ")
//    	.append(" \n                       handling_charge,nominalprice,supervision_fee,period_first_date,year_rate,    ")
//    	.append(" \n                       lease_interval,period_type,hand_method,handling_charge_ratio,prod_id,is_land   ")
//    	.append(" \n                       ,lessee_caution_money,lessee_caution_ratio ,sale_caution_money,    ")
//    	.append(" \n                       sale_caution_ratio,insurance_lessor_ratio,assuretype,operation_type,rent_income_type,    ")
//    	.append(" \n                       other_fee,first_rent,total_amt,start_date,first_payment    ")
//    	.append(" \n                   from proj_condition where proj_id='{proj_id}'     ")
//    	.append(" \n                )proj_condition on proj_info.proj_id = proj_condition.proj_id     ")
//    	.append(" \n                left join(    ")
//    	.append(" \n                    select     ")
//    	.append(" \n                       proj_id,THREE_INSURANCE,infoSub,first_rent_plan_date   ")
//    	.append(" \n                   from proj_condition_temp where proj_id='{proj_id}'     ")
//    	.append(" \n                )proj_condition_temp on proj_info.proj_id = proj_condition.proj_id    ")
//    	.append(" \n                left join(    ")
//    	.append(" \n                   select     ")
//    	.append(" \n                       proj_id,project_name,project_type,industry,project_term,cons_area,province,city,cons_place    ")
//    	.append(" \n                       ,project_amt,cont_term,cons_content,month_income,cont_property,local_economy,economy_info,creator,create_date,firstsource    ")
//    	.append(" \n                       ,rentsource,construction_type,project_startdate,year_income,month_debt,tent_price,is_strange       ")
//    	.append(" \n                    from project_info where proj_id='{proj_id}'   ")
//    	.append(" \n                )project_info on proj_info.proj_id = project_info.proj_id   ")
//    	.append(" \n                left join (  ")
//    	.append(" \n                      select a.sum_rent-b.sum_rent HireMoney_1,a.proj_id,a.sum_rent HireTotalMoney,b.sum_rent GetHireMoney,c.sum_rent RemainMoney from (   ")
//    	.append(" \n                         select sum(rent) as sum_rent,sum(interest) as sum_interest,sum(penalty) as sum_penalty,proj_id  as proj_id    ")
//    	.append(" \n                         from fund_rent_plan where proj_id ='{proj_id}' group by proj_id    ")
//    	.append(" \n                         ) a  ")
//    	.append(" \n                         left join (   ")
//    	.append(" \n                         select sum(rent) as sum_rent,sum(interest) as sum_interest,sum(penalty)  as sum_penalty,proj_id as proj_id    ")
//    	.append(" \n                         from fund_rent_plan where proj_id ='{proj_id}' and nvl(plan_status,0)=1 group by proj_id    ")
//    	.append(" \n                         ) b on a.proj_id = b.proj_id    ")
//    	.append(" \n                         left join (   ")
//    	.append(" \n                         select sum(rent) as sum_rent,sum(interest) as sum_interest,sum(penalty)  as sum_penalty,proj_id as proj_id    ")
//    	.append(" \n                         from fund_rent_plan where proj_id ='{proj_id}' and nvl(plan_status,0)=0 group by proj_id    ")
//    	.append(" \n                         ) c on a.proj_id = c.proj_id ) rent_sum on  proj_info.proj_id = rent_sum.proj_id  ");
//
//	String sql_  = sql_sb.toString();
//	 sql_  = sql_.replaceAll("\\{proj_id\\}",proj_id);
//	 //System.out.println(sql_);
//	map = (Map)(conn.doQueryBySql(sql_.toString()).get(0));
//			 map.put("CustomerNumber", cust_id);
//			  map.put("ProjectNo", proj_id);
//			  map.put("CustomerType", cust_type);
//		      variablesMap.putAll(map);
//		   
//		 
//	   }
//	
//}
