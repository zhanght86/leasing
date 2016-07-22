package com.pqsoft.leeds.cust_info_input.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.materialMgt.service.MaterialMgtService;
import com.pqsoft.leeds.sign_in.service.SignInService;
import com.pqsoft.leeds.utils.ExcelUtil;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.StringUtils;

/**
 * 运营资料录入
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class CustInfoInputAction extends Action {
	private CustInfoInputService service = new CustInfoInputService();

	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "运营资料录入"})
	@aDev(code = "leeds", name = "leeds", email = "leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		String SHOW_FLAG="";
		if(params !=null && params.get("SHOW_FLAG")!=null && !params.get("SHOW_FLAG").equals("")){
			SHOW_FLAG=params.get("SHOW_FLAG")+"";
		}
//		String proId = "556172";
		String proId = params.get("PROJECT_ID").toString();
		// 取得tabs、对应的url
		// 承租人
		List<Map<String, Object>> recs = Dao
				.execSelectSql(""
						+ "SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT,A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
						+ "A.PLATFORM_TYPE, B.NAME, B.TYPE, " + "C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID "
						+ "FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  " + " WHERE A.ID='" + proId + "' AND A.CLIENT_ID=B.ID"
						+ " AND A.ID=C.PROJECT_ID(+)");
		Map<String, Object> rec = recs.get(0);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
		String TYPE = rec.get("TYPE").toString();
		String SCHEME_ID = rec.get("SCHEME_ID") == null ? "0" : rec.get("SCHEME_ID").toString();
		String SCHEME_ROW_NUM = rec.get("SCHEME_ROW_NUM") == null ? "0" : rec.get("SCHEME_ROW_NUM").toString();
		String PLATFORM_TYPE = rec.get("PLATFORM_TYPE").toString();
//		String proCode = service.getProCode(proId);
		String hasGuarantee = rec.get("GUARANTEE") == null ? "1" : rec.get("GUARANTEE").toString();
		String hasJt = rec.get("JOINT_TENANT") == null ? "1" : rec.get("JOINT_TENANT").toString();
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人");
		if(SHOW_FLAG.equals("1")){
//			m.put("url", "/customers/Customers!toViewCustInfo.action?"
//					+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
			m.put("url", "/customers/Customers!toViewCustInfoMain.action?"
					+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
			tabs.add(m);
		}else{
//			m.put("url", "/customers/Customers!toUpdateCustInfo1.action?"
//					+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&PROJECT_ID="+proId);
			m.put("url", "/customers/Customers!toUpdateCustInfoMain.action?"
					+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
			tabs.add(m);
		}
		for (int i = 0; i <recs.size(); i++) {
			rec = recs.get(i);
			int a=i+1;
			PLATFORM_TYPE = rec.get("PLATFORM_TYPE").toString();
			SCHEME_ROW_NUM = rec.get("SCHEME_ROW_NUM") == null ? "0" : rec.get("SCHEME_ROW_NUM").toString();
			SCHEME_ID = rec.get("SCHEME_ID") == null ? "0" : rec.get("SCHEME_ID").toString();
			if (!"资料录入岗".equals(params.get("TASK_NAME").toString())) {
				if (!(SCHEME_ID == "0" || SCHEME_ROW_NUM == "0")) {
					Map<String, Object> m3 = new HashMap<String, Object>();
					m3.put("title", "方案"+a);
					m3.put("url", "/project/project!SchemeView.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
							+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
					tabs.add(m3);
				}
			}
		}

		// 共同承租人
		if (!hasJt.trim().equals("1")) {
			String JOINT_TENANT_ID = rec.get("JOINT_TENANT_ID").toString();
			String JOINT_TENANT_TYPE = rec.get("JOINT_TENANT_TYPE").toString();
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "共同承租人");
//			m2.put("url", "/customers/Customers!toViewCustInfo.action?"
//					+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM");
			if(SHOW_FLAG.equals("1")){
				m2.put("url", "/customers/Customers!toViewCustInfoGTCZR.action?"
						+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM&PROJECT_ID="+proId);
				tabs.add(m2);
			}else{
				m2.put("url", "/customers/Customers!toUpdateCustInfoGTCZR.action?"
						+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		// 担保人
		if (!hasGuarantee.trim().equals("1")) {
			Map dbr = Dao.selectOne("project.getDbr", params);
			if (dbr == null) {
				dbr = new HashMap();
			}
			dbr.put("TIME", new Date().getTime());

			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("title", "担保人");
			if(SHOW_FLAG.equals("1")){
				m2.put("url", "/credit/CreditGuarantor!toViewGuarantorInfo.action?"
						+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=view");
				tabs.add(m2);
			}else{
				m2.put("url", "/credit/CreditGuarantor!toUpdateGuarantorInfo.action?"
						+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=update&date="+dbr.get("TIME"));
				tabs.add(m2);
			}
		}
//		// 附件资料
//		Map<String, Object> m1 = new HashMap<String, Object>();
//		m1.put("title", "附件资料");
//		m1.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?" + "PRO_CODE=" + proCode + "&PROJECT_ID=" + proId);
//		tabs.add(m1);
		if ("1".equals(SHOW_FLAG)) {
			for (Map<String, Object> map : tabs) {
				map.put("url", map.get("url") + "&SHOW_FLAG=1");
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		return new ReplyHtml(VM.html("leeds/cust_info_input/toMain.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "运营资料录入", "加载承租人"})
	@aDev(code = "leeds", name = "leeds", email = "leedsjung@126.com")
	public Reply test1() {
		String proId = "556172";
		new MaterialMgtService().getFileList("立项", proId);
		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "leeds", name = "leeds", email = "leedsjung@126.com")
	public Reply readPic() {
		Map<String, Object> params = _getParameters();
		String path = params.get("path").toString();
		HttpServletResponse response = SkyEye.getResponse();
		OutputStream os = null;
		String s = ResMime.get(path.substring(path.lastIndexOf(".")).replace(".", ""));
		if (s != null) response.setContentType(s);
		try {
			InputStream is = new FileSystemResource(path).getInputStream();
			if (is != null) {
				byte[] bytes = FileCopyUtils.copyToByteArray(is);
				response.setContentLength(bytes.length);
				os = response.getOutputStream();
				os.write(bytes);
				os.flush();
			} else {
				this.logger.warn("\tdosen't find resource : " + path);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "运营资料录入", "查看图片"})
	@aDev(code = "leeds", name = "leeds", email = "leedsjung@126.com")
	public Reply seeImg() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		String proId = param.get("PROJECT_ID").toString();
		String phase = StringUtil.isBlank(param.get("PHASE"))?"立项":param.get("PHASE").toString();
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		
		String sql="SELECT CASE B.TYPE WHEN 'NP' THEN 1 ELSE 2 END CUST_TYPE,"
				+ " CASE A.JOINT_TENANT WHEN '2' THEN 3 WHEN '3' THEN 4 ELSE 0 END GT_TYPE,"
				+ " CASE A.GUARANTEE WHEN '2' THEN 5 WHEN '3' THEN 6 ELSE 0 END GUA_TYPE"
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.CLIENT_ID = B.ID AND A.ID='"+proId+"'";
		List<Map<String, Object>> ms = Dao.execSelectSql(sql);
		
		Map<String, Object> m = ms.get(0);
		List<Map.Entry<String, Object>> entrys = new ArrayList<Map.Entry<String, Object>>(m.entrySet());
		Collections.sort(entrys, new Comparator<Map.Entry<String, Object>>(){
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				return o1.getValue().toString().compareTo(o2.getValue().toString());
			}
		});
		List<Map<String, Object>> mainTypes = new DataDictionaryMemcached().get("主体类型");
		for(Map.Entry<String, Object> me : entrys) {
			
			if(!phase.equals("立项")&&!me.getKey().equals("CUST_TYPE")) continue;
			if(me.getValue().toString().equals("0")) continue;
			for(Map<String, Object> mainType : mainTypes) {
				if(mainType.get("CODE").toString().equals(me.getValue().toString())) {
					mts.add(mainType);
				}
			}
		}
		context.put("mts", mts);
		return new ReplyHtml(VM.html("crm/leedsnew/electronicPhotoAlbum1.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "初审岗", "查看图片"})
	@aDev(code = "170039", name = "yangxue", email = "yangxue@pqsoft.com")
	public Reply seeImg1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		// PROJECT_ID，加载项目上传的附件
		String proId = params.get("PROJECT_ID").toString();
		String phase = params.get("PHASE") == null ? "立项" : params.get("PHASE").toString();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> pics;
		List<Map<String, Object>> pictype;
		String custType = params.get("CUST_TYPE") == null ? "" : params.get("CUST_TYPE").toString();
		if (custType.startsWith("承租人") || custType.startsWith("担保人") || custType.startsWith("共同承租人")){
			pics = new MaterialMgtService().getFileInfoList(phase, proId, custType);//项目所包含文件
			pictype = new MaterialMgtService().getFileTypeList(phase, proId, custType);//项目包含文件类型
		}else {
			pics = new MaterialMgtService().getFileInfoList(phase, proId);
			pictype = new MaterialMgtService().getFileTypeList(phase, proId);//项目包含文件类型
		}

		context.put("pics", pics);
		context.put("pictype", pictype);
		return new ReplyHtml(VM.html("leeds/imgViewer/seeImg1.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "运营资料录入", "查看图片"})
	@aDev(code = "170012", name = "wuyanfei", email = "wuyanfeijob@163.com")
	public Reply updatePictureStatus() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();

		int i = service.updatePictureStatus(params);

		System.out.println(params);
		// int i = 2 ; 测试
		return new ReplyAjax(i > 0, params);
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name={"立项审批", "运营资料录入", "批量审核通过"})
	@aDev(code = "170039", name = "yangxue", email = "yangxue@pqsoft.com")
	public Reply updatePictureStatusPiLiang() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		int i = service.updatePictureStatusPiLiang(params);
		String msg = "";
		boolean flag = true;
		if(i>0){
			msg = "批量通过，完成";
		}else {
			msg = "批量通过，失败";
			flag = false;
		}
		return new ReplyAjax(flag, msg);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static void main(String[] args) {
		ExcelUtil eu = new ExcelUtil();
		try {
			List<Map<String, Object>> ds1 = eu.importXls("F:\\test\\testFiles\\20150225181242_0925.xls");
			List<Map<String, Object>> ds2 = eu.importXls("F:\\test\\testFiles\\20150317172344_2766.xlsx");
			int count = 0;
			ds1.remove(0);
			ds1.remove(0);
			ds2.remove(0);
			ds2.remove(0);
			System.out.println("共有【" + ds1.size() + "】个人，参加邯郸银行校园招聘！");
			System.out.println("###########################");
			System.out.println("共有【" + ds2.size() + "】个人，参加邯郸银行社会招聘！");
			System.out.println("###########################");
			for (Map<String, Object> d1 : ds1) {
				for (Map<String, Object> d2 : ds2) {
					if (d1.get("cell2").toString().equals(d2.get("cell2"))) {
						count++;
						System.out.println("找到【" + count + "】个重复报名者");
						System.out.println("他的校招信息：" + JSONObject.fromObject(d1).toString());
						System.out.println("他的社招信息：" + JSONObject.fromObject(d2).toString());
						System.out.println("********************************************");
					}
				}
			}
			System.out.println("########################");
			System.out.println("########################");
			System.out.println("找到【" + count + "】个重复报名者");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
