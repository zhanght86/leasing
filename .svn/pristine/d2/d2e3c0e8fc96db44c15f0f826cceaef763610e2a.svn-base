package com.pqsoft.fileupload.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.pqsoft.fileupload.service.FileUploadService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;


/**
 * 
 * @author 张路 zhanglu@pqsoft.cn
 * @version 1.0
 */
public class FileUploadAction extends Action {

	private static FileUploadService service = new FileUploadService();

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "zl", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply execute() {
		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "zl", email = "zhanglu@pqsoft.cn", name = "张路")
	@SuppressWarnings({"rawtypes", "unchecked", "unused"})
	public Reply upload() {
		List<FileItem> files = _getFileItem();
		Map<String,Object> map = (Map<String, Object>) service.uploadFileForOne(files) ;
		return new ReplyAjax(true, map);
	}

	
	
}
