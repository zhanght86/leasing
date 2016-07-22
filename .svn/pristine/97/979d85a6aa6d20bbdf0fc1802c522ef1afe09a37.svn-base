//package com.kernal.jbpm.action.Equipment_repurchase;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.utils.StringUtil;
//
//public class EquipmentRepurchaseDeleteAction extends JbpmBaseAction {
//	
//	 public String deleteProcessInstance(HttpServletRequest request,Map<String,String> processInsanceDataMap,JdbcBaseDao conn) throws Exception{
//		 String proj_id = StringUtil.nullToString(processInsanceDataMap.get("ProjectNo"));
//		 String son_paying_table = StringUtil.empty2Other(StringUtil.nullToString(processInsanceDataMap.get("son_paying_table")), "0");
//         //System.out.println("删除啊啊啊啊"+son_paying_table); 
//		 String sql ="";
//		
//		 sql = "delete from proj_end_charge where proj_id='"+proj_id+"' and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doDeleteBySql(sql);
//		 sql = "delete from proj_end_tqzz where proj_id='"+proj_id+"'";
//		 conn.doDeleteBySql(sql);
//		 sql = "update fund_rent_plan set item_method=(select item_method from fund_rent_plan_temp where ROWNUM=1 and rent_list=1 and proj_id ='";
//		 sql +=proj_id+"') where proj_id='"+proj_id+"' and rent_list=1 and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//		 sql = "update fund_rent_plan set item_method=(select rent_paytype from proj_info where  ROWNUM=1  and proj_id ='";
//		 sql +=proj_id+"') where proj_id='"+proj_id+"' and rent_list<>1 and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//		 sql = "update fund_rent_plan set hg_remark=null,hg_date=null where proj_id='"+proj_id+"' and (hg_remark='设备回购' or hg_remark = '待回购项目') and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//		 sql = "update fund_rent_plan set penalty_remark=null where nvl(penalty,0)>0 and proj_id='"+proj_id+"' and not exists ";
//		 sql += " (select id from fund_rent_income where rent_type='违约金' and proj_id='"+proj_id+"' and plan_list=fund_rent_plan.rent_list and  son_paying_table ='" +son_paying_table+"') ";
//		 conn.doUpdateBySql(sql);
//		 sql = "update fund_rent_plan set penalty_remark=null where  proj_id='"+proj_id+"' and (penalty_remark='设备回购' or penalty_remark = '待回购项目') and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//		 //顺序不可以调换
//		 sql ="update fund_rent_plan set penalty_plan_date =null ,penalty=null where proj_id = '"+proj_id+"' and plan_status =0 and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//		 //2013-01-07
//		 sql = "update fund_rent_income set remark = null where (remark='设备回购' or remark = '待回购项目') and proj_id='"+proj_id+"'  and son_paying_table ='" +son_paying_table+"'"; 
//		 conn.doUpdateBySql(sql);
//			
//    	return "删除成功";
//	 }
//}
