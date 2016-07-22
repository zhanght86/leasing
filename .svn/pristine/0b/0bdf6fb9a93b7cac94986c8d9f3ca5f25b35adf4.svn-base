//package com.pqsoft.advert.action;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.InputStream;
//import java.io.Reader;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import oracle.sql.BLOB;
//import oracle.sql.CLOB;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.advert.service.AdvertService;
//import com.pqsoft.notice.service.NoticeService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.User;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
//public class AdvertManagerAction extends Action {
//
//	 private static final String basePath = "advert/";
//	   AdvertService service=new AdvertService();
//	   
//	    @aAuth(type = aAuth.aAuthType.USER)
//		@aPermission(name = {"客户管理","广告管理","查询" })
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply execute() {
//			VelocityContext context = new VelocityContext();
//			Map<String, Object> param = _getParameters();
//			context.put("PContext", param);
//			return new ReplyHtml(VM.html(basePath + "AdManager.vm", context));
//		}
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aPermission(name = {"客户管理","广告管理","查询" })
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply pageData() {
//			Map<String, Object> param = _getParameters();
//			Page pagedata = service.getPageData(param);
//			return new ReplyAjaxPage(pagedata);
//		}
//		
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aPermission(name = {"客户管理","广告管理","添加" })
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply toAdd() {
//			VelocityContext context = new VelocityContext();
//			return new ReplyHtml(VM.html(basePath + "advertAdd.vm", context));
//		}
//		
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aPermission(name = {"客户管理","广告管理","添加" })
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply doAdd() {
//			VelocityContext context = new VelocityContext();
//			try{
//				List<FileItem> fileList = _getFileItem();
//				Map<String, Object> param = _getParameters();
//				param.put("ID", service.selectSeq());
//				Map<String,Object> map=new HashMap<String, Object>();
//				map.put("PRO_ID", param.get("ID"));
//				Iterator<?> i = fileList.iterator();
//				File file = null;
//				while (i.hasNext()) {
//					FileItem fileitem = (FileItem) i.next();
//					if (!fileitem.isFormField()) {// 如果是文件
//						if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
//							byte[] buffer=fileitem.get();
//							map.put("IMAGE", buffer);
//							service.saveAdImage(map);
//							
//						}
//					}else{
//						param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
//					}
//				}
//				if(1==service.saveAd(param)){
//					return new ReplyAjax(true, "添加成功");
//				}else{
//					return new ReplyAjax(false, "添加失败");
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//				return new ReplyAjax(false, "添加失败");
//			}
//		}
//		@aPermission(name = {"客户管理","广告管理","修改" })
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply toUpdate() {
//			    VelocityContext context = new VelocityContext();
//			    Map<String, Object> param = _getParameters();
//				Map<String,Object> map=service.selectAdById(param);
//				List<Map<String,Object>> imageList=service.selectImageByPro_Id(param);
//				for(int i=0;i<imageList.size();i++){
//					Map<String,Object> image=imageList.get(i);
//					context.put("ID"+(i+1), image.get("ID"));
//				}
//				map.put("PRO_INFO", ClobToString((CLOB)map.get("PRO_INFO")));
//				context.put("ad", map);
//				return new ReplyHtml(VM.html(basePath + "advertEdit.vm", context));
//		       
//		}
//		
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aPermission(name = {"客户管理","广告管理","修改" })
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply doUpdate() {
//			VelocityContext context = new VelocityContext();
//			try{
//				List<FileItem> fileList = _getFileItem();
//				Map<String, Object> param = _getParameters();
//				Map<String,Object> map=new HashMap<String, Object>();
//				for(FileItem fileitem :fileList){
//					if (fileitem.isFormField()) {
//						param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
//					}
//				}
//				map.put("PRO_ID", param.get("ID"));
//				Iterator<?> i = fileList.iterator();
//				File file = null;
//				while (i.hasNext()) {
//					FileItem fileitem = (FileItem) i.next();
//					if (!fileitem.isFormField()) {// 如果是文件
//						if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
//							byte[] buffer=fileitem.get();
//							map.put("IMAGE", buffer);
//							if(!"".equals(fileitem.getFieldName())){
//								map.put("ID", fileitem.getFieldName());
//								service.delImageById(map);
//							}
//							service.saveAdImage(map);
//							
//						}
//					}
//				}
//				if(1==service.updateAdById(param)){
//					return new ReplyAjax(true, "保存成功");
//				}else{
//					return new ReplyAjax(false, "保存失败");
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//				return new ReplyAjax(false, "保存失败");
//			}
//		}
//		
//		@aPermission(name = {"客户管理","广告管理","修改" })
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply downloadPictrue() {
//			  Map<String, Object> param = _getParameters();
//				try{
//				Map<String,Object> map=service.selectImageById(param);
//				BLOB blob = (BLOB) map.get("IMAGE");
//				SkyEye.getResponse().setContentType("image/png");
//				InputStream in = new BufferedInputStream(blob.getBinaryStream());   
//				byte[] buf = new byte[1024]; 
//				int hasRead=0;
//				while((hasRead=in.read(buf))>0)
//				{      
//					 SkyEye.getResponse().getOutputStream().write(buf, 0, hasRead);
//				} 
//	            SkyEye.getResponse().getOutputStream().flush();
//	            SkyEye.getResponse().getOutputStream().close();
//	            in.close();
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//				return null;
//		       
//		}
//		
//		@aPermission(name = {"客户管理","广告管理","删除" })
//		@aAuth(type = aAuth.aAuthType.USER)
//		@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
//		public Reply delAd() {
//			VelocityContext context = new VelocityContext();
//			
//			Map<String, Object> param = _getParameters();
//			service.delImageByProId(param);
//			if(1==service.delAdById(param)){
//				return new ReplyAjax(true, "删除成功");
//			}else{
//				return new ReplyAjax(false, "删除失败");
//			}
//		       
//		}
//		public static String ClobToString(CLOB clob) {  
//	        String reString = "";  
//	        try{
//	        Reader is = clob.getCharacterStream();// 得到流  
//	        BufferedReader br = new BufferedReader(is);  
//	        String s = br.readLine();  
//	        StringBuffer sb = new StringBuffer();  
//	        // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING  
//	        while (s != null) {  
//	            sb.append(s);  
//	            s = br.readLine();  
//	        }  
//	        reString = sb.toString();  
//	        }catch(Exception e){
//	        	e.printStackTrace();
//	        }
//	        return reString;  
//	    }  
//}
