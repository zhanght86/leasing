package com.pqsoft.vehicleInvoice.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.runtime.directive.Foreach;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.InvoiceUtil;

public class VehicleInvoiceService {
	
	private String basePath = "VehicleInvoice.";
	
	public Page getApplyPageData(Map<String,Object> param){
		param.put("PARAM1", "租赁期不上牌");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVehApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVehApplyCount", param));
		return page;
	}
	
	public Page getVerPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVerificationList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVerificationCount", param));
		return page;
	}
	
	public Page getVehSearchPageData(Map<String,Object> param){
		Page page = new Page(param);
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVehSearchList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVehSearhCount", param));
		return page;	
	}
	
	public int updateVehicleMess(Map<String,Object> param){
		return Dao.update(basePath+"upVehicleInvoice", param);
	}
	
	public Excel exportInvoiceExcel(Map<String,Object> param,String type){
		List<Map<String,Object>>  dataList = new ArrayList<Map<String,Object>>();
		if("apply".equals(type.trim().toString())){
			param.put("PARAM1", "租赁期不上牌");
		    dataList = Dao.selectList(basePath+"getExportApplyExeclMess", param);
		}else if("verific".equals(type.trim().toString())){
			dataList = Dao.selectList(basePath+"getExportVerificData", param);
		}else{
			dataList = Dao.selectList(basePath+"getExportSearchData", param);
		}
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		if("search".equals(type.trim().toString())){
			title.put("OBJ_TYPE", "开具对象类型");
			title.put("SUP_SHORTNAME", "供应商");
			title.put("PRO_CODE", "项目编号");
			title.put("OBJ_NAME", "购货单位");
			title.put("OBJ_CODE", "身份证或组织机构代码");
			title.put("OBJ_TELPHONE", "电话");
			title.put("OBJ_ADDRESS", "地址");
			title.put("OBJ_POST", "邮编");
			title.put("SUP_SHORTNAME","供应商");
			title.put("PRO_CODE","项目编号");
			title.put("PRO_NAME","项目名称");
	        title.put("CLIENT_NAME","客户名称");
			title.put("ON_CARD","是否上牌");
			title.put("FIRST_PAYMENT_TIME","首付款验证日期");
			title.put("OBJ_NAME","购货单位（人）");
			title.put("OBJ_CODE"," 组织机构代码/身份证号/纳税识别号");
			//title.put("PROJECT_ID","开票节点");
			title.put("OBJ_ADDRESS","开票对象地址");
			title.put("OBJ_TELPHONE","开票对象电话");
			title.put("OBJ_POST","开票对象邮编");
			title.put("INVOICE_ID","发票号");
			title.put("INVOICE_DATE","发票日期");
			title.put("COMPANY_FULLNAME","机动车辆生产企业名称");
			title.put("ACTUAL_PRODUCT_NAME","车辆类型");
			title.put("ACTUAL_PRODUCT_TYPE","厂牌型号");
			title.put("PRODUCT_ADDRESS","产地");
			title.put("CERTIFICATE_NUM","合格证书");
			title.put("IMPORT_NUM","进口证明书号");
			title.put("INSPEC_NUM","商检单号");
			title.put("ENGINE_TYPE","发动机号");
			title.put("CAR_SYMBOL","车辆识别代号/车架号码");
			title.put("TOTAL_PRICE","价税合计");
			title.put("NAME","销货单位名称");
			title.put("CARD_ID","销货单位纳税人识别号");
			title.put("TEL","销货单位电话");
			title.put("BANK_NUMBER","销货单位账号");
			title.put("ADDR","销货单位地址");
			title.put("BANK","销货单位开户银行");
			title.put("TAX_RATE","增值税税率/征收率");
			title.put("TONNAGE","吨位");
			title.put("LIMIT_NUM","限乘人数");
		}else{
			title.put("SUP_SHORTNAME", "供应商");
			title.put("PRO_CODE", "项目编号");
			title.put("OBJ_NAME", "购货单位");
			title.put("OBJ_CODE", "身份证或组织机构代码");
			title.put("OBJ_TELPHONE", "电话");
			title.put("OBJ_ADDRESS", "地址");
			title.put("OBJ_POST", "邮编");
		}
		//封装excel
		Excel excel = new Excel();
		excel.addData(dataList);
		excel.addTitle(title);
		excel.hasRownum();
		return excel;
	}
    
	public String exportInvoiceXML(Map<String,Object> param ) throws Exception{
		param.put("PARAM1", "租赁期不上牌");
		List<Object> dataList = Dao.selectList(basePath+"getExportXmlMess",param);
		String result = "";
		result = InvoiceUtil.getInvoices(dataList);
		if(result != null && !"".equals(result)){
			for (Object obj : dataList) {
				Map<String,Object> dataMess = (Map<String,Object>)obj;
				//插入机动车票据信息
				dataMess.put("EXPORT_FLAG", "1");
				dataMess.put("CREATOR", Security.getUser().getName());
				Dao.insert(basePath+"addVehicleInvoice", dataMess);
			}
		}
		return result ; 
	}
	
	/**
	 * 
	 * @Description: 上传文件XML	 
	 * @return Map
	 * @throws Exception
	 * @date 2013-8-29
	 */
	public Map<String, Object> uploadFileForOne(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			final long MAX_SIZE = 30 * 1024 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path").toString();//
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			List<?> fileList = fp.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			List<Object> filepathList = new ArrayList<Object>();
			File file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = root+ File.separator+"Vehicle"+File.separator+"xml"
							+ File.separator+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					BaseUtil.createDirectory(dir);
					file = new File(dir + File.separator + UUID.randomUUID()
							+ fileItem.getName());
					fileItem.write(file);
					dataMap.put("FILE_NAME", fileItem.getName());
					dataMap.put("FILE_PATH",file.getPath().toString().replace("\\", "/"));
					filepathList.add(file.getAbsoluteFile());
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return dataMap;
	}
	
	public int delInvoiceMess(Map<String,Object> param){
		return Dao.delete(basePath+"deleteEqInvoice",param);
	}
	
	public int upVehInvoiceStatus(Map<String,Object> param){
		return Dao.update(basePath+"updateInvoiceStatus",param);
	}

	public String invoiceFalseReasion(Map<String, Object> param) {
		
		return Dao.selectOne(basePath+"invoiceFalseReasion",param).toString();
	}
}
