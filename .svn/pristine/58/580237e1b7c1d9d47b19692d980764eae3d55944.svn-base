package com.pqsoft.base.suppliersInfo.service;

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

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.BaseUtil;

public class CreditDossierService {
	
	String basePath = "suppCreditDossier.";

    /**        ---------------------------------------------债务档案---------------------          **/
	public Page getDebtListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getDebtPageList", map)), 10);
		return page;
	}

	public int doDeleteDebt(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteDebt", m);
	}
	public int saveDebt(Map<String,Object> param){
		return Dao.insert(basePath+"saveDebt",param);
	}
	public int updateDebt(Map<String,Object> param){
		return Dao.update(basePath+"updateDebt",param);
	}
	/**
     * 银行征信
     */
	public Page getPaymentListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getPaymentListPage", map)), 10);
		return page;
	}

	public int doDeletePayment(Map<String,Object> m) {
		return Dao.delete(basePath+"deletePayment", m);
	}
	public int savePayment(Map<String,Object> param){
		return Dao.insert(basePath+"savePayment",param);
	}
	public int updatPayment(Map<String,Object> param){
		return Dao.update(basePath+"updatePayment",param);
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

			String customerPath = File.separator + "SuppCreditRepayment";
	
			final long MAX_SIZE = 524288*2*1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			
			String root =  SkyEye.getConfig("file.path").toString();
			// 拿到配置文件中设置的上传文件路径
	
			BaseUtil.createDirectory(root);// 调用创建存放上传文件 文件夹方法
	
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
	
					BaseUtil.createDirectory(dir);
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

    /**        ---------------------------------------------信誉档案---------------------          **/

	
	public Page getReputationListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getReputationPageList", map)), 10);
		return page;
	}

	public int doDeleteReputation(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteReputation", m);
	}
	public int saveReputation(Map<String,Object> param){
		return Dao.insert(basePath+"saveReputation",param);
	}

	public int updateReputation(Map<String,Object> param){
		return Dao.insert(basePath+"updateReputation",param);
	}
	


    /**        ---------------------------------------------法院档案---------------------          **/
	
	
	public Page getCourtListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getCourtPageList", map)), 10);
		return page;
	}

	public int doDeleteCourt(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteCourt", m);
	}
	public int saveCourt(Map<String,Object> param){
		return Dao.insert(basePath+"saveCourt",param);
	}

	public int updateCourt(Map<String,Object> param){
		return Dao.insert(basePath+"updateCourt",param);
	}
	

    /**        ---------------------------------------------其他资产档案---------------------          **/
	
	
	public Page getOtherListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getOtherPageList", map)), 10);
		return page;
	}

	public int doDeleteOther(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteOther", m);
	}
	public int saveOther(Map<String,Object> param){
		return Dao.insert(basePath+"saveOther",param);
	}

	public int updateOther(Map<String,Object> param){
		return Dao.insert(basePath+"updateOther",param);
	}
}
