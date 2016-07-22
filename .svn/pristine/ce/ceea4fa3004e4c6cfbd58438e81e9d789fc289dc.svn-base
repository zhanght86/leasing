package com.pqsoft.projectContraceControl.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 
 * @author caizhongyang
 *
 */
public class ProjectContraceControlService {

	private String xmlPath = "projectContractControlManager.";

	/*
	 * 数据字典
	 */
	public List<Object> queryDataDictionary(String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TYPE", type);
		List<Object> list =  Dao.selectList(xmlPath+"queryDataDictionary", param);
		return list;
	}
	
	/**
	 * 	获取数据
	 * @param param
	 * @return
	 */
	public Page queryPage(Map<String, Object> param) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, xmlPath+"getAllProjectControlData", xmlPath+"getAllProjectControlDataCount");
	}
	
	public boolean addProjectControlFileContext(Map<String, Object> param){
	    try {
			int i=Dao.insert(xmlPath+"addProjectControlFileContext",param);
			if(i>0){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public  Map<String, Object> uploadFileForOne(List<FileItem> items, String filePath) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String customerPath = SkyEye.getConfig("file.path")+File.separator + "fileUploadDir" 
					+ File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())
					+ File.separator + filePath;
			//final long MAX_SIZE = 524288 * 2 * 1024;
			final long MAX_SIZE = 200 * 1024 * 1024;//200M
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);

			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);

			Iterator<?> it = items.iterator();
			File file = null;

			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem
							.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					if(StringUtils.isBlank(fileItem.getName())) continue;
					String dir = customerPath
							+ File.separator
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(Calendar.getInstance().getTime());
					createDirectory(dir);
					String FILE_NAME = fileItem.getName();
					String FILE_TYPE = FILE_NAME.substring(FILE_NAME.lastIndexOf("."), FILE_NAME.length());
					file = new File(dir + File.separator + UUID.randomUUID()+FILE_TYPE);
					
					fileItem.write(file);
					dataMap.put("FILE_SIZE", fileItem.getSize());
					dataMap.put("FILE_NAME", FILE_NAME);
					dataMap.put("FILE_TYPE", FILE_TYPE.toUpperCase());
					dataMap.put("FILE_PATH", file.getPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	
	
	private  boolean createDirectory(String path) {
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
	
	public List<Map<String,Object>> queryProjectControlFileContext(Map<String, Object> param){
		List<Map<String,Object>> list=Dao.selectList(xmlPath+"queryProjectControlFileContext",param);
		return list;
	}
	
}
