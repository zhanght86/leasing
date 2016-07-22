package com.pqsoft.customers.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pqsoft.analysisBySynthesis.service.IndustryService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.customModule.service.CustomModuleService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.log.LogUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomersService { 

	private final String xmlPath = "customers.";
	private final String xmlPathcust = "custTel.";
	

	/**
	 * 客户管理分页查询 findCustomers
	 * 
	 * @date 2013-8-28 下午06:20:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page findCustomers(Map<String, Object> m) {
		// 判断供应商登录只能看到当前供应商数据
		ManageService mgService = new ManageService();
		Map map = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if (map != null && map.get("SUP_ID") != null) {
			m.put("SUPP_ID", map.get("SUP_ID"));
		}
		BaseUtil.getDataAllAuth(m);
		return PageUtil.getPage(m, xmlPath + "findCustomers", xmlPath
				+ "findCustomersCount");
	}

	public Page findCustomersBaseInfo(Map<String, Object> m) {
		// 判断供应商登录只能看到当前供应商数据
		ManageService mgService = new ManageService();
		Map map = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if (map != null && map.get("SUP_ID") != null) {
			m.put("SUPP_ID", map.get("SUP_ID"));
		}

		return PageUtil.getPage(m, xmlPath + "findCustomersBaseInfo", xmlPath
				+ "findCustomersBaseInfoCount");
	}

	public List<Map<String, Object>> selectCustData(Map<String, Object> param) {

		// 判断供应商登录只能看到当前供应商数据
		ManageService mgService = new ManageService();
		Map<String, Object> map = (Map<String, Object>) mgService
				.getSupByUserId(Security.getUser().getId());
		if (map != null && map.get("SUP_ID") != null) {
			param.put("SUPP_ID", map.get("SUP_ID"));
		}

		return Dao.selectList(xmlPath + "selectCustData", param);
	}
	
	public List<Map<String, Object>> selectCustDataNew(Map<String, Object> param) {
		return Dao.selectList(xmlPath + "selectCustDataNew", param);
	}

	/**
	 * IsCust 判断新添加客户是否存在
	 * 
	 * @date 2013-8-29 下午06:01:24
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int IsCust(Map<String, Object> map) {

		ManageService ms = new ManageService();
		Map<String, Object> supp = (Map<String, Object>) ms
				.getSupByUserId(Security.getUser().getId());

		if (supp != null && supp.get("SUP_ID") != null) {
			map.put("SUPP_ID", supp.get("SUP_ID"));
			map.put("ORG_ID", supp.get("ORG_BMID"));
		}
		return Dao.selectInt(xmlPath + "IsCust", map);
	}

	/**
	 * doAddCust 添加客户
	 * 
	 * @date 2013-8-29 下午07:31:25
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doAddCust(Map<String, Object> map) {
		ManageService ms = new ManageService();
		Map<String, Object> supp = (Map<String, Object>) ms
				.getSupByUserId(Security.getUser().getId());
		if(Security.getUser().getOrg()!=null){
			map.put("ORG_ID", Security.getUser().getOrg().getId());
			if (supp != null && supp.get("SUP_ID") != null) {
				map.put("SUPP_ID", supp.get("SUP_ID"));
			}
		}
		
		if (map.containsKey("CREDIT_ID") && !map.get("CREDIT_ID").equals("")) {
			map.put("JUDGE_GUARANTOR", "1");
			Dao.insert("creditGuarantor.addGuarantor", map);// 添加担保人---付玉龙
		}

		return Dao.insert(xmlPath + "doAddCust", map);
	}

	// 保存该客户第一手历史资料
	public void doAddCustBaseINfo(Map<String, Object> map) {
		if (map != null && map.get("overMethod") != null
				&& !map.get("overMethod").equals("")
				&& map.get("overMethod").equals("overMethod")) {
			map.put("CUST_FLAG", "4");
		} else {
			map.put("CUST_FLAG", "1");
		}
		Dao.insert(xmlPath + "doAddCustBaseINfo", map);
	}

	/**
	 * findCustDetail 客户详细信息
	 * 
	 * @date 2013-8-31 下午05:36:53
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findCustDetail(Map<String, Object> map) {
		Map<String, Object> m = Dao.selectOne(xmlPath + "findCustDetail", map);
		if (m != null) {
			Dao.getClobTypeInfo2(m, "IDCARD_PHOTO");
		}
		return m;
	}
	
	public int updateProjectCust(Map<String, Object> map){
		return Dao.update(xmlPath+"updateProjectCust",map);
	}
	
	public int updateProjectGtczrCust(Map<String, Object> map){
		return Dao.update(xmlPath+"updateProjectGtczrCust",map);
	}
	
	public int toUpdateDbrCustInfoNew(Map<String, Object> map){
		return Dao.update(xmlPath+"toUpdateDbrCustInfoNew",map);
	}

	public Object findCustDetailXZS(Map<String, Object> map) {
		Map<String, Object> m = Dao
				.selectOne(xmlPath + "queryXZSCustInfo", map);
		if (m != null) {
			Dao.getClobTypeInfo2(m, "IDCARD_PHOTO");
		}
		return m;
	}

	/**
	 * doUpdateCust 修改客户信息
	 * 
	 * @date 2013-9-3 上午11:33:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateCust(Map<String, Object> map) {
		return Dao.update(xmlPath + "updateCust", map);
	}

	/**
	 * 验证法人是否存在注册银行信息
	 * 
	 * @param CLIENT_ID
	 * @return
	 * @author:King 2014-2-21
	 */
	public int checkBankZC(Object CLIENT_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_ID", CLIENT_ID);
		return Dao.selectInt(xmlPath + "checkBankZC", map);
	}

	/**
	 * checedCustToPro 判断客户是否有项目
	 * 
	 * @date 2013-9-3 上午11:27:46
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int checedCustToPro(Map<String, Object> map) {
		return Dao.selectInt(xmlPath + "checCustToPro", map);
	}

	/**
	 * doAddCustSpoust 添加客户配偶
	 * 
	 * @date 2013-8-30 下午05:12:39
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doAddCustSpoust(Map<String, Object> map) {
		String SPOUST_ID = Dao.getSequence("SEQ_FIL_CUST_SPOUST");
		map.put("SPOUST_ID", SPOUST_ID);
		return Dao.insert(xmlPath + "addSpoust", map);
	}

	/**
	 * 修改配偶 doUpdateSpoust
	 * 
	 * @date 2013-9-11 下午02:30:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateSpoust(Map<String, Object> map) {
		System.out.println("====================" + map);
		return Dao.update(xmlPath + "updateSpoust", map);
	}

	/**
	 * findCustLinkInfo 客户配偶
	 * 
	 * @date 2013-8-31 下午05:29:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findCustLinkInfo(Map<String, Object> map) {
		Map<String, Object> infoDetail = new HashMap<String, Object>();
		String type = map.get("TYPE") != null ? map.get("TYPE").toString() : "";
		if (("NP").equals(type)) { // 自然人关联信息
			// 客户配偶
			Map<String, Object> spoustDet = Dao.selectOne(xmlPath
					+ "findSpoustDetail", map);
			if (spoustDet != null) {
				Dao.getClobTypeInfo2(spoustDet, "IDCARD_PHOTO");
			}
			infoDetail.put("spoustDet", spoustDet);

		}
		return infoDetail;
	}

	public Object findCustLinkInfoXZS(Map<String, Object> map) {
		Map<String, Object> infoDetail = new HashMap<String, Object>();
		String type = map.get("TYPE") != null ? map.get("TYPE").toString() : "";
		if (("NP").equals(type)) { // 自然人关联信息
			// 客户配偶
			Map<String, Object> spoustDet = Dao.selectOne(xmlPath
					+ "findSpoustDetailXZS", map);
			if (spoustDet != null) {
				Dao.getClobTypeInfo2(spoustDet, "IDCARD_PHOTO");
			}
			infoDetail.put("spoustDet", spoustDet);

		}
		return infoDetail;
	}

	/**************************************************** 客户关联 *************************************************************************************/
	/**
	 * 客户联系人信息： 子女， 社会关系， 企业关联 getCustLink
	 * 
	 * @date 2013-9-2 下午12:03:57
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getCustLink(Map<String, Object> map) {
		Page page = new Page(map);
		if (map.get("tab") != null) {
			if (map.get("tab").equals("child")) {
				map.put("TYPE_LINK", 0);
			} else if (map.get("tab").equals("solialRe")) {
				map.put("TYPE_LINK", 1);
			} else {
				map.put("TYPE_LINK", 2);
			}

		}
		List<Map<String, Object>> linkL = Dao.selectList(xmlPath
				+ "findLinkPDetail", map);
		JSONArray array = new JSONArray();

		// 判断自然人，法人关系类型
		String reltation_type = "";
		List<Map<String, Object>> reltation_typeL = null;
		// 自然人，与客户关系
		if (map.get("TYPE").equals("NP")) {
			reltation_type = "与客户关系N";
			reltation_typeL = (List) new DataDictionaryMemcached()
					.get(reltation_type);
		} else {// 法人
			reltation_type = "与客户关系L";
			reltation_typeL = (List) new DataDictionaryMemcached()
					.get(reltation_type);
		}

		for (int i = 0; i < linkL.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) linkL.get(i);
			json.put("LINK_ID", m.get("LINK_ID"));
			json.put("LINK_NAME", m.get("LINK_NAME"));
			if (m.get("LINK_SEX") != null) {
				if (m.get("LINK_SEX").equals("0")) {
					json.put("LINK_SEX", "男");
				} else {
					json.put("LINK_SEX", "女");
				}
			}
			json.put("LINK_BIRTHDAY", m.get("LINK_BIRTHDAY"));
			json.put("LINK_IDCARD", m.get("LINK_IDCARD"));
			json.put("LINK_WORK_UNITS", m.get("LINK_WORK_UNITS"));
			if (m.get("LINK_RELATION2CUST") != null) {
				for (int j = 0; j < reltation_typeL.size(); j++) {
					Map<String, Object> m1 = reltation_typeL.get(j);
					if (m1.get("CODE").equals(
							m.get("LINK_RELATION2CUST").toString())) {
						json.put("LINK_RELATION2CUST", m1.get("FLAG"));
					}
				}
			}
			json.put("LINK_MOBILE", m.get("LINK_MOBILE"));
			json.put("LINK_PHONE", m.get("LINK_PHONE"));

			json.put("LINK_WORK_ADDRESS", m.get("LINK_WORK_ADDRESS"));
			json.put("LEGALPERSON", m.get("LEGALPERSON"));
			json.put("REMARK", m.get("REMARK"));
			json.put("PHYSICAL_STATE", m.get("PHYSICAL_STATE"));
			json.put("CLIENT_ID", m.get("CLIENT_ID"));
			array.add(json);
		}
		page.addDate(array,
				Dao.selectInt(xmlPath + "findLinkPDetailCount", map));
		return page;
	}

	/**
	 * 添加客户关联关系 doInsertLink
	 * 
	 * @date 2013-9-4 下午03:18:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertLink(Map<String, Object> m) {
		String ID = Dao.getSequence("SEQ_FIL_CUST_LINKMAN");
		m.put("ID", ID);
		m.put("TYPE", m.get("tab"));
		return Dao.insert(xmlPath + "doInsertLink", m);
	}

	/**
	 * 查看客户关系 getCustRelation
	 * 
	 * @date 2013-9-5 下午02:37:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getCustRelation(Map<String, Object> map) {
		return Dao.selectOne(xmlPath + "findRelation", map);
	}

	/**
	 * doUpdateCustInfo 修改客户关系
	 * 
	 * @date 2013-9-2 下午06:05:12
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateCustInfo(Map<String, Object> map) {
		return Dao.update(xmlPath + "updateRelation", map);
	}

	/*********************************************************************** 开户行 ***************************************************************/
	/**
	 * getBankDetail 法人开户行
	 * 
	 * @date 2013-9-2 下午12:15:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getBankDetail(Map<String, Object> map) {
		Page page = new Page(map);
		List<Map<String, Object>> bank = Dao.selectList(xmlPath
				+ "findBankDetail", map);// 法人开户行
		List<Object> bank_type = new DataDictionaryMemcached().get("开户行账号类型");
		;//
		List<Object> head_office = new DataDictionaryMemcached().get("开户行所属总行");
		;//
		
		Map BPMap=new HashMap();
		if(map !=null && map.get("PROJECT_ID")!=null && !map.get("PROJECT_ID").equals("")){
			String PROJECT_ID=map.get("PROJECT_ID")+"";
			//查询该项目所挂的BANK_ID
			BPMap=Dao.selectOne("project.queryBankByProjectId",PROJECT_ID);
		}
		
		JSONArray array = new JSONArray();
		for (int i = 0; i < bank.size(); i++) {
			JSONObject json = new JSONObject();
			Map<String, Object> m = (Map<String, Object>) bank.get(i);
			json.put("BANK_NAME", m.get("BANK_NAME"));
			json.put("BANK_ACCOUNT", m.get("BANK_ACCOUNT"));
			json.put("BANK_ADDRESS", m.get("BANK_ADDRESS"));
			json.put("TYPE", m.get("TYPE"));
			json.put("REMARK", m.get("REMARK"));
			json.put("CLIENT_ID", m.get("CLIENT_ID"));
			json.put("CO_ID", m.get("CO_ID"));
			json.put("BANK_CUSTNAME", m.get("BANK_CUSTNAME"));
			json.put("HEAD_OFFICE", m.get("HEAD_OFFICE"));
			json.put("BRANCH", m.get("BRANCH"));
			json.put("CITY_NAME", m.get("CITY_NAME"));
			json.put("PROVINCE_NAME", m.get("PROVINCE_NAME"));
			if(BPMap !=null && BPMap.get("BANK_ID")!=null && !BPMap.get("BANK_ID").equals("")){
				if(BPMap.get("BANK_ID").toString().equals(m.get("CO_ID").toString())){
					json.put("BANK_P_STATUS", "代扣卡");
				}else{
					json.put("BANK_P_STATUS", "非代扣卡");
				}
			}else{
				json.put("BANK_P_STATUS", "非代扣卡");
			}
			// json.put("HEAD_OFFICE", m.get("HEAD_OFFICE"));
			if (m.get("FLAG") != null) {
				for (int a = 0; a < bank_type.size(); a++) {
					Map<String, Object> b_flag = (Map<String, Object>) bank_type
							.get(a);
					if (m.get("FLAG").toString()
							.equals(b_flag.get("CODE").toString())) {
						json.put("FLAG", b_flag.get("FLAG"));
					}
				}
			}
			array.add(json);
		}
		page.addDate(array, Dao.selectInt(xmlPath + "findBankDetailCount", map));
		return page;
	}

	/**
	 * doAddOpenBank 添加法人开户行
	 * 
	 * @date 2013-8-31 上午11:17:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doAddOpenBank(Map<String, Object> map) {
		String BANK_ID = Dao.getSequence("SEQ_FIL_CUST_OPENINGBANK");
		map.put("BANK_ID", BANK_ID);
		if(map.get("PROJECT_ID") !=null && !map.get("PROJECT_ID").equals("")){
			map.put("CO_ID", BANK_ID);
			Dao.update(xmlPath+"updateBankProject",map);
		}
		return Dao.insert(xmlPath + "addBlank", map);
	}

	/**
	 * 根据银行id修改银行信息 findBankDetailByid
	 * 
	 * @date 2013-11-7 下午04:17:11
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findBankDetailByid(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "findBankDetailByid", map);
	}

	/**
	 * doUpdateBank 修改开户行
	 * 
	 * @date 2013-9-4 下午12:03:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateBank(Map<String, Object> map) {
		map.put("USERCODE", Security.getUser().getCode());
		if(map.get("PROJECT_ID") !=null && !map.get("PROJECT_ID").equals("")){
			Dao.update(xmlPath+"updateBankProject",map);
		}
		return Dao.update(xmlPath + "updateBank", map);
	}
	
	/**
	 * 校验开户行号
	 * @param map
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月31日,下午6:30:09
	 */
	public int findBank(Map<String,Object> map){
		return Dao.selectInt(xmlPath+"findBank", map);
	}

	/********************************************************************* 删除客户信息 ***********************************************************************/

	/**
	 * 删除信息 delCustInfo
	 * 
	 * @date 2013-8-31 下午05:01:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delCustInfo(Map<String, Object> map) {
		int flag = 0;
		try {
			flag = Dao.delete(xmlPath + "delCustWor", map);// 从业经验
			flag = Dao.delete(xmlPath + "delCustPar", map);// 合作伙伴
			flag = Dao.delete(xmlPath + "delCustI", map);//
			flag = Dao.delete(xmlPath + "delCustH", map);// 企业股东及份额
			flag = Dao.delete(xmlPath + "delCustCom", map);// 企业关联
			flag = Dao.delete(xmlPath + "delCustO", map);// 开户行
			flag = Dao.delete(xmlPath + "delCustL", map);// 客户关系
			flag = Dao.delete(xmlPath + "delCustSp", map);// 配偶
			flag = Dao.delete(xmlPath + "delCust", map);// 客户信息
			//多个担保人执行删除时,关联表中有则删除
			flag=(Dao.delete("creditGuarantor.delGuarantor", map)>0?flag:flag);
		} catch (Exception ex) {
			flag = 0;
		}
		return flag;
	}

	/**
	 * 删除子女 delCustRelation
	 * 
	 * @date 2013-9-4 下午05:56:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delCustRelation(Map<String, Object> map) {
		return Dao.delete(xmlPath + "cancellationCustL", map);
	}

	/**
	 * 删除开户行 delOpenBank
	 * 
	 * @date 2013-9-10 下午07:21:17
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delOpenBank(Map<String, Object> map) {
		return Dao.delete(xmlPath + "delCustO", map);
	}
	
	public int updateBankProject(Map<String, Object> map) {
		return Dao.delete(xmlPath + "updateBankProject", map);
	}

	/*************************************************************** 承租人族谱 *******************************************************************/
	/**
	 * 根据客户id获取客户名称 getClient
	 * 
	 * @date 2013-9-9 下午06:49:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getClient(Map<String, Object> map) {
		return Dao.selectOne(xmlPath + "getClient", map);
	}

	/**
	 * 根据共同承租人id获取客户id
	 * @author xgm 2015年4月1日
	 */
	public Map<String, Object> getClient1(Map<String, Object> map)
	{
		return Dao.selectOne(xmlPath+"getClient1",map);
	}
	/**
	 * 获取配偶信息，根据客户id getSpoust
	 * 
	 * @date 2013-9-9 下午06:50:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getSpoust(Map<String, Object> map) {
		return Dao.selectOne(xmlPath + "getSpoust", map);
	}

	/**
	 * 获取客户联系人 getLinkMan
	 * 
	 * @date 2013-9-9 下午06:51:49
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getLinkMan(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "getLinkMan", map);
	}

	/**
	 * 获取客户企业团队信息，根据客户id getCompanyTeam
	 * 
	 * @date 2013-9-9 下午06:53:25
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getCompanyTeam(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "getCompanyTeam", map);
	}

	/**
	 * 获取客户对外关联信息 getLinkToOut
	 * 
	 * @date 2013-9-9 下午06:55:08
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getLinkToOut(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "getLinkToOut", map);
	}

	/**
	 * 电子相册管理页列表分页
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-30 上午09:40:05
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath + "getPageList", xmlPath
				+ "getPageCount");
	}

	/**
	 * 获取省 getProvince
	 * 
	 * @date 2013-10-24 下午08:58:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getProvince() {
		return Dao.selectList(xmlPath + "getProvince");
	}

	public Object getProvince1() {
		return Dao.selectList(xmlPath + "getProvince1");
	}

	/*************************************************************** 客户资料管理-公用方法 *******************************************************************/
	/**
	 * 
	 * findCustDataType：用户相关配置信息 1：list 客户类型 2：id_typeL 证件类型 3：com_typeL 公司性质 4:
	 * nationL 民族 5: register 本地户籍 6： seniority 纳税资质 7： situation 纳税情况 8：
	 * marital_status 婚姻状况
	 * 
	 * @date 2013-8-29 下午05:23:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object findCustDataType() {
		Map<String, Object> type = new HashMap<String, Object>();
		// 客户类型判断
		String TYPE = "客户类型";
		List<Object> list = (List) new DataDictionaryMemcached().get(TYPE);
		type.put("list", list);

		// 证件类型
		String id_type = "证件类型";
		List<Object> id_typeL = (List) new DataDictionaryMemcached()
				.get(id_type);
		type.put("id_typeL", id_typeL);

		// 公司性质
		String com_type = "公司性质";
		List<Object> com_typeL = (List) new DataDictionaryMemcached()
				.get(com_type);
		type.put("com_typeL", com_typeL);

		// 民族
		List<Object> nationL = (List) new DataDictionaryMemcached().get("民族");
		type.put("nationL", nationL);

		// 本地户籍
		List<Object> register = (List) new DataDictionaryMemcached()
				.get("本地户籍");
		type.put("register", register);

		// 纳税资质
		List<Object> seniority = (List) new DataDictionaryMemcached()
				.get("纳税资质");
		type.put("seniority", seniority);

		// 纳税情况
		List<Object> situation = (List) new DataDictionaryMemcached()
				.get("纳税情况");
		type.put("situation", situation);

		// 婚姻状况
		List<Object> marital_status = (List) new DataDictionaryMemcached()
				.get("婚姻状况");
		type.put("marital_status", marital_status);

		// 文化程度
		List<Object> degree_edu = (List) new DataDictionaryMemcached()
				.get("文化程度");
		type.put("degree_edu", degree_edu);

		// 从事职业
		List<Object> profession = (List) new DataDictionaryMemcached()
				.get("职务");
		type.put("profession", profession);

		// 行业分类
		List<Object> INDUSTRY_FICATION_List = (List) new IndustryService().toFindIndustryMemcache() ;
		type.put("INDUSTRY_FICATION_List", INDUSTRY_FICATION_List);

		// 企业规模
		List<Object> SCALE_ENTERPRISE_List = (List) new DataDictionaryMemcached()
				.get("企业规模");
		type.put("SCALE_ENTERPRISE_List", SCALE_ENTERPRISE_List);

		// 兴趣爱好
		List<Object> XQAH_List = (List) new DataDictionaryMemcached()
				.get("兴趣爱好");
		type.put("XQAH_List", XQAH_List);
		// 性格
		List<Object> XG_List = (List) new DataDictionaryMemcached().get("性格");
		type.put("XG_List", XG_List);
		// 身体状态
		List<Object> STZT_List = (List) new DataDictionaryMemcached()
				.get("身体状态");
		type.put("STZT_List", STZT_List);
		// 客户来源
		List<Object> CUST_SOURCE = (List) new DataDictionaryMemcached()
				.get("客户来源");
		type.put("CUST_SOURCE", CUST_SOURCE);

		// 客户来源为“线下-渠道合作”时的子项
		List<?> offlineChannel = new DataDictionaryMemcached().get("线下-渠道合作");
		type.put("offlineChannel", offlineChannel);
		return type;
	}

	/**
	 * 自然人与客户关系 relationToCust
	 * 
	 * @date 2013-9-4 下午06:20:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object relationToCust() {
		return new DataDictionaryMemcached().get("与客户关系N");
	}

	/**
	 * 根据省的id获取市的数据 getCity
	 * 
	 * @date 2013-10-24 下午08:59:32
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getCity(Map<String, Object> map) {
		return Dao.selectList(xmlPath + "getCity", map);
	}

	/**
	 * 根据省id获取省的编号 getProCode
	 * 
	 * @date 2013-10-25 上午11:00:02
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getProCode(Map<String, Object> map) {
		return Dao.selectOne(xmlPath + "getProCode", map);
	}

	public Map<String, Object> selectFinanceData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "selectFinanceData", param);
	}

	public Map<String, Object> getCustCode(String ID) {
		return Dao.selectOne(xmlPath + "getCustCode", ID);
	}

	public List<Map<String, Object>> getGradeScoreData(Map<String, Object> param) {
		return Dao.selectList(xmlPath + "getGradeScoreData", param);
	}
	
	public Map<String, Object> getGradeScore(Map<String, Object> param){
		return Dao.selectOne(xmlPath + "getGradeScore",param);
	}

	// 插入兴趣爱好和性格
	public void insertANNEX(Map<String, Object> param, String id) {
		List<Map> list_xqah = new ArrayList();
		List<Map> list_xg = new ArrayList();
		list_xqah = (List<Map>) param.get("xqah_list");
		list_xg = (List<Map>) param.get("xg_list");
		for (Map m : list_xqah) {
			m.put("CLIENT_ID", id);
			Dao.insert(xmlPath + "insertANNEX", m);
		}
		for (Map m : list_xg) {
			m.put("CLIENT_ID", id);
			Dao.insert(xmlPath + "insertANNEX", m);
		}
	}

	public void delANNEX(Map<String, Object> param) {
		Dao.delete(xmlPath + "delANNEX", param);
	}

	public List getANNEX(Map<String, Object> param, String type, String dictype) {
		param.put("type", type);
		param.put("dictype", dictype);
		return Dao.selectList(xmlPath + "getANNEX", param);
	}

	public void getCustLeverAll() {
		// 查询所有的客户，在定量打分，统计出等级
		List list = Dao.selectList(xmlPath + "getCustLeverAll");
		for (int i = 0; i < list.size(); i++) {
			double score = 0;
			Map map = (Map) list.get(i);
			Map maoInfo = Dao.selectOne(xmlPath + "queryTSGMInfo", map);
			if (maoInfo != null) {
				List<Map<String, Object>> listInfo = Dao.selectList(xmlPath
						+ "queryDetail", maoInfo);
				for (Map<String, Object> normMap : listInfo) {
					normMap.put("CLIENT_ID", map.get("ID"));
					score = score + this.getNormScore(normMap);
				}
			}

			// 查询等级
			String TYPE = map.get("TYPE") + "";
			String custLever = "F";
			if (TYPE.equals("LP")) {// 法人
				custLever = this.queryLeverByCode("法人—综合等级划分", score);
			} else {// 自然人
				custLever = this.queryLeverByCode("自然人—综合等级划分", score);
			}
			map.put("CUST_LEVER", custLever);
			Dao.update(xmlPath + "updateCustLeverById", map);
		}
	}

	public double getNormScore(Map<String, Object> normMap) {
		double score = 0;
		if ("自然人—逾期".equals(normMap.get("STANDARD_NAME") + "")) {
			// 获取逾期的期数
			int dun = Dao.selectInt(xmlPath + "queryDunPeriod", normMap);
			score = this.queryScoreIntervalByCode(normMap.get("STANDARD_NAME")
					+ "", dun);
		} else if ("自然人—打分等级划分".equals(normMap.get("STANDARD_NAME") + "")) {
			// 获取承租人打分
			Map mapCustS = Dao.selectOne(xmlPath + "queryCustScore", normMap);
			double custScore = (mapCustS != null && mapCustS.get("SCORE") != null) ? Double
					.parseDouble(mapCustS.get("SCORE").toString()) : 0;
			score = this.queryScoreIntervalByCode(normMap.get("STANDARD_NAME")
					+ "", custScore);
		} else if ("法人—逾期".equals(normMap.get("STANDARD_NAME") + "")) {
			// 获取逾期的期数
			int dun = Dao.selectInt(xmlPath + "queryDunPeriod", normMap);
			score = this.queryScoreIntervalByCode(normMap.get("STANDARD_NAME")
					+ "", dun);
		} else if ("法人—打分等级划分".equals(normMap.get("STANDARD_NAME") + "")) {
			// 获取承租人打分
			Map mapCustS = Dao.selectOne(xmlPath + "queryCustScore", normMap);
			double custScore = (mapCustS != null && mapCustS.get("SCORE") != null) ? Double
					.parseDouble(mapCustS.get("SCORE").toString()) : 0;
			score = this.queryScoreIntervalByCode(normMap.get("STANDARD_NAME")
					+ "", custScore);
		}
		return score;
	}

	public double queryScoreIntervalByCode(String STANDARD_NAME, double Score) {
		Map map = new HashMap();
		map.put("STANDARD_NAME", STANDARD_NAME);
		map.put("D_CODE", Score);
		double score = Dao.selectDouble(xmlPath + "queryScoreIntervalByCode",
				map);
		return score;
	}

	public String queryLeverByCode(String STANDARD_NAME, double Score) {
		Map map = new HashMap();
		map.put("STANDARD_NAME", STANDARD_NAME);
		map.put("D_CODE", Score);
		Map mapLever = Dao.selectOne(xmlPath + "queryLeverByCode", map);
		return (mapLever != null && mapLever.get("FLAG_INTNA") != null) ? mapLever
				.get("FLAG_INTNA").toString() : "F";
	}

	/**
	 * @requestFunction 拿到法人企业团队描述
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年8月21日 下午3:52:39
	 */
	public Map getTeamDesc(Map<String, Object> params) {
		return Dao.selectOne(xmlPath + "getTeamDesc", params);
	}

	public int updateTeamDesc(Map<String, Object> params) {
		return Dao.update(xmlPath + "updateTeamDesc", params);
	}

	public int insertTeamDesc(Map<String, Object> params) {
		String id = Dao.getSequence("SEQ_FIL_CUST_COMTEAM_DESC");
		params.put("ID", id);
		return Dao.insert(xmlPath + "insertTeamDesc", params);
	}

	public Page lpCustBaseInfoData(Map<String, Object> m) {
		return PageUtil.getPage(m, xmlPath + "lpCustBaseInfo", xmlPath
				+ "lpCustBaseInfoCount");
	}

	public Page npCustBaseInfoData(Map<String, Object> m) {
		return PageUtil.getPage(m, xmlPath + "npCustBaseInfo", xmlPath
				+ "npCustBaseInfoCount");
	}

	public List getCustBaseInfoMX(Map<String, Object> m) {
		List list = Dao.selectList(xmlPath + "getCustBaseInfoMX", m);
		String TYPE = m.get("TYPE") + "";
		List listDate = new ArrayList();
		Map mapDate = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Map mapJoin = (Map) list.get(i);
			boolean bool = false;
			if (i == 0) {
				listDate.add(map);
			} else {

				if (TYPE.equals("LP")) {// 法人
					// 判断法人字段是否修改
					// 1.REGISTE_CAPITAL
					if (this.comObjectMethod(map.get("REGISTE_CAPITAL"),
							mapDate.get("REGISTE_CAPITAL"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("REGISTE_CAPITAL");
					}

					// 2.BUSINESS_TYPE
					if (this.comObjectMethod(map.get("BUSINESS_TYPE"),
							mapDate.get("BUSINESS_TYPE"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("BUSINESS_TYPE");
					}

					// 3.LEGAL_PERSON
					if (this.comObjectMethod(map.get("LEGAL_PERSON"),
							mapDate.get("LEGAL_PERSON"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("LEGAL_PERSON");
					}

					// 4.REGISTE_PHONE
					if (this.comObjectMethod(map.get("REGISTE_PHONE"),
							mapDate.get("REGISTE_PHONE"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("REGISTE_PHONE");
					}

					// 5.WORK_ADDRESS
					if (this.comObjectMethod(map.get("WORK_ADDRESS"),
							mapDate.get("WORK_ADDRESS"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("WORK_ADDRESS");
					}
				} else if (TYPE.equals("NP")) {// 自然人
					// 判断自然人字段是否修改
					// 1.TEL_PHONE
					if (this.comObjectMethod(map.get("TEL_PHONE"),
							mapDate.get("TEL_PHONE"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("TEL_PHONE");
					}

					// 2.PHONE
					if (this.comObjectMethod(map.get("PHONE"),
							mapDate.get("PHONE"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("PHONE");
					}

					// 3.IS_MARRY
					if (this.comObjectMethod(map.get("IS_MARRY"),
							mapDate.get("IS_MARRY"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("IS_MARRY");
					}

					// 4.ID_CARD_NO
					if (this.comObjectMethod(map.get("ID_CARD_NO"),
							mapDate.get("ID_CARD_NO"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("ID_CARD_NO");
					}

					// 5.HOUSR_RE_ADDRESS
					if (this.comObjectMethod(map.get("HOUSR_RE_ADDRESS"),
							mapDate.get("HOUSR_RE_ADDRESS"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("HOUSR_RE_ADDRESS");
					}

					// 6.SPOUST_NAME
					if (this.comObjectMethod(map.get("SPOUST_NAME"),
							mapDate.get("SPOUST_NAME"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("SPOUST_NAME");
					}

					// 7.SPOUDT_ID_CARD_NO
					if (this.comObjectMethod(map.get("SPOUDT_ID_CARD_NO"),
							mapDate.get("SPOUDT_ID_CARD_NO"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("SPOUDT_ID_CARD_NO");
					}

					// 8.SPOUDT_TEL_PHONE
					if (this.comObjectMethod(map.get("SPOUDT_TEL_PHONE"),
							mapDate.get("SPOUDT_TEL_PHONE"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("SPOUDT_TEL_PHONE");
					}

					// 9.HOUSE_ADDRESS
					if (this.comObjectMethod(map.get("HOUSE_ADDRESS"),
							mapDate.get("HOUSE_ADDRESS"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("HOUSE_ADDRESS");
					}

					// 10.HOUSE_ADDRESS
					if (this.comObjectMethod(map.get("QQ"), mapDate.get("QQ"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("QQ");
					}

					// 11.WX
					if (this.comObjectMethod(map.get("WX"), mapDate.get("WX"))) {// 如果不相等，就需要新增一条数据
						bool = true;
					} else {// 否则清除这个字段
						map.remove("WX");
					}
				}

				if (bool) {
					listDate.add(map);
				}
			}
			mapDate = mapJoin;
		}
		return listDate;
	}

	public boolean comObjectMethod(Object obj, Object obj1) {
		boolean flag = false;
		if (obj != null && obj1 != null && !obj.equals(obj1)) {
			flag = true;
		} else if (obj == null && obj1 != null) {
			flag = true;
		} else if (obj1 == null && obj != null) {
			flag = true;
		}
		return flag;
	}

	public void dataGenerate(Map<String, Object> param) {

		String CREATE_NAME = Security.getUser().getName();
		String CREATE_CODE = Security.getUser().getName();

		// 查询要生成的必填项数据
		String type = param.get("TYPE").toString();
		String fileName = "fil_cust_client_lp.xml";
		String createTableName = "客户法人表";
		if ("NP".equals(type)) {
			fileName = "fil_cust_client_np.xml";
			createTableName = "客户自然人表";
		}
		List<Map<String, String>> l = LogUtil.parseConfig2(fileName);
		CustomModuleService customModuleService = new CustomModuleService();

		// 自定义配置 start
		Map<String,Object> p = new HashMap<String,Object>() ;
		p.put("CREATE_TABLE_NAME", createTableName) ;
		Map<String, Object> tableMap = customModuleService
				.queryTableByCreateTableName(p);
		//根据提供的字段查询出主表关联字段
		tableMap.put("PARENT_MODULE_ID", param.get("CLIENT_ID"));
		//查询页面显示字段
		Map<String,Object> configMap=new HashMap();
		configMap.put("ID", tableMap.get("ID"));
		List info=customModuleService.queryDynamic_Field(configMap);
		
		for(int i = 0 ;i<info.size();i++){
			Map<String,Object> dMap =(Map<String,Object>) info.get(i) ;
			Map<String, Object> refer = new HashMap<String, Object>();
			refer.put("FIELD_ZH", dMap.get("FIELD_NAME"));
			refer.put("FIELD_CN", dMap.get("FIELD_EN"));
			refer.put("TYPE", type);
			refer.put("REQUIRED", 1);
			refer.put("CREATE_NAME", CREATE_NAME);
			refer.put("CREATE_CODE", CREATE_CODE);
			Dao.insert(xmlPath + "dataGenerate", refer);
		}
		//自定义配置 end

		for (Map<String, String> m : l) {
			Map<String, Object> refer = new HashMap<String, Object>();
			refer.put("FIELD_ZH", m.get("VALUE"));
			refer.put("FIELD_CN", m.get("KEY"));
			refer.put("TYPE", type);
			refer.put("REQUIRED", 1);
			refer.put("CREATE_NAME", CREATE_NAME);
			refer.put("CREATE_CODE", CREATE_CODE);
			Dao.insert(xmlPath + "dataGenerate", refer);
		}

	}

	public Page findCustFieldPage(Map<String, Object> param) {
		param.put("PAGE_END",20);
		param.put("PAGE_BEGIN",1);
        Logger.getLogger(this.getClass()).info(this.getClass()+" 客户资料必填项维护   参数值="+param);
        Logger.getLogger(this.getClass()).info(this.getClass()+" 客户资料必填项维护  List="
                 +Dao.selectList(xmlPath + "findCustFieldPage", param));
        Logger.getLogger(this.getClass()).info(this.getClass()+" 客户资料必填项维护   Count="
                +Dao.selectList(xmlPath + "findCustFieldPageCount", param));
		return PageUtil.getPage(param, xmlPath + "findCustFieldPage", xmlPath
				+ "findCustFieldPageCount");
	}

	public void dataSynchronization(Map<String, Object> param) {
		String CREATE_NAME = Security.getUser().getName();
		String CREATE_CODE = Security.getUser().getName();

		// 查询要生成的必填项数据
		String type = param.get("TYPE").toString();
		String createTableName = "客户法人表" ;
		String fileName = "fil_cust_client_lp.xml";
		if ("NP".equals(type)) {
			createTableName = "客户自然人表" ;
			fileName = "fil_cust_client_np.xml";
		}
		List<Map<String, String>> l = LogUtil.parseConfig2(fileName);
		
		//自定义配置 start
		
		CustomModuleService customModuleService = new CustomModuleService() ;
		Map<String,Object> p = new HashMap<String,Object>() ;
		p.put("CREATE_TABLE_NAME", createTableName) ;
		Map<String, Object> tableMap = customModuleService
				.queryTableByCreateTableName(p);
		//根据提供的字段查询出主表关联字段
		
		//查询页面显示字段
		Map<String,Object> configMap=new HashMap();
		configMap.put("ID", tableMap.get("ID"));
		List info=customModuleService.queryDynamic_Field(configMap);
		
		List<Map<String,String>> zdList = new ArrayList<Map<String,String>>() ;
		for(int i=0;i<info.size();i++){
			Map<String,Object> infoCh =(Map<String,Object>) info.get(i) ;
			Map<String,String> temp = new HashMap<String,String>() ;
			temp.put("VALUE", infoCh.get("FIELD_NAME").toString()) ;
			temp.put("KEY", infoCh.get("FIELD_EN").toString()) ;
			zdList.add(temp) ;
		}
		
		//自定义配置  end
		
		List<Map<String, Object>> l1 = Dao.selectList(xmlPath
				+ "findCustFieldList", param);
		
		if(zdList.size()>0){
			l.addAll(zdList) ;
		}
		
		if (l1 == null || l1.size() == 0) {
			dataGenerate(param);
		} else {
			
			for (Map<String, String> t : l) {
				int i = 0;
				String key = t.get("KEY");
				String value = t.get("VALUE");
				for (Map<String, Object> t1 : l1) {
					String key1 = t1.get("FIELD_CN").toString();
					Object valu1 = t1.get("FIELD_ZH")==null?"":t1.get("FIELD_ZH").toString();
					if (key.equals(key1)&&value.equals(valu1)) {
						i = 1;
					}
				}
				if (i == 0) {
					Map<String, Object> refer = new HashMap<String, Object>();
					refer.put("FIELD_ZH", t.get("VALUE"));
					refer.put("FIELD_CN", t.get("KEY"));
					refer.put("TYPE", type);
					refer.put("REQUIRED", 1);
					refer.put("CREATE_NAME", CREATE_NAME);
					refer.put("CREATE_CODE", CREATE_CODE);
					Dao.insert(xmlPath + "dataGenerate", refer);
				}
			}
			
			//提取不存在的字段针对必填项
			Map<String,Object> btParam = new HashMap<String,Object>() ;
			btParam.put("TYPE", type) ;
			List<Map<String,Object>> btList = Dao.selectList(xmlPath+"findCustFieldList", btParam) ;
			StringBuffer deleteIds= new StringBuffer()  ; 
			for(Map<String,Object> bt:btList){
				String FIELD_CN = bt.get("FIELD_CN")==null?"":bt.get("FIELD_CN").toString() ;
				String FIELD_ZH = bt.get("FIELD_ZH")==null?"":bt.get("FIELD_ZH").toString() ;
				int i = 0 ;
				for(Map<String,String> zdm:l){
					if(zdm.containsValue(FIELD_CN)&&zdm.containsValue(FIELD_ZH)){
						i=1 ;
					} 					
				}
				if(i==0)
					deleteIds.append(bt.get("ID")+",") ;
			}
			if(deleteIds!=null && deleteIds.length()>0){
				deleteIds.deleteCharAt((deleteIds.length()-1)) ;
				btParam.put("SQL", "("+deleteIds.toString()+")") ;
				Dao.delete(xmlPath+"deleteCustFieldDirtyData", btParam) ;
			}
			
			
		}

	}

	public int updateRequired(Map<String, Object> param) {

		return Dao.update(xmlPath + "updateRequired", param);
	}

	public Map<String, Object> findCustField(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> l1 = Dao.selectList(xmlPath
				+ "findCustFieldList", param);
		for (Map<String, Object> m : l1) {
			result.put(m.get("FIELD_CN").toString(), m.get("REQUIRED"));
		}
		return result;
	}

	public List<Map<String, Object>> getCustLog(Map<String, Object> param) {

		return Dao.selectList(xmlPath + "getCustLog", param);
	}

	public void findCustomField(Map<String, Object> map) {
		Dao.selectList(xmlPath + "findCustomField", map);

	}

	public int addZdyInfo(Map<String, Object> param) {

		param.put("SEQTABLE", "SEQ_" + param.get("CREATE_TABLE"));
		Dao.update("customModule.createTmpTableSEQ", param);
		Dao.update("customModule.createTmpTable", param);
		return Dao.insert("customModule.add_table_config", param);

	}
	
	/**
	 * 
	 * 根据身份证号或组织机构代码证校验是否存在该客户
	 * @param ID_CARD_NO 身份证号
	 * @param ORAGNIZATION_CODE 组织机构代码
	 * @param CLIENT_NAME客户名称
	 * @return  key:RST表示 0 不存在 -1 存在且与客户名称一致  -2存在数据且与姓名不一致；CLIENT_ID表示正式客户ID
	 * @author King 2015年3月5日
	 */
	public Map<String,Object> queryCheckCustomer(String ID_CARD_NO,String ORAGNIZATION_CODE,String CLIENT_NAME){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID_CARD_NO", ID_CARD_NO);
		map.put("ORAGNIZATION_CODE", ORAGNIZATION_CODE);
		int rst=0;
		Map<String,Object> mm=new HashMap<String, Object>();
		String CLIENT_ID=null;
		List<Map<String,Object>> list =Dao.selectList(xmlPath+"queryCheckCustomer", map);
		if(StringUtils.isBlank(list)&&!list.isEmpty()&&list.size()>=1){
			for (Map<String,Object> m : list) {
				if(CLIENT_NAME.equals(m.get("NAME"))){
					rst= -1;
					CLIENT_ID=m.get("CLIENT_ID")+"";
				}
			}
			if(rst!=1)
				rst=-2;
		}
		mm.put("RST", rst);
		mm.put("NEW_CLIENT_ID", CLIENT_ID);
		return mm;
	}
	
	public void updateProjectClient(Map<String,Object> map){
		Dao.update(xmlPath+"updateProjectClient", map);
	}

	public List<Map<String, Object>> getCustInfoFromSuppliersInfo(
			Map<String, Object> param) {
		
		return Dao.selectList(xmlPath+"getCustInfoFromSuppliersInfo", param);
	}
	public Page findAllcheche(Map<String, Object> param) {
		return PageUtil.getPage(param, "customers.findAllxinxi","customers.findAllxinxicheCount");
	}
	public List<Map<String,Object>> findAllAuto(Map<String,Object> param){
		
		return Dao.selectList("customers.findAllAuto", param);
	}
	//历史车贷记录
	public List<Map<String,Object>> findAutoLoan(Map<String,Object> param){
		return Dao.selectList("customers.findAutoLoan", param);
	}
	//历史车贷记录，承租人
	public List<Map<String,Object>> findTenantAutoLoan(Map<String,Object> param){
		return Dao.selectList("customers.findTenantAutoLoan", param);
	}
	//历史车贷记录，共同承租人
	public List<Map<String,Object>> findJointTenantAutoLoan(Map<String,Object> param){
		return Dao.selectList("customers.findJointTenantAutoLoan", param);
	}
	//历史车贷记录，担保人
	public List<Map<String,Object>> findBondsManAutoLoan(Map<String,Object> param){
		return Dao.selectList("customers.findBondsManAutoLoan", param);
	}
	public int addAutoLoan(Map<String,Object> param){
		return Dao.insert("customers.addAutoLoan", param);
	}
	
	public void delLoad(Map<String,Object> param){
		Dao.insert("customers.delLoad", param);
	}
	
	public Map<String,Object> findClientId(Map<String,Object> param){
		return Dao.selectOne("customers.findClient",param);
	}
	//插入联系人信息
	public void doAddEmergencyInfo(List<Map<String, Object>> emergencyInfo,
			String custClientId) {
		for(Map<String,Object> m:emergencyInfo){
			m.put("CLIENT_ID", custClientId) ;
			Dao.insert(xmlPath+"doAddEmergencyInfo",m) ;
		}
		
	}
	
	//插入联系人信息
	public void doAddEmergencyInfo(Map<String, Object> emergencyInfo) {
		Dao.insert(xmlPath+"doAddEmergencyInfo",emergencyInfo) ;
	}
	
	//删除联系人信息
	public void doDelEmergencyInfo(Map<String, Object> emergencyInfo) {
		Dao.insert(xmlPath+"doDelEmergencyInfo",emergencyInfo) ;
	}
	
	//更新联系人信息
	public void doUpdateEmergencyInfo(Map<String, Object> emergencyInfo) {
		Dao.update(xmlPath+"doUpdateEmergencyInfo",emergencyInfo);
	}

	//获取联系人信息
	public List<Map<String, Object>> getEmergencyInfoByClientId(String clientId) {
		
		return Dao.selectList(xmlPath+"getEmergencyInfoByClientId", clientId);
	}
	
	//获取联系人电调信息
	public List<Map<String, Object>> getFIL_PROJECT_TELCALL_INFO(String OLD_ID) {
		
		return Dao.selectList(xmlPathcust + "getTelCallInfoClientId", OLD_ID);
	}
	
	//更新联系人电调信息
	public List<Map<String, Object>> updateFIL_PROJECT_TELCALL_INFO(Map<String, Object> m) {
		
		return Dao.selectList(xmlPathcust + "updateTelCallInfoClientId", m);
	}
	
	
	//删除联系人信息
	public int doDelEmergencyInfoByClientId(String clientId) {
		
		return Dao.delete(xmlPath+"doDelEmergencyInfoByClientId", clientId) ;
	}
	// 

	public List<Object> getChildAreaById(String id) {
		
		return Dao.selectList(xmlPath+"getChildAreaById", id);
	}
	public List<Object> getCityAreaById(String id) {
		
		return Dao.selectList(xmlPath+"getCityAreaById", id);
	}
	

	public Map<String,Object> queryClientByProject_id(String PROJECT_ID){
		return Dao.selectOne(xmlPath+"queryClientByProject_id",PROJECT_ID);
	}
	
	public Map<String,Object> queryGtczrClientByProject_id(String PROJECT_ID){
		return Dao.selectOne(xmlPath+"queryGtczrClientByProject_id",PROJECT_ID);
	}
	
	/**
	 * add by lishuo 12-29-2015
	 * 在查询客户表之前更新婚姻状态
	 * @param map 
	 */
	public void updateClientISMarry(Map<String,Object> map){
		Dao.update(xmlPath+"updateClientISMarry",map);
	}
	
	public void updateClentInfoByNP(Map<String,Object> map){
		Dao.update(xmlPath+"updateClentInfoByNP",map);
	}
	
	public void updateClentInfoByLP(Map<String,Object> map){
		Dao.update(xmlPath+"updateClentInfoByLP",map);
	}
	
	public void updateClentInfoZDYByNP(Map<String,Object> map){
		Dao.update(xmlPath+"updateClentInfoZDYByNP",map);
	}

	public Map<String, Object> getGradeScoreId(Map<String, Object> param) {
		
		return Dao.selectOne(xmlPath + "getGradeScoreId", param);
	}
	//查询关联表中有担保人,有则更新备注说明
	public Map<String, Object> getGuarantee(Map<String, Object> map){
		return Dao.selectOne(xmlPath +"selectGuarntorById", map);
	}
	public void updateGuarantee(Map<String, Object> map){
		Dao.update(xmlPath +"updateGuarntor", map);
	}
	
	public boolean addCustomerEmpty(Map<String, Object> map){
		boolean flag=true;
		int i=Dao.insert(xmlPath+"addCustomerEmpty", map);
		if(i<0){
			return false;
		}
		return true;
	}
	//实际费用查询
	public List<Map<String,Object>> getSlsjfyInfo(Map<String,Object> mm){
		return Dao.selectList(xmlPath+"getSlsjfyInfo",mm);
		
	}
	

	public Map<String,Object> queryInfoByEqBase(Map<String, Object> map) {
		return Dao.selectOne("project.queryInfoByEqBase", map);
	}

	/**
	 *  查询该项目所选择的设备
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryEqByProjectIDByScheme(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByProjectIDByScheme", param);
	}
	
	public int sjfySave(Map<String,Object> map){
		return Dao.update("customers.doUpCustomers", map);
	}
	
	/**
	 * 保存方法
	 * @param m 操作使用数据集
	 * @return int 操作返回条数
	 */
	public int addxmmx(Map<String, Object> m) {
		
		// 判断当前条记录是否在数据库中已存在，如果存在，则执行修改操作，否则新增
		List<Map<String, Object>> list = Dao.selectList("paymentApply.findXmmxlistByCP", m);
		
		if(null != list && list.size() > 0){
			
			return Dao.update("custTel.updateXmmx", m);
		}else{
			
			if("0".equals(m.get("APPLY_MONEY").toString())){
				return 1;
			}else{
				return Dao.insert("custTel.addxmmx", m);
			}
			
		}
		
	}
	/*add by lishuo 11.17.2015
	 * 內部匹配
	 */
	public List<Map<String,Object>> goInnerCompare(Map<String,Object> mm){
		return Dao.selectList("customers.goInnerCompare",mm);
	}
	
	/*add by lishuo 11.27.2015
	 * 內部匹配先行查询条件
	 */
	public List<Map<String,Object>> test(Map<String,Object> mm){
		return Dao.selectList("customers.test",mm);
	}
	/*add by luo 12.1.2015
	 * 固定月还基础数据
	 */
	public List<Map<String,Object>> queryBaseIfo(Map<String,Object> mm){
		return Dao.selectList("customers.queryBaseIfo",mm);
	}
	
	/*add by lishuo 12.2.2015
	 * 修改固定月还数据
	 */
	public int UpdateSoldProcuct(Map<String,Object> mm){
		return Dao.update("customers.UpdateSoldProcuct",mm);
	}
	
	/*add by lishuo 12.2.2015
	 * 删除固定月还数据
	 */
	public int DeleteSoldProduct(Map<String,Object> mm){
		return Dao.delete("customers.DeleteSoldProduct",mm);
	}
	
	/*add by lishuo 12.2.2015
	 * 根据ID查询固定月还数据
	 */
	public List<Map<String,Object>> FindSoldProductByID(Map<String,Object> mm){
		return Dao.selectList("customers.FindSoldProductByID",mm);
	}
	
	/*add by lishuo 12.1.2015
	 * 查询融资额
	 */
	public List<Map<String,Object>> QueryTotalMoney(Map<String,Object> mm){
		return Dao.selectList("customers.QueryTotalMoney",mm);
	}
	
	/*add by lishuo 3-18-2016
     * 捷越黑名单电话校验
     */
    public List<Map<String,Object>> doCheckBlackPhone(Map<String,Object> mm){
        return Dao.selectList("customers.doCheckBlackPhone",mm);
    }
	
	/*add by lishuo 12.3.2015
	 * 导入Excel数据
	 */
	public int ImportExcel(Map<String,Object> mm){
		mm.put("ID", Dao.getSequence("seq_fil_car_num"));
		return Dao.insert("customers.ImportExcel",mm);
	}
	
	/**
	 * 根据ID查询得到费用明细表对象集合
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getSifjForReset(Map<String,Object> param) {
		return Dao.selectOne("project.getSifjForReset", param);
	}
	
	/**
	 * 固定月还
	 * @param m
	 * @return
	 */
	public Page querySoldPage(Map<String, Object> m) {
		return PageUtil.getPage(m, "customers.queryBaseIfo", "customers.queryBaseIfoCount");
	}
	
	/**
	 * 固定月还新增
	 * @param m
	 * @return
	 */
	public int saveCarInfo(Map<String, Object> map) {
		map.put("ID", Dao.getSequence("seq_fil_car_num"));
		return Dao.insert(xmlPath + "insertBaseInfo", map);
	}
	/**
	 *查询客户姓名 证件号码
	 * @param PROJECT_ID
	 * @author  xingsumin 2015年12月17日14:23:16
	 * @return
	 */
	public Map<String,Object> queryNameAndCardNoByProject_id(String PROJECT_ID){
		return Dao.selectOne(xmlPath+"queryNameAndCardNoByProject_id",PROJECT_ID);
	}
	
	/**
	 *根据证件号码查询客户是否存在黑名单
	 * @param PROJECT_ID
	 * @author  xingsumin 2016年1月6日11:48:24
	 * @return
	 */
	public int checkBlackCust(Map<String, Object> param) {
		return Dao.selectInt(xmlPath + "checkBlackCust", param);
	}
	
	/**
	 * 验证项目是否有代扣银行卡（如果有银行卡但是没有代扣或者代扣的银行卡被删除，给第一个银行卡做默认代扣处理）JZZL-245
	 * @param PROJECT_ID
	 * @author  耿长宝 2016年7月5日17:35:04
	 * @return
	 */
	public void checkForUpdbankId(Map<String, Object> param) {
		Map<String,Object> bankInfo = Dao.selectOne(xmlPath+"queryProAndOpeBankId",param);
		String BANK_ID = "";
		if (bankInfo != null && (bankInfo.get("BANK_ID") == null || "".equals(bankInfo.get("BANK_ID").toString()))
				&& bankInfo.get("C_BANK_ID") != null && !"".equals(bankInfo.get("C_BANK_ID").toString())) {
			BANK_ID = bankInfo.get("C_BANK_ID").toString();
			param.put("BANK_ID", BANK_ID);
			Dao.update(xmlPath+"updateProjectBankId",param);
		}
	}
	
}
