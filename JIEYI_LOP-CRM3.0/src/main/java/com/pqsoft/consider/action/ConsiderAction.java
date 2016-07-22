package com.pqsoft.consider.action;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.velocity.VelocityContext;

import com.pqsoft.analysisBySynthesis.service.IndustryService;
import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.consider.service.ConsiderService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.StringUtils;

public class ConsiderAction extends Action{

	private final String pagePath="consider/";
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * 租赁项目立项审议意见表
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply toConsider(){
		Map<String, Object> param = _getParameters();
		//PROJECT_ID='557506'
		//param.put("PROJECT_ID", "557644");
		String type = param.get("TYPE").toString();
		String project_id = param.get("PROJECT_ID").toString();
		ConsiderService service = new ConsiderService();
		Map<String, Object> map = service.selectInfoById(project_id);
		Map<String, Object> map1 = service.valueById(project_id);
		//查询意见表的信息
		Map<String, Object> feedMap = service.selectFeedBackById(project_id);
		//其他费用
		Map<String, Object> otherFeeMap = service.selectOtherFee(project_id);
		param.putAll(otherFeeMap);
		param.putAll(map);
		if(feedMap!=null){
			param.putAll(feedMap);
		}
		if(map1!=null){
			param.putAll(map1);
		}else{
			param.put("VALUE_STR", "");//客户保证金比例为空
		}
		System.out.println("---consider---65---param:"+param);
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		//type=0 查看页面,type=1 修改页面
		if("1".equals(type)){
			return new ReplyHtml(VM.html(pagePath+"feedbackForm.vm", context));
		}
		return new ReplyHtml(VM.html(pagePath+"feedbackFormToView.vm", context));
	}
	/*
	 * 修改意见表信息
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply feedBackSave(){
		Map<String, Object> param = _getParameters();
		ConsiderService service = new ConsiderService();
		int i = service.feedBackSave(param);
		boolean flag = false;
		if(i>0){
			flag=true;
		}
		String msg ="修改成功!";
		return  new ReplyAjax(flag, msg);
		
	}
	/*
	 * 删除意见表信息
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply deleteFeedBack(){
		Map<String, Object> param = _getParameters();
		ConsiderService service = new ConsiderService();
		int i = service.deleteFeedBack(param);
		boolean flag = false;
		if(i>0){
			flag=true;
		}
		String msg ="删除成功!";
		return  new ReplyAjax(flag, msg);
		
	}
	/*
	 * 行业分类 ,根据查询的行业id,取相应的行业名称
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public String getIndustry(String str){
		// 行业分类
		Map<String, Object> indMap = new HashMap<String, Object>();
		List<Object> INDUSTRY_FICATION_List = (List) new IndustryService().toFindIndustryMemcache();
		//行业名称
		String indName = new String();
		for(int i=0; i<INDUSTRY_FICATION_List.size();i++){
			Map<String, Object> mm = (Map<String, Object>) INDUSTRY_FICATION_List.get(i);
			indMap.putAll(mm);
			String idStr = indMap.get("ID").toString();
			if(str.equals(idStr)){
				indName = indMap.get("INDUSTRY_NAME").toString();
				break;
			}else {
				indName="";
			}
		}
		return indName;
		
	}
	/*
	 * 租赁项目评审申请书
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply toApplication(){
		Map<String, Object> param = _getParameters();
		String type = param.get("TYPE").toString();
		String project_id = param.get("PROJECT_ID").toString();
		//调用service的方法
		ConsiderService service = new ConsiderService();
		Map<String, Object> map = service.selectAppOtherById(project_id);
		//获取行业名称
		if(map.get("INDUSTRY_FICATION") !=null){
			String indString = map.get("INDUSTRY_FICATION").toString();
			String industry_fication =this.getIndustry(indString);
			map.put("INDUSTRY_FICATION", industry_fication);
		}
		//数据存入param,方便页面上根据名称取数据
		param.putAll(map);
		Map<String, Object> map1 = service.valueById(project_id);
		if(map1!=null){
			param.putAll(map1);
		}else{
			param.put("VALUE_STR", "");//客户保证金比例为空
		}
		//其他费用
		Map<String, Object> otherFeeMap = service.selectOtherFee(project_id);
		param.putAll(otherFeeMap);
		//查询申请书的信息
		Map<String, Object> appMap = service.selectAppById(project_id);
		//数据库中的WORD_CONTENTS字段类型为clob,需要转换
		String str = null ;
		//对appMap为空判断,并对其中WORD_CONTENTS为空判断
		if(appMap!=null && !appMap.isEmpty()){
			if(appMap.get("WORD_CONTENTS") != null && !"".equals(appMap.get("WORD_CONTENTS").toString())){
				Clob clob = (Clob) appMap.get("WORD_CONTENTS");
				try {
					str=clob.getSubString(1, (int) clob.length());
					appMap.put("WORD_CONTENTS", str);
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new ActionException("租赁项目评审申请书未录入全，请联系管理员");
				}
			}
			param.putAll(appMap);
		}
//		Map<String, Object> fkje=new HashMap<String, Object>();
//		List<Map<String, Object>> fkje1=new ArrayList<Map<String, Object>>();
//		JSONArray WORD_CONTENTS=JSONArray.fromObject(str);
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		System.out.println("--consider--185----param:"+param);
		//type=0 查看页面,type=1 修改页面
		if("1".equals(type)){
			return new ReplyHtml(VM.html(pagePath+"application.vm", context));
		}
		return new ReplyHtml(VM.html(pagePath+"applicationToView.vm", context));
	}
	
	
	/*
	 * 修改申请书信息
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply appSave(){
		Map<String, Object> param = _getParameters();
		ConsiderService service = new ConsiderService();
		int i = service.appSave(param);
		boolean flag = false;
		if(i>0){
			flag=true;
		}
		String msg ="修改成功!";
		return  new ReplyAjax(flag, msg);
		
	}
	/*
	 * 删除申请书信息
	 */
	@aDev(code = "XQ0013", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply deleteApp(){
		Map<String, Object> param = _getParameters();
		ConsiderService service = new ConsiderService();
		int i = service.deleteApp(param);
		boolean flag = false;
		if(i>0){
			flag=true;
		}
		String msg ="删除成功!";
		return  new ReplyAjax(flag, msg);
		
	}
	
	
	/*
	 * 租赁投放审查审批表
	 */
	@aDev(code = "ZQT024", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply toPlaceReview(){
		Map<String, Object> param = _getParameters();
		ConsiderService service = new ConsiderService();
		Map<String, Object> custInfo = service.selPlaceById(param);
//		System.out.println("--247--custInfo:"+custInfo);
		CustomersService cusService = new CustomersService();
		VelocityContext context = new VelocityContext();
		context.put("custInfo", custInfo);
		context.put("type", cusService.findCustDataType());// 客户关联信息
		//取第一个担保人信息
		projectService proService = new projectService();
		Map<String,Object> map1 = proService.selectGuarantor(param);
		Map<String,Object> guarMap = new HashMap<String, Object>();
		if(map1!=null && !map1.isEmpty()){
			guarMap = proService.selectGuarantorAll(map1);
		}
		context.put("guarantor", guarMap);
		String project_id = param.get("PROJECT_ID").toString();
		Map<String,Object> otherInfo = service.selectAppOtherById(project_id);
		context.put("otherInfo", otherInfo);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		AreaService areaS = new AreaService();
		context.put("getProvince", areaS.getAllProvince());// 省
		Map<String,Object> addressMap = new HashMap<String,Object>();
		String addStr = "REGISTE_";
		if (custInfo.get("TYPE") != null) {
			Map<String,Object> refer = new HashMap<String,Object>() ;
			if (custInfo.get("TYPE").toString().equals("NP")) {// 进入自然人页面
				addStr ="HOUSE_";
			}
//			else if(custInfo.get("TYPE").toString().equals("LP")){// 进入法人页面
//				addStr ="REGISTE_";
//			}
			if(StringUtils.isNotBlank(custInfo.get(addStr+"ADDRESS_PROVINCE"))){
				refer.put("PARENT_ID", custInfo.get(addStr+"ADDRESS_PROVINCE")) ;
				//地址市
				List<Object> registeAddressCity = areaS.getSubset(refer) ;
				context.put("addressCity", registeAddressCity);
				//地址(区/县)
				refer.clear();
				if(StringUtils.isNotBlank(custInfo.get(addStr+"ADDRESS_CITY"))){
					refer.put("PARENT_ID", custInfo.get(addStr+"ADDRESS_CITY")) ;
					List<Object> registeAddressCounty = areaS.getSubset(refer) ;
					context.put("addressCounty", registeAddressCounty);
					refer.clear();
				}	
			}
			addressMap.put("ADDRESS_PROVINCE", custInfo.get(addStr+"ADDRESS_PROVINCE"));
			addressMap.put("ADDRESS_CITY", custInfo.get(addStr+"ADDRESS_CITY"));
			addressMap.put("ADDRESS_COUNTY", custInfo.get(addStr+"ADDRESS_COUNTY"));
		}
		context.put("address", addressMap);
		//取租赁投放审查审批表 内容
		Map<String,Object> approval = service.selectPlaceReview(param);
		context.put("approval", approval);
		System.out.println("--313--approval:"+approval);
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath+"placeReview.vm", context));
	}
	/*
	 * 租赁投放审查审批表 保存
	 */
	@aDev(code = "ZQT024", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply placeReviewSave(){
		Map<String, Object> param =_getParameters();
		ConsiderService service =new ConsiderService();
		int i = service.updatePlaceReview(param);
		boolean flag= false;
		if(i>0){
			flag=true;
		}
		String msg="保存成功!";
		return new ReplyAjax(flag, msg);
	}
	/*
	 * 租赁投放审查审批表 增加
	 */
	@aDev(code = "ZQT024", email = "1094558481@qq.com", name = "zengqt")
	@aAuth(type = aAuthType.ALL)
	public Reply placeReviewAdd(){
		Map<String, Object> param =_getParameters();
		ConsiderService service =new ConsiderService();
		int i = service.addPlaceReview(param);
		boolean flag= false;
		if(i>0){
			flag=true;
		}
		String msg="保存成功!";
		return new ReplyAjax(flag, msg);
	}
}

