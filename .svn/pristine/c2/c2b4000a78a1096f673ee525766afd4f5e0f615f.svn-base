package com.pqsoft.pdfTemplate.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pdfTemplate.service.BusinessSectorService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class BusinessSectorAction extends Action {

	BusinessSectorService service = new BusinessSectorService();

	/**
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-24 下午01:50:29
	 */
	@aPermission(name = { "参数配置", "模板组管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("TPM_BUSINESS_PLATE_LIST", DDMservice.get("业务类型"));// Modify
																		// By：
																		// YangJ
																		// 2014年5月6日14:11:18
		context.put("TPM_PROJECT_TYPE_LIST", DDMservice.get("行业类型"));// Modify
																		// By：
																		// YangJ
																		// 2014年5月6日20:08:35
		return new ReplyHtml(VM.html("pdfTemplate/businessSectorMg.vm", context));
	}

	/**
	 * 管理页列表数据
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@aPermission(name = { "参数配置", "模板组管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getDate() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getDate(param));
	}

}
