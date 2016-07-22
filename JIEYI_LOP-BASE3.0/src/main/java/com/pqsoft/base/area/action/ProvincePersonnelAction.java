package com.pqsoft.base.area.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import net.sf.json.JSONArray;

import com.pqsoft.base.area.service.ProvincePersonnelService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;


public class ProvincePersonnelAction extends Action {

	private ProvincePersonnelService service= new ProvincePersonnelService();
	private String path = "base/Province/";
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","省份人员配置"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply execute() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"ProvincePersonnel.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","省份人员配置"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply selectAllProvincePersonnel(){
		Map<String,Object> param=_getParameters();
		System.out.println(param+"===============");
		Page page = new Page();
		JSONArray quyu = JSONArray.fromObject(service.getAllProvincePersonnel(param));
		page.addDate(quyu, quyu.size());
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","省份人员配置", "人员查询"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply selectpeople(){
		Map<String,Object> param=_getParameters();
		JSONArray renyuan = JSONArray.fromObject(service.getselectpeople(param));
		return new ReplyAjax(renyuan);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","省份人员配置", "人员查询"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply selectAllpeople(){
		Map<String,Object> param=_getParameters();
		System.out.println(param+"==============");
		JSONArray allry = JSONArray.fromObject(service.getselectAllpeople(param));
		return new ReplyAjax(allry);
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","省份人员配置", "保存"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply saveConfig(){
		Map<String,Object> param=_getParameters();
		String msg;
		boolean flag;
		String USER_ID[]=param.get("USER_ID").toString().split(",");
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("PROVINCE_ID", param.get("PROVINCE_ID"));
		service.delProvincePeople(m);
		int num=0;
		for(int i=0;i<USER_ID.length;i++)
		{
			m.put("USER_ID", USER_ID[i]);
			num=num+service.insProvincePeople(m);
		}
		if(num==USER_ID.length)
		{
			msg ="保存成功!";
			flag = true; 
		}else
		{
			msg ="保存失败!";
			flag = false; 
		}
		return new ReplyAjax(flag, msg);
	}
}
