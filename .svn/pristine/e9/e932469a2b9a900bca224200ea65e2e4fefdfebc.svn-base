package com.pqsoft.pickCarStock.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.bpm.service.ConfigService;
import com.pqsoft.buyCertificate.service.BuyCertificateService;
import com.pqsoft.pickCarStock.service.pickCarStockService;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.HttpUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.PageUtil;

import net.sf.json.JSONArray;

public class pickCarStockAction extends Action {

	pickCarStockService service = new pickCarStockService();

	private VelocityContext context = new VelocityContext();

	BuyCertificateService bcs = new BuyCertificateService();

	// 调接口
	// 2423423sdf

	@aPermission(name = { "业务管理", "放款申请", "附加页面" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getCarsByContractNo() {

		String msg = "";

		Map<String, Object> map2 = _getParameters();

		Log.info("map2:->"+map2);
		// 根据合同号查询门店信息 传递给yipan 合同号 [LEASE_CODE]和 门店code
		List selectCar = service.selectCar(map2);
		String menDian = "";
		if (selectCar.size() > 0) {
			Object object = selectCar.get(0);
			if (object == null || object.equals("")) {
				menDian = "";
			} else {
				Map map = (Map) object;
				Object mdObj = map.get("MD");
				menDian = mdObj == null || mdObj.equals("") ? "" : String.valueOf(mdObj);
			}
		} else {
			org.jfree.util.Log.info("租赁系统,通过合同号,没有查找到任何信息,所以调用yipan接口失败");
			msg ="租赁系统,通过合同号,没有查找到任何信息,所以调用yipan接口失败";
		}
		// 读取接口地址的配置文件
		ConfigService configService;
		try {
			configService = new ConfigService();
		} catch (ConfigurationException e) {
			throw new ActionException("选择接口配置文件读取失败！");
		}
		//通过合同号查询项目编号
		String projectCode = service.getProjectCode(map2);
		Log.info(projectCode+"projectCode是否存在呢");
		// 发送请求并获取结果  
		Map map3 = new HashMap();
		map3.put("projectCode",projectCode);// 缺少门店CODE  测试数据,务必修改
		map3.put("organCode", menDian);
		Log.info("发送给yipan参数=" + map3);
		String content = HttpUtil.httpPost(configService.get("host") + configService.get("availableCarUrl"), map3, null,
				null);
		Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(content));
		Object status = map.get("status");
		if ((String.valueOf(status)).equals("ok")) {
			msg = "ok";
			Object object = map.get("cars");
			System.out.println(map+"调用yipan接口成功,所有的车=" + object);
			if (object != null && (!object.equals(""))) {
				JSONArray fromObject = JSONArray.fromObject(object);
				Page page = new Page(map);
				page.addDate(fromObject, 0);
				return new ReplyAjaxPage(page);
			}
		} else {
			msg = "调用接口失败:" + map.get("msg") + "状态:" + map.get("status");
		}
		return new ReplyAjax(true, msg);// 1.修改字段 显示页面的字段
	}

	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "交车中", "列表" })
	public Reply showPage() {
		Map<String, Object> map = _getParameters();
		System.out.println("map:->"+map);
		User user = Security.getUser();
		map.put("EM_ID", user.getId());
		map.put("ORG_CHILDREN", user.getOrg().getName());
		String Lease_code = null;
		if (!"".equals(map.get("PROJECT_ID"))) {
			Lease_code = service.queryAll(map);
			map.put("LEASE_CODE", Lease_code);
		}
		// 通过行业类型查询
		map.put("DATATYPE", "行业类型");
		context.put("param", map);
		// 去车务交车的页面
		return new ReplyHtml(VM.html("pickCarStock/list.vm", context));
	}

	// 根据 租赁系统的合同号查询出 客户 ,并且yipan的状态是3才能选中成功
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "业务管理", "合同签订", "添加签收单" })
	public Reply selectCar() {

		String msg = "租赁系统，通过项目，没有查询到任何信息";
		Map<String, Object> map = _getParameters();
		List selectCar = service.selectCarEx(map);// 租赁：一个合同只返回一辆车
		boolean flag = false;
		System.out.println(selectCar+"selectCar");
		if (selectCar!=null && selectCar.size() > 0) {
			Object carObj = selectCar.get(0);
			if (carObj == null || carObj == "") {
				msg = "租赁系统，通过项目，没有查询到任何信息";
				flag = false;
				return new ReplyAjax(flag, msg);
			} else {
				Map carMap = (Map) carObj;
				Object nameObj = carMap.get("CLIENTNAME");
				if (nameObj == null || nameObj == "") {
					msg = "租赁系统，通过项目，没有找对对应的客户";
					flag = false;
					return new ReplyAjax(flag, msg);
				} else {
					//通过合同号查询是否存在支付表号
					Log.info("刘丽自己的页面传递过来的参数"+map);
//					Object existsPayListCode = service.isExistsPayListCode(map);
//
//						if(existsPayListCode==null ||existsPayListCode.equals("")){
//							msg = "paylistcode不存在";
//							flag = false;
//							return new ReplyAjax(flag, msg);
//						}else{
//							Map plMap =(Map)existsPayListCode;
//							Object payListCodeObj = plMap.get("PAYLIST_CODE");
//							if(payListCodeObj==null || payListCodeObj.equals("")){
//								msg = "paylistcode不存在";
//								flag = false;
//								return new ReplyAjax(flag, msg);
//							}else{
//								Log.info("PAYLIST_CODE"+String.valueOf(payListCodeObj));
//							}
//						}
						
					//租赁系统
//					String PRO_CODE = "";
//					Object PRO_CODE_OBJ = map.get("PRO_CODE");
//					if(PRO_CODE_OBJ==null || PRO_CODE_OBJ.equals("")){
//						
//					}else{
//						PRO_CODE = String.valueOf(PRO_CODE_OBJ);
//					}
					
					String nameStr = String.valueOf(nameObj); // 租赁系统的名字项目都有了
					String customer = "";
					String registerStatus = "";
					String paylistCode ="";
					String baseWareId = "";
					// 判断yipan是否有值
//					Object paylistCodeObj =map.get("paylistCode");
					
					Object customerObj = map.get("customer");

					Object registerStatusObj = map.get("registerStatus");
					
//					Object baseWareIdObj = map.get("baseWareId");
//					System.out.println("baseWareIdObj->"+baseWareIdObj);

					// map.get("");返回的没有合同号

					if (customerObj == null || customerObj.equals("")) {
						// msg ="yipan传递的参数客户为空";
					} else {
						customer = String.valueOf(customerObj);
					}
					if (registerStatusObj == null || registerStatusObj.equals("")) {
						// msg ="yipan传递的参数状态为空";
					} else {
						registerStatus = String.valueOf(registerStatusObj);
					}
//					if(baseWareIdObj ==null || baseWareIdObj.equals("")){
//						
//					}else{
//						baseWareId = String.valueOf(baseWareIdObj);
//					}
					
					System.out.println("liuli->租赁名字"+nameStr+"yipan:"+customer+registerStatus);//+paylistCode+"租赁名字"+LEASE_CODE);
					// 两个系统进行比较
					if (nameStr.equals(customer) && registerStatus.equals("505")){//&&paylistCode.equals(LEASE_CODE)) {
						msg = "选择成功";
						flag = true;
						return new ReplyAjax(flag, msg);
					} else {
						if(!nameStr.equals(customer)){
							msg ="用户不匹配";
						}else if(!registerStatus.equals("505")){
							msg ="交车核验未通过";
						}
//						else if (baseWareId.equals("1")){
//							msg ="该车已经出库,请选择其他车辆";
//						}
						else{
							msg ="可能存在浏览器问题";
						}
						flag = false;
						msg += ",该车不能选择";
						return new ReplyAjax(flag, msg);
					}

				}

			}
		}
		flag = false;
		return new ReplyAjax(flag, msg);
	}

	// 添加签收单
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "业务管理", "合同签订", "添加签收单" })
	public Reply addQianshoudan() {
		// {"status":"error","msg":"请传入车辆资产信息！！！"}
		// LEASE_CODE
		String msg = "数据库查询参数出错,请联系技术人员";
		Map<String, Object> param = _getParametersIO(null, null, null);

		System.out.println(param + "有没有flag？");

		Object object = param.get("flag");
		if (object != null && object != "" && String.valueOf(object).equals("1")) {

			System.out.println("param+================" + param);
			// 调用宝的更新
			int i = bcs.addQianshoudan(param);
			if (i >= 1) {
				System.out.println("大于0");
				return new ReplyAjax().addOp(new OpLog("添加签收单", "添加", param + ""));
			} else {
				System.out.println("小于0");
				return new ReplyAjax(false);
			}

		} else {

			List<Object> paramField = service.getNField(param);
			if (paramField.size() > 0) {
				// 参数转换
				Map mp1 = new HashMap();
				Map mp = (Map) paramField.get(0);
				Object LEASE_CODE = mp.get("LEASE_CODE");
				Object NAME = mp.get("NAME");
				Object ID_CARD_NO = mp.get("ID_CARD_NO");
				Object ZJLX = mp.get("ZJLX");
				Object id = param.get("id");
				mp1.put("car.contractNo", LEASE_CODE == null || LEASE_CODE == "" ? "" : LEASE_CODE.toString());
				mp1.put("car.contractName", NAME == null || NAME == "" ? "" : NAME.toString());
				mp1.put("car.contractCardNo", ID_CARD_NO == null || ID_CARD_NO == "" ? "" : ID_CARD_NO.toString());
				mp1.put("car.contractCardType", ZJLX == null || ZJLX == "" ? "" : ZJLX.toString());
				mp1.put("car.id", id == null || id == "" ? "" : id.toString());
				mp1.put("car.toWareId", "1");

				//mod gengchangbao JZZL-new0707 2016年7月7日16:14:27 Start
				//当车辆已出库，不走出库接口（baseWareId = 1   代表该车辆已出库）
				Object status = "ok";
				Object errMsg = "";
				if (param.get("baseWareId") != null && !"".equals(param.get("baseWareId").toString()) 
						&& !"1".equals(param.get("baseWareId").toString())) {
					// 调扣减库存接口
	
					// 读取接口地址的配置文件
					ConfigService configService;
					try {
						configService = new ConfigService();
					} catch (ConfigurationException e) {
						throw new ActionException("选择接口配置文件读取失败！");
					}
	
					System.out.println("给yipan发送的扣减库存的参数:" + mp1);
				
					// 发送请求并获取结果
					String content = HttpUtil.httpPost(configService.get("host") + configService.get("carDeliverUrl"), mp1,
							null, null);
	
					Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(content));
						status = map.get("status");
						errMsg = map.get("msg");
				}
				if ((String.valueOf(status)).equals("ok")) {

					msg = "调用接口成功";
					Log.info(msg);

					// 插入数据库

					Object ZCID = param.get("id");
					Object CJH = param.get("cjh");
					Object FDJH = param.get("fdjh");
					Object YSMC = param.get("ysmc");
					Object PAYLIST_CODE = param.get("PAYLIST_CODE");

					Map m1 = new HashMap();
					m1.put("ZCID", ZCID == null || ZCID == "" ? "" : String.valueOf(ZCID));
					m1.put("CJH", CJH == null || CJH == "" ? "" : String.valueOf(CJH));
					m1.put("FDJH", FDJH == null || FDJH == "" ? "" : String.valueOf(FDJH));
					m1.put("YSMC", YSMC == null || YSMC == "" ? "" : String.valueOf(YSMC));
					m1.put("PAYLIST_CODE",
							PAYLIST_CODE == null || PAYLIST_CODE == "" ? "" : String.valueOf(PAYLIST_CODE));

					int addProjectEquipment = service.addProjectEquipment(m1);
//					if (addProjectEquipment > 0) {
//						msg = "保存车架号 成功";
//						Log.info(msg);

						// 调用宝的更新
						int i = bcs.addQianshoudan(param);
						if (i >= 1)
							return new ReplyAjax().addOp(new OpLog("添加签收单", "添加", param + ""));
						else
							return new ReplyAjax(false);
//					} else {
//						msg = "更新车架号失败PAYLIST_CODE="+PAYLIST_CODE;
//					}
//
//				} else {
//					msg = "调用接口失败" + errMsg;
				}

			}
		}
		return new ReplyAjax(false, msg);
	}

	// 默认加载页面
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "23", email = "shouli@jiechongchina.com", name = "李硕")
	@aPermission(name = { "业务管理", "合同签订", "列表" })
	public Reply queryData() {
		Map<String, Object> map = _getParameters();
		Page page = null;
		BaseUtil.getDataAllAuth(map);
		if (!"".equals(map.get("PROJECT_ID")) && map.get("PROJECT_ID") != null) {
			String Lease_code = service.queryAll(map);
			map.put("LEASE_CODE", Lease_code);
			page = service.getCertificatePage2(map);
		}
		return new ReplyAjaxPage(page);
	}

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
