package com.pqsoft.letterOpinion.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.letterOpinion.service.LetterOpinionService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.ReplyExcel;

/**
 * 信审意见
 * @author zhengshangcheng
 *
 */
public class LetterOpinionAction extends Action {
	
	private LetterOpinionService service = new LetterOpinionService();
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "信审意见", "进入数据展示清单" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply toLetterOpinionPage(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("bpm/letterOpinion.vm", context));
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "业务管理", "信审意见", "数据展示清单" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply getLetterOpinionPage(){
		Map<String, Object> param = _getParameters();
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			param.put("ENDTRIAL_PERSON", Security.getUser().getName());
		}else if(Security.getUser().getOrg().getRole().equals("信审专员")){
			param.put("FIRSTTRIAL_PERSON", Security.getUser().getName());
		}
		return new ReplyAjaxPage(service.getLetterOpinionPage(param));
	}

	/**
	 * 信审意见
	 * modify by lishuo 12.8.2015
	 * add  @aPermission @aAuth
	 * @return
	 */
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"参数配置","信审意见"})
	public Reply conditionalPass(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("PROJECT_ID", param.get("PROJECT_ID"));
		context.put("spjlList", new DataDictionaryMemcached().get("审批结论"));
		context.put("jjdlList", new DataDictionaryMemcached().get("拒绝大类"));
		List<Map<String, Object>> listLetter = service.getLetterOpinionList(param);
		if(listLetter.size()>0){
			context.put("mapLetter", listLetter.get(0));
		}
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			context.put("type", "zsyj");
		}else{
			context.put("type", "csyj");
		}
		return new ReplyHtml(VM.html("bpm/conditionalPass.vm", context));
	}
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.ALL)
	@aPermission(name = {"参数配置","信审意见修改"})
	public Reply conditionalPassUpdate(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("PROJECT_ID", param.get("PROJECT_ID"));
		context.put("spjlList", new DataDictionaryMemcached().get("审批结论"));
		context.put("jjdlList", new DataDictionaryMemcached().get("拒绝大类"));
		List<Map<String, Object>> listLetter = service.getLetterOpinionList(param);
		if(listLetter.size()>0){
			context.put("mapLetter", listLetter.get(0));
		}
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			context.put("type", "zsyj");
		}else{
			context.put("type", "csyj");
		}
		return new ReplyHtml(VM.html("bpm/conditionalPassUpdate.vm", context));
	}
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.ALL)
	@aPermission(name = {"参数配置","信审意见角色权限"})
	public Reply conditionalRolePermission(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("PROJECT_ID", param.get("PROJECT_ID"));
		context.put("spjlList", new DataDictionaryMemcached().get("审批结论"));
		context.put("jjdlList", new DataDictionaryMemcached().get("拒绝大类"));
		List<Map<String, Object>> listLetter = service.getLetterOpinionList(param);
		if(listLetter.size()>0){
			context.put("mapLetter", listLetter.get(0));
		}
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			context.put("type", "zsyj");
		}else{
			context.put("type", "csyj");
		}
		return new ReplyHtml(VM.html("bpm/conditionalRolePermission.vm", context));
	}
	@Override
	public Reply execute() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax getSPFK(){
		List<Map<String, Object>> spfkList = new DataDictionaryMemcached().get("有条件通过");
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(spfkList);
		return new ReplyAjax(jsonArray);
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax getJJDL(){
		List<Map<String, Object>> spfkList = new DataDictionaryMemcached().get("拒绝大类");
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(spfkList);
		return new ReplyAjax(jsonArray);
	}
	
	@SuppressWarnings("unchecked")
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax getJJFL(){
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> spfkList = new DataDictionaryMemcached().get(param.get("strJJDL").toString());
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(spfkList);
		return new ReplyAjax(jsonArray);
	}
	
	/**
	 * 新增 or 修改
	 * @return
	 */
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax saveLetterOpinion(){
		Map<String, Object> param = _getParameters();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = service.getLetterOpinionList(param);
		if(param.get("SUBMCS") != null && param.get("SUBMCS").toString().equals("1")){
			param.put("FIRSTTRIAL_STATUS", "1");
		}
		if(param.get("SUBMZS") != null && param.get("SUBMZS").toString().equals("1")){
			param.put("ENDTRIAL_STATUS", "1");
		}
		int res = 0;
		if(list.size() > 0){
			if(param.get("TYPE") != null && param.get("TYPE").toString().equals("1")){
				param.put("FIRSTTRIAL_UPDDATA", df.format(new Date()));
			}
			if(param.get("TYPE") != null && param.get("TYPE").toString().equals("2")){
				param.put("ENDTRIAL_PERSON", Security.getUser().getName());
				if(list.get(0).get("ENDTRIAL_CREATEDATA") != null 
						&& !list.get(0).get("ENDTRIAL_CREATEDATA").toString().equals("")){
					param.put("ENDTRIAL_UPDDATA", df.format(new Date()));
				}else{
					param.put("ENDTRIAL_CREATEDATA", df.format(new Date()));
				}
			}
			res = service.updateLetterOpinion(param);
		}else{
			if(param.get("TYPE") != null && param.get("TYPE").toString().equals("1")){
				param.put("FIRSTTRIAL_PERSON", Security.getUser().getName());
				param.put("FIRSTTRIAL_CREATEDATA", df.format(new Date()));
				param.put("ENDTRIAL_PERSON", "系统管理员");
			}
			if(param.get("TYPE") != null && param.get("TYPE").toString().equals("2")){
				param.put("ENDTRIAL_CREATEDATA", df.format(new Date()));
				param.put("ENDTRIAL_PERSON", Security.getUser().getName());
			}
			res = service.addLetterOpinion(param);
		}
		boolean flag = false;
		if(res > 0){
			flag = true;
		}
		return new ReplyAjax(flag);
	}
	
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	@aAuth(type = aAuthType.LOGIN)
	public ReplyAjax getLetterOpinion(){
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> listLetter = service.getLetterOpinionList(param);
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(listLetter);
		return new ReplyAjax(jsonArray);
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply expExcel(){
		List<String> list = new ArrayList<String>();
		Map<String, Object> param = _getParameters();
		param.put("USER_ID", Security.getUser().getId());
		param.put("ORG_ID", Security.getUser().getOrg().getId());
		param.put("USERCODE", Security.getUser().getCode());
		list.add("信审意见");
		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			title.put("ENDTRIAL_PERSON", "信审人员");
		}else if(Security.getUser().getOrg().getRole().equals("信审专员")){
			title.put("FIRSTTRIAL_PERSON", "信审人员");
		}else{
			title.put("FIRSTTRIAL_PERSON", "信审人员");
		}
		title.put("START_DATE", "进件日期");
		title.put(" ", "终审日期");
		title.put("PRO_CODE", "项目号");
		title.put("STATUS_NEW", "状态");
		title.put("NAME", "客户");
		title.put("ID_CARD_NO", "身份证");
		title.put("SCHEME_NAME", "产品");
		title.put("LEASE_TERM", "期数");
		title.put("FINANCE_TOPRIC", "融资额");
		title.put("SPEC_NAME", "车型");
		title.put("FGS", "分公司");
		title.put("SHOP_NAME", "门店");
		titles.add(title);
		
		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
		if(Security.getUser().getOrg().getRole().equals("信审经理")){
			param.put("ENDTRIAL_PERSON", Security.getUser().getName());
		}else if(Security.getUser().getOrg().getRole().equals("信审专员")){
			param.put("FIRSTTRIAL_PERSON", Security.getUser().getName());
		}
		List<Map<String, Object>> l = Dao.selectList("letterOpinion.expList", param);
		
		data.add(l);
		Excel excel = new Excel("信审意见", list, titles, data);
		return new ReplyExcel(excel, "信审意见.xls");
	}
	
}
