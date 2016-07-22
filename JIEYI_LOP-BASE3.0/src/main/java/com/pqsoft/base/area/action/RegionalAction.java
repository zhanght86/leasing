package com.pqsoft.base.area.action;

import java.util.Date;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

public class RegionalAction extends Action {
	
	private String path = "base/regional/";
	private AreaService service= new AreaService();

	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"AreaQ.vm", null));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectAllCountries() {
		Page page = new Page();
		JSONArray quyu = JSONArray.fromObject(service.getAllQuYu());
		page.addDate(quyu, quyu.size());
		return new ReplyAjaxPage(page);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply  doDeleteArea(){
		Map<String,Object> param = _getParameters();	
		//add by lishuo 01-13-2016 Start 删除区域同时删除区域的负责人
		if(!"".equals(param.get("ID")) && param.get("ID") != null){
			service.deleteAreaMan(param);
		}
		//add by lishuo 01-13-2016 End
		return new ReplyAjax(service.deleteArea(param) && service.deleteConfig(param)).addOp(new OpLog("区域管理","删除",param.toString()));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectQuYuSubset(){
		Map<String,Object> param = _getParameters();
		JSONArray quYuSubset = JSONArray.fromObject(service.getQuYuSubset(param));
		return new ReplyAjax(quYuSubset);
	}
	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aDev(code = "23", email = "shuoli@jiezhongchina.com", name = "李硕")
	public Reply searchForModify(){
		Map<String,Object> param = _getParameters();
		JSONArray data = JSONArray.fromObject(service.SearchForModify(param));
		return new ReplyAjax(data);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public  Reply doAddArea(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		Map<String,Object> mm=null;
		Object COUNT= null;
		param.remove("param");
		param.putAll(JsonUtil.toMap(json));
		try{
			//add by lishuo 01-07-2016 Start  添加指定区域合同审核负责人
			service.insertArea(param);
			JSONObject object =JSONObject.fromObject(service.selectArea(param));
			Map<String, Object> map =service.SearchAreaManInfo(param);
			int num =0;
			//查询改用户的ORG_ID
			/*if(map.get("ORG_ID") !=null && !"".equals(map.get("ORG_ID"))){
				Map<String,Object> m =service.queryOrg_ID(map);
				map.put("ORG_ID", "".equals(m.get("ID")) ? false : m.get("ID"));
			}*/
			map.put("CREATE_PERSON", Security.getUser().getName());
			map.put("CARETE_TIME", new Date());
			map.put("AREA_ID", param.get("ID"));
			String USER_ID=(String) map.get("ID");
			if(map.size() >0 ){
				map.put("USER_ID", "".equals(USER_ID) ? false : USER_ID);
			}
			 if(map.get("NAME") != null && !"".equals(map.get("NAME"))){
				 	mm=service.SerchIDByName(map);
				}
			 	if(mm==null){//第一次添加
			 		num=service.insertAreaMan(map);
			 	}else if(mm.get("COUNT") !=null){
					COUNT= mm.get("COUNT").toString();
				}
				if("1".equals(COUNT)){
					String msg="区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！";
					return new ReplyAjax(msg);
				}else if(num == 0){
					service.insertAreaMan(map);
			 }
			//add by lishuo 01-07-2016 End
			return new ReplyAjax(object).addOp(new OpLog("区域管理","添加(区域)",param.toString()));
		}catch(Exception e){
			e.printStackTrace();
			return new ReplyAjax(false).addOp(new OpLog("区域管理","添加(区域)",param.toString()));
		}
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public  Reply doUpdateArea(){
		Map<String,Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.remove("param");
		param.putAll(JsonUtil.toMap(json));
		try{
			//add by lishuo 01-07-2016 Start  修改指定区域合同审核负责人
			service.updateArea(param);
			JSONObject object =JSONObject.fromObject(service.selectArea(param));
			Map<String,Object> mm=null;
			Map<String,Object> result=null;
			Object COUNT= null;
			Map<String, Object> map =service.SearchAreaManInfo(param);
			/*//查询改用户的ORG_ID
			if(map.get("ORG_ID") !=null && !"".equals(map.get("ORG_ID"))){
				Map<String,Object> m =service.queryOrg_ID(map);
				map.put("ORG_ID", "".equals(m.get("ID")) ? false : m.get("ID"));
			}*/
			System.out.println("SELECT_MAN====="+param.get("SELECT_MAN"));
			map.put("AREA_ID", "".equals(param.get("ID")) ? false :param.get("ID"));
			map.put("MODIFY_PERSON", Security.getUser().getName());
			map.put("MODIFY_TIME", new Date());
			map.put("SELECT_MAN", param.get("SELECT_MAN")==null ? map.get("NAME") : param.get("SELECT_MAN"));
			String USER_ID=(String) map.get("ID");
			map.put("USER_ID", "".equals(USER_ID) ? false : USER_ID);
			 if(map.get("NAME") != null && !"".equals(map.get("NAME"))){
				map.put("FLAG", "2");//输入的名字
			}else if(map.get("SELECT_MAN") != null && !"".equals(map.get("SELECT_MAN"))){
				map.put("FLAG", "1");//选匹配的名字
			}
			 if(map.get("NAME") != null && "2".equals(map.get("FLAG"))){
				mm=service.SerchIDByName(map);
			}else if(map.get("SELECT_MAN") != null && "1".equals(map.get("FLAG"))){
				mm=service.SerchID(map);
			} 
			if(mm !=null){
				COUNT= mm.get("COUNT").toString();
			}
			if(mm == null){
				map.remove("ID");
				result=service.queryIDByArea_ID(map);
				//String ID = Dao.getSequence("SEQ_T_SYS_ORG_ASSIGN_MAN");
				map.put("CREATE_PERSON", Security.getUser().getName());
				map.put("CARETE_TIME", new Date());
				if(result.get("ID") !=null){
					map.put("ID",result.get("ID"));	
				}
				service.updateAreaMan(map);
			}else if("1".equals(COUNT)){
				String msg="区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！";
				return new ReplyAjax(msg);
			 }else{
				map.remove("ID");
				map.put("ID",mm.get("ID"));	
				service.updateAreaMan(map);
			}
			//add by lishuo 01-07-2016 End
			return new ReplyAjax(object).addOp(new OpLog("区域管理","修改(区域)",param.toString()));
		}catch(Exception e){
			e.printStackTrace();
			return new ReplyAjax(false).addOp(new OpLog("区域管理","修改(区域)",param.toString()));
		}
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getShengFenData(){
		Map<String,Object> param = _getParameters();
		JSONArray shengFenData = JSONArray.fromObject(service.getShengFenData(param));
		return new ReplyAjax(shengFenData);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectShengShi(){
		Map<String,Object> param = _getParameters();
		JSONArray selectShengShi = JSONArray.fromObject(service.selectShengShi(param));
		return new ReplyAjax(selectShengShi);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","区域管理设置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getQSFData(){
		Map<String,Object> param = _getParameters();
		JSONArray qSFData = JSONArray.fromObject(service.getQSFData(param));
		return new ReplyAjax(qSFData);
	}
	
	@aPermission(name = {"参数配置","区域管理设置"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply saveConfig() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.saveConfig(param));
	}
	
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "23", email = "shuolijiezhong@china.com", name = "李硕")
	public Reply SerchUser() {
		Map<String, Object> param = _getParameters();
		JSONArray data = JSONArray.fromObject(service.SerchUser(param));
		return new ReplyAjax(data);
	}
	
}
