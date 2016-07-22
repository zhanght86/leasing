package com.pqsoft.refundFirst.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;

public class RefundFirstService {

	private final String xmlPath = "refundFirst.";
	
	/******************************************************首付款退款 -核销*****************************************************/
	/**
	 * 查询首期款退款 申请数据
	 * findDeductData
	 * @date 2013-10-10 上午11:04:47
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page toMgDeductData(Map<String,Object> map){
		Page page = new Page(map);
		//首期数据获取
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgDeductData", map);
		List<Object> hexiao_satus = (List)new DataDictionaryMemcached().get("核销状态");
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			json.put("FI_PAY_MONEY", m.get("FI_PAY_MONEY"));//应付金额
			json.put("FI_PAY_DATE", m.get("FI_PAY_DATE"));//应付时间
			json.put("FI_REALITY_MONEY", m.get("FI_REALITY_MONEY"));//实付金额
			json.put("FI_ACCOUNT_DATE", m.get("FI_ACCOUNT_DATE"));//实付日期
			json.put("FI_APP_CODE", m.get("FI_APP_CODE"));//申请人编号
			json.put("FI_APP_NAME", m.get("FI_APP_NAME"));//申请人
			json.put("FI_APP_DATE", m.get("FI_APP_DATE"));//首期时间
			//json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态
			if(hexiao_satus!=null){
				for(int j=0;j<hexiao_satus.size();j++){
					Map<String,Object> c = (Map<String, Object>) hexiao_satus.get(j);
					if(c.get("CODE").equals(m.get("FI_STATUS"))){
						json.put("FI_STATUS", c.get("FLAG"));
					}
				}
			}
			json.put("FI_REMARK", m.get("FI_REMARK"));//备注
			array.add(json);
		}		
		page.addDate(array, Dao.selectInt(xmlPath+"toMgDeductCount",map));//计数
		return page;
	}
	
	/**
	 * 获取申请首付款退款数据
	 * toMgDeductCount
	 * @date 2013-9-25 下午02:50:19
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toGetAppData(Map<String,Object> map){
		Page page = new Page(map);
		//首期数据获取
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toGetAppData", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("HEAD_ID", m.get("HEAD_ID"));
			json.put("PRO_CODE", m.get("PRO_CODE"));//项目编号
			json.put("CLIENT_ID", m.get("CLIENT_ID"));//承租人编号
			json.put("CLIENT_NAME", m.get("CLIENT_NAME"));//承租人
			json.put("PRODUCT_NAME", m.get("EQUIPMENINFOS"));//租赁物类型
			json.put("COMPANY_NAME", m.get("COMPANY_NAMES"));//厂商名称
			json.put("SUP_NAME", m.get("SUP_NAME"));//供应商名称
			json.put("FIRST_MONEY", m.get("FIRST_MONEY"));//首期款
			json.put("OTHER_MONEY", m.get("OTHER_MONEY"));//其他费用
			json.put("DB_MONEY", m.get("DB_MONEY"));//DB 
			json.put("CS_MONEY", m.get("CS_MONEY"));//厂商
			json.put("REALITY_MONEY", m.get("REALITY_MONEY"));//合计
			json.put("FIRST_PAYMENT_TYPE", m.get("FIRST_PAYMENT_TYPE"));//其他费用
			json.put("RECEIVE_DATE", m.get("RECEIVE_DATE"));//应付日期
			json.put("REALITY_DATE", m.get("REALITY_DATE"));//实付日期
			
			array.add(json);
		}		
		page.addDate(array, Dao.selectInt(xmlPath+"toGetDeductCount",map));//计数
		return page;
	}
	
	/**
	 * 获取厂商
	 * toGetCompany
	 * @date 2013-9-25 下午08:29:29
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetCompany(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetCompany", map);
	}
	
	/**
	 * 获取租赁物类型
	 * toGetProduct
	 * @date 2013-9-25 下午08:30:44
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toGetProduct(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toGetProduct", map);
	}
	
	/**
	 * 提交申请数据
	 * doInsertAppData
	 * @date 2013-9-26 下午07:15:04
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInsertAppData(Map<String, Object> map,JSONObject json){		
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		map.put("HEAD_ID", HEAD_ID);
		Map<String, Object> detailData = JSONObject.fromObject(json.get("detailData"));//明细数据
		
		int i = 0;
		i = this.doInsertAppBaseData(map);
		if(i>0){
			if(detailData != null){
				for (int k = 0; k < detailData.size(); k++) {
					Map<String,Object> m = (Map<String, Object>) detailData.get(k);					
					Map<String,Object> detail = (Map<String, Object>) Dao.selectList(xmlPath+"toFindAppData", m.get("PAYLIST_CODE"));
					detail.put("HEAD_ID", HEAD_ID);
					detail.put("D_TO_THE_PAYEE", map.get("USERCODE"));
					detail.put("D_CLIENT_CODE", map.get("CLIENT_ID"));
					detail.put("D_CLIENT_NAME", map.get("CLIENT_NAME"));
					detail.put("FI_PAY_DATE", map.get("FI_PAY_DATE"));
					detail.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					i = Dao.insert(xmlPath+"doInsertAppDetail", detail);
				}
			}
		}else {
			i = 0; 
		}
		return i;
	}
	
	/**
	 * 添加付款单
	 * doInsertAppBaseData
	 * @date 2013-9-27 下午03:41:09
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertAppBaseData(Map<String,Object> map){
		return Dao.insert(xmlPath+"doInsertFundHead", map);		
	}
	/******************************************************首付款退款 -核销********************************************************/
	/**
	 * 查询首期款退款 核销数据
	 * findDeductData
	 * @date 2013-10-10 上午11:04:47
	 * @author 吴剑东
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getRefundFirstConfirmData(Map<String,Object> map){
		// 判断供应商登录只能看到当前供应商数据
		ManageService mgService = new ManageService();
		Map m = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if(m != null && m.get("SUP_ID") != null){
			map.put("SUPP_ID", m.get("SUP_ID"));
		}
		
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(xmlPath+"getRefundFirstConfirmData", map)), Dao.selectInt(xmlPath+"getRefundFirstConfirmCount",map));
		return page;
	}

	public String insertRefundHeadForFrist(Map<String, Object> map) {
		String RE_ID = Dao.getSequence("SEQ_FUND_HEAD");
		map.put("RE_ID", RE_ID);
		Dao.insert(xmlPath+"insertRefundHeadForFrist", map);		
		return RE_ID;
	}

	public void insertRefundAccountForFrist(Map<String, Object> map) {
		Dao.insert(xmlPath+"insertRefundAccountForFrist", map);
	}

	public void delFristRefundHeadByReId(Map<String, Object> map) {
		Dao.delete(xmlPath+"delFristRefundHeadByReId",map);
	}

	public void delFristRefundAccountByReId(Map<String, Object> map) {
		Dao.delete(xmlPath+"delFristRefundAccountByReId",map);
	}

	public void doConfirmRefundAppByProId(Map<String, Object> map) {
		Dao.selectInt(xmlPath+"doConfirmRefundAppByProId",map);
	}
}
