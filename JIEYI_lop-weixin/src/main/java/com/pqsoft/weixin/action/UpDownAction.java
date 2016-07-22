package com.pqsoft.weixin.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.media.util.MUDload;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.leeds.utils.FileUploadService;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.ImageUtils;
import com.pqsoft.weixin.service.BzManageService;

public class UpDownAction extends AbstractWeiXinResponseAction {


	private BzManageService bzSer = new BzManageService();
	/**
	 * 下载微信服务端的图片
	 * @param 
	 */
	@aDev(code = "170025", email = "luyanfenghn@163.com", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply download(){
		JSONObject json = new JSONObject();
		json.put("ok", false);
		json.put("error", "");
		
		// 取参数
		Map<String, Object> param = _getParameters();
		String serverIDs = (String) param.get("SIDs");
		String PROJECT_ID = (String) param.get("PROJECT_ID");
		String PARENT_ID = (String) param.get("PARENT_ID");
		String TPM_TYPE = (String) param.get("TPM_TYPE");
		String TPM_CUSTOMER_TYPE = (String) param.get("TPM_CUSTOMER_TYPE"); 	// 只用于立项阶段，区分承租人、共同承租人、担保人的资料
		String TPM_BUSINESS_PLATE = (String) param.get("TPM_BUSINESS_PLATE");	//阶段名
		String FK_ID = (String) param.get("FK_ID");//放款id
		String FILE_DICT_ID = (String) param.get("FILE_DICT_ID"); 	// FILE_DICT_ID 和MMF_ID 是MATERIAL_MGT_FILE表的联合主键
		String MMF_ID = (String) param.get("MMF_ID");				// 
		
		this.bzSer.checkProjectMan(PROJECT_ID,1,20);
		
		String[] serverIDArray = null;
		if(StringUtils.isBlank(serverIDs)){
			json.put("ok", false);
			json.put("error", "serverID无效或没有指定");
			return new ReplyJson(json );
		}
		/*if(StringUtils.isBlank(TPM_CUSTOMER_TYPE)){
			json.put("ok", false);
			json.put("error", "需要指定资料所有人类型");
			return new ReplyJson(json );
		}*/
		if(serverIDs.contains(",")){
			serverIDArray = serverIDs.split("\\s*,\\s*");
		}else{
			serverIDArray = new String[]{ serverIDs };
		}
		
		String customerPath = SkyEye.getConfig("file.path") + File.separator + "fileUploadDir" 
				+ File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())
				+ File.separator ;
		String dir = customerPath
				+ File.separator
				+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		this.createDirectory(dir);
		
		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		for(String serverId : serverIDArray){
			//从微信服务器下载图片到咱们的服务器上 
			Log.debug("### 文件serverId ： "+serverId);
			String physicalPath = MUDload.downloadMedia(ParamesAPI.access_token, serverId.trim() , dir );
			
			if(StringUtils.isBlank(physicalPath) || !physicalPath.contains(".")){
				json.put("ok", physicalPath != null );
				json.put("error","从微信服务器下载文件失败："+serverId);
				continue;
			}
			Log.debug("已下载到本地服务器的文件："+ physicalPath);
			File file = new File(physicalPath);
			
			
			// 保存到数据库
			Log.debug("#### ##############保存处理开始。。。。");
			String file_type = physicalPath.substring( physicalPath.indexOf(".") , physicalPath.length() ); // 文件后缀： abc.jpg => .jpg
			
			String fileName = UUID.randomUUID().toString().replaceAll("\\-","").toUpperCase();
			File newfile = new File(dir ,fileName +file_type);
			file.renameTo(newfile);
			
			String scale_path = ImageUtils.reSizeImage(newfile, 80,80 , true);
		
			Map<String, Object> p2 = new HashMap<String, Object>();
			p2.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
			p2.put("TPM_BUSINESS_PLATE", TPM_BUSINESS_PLATE);
			p2.put("TPM_TYPE", TPM_TYPE);
			p2.put("TPM_CUSTOMER_TYPE", TPM_CUSTOMER_TYPE);
			p2.put("PDF_PATH",  newfile.getPath() );
			p2.put("PDF_PATH_SCALE",  scale_path );
			p2.put("NAME", fileName);
			p2.put("PROJECT_ID", PROJECT_ID );
			p2.put("CREATE_CODE", Security.getUser()==null? "god" : Security.getUser().getCode());
			p2.put("REMARK", "微信上传");
			p2.put("FILE_LEVEL", 2);
			p2.put("FILE_TYPE", file_type.toUpperCase() );
			p2.put("PARENT_ID", PARENT_ID);
			p2.put("FK_ID", FK_ID);
			p2.put("FILE_DICT_ID", FILE_DICT_ID);
			p2.put("MMF_ID", MMF_ID);
			files.add(p2);
			int insert = Dao.insert("materialMgt.insertFpf", p2);
			json.put("ok", insert == 1);
			Log.debug("#### ##############保存处理正在结束。。。。");
			Dao.commit();
			
		}
		json.put("files", files);
		
		return new ReplyJson(json );
		
	}
	
	
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170025", email = "luyanfenghn@163.com", name = "luyanfeng")
	public ReplyFile downFile() {
		Map<String, Object> param = _getParameters();
		String path = (String)param.get("path");
		String filename = (String)param.get("filename");
		File file = null;
		try {
			file = new File(path);
		} catch (Exception e) {
			Log.error("非重要文件丢失");
			return null;
		}
		if(!file.exists()){
			Log.error("非重要文件丢失");
			return null;
		}
		if(StringUtil.isBlank(filename)){
			filename = file.getName();
		}
		if(!file.exists()){
			Log.error("非重要文件丢失:"+file.getAbsolutePath());
			return null;
		}
		return new ReplyFile(file, filename);
	}
	
	/**
	 * 删除附件
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170025", email = "luyanfenghn@163.com", name = "luyanfeng")
	public Reply delMedia() {
		String PROJECT_ID = SkyEye.getRequest().getParameter("PROJECT_ID");
		String ID = SkyEye.getRequest().getParameter("ID");
		String path = SkyEye.getRequest().getParameter("path");
		if(StringUtils.isBlank(ID) 
				|| StringUtils.isBlank( path) ){
			return new ReplyAjax(false,"缺少参数");
		}
		
		
		this.bzSer.checkProjectMan(PROJECT_ID,0);
		
		String result = "";
		try {
			path = URLDecoder.decode(path,"UTF-8");
			int i = Dao.delete("materialMgt.delFpf", ID);
			if(i==1) FileUploadService.deleteFile(path);
			//删除记录
		} catch (Exception e) {
			e.printStackTrace();
			result = "服务错误";
		}
		
		return new ReplyAjax(result.isEmpty() ,result);
	}
	
	
	private boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
