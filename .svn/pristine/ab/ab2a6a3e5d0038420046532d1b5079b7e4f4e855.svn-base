package com.pqsoft.rentDraw.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 租金划扣管理
 * @author zhengshangcheng
 *
 */
public class RentDrawService {

	//数据查询分页
	public Page getRentDrawPage(Map<String, Object> param){
		return PageUtil.getPage(param, "rentDraw.getRentDrawPage", "getRentDrawCount");
	}
	
	
	public Map<String, Object> getRentDrawDetail(Map<String, Object> param){
		return Dao.selectOne("rentDraw.getRentDrawDetail", param);
	}
	
	//租金划扣管理跑批插入数据
	public void refreshRentDraw(){
		Map<String, Object> param = new HashMap<String, Object>();
		
//		List<Map<String, Object>> list = new DataDictionaryMemcached().get("租金划扣时间");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now_date = df.format(new Date());
		
		String Batch = "";//批次号
		double TOTALPRICE = 0.00;//代扣总金额
		int SUCCCOUNT = 0;//成功数量
		int FAILCOUNT = 0;//失败数量
		
		List<Map<String, Object>> listRent = Dao.selectList("rentDraw.getRentDraw");
		String batch_old = "";
		if(listRent.size()>0){
			batch_old = listRent.get(0).get("BATCH").toString();
			Batch = setBatchDate(Batch, batch_old);
		}else{
			Batch = now_date.replace("-", "");
		}
		param.put("BATCH", Batch);//批次
		
		param = setStartEndDateForBatch(param, batch_old, Batch);
		
		List<Map<String, Object>> listRentDetail = Dao.selectList("rentDraw.getRentDetail", param);
		if(listRentDetail.size() == 0){
			param.put("PERSONTOTAL", "0");//代扣客户总数
		}else{
			param.put("PERSONTOTAL", listRentDetail.size());//代扣客户总数
		}
		for(int k=0;k<listRentDetail.size();k++){
			TOTALPRICE += Double.parseDouble(listRentDetail.get(k).get("ZJ").toString());//代扣总金额
		}

		param.put("STATUS", "1");
		List<Map<String, Object>> listRentDetail1 = Dao.selectList("rentDraw.getRentDetail", param);
		//扣款中数量
		if(listRentDetail1.size() == 0){
			param.put("DRAWINGCOUNT", "0");
		}else{
			param.put("DRAWINGCOUNT", listRentDetail1.size());
		}
		
		param.put("STATUS", "0");
		List<Map<String, Object>> listRentDetail0 = Dao.selectList("rentDraw.getRentDetail", param);
		
		for(int i=0;i<listRentDetail0.size();i++){
			if(listRentDetail0.get(i).get("ZJ").toString().equals(listRentDetail0.get(i).get("YSZJ").toString())){
				SUCCCOUNT = SUCCCOUNT+1;
			}
			FAILCOUNT = FAILCOUNT + Integer.parseInt(listRentDetail0.get(i).get("BEGINNING_FALSE_NUM").toString());	
		}
		
		//代扣总金额
		if(TOTALPRICE == 0 || TOTALPRICE == 0.00){
			param.put("TOTALPRICE", "0.00");
		}else {
			param.put("TOTALPRICE", new java.text.DecimalFormat("#.00").format(TOTALPRICE));
		}
		
		//成功数量
		if(SUCCCOUNT == 0){
			param.put("SUCCCOUNT", "0");
		}else{
			param.put("SUCCCOUNT", SUCCCOUNT);
		}
		
		//失败数量
		if(FAILCOUNT == 0){
			param.put("FAILCOUNT", "0");
		}else{
			param.put("FAILCOUNT", FAILCOUNT);
		}
		
		//未扣数量
		if(listRentDetail0.size()-SUCCCOUNT == 0){
			param.put("NORENTCOUNT", "0");
		}else{
			param.put("NORENTCOUNT", listRentDetail0.size()-SUCCCOUNT);
		}
		Dao.insert("rentDraw.insertRentDraw", param);
	}

