package com.pqsoft.quartzjobs.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.serviceFee.service.ServiceFeeService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.FIinterface;
/**
 * 中金接口   代付服务费
 * @author wanghl
 * @datetime 2015年8月21日,下午4:22:17
 */
public class AutoServiceFee extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context) throws JobExecutionException {
		
				//异步调用
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							ServiceFeeService service = new ServiceFeeService();
							List<Map<String, Object>> feeList = service.serviceFeeToInterface();
							Map<String, Object> titleMap = service.serviceFeeToInterfaceCount();
							FIinterface fi = new FIinterface();
							fi.daifu(feeList, titleMap, "5"); //5代付服务费
							Dao.commit();
							
							//查询已反馈的服务费数据
							List<Map<String,Object>> selectList = Dao.selectList("serviceFee.queryZhongjin");
							Map<String,Object> feeMap = new HashMap<String, Object>();
							List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
							for(int i=0;i<selectList.size();i++){
								feeMap = selectList.get(i);
								batchList = Dao.selectList("serviceFee.queryZhongjinItem",feeMap);
								for(int j=0;j<batchList.size();j++){
									Map<String, Object> map = batchList.get(j);
									service.serviceFeeFrInterFPassOrNo(map);
									//付款成功的更新中金接口中间表
									if("30".equals(map.get("RETURN_STATUS").toString())){
										map.put("USE_STATE", "2");
										Dao.update("serviceFee.updateZhongjinState",map);
									}
								}
							}
							Dao.commit();
						} catch (Exception e) {
							Dao.rollback();
							e.printStackTrace();
						} finally {
							Dao.close();
						}
					}
				}).start();
		
	}

}
