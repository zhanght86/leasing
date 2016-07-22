package com.pqsoft.customers.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CustMainRelationService {
	
	private final String xmlPath = "custMainRelation.";

	/**
	 * getTouziDetail 自然人投资情况
	 * @date 2013-9-2 下午12:13:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getTouziDetail(Map<String,Object> map){
		Page page = new Page(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findInfoDetail", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("INFO_ID", m.get("INFO_ID"));
			json.put("COMPANY_NAME", m.get("COMPANY_NAME"));//单名称位
			json.put("COMPANY_PROPERTY", m.get("COMPANY_PROPERTY"));//单位属性
			json.put("OPEN_DATE", m.get("OPEN_DATE"));//成立时间
			json.put("INVEST_MONEY", m.get("INVEST_MONEY"));//投资金额
			json.put("INVEST_PROPORTION", m.get("INVEST_PROPORTION"));//投资比例
			json.put("POSITION", m.get("POSITION"));//任职
			json.put("ADDR", m.get("ADDR"));//地址
			json.put("POST", m.get("POST"));//邮编
			json.put("TURNOVER", m.get("TURNOVER"));//年营业额
			json.put("INDUSTRY", m.get("INDUSTRY"));
			json.put("LINK_NAME", m.get("LINK_NAME"));//联系人
			json.put("TEL_PHONE", m.get("TEL_PHONE"));//手机
			json.put("SEX", m.get("SEX"));//性别
			json.put("FAX", m.get("FAX"));//传真
			json.put("NATION", m.get("NATION"));//民族
			json.put("REMARK", m.get("REMARK"));//备注
			json.put("PHONE", m.get("PHONE"));//固话
			array.add(json);
		}
		
		page.addDate(array, Dao.selectInt(xmlPath+"findInfoNaCount",map));//计数
		return page;
	}
	
	/**
	 * getParDetail 自然人合作伙伴
	 * @date 2013-9-2 下午12:14:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getParDetail(Map<String,Object> map){
		Page page = new Page(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findPartnerDetail", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("PAR_ID", m.get("PAR_ID"));
			json.put("PARTNER", m.get("PARTNER"));//合作伙伴
			json.put("COMPANY_PROPERTY", m.get("COMPANY_PROPERTY"));//公司属性
			json.put("REG_CAPITAL", m.get("REG_CAPITAL"));//注册资金
			json.put("TAX_NUMBER", m.get("TAX_NUMBER"));//纳税号
			json.put("ADDRESS", m.get("ADDRESS"));//地址
			json.put("POST", m.get("POST"));//邮编
			json.put("TURNOVER", m.get("TURNOVER"));//年营业额
			json.put("STAFF_NUMBER", m.get("STAFF_NUMBER"));//员工人数
			json.put("LINK_NAME", m.get("LINK_NAME"));//联系人姓名
			json.put("LINK_TEL_PHONE", m.get("LINK_TEL_PHONE"));//联系人手机
			json.put("LINK_PHONE", m.get("LINK_PHONE"));//联系固话
			json.put("NEW_PROJ_NAME", m.get("NEW_PROJ_NAME"));//最新项目
			json.put("REMARK", m.get("REMARK"));//备注
			array.add(json);
		}
		
		page.addDate(array, Dao.selectInt(xmlPath+"findPartnerCount",map));//计数
		return page;
	}
	
	/**
	 * getHolderDetail  法人客户股东及份额
	 * @date 2013-9-2 下午12:18:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getHolderDetail(Map<String,Object> map) {
		Page page = new Page(map);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findHolderDetail", map);
		JSONArray array = new JSONArray();
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				JSONObject json = new JSONObject();
				Map<String,Object> m=(Map<String,Object>)list.get(i);
				json.put("HOLDER_ID", m.get("HOLDER_ID"));
				json.put("HOLDER_ID", m.get("HOLDER_ID"));
				json.put("HOLDER_NAME", m.get("HOLDER_NAME"));//股东名称
				json.put("HOLDER_CAPITAL", m.get("HOLDER_CAPITAL"));//股东投资情况
				json.put("HOLDER_WAY", m.get("HOLDER_WAY"));//投资方式
				json.put("HOLDER_RATE", m.get("HOLDER_RATE"));//投资比例
				json.put("HOLDER_MOME", m.get("HOLDER_MOME"));//备注 
				json.put("STATUS", m.get("STATUS"));//地址
				array.add(json);
			}
			
		}
		
		page.addDate(array, Dao.selectInt(xmlPath+"findHolderCount",map));//计数
		return page;
	}
	
	/**
	 * getComDetail 法人企业团队
	 * @date 2013-9-2 下午12:17:02
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getComDetail(Map<String,Object> map) {
		Page page = new Page(map);
		List<Map<String,Object>> comLink = Dao.selectList(xmlPath+"findComLink", map);//企业团队数据
		List<Object> duty = (List)new DataDictionaryMemcached().get("职务");
		List<Object> com_typeL = (List)new DataDictionaryMemcached().get("证件类型");
		List<Object> nationL = (List)new DataDictionaryMemcached().get("民族");
		List<Object> degree_edu = (List)new DataDictionaryMemcached().get("文化程度");
		JSONArray array = new JSONArray();
		for(int i = 0; i< comLink.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)comLink.get(i);
			json.put("COMTEAM_ID", m.get("COMTEAM_ID"));//id
			json.put("NAME", m.get("NAME"));//名称
			if(com_typeL!=null){
				for(int j=0;j<com_typeL.size();j++){
					Map<String,Object> c = (Map<String, Object>) com_typeL.get(j);
					if(c.get("CODE").equals(m.get("ID_CARD_TYPE"))){
						json.put("ID_CARD_TYPE", c.get("FLAG"));
					}
				}
			}
			//json.put("ID_CARD_TYPE", m.get("ID_CARD_TYPE"));//证件类型
			json.put("ID_CARD", m.get("ID_CARD"));//证件号
			json.put("TEL_PHONE", m.get("TEL_PHONE"));//手机
			json.put("PHONE", m.get("PHONE"));//固话
			json.put("FAMILY_ADDR", m.get("FAMILY_ADDR"));//常驻地址
			json.put("HOUSE_ADDR", m.get("HOUSE_ADDR"));//户籍地址
			//json.put("DEGREE_EDU", m.get("DEGREE_EDU"));//文化程度
			//文化程度
			if(degree_edu!=null){
				for(int k=0;k<degree_edu.size();k++){
					Map<String,Object> d = (Map<String, Object>) degree_edu.get(k);
					if(d.get("CODE").equals(m.get("DEGREE_EDU"))){
						json.put("DEGREE_EDU", d.get("FLAG"));
					}
				}
			}
			//民族
			if(nationL!=null){
				for(int v=0;v<nationL.size();v++){
					Map<String,Object> n = (Map<String, Object>) nationL.get(v);
					if(n.get("CODE").equals(m.get("NATION"))){
						json.put("NATION", n.get("FLAG"));
					}
				}
			}
			//json.put("NATION", m.get("NATION"));
			json.put("IS_ILLEGAL", m.get("IS_ILLEGAL"));//违法记录
			json.put("REMARK", m.get("REMARK"));//备注
			//职务
			if(duty!=null){
				for(int ja=0;ja<duty.size();ja++){
					Map<String,Object> du = (Map<String, Object>) duty.get(ja);
					if(du.get("CODE").equals(m.get("TYPE"))){
						json.put("TYPE", du.get("FLAG"));
					}
				}
			}
			json.put("CLIENT_ID", m.get("CLIENT_ID"));
			array.add(json);
		}
		page.addDate(array, Dao.selectInt(xmlPath+"findComLinkCount",map));//计数
		return page;
	}
	
	/**
	 * getWorkExpDetail 从业经验
	 * @date 2013-9-2 下午12:20:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getWorkExpDetail(Map<String,Object> map){
		Page page = new Page(map);
		List<Map<String,Object>> comLink = Dao.selectList(xmlPath+"findWorkExpDetail", map);//企业团队数据
		List<Object> project_type = (List)new DataDictionaryMemcached().get("项目类型");
		JSONArray array = new JSONArray();
		for(int i = 0; i< comLink.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)comLink.get(i);
			json.put("WOR_ID", m.get("WOR_ID"));//id
			//职务
			
			if(project_type!=null){
				for(int j=0;j<project_type.size();j++){
					Map<String,Object> du = (Map<String, Object>) project_type.get(j);
					if(du.get("CODE").equals(m.get("PROJECT_TYPE"))){
						json.put("PROJECT_TYPE", du.get("FLAG"));//项目类型
						System.out.println(json.get("PROJECT_TYPE"));
					}
				}
			}
			json.put("PROJECT_NAME", m.get("PROJECT_NAME"));//项目名称
			json.put("INDUSTRY", m.get("INDUSTRY"));//行业
			json.put("PROJECT_TERM", m.get("PROJECT_TERM"));//工程期限
			json.put("CONT_TERM", m.get("CONT_TERM"));//承包期限（月）
			json.put("START_DATE", m.get("START_DATE"));//开始时间
			json.put("END_DARE", m.get("END_DARE"));//结束时间
			json.put("CONS_PLACE", m.get("CONS_PLACE"));//施工地点
			json.put("CONTACT_PERSON", m.get("CONTACT_PERSON"));//联系人
			json.put("TEL", m.get("TEL"));//电话
			json.put("PROJECT_AMT", m.get("PROJECT_AMT"));//工程总造价
			
			json.put("CONT_AMT", m.get("CONT_AMT"));//承包价值
			json.put("AMT_RESOURCE", m.get("AMT_RESOURCE"));//资源来源
			json.put("MONTH_INCOME", m.get("MONTH_INCOME"));//月收入
			json.put("CONS_CONTENT", m.get("CONS_CONTENT"));//施工内容
			json.put("AMT_INFO", m.get("AMT_INFO"));//到位情况
			json.put("INCOME_INFO", m.get("INCOME_INFO"));//收益情况
			json.put("FLAG", m.get("FLAG"));//状态
			json.put("COMPANYNAME", m.get("COMPANYNAME"));//公司名称
			json.put("OCCUPATION", m.get("OCCUPATION"));//职业
			json.put("REMARK", m.get("REMARK"));//简述
			array.add(json);
		}
		page.addDate(array, Dao.selectInt(xmlPath+"findWorkExpCount",map));//计数
		return page;
	}

	/**
	 * 添加企业团队
	 * doInsertComTeam
	 * @date 2013-9-5 下午04:38:15
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertComTeam(Map<String,Object> map) {
		String ID = Dao.getSequence("SEQ_FIL_CUST_COMTEAM");
		map.put("ID", ID);
		return Dao.insert(xmlPath+"insertComTeam", map);
	}
	
	/**
	 * 企业团队移除
	 * doDelComTeam
	 * @date 2013-9-5 下午05:20:15
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelComTeam(Map<String,Object> map){
		return Dao.delete("customers.delCustCom",map);
	}
	
	/**
	 * 根据id获取企业团队
	 * getComTeam
	 * @date 2013-9-9 上午10:28:59
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getComTeam(Map<String,Object> map){
		Map<String, Object> m = Dao.selectOne(xmlPath+"findComLink", map);
		if(m!=null){
			Dao.getClobTypeInfo2(m,"IDCARD_PHOTO");
		}
		return m;
	}
	
	/**
	 * 修改企业团队信息
	 * updateComTeam
	 * @date 2013-9-9 上午10:34:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateComTeam(Map<String,Object> map){
		return Dao.update(xmlPath+"updateComTeam",map);
	}
	
	/**
	 * 添加股东及份额
	 * doInsertHolder
	 * @date 2013-9-5 下午06:47:04
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertHolder(Map<String,Object> map){
		String ID = Dao.getSequence("SEQ_FIL_CUST_COMTEAM");
		map.put("ID", ID);
		return Dao.insert(xmlPath+"doInsertHolder", map);
	}
	
	/**
	 * 作废股东级份额
	 * doDelHolder
	 * @date 2013-9-5 下午06:48:06
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelHolder(Map<String,Object> map){
		return Dao.update(xmlPath+"delHolder", map);
	}
	
	/**
	 * 根据id获取公司股东级份额
	 * getHolder
	 * @date 2013-9-9 上午10:39:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getHolder(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"findHolderDetail", map);
	}
	
	/**
	 * 修改公司股东及份额
	 * updateHolder
	 * @date 2013-9-9 上午10:41:50
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateHolder(Map<String,Object> map){
		return Dao.update(xmlPath+"updateHolder",map);
	}
	
	
	/**
	 * 添加合作伙伴
	 * doInsertPartners
	 * @date 2013-9-6 下午03:37:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertPartners(Map<String,Object> map){
		return Dao.insert(xmlPath+"doInsertPartner", map);
	}
	
	/**
	 * 删除合作伙伴
	 * doDelPartners
	 * @date 2013-9-6 下午04:16:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelPartners(Map<String,Object> map){
		return Dao.delete(xmlPath+"delPartners", map);
	}
	
	/**
	 * 根据id获取合作伙伴
	 * getPartners
	 * @date 2013-9-9 上午09:30:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getPartners(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"findPartnerDetail", map);
	}
	
	
	/**
	 * 修改合作伙伴
	 * updatePartners
	 * @date 2013-9-9 上午09:33:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updatePartners(Map<String,Object> map){
		return Dao.update(xmlPath+"updatePartners",map);
	}
	
	/**
	 * 添加承租人投资情况
	 * doInsertInvestInfo
	 * @date 2013-9-6 下午05:10:35
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertInvestInfo(Map<String,Object> map){
		return Dao.insert(xmlPath+"doInsertInvestInfo",map);
	}
	
	/**
	 * 删除客户投资情况
	 * delInvest
	 * @date 2013-9-6 下午05:11:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doDelInvest(Map<String,Object> map){
		return Dao.delete(xmlPath+"delInvest", map);
	}
	
	/**
	 * 根据id获取投资情况
	 * getInfoDetail
	 * @date 2013-9-9 上午09:30:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getInfoDetail(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"findInfoDetail", map);
	}
	
	
	/**
	 * 修改投资情况
	 * updateInvest
	 * @date 2013-9-9 上午09:33:44
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateInvest(Map<String,Object> map){
		return Dao.update(xmlPath+"updateInvest",map);
	}
	
	/**
	 *  添加从业经验
	 * doInsertWoekExp
	 * @date 2013-9-7 上午10:47:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertWoekExp(Map<String,Object> map){
		return Dao.insert(xmlPath+"doInsertWoekExp", map);
	}
	
	/**
	 * 根据从业经验id查看信息
	 * getUpdateExperience
	 * @date 2013-9-7 下午04:00:17
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getExperience(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"findWorkExpDetail", map);
	}
	
	/**
	 * 从业经验删除
	 * delWoekExp
	 * @date 2013-9-7 上午11:01:36
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delWoekExp(Map<String,Object> map){
		return Dao.update(xmlPath+"delWoekExp", map);
	}
	
	/**
	 * 修改从业经历
	 * doUpdateExperence
	 * @date 2013-9-7 下午05:12:08
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateExperence(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpdateExperence", map);
	}
}
