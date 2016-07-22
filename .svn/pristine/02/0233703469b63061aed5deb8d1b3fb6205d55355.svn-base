package com.pqsoft.retentionRefund.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RetentionRefundService {

	//读取配置文件路径
	private final String xmlPath="retentionRefund.";
	
	/**************************************************************留购价退款申请-管理页面**************************************************************************/
	
	/**
	 * 留购价退款-管理页面
	 * findRetentionData
	 * @date 2013-10-10 下午05:38:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page findRetentionData(Map<String,Object> map){
		Page page = new Page();
		//首期数据获取
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findRetentionData", map);
//		List<Object> hexiao_satus = (List)new DataDictionaryMemcached().get("退款单状态");
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("RE_ID", m.get("RE_ID"));
			json.put("RE_CODE", m.get("RE_CODE"));//应付单号
			json.put("RE_PAYEE_NAME", m.get("RE_PAYEE_NAME"));//收款人
			json.put("RE_PAYEE_UNIT", m.get("RE_PAYEE_UNIT"));//收款单位
			json.put("RE_PAYEE_ADDRESS", m.get("RE_PAYEE_ADDRESS"));//收款单位地址
			json.put("RE_PAYEE_BANK", m.get("RE_PAYEE_BANK"));//收款银行
			json.put("RE_PAYEE_ACCOUNT", m.get("RE_PAYEE_ACCOUNT"));//收款银行帐号
			json.put("RE_PAYEE_BANK_ADDR", m.get("RE_PAYEE_BANK_ADDR"));//收款银行地址
			
			//确认状态
			if("0".equals(m.get("RE_STATUS").toString())){
				json.put("RE_STATUS", "未退款");
			}else if("1".equals(m.get("RE_STATUS").toString())){
				json.put("RE_STATUS", "评审中");
			}else {
				json.put("RE_STATUS", "已退款");
			}
			
//			json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态
//			if(hexiao_satus!=null){
//				for(int j=0;j<hexiao_satus.size();j++){
//					Map<String,Object> hexiao = (Map<String, Object>) hexiao_satus.get(j);	
//					if(hexiao.get("CODE").equals(m.get("RE_STATUS").toString())){
//						json.put("FI_STATUS", hexiao.get("FLAG"));
//					}
//				}
//			}
			json.put("RE_DATE", m.get("RE_DATE"));//付款日期
			json.put("RE_MONEY", m.get("RE_MONEY"));//留购价金额
			json.put("NAME", m.get("NAME"));//供应商
			array.add(json);
		}		
		page.addDate(array, Dao.selectInt(xmlPath+"findRetentionCount",map));//计数
		return page;
	} 
	
	/**
	 * 留购价退款-申请页面
	 * getRetentionApp
	 * @date 2013-10-10 下午11:46:21
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page getRetentionApp(Map<String,Object> map){
		Page page = new Page();
		//首期数据获取
		String LIUGOU = "留购价款";
		map.put("LIUGOU", LIUGOU);
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"getRetentionApp", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("PROJECT_ID", m.get("PROJECT_ID"));//项目id
			json.put("PRO_CODE", m.get("PRO_CODE"));//项目编号
			json.put("SUP_NAME", m.get("SUP_NAME"));//供应商
			json.put("CLIENT_NAME", m.get("CLIENT_NAME"));//客户名称
			json.put("CLIENT_CODE", m.get("CLIENT_CODE"));//客户编号
			json.put("EQUIPMENINFOS", m.get("EQUIPMENINFOS"));//设备信息
			json.put("START_DATE", m.get("START_DATE"));//起租确认日期
			json.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC"));//租赁物总价款
			json.put("BEGINNING_NAME", m.get("BEGINNING_NAME"));//项目款项
			json.put("BEGINNING_MONEY", m.get("BEGINNING_MONEY"));//项目款金额
			json.put("BEGINNING_RECEIVE_DATA", m.get("BEGINNING_RECEIVE_DATA"));//项目结束日期
			json.put("SKDW", m.get("SUP_NAME"));//收款單位
			//结束方式
			if(m.get("STATUS")!=null){
				if(m.get("STATUS").toString().equals("3")){
					json.put("STATUS","正常结清");
				}else if(m.get("STATUS").toString().equals("6")){
					json.put("STATUS","提前结清");
				}else if(m.get("STATUS").toString().equals("5")) {
					json.put("STATUS", "回购");
				}
			}
			array.add(json);
		}		
		page.addDate(array, Dao.selectInt(xmlPath+"getRententionAppCount",map));//计数
		return page;
	} 
	
	/**
	 * 根据用户编号获取用户所属组织架构
	 * getOrgData
	 * @date 2013-10-11 下午12:19:33
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Map<String,Object> getOrgData(String USERCODE){
		return Dao.selectOne(xmlPath+"getOrgName", USERCODE);
	}
	
	/**
	 * 退款申请-保存
	 * doSaveRetention
	 * @date 2013-10-11 上午12:04:21
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doSaveRetention(Map<String,Object> map){
		//获取页面参数
		Object data_ = map.get("data");
		Map<String, Object> data = JSONObject.fromObject(data_);
		Map<String,Object> baseData = JSONObject.fromObject(data.get("getBaseData"));//退款主表信息
		List<Map<String,Object>> detailData = JSONArray.fromObject(data.get("getDetailData"));//退款明细信息
		
		String RE_ID = Dao.getSequence("SEQ_FI_REFUND_HEAD");//退款项目主表id
		
		String RE_DATE = baseData.get("RE_DATE").toString();//付款日期
		String RE_MONEY = baseData.get("RE_MONEY").toString();//付款金额
		String RE_PROJECT_COUNT = baseData.get("RE_PROJECT_COUNT").toString();//项目数量
		
		String RE_CODE =map.get("USERCODE").toString()+"-"+baseData.get("RE_DATE").toString();//付款单编号
		
		//baseData = Dao.selectOne(xmlPath+"getSuppliersData", baseData);//根据组织架构id获取供应商基础信\
		baseData.put("RE_ID", RE_ID);
		baseData.put("RE_CODE", RE_CODE);//项目编号	
		baseData.put("RE_DATE", RE_DATE);
		baseData.put("RE_MONEY", RE_MONEY);
		baseData.put("RE_PROJECT_COUNT", RE_PROJECT_COUNT);
		baseData.put("USERCODE", map.get("USERCODE"));//用户编号
		baseData.put("USERNAME", map.get("USERNAME"));//用户名称
		
		int i=0;
		i=Dao.insert(xmlPath+"doRetentionApp", baseData);//添加退款主表
		
		if(i>0){
			if(detailData!=null){
				for(int j=0; j<detailData.size(); j++){
					Map<String,Object> m = detailData.get(j);
					m.put("RE_ID", RE_ID);
					i=Dao.insert(xmlPath+"doRetentionAppDe", m);//添加退款明细
				}
				
				if(i>0){
					return i;
				}else{
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 退款管理-提交
	 * doCommitApp
	 * @date 2013-10-11 上午12:19:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCommitApp(Map<String,Object> map){
		Object data_ = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(data_);
		List<Map<String,Object>> baseData = JSONArray.fromObject(data.get("getDetailData"));//提交申请
		int i=0;
		if(baseData!=null){
			for(int j=0; j<baseData.size();j++){
				Map<String,Object> base = baseData.get(j);
				base.put("USERNAME", map.get("USERNAME"));//用户名称
				base.put("USERCODE", map.get("USERCODE"));//用户编号
				i=Dao.update(xmlPath+"doCommitApp", base);
			}
			
			if(i>0){
				return i;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	
	/**
	 * 退款管理-删除
	 * doDelApp
	 * @date 2013-10-11 上午12:21:43
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doDelApp(Map<String,Object> map){
		Object data_ = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(data_);
		List<Map<String,Object>> baseData = JSONArray.fromObject(data.get("getDetailData"));//提交申请
		int i=0;
		if(baseData!=null){
			for(int j=0; j<baseData.size();j++){
				Map<String,Object> base = baseData.get(j);
				base.put("USERNAME", map.get("USERNAME"));//用户名称
				base.put("USERCODE", map.get("USERCODE"));//用户编号
				i=Dao.delete(xmlPath+"delAppRententionDetail", base);//删除明细信息
				if(i>0){
					i=Dao.delete(xmlPath+"delAppRetention", base);//删除主表信息
				}
			}
			
			if(i>0){
				return i;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**************************************************************留购价退款审核-管理页面**************************************************************************/
	/**
	 * 留购价退款-审核管理页面
	 * findRetentionChedkData
	 * @date 2013-10-10 下午06:07:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page findRetentionChedkData(Map<String,Object> map){
		Page page = new Page();
		//首期数据获取
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"findRetentionCheckData", map);
//		List<Object> hexiao_satus = (List)new DataDictionaryMemcached().get("退款单状态");
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			JSONObject json = new JSONObject();
			Map<String,Object> m=(Map<String,Object>)list.get(i);
			json.put("RE_ID", m.get("RE_ID"));
			json.put("RE_CODE", m.get("RE_CODE"));//应付金额
			json.put("RE_PAYEE_NAME", m.get("RE_PAYEE_NAME"));//收款人
			json.put("RE_PAYEE_UNIT", m.get("RE_PAYEE_UNIT"));//收款单位
			json.put("RE_PAYEE_ADDRESS", m.get("RE_PAYEE_ADDRESS"));//收款人地址
			json.put("RE_PAYEE_BANK", m.get("RE_PAYEE_BANK"));//开户行
			json.put("RE_PAYEE_ACCOUNT", m.get("RE_PAYEE_ACCOUNT"));//开户行帐号
			json.put("RE_PAYEE_BANK_ADDR", m.get("RE_PAYEE_BANK_ADDR"));//开户行地址
			
			//确认状态
			if("0".equals(m.get("RE_STATUS").toString())){
				json.put("RE_STATUS", "未退款");
			}else if("1".equals(m.get("RE_STATUS").toString())){
				json.put("RE_STATUS", "评审中");
			}else {
				json.put("RE_STATUS", "已退款");
			}
			json.put("RE_PROJECT_COUNT", m.get("RE_PROJECT_COUNT"));//项目数量
//			json.put("FI_STATUS", m.get("FI_STATUS"));//核销状态
//			if(hexiao_satus!=null){
//				for(int j=0;j<hexiao_satus.size();j++){
//					Map<String,Object> hexiao = (Map<String, Object>) hexiao_satus.get(j);	
//					if(hexiao.get("CODE").equals(m.get("RE_STATUS").toString())){
//						json.put("FI_STATUS", hexiao.get("FLAG"));
//					}
//				}
//			}
			json.put("RE_DATE", m.get("RE_DATE"));//付款日期
			json.put("RE_MONEY", m.get("RE_MONEY"));//留购价金额
			json.put("NAME", m.get("NAME"));//供应商
			array.add(json);
		}		
		page.addDate(array, Dao.selectInt(xmlPath+"findRetentionCheckCount",map));//计数
		return page;
	} 
	
	/**
	 * 退款管理-审核
	 * doCheckedRetention
	 * @date 2013-10-11 上午12:38:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCheckedRetention(Map<String,Object> map){
		Object data_ = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(data_);
		List<Map<String,Object>> baseData = JSONArray.fromObject(data.get("getDetailL"));//提交申请
		int i=0;
		if(baseData!=null){
			for(int j=0; j<baseData.size();j++){
				Map<String,Object> base = baseData.get(j);
				base.put("USERNAME", map.get("USERNAME"));//用户名称
				base.put("USERCODE", map.get("USERCODE"));//用户编号
				i=Dao.update(xmlPath+"doCheckedRetention", base);
			}
			
			if(i>0){
				return i;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * 退款管理-驳回
	 * doCheckedRetention
	 * @date 2013-10-11 上午12:39:19
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doCancelRetention(Map<String,Object> map){
		Object data_ = map.get("data");
		Map<String,Object> data = JSONObject.fromObject(data_);
		List<Map<String,Object>> baseData = JSONArray.fromObject(data.get("getDetailL"));//提交申请
		int i=0;
		if(baseData!=null){
			for(int j=0; j<baseData.size();j++){
				Map<String,Object> base = baseData.get(j);
				base.put("USERNAME", map.get("USERNAME"));//用户名称
				base.put("USERCODE", map.get("USERCODE"));//用户编号
				i=Dao.update(xmlPath+"doCancelRetention", base);
			}
			
			if(i>0){
				return i;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	/**************************************************************留购价退款-申请/审核明细数据**************************************************************************/
	/**
	 * 留购价退款-审核管理页面
	 * findDetailApp
	 * @date 2013-10-10 下午06:07:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findDetailApp(Map<String,Object> map){
		map.put("LIUGOU", "留购价款");
		return Dao.selectList(xmlPath+"getAppDetail", map);
	} 
}
