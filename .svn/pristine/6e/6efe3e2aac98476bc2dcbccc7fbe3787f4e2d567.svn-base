package com.pqsoft.quartzjobs.jobs;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * 接受捷越黑名单数据插入本地库
 * @author 李硕
 * @since  2016年2月22日15:28:40
 */
public class AcceptBlackCust extends AbstractJob {
	private  final Logger logger= Logger.getLogger(super.getClass());
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		logger.info("接收黑名单信息定时任务========AcceptBlackCust开始执行");
		// 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        Map<String,Object> m =new HashMap<String,Object>();
        m.put("INTERFACE_NAME", "/JIEYI_LOP-PROJECT3.0/src/main/java/com/pqsoft/quartzjobs/jobs/AcceptBlackCust.java");
        m.put("INVOKE_TIME", formatDate(new Date()));
        HttpGet httpGet=null;
        
        List<Map<String,Object>> res =Dao.execSelectSql("SELECT REMARK FROM T_SYS_DATA_DICTIONARY WHERE STATUS=0 AND FLAG='查询接口' AND TYPE='捷越黑名单接口'");
        try{
            if(res!=null && res.size()>0){
                httpGet = new HttpGet(res.get(0).get("REMARK").toString());    
                httpGet.addHeader("Authorization", new sun.misc.BASE64Encoder().encode("ES51:jiezhongzuche".getBytes()));
                httpGet.addHeader("Content-Type", "application/json"); 
           }
        }catch(Exception e){
            m.put("STATUS", e.toString());
            m.put("END_TIME", formatDate(new Date()));
            Dao.insert("blackcust.insertT_SysInterface_Log",m);//系统调用接口日志
            Dao.commit();
            Dao.close();
            logger.info("接收黑名单信息定时任务========AcceptBlackCust任务失败");  
        }
        InputStream instreams = null;
        JSONObject json =null;
        HttpResponse response;
        Map<String,Object> map =new HashMap<String,Object>();
		try {
			response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();    
            if (entity != null) {    
                    instreams = entity.getContent();
            }   
			if(instreams!=null){//传回有数据
			    String str = convertStreamToString(instreams);
				try{
				    json=JSONObject.fromObject(str);
				    JSONArray array =json.getJSONArray("responseBody");
				    //获取返回的字段去插入中间表
				    if(array.size()  > 0){
				        for(int i=0;i<array.size();i++){
				            JSONObject myjObject = array.getJSONObject(i);
				            if(myjObject.get("dataType") !=null && myjObject.get("dataValue") !=null){
				                map.put("DATATYPE", myjObject.get("dataType")== null ? "" :myjObject.get("dataType"));
				                map.put("DATAVALUE", myjObject.get("dataValue")== null ? "" :myjObject.get("dataValue"));
				                map.put("OPTYPE", myjObject.get("opType")== "2" ? "1" :myjObject.get("opType"));
				                Object CREATETIME = myjObject.get("createTime");
				                if(CREATETIME instanceof JSONNull){
                                    map.put("CREATETIME", formatDate(new Date()));
                                }else{
                                    map.put("CREATETIME", formatDate(myjObject.get("createTime")));
                                }
				                Object o = myjObject.get("remark");
				                if(o instanceof JSONNull){
				                    map.put("INSTRUCTION", "传入的数据无原因");
				                }else{
				                    map.put("INSTRUCTION", myjObject.get("remark"));
				                }
				                map.put("VALIDATESTATE", myjObject.get("validateState")== null ? "" :myjObject.get("validateState"));
				                //先去查询是否为重复数据，若不重复则进行insert
				                List<Map<String,Object>> list=Dao.selectList("blackcust.checkBlackMessage",map);
				                if(list.size() > 0){
				                    //for(int n=0; n<list.size(); i++){//数据存在，则进行更新操作
				                    map.put("ID",list.get(0).get("ID"));
				                    Dao.update("blackcust.updateBlackCust_middle_jieyue",map);
				                    //}
				                }else{ 
				                    map.put("ID", Dao.getSequence("SEQ_T_BLACK_CUST_MIDDLE_JIEYUE"));
				                    Dao.insert("blackcust.insertBlackCust_middle_jieyue",map);
				                }
				            }
				        } 
                        m.put("RESPONSE_VALUE",json.toString());
                        m.put("STATUS", "0");
                        m.put("END_TIME", formatDate(new Date()));
                        Dao.insert("blackcust.insertT_SysInterface_Log",m);//系统调用接口日志
				        Dao.commit();
				        Dao.close();
				    }
				}catch(Exception e){
					 Dao.rollback();
					 e.printStackTrace();
					 m.put("STATUS", e.toString());
					 m.put("RESPONSE_VALUE",json.toString());
                     m.put("END_TIME", formatDate(new Date()));
                     Dao.insert("blackcust.insertT_SysInterface_Log",m);//系统调用接口日志
                     Dao.commit();
                     Dao.close();
					logger.info("接收黑名单信息定时任务========AcceptBlackCust任务失败");
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
            m.put("STATUS", e.toString());
            m.put("RESPONSE_VALUE",json.toString());
            m.put("END_TIME", formatDate(new Date()));
            Dao.insert("blackcust.insertT_SysInterface_Log",m);//系统调用接口日志
            Dao.commit();
            Dao.close();
			logger.info("接收黑名单信息定时任务========AcceptBlackCust任务失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
          logger.info("接收黑名单信息定时任务========AcceptBlackCust执行完毕");
        }  
	
	public static String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while((line = reader.readLine()) != null) { 
                sb.append(new String(line.getBytes(),"utf-8") + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    }  
	
}