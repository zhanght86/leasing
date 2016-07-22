package com.pqsoft.crm.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.api.datalist.service.DataTempletService;
import com.pqsoft.crm.service.CustomerService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.leeds.utils.FileUploadService;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.osi.api.IDCard;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.ZipUtil;

/**
 * <p>
 * Title: 融资租赁信息系统平台 客户管理
 * </p>
 * <p>
 * Description: 客户管理
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class CustomerAction extends Action {

	private CustomerService service = new CustomerService();

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply execute() {
		return new ReplyHtml(VM.html("crm/customerFrames.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理","查看", "沟通记录" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgCallLog() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/callLogMg.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "业务管理", "客户管理","修改", "沟通记录" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toMgCallLogUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/callLogMg.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "沟通记录", "呼叫中心" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectCallLog() {
		Map<String, Object> param = _getParameters();
		Page page = service.getCallListPage(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "客户管理", "客户资料管理", "沟通记录", "客户巡视" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doSelectUserTour() {
		Map<String, Object> param = _getParameters();
		Page page = service.getUserTourPage(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doCheckCustIDCard() {
		String id = SkyEye.getRequest().getParameter("id");
		CustomerService service = new CustomerService();
		Map<String, Object> cust = service.getCust(id);
		if (cust == null) throw new ActionException("校验失败:无对应客户");
		if (!"NP".equals(cust.get("TYPE"))) throw new ActionException("校验失败:该客户非自然人");
		if ("CHECKSUCCESS".equals(cust.get("IDCARD_CHECK"))) throw new ActionException("校验失败:请勿重复校验");
		String name = (String) cust.get("NAME");
		String card = (String) cust.get("ID_CARD_NO");
		IDCard idCard = new IDCard();
		idCard.setName(name);
		idCard.setIdCard(card);
		idCard.check();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		if (idCard.isReal()) {
			// 记录校验成功信息
			param.put("IDCARD_CHECK", "CHECKSUCCESS");
			param.put("IDCARD_PHOTO", idCard.getPhoto());
			service.upCheckRe(param);
			//如果有资信，写入资信表（该客户下的所有资信均写）
			service.upCheckRe2(param);
			return new ReplyAjax(true, idCard.getPhoto());
		} else {
			// 记录校验失败信息
			param.put("ID", id);
			param.put("IDCARD_CHECK", "CHECKFAIL");
			service.upCheckRe(param);
			//如果有资信，写入资信表（该客户下的所有资信均写）
			service.upCheckRe2(param);
			return new ReplyAjax(false, idCard.getMsg());
		}
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doCheckCustIDCard1() {//个人配偶证件校验
		String id = SkyEye.getRequest().getParameter("id");
		CustomerService service = new CustomerService();
		Map<String, Object> cust = service.getSpoust(id);
		if (cust == null) throw new ActionException("校验失败:无对应客户");
		if (!"NP".equals(cust.get("TYPE"))) throw new ActionException("校验失败:该客户非自然人");
		if ("CHECKSUCCESS".equals(cust.get("IDCARD_CHECK"))) throw new ActionException("校验失败:请勿重复校验");
		String name = (String) cust.get("NAME");
		String card = (String) cust.get("ID_CARD_NO");
		IDCard idCard = new IDCard();
		idCard.setName(name);
		idCard.setIdCard(card);
		idCard.check();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		System.out.println("--------------idCard.isReal()="+idCard.getMsg());
		if (idCard.isReal()) {
			// 记录校验成功信息
			param.put("IDCARD_CHECK", "CHECKSUCCESS");
			param.put("IDCARD_PHOTO", idCard.getPhoto());
			service.upCheckRe1(param);
			return new ReplyAjax(true, idCard.getPhoto());
		} else {
			// 记录校验失败信息
			param.put("ID", id);
			param.put("IDCARD_CHECK", "CHECKFAIL");
			service.upCheckRe1(param);
			return new ReplyAjax(false, idCard.getMsg());
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doCheckCustIDCard3() {//企业团队证件校验
		String id = SkyEye.getRequest().getParameter("id");
		CustomerService service = new CustomerService();
		Map<String, Object> cust = service.getEnterprise(id);
		if (cust == null) throw new ActionException("校验失败:无对应客户");
		if ("CHECKSUCCESS".equals(cust.get("IDCARD_CHECK"))) throw new ActionException("校验失败:请勿重复校验");
		String name = (String) cust.get("NAME");
		String card = (String) cust.get("ID_CARD");
		IDCard idCard = new IDCard();
		idCard.setName(name);
		idCard.setIdCard(card);
		idCard.check();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		if (idCard.isReal()) {
			// 记录校验成功信息
			param.put("IDCARD_CHECK", "CHECKSUCCESS");
			param.put("IDCARD_PHOTO", idCard.getPhoto());
			service.upCheckRe3(param);
			return new ReplyAjax(true, idCard.getPhoto());
		} else {
			// 记录校验失败信息
			param.put("ID", id);
			param.put("IDCARD_CHECK", "CHECKFAIL");
			service.upCheckRe3(param);
			return new ReplyAjax(false, idCard.getMsg());
		}
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doCheckCustIDCard4() {//企业团队证件校验
		String name = SkyEye.getRequest().getParameter("NAME");
		String card = SkyEye.getRequest().getParameter("ID_CARD");
		IDCard idCard = new IDCard();
		idCard.setName(name);
		idCard.setIdCard(card);
		idCard.check();
		if (idCard.isReal()) {
			// 记录校验成功信息
			return new ReplyAjax(true, idCard.getPhoto());
		} else {
			// 记录校验失败信息
			return new ReplyAjax(false, idCard.getMsg());
		}
	}
	
	@aAuth(type = aAuthType.LOGIN)
    @aDev(code = "23", email = "shuoli@jiezhongchina.com", name = "李硕")
    public Reply doCheckBlackPhone() {//捷越黑名单电话校验
        Map<String, Object> map = _getParameters();
        CustomersService service = new CustomersService();
        List<Map<String,Object>> item=service.doCheckBlackPhone(map);
        if(item.size()>0){
            return new ReplyAjax(false,"此手机号为黑名单手机号");
        }
        return new ReplyAjax(null);
    }
	
	/**
	 * 立项 附件信息
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-14 上午10:14:06
	 */
	@SuppressWarnings("unchecked")
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgElectronicPhotoAlbum1() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String proCode = param.get("PRO_CODE").toString();
		String phase = "立项";//需要提供阶段参数
		 String[] listSJZD = new MaterialMgtService().getFileListByCode(phase, proCode);
		if(!param.containsKey("TYPE_")){
			param.put("TYPE_", "");
		}
		context.put("param", param);
		context.put("FILE_LIST", listSJZD);
		return new ReplyHtml(VM.html("crm/electronicPhotoAlbum1.vm", context));
	}
	
	/**
	 * 立项 附件信息
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-14 上午10:14:06
	 */
	@SuppressWarnings("unchecked")
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgElectronicPhotoAlbum2() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> listSJZD = service.getProjectFileListSrc(param);
		if(!param.containsKey("TYPE_"))
			param.put("TYPE_", "");
		context.put("param", param);
		context.put("FILE_LIST", listSJZD);
		return new ReplyHtml(VM.html("crm/electronicPhotoAlbum2.vm", context));
	}

	/**
	 * 立项 附件信息 管理页列表数据
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@SuppressWarnings("unchecked")
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgElectronicPhotoAlbumData1() {
		Map<String, Object> param = _getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		Map<String, Object> renterMap = new HashMap<String, Object>();
		if (param.containsKey("RENTER_CODE") && !param.get("RENTER_CODE").equals("")) {
			renterMap.putAll(param);
			param.put("CUSTOMER_TYPE", renterMap.get("CUSTOMER_TYPE"));
			param.put("RENTER_NAME", renterMap.get("RENTER_NAME"));
			param.put("RENTER_CODE", renterMap.get("RENTER_CODE"));
		} else {
			renterMap = service.selectRenterDetails(param);
			param.put("CUSTOMER_TYPE", renterMap.get("TYPE"));
			param.put("RENTER_NAME", renterMap.get("NAME"));
			param.put("RENTER_CODE", renterMap.get("CUST_ID"));
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// TODO LICHAO 替换新取值方式
		System.out.println("--------------------param="+param);
		List<Map<String, Object>> listXMZLFJ = service.getProjectFileListSrc(param);
		System.out.println("--------------------listXMZLFJ="+listXMZLFJ);
		// ----------------------------------
		boolean flag1 = true;
		if (flag1) {
			for (int i = 0; i < listXMZLFJ.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> xmzlfjMap = listXMZLFJ.get(i);
				if (param.containsKey("FILE_TYPE") && !"".equals(param.get("FILE_TYPE"))) {
					if (param.get("FILE_TYPE").toString().trim().equals(xmzlfjMap.get("CODE").toString().trim())) {
						param.put("FILE_PATH", param.get("PRO_CODE") + "/项目资料附件清单/" + xmzlfjMap.get("CODE"));
						String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param
								.get("FILE_PATH").toString(), false);
						List<Map<String, Object>> listMap = fcu.selectFileList(catalogId);
						if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
							map.put("PICTURE", "YES");
						} else {
							map.put("PICTURE", "NO");
						}
						map.put("DZDA_TYPE", xmzlfjMap.get("CODE"));
						map.put("CODE_TYPE", xmzlfjMap.get("CODE_TYPE"));
						map.put("CODE_TYPE_FLAG", xmzlfjMap.get("CODE_TYPE_FLAG"));
						map.put("CATALOG_ID", catalogId);
						map.put("FILE_PATH", param.get("FILE_PATH"));
						map.put("CATALOG_TYPE", "项目资料附件清单");
						map.put("RENTER_NAME", param.get("RENTER_NAME"));
						map.put("RENTER_CODE", param.get("RENTER_CODE"));
						map.put("PRO_CODE", param.get("PRO_CODE"));
						dataList.add(map);// 拼页面数据
					}
				} else {
					param.put("FILE_PATH", param.get("PRO_CODE") + "/项目资料附件清单/" + xmzlfjMap.get("CODE"));
					String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(),
							param.get("FILE_PATH").toString(), false);
					List<Map<String, Object>> listMap = fcu.selectFileList(catalogId);
					if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
						map.put("PICTURE", "YES");
					} else {
						map.put("PICTURE", "NO");// TODO 此处判断是否上传资料
					}
					map.put("DZDA_TYPE", xmzlfjMap.get("CODE"));
					map.put("CODE_TYPE", xmzlfjMap.get("CODE_TYPE"));
					map.put("CODE_TYPE_FLAG", xmzlfjMap.get("CODE_TYPE_FLAG"));
					map.put("CATALOG_ID", catalogId);
					map.put("FILE_PATH", param.get("FILE_PATH"));
					map.put("CATALOG_TYPE", "项目资料附件清单");
					map.put("RENTER_NAME", param.get("RENTER_NAME"));
					map.put("RENTER_CODE", param.get("RENTER_CODE"));
					map.put("PRO_CODE", param.get("PRO_CODE"));
					dataList.add(map);// 拼页面数据
				}
			}
		}
		int pageVM = 1;
		int rows = 10;
		if (param.containsKey("page") && param.containsKey("rows") && !param.get("page").equals("") && !param.get("page").equals("rows")) {
			pageVM = Integer.parseInt(param.get("page").toString());
			rows = Integer.parseInt(param.get("rows").toString());
		}
		int numS = pageVM * rows - rows + 1;
		List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
		for (int i = numS - 1; pageList.size() < rows && i < dataList.size(); i++) {
			pageList.add(dataList.get(i));
		}
		Page page = new Page();
		page.addDate(JSONArray.fromObject(pageList), listXMZLFJ.size());
		return new ReplyAjaxPage(page);
	}
	/**
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-16 下午02:21:50
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "上传" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddXMZL() {
		List<FileItem> fileList = _getFileItem();
		Map<String, Object> param = _getParameters();
		CustomerService customerService  = new CustomerService() ;
		
		
		try {
			if (param == null || param.isEmpty()) {
				param = this.getPostParams(fileList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException("上传文件失败");
		}
		FileCatalogUtil fcu = new FileCatalogUtil();
		if ("项目资料附件清单".equals(param.get("CATALOG_TYPE"))) {
			param.put("FILE_PATH", param.get("PRO_CODE") + "/" + param.get("CATALOG_TYPE") + "/" + param.get("FILE_TYPE").toString().trim());
//		} else if ("电子档案".equals(param.get("CATALOG_TYPE"))) {
//			param.put("FILE_PATH", param.get("CATALOG_TYPE") + "/" + param.get("FILE_TYPE").toString().trim());
		}
		// boolean flag = fcu.uploadPicture(param, fileList);
		boolean flag = false;
		{
			if (param != null && !param.isEmpty() && param.containsKey("RENTER_NAME") && param.containsKey("RENTER_CODE")
					&& param.containsKey("FILE_PATH")) {
				String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(),
						param.get("FILE_PATH").toString(), false);
				param.put("CATALOG_ID", catalogId);
				List<Map<String, Object>> list = fcu.doUploadPictureBat(param, fileList,
						fcu.rootCatalog(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param.get("FILE_PATH").toString()));
				for (Map<String, Object> map : list) {
					String proId = new CustInfoInputService().getProId(map.get("PRO_CODE").toString()) ;
					// map.putAll(param);
					flag = fcu.insertFileMessage(map);// 在数据库中记录
					 
					// insert fil_project file record   start 
						map.put("TPM_BUSINESS_PLATE", "立项") ;
						map.put("PROJECT_ID", proId) ;
						map.put("TPM_TYPE", map.get("DZDA_TYPE")) ;
//						map.put("CREATE_CODE", Security.getUser().getCode()) ;
						service.insertProjectFile(map) ;
					//  insert fil_project file record   end
					
					map.put("INSTRUCTION", "ADDPICTURE");
					map.put("RECORD_REMARK", "上传图片");
					fcu.addPictrueRecord(map);// 添加记录
				}
				flag = true;
				// if(fcu.doUploadPicture(param,fileList,
				// fcu.rootCatalog(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString()))){//上传到磁盘
				// flag = fcu.insertFileMessage(param);//在数据库中记录
				// // param.put("INSTRUCTION","ADDPICTURE");
				// // param.put("RECORD_REMARK","上传图片");
				// // fcu.addPictrueRecord(param);//添加记录
				// // flag = true;
				// }
			}
		}
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("上传", "上传", param.toString()));
		} else {
			return new ReplyAjax(flag, "上传失败");
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午09:41:03
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "下载" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public ReplyFile downFile() {
		Map<String, Object> param = _getParameters();
		
		String path;
		try {
			path = URLDecoder.decode(param.get("path").toString(),"UTF-8");
			File file = new File(path);
			String filename;
			if(StringUtil.isBlank(param.get("filename"))) filename = file.getName();
			else filename= param.get("filename").toString();
			return new ReplyFile(file, filename);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * 删除文件
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午09:41:03
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "删除" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteFile() {
		FileCatalogUtil fcu = new FileCatalogUtil();
		Map<String, Object> param = _getParameters();
		CustomerService customerService = new CustomerService() ;
		String path;
		try {
			path = URLDecoder.decode(param.get("path").toString(),"UTF-8");
			fcu.deleteFile(path, false);// 删除磁盘上的文件
			Map<String, Object> param1 = fcu.selectOperationFile(path);
			
			//暂时不删除该记录 PICTUREFILE
			//boolean flag = fcu.deleteFileById(param1.get("ID").toString());
			boolean flag = customerService.deleteProjectFileById(param1.get("ID").toString()) ;
			if (flag) {
				return new ReplyAjax(param).addOp(new OpLog("删除", "删除", param.toString()));
			} else {
				return new ReplyAjax(flag, "删除失败");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午09:41:03
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "删除" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteFile1() {
		FileCatalogUtil fcu = new FileCatalogUtil();
		Map<String, Object> param = _getParameters();
		String path = param.get("path").toString();
		fcu.deleteFile(path, false);// 删除磁盘上的文件
		Map<String, Object> param1 = fcu.selectOperationFile(path);
		boolean flag = fcu.deleteFileById(param1.get("ID").toString());
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("删除", "删除", param.toString()));
		} else {
			return new ReplyAjax(flag, "删除失败");
		}
	}

	/**
	 * 查询文件列表
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-30 上午10:49:50
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "立项", "附件信息", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectFileList() {
		Map<String, Object> param = _getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		VelocityContext context = new VelocityContext();
		context.put("fileList", fcu.selectFileList(param.get("CATALOG_ID").toString()));
		return new ReplyHtml(VM.html("crm/fileList.vm", context));
	}

	/**
	 * 查询文件列表
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-30 上午10:49:50
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectFileList1() {
		Map<String, Object> param = _getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		VelocityContext context = new VelocityContext();
		context.put("fileList", fcu.selectFileList(param.get("CATALOG_ID").toString()));
		return new ReplyHtml(VM.html("crm/fileList.vm", context));
	}

	/**
	 * 电子档案
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-14 上午10:14:06
	 */
	@SuppressWarnings("unchecked")
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgElectronicPhotoAlbum() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		List<Map<String, Object>> listSJZD = new DataDictionaryMemcached().get("电子档案");
		if (listSJZD != null && listSJZD.size() > 0 && !listSJZD.isEmpty()) {
			for (int i = 0; i < listSJZD.size(); i++) {
				Map<String, Object> sjzdMap = listSJZD.get(i);
				sjzdMap.put("REMARK", sjzdMap.get("REMARK").toString().replaceAll(" ", ""));
			}
		}
		context.put("FILE_LIST", listSJZD);
		return new ReplyHtml(VM.html("crm/electronicPhotoAlbum.vm", context));
	}

	/**
	 * 管理页列表数据
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@SuppressWarnings("unchecked")
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "查看" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgElectronicPhotoAlbumData() {
		Map<String, Object> param = _getParameters();
		FileCatalogUtil fcu = new FileCatalogUtil();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSJZD = new DataDictionaryMemcached().get("电子档案");
		boolean flag = true;
		if (listSJZD == null || listSJZD.size() <= 0 || listSJZD.isEmpty()) {
			flag = false;
		}
		int count = 0;
		for (int i = 0; listSJZD!=null && i < listSJZD.size(); i++) {
			Map<String, Object> sjzdMap = listSJZD.get(i);
			sjzdMap.put("REMARK", sjzdMap.get("REMARK").toString().replaceAll(" ", ""));
			if (param.get("CUSTOMER_TYPE").equals(sjzdMap.get("REMARK"))) {// 判断电子档案的承租人类型
				if (param.containsKey("FILE_TYPE") && !"".equals(param.get("FILE_TYPE"))) {
					if (param.get("FILE_TYPE").toString().trim().equals(sjzdMap.get("FLAG").toString().trim())) {
						count++;
					}
				} else {
					count++;
				}
			}
		}
		int pageVM = 1;
		int rows = 10;
		if (param.containsKey("page") && param.containsKey("rows") && !param.get("page").equals("") && !param.get("page").equals("rows")) {
			pageVM = Integer.parseInt(param.get("page").toString());
			rows = Integer.parseInt(param.get("rows").toString());
		}
		int numS = pageVM * rows - rows + 1;
		if (flag) {
			for (int i = numS - 1; dataList.size() < rows && i < listSJZD.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> sjzdMap = listSJZD.get(i);
				sjzdMap.put("REMARK", sjzdMap.get("REMARK").toString().replaceAll(" ", ""));
				if (param.get("CUSTOMER_TYPE").equals(sjzdMap.get("REMARK"))) {// 判断电子档案的承租人类型
					if (param.containsKey("FILE_TYPE") && !"".equals(param.get("FILE_TYPE"))) {
						if (param.get("FILE_TYPE").toString().trim().equals(sjzdMap.get("FLAG").toString().trim())) {
							param.put("FILE_PATH", "电子档案/" + sjzdMap.get("FLAG"));
							String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param
									.get("FILE_PATH").toString(), false);
							List<Map<String, Object>> listMap = fcu.selectFileList(catalogId);
							if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
								map.put("PICTURE", "YES");
							} else {
								map.put("PICTURE", "NO");
							}
							map.put("DZDA_TYPE", sjzdMap.get("FLAG"));
							map.put("CATALOG_ID", catalogId);
							map.put("FILE_PATH", param.get("FILE_PATH"));
							map.put("CATALOG_TYPE", "电子档案");
							map.put("RENTER_NAME", param.get("RENTER_NAME"));
							map.put("RENTER_CODE", param.get("RENTER_CODE"));
							dataList.add(map);// 拼页面数据
						}
					} else {
						param.put("FILE_PATH", "电子档案/" + sjzdMap.get("FLAG"));
						String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param
								.get("FILE_PATH").toString(), false);
						List<Map<String, Object>> listMap = fcu.selectFileList(catalogId);
						if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
							map.put("PICTURE", "YES");
						} else {
							map.put("PICTURE", "NO");
						}
						map.put("DZDA_TYPE", sjzdMap.get("FLAG"));
						map.put("CATALOG_ID", catalogId);
						map.put("FILE_PATH", param.get("FILE_PATH"));
						map.put("CATALOG_TYPE", "电子档案");
						map.put("RENTER_NAME", param.get("RENTER_NAME"));
						map.put("RENTER_CODE", param.get("RENTER_CODE"));
						dataList.add(map);// 拼页面数据
					}
				}
			}
		}
		Page page = new Page();
		page.addDate(JSONArray.fromObject(dataList), count);
		return new ReplyAjaxPage(page);
	}

	// /**
	// *
	// * @param
	// * @author 付玉龙 fuyulong47@foxmail.com
	// * @date 2013-10-16 下午02:21:50
	// */
	// @aPermission(name = { "客户管理", "客户资料管理", "电子档案", "管理页", "上传" })
	// @aAuth(type = aAuthType.USER)
	// @aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	// public Reply doAddDZDA(){
	// Map<String,Object> param = _getParameters();
	// List<FileItem> fileList = _getFileItem();
	// FileCatalogUtil fcu = new FileCatalogUtil();
	// param.put("FILE_PATH",
	// param.get("CATALOG_TYPE")+"/"+param.get("FILE_TYPE"));
	// // boolean flag = fcu.uploadPicture(param, fileList);
	// boolean flag = false;
	// {
	// if(param !=null && !param.isEmpty() && param.containsKey("RENTER_NAME")
	// && param.containsKey("RENTER_CODE") && param.containsKey("FILE_PATH")){
	// String catalogId =
	// fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString(),false);
	// param.put("CATALOG_ID", catalogId);
	// if(fcu.doUploadPicture(param,fileList,
	// fcu.rootCatalog(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString()))){//上传到磁盘
	// fcu.insertFileMessage(param);//在数据库中记录
	// // param.put("INSTRUCTION","ADDPICTURE");
	// // param.put("RECORD_REMARK","上传图片");
	// // fcu.addPictrueRecord(param);//添加记录
	// flag = true;
	// }
	// }
	// }
	// if(flag){
	// return new ReplyAjax(param).addOp(new OpLog("上传", "上传",
	// param.toString()));
	// }else{
	// return new ReplyAjax(flag,"上传失败");
	// }
	// }
	//
	/**
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-16 下午02:21:50
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "上传" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply updateDZDA() {
		Map<String, Object> param = _getParameters();
		System.out.println("--------------param="+param);
		List<FileItem> fileList = _getFileItem();
		FileCatalogUtil fcu = new FileCatalogUtil();
		if ("项目资料附件清单".equals(param.get("CATALOG_TYPE"))) {
			param.put("FILE_PATH", param.get("PRO_CODE") + "/" + param.get("CATALOG_TYPE") + "/" + param.get("FILE_TYPE").toString().trim());
		} else if ("电子档案".equals(param.get("CATALOG_TYPE"))) {
			param.put("FILE_PATH", param.get("CATALOG_TYPE") + "/" + param.get("FILE_TYPE").toString().trim());
		}
		// boolean flag = fcu.uploadPicture(param, fileList);
		boolean flag = false;
		{
			if (param != null && !param.isEmpty() && param.containsKey("RENTER_NAME") && param.containsKey("RENTER_CODE")
					&& param.containsKey("FILE_PATH")) {
				String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(),
						param.get("FILE_PATH").toString(), false);
				param.put("CATALOG_ID", catalogId);
				if (fcu.doUploadPicture(param, fileList,
						fcu.rootCatalog(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param.get("FILE_PATH").toString()))) {// 上传到磁盘
					fcu.insertFileMessage(param);// 在数据库中记录
					// param.put("INSTRUCTION","ADDPICTURE");
					// param.put("RECORD_REMARK","上传图片");
					// fcu.addPictrueRecord(param);//添加记录
					flag = true;
				}
			}
		}
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("上传", "上传", param.toString()));
		} else {
			return new ReplyAjax(flag, "上传失败");
		}
	}

	/**
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午09:41:03
	 */
