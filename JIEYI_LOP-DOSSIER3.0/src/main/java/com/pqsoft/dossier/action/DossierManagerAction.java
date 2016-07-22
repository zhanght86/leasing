package com.pqsoft.dossier.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.common.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.dossier.service.DossierConfigService;
import com.pqsoft.dossier.service.DossierManagerService;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;

/**
 * 档案管理页主action
 * 
 * @author King 2013-10-8
 */
public class DossierManagerAction extends Action {
	private String path = "dossier/";
	private Logger log = Logger.getLogger(this.getClass());
	private DossierManagerService service = new DossierManagerService();

	/**
	 * 进入档案管理页面
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "档案管理", "档案归档", "列表显示"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("tabsList", new DataDictionaryMemcached().get("档案管理系统"));
		context.put("BUSINESS_PLATE_LIST", new DataDictionaryMemcached().get("PDF模版所属商务板块"));
		context.put("YEWU", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path + "dossierManager.vm", context));
	}

	/**
	 * 进入档案管理页面并获取数据
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "档案管理", "档案管理", "查询" })
	public Reply doShowDossierList() {
		Map<String, Object> m = _getParameters();
		Map<String, Object> param = new HashMap<String, Object>();
		if (m.containsKey("param")) {
			param.putAll(JSONObject.fromObject(m.get("param")));
		} else{
			param.put("DOSSIER_TYPE", "合同档案");
		}
		if (m.containsKey("page"))
			param.put("page", m.get("page"));
		else
			param.put("page", "1");
		if (m.containsKey("rows"))
			param.put("rows", m.get("rows"));
		else
			param.put("rows", "10");
		log.debug(param);
		return new ReplyAjaxPage(service.getDossierManagerList(param));
	}
	
	/**
	 * 查看档案列表展开层信息
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
//	@aPermission(name = { "档案管理", "档案管理列表", "子级列表","查看文件列表" })
	public Reply doShowDossierDetailList(){
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		List<Map<String,Object>> listMap = service.doShowDetailList1(param);
		System.out.println("subList:::::::"+listMap);
		
		
		
		String CLIENT_ID=null;
		for (int i = 0; i < listMap.size(); i++) {
			Map<String,Object> map = listMap.get(i);
			System.out.println("subList111:::::::"+map.get("FILE_NAME"));
			service.getPicture(map);
			CLIENT_ID=map.get("CLIENT_ID").toString();
		}

		/**
		 * 获取项目或客户对应的最新使用的档案袋和档案柜编号
		 */
		Map<String, Object> portfoliMap =new HashMap<String, Object>();
		if(StringUtils.isNotBlank(CLIENT_ID)){
			portfoliMap= new PigeonholeService().doShowClientPortfolioAndCabinet(CLIENT_ID, null == param.get("PROJECT_CODE") ? "" : param.get("PROJECT_CODE").toString());
		}
		if (portfoliMap == null || portfoliMap.size() < 1 || portfoliMap.isEmpty()) {
			if (StringUtils.isBlank(param.get("PROJECT_CODE")))
				param.put("PORTFOLIO_NUMBER", "CZR-" + DateUtil.getSysDate());
			else
				param.put("PORTFOLIO_NUMBER", "HT-" + param.get("PROJECT_CODE"));
		} else {
			param.put("PORTFOLIO_NUMBER", portfoliMap.get("PORTFOLIO_NUMBER"));
			if (!StringUtils.isBlank(portfoliMap.containsKey("CABINET_NUMBER") ? portfoliMap.get("CABINET_NUMBER") : "")) {
				String CABINET_NUMBERS[] = portfoliMap.get("CABINET_NUMBER").toString().split("-");
				param.put("HEAD", CABINET_NUMBERS[0]);
				param.put("ROW", CABINET_NUMBERS[1]);
				param.put("LINE", CABINET_NUMBERS[2]);
			} else {
				param.put("ROW", "");
				param.put("LINE", "");
			}
		}
		context.put("CABINETLIST", new DossierConfigService().doShowDossierConfigList(new HashMap()));
		context.put("BASEINFO", param);
		context.put("subList", listMap);
		
		return new ReplyHtml(VM.html(path+"subList.vm", context));
	}
	
	/**
	 * 查询文件列表
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-30  上午10:49:50
	 */
