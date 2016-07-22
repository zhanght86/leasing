package com.pqsoft.manufacturerApproval.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.manufacturerApproval.service.ManufacturerApprovalService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ManufacturerApprovalAction extends Action{
	
	private String basePath = "manufacturerApproval/"; 
	private String docPath = "doc/";
	private ManufacturerApprovalService service = new ManufacturerApprovalService();
	private ManageService mgService = new ManageService();
	
	/**
	 *  显示主页面
	 */
	@Override
	@aPermission(name = { "厂商项目审批", "项目审批", "主页面"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(basePath + "manufacturerApprovalShow.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "主页面分页查询"})
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		//数据权限,根据对应的厂商显示信息
		Map comMap = (Map) mgService.getCompanyByUserId(Security.getUser().getId());
		if(comMap != null && comMap.get("COMPANY_ID") != null){
			param.put("COMPANY_ID", comMap.get("COMPANY_ID"));
		}
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	///////////////////////////////////////////////////////////////////////////
	/**
	 *  新增共有方法 
	 */
	/**
	 * 用于上传附件
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongsflc.com", name = "韩晓龙")
	public Reply uploadAttachment(){
		//1.得到上传的文件
		File filetemp = _getFileOne();
		//2.创建厂家审批文件的目录  webapp下 创建manufacturerApprovalFile目录用于存放 附件
		String filepath = "";
		//filepath = SkyEye.getRequest().getSession().getServletContext().getRealPath("/");
		//filepath += "policyFile";
		filepath = "/manufacturerApprovalFile";
		File temp = new File(filepath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		filepath += "/" + filetemp.getName();
		temp = new File(filepath);
		try {
			FileCopyUtils.copy(FileCopyUtils.copyToByteArray(filetemp), temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//4.删除上传的临时文件
		//filetemp.delete();
		Map<String,Object> param = _getParameters();
		//从拼接的请求参数中取出ID
		param.put("PROJECT_ID", param.get("PROJECT_ID").toString());
		param.put("FILE_NAME", temp.getName());
		param.put("FILE_PATH", temp.getPath());
		
		Boolean flag = true;
		String msg = "";
		int result = service.insertManufacturerApprovalFile(param);
		if(result>0){
			flag = true;
			msg = "保存成功！";
		}else{
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	/**
	 * 用于下载文件
	 * 未完成      文件名还是有问题
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply downloadAttachment(){
		Map<String,Object> param = _getParameters();
		Map map = (Map) service.selectManufacturerFileById(param);
		return new ReplyFile(new File((String) map.get("FILE_PATH")),(String) map.get("FILE_NAME"));
	}
	/**
	 *  查询所有的附件
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply selectAllFiles(){
		Map<String,Object> param = _getParameters();
		param.put("PROJECT_ID", param.get("PROJECT_ID"));
		JSONArray json = JSONArray.fromObject(service.selectAllFiles(param));
		return new ReplyJson(json);
	}
	/**
	 *  根据id删除一个附件
	 * @return
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply deleteOneFile(){
		Map<String,Object> param = _getParameters();
		int result = service.deleteManufacturerFileById(param);
		Boolean flag = true;
		String msg = "";
		if(result>0){
			flag = true;
			msg = "删除成功！";
		}else{
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag,msg);
	}
	///////////////////////////////////////////////////////////////////////////
	
	
	/**
	 *  跳转陕汽审批单页面
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "跳转陕汽业务审批单页面" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowSQPage() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		context.put("ProjInfo", map);
		//终端客户判断
		//if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
		if(map.containsKey("FINAL_CUST_ID")){//如果有终端客户
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(map.get("TERMINAL_KH_TYPE").toString())) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", service.selectTerminalNPCustInfo(param));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", service.selectTerminalLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(map.get("KH_TYPE").toString())) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", service.selectNPCustInfo(param));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", service.selectLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}
		//3.得到设备信息
		context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		//4.得到通过总经理审批的时间
		context.put("ZJLTime", service.selectZJLTimeByProjID(param));
		//5.得到信审担当送审意见
		context.put("XS_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "信审担当送审"));
		//6.得到总经理审批意见
		context.put("ZJL_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "总经理审批"));
		//7.得到呼叫中心意见
		context.put("CALL_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "呼叫中心"));
		
		return new ReplyHtml(VM.html(basePath + "manufacturerApprovalSQExamine.vm", context));
	}
	
	/**
	 *  跳转陕汽确认函审核页面
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "跳转陕汽审核导出确认函页面" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowSQConfirmPage() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		context.put("ProjInfo", map);
		//if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
		if(map.containsKey("FINAL_CUST_ID")){//如果有终端客户
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(map.get("TERMINAL_KH_TYPE").toString())) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", service.selectTerminalNPCustInfo(param));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", service.selectTerminalLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(map.get("KH_TYPE").toString())) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", service.selectNPCustInfo(param));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", service.selectLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}
		//3.得到设备信息
		context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		//4.得到通过总经理审批的时间
		//context.put("ZJLTime", service.selectZJLTimeByProjID(param));
		//5.得到信审担当送审意见
		//context.put("XS_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "信审担当送审"));
		//6.得到总经理审批意见
		//context.put("ZJL_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "总经理审批"));
		//7.得到呼叫中心意见
		//context.put("CALL_MEMO", service.selectMemoAndTimeByProjIDAndFlowName(param, "呼叫中心"));
		
		return new ReplyHtml(VM.html(basePath + "manufacturerApprovalSQConfirm.vm", context));
	}
	
	/**
	 *  跳转山重建机审查页面
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "跳转山重建机审查页面" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowJJPage() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//context = getdetail(context, param, service);
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		context.put("ProjInfo", map);
		if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(map.get("TERMINAL_KH_TYPE").toString())) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", service.selectTerminalNPCustInfo(param));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", service.selectTerminalLPCustInfo(param));
			}
			//8.建机和山推仿照陕重汽根据挂车计算台量
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(map.get("KH_TYPE").toString())) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", service.selectNPCustInfo(param));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", service.selectLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}
		//3.得到设备信息
		context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		return new ReplyHtml(VM.html(basePath + "manufacturerApprovalJJExamine.vm", context));
	}
	
	/**
	 *  跳转山推股份审查页面
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "跳转山推股份审查页面" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply toShowSTPage() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		context.put("ProjInfo", map);
		if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(map.get("TERMINAL_KH_TYPE").toString())) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", service.selectTerminalNPCustInfo(param));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", service.selectTerminalLPCustInfo(param));
			}
			//8.建机和山推仿照陕重汽根据挂车计算台量
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(map.get("KH_TYPE").toString())) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", service.selectNPCustInfo(param));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", service.selectLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}
		//3.得到设备信息
		context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		return new ReplyHtml(VM.html(basePath + "manufacturerApprovalSTExamine.vm", context));
	}
	
	/**
	 *  陕汽导出业务审批单Excel
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "陕汽导出业务审批单" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exportExcel(){
		Map<String,Object> param = _getParameters();
		return new ReplySQExcel(param.get("PROJECT_ID").toString());
	}
	
	/**
	 *  陕汽导出业务确认函Excel
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "陕汽导出业务确认函" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exportConfirmExcel(){
		Map<String,Object> param = _getParameters();
		return new ReplySQConfirmExcel(param.get("PROJECT_ID").toString(),getNotNullValue(param,"CAR_SALE_DATE"),getNotNullValue(param,"ADVICE"));
	}
	
	/**
	 *  山重建机导出山重建机厂家确认函Word
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "山重建机厂家确认函导出" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exportSZJJWord(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println("=============走这个===========");
		//将信息放入context  , 需要改变编码
		context = getUnicodeDetail(context, param, service);
		//测试
		String filename = "山重建机厂家确认函";
		//页面控制 个人还是法人模板
		if ("NP".equals(param.get("FILETYPE"))) {
			return new ReplyWordByVM(VM.html(basePath + docPath + "szjjConfirmFileNP.vm", context),filename);
		}else {
			//法人
			return new ReplyWordByVM(VM.html(basePath + docPath + "szjjConfirmFileLP.vm", context),filename);
		}
	}
	
	/**
	 *  山推股份导出山推股份厂家确认函Word
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "山推股份厂家确认函导出" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply exportSTGFWord(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		//将信息放入context  , 需要改变编码
		context = getUnicodeDetail(context, param, service);
		String filename = "山推股份厂家确认函";
		if ("NP".equals(param.get("FILETYPE"))) {
			return new ReplyWordByVM(VM.html(basePath + docPath + "stgfConfirmFileNP.vm", context),filename);
		}else{
			//法人
			return new ReplyWordByVM(VM.html(basePath + docPath + "stgfConfirmFileLP.vm", context),filename);
		}
	}
	
	/**
	 *  保存审批意见
	 */
	@aPermission(name = { "厂商项目审批", "项目审批", "保存审批意见" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply saveAdvice(){
		boolean flag = false;
		String msg = "";
		Map<String,Object> param = _getParameters();
		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
		//先删除之前的保存意见
		service.deleteManufacturerAdvice(param);
		int i = service.insertManufacturerAdvice(param);
		if (i>0) {
			flag = true;
			msg = "记录添加成功";
		}else {
			flag = false;
			msg = "记录添加失败,请重试";
		}
		return  new ReplyAjax(flag,msg);
	}
	
	
/////////////////////////////////////////////以下是共用方法///////////////////////////////////////////////////////	
	//共用方法
	/**
	 *  解决无值 返回 空字符串的问题
	 */
	private static String getNotNullValue(Map map,String key){
		 return map.containsKey(key) ? map.get(key).toString() : "";
	}
	
	public static VelocityContext getdetail(VelocityContext context,Map<String,Object> param,ManufacturerApprovalService service){
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		context.put("ProjInfo", map);
		if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(map.get("TERMINAL_KH_TYPE").toString())) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", service.selectTerminalNPCustInfo(param));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", service.selectTerminalLPCustInfo(param));
			}
			//8.建机和山推仿照陕重汽根据挂车计算台量
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(map.get("KH_TYPE").toString())) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", service.selectNPCustInfo(param));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", service.selectLPCustInfo(param));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", service.selectZCCountByProjID(param));//整车 数量 总价
			context.put("BGCDetail", service.selectBGCCountByProjID(param));//挂车 数量 总价
		}
		//3.得到设备信息
		context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		return context;
	}
	
	//汉字转 unicode 十进制编码
	public static String enCode(String str)
	   {
	    
	   if(str==null)return "";
	   String hs="";

	   try
	   {
	   byte b[]=str.getBytes("UTF-16");
	   //System.out.println(byte2hex(b));
	   for (int n=0;n<b.length;n++)
	   {
	   str=(java.lang.Integer.toHexString(b[n] & 0XFF));
	   if (str.length()==1)
	   hs=hs+"0"+str;
	   else
	   hs=hs+str;
	   if (n<b.length-1)hs=hs+"";
	   }
	   //去除第一个标记字符
	   str= hs.toUpperCase().substring(4);
	   //System.out.println(str);
	   char[] chs=str.toCharArray();
	   str="";
	   for(int i=0;i<chs.length;i=i+4)
	   {
	   str+="&#x"+chs[i]+chs[i+1]+chs[i+2]+chs[i+3]+";";
	   }
	   return str;
	   }
	   catch(Exception e)
	   {
	   System.out.print(e.getMessage());
	   }
	   return str;
	   }
	
	public static Map mapToUnicode10Map(Map temp){
		if (temp == null) {//如果为空不做任何处理
			return temp;
		}
		if (temp.size()<=0) {//如果为空不做任何处理
			return temp;
		}
		Set<String> key = temp.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            temp.put(s, enCode(temp.get(s).toString()));//转码
        }
        Set<String> key2 = temp.keySet();
        for (Iterator it = key2.iterator(); it.hasNext();) {
            String s = (String) it.next();
        }
		return temp;
	}
	//转码详细操作
	public static VelocityContext getUnicodeDetail(VelocityContext context,Map<String,Object> param,ManufacturerApprovalService service){
		//1.得到项目基本信息
		Map map = new HashMap();
		map = (Map) service.selectProjInfo(param);
		//存储几个临时变量
		String TERMINAL_KH_TYPE = map.get("TERMINAL_KH_TYPE").toString();
		String KH_TYPE = map.get("KH_TYPE").toString();
		context.put("ProjInfo", mapToUnicode10Map(map));
		if(map.containsKey("FINAL_CUST_ID") && !"陕重汽".equals(getNotNullValue(map,"MANUFACTURER"))){//如果有终端客户 并且厂商不是陕重汽
			System.out.println("查询的终端客户信息!");
			if ("NP".equals(TERMINAL_KH_TYPE)) {
				//2.得到终端客户(自然人)信息
				context.put("NPcustInfo", mapToUnicode10Map((Map)service.selectTerminalNPCustInfo(param)));
			}else {
				//2.得到终端客户(法人)信息
				context.put("LPcustInfo", mapToUnicode10Map((Map)service.selectTerminalLPCustInfo(param)));
			}
			//8.建机和山推仿照陕重汽根据挂车计算台量
			context.put("ZCDetail", mapToUnicode10Map((Map)service.selectZCCountByProjID(param)));//整车 数量 总价
		}else {
			System.out.println("查询的承租人信息!");
			if ("NP".equals(KH_TYPE)) {
				//2.得到客户(自然人)信息
				context.put("NPcustInfo", mapToUnicode10Map((Map)service.selectNPCustInfo(param)));
			}else {
				//2.得到客户(法人)信息
				context.put("LPcustInfo", mapToUnicode10Map((Map)service.selectLPCustInfo(param)));
			}
			//8.陕重汽根据挂车计算数量 总价
			context.put("ZCDetail", mapToUnicode10Map((Map)service.selectZCCountByProjID(param)));//整车 数量 总价
		}
		//TODO 暂时不处理
		//3.得到设备信息
		//context.put("ProductsInfo", service.selectProductInfoByProjID(param));
		return context;
	}
}