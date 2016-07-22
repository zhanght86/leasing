package com.pqsoft.fiForRed.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.common.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fiForRed.service.FiForRedService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.StringUtils;

/**
 * 资金冲红作废
 * 
 * @author King 2013-11-17
 */
public class FiForRedAction extends Action {
	private String path = "fiForRed/";
	private FiForRedService service = new FiForRedService();
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = {"资金管理","还款冲红申请", "列表显示"})
	public Reply toMgFiForRedApp() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "FiForRedMg.vm", context));
	}
    
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	// @aPermission(name = { "资金管理","冲红申请", "列表" })
	public Reply doShowMgFiForRedApp() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		return new ReplyAjaxPage(service.doShowMgFiForRedApp(param));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理","冲红申请", "申请冲红" })
	public Reply doAddFiForRedApp() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			i += service.doAddFiForRedApp(map);
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "申请冲红", list + ""));
		else
			throw new ActionException("申请失败");
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理","冲红申请", "申请作废" })
	public Reply doAddFiForRedApp_ZF() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			i += service.doAddFiForRedApp(map);
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("非网银作废", "申请作废", list + ""));
		else
			throw new ActionException("申请失败");
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "冲红审核", "列表" })
	public Reply toMgFiForRedConfirm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.put("TYPE", "1");
		param.put("STATUS", "未处理");
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "FiForRedConfirm.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	// @aPermission(name = { "资金管理", "冲红审核",, "列表" })
	public Reply doShowMgFiForRedConfirm() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		if (StringUtils.isBlank(param.get("TYPE"))) {
			// 作废
			param.put("TYPE", "1");
		}
		if (StringUtils.isBlank(param.get("STATUS"))) {
			// 未处理
			param.put("STATUS", "未处理");
		}
		return new ReplyAjaxPage(service.doShowMgFiForRedConfirm(param));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "冲红审核", "驳回" })
	public Reply doUpdateFiForRedConfirmNotPass() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		StringBuffer sb = new StringBuffer();
		try {
			for (Map<String, Object> map : list) {
				service.doUpdatefiForRedConfirmNotPass(map);
				sb.append(map.get("ID")).append(",");
			}
			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "驳回", sb + ""));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActionException("驳回失败");
		}
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "冲红审核", "确认冲红作废" })
	public Reply doUpdateFiForRedConfirmPass() {
		Map<String, Object> param = _getParameters();
		try {
			StringBuffer sb = new StringBuffer();
			String TYPE = param.get("TYPE") + "";
			if (TYPE.equals("1")) {
				// 冲红
				List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
				System.out.println(list+"---------list----------");
				for (Map<String, Object> map : list) {
					sb.append("'").append(map.get("ID")).append("'").append(",");
				}
				if (!StringUtils.isBlank(sb) && sb.length() > 1) {
					String idarry = sb.substring(0, sb.length() - 1);
					service.doUpdateFiForRedConfirmPass(idarry);
				}
			} else {
				// 作废
				List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
				List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
				System.out.println(list+"---------list----------");
				for (Map<String, Object> map : list) {
					Map<String,Object> map2 = new HashMap<String, Object>();
					map2.put("ID", map.get("ID"));
					map2.put("RECEIVE_MONEY", map.get("RECEIVE_MONEY"));
					map2.put("FI_FUND_ID", map.get("FI_FUND_ID"));
					list2.add(map2);
				}
				System.out.println(list2+"-----------list2---------------");
				if(list2.size()>0){
					service.doUpdateFiForRedConfirmPass_ZF(list2);
				}
			}

			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "确认撤销", sb + ""));
		} catch (Exception e) {
			throw new ActionException("确认撤销失败",e);
		}
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "申请", "列表" })
	public Reply toMgFiForRedWebApp() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("bankList", new DataDictionaryMemcached().get("核销银行"));
		return new ReplyHtml(VM.html(path + "fiForRedWebMg.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "申请", "查询" })
	public Reply doShowMgFiForRedWebApp() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		param.put("STATUS", "0,3");
		return new ReplyAjaxPage(service.doShowMgFiForRedWebApp(param));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "申请", "新增" })
	public Reply doAddFiForRedWebApp() {
		Map<String, Object> param = _getParametersIO(null, null, null);
		int i = service.doAddFiForRedWebApp(param);
		if (i > 0) {
			return new ReplyAjax().addOp(new OpLog("网银冲红作废", "保存", param + ""));
		} else
			throw new ActionException("新增失败");
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "申请", "提交" })
	public Reply doUpdateFiForRedWebAppSubmit() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			map.put("TYPE", param.get("TYPE"));
			i += service.doUpdateFiForRedWebAppSubmit(map);
		}
		if (i == list.size()) {
			return new ReplyAjax().addOp(new OpLog("网银冲红作废-申请", "提交", param + ""));
		} else
			throw new ActionException("网银冲红作废申请成功条数不匹配，请确认");
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "申请", "删除" })
	public Reply doDeleteFiForRedWebApp() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			String str = map.get("ID").toString();
			i += service.doDeleteFiForRedWebApp(str);
		}
		if (i > 0)
			return new ReplyAjax().addOp(new OpLog("网银冲红作废-申请", "删除", param + ""));
		else
			throw new ActionException("删除申请数据条数条数不匹配");
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "审核", "列表" })
	public Reply toMgFiForRedWebConfirm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "fiForRedWebConfirm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "审核", "查询" })
	public Reply doMgFiForRedWebConfirm() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		if (param.get("STATUS") == null || "".equals(param.get("STATUS") == null ? "" : param.get("STATUS").toString())) {
			param.put("STATUS", "1");
		}
		return new ReplyAjaxPage(service.doShowMgFiForRedWebApp(param));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "审核", "驳回" })
	public Reply doUpdateRedWebFormApp() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			map.put("STATUS", "3");
			i += service.doUpdateFiForRedWebAppSubmit(map);
		}
		if (i > 0)
			return new ReplyAjax().addOp(new OpLog("网银冲红作废-审核", "驳回", param + ""));
		else
			throw new ActionException("驳回数据条数不匹配,请联系管理员");
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "网银冲红作废", "审核", "冲红作废确认" })
	public Reply doUpdateFiRedCheckPass() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		JSONArray listFile = JSONArray.fromObject(param.get("selectData"));
		Map<String, Object> mapHead = new HashMap<String, Object>();
		FiEbankUtil util = new FiEbankUtil();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < listFile.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) listFile.get(i);
			if (map != null && map.get("FILE_NAME") != null && !map.get("FILE_NAME").equals("")) {
				String FILE_NAME = map.get("FILE_NAME").toString();
				String bankFlag = FILE_NAME.substring(FILE_NAME.length() - 3, FILE_NAME.length());
				List list = new ArrayList();
				if (bankFlag.toUpperCase().equals("TXT")) {
					try {
						list = util.parseMSEbank(new File(FILE_NAME));// 民生银行（txt）
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					try {
						list = util.parseJSEbank(new File(FILE_NAME));// 建设银行（excel）
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int num = 0; num < list.size(); num++) {
					Map mapFile = (Map) list.get(num);
					if (mapFile != null && mapFile.get("deducted_id") != null && !mapFile.get("deducted_id").equals("")) {
						String HEAD_IDORPROJ_CODE = mapFile.get("deducted_id").toString();// 首期款为项目CODE，租金为ID
						// 判断如果是数字类型 内容为供应商垫付付款单id
						if (!HEAD_IDORPROJ_CODE.matches("[0-9]+")) {
							// 判断如果包含","内容为租金或违约金付款单 还款计划 期初 类型 2租金 5违约金
							if (HEAD_IDORPROJ_CODE.indexOf(",") != -1) {
								String[] str = HEAD_IDORPROJ_CODE.split(",");
								if (str.length == 3) {
									mapHead.put("PAY_CODE", str[0]);
									mapHead.put("PERIOD", str[1]);
									if ("2".equals(str[2])) {
										mapHead.put("D_STATUS", "1");
									} else {
										mapHead.put("D_STATUS", "2");
									}
									HEAD_IDORPROJ_CODE = service.doSelectHeadIDByCodes(mapHead);
								}
								// 判断如果不包含","内容为首付款 项目编号
							} else {
								mapHead.put("PRO_CODE", HEAD_IDORPROJ_CODE);
								HEAD_IDORPROJ_CODE = service.doSelectHeadIDByProCode(mapHead);
							}
						}
						sb.append("'").append(HEAD_IDORPROJ_CODE).append("'").append(",");
					}
				}

				if (!StringUtils.isBlank(sb) && sb.length() > 1) {
					String idarry = sb.substring(0, sb.length() - 1);
					if ("冲红".equals(map.get("TYPE"))) {
						service.doUpdateFiForRedConfirmPassWY(idarry);
					} else {
						service.doUpdateFiForRedConfirmPassWY_ZF(idarry);
					}
					// 更新状态为已核销
					map.put("STATUS", "2");
					service.doUpdateFiForRedWebAppSubmit(map);
				}
			}
		}

		return new ReplyHtml(VM.html(path + "fiForRedWebConfirm.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply doDownFile() {
		Map<String, Object> param = _getParameters();
		return new ReplyFile(new File(param.get("FILE_PATH") + ""), param.get("FILE_NAME") + "");
	}

	/**
	 * 判断当期是否有违约金核销，但却未申请冲红作废
	 * 
	 * @return
	 * @author:King 2014-1-9
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply checkDunHX() {
		Map<String, Object> map = _getParameters();
		boolean flag = service.checkDunHX(map.get("PAY_CODE"), map.get("PERIOD"));
		return new ReplyAjax(flag, "");
	}
	/*
	 * 冲红重构 -- 还款红冲 页面
	 * */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = {"资金管理","还款冲红申请", "列表显示"})
	public Reply toMgFiForRedApp2() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		//return new ReplyHtml(VM.html(path + "FiForRedMg.vm", context));
		return new ReplyHtml(VM.html(path+"FiForRedMg2.vm", context));
	}
	/*
	 * 冲红重构 -- 还款红冲 列表
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	// @aPermission(name = { "资金管理","冲红申请", "列表" })
	public Reply doShowMgFiForRedApp2() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		return new ReplyAjaxPage(service.doShowMgFiForRedApp2(param));
	}
	/*
	 * 冲红重构 -- 还款红冲 申请冲红
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理","冲红申请", "申请冲红" })
	public Reply doAddFiForRedApp2() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			i += service.doAddFiForRedApp2(map);
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "申请冲红", list + ""));
		else
			throw new ActionException("申请失败");
	}
	/*
	 * 冲红重构 -- 还款红冲 申请作废
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理","冲红申请", "申请作废" })
	public Reply doAddFiForRedApp_ZF2() {
		System.out.println("--------");
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		int i = 0;
		for (Map<String, Object> map : list) {
			System.out.println("--------");
			i += service.doAddFiForRedApp2(map);
		}
		if (i == list.size())
			return new ReplyAjax().addOp(new OpLog("非网银作废", "申请作废", list + ""));
		else
			throw new ActionException("申请失败");
	}
	/*
	 * 冲红重构 -- 冲红审核页面
	 * */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "冲红审核", "列表" })
	public Reply toMgFiForRedConfirm2() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.put("TYPE", "2");
		param.put("STATUS", "未处理");
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "FiForRedConfirm2.vm", context));
	}
	/*
	 * 冲红重构 -- 冲红审核 列表
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	// @aPermission(name = { "资金管理", "冲红审核",, "列表" })
	public Reply doShowMgFiForRedConfirm2() {
		Map<String, Object> param = _getParameters();
		System.out.println(param+"-----------");
		if (StringUtils.isBlank(param.get("TYPE"))) {
			// 作废
			param.put("TYPE", "2");
		}
		if (StringUtils.isBlank(param.get("STATUS"))) {
			// 未处理
			param.put("STATUS", "未处理");
		}
		return new ReplyAjaxPage(service.doShowMgFiForRedConfirm2(param));
	}
	/*
	 * 冲红重构 -- 确认冲红作废
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "冲红审核", "确认冲红作废" })
	public Reply doUpdateFiForRedConfirmPass2() {
		Map<String, Object> param = _getParameters();
		try {
			StringBuffer sb = new StringBuffer();
			String TYPE = param.get("TYPE") + "";
			if (TYPE.equals("1")) {
				// 冲红
				List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
				service.doUpdateFiForRedConfirmPassNew(list);
				/*System.out.println(list+"---------list----------");
				for (Map<String, Object> map : list) {
					sb.append("'").append(map.get("ID")).append("'").append(",");
				}
				if (!StringUtils.isBlank(sb) && sb.length() > 1) {
					String idarry = sb.substring(0, sb.length() - 1);
					service.doUpdateFiForRedConfirmPass(idarry);
				}*/
			} else {
				// 作废
				List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
				service.doUpdateFiForRedConfirmPass_ZFNew(list);
			}

			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "确认撤销", sb + ""));
		} catch (Exception e) {
			throw new ActionException("确认撤销失败",e);
		}
	}
	/*
	 * 冲红重构 -- 驳回
	 * */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", name = "肖云飞", email = "1226781445@qq.com")
	@aPermission(name = { "资金管理", "冲红审核", "驳回" })
	public Reply doUpdateFiForRedHeadConfirmNotPass() {
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> list = JSONArray.fromObject(param.get("param"));
		System.out.println(list+"-----------------------");
		StringBuffer sb = new StringBuffer();
		try {
			for (Map<String, Object> map : list) {
				service.doUpdatefiForRedHeadConfirmNotPass(map);
				sb.append(map.get("ID")).append(",");
			}
			return new ReplyAjax().addOp(new OpLog("非网银冲红作废", "驳回", sb + ""));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActionException("驳回失败");
		}
	}
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = {"资金管理","重签冲红申请", "列表显示"})
	public Reply toMgFiForRedAppCW() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "FiForRedMgCW.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply doShowMgFiForRedAppCW() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		return new ReplyAjaxPage(service.doShowMgFiForRedApp(param));
	}
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	@aPermission(name = { "资金管理", "重签冲红审核", "列表" })
	public Reply toMgFiForRedConfirmCW() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param.put("TYPE", "1");
		param.put("STATUS", "未处理");
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "FiForRedConfirmCW.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	// @aPermission(name = { "资金管理", "冲红审核",, "列表" })
	public Reply doShowMgFiForRedConfirmCW() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		if (StringUtils.isBlank(param.get("TYPE"))) {
			// 作废
			param.put("TYPE", "1");
		}
		if (StringUtils.isBlank(param.get("STATUS"))) {
			// 未处理
			param.put("STATUS", "未处理");
		}
		return new ReplyAjaxPage(service.doShowMgFiForRedConfirm(param));
	}
}