	/**
	 * 设置开始和结束时间(根据批次号)
	 * @param Batch		结束批次(时间)
	 * @param batch_old	开始批次(时间)
	 * @return
	 */
	private String setBatchDate(String Batch, String batch_old) {
		if(batch_old.substring(6, batch_old.length()).equals("01")){
			Batch = batch_old.substring(0, 6)+"16";
		}else if(batch_old.substring(6, batch_old.length()).equals("16")){
			if(batch_old.substring(4, 6).equals("12")){
				Batch = Integer.parseInt(batch_old.substring(0, 4))+ 1 +"0101";
			}else{
				if(Integer.parseInt(batch_old.substring(4, 6)) >= 10){
					Batch = batch_old.substring(0, 4) + (Integer.parseInt(batch_old.substring(4, 6)) + 1) + "01";
				}else{
					Batch = batch_old.substring(0, 4) + "0" + (Integer.parseInt(batch_old.substring(4, 6)) + 1) + "01";
				}
			}
		}else if(Integer.parseInt(batch_old.substring(6, 8)) > 1 
				&& Integer.parseInt(batch_old.substring(6, 8)) < 16){
			
			Batch = batch_old.substring(0, 6) + "16";
		}else if(Integer.parseInt(batch_old.substring(6, 8)) > 16
				|| batch_old.substring(6, 8).equals("00")){
			
			if(batch_old.substring(4, 6).equals("12")){
				Batch = Integer.parseInt(batch_old.substring(0, 4))+ 1 +"0101";
			}else{
				Batch = batch_old.substring(0, 4) + (Integer.parseInt(batch_old.substring(4, 6)) + 1) + "01";
			}
		}
		return Batch;
	}

	/**
	 * 根据批次号赋值开始和结束时间
	 * @param param
	 * @param batch_old
	 * @param Batch
	 */
	private Map<String, Object> setStartEndDateForBatch(Map<String, Object> param, String batch_old, String Batch) {
		if(batch_old != null && !batch_old.equals("")){
			String begin_date = batch_old.substring(0, 4)+"-"+batch_old.substring(4, 6)+"-"+batch_old.substring(6, 8);
			param.put("BEGIN_DATE", begin_date);
		}
		
		if(Batch != null && !Batch.equals("")){
			String end_date = Batch.substring(0, 4)+"-"+Batch.substring(4, 6)+"-"+Batch.substring(6, 8);
			param.put("END_DATE", end_date);
		}
		
		return param;
	}

	/**
	 * 租金划扣明细表数据查询
	 * @param param
	 * @return
	 */
	public Page getRentDrawDetailPage(Map<String, Object> param) {
		
		Page page = null;
		
		// 代扣清单
		if(StringUtils.isNotBlank(param.get("OVERDUE"))){
			
			// 取当前时间的前三个月和后三个月的明细记录
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	// 格式转换
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			
			param.put("BEGIN_DATE", sdf.format(calendar.getTime()));
			
			calendar.add(Calendar.MONTH, 6);
			param.put("END_DATE", sdf.format(calendar.getTime()));
			
			param.put("OVERDUE", param.get("OVERDUE"));
			
			page = PageUtil.getPage(param, "rentDraw.getRentDrawDetailPage", "getRentDrawDetailCount");
		}else{
			
			// 根据批次号查询明细记录
			String batch_old = param.get("BATCH")==null?"":param.get("BATCH").toString();		//批次号
			
			String Batch = "";
			
			Batch = setBatchDate(Batch, batch_old);
			
			param = setStartEndDateForBatch(param, batch_old, Batch);
			
			page = PageUtil.getPage(param, "rentDraw.getRentDrawDetailPage", "getRentDrawDetailCount");
		}
		
		return page;
	}


	/**
	 * 租金划扣明细日志数据查询
	 * @param param
	 * @return
	 */
	public Page getRentDrawLogPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "rentDraw.getRentDrawLogPage", "getRentDrawLogCount");
	}
	
	
}
