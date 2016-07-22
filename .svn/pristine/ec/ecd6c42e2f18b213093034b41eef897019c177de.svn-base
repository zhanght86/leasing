package com.pqsoft.mortgageProduct.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.mortgageProduct.service.MortgageProductService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class MortgageProductAction extends Action {


	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context = new VelocityContext();
	public MortgageProductService service = new MortgageProductService();

	/*********************************************************************************************************************************
     ****************************************                                                                *************************
     *                                                           以下为融资担保管理
     * *******************************************************************************************************************************
     *********************************************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","列表显示"})
	public Reply execute(){
		Map<String,Object> map = new HashMap<String,Object>();
		map = _getParameters();
		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(map);
		context.put("param", param);
		logger.debug("页面参数  = "+param);
		context.put("findOrg", service.findOrg());
		logger.debug("获取融资机构");
		return new ReplyHtml(VM.html("mortgageProduct/finceMorManager.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资担管理","列表显示"})
	public Reply toMgMorgage(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.findAllInfo(map));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","创建抵押或质押"})
	public Reply doMortgage()  {
		Map<String,Object> map = _getParameters();
		logger.debug("页面参数=========="+map);
		context.put("map", map);
		context.put("findLeaseCost",service.findLeaseCost(map));
		logger.debug("====抵押物总价值======"+service.findLeaseCost(map));
		context.put("getMorOrg", service.findMorOne(map));
		logger.debug("====获取单个融资机构名称======");	
		
		//判断是做抵押还是做质押， FLAGE=0 做抵押， FLAGE=1做质押
		if(Integer.parseInt(map.get("FLAGE").toString()) == 0) {
			context.put("getMorData", service.findMorData(map));
			logger.debug("====获取抵押资产详细======");
			return new ReplyHtml(VM.html("mortgageProduct/updatefinceMorManager.vm", context));
		}else if (Integer.parseInt(map.get("FLAGE").toString()) == 1) {
			context.put("getMorData", service.findPledgeData(map));
			logger.debug("====获取质押资产详细======");
			return new ReplyHtml(VM.html("mortgageProduct/finacePledge.vm", context));
		}
		return null;
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","新增抵押或质押"})
	public Reply doMorContract()  {
		Map<String,Object> map = _getParameters();
		logger.debug("页面参数=========="+map);
		//System.out.println("==============="+map.get("GUARANT_WAY"));
		context.put("findLeaseCost",service.findLeaseCost(map));
		logger.debug("======抵押物总价值======");
		context.put("getMorOrg", service.findMorOne(map));
		logger.debug("======获取单个融资机构名称======");	
		if(Integer.parseInt(map.get("GUARANT_WAY").toString().substring(0,1))== 1) {
			map.put("FLAGE", 0);
			context.put("map", map);
			context.put("getMorData", service.findMorContract(map));
			logger.debug("======获取抵押资产详细======");
			return new ReplyHtml(VM.html("mortgageProduct/updatefinceMorManager.vm", context));
		}else if (Integer.parseInt(map.get("GUARANT_WAY").toString().substring(0,1)) == 2) {
			map.put("FLAGE", 1);
			context.put("map", map);
			context.put("getMorData", service.findPledgeContract(map));
			logger.debug("======获取质押资产详细======");
			return new ReplyHtml(VM.html("mortgageProduct/finacePledge.vm", context));
		}	
		return null;
	}
	

    @aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资担保合同管理","新增抵押或质押"})
	public Reply doInertMor()   {
		Map<String, Object> map = _getParameters();	
		logger.debug("页面参数=====11====="+map);
		if(Integer.parseInt(map.get("FLAGE").toString())==0) {
			//合同类型：0为抵押合同
			map.put("FLAGE", "0");			
			//支付表抵押状态
			map.put("MORTGATE_STATUS", "1");			
			//日志文件 抵押状态：0已抵押
			map.put("FLAGE","0");
			//抵押物件类型： 0 抵押
			map.put("TYPE", "0");
			//System.out.println("--------map-01-------"+map.get("TREID"));
			
			//添加抵押合同
			service.doInertMor(map);
			System.out.println("--------map-01-------"+map.get("TREID"));
			//添加抵押合同日志文件
			service.doMorLog(map);
			System.out.println("--------map-01-------"+map.get("TREID"));
			//添加抵押物
			service.doInsertMorEqu(map);
			System.out.println("--------map-01-------"+map.get("TREID"));
			//修改支付表
			service.dochangePay(map);
			
			System.out.println("--------map-01-------"+map.get("TREID"));
			return new ReplyAjax(true,null).addOp(new OpLog("融资管理","添加抵押或质押",""));
		} else if(Integer.parseInt(map.get("FLAGE").toString())==1) {
			//合同类型：1为抵押合同
			map.put("FLAGE", "1");			
			//支付表抵押状态
			map.put("MORTGATE_STATUS", "2");			
			//日志文件 抵押状态： 1已质押
			map.put("FLAGE","1");
			//抵押物件类型： 1抵押
			map.put("TYPE", "1");			
			//System.out.println("--------map--------"+map.get("TREID"));
			
			//添加抵押合同
			service.doInertMor(map);			
			//添加抵押合同日志文件
			service.doMorLog(map);
			//添加抵押物
			service.doInsertMorEqu(map);
			//修改支付表
			service.dochangePay(map);
			return new ReplyAjax(true,null).addOp(new OpLog("融资管理","添加抵押或质押",""));
		}
		return null;
	}
	

    @SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","修改"})
	public Reply getRegister()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();	
		logger.debug("页面参数=========="+map);	
		map.putAll(JSONObject.fromObject(map.get("json").toString()));
		context.put("param", map);
		context.put("findRegInfo", service.getRegister(map));	
		logger.debug("=====获取登记机关=====");
		
		return new ReplyHtml(VM.html("mortgageProduct/getRegister.vm", context));
	}

    /*********************************************************************************************************************************
     ****************************************                                                                *************************
     *                                                           以下为融资担保合同管理
     * *******************************************************************************************************************************
     *********************************************************************************************************************************/
    
    /**
     * findMorInfor
     * @date 2013-10-21 下午05:11:10
     * @author 杨雪
     * @return 返回值类型
     * @throws Exception
     */
    
    
    @aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","列表显示"})
	public Reply findMorInfor()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();	
		logger.debug("页面参数=========="+map);
		context.put("param", map);
		return new ReplyHtml(VM.html("mortgageProduct/finaceMortgageManager.vm", context));
	}
    
    @SuppressWarnings("unchecked")
    @aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资担保合同管理","列表显示"})
	public Reply findMorInforData(){
    	Map<String,Object> map = _getParameters();
    	JSONObject json = JSONObject.fromObject(map.get("param"));
    	map.remove("param");
    	map.putAll(json);
    	return new ReplyAjaxPage(service.findMorInfo(map));
    }
	
    @SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","解压"})
	public Reply doRemoveMor()  {
		Map<String, Object> map = new HashMap<String, Object>();
		map = _getParameters();
		logger.debug("页面参数=========="+map);
		int i = 0;
		List<Map<String,Object>> list = (List<Map<String, Object>>) service.selectFiMorStatus(map);
		logger.debug("=====抵押合同状态及支付表====="+list);
		if(list.size()>0) {//遍历所有支付表
			for(Object obj:list) {
				Map<String,Object> m = (Map<String,Object>)obj;
				//当支付表处于抵押或质押状态是方可解押
				if(Integer.parseInt(m.get("MORTGATE_STATUS").toString())==1||Integer.parseInt(m.get("MORTGATE_STATUS").toString())==2) {
					m.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
					i = service.doRemoveMor(m);
				}								
			}
		} 
		i = service.doRemoveMorC(map);
		logger.debug("=====抵押合同的日志文件状态=====");
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		//System.out.println("========"+i);
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","资产解压管理",""));
	}
	
    @aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","查看"})
	public Reply doSearchInforMor()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();	
		context.put("map", map);
		context.put("dosearchInforCon", service.doSearchInforCon(map));
		context.put("dosearchInforEuq", service.doSearchInforEqu(map));
		if(Integer.parseInt(map.get("type").toString())==1) {
			return new ReplyHtml(VM.html("mortgageProduct/doSearchInforMor.vm", context));					
		}else if(Integer.parseInt(map.get("type").toString())==2) {
			return new ReplyHtml(VM.html("mortgageProduct/updateMorInfor.vm", context));					
		}else {
			return new ReplyHtml(VM.html("mortgageProduct/doRegister.vm", context));
		}
	}
	
    @aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","修改"})
	public Reply doRegister()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();
		int i = service.doRegister(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资担保登记书添加",""));
	}	

    @aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","修改"})
	public Reply updateMorContract()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();
		int i = service.updateMorContract(map);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		
		return new ReplyAjax(flag,null).addOp(new OpLog("融资管理","融资担保合同修改",""));
	}	
	
    @SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担合同管理","列表显示"})
	public Reply getProjectName()  {
		Map<String, Object> map = new HashMap<String,Object>();
		map = _getParameters();	
		logger.debug("页面参数=========="+map);
		map.putAll(JSONObject.fromObject(map.get("json").toString()));
		context.put("param", map);
		context.put("findRroName", service.getProjectName(map));
		return new ReplyHtml(VM.html("mortgageProduct/getProjectName.vm", context));
		
	}
	
    @SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","导出"})
	public Reply excelMor() {
		Map<String, Object> map =  _getParameters();
		
		JSONObject json = JSONObject.fromObject(map.get("toCreateMor"));
		map.remove("toCreateMor");
		map.putAll(json);
		
		return new ReplyExcel(service.excelMor(map),"项目留购"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
	}
	
    @SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资担保合同管理","导出"})
	public Reply excelMor1() {
    	Map<String, Object> map =  _getParameters();
    	JSONObject json = JSONObject.fromObject(map.get("dataListR"));
    	List<Map<String, Object>> detailData = JSONArray.fromObject(json.get("dataList"));
    	String aa="(";
    	if(detailData != null){
			for (int k = 0; k < detailData.size(); k++) {
				 Map<String, Object> getMorData = detailData.get(k);
					if(k==detailData.size()-1)
					{
						aa += getMorData.get("FMPID");
					}
					else
					{
						aa+= getMorData.get("FMPID")+",";
					}
			}			
		}
    	aa+=")";
    	map.remove("dataListR");
    	map.put("ID", aa);
		return new ReplyExcel(service.excelMor(map),"项目留购"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
	}

}
