package com.pqsoft.base.action;

import java.io.File;
import java.util.Map;

import com.pqsoft.base.service.FileService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FileAction extends Action{

	FileService service = new FileService();
	
	@Override
	public Reply execute() {
		
		return null;
	}
	/**
	 * 
	 * @Title: downloadFile 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "参数配置", "模板管理", "下载" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply downloadFile(){
		Map<String,Object> param = _getParameters();
		String file_id = param.get("FIL_ID").toString();
		Map<String, Object> fileMess = (Map<String, Object>) service.findFileByID(file_id,param);
		// path是指欲下载的文件的路径。
		String filePath =  fileMess.get("FIL_PATH").toString();
		String fileName = fileMess.get("FIL_NAME").toString();
		File file = new File(filePath);
		return new ReplyFile(file, fileName);
	}
}
