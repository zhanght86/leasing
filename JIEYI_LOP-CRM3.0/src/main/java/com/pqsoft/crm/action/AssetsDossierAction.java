package com.pqsoft.crm.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.crm.service.AssetsDossierService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.Action404Exception;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * <p>
 * Title: 融资租赁信息系统平台 客户管理 资产档案
 * </p>
 * <p>
 * Description: 资产档案管理
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class AssetsDossierAction extends Action {

	private static AssetsDossierService service = new AssetsDossierService();
	String path = "crm/assetsDossier/";

	@aPermission(name = { "业务管理", "客户管理","查看","资产档案" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assetsDossierMg.vm", context));
	}
	
	@aPermission(name = { "业务管理", "客户管理","修改","资产档案" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply executeUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"assetsDossierMg.vm", context));
	}

    /**        ---------------------------------------------设备档案---------------------          **/
	/**
	 * 设备档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgEquipment
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-设备档案","列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgEquipment() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map=Dao.selectOne("customers.findCustDetail",param);
		param.put("NAME", map.get("NAME"));
		String TYPE = "设备类型";
		List<Object> list = (List)new DataDictionaryMemcached().get(TYPE);
		context.put("equipmentTypeList",list);
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"equipmentMg.vm", context));
	}

	/**
	 * 设备档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectEquipment
	 * @return 设备档案Page
	 * @author: 吴剑东
	 * @date: 2013-8-28 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-设备档案","数据" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectEquipment() {
		Map<String, Object> param = _getParameters();
		Page page = service.getEquipmentListPage(param);
		return new ReplyAjaxPage(page);
	}


	/**
	 *设备档案保存
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doSaveEquipment
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:03:56
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-设备档案","保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSaveEquipment() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveEquipment(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 *设备档案删除
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doDeleteEquipment
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-设备档案", "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteEquipment() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteEquipment(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			throw new ActionException("删除失败");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-设备档案", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateEquipment() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			System.out.println("wjd---"+param);
			result = service.updateEquipment(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}
	

    /**        ---------------------------------------------房产档案---------------------          **/
	
	
	/**
	 * 房产档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgHouse
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-8-29 下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-房产档案","列表" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgHouse() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String, Object> map=Dao.selectOne("customers.findCustDetail",param);
		param.put("NAME", map.get("NAME"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"houseMg.vm", context));
	}

	/**
	 * 房产档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectHouse
	 * @return 设备档案Page
	 * @author: 吴剑东
	 * @date: 2013-8-28 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-房产档案" ,"数据"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectHouse() {
		Map<String, Object> param = _getParameters();
		Page page = service.getHouseListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 *房产档案保存
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doSaveHouse
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-房产档案","保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSaveHouse() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveHouse(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 *房产档案删除
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doDeleteHouse
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-8-30 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-房产档案", "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteHouse() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteHouse(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			throw new ActionException("删除房产档案操作失败");
		}
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-房产档案", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateHouse() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateHouse(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}
	
	
/**        ---------------------------------------------土地档案---------------------          **/
	
	
	/**
	 * 土地档案页面
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.toMgEstate
	 * @return
	 * 
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午12:10:38
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-土地档案" ,"列表"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgEstate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"estateMg.vm", context));
	}

	/**
	 * 土地档案列表
	 * 
	 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectEstate
	 * @return 设备档案Page
	 * @author: 吴剑东
	 * @date: 2013-9-02 下午05:58:35
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-土地档案" ,"数据"})
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectEstate() {
		Map<String, Object> param = _getParameters();
		Page page = service.getEstateListPage(param);
		return new ReplyAjaxPage(page);
	}

	/**
	 *土地档案保存
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doSaveEstate
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-9-02 下午04:04:19
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-土地档案","保存" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSaveEstate() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.saveEstate(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	/**
	 *土地档案删除
	 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doDeleteEstate
	 * @return
	 *
	 * @author:  吴剑东
	 * @date:    2013-9-02 下午04:04:19
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-土地档案", "删除" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doDeleteEstate() {
		try {

			Map<String, Object> param = _getParameters();
			int count = (Integer) service.doDeleteEstate(param);
			boolean flag = false;
			if (count > 0) {
				flag = true;
			}
			return new ReplyAjax(flag, null);

		} catch (Exception e) {
			throw new ActionException("删除失败");
		}
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "资产档案-土地档案", "修改" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doUpdateEstate() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = 0;
		try {
			result = service.updateEstate(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			flag = true;
			msg = "修改成功";
		} else {
			flag = false;
			msg = "修改失败";
		}
		return new ReplyAjax(flag, msg);
	}
	

	/**        ---------------------------------------------其他资产档案---------------------          **/
		
		
		/**
		 * 其他资产档案页面
		 * 
		 * @Function: com.pqsoft.crm.action.CustomerAction.toMgOther
		 * @return
		 * 
		 * @author: 吴剑东
		 * @date: 2013-9-02 下午12:10:38
		 */
		@SuppressWarnings("unchecked")
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理", "资产档案-其他资产档案" ,"列表"})
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply toMgOther() {

			Map<String, Object> param = _getParameters();
			VelocityContext context = new VelocityContext();
			String TYPE = "其他资产类型";
			List<Object> list = (List)new DataDictionaryMemcached().get(TYPE);
			context.put("otherAssetsTypeList",list);
			context.put("param", param);
			return new ReplyHtml(VM.html(path+"otherMg.vm", context));
		}

		/**
		 * 其他资产档案列表
		 * 
		 * @Function: com.pqsoft.crm.action.CustomerAction.doSelectOther
		 * @return 设备档案Page
		 * @author: 吴剑东
		 * @date: 2013-9-02 下午05:58:35
		 */
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理", "资产档案-其他资产档案","数据" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply doSelectOther() {
			Map<String, Object> param = _getParameters();
			Page page = service.getOtherListPage(param);
			return new ReplyAjaxPage(page);
		}

		/**
		 *其他资产档案保存
		 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doSaveOther
		 * @return
		 *
		 * @author:  吴剑东
		 * @date:    2013-9-02 下午04:04:19
		 */
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理", "资产档案-其他资产档案","保存" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply doSaveOther() {
			Map<String, Object> param = _getParameters();
			Boolean flag = true;
			String msg = "";
			int result = 0;
			try {
				result = service.saveOther(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result > 0) {
				flag = true;
			} else {
				flag = false;
			}
			return new ReplyAjax(flag, msg);
		}

		/**
		 *其他资产档案删除
		 * @Function: com.pqsoft.crm.action.AssetsDossierAction.doDeleteOther
		 * @return
		 *
		 * @author:  吴剑东
		 * @date:    2013-9-02 下午04:04:19
		 */
//		@aPermission(name = { "客户管理", "客户资料管理", "资产档案-其他资产档案", "删除" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		@aAuth(type = aAuthType.LOGIN)
		public Reply doDeleteOther() {
			try {
				Map<String, Object> param = _getParameters();
				int count = (Integer) service.doDeleteOther(param);
				boolean flag = false;
				if (count > 0) {
					flag = true;
				}
				return new ReplyAjax(flag, null);

			} catch (Exception e) {
				throw new Action404Exception(e);
			}
		}
		@aAuth(type = aAuth.aAuthType.LOGIN)
//		@aPermission(name = { "客户管理", "客户资料管理", "资产档案-其他资产档案", "修改" })
		@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
		public Reply doUpdateOther() {
			Map<String, Object> param = _getParameters();
			Boolean flag = true;
			String msg = "";
			int result = 0;
			try {
				result = service.updateOther(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result > 0) {
				flag = true;
				msg = "修改成功";
			} else {
				flag = false;
				msg = "修改失败";
			}
			return new ReplyAjax(flag, msg);
		}
}
