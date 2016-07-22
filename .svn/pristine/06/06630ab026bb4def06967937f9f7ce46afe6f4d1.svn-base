package com.pqsoft.newRentWrite.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.jfree.util.Log;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.newRentWrite.service.whiteListService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.RarFile;
import com.pqsoft.util.ReplyExcel;

public class whiteListAction extends Action {

	public VelocityContext context = new VelocityContext();

	private FiEbankUtil util = new FiEbankUtil();

	whiteListService service = new whiteListService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	// 租金台账监控入口
	@aPermission(name = { "报表管理", "白名单", "列表" })
	@aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
	@aAuth(type = aAuthType.USER)
	public Reply rentAccountMonitorPage() {

		Map map = this._getParameters();

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// // 开户行所属总行
		// List<Object> BANK_NAME_LIST = (List) new
		// DataDictionaryMemcached().get("开户行所属总行");
		// context.put("BANK_NAME_LIST", BANK_NAME_LIST);
		//
		// // ITEM_FLAG_LIST
		// List<Object> ITEM_FLAG_LIST = (List) new
		// DataDictionaryMemcached().get("租金类别");
		// context.put("ITEM_FLAG_LIST", ITEM_FLAG_LIST);
		// 业务类型
		List<Object> ZJ_TYPE_LIST = (List) new DataDictionaryMemcached().get("证件类型");
		context.put("ZJ_TYPE_LIST", ZJ_TYPE_LIST);
		return new ReplyHtml(VM.html("newRentWrite/whiteList.vm", context));

	}

	// 查询按钮
	@aPermission(name = { "报表管理", "白名单", "查询" })
	@aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
	@aAuth(type = aAuthType.ALL)
	public Reply query() {
		Map<String, Object> param = _getParameters();
		Log.info(param + "--------");
		Page page = service.query(param);
		return new ReplyAjaxPage(page);
	}

	// 导出下载
	@aPermission(name = { "报表管理", "白名单", "下载" })
	@aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
	@aAuth(type = aAuthType.ALL)
	public Reply excelUpload() {
		Map map = _getParameters();
		Object mapObj = map.get("IDS");
		if(mapObj!=null && (!mapObj.equals(""))){
            String[] split = String.valueOf(mapObj).split(",");
            map.put("IDS", split);
		}
		if (map != null && map.get("exportType") != null && !map.get("exportType").equals("")) {
			String uploadType = map.get("exportType").toString();
			List listReturn = new ArrayList();
//			if (uploadType.equals("all")) {
				listReturn = service.excelAll(map);
//			}
			map.put("dataList", listReturn);
			String SHH = "00000001";// 商户号
			String BS = "S";// 标示，S为收款
			String FGF = "_";// 分割符
			String BBH = "02";// 版本号
			String DA = DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "");
			// excel导出
			String no = new FiEbankUtil().getExportNo("租金监控");
			return new ReplyExcel(util.exportWhiteList(map),
					"rent" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") + no + ".xls");// 山重导出
		}
		return this.rentAccountMonitorPage();
	}

	// 打包下载 BASEPATH
	@aPermission(name = { "报表管理", "白名单", "下载" })
	@aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
	@aAuth(type = aAuthType.ALL)
	public Reply DBXZ() {
		Map map = _getParameters();
		
		Object mapObj = map.get("IDS");
		if(mapObj!=null && (!mapObj.equals(""))){
            String[] split = String.valueOf(mapObj).split(",");
            map.put("IDS", split);
		}
		Log.info(map + "=========================");

		RarFile rf = new RarFile();

		try {

			// 临时存放目录
			File tempfile = new File(/*"D:"+File.separator+*/"/tempfile");
			Log.info(tempfile.getPath());
			// 如果文件夹不存在则创建
			if (!tempfile.exists() && !tempfile.isDirectory()) {
				Log.info("//不存在");
				tempfile.mkdir();
			} else {
				deletefile(tempfile);
				tempfile.mkdir();
				Log.info("//目录已创建");
			}
			// 存放压缩包目录
			File tempfile2 = new File(/*"d:"+File.separator+*/"/tempfile2");
			Log.info(tempfile2.getAbsolutePath());
			// 如果文件夹不存在则创建
			if (!tempfile2.exists() && !tempfile2.isDirectory()) {
				Log.info("//不存在");
				tempfile2.mkdir();
			} else {
				Log.info("//目录存在");
			}

			Log.info(service.down(map));
			String no = new FiEbankUtil().getExportNo("rar");
			String dt = DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "");
			rf.rarFiles("", service.down(map), tempfile.getPath(),
					tempfile2.getPath() + File.separator + dt + no + ".rar");

			File ff = new File(tempfile2+File.separator+dt + no + ".rar");
			return new ReplyFile(ff,dt + no + ".rar");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.rentAccountMonitorPage();
	}
	
	//工具方法
	private void deletefile(File file){
		   if(file.exists()){
		    if(file.isFile()){
		     file.delete();
		    }else if(file.isDirectory()){
		     File files[] = file.listFiles();
		     for(int i=0;i<files.length;i++){
		      this.deletefile(files[i]);
		     }
		    }
		    file.delete();
		   }else{
		    System.out.println("所删除的文件不存在！"+'n');
		   }
		}
}
