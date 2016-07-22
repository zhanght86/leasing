package com.pqsoft.rzzlAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.osi.restfulApi.ApiRestFulClient;

/**
 * Hello world!
 *
 */
public class CarRegistAPI {
	/**
	 * 生成合同时的车辆vin验证
	 * @param certificate_id 合同号
	 * @param equipid	车id
	 * @author : LUYANFENG @ 2015年5月25日
	 * @param payCode 还款计划标识
	 */
	public static boolean sendCarInfoMonitor(String certificate_id, String payCode){
		String apiHost = checkHost();
		if(StringUtils.isBlank(apiHost)){
			Log.debug("监控车辆API-IP - 注册接口host 为空");
			return false;
		}
		if( StringUtils.isBlank(certificate_id)){
			return false;
		}
		/*
		 * 	车辆标识	vehEquipmentId 必填
			车架号VIN码	vin  必填
			车牌号	plateNum
			发动机号	engNum
			车型	vehType
			车系	vehModel
			承租人标识	clientCode 必填
			合同标识	contractCode 必填
			还款计划标识	payCode
			经销商/代理商标识	supplierCode
			添加时间	createTime 必填
			操作人标识	operatorId 必填
		 */
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("certificate_id", certificate_id);
			Map<String, Object> equipInfo = Dao.selectOne("buyCertificate.selectEquipInfo", params);
			
			params.clear();
			params.put("vehEquipmentId", equipInfo.get("CERTIFICATE_ID"));	
			params.put("vin", equipInfo.get("CAR_SYMBOL").toString());
			params.put("plateNum", "");
			params.put("engNum", equipInfo.get("ENGINE_CODE") );
			params.put("vehType", equipInfo.get("TYPE_NAME"));
			params.put("vehModel", equipInfo.get("CATENA_NAME") );
			params.put("clientCode", equipInfo.get("CLIENT_ID").toString() );
			params.put("clientName", equipInfo.get("CLIENT_NAME").toString() );
			params.put("contractCode", certificate_id );
			params.put("payCode", payCode );
			params.put("supplierCode",equipInfo.get("SUPPLIERS_NAME").toString() );
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			params.put("createTime",  sdf.format(new Date()));
			params.put("operatorId", equipInfo.get("CLERK_ID").toString());
			
			String apiURL = String.format("http://%1$s/rzzl/ws/0.1/api/vehicle/regist", apiHost);
			JSONObject result = ApiRestFulClient.sendToRestFul( apiURL, params);
			if("1".equals(result.get("returnCode"))){
				return true;
			}else{
				throw new ActionException(result.get("returnMessage").toString().replaceAll("\\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}

	private static String checkHost() {
		ArrayList<?> list = SysDictionaryMemcached.getList("监控车辆API-IP");
		if(list != null){
			for(Object o : list){
				Map<String,Object> m = (Map<String,Object>)o;
				Object CODE = m.get("CODE");
				if("注册接口".equals(CODE)){
					return (String) m.get("FLAG");
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 修改车辆信息vin接口
	 * @param certificate_id
	 * @param payCode
	 * @param equipid
	 * @return
	 * @author : LUYANFENG @ 2015年5月25日
	 */
	public static boolean modifyCarInfoMonitor(String certificate_id, String payCode){
		String apiHost = checkHost();
		if(StringUtils.isBlank(apiHost)){
			Log.debug("监控车辆API-IP - 注册接口host 为空");
			return false;
		}
		if(StringUtils.isBlank(certificate_id)){
			return false;
		}
		
		/*
		 * 	车辆标识	vehEquipmentId 必填
			车架号VIN码	vin  必填
			车牌号	plateNum
			发动机号	engNum
			车型	vehType
			车系	vehModel
			承租人标识	clientCode 必填
			承租人名称	clientName 必填
			合同标识	contractCode 必填
			还款计划标识	payCode
			经销商/代理商标识	supplierCode
			添加时间	createTime 必填
			操作人标识	operatorId 必填
		 */
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("certificate_id", certificate_id);
			Map<String, Object> equipInfo = Dao.selectOne("buyCertificate.selectEquipInfo", params);
			
			params.clear();
			params.put("vehEquipmentId", equipInfo.get("ID"));	
			params.put("vin", equipInfo.get("CAR_SYMBOL").toString());
			params.put("plateNum", "");
			params.put("engNum", equipInfo.get("ENGINE_CODE") );
			params.put("vehType", equipInfo.get("TYPE_NAME"));
			params.put("vehModel", equipInfo.get("CATENA_NAME") );
			params.put("clientCode", equipInfo.get("CLIENT_ID").toString() );
			params.put("clientName", equipInfo.get("CLIENT_NAME").toString() );
			params.put("contractCode", certificate_id );
			params.put("payCode", payCode );
			params.put("supplierCode",equipInfo.get("SUPPLIERS_NAME").toString() );
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			params.put("createTime",  sdf.format(new Date()));
			params.put("operatorId", equipInfo.get("CLERK_ID").toString());
			
			String apiURL = String.format("http://%1$s/rzzl/ws/0.1/api/vehicle/modify", apiHost);
			JSONObject result = ApiRestFulClient.sendToRestFul( apiURL, params);
			if("1".equals(result.get("returnCode"))){
				return true;
			}else{
				throw new ActionException(result.get("returnMessage").toString().replaceAll("\\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}
	
	/**
	 * 注销接口
	 * @param certificate_id
	 * @param payCode
	 * @return
	 * @author wanghl
	 * @datetime 2015年5月29日,下午3:37:12
	 */
	public static boolean vehDel(String PAY_ID){
		String apiHost = checkHost();
		if(StringUtils.isBlank(apiHost)){
			Log.debug("监控车辆API-IP - 注销接口host 为空");
			return false;
		}
		/*
		 * 	车辆标识	vehEquipmentId 必填
			车架号VIN码	vin  必填
			承租人标识	clientCode 必填
			合同标识	contractCode 必填
			还款计划标识	payCode
			经销商/代理商标识	supplierCode
			注销时间	createTime 必填
			操作人标识	operatorId 必填
		 */
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("PAR_ID", PAY_ID);
//			params.put("certificate_id", certificate_id);
			List<Map<String,Object>> selectList = Dao.selectList("buyCertificate.selectEquipInfo",params);
			Map<String, Object> equipInfo = selectList.get(0);
			
			params.clear();
			//车辆标识	vehEquipmentId 必填
			params.put("vehEquipmentId", equipInfo.get("ID"));	
			//车架号VIN码	vin  必填
			params.put("vin", equipInfo.get("CAR_SYMBOL"));
//			params.put("vin", "LSVAA49J132047371");
			//承租人标识	clientCode 必填
			params.put("clientCode", equipInfo.get("CLIENT_ID"));
			//合同标识	contractCode 必填
			params.put("contractCode", equipInfo.get("LEASE_CODE"));
//			params.put("contractCode", "0201505220001");
			//还款计划标识	payCode
			params.put("payCode", equipInfo.get("PAYLIST_CODE") );
//			params.put("payCode", "0201505220001-1");
			//经销商/代理商标识	supplierCode
			params.put("supplierCode",equipInfo.get("SUPPLIERS_ID") );
			//注销时间	createTime 必填
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			params.put("createTime",  sdf.format(new Date()));
			//操作人标识	operatorId 必填
			params.put("operatorId", equipInfo.get("CLERK_ID").toString());
			
			String apiURL = String.format("http://%1$s/rzzl/ws/0.1/api/vehicle/cancel", apiHost);
			JSONObject result = ApiRestFulClient.sendToRestFul( apiURL, params);
			System.out.println("result:"+result);
			if("1".equals(result.get("returnCode"))){
				return true;
			}else{
				throw new ActionException(result.get("returnMessage").toString().replaceAll("\\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}
	
	/**
	 * 单车远程控制接口
	 * @param EQ_ID 设备表id
	 * @param CTRLTYPE 控制类型
	 * @param CTRL_ID 控制表id
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月1日,下午1:31:25
	 */
	public static boolean vehCtrl(String EQ_ID,String CTRLTYPE,String CTRL_ID){
		String apiHost = checkHost();
		if(StringUtils.isBlank(apiHost)){
			Log.debug("监控车辆API-IP - 单车远程控制接口host 为空");
			return false;
		}
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Map<String,Object>> selectList = Dao.selectList("buyCertificate.selectEquipInfo");
			Map<String, Object> equipInfo = selectList.get(0);
			/*
			 * 车辆标识 vehEquipmentId 必填
			 * 控制类型 ctrlType 必填     1–锁车；2–解除锁车
			 * 远程控制申请标识 ctrlApplyId 必填
			 */
			params.put("vehEquipmentId", EQ_ID);	
			params.put("ctrlType", CTRLTYPE);
			params.put("ctrlApplyId", CTRL_ID);
			
			String apiURL = String.format("http://%1$s/rzzl/ws/0.1/api/vehicle/control", apiHost);
			JSONObject result = ApiRestFulClient.sendToRestFul( apiURL, params);
			System.out.println("result:"+result);
			if("1".equals(result.get("returnCode"))){
				return true;
			}else{
				throw new ActionException(result.get("returnMessage").toString().replaceAll("\\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}
	
	/**
	 * 报警关闭接口
	 * @return
	 * @author wanghl
	 * @datetime 2015年6月1日,下午3:23:43
	 */
	public static boolean alarmClose(String alarmId){
		String apiHost = checkHost();
		if(StringUtils.isBlank(apiHost)){
			Log.debug("监控车辆API-IP - 报警关闭接口host 为空");
			return false;
		}
		if(StringUtils.isBlank(alarmId)){
			return false;
		}
		try {
			Map<String,Object> params = new HashMap<String, Object>();
//			List<Map<String,Object>> selectList = Dao.selectList("buyCertificate.selectEquipInfo");
//			Map<String, Object> equipInfo = selectList.get(0);
			
			//报警顺序号 必填
			params.put("alarmId", alarmId);
			
			String apiURL = String.format("http://%1$s/rzzl/ws/0.1/api/alarm/close", apiHost);
			JSONObject result = ApiRestFulClient.sendToRestFul( apiURL, params);
			System.out.println("result:"+result);
			if("1".equals(result.get("returnCode"))){
				return true;
			}else{
				throw new ActionException(result.get("returnMessage").toString().replaceAll("\\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}
	
}