//	@aPermission(name = { "档案管理", "档案管理", "子级列表","文件列表" })
//	@aPermission(name = { "档案管理", "档案管理列表", "子级列表", "查看文件列表" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectFileList(){
		Map<String,Object> param=_getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		VelocityContext context=new VelocityContext();
		context.put("showType",param.get("showType"));
		param.putAll(service.selectDossierStorage(param));
//		if(param.containsKey("DOSSIER_FILE_TYPE") && param.containsKey("DOSSIER_CODE")){
//			Map<String,Object> map1 = new PigeonholeService().selectPdfTemplateManagementData(param);
//			if(map1 != null && !map1.isEmpty()){
				param.put("RENTER_NAME", param.get("CLIENT_NAME"));
				param.put("RENTER_CODE", param.get("CUST_ID"));
//				param.put("FILE_PATH", param.get("PROJECT_CODE")+"/"+param.get("DOSSIER_FILE_TYPE")+"/"+param.get("CABINET_NUMBER")+"/"+param.get("PORTFOLIO_NUMBER")+"/"+param.get("FILE_NAME"));
				param.put("FILE_PATH", param.get("PROJECT_CODE")+"/"+param.get("DOSSIER_FILE_TYPE")+"/"+param.get("FILE_NAME"));
				{
					if(param !=null && !param.isEmpty() && param.containsKey("RENTER_NAME") && param.containsKey("RENTER_CODE") && param.containsKey("FILE_PATH")){
						String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString(),true);
						context.put("fileList", fcu.selectFileList(catalogId));
					}
				}
