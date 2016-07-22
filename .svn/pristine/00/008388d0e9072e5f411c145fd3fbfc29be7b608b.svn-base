package com.pqsoft.GPS.GPSCommand.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.osi.gpsApi.ApiGPS;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class GPSCommandService {
	private String namespace="GPSCommand.";
	
	/**
	 * 查询GPS命令记录列表
	 * @param map
	 * @return
	 * @author King 2014年11月14日
	 */
	public Page mgGspCommand(Map<String,Object> map){
		return PageUtil.getPage(map, namespace+"GspCommandList", namespace+"GspCommandCount");
	}
	
	/**
	 * 添加GPS命令
	 * @param map
	 * @return
	 * @author King 2014年11月14日
	 */
	public int addGpsCommand(Map<String,Object> map){
		String ID=Dao.getSequence("SEQ_T_SYS_GPS_COMMAND");
		map.put("ID", ID);
		System.out.println("==============="+map);
		/*********************调用GPS接口***********************/
		ApiGPS gps=new ApiGPS();
		gps.setCOMMAND_TYPE(StringUtils.isNotBlank(map.get("COMMAND_TYPE"))?map.get("COMMAND_TYPE").toString():null);
		gps.setGPS_CODE(StringUtils.isNotBlank(map.get("GPS_CODE"))?map.get("GPS_CODE").toString():null);
		gps.setGPS_TYPE(StringUtils.isNotBlank(map.get("GPS_TYPE"))?map.get("GPS_TYPE").toString():null);
		gps.setId(Integer.parseInt(ID));
		gps.setLOCK_LEVEL(StringUtils.isNotBlank(map.get("LOCK_LEVEL"))?map.get("LOCK_LEVEL").toString():null);
		gps.setINTERVAL_TIME(StringUtils.isNotBlank(map.get("INTERVAL_TIME"))?map.get("INTERVAL_TIME").toString():null);
		gps.setGPS_FIRST_DATE(StringUtils.isNotBlank(map.get("GPS_FIRST_DATE"))?map.get("GPS_FIRST_DATE").toString():null);
		gps.setCOMMAND_MODEL(StringUtils.isNotBlank(map.get("COMMAND_MODEL"))?map.get("COMMAND_MODEL").toString():null);
		gps.setWORK_TIME(StringUtils.isNotBlank(map.get("WORK_TIME"))?map.get("WORK_TIME").toString():null);
		System.out.println("getCOMMAND_MODEL="+gps.getCOMMAND_MODEL()+";getCOMMAND_TYPE="+gps.getCOMMAND_TYPE()
				+";getGPS_CODE="+gps.getGPS_CODE()+";getGPS_FIRST_DATE="+gps.getGPS_FIRST_DATE()
				+";getGPS_TYPE="+gps.getGPS_TYPE()+";getId="+gps.getId()
				+";getINTERVAL_TIME="+gps.getINTERVAL_TIME()+";getLOCK_LEVEL="+gps.getLOCK_LEVEL()+";getWORK_TIME="+gps.getWORK_TIME());
		int i=gps.commandSet(gps);
		/********************************************/
		if(i==1)
			return Dao.insert(namespace+"addGpsCommand",map);
		else
			return i;
	}
}
