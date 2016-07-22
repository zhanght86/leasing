//package com.pqsoft.anchored.action;
//
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.anchored.service.AnchoredManagerService;
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.exception.ActionException;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
///**
// * 挂靠公司维护
// * 
// * @author King 2013-11-10
// */
//public class AnchoredManagerAction extends Action {
//	private String path = "anchored/";
//	private AnchoredManagerService service = new AnchoredManagerService();
//
//	@Override
//	public Reply execute() {
//		return null;
//	}
//
//	/**
//	 * 进入挂靠公司管理页
//	 * 
//	 * @return
//	 * @author:King 2013-11-10
//	 */
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "挂靠公司管理", "列表" })
//	public Reply toMgAnchored() {
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		// Map<String,Object> sup=null==BaseUtil.getSup()?new
//		// HashMap<String,Object>():BaseUtil.getSup();
//		// param.putAll(sup);
//		context.put("param", param);
//		// Map<String,Object> map=BaseUtil.getSup();
//		// context.put("suplist", new
//		// BaseSchemeService().doShowSupplierInfo(map));
//		context.put("TAXLIST", new DataDictionaryMemcached().get("纳税资质"));
//		return new ReplyHtml(VM.html(path + "anchoredMg.vm", context));
//	}
//
//	/**
//	 * 查询挂靠公司
//	 * 
//	 * @return
//	 * @author:King 2013-11-26
//	 */
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply doShowAnchored() {
//		Map<String, Object> param = _getParameters();
//		param.putAll(JSONObject.fromObject(param.get("param")));
//		return new ReplyAjaxPage(service.doShowAnchoredList(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "挂靠公司管理", "添加" })
//	public Reply doAddAnchored() {
//		Map<String, Object> param = _getParameters();
//		int count = service.doShowAnchoredByName(param.get("NAME") + "", param.get("SUP_ID") + "");
//		if (count > 0) throw new ActionException("该挂靠公司已经存在");
//		int i = service.doAddAnchored(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("挂靠公司管理", "添加", param + ""));
//		} else {
//			throw new ActionException("添加失败");
//		}
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "挂靠公司管理", "修改" })
//	public Reply doUpdateAnchored() {
//		Map<String, Object> param = _getParameters();
//		int i = service.doUpdateAnchored(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("挂靠公司管理", "修改", param + ""));
//		} else {
//			throw new ActionException("修改失败");
//		}
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "挂靠公司管理", "删除" })
//	public Reply doDeleteAnchored() {
//		Map<String, Object> param = _getParameters();
//		int count = service.isDelAnchoredCheck(param.get("ID") + "");
//		if (count > 0) { throw new ActionException("该挂靠公司已被项目使用，不能删除"); }
//		int i = service.doDeleteAncored(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("挂靠公司管理", "删除", param + ""));
//		} else {
//			throw new ActionException("删除失败");
//		}
//	}
//
//	/**
//	 * 进入挂靠公司管理页
//	 * 
//	 * @return
//	 * @author:King 2013-11-10
//	 */
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "联合租赁融资公司", "列表" })
//	public Reply toMgFL_Company() {
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		context.put("TAXLIST", new DataDictionaryMemcached().get("纳税资质"));
//		return new ReplyHtml(VM.html(path + "fl_ComanyMg.vm", context));
//	}
//
//	/**
//	 * 查询挂靠公司
//	 * 
//	 * @return
//	 * @author:King 2013-11-26
//	 */
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply doShowFl_Company() {
//		Map<String, Object> param = _getParameters();
//		param.putAll(JSONObject.fromObject(param.get("param")));
//		return new ReplyAjaxPage(service.doShowFLList(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "联合租赁融资公司", "添加" })
//	public Reply doAddPL() {
//		Map<String, Object> param = _getParameters();
//		int count = service.doShowPLByName(param.get("NAME") + "");
//		if (count > 0) throw new ActionException("该联合租赁融资公司已经存在");
//		int i = service.doAddPL(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("联合租赁融资公司管理", "添加", param + ""));
//		} else {
//			throw new ActionException("添加失败");
//		}
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "联合租赁融资公司", "修改" })
//	public Reply doUpdateFL() {
//		Map<String, Object> param = _getParameters();
//		int i = service.doUpdatePL(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("联合租赁融资公司管理", "修改", param + ""));
//		} else {
//			throw new ActionException("修改失败");
//		}
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "参数配置", "联合租赁融资公司", "删除" })
//	public Reply doDeletePL() {
//		Map<String, Object> param = _getParameters();
//		int count = service.isDelPLCheck(param.get("ID") + "");
//		if (count > 0) { throw new ActionException("该联合租赁融资公司已被项目使用，不能删除"); }
//		int i = service.doDeletePL(param);
//		if (i > 0) {
//			return new ReplyAjax().addOp(new OpLog("联合租赁融资公司管理", "删除", param + ""));
//		} else {
//			throw new ActionException("删除失败");
//		}
//	}
//}
