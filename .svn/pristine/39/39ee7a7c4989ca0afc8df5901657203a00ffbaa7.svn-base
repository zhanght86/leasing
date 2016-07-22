package com.pqsoft.white.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.white.service.WhiteListManageService;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class WhiteListManageAction extends Action {

	public VelocityContext context = new VelocityContext();
	private String path = "white/";
	private WhiteListManageService service = new WhiteListManageService();
	private Logger logger = Logger.getLogger(WhiteListManageAction.class);
	
	
	/**
	 * 2016-01-19 新增
	 * 功能：贷后管理，白名单管理列表显示
	 * 
	 * (non-Javadoc)
	 * @see com.pqsoft.skyeye.api.Action#execute()
	 */
	@Override
	@aPermission (name = {"贷后管理","白名单管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public Reply execute() {
		return new ReplyHtml(VM.html(path +"whiteListManage.vm",context));
	}
	
	
	/**
	 * 查询白名单列表数据
	 * 
	 * 白名单管理
	 * 
	 * @return
	 */
	@aPermission (name = {"贷后管理","白名单管理","查询"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public  Reply searchPage() {
		
		Map<String,Object> param = _getParameters();
		// 日期时间
		String REPAYMENT_DATE = param.get("REPAYMENT_DATE") == null ? "" : param.get("REPAYMENT_DATE").toString();
		// 姓名
		String CNAME = param.get("CNAME") == null ? "" : param.get("CNAME").toString();
		if(StringUtils.isBlank(REPAYMENT_DATE) && StringUtils.isBlank(CNAME)) {
			return null;
		}
		
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	/**
	 * 导出全部白名单
	 * 
	 * @return
	 */
	@aPermission (name = {"贷后管理","白名单管理","导出全部"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public  Reply exportExcelAll() {
		
		Map<String,Object> param = _getParameters();
		List<Map<String, Object>> returnList = service.getexportExcelAll(param);
		int h = 0;
		// 格式化委托扣款授权书目录及图片文件
		for (Map<String, Object> map : returnList) {
			String CNAME = map.get("CNAME") == null ? "": map.get("CNAME").toString();
        	String PDF_PATH = map.get("PATH") == null ? "": map.get("PATH").toString();
        	// 日期时间
        	String RECEIVE_DATA = map.get("RECEIVE_DATA") == null ? "" : map.get("RECEIVE_DATA").toString();
        	
        	if(!StringUtils.isBlank(PDF_PATH)) {
        		String[] pathStr = PDF_PATH.split(",");
        		h = pathStr.length;
        		int p = 0;
        		for (String PDF_PATHStr : pathStr) {
	        		String[] strPath = PDF_PATHStr.split("/");
	        		String picPath = strPath[strPath.length - 1]; // 文件名
					String SUFFIX = picPath.substring(picPath.indexOf(".") , picPath.length());  // 后缀名
	        		map.put("CONTENTS_PATH" + p, CNAME + RECEIVE_DATA/**+"/" /*+ CNAME + p + SUFFIX*/);
	        		p++;
        		}
        	}
		}
		
		try {
			Calendar calendar = Calendar.getInstance();
			String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(calendar.getTime());
			
	        return new ReplyFile(zipFiles(returnList,dateTime, h).toByteArray(), dateTime + ".zip");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 根据页面选中导出数据
	 * 
	 * 贷后管理
	 * 白名单管理
	 * 
	 * @return
	 */
	@aPermission (name = {"贷后管理","白名单管理","导出选中"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public  Reply exportExcelCheck() {
		
		Map<String,Object> param = _getParameters();
		// START 数据格式化
		String LID = param.get("LID") == null ? "" : param.get("LID").toString();
		String mapLID = LID.replace("\"", "");
		String[] str = mapLID.split(",");
		List<String> l = new ArrayList<String>();
		for(String s: str) {
			l.add(s);
		}
		param.put("PID", l);
		// END
		
		List<Map<String, Object>> returnList = service.getexportExcelCheck(param);
		int h = 0;
		// 格式化委托扣款授权书目录及图片文件
		for (Map<String, Object> map : returnList) {
			// 名称
			String CNAME = map.get("CNAME") == null ? "": map.get("CNAME").toString();
			// 路径
        	String PDF_PATH = map.get("PATH") == null ? "": map.get("PATH").toString();
        	// 日期时间
        	String RECEIVE_DATA = map.get("RECEIVE_DATA") == null ? "" : map.get("RECEIVE_DATA").toString();
        	
        	// 不为空，则进入
        	if(!StringUtils.isBlank(PDF_PATH)) {
        		String[] pathStr = PDF_PATH.split(",");
        		h = pathStr.length;
        		int p = 0;
        		for (String PDF_PATHStr : pathStr) {
        			String[] strPath = PDF_PATHStr.split("/");
            		String picPath = strPath[strPath.length - 1]; // 文件名
    				String SUFFIX = picPath.substring(picPath.indexOf(".") , picPath.length());  // 后缀名
            		map.put("CONTENTS_PATH" + p, CNAME + RECEIVE_DATA /**+ "/" /*+ CNAME + SUFFIX*/);
            		p++;
				}
        	}
		}
		
		try {
			Calendar calendar = Calendar.getInstance();
			String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(calendar.getTime());
	        return new ReplyFile(zipFiles(returnList, dateTime, h).toByteArray(), dateTime + ".zip");
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/**
	 * 图片预览
	 * 
	 * @return
	 */
	@aPermission (name = {"贷后管理","白名单管理","图片预览"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public  Reply preViewPic() {
		
		Map<String, Object> params = _getParameters();
		String path = params.get("path") == null ? "" :params.get("path").toString();
		if(StringUtils.isBlank(path)) {
			return null;
		}
		String[] pathUrl = path.split(",");
		HttpServletResponse response = SkyEye.getResponse();
		OutputStream os = null;
		
		if(pathUrl.length != 1) {
			
			try {
				byte[] bytes = new byte[1024];
				response.setContentLength(bytes.length);
				os = response.getOutputStream();
				os.write(bytes);
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			for (String str : pathUrl) {
				
				String s = ResMime.get(str.substring(str.lastIndexOf(".")).replace(".", ""));
				if (s != null) response.setContentType(s);
				try {
					InputStream is = new FileSystemResource(str).getInputStream();
					if (is != null) {
						byte[] bytes = FileCopyUtils.copyToByteArray(is);
						response.setContentLength(bytes.length);
						os = response.getOutputStream();
						os.write(bytes);
						os.flush();
					} else {
						this.logger.warn("\tdosen't find resource : " + path);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * 下载数据和相关图
	 * 
	 * 贷后管理
	 * 白名单管理
	 * 
	 * @return
	 */
	@aPermission (name = {"贷后管理","白名单管理","导出选中"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "lizhiheng", email = "zhihengli@jiezhongchina.com", name = "lzh")
	public  Reply exportExcelCheckPic() {
		
		Map<String,Object> param = _getParameters();
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		returnList.add(param);
		
		try {
			Calendar calendar = Calendar.getInstance();
			String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(calendar.getTime());
			
			return new ReplyFile(zipFiles(returnList, null, 0).toByteArray(), dateTime + ".zip");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return null;
	}

	
	/**
     * 文件头数据
     * 
     * @return
     */
    private static List<String> getExportHeadList(int h){
    	
    	
        List<String> headList = new ArrayList<String>();
        headList.add("客户姓名");
        headList.add("身份证号");
        headList.add("手机号");
        headList.add("银行类型");
        headList.add("银行账户");
        headList.add("支行名称");
        if(h == 0 || h ==1) {
        	headList.add("委托扣款授权书目录");
    	} else {
    		headList.add("委托扣款授权书目录");
    		headList.add("委托扣款授权书目录");
    	}
        
        
        return headList;
    }
    
    
    /**
     * KEY值
     * @return
     */
    private static List<String> getExportKeyList(int h){
    	
    	
        List<String> headList = new ArrayList<String>();
        headList.add("CNAME");
        headList.add("ID_CARD_NO");
        headList.add("TEL_PHONE");
        headList.add("BANK_NAME");
    	headList.add("BANK_ACCOUNT");
    	headList.add("BRANCH");
    	if(h == 0 || h ==1) {
    		headList.add("CONTENTS_PATH0");
    	} else {
    		
    		headList.add("CONTENTS_PATH0");
    		headList.add("CONTENTS_PATH1");
    	}
    	
        
        return headList;
    }
    
    
    /**
     * 数据内容
     * @return
     */
    private static Map<String,String> getExportFormat() {
    	
        Map<String,String> headMap = new HashMap<String, String>();
        headMap.put("no","String");
        headMap.put("months", "String");
        headMap.put("province", "String");
        headMap.put("city", "String");
        headMap.put("addCount", "Integer");
        headMap.put("addCount", "Integer");
        headMap.put("addCount", "Integer");
        
        return headMap;
    }
    
    /**
	 * 
	 * @description：压缩文件操作
	 * @param filePath
	 *            要压缩的文件路径
	 * @param descDir
	 *            压缩文件保存的路径
	 * @return 
	 */
	public static ByteArrayOutputStream zipFiles(List<Map<String,Object>> list,String fileName, int h) throws Exception {
		ZipOutputStream zos = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File directory = null;
		try {
			// 创建一个Zip输出流
			zos = new ZipOutputStream(baos);
			if (!StringUtils.isBlank(fileName)) {
				// 设定保存临时文件路径
				String path = "/pqsoft/file/excel/" + System.currentTimeMillis() + "/";
				File pathFile = new File(path);
				// 判断文件夹是否为空，为空则创建
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				// 文件名称
				fileName = fileName + ".xls";
				// 全路径
				String pathName = path + fileName;
				// 输出
				FileOutputStream fileOut = new FileOutputStream(pathName);
		        ExportExcelUtils eeu = new ExportExcelUtils();
				SXSSFWorkbook workbook = new SXSSFWorkbook();
				eeu.exportSXSSFExcel(workbook, 0, getExportHeadList(h), list, getExportKeyList(h));
				workbook.write(fileOut);
				
				directory = new File(pathName); //设定为当前文件夹
				fileOut.close();
				if (directory.exists()) {
					zipFile(zos, null, null, directory, null);
				}
			}
	         
			// 启动压缩
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// 名称
				String CNAME = map.get("CNAME") == null ? "": map.get("CNAME").toString();
				// 路径地址
            	String PDF_PATH = map.get("PATH") == null ? "": map.get("PATH").toString();
            	// 日期时间
            	String RECEIVE_DATA = map.get("RECEIVE_DATA") == null ? "" : map.get("RECEIVE_DATA").toString();
            	
				if(!StringUtils.isBlank(PDF_PATH)) {
					String[] path = PDF_PATH.split(",");
	        		for (String PDF_PATHStr : path) {
						File file = new File(PDF_PATHStr);
						// 
						if (file.exists()) { // && file.isDirectory()判断是否为目录
							zipFile(zos, CNAME, file, null, RECEIVE_DATA);
						}
	        		}
				}
				
			}
			return baos;
//			System.out.println("******************压缩完毕********************");
		} 
		finally {
			try {
				if (zos != null){
					zos.close();
					// 不为空则删除文件
					if(directory != null) {
						String p = directory.getPath();
						File f = new File(p); // 输入要删除的文件位置
						boolean result = f.delete();
						if(!result) {
						    System.gc();
						    f.delete();
						   }
					}
					
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * @description：压缩单个文件到目录中
	 * @param zos
	 *            zip输出流
	 * @param oppositePath
	 *            图片 在zip文件中的相对路径
	 * @param filePic
	 *            要压缩的图片文件
	 * @param fileExcel
	 *            要压缩的EXCEL文件
	 */
	public static void zipFile(ZipOutputStream zos, String oppositePath, File filePic, File fileExcel, String date) throws Exception {
		
		// 创建一个Zip条目，每个Zip条目都是必须相对于根路径
		InputStream isPic = null;
		InputStream isExcel = null;
		try {
			
			// 判断EXCEL文件是否为空，不为空则进行压缩
			if(fileExcel != null) {
				// 相对压缩路径
				ZipEntry entryExcel = new ZipEntry(fileExcel.getName());
//				System.out.println(fileExcel.getPath());
				// 将条目保存到Zip压缩文件当中
				zos.putNextEntry(entryExcel);
				// 从文件输入流当中读取数据，并将数据写到输出流当中.
				isExcel = new FileInputStream(fileExcel);

				int length = 0;
				int bufferSize = 1024*1024;
				byte[] buffer = new byte[bufferSize];

				// 写入
				while ((length = isExcel.read(buffer, 0, bufferSize)) >= 0) {
					zos.write(buffer, 0, length);
				}
				fileExcel.deleteOnExit(); // 删除文件
			} else {
			
				String name = filePic.getName() == null ? "" : filePic.getName();
				String SUFFIX = name.substring(name.indexOf(".") , name.length()); 
				ZipEntry entryPic = new ZipEntry(oppositePath + date+ "/"+ oppositePath + SUFFIX );
				// 将条目保存到Zip压缩文件当中
				zos.putNextEntry(entryPic);
				// 从文件输入流当中读取数据，并将数据写到输出流当中.
				isPic = new FileInputStream(filePic);
	
				int length = 0;
				int bufferSize = 1024*1024;
				byte[] buffer = new byte[bufferSize];
	
				while ((length = isPic.read(buffer, 0, bufferSize)) >= 0) {
					zos.write(buffer, 0, length);
				}
			}
			zos.closeEntry();
		}
		finally {
			try {
				if (isPic != null) {
					isPic.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
