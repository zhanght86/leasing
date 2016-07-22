package com.pqsoft.zcfl.service;


public class ZCFL {

	/**
	 * 直接通过
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void simpleSuccess(String id) {
		new AssetsService().addZcflResultSuccess(id);// 通过
	}

	/**
	 * 直接不通过
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void simpleFail(String id) {
		new AssetsService().addZcflResultFailure(id);// 不通过
	}

	/**
	 * 非直接通过
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void complexSuccess(String id) {
		new AssetsService().addZcflResultSuccess(id);// 通过
	}

	/**
	 * 非直接不通过
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void complexFail(String id) {
		new AssetsService().addZcflResultFailure(id);// 不通过
	}

	// public void activity(String ASSETS_ID) {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("ASSETS_ID", ASSETS_ID);
	// param.put("STATUS", "activity");
	// new AssetsService().update(param);
	// }
	//
	// public void endCancel(String ASSETS_ID) {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("ASSETS_ID", ASSETS_ID);
	// param.put("STATUS", "new");
	// new AssetsService().update(param);
	// }
	//
	// public void endNormal(String ASSETS_ID) {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("ASSETS_ID", ASSETS_ID);
	// param.put("ASSETS_RESULT", "损失级");
	// param.put("STATUS", "endNormal");
	// new AssetsService().update(param);//同时获取到设备ID
	//
	// ClassificationService ClassificationService = new
	// ClassificationService();
	// ClassificationService.endEquipment(param.get("EQUIPMENT_ID").toString(),"损失级");
	// }

}
