package com.pqsoft.crm.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.crm.service.TrackService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class TrackAction extends Action {

	/**
	 * 查看所有跟踪信息
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a>
	 */
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "风险管理", "跟踪管理","列表" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/track.vm", context));// 通过velocity生成返回结果
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "风险管理", "跟踪管理","列表" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new TrackService().getPage(param));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "风险管理", "跟踪管理", "消除" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply eliminate() {
		Map<String, Object> param = _getParameters();
		param.put("STATUS", "ELIMINATE");
		new TrackService().upTrack(param);
		return new ReplyAjax();
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "跟踪配置","列表显示" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply config() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("list", new TrackService().getList());
		context.put("TYPES", DataDictionaryMemcached.getList("跟踪类型"));
		context.put("STARTDATETYPE", DataDictionaryMemcached.getList("跟踪发起时间"));
		return new ReplyHtml(VM.html("crm/track-config.vm", context));// 通过velocity生成返回结果
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "跟踪配置","列表显示" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply setConfig() {
		Map<String, Object> param = _getParameters();
		TrackService service = new TrackService();
		try {
			if (StringUtils.isEmpty((String) param.get("ID"))) {
				service.addConfig(param);
			} else {
				service.upConfig(param);
			}
			return new ReplyAjax(param.get("ID"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyAjax(false, e.getMessage());
		}
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "跟踪配置","删除" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply del() {
		TrackService service = new TrackService();
		service.del(SkyEye.getRequest().getParameter("id"));
		return new ReplyAjax();
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "跟踪配置","列表显示" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply get() {
		TrackService service = new TrackService();
		JSONObject json = JSONObject.fromObject(service.get(SkyEye.getRequest().getParameter("id")));
		return new ReplyAjax(json);
	}

	// public String add() throws Exception {
	// VelocityContext context = new VelocityContext();
	// context.put("TYPES", TrackService.getTypeList());
	// return mSGA.mergeVelocity("track/add.vm", context);
	// }
	//
	// /**
	// * 添加新跟踪操作
	// *
	// * @return
	// * @throws IOException
	// * @author <a href="mailto:lichaohn@163.com">lichao</a>
	// */
	// @aOperation(name = "[操作]添加新跟踪操作")
	// public JSONObject doAdd() throws IOException {
	// JSONObject re = new JSONObject();
	// Map<String, Object> param = BaseUtil.getParameters();
	// // 当前登录用户
	// param.put("CREATE_ID", USERID);
	// boolean flag = service.doAdd(param);
	// re.put("flag", flag);
	// re.put("id", param.get("ID"));
	// // 添加完成，请求重定向到查看详细页面
	// return re;
	// }
	//
	// /**
	// * 查看跟踪详细信息
	// *
	// * @return
	// * @throws Exception
	// * @author <a href="mailto:lichaohn@163.com">lichao</a>
	// */
	// @aOperation(name = "[页面]查看跟踪详细信息")
	// public String show() throws Exception {
	// VelocityContext context = new VelocityContext();
	// Map<String, Object> data =
	// service.getInfoById(request.getParameter("ID"));
	// Clob clob = (Clob) data.get("MEMO");
	// String MEMO = clob != null ? clob.getSubString(1, (int) clob.length()) :
	// null;
	// data.put("MEMO", MEMO);
	// context.put("data", data);
	// return mSGA.mergeVelocity("track/show.vm", context);
	// }
	//
	// /**
	// * 完成跟踪任务
	// *
	// * @return
	// * @author <a href="mailto:lichaohn@163.com">lichao</a>
	// */
	// @aOperation(name = "[操作]完成跟踪任务")
	// public JSONObject doComplete() {
	// JSONObject re = new JSONObject();
	// String id = request.getParameter("ID");
	// if (service.doComplete(id)) {
	// re.put("flag", true);
	// } else {
	// re.put("flag", false);
	// }
	// return re;
	// }
	//
	// /**
	// * 查看修改跟踪信息
	// *
	// * @return
	// * @throws mException
	// * @author <a href="mailto:lichaohn@163.com">lichao</a>
	// * @throws SQLException
	// */
	// @aOperation(name = "[页面]修改跟踪信息")
	// public String doUpdate() throws mException, SQLException {
	// String id = request.getParameter("ID");
	// Map<String, Object> data = service.getInfoById(id);
	// Clob clob = (Clob) data.get("MEMO");
	// String MEMO = clob != null ? clob.getSubString(1, (int) clob.length()) :
	// null;
	// data.put("MEMO", MEMO);
	// VelocityContext context = new VelocityContext();
	// context.put("data", data);
	// return mSGA.mergeVelocity("track/update.vm", context);
	// }
	//
	// /**
	// * 执行跟踪信息修改操作
	// *
	// * @return
	// * @throws mException
	// * @author <a href="mailto:lichaohn@163.com">lichao</a>
	// */
	// @aOperation(name = "[操作]修改跟踪信息")
	// public JSONObject doUpdateOp() throws mException {
	// JSONObject re = new JSONObject();
	// Map<String, Object> param = BaseUtil.getParameters();
	// if (service.doUpdate(param)) {
	// re.put("flag", true);
	// } else {
	// re.put("flag", false);
	// }
	// return re;
	// }

}
