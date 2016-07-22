package com.pqsoft.pictureSynchronous.action;

import java.io.File;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import com.pqsoft.pictureSynchronous.service.PictureSynchronousService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.JsonUtil;

public class PictureSynchronousAction extends Action{
	
	FileCatalogUtil fcu = new FileCatalogUtil();
	PictureSynchronousService service = new PictureSynchronousService();

	@Override
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		return null;
	}
	
	/**
	 * 查询业务系统中同步命令
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-11-18  上午08:51:18
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectPictureRecord(){
		Map<String,Object> param = _getParameters();
		String path = service.getConfig();
		path = path.replaceAll("\\W", ".");
		path = "^"+path+".(.+)[\\/][^\\^/]+$";
		param.put("path", path);
		List<Map<String,Object>> listMap = service.selectPictureRecord(param);
		return new ReplyAjax(listMap);
	}
	
	/**
	 * 查询图像采集承租人外层目录
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getConfig(){
		return new ReplyAjax(service.getConfig());
	}
	
	/**
	 * 下载图片
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public ReplyFile downFile(){
		Map<String,Object> param = _getParameters();
		String path = param.get("FILE_PATH").toString();
		File file = new File(path);
		return new ReplyFile(file,file.getName());
	}
	
	/**
	 * 同步图像采集上传的图片
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply uploadFile(){
		List<FileItem> fileList = _getFileItem();
		Map<String,Object> param = _getParameters();
		param = JsonUtil.toMap(JSONObject.fromObject(param.get("param")));
		String file_name = param.get("VALUE1").toString();
		String URL = param.get("URL").toString();
		String CatalogId = fcu.getCatalogIdByPath("","",URL);
		boolean flag = true;
		try{
			fcu.doUploadPicture(param, fileList, URL);//在业务系统中上传图片
			if(param.containsKey("CREATE_DATE")){
				param.put("VALUE3", param.get("CREATE_DATE"));//图片上传的时间
				
			}
			param.put("CATALOG_ID", CatalogId);
			param.put("FILE_NAME", file_name);
//			{//防止同一文件夹下的文件名称重复
//				Map<String,Object> fileMap = fcu.selectRepeatFileMax(param);
//				if(fileMap != null && fileMap.containsKey("FILE_NAME_REPEAT")){
//					int FILE_NAME_REPEAT = Integer.parseInt(fileMap.get("FILE_NAME_REPEAT").toString())+1;
//					param.put("FILE_NAME", param.get("FILE_NAME")+"("+FILE_NAME_REPEAT+")");
//					param.put("FILE_NAME_REPEAT", FILE_NAME_REPEAT);
//				}else{
//					param.put("FILE_NAME_REPEAT", "0");
//				}
//			}
			fcu.insertFileMessage(param);//在业务系统PICTUREFILE表添加一条数据
			fcu.addPictrueRecord(param);//在业务系统中记录
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
			return new ReplyAjax(flag);
		}
		return new ReplyAjax(flag,param.get("ORIGINAL_PATH"));
	}
	
	/**
	 * 同步图像采集删除的图片
	 */
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteFile(){
		Map<String,Object> param = _getParameters();
		param = JsonUtil.toMap(JSONObject.fromObject(param.get("param")));
		boolean flag = true;
		try{
			{//不同步图像采集系统删除的文件
				Map<String,Object> param1 = fcu.selectOperationFile(param.get("VALUE").toString());
	        	if(param != null){
	        		param1.put("INSTRUCTION","DELETEPICTURE");
	        		param1.put("RECORD_REMARK","删除图片");
	        	}
	        	fcu.addPictrueRecord(param1);
			}
			{//同步图像采集系统删除的文件
	//			fcu.deleteFile(param.get("VALUE").toString(),true);//在业务系统磁盘中删除图片
	//			param = fcu.selectOperationFile(param.get("VALUE").toString());//根据下载全路径查询业务系统图片ID
	//			fcu.deleteFileById(param.get("ID").toString());//在业务系统PICTUREFILE表删除一条数据
			}
		}catch(Exception e){
			flag = false;
		}
		return new ReplyAjax(flag);
	}
	
}
