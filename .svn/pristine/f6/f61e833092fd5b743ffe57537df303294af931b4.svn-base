/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml 测试demo
 */
package com.allinpay.xmltrans.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.aipg.acctvalid.ValbSum;
import com.aipg.acctvalid.ValidBD;
import com.aipg.acctvalid.ValidBReq;
import com.aipg.acctvalid.VbDetail;
import com.aipg.agrmsync.SignInfoDetail;
import com.aipg.agrmsync.SignInfoSync;
import com.aipg.ahquery.AHQueryReq;
import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
import com.aipg.payreq.Trans_Sum;
import com.aipg.qtd.QTDReq;
import com.aipg.rtreq.Trans;
import com.aipg.rtrsp.TransRet;
import com.aipg.signquery.NSignReq;
import com.aipg.signquery.QSignDetail;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.singleacctvalid.ValidRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.allinpay.XmlTools;
import com.allinpay.xmltrans.pojo.TranxCon;
import com.allinpay.xmltrans.tools.FileUtil;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 */
public class TranxServiceImpl {
	TranxCon tranxContants=new TranxCon();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 日期：Sep 4, 2012
	 * 功能：
	 * @param trx_code 
	 * @throws Exception 
	 */
	public void batchTranx(String url,String trx_code,String busicode, boolean isTLTFront) throws Exception {
		
		String xml="";
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq(trx_code);
		aipg.setINFO(info);
		Body body = new Body() ;
		Trans_Sum trans_sum = new Trans_Sum() ;
		trans_sum.setBUSINESS_CODE(busicode) ;
		trans_sum.setMERCHANT_ID(tranxContants.merchantId) ;
		trans_sum.setTOTAL_ITEM("3") ;
		trans_sum.setTOTAL_SUM("100002") ;
		body.setTRANS_SUM(trans_sum) ;
		List <Trans_Detail>transList = new ArrayList<Trans_Detail>() ;
		Trans_Detail trans_detail = new Trans_Detail() ;
		Trans_Detail trans_detail2 = new Trans_Detail() ;
		Trans_Detail trans_detail3 = new Trans_Detail() ;
		trans_detail.setSN("0001") ;
    	trans_detail.setACCOUNT_NAME("许燕") ;
 		trans_detail.setACCOUNT_PROP("0") ;
		trans_detail.setACCOUNT_NO("603023061216191772") ;
		trans_detail.setBANK_CODE("103") ;
		trans_detail.setAMOUNT("100000") ;
		trans_detail.setCURRENCY("NBK");
		transList.add(trans_detail) ;
		
		trans_detail2.setSN("0002") ;
		trans_detail2.setACCOUNT_NAME("系统对接测试02") ;
		trans_detail2.setACCOUNT_NO("622682-0013800763464") ;
		trans_detail2.setBANK_CODE("103") ;
		trans_detail2.setAMOUNT("1") ;
		trans_detail2.setCURRENCY("CNY");
		trans_detail2.setUNION_BANK("234234523523");
		transList.add(trans_detail2);
		
		trans_detail3.setSN("0003") ;
		trans_detail3.setACCOUNT_NAME("系统对接测试03") ;
		trans_detail3.setACCOUNT_NO("621034-32645-1271") ;
		trans_detail3.setBANK_CODE("103") ;
		trans_detail3.setAMOUNT("1") ;
		trans_detail3.setUNION_BANK("234234523523");
		transList.add(trans_detail3);
        body.setDetails(transList) ;
        aipg.addTrx(body) ;
		
        xml=XmlTools.buildXml(aipg,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}

	
public void batchTranx1(String url,String trx_code,String busicode,Map<String,Object> map,List<Map<String,Object>> list, boolean isTLTFront) throws Exception {
	
		String xml="";
		//INFO
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq(trx_code);
		aipg.setINFO(info);
		Body body = new Body() ;
		//TRANS_SUM
		Trans_Sum trans_sum = new Trans_Sum() ;
		trans_sum.setBUSINESS_CODE(busicode) ;
		trans_sum.setMERCHANT_ID(tranxContants.merchantId) ;
		trans_sum.setTOTAL_ITEM(map.get("TOTAL_ITEM").toString()) ;
		trans_sum.setTOTAL_SUM(map.get("TOTAL_SUM").toString()) ;
		body.setTRANS_SUM(trans_sum) ;
		//TRANS_DETAILS
		List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("银行代码");
		Map<String, Object> m1=new HashMap<String, Object>();
		List <Trans_Detail>transList = new ArrayList<Trans_Detail>() ;
		for (int i = 0; i < list.size(); i++) {
			Trans_Detail trans_detail = new Trans_Detail() ;
			Map<String,Object> m=new HashMap<String,Object>();
			m=list.get(i);
			m.put("TYPE", map.get("TYPE"));
			m.put("REQ_SN_MAIN", info.getREQ_SN());
			trans_detail.setSN(m.get("SN").toString()) ;
			trans_detail.setE_USER_CODE(m.get("CUST_USERID").toString());
			for(int j=0;j<bankcode.size();j++){
				m1=bankcode.get(j);
				if(m.get("BANK_NAME").toString().contains(m1.get("FLAG").toString())){
					trans_detail.setBANK_CODE(m1.get("CODE").toString()) ;
				}
			}
//			trans_detail.setBANK_CODE("103") ;
			trans_detail.setACCOUNT_TYPE(m.get("ACCOUNT_TYPE").toString());
			trans_detail.setACCOUNT_NO(m.get("ACCOUNT_NO").toString()) ;
			trans_detail.setACCOUNT_PROP(m.get("ACCOUNT_PROP").toString());
	    	trans_detail.setACCOUNT_NAME(m.get("ACCOUNT_NAME").toString()) ;
	 		trans_detail.setACCOUNT_PROP("0") ;
			trans_detail.setAMOUNT(m.get("AMOUNT").toString()) ;
			trans_detail.setCURRENCY("CNY");
			trans_detail.setREMARK(m.get("REMARK").toString());
			trans_detail.setBANK_NAME(m.get("BANK_NAME").toString());
			transList.add(trans_detail) ;
			Dao.insert("rentWriteNew.insTranxMiddle",m);
		}
        body.setDetails(transList) ;
        aipg.addTrx(body) ;
		
        xml=XmlTools.buildXml(aipg,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}
public void batchTranx3(String url,String trx_code,String busicode, boolean isTLTFront) throws Exception {
	
	String xml="";
	//INFO
	AipgReq aipg=new AipgReq();
	InfoReq info=makeReq(trx_code);
	aipg.setINFO(info);
	Body body = new Body() ;
	//TRANS_SUM
	Trans_Sum trans_sum = new Trans_Sum() ;
	trans_sum.setBUSINESS_CODE(busicode) ;
	trans_sum.setMERCHANT_ID(tranxContants.merchantId) ;
	Map<String,Object> map=Dao.selectOne("paymentApply.getPaymentCount");
	trans_sum.setTOTAL_ITEM(map.get("BS").toString()) ;
	trans_sum.setTOTAL_SUM(map.get("JE").toString()) ;
	body.setTRANS_SUM(trans_sum) ;
	//TRANS_DETAILS
	List <Trans_Detail>transList = new ArrayList<Trans_Detail>() ;
	List<Map<String,Object>> list=Dao.selectList("paymentApply.getPaymentAll");
	for (int i = 0; i < list.size(); i++) {
		Trans_Detail trans_detail = new Trans_Detail() ;
		Map<String,Object> m=new HashMap<String,Object>();
		m=list.get(i);
		trans_detail.setSN(m.get("ID").toString()) ;
    	trans_detail.setACCOUNT_NAME(m.get("NAME").toString()) ;
 		trans_detail.setACCOUNT_PROP("0") ;
		trans_detail.setACCOUNT_NO(m.get("PAY_BANK_ACCOUNT").toString()) ;
		trans_detail.setBANK_CODE("102") ;
		trans_detail.setAMOUNT(m.get("PAYMENT_MONEY").toString()) ;
		trans_detail.setCURRENCY("NBK");
		transList.add(trans_detail) ;
	}
    body.setDetails(transList) ;
    aipg.addTrx(body) ;
	
    xml=XmlTools.buildXml(aipg,true);
	dealRet(sendToTlt(xml,isTLTFront,url));
}

	/**
	 * 组装报文头部
	 * @param trxcod
	 * @return
	 *日期：Sep 9, 2012
	 */
	private InfoReq makeReq(String trxcod)
	{
		  
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(tranxContants.merchantId+"-"+System.currentTimeMillis());
		info.setUSER_NAME(tranxContants.userName);
		System.out.println(tranxContants.userName+"====================");
		info.setUSER_PASS(tranxContants.password);
		info.setLEVEL("0");
		info.setDATA_TYPE("2");
		info.setVERSION("03");
		if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)){
			info.setMERCHANT_ID(tranxContants.merchantId);
		}
		return info;
	}
	public String sendXml(String xml,String url,boolean isFront) throws UnsupportedEncodingException, Exception{
		System.out.println("======================发送报文======================：\n"+xml);
		String resp=XmlTools.send(url,xml);
		System.out.println("======================响应内容======================") ;
//		System.out.println(resp) ;
		boolean flag= this.verifyMsg(resp, tranxContants.tltcerPath,isFront);
		if(flag){
			System.out.println("响应内容验证通过") ;
		}else{
			System.out.println("响应内容验证不通过") ;
		}
		return resp;
	}
	
