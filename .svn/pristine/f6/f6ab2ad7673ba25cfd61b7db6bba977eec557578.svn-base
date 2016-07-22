package com.pqsoft.weixin.action;

import java.util.HashMap;
import java.util.Map;

import jsp.weixin.ParamesAPI.util.WeixinJSUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

/**
 * 租赁方案crud
 * @author LUYANFENG @ 2015年2月28日
 *
 */
public class SchemeAction extends AbstractWeiXinResponseAction {
	
	/**
	 * 下一页 json数据
	 * page
	 * rows
	 * @return
	 */
	@aDev(code = "170025", email = "luyf@163.com", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply nextPageJson(){
		Map<String, Object> m = _getParameters();
		Map<String,Object > param = new HashMap<String,Object>();
		param.putAll(m);
		if(StringUtils.isNotBlank((String)m.get("page"))){
			param.put("page", m.get("page"));
		}
		if(StringUtils.isNotBlank((String)m.get("rows"))){
			param.put("rows", m.get("rows"));
		}
		Map<String, Object>  SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			param.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map<String, Object>  COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			param.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		if (StringUtils.isBlank((String) m.get("t")) || m.get("t").equals("sq")) { // 申请
			param.put("STATUS", "0");
		} else if (m.get("t").equals("cx")) { // 查询
			param.put("STATUS2", "case2");
		} else if (m.get("t").equals("bc")) { // 补充
			param.put("DICT_TYPE", "微信补充资料");
		}
		param.put("CLERK_ID", Security.getUser().getId() );
		
		param.put("dict_type", "微信补充资料");
		param.put("tsdd_type", "业务类型");
		param.put("tsdd1_type", "项目状态位");
		param.put("tsdd2_type", "客户类型");
		Page page = PageUtil.getPage(param, "weixin_view.getAllProject", "weixin_view.getAllProject_count");
		
		JSONObject json = new JSONObject();
		json.put("page", page);
		return new ReplyJson(json );
	}
	
	
	@aDev(code = "170025", email = "luyf@163.com", name = "luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply listHTML(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/scheme/listHTML.ejs.vm", context ));
	}
	
	@aDev(code = "170025", email = "luyf@163.com", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply list(){
		VelocityContext vm = new VelocityContext();
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/scheme/list.vm", vm ) );
	}
}
