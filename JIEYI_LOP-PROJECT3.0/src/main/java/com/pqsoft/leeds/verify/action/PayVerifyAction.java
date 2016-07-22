package com.pqsoft.leeds.verify.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.sign_in.service.SignInService;
import com.pqsoft.leeds.utils.ExcelUtil;
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
 * 放款审核
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2015年2月27日 上午11:35:27
 */
public class PayVerifyAction extends Action {
	private CustInfoInputService service = new CustInfoInputService();
	@Override
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"立项审批", "运营资料录入"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> params = _getParameters();
		context.put("params", params);
		String proId = "556104";
		//取得tabs、对应的url
		//承租人
		List<Map<String, Object>> recs = Dao.execSelectSql("SELECT A.CLIENT_ID, B.NAME, B.TYPE FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B"
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID");
		Map<String, Object> rec = recs.get(0);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
		String TYPE = rec.get("TYPE").toString();
		String proCode = service.getProCode(proId);
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", "承租人");
		m.put("url", "/customers/Customers!toUpdateCustInfo1.action?"
				+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update");
		tabs.add(m);
		//附件资料
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("title", "附件资料");
		m1.put("url", "/crm/Customer!toMgElectronicPhotoAlbum1.action?"
				+ "PRO_CODE="+proCode+"&updateStart=jdbpm");
		tabs.add(m1);
		context.put("tabs", JSONArray.fromObject(tabs));
		return new ReplyHtml(VM.html("leeds/cust_info_input/toMain.vm", context));
	}
	
}
