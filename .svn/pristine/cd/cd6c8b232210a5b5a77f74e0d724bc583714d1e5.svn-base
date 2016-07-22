package com.pqsoft.leaseApplication.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pqsoft.Code.service.CodeService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class LeaseApplicationService {

	private String basePath = "leaseApplication.";
	
	public List<Map<String,Object>> queryEqCountByProjectID(Map<String,Object> param){
		return Dao.selectList(basePath+"queryEqCountByProjectID", param);
	}
	
	public List<Map<String,Object>> queryEqCountByProjectIDDXM(Map<String,Object> param){
		return Dao.selectList(basePath+"queryEqCountByProjectIDDXM", param);
	}
	
	public List<Map<String,Object>> queryEqListByProjectId(Map<String,Object> param){
		return Dao.selectList(basePath+"queryEqListByProjectId", param);
	}
	
	public List<Map<String,Object>> companyList(Map<String,Object> param){
		param.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		param.put("SUP_ID", param.get("SUPPLIERS_ID"));
		return Dao.selectList("Company.queryCompanyBySupId", param);
	}
	
	public List<Map<String,Object>> thingList(Map<String,Object> param){
		return Dao.selectList("project.queryProductByCompId", param);
	}
	
	public List<Map<String,Object>> productList(Map<String,Object> param){
		return Dao.selectList("project.queryCatenaByEqID", param);
	}
	
	public List<Map<String,Object>> specList(Map<String,Object> param){
		return Dao.selectList("project.querySpecByEqID", param);
	}
	public List<Map<String,Object>> queryEqListByProjectIdDXM(Map<String,Object> param){
		return Dao.selectList(basePath+"queryEqListByProjectIdDXM", param);
	}
	
	public Map<String,Object> queryProjectById(Map<String,Object> param){
		List<Map<String,Object>> list=Dao.selectList(basePath+"queryBaseInfoByProjectID", param);
		if(!list.isEmpty()&&list.size()>=1)
			return list.get(0);
		else
			return new HashMap<String,Object>();
	}
	
	public List<Map<String,Object>> getScheme(Map<String,Object> param){
		return Dao.selectList(basePath+"getScheme",param);
	}
	//公共方法，轻易不要修改
	public VelocityContext schemeUtil(VelocityContext context,Map<String,Object> map){
		//查询基本信息
		projectService projectSer=new projectService();
		context.put("baseMap", projectSer.queryInfoByEqBase(map));
		
		
		context.put("eqList", this.queryEqByChange(map));
		//查询方案和支付表
		context=this.queryScheme(context, map);
		
		context.put("toNum", new UtilFinance());
		context.put("param", map);
		return context;
	}
	
	//公共方法，轻易不要修改
	public VelocityContext rentPlanUtil(VelocityContext context,Map<String,Object> map){
		//查询基本信息
//		projectService projectSer=new projectService();
		Map<String,Object> baseMap=Dao.selectOne(basePath+"queryInfoByRentPlan", map);
		context.put("baseMap", baseMap);
		map.put("tags", "设备单位");
		List<Map<String,Object>> eqlist = Dao.selectList(basePath+"queryEqByProjectIDByPayID", map);
		context.put("eqList", eqlist);
		//查询方案和支付表
		context=this.queryRentPlanInfo(context, map);
		
		context.put("toNum", new UtilFinance());
		map.put("PLATFORM_TYPE", baseMap.get("PLATFORM_TYPE"));
		context.put("param", map);
		if(baseMap.get("PLATFORM_TYPE")!=null && "8".equals(baseMap.get("PLATFORM_TYPE").toString())){
			//联合租赁 需要展示出资情况，及是代收代付还是自付
			List<Map<String,Object>> LHSQFSLIST=DataDictionaryService.queryDataDictionaryByScheme("联合收取方式");
			context.put("LHSQFSLIST", LHSQFSLIST);

			//查询联合租赁信息
			context.put("FL_LIST", new projectService().getFLByProjectId(baseMap));
		}
		
		return context;
	}
	
	//公共方法，轻易不要修改
	public VelocityContext schemeUtilLH(VelocityContext context,Map<String,Object> map){
		//查询基本信息
		projectService projectSer=new projectService();
		Map<String,Object> baseMap=projectSer.queryInfoByEqBase(map);
		context.put("baseMap", baseMap);
		
		context.put("eqList", this.queryEqBySchemeChange(map));
		//查询方案和支付表
		
		context.put("toNum", new UtilFinance());
		context.put("param", map);
		return context;
	}
	
	
	
	public VelocityContext queryScheme(VelocityContext context,Map<String,Object> m){
		//先查出本次拆分的比例
		m.put("MONEYCF", Dao.selectDouble(basePath+"queryEqInId", m));
		m.put("MONEYTOTAL", Dao.selectDouble(basePath+"queryEqByScheme", m));
		BaseSchemeService bsService=new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		context.put("SCHEMEDETAIL", bsService.getSchemeClob(null,m.get("PROJECT_ID")+"",m.get("SCHEME_ROW_NUM")+""));
		Map<String,Object> mapbase=Dao.selectOne(basePath+"getSchemeBaseInfoByProjectIdINT", m);
		context.put("schemeBase", mapbase);

		if(mapbase!=null){
			mapbase.put("SCHEME_ID", mapbase.get("ID"));
			List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
			for (Map<String, Object> map2 : clobList) {
				mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
			}
			mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
			mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
			mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
			mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
			mapbase.put("pay_way", mapbase.get("PAY_WAY"));
			mapbase.put("date", m.get("START_DATE_CHANGE"));
			mapbase.put("date1", m.get("REPAYMENT_DATE_CHANGE"));
			mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
			mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
			mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
			mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
			
			PayTaskService payTast=new PayTaskService();
			JSONObject page=null;
			try {
				page = payTast.quoteCalculateTest(mapbase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.put("detailList",  page.get("ln"));
		}
		return context;
	}
	
	//联合租赁专用
	@SuppressWarnings("unchecked")
	public VelocityContext toSchemeInfoLH(VelocityContext context,Map<String,Object> m){
		//查询方案和支付表
		//先查出本次拆分的比例
		Map<String,Object> CFMap=Dao.selectOne(basePath+"queryEqPriceByProjectID",m);
		m.putAll(CFMap);
		
		BaseSchemeService bsService=new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		context.put("SCHEMEDETAIL", bsService.getSchemeClob(null,m.get("PROJECT_ID")+"",m.get("SCHEME_ROW_NUM")+""));
		Map<String,Object> mapbase=Dao.selectOne(basePath+"getSchemeBaseInfoByProjectIdINT", m);
		

		if(mapbase!=null){
			mapbase.put("SCHEME_ID", mapbase.get("ID"));
			List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
			for (Map<String, Object> map2 : clobList) {
				mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
			}
			mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
			mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
			mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
			mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
			mapbase.put("pay_way", mapbase.get("PAY_WAY"));
			mapbase.put("date", m.get("START_DATE_CHANGE"));
			mapbase.put("date1", m.get("REPAYMENT_DATE_CHANGE"));
			mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
			mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
			mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
			mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
			
			PayTaskService payTast=new PayTaskService();
			JSONObject page=null;
			try {
				page = payTast.quoteCalculateTest(mapbase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
			//查询租金合计
			double zjCount=0d;
			double lxCount=0d;
			double bjCount=0d;
			double sybjCount=0d;
			for(int ir=0;ir<irrList.size();ir++){
				Map<String,String> irMap=irrList.get(ir);
				if(irMap!=null && irMap.get("zj")!=null && !irMap.get("zj").equals("")){
					zjCount=zjCount+Double.parseDouble(irMap.get("zj").toString());
				}
				
				if(irMap!=null && irMap.get("lx")!=null && !irMap.get("lx").equals("")){
					lxCount=lxCount+Double.parseDouble(irMap.get("lx").toString());
				}
				
				if(irMap!=null && irMap.get("bj")!=null && !irMap.get("bj").equals("")){
					bjCount=bjCount+Double.parseDouble(irMap.get("bj").toString());
				}
				
				if(irMap!=null && irMap.get("sybj")!=null && !irMap.get("sybj").equals("")){
					sybjCount=sybjCount+Double.parseDouble(irMap.get("sybj").toString());
				}
			}
			mapbase.put("ZJ_COUNT", zjCount);
			mapbase.put("BJ_COUNT", bjCount);
			mapbase.put("LX_COUNT", lxCount);
			mapbase.put("SYBJ_COUNT", sybjCount);
			context.put("detailList", irrList);
		}
		context.put("schemeBase", mapbase);
		
		
		context.put("toNum", new UtilFinance());
		context.put("param", m);
		return context;
	}
	
	public VelocityContext queryRentPlanInfo(VelocityContext context,Map<String,Object> m){
		//先查出本次拆分的比例
//		BaseSchemeService bsService=new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		
		JSONArray SCHEME_CLOB=null;
		Map<String,Object> object= Dao.selectOne(basePath+"queryClobByID", m);
		if(StringUtils.isNotBlank(object)){
			Map<String,Object> mapInfo=new HashMap<String,Object>();
			mapInfo.putAll(object);
			Dao.getClobTypeInfo2(mapInfo, "SCHEME_CLOB");
			SCHEME_CLOB=JSONArray.fromObject(mapInfo.get("SCHEME_CLOB"));
		}
		try{
		context.put("BASE_SCHEME", this.getBaseSchemeBySchemeCode(m.get("PAY_ID")+""));
		}catch(Exception e){
			
		}
		context.put("SCHEMEDETAIL", SCHEME_CLOB);
		Map<String,Object> mapbase=Dao.selectOne(basePath+"queryHeadInfoByPayId", m);
		context.put("schemeBase", mapbase);
		
		PayTaskService payTast=new PayTaskService();
		m.put("ID", m.get("PAY_ID"));
		context.put("detailList", payTast.doShowRentPlanDetail(m));
		
		//查询其他费用
		context.put("qtFy", Dao.selectList(basePath+"queryQT", m));
		//查询首期款
		context.put("qtSQ", Dao.selectList(basePath+"querySQ", m));
		return context;
	}
	
	// 查询该项目所选择的设备
	public List<Map<String,Object>> queryEqByChange(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList(basePath+"queryEqByProjectIDByScheme", param);
	}
	
	// 查询该项目所选择的方案设备
	public List<Map<String,Object>> queryEqBySchemeChange(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList(basePath+"queryEqByProjectIDBySchemeLH", param);
	}
	
	public double getDoubled(String doubleStr){
		try{
			System.out.println("dddd="+doubleStr);
			return Double.parseDouble(doubleStr);
		}catch(Exception e){
			return 0;
		}
	}
	
	public String updateEqStatusCredit(Map<String,Object> param)
	{
		//
		PayTaskService pay=new PayTaskService();
		//方案
//		Object schemeObject=null;
		//从方案中获取首期款和其他费用
		//获得支付表ID
		String PAY_ID=Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");
		
		//是否固定利息
		String LXTQSQ=param.get("LXTQSQ")!=null ? param.get("LXTQSQ")+"":"1";//1:否，2：是
		
		//保存支付表头表(
		//保存支付表子表
		double ZJHJ=Double.parseDouble(param.get("MONTH_PRICE").toString());
		double LXHJ=Double.parseDouble(param.get("GDLX").toString());
		JSONArray josn = JSONArray.fromObject(param.get("ROWS"));
		
		List<Map<String, String>> list = pay.getParsePayList(josn);
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}
		
		//获取元方案信息
		Map<String,Object> headMap=Dao.selectOne(basePath+"queryHeadByScheme", param);
		Map<String,Object> returnMap=this.jiexiScheme(headMap, param,PAY_ID,LXTQSQ,LXHJ);
		String PAYLIST_CODE="";
		if(headMap!=null){
			Map<String,Object> headBaseInfoMap=Dao.selectOne(basePath+"queryHeadBaseInfo", param);
			Map<String,Object> MapPayCode=Dao.selectOne(basePath+"createPayListCode", headBaseInfoMap);
			if(MapPayCode !=null){
				PAYLIST_CODE=MapPayCode.get("PAYLIST_CODE").toString();
			}
			headMap.putAll(headBaseInfoMap);
			headMap.put("PAY_ID", PAY_ID);
			headMap.put("STATUS", "1");
			//headMap.put("START_DATE", param.get("START_DATE_CHANGE"));
			//headMap.put("REPAYMENT_DATE", param.get("REPAYMENT_DATE_CHANGE"));
			headMap.put("SCHEME_CLOB", returnMap.get("SCHEME_CLOB").toString());
			headMap.put("MONEY_ALL", ZJHJ);
			headMap.put("PAYLIST_CODE", PAYLIST_CODE);
			headMap.put("VERSION_CODE", "1");
			if(LXTQSQ.equals("2")){
				headMap.put("GDLX", LXHJ);
			}
			Dao.insert(basePath+"insertRent_Plan_head",headMap);
		}
		
		
		List listFY=(List)returnMap.get("listFY");
		for(int num=0;num<listFY.size();num++){
			Map mapFY=(Map)listFY.get(num);
			Dao.insert("PayTask.payplan_detail", mapFY);
		}
		
		//修改设备表数据并反更支付表ID
		param.put("PAY_ID", PAY_ID);
		List<Map<String,Object>> eqList=new ArrayList<Map<String,Object>>();
		if (param.get("EQLIST") != null && !param.get("EQLIST").equals("")) {
			JSONObject eqMap = JSONObject.fromObject(param.get("EQLIST"));
			eqList=(List)eqMap.get("EqList");
		}
		for(int i=0;i<eqList.size();i++){
			Map<String,Object> eqMap=eqList.get(i);
			eqMap.put("PAY_ID", PAY_ID);
			//先判断本次有拆分吗
			double price=Double.parseDouble(eqMap.get("UNIT_PRICE").toString());
			Map<String,Object> priceMap=Dao.selectOne(basePath+"queryEqByCF", eqMap);
			double priceOld=Double.parseDouble(priceMap.get("UNIT_PRICE").toString());
			eqMap.put("PAYLIST_CODE", PAYLIST_CODE);
			if(price>=priceOld){
				Dao.update(basePath+"updateEqByCF1", eqMap);
			}
			else{
				//先复制一条出来获取ID
				String EQ_NEW_ID=Dao.getSequence("SEQ_FIL_PROJECT_EQUIPMENT");
				eqMap.put("EQ_NEW_ID", EQ_NEW_ID);
				Dao.insert(basePath+"insertEqByCF", eqMap);
				Dao.update(basePath+"updateEqByCF", eqMap);
			}
			
		}
		
		return PAY_ID;
	}
	
	//设备拆分解析方案
	public Map<String,Object> jiexiScheme(Map<String,Object> headMap,Map<String,Object> param,String PAY_ID,String LXTQSQ,double LXHJ){
		double MONEYCF=Double.parseDouble(param.get("MONEYCF").toString());
		double MONEYTOTAL=Double.parseDouble(param.get("MONEYTOTAL").toString());
		
		List<Map<String,Object>> listFY=new ArrayList<Map<String,Object>>();
		Map<String,Object> returnMap=new HashMap<String,Object>();
		if(headMap!=null){
			Dao.getClobTypeInfo2(headMap, "SCHEME_CLOB");
			JSONArray SCHEME_CLOB=JSONArray.fromObject(headMap.get("SCHEME_CLOB"));
			for(int i=0;i<SCHEME_CLOB.size();i++){
				Map<String,Object> schemeMap=(Map)SCHEME_CLOB.get(i);
				if(schemeMap!=null && schemeMap.get("KEY_NAME_EN")!=null && !schemeMap.get("KEY_NAME_EN").equals(""))
				{
					String KEY_NAME_EN=schemeMap.get("KEY_NAME_EN").toString();
					if(!KEY_NAME_EN.equals("LAST_MONEY")){//--尾款不放在首期款中也不放在其他费用中
						if(KEY_NAME_EN.indexOf("MONEY")!=-1 || KEY_NAME_EN.indexOf("PRICE")!=-1){//说明是金额
							double CODE_MONEY=0d;
							if(schemeMap.get("VALUE_STR")!=null && !schemeMap.get("VALUE_STR").equals("")){
								CODE_MONEY=Double.parseDouble(schemeMap.get("VALUE_STR").toString());
							}
							double MONEY=0d;
							if(KEY_NAME_EN.equals("GDLX_PRICE") && LXTQSQ.equals("2")){
								MONEY=LXHJ;
							}
							else{
								MONEY=CODE_MONEY * MONEYCF / MONEYTOTAL;
							}
							schemeMap.put("VALUE_STR", MONEY);
							if(schemeMap.get("FYGS")!=null && !schemeMap.get("FYGS").equals("") && schemeMap.get("FYGS").equals("JRSQK"))//首期款
							{
								if(MONEY>0){
									Map<String,Object> mapFY=new HashMap<String,Object>();
									mapFY.put("ITEM_FLAG", 1);//类型
									mapFY.put("ITEM_NAME",schemeMap.get("KEY_NAME_ZN"));//中文名称
									mapFY.put("ITEM_NAME_EN", KEY_NAME_EN);//名称
									mapFY.put("ITEM_MONEY", MONEY);//金额
									mapFY.put("PAY_DATE", param.get("FIRSTPAYDATE"));//应收时间
									mapFY.put("PAY_ID", PAY_ID);//支付表ID
									mapFY.put("IS_COLLECTING", schemeMap.get("DSFS"));//是否代收
									listFY.add(mapFY);
								}
								
							}
							else if(schemeMap.get("FYGS")!=null && !schemeMap.get("FYGS").equals("") && schemeMap.get("FYGS").equals("JRQTFY"))//其他费用
							{
								if(MONEY>0){
									Map<String,Object> mapFY=new HashMap<String,Object>();
									mapFY.put("ITEM_FLAG", 4);//类型
									mapFY.put("ITEM_NAME",schemeMap.get("KEY_NAME_ZN"));//中文名称
									mapFY.put("ITEM_NAME_EN", KEY_NAME_EN);//名称
									mapFY.put("ITEM_MONEY", MONEY);//金额
									mapFY.put("PAY_DATE", param.get("FIRSTPAYDATE"));//应收时间
									mapFY.put("PAY_ID", PAY_ID);//支付表ID
									mapFY.put("IS_COLLECTING", schemeMap.get("DSFS"));//是否代收
									listFY.add(mapFY);
								}
								
							}
						}
					}
					
					
				}
			}
			//手续费（属于首期款）不再大字段中 ，手动添加
			//先判断手续费是一次性收取还是分期收取，如果一次性收取放首期款中，如果分期收取则平摊到每期（如果分期则前面已处理）
			String MANAGEMENT_FEETYPE="1";//1默认为一次性收取
			if(headMap!=null && headMap.get("MANAGEMENT_FEETYPE")!=null && !headMap.get("MANAGEMENT_FEETYPE").equals("")){
				MANAGEMENT_FEETYPE=headMap.get("MANAGEMENT_FEETYPE").toString();
				
			}
			if(MANAGEMENT_FEETYPE.equals("1")){
				if(headMap!=null && headMap.get("POUNDAGE_PRICE")!=null && !headMap.get("POUNDAGE_PRICE").equals("")){
					double poundage_price=Double.parseDouble(headMap.get("POUNDAGE_PRICE").toString());
					if(poundage_price>0){
						Map<String,Object> mapFY=new HashMap<String,Object>();
						mapFY.put("ITEM_FLAG", 1);//类型
						mapFY.put("ITEM_NAME","手续费");//中文名称
						mapFY.put("ITEM_NAME_EN", "POUNDAGE_PRICE");//名称
						mapFY.put("ITEM_MONEY", poundage_price);//金额
						mapFY.put("PAY_DATE", param.get("FIRSTPAYDATE"));//应收时间
						mapFY.put("PAY_ID", PAY_ID);//支付表ID
						listFY.add(mapFY);
					}
				}
			}
			
			
			
			returnMap.put("SCHEME_CLOB", SCHEME_CLOB);
			returnMap.put("listFY", listFY);
		}
		
		return returnMap;
	}
	
	//查询首付款情况
	public VelocityContext fundInfo(Map<String,Object> map,VelocityContext context){
		//通过支付表查询还款类型是期末还是期初，期初付款包括第一期金额
		Map<String,Object> mapPayWay=Dao.selectOne(basePath+"queryPayWayByPayId", map);
		String PAY_WAY=mapPayWay.get("PAY_WAY")!=null ? mapPayWay.get("PAY_WAY").toString():"1";
		map.put("PAYLIST_CODE", mapPayWay.get("PAYLIST_CODE")); 
		List<Map<String,Object>> beginList=new ArrayList<Map<String,Object>>();
		Map<String,Object> fundMap=new HashMap<String,Object>();
		if(PAY_WAY.equals("2") || PAY_WAY.equals("4") || PAY_WAY.equals("6")){//期初
			beginList=Dao.selectList(basePath+"queryBeginByCodePayWay", mapPayWay);
			fundMap=Dao.selectOne(basePath+"queryBeginByCodeMoneyAllPayWay", mapPayWay);
		}
		else{//期末
			beginList=Dao.selectList(basePath+"queryBeginByCode", mapPayWay);
			fundMap=Dao.selectOne(basePath+"queryBeginByCodeMoneyAll", mapPayWay);
		}
		context.put("fundMap", fundMap);
		context.put("beginList", beginList);
		
		map.put("tags", "设备单位");
		context.put("eqList", Dao.selectList(basePath+"queryEqByProjectIDByPayID", map));
		return context;
	}
	
	//查询发货单数据
	public Map<String,Object> queryContractInfo(Map<String,Object> map){
		//先查询有发货单信息没
		int COUNTNUM=Dao.selectInt(basePath+"querySendCount", map);
		if(COUNTNUM==0){
			Map<String,Object> mapInfo=Dao.selectOne(basePath+"queryContractInfo", map);
			//通过支付表编号查询项目ID
			Map<String,Object> mapPro=Dao.selectOne(basePath+"queryProjectIdByPay_Code",map);
			String CODE=CodeService.getCode("发货编号", null, mapPro.get("PROJECT_ID")+"");
			mapInfo.put("SEND_LEASE_CODE", CODE);
			return mapInfo;
		}
		else{
			return Dao.selectOne(basePath+"querySendInfo",map);
		}
	}
	
	
	public Map<String,Object> queryContractInfoByFund(Map<String,Object> map){
		Map<String,Object> mapInfo=Dao.selectOne(basePath+"queryContractInfo", map);
		return mapInfo;
		
	}
	
	/**
	 * 保存发货单
	 * */
	public void doAddDeliveryProduct(Map<String, Object> map){
		map.put("USERNAME", Security.getUser().getName());
		map.put("CREATE_ID", Security.getUser().getId());
		int COUNTNUM=Dao.selectInt(basePath+"querySendCount", map);
		if(COUNTNUM==0){
			String SendNoticeId = Dao.getSequence("SEQ_EQUIPMENT_SENDNOTICE");
			map.put("SENDNOTICEID", SendNoticeId);
			//创建一条验收单
			Dao.insert(basePath+"insertRECEIPT",map);
			String path=newPath3(map);
			try{
				this.expContractPdf3(map, path);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			map.put("URL",path.replace("\\", "/"));	
			Dao.insert(basePath+"doAddDeliveryProduct", map);
		}
		else{
			String path=newPath3(map);
			try{
				this.expContractPdf3(map, path);
			}catch(Exception e){
				e.printStackTrace();
			}
			map.put("URL",path.replace("\\", "/"));	
			Dao.update(basePath+"doUpdateDeliveryProduct", map);
		}
//		//新建一条租赁物交接清单
//		BaseDao.newInstance().insert("DeliveryProduct.handover", map);//租赁物交接清单应该在发货之后才会存在
	}
	
	
	public boolean createDirectory1(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	
	
	public String newPath3(Map<String,Object> map)
	{
		String root =  SkyEye.getConfig("file.path").toString();
		String relativepath =root+File.separator+"deliveryProduct"+File.separator+map.get("SENDNOTICEID").toString();
		createDirectory1(relativepath);
		String filetype=".pdf";
		String RE_FILE_NAME = UUID.randomUUID().toString() + filetype;
		String path=relativepath+File.separator+RE_FILE_NAME;
		return path;
	}	
	
	
	public void expContractPdf3(Map<String, Object> map, String path)
	throws Exception {

		// 字体设置
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.EMBEDDED);
		Font FontColumn2 = new Font(bfChinese, 15, Font.BOLD);
		@SuppressWarnings("unused")
		Font FontDefault22 = new Font(bfChinese, 5, Font.NORMAL);
		@SuppressWarnings("unused")
		Font FontTitle = new Font(bfChinese, 10, Font.UNDERLINE);
		Font FontDefault2 = new Font(bfChinese, 10, Font.NORMAL);
//		Font FontDefault = new Font(bfChinese, 15, Font.NORMAL);
//		Font FontDefaultbig = new Font(bfChinese, 20, Font.BOLD);
//		Font fa = new Font(bfChinese, 12, Font.BOLD);
		
		// 页面设置
		Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小
		Document document = new Document(rectPageSize, 20, 20, 20, 20); // 其余4个参数，设置了页面的4个边距
		
		FileOutputStream baoss = new FileOutputStream(path);
		
		PdfWriter.getInstance(document, baoss);
		
		document.open();
		
		List<?> list=Dao.selectList(basePath+"queryEquimentForDeilvery", map);
		
		
		PdfPTable tT4 = new PdfPTable(7);
		tT4.setWidthPercentage(100f);
		tT4.addCell(makeCellSetColspan2222("发货通知单", PdfPCell.ALIGN_CENTER,FontColumn2, 7));
		tT4.addCell(makeCellSetColspan2222("", PdfPCell.ALIGN_CENTER,FontColumn2, 7));
		tT4.addCell(makeCellSetColspan2222("", PdfPCell.ALIGN_CENTER,FontColumn2, 7));
		tT4.addCell(makeCellSetColspan2222("", PdfPCell.ALIGN_CENTER,FontColumn2, 7));
		
		tT4.addCell(makeCellSetColspanAll("设备名称",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("设备型号",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("单价",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("数量",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("单位",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("供应商",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll("厂商",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		
		for (int i = 0; i < list.size(); i++) 
		{
			Map<String,Object> map1=(Map<String,Object>)list.get(i);
			tT4.addCell(makeCellSetColspanAll(map1.get("THING_NAME")==null || map1.get("THING_NAME")==""?" ":map1.get("THING_NAME").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("MODEL_SPEC")==null || map1.get("MODEL_SPEC")==""?" ":map1.get("MODEL_SPEC").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("UNIT_PRICE")==null || map1.get("UNIT_PRICE")==""?" ":map1.get("UNIT_PRICE").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("AMOUNT")==null || map1.get("AMOUNT")==""?" ":map1.get("AMOUNT").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("UNIT")==null || map1.get("UNIT")==""?" ":map1.get("UNIT").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("SUPPLIERS_NAME")==null || map1.get("SUPPLIERS_NAME")==""?" ":map1.get("SUPPLIERS_NAME").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
			tT4.addCell(makeCellSetColspanAll(map1.get("COMPANY_NAME")==null || map1.get("COMPANY_NAME")==""?" ":map1.get("COMPANY_NAME").toString(),PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		}
		
		
		tT4.addCell(makeCellSetColspanAll("合同号",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("LEASE_CODE")==null || map.get("LEASE_CODE")==""?" ":map.get("LEASE_CODE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("支付表号",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("PAY_CODE")==null || map.get("PAY_CODE")==""?" ":map.get("PAY_CODE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));		
		
		tT4.addCell(makeCellSetColspanAll("承租人",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("BECR_NAME")==null || map.get("BECR_NAME")==""?" ":map.get("BECR_NAME").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("发货时间",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("SEND_TIME")==null || map.get("SEND_TIME")==""?" ":map.get("SEND_TIME").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));		
		
		tT4.addCell(makeCellSetColspanAll("收货单位",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("RECEIVE_COMPANY")==null || map.get("RECEIVE_COMPANY")==""?" ":map.get("RECEIVE_COMPANY").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("收货人",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("RECEIVE_NAME")==null || map.get("RECEIVE_NAME")==""?" ":map.get("RECEIVE_NAME").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));		
		
		tT4.addCell(makeCellSetColspanAll("收货地址",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("RECEIVE_ADDRESS")==null || map.get("RECEIVE_ADDRESS")==""?" ":map.get("RECEIVE_ADDRESS").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 6));	
		
		tT4.addCell(makeCellSetColspanAll("发货联系人",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("SEND_LINKMAN")==null || map.get("SEND_LINKMAN")==""?" ":map.get("SEND_LINKMAN").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("联系电话",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("SEND_LINKMANPHONE")==null || map.get("SEND_LINKMANPHONE")==""?" ":map.get("SEND_LINKMANPHONE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));
		
		tT4.addCell(makeCellSetColspanAll("收货联系人",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("RECEIVE_LINKMAN")==null || map.get("RECEIVE_LINKMAN")==""?" ":map.get("RECEIVE_LINKMAN").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("联系电话",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("RECEIVE_LINKMANPHONE")==null || map.get("RECEIVE_LINKMANPHONE")==""?" ":map.get("RECEIVE_LINKMANPHONE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));		
		
		tT4.addCell(makeCellSetColspanAll("融资租赁公司联系人",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("HOSH_LINKMAN")==null || map.get("HOSH_LINKMAN")==""?" ":map.get("HOSH_LINKMAN").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 3));		
		tT4.addCell(makeCellSetColspanAll("联系电话",PdfPCell.ALIGN_CENTER, FontDefault2, 1));
		tT4.addCell(makeCellSetColspanAll(map.get("HOSH_LINKMANPHONE")==null || map.get("HOSH_LINKMANPHONE")==""?" ":map.get("HOSH_LINKMANPHONE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 2));	
		
		tT4.addCell(makeCellSetColspanAll("发货通知单编号",PdfPCell.ALIGN_CENTER, FontDefault2, 1));	
		tT4.addCell(makeCellSetColspanAll(map.get("SEND_LEASE_CODE")==null || map.get("SEND_LEASE_CODE")==""?" ":map.get("SEND_LEASE_CODE").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 6));
		
		tT4.addCell(makeCellSetColspanAll("付款说明",PdfPCell.ALIGN_CENTER, FontDefault2, 1));	
		tT4.addCell(makeCellSetColspanAll(map.get("PAY_REMARK")==null || map.get("PAY_REMARK")==""?" ":map.get("PAY_REMARK").toString(),PdfPCell.ALIGN_LEFT, FontDefault2, 6));
		
		document.add(tT4);
		
		
		
		document.close();
		baoss.close();
		
		}
	
	
	/**
	 * 全有
	 * 
	 * @param content
	 * @param align
	 * @param FontDefault
	 * @param colspan
	 * @return
	 */
	private PdfPCell makeCellSetColspanAll(String content, int align,
			Font FontDefault, int colspan) {
		Phrase objPhase = new Phrase(content, FontDefault);
		PdfPCell objCell = new PdfPCell(objPhase);
		objCell.setHorizontalAlignment(align);
		objCell.setVerticalAlignment(align);
		objCell.setColspan(colspan);
		objCell.setBorderWidthLeft(0.5f);
		return objCell;
	}
	
	
	private PdfPCell makeCellSetColspan2222(String content, int align,
			Font FontDefault, int colspan) {
		Phrase objPhase = new Phrase(content, FontDefault);
		PdfPCell objCell = new PdfPCell(objPhase);
		objCell.setPaddingLeft(50);
		objCell.setHorizontalAlignment(align);
		objCell.setVerticalAlignment(align);
		objCell.setColspan(colspan);
		objCell.setBorderWidthBottom(0);
		objCell.setBorderWidthTop(0);
		objCell.setBorderWidthLeft(0);
		objCell.setBorderWidthRight(0);
		return objCell;
	}
	
	
	/**
	 * 
	 * author:李威
	 * time:2011-6-24上午09:15:03
	 * @param path 文件路径
	 * @param response 
	 * @return
	 */
	public static HttpServletResponse download(String path, HttpServletResponse response) {
        try  {
            //  path是指欲下载的文件的路径。
           File file  =   new  File(path);
            //  取得文件名。
           String filename  =  file.getName();
            //  取得文件的后缀名。
//           String ext  =  filename.substring(filename.lastIndexOf( "." )  +   1 ).toUpperCase();

            //  以流的形式下载文件。
           InputStream fis  =   new  BufferedInputStream( new  FileInputStream(path));
            byte [] buffer  =   new   byte [fis.available()];
           fis.read(buffer);
           fis.close();
            //  清空response
           response.reset();
            //  设置response的Header
           response.setCharacterEncoding("UTF-8");
           response.addHeader( "Content-Disposition" ,  "attachment;filename="   +   new  String(filename.getBytes("GB2312"), "ISO-8859-1"));
//           response.addHeader( "Content-Length" ,  ""   +  file.length());
           OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
           response.setContentType( "application/octet-stream" );
           toClient.write(buffer);
           // 20110714 关闭流
           toClient.close();
           fis.close();
       }  catch  (IOException ex) {
    	  ;
       }
        return  response;
   } 
	
	/**
	 * 
	* @param @param path
	* @param @param response
	* @param @return
	* @return boolean
	* @throws
	* @date 2014年7月22日 下午2:58:43 
	* @author WuYanFei
	 */
	public  boolean downloadFile(String path, HttpServletResponse response) {
		boolean flag = false ;
        try  {
            //  path是指欲下载的文件的路径。
           File file  =   new  File(path);
           if(file.exists()){
        	   //  取得文件名。
               String filename  =  file.getName();
                //  取得文件的后缀名。
//               String ext  =  filename.substring(filename.lastIndexOf( "." )  +   1 ).toUpperCase();

                //  以流的形式下载文件。
               InputStream fis  =   new  BufferedInputStream( new  FileInputStream(path));
                byte [] buffer  =   new   byte [fis.available()];
               fis.read(buffer);
               fis.close();
                //  清空response
               response.reset();
                //  设置response的Header
               response.setCharacterEncoding("UTF-8");
               response.addHeader( "Content-Disposition" ,  "attachment;filename="   +   new  String(filename.getBytes("GB2312"), "ISO-8859-1"));
//               response.addHeader( "Content-Length" ,  ""   +  file.length());
               OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
               response.setContentType( "application/octet-stream" );
               toClient.write(buffer);
               // 20110714 关闭流
               toClient.close();
               fis.close();
               flag = true ;   
           }
       }  catch  (IOException ex) {
    	  
       }
        return  flag;
   } 
	
	
	public Map<String,Object> queryReceiptMap(Map<String,Object> map){
		return Dao.selectOne(basePath+"queryReceiptMap",map);
	}
	
	public void doAddReceipt(Map<String,Object> map){
		Dao.update(basePath+"doUpdateReceipt",map);
	}
	
	public void updateSendStatus(Map<String,Object> map){
		map.put("STATUS", 6);
		Dao.update(basePath+"updateSendStatus",map);
	}
	
	public int uploadReceiptFile(Map<String,Object> map){
		return Dao.update(basePath+"uploadReceiptFile",map);
	}
	
	public int uploadProjectFile(Map<String,Object> map){
		return Dao.update(basePath+"uploadProjectFile",map);
	}
	
	public Page toAjaxData(Map<String, Object> m) {
//		if(!Security.getUser().getId().equals("1")){
//			String ORG_LIST = BaseUtil.getSupOrgChildren();
//			m.put("ORG_LIST", ORG_LIST);
//		}
		return PageUtil.getPage(m, basePath+"toStartProject",basePath+"toStartProject_count");
	}
	
	public List<Map<String,Object>> getLeasePayList(Map<String, Object> map) {
		return Dao.selectList(basePath+"getLeasePayList", map);
	}
	
	public Page toDeliverAjaxData(Map<String, Object> m) {
		return PageUtil.getPage(m, basePath+"toDeliverData",basePath+"toDeliverData_count");
	}
	
	//公共方法，轻易不要修改
	public VelocityContext schemeUtilDXM(VelocityContext context,Map<String,Object> map){
		//查询基本信息
		projectService projectSer=new projectService();
		Map<String,Object> baseMap=projectSer.queryInfoByEqBase(map);
		context.put("baseMap", baseMap);
		
		if (map.get("ChangeViewData") != null && !map.get("ChangeViewData").equals("")) {
			JSONObject eqMap = JSONObject.fromObject(map.get("ChangeViewData"));
			context.put("eqList", eqMap.get("EqList"));
		}
		
		//查询方案和支付表
		context=this.querySchemeDXM(context, map);
		
		context.put("toNum", new UtilFinance());
		context.put("param", map);
		return context;
	}
	
	public VelocityContext querySchemeDXM(VelocityContext context,Map<String,Object> m){
		//查询设备总价
		m.put("MONEYTOTAL", Dao.selectDouble(basePath+"queryEqByScheme", m));
		BaseSchemeService bsService=new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		context.put("SCHEMEDETAIL", bsService.getSchemeClob(null,m.get("PROJECT_ID")+"",m.get("SCHEME_ROW_NUM")+""));
		Map<String,Object> mapbase=Dao.selectOne(basePath+"getSchemeBaseInfoByProjectIdINT", m);
		context.put("schemeBase", mapbase);

		if(mapbase!=null){
			mapbase.put("SCHEME_ID", mapbase.get("ID"));
			List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
			for (Map<String, Object> map2 : clobList) {
				mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
			}
			mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
			mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
			mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
			mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
			mapbase.put("pay_way", mapbase.get("PAY_WAY"));
			mapbase.put("date", m.get("START_DATE_CHANGE"));
			mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
			mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
			mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
			mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
			
			PayTaskService payTast=new PayTaskService();
			JSONObject page=null;
			try {
				page = payTast.quoteCalculateTest(mapbase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.put("detailList", page.get("ln"));
		}
		return context;
	}
	
	

	public String updateEqStatus(Map<String,Object> param)
	{
		PayTaskService pay=new PayTaskService();
		//方案
//		Object schemeObject=null;
		//从方案中获取首期款和其他费用
		//获得支付表ID
		String PAY_ID=Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");
		
		//是否固定利息
		String LXTQSQ=param.get("LXTQSQ")!=null ? param.get("LXTQSQ")+"":"1";//1:否，2：是
		
		//计算起租日期和还款日期利息差
		double DateLXMoney=0d;
		double residualPrincipal=param.get("FINANCE_TOPRIC")!=null ? Double.parseDouble(param.get("FINANCE_TOPRIC").toString()):0d;
		double annualRate=param.get("YEAR_INTEREST")!=null ? Double.parseDouble(param.get("YEAR_INTEREST").toString()):0d;
		//获取元方案信息
		Map<String,Object> headMap=Dao.selectOne(basePath+"queryHeadByScheme", param);
		int LEASE_PERIOD=1;
		if(StringUtils.isNotBlank(headMap.get("LEASE_PERIOD")))
			LEASE_PERIOD=Integer.parseInt(headMap.get("LEASE_PERIOD")+"");
		String PAY_WAY=headMap.get("PAY_WAY")+"";
		DateLXMoney=pay.getDateLiXiMoney(param.get("START_DATE_CHANGE")+"", param.get("REPAYMENT_DATE_CHANGE")+"", residualPrincipal, annualRate,LEASE_PERIOD,PAY_WAY);
		
		//保存支付表头表(先不生成支付表号，当流程审批通过后在给支付表编号并且刷数据到财务系统)
		//保存支付表子表
		double ZJHJ=Double.parseDouble(param.get("MONTH_PRICE").toString());
		double LXHJ=Double.parseDouble(param.get("GDLX").toString());
		JSONArray josn = JSONArray.fromObject(param.get("ROWS"));
		
		List<Map<String, String>> list = pay.getParsePayList(josn);
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}
		

		Map<String,Object> returnMap=this.jiexiScheme(headMap, param,PAY_ID,LXTQSQ,LXHJ);
		if(headMap!=null){
			Map<String,Object> headBaseInfoMap=Dao.selectOne(basePath+"queryHeadBaseInfo", param);
			headMap.putAll(headBaseInfoMap);
			headMap.put("PAY_ID", PAY_ID);
			headMap.put("STATUS", "1");
			headMap.put("START_DATE", param.get("START_DATE_CHANGE"));
			headMap.put("REPAYMENT_DATE", param.get("REPAYMENT_DATE_CHANGE"));
			headMap.put("SCHEME_CLOB", returnMap.get("SCHEME_CLOB").toString());
			headMap.put("MONEY_ALL", ZJHJ);
			
			if(LXTQSQ.equals("2")){
				headMap.put("GDLX", LXHJ);
			}
			Dao.insert(basePath+"insertRent_Plan_head",headMap);
		}
		
		
		List<Map<String,Object>> listFY=(List)returnMap.get("listFY");
		for(int num=0;num<listFY.size();num++){
			Map<String,Object> mapFY=listFY.get(num);
			Dao.insert("PayTask.payplan_detail", mapFY);
		}
		
		//修改设备表状态并反更支付表ID
		param.put("PAY_ID", PAY_ID);
		Dao.update(basePath+"updateEqStatus",param);
		return PAY_ID;
	}
	
	public String updateEqStatusLH(Map<String,Object> param)
	{
		//先查询该项目是代收代付还是自收
		Map<String,Object> baseMap=Dao.selectOne(basePath+"queryLhInfoByProject",param);
		PayTaskService pay=new PayTaskService();
		//方案
//		Object schemeObject=null;
		//从方案中获取首期款和其他费用
		//获得支付表ID
		String PAY_ID=Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD");
		
		//是否固定利息
		String LXTQSQ=baseMap.get("LXTQSQ")!=null ? baseMap.get("LXTQSQ")+"":"1";//1:否，2：是
		
		double ZJHJ=0d;
		double LXHJ=0d;
		
		List<Map<String, String>> irrList = new ArrayList<Map<String, String>>();
		
		if(baseMap!=null && baseMap.get("LHSQFS")!=null && !baseMap.get("LHSQFS").equals("")){
			String LHSQFS=baseMap.get("LHSQFS").toString();//1:代收代付 2：自收
			//如果为代收的话，就要保存整个支付表到支付表管理，并且记录下各个子表
			//如果为自收，则只需要保存该平台的支付表信息。并且记录各个子表
			
			
			
			if(LHSQFS.equals("1")){
				//先保存整的
				
				//代收代付
				Map<String,Object> CFMap=Dao.selectOne(basePath+"queryEqMoneyByProjectIDRow",param);
				param.putAll(CFMap);
				
				//保存小方案到联合租赁记录表中
			}
			else{
				//代收代付
				Map<String,Object> CFMap=Dao.selectOne(basePath+"queryEqPriceByProjectID11",param);
				param.putAll(CFMap);
			}
		}
		
		
//		BaseSchemeService bsService=new BaseSchemeService();
		Map<String,Object> mapbase=Dao.selectOne(basePath+"getSchemeBaseInfoByProjectIdINT", param);

		if(mapbase!=null){
			mapbase.put("SCHEME_ID", mapbase.get("ID"));
			List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
			for (Map<String, Object> map2 : clobList) {
				mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
			}
			mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
			mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
			mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
			mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
			mapbase.put("pay_way", mapbase.get("PAY_WAY"));
			mapbase.put("date", param.get("START_DATE_CHANGE"));
			mapbase.put("date1", param.get("REPAYMENT_DATE_CHANGE"));
			mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
			mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
			mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
			mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
			
			PayTaskService payTast=new PayTaskService();
			JSONObject page=null;
			try {
				page = payTast.quoteCalculateTest(mapbase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			irrList = (List<Map<String, String>>) page.get("ln");
			//查询租金合计
			for(int ir=0;ir<irrList.size();ir++){
				Map irMap=(Map)irrList.get(ir);
				if(irMap!=null && irMap.get("zj")!=null && !irMap.get("zj").equals("")){
					ZJHJ=ZJHJ+Double.parseDouble(irMap.get("zj").toString());
				}
				
				if(irMap!=null && irMap.get("lx")!=null && !irMap.get("lx").equals("")){
					LXHJ=LXHJ+Double.parseDouble(irMap.get("lx").toString());
				}
			}
		}
		
		List<Map<String, String>> list = pay.getParsePayList((JSONArray)irrList);
		for (Map<String, String> map_ : list) {
			map_.put("PAY_ID", PAY_ID);
			map_.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map_);
		}
		
		//获取元方案信息
		Map<String,Object> headMap=Dao.selectOne(basePath+"queryHeadByScheme", param);
		Map<String,Object> returnMap=this.jiexiScheme(headMap, param,PAY_ID,LXTQSQ,LXHJ);
		if(headMap!=null){
			Map<String,Object> headBaseInfoMap=Dao.selectOne(basePath+"queryHeadBaseInfo", param);
			headMap.putAll(headBaseInfoMap);
			headMap.put("PAY_ID", PAY_ID);
			headMap.put("STATUS", "1");
			headMap.put("START_DATE", param.get("START_DATE_CHANGE"));
			headMap.put("REPAYMENT_DATE", param.get("REPAYMENT_DATE_CHANGE"));
			headMap.put("SCHEME_CLOB", returnMap.get("SCHEME_CLOB").toString());
			headMap.put("MONEY_ALL", ZJHJ);
			
			if(LXTQSQ.equals("2")){
				headMap.put("GDLX", LXHJ);
			}
			Dao.insert(basePath+"insertRent_Plan_head",headMap);
		}
		
		
		List<Map<String,Object>> listFY=(List)returnMap.get("listFY");
		for(int num=0;num<listFY.size();num++){
			Map<String,Object> mapFY=listFY.get(num);
			Dao.insert("PayTask.payplan_detail", mapFY);
		}
		
		//修改设备表状态并反更支付表ID
		param.put("PAY_ID", PAY_ID);
		Dao.update(basePath+"updateEqStatus",param);
		
		//保存联合租赁各个方案到新建表内
		saveLHInfo(param);
		return PAY_ID;
	}
	
	//保存联合融资租赁的详细还款明细
	public void saveLHInfo(Map<String,Object> param){
		Map<String,Object> infoMap=param;
		if(infoMap!=null){
			
			
			List<Map<String,Object>> CFList=Dao.selectList(basePath+"queryEqPriceByProjectID22",param);
			for(int i=0;i<CFList.size();i++){
				Map<String,Object> CFMap=CFList.get(i);
				infoMap.putAll(CFMap);
				
//				BaseSchemeService bsService=new BaseSchemeService();
				Map<String,Object> mapbase=Dao.selectOne(basePath+"getSchemeBaseInfoByProjectIdINT", param);
				
				if(mapbase!=null){
					mapbase.put("SCHEME_ID", mapbase.get("ID"));
					List<Map<String,Object>> clobList=Dao.selectList(basePath+"queryfil_scheme_clobForCs", mapbase);
					for (Map<String, Object> map2 : clobList) {
						mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
					}
					mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
					mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
					mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
					mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
					mapbase.put("pay_way", mapbase.get("PAY_WAY"));
					mapbase.put("date", param.get("START_DATE_CHANGE"));
					mapbase.put("date1", param.get("REPAYMENT_DATE_CHANGE"));
					mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
					mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
					mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
					mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
					
					PayTaskService payTast=new PayTaskService();
					JSONObject page=null;
					try {
						page = payTast.quoteCalculateTest(mapbase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
					
					List<Map<String, String>> list = payTast.getParsePayList((JSONArray)irrList);
					for (Map<String, String> map_ : list) {
						map_.put("PAY_ID", param.get("PAY_ID").toString());
						map_.put("ITEM_FLAG", "2");
						map_.put("FL_JOIN_ID", CFMap.get("ID").toString());
						Dao.insert(basePath+"payplan_LH_detail", map_);
					}
				}
			}
			
		}
		
	}
	
	//查询未起租的支付表
	public List<Map<String,Object>> queryPayListByEq(Map<String,Object> map){
		return Dao.selectList(basePath+"queryPayListByEq", map);
	}
	
	//公共方法，轻易不要修改
	public VelocityContext rentPlanUtilData(VelocityContext context,Map<String,Object> map){
		//查询基本信息
//		projectService projectSer=new projectService();
		Map<String,Object> baseMap=Dao.selectOne(basePath+"queryInfoByRentPlan", map);
		baseMap.put("START_DATE", map.get("START_DATE_CHANGE"));
		baseMap.put("REPAYMENT_DATE", map.get("REPAYMENT_DATE_CHANGE"));
		context.put("baseMap", baseMap);
		map.put("tags", "设备单位");
		context.put("eqList", Dao.selectList(basePath+"queryEqByProjectIDByPayID", map));
		//查询方案和支付表
		context=this.queryRentPlanInfoData(context, map);
		
		context.put("toNum", new UtilFinance());
		map.put("PLATFORM_TYPE", baseMap.get("PLATFORM_TYPE"));
		context.put("param", map);
		return context;
	}
	
	public VelocityContext queryRentPlanInfoData(VelocityContext context,Map<String,Object> m){
		//先查出本次拆分的比例
//		BaseSchemeService bsService=new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		
		Map<String,Object> mapbase=Dao.selectOne(basePath+"queryHeadInfoByPayId", m);
		//计算起租日期和还款日期利息差
		PayTaskService pay=new PayTaskService();
		double DateLXMoney=0d;
		double residualPrincipal=mapbase.get("FINANCE_TOPRIC")!=null ? Double.parseDouble(mapbase.get("FINANCE_TOPRIC").toString()):0d;
		double annualRate=mapbase.get("YEAR_INTEREST")!=null ? Double.parseDouble(mapbase.get("YEAR_INTEREST").toString()):0d;
		int LEASE_PERIOD=1;
		if(StringUtils.isNotBlank(mapbase.get("LEASE_PERIOD")))
			LEASE_PERIOD=Integer.parseInt(mapbase.get("LEASE_PERIOD")+"");
		String PAY_WAY=mapbase.get("PAY_WAY")+"";
		DateLXMoney=pay.getDateLiXiMoney(m.get("START_DATE_CHANGE")+"", m.get("REPAYMENT_DATE_CHANGE")+"", residualPrincipal, annualRate,LEASE_PERIOD,PAY_WAY);
		
		JSONArray SCHEME_CLOB=null;
		Map<String,Object> object= Dao.selectOne(basePath+"queryClobByID", m);
		if(StringUtils.isNotBlank(object)){
			Map<String,Object> mapInfo=new HashMap<String,Object>();
			mapInfo.putAll(object);
			Dao.getClobTypeInfo2(mapInfo, "SCHEME_CLOB");
			SCHEME_CLOB=JSONArray.fromObject(mapInfo.get("SCHEME_CLOB"));
		}
		
		context.put("SCHEMEDETAIL", SCHEME_CLOB);
		
		context.put("schemeBase", mapbase);
		
		PayTaskService payTast=new PayTaskService();
		m.put("ID", m.get("PAY_ID"));
		
		List<Map<String,Object>> detailList=payTast.doShowRentPlanDetail(m);
		detailList=this.updatePayLineDate(detailList, m.get("REPAYMENT_DATE_CHANGE")+"", mapbase.get("PAY_WAY")+"", mapbase.get("LEASE_PERIOD")+"", mapbase.get("LEASE_TERM")+"");
		if(DateLXMoney>0 && detailList.size()>0){
			Map mapOne=(Map)detailList.get(0);
			mapOne.put("ZJ", (Double.parseDouble(mapOne.get("ZJ").toString())+DateLXMoney));
			mapOne.put("LX", (Double.parseDouble(mapOne.get("LX").toString())+DateLXMoney));
		}
		context.put("detailList", detailList);
		
		//查询其他费用
		List qtFy=Dao.selectList(basePath+"queryQT", m);
		context.put("qtFy", qtFy);
		//查询首期款
		List qtSQ=Dao.selectList(basePath+"querySQ", m);
		context.put("qtSQ", qtSQ);
		context.put("DateLXMoney", DateLXMoney);
		return context;
	}
	
	public int saveStartDate(Map map){
		//保存主表起租日和还款日
		int num=Dao.update(basePath+"updateRentPlanStartDateNew",map);
		//查询起租日和还款日之间利息差
		//计算起租日期和还款日期利息差
		PayTaskService pay=new PayTaskService();
		Map mapbase=Dao.selectOne(basePath+"queryHeadInfoByPayId", map);
		double DateLXMoney=0d;
		double residualPrincipal=mapbase.get("FINANCE_TOPRIC")!=null ? Double.parseDouble(mapbase.get("FINANCE_TOPRIC").toString()):0d;
		double annualRate=mapbase.get("YEAR_INTEREST")!=null ? Double.parseDouble(mapbase.get("YEAR_INTEREST").toString()):0d;
		
		int LEASE_PERIOD=1;
		String PAY_WAY=mapbase.get("PAY_WAY")+"";
		if(StringUtils.isNotBlank(mapbase.get("LEASE_PERIOD")))
			LEASE_PERIOD=Integer.parseInt(mapbase.get("LEASE_PERIOD")+"");
		DateLXMoney=pay.getDateLiXiMoney(map.get("START_DATE")+"", map.get("REPAYMENT_DATE")+"", residualPrincipal, annualRate,LEASE_PERIOD,PAY_WAY);
		
		PayTaskService payTast=new PayTaskService();
		map.put("ID", map.get("PAY_ID"));
		List detailList=payTast.doShowRentPlanDetail(map);
		detailList=this.updatePayLineDate(detailList, map.get("REPAYMENT_DATE")+"", mapbase.get("PAY_WAY")+"", mapbase.get("LEASE_PERIOD")+"", mapbase.get("LEASE_TERM")+"");
		if(DateLXMoney>0 && detailList.size()>0){
			Map mapOne=(Map)detailList.get(0);
			mapOne.put("ZJ", (Double.parseDouble(mapOne.get("ZJ").toString())+DateLXMoney));
			mapOne.put("LX", (Double.parseDouble(mapOne.get("LX").toString())+DateLXMoney));
		}
		for(int i=0;i<detailList.size();i++){
			Map mapDetail=(Map)detailList.get(i);
			if(i==0){//第一期要保存利息差
				Dao.update(basePath+"updatePlanDetailOneZJ",mapDetail);
				Dao.update(basePath+"updatePlanDetailOneLX",mapDetail);
			}
				Dao.update(basePath+"updatePlanDetail",mapDetail);
			
		}
		
		//修改设备表的起租状态
		Dao.update(basePath+"updateEqByPayId",map);
		
//		//刷数据到期初表
//		//获取其他费用和首期款费用
//		List QTFY=Dao.selectList(basePath+"queryQTFYByPayID", map);
//		for(int i=0;i<QTFY.size();i++){
//			Map qtMap=(Map)QTFY.get(i);
//			qtMap.put("CREATE_ID", Security.getUser().getId());
//			Dao.insert("PayTask.insert_r_beginning",qtMap);
//		}
//		List ZJFY=Dao.selectList(basePath+"queryZJByPayID", map); 
//		for(int i=0;i<ZJFY.size();i++){
//			Map zjMap=(Map)ZJFY.get(i);
//			zjMap.put("CREATE_ID", Security.getUser().getId());
//			Dao.insert("PayTask.insert_r_beginning",zjMap);
//		}
		
		return num;
	}
	
	/**
	 * 设定起租日-更新子表还款日期
	 * 
	 * @param
	 * @return object
	 */
	public List<Map<String,Object>> updatePayLineDate(List<Map<String,Object>> list, String startdate, String pay_way, String lease_period, String lease_term){
		int cutTerm = Integer.valueOf(lease_period);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String[] dateTrem = startdate.split("-");
		Calendar calender = Calendar.getInstance();
		calender.set(Integer.parseInt(dateTrem[0]), Integer.parseInt(dateTrem[1]) - 1, Integer.parseInt(dateTrem[2]));
		int num = Integer.parseInt(lease_term);// 期次
		if (pay_way.equals("1") || pay_way.equals("3") || pay_way.equals("5")) {
			for (int i = 0; i < num; i++) {
				calender.set(Integer.parseInt(dateTrem[0]), Integer.parseInt(dateTrem[1]) - 1, Integer.parseInt(dateTrem[2]));
				calender.add(Calendar.MONTH, (i == 0 ? cutTerm : (i+1) * cutTerm));
				calender.set(Calendar.HOUR_OF_DAY, 0);
				calender.set(Calendar.MINUTE, 0);
				calender.set(Calendar.SECOND, 0);
				calender.set(Calendar.MILLISECOND, 0);
				startdate = sf.format(calender.getTime());
				Map<String, Object> m = (Map<String, Object>) list.get(i);
				m.put("PAY_DATE", startdate);
				m.put("PERIOD_NUM", i + 1);
				
			}
		} else if (pay_way.equals("2") || pay_way.equals("4") || pay_way.equals("6")) {
			for (int i = 0; i < num; i++) {
				calender.set(Integer.parseInt(dateTrem[0]), Integer.parseInt(dateTrem[1]) - 1, Integer.parseInt(dateTrem[2]));
				calender.add(Calendar.MONTH, i == 0 ? 0 : i * cutTerm);
				calender.set(Calendar.HOUR_OF_DAY, 0);
				calender.set(Calendar.MINUTE, 0);
				calender.set(Calendar.SECOND, 0);
				calender.set(Calendar.MILLISECOND, 0);
				startdate = sf.format(calender.getTime());
				Map<String, Object> m = (Map<String, Object>) list.get(i);
				m.put("PAY_DATE", startdate);
				m.put("PERIOD_NUM", i + 1);
				
			}

		} 
		return list;
	}
	
	public void updateLHCZ(Map<String,Object> map){
		//先查询有没有生成支付表，如果有生成，则不让其修改出资金额
		//查询出该项目的设备总额待用
		projectService Prservice=new projectService();
		int num=Prservice.getPayByProjectID(map);
		if(num==0){//可以修改
			List<Map<String, Object>> FL = (List<Map<String,Object>>) map.get("FLLIST");
			for(int i=0;i<FL.size();i++){
				Map<String,Object> FLMap=FL.get(i);
				Dao.update(basePath+"updateFLJOINByProjectID",FLMap);
			}
		}
	}
	
	public void queryEqIdsByProjectIDRow(Map<String,Object> map){
		map.putAll((Map<String,Object>)Dao.selectOne(basePath+"queryEqIdsByProjectIDRow", map));
	}
	
	public Map<String,Object> queryBaseInfoByPayId(Map<String,Object> map){
		return Dao.selectOne(basePath+"queryBaseInfoByPayId", map);
	}
	
	public Map<String,Object> getBaseSchemeBySchemeCode(String Pay_id){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("PAY_ID",Pay_id);
		String SCHEME_ID=Dao.selectOne(basePath+"getBaseSchemeBySchemeCode", map);
		map.put("SCHEME_ID", SCHEME_ID);
		return new projectService().getBaseSchemeBySchemeCode(map);
	}
}
