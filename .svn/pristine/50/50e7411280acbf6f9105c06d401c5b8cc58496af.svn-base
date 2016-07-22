package com.pqsoft.adjustRate.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.adjustRate.service.AdjustRateNoticeService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ZipUtil;

public class AdjustRateNoticeAction extends Action {
	/****** 租金调整通知书***@auth: king 2014年9月23日 *************************/
	private AdjustRateNoticeService service = new AdjustRateNoticeService();

	@Override
	public Reply execute() {
		return null;
	}

	@aPermission(name = { "权限管理", "调息管理", "租金调整通知书导出" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply expPdf() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> payList = service
				.queryAdjustRatePdfInfo(param);
		String create_date = null;
		if (payList.size() > 0) {
			create_date = payList.get(0).get("DEPEND_TIME") + "";
		} else {
			return new ReplyAjax(false, "没有调整的还款计划");
		}
		File file = null;
		String strZipPath = SkyEye.getConfig("file.path") + File.separator
				+ create_date + "租金调整通知书.zip";
		if (!new File(strZipPath).exists()) {
			List<File> files = new ArrayList<File>();
			for (int i = 0; i < payList.size(); i++) {
				Map<String, Object> map = payList.get(i);
				service.queryRateInfo(map);
				List<Map<String, Object>> detailList = service
						.queryAdjustPayDetailList(map);
				files.add(new File(service.expPdf(map, detailList)));
			}
			file = ZipUtil.createZipFile(strZipPath, files, false);
		} else {
			file = new File(strZipPath);
		}
		return new ReplyFile(file, file.getName());
	}
}
