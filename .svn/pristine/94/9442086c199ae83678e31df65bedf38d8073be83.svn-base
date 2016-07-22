package com.pqsoft.qsd.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.qsd.service.QsdService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class QsdAction extends Action {
	public VelocityContext context = new VelocityContext();
	QsdService service = new QsdService();

	@Override
	@aPermission(name = { "业务管理", "签收单管理", "列表" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		return new ReplyHtml(VM.html("qsd/Qsd.vm", context));
	}

	@aPermission(name = { "业务管理", "签收单管理", "列表" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findqsd() {
		Map<String, Object> param = _getParameters();
		Page page = service.qsd(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "业务管理", "签收单管理", "签收" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply uploadPdfTemplate() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPdfTemplate(param, fileList);
		logger.debug("param=" + param);
		if (flag) {
			boolean flag1 = service.uploadPdfTemplate(param);
			if (flag1) {
				return new ReplyAjax(param);
			} else {
				return new ReplyAjax(false, "保存失败！");
			}
		}
		return new ReplyAjax(false, "保存失败！");
	}

	@aPermission(name = { "业务管理", "签收单管理", "签收明细" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply toUnfoldDate() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("row"));
		param.remove("row");
		param.putAll(JsonUtil.toMap(json));
		System.out.println(param);
		return new ReplyAjax(service.getUnfoldDate(param));
	}

	@aPermission(name = { "业务管理", "签收单管理", "签收明细", "下载" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public ReplyFile downPdfTemplate() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.selectPdfPath(param);
		String pdfPath = map.get("FILE_URL").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		return new ReplyFile(file, fileName);
	}

	@aPermission(name = { "参数配置", "模版管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteqsd() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.doDeleteqsd(param);
		if (flag) {
			return new ReplyAjax(param);
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}
}
