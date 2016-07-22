package com.pqsoft.wx.base.tools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.ImageUtils;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
/**
 * 工具类
 * @author liangds
 *
 */
public class WeixinTools {
	static Logger logger=Logger.getLogger(WeixinTools.class);
	private WeixinTools(){}

	/**
	 * 签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	 public static Map<String, String> sign(String jsapi_ticket, String url) {
		 Map<String, String> ret = new HashMap<String, String>();
		 String nonce_str = create_nonce_str();
	     String timestamp = create_timestamp();
	     String string1;
	     String signature = "";
	     string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp +"&url=" + url;
	     logger.info(string1);
	     try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
	     logger.info("signature>>"+signature);
	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
		 return ret;
	 }
	 /**
	  * 获取签名后的信息
	  * @param url
	  * @return
	  */
	 public static void getSign(String url,String agentId,VelocityContext context){
		 String jsapi_ticket=WeixinConfig.getAccessTicket(agentId);
		 Map<String,String> m=sign(jsapi_ticket,WeixinConfig.config.get(Parameters.URL_PREFIX)+url);
		 context.put("jsapi_ticket", m.get("jsapi_ticket"));
		 context.put("nonceStr", "'"+m.get("nonceStr")+"'");
		 context.put("timestamp", "'"+m.get("timestamp")+"'");
		 context.put("signature", "'"+m.get("signature")+"'");
		 context.put("appId", "'"+WeixinConfig.getCorpId()+"'");
	 }
	 