	public String sendToTlt(String xml,boolean flag,String url) {
		try{
			if(!flag){
				xml=this.signMsg(xml);
			}else{
				xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml,url,flag);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof ConnectException||e instanceof ConnectException){
				System.out.println("请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易");
			}
		}
		return "请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易";
	}
	/**
	 * 报文签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public String signMsg(String xml) throws Exception{
		xml=XmlTools.signMsg(xml, tranxContants.pfxPath,tranxContants.pfxPassword, false);
		return xml;
	}
	
	/**
	 * 验证签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		 boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
		System.out.println("验签结果["+flag+"]") ;
		return flag;
	}
	
	/**
	 *下载对账文件
	 * @see com.aipg.core.TranxOperationInf#downBills(java.lang.String, boolean)
	 */
	public void downBills(String url, boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("200002");
		TransQueryReq dqr=new TransQueryReq();
		aipgReq.setINFO(info);
		aipgReq.addTrx(dqr);
		dqr.setSTATUS(2);
		dqr.setMERCHANT_ID(tranxContants.merchantId);
		dqr.setTYPE(1) ;
		dqr.setSTART_DAY("20140506010000");
		dqr.setEND_DAY("20140506235959");
		dqr.setCONTFEE("1") ;
		
		xml=XmlTools.buildXml(aipgReq,true);
		String resp=sendToTlt(xml,isTLTFront,url);
		//解释并写对账文件
		writeBill(resp);
	}

	/**
	 * @param resp
	 * @throws Exception 
	 */
	private void writeBill(String resp) throws Exception {
		
		int iStart = resp.indexOf("<CONTENT>");
		if(iStart==-1) throw new Exception("XML报文中不存在<CONTENT>");
		int end = resp.indexOf("</CONTENT>");
		if(end==-1) throw new Exception("XML报文中不存在</CONTENT>");	
		String billContext = resp.substring(iStart + 9, end);
		
		//写文件
		FileOutputStream sos=null;
		sos=new FileOutputStream(new File("bills/bill.gz"));
		Base64InputStream b64is=new Base64InputStream(IOUtils.toInputStream(billContext),false);
		IOUtils.copy(b64is, sos);
		IOUtils.closeQuietly(b64is);
		//解压
		ZipInputStream zin=new ZipInputStream(new FileInputStream(new File("bills/bill.gz")));
		ZipEntry zipEntry=null;
		 while ((zipEntry = zin.getNextEntry()) != null) {  
			 String entryName = zipEntry.getName().toLowerCase(); 
			 FileOutputStream os = new FileOutputStream("bills/"+entryName);  
             // Transfer bytes from the ZIP file to the output file  
             byte[] buf = new byte[1024];  
             int len;  
             while ((len = zin.read(buf)) > 0) {  
                 os.write(buf, 0, len);  
             }  
             os.close();  
             zin.closeEntry();
		 }
	}	/**
	 * 签约通知
	 *  
	 */
	public void signNotice(String url, boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("210003");//交易类型
		aipgReq.setINFO(info); 
		NSignReq nsign=new NSignReq();
		QSignDetail notify=new QSignDetail();
		notify.setACCTNAME("testt2");//账户名
		notify.setAGREEMENTNO("0002000012");//签约协议号
		notify.setACCT("6222023700014285995");//银行账号
		notify.setSIGNTYPE("2");
		notify.setSTATUS("2");
		nsign.addDtl(notify);
		aipgReq.addTrx(nsign);
		xml=XmlTools.buildXml(aipgReq,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}
	public void signNotice1(String url, boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("210011");//交易类型
		aipgReq.setINFO(info); 
		SignInfoSync sync=new SignInfoSync();
		SignInfoDetail detail=new SignInfoDetail();
		detail.setAGREEMENTNO("10000001");
		detail.setMOBILE("15101064167");
		detail.setACCT("11014748807003");
		detail.setACNAME("建元鼎铭国际融资租赁有限公司");
		detail.setBANKCODE("4105840");
		detail.setBANKPROV("");
		detail.setBANKCITY("");
		detail.setBANKNAME("平安银行北京国贸支行");
		detail.setCNAPSNO("");
		detail.setACCTTYPE("00");
		detail.setACCTPROP("1");
		detail.setIDTYPE("");
		detail.setIDNO("");
		detail.setMAXSINGLEAMT(0);
		detail.setDAYMAXSUCCCNT(0);
		detail.setDAYMAXSUCCAMT(0);
		detail.setMONMAXSUCCCNT(0);
		detail.setMONMAXSUCCAMT(0);
		detail.setAGRDEADLINE("20150531");
		sync.addDetail(detail);
		sync.setTOTAL(1);
		sync.setMERCHANT_ID(tranxContants.merchantId);
		sync.setCONTRACTNO("0");
		aipgReq.addTrx(sync);
		xml=XmlTools.buildXml(aipgReq,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}

	/**
	 * 下载简单对账文件
	 */
	public void downSimpleBills(String uRL11, boolean isTLTFront) throws Exception {
		String MERID =tranxContants.merchantId;
		String SETTDAY ="20130527";
		String REQTIME ="20130527121212";//df.format(new Date());
		String CONTFEE ="";
		String SIGN ="";
		CONTFEE=SETTDAY+"|"+REQTIME+"|"+MERID;
		System.out.println(CONTFEE);
		SIGN=XmlTools.signPlain(CONTFEE, tranxContants.getPfxPath(), tranxContants.getPfxPassword(), false);
		uRL11=uRL11.replaceAll("@xxx", SETTDAY).replaceAll("@yyy", REQTIME).replaceAll("@zzz", MERID).replaceAll("@sss", SIGN);
		System.out.println(uRL11);
		CONTFEE=sendXml("",uRL11,true);
		FileUtil.saveToFile(CONTFEE, "bill.txt", "");//默认编码GBK
	}
	/**
	 * 日期：Sep 4, 2012
	 * 功能：实时单笔代收付，100011是实时代笔代收，100014是实时单笔代付
	 * @param trx_code 
	 * @throws Exception 
	 */
	public void singleTranx(String url,String trx_code, String busicode, boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq(trx_code);
		aipg.setINFO(info);
		Trans trans=new Trans();
		trans.setBUSINESS_CODE(busicode);
		trans.setMERCHANT_ID(tranxContants.merchantId);
		trans.setSUBMIT_TIME(df.format(new Date()));
		trans.setACCOUNT_NAME("testl8");
		trans.setACCOUNT_NO("6225886551142015");
		trans.setACCOUNT_PROP("0");
//		trans.setACCOUNT_TYPE("01");
		trans.setAMOUNT("100000");
		trans.setBANK_CODE("0308");
		trans.setCURRENCY("CNY");
		trans.setCUST_USERID("252523524253xx");
		trans.setTEL("18617315452");
		aipg.addTrx(trans);
		
	    xml=XmlTools.buildXml(aipg,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
		
	}
	/**
	 * @param reqsn 交易流水号
	 * @param url 通联地址
	 * @param isTLTFront 是否通过前置机
	 * @param startDate YYYYMMDDHHmmss
	 * @param endDate YYYYMMDDHHmmss
	 * 日期：Sep 4, 2012
	 * 功能：交易结果查询
	 * @throws Exception 
	 */
	public void queryTradeNew(String url,String reqsn,boolean isTLTFront,String startDate,String endDate) throws Exception {
	 
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("200004");
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("BUSINESS_SN_MAIN", info.getREQ_SN());
		m.put("REQ_SN_MAIN", reqsn);
		Dao.update("paymentApply.updBusinessSnMain",m);
		aipgReq.setINFO(info);
		TransQueryReq dr=new TransQueryReq();
		aipgReq.addTrx(dr);
		dr.setMERCHANT_ID(tranxContants.merchantId) ;
		dr.setQUERY_SN(reqsn);
		dr.setSTATUS(2);
		dr.setTYPE(1) ;
		if(reqsn==null||"".equals(reqsn)){
			dr.setSTART_DAY(startDate);
			dr.setEND_DAY(endDate);
		}
		xml=XmlTools.buildXml(aipgReq,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}

 
	/**
	 * @param url 接口地址
	 * @param isTLTFront 是否发送前置机（特殊商户使用）
	 * @param startDate yyyyMMdd
	 * @param endDate  yyyyMMdd 
	 *  功能： 历史余额查询 300001
	 */
	public void merAcctBalance(String url,boolean isTLTFront,String startDate,String endDate) {
		String xml="";
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq("300001");//交易码
		aipg.setINFO(info);
		AHQueryReq ahquery=new AHQueryReq();
		ahquery.setACCTNO("201303000000000130440");//商户在通联的虚拟账号
		ahquery.setSTARTDAY(startDate);//查询开始日期
		ahquery.setENDDAY(endDate);//查询结束日期
		aipg.addTrx(ahquery);
		
		xml=XmlTools.buildXml(aipg,true); 
		sendToTlt(xml,isTLTFront,url);
		System.err.println("注意：CLOSINGBAL的值为日终余额，也就是当前余额");
	}
	
	
	/**
	 * 返回报文处理逻辑
	 * @param retXml
	 */
	public void dealRet(String retXml){
		String trxcode = null;
		AipgRsp aipgrsp=null;
		//或者交易码
		if (retXml.indexOf("<TRX_CODE>") != -1)
		{
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0) trxcode = retXml.substring(begin, end);
		}
		aipgrsp=XSUtil.parseRsp(retXml);
		
		//批量代收付返回处理逻辑
		if("100001".equals(trxcode)||"100002".equals(trxcode)||"211000".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("受理成功，请在20分钟后进行10/每次的轮询");
			}else{
				System.out.println("受理失败，失败原因："+aipgrsp.getINFO().getERR_MSG());
			}
			Analysis(retXml);
		}
		//交易查询处理逻辑
		if("200004".equals(trxcode)||"200005".equals(trxcode)){
			Map<String,Object> map=new HashMap<String,Object>();
			Map<String,Object> m=new HashMap<String,Object>();
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				QTransRsp qrsq=(QTransRsp) aipgrsp.getTrxData().get(0);
				System.out.println("查询成功，具体结果明细如下:");
				List<QTDetail> details=qrsq.getDetails();
				for(QTDetail lobj:details){
					System.out.print("原支付交易批次号:"+lobj.getBATCHID()+"  ");
					System.out.print("记录序号:"+lobj.getSN()+"  ");
					System.out.print("账号:"+lobj.getACCOUNT_NO()+"  ");
					System.out.print("户名:"+lobj.getACCOUNT_NAME()+"  ");
					System.out.print("金额:"+lobj.getAMOUNT()+"  ");
					System.out.print("返回结果:"+lobj.getRET_CODE()+"  ");
					
					if("0000".equals(lobj.getRET_CODE())){
						System.out.println("返回说明:交易成功  ");
						System.out.println("更新交易库状态（原交易的状态）");
					}else{
						System.out.println("返回说明:"+lobj.getERR_MSG()+"  ");
						System.out.println("更新交易库状态（原交易的状态）");
					}
					map=new HashMap<String,Object>();
					m=new HashMap<String,Object>();
					map.put("SN", lobj.getSN());
					map.put("USE_STATE", "1");
					map.put("REQ_SN_MAIN", lobj.getBATCHID());
					map.put("RETURN_STATUS", lobj.getRET_CODE());
					map.put("RETURN_ERR_MSG", lobj.getERR_MSG());
					m=Dao.selectOne("rentWriteNew.getTranxMiddleStatus",map);
					if(!"2".equals(m.get("USE_STATE").toString())){
						Dao.update("rentWriteNew.updTranxMiddleReturn",map);
					}
				}
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.print("返回说明:"+aipgrsp.getINFO().getRET_CODE()+"  ");
				System.out.println("返回说明："+aipgrsp.getINFO().getERR_MSG());
				System.out.println("该状态时，说明整个批次的交易都在处理中");
				map=new HashMap<String,Object>();
				map.put("USE_STATE", "0");
				map.put("BUSINESS_SN_MAIN", aipgrsp.getINFO().getREQ_SN());
				map.put("RETURN_STATUS", aipgrsp.getINFO().getRET_CODE());
				map.put("RETURN_ERR_MSG", aipgrsp.getINFO().getERR_MSG());
				Dao.update("rentWriteNew.updTranxMiddleReturn",map);
			}else if("2004".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("整批交易未受理通过（最终失败）");
				map=new HashMap<String,Object>();
				map.put("USE_STATE", "1");
				map.put("BUSINESS_SN_MAIN", aipgrsp.getINFO().getREQ_SN());
				map.put("RETURN_STATUS", aipgrsp.getINFO().getRET_CODE());
				map.put("RETURN_ERR_MSG", aipgrsp.getINFO().getERR_MSG());
				m=Dao.selectOne("rentWriteNew.getTranxMiddleStatus",map);
				if(!"2".equals(m.get("USE_STATE").toString())){
					Dao.update("rentWriteNew.updTranxMiddleReturn",map);
				}
			}else if("1002".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("查询无结果集");
				map=new HashMap<String,Object>();
				map.put("USE_STATE", "1");
				map.put("BUSINESS_SN_MAIN", aipgrsp.getINFO().getREQ_SN());
				map.put("RETURN_STATUS", aipgrsp.getINFO().getRET_CODE());
				map.put("RETURN_ERR_MSG", aipgrsp.getINFO().getERR_MSG());
				m=Dao.selectOne("rentWriteNew.getTranxMiddleStatus",map);
				if(!"2".equals(m.get("USE_STATE").toString())){
					Dao.update("rentWriteNew.updTranxMiddleReturn",map);
				}
			}else{
				System.out.println("查询请求失败，请重新发起查询");
				map=new HashMap<String,Object>();
				map.put("USE_STATE", "1");
				map.put("BUSINESS_SN_MAIN", aipgrsp.getINFO().getREQ_SN());
				map.put("RETURN_STATUS", aipgrsp.getINFO().getRET_CODE());
				map.put("RETURN_ERR_MSG", "查询请求失败，请重新发起查询");
				m=Dao.selectOne("rentWriteNew.getTranxMiddleStatus",map);
				if(!"2".equals(m.get("USE_STATE").toString())){
					Dao.update("rentWriteNew.updTranxMiddleReturn",map);
				}
			}
		}
		//实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
		if("100011".equals(trxcode)||"100014".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("提交成功");
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
				if("0000".equals(ret.getRET_CODE())){
					System.out.println("交易成功（最终结果）");
				}else{
					System.out.println("交易失败（最终结果）");
					System.out.println("交易失败原因："+ret.getERR_MSG());
				}
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
				System.out.println("交易请求失败，原因："+errormsg);
			}else{
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易失败(最终结果)，失败原因："+ret.getERR_MSG());
			}
		}
		//(单笔实时身份验证结果返回处理逻辑)
		if("211003".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("提交成功");
				ValidRet ret=(ValidRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("验证处理中或者不确定状态，需要在稍后5分钟后进行验证结果查询（轮询）");
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
				System.out.println("验证请求失败，原因："+errormsg);
			}else{
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("验证失败(最终结果)，失败原因："+ret.getERR_MSG());
			}
		}
	}
	
	
	/**
	 * 日期：Sep 4, 2012
	 * 功能：单笔实时身份验证
	 * @throws Exception 
	 */
	public void singleAcctVerify(String url,boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("211003");
		aipgReq.setINFO(info);
		
		ValidR valid=new ValidR();
		valid.setACCOUNT_NAME("zhanggh");
		valid.setACCOUNT_NO("6228485880571233334");
//		valid.setACCOUNT_PROP("1");
//		valid.setACCOUNT_TYPE("01");
		valid.setBANK_CODE("0105");
		valid.setID("318562198408151666");
		valid.setID_TYPE("0");
		valid.setMERCHANT_ID(tranxContants.merchantId);
		valid.setTEL(tranxContants.tel);
		valid.setSUBMIT_TIME(df.format(new Date()));
		valid.setREMARK("单笔实时身份验证-备注字段");
		aipgReq.addTrx(valid);
		
		xml=XmlTools.buildXml(aipgReq,true);//
		String resp = sendToTlt(xml,isTLTFront,url);
		dealRet(resp);
	}
	
	/**
	 * 日期：Sep 4, 2012
	 * 功能：批量账户验证 211000
	 * @throws Exception 
	 */
	public void batchAcctVerify(String url,boolean isTLTFront) throws Exception {
		String xml="";
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("211000");
		aipgReq.setINFO(info);
		
		ValidBReq vbreq=new ValidBReq();
		ValbSum VALBSUM =new ValbSum();
		VALBSUM.setMERCHANT_ID(tranxContants.merchantId);
		VALBSUM.setSUBMIT_TIME(df.format(new Date()));
		VALBSUM.setTOTAL_ITEM("12");
//		VALBSUM.setTOTAL_SUM("200000");
		
		ValidBD VALIDBD=new ValidBD();
		VbDetail vbdetail=null;
		for(int i=0;i<12;i++){
			if(i%2!=0)
				tranxContants.bankcode="0104";
			if(i%3!=0)
				tranxContants.bankcode="0105";
			vbdetail=new VbDetail();
			vbdetail.setACCOUNT_NAME(tranxContants.acctName+i);
			vbdetail.setACCOUNT_NO(tranxContants.acctNo+i);
			vbdetail.setACCOUNT_PROP("1");
			vbdetail.setACCOUNT_TYPE("01");
			vbdetail.setBANK_CODE(tranxContants.bankcode);
			vbdetail.setSN("00"+i);
			vbdetail.setTEL(tranxContants.tel);
			vbdetail.setOPTYPE("01");//01—新增；02—解除；03—更改
			vbdetail.setID_TYPE("0");//证件类型：0 身份证
			vbdetail.setID("44201010423543543543");//身份证号
			VALIDBD.addDTL(vbdetail);
		}
		vbreq.setVALBSUM(VALBSUM);
		vbreq.setVALIDBD(VALIDBD);
		aipgReq.addTrx(vbreq);
		
		xml=XmlTools.buildXml(aipgReq,true).replaceAll("<details>\n", "").replaceAll("</details>\n", "");
		String resp = sendToTlt(xml,isTLTFront,url);
		dealRet(resp);
	}
	
	
	public void Analysis(String xml){
		Document doc = null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
        try {
            doc = DocumentHelper.parseText(xml); 
            Element rootElt = doc.getRootElement(); 
            //成功回传
            Map<String,Object> m=new HashMap<String,Object>();
            // 遍历head节点
                Iterator INFO = rootElt.elementIterator("INFO"); 
                while (INFO.hasNext()) {
                	Element INFO1 = (Element) INFO.next();
                	String TRX_CODE = INFO1.elementTextTrim("TRX_CODE"); 
                	if(TRX_CODE !=null && !"".equals(TRX_CODE))
                    {
                    	m.put("TRX_CODE", TRX_CODE);
                    }
                	String REQ_SN = INFO1.elementTextTrim("REQ_SN"); 
                	if(REQ_SN !=null && !"".equals(REQ_SN))
                    {
                    	m.put("REQ_SN_MAIN", REQ_SN);
                    }
                	String RET_CODE = INFO1.elementTextTrim("RET_CODE"); 
                	if(RET_CODE !=null && !"".equals(RET_CODE))
                    {
                    	m.put("RET_CODE", RET_CODE);
                    }
                	String ERR_MSG = INFO1.elementTextTrim("ERR_MSG"); 
                	if(ERR_MSG !=null && !"".equals(ERR_MSG))
                    {
                    	m.put("ERR_MSG", ERR_MSG);
                    }
                }
                Iterator BODY = rootElt.elementIterator("BODY"); 
                while (BODY.hasNext()) {
                    Element itemEle = (Element) BODY.next();
                    Iterator RET_DETAILS = itemEle.elementIterator("RET_DETAILS"); 
                    while (RET_DETAILS.hasNext()) {
                        Element itemItem = (Element) RET_DETAILS.next();
                        Iterator RET_DETAIL = itemItem.elementIterator("RET_DETAIL"); 
                        while (RET_DETAIL.hasNext()) {
                        	Element RET_DETAIL1 = (Element) RET_DETAIL.next();
                        	String SN = RET_DETAIL1.elementTextTrim("SN"); 
                        	if(SN !=null && !"".equals(SN))
                            {
                            	m.put("SN", SN);
                            }
                        	String RET_CODE = RET_DETAIL1.elementTextTrim("RET_CODE"); 
                        	if(RET_CODE !=null && !"".equals(RET_CODE))
                            {
                            	m.put("RET_CODE", RET_CODE);
                            }
                        	String ERR_MSG = RET_DETAIL1.elementTextTrim("ERR_MSG"); 
                        	if(ERR_MSG !=null && !"".equals(ERR_MSG))
                            {
                            	m.put("ERR_MSG", ERR_MSG);
                            }
                        	Dao.update("rentWriteNew.updTranxMiddle",m);
                        }
                    }
                }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
