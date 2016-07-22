package com.pqsoft.screened.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.screened.service.ScreenedService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ScreenedAction extends Action {
	private final ScreenedService service=new ScreenedService();
	private final Logger  logger = Logger.getLogger(ScreenedAction.class);
	private final String  pagePath = "screened/";
	
	/***********************************************************************************************************************************
	 **********                                           以下为融资筛选列表                             　　　　　　　　　　　　　　　                                **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","列表"})
	@Override
	public Reply execute() {
		Map<String,Object> map = new HashMap<String,Object>();
		VelocityContext context=new VelocityContext();
		
		map.put("USERID",  Security.getUser().getId());//登陆用户编号
		//计算用户支付表总和
		Map<String,Object> cmap = (Map<String,Object>)service.getSumCartByUser(map);
		map.put("SUM",new DecimalFormat("0.00").format((BigDecimal)cmap.get("SUM")));
		
		//计算用户支付表数量
		map.put("SIZE",service.selCartByUser(map).size());
		
		//获取页面参数
		Map<String,Object> param = _getParameters();

		context.put("param", param);//页面参数		
		context.put("payStatus", new DataDictionaryMemcached().get("还款计划状态"));//支付表状态获取
		context.put("refundway", service.getRefundBailoutway()); //融资方式查询
		context.put("PAYIDMAP", map);
		context.put("promodelList", new SysDictionaryMemcached().get("业务类型"));//业务类型
		SkyEye.getRequest().getSession().setAttribute("PAYIDMAP",map);
		
		return new ReplyHtml(VM.html(pagePath+"payScreenedManager.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","列表"})
	public Reply payConformScreened(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("USERID",  Security.getUser().getId());//登陆用户编号
		//计算用户支付表总和
		Map<String,Object> cmap = (Map<String,Object>)service.getSumCartByUser(map);
		map.put("SUM",new DecimalFormat("0.00").format((BigDecimal)cmap.get("SUM")));
		
		//计算用户支付表数量
		map.put("SIZE",service.selCartByUser(map).size());
		
		//获取页面参数
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("param", json);
		returnMap.put("agency", service.selAgency(json));//	查询机构
		returnMap.put("PAYIDMAP", map);
		returnMap.put("getScreened", service.getConformPayList1(json)); //查询符合融资条件的列表
		JSONObject page = JSONObject.fromObject(returnMap);
		return new ReplyAjax(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","创建项目"})
	public Reply projectCode(){
		String pro_code = service.getProjectCode();
		
		return new ReplyAjax(true, pro_code);
	}
	
	/***********************************************************************************************************************************
	 **********                                           以下为创建融资项目                           　　　　　　　　　　　　　　　                                ***********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","创建项目"})
	public Reply createProject() {
		//页面参数
		Map<String,Object> map = _getParameters();
		JSONObject object = JSONObject.fromObject(map.get("dataJson"));
		object.put("USERNAME",  Security.getUser().getName());//登陆用户名
		object.put("USERCODE",  Security.getUser().getCode());//登陆用户编号、
		
		//支付表编号
		String[] payd = object.get("payid").toString().split(",");

		//插入融资项目主表
		String pro_id = service.createProjectHead(object);	 
		boolean flag = false;
		String msg = "";
		
		if(pro_id.equals(null)){
			flag = false;
			msg = "融资项目添加失败";
		}else {
			object.put("PROJECTID", pro_id);//项目主表id
			
			//移植支付表
			for(int i=0;i<payd.length;i++){
				object.put("PAYID",payd[i]);
				//移植到融资对应的表
				service.transplantsRefundPay(object);
				//移植融资支付表对应支付表明细
				service.transplantsRefundPayDetail(object);
				//移植设备
				service.transplantsRefundPayEquipment(object);
				//融资机构每个项目的融资基数
				service.addOrganrefund(object);
			}
			
			//发起退款评审流程
			List<String> prcessList = JBPM.getDeploymentListByModelName("融资项目审批");
			if(prcessList.size() > 0){
				Map<String, Object> jmap = new HashMap<String, Object>();
				jmap.put("PROJECT_ID",pro_id);
	    		jmap.put("PROJECT_CODE",object.get("PROJECT_CODE")+"");
				String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"", "","",jmap).getId(); 
				msg += jbpm_id+"已发起！";
			}else{
				flag = false;
				msg = "未找到流程";
				throw new ActionException("未找到流程");
			}
			
			//调用档案
			/*try{
				Map<String,Object> m1 = new HashMap<String,Object>();
				m1.put("REFUND_PROJECT_ID", pro_id);
				m1.put("OPER_STATE", "4");
				service.addRfDossier(m1);
			}catch(Exception e){
				flag = false;
				msg = "融资项目插入成功， 融资档案调用失败";
				logger.error(e, e);
				return new ReplyAjax(flag,msg).addOp(new OpLog("融资筛选","新建项目","新建项目"));
			}*/
			flag = true;
			return new ReplyAjax(flag,msg).addOp(new OpLog("融资筛选","新建项目","新建项目"));
		}
		
		return new ReplyAjax(flag,msg).addOp(new OpLog("融资筛选","新建项目","新建项目"));
	}
	
	/***********************************************************************************************************************************
	 **********                                           以下为创建上传项目清单                         　　　　　　　　　　　　　　　                        ***********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","上传清单"})
	public Reply analyticalExcel(){
		List<Map> list=new ArrayList<Map>();
		Map<String,Object> m = new HashMap<String,Object>();
		String paylist_code="";
		try {
			logger.debug("设置文件大小");
			final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(4096);///设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
			File f=new File(SkyEye.getConfig("file.path").toString()+File.separator+"refundproject");//文件路劲
			f.mkdirs();//创建文件夹
			dfif.setRepository(f);// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录

			logger.debug("获取servlet上传文件");
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			sfu.setHeaderEncoding("UTF-8");//设置编码
			sfu.setSizeMax(MAX_SIZE);

			List<?> fileList = sfu.parseRequest(SkyEye.getRequest());
			Iterator<?> it = fileList.iterator();
			String type = null;
			File file = null;
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("type")) {
						
						type = fileItem.getString();
					}
					m.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"),"UTF-8"));
				} else {
					if (fileItem.getFieldName().equals("file")) {
						logger.debug("处理文件名："+fileItem.getName());
						logger.debug("开始获取文件：" + fileItem.getName());
						file = new File(SkyEye.getConfig("file.path").toString()
								+ File.separator+"refundproject"
								+ File.separator
								+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance()
										.getTime()) + "_"+UUID.randomUUID()+ fileItem.getName());
						fileItem.write(file);
						logger.debug("文件保存完毕：" + file.getAbsolutePath());
						// 检查文件名是否符合日期命名规范，用正则表达式作
						// 判断文件扩展名
						String filename = file.getName();
						String lastname = filename.substring(filename.length() - 4);
						if (!".xls".equals(lastname)) {
							logger.error("您上传的文件格式不正确! --> " + file.getName());
							return new ReplyAjax(false,"您上传的文件格式不正确!");
						}
						// 约束，相同文件名的文件只允许上传一次
						// 检查完成，开始解析文件
						System.out.println("type===="+type+";"+file);
						
					}
					
				}
				if (type != null && file != null) {
					
					InputStream ins = new FileInputStream(file.getAbsolutePath());
					
					Workbook rwb=Workbook.getWorkbook(ins);
					Sheet rs=rwb.getSheet(0);
					int rsColunms = rs.getColumns();
					int rsRows=rs.getRows();
					for(int i=1;i<rsRows;i++){
							Map<String, Object> map=new HashMap<String, Object>();
							for(int j=0;j<rsColunms;j++){
								Cell cell=rs.getCell(j, i);
								if(j==0){
									map.put("SERIAL", cell.getContents());
								}
								if(j==1){
									map.put("PAYLISTCODE", cell.getContents());
									paylist_code+=",'"+cell.getContents()+"'";
								}
//								if(j==2){
//									map.put("PAYLISTCODE", cell.getContents());
//									
//									//paylist_code+=","+cell.getContents();
//								}
							}
							logger.info(map.toString());
							list.add(map);
						}
						rwb.close();
				}
			}
		} catch (Exception e) {
			logger.error("文件上传错误",e);
			return new ReplyAjax(false,"文件上传错误");
		}
		logger.info(m.toString());
		Map<String,Object> resultMap = new HashMap<String,Object>();
		m.put("PAY_WAY", m.get("FILE_PAY_WAY"));
		m.put("PAY_BASE", m.get("FILE_PAY_BASE"));
		m.put("PROJECT_MONEY", null==m.get("FILE_PROJECT_MONEY") || "".equals(m.get("FILE_PROJECT_MONEY"))?"":(m.get("FILE_PROJECT_MONEY")));
		m.put("START_DATE", null==m.get("FILE_START_DATE") || "".equals(m.get("FILE_START_DATE"))?"":(m.get("FILE_START_DATE")));
		m.put("PAYLIST_CODE", paylist_code.substring(1, paylist_code.length()));
		resultMap.put("param", m);
		resultMap.put("agency", service.selAgency(m));
		resultMap.put("refundway", service.getRefundBailoutway());
		resultMap.put("getExcelData", service.getConformPayListExcel(m));
		JSONObject page = JSONObject.fromObject(resultMap);
		return new ReplyAjax(page);
		
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资筛选","查看资料是否齐全"})
	public Reply checkCondition(){
		//页面参数
		Map<String,Object> m = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("condition", service.checkCondition(m));
		return new ReplyHtml(VM.html(pagePath+"checkCondition.vm", context));
	}
	
	
	/***********************************************************************************************************************************
	 **********                                           以下为购物车数据                                   　　　　　　　　　　　　　　　                                **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资购物车","加入购物车"})
	public Reply doAddPayIdToSession(){
		String payId = SkyEye.getRequest().getParameter("payId");
		String Money = SkyEye.getRequest().getParameter("Money");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("FLAG","0");
		map.put("USERID", Security.getUser().getId());
		map.put("PAYID", payId);
		map.put("MONEY", Money);
		int flag = service.isExistInCart(map);
		
		//判断是否已经存在
		if(flag == 0){
			//新增购物车数据
			service.addCartByPayUser(map);
			//计算用户支付表总和
			Map<String,Object> cmap = (Map<String,Object>)service.getSumCartByUser(map);
			map.put("SUM",new DecimalFormat("0.00").format((BigDecimal)cmap.get("SUM")));
			//计算用户支付表数量
			map.put("SIZE",service.selCartByUser(map).size());
			//判断是否已经存在
			map.put("FLAG","1");
		}
		
		return new ReplyAjax(JSONObject.fromObject(map));
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资购物车", "清空购物车"})
	//根据用户ID清空购物车数据
	public Reply doClearPayIdToSession(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("USERID", Security.getUser().getId());
		//用户ID 删除购物车数据
		service.delCartByUser(map);
		
		map.put("SUM","0.00");
		map.put("SIZE","0");
		return new ReplyAjax(JSONObject.fromObject(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资购物车","删除购物车"})
	// 根据支付表ID 用户ID 删除购物车数据
	public Reply doDelPayIdToSession(){
		String payId = SkyEye.getRequest().getParameter("payId");
//		String Money = SkyEye.getRequest().getParameter("Money");

		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("FLAG","0");
		map.put("USERID", Security.getUser().getId());
		map.put("PAYID", payId);
		//支付表ID 用户ID 删除购物车数据
		service.delCartByPayUser(map);
		//计算用户支付表总和
		map.put("SUM",service.getSumCartByUser(map));
		//计算用户支付表数量
		map.put("SIZE",service.selCartByUser(map).size());
		
		return new ReplyAjax(JSONObject.fromObject(map));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资购物车","购物车"})
	public Reply goSelectPay(){ 
		Map<String,Object> map = new HashMap<String,Object>();
		VelocityContext context=new VelocityContext();
		map.put("USERID", Security.getUser().getId());//用户编号
		
		//获取所有已选PAYID
		List<String> PAYIDS = new ArrayList<String>();
		PAYIDS.add("0");
		
		List<Object> list = service.selCartByUser(map);
		
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> cmap = (Map<String,Object>)list.get(i);
			PAYIDS.add(cmap.get("PAYID")+"");
		}
		
		Map<String,Object> param= _getParameters();
		//PAY_BASE  PAY_WAY
		if(param.get("PAY_BASE") ==null || "".equals(param.get("PAY_BASE"))){
			param.put("PAY_BASE","0");
		}
		if(param.get("PAY_WAY") ==null || "".equals(param.get("PAY_WAY"))){
			param.put("PAY_WAY","62");
		}
		
		param.put("PAYIDS",PAYIDS);
		context.put("param", param);//页面参数
		context.put("agency", service.selAgency(param));
		context.put("refundway", service.getRefundBailoutway());	
		context.put("getScreened", service.getConformPayList1(param)); //查询符合融资条件的列表
		return new ReplyHtml(VM.html(pagePath+"paySelectedManager.vm", context));
	}
}
