package com.pqsoft.util;

import java.io.*;

import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class FileUtil {
	final private static Logger logger = Logger.getLogger(Util.class);
	
	/**
	 * 创建文件路径
	 * createDirectory
	 * @date 2013-10-18 上午11:05:13
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public static boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			logger.error(e);
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 附件下载
	 * download
	 * @date 2013-10-18 上午11:05:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public static HttpServletResponse download(String path, HttpServletResponse response) {
        try  {
            //  path是指欲下载的文件的路径。
           File file  =   new  File(path);
            //  取得文件名。
           String filename  =  file.getName();
           logger.debug(filename);
            //  取得文件的后缀名。
           @SuppressWarnings("unused")
		   String ext  =  filename.substring(filename.lastIndexOf( "." )  +   1 ).toUpperCase();

            //  以流的形式下载文件。
           InputStream fis  =   new  BufferedInputStream( new  FileInputStream(path));
            byte [] buffer  =   new   byte [fis.available()];
           fis.read(buffer);
           fis.close();
            //  清空response
           response.reset();
            //  设置response的Header
           response.setCharacterEncoding("UTF-8");
           response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("GB2312"), "ISO-8859-1"));// 设定输出文件头
           response.addHeader( "Content-Length" ,  ""   +  file.length());
           OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
           response.setContentType( "application/octet-stream" );
           toClient.write(buffer);
           // 20110714 关闭流
           toClient.close();
           fis.close();
       }  catch  (IOException ex) {
    	   logger.error(ex);
       }
        return  response;
   	}


}
