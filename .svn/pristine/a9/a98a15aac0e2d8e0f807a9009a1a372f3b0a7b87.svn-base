package com.pqsoft.gls.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.Resource;

import com.pqsoft.gls.service.GlsPjtLocationService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.JsonUtil;
/**
 * 营销平台
 * @author czy
 *
 */
public class GlsPjtLocationAction extends Action {

	private String path = "gls/";
	private GlsPjtLocationService service= new GlsPjtLocationService();
	private static VelocityContext context = new VelocityContext();  
	public Reply execute() {
		
		return new ReplyHtml(VM.html(path+"glsMg.vm", null));
	}
	/**
	 * 省
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
    public Reply getGlsPjtLocationProvince() {
		Map<String, Object> param = _getParameters();
		if (!"".equals(param.get("parentId"))&&null!=param.get("parentId")) {
			param.put("parentId", Integer.parseInt(param.get("parentId").toString()));
		};
		if (!"".equals(param.get("areaLevel"))&&null!=param.get("areaLevel")) {
			int parentId=(Integer) param.get("parentId");
			if(parentId==100439000||parentId==100246608||parentId==100010003||parentId==100009998){
				param.put("areaLevel", 4);
			}else{
				param.put("areaLevel", Integer.parseInt(param.get("areaLevel").toString()));
			}
		};
		ReplyAjax ajax=new ReplyAjax(service.getPage(param));
		return ajax;
	}
	
	/**
	 * 跟进，多个文件
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply addGlsMore() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		JSONArray list=JSONArray.fromObject(param.get("list"));
		//JSONObject list = JSONObject.fromObject(param.get("list"));
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		/*文件list*/
		param.put("num", list.size());
		param.put("list", list);
		ReplyAjax ajax=new ReplyAjax(service.doAddGlsMore(param));
		return ajax;
	} 
	
	
	/**
	 * 保存文件
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply saveFiles() {
		/*文件list*/
		List<FileItem> fileList = _getFileItem();
		List<GlsFile> fileList2=new ArrayList<GlsFile>();
		if(fileList.size()>0){
			for (int i = 0; i < fileList.size(); i++) {
				FileItem fileItem=fileList.get(i);
				if(null==fileItem.getName()||"".equals(fileItem.getName())){
					fileList.remove(i);
					continue;
				};
				List<FileItem> newFileList=new ArrayList<FileItem>();
				newFileList.add(fileList.get(i));
				Map<String, Object> fileMap = service.uploadFileForOne(newFileList,"genjinjilu");
				GlsFile glsFile=new GlsFile();
				glsFile.setFILE_NAME(fileMap.get("FILE_NAME").toString());
				glsFile.setFILE_PATH(fileMap.get("FILE_PATH").toString());
				glsFile.setFILE_TYPE(fileMap.get("FILE_TYPE").toString());
				glsFile.setFILE_SIZE(Long.parseLong(fileMap.get("FILE_SIZE").toString()));
				fileList2.add(glsFile);
			}
		}
		return new ReplyAjax(fileList2);
	} 
	
	/**
	 * 获取跟进记录
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getGlsDataList() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.getGlsDataList(param));
	}
	
	/**
	 * 获取跟进记录(废弃)
	 */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getGlsFileDataList() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		//ReplyAjax ajax=new ReplyAjax(service.getGlsFileDataList(param));;
		return null;
	}
	
	/**
	 * 获取文件通过营销id
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getFileListByProjectId() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getFileListByProjectId(param));
	}
	
	
	/**
	 * 删除文件通过id
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply delProjectFileByFileId() {
		Map<String, Object> param = _getParameters();
		param.put("SCOPETYPE", 0);
		return new ReplyAjax(service.delProjectFileByFileId(param));
	}
	
	/**
	 * 删除跟进记录通过id
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply delGlsZDataById() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.delGlsZDataById(param));
	}
	
	
	/**
	 * 导出excel
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply exportExcel(){
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		BaseUtil.getDataAllAuth(param);
		
		/**获取用户权限id及下属id--start**/
		param.put("ORG_CHILDREN", Security.getUser().getOrg().getId());
		JSONObject jsonOrg = JSONObject.fromObject( Dao.selectOne("getOrgs",param));
		/**增加权限范围限制**/
		param.put("ORG_CHILDREN", jsonOrg.getString("ORG_CHILDREN"));
		
		if(null==param.get("orderName")||"".equals(param.get("orderName").toString())){
			param.put("orderName", "ID");
			param.put("orderType", "ASC");
		}
		
		List<Map<String,Object>> dataList = Dao.selectList("glspjtlocation.getPageList", param);
		String[] titles={"营销编号","项目名称","项目简称 ","客户名称 ","当前状态","估计金额（万元）","跟进人 ","跟进日期"};
				//service.getData(new HashMap<String,Object>());
		OutputStream ouputStream = null;
		try {
			Resource resource = UTIL.RES.getResource("classpath:/content/gls/营销模版.xls");
			Workbook wb = WorkbookFactory.create(resource.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			Row rowTitle = sheet.getRow(0);
		    
			for (int j = 0; j < titles.length; j++) {
				rowTitle.createCell(j).setCellValue(titles[j]);
			}
			
			for (int i = 0; i < dataList.size(); i++) {
				//PRO_CODE,PJT_NAME,PJT_SHOT_NAME,CUS_NAME,STATUS,PRICE,GENJINREN,GENJIN_DATE
				Row row = sheet.createRow(i+1);
					int num=0;
					Map<String,Object> objectMap=dataList.get(i);
					if(null!=objectMap.get("PRO_CODE")&&!"".equals(objectMap.get("PRO_CODE"))){
						row.createCell(num).setCellValue(objectMap.get("PRO_CODE").toString());
						
				    }
					num++;
					if(null!=objectMap.get("PJT_NAME")&&!"".equals(objectMap.get("PJT_NAME"))){
						row.createCell(num).setCellValue(objectMap.get("PJT_NAME").toString());
						
				    }
					num++;
					if(null!=objectMap.get("PJT_SHOT_NAME")&&!"".equals(objectMap.get("PJT_SHOT_NAME"))){
						row.createCell(num).setCellValue(objectMap.get("PJT_SHOT_NAME").toString());
						
				    }
					num++;
					if(null!=objectMap.get("CUS_NAME")&&!"".equals(objectMap.get("CUS_NAME"))){
						row.createCell(num).setCellValue(objectMap.get("CUS_NAME").toString());
						
				    }
					num++;
					if(null!=objectMap.get("STATUS")&&!"".equals(objectMap.get("STATUS"))){
						row.createCell(num).setCellValue(objectMap.get("STATUS").toString());
						
				    }
					num++;
					if(null!=objectMap.get("PRICE")&&!"".equals(objectMap.get("PRICE"))){
						row.createCell(num).setCellValue(objectMap.get("PRICE").toString());
						
				    }
					num++;
					if(null!=objectMap.get("GENJINREN")&&!"".equals(objectMap.get("GENJINREN"))){
						row.createCell(num).setCellValue(objectMap.get("GENJINREN").toString());
						
				    }
					num++;
					if(null!=objectMap.get("GENJIN_DATE")&&!"".equals(objectMap.get("GENJIN_DATE"))){
						row.createCell(num).setCellValue(objectMap.get("GENJIN_DATE").toString());
						
				    }
					num++;
			}
			
			String fileName = "营销导出"+new SimpleDateFormat("yyyyMMddHHmmss")
								.format(Calendar.getInstance().getTime());
			fileName = new String(fileName.getBytes(), "iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");  
	        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");  
	        ouputStream = response.getOutputStream();  
	        wb.write(ouputStream);  
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(ouputStream!=null){
					ouputStream.flush();
					ouputStream.close();  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	/**
	 * 删除跟进记录通过id
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply testForm() {
		System.out.println("testForm----");
		
		Map<String, Object> param = _getParameters();
		return null;
	}
	
	/**
	 * 保存批示id
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply saveInstructionByGlsId() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("ORD_ID", Security.getUser().getOrg().getId());
		ReplyAjax ajax=new ReplyAjax(service.saveInstructionByGlsId(param));
		return ajax;
	}
	
	/**
	 * 批示列表
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply queryInstructByGlsId() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("ORD_ID", Security.getUser().getOrg().getId());
		ReplyAjax ajax=new ReplyAjax(service.queryInstructByGlsId(param));
		return ajax;
	}
	
	
	/**
	 * to-glsMgView.vm
	 * */
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name ={"营销平台"})
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toGlsMgViewVm() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		context.put("ID", param.get("ID"));
		context.put("ORD_ID", param.get("ORD_ID"));
		return new ReplyHtml(VM.html(path+"glsMgView.vm", context));
	}
	
}