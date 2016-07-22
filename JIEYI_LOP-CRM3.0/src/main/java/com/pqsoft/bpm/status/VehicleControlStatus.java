package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.rzzlAPI.CarRegistAPI;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.vehicleControl.service.VehicleControlService;

/**
 * 车辆远程控制
 * @author wanghl
 * @datetime 2015年6月4日,下午1:59:49
 */
public class VehicleControlStatus {

	/**
	 * 通过
	 * @param EQ_ID
	 * @author wanghl
	 * @datetime 2015年6月4日,下午2:00:57
	 */
	public void vehicleControlPass(String EQ_ID){
		if(StringUtils.isNotBlank(EQ_ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ID", EQ_ID);
			
			VehicleControlService service = new VehicleControlService();
			Map<String, Object> eqMap = service.selectEquipmentById(param);
			//控制类型 1锁车 2解除锁车
			String CTRLTYPE = (String) eqMap.get("CTRLTYPE");
			//控制结果 0成功 1失败
			String OPERATERESULT = (String) eqMap.get("OPERATERESULT");
			
			if("1".equals(CTRLTYPE)){
				//解锁
				param.put("EQ_ID", EQ_ID);
				param.put("CTRLTYPE", "2");
				param.put("CREATE_NAME", Security.getUser().getName());
				service.addControl(param);
				String ctrl_id = param.get("ctrl_id").toString();
				if(CarRegistAPI.vehCtrl(EQ_ID, "2", ctrl_id)){
					service.updateCtrlType(param);
				}
			}else{
				//锁车操作
				param.put("EQ_ID", EQ_ID);
				param.put("CTRLTYPE", "1");
				param.put("CREATE_NAME", Security.getUser().getName());
				service.addControl(param);
				String ctrl_id = param.get("ctrl_id").toString();
				if(CarRegistAPI.vehCtrl(EQ_ID, "1", ctrl_id)){
					service.updateCtrlType(param);
				}
			}
			param.put("CONTROL_STATUS", 2);
			Dao.update("bpm.vehicleControl.updateStatus",param);
		}else{
			throw new ActionException("通过状态变更失败");
		}
	}
	
	/**
	 * 不通过
	 * @param EQ_ID
	 * @author wanghl
	 * @datetime 2015年6月4日,下午3:31:42
	 */
	public void vehicleControlNoPass(String EQ_ID){
		if(StringUtils.isNotBlank(EQ_ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("EQ_ID", EQ_ID);
			param.put("CONTROL_STATUS", 3);
			Dao.update("bpm.vehicleControl.updateStatus",param);
		}else{
			throw new ActionException("不通过状态变更失败");
		}
	}
}
