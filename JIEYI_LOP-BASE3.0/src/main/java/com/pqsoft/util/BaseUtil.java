package com.pqsoft.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.SessAttr;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class BaseUtil {

	/**
	 * 创建文件路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createDirectory(String path) {
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

	private static final String _ISSUP_ = "_ISSUP_";

	/**
	 * 获取供应商子组织架构ID(1000,1001,1002)
	 * 
	 * @return
	 */
	public static String getSupOrgChildren() {
		String ISSUP = (String) SessAttr.getAttribute(_ISSUP_);// (1000,1001,1002)
		if (ISSUP == null) {
			Map<String, Object> m = new ManageService().getSupByUserId(Security.getUser().getId());
			if (m == null || m.size() == 0) {
				SessAttr.setAttribute(_ISSUP_, "ISNOTSUP");
				return null;
			} else {
				SessAttr.setAttribute(_ISSUP_, "ISSUP");
				return getDataAuthByBranch();
			}
		} else {
			if ("ISNOTSUP".equals(ISSUP)) return null;
			return getDataAuthByBranch();
		}
	}

	private static final String _DATA_AUTH_BRANCH_ = "_DATA_AUTH_BRANCH_";

	/**
	 * 获取分支机构类型数据权限组织架构ID
	 * 
	 * @例 (1000,1001,1002)
	 * @return
	 */
	private static String getDataAuthByBranch() {
		String ORG_CHILDREN = (String) SessAttr.getAttribute(_DATA_AUTH_BRANCH_);// (1000,1001,1002)
		if (StringUtils.isBlank(ORG_CHILDREN) && Security.getUser().getRoles().size() >= 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ORG_ID", Security.getUser().getOrg().getId());
			map.put("TYPE", "5");
			List<String> list = Dao.selectList("base.util.getOrgParentByOrgId", map);
			if (list.size() > 0) {
				map.put("ORD_ID", list.get(0));
				ORG_CHILDREN = Dao.selectOne("base.util.getOrgChildById", map);
				SessAttr.setAttribute(_DATA_AUTH_BRANCH_, ORG_CHILDREN);
			} else {
				// 如果没有分支机构 则只看到登陆人所在的平台下的数据
				map.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
				map.put("ORD_ID", Dao.selectOne("base.util.queryOrgIdByPlatformId", map));
				ORG_CHILDREN = Dao.selectOne("base.util.getOrgChildById", map);
				SessAttr.setAttribute(_DATA_AUTH_BRANCH_, ORG_CHILDREN);
				// SessAttr.setAttribute(_DATA_AUTH_BRANCH_, "");
			}
		}
		return ORG_CHILDREN;
	}

	// 区域
	public static String getDataAuthByRegional() {
		String ORG_REGIONAL = "";
		if (Security.getUser().getRoles().size() >= 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ORG_ID", Security.getUser().getId());
			map.put("TYPE", "13");
			List<String> list = Dao.selectList("base.util.getOrgRegionalByOrgId", map);
			if (list.size() > 0) {
				ORG_REGIONAL = Dao.selectOne("base.util.getOrgRegionalById", map);
			} else {
				map.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
				map.put("ORD_ID", Dao.selectOne("base.util.queryOrgIdByPlatformId", map));
				ORG_REGIONAL = Dao.selectOne("base.util.getOrgChildById", map);
			}
		}
		return ORG_REGIONAL;
	}

	// 省份
	public static String getDataAuthByProvince() {
		String ORG_PROVINCE = "";
		if (Security.getUser().getRoles().size() >= 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ORG_ID", Security.getUser().getId());
			map.put("TYPE", "14");
			List<String> list = Dao.selectList("base.util.getOrgProvinceByOrgId", map);
			if (list.size() > 0) {
				ORG_PROVINCE = Dao.selectOne("base.util.getOrgProvinceById", map);
			} else {
				map.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
				map.put("ORD_ID", Dao.selectOne("base.util.queryOrgIdByPlatformId", map));
				ORG_PROVINCE = Dao.selectOne("base.util.getOrgChildById", map);
			}
		}
		return ORG_PROVINCE;
	}

	// 供应商
	public static String getDataAuthBySupplier() {
		String ORG_SUPPLIER = "";
		if (Security.getUser().getRoles().size() >= 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ORG_ID", Security.getUser().getOrg().getId());
			map.put("TYPE", "8");
			List<String> list = Dao.selectList("base.util.getOrgSupByOrgId", map);
			if (list.size() > 0) {
				map.put("ORD_ID", Security.getUser().getOrg().getId());
				ORG_SUPPLIER = Dao.selectOne("base.util.getOrgSupById", map);
			} else {
				map.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
				map.put("ORD_ID", Dao.selectOne("base.util.queryOrgIdByPlatformId", map));
				ORG_SUPPLIER = Dao.selectOne("base.util.getOrgChildById", map);
			}
		}
		return ORG_SUPPLIER;
	}

	/**
	 * 是否客户经理
	 * 
	 * @return
	 */
	public static boolean isClerk() {
		List list = Dao.selectList("User.selectSalesman", Security.getUser().getOrg().getId());
		if (Security.getUser().getRoles().size() >= 1 && list.size() > 0) { return true; }
		return false;
	}

	/**
	 * 获取数据权限接口
	 * 
	 * @param map
	 * @author King 2014年9月4日
	 */
	public static void getDataAllAuth(Map<String, Object> map) {
		if (Security.getUser().getOrg() != null) {
			if (BaseUtil.isClerk()) {// 客户经理授权
				System.out.println("+++++++++++++=================");
				map.put("SYS_CLERK_ID_", Security.getUser().getId());
				map.put("SYS_CLERK_CODE_", Security.getUser().getCode());
			} else {
				System.out.println("--------------------------");
				// 分公司或平台授权
				map.put("SYS_ORGIDS_", BaseUtil.getDataAuthByBranch());
				// 区域
//				map.put("SYS_REGIONAL", BaseUtil.getDataAuthByRegional());
//				// 区域
//				map.put("SYS_BIGAREAID", Security.getUser().getOrg().getBigAreaId());
//				// 省市
//				map.put("SYS_PROVINCE", BaseUtil.getDataAuthByProvince());
//				// 供应商
//				map.put("SYS_SUPPLIER", BaseUtil.getDataAuthBySupplier());
//				// 供应商
//				map.put("SYS_SUPPID", Security.getUser().getOrg().getSuppId());
			}
		}
		// if("".equals("")){
		//
		// }
	}

	/**
	 * 获取当前登录用户对应的供应商
	 * 
	 * @return
	 */
	public static Map<String, Object> getSup() {
		return null;
	}

	/**
	 * 获取当前登录用户对应的厂商
	 * 
	 * @return
	 */
	public static Map<String, Object> getCom() {
		return null;
	}

	/**
	 * 通过还款计划编号查询该还款计划是否在申请中或者被虚拟核销占用中（未退款未转应收）
	 * 参数：PAYLIST_CODE -还款计划编号
	 * true:正常 false:申请中 或者 虚拟核销
	 */
	public static boolean getVinualByPayListCode(String PAYLIST_CODE) {

		int num = 0;
		num = Dao.selectInt("base.util.queryFundVinual", PAYLIST_CODE);// 租金是否虚拟核销（未退款未转应收）或者在核销中
		if (num > 0) { return false; }

		num = Dao.selectInt("base.util.queryDueVinual", PAYLIST_CODE);// 违约金是否被虚拟核销（未退款未转应收）或者在核销中
		if (num > 0) { return false; }
		return true;
	}

	/**
	 * 通过还款计划编号和期次查询该期是否在申请中或者被虚拟核销占用中（未退款未转应收）
	 * 参数：PAYLIST_CODE -还款计划编号，PERIOD -期次
	 * true:正常 false:申请中 或者 虚拟核销
	 */
	public static boolean getVinualByPayListCodeAndPeriod(String PAYLIST_CODE, String PERIOD) {
		Map map = new HashMap();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		map.put("PERIOD", PERIOD);

		int num = 0;
		num = Dao.selectInt("base.util.queryFundVinualByPeriod", map);// 租金是否虚拟核销（未退款未转应收）或者在核销中
		if (num > 0) { return false; }

		num = Dao.selectInt("base.util.queryDueVinualByPeriod", map);// 违约金是否被虚拟核销（未退款未转应收）或者在核销中
		if (num > 0) { return false; }
		return true;
	}
}
