package com.pqsoft.bailoutCondition.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bailoutCondition.service.BailoutConditionService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class BailoutConditionAction extends Action {

	private final String pagePath="bailoutcondition/";
	private final BailoutConditionService service = new BailoutConditionService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件方式管理","列表显示"})
	public Reply toMgBailout(){//进入管理页面
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//获取用户编
		VelocityContext context = new VelocityContext();
		context.put("parame",map);
		context.put("fhfalist",service.querFhfa(map));
		context.put("conditionlist", service.querCondition(map));
		return new ReplyHtml(VM.html(pagePath+"toMgBailoutCondition.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件方式管理","查询"})
	public Reply toMgBailoutData(){//返回查询数据
		//获取页面参数
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgBailout(map));//融资条件方式数据查询
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件方式管理","添加选择方式"})
	public Reply getBailoutWay() {
		Map<String, Object> map = _getParameters();
		StringBuffer sb = new StringBuffer();
		List bailoutWaylist = (List) service.querBalloutWayService(map);// 查询相关的融资方式
		sb.append("[");
		for (int i = 0; i < bailoutWaylist.size(); i++) {
			Map<String, Object> bwmap = (Map<String, Object>) bailoutWaylist
					.get(i);
			if (i > 0) {
				sb.append(",{\"namese\":\"").append(bwmap.get("FLAG")).append(
						"\",\"value\":\"").append(bwmap.get("ID"))
						.append("\"}");
			} else {
				sb.append("{\"namese\":\"").append(bwmap.get("FLAG")).append(
						"\",\"value\":\"").append(bwmap.get("ID"))
						.append("\"}");
			}
		}
		sb.append("]");
		return new ReplyAjax(true, sb.toString(), null);

	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资条件方式管理","添加"})
	public Reply toAddBailout(){
		//获取页面参数
		Map<String,Object> paramMap = _getParameters();
		List<Map<String,Object>> conditionlist = (List<Map<String,Object>>) service.getAllConditionService();// 查询所有条件信息
		
		String conditionone = null;
		if (paramMap.containsKey("CONDITIONONE")){
			conditionone = SkyEye.getRequest().getParameter("CONDITIONONE");
		}else if(paramMap.containsKey("pTRBCID")){
			String id = paramMap.get("pTRBCID") + "";
			Map<String, Object> m = (Map<String, Object>) service.querBailoutCondition(id);
			conditionone = m.get("CONDITIONONE").toString();
		}
		
		JSONArray json = null;
		
		if (StringUtils.isNotEmpty(conditionone) && !"null".equals(conditionone)){
			json = JSONArray.fromObject(conditionone);
		}
		
		List<Map<String, Object>> conlist = new ArrayList<Map<String, Object>>();
		
		if (json != null && !"null".equals(json))
			conlist = json;
		
		StringBuffer sb = new StringBuffer();
		List cnamelist = new ArrayList();
		
		//添加table表格
		sb.append("<table cellspacing='0' cellpadding='0' border='0' class='table_01' style='float:left;'>");
		
		for (int i = 0; i < conditionlist.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) conditionlist
					.get(i);
			int p = 0;
			if (cnamelist.size() > 0) {
				for (int j = 0; j < cnamelist.size(); j++) {
					if (cnamelist.get(j).equals(map.get("CONDITIONTYPENAME"))) {
						p++;
					}
					if (p == 0 && j == cnamelist.size() - 1) {
						cnamelist.add(map.get("CONDITIONTYPENAME"));
					}
				}
			} else {
				cnamelist.add(map.get("CONDITIONTYPENAME"));
			}
		}
		sb.append("<tbody id='TCONDITION'>");
		
		for (int z = 0; z < cnamelist.size(); z++) {
			sb.append("<tr>");
			
			sb.append("<td class='BCTD'>").append((String) cnamelist.get(z))
					.append("</td>");
			
			sb
					.append("	<td class='BCTD2'><table cellspacing='0' cellpadding='0' border='0' class='table_01' style='float:left;'>");
			
			for (int i = 0; i < conditionlist.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) conditionlist
						.get(i);
				if (cnamelist.get(z).equals(map.get("CONDITIONTYPENAME"))) {
					sb
							.append("<tr class='conditonTr'><td style='width:5%;'><input type='checkbox' value='"
									+ map.get("TRCID") + "' ");
					if (conlist != null && !conlist.isEmpty()
							&& conlist.size() > 0) {
						for (int j = 0; j < conlist.size(); j++) {
							Map<String, Object> mm = conlist.get(j);
							String contionId = mm.get("contionId") + "";
							if (contionId.equals(map.get("TRCID") + "")) {
								sb.append(" checked='checked' ");
							}
						}
					}
					
					sb
							.append("name='CONDITIONONE' class='CONDITIONONE'/></td><td style='width:45%;'>");
					if (map.get("BIDBONDRENT") != null) {
						sb.append("<a title='").append(map.get("BIDBONDRENT"))
								.append("'>");
						sb.append(map.get("CNAME")).append("</a>");
					} else {
						sb.append(map.get("CNAME"));
					}
					boolean flag = false;
					if (conlist != null && !conlist.isEmpty()
							&& conlist.size() > 0) {
						for (int j = 0; j < conlist.size(); j++) {
							Map<String, Object> mm = conlist.get(j);
							String contionId = mm.get("contionId") + "";
							String FILE_TYPE = mm.get("FILE_TYPE") + "";
							String FILE_COUNT = mm.get("FILE_COUNT") + "";
							if (contionId.equals(map.get("TRCID") + "")) {
								flag = true;
								sb
										.append("</td><td style='width:30%;'><input type='radio' value='1' name='FILE_TYPE"
												+ map.get("TRCID")
												+ "' class='FILE_TYPE'");
								if ("1".equals(FILE_TYPE)) {
									sb.append("checked='checked'");
								}
								sb
										.append("/>原件 &nbsp;&nbsp;<input type='radio' value='2' name='FILE_TYPE"
												+ map.get("TRCID")
												+ "' class='FILE_TYPE'");
								if ("2".equals(FILE_TYPE)) {
									sb.append("checked='checked'");
								}
								sb
										.append("/>复印件</td><td style='width:20%;'>共<input type='text' name='FILE_COUNT"
												+ map.get("TRCID")
												+ "'style='width:20px;' class='FILE_COUNT'");
								if (StringUtils.isNotEmpty(FILE_COUNT)
										&& !"null".equals(FILE_COUNT)) {
									sb.append("value='" + FILE_COUNT
											+ "'/>份</td></tr>");
								} else {
									sb.append("value='1'/>份</td></tr>");
								}

							}
						}
						if (!flag) {
							sb
									.append("</td><td style='width:30%;'><input type='radio' value='1' name='FILE_TYPE"
											+ map.get("TRCID")
											+ "' class='FILE_TYPE'/>原件 &nbsp;&nbsp;<input type='radio' value='2' name='FILE_TYPE"
											+ map.get("TRCID")
											+ "' class='FILE_TYPE' checked/>复印件</td><td style='width:20%;'>共<input type='text' name='FILE_COUNT"
											+ map.get("TRCID")
											+ "'style='width:20px;' class='FILE_COUNT' value='1'/>份</td></tr>");
						}
					} else {
						sb
								.append("</td><td style='width:30%;'><input type='radio' value='1' name='FILE_TYPE"
										+ map.get("TRCID")
										+ "' class='FILE_TYPE'/>原件 &nbsp;&nbsp;<input type='radio' value='2' name='FILE_TYPE"
										+ map.get("TRCID")
										+ "' class='FILE_TYPE' checked/>复印件</td><td style='width:20%;'>共<input type='text' name='FILE_COUNT"
										+ map.get("TRCID")
										+ "'style='width:20px;' class='FILE_COUNT' value='1'/>份</td></tr>");
					}
				}

			}
			
			sb.append("</table></td>");
			sb.append("</tr>");
		}
		
		sb.append(" </TBODY></table>");
		
		return new ReplyAjax(true, sb.toString(),null);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资条件方式管理","添加"})
	public Reply doAjaxInsetBailoutCondition(){
		Map<String,Object> map = _getParameters();//获取页面参数
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doInsetBailoutCondition(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("融资条件方式管理","添加融条件方式", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件方式管理","修改"})
	public Reply doReviseBailoutCondition(){
		Map<String,Object> map = _getParameters();//获取页面参数
		map.put("USERCODE", Security.getUser().getCode());
		int i = service.doReviseBailoutCondition(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("融资条件方式管理","修改融条件方式", map.get("USERCODE").toString()));
	}
}
