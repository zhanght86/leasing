package com.pqsoft.fileupload.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.DownloadFile;
/**
 * 上传、下载工具类
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2014年8月29日 下午3:41:46
 */
public class FileUploadService {
	
	public Object uploadFileForOne(List<FileItem> items) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			String customerPath = File.separator + "FIL_ATTACHMENT";

			final long MAX_SIZE = 524288 * 2 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path").toString();
			// 拿到配置文件中设置的上传文件路径

			createDirectory(root);// 调用创建存放上传文件 文件夹方法

			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);

			Iterator<?> it = items.iterator();
			List filepathList = new ArrayList();

			File file = null;

			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem
							.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = root
							+ File.separator
							+ customerPath
							+ File.separator
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(Calendar.getInstance().getTime());

					createDirectory(dir);
					file = new File(dir + File.separator + fileItem.getName());
					fileItem.write(file);
					dataMap.put("FILE_SIZE", fileItem.getSize());
					dataMap.put("FILE_NAME", fileItem.getName());
					dataMap.put(
							"FILE_TYPE",
							file.getPath()
									.toString()
									.substring(
											file.getPath().toString()
													.lastIndexOf("."),
											file.getPath().toString().length()));
					dataMap.put("FILE_PATH",
							file.getPath().toString().replace("\\", "/"));
					filepathList.add(file.getAbsoluteFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
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
	/**
	 *@requestFunction
	 * 提供一个文件路径，下载之
	 * @param
	 * filePath 上传文件的路径
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月29日 下午3:40:19
	 */
	public void downloadFile(String filePath){
		DownloadFile.download(filePath, SkyEye.getResponse());
	}
}