	 private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	 private static String create_nonce_str() {
	     return UUID.randomUUID().toString();
	 }
	 private static String create_timestamp() {
	     return Long.toString(System.currentTimeMillis() / 1000);
	 }
	 /**
	  * 解析进来的消息
	  * @param xml
	  * @return
	  * @throws Exception
	  */
	 public static Map<String, String> parseXml(String xml) throws Exception {
			// 解析结果存储在HashMap
			Map<String, String> map = new HashMap<String, String>();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(xml));
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			logger.info("------------------------ xml ----------------------");
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
				logger.info(e.getName() + "\t:\t" + e.getText());
			}
			return map;
		}
	 /**
	  * 从请求中获取进来的url带参数
	  * @param request
	  * @return
	  */
	 public static String getUrl(HttpServletRequest request){
		 String url=SkyEye.getRequest().getRequestURI();
			if(SkyEye.getRequest().getQueryString()!=null && SkyEye.getRequest().getQueryString().length()>0){
				url+="?"+SkyEye.getRequest().getQueryString();
			}
		 return url;
	 }
	 /**
	  * wx上传下载图片信息
	  * @param param
	  * @return
	  */
	 public static Map<String,Object> downImage(Map<String,Object> param,String agentId){
		 Map<String,Object> result=new HashMap<String,Object>();
		 boolean flag=true;
		 String error="上传成功";
		 try{
			 String projectId=(String) param.get("projectId");
			 String wxImgId=(String) param.get("wxImgId");
			 String modeName=(String) param.get("modeName");
			 String parentId=getFileParentId(projectId,modeName);
			 if(StringUtils.isBlank(agentId) || StringUtils.isBlank(projectId) || StringUtils.isBlank(parentId)  || StringUtils.isBlank(wxImgId)  || StringUtils.isBlank(modeName) ){
				 flag=false;
				 error="请求参数为空";
			 }else{
				 //下载图片
				 String uuid=UUID.randomUUID().toString().toLowerCase();
				 String customerPath = SkyEye.getConfig("file.path") + File.separator + "fileUploadDir" + File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())+ File.separator ;
				 String dir = customerPath+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
				 createDirectory(dir);
				 String physicalPath =downloadMedia(WeixinConfig.getAccessToken(agentId), wxImgId, dir,uuid );
				 if(StringUtils.isBlank(physicalPath) || !physicalPath.contains(".")){
					 flag=false;
					 error="从微信服务器下载失败";
				 }else{
					 File file = new File(physicalPath);
					 String fileType = physicalPath.substring( physicalPath.indexOf(".") , physicalPath.length() );
					 String fileName = uuid+fileType;
					 //大图
					 File newfile = new File(dir ,fileName);
					 file.renameTo(newfile);
					//小图
					 String scalePath = ImageUtils.reSizeImage(newfile, 80,80 , true);
					 
					 //保存到表
					 Map<String, Object>  p= new HashMap<String, Object>();
					 p.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
					 p.put("TPM_BUSINESS_PLATE", modeName);
					 p.put("TPM_TYPE", "家访报告");
					 p.put("PDF_PATH", file.getPath());
					 p.put("PDF_PATH_SCALE", scalePath);
					 p.put("NAME", fileName);
					 p.put("PROJECT_ID", projectId);
					 p.put("PARENT_ID", parentId);
					 p.put("CREATE_CODE", Security.getUser()==null? "god" : Security.getUser().getCode());
					 p.put("FILE_LEVEL", "2");
					 p.put("FILE_TYPE", fileType.toUpperCase());
					 p.put("REMARK", "微信家访");
					 Dao.insert("materialMgt.insertFpf", p);
					 result.put("wxImgId", wxImgId);
				 }
			 }
		 }catch(Exception e){
			 flag=false;
			 error="下载图片失败";
			 e.printStackTrace();
		 }
		 result.put("flag", flag);
		 result.put("error", error);
		 return result;
	 }
	 /**
	  * 查找文件的parentId
	  */
	 public static String getFileParentId(String projectId,String modelName){
		 List<Map<String,Object>> list=Dao.execSelectSql(""
		 		+ " select nvl((SELECT id "
		 		+ " from FIL_PROJECT_FILE "
		 		+ "  where project_id ="+projectId+"  "
		 		+ " and TPM_BUSINESS_PLATE ='"+modelName+"' "
		 		+ " and parent_id is null"
		 		+ " and rownum = 1),"
		 		+ " 0) parent_id "
		 		+ " from dual ");
		 return list.get(0).get("PARENT_ID").toString();
	 }
	 
	 /**
	  * 从wx服务器下载图片
	  * @param accessToken
	  * @param mediaId
	  * @param savePath
	  * @return
	  */
	 public static String downloadMedia(String accessToken, String mediaId, String savePath,String uuid) {
			String filePath = null;
			String requestUrl = Parameters.MEDIA_URL+"?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
			logger.info(requestUrl);
			try {
				new File(savePath).mkdirs();
				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");

				if (!(savePath.endsWith("/"))) {
					savePath = savePath + "/";
				}

				String fileExt = WeixinUtil.getFileEndWitsh(conn.getHeaderField("Content-Type"));

				filePath = savePath+uuid+fileExt;

				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1)
					fos.write(buf, 0, size);
				fos.close();
				bis.close();

				conn.disconnect();
				String info = String.format("下载媒体文件成功，filePath=" + filePath, new Object[0]);
				logger.info(info);
			} catch (Exception e) {
				filePath = null;
				String error = String.format("下载媒体文件失败：%s", new Object[] { e });
				logger.info(error);
			}
			return filePath;
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
	  * 检查用户权限
	  * @param roleName
	  * @return
	  */
	 public static boolean checkRole(String roleName){
		boolean result=false;
		if(Security.getUser()!=null && Security.getUser().getRoles()!=null){
			List<String> roles=Security.getUser().getRoles();
			logger.info("user role>>"+roleName+">>"+roles.toString());
			for(String role : roles){
				if(roleName.equals(role)){
					result=true;
					break;
				}
			}
		}
		return result;
	 }
	 /**
	  * 
	  * @param appsId
	  * @param assessToken
	  * @param assessTicket
	  */
	 public static void saveTokenToDB(String appsId,String assessToken,String assessTicket){
		 try{
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("APPS_ID", appsId);
			 map.put("ACCECC_TOKEN", assessToken);
			 map.put("ACCESS_TICKET", assessTicket);
			 Dao.insert("weixin.updateToken", map);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	 }
	 /**
	  * 通过jz_code和应用ID获取对应系统的登录用户
	  * @param jzCode
	  * @param agentId
	  * @return
	  */
	 public static String getLoginUserCode(String jzCode,String agentId){
		 String userCode="-5";
		 try{
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("JZ_CODE", jzCode.toUpperCase());
			 map.put("AGENT_ID", agentId);
			 Map<String,Object> result= Dao.selectOne("weixin.getWxUser", map);
			 if(result!=null && !result.isEmpty())
				 userCode=result.get("USER_CODE").toString();
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return userCode;
	 }
}
