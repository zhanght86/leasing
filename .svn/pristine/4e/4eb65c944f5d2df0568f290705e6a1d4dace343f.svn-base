package com.pqsoft.gls.service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

/**
 * 营销平台
 * @author czy
 *
 */
public class GlsPjtLocationService  {

	private String xmlPath = "glspjtlocation.";//xml路径
	public List<Object> getPage(Map<String, Object> param) {
		List<Object> list =  Dao.selectList(xmlPath+"getArea", param);
		return list;
		//return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public boolean doAddGlsMore(Map<String, Object> param) {
			Dao.insert(xmlPath + "addGlsMore", param);
		return true;
	}
	
	public List<Object[]> getGlsDataList(Map<String, Object> param) {
		List<Object[]> list=new ArrayList<Object[]>();
		List<Object> listGls =  Dao.selectList(xmlPath+"getGlsDataList", param);
		for (int i = 0; i < listGls.size(); i++) {
			int ID=Integer.parseInt(JSONObject.fromObject(listGls.get(i)).get("ID").toString());
			param.put("ID", ID);
			List<Object> listFile =  Dao.selectList(xmlPath+"getGlsFileDataList", param);
			Object[] data={listGls.get(i),listFile};
			list.add(data);
		}
		return list;
	}
	/**
	 * 营销平台上传文件
	 * @param items
	 * @param filePath
	 * @return
	 */
	public  Map<String, Object> uploadFileForOne(List<FileItem> items, String filePath) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String customerPath = SkyEye.getConfig("file.path")+File.separator + "fileUploadDir" 
					+ File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())
					+ File.separator + filePath;
			//final long MAX_SIZE = 524288 * 2 * 1024;
			final long MAX_SIZE = 20 * 1024 * 1024;//20M
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
	
	public List<Object> getFileListByProjectId(Map<String, Object> param) {
		List<Object> list =  Dao.selectList(xmlPath+"getFileListByProjectId", param);
		return list;
		//return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public boolean delProjectFileByFileId(Map<String, Object> param) {
		boolean flag = true;
		try {
			int i=Dao.delete(xmlPath+"delProjectFileByFileId", param);
			if(i<1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public boolean delGlsZDataById(Map<String, Object> param) {
		boolean flag = true;
		try {
			/**删除跟进记录**/
			int i=Dao.delete(xmlPath+"delGlsZDataById", param);
			/**删除跟进记录上传文件**/
			param.put("SCOPETYPE", 1);
			Dao.delete(xmlPath+"delGlsZFileDataById", param);
			if(i<1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public boolean saveInstructionByGlsId(Map<String, Object> param) {
		boolean flag=true;
		try {
			int i=Dao.insert(xmlPath + "saveInstructionByGlsId", param);
			if(i<0){
				flag=false;
			}
		} catch (Exception e) {
			flag=false;
		}
	    return flag;
   }
	
	public List<Object> queryInstructByGlsId(Map<String, Object> param) {
		List<Object> list =  Dao.selectList(xmlPath+"queryInstructByGlsId", param);
	    return list;
   }
	
}