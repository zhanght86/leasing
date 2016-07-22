package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.rzzlAPI.CarRegistAPI;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.vehicleAlarm.service.VehicleAlarmService;

/**
 * 车辆报警关闭申请
 * @author wanghl
 * @datetime 2015年6月3日,上午10:24:29
 */
public class VehicleAlarmStatus {

	/**
	 * 审批通过状态
	 * @param ID
	 * @author wanghl
	 * @datetime 2015年6月3日,上午10:23:27
	 */
	public void vehicleAlarmPass(String ID){
		if(StringUtils.isNotBlank(ID)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ID", ID);

			VehicleAlarmService service = new VehicleAlarmService();
			Map<String, Object> alarmMap = service.selectAlarmById(param);
			String ALARM_ID = alarmMap.get("ALARM_ID").toString();
			if(CarRegistAPI.alarmClose(ALARM_ID)){
				param.put("STATUS", 2);
				Dao.update("bpm.vehicleAlarm.updateStatus",param);
			}else{
				throw new ActionException("通过状态变更失败");
			}
		}else{
			throw new ActionException("通过状态变更失败");
		}
	}
	
	/**
	 * 审批不通过
	 * @param ID
	 * @author wanghl
	 * @datetime 2015年6月3日,上午10:27:20
	 */
	public void vehicleAlarmNoPass(String ID){
		if(StringUtils.isNotBlank(ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ID", ID);
			param.put("STATUS",3);
			Dao.update("bpm.vehicleAlarm.updateStatus",param);
		}else{
			throw new ActionException("不通过状态变更失败");
		}
	}
}
