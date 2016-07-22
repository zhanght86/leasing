package com.pqsoft.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.crm.service.CustomerService;
import com.pqsoft.customers.service.CompanyManagerService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.math.Finance;
import com.pqsoft.skyeye.math.RefundsForJieYi;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilDate;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FileCatalogUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class projectService {
	private Logger log = Logger.getLogger(projectService.class);
	public List<Map<String,Object>> queryCatenaByEqID(Map<String,Object> m) {
		return Dao.selectList("project.queryCatenaByEqID", m);
	}

	public List<Map<String,Object>> querySpecByEqID(Map<String,Object> m) {
		return Dao.selectList("project.querySpecByEqID", m);
	}

	// 通过登陆人查询供应商,厂商
	public Map<String,Object> querySuppByUserId(Map<String,Object> m) {
		ManageService service = new ManageService();
		Map<String,Object> map = (Map<String,Object>) service.getSupByUserId(Security.getUser().getId());
		if (map == null || map.isEmpty()) {
			// 通过项目对应的设备供应商查询供应商,厂商信息 参数为项目id PROJECT_ID
			return Dao.selectOne("project.querySuppBySupId", m);
		}
		return Dao.selectOne("project.querySuppByUserId", map);
	}

	// 通过供应商查询所有设备
	public List<Map<String,Object>> queryProductByCompId(Map<String,Object> m) {
		return Dao.selectList("project.queryProductByCompId", m);
	}
	/**
	 *  更新或新增项目
	 * @param params
	 * PROJECT_ID ： fil_project_head id
	 * 
	 */
	public boolean projectSave(Map<String,Object> params) {
		boolean flag = true;
		if(StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())){
			params.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSuppId());
		}else if(StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())){
			params.put("BUSINESS_SOURCE", Security.getUser().getOrg().getSpId());
		}
		if (params != null) {
			String PROJECT_ID = (String) params.get("PROJECT_ID");
			if(StringUtils.isBlank(PROJECT_ID)){ // 新增
				return this.projectAdd(params);
				
			}else{ // 更新
				String SCHEME_ROW_NUM = Dao.getSequence("SEQ_FIL_PROJECT_EQUIPMENT");
				params.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);

				//组织架构id需要屏蔽 组织架构从系统中取
				params.put("ORD_ID", Security.getUser().getOrg().getId());
				params.put("PROJECT_ID", PROJECT_ID);
				//新加平台ID
				params.put("FHFA_ID", Security.getUser().getOrg().getPlatformId());
				int count = Dao.update("project.updateBYID", params);
				if (count > 0) {
					// 新建设备
					JSONArray EqList=JSONArray.fromObject(params.get("EqList"));
					Dao.delete("project.EquipmentDelete",  PROJECT_ID);
					for (int i = 0; i < EqList.size(); i++) {
						Map<String,Object> eqMap = (Map<String,Object>) EqList.get(i);
						if (eqMap != null) {
					
							eqMap.put("PROJECT_ID", PROJECT_ID);
							eqMap.put("USERID", params.get("USERID"));
							eqMap.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
							if(!params.containsKey("BUSINESS_SOURCE") || "".equals(params.get("BUSINESS_SOURCE"))){
								Dao.update("project.updBusinessSource",eqMap);
							}
							int num = Dao.insert("project.EquipmentAdd", eqMap);
							if (num <= 0) {
								flag = false;
							}
						}
					}
				} else {
					flag = false;
				}
				try{
					// 通过项目和设备分组查询必要信息
					params.putAll((Map)Dao.selectOne("project.queryInfoByEq", params));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		return flag;
	}

	// 新建项目
	public boolean projectAdd(Map<String,Object> m) {
		boolean flag = true;
		Map<String, Object> mm = new HashMap<String, Object>();
		if (m != null) {
			String PROJECT_ID = Dao.getSequence("SEQ_FIL_PROJECT_HEAD");
			String SCHEME_ROW_NUM = Dao.getSequence("SEQ_FIL_PROJECT_EQUIPMENT");
			m.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
			mm.put("PROJECT_ID", PROJECT_ID);
			if("LP".equals(m.get("CUSTOMER_TYPE").toString())){
				this.doAddFeedBackProjectId(mm);
				this.doAddAppProjectId(mm);

			}
			//组织架构id需要屏蔽 组织架构从系统中取
			m.put("ORD_ID", Security.getUser().getOrg().getId());
			m.put("PROJECT_ID", PROJECT_ID);
			//新加平台ID
			m.put("FHFA_ID", Security.getUser().getOrg().getPlatformId());
			//担保人
			String GUARANTEE=(m.get("GUARANTEE")!=null && !m.get("GUARANTEE").equals(""))?m.get("GUARANTEE").toString():"1";
			System.out.println(GUARANTEE+"=============================="+m);
			if(GUARANTEE.equals("1"))
			{
				m.put("GUARANTEE", "1");
			}else if(GUARANTEE.equals("2"))//担保人为自然人
			{
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("CREDIT_ID", PROJECT_ID);
				custMap.put("TYPE", "NP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				m.put("GUARANTEE", "2");
			}else
			{
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("CREDIT_ID", PROJECT_ID);
				custMap.put("TYPE", "LP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				m.put("GUARANTEE", "3");
			}
			//共同承租人的处理
			String JOINT_TENANT=(m.get("JOINT_TENANT")!=null && !m.get("JOINT_TENANT").equals(""))?m.get("JOINT_TENANT").toString():"1";
			if(JOINT_TENANT.equals("1")){
				m.put("JOINT_TENANT_ID", "");
			}else if(JOINT_TENANT.equals("2")){//自然人
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("TYPE", "NP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				
				m.put("JOINT_TENANT_ID", ID);
			}else{//法人
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("TYPE", "LP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				
				m.put("JOINT_TENANT_ID", ID);
			}
			
			// add gengchangbao JZZL-198 2016年5月30日17:34:40 Start
			if (m.get("TOTHEMONEY") != null && !"".equals(m.get("TOTHEMONEY").toString()) && !"0".equals(m.get("TOTHEMONEY").toString())) {
				Map<String, Object> totheMoneyMap =new HashMap<String, Object>();
				totheMoneyMap.put("PROJECT_ID", PROJECT_ID);
				totheMoneyMap.put("TOTHEPEOPLE", m.get("CLIENT_NAME"));//来款人
				totheMoneyMap.put("TOTHEMONEY", m.get("TOTHEMONEY")); //定金
				totheMoneyMap.put("TOTHETYPE", "1"); //来款类型   1：定金
				totheMoneyMap.put("CREATECODE", Security.getUser().getCode()); 
				totheMoneyMap.put("CREATENAME", Security.getUser().getName());//创建人
				totheMoneyMap.put("STATUS", "1"); // 1：已付
				Dao.insert("project.insertDeposit", totheMoneyMap);
			}
			// add gengchangbao JZZL-198 2016年5月30日17:34:40 End
			
			int count = Dao.insert("project.projectAdd", m);
			if (count > 0) {
				// 新建设备
				JSONArray EqList=JSONArray.fromObject(m.get("EqList"));
//				List EqList = (List) m.get("EqList");
				for (int i = 0; i < EqList.size(); i++) {
					Map<String,Object> eqMap = (Map<String,Object>) EqList.get(i);
					if (eqMap != null) {
				
						eqMap.put("PROJECT_ID", PROJECT_ID);
						eqMap.put("USERID", m.get("USERID"));
						eqMap.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
						eqMap.put("CAR_COLOR", m.get("CAR_COLOR"));
						eqMap.put("XH_PARAM", m.get("XH_PARAM"));
						
						eqMap.put("BX", m.get("BX"));
						eqMap.put("CCS", m.get("CCS"));
						eqMap.put("PRO_REMARK", m.get("PRO_REMARK"));
						eqMap.put("JQX", m.get("JQX"));
						//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
						eqMap.put("CC_PRICE", m.get("CC_PRICE"));
						//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
						//add gengchangbao JZZL- 204 2016年6月15日09:53:56 Start
						eqMap.put("REAL_PRICE", m.get("REAL_PRICE"));
						//add gengchangbao JZZL- 204 2016年6月15日09:53:56 End
						if(!m.containsKey("BUSINESS_SOURCE") || "".equals(m.get("BUSINESS_SOURCE"))){
							if(eqMap.get("SUPPLIERS_ID") !=null && !eqMap.get("SUPPLIERS_ID").equals("")){
								Dao.update("project.updBusinessSource",eqMap);
							}
						}
						int num = Dao.insert("project.EquipmentAdd", eqMap);
						if (num <= 0) {
							flag = false;
						}
					}
				}
			} else {
				flag = false;
			}
			try{
			// 通过项目和设备分组查询必要信息
			m.putAll((Map)Dao.selectOne("project.queryInfoByEq", m));
			}catch(Exception e){}
			// 销售型售后回租项目编号和 销售型直租一样 
			Map<String, Object> proMap = new HashMap<String, Object>();
			String PRO_CODE = "";
			if ("4".equals(m.get("PLATFORM_TYPE").toString())
					&& "back_leasing".equals(m.get("LEASE_MODEL").toString())) {
				m.put("PARENT_ID",
						this.getProjectId(m.get("PRO_CODE").toString()));
				PRO_CODE = m.get("PRO_CODE").toString() + "_SH";
			} else {
				PRO_CODE = CodeService.getCode("项目编号", m.get("CLIENT_ID").toString(), PROJECT_ID);
			}
			proMap.put("PRO_CODE", PRO_CODE);
			proMap.put("PROJECT_ID", PROJECT_ID);
			Dao.update("project.updateProcodeById", proMap);
		}
		return flag;
	}

	// 修改方案时 担保人修改
	public boolean UpdateProjectOther(Map<String,Object> m) {
		boolean flag = true;
		String PROJECT_ID =	m.get("PROJECT_ID").toString();
		if (m != null) {
			//担保人
			String GUARANTEE=(m.get("GUARANTEE")!=null && !m.get("GUARANTEE").equals(""))?m.get("GUARANTEE").toString():"1";
			System.out.println(GUARANTEE+"==========="+m);
			if(GUARANTEE.equals("1"))
			{
//				Dao.delete("creditGuarantor.deleteGuarantor", m);
				m.put("GUARANTEE", "1");
			}else if(GUARANTEE.equals("2"))//担保人为自然人
			{
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("CREDIT_ID", PROJECT_ID);
				custMap.put("TYPE", "NP");
				//临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				m.put("GUARANTEE", "2");
			}else
			{
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("CREDIT_ID", PROJECT_ID);
				custMap.put("TYPE", "LP");
				//临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				m.put("GUARANTEE", "3");
			}
		}
		return flag;
	}
	// 修改方案时 共同承租人修改
	public boolean UpdateProjectOtherGT(Map<String,Object> m) {
		boolean flag = true;
//		String PROJECT_ID =	m.get("PROJECT_ID").toString();
		if (m != null) {
			//共同承租人的处理
			String JOINT_TENANT=(m.get("JOINT_TENANT")!=null && !m.get("JOINT_TENANT").equals(""))?m.get("JOINT_TENANT").toString():"1";
			m.put("JOINT_TENANT", JOINT_TENANT);
			if(JOINT_TENANT.equals("1")){
				m.put("JOINT_TENANT_ID", "");
			}else if(JOINT_TENANT.equals("2")){//自然人
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("TYPE", "NP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				
				m.put("JOINT_TENANT_ID", ID);
			}else{//法人
				CustomersService cService = new CustomersService();
				String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
				Map<String, Object> custMap =new HashMap<String, Object>();
				custMap.put("ID", ID);
				custMap.put("USERID", Security.getUser().getId());
				custMap.put("USERCODE",Security.getUser().getCode());
				custMap.put("USERNAME",Security.getUser().getName());
				String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
				custMap.put("CUST_ID", CUST_ID);
				custMap.put("TYPE", "LP");
			    //临时客户状态
				custMap.put("CUST_STATUS", "0");
				cService.doAddCust(custMap);
				
				m.put("JOINT_TENANT_ID", ID);
			}
//			System.out.println("--projectService--m:"+m);
			this.updateGTById(m);
		}
		return flag;
	}
	
    public String getProjectId(String PRO_CODE){
    	return Dao.selectOne("project.getProjectId", PRO_CODE);
    }
	// 追加设备方案
	public boolean projectAppend(Map m) {
		boolean flag = true;
		Map<String, Object> mm = new HashMap<String, Object>();
		if (m != null) {
			String project_head_id = m.get("PROJECT_ID").toString();
			String SCHEME_ROW_NUM = Dao.getSequence("SEQ_FIL_PROJECT_EQUIPMENT");
			m.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
			mm.put("PROJECT_ID", m.get("PROJECT_ID"));
			m.put("project_head_id", project_head_id);
			int count = Dao.update("project.projectUpdate", m);

			// 新建设备
			List EqList = (List) m.get("EqList");
			for (int i = 0; i < EqList.size(); i++) {
				Map eqMap = (Map) EqList.get(i);
				if (eqMap != null) {
					eqMap.put("PROJECT_ID", project_head_id);
					eqMap.put("USERID", m.get("USERID"));
					eqMap.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
					int num = Dao.insert("project.EquipmentAdd", eqMap);
					if (num <= 0) {
						flag = false;
					}
				}

			}
			// 通过项目和设备分组查询必要信息
			Map mapInfo = (Map) Dao.selectOne("project.queryInfoByEq", m);
			if (mapInfo != null) {
				m.putAll(mapInfo);
			}

		}
		return flag;
	}

	public Page queryPage(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		Map SUP_MAP = BaseUtil.getSup();
		m.put("ORD_ID", Security.getUser().getOrg().getId());
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		System.out.println(m);
		List<Map<String, Object>> selectList ;
		//modify by lishuo 12.17.2015 start 若不加页面不能显示分公司
		Page page = new Page(m);
		if("ALL".equals(m.get("ALL")) && m.get("ALL") != null){
			 selectList = Dao.selectList("project.getAllProject2", m);
		}else
			 selectList = Dao.selectList("project.getAllProject", m);
		for(int i=0;i<selectList.size();i++){
			Map<String, Object> param1 = new HashMap<String, Object>();
			if(selectList.get(i).get("SHOP_NAME") != null && !"".equals(selectList.get(i).get("SHOP_NAME").toString())){
				param1.put("SHOPNAME", selectList.get(i).get("SHOP_NAME"));
				//add by lishuo 04-18-2016 Start
                Map<String,Object> result =Dao.selectOne("bpm.task.getFGS", param1);
                if("".equals(result) || null == result){
                    selectList.get(i).put("FGS",param1.get("SHOPNAME"));//若查询分公司没有值则取门店名
                }else{
                    selectList.get(i).put("FGS", ((Map<String, Object>)Dao.selectOne("bpm.task.getFGS", param1)).get("NAME"));
                }
                //add by lishuo 04-18-2016 End
			}
		}
		page.addDate(JSONArray.fromObject(selectList ), Dao.selectInt("project.getAllProject_count".trim(), m));
		return page;
		//modify by lishuo 12.17.2015 end
		//return PageUtil.getPage(m, "project.getAllProject", "project.getAllProject_count");
	}

	public List<Map<String,Object>> queryBank(String CLIENT_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_ID", CLIENT_ID);
		return Dao.selectList("customers.findBankDetail", map);// 开户行
	}

	// 项目编码问题
	public String queryProjectCodeNext(String supp_code) {
		String projectCode = "";
		String year = UtilDate.formatDate(new Date(), "yyyy");

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("khbs", supp_code);
		map.put("year", year);
		Map<String,Object> mapOne = Dao.selectOne("project.queryProjectCodeKey", map);
		if (mapOne != null && mapOne.get("ID") != null && !mapOne.get("ID").equals("")) {
			Dao.update("project.updateProjectCodeKey", map);
		} else {
			Dao.insert("project.insertProjectCodeKey", map);
		}

		// 项目编号重复判断
		while (true) {
			Map<String,Object> mapWrong = Dao.selectOne("project.queryProjectCodeKeyWrong", map);
			if (mapWrong != null && mapWrong.get("ID") != null && !mapWrong.get("ID").equals("")) {
				Map<String,Object> mapKey = Dao.selectOne("project.queryProjectCodeKeyUser", map);
				projectCode = mapKey.get("PROJECTCODE").toString();
				break;
			} else {
				Dao.update("project.updateProjectCodeKey", map);
			}
		}
		return projectCode;
	}

	/**
	 * 添加商务政策信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-8-31
	 */
	public int doAddSchemeProject(Map<String, Object> map) {
		return Dao.insert("project.doAddSchemeProject", map);
	}

	public int doAddGuaranProject(Map<String, Object> map) {
		return Dao.insert("project.doAddGuaranProject", map);
	}
	
	//保存联合租赁信息
	public int doAddFlProject(Map<String, Object> map) {
		return Dao.insert("project.doAddFlProject", map);
	}
	

	public int updateProjectByBankId(Map<String, Object> map) {
		return Dao.update("project.updateProjectByBankId", map);
	}

	// 查询登陆人的组织架构
	public List<Map<String,Object>> orgListByUserId(Map<String,Object> param) {
		return Dao.selectList("project.orgListByUserId", param);
	}

	// 查询该项目所选择的设备
	public List<Map<String,Object>> queryEqByProjectID(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByProjectID", param);
	}
	
	//根据项目表查询是否生成支付表
	public int getPayByProjectID(Map<String,Object> param){
		return Dao.selectInt("project.getPayByProjectID", param);
	}
	// 查询该项目所选择的设备-租前息
		public List<Map<String,Object>> queryZQXEqByProjectID(Map<String,Object> param) {
			param.put("tags", "设备单位");
			return Dao.selectList("project.queryZQXEqByProjectID", param);
		}
	
	// 查询该项目所选择的设备
	public List<Map<String,Object>> queryEqByPID(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByPID", param);
	}

	public List<Map<String,Object>> queryEqCountByProjectID(Map<String,Object> param) {
		return Dao.selectList("project.queryEqCountByProjectID", param);
	}

	// 查询项目所有的担保人
	public List<Map<String,Object>> queryGuaranByProjectID(Map<String,Object> param) {
		param.put("tags", "客户类型");
		return Dao.selectList("project.queryGuaranByProjectID", param);
	}

	// 查询该项目的方案
	public List<Map<String, Object>> querySechmeByProjectID(Map<String,Object> param) {
		return Dao.selectList("project.querySechmeByProjectID", param);
	}

	public Map<String,Object> queryProjectById(Map<String,Object> map) {
		map.put("tags1", "业务类型");
		map.put("tags3", "客户类型");
		map.put("tags4", "业务类型");
		Map<String, Object> m = Dao.selectOne("project.queryProjectById", map);
		if (m != null) {
			Clob clob = (Clob) m.get("IDCARD_PHOTO");
			if (clob != null) {
				try {
					m.put("IDCARD_PHOTO", clob.getSubString(1, (int) clob.length()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	//销售型售后回租查看
	public Map<String,Object> queryProjectType4ById(Map<String,Object> map) {
		map.put("tags1", "业务类型");
		map.put("tags3", "客户类型");
		map.put("tags4", "业务类型");
		Map<String, Object> m = Dao.selectOne("project.queryProjectType4ById", map);
		if (m != null) {
			Clob clob = (Clob) m.get("IDCARD_PHOTO");
			if (clob != null) {
				try {
					m.put("IDCARD_PHOTO", clob.getSubString(1, (int) clob.length()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	public Map<String,Object> queryProjectByIdAppend(Map<String,Object> map) {
		return  Dao.selectOne("project.queryProjectByIdAppend", map);
	}

	// 查询该项目的方案
	public List<Map<String, Object>> querySechmeByAllDate(Map<String,Object> param) {
		return Dao.selectList("project.querySechmeByAllDate", param);
	}

	// 根据登录人查询供应商，制造上
	public Map<String,Object> querySupp(Map<String,Object> m) {
		// 供应商登陆选择设备
		// 查询出该供应商及厂商及设备列表
		Map<String,Object> suppMap = this.querySuppByUserId(m);
		if (suppMap == null || suppMap.isEmpty()) {
			// 通过项目对应的设备供应商查询供应商,厂商信息 参数为项目id PROJECT_ID
			return Dao.selectOne("project.querySuppBySupId", m);
		}
		String SUPPLIERS_ID = null;
		String SUP_SHORTNAME = null;
		String SUPPLIERS_NAME = null;
		String SUPPLIERS_FLAG = null;
		String COMPANY_ID = "";// 厂商一对多供应商
		String COMPANY_NAME = "";
		String COMPANY_CODE = "";
		String SUP_LEVEL = "";
		String BUSINESS_SECTOR = "";

		if (suppMap != null) {
			if (suppMap.get("SUP_ID") != null && !suppMap.get("SUP_ID").equals("")) {
				SUPPLIERS_ID = suppMap.get("SUP_ID").toString();
			}

			if (suppMap.get("SUP_SHORTNAME") != null && !suppMap.get("SUP_SHORTNAME").equals("")) {
				SUP_SHORTNAME = suppMap.get("SUP_SHORTNAME").toString();
			}

			if (suppMap.get("SUP_NAME") != null && !suppMap.get("SUP_NAME").equals("")) {
				SUPPLIERS_NAME = suppMap.get("SUP_NAME").toString();
			}

			if (suppMap.get("SUP_FLAG") != null && !suppMap.get("SUP_FLAG").equals("")) {
				SUPPLIERS_FLAG = suppMap.get("SUP_FLAG").toString();
			}

			if (suppMap.get("COMPANY_ID") != null && !suppMap.get("COMPANY_ID").equals("")) {
				COMPANY_ID = suppMap.get("COMPANY_ID").toString();
			}

			if (suppMap.get("COMPANY_NAME") != null && !suppMap.get("COMPANY_NAME").equals("")) {
				COMPANY_NAME = suppMap.get("COMPANY_NAME").toString();
			}

			if (suppMap.get("COMPANY_CODE") != null && !suppMap.get("COMPANY_CODE").equals("")) {
				COMPANY_CODE = suppMap.get("COMPANY_CODE").toString();
			}
			if (suppMap.get("SUP_LEVEL") != null && !suppMap.get("SUP_LEVEL").equals("")) {
				SUP_LEVEL = suppMap.get("SUP_LEVEL").toString();
			}
			if (suppMap.get("BUSINESS_SECTOR") != null && !suppMap.get("BUSINESS_SECTOR").equals("")) {
				BUSINESS_SECTOR = suppMap.get("BUSINESS_SECTOR").toString();
			}

		}
		Map conMap = new HashMap();
		conMap.put("SUPPLIERS_ID", SUPPLIERS_ID);
		conMap.put("SUP_SHORTNAME", SUP_SHORTNAME);
		conMap.put("SUPPLIERS_NAME", SUPPLIERS_NAME);
		conMap.put("SUPPLIERS_FLAG", SUPPLIERS_FLAG);
		conMap.put("COMPANY_ID", COMPANY_ID);
		conMap.put("COMPANY_NAME", COMPANY_NAME);
		conMap.put("COMPANY_CODE", COMPANY_CODE);
		conMap.put("SUP_LEVEL", SUP_LEVEL);
		conMap.put("BUSINESS_SECTOR", BUSINESS_SECTOR);
		return conMap;
	}

	// 修改项目
	public boolean projectUpdate(Map m) {
		boolean flag = true;
		if (m != null) {
			int count = Dao.update("project.updateProjectByBankId", m);
			if (count > 0) {
				Dao.delete("project.deleteEqByProjectId", m);
				// 新建设备
				JSONArray jsonArray = JSONArray.fromObject(m.get("EqList"));
				for (Object object : jsonArray) {
					Map<String, Object> eqMap = (Map<String, Object>) object;
					if (eqMap != null) {
						eqMap.put("project_head_id", m.get("PRO_ID"));
						eqMap.put("USERID", Security.getUser().getId());
						int num = Dao.insert("project.EquipmentAdd", eqMap);
						if (num <= 0) {
							flag = false;
						}
					}
				}
				// TODO 删除方案 需要修改
				Dao.delete("project.deleteSchemeByProjectId", m);
				// String AFFILIATED_COMPANY=null;

				// 发票开具类型
				if (m.get("INVOICE_TARGET_TYPE") != null && !m.get("INVOICE_TARGET_TYPE").equals("")) {
					String INVOICE_TARGET_TYPE = m.get("INVOICE_TARGET_TYPE").toString();
					if (INVOICE_TARGET_TYPE.equals("1")) {
						m.put("INVOICE_TARGET_ID", m.get("CLIENT_ID"));
					} else if (INVOICE_TARGET_TYPE.equals("2")) {
						m.put("INVOICE_TARGET_ID", m.get("FINAL_CUST_ID"));
					} else if (INVOICE_TARGET_TYPE.equals("3")) {
						m.put("INVOICE_TARGET_ID", m.get("SUP_ID"));
					} else if (INVOICE_TARGET_TYPE.equals("4")) {
						m.put("INVOICE_TARGET_ID", m.get("COMPANY_ID"));
						// } else if (INVOICE_TARGET_TYPE.equals("5")) {
						// m.put("INVOICE_TARGET_ID", AFFILIATED_COMPANY);//
						// 挂靠公司以后再改
						// } else if (INVOICE_TARGET_TYPE.equals("6")) {
						// m.put("INVOICE_TARGET_ID", m.get("CLIENT_ID"));//
						// 担保公司以后再改
						// } else {
						m.put("INVOICE_TARGET_ID", m.get("CLIENT_ID"));// 担保公司以后再改
					}
				}
				// 保存银行信息
				updateProjectByBankId(m);
				// Dao.delete("project.deleteGuaranByProjectId", m);
				// // 保存担保人
				// JSONArray guaranjsonArray =
				// JSONArray.fromObject(m.get("guaranList"));
				// for (Object object : guaranjsonArray) {
				// Map<String, Object> mapGuaran = (Map<String, Object>) object;
				// mapGuaran.put("PROJECT_ID", m.get("PRO_ID"));
				// mapGuaran.put("USER_ID", Security.getUser().getId());
				// this.doAddGuaranProject(mapGuaran);
				// }
			} else {
				flag = false;
			}
		}
		return flag;
	}

	// 像支付表子表插入费用项
	public void createPayParam(Map map) {
		// 查询费用List
		List list = Dao.selectList("project.queryPayListByOther", map);
		for (int i = 0; i < list.size(); i++) {
			Map payMap = (Map) list.get(i);
			payMap.put("PAY_ID", map.get("PAY_ID"));
			payMap.put("FIRSTPAYDATE", map.get("FIRSTPAYDATE"));
			Dao.selectOne("project.createPayParam", payMap);
		}
	}

	// 像支付表子表插入费用项
	public void createPayParamScheme(Map map) {
		// 查询费用List
		List list = Dao.selectList("project.queryPayListByOther", map);
		for (int i = 0; i < list.size(); i++) {
			Map payMap = (Map) list.get(i);
			payMap.put("PAY_ID", map.get("PAY_ID"));
			payMap.put("FIRSTPAYDATE", map.get("FIRSTPAYDATE"));
			Dao.selectOne("project.createPayParamScheme", payMap);
		}
	}

	// 修改状态
	public int updateProjectStatusById(Map map) {
		return Dao.update("project.updateProjectStatusById", map);
	}
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 start
	// 修改签约时间
	public int updateSignDateByProId(Map map) {
		return Dao.update("project.updateSignDateByProId", map);
	}
	// add gengchangbao 2016年4月21日17:31:06  JZZL-168 end

	/**
	 * 根据项目id更新设备表的还款计划编号
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-9-23
	 */
	public int updateProEquPayCodeByProId(String PROJECT_ID) {
		return Dao.update("project.updateProEquPayCodeByProId", PROJECT_ID);
	}

	public int updateProEquPayCodeByProIdAppend(String PROJECT_ID, String SCHEME_ROW_NUM) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
		return Dao.update("project.updateProEquPayCodeByProIdAppend", map);
	}

	public Map<String, Object> selectRenterDetails(Map<String, Object> param) {
		Map<String, Object> map = Dao.selectOne("project.selectRenterDetails", param);
		Dao.getClobTypeInfo2(map, "IDCARD_PHOTO");
		return map;
	}

	public Map<String, Object> selectProjectDetails(Map<String, Object> param) {
		return Dao.selectOne("project.selectProjectDetails", param);
	}
	
	public Map<String,Object> selectProjectParentIdForSH(String id){
		return Dao.selectOne("project.selectProjectParentIdForSH",id) ;
	}

	public boolean selectOldProjectCreatDate(String PROJECT_ID) {// true 新项目
																	// false 老项目
		String creatdate = Dao.selectOne("project.selectOldProjectCreatDate", PROJECT_ID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = true;
		try {
			Date date = sdf.parse(creatdate);
			Date xtsxsj = sdf.parse("2013-12-09");// 系统上线时间
			if (date.before(xtsxsj)) {
				flag = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteProjectFile(Map<String, Object> param) {
		return Dao.delete("project.deleteProjectFile", param) >= 0 ? true : false;
	}

	public Map<String, Object> getJbpmId(Map<String, Object> param)
	{
		return Dao.selectOne("project.getJbpmId",param);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> electronicPhotoAlbumData(Map<String, Object> param) {
		FileCatalogUtil fcu = new FileCatalogUtil();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listXMZLFJ = new CustomerService().getProjectFileListSrc(param);
		for (int i = 0; i < listXMZLFJ.size(); i++) {
			Map<String, Object> xmzlfjMap = listXMZLFJ.get(i);
			param.put("FILE_PATH", param.get("PRO_CODE") + "/项目资料附件清单/" + xmzlfjMap.get("CODE").toString().trim());
			String catalogId = fcu.getCatalogIdByPath(param.get("RENTER_NAME").toString(), param.get("RENTER_CODE").toString(), param
					.get("FILE_PATH").toString());
			param.put("CATALOG_ID", catalogId);
			List<Map<String, Object>> listMap = fcu.selectFileList(param.get("CATALOG_ID").toString());
			if (listMap != null && !listMap.isEmpty() && listMap.size() > 0) {
				for (int j = 0; j < listMap.size(); j++) {
					Map<String, Object> map = listMap.get(j);
					if (map != null && !map.isEmpty()) {
						map.put("ORIGINAL_PATH", map.get("ORIGINAL_PATH"));
						map.put("PROJECT_ID", param.get("PROJECT_ID"));
						map.put("TMP_TYPE", xmzlfjMap.get("CODE"));
						dataList.add(map);
					}
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ORIGINAL_PATH", "");
				map.put("PROJECT_ID", param.get("PROJECT_ID"));
				map.put("TMP_TYPE", xmzlfjMap.get("CODE"));
				dataList.add(map);
			}
		}

		return dataList;
	}

	public int addProjectFile(Map<String, Object> param) {
		return Dao.insert("project.addProjectFile", param);
	}

	public List<Map<String, Object>> selectProjectFileData(String PROJECT_ID) {
		return Dao.selectList("project.selectProjectFileData", PROJECT_ID);
	}

	/**
	 * 将一个项目的保险费用平均分配到每台设备上
	 * 
	 * @param PROJECT_ID
	 * @author:King 2013-10-18
	 */
	public void doUpdateAverageInsuranceByProjectId(String PROJECT_ID) {
		String INSURANCE_MONEY = Dao.selectOne("project.doShowAverageInsuranceByProjectId", PROJECT_ID);
		if (!StringUtils.isBlank(INSURANCE_MONEY)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("INSURANCE_MONEY", INSURANCE_MONEY);
			map.put("PROJECT_ID", PROJECT_ID);
			Dao.update("project.doUpdateAverageInsuranceByProjectId", map);
		}
	}

	public void doUpdateAverageInsuranceByProjectIdAppend(Map mapInfo) {
		String INSURANCE_MONEY = Dao.selectOne("project.doShowAverageInsuranceByProjectIdAppend", mapInfo);
		if (!StringUtils.isBlank(INSURANCE_MONEY)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("INSURANCE_MONEY", INSURANCE_MONEY);
			map.put("PROJECT_ID", mapInfo.get("PRO_ID") + "");
			map.put("SCHEME_ROW_NUM", mapInfo.get("SCHEME_ROW_NUM") + "");
			Dao.update("project.doUpdateAverageInsuranceByProjectIdAppend", map);
		}
	}

	/**
	 * 更新设备表中的挂靠公司
	 * 
	 * @param AFFILIATED_COMPANY
	 *            挂靠公司ID
	 * @param PROJECT_ID
	 *            项目id
	 * @author:King 2013-11-11
	 */
	public void doUpdateEqAffiliatedCompany(String AFFILIATED_COMPANY, String PROJECT_ID) {
		if (!AFFILIATED_COMPANY.equals("") && AFFILIATED_COMPANY.length() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("AFFILIATED_COMPANY", AFFILIATED_COMPANY);
			map.put("PROJECT_ID", PROJECT_ID);
			Dao.update("project.doUpdateEqAffiliatedCompany", map);
		}
	}

	public void doUpdateEqAffiliatedCompanyAppend(String AFFILIATED_COMPANY, String PROJECT_ID, String SCHEME_ROW_NUM) {
		if (!AFFILIATED_COMPANY.equals("") && AFFILIATED_COMPANY.length() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("AFFILIATED_COMPANY", AFFILIATED_COMPANY);
			map.put("PROJECT_ID", PROJECT_ID);
			map.put("SCHEME_ROW_NUM", SCHEME_ROW_NUM);
			Dao.update("project.doUpdateEqAffiliatedCompanyAppend", map);
		}
	}

	/**
	 * 根据项目id查询还款计划id及还款计划编号
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-10-19
	 */
	public Map<String, Object> doShowPayIdByProjectId(String PROJECT_ID) {
		return Dao.selectOne("project.doShowPayIdByProjectId", PROJECT_ID);
	}

	/**
	 * 更新验收日及使用地点
	 * 
	 * @param PROJECT_ID
	 * @param DELIVER_DATE
	 * @param DELIVER_ADDRESS
	 * @author:King 2013-10-24
	 */
	public void doUpdateDELIVER_DATE(String PROJECT_ID, String DELIVER_DATE, String DELIVER_ADDRESS) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("DELIVER_DATE", DELIVER_DATE);
		map.put("DELIVER_ADDRESS", DELIVER_ADDRESS);
		Dao.update("project.doUpdateDELIVER_DATE", map);
	}

	/**
	 * 查询首期款合计，首期款付款日期及租金合计
	 * 
	 * @param PAY_ID
	 * @return
	 * @author:King 2013-10-21
	 */
	public Map<String, Object> doShowRentInfo(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", PAY_ID);
		map.put("ITEM_NAME", "租金");
		return Dao.selectOne("project.doShowRentInfo", map);
	}

	/**
	 * 确认签约日期
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-22
	 */
	public int doUpdateSignedDate(Map<String, Object> map) {
		return Dao.update("project.doUpdateSignedDate", map);
	}
	
	
	public int doUpdateSIGN_SAVE(Map<String, Object> map) {
		//根据起租规则 计算日期
		List rendID = DateSiteUtil.getRend_ID(map.get("PROJECT_ID")+"");
		String PAY_ID = "";
		for(int t = 0 ;t < rendID.size(); t++){
			Map mapPay=new HashMap();
			mapPay.put("PAY_ID", PAY_ID);
			paymentService paymentSer=new paymentService();
			try{
				//先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
				Map ISCELXMap=Dao.selectOne("PayTask.querySchemeSFCELXByPay",mapPay);
				if(ISCELXMap !=null && ISCELXMap.get("VALUE_STR") !=null && ISCELXMap.get("VALUE_STR").equals("3")){
					
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,true);//参数PAY_ID,3固定为放款日期类型标示
					
				}else{
					
					//根据关键日期管理查询出还款日和起租日
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,false);//参数PAY_ID,3固定为放款日期类型标示
					
					Map<String,Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);
					
					//查询起租日和还款日
					Map dataMap=Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);
					
					if (mapbase != null) {
						mapbase.put("SCHEME_ID", mapbase.get("ID"));
						List<Map<String,Object>> clobList=Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
						for (Map<String, Object> map2 : clobList) {
							mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
						}
						
						try{
							mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
						}catch(Exception e){}
						
						
						mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
						mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
						mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
						mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
						mapbase.put("pay_way", mapbase.get("PAY_WAY"));
						
						mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
						mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
						mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
						mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
						
						mapbase.put("START_DATE", dataMap.get("START_DATE"));
						mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));
						
						JSONObject page=null;
						Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
						page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
						List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
						
						double ZJHJ = 0.00;//租金合计
						for(int hh=0;hh<irrList.size();hh++){
							//"zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
							Map mapIrr=irrList.get(hh);
							if(mapIrr !=null){
								mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));
								
								ZJHJ = ZJHJ + Double.parseDouble((mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj")+"":"0");
								//更新租金
								mapIrr.put("ITEM_NAME", "租金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新PMT租金
								mapIrr.put("ITEM_NAME", "PMT租金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("PMTzj") !=null && mapIrr.get("PMTzj") !="") ? mapIrr.get("PMTzj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新本金
								mapIrr.put("ITEM_NAME", "本金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") !=null && mapIrr.get("bj") !="") ? mapIrr.get("bj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新利息
								mapIrr.put("ITEM_NAME", "利息");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") !=null && mapIrr.get("lx") !="") ? mapIrr.get("lx"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新剩余本金
								mapIrr.put("ITEM_NAME", "剩余本金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("sybj") !=null && mapIrr.get("sybj") !="") ? mapIrr.get("sybj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新管理费
								mapIrr.put("ITEM_NAME", "管理费");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("glf") !=null && mapIrr.get("glf") !="") ? mapIrr.get("glf"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新手续费
								mapIrr.put("ITEM_NAME", "手续费");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("sxf") !=null && mapIrr.get("sxf") !="") ? mapIrr.get("sxf"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新利息增值税
								mapIrr.put("ITEM_NAME", "利息增值税");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("lxzzs") !=null && mapIrr.get("lxzzs") !="") ? mapIrr.get("lxzzs"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
							}
							
							
						}
						
						//更新还款计划主表租金合计
						Map pmap=new HashMap();
						pmap.put("ID", mapPay.get("PAY_ID"));
						pmap.put("ZJHJ", ZJHJ);
						Dao.update("PayTask.updatePayHeadMoneyAll",pmap);
						
						System.out.println("------------------------dataMap="+dataMap);
						//同步应收期初表数据
						// 同步财务数据
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("PAY_ID", mapPay.get("PAY_ID")+"");
						temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
						temp.put("PMT", "PMT租金");
						temp.put("ZJ", "租金");
						temp.put("SYBJ", "剩余本金");
						temp.put("D", "第");
						temp.put("QI", "期");
						// 删除财务表数据
						Dao.delete("PayTask.deleteBeginning", temp);
						Dao.insert("PayTask.synchronizationBeginning", temp);
						
						//同步中间表
						//刷单条逾期数据
						Dao.update("PayTask.doDunDateByPaylist_code",temp);
						Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE",temp);
						
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return Dao.update("project.doUpdateSIGN_SAVE", map);
	}

	/**
	 * 查询项目所走过的流程列表
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-10-23
	 */
	public List<Map<String, Object>> doShowProjectJbpmList(String PROJECT_ID) {
		return Dao.selectList("project.doShowProjectJbpmList", PROJECT_ID);
	}

	/**
	 * 查询项目所走过的流程列表
	 * 
	 * @param PAY_CODE
	 * @return
	 * @author:Donzell 2013-12-26
	 */
	public List<Map<String, Object>> doShowProjectJbpmList2(String PAY_CODE) {
		return Dao.selectList("project.doShowProjectJbpmList2", PAY_CODE);
	}

	/**
	 * 更新项目的方案政策
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-10-24
	 */
	public int doUpdateSchemeInfo(Map<String, Object> map) {
		return Dao.update("project.doUpdateSchemeInfo", map);
	}

	/**
	 * 根据供应商ID查询供应商账户信息
	 * 
	 * @param SUPPLIER_ID
	 * @return
	 * @author:King 2013-11-12
	 */
	public List<Map<String, Object>> doShowSupAccount(String SUPPLIER_ID) {
		return Dao.selectList("project.doShowSupAccount", SUPPLIER_ID);
	}

	//
	public List<Map<String,Object>> doSuppByClent(String CUST_ID) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CUST_ID", CUST_ID);
		return Dao.selectList("project.doSuppByClent", map);
	}

	public Map<String,Object> doCardByClent(String CUST_ID) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CUST_ID", CUST_ID);
		return Dao.selectOne("project.doCardByClent", map);
	}
	
	public List doListDASB(String CUST_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CUST_ID", CUST_ID);
		return Dao.selectList("project.doListDASB",map);
	}

	/**
	 * 更新设备信息
	 * 
	 * @param map
	 * @author:King 2013-12-7
	 */
	public void updateEquInfo(Map<String, Object> map) {
		Dao.update("project.updateEquInfo", map);
	}

	/**
	 * 查看当前客户是否存在未发起评审流程的项目
	 * 
	 * @param CUST_ID
	 * @return
	 * @author:King 2013-12-9
	 */
	public int isHaveProject(String CUST_ID) {
		return Dao.selectInt("project.isHaveProject", CUST_ID);
	}

	public void delProject(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		Dao.delete("project.delProjectJBPM1", param);
		Dao.delete("project.delProjectJBPM2", param);
		Dao.delete("project.delProjectJBPM3", param);
		Dao.delete("project.delProjectJBPM4", param);
		Dao.delete("project.delProjecteci", param);
		Dao.delete("project.delProjectici", param);
		Dao.delete("project.delProjectici1", param);
		Dao.delete("project.delProjectccr", param);
		Dao.delete("project.delProjectttc", param);
		Dao.delete("project.delProjectttc1", param);
		Dao.delete("project.delProjecttte", param);
		Dao.delete("project.delProjecttma", param);
		Dao.delete("project.delProjectfrb", param);
		Dao.delete("project.delProject1", param);
		Dao.delete("project.delProject2", param);
		Dao.delete("project.delProject3", param);
		Dao.delete("project.delProject4", param);
		Dao.delete("project.delProject5", param);
		Dao.delete("project.delProject6", param);
		Dao.delete("project.delProject7", param);
		Dao.delete("baseScheme.delSchemeClob", id);
	}

	public List queryBankByCustByProId(Map<String, Object> param) {
		return Dao.selectList("project.queryBankByCustByProId", param);
	}

	// public String doShowGuranId(Map<String,Object> map){
	// return Dao.selectOne("project.doShowGuranId", map);
	// }

	/**
	 * 验证银行卡号是否在项目中被使用
	 */
	public int isBankDelCheck(Map<String, Object> map) {
		return Dao.selectInt("project.doShowBankDelCheck", map);
	}

	/**
	 * 验证银行卡号是否在项目中被使用
	 */
	public int isBankDelCheckNew(Map<String, Object> map) {
		return Dao.selectInt("project.doShowBankDelCheckNew", map);
	}
	/**
	 * 流程一览导出excel文件
	 * 
	 * @author 韩晓龙
	 */
	public Excel exportData(Map<String, Object> params) {
		/*modify by lishuo 12.15.2015
		 * columnString add  进件时间        start to 销售人员 	 end 
		 * columnName   add  APP_DATE start to SALE_NAME end
		 */
		BaseUtil.getDataAllAuth(params);
		params.put("ALL", "ALL");
		Page page = this.queryPage(params);
		params.put("tags1", "业务类型");
		params.put("tags2", "项目状态位");
		params.put("tags3", "客户类型");
		// 数据
		List<Map<String, Object>> dataList =page.getData();
		// 表头
		String columnString = "流程状态,流程节点名称,状态,业务类型 ,项目编号,合同编号,客户名称,客户渠道,"
				+ "客户经理,创建时间,进件时间,提件的网点,客户身份信息,产品信息,上一个节点完成时间,其他状态,分公司,车系,当前节点意见,电话号码,期限,融资金额,销售人员,终审时间";
		String columnName = "LC_STATUS,LCNAME,STATUS_NAME,PLATFORM_NAME,PRO_CODE,"
				+ "LEASE_CODE,CUST_NAME,KHQD,CLERK_NAME,CREATE_TIME,APP_DATE,SHOP_NAME,CUST_ID_INFO,SCHEME_NAME,OP_TIME,STATUS_NEW,FGS,CATENA_NAME,WMEMO,TEL_PHONE,LEASE_TERM,"
				+ "FINANCE_TOPRIC,SALE_NAME,ENDTRIAL_UPDDATE";
		String[] columns = columnString.split(",");
		String[] columnNames = columnName.split(",");
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		// 封装excel
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("workFlow" + DateUtil.getSysDate("yyyymmddss") + ".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);

		return excel;
	}

	/**
	 * 查询挂靠公司
	 * 
	 * @param T_ANCHORED_COMPANY_ID
	 *            挂靠公司ID
	 * @return
	 * @author:King 2013-12-26
	 */
	public Map<String, Object> doQueryAnchoredCompany(String T_ANCHORED_COMPANY_ID) {
		return Dao.selectOne("project.doQueryAnchoredCompany", T_ANCHORED_COMPANY_ID);
	}

	/**
	 * 查询票据信息
	 * 
	 * @param PROJECT_CODE
	 * @return
	 * @author:King 2013-12-26
	 */
	public List<Map<String, Object>> toShowFP(String PROJECT_CODE) {
		return Dao.selectList("project.toShowFP", PROJECT_CODE);
	}

	/**
	 * 解析资产包模板数据
	 * 
	 * @param txtFile
	 * @return
	 * @author:King 2014-2-11
	 */
	public Map<String, Object> parseZCBExcel(File txtFile) {
		Map<String, Object> rstMap = new HashMap<String, Object>();
		int fflag = 0;// 用来判断控制验证
		String falseFrameNum = "";// 用以记录重复的车架号
		String emptyFrameNum = "";// 用以记录重复的车架号
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String prod_name = "";// 租赁物名称
			String prod_brand = "";// 租赁物品牌
			String engineer_no = "";// 发动机号
			String frame_no = "";// 车架号
			String manufacturer = "";// 厂商
			String is_new = "";// 是否二手车
			String headOrHang = "";// 头挂
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 1; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (rs.getCell(1, i) != null && !"".equals(rs.getCell(1, i).getContents().trim())) {
					// 如果租赁物名称为空则不再读
					prod_name = rs.getCell(1, i).getContents().trim();
				} else {
					continue;
				}
				// 租赁物品牌
				if (rs.getCell(2, i) != null) {
					prod_brand = rs.getCell(2, i).getContents().trim();
				} else {
					prod_brand = "";
				}
				// 发动机号
				if (rs.getCell(3, i) != null) {
					engineer_no = rs.getCell(3, i).getContents().trim();
				} else {
					engineer_no = "";
				}
				// 车架号
				if (rs.getCell(4, i) != null) {
					frame_no = rs.getCell(4, i).getContents().trim();
					if ("".equals(frame_no)) {
						// 空车架号
						emptyFrameNum = "明细表中有空车架号,请确认!";
					} else {
						// 新增车架号唯一性验证
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("FRAME_NO", frame_no);
						int count = Dao.selectInt("project.queryZcbFrameNo", m);
						if (count >= 1) {
							falseFrameNum += frame_no + ",";// 记录失败的车架号
							fflag++;
						}
					}
				} else {
					// frame_no = "";
					fflag++;// 车架号为空直接不行
					emptyFrameNum = "明细表中有空车架号,请确认!";
				}
				// 厂商
				if (rs.getCell(5, i) != null) {
					manufacturer = rs.getCell(5, i).getContents().trim();
				} else {
					manufacturer = "";
				}
				// 是否二手车
				if (rs.getCell(6, i) != null) {
					is_new = rs.getCell(6, i).getContents().trim();
					if ("是".equals(is_new) || "否".equals(is_new)) {} else {
						fflag++;
					}
				} else {
					fflag++;
				}
				// 头挂
				if (rs.getCell(7, i) != null) {
					headOrHang = rs.getCell(7, i).getContents().trim();
					if ("头".equals(headOrHang) || "挂".equals(headOrHang)) {} else {
						fflag++;
					}
				} else {
					fflag++;
				}
				map.put("PROD_NAME", prod_name);
				map.put("PROD_BRAND", prod_brand);
				map.put("ENGINEER_NO", engineer_no);
				map.put("FRAME_NO", frame_no);
				map.put("MANUFACTURER", manufacturer);
				map.put("IS_NEW", is_new);
				map.put("HEADORHANG", headOrHang);
				list.add(map);
			}
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() == 0) {
			emptyFrameNum = "导入的文件数据为空";
		}
		rstMap.put("EMPTYFRAMENUM", emptyFrameNum);
		rstMap.put("FALSEFRAMENUM", falseFrameNum);
		rstMap.put("list", list);
		return rstMap;
	}

	/**
	 * 插入资产包数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-2-12
	 */
	public int addProjectEquipmentZCB(Map<String, Object> map) {
		return Dao.insert("project.addProjectEquipmentZCB", map);
	}

	/**
	 * 根据项目名称删除资产包数据
	 * 
	 * @param proj_name
	 * @return
	 * @author:King 2014-2-12
	 */
	public int delZCB(String proj_name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_NAME", proj_name);
		return Dao.delete("project.delZCB", map);
	}

	/**
	 * 查询资产包明细
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-2-12
	 */
	public List<Map<String, Object>> QUERYZCBModel(Map<String, Object> map) {
		return Dao.selectList("project.QUERYZCBModel", map);
	}

	/**
	 * 大项目初审报告 --项目信息
	 * 
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2014-2-18
	 */
	public Map<String, Object> queryBigProjectInfo(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PROJECT_ID", PROJECT_ID);
			return Dao.selectOne("project.queryBigProjectInfo", map);
		} else return new HashMap<String, Object>();
	}

	/**
	 * 验证初审报告是否已经存在
	 * 
	 * @param Project_id
	 * @return
	 * @author:King 2014-3-10
	 */
	public int checkBigProjReportExist(String Project_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", Project_id);
		return Dao.selectInt("project.checkBigProjReportExist", map);
	}

//	// 查询平台上的行业
//	public List<Map<String, Object>> HangYeList(Map<String, Object> map) {
//		return Dao.selectList("project.HangYeList", map);
//	}

	public Map<String,Object> queryInfoByEqBase(Map<String, Object> map) {
		return Dao.selectOne("project.queryInfoByEqBase", map);
	}
	
	// add gengchangbao 2016年5月5日  JZZL-173 start
	//合同相关信息
	public Map<String,Object> queryContractDetailInfo(Map<String, Object> map) {
		return Dao.selectOne("project.queryContractDetailInfo", map);
	}
	// add gengchangbao 2016年5月5日  JZZL-173 end
	// 查询该项目所选择的设备
	public List<Map<String,Object>> queryEqByProjectIDByScheme(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByProjectIDByScheme", param);
	}

	// 根据合同id查询支付表id
	public int queryIdByProjectIdRowNum(Map<String,Object> param) {
		return Dao.selectInt("project.queryIdByProjectIdRowNum", param);
	}

	public void updateEqInfo(Map<String,Object> param) {
		Dao.update("project.updateEqInfo", param);
	}
	
	public void updateProjectHeadByUp(Map param){
		Dao.update("project.updateProjectHeadByUp", param);
	}
	//项目主表中修改担保方式
	public void updateProjectHeadByUpId(Map<String,Object> param){
		Dao.update("project.updateProjectHeadByUpId", param);
	}
	public int SchemeDelete(Map param) {
		// 查询该方案的设备总价
		double money = Dao.selectDouble("project.querySchemeEqTopic", param);
		param.put("MONEY", money);
		Dao.update("project.updateProjectById", param);
		int PAY_ID = Dao.selectInt("project.queryIdByProjectIdRowNum", param);
		param.put("PAY_ID", PAY_ID);
		Dao.delete("project.deleteDetailByPayId", param);
		Dao.delete("project.deleteSchemeByPayId", param);
		return Dao.delete("project.deleteEQBYROWNUM", param);
	}

	public int saveBaseInfo(Map param) {
		//如果为联合租赁，先保存联合租赁融资公司，先删除在插入
		if("8".equals(param.get("PLATFORM_TYPE")+"")){
			Dao.delete("project.deleteFlProject",param);
			
			//保存联合融资公司（只有为联合租赁时候才有值）
			JSONArray fljsonArray = JSONArray.fromObject(param.get("flList"));
			for (Object object : fljsonArray) {
				Map<String, Object> m = (Map<String, Object>) object;
				m.put("PROJECT_ID", param.get("PROJECT_ID"));
				this.doAddFlProject(m);
			}
		}
		return Dao.update("project.saveBaseInfo", param);
	}

	public void delSh(String id) {
		Map<String, Object> m = Dao.selectOne("project.getHsInfo", id);
		if (m != null && m.get("JBPM_ID") != null) {
			m.put("STATUS", "0");
			Dao.update("project.updateProjectStatusByIdJBPMNull", m);
			JBPM.endProcessInstance((String) m.get("JBPM_ID"), "项目收回");
		} else {
			throw new ActionException("不符合收回要求");
		}

	}
	
	public Map<String,Object> querySuppByProjectID(String PRO_ID){
		return Dao.selectOne("project.querySuppByProjectID", PRO_ID);
	}
	
	
	//分数查询 Add By YangJ 2014年5月8日15:01:14
	public Map<String,Object> queryScore(String proj_id,String sup_id,String prod_id){//项目\资信 ID ->信誉分；客户ID ->客户分；供应商ID ->供应商分；产品ID ->产品分
		Map<String, Object> m =new HashMap<String, Object>();
		Integer cust_id=Dao.selectOne("project.queryCustid",proj_id);
		if(!proj_id.equals("") && !cust_id.equals("")){
			
			m.put("PROJ_SCORE", Dao.selectOne("project.queryXYScore", proj_id));
			List<Map<String,Object>> custScore=Dao.selectList("project.queryCunScore", cust_id);
			//担保人评分
			double dbrscore= Dao.selectDouble("project.queryScoreForDBR", cust_id) ;
			
			if(custScore.size()==0){
				m.put("CUST_SCORE",0);
			}
			else if(custScore.size()!=0){
				double cust_Score_1 = Double.parseDouble((custScore.get(0)).get("SCORE")+"")  ;
				double cust_Score_2 = dbrscore*0.1f ;
				double cust_Score = cust_Score_1 + cust_Score_2 ;
				m.put("CUST_SCORE",cust_Score);
			}
			
			if(!sup_id.equals("") && !prod_id.equals(""))
			{
				m.put("SUP_SCORE", Dao.selectOne("project.querySupScore", sup_id));
				m.put("PROD_SCORE", Dao.selectOne("project.queryProdScore", prod_id));
			}
			else
			{
				m.put("SUP_SCORE",0);
				m.put("PROD_SCORE", 0);
			}
			return m;
		}
		else{
			return m;
		}
	}
	
	//分数查询 Add By YangJ 2014年5月8日15:01:14
	public Map<String, Object> queryScoreAdd(String cust_id){//项目\资信 ID ->信誉分；客户ID ->客户分；供应商ID ->供应商分；产品ID ->产品分
			Map<String, Object> m =new HashMap<String, Object>();
			
			List custScore=Dao.selectList("project.queryCunScore", cust_id);
			if(custScore.size()==0){
				m.put("CUST_SCORE",0);
			}
			else if(custScore.size()!=0){
				m.put("CUST_SCORE",((Map)custScore.get(0)).get("SCORE"));
			}
			return m;
		
	}
		
	
	//由项目id查找业务类型
	public Map<String,Object> QueryBusType(String pro_id){
		Map<String,Object> m1=Dao.selectOne("project.queryBusid",pro_id);
		List<Map<String,Object>> l1=new SysDictionaryMemcached().get("业务类型");
		String PLATFORM_TYPE=null;
		for(Map<String,Object> l:l1){
			System.out.println(m1.get("PLATFORM_TYPE")+"============"+l.get("CODE").toString()+"==="+l.get("FLAG"));
			PLATFORM_TYPE=l.get("CODE").toString();
			if(PLATFORM_TYPE.equals(m1.get("PLATFORM_TYPE"))){
				System.out.println("aaaaaaaaaaaa");
				m1.put("PLATFORM_TYPE", l.get("FLAG"));
				break;
			}
		}
		System.out.println("aaaaaaaaaaaa="+m1);
		return m1;
		
	}
	
	//2014年5月9日18:49:43 YangJ 类似action中的那一段查分数的代码 下载提供给 项目查看和修改页面用
	public String Fen(String pro_id){
		//获取评分！--------------------------------------------------------------------获取评分--YangJ--2014年5月8日15:41:11
		
		
		Map m1= queryScore(pro_id, "", "");
		
		float i1=Float.parseFloat (m1.get("PROJ_SCORE").toString()) ;//信用分数   都是原始表中的分数
		float i2=Float.parseFloat (m1.get("CUST_SCORE").toString());//客户分数
		float i3=Float.parseFloat (m1.get("SUP_SCORE").toString());//供应商分数
		float i4=Float.parseFloat (m1.get("PROD_SCORE").toString());//产品分数

		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		
		List datadic=DDMservice.get("项目打分评估系数");//数据字典分数百分比配置
		
		float projscore=0;//信用分数
		float custscore=0;//客户分数
		float supscore=0;//供应商分数
		float prodscore=0;//产品分数
		
		for(Object obj:datadic){
			Map<String,String> temp = (Map<String,String>)obj;
		if(temp.get("CODE").equals("1"))
			custscore=Integer.parseInt(temp.get("SHORTNAME"))*i2/100;//客户分数
			else if(temp.get("CODE").equals("2"))
				supscore=Integer.parseInt(temp.get("SHORTNAME"))*i3/100;//供应商分数
				else if(temp.get("CODE").equals("4"))
					projscore=Integer.parseInt(temp.get("SHORTNAME"))*i1/100;//信用分数
					else if(temp.get("CODE").equals("5"))
						prodscore=Integer.parseInt(temp.get("SHORTNAME"))*i4/100;//产品分数
					else
						System.out.println("错误！！没有取到数据字典CODE码");
		}
		
		
		Map<String,Object> score=new HashMap<String,Object>();
		score.put("ALL_SCORE", projscore+custscore+supscore+prodscore);
		score.put("PROJ_SCORE", projscore);//信用分数
		score.put("CUST_SCORE", custscore);//客户分数
		score.put("SUP_SCORE", supscore);//供应商分数
		score.put("PROD_SCORE", prodscore);//产品分数
		score.put("title","客户分数:"+custscore+" 供应商分数:"+supscore+" 信用分数:"+projscore+" 产品分数:"+prodscore);
//		System.out.println("ACTION:输出：总分："+score.get("ALL_SCORE")+"信用分数"++score.get("PROJ_SCORE")+"客户分数"+score.get("CUST_SCORE")+"供应商分数"+score.get("SUP_SCORE")+"产品分数"+score.get("PROD_SCORE"));
		return score.get("ALL_SCORE").toString();
		//-------------------------------------------------------------------------获取评分结束------
		
	}

	public Map<String,Object> getBaseSchemeBySchemeCode(Map<String, Object> m) {
		return Dao.selectOne("project.getBaseSchemeBySchemeCode", m);
	}
	
	/**
	 *add by lishuo 03-15-2016 
	 * @param m
	 * @return
	 */
	public Map<String,Object> queryBaseScheme(Map<String, Object> m) {
		return Dao.selectOne("project.queryBaseScheme", m);
	}

	/**
     *add by lishuo 03-30-2016 
     * @param m
     * @return
     */
    public Map<String,Object> queryPolicyTimeByBaseScheme(Map<String, Object> m) {
        return Dao.selectOne("project.queryPolicyTimeByBaseScheme", m);
    }
	
	/**
	 * 
	 * @Description: 上传文件处理
	 * @author cxq
	 * @return Map
	 * @throws Exception
	 */
	public Object uploadFileForOne(List<FileItem> items) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			String customerPath = File.separator + "FIL_ATTACHMENT";

			final long MAX_SIZE = 524288 * 2 * 1024;
			DiskFileItemFactory fif = new DiskFileItemFactory();
			fif.setSizeThreshold(4096);
			String root = SkyEye.getConfig("file.path").toString();
			// 拿到配置文件中设置的上传文件路径

			createDirectory(root);// 调用创建存放上传文件 文件夹方法

			ServletFileUpload fp = new ServletFileUpload(fif);
			fp.setHeaderEncoding("UTF-8");
			fp.setSizeMax(MAX_SIZE);

			Iterator<?> it = items.iterator();
			List filepathList = new ArrayList();

			File file = null;

			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// 页面参数
					dataMap.put(fileItem.getFieldName(), new String(fileItem
							.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} else {
					String dir = root
							+ File.separator
							+ customerPath
							+ File.separator
							+ new SimpleDateFormat("yyyy-MM-dd")
									.format(Calendar.getInstance().getTime());

					createDirectory(dir);
					file = new File(dir + File.separator + fileItem.getName());
					fileItem.write(file);
					dataMap.put("FILE_SIZE", fileItem.getSize());
					dataMap.put("FILE_NAME", fileItem.getName());
					dataMap.put(
							"FILE_TYPE",
							file.getPath()
									.toString()
									.substring(
											file.getPath().toString()
													.lastIndexOf("."),
											file.getPath().toString().length()));
					dataMap.put("FILE_PATH",
							file.getPath().toString().replace("\\", "/"));
					filepathList.add(file.getAbsoluteFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	
	public boolean createDirectory(String path) {
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
	
	//添加音像资料
		public int doInsertAttachment(Map<String, Object> map) {
			map.put("CREATE_CODE", Security.getUser().getCode());
			map.put("CLIENT_ID", map.get("CUST_ID")) ;
			return Dao.insert("project.doInsertAttachment",map) ;
		}
	//查看音像资料
	public List<Map<String,Object>> findAttachments(Map<String,Object> param){
		return Dao.selectList("project.findAttachments", param) ;
	}	
	public File downloadFileForZip(Map<String, Object> param) {
		JSONArray jsonArray = JSONArray.fromObject(param.get("ROWS"));
		List<File> listFile = new ArrayList<File>();
		try {
			for (Object object : jsonArray) {
				Map<String, Object> map = (Map) object;
				File file = new File(map.get("FILE_PATH").toString());
				listFile.add(file);
			}
	
			if (listFile != null && listFile.size() > 0) {
				byte[] buffer = new byte[1024];
				String root = SkyEye.getConfig("file.path").toString();		
				String filePath =  root + File.separator + "temp" ;
				String strZipName = "附件文件.zip";
				createDirectory(filePath);
				String fullZipPath = filePath + File.separator + strZipName ;
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fullZipPath));
				for (File fileTemp : listFile) {
					FileInputStream fis = new FileInputStream(fileTemp);

					out.putNextEntry(new ZipEntry(fileTemp.getName()));

					int len;

					// 读入需要下载的文件的内容，打包到zip文件

					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
				out.close();
				File file = new File(fullZipPath.replace("\\", "/")) ;
				return file ;
			}
		} catch (Exception e) {
			return null ;
		}
		return null;
	}
	
	
	//通过项目ID查询出联合租赁公司
	public List<Map<String,Object>> getFLByProjectId(Map<String,Object> map){
		return Dao.selectList("project.getFLByProjectId", map);
	}
	
	//查询出联合租赁公司
	public List getFLInfo(){
		return Dao.selectList("project.getFLInfo");
	}


	public List queryFLBYMana(Map map){
		return Dao.selectList("project.queryFLBYMana",map);
	}
	
	public List<Map<String,Object>> queryACCOUNTTYPE(Map<String,Object> map)
	{
		return Dao.selectList("project.queryACCOUNTTYPE",map);
	}
	
	
	public int isDunCust(Map map){
		int num=0;
		//num=0:新客户无逾期，num=1：老客户无逾期，num=2：老客户有逾期
		//先查询是否是新客户
		if(Dao.selectInt("project.queryISNewCust", map)>0){
			num=0;
		}
		else if(Dao.selectInt("project.queryIsDunCust", map)<=0){
			num=1;
		}
		else{
			num=2;
		}
		return num;
	}
	
	// 逾期明细
	public List query_overdue_AllByCust(Map<String, Object> map) {
		return Dao.selectList("project.query_overdue_AllByCust", map);
	}

	public boolean deleteFile(Map<String, Object> param) {
		JSONArray jsonArray = JSONArray.fromObject(param.get("ROWS"));
		for(Map<String,Object> map :(List<Map<String,Object>>)jsonArray){
			String filePath = map.get("FILE_PATH").toString() ;
			File file = new File(filePath) ;
			if(file.delete()){
				Dao.delete("project.deleteFile",map) ;
			}else{
				return false ;
			}
		}
		
		return true;
	}
	
	/**
	 * 查询所有使用客户信息作为共同承租人
	 * @return
	 * @author King 2015年3月5日
	 */
	public List<Map<String,Object>> queryJOINT_TENANT(Map<String,Object> map){
		map.put("TYPE_", "客户类型");
		return Dao.selectList("project.queryJOINT_TENANT",map);
	}
	
	/**
	 * 查询公司平台下的区域
	 * @param MANAGER_ID 公司平台
	 * @param parent_id  显示层级区域的父ID
	 * @param area_leve 父ID级别  国家1 省2 市3 县 4
	 * @return
	 * @author King 2015年3月6日
	 */
	public List<Object> queryManagerArea(String MANAGER_ID,String parent_id,int area_leve){
		List<Object> lst=new ArrayList<Object>();
		AreaService aservice=new AreaService();
		Map<String,Object> mm=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parent_id)){
			mm.put("PARENT_ID", parent_id);
			lst.addAll(aservice.getSubset(mm));
		}else{
			mm.put("ID", MANAGER_ID);
			List<Map<String,Object>>list=new CompanyManagerService().queryArea(mm);
			for (Map<String, Object> map : list) {
				String []AREAID=null;
				if(map.containsKey("AREAID")&&StringUtils.isNotBlank(map.get("AREAID"))){
					AREAID=map.get("AREAID").toString().split(",");
					
					if(AREAID.length>=area_leve){
						map.put("ID", AREAID[area_leve-1]);
//						map.put("PARENT_ID", AREAID[area_leve-1]);
						lst.add(aservice.selectArea(map));
//						lst.addAll(aservice.getSubset(map));
					}else{
						map.put("PARENT_ID", AREAID[area_leve-2]);
						lst.addAll(aservice.getSubset(map));
					}
				}
			}
		}
		return lst;
	}
	
	/**
	 * 添加项目客户银行卡号
	 * @param map
	 * @return
	 * @author King 2015年3月7日
	 */
	public String addProjectCustBank(Map<String,Object> map){
		String ID=Dao.getSequence("seq_fil_cust_openingbank");
		map.put("ID", ID);
		Dao.insert("project.addProjectCustBank",map);
		return ID;
	}
	
	public void updateProjectCustBank(Map<String,Object> map){
		Dao.update("project.updateProjectCustBank",map);
	}
	
	/*查询销售人员
	 * add by lishuo  
	*/
	public List<Map<String,Object>> querySaller(Map<String,Object> map){
		return Dao.selectList("project.querySaller",map);
	}
	
	//查询经销商或者SP的省市
	public Map<String,Object> queryAreaSupMap(Map<String,Object> map){
		return Dao.selectOne("project.queryAreaSupMap", map);
	}
	
	public Map<String,Object> queryBusinMap(Map<String,Object> map){
		return Dao.selectOne("project.queryBusinMap", map);
	}
	
	public List<Map<String,Object>> queryAccountTypeCust(Map<String,Object> map){
		return Dao.selectList("project.queryAccountTypeCust",map);
	}
	
	//验证银行账号是否重复
	public int findBank(Map<String,Object> map){
		return Dao.selectInt("project.findBank", map);
	}
	//验证有没有此条银行记录
	public int selectBandId(Map<String,Object> map){
		return Dao.selectInt("project.selectBankId", map);
	}
	
	public int doAddFeedBackProjectId(Map<String,Object> map){
		return Dao.insert("project.addFeedBackProjectId", map);
	}
	public int doAddAppProjectId(Map<String,Object> map){
		return Dao.insert("project.addAppProjectId", map);
	}
	//查看项目是否做过放款
	public int toSearchPayment(Map<String,Object> map){
		return Dao.selectInt("project.toSearchPayment", map);
	}
	//根据PROJECT_ID查询CUST_ID
	public Map<String,Object> selectGuarantor(Map<String, Object> map){
		return Dao.selectOne("creditGuarantor.selectGuarantor", map);
	}
	//根据CUST_ID查询担保人信息
	public Map<String,Object> selectGuarantorAll(Map<String, Object> map){
		return Dao.selectOne("customers.selectGuarntorAll", map);
	}
	//根据CUST_ID删除担保人信息
	public int delGuarantor(Map<String, Object> map){
		return Dao.delete("customers.delGuarantor", map);
	}
	//根据PROJECT_ID删除担保人关联
	 public int delGuarGL(Map<String, Object> map){
		return Dao.delete("creditGuarantor.deleteGuarantor", map);
	 }
	
//	 public static void main(String[] args) {
//		projectService p=new projectService();
//		List<Map<String,Object>> payList=new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < 36; i++) {
//			Map<String,Object> map=new HashMap<String, Object>();
//			int m=i+1;
//			map.put("PERIOD_NUM", m);
//			map.put("ZJ", "7744");
//			payList.add(map);
//		}
//		Map<String,Object> map=new HashMap<String, Object>();
//		map.put("SCHEME_SYL_BZ_VALUE", "0.095688");
//		map.put("LEASE_TERM", "36");
//		map.put("LEASE_PERIOD", "1");
//		map.put("FINANCE_TOPRIC", "160000");
//		map.put("LEASE_TOPRIC", "200000");
//		map.put("MQGLF", "1300");
//		
//		p.payList(map, payList);
//	}
	 
	 /**
	  * 捷翊还款计划导出准备数据
	  * @param map
	  * @param payList
	  * @return
	  * @author King 2015年8月11日
	  */
	 public List<Map<String,Object>> payList(Map<String,Object> map,List<Map<String,Object>> payList){
		 //内部标准收益率
		 double RATE=0d;
		 if(StringUtils.isBlank(map.get("SCHEME_SYL_BZ_VALUE")))
			 RATE=Double.parseDouble(map.get("YEAR_INTEREST")+"");
		 else{
			 RATE=Double.parseDouble(map.get("SCHEME_SYL_BZ_VALUE")+"");
			 RATE=RATE/100;
		 }
		 //租期
		 int LEASE_TERM=Integer.parseInt(map.get("LEASE_TERM")+"");
		 //周期
		 int LEASE_PERIOD=Integer.parseInt(map.get("LEASE_PERIOD")+"");
		 //融资额
		 double FINANCE_TOPRIC=Double.parseDouble(map.get("FINANCE_TOPRIC")+"");
		 
		 //每期车牌费
		 double MQGLF=0;
		 if(StringUtils.isNotBlank(map.get("MQGLF")))
			 MQGLF=Double.parseDouble(map.get("MQGLF")+"");
		 //平息租金
		 double pxzj=Double.parseDouble(payList.get(0).get("ZJ")+"");
		 
		 //现值
		 double pv=UtilFinance.formatDouble0(Finance.PV(RATE/12*LEASE_PERIOD, LEASE_TERM, pxzj));
		 double vpv=pv;
		//纯收入
		 double inCome=pv-FINANCE_TOPRIC-MQGLF*LEASE_TERM;
		 double pvv=FINANCE_TOPRIC+MQGLF*LEASE_TERM;
		 for (Map<String, Object> map2 : payList) {
			int per=Integer.parseInt(map2.get("PERIOD_NUM")+"");
			double bj=UtilFinance.formatDouble2(-Finance.ppmt(RATE/12*LEASE_PERIOD, per, LEASE_TERM, vpv));
			//剩余本金
			pv=pv-bj;
			//提前结清退服务费
			double lfwf=UtilFinance.formatDouble2(RefundsForJieYi.reFundsPercent(inCome, pvv, LEASE_TERM, LEASE_TERM-per, 0.05)*pvv);
			double lMQGLF=UtilFinance.formatDouble2(MQGLF*(LEASE_TERM-per));
			
			double pmtzj=UtilFinance.formatDouble2(pxzj+pv-lfwf-lMQGLF);
			map2.put("PMTZJ", pmtzj);
			map2.put("REMARK", "");
		}
		 return payList;
	 }
	 
	/**
	 * 导出Excel
	 * @modified yipan 2016年1月26日 15:31:24
	 * @param list	数据集
	 * @param param	合同编号和承租方
	 * @return
	 */
	public Excel exportData(List<Map<String,Object>> list, Map<String,Object> param) {
		Excel excel = new Excel();
		excel.setAutoColumn(25);
		excel.addSheetName("还款计划");
		excel.addData(list);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("PERIOD_NUM", "期数");
		title.put("PAY_DATE", "还款日期");
		title.put("ZJ", "月还租金");
		title.put("PMTZJ", "提前结清款");
		excel.addTitle(title);
//			excel.hasRownum("编号");
		excel.setHasTitle(true);
		
		// 添加行title显示承租方和合同编号
		Map<String,Object> customerAndProject = Dao.selectOne("project.queryCustomerAndProjectByProjectID", param);
		if(null != customerAndProject){
			String LEASE_CODE = "承租方:" + customerAndProject.get("NAME");
			String CUST_SIGN_NAME = "          合同编号：" + customerAndProject.get("LEASE_CODE");
			
			excel.setHeadTitles(LEASE_CODE + CUST_SIGN_NAME);
			excel.setHeadDate(true);
			excel.setAutoHeadDate(false);
		}
		
		return excel;
	}
		
	/**
	 * 根据客户ID，查询客户的地域，用于同步区域
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryCustProAndCity(Map<String, Object> param){
		return Dao.selectOne("project.queryCustProAndCity", param);
	}
	//项目表中查询共同承租人类型\id
	public Map<String, Object> selectGTById(Map<String, Object> param){
		return Dao.selectOne("project.selectGTById", param);
	}
	//项目表中查询共同承租人类型\id
	public int delGTCust(Map<String, Object> param){
		return Dao.delete("project.delGTCust", param);
	}
	public int updateGTById(Map<String, Object> param){
		return Dao.update("project.updateGTById", param);
	}
	//删除方案
	public int delscheme(Map<String, Object> param){
		Dao.delete("project.delclob",param);
		Dao.delete("project.delDetail",param);
		return Dao.delete("project.delscheme",param);
	}
	
	//删除方案
	public int delschemeProjectId(Map<String, Object> param){
		Dao.delete("project.delclobProjectId",param);
		Dao.delete("project.delDetail",param);
		return Dao.delete("project.delschemeProjectId",param);
	}
		
	public int deleq(Map<String, Object> param){
		return Dao.delete("project.deleq",param);
	}
//	更新融资额
	public String selectDBID(Map<String, Object> map){
		return Dao.selectOne("project.selectDBID", map);
	}
	public int upRZE(Map<String, Object> map){
		return Dao.update("project.upRZE", map);
	}
	//查看音像资料
	public List<Map<String,Object>> findProjectList(Map<String,Object> param){
		return Dao.selectList("project.findprojectlist", param) ;
	}	
	/**
	 * 保存修改明细
	 * @param map
	 * @return
	 */
	public int updateSjmx(Map<String, Object> param) {
		
		String[] SIGN = param.get("SIGN").toString().split(",");			// 页面选择的费用款项内容，以,号分隔
		String[] SIGNVALUE = param.get("SIGNVALUE").toString().split(",");	// 页面选择的费用款项值，已,号分隔
		
		String REDURE_SUM_MONEY = param.get("REDURE_SUM_MONEY")==null?"0":param.get("REDURE_SUM_MONEY").toString();	// 扣减总额
		String WAIT_SUM_MONEY = param.get("WAIT_SUM_MONEY")==null?"0":param.get("WAIT_SUM_MONEY").toString();		// 待请款金额
		
		List<Map<String,Object>> list = findXmmxlist(param);
		
		// 如果有数据，则更新当前项目的实际费用明细IS_PAY(是否请款)为0，未请款
		if(null != list && list.size() > 0){
			param.put("IS_PAY", 0);
			Dao.update("project.updateSjmxIsPay", param);
		}
		
		int result = 0;
		
		for (Map<String, Object> map : list) {
			
			for (int i = 0; i < SIGN.length; i++) {
			
				String COUSTNAME = map.get("COUSTNAME")==null?"":map.get("COUSTNAME").toString();
				
				// 如果页面值和数据库查询出来的语句匹配，则将数据存入表中，并更新ISPAY，表示已请款
				if(COUSTNAME.equals(SIGN[i])){
					
					if("车辆内部采购价".equals(SIGN[i])){
						map.put("REDURE_SUM_MONEY", REDURE_SUM_MONEY);
						map.put("WAIT_SUM_MONEY", WAIT_SUM_MONEY);
					}
					
					map.put("APPLY_MONEY", SIGNVALUE[i]);
					
					result += Dao.update("project.updateSjmx", map);
					
					break;
				}
				
			}
			
		}
		
		return result;
	}
	
	/**
	 * 根据ProjectId得到ProjectDetailBase类型和价格
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findXmmxlist(Map<String,Object> param){
		return Dao.selectList("project.findXmmxlist", param);
	}
	
	/**
	 * 根据ProjectId得到ProjectDetailBase类型和价格
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findReSignXmmxlist(Map<String,Object> param){
		return Dao.selectList("project.findReSignXmmxlist", param);
	}

	/**
	 * 根据ProjectId得到ProjectDetailBase差额汇总
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findXmmxBalanceList(Map<String, Object> param) {
		return Dao.selectList("project.findXmmxBalanceList", param);
	}

	/**
	 * 根据ProjectId得到ProjectDetailBase类型和价格(取得已经请款的记录)
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findXmmxPayOklist(Map<String, Object> param) {
		return Dao.selectList("project.findXmmxPayOklist", param);
	}

	/**
	 * add by lishuo 12.10.2015
	 * 首期租金预付月数 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> QueryYSZJQC(Map<String, Object> map) {
		return Dao.selectList("project.QueryYSZJQC",map);
	}

	/**
	 * add by lishuo 12.10.2015
	 * 查询租金利率
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> QueryRent(Map<String, Object> map) {
		return Dao.selectList("project.QueryRent",map);
	}
	
	/**
	 * add by lishuo 12.10.2015
	 * 查询进件12W
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> CheckTotalPrice(Map<String, Object> map) {
		return Dao.selectList("project.CheckTotalPrice",map);
	}
	/**
	 * add by gengchangbao 2016年3月1日18:08:04
	 * 查询重签合同前版【废弃】项目编号
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryParentProjectById(Map<String,Object> param) {
		return Dao.selectList("project.queryParentProjectById", param);
	}
	/**
	 * @author 吴国伟
	 * @param Project_id
	 *            项目ID
	 * @return string
	 */
	public String moveProjectData(String PROJECT_ID) {
		/*
		 * 复制项目主表 fil_project_head
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		// 新项目ID
		String NEW_PROJECT_ID = Dao.getSequence("SEQ_FIL_PROJECT_HEAD");
		param.put("NEW_PROJECT_ID", NEW_PROJECT_ID);
		log.info("新项目ID：" + NEW_PROJECT_ID);
		// 原项目主表信息
		Map oProDetail = Dao.selectOne("project.selectProjectDetails", param);

		int reType = Dao.insert("project.newProject", param);
		if (reType < 1)
			return "插入主表错误";
		log.info("主表插入状态：" + reType);

		oProDetail.put("NEW_PROJECT_ID", NEW_PROJECT_ID);
		// 新项目编号
		String PRO_CODE = createProCode(oProDetail);
		param.put("PRO_CODE", PRO_CODE);
		log.info("新项目编号：" + PRO_CODE);
		Dao.update("project.newProjectCode", param);
		if (reType < 1)
			return "更新项目编号错误";
		log.info("更新项目编号状态：" + reType);
		/*
		 * 项目方案 FIL_PROJECT_SCHEME
		 */
		// 原方案ID
		int projectSchemeID = Dao.selectInt("project.selectMaxProjectScheme",
				param);
		param.put("SCHEME_ID", projectSchemeID);
		// 新方案ID
		String SCHEME_ID_SEQ = Dao.getSequence("SEQ_FIL_PROJECT_SCHEME");
		param.put("SCHEME_ID_SEQ", SCHEME_ID_SEQ);
		log.info("方案主表ID：" + SCHEME_ID_SEQ);
		reType = Dao.insert("project.newProjectScheme", param);
		log.info("方案主表插入状态：" + reType);
		if (reType < 1)
			return "插入方案主表错误";
		/*
		 * 项目方案 明细 FIL_RENT_PLAN_DETAIL_SCHEME
		 */
		reType = Dao.insert("project.newPayParamScheme", param);

		log.info("方案明细插入状态：" + reType);

		if (reType < 1)
			return "插入方案明细主表错误";
		/*
		 * 插入设备明细 FIL_PROJECT_EQUIPMENT
		 */
		reType = Dao.insert("project.newProjectEquipment", param);

		log.info("设备明细插入状态：" + reType);
		if (reType < 1)
			return "插入设备错误";

		/*
		 * 担保人信息 FIL_PROJECT_GUARAN
		 */
		reType = Dao.insert("project.newGuaran", param);
		log.info("担保人明细插入状态：" + reType);
		// if (reType < 1)
		// return "插入担保人错误";

		/*
		 * 电话调查表 FIL_PROJECT_TELCALL_INFO
		 */
		reType = Dao.insert("project.newTelcall", param);
		log.info("电话调查明细插入状态：" + reType);
		// if (reType < 1)
		// return "插入电话调查明细错误";

		/*
		 * 附件信息 FIL_PROJECT_FILE
		 */
		reType = addNewFile(param);
		log.info("附件明细插入状态：" + reType);
		// if (reType < 1)
		// return "插入附件明细错误";
		reType = Dao.update("project.updateOldPayRentHead", param);
		log.info("更新原支付表状态-作废：" + reType);
		return "success";
	}

	/**
	 * 迁移附件
	 * 
	 * @author 吴国伟
	 * @param param
	 * @return
	 */
	public int addNewFile(Map param) {
		/*
		 * 附件信息 FIL_PROJECT_FILE
		 */
		int reType = Dao.insert("project.newProjectFile", param);
		log.info("附件明细插入状态：" + reType);
		List<Map<String, Object>> list = Dao.selectList("project.queryNewFile",
				param);
		for (Map<String, Object> m : list) {
			int f = Dao.update("project.updateNewFileParentId", m);
			log.info("更新状态：" + f);
		}
		return reType;
	}

	/**
	 * 创建项目编号
	 * 
	 * @author 吴国伟
	 * @return
	 */
	public String createProCode(Map m) {
		String PRO_CODE = "";
		if ("4".equals(m.get("PLATFORM_TYPE").toString())
				&& "back_leasing".equals(m.get("LEASE_MODEL").toString())) {
			m.put("PARENT_ID", this.getProjectId(m.get("PRO_CODE").toString()));
			PRO_CODE = m.get("PRO_CODE").toString() + "_SH";
		} else {
			PRO_CODE = CodeService.getCode("项目编号", m.get("CLIENT_ID")
					.toString(), m.get("NEW_PROJECT_ID").toString());
		}
		return PRO_CODE;
	}

	/**
	 * @author 吴国伟
	 * @param m
	 */
	public void addSchemeEquipment(Map m) {
		// 方案信息
		m.put("BASE_SCHEME", getBaseSchemeBySchemeCode(m));
		// 查询基本信息
		Map<String, Object> baseMap = queryInfoByEqBase(m);

		// 设备信息
		List<Map<String, Object>> eqlist = queryEqByProjectIDByScheme(m);

	}

	/**
	 * 查询项目方案信息
	 * 
	 * @param m
	 * @return
	 */
	public Map<String, Object> queryEquipmentSchemeByProjectID(Map m) {
		return Dao.selectOne("project.queryEquipmentSchemeByProjectID", m);
	}

	/**
	 * 查询项目父ID
	 * 
	 * @param m
	 * @return
	 */
	public Map<String, Object> queryOldProjectByID(Map m) {
		return Dao.selectOne("project.queryOldProjectByID", m);
	}
	/**
	 * 重签列表
	 * @param m
	 * @return
	 */
	public Page queryPageCQ(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "project.getAllProjectCQ", "project.getAllProject_countCQ");
	}
	
	/**
	 * 重签列表
	 * @param m
	 * @return
	 */
	public Page queryPageDeposit(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "project.queryPageDeposit", "project.queryPageDeposit_COUNT");
	}
	
	/**
	 * 插入项目比对数据
	 * @param m
	 * @return
	 */
	public int insertComparisonData(Map<String,Object> m,Object project_id){
		m.put("PROJECT_ID",  project_id);
		return Dao.insert("project.insertComparisonData", m);
	}
	/**
	 * 查询比对数据
	 * @param m
	 */
    public void queryProjectComparison(Map<String,Object> m){
    	/*删除原数据*/
    	deleteComparison(m);
    	
    	Map<String, Object> map = queryEquipmentSchemeByProjectID(m);
		Map<String,Object> imap;
		m.putAll(map);
		m.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		m.put("BASE_SCHEME", getBaseSchemeBySchemeCode(m));
		// 查询基本信息
		Map<String, Object> baseMap = queryInfoByEqBase(m);

		List<Map<String, Object>> eqlist = queryEqByProjectIDByScheme(m);
		Map<String,Object> eqOne  = eqlist.get(0);
		Set seteqOne = eqOne.entrySet();
		Iterator iteratorEqOne = seteqOne.iterator();
		    while(iteratorEqOne.hasNext()){
		    	Map.Entry<String, Object> it= (Map.Entry<String, Object>)iteratorEqOne.next();
		    	imap = new HashMap<String,Object>();
		    	if("SUPPLIERS_NAME".equals(it.getKey())){
		    		//字典
		    		imap.put("KEY", "经销商");
		    		imap.put("SORTS", "1");
		    	}if("COMPANY_NAME".equals(it.getKey())){
		    		imap.put("KEY", "厂商");
		    		imap.put("SORTS", "2");
		    	}
		    	if("PRODUCT_NAME".equals(it.getKey())){
		    		imap.put("KEY", "品牌");
		    		imap.put("SORTS", "3");
		    	}
		    	if("CATENA_NAME".equals(it.getKey())){
		    		imap.put("KEY", "车系");
		    		imap.put("SORTS", "4");
		    	}
		    	if("SPEC_NAME".equals(it.getKey())){
		    		imap.put("KEY", "型号");
		    		imap.put("SORTS", "5");
		    	}if("UNIT_PRICE".equals(it.getKey())){
		    		imap.put("KEY", "4S店采购价(元)");
		    		imap.put("SORTS", "6");
		    	}if("ACTUAL_PRICE".equals(it.getKey())){
		    		imap.put("KEY", "实际融车价(元)");
		    		imap.put("SORTS", "7");
		    	}
		    	if(null != imap.get("KEY")){
		    		imap.put("VALUE", it.getValue());
		    		if(null != it.getValue() && !"".equals(it.getValue())){
			    		insertComparisonData(imap,m.get("PROJECT_ID"));
			    	}
			    	//insertComparisonData(imap,m.get("PROJECT_ID"));
		    	}
		    	
		    }
		// 通过平台查厂商
//		CompanyService comService = new CompanyService();
//		context.put("CompanyList", comService.queryCompanyList(m));		
//		// 单位
//		context.put("unitList", new DataDictionaryMemcached().get("设备单位"));
//
//		// 通过平台查询出行业
//		List<Map<String, Object>> HangYeList = new BaseSchemeService()
//				.getFHFA_MANAGERSUBINFO(m.get("MANAGER_ID") + "", Security
//						.getUser().getOrg().getPlatform(), "行业类型");
//		// List HangYeList = service.HangYeList(m);
//		context.put("HangYeList", HangYeList);
//
//		// 区域（平台下的区域到省）
//		context.put(
//				"AREAS",
//				service.queryManagerArea(Security.getUser().getOrg()
//						.getPlatformId(), null, 2));

		// king查方案
		BaseSchemeService bsService = new BaseSchemeService();
		//-map={tags=设备单位, MANAGER_ID=10842, _SOURCE_TYPE_=WEB, _SOURCE_TYPE_VALUE=是, SCHEME_ROW_NUM=529487, PROJECT_ID=567165, BASE_SCHEME={SCHEME_CODE=f438370c-0d12-4176-b863-f6519e11b296, BUSINESS_PLATFORM=汽车租赁集团, ALIASES=标准, SCHEME_NAME=轻松30租, BUSINESS_TYPE=7,8,13,15, PAY_WAY=平息法, START_PERCENT=30}, SCHEME_ID=f438370c-0d12-4176-b863-f6519e11b296, SCHEME_ID_ACTUAL=7979435}
		System.out.println("============================"+bsService.querySchemeInfoList(m, "WEB"));
		Map<String,Object> baseMapScheme = (Map<String,Object>)m.get("BASE_SCHEME");//bsService.querySchemeInfoList(m, "WEB").get(0);
		//context.put("BASE_SCHEME1", bsService.querySchemeInfoList(m, "WEB"));
		Set setbaseMapScheme = baseMapScheme.entrySet();
		Iterator iteratorBaseMapScheme = setbaseMapScheme.iterator();
		    while(iteratorBaseMapScheme.hasNext()){
		    	Map.Entry<String, Object> it= (Map.Entry<String, Object>)iteratorBaseMapScheme.next();
		    	imap = new HashMap<String,Object>();
		    	if("SCHEME_NAME".equals(it.getKey())){
		    		imap.put("KEY", "产品方案名称");
		    		imap.put("SORTS", "8");
		    	}if("ALIASES".equals(it.getKey())){
		    		imap.put("KEY", "方案类型");
		    		imap.put("SORTS", "9");
		    	}
		    	if(null != imap.get("KEY")){
		    		imap.put("VALUE", it.getValue());
		    		if(null != it.getValue() && !"".equals(it.getValue())){
			    		insertComparisonData(imap,m.get("PROJECT_ID"));
			    	}
			    	//insertComparisonData(imap,m.get("PROJECT_ID"));
		    	}
		    	
		    }
		/*添加基本方案*/
//		context.put("dicTag", Util.DICTAG);
//		context.put("MyNumberTool", new MyNumberTool());
		Map<String, Object> schemeBase = bsService.getSchemeBaseInfoByProjectId(m.get("PROJECT_ID") + "", null,m.get("SCHEME_ROW_NUM") + "").get(0);
		 Set setSchemeBase = schemeBase.entrySet();
			Iterator iteratorSchemeBase = setSchemeBase.iterator();
			    while(iteratorSchemeBase.hasNext()){
			    	Map.Entry<String, Object> it= (Map.Entry<String, Object>)iteratorSchemeBase.next();
			    	imap = new HashMap<String,Object>();
			    	if("LEASE_TOPRIC".equals(it.getKey())){
			    		imap.put("KEY", "实际成交价");
			    		imap.put("SORTS", "10");
			    	}if("FINANCE_TOPRIC".equals(it.getKey())){
			    		imap.put("KEY", "融资额");
			    		imap.put("SORTS", "11");
			    	}if("PLATFORM_TYPE".equals(it.getKey())){
			    		//字典
			    		imap.put("KEY", "业务类型");
			    		imap.put("SORTS", "12");
			    	}if("TOTAL_MONTH_PRICE".equals(it.getKey())){
			    		imap.put("KEY", "租金总额");
			    		imap.put("SORTS", "13");
			    	}if("LEASE_TERM".equals(it.getKey())){
			    		imap.put("KEY", "期限");
			    		imap.put("SORTS", "14");
			    	}if("FIRSTPAYDATE".equals(it.getKey())){
			    		imap.put("KEY", "首期付款日期");
			    		imap.put("SORTS", "15");
			    	}if("FIRSTPAYALL".equals(it.getKey())){
			    		imap.put("KEY", "首期付款合计");
			    		imap.put("SORTS", "16");
			    	}if("YEAR_INTEREST".equals(it.getKey())){
			    		imap.put("KEY", "租赁利率");
			    		imap.put("SORTS", "17");
			    	}
			    	if("MONTH_RENT".equals(it.getKey())){
			    		imap.put("KEY", "月还租金");
			    		imap.put("SORTS", "18");
			    	}
			    	if(null != imap.get("KEY")){
			    		if("MONTH_RENT".equals(it.getKey())){
				    		PayTaskService pay = new PayTaskService();
				    		int PAY_ID = queryIdByProjectIdRowNum(m);
				    		Map<String, Object> payMap = new HashMap<String, Object>();
				    		payMap.put("ID", PAY_ID);
				    		baseMap.put("ID", PAY_ID);
				    		List<Map<String, Object>> listDetail = pay
				    				.doShowRentPlanDetailScheme(payMap);
				    		imap.put("VALUE", (listDetail.get(0)).get("ZJ") + "");	
				    		insertComparisonData(imap,m.get("PROJECT_ID"));
				    	}else{
				    		imap.put("VALUE", it.getValue());
				    		if(null != it.getValue() && !"".equals(it.getValue())){
					    		insertComparisonData(imap,m.get("PROJECT_ID"));
					    	}
				    	}
			    		
				    	//insertComparisonData(imap,m.get("PROJECT_ID"));
			    	}
			    	
			    }
		
		//context.put("schemeBase",bsService.getSchemeBaseInfoByProjectId(m.get("PROJECT_ID") + "", null,m.get("SCHEME_ROW_NUM") + "").get(0));
		
			    
	    List<Map<String, Object>> schemeDetail = bsService.getSchemeClob(null, m.get("PROJECT_ID") + "",
				m.get("SCHEME_ROW_NUM") + "");
		Map<String,Object> mone = new HashMap<String,Object>();
		for(Map<String, Object> mschemem:schemeDetail){
			System.out.println(mschemem.get("KEY_NAME_ZN").toString()+"==="+ null==mschemem.get("VALUE_STR")?" ":mschemem.get("VALUE_STR").toString());
			mone.put(mschemem.get("KEY_NAME_ZN").toString(), null==mschemem.get("VALUE_STR")?" ":mschemem.get("VALUE_STR").toString());
		}
		//context.put("SCHEMEDETAIL",bsService.getSchemeClob(null, m.get("PROJECT_ID") + "",m.get("SCHEME_ROW_NUM") + ""));

		/*方案信息入库*/
		int i =19;
	    Set set = mone.entrySet();
		Iterator iterator = set.iterator();
		    while(iterator.hasNext()){
		    	Map.Entry<String, Object> it= (Map.Entry<String, Object>)iterator.next();
		    	imap = new HashMap<String,Object>();
		    	imap.put("KEY", it.getKey());
		    	imap.put("VALUE", it.getValue());
		    	
		    	if(null != it.getValue() && !"".equals(it.getValue())){
		    		imap.put("SORTS", ++i);
		    		insertComparisonData(imap,m.get("PROJECT_ID"));
		    	}
		    	
		    	System.out.println(it.getKey()+"========="+it.getValue());
		    }
    }
    /**
     * 查询最终结果
     * @param m
     * @return
     */
    public List<Map<String,Object>> queryProjetShceme(Map<String,Object> m){
    	return Dao.selectList("project.queryProjetShceme", m);
    	
    }
    /**
     * 删除数据
     * @param m
     * @return
     */
    public int deleteComparison(Map<String,Object> m){
    	return Dao.delete("project.deleteComparison", m);
    }
    /**
     * 获取作废/重签理由
     * @return
     */
    public Map<String,Object> getProjectdInvalidSign(Map<String,Object> m){
    	return Dao.selectOne("project.getProjectdInvalidSign", m);
    }
    
    //add gengchangbao JZZL-198 2016年5月31日16:37:18 Start
    /**
     * 查询来款详情
     * @return
     */
	public List<Map<String,Object>> queryDepositInfo(Map<String, Object> map) {
		return Dao.selectList("project.queryDepositInfo", map);
	}
    /**
     * 应付款查询
     * @return
     */
	public Map<String,Object> getAmountPayable(Map<String, Object> map) {
		return Dao.selectOne("project.getAmountPayable", map);
	}
	
    /**
     * 清除来款数据
     * @return
     */
    public int deleteDeposit(Map<String,Object> m){
    	return Dao.delete("project.deleteDeposit", m);
    }
    
    /**
     * 插入来款记录
     * @return
     */
	public int insertDeposit(Map<String,Object> m){
		return Dao.insert("project.insertDeposit", m);
	}
	
    /**
     *更新来款记录
     * @return
     */
	public int updateDeposit(Map<String,Object> m){
		return Dao.update("project.updateDeposit", m);
	}
	//add gengchangbao JZZL-198 2016年5月31日16:37:18 End
	
	
    /**
     *更新来款记录
     * @return
     */
	public int updateDepositStarts(Map<String,Object> m){
		return Dao.update("project.updateDepositStarts", m);
	}
	
	
	/**
	 * 上传资料文件
	 * @author 耿长宝
	 * @date 2016年5月12日16:39:20
	 */
	public boolean uploadPaperFile(Map<String, Object> param,List<FileItem> fileList){
		// 设置文件上传的大小 目前设置为30m
		final long MAX_SIZE = 300 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path");
		path = path + "/parerFiles/";
		//path = "D:/parerFiles/";
		
		try {
			File wf = new File(path);
			if (!wf.exists()) {//检查磁盘上是否存在path路径文件
				wf.mkdirs();//在磁盘上创建路径
			}
		} catch (Exception e) {
			return false;
		}
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if(!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())){
					String fn = Action._getFileName(fileitem.getName());
					String fileName = fn;
					
					
					fn = fn.substring(0,fn.lastIndexOf(".")) + new SimpleDateFormat("yyyyMMddHHmmss")
							.format(Calendar.getInstance().getTime())+fn.substring(fn.lastIndexOf("."));
					
					
					
					
					file = new File(path + fn);
					try {
						fileitem.write(file);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					param.put("FILE_NAME", fileName);
					param.put("FILE_PATH", file.getPath());
				}
			} else {
				try {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	 // add gengchangbao JZZL-178 2016年5月6日16:44:01 Start
		// 查询分部列表
		public List<Map<String, Object>> getAllFB(Map<String,Object> param) {
			return Dao.selectList("project.getAllFB", param);
		}
		
		// 查询分部下的所有分公司
		public List<Map<String, Object>> getAllFGSbyOrgId(Map<String,Object> param) {
			return Dao.selectList("project.getAllFGSbyOrgId", param);
		}
		
		// 查询该分公司同分部下的所有分公司
		public List<Map<String, Object>> getAllFGSbyFGSId(Map<String,Object> param) {
			return Dao.selectList("project.getAllFGSbyFGSId", param);
		}
		
		// 查询该分公司的分部
		public Map<String, Object> getFBbyFGSId(Map<String,Object> param) {
			return Dao.selectOne("project.getFBbyFGSId", param);
		}
		// add gengchangbao JZZL-178 2016年5月6日16:44:01 End
	
}