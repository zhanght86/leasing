//package com.kernal.jbpm.action.Equipment_repurchase;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.utils.StringUtil;
//public class ApplyEndAction extends JbpmBaseAction{
//	 /**
//    * 写入业务逻辑操作(startAction和endAction均触发)
//   * @param variablesMap
//   */
//	public void execute(HttpServletRequest request,Map<String,Object> variablesMap,JdbcBaseDao conn) throws Exception
//	    {
//		String proj_id=String.valueOf(variablesMap.get("ProjectNo"));
//		String son_paying_table = StringUtil.empty2Other(StringUtil.nullToString(variablesMap.get("son_paying_table")), "0");
//    	String DepositDate_hg = String.valueOf(variablesMap.get("DepositDate_hg"));
//		
//		
//		String sql = "";
//		String proj_togo =  String.valueOf(request.getParameter("proj_togo"));
//		sql = "update fund_rent_plan set item_method='"+proj_togo+"中' where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' ";
//		conn.doUpdateBySql(sql);
//		//为避免网银扣划和设备回购重复扣划，特将租金扣划表数据删除
//		sql ="delete from t_rent_hire where  proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' ";
//		conn.doDeleteBySql(sql);
//		//将未还的租金标识为设备回购
//		sql = "update fund_rent_plan set hg_remark='"+proj_togo+"',hg_date='"+DepositDate_hg+"' where proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"' and plan_status<>'1'";
//		conn.doUpdateBySql(sql);
//		//将未还的违约金标识为设备回购
//		sql = "update fund_rent_plan set penalty_remark='"+proj_togo+"' where nvl(penalty,0)>0 and proj_id='"+proj_id+"' and son_paying_table='"+son_paying_table+"'  and not exists ";
//		sql += " (select id from fund_rent_income where rent_type='违约金' and proj_id = fund_rent_plan.proj_Id and plan_list=fund_rent_plan.rent_list and son_paying_table=fund_rent_plan.son_paying_table)";
//		conn.doUpdateBySql(sql);
//		
//	   }
//	
//}