//			}
//		}
		return new ReplyHtml(VM.html(path+"fileList.vm", context));
	}
	
	/**
	 * 上传档案资料
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-30  上午10:49:50
	 */
	@aPermission(name = { "档案管理", "档案归档", "上传文件" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply upLoadFile(){
		Map<String,Object> param = _getParameters();
		List<FileItem> fileList = _getFileItem();
		FileCatalogUtil fcu = new FileCatalogUtil();
		param.putAll(service.selectDossierStorage(param));
		boolean flag = false;
//		if(param.containsKey("DOSSIER_FILE_TYPE") && param.containsKey("DOSSIER_CODE")){
//			Map<String,Object> map1 = new PigeonholeService().selectPdfTemplateManagementData(param);
//			if(map1 != null && !map1.isEmpty()){
				param.put("RENTER_NAME", param.get("CLIENT_NAME"));
				param.put("RENTER_CODE", param.get("CUST_ID"));
//				param.put("FILE_PATH", param.get("PROJECT_CODE")+"/"+param.get("DOSSIER_FILE_TYPE")+"/"+param.get("CABINET_NUMBER")+"/"+param.get("PORTFOLIO_NUMBER")+"/"+param.get("FILE_NAME"));
				param.put("FILE_PATH", param.get("PROJECT_CODE")+"/"+param.get("DOSSIER_FILE_TYPE")+"/"+param.get("FILE_NAME"));
				{
					if(param !=null && !param.isEmpty() && param.containsKey("RENTER_NAME") && param.containsKey("RENTER_CODE") && param.containsKey("FILE_PATH")){
						String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString(),true);
						param.put("CATALOG_ID", catalogId);
						if(fcu.doUploadPicture(param,fileList, fcu.rootCatalog(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString()))){//上传到磁盘
							fcu.insertFileMessage(param);//在数据库中记录
							param.putAll(fcu.selectOperationFile(param.get("ORIGINAL_PATH").toString()));
							param.put("INSTRUCTION","ADDPICTURE");
							param.put("RECORD_REMARK","上传图片");
							fcu.addPictrueRecord(param);//添加记录
							flag =  true;
						}
					}
				}
//			}
//		}
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("上传", "上传", param.toString()));
		}else{
			return new ReplyAjax(flag,"上传失败");
		}
	}
	
	/**
	 * 下载文件
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:41:03
	 */
	@aPermission(name = { "档案管理", "档案归档", "电子文件列表显示", "下载" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public ReplyFile downFile() {
		Map<String, Object> param = _getParameters();
		String path = param.get("path").toString();
		File file  =   new  File(path);
		return new ReplyFile(file,file.getName());
	}
	
	/**
	 * 删除文件
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:41:03
	 */
	@aPermission(name = { "档案管理", "档案归档", "电子文件列表显示", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteFile() {
		FileCatalogUtil fcu = new FileCatalogUtil();
		Map<String, Object> param = _getParameters();
		String path = param.get("path").toString();
		fcu.deleteFile(path,false);//删除磁盘上的文件
		Map<String,Object> param1 = fcu.selectOperationFile(path);
		boolean flag = fcu.deleteFileById(param1.get("ID").toString());
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("删除", "删除", param.toString()));
		}else{
			return new ReplyAjax(flag,"删除失败");
		}
	}
	
//	@aAuth(type=aAuthType.LOGIN)
//	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name = { "档案管理", "档案业务台账", "列表" })
//	public Reply toMgDossierLedger(){
//		VelocityContext context=new VelocityContext();
//		return new ReplyHtml(VM.html(path+"dossierLedgerMg.vm", context));
//	}
	
	
//	@aAuth(type=aAuthType.LOGIN)
//	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name = { "档案管理", "档案业务台账", "列表" })
//	public Reply doMgDossierLedger(){
//		Map<String,Object> param=_getParameters();
//		return new ReplyAjaxPage(service.doMgDossierLedger(param));
//	}
	
	/**
	 * 档案业务台账导出
	 * @return
	 * @author:King 2014-3-4
	 */
//	@aAuth(type=aAuthType.LOGIN)
//	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name = { "档案管理", "档案业务台账", "列表" })
//	public Reply doExpDossierLedger(){
//		Map<String,Object> param=_getParameters();
//		return new ReplyExcel(service.doExpDossierLedger(param), DateUtil.getSysDate("yyyyMMdd")+"档案业务台账.xls");
//	}
	
	/**
	 * 接收档案文件
	 * @return
	 * @author King 2014年8月10日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"档案管理","档案归档","接收勾选文件"})
	public Reply doRecieveFile(){
		JSONArray jsonArray=JSONArray.fromObject(_getParameters().get("param"));
		List<Map<String,Object>> list=jsonArray;
		int i=0;
		for (Map<String, Object> map : list) {
			i+=service.doRecieveFile(map.get("ID"));
		}
		if(i>=1)
			return new ReplyAjax(true, i, null);
		else
			return new ReplyAjax(false);
	}
	
	
	/**
	 * 接收档案文件
	 * @return
	 * @author King 2014年8月10日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"档案管理","档案归档","接收勾选文件"})
	public Reply doDestoryFile(){
		JSONArray jsonArray=JSONArray.fromObject(_getParameters().get("param"));
		List<Map<String,Object>> list=jsonArray;
		int i=0;
		for (Map<String, Object> map : list) {
			i+=service.doDestoryFile(map.get("ID"));
		}
		if(i>=1)
			return new ReplyAjax(true, i, null);
		else
			return new ReplyAjax(false);
	}
	
	
	/**
	 * 档案入柜
	 * @return
	 * @author King 2014年8月10日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"档案管理","档案归档","勾选文件入柜"})
	public Reply dossierFileRG(){
		Map<String,Object> param=_getParameters();
		JSONArray jsonArray=JSONArray.fromObject(param.get("param"));
		List<Map<String,Object>> list=jsonArray;
		int i=0;
		for (Map<String, Object> map : list) {
			map.put("CABINET_NUMBER", param.get("CABINET_NUMBER"));
			map.put("PORTFOLIO_NUMBER", param.get("PORTFOLIO_NUMBER"));
			i+=service.dossierFileRG(map);
		}
		if(i>=1)
			return new ReplyAjax(true, i, null);
		else
			return new ReplyAjax(false);
	}
	@aPermission(name = { "档案管理", "档案归档", "列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply DatPpreview() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		List<Map<String, Object>> list=service.getpayment(params);
		context.put("params", params);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("title", "承租人");
		m4.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?task=jf&JFBG=JFBG&PROJECT_ID="+params.get("PROJECT_ID").toString()+"&CUST_TYPE=承租人&YHBC=YHBC");
		tabs.add(m4);
		Map<String, Object> map=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			map=list.get(i);
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", map.get("PAYLIST_CODE").toString()+"放款资料");
			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?task=jf&JFBG=JFBG&YHBC=1&FK_ID="+map.get("ID").toString()+"&PHASE=放款前&taskStatue=1&PROJECT_ID="+map.get("PROJECT_ID").toString());
			tabs.add(m2);
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("title", map.get("PAYLIST_CODE").toString()+"放款后");
			m3.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?task=jf&JFBG=JFBG&YHBC=1&FK_ID="+map.get("ID")+"&PROJECT_ID="+map.get("PROJECT_ID")+"&PHASE=放款后");
			tabs.add(m3);
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		return new ReplyHtml(VM.html(path+"toMain.vm", context));
	}
}
