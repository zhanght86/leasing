package com.pqsoft.quartzjobs.jobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;

public class approvalTotalReport extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("TYPE", "1");
		String SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm ,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=20 and TRUNC(op_time)=TRUNC(SYSDATE)";
		map.put("SQL", SQL);
		int num=Dao.selectInt("jobs.getNumber", map);
		map.put("THROUGH", num);
		SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=27 and TRUNC(op_time)=TRUNC(SYSDATE)";
		map.put("SQL", SQL);
		num=Dao.selectInt("jobs.getNumber", map);
		map.put("NOTBY", num);
		SQL="select count(*) from jbpm_memo jm where task_name='初次审核' and op_type like '%驳回%' and TRUNC(op_time)=TRUNC(SYSDATE)";
		map.put("SQL", SQL);
		num=Dao.selectInt("jobs.getNumber", map);
		map.put("REJECT", num);
		SQL="select count(*) from jbpm4_task where name_ in（'初次审核','最终审核') and TRUNC(CREATE_)=TRUNC(SYSDATE)";
		map.put("SQL", SQL);
		num=Dao.selectInt("jobs.getNumber", map);
		map.put("UNTREATED", num);
		SQL="select count(*) from jbpm_memo where task_name ='初次审核' and  TRUNC(op_time)=TRUNC(SYSDATE)";
		map.put("SQL", SQL);
		num=Dao.selectInt("jobs.getNumber", map);
		map.put("TOTAL", num);
		Dao.insert("jobs.insApprovalTotalReport",map);
		Date bdate = new Date();
		System.out.println(bdate+"======================");
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(bdate);
	    //周
	    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
	    {
	    	map.put("TYPE", "2");
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=20 and TRUNC(op_time)>=trunc(next_day(sysdate-8,1)+1) and TRUNC(op_time)<=trunc(next_day(sysdate-8,1)+7)+1";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("THROUGH", num);
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=27 and TRUNC(op_time)>=trunc(next_day(sysdate-8,1)+1) and TRUNC(op_time)<=trunc(next_day(sysdate-8,1)+7)+1";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("NOTBY", num);
	    	SQL="select count(*) from jbpm_memo jm where task_name='初次审核' and op_type like '%驳回%' and TRUNC(op_time)>=trunc(next_day(sysdate-8,1)+1) and TRUNC(op_time)<=trunc(next_day(sysdate-8,1)+7)+1";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
	    	map.put("REJECT", num);
	    	SQL="select count(*) from jbpm4_task where name_ in（'初次审核','最终审核') and TRUNC(CREATE_)>=trunc(next_day(sysdate-8,1)+1) and TRUNC(CREATE_)<=trunc(next_day(sysdate-8,1)+7)+1";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("UNTREATED", num);
			SQL="select count(*) from jbpm_memo where task_name ='初次审核' and  TRUNC(op_time)>=trunc(next_day(sysdate-8,1)+1)";
			map.put("SQL", SQL);
			num=Dao.selectInt("jobs.getNumber", map);
			map.put("TOTAL", num);
			Dao.insert("jobs.insApprovalTotalReport",map);
	    }
	    //月
	    cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1)); 
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) { 
        	map.put("TYPE", "3");
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=20 and TRUNC(op_time)>=TRUNC(SYSDATE, 'MM')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("THROUGH", num);
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=27 and TRUNC(op_time)>=TRUNC(SYSDATE, 'MM')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("NOTBY", num);
	    	SQL="select count(*) from jbpm_memo jm where task_name='初次审核' and op_type like '%驳回%' and TRUNC(op_time)>=TRUNC(SYSDATE, 'MM')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
	    	map.put("REJECT", num);
	    	SQL="select count(*) from jbpm4_task where name_ in（'初次审核','最终审核') and TRUNC(CREATE_)>=TRUNC(SYSDATE, 'MM')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("UNTREATED", num);
			SQL="select count(*) from jbpm_memo where task_name ='初次审核' and  TRUNC(op_time)>=TRUNC(SYSDATE, 'MM')";
			map.put("SQL", SQL);
			num=Dao.selectInt("jobs.getNumber", map);
			map.put("TOTAL", num);
			Dao.insert("jobs.insApprovalTotalReport",map);
			//统计销售业绩为目的的各个区域销售统计
			List<Map<String,Object>> l1=Dao.selectList("jobs.getReportFormArea");
			Map<String,Object> m1=new HashMap<String,Object>();
			for(int i=0;i<l1.size();i++){
				m1=l1.get(i);
				System.out.println(m1+"============");
				Dao.insert("jobs.insReportFormArea",m1);
			}
			List<Map<String,Object>> l2=Dao.selectList("jobs.getReportFormArea2");
			Map<String,Object> m2=new HashMap<String,Object>();
			for(int i=0;i<l2.size();i++){
				m2=l2.get(i);
				Dao.insert("jobs.insReportFormArea2",m2);
			}
        } 
        //本季度
        int month = getQuarterInMonth(cal.get(Calendar.MONTH), false);  
        System.out.println(month+"月====================="+cal.get(Calendar.MONTH));
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH, 0); 
        System.out.println("当前时间的季度末：" + print(cal.getTime()));  
        if(cal.getTime()==bdate){
        	map.put("TYPE", "4");
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=20 and TRUNC(op_time)>=TRUNC(SYSDATE,'Q')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("THROUGH", num);
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=27 and TRUNC(op_time)>=TRUNC(SYSDATE,'Q')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("NOTBY", num);
	    	SQL="select count(*) from jbpm_memo jm where task_name='初次审核' and op_type like '%驳回%' and TRUNC(op_time)>=TRUNC(SYSDATE,'Q')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
	    	map.put("REJECT", num);
	    	SQL="select count(*) from jbpm4_task where name_ in（'初次审核','最终审核') and TRUNC(CREATE_)>=TRUNC(SYSDATE,'Q')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("UNTREATED", num);
			SQL="select count(*) from jbpm_memo where task_name ='初次审核' and  TRUNC(op_time)>=TRUNC(SYSDATE,'Q')";
			map.put("SQL", SQL);
			num=Dao.selectInt("jobs.getNumber", map);
			map.put("TOTAL", num);
			Dao.insert("jobs.insApprovalTotalReport",map);
        }
        //本年
        int year = cal.get(Calendar.YEAR);  
        cal.clear();  
        cal.set(Calendar.YEAR, year);  
        cal.roll(Calendar.DAY_OF_YEAR, -1);  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        System.out.println(df.format(bdate)+"当前时间的年末：" +df.format(cal.getTime()));
        if(df.format(bdate).equals(df.format(cal.getTime()))){
        	map.put("TYPE", "5");
        	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=20 and TRUNC(op_time)>=trunc(sysdate,'yy')";
        	map.put("SQL", SQL);
        	num=Dao.selectInt("jobs.getNumber", map);
			map.put("THROUGH", num);
	    	SQL="select count(*) from (select jbpm_id,max(op_time) op_time from jbpm_memo group by jbpm_id) jm,fil_project_head fph where jm.jbpm_id=fph.jbpm_id and fph.status=27 and TRUNC(op_time)>=trunc(sysdate,'yy')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("NOTBY", num);
	    	SQL="select count(*) from jbpm_memo jm where task_name='初次审核' and op_type like '%驳回%' and TRUNC(op_time)>=trunc(sysdate,'yy')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
	    	map.put("REJECT", num);
	    	SQL="select count(*) from jbpm4_task where name_ in（'初次审核','最终审核') and TRUNC(CREATE_)>=trunc(sysdate,'yy')";
	    	map.put("SQL", SQL);
	    	num=Dao.selectInt("jobs.getNumber", map);
			map.put("UNTREATED", num);
			SQL="select count(*) from jbpm_memo where task_name ='初次审核' and  TRUNC(op_time)>=trunc(sysdate,'yy')";
			map.put("SQL", SQL);
			num=Dao.selectInt("jobs.getNumber", map);
			map.put("TOTAL", num);
			Dao.insert("jobs.insApprovalTotalReport",map);
        }
	}
	// 返回第几个月份，不是几月  
    // 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月  
	 private static int getQuarterInMonth(int month, boolean isQuarterStart) {  
	        int months[] = { 1, 4, 7, 10 };  
	        if (!isQuarterStart) {  
	            months = new int[] { 3, 6, 9, 12 };  
	        }  
	        if (month >= 1 && month <= 3)  
	            return months[0];  
	        else if (month >= 4 && month <= 6)  
	            return months[1];  
	        else if (month >= 7 && month <= 9)  
	            return months[2];  
	        else  
	            return months[3];  
	    }  

	 private static String print(Date d) {  
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        return df.format(d);  
	    }  
}
