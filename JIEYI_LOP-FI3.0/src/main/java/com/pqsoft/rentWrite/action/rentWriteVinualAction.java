package com.pqsoft.rentWrite.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
import com.pqsoft.rentWrite.service.rentWriteVinualService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class rentWriteVinualAction extends Action {

	private final Logger logger = Logger.getLogger(this.getClass());
	public VelocityContext context = new VelocityContext();
	private static DataDictionaryService dataDictionaryService = new DataDictionaryService();
	rentWriteVinualService service = new rentWriteVinualService();
	private FiEbankUtil util = new FiEbankUtil();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/bank_C_MangerVinual.vm", context));
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_MangerSUPP() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/bank_C_MangerVinual1.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply Bank_C_PageAjax() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Bank_C_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "租金扣划-虚拟-申请", "列表显示"})
	public Reply toMgDeduct() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));

		// 判断是否为供应商，供应商（厂商）则不能修改金额提交
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			context.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			context.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}

		return new ReplyHtml(VM.html("rentWriteVinual/bank_S_MangerVinual.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "租金扣划-虚拟-申请", "列表显示"})
	public Reply toAjaxData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toAjaxData(param));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "作废" })
	public Reply toRemovePayment() {
		Map<String, Object> map = _getParameters();// 获取页面参数

		map.put("USERCODE", Security.getUser().getCode());// 用户编号
		map.put("USERNAME", Security.getUser().getName());// 用户名

		int i = 0;
		i = service.doCancellation(map);

		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-非网银", "申请-作废", map.get("USERCODE").toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "提交" })
	public Reply toSubmitPayment() {
		Map<String, Object> map = _getParameters();// 获取页面参数

		map.put("USERCODE", Security.getUser().getCode());// 用户编号
		map.put("USERNAME", Security.getUser().getName());// 用户名

		FundNotEBankService service = new FundNotEBankService();
		int i = 0;
		i = service.dosubmitApp(map);

		boolean flag = false;
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-虚拟", "申请-提交", map.get("USERCODE").toString()));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请", "提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_C_Submit() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.bank_C_Submit(map);
		return this.toMgDeduct();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销", "列表显示" })
	public Reply toMgAppCheckMg() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/toFund_ConfirmVinual.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销", "列表显示" })
	public Reply toMgAppCheckMgData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toMgAppAlready(param));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销"})
	public Reply getCheckedDetail() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		context.put("param", map);
		Map mapPage = (Map) service.queryHeXiaoPage(map);
		// 判断是租金非网银还是供应商垫付 和租金池
		List listTage = new ArrayList();

		List poolList = new ArrayList();
		String OWNER_ID = null;

		listTage = service.querySuppByFundID(mapPage);
		if (listTage.size() > 0) {
			Map MapTage = (Map) listTage.get(0);
			OWNER_ID = (MapTage.get("FI_TAGE_ID") != null && !MapTage.get("FI_TAGE_ID").equals("")) ? MapTage.get("FI_TAGE_ID").toString() : "";
		}

		// 只需要DB保证金池，供应商电汇资金池，设备付款资金池，待处理资金池
		// 虚拟数据
		Map map1 = new HashMap();
		map1.put("OWNER_ID", OWNER_ID);
		map1.put("FUND_ID", map.get("FUND_ID"));
		map1.put("POOLNAME", "DB保证金池");
		map1.put("POOLID", "1");
		double userMoney1 = service.queryPoolMoneyDe(map1);
		map1.put("POOLUSERMONEY", service.queryPoolMoney(map1) + userMoney1);
		map1.put("dichong_money", userMoney1);
		poolList.add(map1);

		Map map2 = new HashMap();
		map2.put("OWNER_ID", OWNER_ID);
		map2.put("FUND_ID", map.get("FUND_ID"));
		map2.put("POOLNAME", "代理垫汇资金池");
		map2.put("POOLID", "2");
		double userMoney2 = service.queryPoolMoneyDe(map2);
		map2.put("POOLUSERMONEY", service.queryPoolMoney(map2) + userMoney2);
		map2.put("dichong_money", userMoney2);
		poolList.add(map2);

		Map map3 = new HashMap();
		map3.put("OWNER_ID", OWNER_ID);
		map3.put("FUND_ID", map.get("FUND_ID"));
		map3.put("POOLNAME", "设备付款资金池");
		map3.put("POOLID", "3");
		double userMoney3 = service.queryPoolMoneyDe(map3);
		map3.put("POOLUSERMONEY", service.queryPoolMoney(map3) + userMoney3);
		map3.put("dichong_money", userMoney3);
		poolList.add(map3);

		Map map4 = new HashMap();
		map4.put("OWNER_ID", OWNER_ID);
		map4.put("FUND_ID", map.get("FUND_ID"));
		map4.put("POOLNAME", "待处理资金池");
		map4.put("POOLID", "7");
		double userMoney4 = service.queryPoolMoneyDe(map4);
		map4.put("POOLUSERMONEY", service.queryPoolMoney(map4) + userMoney4);
		map4.put("dichong_money", userMoney4);
		poolList.add(map4);

		context.put("listTage", listTage);
		// 查询是否有池子存在
		mapPage.put("POOLSTATE", service.queryPoolNumber(map));
		context.put("f_data", mapPage);// 查看申请表数据
		context.put("poolList", poolList);
		return new ReplyHtml(VM.html("rentWriteVinual/to_fund_confirm_detailVinual.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销", "保存" })
	public Reply doCommitHXSheet() {
		Map<String, Object> paramMap = _getParameters();
		paramMap.put("USERCODE", Security.getUser().getCode());// 用户编号
		paramMap.put("USERNAME", Security.getUser().getName());// 用户名

		int i = 0;
		boolean flag = false;
		i = service.doUpdateFHead(paramMap);// 更新申请单数据
		if (i > 0) {
			service.doInsertAccount1(paramMap);// 添加实收明细
			if (i > 0) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-虚拟", "核销-保存", paramMap.get("USERCODE").toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销", "核销" })
	public Reply doCommitHXSheetHexiao() {
		Map<String, Object> paramMap = _getParameters();
		paramMap.put("USERCODE", Security.getUser().getCode());// 用户编号
		paramMap.put("USERNAME", Security.getUser().getName());// 用户名

		int i = 0;
		boolean flag = false;
		i = service.doUpdateFHead(paramMap);// 更新申请单数据
		if (i > 0) {
			service.doInsertAccount(paramMap);// 添加实收明细
			if (i > 0) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-虚拟", "核销-核销", paramMap.get("USERCODE").toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-非网银-虚拟核销", "核销" })
	public Reply doCheckedFund() {
		Map<String, Object> paramMap = _getParameters();
		paramMap.put("USERCODE", Security.getUser().getCode());// 用户编号
		paramMap.put("USERNAME", Security.getUser().getName());// 用户名
		int i = 0;
		boolean flag = false;

		i = service.doCheckedFund(paramMap);// 核销资金
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-虚拟", "核销", paramMap.get("USERCODE").toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代理商垫付-非网银核销" })
	public Reply doReject() {
		Map<String, Object> paramMap = _getParameters();
		paramMap.put("USERCODE", Security.getUser().getCode());// 用户编号
		int i = 0;
		boolean flag = false;

		i = service.doReject(paramMap);// 驳回
		if (i > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("租金扣划-虚拟", "驳回", paramMap.get("USERCODE").toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = {"资金管理", "租金扣划-虚拟", "代理商垫付-网银核销", "列表显示"})
	public Reply toSupperFundMg() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/tocyber_ConfirmVinual.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代理商垫付-网银核销", "列表显示"})
	public Reply toSupperFundMgData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toSupperFundMg(param));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代理商垫付-网银核销"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply toSupper_Upload() {
		Map map = _getParameters();
		if (map != null && map.get("uploadType1") != null && !map.get("uploadType1").equals("")) {
			String uploadType = map.get("uploadType1").toString();
			String bankFlag = map.get("bankFlag") != null ? map.get("bankFlag").toString() : "-1";// 判断选择的银行模版
																									// 1：建设银行
																									// 2：民生银行
			if (uploadType.equals("select"))// 导出选中项
			{
				List list = new ArrayList();
				JSONArray guaranjsonArray = JSONArray.fromObject(map.get("selectDateHidden"));
				for (Object object : guaranjsonArray) {
					Map<String, Object> m = (Map<String, Object>) object;
					list.add(m);
				}
				List listReturn = service.toSupper_Upload(list, map);

				map.put("dataList", listReturn);
				if (bankFlag.equals("1")) {
					// excel导出
					String no = new FiEbankUtil().getExportNo("供应商垫付虚拟");
					return new ReplyExcel(util.exportData(map), "headpayment" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") + no + ".xls");
				} else if (bankFlag.equals("2")) {
					// txt导出
					map.put("EXPORT_TYPE", "供应商垫付虚拟");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(), map);
					return null;
				}
			} else// 导出所有
			{
				// 根据条件查询所需要导出的数据和修改状态
				List list = service.querySupp_uplad_All(map);
				List listReturn = service.toSupper_Upload(list, map);

				map.put("dataList", listReturn);
				if (bankFlag.equals("1")) {
					// excel导出
					String no = new FiEbankUtil().getExportNo("供应商垫付虚拟");
					return new ReplyExcel(util.exportData(map), "headpayment" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") + no + ".xls");
				} else if (bankFlag.equals("2")) {
					// txt导出
					map.put("EXPORT_TYPE", "供应商垫付虚拟");
					map.put("FILE_HEAD", "DK");
					util.exportMSEbank(SkyEye.getResponse(), map);
					return null;
				}
			}
		}
		return this.toSupperFundMg();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代理商垫付-网银核销" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply destroySuppUp() {
		Map map = _getParameters();
		service.destroySuppUp(map);
		return this.toSupperFundMg();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "申请" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply HEAD_Upload() {
		Map map = _getParameters();
		return new ReplyExcel(service.HEAD_Upload(map), "租金扣划明细导出").addOp(new OpLog("资金管理", "租金扣划申请导出", "导出错误"));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代理商垫付-网银核销" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply uploadExcel1() {
		boolean flag = false;
		String msg = "";
		List<File> files = _getFile();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = _getParameters();
		try {

			// 判断选择的银行模版 1：建设银行 其他：民生银行
			if ("1".equals(m.get("bankFlag") + "")) {
				list = util.parseJSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国建设银行");
			} else {
				list = util.parseMSEbank(files.get(0));
				m.put("FI_REALITY_BANK", "中国建设银行");
			}
			// 对回执的数据进行处理
			Map mapDate = service.backFileOp(list, m);
			int SUCCESS_NUM = Integer.parseInt(mapDate.get("SUCCESS_NUM").toString());
			int ERROR_BANK_NUM = Integer.parseInt(mapDate.get("ERROR_BANK_NUM").toString());
			int ERROR_NUM = list.size() - SUCCESS_NUM - ERROR_BANK_NUM;
			if (list.size() > 0) {
				msg = "核销成功【" + SUCCESS_NUM + "】条，失败【" + ERROR_BANK_NUM + "】条，异常【" + ERROR_NUM + "】条！";
				flag = true;
			} else {
				msg = "核销失败,请检查上传文件格式！";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag, null, msg);
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "虚拟转实收", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply bank_A_TO_B_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/bank_A_TO_B_MangerVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "虚拟转实收", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Bank_A_TO_B_Page() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Bank_A_TO_B_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "虚拟转实收", "转实收" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_A_TO_B_Submit() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_A_TO_B_Submit(map);
		return this.bank_A_TO_B_Manger();
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_Fund_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/Back_Fund_MangerVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_Fund_Page() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Back_Fund_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_C_Submit() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_Back_C_Submit(map);
		int i = service.addRefundRecord(map, 1);
		System.out.println("插入退款记录条数: " + i);
		return this.query_Back_Fund_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Back_Excle() {
		Map<String, Object> param = _getParameters();

		service.pay_Back_Excle(param);
		return null;
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款记录" })
	@aDev(code = "170012", email = "wuyanfei@163.com", name = "武燕飞")
	@aAuth(type = aAuthType.USER)
	public Reply Refund_Record_Excle() {
		Map<String, Object> param = _getParameters();

		service.Refund_Record_Excle(param);
		return null;
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款-申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_Fund_SUPP_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/Back_Fund_SuppVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款-申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_Fund_SUPP_Page() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Back_Fund_SUPP_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款-申请", "提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_Submit_SUPP() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_Back_Submit_SUPP(map);
		return this.query_Back_Fund_SUPP_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款-申请" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply supp_Bank_UP() {
		Map map = this._getParameters();
		int num = service.supp_Bank_UP(map);
		boolean flag = false;
		if (num > 0) {
			flag = true;
		}
		return new ReplyAjax(flag, null, null).addOp(new OpLog("资金管理", "租金扣划-虚拟", "修改"));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_BoHui() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_Back_BoHui(map);
		return this.query_Back_Fund_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_ChongZ() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_Back_ChongZ(map);
		return this.query_Back_Fund_Manger();
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款-申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_DUN_SUPP_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/Back_DUN_SuppVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款-申请", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_DUN_SUPP_Page() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Back_DUN_SUPP_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款-申请" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply supp_Bank_DUN_UP() {
		Map map = this._getParameters();
		int num = service.supp_Bank_DUN_UP(map);
		boolean flag = false;
		if (num > 0) {
			flag = true;
		}
		return new ReplyAjax(flag, null, null).addOp(new OpLog("资金管理", "租金扣划-虚拟", "修改"));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款-申请", "提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_DUN_Submit_SUPP() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_Back_DUN_Submit_SUPP(map);
		return this.query_Back_DUN_SUPP_Manger();
	}

	// 非网银申请页面
	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_DUN_Manger() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/Back_DUN_MangerVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款", "列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply query_Back_DUN_Page() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_Back_DUN_Page(param);
		return new ReplyAjaxPage(page);
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_Back_DUN_Excle() {
		Map<String, Object> param = _getParameters();

		service.pay_Back_DUN_Excle(param);
		return null;
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_BackDUN_ChongZ() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_BackDUN_ChongZ(map);
		return this.query_Back_DUN_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_BackDUN_BoHui() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.fund_BackDUN_BoHui(map);
		return this.query_Back_DUN_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "代收违约金退款" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fund_Back_DUN_C_Submit() {
		Map map = this._getParameters();
		// 保存选中的数据插入到结算单和明细中
		service.addRefundRecord(map, 2);
		service.fund_Back_DUN_C_Submit(map);
		return this.query_Back_Fund_Manger();
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款记录", "列表" })
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aAuth(type = aAuthType.USER)
	public Reply refundRecordManagerPage() {
		Map map = this._getParameters();

		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));// 获取付款方式
		FundNotEBankService Fundservice = new FundNotEBankService();
		context.put("toGetCompany", Fundservice.toGetCompany(map));// 获取厂商
		context.put("toGetProduct", Fundservice.toGetProduct(map));// 获取租赁物类型

		map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
		map.put("USERNAME", Security.getUser().getName());// 获取登陆人

		// 通过登陆人查询供应商SUP_ID
		ManageService maservice = new ManageService();
		Map mapSupp = (Map) maservice.getSupByUserId(Security.getUser().getId());
		if (mapSupp == null || mapSupp.isEmpty()) {
			mapSupp = new HashMap();
			mapSupp.put("SUPP_ID", Security.getUser().getId());
		} else {
			mapSupp.put("SUPP_ID", mapSupp.get("SUP_ID"));
		}
		context.put("mapBank", service.querySupp_Bank(mapSupp));
		context.put("param", map);
		return new ReplyHtml(VM.html("rentWriteVinual/Refund_Record_MangerVinual.vm", context));
	}

	@aPermission(name = { "资金管理", "租金扣划-虚拟", "退款记录", "列表" })
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	@aAuth(type = aAuthType.USER)
	public Reply refundRecordManager() {
		Map<String, Object> param = _getParameters();
		Page page = service.queryRefundRecord(param);
		return new ReplyAjaxPage(page);
	}
}
