//package com.jbpm.utils;
//
//import java.util.List;
//import java.util.Map;
//
//
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.springSecurity.PlatformUser;
//import com.kernal.utils.ResourceUtil;
//import com.kernal.utils.StringUtil;
//import com.kernal.utils.WebUtil;
//
//@SuppressWarnings("unchecked")
//public class PersonalProjectWorkFlowUtil 
//{
//	private static JdbcBaseDao personalProjectDao =null;
//	static
//	{
//		personalProjectDao = (JdbcBaseDao)WebUtil.getApplicationContext().getBean("jdbcDao");
//	}
//	public static Map getPersonalProjectMapInfo(String cust_id,String cust_type,String proj_id) throws Exception 
//	{
////		//String proj_id = "";
//		String sql_query = "select proj_id from proj_info where  proj_status='0' and customerType='"+cust_type+"' and cust_id='"+cust_id+"'";
//		List list_query = personalProjectDao.doQueryBySql(sql_query);
//		if(list_query.size()>=1)
//		{
//			proj_id = StringUtil.nullToString(((Map)list_query.get(0)).get("PROJ_ID"));
//		}
//		PlatformUser user = (PlatformUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String login_username = user.getUsername();
//		StringBuffer sql_sb = new StringBuffer();
//		sql_sb.append(" \n   ----立项信息数据 ")
//    	.append(" \n   select  ")
//    	.append(" \n         vi_cust_all_info.cust_id as cust_id, --客户编号 ")
//    	.append(" \n         vi_cust_all_info.cust_name as cust_name,--客户名称 ")
//    	.append(" \n         vi_cust_all_info.cust_type as cust_type,--客户类型 ")
//    	.append(" \n         dl_mpxx.khbh as khbh,--供应商编号 ")
//    	.append(" \n         dl_mpxx.db_level as db_level,--模式 ")
//    	.append(" \n         dl_mpxx.khbs  as khbs,--用于生成项目编号的供应商表述 ")
//    	.append(" \n         dl_mpxx.khjc as khjc,--供应商简称 ")
//    	.append(" \n         dl_mpxx.BALANCE_LOAN_FLAG as balance_loan_flag, ")
//    	.append(" \n         dl_mpxx.IF_B_MODE as if_b_mode, ")//增加 担保模式 A、B模式判断字段  hxl
//    	.append(" \n         dl_mpxx.zzdm as zzdm, --厂商 ")
//    	.append(" \n         nvl(dl_mpxx_sub.fzjg,'无') as subcompany,--分支机构 ")
//    	.append(" \n         nvl(proj_id_workflow_key.workflow_no,0) as workflow_no,--该供应商最大的流水号 ")
//    	.append(" \n         /***立项信息开始****/ ")
//    	.append(" \n         proj_info.proj_id as proj_id ,   --项目编号, ")
//    	.append(" \n         proj_info.doc_id as doc_id,--文档id（唯一标示） ")
//    	.append(" \n         --proj_info.cust_id as cust_id,  --客户编号, ")
//    	.append(" \n         --proj_info.agent_id as agent_id, --供应商编号(khbh) ")
//    	.append(" \n         --proj_info.proj_manage as proj_manage,--供应商名称 ")
//    	.append(" \n         proj_info.prod_id as prod_id, --租赁物名称 ")
//    	.append(" \n         proj_info.on_card as on_card,--是否上牌 ")
//    	.append(" \n         proj_info.equip_amount as equip_amount,--组猎物明细数量 ")
//    	.append(" \n         proj_info.bank as  bank,--开户银行 ")
//    	.append(" \n         proj_info.account as account,--银行卡号 ")
//    	.append(" \n         proj_info.account_name as account_name,--账户名称 ")
//    	.append(" \n         proj_condition_temp.first_payment as first_payment,--首付款合计 ")
//    	.append(" \n         proj_info.deposit_date as deposit_date,--首付款支付日期 ")
//    	.append(" \n         proj_info.insurance_type as insurance_type,--保险类型 ")
//    	.append(" \n         --proj_info.insurance_money as insurance_money,--保险费 ")
//    	.append(" \n         proj_info.first_paytype as first_paytype,--首期付款方式（网银、非网银） ")
//    	.append(" \n         proj_info.rent_paytype as rent_paytype,--租金付款方式（网银、非网银） ")
//    	.append(" \n         proj_info.proj_approvedate as proj_approvedate,--验收日期 ")
//    	.append(" \n         proj_info.is_monitor as is_monitor,--监控设备(GPS) ")
//    	.append(" \n         --proj_info.subcompany as subcompany,--分支机构  ")
//    	.append(" \n         proj_info.is_invoice_month as is_invoice_month,--租金开票方式(按月开具、合并开具) ")
//    	.append(" \n         proj_info.customertype as customertype,--客户类型(个体、法人) ")
//    	.append(" \n         --proj_info.json_equip_list_str as json_equip_list_str,--设备列表 ")
//    	.append(" \n         --proj_info.json_charge_plan_list_str as json_charge_plan_list_str,--资金计划列表 ")
//    	.append(" \n         proj_info.terminalcustid,proj_info.terminalcustname,proj_info.TerminalAccountName,proj_info.TerminalAccount,proj_info.TerminalCustType,proj_info.TerminalCardId,proj_info.TerminalBank,--终端客户信息 ")
//    	.append(" \n         /***立项信息结束****/ ")
//    	.append(" \n         --------------------------------------------------------------------------------- ")
//    	.append(" \n         /***立项条件开始***/  ")
//    	.append(" \n         proj_condition_temp.settle_method as settle_method,--租金付款方式（网银、非网银） ")
//    	.append(" \n         proj_condition_temp.equip_amt as equip_amt,--设备总金额 ")
//    	.append(" \n         proj_condition_temp.lease_money as lease_money,--融资金额 ")
//    	.append(" \n         proj_condition_temp.head_ratio as head_ratio,--起租比例% ")
//    	.append(" \n         proj_condition_temp.lease_term as lease_term,--融资期限 ")
//    	.append(" \n         proj_condition_temp.income_number as income_number,--租金付款次数 ")
//    	.append(" \n         proj_condition_temp.method as method,--是否规则付款 ")
//    	.append(" \n         proj_condition_temp.head_amt as head_amt,--1、起租租金 ")
//    	.append(" \n         proj_condition_temp.caution_money as caution_money,--2、客户保证金 ")
//    	.append(" \n         proj_condition_temp.caution_money_ratio as caution_money_ratio,--客户保证金比率 ")
//    	.append(" \n         proj_condition_temp.insurance_lessor as insurance_lessor,--4、保险费 ")
//    	.append(" \n         proj_condition_temp.handling_charge as handling_charge,--6、手续费 ")
//    	.append(" \n         proj_condition_temp.nominalprice as nominalprice,--7、留购价款： ")
//    	.append(" \n         proj_condition_temp.supervision_fee as supervision_fee,--9管理服务费 ")
//    	.append(" \n         proj_condition_temp.period_first_date as period_first_date,--设备验收日期 ")
//    	.append(" \n         proj_condition_temp.year_rate as year_rate ,--年利率 ")
//    	.append(" \n         proj_condition_temp.lease_interval as lease_interval ,--租金间隔(1、月付，2、双月，3、季付） ")
//    	.append(" \n         proj_condition_temp.period_type as period_type,--租金支付方式：(1、期初支付,0、期末支付) ")
//    	.append(" \n         proj_condition_temp.hand_method as hand_method,--租前息处理：免除表单隐藏字段(免除)常量 ")
//    	.append(" \n         proj_condition_temp.handling_charge_ratio as handling_charge_ratio,--手续费比例 ")
//    	.append(" \n        -- proj_condition_temp.prod_id as prod_id,--租赁物id指向t_dict_node中的nodecode ")
//    	.append(" \n         proj_condition_temp.is_land as is_land ,--有无盗抢险 ")
//    	.append(" \n         proj_condition_temp.first_rent_plan_date as first_rent_plan_date,--首付款支付日期 ")
//    	.append(" \n         proj_condition_temp.lessee_caution_money as lessee_caution_money,--5、担保费： ")
//    	.append(" \n         proj_condition_temp.lessee_caution_ratio as lessee_caution_ratio,--担保费率 ")
//    	.append(" \n         proj_condition_temp.sale_caution_money as sale_caution_money,--8、DB保证金 ")
//    	.append(" \n         proj_condition_temp.sale_caution_ratio as sale_caution_ratio,--DB保证金比率 ")
//    	.append(" \n         proj_condition_temp.insurance_lessor_ratio as insurance_lessor_ratio,--保险费率 ")
//    	.append(" \n         proj_condition_temp.assuretype as assuretype,--担保方式： ")
//    	.append(" \n         proj_condition_temp.operation_type as operation_type,--业务模式：（建机、保仓税） ")
//    	.append(" \n         proj_condition_temp.rent_income_type as rent_income_type,--租金支付方式：(期初支付,期末支付) ")
//    	.append(" \n         proj_condition_temp.other_fee as other_fee,--sflc其他费用 ")
//    	.append(" \n         proj_condition_temp.THREE_INSURANCE as THREE_INSURANCE,--三者险年保费 ")
//    	.append(" \n         proj_condition_temp.infoSub as infoSub,--资料后补：(否;是,70%;是,100%) ")
//    	.append(" \n         proj_condition_temp.first_rent as first_rent,--第1期租金 ")
//    	.append(" \n         proj_condition_temp.total_amt  as total_amt,--租金合计")
//    	.append(" \n         /***立项条件结束***/ ")
//    	.append(" \n         --------------------------------------------------------------------------------- ")
//    	.append(" \n         /***项目信息开始***/  ")
//    	.append(" \n          project_info.project_name as project_name,--项目名称 ")
//    	.append(" \n          project_info.project_type as project_type,--工程性质 ")
//    	.append(" \n          project_info.industry as industry,--所属行业 ")
//    	.append(" \n          project_info.project_term as project_term,--工程期限(月) ")
//    	.append(" \n          project_info.cons_area as cons_area,--是否异地施工 ")
//    	.append(" \n          project_info.province as province,--省 ")
//    	.append(" \n          project_info.city as city,--市 ")
//    	.append(" \n          project_info.cons_place as cons_place,--施工地点 ")
//    	.append(" \n          project_info.project_amt as project_amt,--施工规模(万) ")
//    	.append(" \n          project_info.cont_term as cont_term,--合同时间 ")
//    	.append(" \n          project_info.cons_content as cons_content,--合同内容 ")
//    	.append(" \n          project_info.month_income as month_income,--每月收入(万） ")
//    	.append(" \n          project_info.cont_property as cont_property,--承包性质 ")
//    	.append(" \n          project_info.local_economy as local_economy,--本地经济 ")
//    	.append(" \n          project_info.economy_info  as economy_info,--经济环境 ")
//    	.append(" \n          project_info.firstsource as firstsource,--首付款来源 ")
//    	.append(" \n          project_info.rentsource as rentsource,--还租依赖工程款 ")
//    	.append(" \n          project_info.construction_type as construction_type,--项目结构类型（承包工程） ")
//    	.append(" \n          project_info.project_startdate as project_startdate,--开工日期 ")
//    	.append(" \n          project_info.year_income as year_income,--年营业收入(万) ")
//    	.append(" \n          project_info.month_debt as month_debt,--每月债务(万)： ")
//    	.append(" \n          project_info.tent_price as tent_price,--台班费/月(万)： ")
//    	.append(" \n          project_info.is_strange as  is_strange-- 是否异地租赁：  ")
//    	.append(" \n         /***项目信息结束***/ ")
//    	.append(" \n    from( ")
//    	.append(" \n      select cust_id,cust_name,cust_type,bmbh from vi_cust_all_info where cust_id='"+cust_id+"' and cust_type='"+cust_type+"' ")
//    	.append(" \n   )vi_cust_all_info left join( ")
//    	.append(" \n      select sub_id,login_name from jb_yhxx where login_name ='"+login_username+"' ")
//    	.append(" \n   )jb_yhxx on 1=1  ")
//    	.append(" \n   left join( ")
//    	.append(" \n      select id,fzjg from dl_mpxx_sub  ")
//    	.append(" \n   )dl_mpxx_sub on dl_mpxx_sub.id=jb_yhxx.sub_id ")
//    	.append(" \n   left join( ")
//    	.append(" \n       select khbh,khbs,khjc ,zzdm,nvl(balance_loan_flag,'关闭') balance_loan_flag,nvl(IF_B_MODE,'关闭') IF_B_MODE,db_level from dl_mpxx ")
//    	.append(" \n   )dl_mpxx on  dl_mpxx.khbh = vi_cust_all_info.bmbh  ")
//    	.append(" \n   left join( ")
//    	.append(" \n       select khbs,workflow_no from proj_id_workflow_key  where year_ = substr(sysdate,0,4) ")
//    	.append(" \n   )proj_id_workflow_key on proj_id_workflow_key.khbs = dl_mpxx.khbs ")
//    	.append(" \n   left join( ")
//    	.append(" \n        select  ")
//    	.append(" \n         proj_id, cust_id,customertype, agent_id, proj_manage, prod_id, on_card, equip_amount, bank, account, account_name, ")
//    	.append(" \n         first_payment, deposit_date,insurance_type, insurance_money, first_paytype, rent_paytype, proj_approvedate, ")
//    	.append(" \n         doc_id,is_monitor, subcompany, is_invoice_month,json_equip_list_str,json_charge_plan_list_str,terminalcustid,terminalcustname,TerminalAccountName,TerminalAccount,TerminalCustType,TerminalCardId,TerminalBank ")
//    	.append(" \n        from proj_info where proj_id='{proj_id}'  ")
//    	.append(" \n   )proj_info on proj_info.cust_id = vi_cust_all_info.cust_id and proj_info.customertype=vi_cust_all_info.cust_type")
//    	.append(" \n   left join( ")
//    	.append(" \n       select  ")
//    	.append(" \n          proj_id,doc_id,settle_method,equip_amt,lease_money,head_ratio,lease_term, ")
//    	.append(" \n          income_number,method,head_amt,caution_money,caution_money_ratio,insurance_lessor, ")
//    	.append(" \n          handling_charge,nominalprice,supervision_fee,period_first_date,year_rate, ")
//    	.append(" \n          lease_interval,period_type,hand_method,handling_charge_ratio,prod_id,is_land, ")
//    	.append(" \n          first_rent_plan_date,lessee_caution_money,lessee_caution_ratio ,sale_caution_money, ")
//    	.append(" \n          sale_caution_ratio,insurance_lessor_ratio,assuretype,operation_type,rent_income_type, ")
//    	.append(" \n          other_fee,THREE_INSURANCE,infoSub,first_rent,total_amt,first_payment ")
//    	.append(" \n      from proj_condition_temp where proj_id='{proj_id}'  ")
//    	.append(" \n   )proj_condition_temp on proj_info.proj_id = proj_condition_temp.proj_id and proj_condition_temp.doc_id=proj_condition_temp.doc_id ")
//    	.append(" \n   left join( ")
//    	.append(" \n      select  ")
//    	.append(" \n          proj_id,project_name,project_type,industry,project_term,cons_area,province,city,cons_place ")
//    	.append(" \n          ,project_amt,cont_term,cons_content,month_income,cont_property,local_economy,economy_info,creator,create_date,firstsource ")
//    	.append(" \n          ,rentsource,construction_type,project_startdate,year_income,month_debt,tent_price,is_strange    ")
//    	.append(" \n       from project_info where proj_id='{proj_id}'  ")
//    	.append(" \n   )project_info on proj_info.proj_id = project_info.proj_id");
//        String sql_  = sql_sb.toString().replaceAll("\\{proj_id\\}",proj_id);
//		Map map = (Map)(personalProjectDao.doQueryBySql(sql_).get(0));
//		if(ResourceUtil.isDebug())
//		{
//			System.out.println("sql:"+sql_);
//		}
//		return map;
//	}
//	   public static void getAgentProjectMoneyInfo(Map variablesMap) throws Exception
//	   {
//			//供应商编号
//			String khbh = StringUtil.nullToString(variablesMap.get("KHBH"));
//			String tmpProjectNo = StringUtil.nullToString(variablesMap.get("PROJ_ID"));
//			
//			String sql = "select award_limit,sustain_limit,onetoone_limit,db_level,zzdm from dl_mpxx where khbh ='"+khbh+"'";
//			List l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				String AgentCreditA = StringUtil.nullToString(m.get("AWARD_LIMIT"));
//				String AgentCreditSustain = StringUtil.nullToString(m.get("SUSTAIN_LIMIT"));
//				String AgentCreditOnetoone = StringUtil.nullToString(m.get("ONETOONE_LIMIT"));
//				variablesMap.put("AGENTCREDITA",AgentCreditA);
//				variablesMap.put("AGENTCREDITSUSTAIN",AgentCreditSustain);
//				variablesMap.put("AGENTCREDITONETOONE",AgentCreditOnetoone);
//			}
//			//标准额度
//			sql =  "select sum(rent*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(PF.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(PF.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pct.HEAD_RATIO AND RULE2 < pct.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(PF.PROD_ID,'空'))  ) ) as sumrentD from fund_rent_plan frp left join proj_info pf on frp.proj_id=pf.proj_id left join proj_condition pct on pct.proj_id=pf.proj_id where nvl(plan_status,0)=0 and pct.assuretype ='免担保' and agent_id='"+khbh+"' and proj_status>=20";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			//支持额度
//			sql = "select sum(rent*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(PF.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(PF.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pct.HEAD_RATIO AND RULE2 < pct.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(PF.PROD_ID,'空'))  ) ) as sumrentD_s from fund_rent_plan frp left join proj_info pf on frp.proj_id=pf.proj_id left join proj_condition pct on pct.proj_id=pf.proj_id where nvl(plan_status,0)=0 and pct.assuretype in('本地担保公司','租赁公司代办','第三方法人担保') and agent_id='"+khbh+"' and proj_status>=20";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			//一单一议
//			sql = "select sum(rent*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(PF.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(PF.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pct.HEAD_RATIO AND RULE2 < pct.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(PF.PROD_ID,'空'))  ) ) as sumrentD_o from fund_rent_plan frp left join proj_info pf on frp.proj_id=pf.proj_id left join proj_condition pct on pct.proj_id=pf.proj_id where nvl(plan_status,0)=0 and pct.assuretype ='一单一议' and agent_id='"+khbh+"' and proj_status>=20";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			//流程中的项目融资总额
//			//'流程中的项目融资总额_标准
//			sql = "select sum(lease_money*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(P.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(P.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pc.HEAD_RATIO AND RULE2 < pc.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(P.PROD_ID,'空'))  ) ) as leasingAllMoney from proj_condition_temp pc left join proj_info p on pc.proj_id = p.proj_id where nvl(p.proj_status,0) < 20 and pc.assuretype='免担保' and agent_id='"+khbh+"' and pc.proj_id<>'"+tmpProjectNo+"'";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			//'流程中的项目融资总额_支持
//			sql = "select sum(lease_money*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(P.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(P.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pc.HEAD_RATIO AND RULE2 < pc.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(P.PROD_ID,'空'))  ) ) as leasingAllMoney_s from proj_condition_temp pc left join proj_info p on pc.proj_id = p.proj_id where nvl(p.proj_status,0) < 20 and pc.assuretype in('本地担保公司','租赁公司代办','第三方法人担保') and agent_id='"+khbh+"' and pc.proj_id<>'"+tmpProjectNo+"'";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			//'流程中的项目融资总额_一单一议
//			sql = "select sum(lease_money*( SELECT NVL(MIN(nvl(LINE_RATE,1)),1) FROM AUDIT_LINE_RULES ";
//			sql +=  "   WHERE (ATTRIBUTES='上牌方式' AND DETAIL = NVL(P.ON_CARD,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='监控设备' AND DETAIL = NVL(P.IS_MONITOR,'空')) OR  ";
//			sql +=  "     (ATTRIBUTES='首付比例' AND RULE1 >= pc.HEAD_RATIO AND RULE2 < pc.HEAD_RATIO) OR  ";
//			sql +=  "     (ATTRIBUTES='租赁物类型' AND DETAIL = NVL(P.PROD_ID,'空'))  ) ) as leasingAllMoney_o from proj_condition_temp pc left join proj_info p on pc.proj_id = p.proj_id where nvl(p.proj_status,0) < 20 and pc.assuretype='一单一议' and agent_id='"+khbh+"' and pc.proj_id<>'"+tmpProjectNo+"'";
//			l = personalProjectDao.doQueryBySql(sql);
//			if(l.size()>0)
//			{
//				Map m  = (Map)l.get(0);
//				variablesMap.putAll(m);
//			}
//			/*
//			doc_current.AgentCreditA=AgentCreditA
//			doc_current.AgentCreditSustain=AgentCreditSustain
//			doc_current.AgentCreditOnetoone=AgentCreditOnetoone
//			doc_current.sumrentD=sumrentD
//			doc_current.sumrentD_s=sumrentD_s
//			doc_current.sumrentD_o=sumrentD_o
//			doc_current.dblevel=dblevel
//			doc_current.zzs=zzs
//			doc_current.leasingAllMoney=leasingAllMoney
//			doc_current.leasingAllMoney=leasingAllMoney
//		    doc_current.leasingAllMoney_s=leasingAllMoney_s
//		    doc_current.leasingAllMoney_o=leasingAllMoney_o
//			*/
//			
//			// "-------标准额度"+AgentCreditA
//			// "-------租金余额"+sumrentD
//			// "-------支持额度"+AgentCreditSustain
//			// "-------租金余额"+sumrentD_s
//			// "-------一单一议"+AgentCreditOnetoone
//			// "-------租金余额"+sumrentD_o
//			// "-------评信级别"+dblevel
//			// "-------厂商"+zzs
//			// "-------流程中的项目融资总额_标准"+leasingAllMoney
//			// "-------流程中的项目融资总额_支持"+leasingAllMoney_s
//			// "-------流程中的项目融资总额_一单一议"+leasingAllMoney_o
//			
//	   }
//}