//	@aPermission(name = { "客户管理", "客户资料管理", "电子档案", "下载" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public ReplyFile downFile1() {
		Map<String, Object> param = _getParameters();
		String path = param.get("path").toString();
		File file = new File(path);
		String filename;
		if(StringUtil.isBlank(param.get("filename"))) filename = file.getName();
		else filename= param.get("filename").toString();
		return new ReplyFile(file, filename);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply showPicture() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/showPicture.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply showImg() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("project/showPicute.vm", context));
	}

	//
	// /**
	// *
	// * @param
	// * @author 付玉龙 fuyulong47@foxmail.com
	// * @date 2013-10-16 下午02:21:50
	// */
	// @aPermission(name = { "客户管理", "客户资料管理", "电子档案", "管理页", "文件列表", "删除" })
	// @aAuth(type = aAuthType.USER)
	// @aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	// public Reply doDeleteDZDA(){
	// Map<String,Object> param = _getParameters();
	// FileCatalogUtil fcu = new FileCatalogUtil();
	// boolean flag = false;
	// fcu.deleteFile(param.get("ORIGINAL_PATH").toString(),false);//删除磁盘上的文件
	// flag = fcu.deleteFileById(param.get("ID").toString());
	// if(flag){
	// return new ReplyAjax(param).addOp(new OpLog("删除电子档案", "删除",
	// param.toString()));
	// }else{
	// return new ReplyAjax(flag,"删除失败");
	// }
	// }
	// 处理自设的 传参数
	public Map<String, Object> getPostParams(List<FileItem> items) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();
			if (item.isFormField() && null == item.getName()) {
				result.put(item.getFieldName(), inputStream2String(item.getInputStream()));
			}
		}

		return result;
	}

	private String inputStream2String(InputStream is) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while (null != (line = bf.readLine())) {
			sb.append(line);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		String str = new String("test") ;
		map.put("test",str) ;
		System.out.println(map.get("test").equals("test"));
	}
	
	//---------------------------------------------------------------张路--------2014-03-11---begin	
	/**
	 * 进入上传资料主页
	 * 需要传入的参数
	 * 1、阶段
	 * 2、项目编号
	 * 3、阶段是付款的，需再传入付款id
	 * @author leedsjung
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
	public Reply toMgElectronicPhotoAlbum11() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		System.out.println("================yx=========="+param);
		String HTSH = "";
		if (param.containsKey("HTSH") && param.get("HTSH") != null && !"".equals(param.get("HTSH").toString())) {
			HTSH = param.get("HTSH").toString();
		}
		//add gengchangbao JZZL-258 2016年7月13日17:02:48 Start
		String JCSH = "";
		if (param.containsKey("JCSH") && param.get("JCSH") != null && !"".equals(param.get("JCSH").toString())) {
			JCSH = param.get("JCSH").toString();
		}
		//add gengchangbao JZZL-258 2016年7月13日17:02:48 End
		context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
		String proId = param.get("PROJECT_ID").toString();
		String phase = StringUtil.isBlank(param.get("PHASE"))?"立项":param.get("PHASE").toString();
		param.put("PHASE", phase);
		List<Map<String, Object>> mts = this.service.getDocAndFiles_New(proId, phase,HTSH,JCSH);
		context.put("mts", mts);
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/leedsnew/electronicPhotoAlbum1.vm", context));
	}

	/**
	 * 附件信息 管理页列表数据
	 * 
	 * 张路
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
	public Reply toMgElectronicPhotoAlbumData11() {
		Map<String, Object> param = _getParameters();
		String proCode ;
		if(StringUtil.isBlank(param.get("PRO_CODE")))
			proCode = new CustInfoInputService().getProCode(param.get("PROJECT_ID").toString());
		else proCode = param.get("PRO_CODE").toString();
		String phase = StringUtil.isBlank(param.get("PHASE"))?"立项":param.get("PHASE").toString();
//		String phase = "立项";//需要提供阶段参数phase = param.get("PHASE").toString();
		
		if (param.get("mainType") != null && !"".equals(param.get("mainType").toString()) && "合同审核".equals(param.get("mainType").toString())) {
			phase = "放款前";
		}
		if (param.get("mainType") != null && !"".equals(param.get("mainType").toString()) && "交车审核".equals(param.get("mainType").toString())) {
			phase = "交车审核";
		}
		
		List<Map<String, Object>> listSJZD;
		DataTempletService mm = new DataTempletService();
		if(phase.startsWith("放款") || phase.startsWith("交车审核")) {
			String fkId = param.get("FK_ID").toString();
			listSJZD = mm.getLevelOneListFK(phase ,proCode, fkId);
		} else if(phase.equals("立项")) {
			String mainType = param.get("mainType").toString();
//			listSJZD = mm.getLevelOneListLX(mainType, proCode);
			listSJZD = mm.getLevelOneList_New(mainType, proCode);
		} else {
			listSJZD = mm.getLevelOneList(phase, proCode);
		}
		try {
			// Replace  --   WM_CONCAT 
			if(listSJZD != null && !listSJZD.isEmpty()){
				for(Map<String, Object> m :listSJZD){
					String id = m.get("ID").toString(); // 不可能为空， 空的话就不用往下走了，下面都依懒它呢。
					if(StringUtils.isBlank(id)){
						continue;
					}
					List<Map<String,Object>> files = Dao.selectList("materialMgt.selectFilesByParentId", m);
					if(files != null && !files.isEmpty()){
						String paths = "", path_scales = "", sub_ids = "",  names = "" , check_status_ ="";
						for(Map<String,Object> m_ : files){
							String pdf_path_ = (String) m_.get("PDF_PATH");
							String pdf_path_scale = (String) m_.get("PDF_PATH_SCALE");
							String id_ = m_.get("ID").toString();
							String name_ = m_.get("NAME")+"";
							String check_status__ =  m_.get("CHECK_STATUS")+"";
							
							paths +="," + pdf_path_;
							path_scales +="," + pdf_path_scale;
							sub_ids +="," + id_;
							names +="," + name_;
							check_status_ +="," + check_status__;
						}
						if(StringUtils.isNotBlank(paths)){
							paths = paths.replaceFirst(",", "");
							m.put("PATHS", paths);
						}
						if(StringUtils.isNotBlank(path_scales)){
							path_scales = path_scales.replaceFirst(",", "");
							m.put("PATH_SCALES", path_scales);
						}
						if(StringUtils.isNotBlank(sub_ids)){
							sub_ids = sub_ids.replaceFirst(",", "");
							m.put("SUB_IDS", sub_ids);
						}
						if(StringUtils.isNotBlank(names)){
							names = names.replaceFirst(",", "");
							m.put("NAMES", names);
						}
						if(StringUtils.isNotBlank(check_status_)){
							check_status_ = check_status_.replaceFirst(",", "");
							m.put("CHECK_STATUS_", check_status_);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("服务错误："+e.getMessage());
		}


		Page page = new Page();
		page.addDate(JSONArray.fromObject(listSJZD), listSJZD.size());
		return new ReplyAjaxPage(page);
	}
		
		/**
		 * 上传文件
		 * 张路
		 */
		@aAuth(type = aAuthType.ALL)
		@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
		public Reply doUploadPic() {
			List<FileItem> fileList = _getFileItem();
			Map<String, Object> p1 = FileUploadService.uploadFileForOne(fileList);
			Boolean flag=true;
			String msg="";
			if(!p1.containsKey("TPM_TYPE")){
				List<Map<String,Object>> list=Dao.selectList("materialMgt.getType",p1);
				Map<String,Object> m=new HashMap<String,Object>();
				//String temp_file_name = p1.get("FILE_NAME").toString().split("_")[0];
				String temp_file_name = p1.get("FILE_NAME").toString().substring(0, 1);
				for(int i=0;i<list.size();i++){
					m=list.get(i);
					if(m.get("TPM_TYPE").toString().contains(temp_file_name) && (p1.get("TPM_CUSTOMER_TYPE")==null || p1.get("TPM_CUSTOMER_TYPE").toString().equals(m.get("TPM_CUSTOMER_TYPE").toString()))){
						Map<String, Object> p2 = new HashMap<String, Object>();
						p2.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
						p2.put("TPM_BUSINESS_PLATE", p1.get("TPM_BUSINESS_PLATE"));
						p2.put("TPM_TYPE", m.get("TPM_TYPE"));
						p2.put("PDF_PATH", p1.get("FILE_PATH"));
						p2.put("PDF_PATH_SCALE",  p1.get("SCALE_PATH") );
						p2.put("NAME", p1.get("FILE_NAME").toString().replace(" ","_"));
						p2.put("PROJECT_ID", p1.get("PROJECT_ID"));
						p2.put("CREATE_CODE", Security.getUser()==null? "god" : Security.getUser().getCode());
						p2.put("REMARK", p1.get("REMARK"));
						p2.put("FILE_LEVEL", 2);
						p2.put("FILE_TYPE", p1.get("FILE_TYPE"));
						p2.put("PARENT_ID", m.get("PARENT_ID"));
						p2.put("TPM_CUSTOMER_TYPE", p1.get("TPM_CUSTOMER_TYPE"));
						p2.put("FK_ID", p1.get("FK_ID")==null?"":p1.get("FK_ID"));
						Dao.insert("materialMgt.insertFpf", p2);
					}
				}
			}else{
				Map<String, Object> p2 = new HashMap<String, Object>();
				p2.put("ID", Dao.getSequence("SEQ_FIL_PROJECT_FILE"));
				p2.put("TPM_BUSINESS_PLATE", p1.get("TPM_BUSINESS_PLATE"));
				p2.put("TPM_TYPE", p1.get("TPM_TYPE"));
				p2.put("PDF_PATH", p1.get("FILE_PATH"));
				p2.put("PDF_PATH_SCALE",  p1.get("SCALE_PATH") );
				p2.put("NAME", p1.get("FILE_NAME").toString().replace(" ","_"));
				p2.put("PROJECT_ID", p1.get("PROJECT_ID"));
				p2.put("CREATE_CODE", Security.getUser()==null? "god" : Security.getUser().getCode());
				p2.put("REMARK", p1.get("REMARK"));
				p2.put("FILE_LEVEL", 2);
				p2.put("FILE_TYPE", p1.get("FILE_TYPE"));
				p2.put("PARENT_ID", p1.get("PARENT_ID"));
				p2.put("TPM_CUSTOMER_TYPE", p1.get("TPM_CUSTOMER_TYPE"));
				p2.put("FK_ID", p1.get("FK_ID")==null?"":p1.get("FK_ID"));
				Dao.insert("materialMgt.insertFpf", p2);
			}
			return null;
		}

		@aAuth(type = aAuthType.LOGIN)
		@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
		public Reply deleteFile11() {
			Map<String, Object> param = _getParameters();
			String path;
			try {
				path = URLDecoder.decode(param.get("path").toString(),"UTF-8");
				int i = 0;
				if (param.get("ID") != null) {
					i = Dao.delete("materialMgt.delFpf", param.get("ID").toString());
					if(i > 0) FileUploadService.deleteFile(path);
				} else if (param.get("PARENT_ID") != null) {
					i = Dao.delete("materialMgt.delAllFpf", param.get("PARENT_ID").toString());
					if(i > 0 && path != null && !"".equals(path)) {
						String[] paths = path.split(",");
						if (paths != null && paths.length > 0) {
							for (int index = 0; index < paths.length ; index++) {
								if (paths[index] != null && !"".equals(paths[index])) {
									FileUploadService.deleteFile(paths[index]);
								}
							}
						}
						
						 
					}
					
				}
				//删除记录
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ReplyAjax(true);
		}
		@aAuth(type = aAuthType.LOGIN)
		@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
		public Reply changePicDir() {
			Map<String, Object> param = _getParameters();
			Map<String, Object> p1 = new HashMap<String, Object>();
			p1.put("ID", param.get("ID"));
//			p1.put("TPM_CUSTOMER_TYPE", param.get("TPM_CUSTOMER_TYPE"));
			p1.put("TPM_TYPE", param.get("TPM_TYPE"));
			p1.put("PARENT_ID", param.get("PARENT_ID"));
			Dao.update("materialMgt.updateFpf", p1);
			return new ReplyAjax(true);
		}
		
		/**
		 * 查询文件列表
		 * 张路
		 */
		@aAuth(type = aAuthType.LOGIN)
		@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
		public Reply selectFileList11() {
			Map<String, Object> param = _getParameters();
			VelocityContext context = new VelocityContext();
			if(StringUtil.isBlank(param.get("PARENT_ID"))) param.put("PARENT_ID", 0);
			context.put("fileList", Dao.selectList("materialMgt.getFpfs", param));
			return new ReplyHtml(VM.html("crm/leedsnew/fileList.vm", context));
		}
	//---------------------------------------------------------------张路--------2014-03-11---end	
		
		@aAuth(type = aAuth.aAuthType.LOGIN)
		@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
		public Reply turnThePage(){
			Map<String, Object> m = _getParameters();
			HttpServletRequest request = SkyEye.getRequest();
			String msg = "";
			Boolean flag = true;
			Map<String, Object> map=Dao.selectOne("materialMgt.getParameter",m);
			map.put("TYPE", m.get("TYPE"));
			List<Map<String, Object>> list=Dao.selectList("materialMgt.turnThePage",map);
			int j=0;
			Map<String, Object> m1=new HashMap<String, Object>();
			for(int i=0;i<list.size();i++){
				m1=list.get(i);
				if(m1.get("ID").toString().equals(m.get("ID").toString())){
					j=i+1;
					break;
				}
			}
			if(j==list.size()){
				flag=false;
				if("1".equals(m.get("TYPE").toString())){
					msg="已到第一页";
				}else{
					msg="已到最后一页";
				}
			}else{
				Map<String,Object> map1=list.get(j);
				String picSrc = request.getContextPath()+"/leeds/cust_info_input/CustInfoInput!readPic.action?path="+map1.get("PDF_PATH");
				map1.put("picSrc", picSrc);
				map1.put("id", map1.get("ID"));
				map1.put("name", map1.get("NAME"));
				map1.put("path", map1.get("PDF_PATH"));
				map1.put("statu", map1.get("STATU"));
				return new ReplyAjax(flag,JSONObject.fromObject(map1), msg);
			}
			return new ReplyAjax(flag, msg);
		}
		/**
		 * 
		 * @author xgm 2015年4月28日
		 */
		@aAuth(type = aAuth.aAuthType.LOGIN)
		@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
		public Reply getImg(){
			Map<String, Object> param = _getParameters();
			VelocityContext context = new VelocityContext();
			param=Dao.selectOne("materialMgt.getImage",param);
			param.put("path", param.get("PATH"));
			context.put("param", param);
			return new ReplyHtml(VM.html("crm/showPicture.vm", context));
		}
		
		
		@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
		@aAuth(type = aAuthType.ALL)
		public Reply expPdf() {
			Map<String, Object> param = _getParameters();
			List<Map<String,Object>> payList=service.getAllMaterial(param);
			return new ReplyFile(ZipUtil.zipFiles(payList).toByteArray(), "资料清单.zip");
		}
}
