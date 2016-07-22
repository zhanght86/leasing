/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：交易demo测试参数
 */
package com.allinpay.xmltrans.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;

/**
 */
public class TranxCon {

	public TranxCon() {
		List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("通联财务接口");
		Map<String, Object> m=new HashMap<String, Object>();
		Map<String, Object> map=new HashMap<String, Object>();
		for(int i=0;i<cwjk.size();i++){
			m=cwjk.get(i);
			if("MERCHANTID".equals(m.get("FLAG").toString())){
				merchantId=m.get("CODE").toString();
			}else if("PFXPATH".equals(m.get("FLAG").toString())){
				pfxPath=m.get("CODE").toString();
			}else if("TLTCERPATH".equals(m.get("FLAG").toString())){
				tltcerPath=m.get("CODE").toString();
			}else if("PASSWORD".equals(m.get("FLAG").toString())){
				password=m.get("CODE").toString();
			}else if("USERNAME".equals(m.get("FLAG").toString())){
				userName=m.get("CODE").toString();
			}
		}
	}
	
	/**
	 * XML交易参数
	 */
	
	public   String acctName = "张国光";
	public   String acctNo = "6225882516636351";
	public   String amount = "100000";//交易金额
	public   String bankcode = "0105";//银行代码
	public   String cerPath= "config\\20060400000044502.cer";
	public   String merchantId = "";//商户号
	public   String password = "";
//	public   String merchantId = "200604000000445";//商户号
//	public   String password = "111111";
	//商户证书信息
	public   String pfxPassword = "111111";
//	public   String pfxPath= "E:\\YAXIA\\LOPSKYEYE-BASE3.0\\src\\main\\resources\\config\\20010000001445404.p12";
	public   String pfxPath= "";
	public   String sum = "200000";//交易总金额
	public   String tel = "13434245847";
//	public   String tltcerPath= "E:\\YAXIA\\LOPSKYEYE-BASE3.0\\src\\main\\resources\\config\\allinpay-pds.cer";
	public   String tltcerPath= "";
	public   String userName = ""; 
//	public   String userName = "20060400000044502"; 
	public String getAcctName() {
		return acctName;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public String getAmount() {
		return amount;
	}
	public String getBankcode() {
		return bankcode;
	}
	public String getCerPath() {
		return cerPath;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getPassword() {
		return password;
	}
	public String getPfxPassword() {
		return pfxPassword;
	}
	public String getPfxPath() {
		return pfxPath;
	}
	public String getSum() {
		return sum;
	}
	public String getTel() {
		return tel;
	}
	public String getTltcerPath() {
		return tltcerPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPfxPassword(String pfxPassword) {
		this.pfxPassword = pfxPassword;
	}
	public void setPfxPath(String pfxPath) {
		this.pfxPath = pfxPath;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setTltcerPath(String tltcerPath) {
		this.tltcerPath = tltcerPath;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	
	
}
