package com.pqsoft.quartzjobs.jobs;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;

import net.sf.json.JSONArray;

/**
 * 本地黑名单数据发送
 * @author 李硕
 * @since  2016年2月19日11:22:11
 */
public class SendBlackCustJob extends AbstractJob {
	private  final Logger logger= Logger.getLogger(super.getClass());
	@Override
	protected void exec(JobExecutionContext context){
		logger.info("发送黑名单信息定时任务========SendBlackCustJob开始执行");
		// 创建HttpClient实例     	
        HttpClient httpClient = new DefaultHttpClient();  
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("INTERFACE_NAME", "/JIEYI_LOP-PROJECT3.0/src/main/java/com/pqsoft/quartzjobs/jobs/SendBlackCustJob.java");
        map.put("INVOKE_TIME", formatDate(new Date()));
        HttpPost httpPost=null;
        
        // 创建Post方法实例     
        List<Map<String,Object>> res =Dao.execSelectSql("SELECT REMARK FROM T_SYS_DATA_DICTIONARY WHERE STATUS=0 AND FLAG='新增修改接口' AND TYPE='捷越黑名单接口'");
        try{
            if(res!=null && res.size()>0){
              httpPost = new HttpPost(res.get(0).get("REMARK").toString());   
              httpPost.addHeader("Authorization", new sun.misc.BASE64Encoder().encode("ES51:jiezhongzuche".getBytes()));
              httpPost.addHeader("Content-Type", "application/json");
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("STATUS", e.toString());
            map.put("END_TIME", formatDate(new Date()));
            Dao.insert("blackcust.insertT_SysInterface_Log",map);//系统调用接口日志
            Dao.commit();
            Dao.close();
            logger.info("接收黑名单信息定时任务========AcceptBlackCust任务失败");
        }
		
		//查询本地黑名单数据
		List<Map<String,Object>>  list  = Dao.selectList("blackcust.querySendBlackCustInfo");//新增黑名单数据
		List<Map<String,Object>>  list2 = Dao.selectList("blackcust.querySendRemoveBlackCustInfo");//移出黑名单数据
		list.addAll(list2);
		
		//循环放传输数据
		for(int i=0;i<list.size();i++){
			list.get(i).put("opType", "2");//转入：1；转出：2；
			list.get(i).put("dataValue", list.get(i).get("ID_CARD_NO"));//身份证
			list.get(i).put("dataType", "3");//传输数据类型 1:客户姓名、2:企业名称、3:证件号码、4:电话、5:地址、6:电子邮件、7:员工、8:权证	
			list.get(i).put("validateState", "1");
			list.get(i).put("createTime", list.get(i).get("CREATETIME"));
			list.get(i).put("instruction", list.get(i).get("CASE_RECORDS"));
			list.get(i).put("initSystem", "ES51");
			list.get(i).remove("CUST_CLIENT_ID");
			list.get(i).remove("ID_CARD_NO");
			list.get(i).remove("STATUS");
			list.get(i).remove("CREATE_TIME");
			list.get(i).remove("CASE_RECORDS");
		   }
    		if(list!=null &&list.size() > 0){
    		    JSONArray array = JSONArray.fromObject(list);
    		    StringBuffer sb =new StringBuffer("{"+'"'+"blacks"+'"'+":");
    		    sb.append(array.toString()).append("}");
    		    logger.info("发送黑名单信息定时任务的信息为========"+sb);
    		    StringEntity entity;
        		    try {
        		        entity = new StringEntity(sb.toString(),"utf-8");//解决中文乱码问题    
        		        entity.setContentEncoding("UTF-8");    
        		        entity.setContentType("application/json");    
        		        httpPost.setEntity(entity);  
        		        //获取返回状态，若返回状态为正常，则修改本地黑名单数据的发送状态
        		        HttpResponse result = httpClient.execute(httpPost);  
        	            HttpEntity httpEntity = result.getEntity();  
        	            String resData = EntityUtils.toString(httpEntity , "UTF-8").trim();  
        		        String[] arry =resData.split(",");
        		        if(arry[2].indexOf("10000") > -1){//对方成功状态码：10000
        		            map.put("STATUS", "0");//0 success
        		            Dao.update("blackcust.updateLocalBlackCustInfo");//转出成功 
        		        }else{
        		            map.put("STATUS", "1(返回状态未成功)");//1 failure
        		            Dao.update("blackcust.updateLocalBlackCustInfo2");//转出失败
        		        }
        		        map.put("SEND_VALUE",sb.toString());
        		        map.put("RESPONSE_VALUE",resData);
        		        map.put("END_TIME", formatDate(new Date()));
        		        Dao.insert("blackcust.insertT_SysInterface_Log",map);//系统调用接口日志
        		        Dao.commit();
        		        Dao.close();
        		    }catch (Exception e) {
        		        Dao.rollback();
        		        e.printStackTrace();
        		        map.put("STATUS", e.toString());
                        map.put("END_TIME", formatDate(new Date()));
        	            Dao.insert("blackcust.insertT_SysInterface_Log",map);//系统调用接口日志
        	            Dao.commit();
        	            Dao.close();
        		        logger.info("发送黑名单信息定时任务========SendBlackCustJob执行失败");
        		    }
    		    }
    		logger.info("发送黑名单信息定时任务========SendBlackCustJob执行结束");
		}
	}
