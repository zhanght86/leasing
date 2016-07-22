package com.pqsoft.util;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 *
 * IO工具类
 *
 * @className IOUtil
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class IOUtil {

    /**
     * 保存输入流到指定的文件
     *
     * @author 钟林俊
     * @param inputStream 输入流
     * @param destFile 目的文件
     * @param overwrite 是否覆盖已存在的目的文件
     */
    public static void saveFile(InputStream inputStream, File destFile, boolean overwrite){
        Preconditions.checkNotNull(inputStream, "输入流为空！");
        Preconditions.checkArgument(!destFile.isDirectory(), "目标文件是个目录！");
        Preconditions.checkArgument(!(destFile.exists() && !overwrite), "目标文件已存在");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            int i;
            byte[] bytes = new byte[1024];
            while((i = inputStream.read(bytes)) != -1){
                bos.write(bytes, 0, i);
            }
        } catch (IOException e) {
            destFile.delete();
            throw new RuntimeException("保存文件失败！");
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    
    /**
    * 将一个输入流转化为字符串
    * 
    * @author 王华英
    * @param tInputStream 输入流
    */
    public static String getStreamString(InputStream tInputStream){
    	String result;
    if (tInputStream != null){
	    try{
		    BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
		    StringBuffer tStringBuffer = new StringBuffer();
		    String sTempOneLine = new String("");
		    while ((sTempOneLine = tBufferedReader.readLine()) != null){
		    	tStringBuffer.append(sTempOneLine);
		    }
		    result = tStringBuffer.toString();
		    return result;
	    }catch (Exception ex){
	    	ex.printStackTrace();
	    }
    }
    	return null;
    }
}
