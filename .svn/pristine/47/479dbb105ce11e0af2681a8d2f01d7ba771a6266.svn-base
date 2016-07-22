package com.pqsoft.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.pqsoft.dataDictionary.action.DataDictionaryAction;

public class DownloadFile {
	
	private static Logger logger = Logger.getLogger(DataDictionaryAction.class);
	
	/**
	 * 下载文件
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:47:32
	 */
	public static HttpServletResponse download(String path, HttpServletResponse response) {
        try  {
            //  path是指欲下载的文件的路径。
           File file  =   new  File(path);
            //  取得文件名。
           String filename  =  file.getName();
           logger.info(filename);
            //  取得文件的后缀名。
//           String ext  =  filename.substring(filename.lastIndexOf( "." )  +   1 ).toUpperCase();

            //  以流的形式下载文件。
           InputStream fis  =   new  BufferedInputStream( new  FileInputStream(path));
            byte [] buffer  =   new   byte [fis.available()];
           fis.read(buffer);
           fis.close();
            //  清空response
           response.reset();
            //  设置response的Header
           response.setCharacterEncoding("UTF-8");
           response.addHeader( "Content-Disposition" ,  "attachment;filename="  +  new  String(filename.getBytes(),"UTF-8"));
           response.addHeader( "Content-Length" , file.length()+"");
           OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
           response.setContentType( "application/octet-stream" );
           toClient.write(buffer);
           // 关闭流
           toClient.close();
           fis.close();
       }  catch  (IOException ex) {
    	   logger.error(ex);
       }
        return  response;
   } 

}
