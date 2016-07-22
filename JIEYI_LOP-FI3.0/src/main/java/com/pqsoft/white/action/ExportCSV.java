package com.pqsoft.white.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportCSV {

	/**
     * 数据信息
     */
    private List<Map<String, Object>> mapList;
    
    /**
     * 
     */
    private List<List<Map<String, Object>>> outList;

    /**
     * 列名
     */
    private List<String> headList;

    /**
     * 列字段key
     */
    private List<String> keyList;
    
    /**
     * 数据格式
     */
    private Map<String, String> formatMap;
    
    /**
     * 输出流
     */
    private OutputStream ous = null;
    /**
     * 文本输出流
     */
    private PrintWriter pw = null;

    /**
     * 请求接受
     */
    private HttpServletRequest request;
    /**
     * 请求返回
     */
    private HttpServletResponse response;
    /**
     * 返回名
     */
    private String fileName;
    /**
     * 火狐
     */
    private String firefox = ".*firefox.*";  // 判断字符串中是否含有特定字符串ll
    private String chrome = ".*chrome.*";//360 // google
    private String ie = ".*trident.*";  // IE  
    private String mac = ".*mac os.*";
    private String applewebkit = ".*applewebkit.*";
    /**
     * 后缀名
     */
    private String suffix = ".xls";
    
    /**
     * 构造函数
     *
     * @param headList     列名
     * @param mapList      内容信息
     */
    public ExportCSV(HttpServletRequest request, HttpServletResponse response, String fileName, List<String> headList, List<Map<String, Object>> mapList, List<String> keyList, Map<String, String> formatMap) {
        this.request = request;
        this.response = response;
        this.fileName = fileName;
        this.headList = headList;
        this.mapList = mapList;
        this.keyList = keyList;
        this.formatMap = formatMap;
    }
    
    /**
     * 构造函数
     *
     * @param headList     列名
     * @param mapList      内容信息
     */
    public ExportCSV(HttpServletRequest request, HttpServletResponse response, String fileName, List<String> headList, List<Map<String, Object>> mapList, List<String> keyList) {
        this.request = request;
        this.response = response;
        this.fileName = fileName;
        this.headList = headList;
        this.mapList = mapList;
        this.keyList = keyList;
    }
    
    /**
     * 构造函数
     * @param outputStream
     * @param headList
     * @param mapList
     * @param keyList
     */
    public ExportCSV(OutputStream outputStream, List<String> headList, List<Map<String, Object>> mapList, List<String> keyList) {
        this.ous = outputStream;
        this.headList = headList;
        this.mapList = mapList;
        this.keyList = keyList;
    }
    
    
    /**
     * 构造函数
     * 
     * @param request
     * @param response
     * @param fileName
     * @param headList
     * @param outList
     * @param keyList
     * @param o
     */
    public ExportCSV(HttpServletRequest request, HttpServletResponse response, String fileName, List<String> headList, List<List<Map<String, Object>>> outList, List<String> keyList, Object o) {
    	this.request = request;
        this.response = response;
        this.fileName = fileName;
        this.headList = headList;
        this.outList = outList;
        this.keyList = keyList;
    }
    
    
    /**
     * 构造函数
     *
     * @param outputStream 输出流
     * @param headList     列名
     */
    public ExportCSV(OutputStream outputStream, List<String> headList, List<List<Map<String, Object>>> outList, List<String> keyList, Object o) {
        this.ous = outputStream;
        this.headList = headList;
        this.outList = outList;
        this.keyList = keyList;
    }
    
    /**
     * 构造函数
     *
     * @param printWriter 输出流
     * @param headList     列名
     * @param mapList      内容信息
     */
    public ExportCSV(PrintWriter printWriter, List<String> headList, List<Map<String, Object>> mapList, List<String> keyList) {
        this.pw = printWriter;
        this.headList = headList;
        this.mapList = mapList;
        this.keyList = keyList;
    }
    
    
    
    
    
    /**
     * 数据导出
     * xls 文件格式
     * @throws IOException
     */
    public void ExportExcelOut() throws IOException {
    	try {
	    	OutputStream out = setResponseCharset();
			
			ExportExcelUtils eeu = new ExportExcelUtils();
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			eeu.exportSXSSFExcel(workbook, 0, headList, mapList, keyList, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 单线程导出文件
     * xls 文件格式
     * 
     * @throws IOException
     */
    public void ExportExcelSheetOut() throws IOException {
    	
		try {
			OutputStream out = setResponseCharset();
			ExportExcelUtils eeu = new ExportExcelUtils();
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			eeu.exportSXSSFExcelSheet(workbook, headList, outList, keyList, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 返回头编码设置
     * 文件名编码
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
	public OutputStream setResponseCharset() throws UnsupportedEncodingException, IOException {
		String AGENT = request.getHeader( "USER-AGENT" ).toLowerCase();
    	//定义导出文件的格式的字符串
    	String contentType = "application/vnd.ms-excel";
    	// 
    	String charset = "UTF-8";
    	fileName = fileName + suffix;
        // 判断火狐
    	if (AGENT.matches(firefox)) {
    		fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
		}
    	else if (AGENT.matches(chrome) || AGENT.matches(ie)) {
    		fileName = URLEncoder.encode(fileName, charset);
		}
    	else if(AGENT.matches(mac) || AGENT.matches(applewebkit)) {
    		fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
    	}
    	
    	response.setContentType(contentType + ";charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName); // 解决中文
        OutputStream out = response.getOutputStream();
		return out;
	}
    

    /**
     * 文件标题头格式化
     * 
     * @param titile
     * @return
     */
	public String ForMatTitle(String titile) {
		for (int i = 0; i < headList.size(); i++) {
			
			if(i == headList.size() - 1) {
				titile+= headList.get(i) + "\n";
			} else {
				titile+= headList.get(i) + ",";
			}
		}
		return titile;
	}
	
	/**
	 * 内容数据格式化
	 * 
	 * @param mapData
	 * @return
	 */
	public String ForMatDateList(Map<String, Object> mapData) {
		String data = "";
		Map<String, Object> mp = mapData;
		for (int i = 0; i < keyList.size(); i++) {
			if(i == keyList.size() -1) {
				data+=  mp.get(keyList.get(i)) + "\n";
			} else {
				data+= mp.get(keyList.get(i)) + ",";
				data = data.replaceAll("\n", "");
			}
		}
		return data;
	}
    
	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	public List<String> getHeadList() {
		return headList;
	}

	public void setHeadList(List<String> headList) {
		this.headList = headList;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public List<List<Map<String, Object>>> getOutList() {
		return outList;
	}

	public void setOutList(List<List<Map<String, Object>>> outList) {
		this.outList = outList;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OutputStream getOus() {
		return ous;
	}

	public void setOus(OutputStream ous) {
		this.ous = ous;
	}

	public Map<String, String> getFormatMap() {
		return formatMap;
	}

	public void setFormatMap(Map<String, String> formatMap) {
		this.formatMap = formatMap;
	}
}
