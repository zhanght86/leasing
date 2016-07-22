package com.pqsoft.creditWind.service;

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

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.PageUtil;

public class CreditWindService {
	private String basePath = "creditWind.";
	
	public Page selectCreditWind(Map<String, Object> m) {
		return PageUtil.getPage(m, basePath+"selectCurrentWind",basePath+"selectCurrentWindCount");
	}
	
	public int ExecuteAddControlMeeting(Map map){
		boolean a=true;
		Dao.delete(basePath+"DEL_FIL_CREDIT_SUMMY_LEVEL",map);
		String SEQUENCE=Dao.getSequence("SEQ_FIL_WIND_CONTROL_SUMMARY");
		//begin update by zhangzhl date:2014-03-15   for:去掉注释掉的添加评审人评审意见信息
		{//此段代码保留
							List<Map<String,Object>> list=JSONArray.fromObject(map.get("json").toString());
							//添加风控评审人建议
							for (Map<String, Object> maps : list) {
								maps.put("SEQUENCE", SEQUENCE);
								a=Dao.insert(basePath+"ADD_FIL_CREDIT_SUMMY_LEVEL",maps)==1&&a;
							}
							if(!a){
					    		return 0;
					    	}else{
					    		
					    	}
		}
		//end update by zhangzhl date:2014-03-15   for:去掉注释掉的添加评审人评审意见信息
    		//添加风控会议评审
    		map.put("SEQUENCE", SEQUENCE);
    		a=Dao.insert(basePath+"ADD_FIL_WIND_CONTROL_SUMMARY",map)==1&&a;
    		map.put("WIND_ID", SEQUENCE);
    		insertWindRecordFile(map);
//    	}
    		
    	return 1;

	}
	
	//添加录音
	public int insertWindRecordFile(Map<String,Object> map){
		int num = Dao.insert(basePath+"insertWindRecord",map);
		return num;
	}
	
	public Object getWindSumaryInfo(Map<String, Object> m){
		return Dao.selectOne(basePath+"SelectWindControlDetails", m); 
	}
	
	public List getWindLevel(Map<String, Object> map){
		return Dao.selectList(basePath+"getWindLevel",map);
	}
	
	
	public Map queryProCodeByPro_ID(Map<String, Object> map){
		return Dao.selectOne(basePath+"queryProCodeByPro_ID", map); 
	}
	
	/**
	 * 
	 * @Description: 上传文件处理
	 * @author cxq
	 * @return Map
	 * @throws Exception
	 */
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
	
	/**
	 * 根据项目ID查询最大的风控会议纪要ID
	 * @param PROJECT_ID
	 * @return
	 * @author King 2014年8月21日
	 */
	public Object queryWindIdByProjectId(Object PROJECT_ID){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(basePath+"queryWindIdByProjectId", map);
	}
	
}
