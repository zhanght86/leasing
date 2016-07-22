package com.pqsoft.complement.service;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.skyeye.api.Dao;

public class ComplementJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			ComplementService service=new ComplementService();
			List list=Dao.selectList("complement.queryProj_All1");
			for(int i=0;i<list.size();i++)
			{
				Map map=(Map)list.get(i);
				if(map!=null && map.get("PROJ_ID")!=null && !map.get("PROJ_ID").equals(""))
				{
//					service.sendDB(map.get("PRO_CODE").toString(),map.get("PROJ_ID").toString());
					//将此项目置为已到期未补齐
					Dao.update("complement.updateStatusByComp",map);
				}
			}
			
			// TODO
			Dao.commit();
		} catch (Exception e) {
			Dao.rollback();
		}
	}
	
	
	//自动转保证金
	public void aa(){
		ComplementService service=new ComplementService();
		List list=Dao.selectList("complement.queryProj_All");
		for(int i=0;i<list.size();i++)
		{
			Map map=(Map)list.get(i);
			if(map!=null && map.get("PROJ_ID")!=null && !map.get("PROJ_ID").equals(""))
			{
				service.sendDB(map.get("PRO_CODE").toString(),map.get("PROJ_ID").toString());
			}
		}
	}

}
