package com.pqsoft.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import payment.api.system.CMBEnvironment;
import payment.api.system.PaymentEnvironment;
import payment.api.system.PaymentUserEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.gatheringbatch.Tx1610Request;
import payment.api.tx.gatheringbatch.Tx1610Response;
import payment.api.tx.gatheringbatch.Tx1620Request;
import payment.api.tx.gatheringbatch.Tx1620Response;
import payment.api.tx.gatheringbatch.Tx1650Response;
import payment.api.tx.paymentbatch.Tx1510Request;
import payment.api.tx.paymentbatch.Tx1510Response;
import payment.api.tx.paymentbatch.Tx1520Request;
import payment.api.tx.paymentbatch.Tx1520Response;
import payment.api.tx.paymentbatch.Tx1550Response;
import payment.api.tx.paymentbinding.Tx2501Request;
import payment.api.tx.paymentbinding.Tx2501Response;
import payment.api.tx.paymentbinding.Tx2502Request;
import payment.api.tx.paymentbinding.Tx2502Response;
import payment.api.tx.paymentbinding.Tx2503Request;
import payment.api.tx.paymentbinding.Tx2503Response;
import payment.api.vo.GatheringItem;
import payment.api.vo.PaymentItem;
import payment.tools.util.GUID;

import com.allinpay.XmlTools;
import com.allinpay.xmltrans.service.TranxServiceImpl;
import com.pqsoft.crm.service.BankSignMgService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;

