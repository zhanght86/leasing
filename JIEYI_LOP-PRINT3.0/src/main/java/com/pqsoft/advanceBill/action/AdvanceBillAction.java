package com.pqsoft.advanceBill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.advanceBill.service.AdvanceBillService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.UtilChart;

public class AdvanceBillAction extends Action {
    private String path = "advanceBill/"; 
    private AdvanceBillService service = new AdvanceBillService();
    
    private String[] titlesName=null;
    private String[] sqlsName = null;

    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","首期款提前开票申请","列表"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    @Override
    public Reply execute() {
	VelocityContext context = new VelocityContext();
	Map<String,Object> param = _getParameters();
	context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
	context.put("param", param);
	
	//先加入固定的数据
	int GDNUM=13;
	
	//查询剩下的明细项
	List list=Dao.selectList("advanceBill.tqkjColumnsAllTitel");
	
	titlesName=new String[GDNUM+list.size()];
	sqlsName=new String[GDNUM+list.size()];
	
	titlesName[0]="经销商";
	sqlsName[0]="SUP_SHORTNAME";
	
	titlesName[1]="客户名称";
	sqlsName[1]="CLIENT_NAME";
	
	titlesName[2]="融资租赁合同号";
	sqlsName[2]="LEASE_CODE";
	
	titlesName[3]="支付表号";
	sqlsName[3]="PAYLIST_CODE";
	
	titlesName[4]="业务类型";
	sqlsName[4]="PROJECT_MODEL";
	
	titlesName[5]="起租日期";
	sqlsName[5]="CONFIRM_DATE_START";
	
	titlesName[6]="应收日期";
	sqlsName[6]="BEGINNING_RECEIVE_DATA";
	
	titlesName[7]="是否上牌";
	sqlsName[7]="ON_CARD";
	
	titlesName[8]="税率";
	sqlsName[8]="TAX_RATE";
	
	titlesName[9]="款项合计";
	sqlsName[9]="TOTAL_MONEY";
	
	titlesName[10]="首期租金";
	sqlsName[10]="ACCRUE_MONEY";
	
	titlesName[11]="第一期租金";
	sqlsName[11]="FIRSTRENT_MONEY";
	
	titlesName[12]="租前利息";
	sqlsName[12]="STARTLX_MONEY";
	
	
	int DTNUM=0;
	for(int i=0;i<list.size();i++){
		Map map=(Map)list.get(i);
		if(map !=null && map.get("FUND_TYPE") !=null && !map.get("FUND_TYPE").equals("") && map.get("SHORTNAME") !=null && !map.get("SHORTNAME").equals("")){
			titlesName[GDNUM+DTNUM]=map.get("FUND_TYPE").toString();
			sqlsName[GDNUM+DTNUM]=map.get("SHORTNAME").toString();
			DTNUM++;
		}
		
	}
	
	
	UtilChart utilChart=new UtilChart();
	context.put("columnInit", utilChart.parseColumnInit("提前开票页面字段", titlesName, sqlsName));
	
	return new ReplyHtml(VM.html(path+"advanceFirstBillPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","首期款提前开票申请","查询"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getAdvanceBillFirstPageDate(){
	Map<String,Object> param = _getParameters();
	Page pageData = service.getAdvanceBillFirstPage(param);
	return new ReplyAjaxPage(pageData);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","租金提前开票申请","列表"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getAdvanceBillRentPage(){
	VelocityContext context = new VelocityContext();
	Map<String,Object> param = _getParameters();
	context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
	context.put("param", param);
	return new ReplyHtml(VM.html(path+"advanceRentBillPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","租金提前开票申请","查询"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getRentBillPageData(){
	Map<String,Object> param = _getParameters();
	Page pageData = service.getAdvanceBillRentPage(param);
	return new ReplyAjaxPage(pageData);
    }
    
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","提前开票申请","申请开票"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply applyAdvanceBillFirst(){
	Map<String,Object> param = _getParameters();
	JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
	int result = 0 ;
	List<Object> applyMess = new ArrayList<Object>();
	if(jsonArray.size()>0){
	    applyMess = jsonArray ;
	}else{
	    param.put("VAT_FLAG", "YES");
	    param.put("INVOICE_TYPE", "增值税发票");
	    applyMess = service.getBillFirstInviceMess(param);
	}
	for (Object object : applyMess) {
	    Map<String,Object> newParam = (Map<String, Object>)object;
	    newParam.put("CREATOR", Security.getUser().getName());
	    newParam.put("FUND_TYPE", "首期付款");
	    newParam.put("FUND_NAME", "首期付款");
	    //插入发票详细信息
	    List<String> s = new ArrayList<String>();
	    
		s.add("首期租金");
		if(newParam.containsKey("PAY_WAY") && newParam.get("PAY_WAY") !=null && ("2".equals(newParam.get("PAY_WAY").toString()) ||
				"4".equals(newParam.get("PAY_WAY").toString()) || "6".equals(newParam.get("PAY_WAY").toString()))){
			s.add("本金");
			s.add("利息");
		}
		s.add("第一期租金");
		s.add("租前利息");
		
		//查询费用名称
//		s.add("手续费");
//		s.add("留购价款");
//		s.add("管理费");
//		s.add("保险费");
//		s.add("GPS费用");
		
		//查询费用名称
		List listStr=Dao.selectList("advanceBill.tqkjColumnsAllTitel");
		for(int iii=0;iii<listStr.size();iii++){
			Map map=(Map)listStr.get(iii);
			if(map !=null && map.get("FUND_TYPE") !=null && !map.get("FUND_TYPE").equals("")){
				s.add(map.get("FUND_TYPE")+"");
			}
		}
		
	    newParam.put("FIRST_TYPE", "0");
	    newParam.put("BEGINNING_NAMES", s);
	    newParam.put("INVOICE_TYPE", "增值税发票");
	    newParam.put("IF_INVOICE", "yes");
	    newParam.put("IS_TQKJ", "2");//提前开票
	    result += service.advanceBillApply(newParam);
	}
	if(result >0 ){
	    return new ReplyAjax(true, "");
	}else{
	    return new ReplyAjax(true,"");
	}
    } 
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","提前开票核销","列表显示"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply showAdvanceHexiaoPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		List<Object> invoiceTypes = (List<Object>)new DataDictionaryMemcached().get("发票_类型名称");
    	context.put("invoiceTypes", invoiceTypes);
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param",param);
		return new ReplyHtml(VM.html(path+"advanceHeXiaoBillPage.vm", context));
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
    @aPermission(name ={"票据管理","提前开票","提前开票核销","列表显示"})
    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
    public Reply getHeXiaoPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getHeXiaoPage(param);
		return new ReplyAjaxPage(pageData);
    }
    
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","提前开票","租金发票申请","申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyAdvanceRentMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService vatService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		int result = 0 ;
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size()>0){
			applyMess = jsonArray ;
		}else{
			param.put("VAT_FLAG", "YES");
			param.put("INVOICE_TYPE", "增值税发票");
			applyMess = service.getAllRentBillMess(param);
		}
		//插入发票详细信息
		List<String> s = new ArrayList<String>();
		for (Object object : applyMess) {
			s.clear();
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "租金");
			if(newParam.containsKey("ITEM_FLAG") && newParam.get("ITEM_FLAG") !=null ){
				newParam.put("FUND_NAME", "租金");
				s.add("本金");
				s.add("利息");
			}
			
			s.add("手续费");
			s.add("管理费");
			s.add("利息增值税");
			
			String D_STATUS ="2";
			newParam.put("D_STATUS", D_STATUS);
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("ITEM_MODEL", newParam.containsKey("BEGINNING_NUM") && newParam.get("BEGINNING_NUM") !=null ?newParam.get("BEGINNING_NUM").toString()+"期":"");
			newParam.put("IF_INVOICE", "yes");
			 newParam.put("IS_TQKJ", "2");//提前开票
			result += service.advanceBillApply(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}

}
