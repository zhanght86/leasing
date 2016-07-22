package com.pqsoft.reCreditManagement.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;

import com.pqsoft.reCreditManagement.service.ReCreditManagementService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.FileUtil;

public class ReCreditManagementAction extends Action {
	
	private final String pagePath="reCreditManagement/";

	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","添加授信"})
	@SuppressWarnings("unchecked")
	public Reply toAddReCredit(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		String id = map.get("FHFA_ID")==null?"1":map.get("FHFA_ID").toString();//融资机构id
		String type = map.get("type")==null?"1":map.get("type").toString();//操作类型
		ReCreditManagementService service = new ReCreditManagementService();
		//获取融资机构信息
		try {
			map = (Map<String,Object>) service.getFhfaById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.put("GRANTPLAN", map);
		if(type.equals("1")){//添加授信
			return new ReplyHtml(VM.html(pagePath+"toAddReCredit.vm", context));
		}else if(type.equals("0")){//查看授信
		//	String cugpID=map.get("ID").toString();
			return new ReplyHtml(VM.html(pagePath+"toShowReCredit.vm", context));
		}else{
			return new ReplyHtml(VM.html(pagePath+"toAddReCredit.vm", context));
		}		
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","添加授信"})
	public Reply addGrantplan() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			//上传文件路径
			String filedirpath = (String)SkyEye.getConfig("file.path");
			filedirpath = filedirpath != null ? filedirpath.toString()+File.separator+"reCreditManagement":"/pqsoft/file";
			File dir = new File(filedirpath);
			dir.mkdirs(); //创建文件夹
			final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(4096);////设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
			dfif.setRepository(dir);// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
			
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			sfu.setHeaderEncoding("UTF-8");
			sfu.setSizeMax(MAX_SIZE);
			List<?> fileList = sfu.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			@SuppressWarnings("unused")
			String type = null;
			File file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("type")) {
						
						type = fileItem.getString();
					}
					map.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"),"UTF-8"));
				} else {
					if (fileItem.getFieldName().equals("file")) {
						//文件路径
						file = new File(SkyEye.getConfig("file.path")
								+ File.separator+"reCreditManagement"
								+ File.separator
								+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance()
										.getTime()) + "_"+UUID.randomUUID()+ fileItem.getName());
						fileItem.write(file);
						
						map.put("FILE_PATHURL",file.getPath());//返回文件路径
					}
					
				}
			}
		}catch(Exception ex){
			ex.getStackTrace();
		}
		
		
		//添加授信信息
		VelocityContext context = new VelocityContext();
		map.put("MODIFY_ID", Security.getUser().getCode());//用户编号
		// 添加授信信息
		map.put("LAST_PRICE", map.get("GRANT_PRICE"));
		Map<String, Object> map1 = new HashMap<String, Object>(map);
		ReCreditManagementService service = new ReCreditManagementService();
	
		int i = service.addT_REFUND_GRANTPLAN(map1);
		boolean flag = false;
		if(i>0){
			flag = service.toAddCredit(map1);
		}else {
			flag = false;
		}
		context.put("message", 1);
		context.put("ID", map.get("FAFH_ID").toString());
		
		
		
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构", "添加授信", map.get("MODIFY_ID").toString()));
	}
	
	/***************************************************************************************************************
	 *                                                                                                             *
	 *                                              以下为授信查看                                                                                                                                     *
	 *                                                                                                             *
	 **************************************************************************************************************/
	 
	/** queryGrantplanList
	 * @date 2013-10-14 下午04:45:49
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","列表显示"})
	//授信查看类表
	public Reply queryGrantplanList(){
		Map<String,Object> map=_getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println(map);
		ReCreditManagementService service = new ReCreditManagementService();
		context.put("GRANTPLANLIST", service.queryGrantplanByFhfaId(map.get("FK_ID").toString()));
		return new ReplyHtml(VM.html(pagePath+"toShowReCredit.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","取消授信"})
	//授信取消
	public Reply creditCancel() {
		Map<String,Object> mapu = _getParameters();
		mapu.put("USERCODE", Security.getUser().getCode());//用户编号
		int k = 0;
		boolean flag = false;
		try{
			ReCreditManagementService service = new ReCreditManagementService();
			Map<String,Object> a=service.SelT_REFUND_FHFA((String) mapu.get("FK_ID"));
//			Map<String,Object> b=service.SelT_REFUND_GRANTPLAN(mapu);

			double BALANCE=Double.parseDouble((String)a.get("BALANCE"));
//			double GRANT_PRICE=Double.parseDouble((String)b.get("GRANT_PRICE"));

			int i = 0;
			int j = 0;
			int m = service.updateGrantplanStatus(mapu);
			Map<String,Object> m1 = service.Total((String) mapu.get("FK_ID"));
			if(m1==null){
				mapu.put("CREDIT_STATUS", "0");
				i = service.UpdT_REFUND_FHAH(mapu);
			}else{
				double total=Double.parseDouble(m1.get("TOTAL_CREDIT").toString());
				if(BALANCE>total){
					mapu.put("BALANCE1", total);
				}
				mapu.put("TOTAL_CREDIT1",m1.get("TOTAL_CREDIT"));//授信总额更新
				mapu.put("CREDIT_BIN_DEADLINE", m1.get("CREDIT_BIN_DEADLINE"));//授信起始时间更新
				mapu.put("CREDIT_END_DEADLINE",m1.get("CREDIT_END_DEADLINE"));//授信结束时间更新
				j =service.UpdT_REFUND_FHAH(mapu);
			}
			
			//跟新状态
			k = m+j+i;
		}catch(Exception ex){
			ex.getStackTrace();
		}
	
		if(k>=2){
			flag = true;
		}else {
			flag = true;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构", "取消授信", mapu.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","启动授信"})
	//授信启动
	public Reply startCredit(){
		Map<String,Object> mapu = _getParameters();
		mapu.put("USERCODE", Security.getUser().getCode());//用户编号
		int k = 0;
		boolean flag = false;
		try{
			ReCreditManagementService service = new ReCreditManagementService();
			
			//融资机构查询余额、总额   根据融资机构id
			Map<String,Object> a=service.SelT_REFUND_FHFA((String) mapu.get("FK_ID"));
			
			//融资机构查询余额、总额   根据融资机构id
			Map<String,Object> b=service.SelT_REFUND_GRANTPLAN(mapu);

			double BALANCE=Double.parseDouble((String)a.get("BALANCE"));
			double GRANT_PRICE=Double.parseDouble((String)b.get("GRANT_PRICE"));
			
			//取消授信 修改授信状态
			int m = service.updateGrantplanStatus(mapu);
			Map<String,Object> m1 = service.Total((String) mapu.get("FK_ID"));
			//授信总金额
			double Total = Double.parseDouble((String)m1.get("TOTAL_CREDIT"));
			
			
			double BALANCE_LAST=BALANCE+GRANT_PRICE;
			if(BALANCE_LAST>Total){
				mapu.put("BALANCE1", Total);
			}else{
				mapu.put("BALANCE1", BALANCE_LAST);
			}
			mapu.put("TOTAL_CREDIT1",m1.get("TOTAL_CREDIT"));//授信总额更新
			mapu.put("CREDIT_BIN_DEADLINE", m1.get("CREDIT_BIN_DEADLINE"));//授信起始时间更新
			mapu.put("CREDIT_END_DEADLINE",m1.get("CREDIT_END_DEADLINE"));//授信结束时间更新
			mapu.put("CREDIT_STATUS", 1);
			
			//更改融资机构授信金额
		    int j =service.UpdT_REFUND_FHAH(mapu);
		    
		    //更新状态判断
		    k=m+j;
		}catch(Exception ex){
			ex.getStackTrace();
		}
		if(k>=2){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构", "启动授信", mapu.get("USERCODE").toString()));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","列表显示"})
	//查看授信详细信息
	public Reply selGrantplan(){
		Map<String,Object> map = _getParameters();
		ReCreditManagementService service = new ReCreditManagementService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> mapq;
		try {
			mapq = service.SelT_REFUND_GRANTPLAN(map);
			context.put("param", mapq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyHtml(VM.html(pagePath+"getCreateReCredit.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","修改授信"})
	//修改授信信息详细信息
	public Reply GrantplanUpd(){
		Map<String,Object> map = _getParameters();
		ReCreditManagementService service = new ReCreditManagementService();
		Map<String , Object> mapq=service.SelupRecheck(map);
		VelocityContext context = new VelocityContext();
		context.put("param", mapq);
		return new ReplyHtml(VM.html(pagePath+"ReCreditUpd.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","修改授信"})
	//授信修改操作
	public Reply GrantplanAjaxUpd() {
		Map<String,Object> mapu = _getParameters();
		mapu.put("USERCODE", Security.getUser().getCode());//用户编号
		int k = 0;
		boolean flag = false;
		try{
			ReCreditManagementService service = new ReCreditManagementService();
			Map<String,Object> a=service.SelT_REFUND_FHFA((String) mapu.get("FK_ID"));
			
			int i=service.UpdT_REFUND_GRANTPLAN(mapu);
			
			double b1=Double.parseDouble((String)a.get("BALANCE"));
			double b=Double.parseDouble((String)a.get("TOTAL_CREDIT"));
			double GRANT_PRICE=Double.parseDouble((String)mapu.get("GRANT_PRICE"));
			Map<String,Object> m = service.Total((String) mapu.get("FK_ID"));
			
			if(b1>b){
				mapu.put("BALANCE1", b);//授信余额更新
			}else if((b1+GRANT_PRICE)>b){
				mapu.put("BALANCE1", b);//授信余额更新
			}else{
				b1=b1+GRANT_PRICE;
				mapu.put("BALANCE1", b1);//授信余额更新
			}
			
			mapu.put("TOTAL_CREDIT1",m.get("TOTAL_CREDIT"));//授信总额更新
			mapu.put("CREDIT_BIN_DEADLINE", m.get("START_DATE"));
			mapu.put("CREDIT_END_DEADLINE", m.get("END_DATE"));
			mapu.put("STATUS", mapu.get("REPEAT_CREDIT"));
			int j =service.UpdT_REFUND_FHAH(mapu);
			k=i+j;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(k>0){
			flag = true;
		}else {
			flag = false;
		}
		
		return new ReplyAjax(flag, null).addOp(new OpLog("合作金融机构", "修改授信", mapu.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "合作金融机构","查看授信","查看授信(下载)"})
	//查看文件
	public Reply download(){
		String path=SkyEye.getRequest().getParameter("path");	
		FileUtil.download(path, SkyEye.getResponse());       
       return new ReplyFile(new File(path),"文件下载");
   } 
	
	/***************************************************************************************************************
	 *                                                                                                             *
	 *                                              以上为授信查看                                                                                                                                     *
	 *                                                                                                             *
	 **************************************************************************************************************/
}
