package com.pqsoft.buyCertificate.action;



import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.jfree.util.Log;

import com.pqsoft.buyCertificate.service.BuyCertificateService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.rzzlAPI.CarRegistAPI;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.SystemException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * (name = "合格证")
 * @author WuYanFei
 *
 */

public class BuyCertificateAction extends Action{
	
	private static Logger logger = Logger.getLogger(BuyCertificateAction.class);
	private VelocityContext context = new VelocityContext();
	
	BuyCertificateService service=new BuyCertificateService();

	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "列表显示" })
	public Reply execute(){
		Map<String,Object> map=_getParameters();
		User user = Security.getUser() ;
		map.put("EM_ID", user.getId());
		map.put("ORG_CHILDREN", user.getOrg().getName());
		//通过行业类型查询
		map.put("DATATYPE", "行业类型");		
		context.put("param", map) ;
		return new ReplyHtml(VM.html("buyCertificate/certificateentrymanage.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "23", email = "lishuojiezhong@china.com", name = "李硕")
	@aPermission(name = { "业务管理", "合同签订", "列表显示"})
	public Reply toExecuteForJC(){
		Map<String,Object> map=_getParameters();
		User user = Security.getUser() ;
		map.put("EM_ID", user.getId());
		map.put("ORG_CHILDREN", user.getOrg().getName());
		String Lease_code =null;
		String PROJECT_ID =null;
		if(map.get("LEASE_CODE") !=null){
			PROJECT_ID = service.queryProject_ID(map);
			map.put("PROJECT_ID", PROJECT_ID);
		}
		if(!"".equals(map.get("PROJECT_ID"))){
			Lease_code = service.queryLease_Code(map);
			map.put("LEASE_CODE", Lease_code);
		}
		//通过行业类型查询
		map.put("DATATYPE", "行业类型");		
		context.put("param", map) ;
		//去车务交车的页面
		return new ReplyHtml(VM.html("buyCertificate/certificateentrymanagetoJC.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "23", email = "lishuojiezhong@china.com", name = "李硕")
	@aPermission(name = { "业务管理", "合同签订", "列表显示"})
	public Reply toExecuteForCW(){
		Map<String,Object> map=_getParameters();
		User user = Security.getUser() ;
		map.put("EM_ID", user.getId());
		map.put("ORG_CHILDREN", user.getOrg().getName());
		String Lease_code =null;
		String PROJECT_ID =null;
		if(map.get("LEASE_CODE") !=null){
			PROJECT_ID = service.queryProject_ID(map);
			map.put("PROJECT_ID", PROJECT_ID);
		}
		if(!"".equals(map.get("PROJECT_ID"))){
			Lease_code = service.queryLease_Code(map);
			map.put("LEASE_CODE", Lease_code);
		}
		//通过行业类型查询
		map.put("DATATYPE", "行业类型");		
		context.put("param", map) ;
		return new ReplyHtml(VM.html("buyCertificate/certificateentrymanagetoCW.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "列表" })
	public Reply getCertificatePage(){
		Map<String,Object> map = _getParameters();
		BaseUtil.getDataAllAuth(map);
		Page page = service.getCertificatePage(map);
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "23", email = "shouli@jiechongchina.com", name = "李硕")
	@aPermission(name = { "业务管理", "合同签订", "列表" })
	public Reply getCertificatePagetoCW(){
		Map<String,Object> map = _getParameters();
		Page page =null;
		BaseUtil.getDataAllAuth(map);
		if(!"".equals(map.get("PROJECT_ID")) && map.get("PROJECT_ID") !=null){
			String Lease_code= service.queryLease_Code(map);
			map.put("LEASE_CODE", Lease_code);
			page = service.getCertificatePage2(map);
		}
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type = aAuthType.ALL)
    @aDev(code = "23", email = "shouli@jiechongchina.com", name = "李硕")
    @aPermission(name = { "业务管理", "合同签订", "新建" })
    public Reply goCertificate_eqiupChoose(){
        //此功能为点击tab页加载
        List<Map<String,Object>> mm =null;
        Map<String,Object> map=_getParameters();
        if(!map.isEmpty() && map.size() > 0){
            mm =service.gerMessage(map);
        }
        if(!mm.isEmpty() &&  mm.size() > 0){
            map.put("LEASE_CODE",mm.get(0).get("LEASE_CODE"));
            map.put("PAYLIST_CODE",mm.get(0).get("PAYLIST_CODE"));
            map.put("CUST_SIGN_NAME",mm.get(0).get("NAME"));
            map.put("PAY_ID", mm.get(0).get("ID"));
            context.put("param", map);
            context.put("equipment", service.getEquipList(map));
        }
        return new ReplyHtml(VM.html("buyCertificate/certificate_eqiup_choose.vm",context)) ;
    }
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "新建" })
	public Reply gocertificate_eqiupChoose(){
		Map<String,Object> map=_getParameters();
		JSONObject jsonObj = JSONObject.fromObject((map.get("paramJson")));
		context.put("equipment", service.getEquipList(jsonObj));
		context.put("param", jsonObj);
		return new ReplyHtml(VM.html("buyCertificate/certificate_eqiup_choose.vm",context)) ;
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "合格证查看" })
	public Reply certificateCheck(){
		Map<String,Object> map=_getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		context.put("param", map);
		//ADD BY  LISHUO 01-17-2016 Start
		if(map.get("LEASE_CODE") != null){
			map.put("lease_Code", map.get("LEASE_CODE"));
			String ProjectID=service.queryProjectID(map);
			if(!"".equals(ProjectID)){
				map.put("ProjectID", ProjectID) ;
				List<Map<String,Object>> GPSInfo =service.queryGPSMessage(map);
				if(GPSInfo.size() > 0){
					context.put("GPSInfo", GPSInfo);
				}
			  }
			}
		//ADD BY  LISHUO 01-17-2016 End
		logger.debug(map);
		Map<String,Object> certificateinfoMap = service.checkCertificate(map);
		context.put("certificateinfo", certificateinfoMap);
		//得到联系人以及对应的支付表编号
		Map<String,Object> dataInfoMap = service.getInfo(map);
		context.put("dataInfo", dataInfoMap);
		//查询设备原始型号
		context.put("equipcode", service.getEquipCode(map));
//		context.put("upload", service.getUploadFile(map));
	
		//TODO 查询图片所在目录
	/*	String RENTER_NAME = map.get("RENTER_NAME").toString();//承租人名称
		String RENTER_CODE = map.get("RENTER_CODE").toString();//承租人编号
		String LEASE_CODE = map.get("LEASE_CODE").toString();//合同编号
		String PAYLIST_CODE = dataInfoMap.get("PAYLIST_CODE").toString();//支付表编号
		String NO = certificateinfoMap.get("NO").toString();//流水号
		String filePath = "合同中心/融资租赁合同编号"+LEASE_CODE+"/还款计划编号"+PAYLIST_CODE+"/合格证/流水号"+NO;
		String catalogId = fcu.getCatalogIdByPathNoCreat(RENTER_NAME,RENTER_CODE,filePath);//查询图片所在目录ID 
		
		if(catalogId != null){
			List<Map<String,Object>> listFileId = service.selectFielId(catalogId);
			context.put("upload", listFileId);
		}else{
			context.put("upload", null);
		}
		*/
		List<Object> findcrelist = service.queryDataDictionary("唯一标识");
		context.put("findcrelist", findcrelist);
		logger.debug(map);
		return new ReplyHtml(VM.html("buyCertificate/certificate_check.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "合格证图片下载" })
	public Reply downLoadRecordFile() {
		Map<String,Object> map = _getParameters();
		return new ReplyFile(new File(map.get("file_url") + ""), map.get("file_name") + "");
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "修改" })
	public Reply certificateUpdate(){
		Map<String,Object> map=_getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		String no =  map.get("NO").toString() ;
		JSONObject jsonObj = JSONObject.fromObject((map.get("paramJson")));
		jsonObj.element("NO", no) ;
		//ADD BY  LISHUO 01-17-2016 Start
		context.put("list", new DataDictionaryMemcached().get("GPS厂商"));
		if(jsonObj.get("LEASE_CODE") != null){
			map.put("lease_Code", jsonObj.get("LEASE_CODE"));
			String ProjectID=service.queryProjectID(map);
			if(!"".equals(ProjectID)){
				jsonObj.element("ProjectID", ProjectID) ;
				List<Map<String,Object>> GPSInfo =service.queryGPSMessage(jsonObj);
				Map<String,Object> m = new HashMap<String,Object>();
				if(GPSInfo.size() > 0){
					context.put("GPSInfo1", m);
					context.put("GPSInfo2", m);
					context.put("GPSInfo3", m);
					context.put("GPSInfo4", m);
					for(int a=0;a<GPSInfo.size();a++){
						 m =GPSInfo.get(a);
						if(m.get("GPS_COMPANY_TYPE").equals("1")){
							context.put("GPSInfo1", m);
						}
						if(m.get("GPS_COMPANY_TYPE").equals("2")){
							context.put("GPSInfo2", m);
						}
						if(m.get("GPS_COMPANY_TYPE").equals("3")){
							context.put("GPSInfo3", m);
						}
						if(m.get("GPS_COMPANY_TYPE").equals("4")){
							context.put("GPSInfo4", m);
						}
					}
					
				}
			  }
			}
		//ADD BY  LISHUO 01-17-2016 End
		//add by lishuo 02-17-2016 Start 增加跳转页面标识
		jsonObj.element("ClickType", "菜单");
		//add by lishuo 02-17-2016 End
		context.put("param", jsonObj);
		Map<String,Object> certificateinfoMap = service.checkCertificate(jsonObj);
		context.put("certificateinfo", certificateinfoMap);
		//查询设备原始型号
		context.put("equipcode", service.getEquipCode(jsonObj));
		//得到联系人以及对应的支付表编号
		Map<String,Object> dataInfoMap = service.getInfo(jsonObj);
		context.put("dataInfo", dataInfoMap);
//		context.put("upload", service.getUploadFile(map));
		
		//TODO  查询图片所在目录
	/*	String RENTER_NAME = map.get("RENTER_NAME").toString();//承租人名称
		String RENTER_CODE = map.get("RENTER_CODE").toString();//承租人编号
		String LEASE_CODE = map.get("LEASE_CODE").toString();//承租人编号
		String PAYLIST_CODE = dataInfoMap.get("PAYLIST_CODE").toString();//支付表编号
		String NO = certificateinfoMap.get("NO").toString();//流水号
		String filePath = "合同中心/融资租赁合同编号"+LEASE_CODE+"/还款计划编号"+PAYLIST_CODE+"/合格证/流水号"+NO;
		String catalogId = fcu.getCatalogIdByPathNoCreat(RENTER_NAME,RENTER_CODE,filePath);//查询图片所在目录ID
		if(catalogId != null){
			List<Map<String,Object>> listFileId = service.selectFielId(catalogId);
			context.put("upload", listFileId);
		}else{
			context.put("upload", null);
		}
		*/
		List<Object> upcrelist = service.queryDataDictionary("唯一标识");
		context.put("upcrelist", upcrelist);
		return new ReplyHtml(VM.html("buyCertificate/certificate_update.vm", context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "【跳转页面】合格证录入页面" })
	public Reply certificateEntry(){
		Map<String,Object> map=_getParameters();
		context.put("param",map);
		context.put("contractInfo", service.getbuycontractInfo(map));//支付表信息
		context.put("eqiupInfo", service.getEquipInfo(map));//设备信息
		//查询最大流水号
		String maxno=service.getMaxno();
		logger.debug("*****************************:"+maxno);
		context.put("NO", maxno);
		FileCatalogUtil fcu = new FileCatalogUtil();
		String renter_name = map.get("RENTER_NAME").toString();
		//String renter_code = map.get("RENTER_CODE").toString();
		//context.put("RENTER_CODE", renter_code);
		context.put("RENTER_NAME", renter_name);
		String file_path = "合同中心/融资租赁合同编号"+map.get("LEASE_CODE").toString()+"/还款计划编号"+map.get("PAYLIST_CODE1").toString()+"/合格证/流水号"+maxno;
		List<Object> crelist = service.queryDataDictionary("唯一标识");
		new SysDictionaryMemcached().get("项目状态位");
		//add by lishuo 01-25-2016 Start
		context.put("list", new DataDictionaryMemcached().get("GPS厂商"));
		//add by lishuo 01-25-2016 End
		//TODO　
		//String catalogId = fcu.getCatalogIdByPath(renter_name,renter_code,file_path);
		System.out.println(crelist);
		context.put("FILE_PATH", file_path);
		context.put("crelist",crelist );
		return new ReplyHtml(VM.html("buyCertificate/certificate_entry.vm", context)) ; 
	}
	
	

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "【保存页面】合格证信息录入" })
	public Reply insertCertificate() {
		Map<String, Object> param = _getParameters();
		String seqid=service.getCertificateid();
		JSONObject  object = JSONObject.fromObject(param.get("paramJson"));
		String username = Security.getUser().getName() ;
		object.element("CERTIFICATE_ID", seqid);
		object.element("USERNAME", username) ;	
		object.element("PLATFORM", Security.getUser().getOrg().getPlatform()) ;
		object.element("CREATE_TIME","") ;
		//add by lishuo 01-14-2016 Strat
		param.remove("paramJson");
		param.putAll(object);
		if(object.get("lease_Code") != null){
			String ProjectID=service.queryProjectID(param);
			if(!"".equals(ProjectID)){
				param.put("ProjectID", ProjectID);
				object.element("ProjectID", ProjectID);
				if(!"".equals(object.get("gps_company_type1")) &&"1".equals(object.get("gps_company_type1"))){
					service.insertGPSINFO(object);
				}
				if(!"".equals(object.get("gps_company_type2")) &&"2".equals(object.get("gps_company_type2"))){
					service.insertGPSINFO2(object);
				}
				if(!"".equals(object.get("gps_company_type3")) &&"3".equals(object.get("gps_company_type3"))){
					service.insertGPSINFO3(object);
				}
				if(!"".equals(object.get("gps_company_type4")) &&"4".equals(object.get("gps_company_type4"))){
					service.insertGPSINFO4(object);
				}
			}
		}
		
		//Map mapPath = this._getParametersIO(null, null, null);
		boolean a =  service.addCertificateInfo(object);//添加合格证信息	
		boolean b = service.updateEquip_Certificateid(object); //添加设备信息
		
		if(StringUtils.isNotBlank(object.get("uploadfile"))){
			String[] arr = object.get("uploadfile").toString().split(",");
			object.put("FILE_PATH",arr[0]);
			object.put("FILE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("FINC_CONTRACT_FILE"))){
			String[] arr = object.get("FINC_CONTRACT_FILE").toString().split(",");
			object.put("FINC_CONTRACT_URL",arr[0]);
			object.put("FINC_CONTRACT_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("BUYCAR_INVOICE_FILE"))){
			String[] arr = object.get("BUYCAR_INVOICE_FILE").toString().split(",");
			object.put("BUYCAR_INVOICE_URL",arr[0]);
			object.put("BUYCAR_INVOICE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("CAR_INSURANCE_FILE"))){
			String[] arr = object.get("CAR_INSURANCE_FILE").toString().split(",");
			object.put("CAR_INSURANCE_URL",arr[0]);
			object.put("CAR_INSURANCE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("PROMISE_BOOK_FILE"))){
			String[] arr = object.get("PROMISE_BOOK_FILE").toString().split(",");
			object.put("PROMISE_BOOK_URL",arr[0]);
			object.put("PROMISE_BOOK_NAME", arr[1]);
		}
		
		// add gengchangbao JZZL-114 2016年3月8日 start
		if(StringUtils.isNotBlank(object.get("DRIVING_BOOK_FILE"))){
			String[] arr = object.get("DRIVING_BOOK_FILE").toString().split(",");
			object.put("DRIVING_BOOK_FILE_URL",arr[0]);
			object.put("DRIVING_BOOK_FILE_NAME", arr[1]);
		}
		// add gengchangbao JZZL-114 2016年3月8日 end
		
		service.uploadReceiptFile(object);
		//add by lishuo 01-14-2016 End
		boolean flag=false;
		String msg = "添加失败" ;
		if(a&&b)
		{
			flag=true;
			msg = "添加成功" ;
			//监控车辆的注册接口(车网提供)
			String payCode = (String) object.get("productCode");
			boolean isSend  = CarRegistAPI.sendCarInfoMonitor(seqid, payCode);
			if(isSend){
				Log.debug("已发出【监控车辆的注册】请求");
			}
		}
		return new ReplyAjax(flag, msg);
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "23", email = "lishuo@jiezhongchina.com", name = "李硕")
	public Reply FileUploadOneByOne(){
		Map<String,Object> m =_getParameters();
		String msg ="";
		Map<String,Object> mapPath =null;
		try{
			mapPath =this._getParametersIO(null,null,null);
		}catch(SystemException e){
			e.printStackTrace();
			msg= e.getMessage();
		}
		if (mapPath != null && mapPath.get("_FILE_NAME") != null && !mapPath.get("_FILE_NAME").equals("")) {
			return new ReplyAjax(true,mapPath.get("tmp")+","+mapPath.get("_FILE_NAME"));
		}else{
			return new ReplyAjax(false,msg);
		}
	}

	
	/**
	 * 设置文件名称和文件路径
	 * @param object
	 * @param urlName
	 * @param fileName
	 * @return
	 */
	private JSONObject setObjectPathOrName(JSONObject object, String urlName, String fileName) {
		
		String FILE_PATH = object.get(urlName) + "";
		
		if("" != FILE_PATH){
			
			String FILE_NAME = FILE_PATH.substring(FILE_PATH.lastIndexOf("\\")+1, FILE_PATH.length());
			
			object.put(fileName, FILE_NAME);
		}
		
		return object;
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "判断合格证编号是否重复" })
	public Reply checkCode(){
		Map<String,Object> m =_getParameters();
	    List<Map<String,Object>> ojbect = service.checkCode(m);
	    boolean flag=false;
		String msg = "合格证编号重复" ;
		if(ojbect.size()==0)
		{
			flag=true;
			//用于测试
			msg = "合格证编号可以使用" ;
		}
		return new ReplyAjax(flag,msg) ;
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "判断车架号是否重复" })
	public Reply CheckCarSysmle(){
		Map<String,Object> m =_getParameters();
		List<Map<String,Object>> ojbect = service.CheckCarSysmle(m);
	    boolean flag=false;
		String msg = "车架号重复" ;
		if(ojbect.size()==0)
		{
			flag=true;
			//用于测试
			msg = "车架号可以使用" ;
		}
		return new ReplyAjax(flag,msg) ;
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "判断发动机型号是否重复" })
	public Object CheckEngineType(){
		Map<String,Object> m =_getParameters();
		List<Map<String,Object>> ojbect = service.CheckEngineType(m);
	    boolean flag=false;
		String msg = "发动机型号重复" ;
		if(ojbect.size()==0)
		{
			flag=true;
			//用于测试
			msg = "发动机型号可以使用" ;
		}
		return new ReplyAjax(flag,msg) ;
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "修改" })
	public Reply subcertificateInfo(){
		Map<String,Object> param = _getParameters() ;	
		JSONObject  object = JSONObject.fromObject(param.get("paramJson"));
		object.element("CERTIFICATE_ID", object.get("ID")) ;
		Map mapPath = this._getParametersIO(null, null, null);
		if(StringUtils.isNotBlank(object.get("uploadfile"))){
			String[] arr = object.get("uploadfile").toString().split(",");
			object.put("FILE_PATH",arr[0]);
			object.put("FILE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("FINC_CONTRACT_FILE"))){
			String[] arr = object.get("FINC_CONTRACT_FILE").toString().split(",");
			object.put("FINC_CONTRACT_URL",arr[0]);
			object.put("FINC_CONTRACT_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("BUYCAR_INVOICE_FILE"))){
			String[] arr = object.get("BUYCAR_INVOICE_FILE").toString().split(",");
			object.put("BUYCAR_INVOICE_URL",arr[0]);
			object.put("BUYCAR_INVOICE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("CAR_INSURANCE_FILE"))){
			String[] arr = object.get("CAR_INSURANCE_FILE").toString().split(",");
			object.put("CAR_INSURANCE_URL",arr[0]);
			object.put("CAR_INSURANCE_NAME", arr[1]);
		}
		if(StringUtils.isNotBlank(object.get("PROMISE_BOOK_FILE"))){
			String[] arr = object.get("PROMISE_BOOK_FILE").toString().split(",");
			object.put("PROMISE_BOOK_URL",arr[0]);
			object.put("PROMISE_BOOK_NAME", arr[1]);
		}
		
		// add gengchangbao JZZL-114 2016年3月8日 start
		if(StringUtils.isNotBlank(object.get("DRIVING_BOOK_FILE"))){
			String[] arr = object.get("DRIVING_BOOK_FILE").toString().split(",");
			object.put("DRIVING_BOOK_FILE_URL",arr[0]);
			object.put("DRIVING_BOOK_FILE_NAME", arr[1]);
		}
		// add gengchangbao JZZL-114 2016年3月8日 end
		
		 service.uploadReceiptFile(object);
		object.element("MODIFY_PERSON", Security.getUser().getName()) ;
		boolean a=service.updateCertificateInfo(object);//修改合格证信息
		boolean b=service.updateStatus(object);
		if(object.get("LEASE_CODE") != null){
			String lease_Code= (String) object.get("LEASE_CODE");
			param.put("lease_Code", lease_Code);
			String ProjectID=service.queryProjectID(param);
			if(!"".equals(ProjectID)){
				//更新增加gps信息
				//第一组gps信息
				for (int i = 1;i<5; i++){
					String  GPS_INFO_ID="GPS_INFO_ID"+i;
					String GPS_COMPANY="GPS_COMPANY"+i;
					String GPS_COMPANY_TYPE="GPS_COMPANY_TYPE"+i;
					String GPS_TYPE="GPS_TYPE"+i;
					String GPS_ID="GPS_ID"+i;
					if(object.get(GPS_INFO_ID) != null&&!object.get(GPS_INFO_ID).equals("")){
						//更新gps信息
						Map<String,Object> map= new HashMap<String,Object>();
						map.put("PROJECT_ID", ProjectID);
						map.put("ID", object.get(GPS_INFO_ID));
						map.put("GPS_TYPE", object.get(GPS_TYPE));
						//TODO 车架号 GPS厂商类型
						map.put("GPS_COMPANY", object.get(GPS_COMPANY));
						map.put("GPS_COMPANY_TYPE", object.get(GPS_COMPANY_TYPE));
						map.put("GPS_ID", object.get(GPS_ID));
						service.updateGpsInfo(map);
					}else{
						 //新增gps信息
						Map<String,Object> map= new HashMap<String,Object>();
						map.put("ProjectID", ProjectID);
						map.put("GPS1", object.get(GPS_TYPE));
						//TODO 车架号 GPS厂商类型
						map.put("gps_company1", object.get(GPS_COMPANY));
						map.put("gps_company_type1", object.get(GPS_COMPANY_TYPE));
						map.put("GPS_ID1", object.get(GPS_ID));
						service.insertGPSINFO(map);
					}
					
				}
			}
		}
		
		boolean flag=false;
		String msg = "更新失败" ;
		if(a&&b)
		{
			flag=true;
			msg = "更新成功" ;
			//监控车辆的注册接口(车网提供)

			String payCode = (String) object.get("PAYLIST_CODE");
			boolean isSend  = CarRegistAPI.modifyCarInfoMonitor(object.get("ID").toString(), payCode);
			if(isSend){
				Log.debug("已发出【监控车辆的注册】请求");
			}
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "业务管理", "合同签订", "添加签收单" })
	public Reply addQianshoudan(){
		Map<String,Object> param=_getParametersIO(null, null, null);
		int i=service.addQianshoudan(param);
		if(i>=1)
			return new ReplyAjax().addOp(new OpLog("添加签收单", "添加", param+""));
		else
			return new ReplyAjax(false);
	}
	
	//add by lishuo 03-07-2016 修改签收单路径 Start 
	public static Map<String, Object> _getParametersIO(String type, Long max, File target) {
		Map param = _getParameters();
		String filedirpath ="/pqsoft/file/receipt";
		try {
			File dir = new File(filedirpath);

			dir.mkdirs();

			DiskFileItemFactory factory = new DiskFileItemFactory();

			factory.setSizeThreshold(4096);

			ServletFileUpload upload = new ServletFileUpload(factory);
			if (max != null)
				upload.setSizeMax(max.longValue());
			List<FileItem> items = upload.parseRequest(SkyEye.getRequest());
			for (FileItem fileItem : items)
				if (!(fileItem.isFormField())) {
					String fileName = _getFileName(fileItem.getName());
					if (fileItem.getName() == null)
						continue;
					if (!("".equals(fileItem.getName())))
						;
					if ((type == null) || (fileName.endsWith(type)))
						;
					if (target == null)
						target = new File(new StringBuilder().append(filedirpath).append(File.separator)
								.append(System.currentTimeMillis()).append(fileName).toString());
					fileItem.write(target);
					param.put(fileItem.getFieldName(), target.getPath());
					param.put("_FILE_NAME", fileName);
				} else {
					param.put(fileItem.getFieldName(),
							new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}
		} catch (Exception e) {
			throw new SystemException(new StringBuilder().append("获取参数错误：").append(e.getMessage()).toString(), e);
		}
		return param;
	}
	//add by lishuo 03-07-2016 End
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "业务管理", "合同签订", "添加签收单" })
	public Reply downqianshoufile(){
		Map<String, Object> param = _getParameters();
		String path = param.get("HAND_FILE").toString();
		File file  =   new  File(path);
		return new ReplyFile(file,file.getName());
	}
/*		
	@aAuth(type = aAuthType.USER)
	@aDev(code = "wuyanfei", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aPermission(name = { "业务管理", "合同签订", "合格证录入管理列表页面" })
	public Object getCertificateEntryManage(){
		Map<String,Object> map=_getParameters();
		VelocityContext context=new VelocityContext();
		
		//TODO  this.USERID
		map.put("EM_ID",null);
		
		Map<String,Object> mm = BaseUtil.getCurrUserInfo();
		map.put("ORG_CHILDREN", mm.get("ORG_CHILDREN"));
		
		//通过行业类型查询
		map.put("DATATYPE", "行业类型");
		if(mSGA.get("LIONB_ZK")!=null && !mSGA.get("LIONB_ZK").equals(""))//狮桥-重卡
		{
			map.put("BUSINESSDE_TYPE", mSGA.get("LIONB_ZK"));
		}
		context.put("pageTemplate", service.getCertificate(map));
		context.put("param", map);
		//context.put("disvion", service.getDisvsion());
		context.put("Check", Security.validate("/buyCertificate/BuyCertificate!certificateUpdate.action"));
		context.put("createCertificate", Security.validate("/buyCertificate/BuyCertificate!gocertificate_eqiupChoose.action"));
		return mSGA.mergeVelocity("buyCertificate/certificateentrymanage.vm", context);
	}

	
	@aOperation(name="【跳转页面】-合格证-设备选择")
	public Object gocertificate_eqiupChoose()throws Exception{
		Map<String,Object> map=this.getParameters();
		VelocityContext context=new VelocityContext();
		context.put("equipment", service.getEquipList(map));
		context.put("param", map);
		return mSGA.mergeVelocity("buyCertificate/certificate_eqiup_choose.vm", context);
	}
	@aOperation(name="【跳转页面】合格证录入页面")
	public Object certificateEntry()throws Exception{
		Map map=this.getParameters();
		VelocityContext context=new VelocityContext();
		logger.debug(map);
		context.put("param",map);
		context.put("contractInfo", service.getbuycontractInfo(map));//支付表信息
		context.put("eqiupInfo", service.getEquipInfo(map));//设备信息
		//查询最大流水号
		String maxno=service.getMaxno();
		logger.debug("*****************************:"+maxno);
		context.put("NO", maxno);
		FileCatalogUtil fcu = new FileCatalogUtil();
		String renter_name = map.get("RENTER_NAME").toString();
		String renter_code = map.get("RENTER_CODE").toString();
		context.put("RENTER_CODE", renter_code);
		context.put("RENTER_NAME", renter_name);
		String file_path = "合同中心/融资租赁合同编号"+map.get("LEASE_CODE").toString()+"/还款计划编号"+map.get("PAYLIST_CODE1").toString()+"/合格证/流水号"+maxno;
		String catalogId = fcu.getCatalogIdByPath(renter_name,renter_code,file_path);
		context.put("FILE_PATH", file_path);
		return mSGA.mergeVelocity("buyCertificate/certificate_entry.vm", context);
	}
	
	@aOperation(name="【保存页面】合格证信息录入")
	public Object insertCertificateInfo() throws Exception{
		String returnValue="success"; 
		try{
			logger.info("设置文件大小");
			final long MAX_SIZE = 30*1024*1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			
			String root = mSGA.get("uploadDir").toString();//拿到配置文件中设置的上传文件路径
			utils.createDirectory(root);//调用创建存放上传文件 文件夹方法
		   
			logger.info("获取servlet上传文件");
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			
			logger.info("获取上传文件列表");
			List<?> fileList = fp.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			List filepathList = new ArrayList();
			
			logger.info("准备处理每一个文件");
			File file = null;
			int num=1;
			Map<String,Object> map = new HashMap<String,Object>();
			InputStream is  = null;
			String file_name = "";
			String file_type = "";
			String file_id = "";
			map.put("USERNAME", USERNAME);
//			//获取合格证id、图片id
//			String imgid=service.getCertimageid();
//			map.put("IMGID", imgid);
			String seqid=service.getCertificateid();
			map.put("CERTIFICATE_ID", seqid);
			while(it.hasNext()){
				FileItem fileitem = (FileItem)it.next();
				if(fileitem.isFormField()){
					map.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}else {
					is  =   fileitem.getInputStream();
					file_name = fileitem.getName();
					file_type = fileitem.getName().toString().substring(fileitem.getName().toString().lastIndexOf(".")+1, fileitem.getName().toString().length());
					num++;
					FileCatalogUtil fcu = new FileCatalogUtil();
					String renter_name = map.get("RENTER_NAME")==null?"":map.get("RENTER_NAME").toString();
					String renter_code = map.get("RENTER_CODE")==null?"":map.get("RENTER_CODE").toString();
					String file_path = map.get("FILE_PATH").toString();
					String catalogId = fcu.getCatalogIdByPath(renter_name,renter_code,file_path);
					String CERTIFICATE_CODE=map.get("NO_1")==null?"":map.get("NO_1").toString();
					String remark = map.get("REMARK")==null?"":map.get("REMARK").toString();
					if(!fcu.isNull(catalogId)){
						file_id = fcu.insertFile(catalogId, file_name, file_type, is, remark);
					}
					map.put("IMGID", file_id);
					map.put("path", "流水号"+CERTIFICATE_CODE);
					map.put("fileName", file_name);
					service.uploadFile(map);//合格证上传图片
					service.uploadImage(map);//向合格证图片信息中间表插入数据
//					if("uploadfile".equals(fileItem.getFieldName())){
//					logger.info("文件名===="+fileItem.getFieldName());
//				    String dir = mSGA.get("uploadDir").toString()+File.separator+"certificate"+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//					utils.createDirectory(dir);
//					logger.info("开始获取文件");
//					file = new File(dir+File.separator+ UUID.randomUUID()+ "_" + fileItem.getName());
//					fileItem.write(file);
//					m.put("file_title", fileItem.getName());
//					m.put("fileName", fileItem.getName() );
//					m.put("path", file.getPath().toString().replace("\\", "/"));
//					filepathList.add(file.getAbsoluteFile());
//					num++;
//					
//				}
				}
			}
			service.addCertificateInfo(map);//添加合格证信息
			//更新设备下的合格证id,合格证是否录全状态、以及设备信息
			logger.debug(map);
			service.updateEquip_Certificateid(map);
		}catch(Exception e){
			logger.error(e);
			returnValue="fail";
		}
		
		VelocityContext context=new VelocityContext();
	   if("success".equals(returnValue)){
		   context.put("doOkOrNo", "ok");
		   context.put("LEASE_CODE",request.getParameter("LEASE_CODE"));
		   return mSGA.mergeVelocity("buyCertificate/certificate_entry.vm", context);
//		return "<script>  alert('保存成功！');</script>";
	   }else{
		   return "<script>  alert('保存失败！');</script>";
	   }

	}
	@SuppressWarnings("unchecked")
	@aOperation(name="合格证查看")
	public Object certificateCheck()throws Exception{
		Map<String,Object> map=this.getParameters();
		VelocityContext context=new VelocityContext();
		FileCatalogUtil fcu = new FileCatalogUtil();
		context.put("param", map);
		logger.debug(map);
		Map<String,Object> certificateinfoMap = (Map<String,Object>)service.checkCertificate(map);
		context.put("certificateinfo", certificateinfoMap);
		//得到联系人以及对应的支付表编号
		Map<String,Object> dataInfoMap = (Map<String,Object>)service.getInfo(map);
		context.put("dataInfo", dataInfoMap);
		//查询设备原始型号
		context.put("equipcode", service.getEquipCode(map));
//		context.put("upload", service.getUploadFile(map));
		//----付玉龙
		String RENTER_NAME = map.get("RENTER_NAME").toString();//承租人名称
		String RENTER_CODE = map.get("RENTER_CODE").toString();//承租人编号
		String LEASE_CODE = map.get("LEASE_CODE").toString();//合同编号
		String PAYLIST_CODE = dataInfoMap.get("PAYLIST_CODE").toString();//支付表编号
		String NO = certificateinfoMap.get("NO").toString();//流水号
		String filePath = "合同中心/融资租赁合同编号"+LEASE_CODE+"/还款计划编号"+PAYLIST_CODE+"/合格证/流水号"+NO;
		String catalogId = fcu.getCatalogIdByPathNoCreat(RENTER_NAME,RENTER_CODE,filePath);//查询图片所在目录ID
		if(catalogId != null){
			List<Object> listFileId = (List<Object>)service.selectFielId(catalogId);
			context.put("upload", listFileId);
		}else{
			context.put("upload", null);
		}
		
		logger.debug(map);
		return mSGA.mergeVelocity("buyCertificate/certificate_check.vm", context);
	}
	@SuppressWarnings("unchecked")
	@aOperation(name ="【跳转页面】合格证信息修改页面")
	public Object certificateUpdate()throws Exception{
		Map<String,Object> map=this.getParameters();
		VelocityContext context=new VelocityContext();
		FileCatalogUtil fcu = new FileCatalogUtil();
		context.put("param", map);
		Map<String,Object> certificateinfoMap = (Map<String,Object>)service.checkCertificate(map);
		context.put("certificateinfo", certificateinfoMap);
		//得到联系人以及对应的支付表编号
		Map<String,Object> dataInfoMap = (Map<String,Object>)service.getInfo(map);
		context.put("dataInfo", dataInfoMap);
//		context.put("upload", service.getUploadFile(map));
		
		//----付玉龙
		String RENTER_NAME = map.get("RENTER_NAME").toString();//承租人名称
		String RENTER_CODE = map.get("RENTER_CODE").toString();//承租人编号
		String LEASE_CODE = map.get("LEASE_CODE").toString();//承租人编号
		String PAYLIST_CODE = dataInfoMap.get("PAYLIST_CODE").toString();//支付表编号
		String NO = certificateinfoMap.get("NO").toString();//流水号
		String filePath = "合同中心/融资租赁合同编号"+LEASE_CODE+"/还款计划编号"+PAYLIST_CODE+"/合格证/流水号"+NO;
		String catalogId = fcu.getCatalogIdByPathNoCreat(RENTER_NAME,RENTER_CODE,filePath);//查询图片所在目录ID
		if(catalogId != null){
			List<Object> listFileId = (List<Object>)service.selectFielId(catalogId);
			context.put("upload", listFileId);
		}else{
			context.put("upload", null);
		}
		
		if(Util.isAdmin(this.USERID)){
			context.put("adminFlag", 1);
		}
		return mSGA.mergeVelocity("buyCertificate/certificate_update.vm", context);
	}
	@aOperation(name="合格证信息修改保存")
	public Object subcertificateInfo()throws Exception{
		String returnValue="success";
		try{
			logger.info("设置文件大小");
			final long MAX_SIZE = 30*1024*1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			
			String root = mSGA.get("uploadDir").toString();//拿到配置文件中设置的上传文件路径
			utils.createDirectory(root);//调用创建存放上传文件 文件夹方法
		   
			logger.info("获取servlet上传文件");
			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);
			
			logger.info("获取上传文件列表");
			List<?> fileList = fp.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			
			List filepathList = new ArrayList();
			
			logger.info("准备处理每一个文件");
			File file = null;
			int num=1;
			Map<String,Object> m = new HashMap<String,Object>();
			InputStream is  = null;
			String file_name = "";
			String file_type = "";
			String file_id = "";
			m=this.getParameters();
			m.put("USERNAME", USERNAME);
			//获取图片id
			while(it.hasNext()){
				
				FileItem fileitem = (FileItem)it.next();
				if(fileitem.isFormField()){
					m.put(fileitem.getFieldName(),  new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}else {
//					if("uploadfile".equals(fileItem.getFieldName())){
//					logger.info("文件名===="+fileItem.getFieldName());
//					String dir = mSGA.get("uploadDir").toString()+File.separator+"certificate"+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//					utils.createDirectory(dir);
//					logger.info("开始获取文件");
//					file = new File(dir+File.separator+ UUID.randomUUID()+ "_" + fileItem.getName());
//					fileItem.write(file);
//					m.put("file_title", fileItem.getName());
//					m.put("fileName",fileItem.getName() );
//					m.put("path", file.getPath().toString().replace("\\", "/"));
					
//					filepathList.add(file.getAbsoluteFile());
					is  =   fileitem.getInputStream();
					file_name = fileitem.getName();
					file_type = fileitem.getName().toString().substring(fileitem.getName().toString().lastIndexOf(".")+1, fileitem.getName().toString().length());
					num++;
					FileCatalogUtil fcu = new FileCatalogUtil();
					String renter_name = m.get("RENTER_NAME")==null?"":m.get("RENTER_NAME").toString();
					String renter_code = m.get("RENTER_CODE")==null?"":m.get("RENTER_CODE").toString();
					String CERTIFICATE_CODE=m.get("NO_1")==null?"":m.get("NO_1").toString();
					String remark = m.get("REMARK")==null?"":m.get("REMARK").toString();
					String catalogId = fcu.getCatalogIdByPath(renter_name,renter_code,"合同中心/融资租赁合同编号"+m.get("LEASE_CODE").toString()+"/还款计划编号"+m.get("PAYLIST_CODE").toString()+"/合格证/流水号"+m.get("NO_1").toString());
					if(!fcu.isNull(catalogId)){
						file_id = fcu.insertFile(catalogId, file_name, file_type, is, remark);
					}
					m.put("IMGID",file_id );
					m.put("path", "流水号"+CERTIFICATE_CODE);
					m.put("fileName", file_name);
					service.uploadFile(m);//合格证上传图片
					service.uploadImage(m);//向合格证图片信息中间表插入数据
//				}
				}
			}
			service.updateCertificateInfo(m);//修改合格证信息
			service.updateStatus(m);
			
		}catch(Exception e){
			logger.error(e);
			returnValue="fail";
		}
	
		VelocityContext context=new VelocityContext();
		if("success".equals(returnValue)){
			context.put("doOkOrNo", "ok");
			context.put("LEASE_CODE",request.getParameter("LEASE_CODE"));
			return mSGA.mergeVelocity("buyCertificate/certificate_update.vm", context);
//			return "<script>  alert('修改成功！');</script>";
		   }else{
			   return "<script>  alert('修改失败！');</script>";
		   }

	
	}
	@aOperation(name="删除图片")
	public Object deletePic()throws Exception{
		Map<String,Object> m =this.getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		return fcu.deleteFileById(m.get("id").toString());
//		return service.deleteUploadFile(m);
	}
	@aOperation(name="判断合格证编号是否重复")
	public Object checkCode()throws Exception{
		Map<String,Object> m =this.getParameters();
	    List ojbect = (List)service.checkCode(m);
		return ojbect.size()==0?"success":"fail";
	}
	@aOperation(name="判断车架号是否重复")
	public Object CheckCarSysmle()throws Exception{
		Map<String,Object> m =this.getParameters();
		List ojbect = (List)service.CheckCarSysmle(m);
		return ojbect.size()==0?"success":"fail";
	}
	@aOperation(name="判断发动机型号是否重复")
	public Object CheckEngineType()throws Exception{
		Map<String,Object> m =this.getParameters();
		List ojbect = (List) service.CheckEngineType(m);
		return ojbect.size()==0?"success":"fail";
	}
*/	
}
