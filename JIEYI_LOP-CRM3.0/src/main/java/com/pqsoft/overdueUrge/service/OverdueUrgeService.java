package com.pqsoft.overdueUrge.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.PageUtil;

public class OverdueUrgeService {
	private String basePath = "overdueUrge.";
	

	public Object uploadFileForOne(HttpServletRequest request){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		 try {

		String customerPath = File.separator + "RecordMeeting";

		final long MAX_SIZE = 524288*2*1024;
		DiskFileItemFactory fif = new DiskFileItemFactory();
		fif.setSizeThreshold(4096);
		String root =  SkyEye.getConfig("file.path").toString();
		// 拿到配置文件中设置的上传文件路径

		createDirectory(root);// 调用创建存放上传文件 文件夹方法

		ServletFileUpload fp = new ServletFileUpload(fif);
		fp.setHeaderEncoding("UTF-8");
		fp.setSizeMax(MAX_SIZE);

		List<?> fileList = fp.parseRequest(request);
		Iterator<?> it = fileList.iterator();
		List filepathList = new ArrayList();

		File file = null;

		while (it.hasNext()) {
			FileItem fileItem = (FileItem) it.next();
			if (fileItem.isFormField()) {
				// 页面参数
				dataMap.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
			} else {
				String dir = root + File.separator + customerPath + File.separator
						+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

				createDirectory(dir);
				file = new File(dir + File.separator + UUID.randomUUID() + fileItem.getName());
				fileItem.write(file);
				dataMap.put("FILE_NAME", fileItem.getName());
				dataMap.put(
						"FILE_TYPE",
						file.getPath().toString()
								.substring(file.getPath().toString().lastIndexOf("."), file.getPath().toString().length()));
				dataMap.put("FILE_PATH", file.getPath().toString().replace("\\", "/"));
				filepathList.add(file.getAbsoluteFile());
			}
		}
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		return dataMap;
	}
	
	public boolean createDirectory(String path) {
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

	public Page getUrgeLettrtData(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath+"getUrgeLettrtData",basePath+"getUrgeLettrtDataConut");
	}

	public Map getShowUrgeLettrtData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return Dao.selectOne(basePath+"getShowUrgeLettrtData",param);
	}

	public int addUrgeLetter(Map<String, Object> param) {
		return Dao.insert(basePath+"saveUrgeLetter", param);
		// TODO Auto-generated method stub
		
	}

	
	public Page getUrgePhoneData(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath+"getUrgePhoneData",basePath+"getUrgePhoneDataConut");
	}

	public Map getShowUrgePhoneData(Map<String, Object> param) {
		return Dao.selectOne(basePath+"getShowUrgePhoneData",param);
	}

	public int addUrgePhone(Map<String, Object> param) {
		return Dao.insert(basePath+"addUrgePhone", param);
	}
	
	public Page getUrgeLawyerData(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath+"getUrgeLawyerData",basePath+"getUrgeLawyerDataConut");
	}

	public Map getShowUrgeLawyerData(Map<String, Object> param) {
		return Dao.selectOne(basePath+"getShowUrgeLawyerData",param);
	}

	public int addUrgeLawyer(Map<String, Object> param) {
		return Dao.insert(basePath+"addUrgeLawyer", param);
	}
	
	public Page getUrgeForceData(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath+"getUrgeForceData",basePath+"getUrgeForceDataConut");
	}

	public Map getShowUrgeForceData(Map<String, Object> param) {
		return Dao.selectOne(basePath+"getShowUrgeForceData",param);
	}

	public int addUrgeForce(Map<String, Object> param) {
		return Dao.insert(basePath+"addUrgeForce", param);
	}

	public List showUrgeLawyerLog(Map<String, Object> param) {
		return Dao.selectList(basePath+"showUrgeLawyerLog", param);
	}
	
}
