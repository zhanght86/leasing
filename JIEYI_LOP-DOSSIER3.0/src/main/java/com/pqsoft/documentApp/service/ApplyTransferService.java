package com.pqsoft.documentApp.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class ApplyTransferService {

	private String xmlPath = "dossierApp.transfer.";
	
	/**
	 * 多档案移交申请
	 * @author yangxue
	 * @date 2015-6-16
	 * @param map
	 * @return
	 */
	public String doInsertTransfer(Map<String,Object> map){
		JSONObject json = JSONObject.fromObject(map.get("datas"));
		List<Map<String, Object>> list = json.getJSONArray("saveRecord");
		String ID = Dao.getSequence("SEQ_API_DOCUMENT_TRANSFER");
		map.put("USERCODE", Security.getUser().getCode());//用户名
		map.put("ID", ID);//档案归档申请单id
		map.put("HANDIN_DATE", json.get("HANDIN_DATE"));//提交时间爱你
		map.put("HANDIN_PERSON", json.get("HANDIN_PERSON"));//呈报人
		map.put("HANDIN_PHONE", json.get("HANDIN_PHONE"));//呈报人电话
		map.put("RECEIVE_PER", json.get("RECEIVE_PER"));//接收人名称
		map.put("RECEIVE_IDCARD", json.get("RECEIVE_IDCARD"));//接受人身份证编号
		map.put("RECEIVE_PHONE", json.get("RECEIVE_PHONE"));//接受人电话
		map.put("TRANSFER_TYPE", json.get("TRANSFER_TYPE"));//投递方式
		map.put("MAILING_ADDRESS", json.get("MAILING_ADDRESS"));//邮寄地址
		//map.put("INDUSTRY_TYPE", json.get("INDUSTRY_TYPE"));//事业部
		map.put("ZIP_CODE", json.get("ZIP_CODE"));//邮编
		map.put("REMARK", json.get("REMARK"));//备注
		map.put("STATUS", "1");//移交状态
		
		System.out.println("--yx----"+map);
		
		int i = Dao.insert(xmlPath+"doInsertTransfer", map);
		int t = 0;
		System.out.println("--y---x-"+list.size());
		if (i>0){
			for(int k=0; k<list.size(); k++){
				Map<String,Object> detial = list.get(k);
				detial.put("TRANSFER_ID", ID);
				detial.put("USERCODE", Security.getUser().getCode());
				t = Dao.insert(xmlPath+"doInsertTransferDetail", detial);//插入申请明细
			}
		}

		if(i>0 && t>0){
			return ID;
		}
		
		return "";
	}
	
	/**
	 * 查看移交申请数据
	 * @author yangxue
	 * @date 2015-6-16
	 * @param map
	 * @return
	 */
	public Object toShowTransferApp(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toShowTransferApp", map);
	}
	
	/**
	 * 查看移交合同
	 * @author yangxue
	 * @date 2015-6-16
	 * @param map
	 * @return
	 */
	public Object toShowTransferLease(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toShowTransferLease", map);
	}
	
	/**
	 * 查看移交档案资料明细
	 * @author yangxue
	 * @date 2015-6-16
	 * @param map
	 * @return
	 */
	public Object toShowTransferDetail(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toShowTransferDetail", map);
	}
	
	/**
	 * 查看移交流程所对应的归档数据id
	 * @author yangxue
	 * @date 2015-8-11
	 * @param TRANSFER_ID
	 * @return
	 */
	public String getApplyDossier(String TRANSFER_ID){
		return Dao.selectOne(xmlPath+"getApplyDossier", TRANSFER_ID);
	}
	
	/**
	 * 调整档案移交状态
	 * @param map
	 * @return
	 */
	public int doTransferStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"doTransferStatus",map);
	}
}