public class FIinterface {
	public void tranx(List<Map<String,Object>> list,Map<String,Object> map,String trx_code)
	{
		String testTranURL="";
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("通联财务接口");
		Map<String,Object>  m=new HashMap<String,Object>();
		m=cwjk.get(0);
		testTranURL=m.get("CODE").toString();
		System.out.println("testTranURL="+testTranURL+"======================");
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
//		String reqsn="1404826943281";//交易流水号(交易结果查询时，待查询的原支付交易流水号)
//		String trx_code,busicode;//100001批量代收 100002批量代付 100011单笔实时代收 100014单笔实时代付
		String busicode="";
		TranxServiceImpl tranxService=new TranxServiceImpl();
		if("100002".equals(trx_code))//收款的时候，填写收款的业务代码
		{
			busicode="09900";
			map.put("TYPE", "1");
		}
		if("100001".equals(trx_code)){
			busicode="19900";
			map.put("TYPE", "2");
		}
		if("100003".equals(trx_code)){
			//首期款核销
			trx_code="100001";
			busicode="19900";
			map.put("TYPE", "3");
		}
		//设置安全提供者,注意，这一步尤为重要
		BouncyCastleProvider provider = new BouncyCastleProvider();
		XmlTools.initProvider(provider);
		try {
			tranxService.batchTranx1(testTranURL,trx_code,busicode,map,list, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取配置信息
	 * @return
	 * @author wanghl
	 * @datetime 2015年8月14日,下午6:00:25
	 */
	public Map<String,Object> getConfig(){
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("中金财务接口");
		Map<String,Object>  m=new HashMap<String,Object>();
		for(Map<String,Object> map : cwjk){
			if("环境参数".equals(map.get("FLAG").toString())){
				m.put("paymentConfigPath", map.get("CODE").toString());
//				m.put("paymentConfigPath", "D:/config/payment");
			}else if("机构号码".equals(map.get("FLAG").toString())){
				m.put("institutionID", map.get("CODE").toString());
//				m.put("institutionID", "000020");
			}
		}
		return m;
	}
	
	/**
	 * 是否启用
	 * @return
	 * @author wanghl
	 * @datetime 2015年8月21日,下午5:17:33
	 */
	public static String on_off(){
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("中金财务接口");
		for(Map<String,Object> map : cwjk){
			if("是否启用".equals(map.get("FLAG").toString())){
				if("1".equals(map.get("CODE").toString())){
					return map.get("CODE").toString();
				}
			}
		}
		return null;
	}
	
	/**
	 * 初始化支付环境
	 * @author wanghl
	 * @datetime 2015年8月17日,上午11:52:53
	 */
	public FIinterface(){
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("中金财务接口");
		for(Map<String,Object> map : cwjk){
			if("环境参数".equals(map.get("FLAG").toString())){
				try {
					// 初始化支付环境
					PaymentEnvironment.initialize(map.get("CODE").toString());
//					PaymentEnvironment.initialize("D:/config/payment");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 建立绑定关系   签约接口
	 * @param map
	 * @author wanghl
	 * @datetime 2015年8月14日,下午5:52:47
	 */
	public boolean Binding(Map<String,Object> map){
		try {
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
			// 创建交易请求对象
			Tx2501Request tx2501Request = new Tx2501Request();
			
			tx2501Request.setInstitutionID(config.get("institutionID")+"");
            tx2501Request.setTxCode("2501");
            tx2501Request.setTxSNBinding(map.get("TXSNBINDING").toString());//流水号
            Map<String,Object> m1;
            for(int j=0;j<bankcode.size();j++){
				m1=bankcode.get(j);
				//匹配银行ID
				if(map.get("BANKID").toString().contains(m1.get("FLAG").toString())){
					tx2501Request.setBankID(m1.get("CODE").toString());
					map.put("BANK_CODE", m1.get("CODE").toString());
					break;
				}
			}
            tx2501Request.setBankID(map.get("BANK_CODE").toString());
            tx2501Request.setAccountName(map.get("ACCOUNT_NAME").toString());//账户名称
            tx2501Request.setAccountNumber(map.get("ACCOUNT_NUMBER").toString());//账户号码
            tx2501Request.setIdentificationNumber(map.get("IDENTIFICATION_TYPE").toString());
            tx2501Request.setIdentificationType(map.get("IDENTIFICATION_NUMBER").toString());
            tx2501Request.setPhoneNumber(map.get("PHONE_NUMBER").toString());
            tx2501Request.setCardType(map.get("CARD_TYPE").toString());
//            tx2501Request.setValidDate(map.get("ValidDate").toString());
//            tx2501Request.setCvn2(map.get("Cvn2").toString());
            
            tx2501Request.process();
            String message = tx2501Request.getRequestMessage();
            String signature = tx2501Request.getRequestSignature();
            String txCode = "2501";
            String txName = "建立绑定关系";
            String TXSNBINDING = map.get("TXSNBINDING").toString();
            
            this.zjBatch(message, signature, txCode, txName, "", "","",TXSNBINDING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 查询绑定关系
	 * @author wanghl
	 * @datetime 2015年8月14日,下午6:32:10
	 */
	public boolean querySigning(String txSNBinding){
		try{
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			String txCode = "2502";
			// 创建交易请求对象
            Tx2502Request tx2502Request = new Tx2502Request();
            tx2502Request.setInstitutionID(config.get("institutionID")+"");
            tx2502Request.setTxCode(txCode);
            tx2502Request.setTxSNBinding(txSNBinding);
            // 执行报文处理
            tx2502Request.process();
            String message = tx2502Request.getRequestMessage();
            String signature = tx2502Request.getRequestSignature();
            String txName = "绑定关系查询";
            this.zjBatch(message, signature, txCode, txName, "", "","",txSNBinding);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 解除绑定关系
	 * @author wanghl
	 * @datetime 2015年8月17日,上午10:37:26
	 */
	public boolean relieveBinding(String txSNBinding){
		try{
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			String txCode = "2503";
			// 创建交易请求对象
            Tx2503Request tx2503Request = new Tx2503Request();

            tx2503Request.setInstitutionID(config.get("institutionID")+"");
            tx2503Request.setTxCode(txCode);
            tx2503Request.setTxSNBinding(txSNBinding);
            tx2503Request.setTxSNUnBinding(GUID.getTxNo());
            // 执行报文处理
            tx2503Request.process();
            String message = tx2503Request.getRequestMessage();
            String signature = tx2503Request.getRequestSignature();
            String txName = "解除绑定关系";
            
            this.zjBatch(message, signature, txCode, txName, "", "","","");
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 
	 * 中金批量代扣
	 * @param list
	 * @param map
	 * @param type  2代扣	
	 * @author wanghl
	 * @datetime 2015年8月12日,下午12:53:48
	 */
	public boolean daikou(List<Map<String,Object>> list,Map<String,Object> map,String type)throws Exception{
		
		if(StringUtils.isBlank(on_off())){
			return false;
		}
		Map<String, Object> config = this.getConfig();
		// 创建交易请求对象
        Tx1610Request txRequest = new Tx1610Request();
        //机构号码
        txRequest.setInstitutionID(config.get("institutionID")+"");
        //批次号
        String batchNo = list.get(0).get("BATCH_NO").toString();
        txRequest.setBatchNo(batchNo);
        //总金额
        txRequest.setTotalAmount(Long.parseLong(map.get("TOTAL_SUM").toString()));
        //总笔数
        txRequest.setTotalCount(Integer.parseInt(map.get("TOTAL_ITEM").toString()));
        //备注
        txRequest.setRemark("");
		
		ArrayList<GatheringItem> gatheringItemList = null;
        List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
        Map<String, Object> m1=new HashMap<String, Object>();
        if(list != null && list.size() > 0){
        	gatheringItemList = new ArrayList<GatheringItem>();
        	for(int i = 0; i < list.size(); i ++){
        		GatheringItem gatheringItem = new GatheringItem();
        		Map<String, Object> itemMap = list.get(i);
        		//流水号
        		gatheringItem.setItemNo(itemMap.get("ITEM_NO").toString());
                gatheringItem.setAccountName(itemMap.get("ACCOUNT_NAME").toString());
                gatheringItem.setAccountNumber(itemMap.get("ACCOUNT_NUMBER").toString());
                gatheringItem.setAccountType(Integer.parseInt(itemMap.get("ACCOUNT_TYPE").toString()));
                gatheringItem.setBankID(itemMap.get("BANK_CODE").toString());
//                String contractNo = GUID.getTxNo();
//                itemMap.put("CONTRACT_NO", contractNo);
//                gatheringItem.setContractNo(contractNo);
//                String contractUserID = GUID.getTxNo();
//                itemMap.put("CONTRACT_USERID", contractUserID);
//                gatheringItem.setContractUserID(contractUserID);
//	                gatheringItem.setSettlementFlag(itemMap.get("SETTLEMENT_FLAG").toString());
                gatheringItem.setAmount(Long.parseLong(itemMap.get("AMOUNT").toString()));
//	                gatheringItem.setBranchName(itemMap.get("BRANCH_NAME").toString());
//	                gatheringItem.setProvince(itemMap.get("PROVINCE").toString());
//	                gatheringItem.setCity(itemMap.get("CITY").toString());
//	                gatheringItem.setNote(itemMap.get("NOTE").toString());
//	                gatheringItem.setPhoneNumber(itemMap.get("PHONE_NUMBER").toString());
//	                gatheringItem.setEmail(itemMap.get("EMAIL").toString());
//	                gatheringItem.setIdentificationNumber(itemMap.get("IDENTIFICATION_NUMBER").toString());
//	                gatheringItem.setIdentificationType(itemMap.get("IDENTIFICATION_TYPE").toString());
                gatheringItemList.add(gatheringItem);
                
//                itemMap.put("BATCH_NO", batchNo);
//                itemMap.put("TYPE", type);//2代扣
//                //中间表
//                Dao.insert("rentWriteNew.insertZhongjinMiddle", itemMap);
        	}
        }
        
        txRequest.setGatheringItemList(gatheringItemList);
        // 执行报文处理
        txRequest.process();
        
        System.out.println("RequestPlainText--------"+txRequest.getRequestPlainText());
        System.out.println("RequestMessage--------"+txRequest.getRequestMessage());
        System.out.println("RequestSignature--------"+txRequest.getRequestSignature());
        
        String message = txRequest.getRequestMessage();
        String signature = txRequest.getRequestSignature();
        String txCode = "1610";
        String txName = "批量代扣";
        String flag = "";
        this.zjBatch(message, signature, txCode, txName, flag,batchNo,type,"");
		
		return true ;
	}
	
	/**
	 * 批量代扣查询
	 * @param batchNo 批次号
	 * @author wanghl
	 * @datetime 2015年8月13日,下午5:07:58
	 */
	public boolean queryDaikou(String batchNo){
		try {
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			// 创建交易请求对象
            Tx1620Request tx1620Request = new Tx1620Request();
            //机构号码
	        tx1620Request.setInstitutionID(config.get("institutionID")+"");
            tx1620Request.setBatchNo(batchNo);
            // 3.执行报文处理
            tx1620Request.process();
            
            String message = tx1620Request.getRequestMessage();
	        String signature = tx1620Request.getRequestSignature();
	        String txCode = "1620";
	        String txName = "批量代扣查询";
	        String flag = "";
	        this.zjBatch(message, signature, txCode, txName, flag,batchNo,"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 中金批量代付
	 * @param list
	 * @param map
	 * @param type 1 代付  5服务费
	 * @author wanghl
	 * @datetime 2015年8月13日,下午5:22:45
	 */
	public boolean daifu(List<Map<String,Object>> list,Map<String,Object> map,String type) {
		try {
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			// 创建交易请求对象
	        Tx1510Request txRequest = new Tx1510Request();
            //机构号码
	        txRequest.setInstitutionID(config.get("institutionID")+"");
	        //批次号
	        String batchNo = list.get(0).get("BATCH_NO").toString();
	        txRequest.setBatchNo(batchNo);
	        //总金额
	        txRequest.setTotalAmount(Long.parseLong(map.get("TOTAL_SUM").toString()));
	        //总笔数
	        txRequest.setTotalCount(Integer.parseInt(map.get("TOTAL_ITEM").toString()));
	        //备注
	        txRequest.setRemark("");
			
	        ArrayList<PaymentItem> itemList = null;
	        ArrayList<String> txNoList = new ArrayList<String>();
	        List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
	        Map<String, Object> m1=new HashMap<String, Object>();
	        if(list != null && list.size() > 0){
	        	itemList = new ArrayList<PaymentItem>();
	        	for(int i = 0; i < list.size(); i ++){
	        		PaymentItem item = new PaymentItem();
	        		Map<String, Object> itemMap = list.get(i);
	        		//流水号
	        		item.setItemNo(itemMap.get("ITEM_NO").toString());
	                item.setAccountName(itemMap.get("ACCOUNT_NAME").toString());
	                item.setAccountNumber(itemMap.get("ACCOUNT_NUMBER").toString());
	                item.setAccountType(Integer.parseInt(itemMap.get("ACCOUNT_TYPE").toString()));
//	                gatheringItem.setSettlementFlag(itemMap.get("SETTLEMENT_FLAG").toString());
	                item.setBankID(itemMap.get("BANK_CODE").toString());
	                item.setAmount(Long.parseLong(itemMap.get("AMOUNT").toString()));
	                item.setBranchName(itemMap.get("BRANCH_NAME").toString());
	                item.setProvince(itemMap.get("PROVINCE").toString());
	                item.setCity(itemMap.get("CITY").toString());
//	                item.setNote(itemMap.get("NOTE").toString());
//	                gatheringItem.setPhoneNumber(itemMap.get("PHONE_NUMBER").toString());
//	                gatheringItem.setEmail(itemMap.get("EMAIL").toString());
//	                item.setIdentificationNumber(itemMap.get("IDENTIFICATION_NUMBER").toString());
//	                item.setIdentificationType(itemMap.get("IDENTIFICATION_TYPE").toString());
	                itemList.add(item);
	        	}
	        }
            
	        txRequest.setItemList(itemList);
	        // 执行报文处理
	        txRequest.process();
	        
	        System.out.println("RequestPlainText--------"+txRequest.getRequestPlainText());
	        System.out.println("RequestMessage--------"+txRequest.getRequestMessage());
	        System.out.println("RequestSignature--------"+txRequest.getRequestSignature());
	        
	        String message = txRequest.getRequestMessage();
	        String signature = txRequest.getRequestSignature();
	        String txCode = "1510";
	        String txName = "批量代付";
	        String flag = "";
	        this.zjBatch(message, signature, txCode, txName, flag,batchNo,type,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 批量代付查询
	 * @param batchNo
	 * @author wanghl
	 * @datetime 2015年8月13日,下午5:34:06
	 */
	public boolean queryDaifu(String batchNo){
		try {
			if(StringUtils.isBlank(on_off())){
				return false;
			}
			Map<String, Object> config = this.getConfig();
			// 创建交易请求对象
            Tx1520Request txRequest = new Tx1520Request();
            //机构号码
	        txRequest.setInstitutionID(config.get("institutionID")+"");
            txRequest.setBatchNo(batchNo);
            // 3.执行报文处理
            txRequest.process();
            
            String message = txRequest.getRequestMessage();
	        String signature = txRequest.getRequestSignature();
	        String txCode = "1520";
	        String txName = "批量代付查询";
	        String flag = "";
	        this.zjBatch(message, signature, txCode, txName, flag,batchNo,"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @param message
	 * @param signature
	 * @param txCode
	 * @param txName
	 * @param flag
	 * @param batchNo
	 * @param type  类型1代付2代扣    5服务费
	 * @author wanghl
	 * @datetime 2015年8月21日,下午12:05:52
	 */
	public void zjBatch(String message,String signature,String txCode,String txName,String flag,String batchNo,String type,String TXSNBINDING){
		try {
            // 与支付平台进行通讯
            TxMessenger txMessenger = new TxMessenger();
            String[] respMsg = null;
            // Flag=10:cmb, 20:paymentAccount
            if ("10".equals(flag)) {
                respMsg = txMessenger.send(message, signature, CMBEnvironment.cmbtxURL);// 0:message;
            } else if ("20".equals(flag)) {
                respMsg = txMessenger.send(message, signature, PaymentUserEnvironment.paymentusertxURL);
            } else {
                respMsg = txMessenger.send(message, signature);// 0:message;
            }
            String plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
            if ("1510".equals(txCode)) {//批量代付
                Tx1510Response tx1510Response = new Tx1510Response(respMsg[0], respMsg[1]);
                System.out.println("tx1510Response.getResponsePlainText()==="+tx1510Response.getResponsePlainText());
                
                Map<String,Object> map = new HashMap<String, Object>();
            	map.put("BATCH_NO", batchNo);
            	map.put("USE_STATE", "0");
            	map.put("SUBMIT_ERR_MSG", tx1510Response.getMessage() );
            	map.put("SUBMIT_STATE", tx1510Response.getCode());
            	Dao.update("rentWriteNew.updateZhongjinMiddle", map);
                if ("2000".equals(tx1510Response.getCode())) {
                	
                }// TODO 如果提交失败需要处理标记。
                else {
                	map.put("USE_STATE", "1");
                	map.put("RETURN_ERR_MSG", "提交失败了");
                	map.put("RETURN_STATUS", "40");
                	Dao.update("rentWriteNew.updateZhongjinState",map);
                }
            } else if ("1520".equals(txCode)) {//批量代付查询
                Tx1520Response tx1520Response = new Tx1520Response(respMsg[0], respMsg[1]);
                System.out.println("tx1520Response.getResponsePlainText()==="+tx1520Response.getResponsePlainText());
                
                Map<String,Object> map=new HashMap<String,Object>();
    			Map<String,Object> m=new HashMap<String,Object>();
                if ("2000".equals(tx1520Response.getCode())) {
                	System.out.println("查询成功");
                    if (tx1520Response.getItemList() != null) {
                        int size = tx1520Response.getItemList().size();
                        for (int i = 0; i < size; i++) {
                            PaymentItem item = tx1520Response.getItemList().get(i);
                            map=new HashMap<String,Object>();
//        					map.put("BATCH_NO", item.getBatchNo());
                            if(item.getStatus()==30 || item.getStatus() == 40){// 银行有结果才做记录
                            	map.put("USE_STATE", "1");
                            	map.put("BANK_DATE", item.getBankTxTime());
                            }else{
                            	map.put("USE_STATE", "0");
                            }
        					map.put("BATCH_NO", batchNo);
        					map.put("ITEM_NO", item.getItemNo());
        					map.put("RETURN_STATUS", item.getStatus());
        					map.put("RETURN_ERR_MSG", item.getResponseMessage());
        					m=Dao.selectOne("rentWriteNew.getZhongjinMiddleStatus", map);
        					if(!"2".equals(m.get("USE_STATE").toString())){
        						Dao.update("rentWriteNew.updZhongjinMiddleReturn",map);
        					}
                        }
                    }
                    // 处理业务
                }else{
                	map=new HashMap<String,Object>();
//					map.put("BATCH_NO", tx1520Response.getBatchNo());
					map.put("BATCH_NO", batchNo);
					map.put("USE_STATE", "0");
					map.put("RETURN_STATUS", tx1520Response.getCode());
					map.put("RETURN_ERR_MSG", tx1520Response.getMessage());
					List<Map<String,Object>> list=Dao.selectList("rentWriteNew.getZhongjinMiddleStatus", map);
					m=list.get(0);
					if(!"2".equals(m.get("USE_STATE").toString())){
						Dao.update("rentWriteNew.updZhongjinMiddleReturn",map);
					}
                }
            } else if ("1550".equals(txCode)) {
                Tx1550Response tx1550Response = new Tx1550Response(respMsg[0], respMsg[1]);
                if ("2000".equals(tx1550Response.getCode())) {
                    if (tx1550Response.getItemList() != null) {
                        int size = tx1550Response.getItemList().size();
                        for (int i = 0; i < size; i++) {
                            PaymentItem item = tx1550Response.getItemList().get(i);
                        }
                    }

                    // 处理业务
                }
            } else if ("1610".equals(txCode)) {//批量代扣
                Tx1610Response tx1610Response = new Tx1610Response(respMsg[0], respMsg[1]);
                System.out.println("tx1610Response.getResponsePlainText()==="+tx1610Response.getResponsePlainText());
                System.out.println("[Message]=[" + tx1610Response.getMessage() + "]");
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("BATCH_NO", batchNo);
                map.put("USE_STATE", "0");
                map.put("SUBMIT_ERR_MSG", tx1610Response.getMessage() );
                map.put("SUBMIT_STATE", tx1610Response.getCode());
                Dao.update("rentWriteNew.updateZhongjinMiddle", map);
                if ("2000".equals(tx1610Response.getCode())) {
                    // 处理业务
                }// TODO 如果提交失败需要处理标记。
                else{
                	map.put("USE_STATE", "1");
                	map.put("RETURN_ERR_MSG", "提交失败了");
                	map.put("RETURN_STATUS", "40");
                	Dao.update("rentWriteNew.updateZhongjinState",map);
                }
            } else if ("1620".equals(txCode)) {//批量代扣查询
                Tx1620Response tx1620Response = new Tx1620Response(respMsg[0], respMsg[1]);
                System.out.println("tx1620Response.getResponsePlainText()==="+tx1620Response.getResponsePlainText());
                
                Map<String,Object> map=new HashMap<String,Object>();
    			Map<String,Object> m=new HashMap<String,Object>();
                if ("2000".equals(tx1620Response.getCode())) {
                    if (tx1620Response.getGatheringItemList() != null) {
                        int size = tx1620Response.getGatheringItemList().size();
                        for (int i = 0; i < size; i++) {
                            GatheringItem item = tx1620Response.getGatheringItemList().get(i);
                            
                            map=new HashMap<String,Object>();
//        					map.put("BATCH_NO", item.getBatchNo());
                            map.put("BATCH_NO", batchNo);
        					map.put("ITEM_NO", item.getItemNo());
        					if(item.getStatus()==30 || item.getStatus() == 40){
        						map.put("USE_STATE", "1");
        						map.put("BANK_DATE", item.getBankTxTime());
        					}else{
        						map.put("USE_STATE", "0");
        					}
        					map.put("RETURN_STATUS", item.getStatus());
        					map.put("RETURN_ERR_MSG", item.getResponseMessage());
        					m=Dao.selectOne("rentWriteNew.getZhongjinMiddleStatus", map);
        					if(!"2".equals(m.get("USE_STATE").toString())){
        						Dao.update("rentWriteNew.updZhongjinMiddleReturn",map);
        					}
                        }
                    }
                    // 处理业务
                }else{
                	map=new HashMap<String,Object>();
//					map.put("BATCH_NO", tx1620Response.getBatchNo());
					map.put("BATCH_NO", batchNo);
					map.put("USE_STATE", "0");
					map.put("RETURN_STATUS", tx1620Response.getCode());
					map.put("RETURN_ERR_MSG", tx1620Response.getMessage());
					List<Map<String,Object>> list=Dao.selectList("rentWriteNew.getZhongjinMiddleStatus", map);
					m = list.get(0);
					if(!"2".equals(m.get("USE_STATE").toString())){
						Dao.update("rentWriteNew.updZhongjinMiddleReturn",map);
					}
                }
            } else if ("1650".equals(txCode)) {
                Tx1650Response tx1650Response = new Tx1650Response(respMsg[0], respMsg[1]);
                if ("2000".equals(tx1650Response.getCode())) {
                    if (tx1650Response.getGatheringItemList() != null) {
                        int size = tx1650Response.getGatheringItemList().size();
                        for (int i = 0; i < size; i++) {
                            GatheringItem item = tx1650Response.getGatheringItemList().get(i);
                        }
                    }

                    // 处理业务
                }
            } else if ("2501".equals(txCode)) {//建立绑定关系
                Tx2501Response tx2501Response = new Tx2501Response(respMsg[0], respMsg[1]);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("TXSNBINDING", TXSNBINDING);
                map.put("SUBMIT_CODE", tx2501Response.getCode());
                map.put("SUBMIT_MESSAGE", tx2501Response.getMessage());
                map.put("SUBMIT_DATE", new Date());
                map.put("STATUS", "1");
                if ("2000".equals(tx2501Response.getCode())) {
                    // 处理业务
                	map.put("SUBMIT_STATUS", tx2501Response.getStatus());
                	map.put("SUBMIT_BANKTXTIME", tx2501Response.getBankTxTime());
                	map.put("SUBMIT_RESPONSE_CODE", tx2501Response.getResponseCode());
                	map.put("SUBMIT_RESPONSE_MESSAGE", tx2501Response.getResponseMessage());
                	Dao.update("BankAccountSign.updateZhongjinBinding",map);
                }else{
//                	map.put("SUBMIT_STATUS", "20");
//                	Dao.update("BankAccountSign.updateZhongjinBinding",map);
                	//修改签约表状态为1，删除接口表该条数据
                	map.put("SIGN_FLAG","1");
                	map.put("AGREEMENTNO", TXSNBINDING);
        			Dao.update("BankAccountSign.updateBankSignStatus",map);
        			Dao.delete("BankAccountSign.deleteZhongjinBinding", map);
                }
            } else if ("2502".equals(txCode)) {//绑定关系查询
                Tx2502Response tx2502Response = new Tx2502Response(respMsg[0], respMsg[1]);
                Map<String,Object> map = new HashMap<String, Object>();
                if ("2000".equals(tx2502Response.getCode())) {
                    // 处理业务
                	map.put("TXSNBINDING", TXSNBINDING);
                	map.put("RETURN_CODE", tx2502Response.getCode());
                	map.put("RETURN_MESSAGE", tx2502Response.getMessage());
                	map.put("RETURN_DATE", new Date());
                	map.put("STATUS", "1");
                	if("10".equals(tx2502Response.getStatus())){//10绑定处理中 20绑定失败 30绑定成功 40解绑成功
                		map.put("STATUS", "1");
                	}
                	else if("20".equals(tx2502Response.getStatus())){
                		map.put("STATUS", "2");
                	}else if("30".equals(tx2502Response.getStatus())){
                		map.put("STATUS", "2");
                	}else if("40".equals(tx2502Response.getStatus())){
                		map.put("STATUS", "2");
                	}
                	
                	map.put("RETURN_STATUS", tx2502Response.getStatus());
                	map.put("RETURN_RESPONSE_CODE",  tx2502Response.getResponseCode());
                	map.put("RETURN_RESPONSE_MESSAGE", tx2502Response.getResponseMessage());
                	map.put("RETURN_ISSINSCODE", tx2502Response.getIssInsCode());
                	map.put("RETURN_PAYCARDTYPE", tx2502Response.getPayCardType());
                	Dao.update("BankAccountSign.updateZhongjinBinding",map);
                	
        			BankSignMgService service = new BankSignMgService();
        			service.bankSignBackInfo(map);
                }
            } else if ("2503".equals(txCode)) {//解除绑定关系
                Tx2503Response tx2503Response = new Tx2503Response(respMsg[0], respMsg[1]);
                if ("2000".equals(tx2503Response.getCode())) {
                    // 处理业务
                }
            }
		} catch (Exception e) {
            e.printStackTrace();
        }    
	}
	
	
	//测试代扣
	public void test(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ITEM_NO", "15081315075263631142");
		map.put("ACCOUNT_NAME", "中国民生银行");
		map.put("ACCOUNT_NUMBER", "1508185215010809");
		map.put("ACCOUNT_TYPE", "11");
		map.put("AMOUNT", "23252024");
		
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("TOTAL_SUM", "23252024");
		map.put("TOTAL_ITEM", "1");
		
		try {
			this.daikou(list, map, "2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//测试代扣查询
	public void test1(){
		this.queryDaikou("15081315075536171988");
	}
	
	//测试代付
	public void test3(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ITEM_NO", "15081315075263631142");
		map.put("ACCOUNT_NAME", "中国民生银行");
		map.put("ACCOUNT_NUMBER", "1508185215010809");
		map.put("ACCOUNT_TYPE", "11");
		map.put("AMOUNT", "23252024");
		map.put("BRANCH_NAME", "北京市宣武支行");
		map.put("PROVINCE", "北京");
		map.put("CITY", "北京");
		
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("TOTAL_SUM", "23252024");
		map.put("TOTAL_ITEM", "1");
		
		this.daifu(list, map, "1");
	}
	//测试代付查询
	public void test4(){
		this.queryDaikou("15081315075536171988");
	}
}
