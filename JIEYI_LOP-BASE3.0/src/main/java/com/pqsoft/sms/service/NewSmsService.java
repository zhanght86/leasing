package com.pqsoft.sms.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;


public class NewSmsService {
	
	String xmlPath = "newSms.";//xml路径
	private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat("HHmmss");
	/**
	 * 获取所有的节假日job1
	 * @return
	 */
	public int getFestivalInfo(){
		int i=0;
		List<?> listAllFestivalInfo=Dao.selectList(xmlPath+"getAllFestivalInfo");
		for(int all=0;all<listAllFestivalInfo.size();all++){
            Map<?, ?> mapAllFestivalInfo = (Map<?, ?>) listAllFestivalInfo.get(all);
            String strName=mapAllFestivalInfo.get("NAME").toString();
			 if("节日".equals(strName)|| "春节".equals(strName) ||"元旦".equals(strName)){
				 String strFestival=Dao.selectOne(xmlPath+"getFestivalInfo");
					//String strFestival="节日";//TODO:此处为模拟数据，需要改为上边的数据
					if(strFestival!=null && ! strFestival.isEmpty()){
						Map<String, String> mapFestival=new HashMap<String, String>();
						if("元旦".equals(strFestival)){
							mapFestival.put("name", "元旦");
						}
						else if("春节".equals(strFestival)){
							mapFestival.put("name", "春节");
						}
						else if("节日".equals(strFestival)){
							mapFestival.put("name", "节日");
						}
						i=Dao.insert(xmlPath+"insertAllFestival",mapFestival);
					}
			 }else if("生日祝福".equals(strName)){
				//生日祝福——向接口表中插入逾期提醒的模板
				List<?> listBirthday=Dao.selectList(xmlPath+"queryHasBirthday");//查询今天过生日的人
				if(listBirthday.size()>=0){
					Map<String, String> mapBirthday=new HashMap<String, String>();
					mapBirthday.put("operator", "admin");
					//插入拼接完成，需要写入接口表的数据
					i=Dao.insert(xmlPath+"insertBirthday",mapBirthday);
				}
			}else if("逾期提醒".equals(strName)){
				//逾期提醒——向接口表中插入逾期提醒的模板
				List<?> listOverdue=Dao.selectList(xmlPath+"queryHasOverdue");//查询今天逾期的人
				if(listOverdue.size()>=0){
					Map<String, String> mapOverdueTips=new HashMap<String, String>();
					mapOverdueTips.put("name", "逾期提醒");
					mapOverdueTips.put("operator", "admin");
					i=Dao.insert(xmlPath+"insertOverdueTips",mapOverdueTips);
				}
			}else if("还款提醒".equals(strName)){
				//还款提醒——向接口表中插入还款提醒的模板
				List<?> listReturnMoney=Dao.selectList(xmlPath+"queryHasReturnMoney");//查询还款提醒的人
				if(listReturnMoney.size()>=0){
					Map<String, String> mapReturnMoneyTips=new HashMap<String, String>();
					mapReturnMoneyTips.put("name", "还款提醒");
					mapReturnMoneyTips.put("operator", "admin");
					i=Dao.insert(xmlPath+"insertReturnMoneyTips",mapReturnMoneyTips);
				}
			}
		}
		return i;	
	}
	
