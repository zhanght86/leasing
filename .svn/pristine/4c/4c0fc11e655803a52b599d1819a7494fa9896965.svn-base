package com.pqsoft.bpm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.sms.service.SmsService;
import com.pqsoft.sms.service.WeixinService;

public class FundRemindJbpmService {
	String xmlPath = "fundRemind.";//xml路径
	public List<Map<String, Object>> query_Remind_Price(
			Map<String, Object> param) {
		return Dao.selectList("fi.Remind.query_Supp_Remind_Page", param);
	}

	public List<Map<String, Object>> query_overdue_Price(
			Map<String, Object> param) {
		return Dao.selectList("fi.overdue.query_Supp_OverDue_Page", param);
	}
	
	public List<Map<String, Object>> query_fundRemind(
			Map<String, Object> param) {
		return Dao.selectList(xmlPath+"query_Remind_mobile1", param);		
	}

	
	public static void sendEmail(String email, String subject, String body) {

		try {
			String stmp = null;
			String username = null;
			String password = null;
			List<Object> list = new SysDictionaryMemcached().get("系统邮箱");
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<?, ?> m = (Map<?, ?>) list.get(i);
					if (m.get("FLAG").toString().equalsIgnoreCase("stmp")) {
						stmp = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("password")) {
						password = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("username")) {
						username = m.get("CODE").toString();
					}
				}
			}
            Properties props = new Properties();
            props.put("mail.smtp.host", stmp);
            props.put("mail.smtp.port", String.valueOf(25));
            props.put("mail.smtp.auth", "true");
            Transport transport = null;
            Session session = Session.getDefaultInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(stmp, username, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = new InternetAddress(username);
            msg.setFrom(fromAddress);
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject, "UTF-8");    
            msg.setText(body, "UTF-8");
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
	
	
	public void sendJbpmMessage(String NODE_NAME, Map<String,Object> param,String TYPE) {
      try{
    	  List<Map<String, Object>> mlist=query_Remind_Price(param); 
  		  List<Map<String, Object>> slist=query_overdue_Price(param);
  		 String SQL_NAME ="通知";
  		//还款提醒
  		Map<String, Object> p2 = new HashMap<String, Object>();
  		for (Map<String, Object> obj : mlist) {
  		  String newContent=("融资租赁合同号为"+obj.get("LEASE_CODE")+obj.get("NAME")+"的客户，剩余租期"+ 
  	                obj.get("LASE_TERM")+"，应收余额为"+obj.get("BEGINNING_MONEY")+",实际收额"+
  	                obj.get("BEGINNING_PAID")+"，目前需要交的余额"+obj.get("BALANCE")).toString();
  	      newContent.replace("【" +SQL_NAME + "】", newContent);
  		  p2.put("newContent", newContent);
  		}
  		
  		//催款提醒
  		Map<String, Object> p3 = new HashMap<String, Object>();
  	    for (Map<String, Object> obj : slist) {
  		    String newContent1=("客户名称"+obj.get("CUST_NAME")+"，还款计划"+ obj.get("PAYLIST_CODE")+"，应收总金额"+ 
  				   obj.get("PAID_MONEY_ALL")+"，逾期金额为"+ obj.get("RENT_RECE_ALL")+"，罚息金额"+
  				   obj.get("PENALTY_RECE_ALL")).toString();
  			newContent1.replace("【" +SQL_NAME + "】", newContent1);
  			p3.put("newContent1", newContent1);
  		 }
  	    
  	  String Type="";
		for (int j = 0; j < mlist.size(); j++) {
			Map<String, Object> mMap=mlist.get(j);
			if(Type.equals("EMAIL")){
				try {
					String address = mMap.get("邮件地址").toString();
					String title = mMap.get("邮件标题").toString();
					//SystemMail.sendMail(address, title, p2.get("newContent").toString());
					this.sendEmail("624817356@qq.com", "还款通知", p2.get("newContent").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			if(Type.equals("WX")){
//				try {
//					System.out.println("微信企业号");
//					String wxq = mMap.get("微信企业号").toString();
//					WeixinService.sendTextMsg(wxq, p2.get("newContent").toString(), "app5");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			if(Type.equals("SMS")){
//				try {
//					String phone = mMap.get("电话").toString();
//					Map<String, Object> param1 = new HashMap<String, Object>();
//					param1.put("PHONE", phone);
//					param1.put("CONTENT",p2.get("newContent").toString());
//					new SmsService().sendSms(param1);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		
		}	
		
		for (int j = 0; j < slist.size(); j++) {
			Map<String, Object> sMap=slist.get(j);
		}  
    	  
      }catch (Exception e) {
			e.printStackTrace();
	  }
		
	}
	

}
