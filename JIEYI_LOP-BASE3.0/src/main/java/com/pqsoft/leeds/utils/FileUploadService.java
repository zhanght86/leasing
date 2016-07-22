package com.pqsoft.leeds.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.IMG;
import com.pqsoft.util.ImageUtils;
import com.pqsoft.util.StringUtils;
/**
 * 上传、下载工具类
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2014年8月29日 下午3:41:46
 */
public class FileUploadService {
	/**
	 *@requestFunction
	 * 上传单个input[type=file]文件控件所提交的文件。
	 * @param
	 * items 
	 * 		通过_getFileItem()取得form为enctype="multipart/form-data" method="post"的表单，所上传的文件。
	 * 注意，有多个文件控件的话，先析出单个，然后逐一调用本方法。
	 * @param
	 * 	filePath 
	 * 		设定服务器上的上传路径（所传路径，已保证为当前用户所独有）
	 * @return
	 * 返回一个map。包括上传文件路径、name、path等
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年9月17日 上午9:03:46
	 */
	public static Map<String, Object> uploadFileForOne(List<FileItem> items, String filePath) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String customerPath = SkyEye.getConfig("file.path")+File.separator + "f" 
					//+ File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())
					+ File.separator + filePath;
			//final long MAX_SIZE = 524288 * 2 * 1024;
			final long MAX_SIZE = 50 * 1024 * 1024;//50M=52428800B
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
					/*String dir = customerPath
							+ File.separator
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(Calendar.getInstance().getTime());*/
					String dir = customerPath
							+ File.separator
							+ dataMap.get("PARENT_ID").toString();
					createDirectory(dir);
					// 文件名不能有空格
					String FILE_NAME = fileItem.getName().replace(" ","");
					String FILE_TYPE = FILE_NAME.substring(FILE_NAME.lastIndexOf("."), FILE_NAME.length());
					String temp_file_name =  FILE_NAME.substring(0, FILE_NAME.lastIndexOf("."));
					//file = new File(dir + File.separator + UUID.randomUUID()+FILE_TYPE);
					file = new File(dir + File.separator + temp_file_name + "_" + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime()) + FILE_TYPE);
					File temp = File.createTempFile(file.getName(), ".file");
					fileItem.write(temp);
					if(ImageUtils.isImage(FILE_TYPE)){
						/** -- yipan update 2016-2-19 10:04:04 start -- **/
						//IMG.shrink(temp, FILE_TYPE, 2048, 2048,file);
						IMG.shrink(temp, FILE_TYPE, 5000, 10000,file);
						/** -- yipan update 2016-2-19 10:04:04 end -- **/
					}else{
						FileCopyUtils.copy(temp, file);
					}
					temp.delete();
					try {
						if(ImageUtils.isImage(FILE_TYPE)){
								String scale_path = ImageUtils.reSizeImage(file, 80,80 , true);
								dataMap.put("SCALE_PATH", scale_path);
						}
					} catch (Throwable e) {
					}
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
	public static Map<String, Object> uploadFileForOne(List<FileItem> items) {
		return uploadFileForOne(items, "");
	}
	
	private static boolean createDirectory(String path) {
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
	public static void downloadFile(String filePath){
		HttpServletResponse response = SkyEye.getResponse();
		 File file  =   new  File(filePath);
         //  取得文件名。
        String filename  =  file.getName();
        try{
	         //  以流的形式下载文件。
	        InputStream fis  =   new  BufferedInputStream( new  FileInputStream(filePath));
	         byte [] buffer  =   new   byte [fis.available()];
	        fis.read(buffer);
	        fis.close();
	         //  清空response
	        response.reset();
	         //  设置response的Header
	        response.setCharacterEncoding("UTF-8");
	        response.addHeader( "Content-Disposition" ,  "attachment;filename="  +  new  String(filename.getBytes("gb2312"),"iso8859-1"));
	        response.addHeader( "Content-Length" , file.length()+"");
	        OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
	        response.setContentType( "application/octet-stream" );
	        toClient.write(buffer);
	        // 关闭流
	        toClient.close();
	        fis.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 *@requestFunction
	 * 提供一个文件路径，删除，该文件路径下所有文件
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年9月16日 下午6:18:10
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		//文件
		if( !file.exists()){
			return ;
		}
        if(file.isFile()){
        	file.delete();
        }else{ //文件夹
             //获得当前文件夹下的所有子文件和子文件夹
             File f1[] = file.listFiles();
             //循环处理每个对象
             int len = f1.length;
             for(int i = 0;i < len;i++){
                      //递归调用，处理每个文件对象
            	 deleteFile(f1[i].getPath());
             }
             //删除当前文件夹
             file.delete();
        }
	}
}