	/**
	 *  查询所有需要发送的信息 job2
	 *  以下代码所使用的通道1，使用调用的是webservice接口
	 * @return
	 */
	public Boolean getAllSMSToSend() {
		
		Logger log= Logger.getLogger(NewSmsService.class);
		List<Map<String,Object>> list=Dao.selectList(xmlPath+"getAllSMSToSend");
		JaxWsDynamicClientFactory  factory=JaxWsDynamicClientFactory.newInstance();
		Client client =factory.createClient("http://121.41.63.15/websend/mainservice.asmx?wsdl");
		Object[] result = null;
		String sName="JZZC"; //企业账号JZZC
		String sPsd="JZZC001"; //企业密码JZZC001
		String sdst="";//手机号码（联通移动电信）、小灵通必须分开单独为一组进行提交，号码之间必须用英文逗号分割,最后一个手机号后不加逗号, 必填,一个包应少于300个号码。
		String smsg="";//短信内容最多1000字，“字”解释：1个数字或字母或符号为1字，1个汉字为1字，以字为计，非字节。我们平台会根据通道不同系统自动拆分多条或长短信。
		String stime=""; //默认为空，为立即发送。定时发送格式: YYYYMMDDHHMM；15位时间表示，不符合规则的将立即进行发送。
		String sexno="";//纯数字的字符串，长度<10,完整的特服号码长度<20，在短信分配的特服代码后附加的数字。
		String islong="";//直接填空，目前系统自动识别
		String sequnceId="";//主要用于：手机用户回复短信后，需要与下发的信息一一匹配长度：最大64位。
		try {
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						if(list.get(i).get("TEMPLATE_NAME").equals("节日")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";").toString();
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}else if(list.get(i).get("TEMPLATE_NAME").equals("生日祝福")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";").toString();
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}else if(list.get(i).get("TEMPLATE_NAME").equals("春节")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";").toString();
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}else if(list.get(i).get("TEMPLATE_NAME").equals("元旦")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";");
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}else if(list.get(i).get("TEMPLATE_NAME").equals("逾期提醒")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";");
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}else if(list.get(i).get("TEMPLATE_NAME").equals("还款提醒")){
							 sdst=list.get(i).get("TEL_PHONE").toString().replace(",", ";");
							 smsg=list.get(i).get("TEMPLATE_CONTENT").toString();
						}
						//返回结果result数据：[num=1&success=18230168878,&faile=&err=发送成功&errid=0]
						result = client.invoke("Masssend1", sName,sPsd,sdst,smsg,stime,sexno,islong,sequnceId);
						log.info(result);
					}
				}else{
					log.info("没有需要发送的短息");
				}
			} catch (Exception e) {
				log.error(" webservice authenticate faild ", e);
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	/**
	 *  查询所有需要发送的信息 job3
	 *  以下代码所使用的通道2，使用调用的是Http接口
	 * @return
	 */
	public Boolean getAllSMSToSendByHttp() {
		String url = "http://api.china95059.net:8080/sms/send";// 应用地址
		Map<String, String> map =null;
		int intHistory;
		Map<String, String> resultMap=new HashMap<String, String>();
		Boolean flag=true;
		try {
			List<Map<String,Object>> list=Dao.selectList(xmlPath+"getAllSMSToSendForHttp");
			for(int i=0;i<list.size();i++){
				map = new HashMap<String, String>();
				String statusStr="";
				map.put("name", "hshc");
				map.put("pswd", "hshc@2016~");
				map.put("mobile", list.get(i).get("TEL_PHONE").toString());
				map.put("msg", list.get(i).get("TEMPLATE_CONTENT").toString());//用自己的内容白名单做测试
				map.put("needstatus", String.valueOf(true));
				map.put("sender", null);
				map.put("type", "json");
				//发送短信
				String returnString = send(url, map);
				
				//解析回执结果状态
				String[] arr=returnString.split(",");
				if(!arr[1].isEmpty() && !"".equals(arr[1])){
					String[] ar=arr[1].split(":");
					if(ar.length!=0){
						statusStr=ar[1].toString();
					}
				}
				//处理回执信息结果
				resultMap.put("return_log", returnString);
				resultMap.put("status",statusStr );
				int in=Dao.insert(xmlPath+"insertInterfaceLog",resultMap);
				if(in<=0){
					Log.error("短信平台：回执信息写入SMS_NEWINTERFACELOG失败");
				}
				else{
					Log.info("短信平台：回执信息写入SMS_NEWINTERFACELOG成功");
				}
				
				//成功，更改接口表中的发送状态为0；不成功，更改接口表中的发送状态为1
				int up=0;
				if("0".equals(statusStr)){
					Log.info("短信平台：短信发送成功");
					Map<String, String> mapWhere1=new HashMap<String, String>();
					mapWhere1.put("id", list.get(i).get("ID").toString());
					mapWhere1.put("status","0");
					up=Dao.update(xmlPath+"updateInterfaceStatus",mapWhere1);
					if(up<=0){
						flag=false;
						Log.error("短信平台：接口表中的发送状态为0失败");
					}
					else{
						Log.info("短信平台：接口表中的发送状态为0成功");
					}
				}else{
					Map<String, String> mapWhere2=new HashMap<String, String>();
					mapWhere2.put("id", list.get(i).get("ID").toString());
					mapWhere2.put("status","1");
					up=Dao.update(xmlPath+"updateInterfaceStatus",mapWhere2);
					 if(up<=0){
							flag=false;
							Log.error("短信平台：接口表中的发送状态为1失败");
						}
						else{
							Log.info("短信平台：接口表中的发送状态为1成功");
						}
				}				
			}
			
			//将发送发送过的成功短信或不成功短信插入到历史表中
			intHistory=Dao.insert(xmlPath+"insertNewInterfaceHistory");
			if(intHistory<0){
				Log.error("短信平台：短信插入历史表失败");
			}
			else{
				Log.info("短信平台：短信插入历史表成功");
				int i=Dao.delete(xmlPath+"deleteNewInterface");
				if(i<0){
					Log.error("短信平台：发送过的短信删除失败");
				}else{
					Log.info("短信平台：发送过的短信删除成功");
				}
			}
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	//发送短信
	private  String send(String url, Map<String, String> map) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String name = it.next();
			params.add(new BasicNameValuePair(name, map.get(name)));
		}
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, StandardCharsets.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * 查询所有的模板信息
	 * @return
	 */
	public Page  getAllTemplateDetail(Map<String, Object> param){
		Page page = new Page(param);
		return PageUtil.getPage(param, xmlPath+"getAllTemplateDetail", xmlPath+"getAllTemplateDetailCount");
	}
	

	/**
	 * 添加模板详情
	 * @param map
	 * @return
	 */
	public int addTemplateDetail(Map<String ,Object> map){
		return Dao.insert(xmlPath+"insertTemplateDetail",map);
	}
	
	/**
	 * 修改模板详情
	 * @param map
	 * @return
	 */
	public int updateTemplateDetail(Map<String ,Object> map){
		return Dao.insert(xmlPath+"updateTemplateDetail",map);
	}
}
