package com.pqsoft.quartzjobs.jobs;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;


/**
 * 通过本地备份捷越数据更新本地黑名单
 * @author 李硕 
 * @since  2016年2月22日15:28:28
 */
public class UpdateLocalBlackCust extends AbstractJob {
	private  final Logger logger= Logger.getLogger(super.getClass());
	
	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		logger.info("更新本地黑名单信息定时任务========UpdateLocalBlackCust开始执行");
		
         //查询捷越给的数据
         List<Map<String,Object>> list =Dao.selectList("blackcust.findBlackCustFromJIEYUE");
         if(list!=null  && list.size() > 0){
             while(list.size() > 0){
                 handel(list);
                 list.clear();
                 list=Dao.selectList("blackcust.findBlackCustFromJIEYUE");
             }
         }
         logger.info("更新本地黑名单信息定时任务========UpdateLocalBlackCust执行完毕");
       }
	
	//处理数据
	public void handel(List<Map<String,Object>> list){
        try{
            for(int i=0;i<list.size();i++){
                if(list.get(i).get("FLAG") !=null && "0".equals(list.get(i).get("FLAG"))){// FLAG=0表示是未与本地数据空同步
                     if(list.get(i).get("DATATYPE") !=null && "3".equals(list.get(i).get("DATATYPE"))){
                        List<Map<String,Object>> list2 =Dao.selectList("blackcust.checkBlackCustMessage", list.get(i));
                          if(list2.size() > 0){//有数据更新本地数据
                             try{
                                list.get(i).put("SYNC_STATUS", list2.get(0).get("SYNC_STATUS"));
                                list.get(i).put("DATA_SOURCE", list2.get(0).get("DATA_SOURCE"));
                                if(!StringUtils.isNotBlank(list.get(i).get("CREATETIME"))){
                                      list.get(i).put("CREATETIME", formatDate(new Date()));
                                }
                                    Dao.update("blackcust.updateBlackCustFromJIEYUE",list.get(i).get("ID"));//更新FLAG标示
                                    list.get(i).put("ID", list2.get(0).get("ID"));
                                    Dao.update("blackcust.UpdateBlackLocalMessage",list.get(i));
                              }catch(Exception e){
                                    Dao.rollback();
                                    e.printStackTrace();
                                    Dao.update("blackcust.UpdateBlackLocalErrorMessage",list.get(i));//本地数据错误情况FLAG标识符为2
                                    Dao.commit();
                                    Dao.close();
                               }
                            }else{
                                 try{
                                    Dao.insert("blackcust.updateLocalBlackCust",list.get(i));//更新本地黑名单数据（捷越提供的数据类型为身份证的数据）
                                    Dao.update("blackcust.updateBlackCustFromJIEYUE",list.get(i).get("ID"));//更新FLAG标示
                                }catch(Exception e){
                                    e.printStackTrace();
                                    Dao.rollback();
                                    Dao.update("blackcust.UpdateBlackLocalErrorMessage",list.get(i).get("ID"));//本地数据错误情况FLAG标识符为2
                                    Dao.commit();
                                    Dao.close();
                                }
                              }
                           }
                   
                    if(list.get(i).get("DATATYPE") !=null && !"3".equals(list.get(i).get("DATATYPE"))){//先查询是否有此数据
                        List<Map<String,Object>> list3 =Dao.selectList("blackcust.checkBlackRefMessage", list.get(i));
                        if(list3.size() > 0){//有数据更新本地数据
                            try{
                                Dao.update("blackcust.updateBlackCustFromJIEYUE",list.get(i).get("ID"));//更新FLAG标示
                                list.get(i).put("ID", list3.get(0).get("ID"));
                                Dao.update("blackcust.updateBlackCust_Ref",list.get(i));
                            }catch(Exception e){
                                Dao.rollback();
                                e.printStackTrace();
                                Dao.update("blackcust.UpdateBlackLocalErrorMessage",list.get(i));//本地数据错误情况FLAG标识符为2
                                Dao.commit();
                                Dao.close();
                           }
                        }else {//不是身份证类型的数据都插入ref表
                            try{
                                Dao.insert("blackcust.saveBlackCustRef",list.get(i));
                                Dao.update("blackcust.updateBlackCustFromJIEYUE",list.get(i).get("ID"));//更新FLAG标示
                            }catch(Exception e){
                                e.printStackTrace();
                                Dao.rollback();
                                Dao.update("blackcust.UpdateBlackLocalErrorMessage",list.get(i).get("ID"));//本地数据错误情况FLAG标识符为2
                                Dao.commit();
                                Dao.close();
                            }
                        }
                    }
                }
            }
            Dao.commit();
            Dao.close();
        }catch(Exception e){
            Dao.rollback();
            Dao.close();
            e.printStackTrace();   
            logger.info("更新本地黑名单信息定时任务========UpdateLocalBlackCust任务失败");
        }
    }
    
}